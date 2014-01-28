import webapp2, datetime
from google.appengine.ext import db
from google.appengine.api.users import User

class Usuario(db.Model):
    usuario_google = db.UserProperty(required=True)
    nome = db.StringProperty(required=True)
    email = db.EmailProperty(required=True)
    setor = db.StringProperty(required=True)

class Pedido(db.Model):
    demandante = db.StringProperty(required=True)
    data_entrada = db.DateTimeProperty(required=True)
    descricao = db.StringProperty(required=True)
    numero = db.StringProperty(required=True)
    email_demandante = db.StringProperty(required=True)
    local = db.StringProperty(default = "PRA/Gabinete")

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
    
    nota_almoxarifado_data = db.DateTimeProperty() 
    
    patrimonio_data = db.DateTimeProperty() 
    
    nota_contabilidade_data = db.DateTimeProperty() 
    
    liquidacao_data = db.DateTimeProperty() 

    pagamento_data = db.DateTimeProperty() 

    def estados_totais(self):
        return 19
    def estados_concluidos(self):   
        return 1



