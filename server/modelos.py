#coding: utf-8
import webapp2, datetime
from google.appengine.ext import db
from google.appengine.api.users import User

class Usuario(db.Model):
    usuario_google = db.UserProperty(required=True)
    nome = db.StringProperty(required=True)
    email = db.EmailProperty(required=True)
    setor = db.StringProperty(required=True)

class Entrada(db.Model):
    data = db.DateTimeProperty(required=True)
    user = db.StringProperty(required=True)

class Workflow:

    def __init__(self, tipo):
        #super(Workflow, self).__init__(**kwargs)
        if tipo == "pregao" or  tipo == "sessao":
            self.nomes_publicos = ["Legalidade", "Autorização", "Corretude", "Minuta", "Pregão", "Adjudicação", "Homologação", "Publicação", "Empenho", "Recebimento"]
            self.nomes = ["legalidade", "autorizacao", "corretude", "minuta", "pregao", "adjudicacao", "homologacao", "publicacao", "empenho", "recebimento"]
            self.condicoes_saida = ["parecer", "parecer", "descricao, quantitativo, cotacao", "parecer", "parecer", "data", "data", "data", "data, nota_almoxarifado_data, patrimonio_data", "nota_contabilidade_data, liquidacao_data"]
            self.condicoes_fim = ["not parecer", "not parecer", "not descricao, quantitativo, cotacao","not parecer", "not parecer", "", "", "", "", ""]
            self.locais = ["Procuradoria", "PRA / Gabinete", "PRA / Coordenação de Compras", "PRA / Coordenação de Compras", "PRA / Coordenação de Compras / DM", "PRA / ComissÃ£o Permanente de Licitação", "PRA / Gabinete", "PRA / Comissão Permanente de Licitação", "PRA / Contabilidade", "PRA / Almoxarifado"]
        else:
            self.nomes_publicos = ["Legalidade", "Corretude",  "Pregão",  "Publicação", "Empenho", "Recebimento"]
            self.nomes = ["legalidade", "corretude",  "pregao",  "publicacao", "empenho", "recebimento"]
            self.condicoes_saida = ["parecer", "descricao, quantitativo, cotacao",  "parecer", "data", "data, nota_almoxarifado_data, patrimonio_data", "nota_contabilidade_data, liquidacao_data"]
            self.condicoes_fim = ["not parecer",  "not descricao, quantitativo, cotacao", "not parecer","", "", ""]
            self.locais = ["Procuradoria", "PRA / Coordenação de Compras",  "PRA / Coordenação de Compras / DM", "PRA / Comissção Permanente de Licitação", "PRA / Contabilidade", "PRA / Almoxarifado"]

    def _indice_atual(self, pedido):
        for i in range(len(self.nomes)):
            nome = self.nomes[i]
            condicoes_saida = self.condicoes_saida[i].split(", ")
            status = True
            for condicao_saida in condicoes_saida:
                status = status and pedido.get(nome + "_" + condicao_saida)
            if not status:
                return i
        return i

    def local_atual(self, pedido):
        return self.locais[self._indice_atual(pedido)]

    def nome_atual(self, pedido):
        return self.nomes[self._indice_atual(pedido)]

    def status_atual(self, pedido):
        """
        1 para em andamento
        2 para True
        3 para False

        """
        i = self._indice_atual(pedido)


        nome = self.nomes[i]
        condicoes_saida = self.condicoes_saida[i].split(", ")
        status = True
        for condicao_saida in condicoes_saida:
            status = status and pedido.get(nome + "_" + condicao_saida)
        if status:
            #Caso seja ultima etapa e estiver concluida, retorna 2
            return 2
        
        condicoes_fim = self.condicoes_fim[i]
        if not condicoes_fim:
            return 1
        assert condicoes_fim.startswith("not")
        
        status_fim = False
        for condicao_fim in condicoes_fim[4:].split(", "):
                status_fim = status_fim or pedido.get(nome + "_" + condicao_fim) == False
        
        if status_fim:
            return 3
        
        return 1

    def lista_status(self, pedido):
        lista_de_status = [2] * (self._indice_atual(pedido))
        lista_de_status.append(self.status_atual(pedido))
        lista_de_status += [0] * (len(self.nomes) - (self._indice_atual(pedido)+1))
        return lista_de_status

class Pedido(db.Model):
    demandante = db.StringProperty(required=True)
    data_entrada = db.DateTimeProperty(required=True)
    descricao = db.StringProperty(required=True)
    numero = db.StringProperty(required=True)
    email_demandante = db.StringProperty(required=True)
    local = db.StringProperty(default = "PRA/Gabinete")
    tipo_pedido = db.StringProperty(required=True, default = "pregao")

    historico_data = db.ListProperty(datetime.datetime, indexed=True, default=[]) 
    historico_info = db.StringListProperty(str, indexed=True, default=[]) 
    historico_user = db.StringListProperty(str, indexed=True, default=[]) 

    legalidade_parecer = db.BooleanProperty()
    legalidade_data_envio = db.DateTimeProperty()
    legalidade_data_retorno = db.DateTimeProperty()    

    autorizacao_parecer = db.BooleanProperty()

    corretude_descricao = db.BooleanProperty()
    corretude_quantitativo = db.BooleanProperty()
    corretude_cotacao = db.BooleanProperty()
    corretude_data = db.DateTimeProperty()  
    
    #Listas em que um indice representa um ciclo completo de elaboracao da minuta
    
    minuta_parecer = db.ListProperty(bool, indexed=True, default=[])
    minuta_data_inicio = db.ListProperty(datetime.datetime, indexed=True, default=[])      
    minuta_data_envio = db.ListProperty(datetime.datetime, indexed=True, default=[]) 
    minuta_data_retorno = db.ListProperty(datetime.datetime, indexed=True, default=[]) 
    
    #ciclo do pregao
    pregao_indice = db.IntegerProperty(default=-1)
    pregao_parecer = db.ListProperty(bool, indexed=True, default=[])
    pregao_data = db.ListProperty(datetime.datetime, indexed=True, default=[]) 
    pregao_numero = db.ListProperty(str, indexed=True, default=[]) 
    pregao_licitacao_data = db.ListProperty(datetime.datetime, indexed=True, default=[])

    adjudicacao_data = db.DateTimeProperty()
  
    homologacao_data = db.DateTimeProperty()  

    publicacao_data = db.DateTimeProperty()  

    detalhamento_parecer = db.BooleanProperty()  
    detalhamento_data = db.DateTimeProperty()  

    empenho_data = db.DateTimeProperty() 
    
    empenho_nota_almoxarifado_data = db.DateTimeProperty()
    
    empenho_patrimonio_data = db.DateTimeProperty()
    
    recebimento_nota_contabilidade_data = db.DateTimeProperty() #Provisoria
    
    recebimento_liquidacao_data = db.DateTimeProperty() #Definitiva

    pagamento_data = db.DateTimeProperty() #Inutil

    def get(self, atributo):
        if type(getattr(self, atributo)) != list:
            return getattr(self, atributo)
        elif len(getattr(self, atributo)) > 0:
            return getattr(self, atributo)[-1]
        else:
            return None

    def get_local_atual(self):
        return Workflow(self.tipo_pedido).local_atual(self)

    def get_lista_status(self):
        return Workflow(self.tipo_pedido).lista_status(self)

    def get_lista_nomes(self):
        return Workflow(self.tipo_pedido).nomes_publicos

