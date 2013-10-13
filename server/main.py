#!/usr/bin/env python
# coding: utf-8
#
# Copyright 2007 Google Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
import webapp2, datetime
from google.appengine.ext import db
from modelos import *


from google.appengine.api import users


permission_type = {
1:'acompanhar_qualquer',
2:'acompanhar_proprios',
3:'legalidade',
4:'autorizacao',
5:'conferencia',
6:'minuta',
7:'legalidade_materiais',
8:'pregao',
9:'licitacao',
10:'adjudicacao',
11:'homologacao',
12:'publicacao',
13:'minuta_empenho',
14:'detalhamento',
15:'empenho',
16:'nota_almoxarifado',
17:'tombamento',
18:'nota_contabilidade',
19:'liquidacao',
20:'pagamento',
21:'criacao'
}

users_permission = { 
users.User("usuario_qualquer@example.com"): [2],
users.User("joopeeds@gmail.com"): [1, 21],
users.User("jcafigueiredo@gmail.com"): [1, 21],
users.User("daltonserey@gmail.com"): [1, 21]
}
    
class MainHandler(webapp2.RequestHandler):
    def get(self):
        body = open("base.html").read()
        self.response.out.write('<html><body>%s</body></html>' % body)





class PermissoesHandler(webapp2.RequestHandler):
    def get(self):
        import json
        if not users.get_current_user():
            self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
            self.response.out.write(json.dumps({'status':'Disconnected', 'url': users.create_login_url('/')}))
        else:
            self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
            permissions = []
            for permission_number in users_permission[users.get_current_user()]:
                permissions.append(permission_type[permission_number])
            self.response.out.write(json.dumps({'status':'Connected', 'permissoes': permissions }))    


        
class LoginHandler(webapp2.RequestHandler):
    def get(self):
        import json
        if not users.get_current_user():
            self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
            self.response.out.write(json.dumps({'status':'Disconnected', 'url': users.create_login_url('/')}))
        else:
            self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
            self.response.out.write(json.dumps({'status':'Connected', 'url': users.create_logout_url('/'), 'user_email': users.get_current_user().email(), 'user_nickname':           users.get_current_user().nickname(), 'user_user_id': users.get_current_user().user_id()}))        

class ListaPedido(webapp2.RequestHandler):
    def get(self):
        import json
        if not users.get_current_user():
            self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
            self.response.out.write(json.dumps({'status':'Disconnected', 'url': users.create_login_url('/')}))
        else:
            dic = {}
            if self.request.get("numero"):
                for pedido in db.GqlQuery("SELECT * FROM Pedido WHERE numero in ('"+self.request.get("numero")+"')"):
                    dic = {
                        "numero": pedido.numero,
                        "demandante": pedido.demandante,
                        "descricao": pedido.descricao,
                        "data_entrada": pedido.data_entrada,
                        "email_demandante": pedido.email_demandante,
                        "local": pedido.local,
                        "legalidade": { "status": pedido.legalidade, "choices": pedido.legalidade_choices},
                        "autorizacao": {"status":pedido.autorizacao, "choices":pedido.autorizacao_choices},
                        "conferencia": {"status":pedido.conferencia, "choices":pedido.conferencia_choices}, 
                        "minuta":  {"status":pedido.minuta, "choices":pedido.minuta_choices},
                        "legalidade_materiais":  {"status":pedido.legalidade_materiais, "choices":pedido.legalidade_materiais_choices},
                        "pregao":  {"status":pedido.pregao, "choices":pedido.pregao_choices},
                        "licitacao":  {"status":pedido.licitacao, "choices":pedido.licitacao_choices},
                        "adjudicacao":  {"status":pedido.adjudicacao, "choices":pedido.adjudicacao_choices},
                        "homologacao":  {"status":pedido.homologacao, "choices":pedido.homologacao_choices},
                        "publicacao":  {"status":pedido.publicacao, "choices":pedido.publicacao_choices},
                        "minuta_empenho":  {"status":pedido.minuta_empenho, "choices":pedido.minuta_empenho_choices},
                        "detalhamento":  {"status":pedido.detalhamento, "choices":pedido.detalhamento_choices},
                        "empenho":  {"status":pedido.empenho, "choices":pedido.empenho_choices},
                        "liquidacao":  {"status":pedido.liquidacao, "choices":pedido.liquidacao_choices},
                        "pagamento":  {"status":pedido.pagamento, "choices":pedido.pagamento_choices}                           
                        }

                self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
                self.response.out.write(json.dumps(dict({'status':'Connected'}.items() + dic.items()), indent=2)) 
            else:
                todos = []
                if 1 in users_permission[users.get_current_user()]:
                     query = db.GqlQuery("SELECT numero FROM Pedido")
                else:
                     query = db.GqlQuery("SELECT * FROM Pedido WHERE email_demandante in ('"+users.get_current_user().email()+"')")
                for each in list(query):
                       todos.append(each.numero)
                self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
                self.response.out.write(json.dumps({'status':'Connected', 'pedidos': todos}))    















#Fazendo
class GetPedido(webapp2.RequestHandler):
    def get(self):
            import json
            query = self.request.get("q")
            if query:
                dic = {}
                for pedido in db.GqlQuery("SELECT * FROM Pedido WHERE numero in ('"+query+"')"):
                    dic = {
                        "numero": pedido.numero,
                        "demandante": pedido.demandante,
                        "descricao": pedido.descricao,
                        "data_entrada": pedido.data_entrada.isoformat(),
                        "email_demandante": pedido.email_demandante,
                        "local": pedido.local,
                        "historico_data": [ data.isoformat() for data in pedido.historico_data ],
                        "historico_info": pedido.historico_info,
                        "legalidade": { 
                                    "parecer": pedido.legalidade_parecer, 
                                    "data_envio": pedido.legalidade_data_envio.isoformat() if pedido.legalidade_data_envio else "", 
                                    "data_retorno": pedido.legalidade_data_retorno.isoformat() if pedido.legalidade_data_retorno else "" },
                        "autorizacao": {"parecer":pedido.autorizacao_parecer },
                        "corretude": {
                                    "descricao":pedido.corretude_descricao, 
                                    "quantitivo":pedido.corretude_quantitativo, 
                                    "cotacao":pedido.corretude_cotacao, 
                                    "data":pedido.corretude_data.isoformat()  if pedido.corretude_data else "" }, 
                        "minuta":  { "parecer": pedido.minuta_parecer, 
                                    "data_inicio": [ data.isoformat() for data in pedido.minuta_data_inicio ], 
                                    "data_envio":  [ data.isoformat() for data in pedido.minuta_data_envio ], 
                                    "data_retorno": [ data.isoformat() for data in pedido.minuta_data_retorno ]}, 
                        "pregao": { "parecer": pedido.pregao_parecer,
                                    "pregao_data": [ data.isoformat() for data in pedido.pregao_data ],
                                    "pregao_numero": pedido.pregao_numero,
                                    "pregao_licitacao_data": [ data.isoformat() for data in pedido.pregao_licitacao_data ], 
                                    },
                        "adjudicacao": pedido.adjudicacao_data.isoformat()  if pedido.adjudicacao_data else "" ,
                        "homologacao":  pedido.homologacao_data.isoformat() if pedido.homologacao_data else "",
                        "publicacao": pedido.publicacao_data.isoformat() if pedido.publicacao_data else "",
                        "detalhamento": {"parecer":pedido.detalhamento_parecer, 
                                         "data":pedido.detalhamento_data.isoformat() if pedido.detalhamento_data else ""},
                        "empenho": pedido.empenho_data,
                        "nota_almoxarifado":pedido.nota_almoxarifado_data.isoformat() if  pedido.nota_almoxarifado_data else "",
                        "patrimonio":pedido.patrimonio_data.isoformat() if  pedido.patrimonio_data else "",
                        "nota_contabilidade":pedido.nota_contabilidade_data.isoformat() if pedido.nota_contabilidade_data else "",
                        "liquidacao": pedido.liquidacao_data.isoformat() if pedido.liquidacao_data else "",
                        "pagamento": pedido.pagamento_data.isoformat() if pedido.pagamento_data else ""                        
                        }

                self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
                self.response.out.write(json.dumps(dict({'status':'Connected'}.items() + dic.items()), indent=2)) 





class ListaPedidoForTable(webapp2.RequestHandler):
    def get(self):
            import json
            dic = {"pedidos": []}
            search = self.request.get("search")
            query = db.GqlQuery("SELECT * FROM Pedido ORDER BY data_entrada DESC")
            for pedido in query:
                    tudo = pedido.numero + pedido.demandante + pedido.descricao + pedido.data_entrada
                    pedido_info = {
                        "numero": pedido.numero,
                        "demandante": pedido.demandante,
                        "descricao": pedido.descricao,
                        "data_entrada": pedido.data_entrada,
                        "email_demandante": pedido.email_demandante,
                        "local": pedido.local, 
                        "legalidade":  pedido.legalidade,
                        "autorizacao": pedido.autorizacao, 
                        "conferencia": pedido.conferencia, 
                        "minuta":  pedido.minuta, 
                        "legalidade_materiais": pedido.legalidade_materiais, 
                        "pregao":  pedido.pregao, 
                        "licitacao":  pedido.licitacao, 
                        "adjudicacao":  pedido.adjudicacao, 
                        "homologacao":  pedido.homologacao,
                        "publicacao": pedido.publicacao,
                        "minuta_empenho": pedido.minuta_empenho, 
                        "detalhamento": pedido.detalhamento, 
                        "empenho":  pedido.empenho, 
                        "almoxarifado":  pedido.nota_almoxarifado, 
                        "tombamento":  pedido.tombamento, 
                        "contabilidade":  pedido.nota_contabilidade, 
                        "liquidacao": pedido.liquidacao, 
                        "pagamento": pedido.pagamento                 
                        }
                    if search.lower() in tudo.lower():
                        dic["pedidos"].append(pedido_info)


            self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
            self.response.out.write(json.dumps(dict({'status':'Connected'}.items() + dic.items()), indent=2)) 
            




class InitSys(webapp2.RequestHandler):
    def get(self):
        #if users.get_current_user(): #and 21 in users_permission[users.get_current_user()]:
            for i in range(20):
                novo = Pedido(key_name="CG26388734"+str(i), demandante="Joao Pedro Ferreira de Melo Leoncio", 
                              data_entrada=datetime.datetime(2005, 7, 14, 12, 30), 
                              descricao="Pedido de exemplo para que o sistema funcione inicialmente", 
                              numero="CG26388734"+str(i), 
                              email_demandante="joopeeds@gmail.com")
                novo.historico_info.append("Pedido criado no sistema")
                novo.historico_data.append(datetime.datetime.now())
                novo.put()



class CadastraPedido(webapp2.RequestHandler):
    def post(self):
        #if users.get_current_user(): #and 21 in users_permission[users.get_current_user()]:
            novo = Pedido(demandante=self.request.get("demandante"), 
                          data_entrada=self.request.get("data_entrada"), 
                          descricao=self.request.get("descricao"), 
                          numero=self.request.get("numero"), 
                          email_demandante=self.request.get("email_demandante"))
            novo.put()

class SetPedido(webapp2.RequestHandler):
    def get(self):
        #if users.get_current_user(): #and 21 in users_permission[users.get_current_user()]:
        if self.request.get("numero") and self.request.get("demandante") and self.request.get("data_entrada") and self.request.get("descricao") and self.request.get("email_demandante"):
                novo = Pedido(key_name=self.request.get("numero"), 
                              demandante=self.request.get("demandante"), 
                              data_entrada=datetime.datetime.strptime(self.request.get("data_entrada"), "%Y-%m-%dT%H:%M:%S"), 
                              descricao=self.request.get("descricao"), 
                              numero=self.request.get("numero"), 
                              email_demandante=self.request.get("email_demandante"))
                novo.historico_info.append("Pedido criado no sistema")
                novo.historico_data.append(datetime.datetime.now())
                novo.put()
        elif self.request.get("numero"):
                pedido = searchkey(self.request.get("numero"))
                if self.request.get("demandante"): pedido.demandante = self.request.get("demandante")
                if self.request.get("data_entrada"): pedido.data_entrada = datetime.datetime.strptime(self.request.get("data_entrada"), "%Y-%m-%dT%H:%M:%S")
                if self.request.get("descricao"): pedido.descricao=self.request.get("descricao")
                if self.request.get("email_demandante"): pedido.email_demandante=self.request.get("email_demandante")
                pedido.put()


def searchkey(key):
       return list(db.GqlQuery("SELECT * FROM Pedido WHERE numero in ('"+key+"')"))[0]

class SearchPedido(webapp2.RequestHandler):

    # TODO Ajeitar esta seboseira
    def get(self):
            import json
            dic = {"pedidos": []}
            search = self.request.get("q")
            query = db.GqlQuery("SELECT * FROM Pedido ORDER BY data_entrada DESC")

            if search.lower() == 'legalidade:legal':
                for pedido in query:
                    if pedido.legalidade_parecer:
                        pedido_info = { "numero": pedido.numero,
                                        "demandante": pedido.demandante,
                                        "descricao": pedido.descricao,
                                        "data_entrada": pedido.data_entrada.isoformat(),
                                        "local": pedido.local,
                                        "estados_concluidos": pedido.estados_concluidos(),
                                        "estados_totais": pedido.estados_totais()
                                        }
                        dic["pedidos"].append(pedido_info)
                        
            elif search.lower() == 'legalidade:ilegal':
                for pedido in query:
                    if not pedido.legalidade_parecer:
                        pedido_info = { "numero": pedido.numero,
                                        "demandante": pedido.demandante,
                                        "descricao": pedido.descricao,
                                        "data_entrada": pedido.data_entrada.isoformat(),
                                        "local": pedido.local,
                                        "estados_concluidos": pedido.estados_concluidos(),
                                        "estados_totais": pedido.estados_totais()
                                        }
                        dic["pedidos"].append(pedido_info)
            
            elif search.lower() == 'legalidade:indefinido' or search.lower() == 'legalidade:indefinida':
                for pedido in query:
                    if pedido.legalidade_parecer == None:
                        pedido_info = { "numero": pedido.numero,
                                        "demandante": pedido.demandante,
                                        "descricao": pedido.descricao,
                                        "data_entrada": pedido.data_entrada.isoformat(),
                                        "local": pedido.local,
                                        "estados_concluidos": pedido.estados_concluidos(),
                                        "estados_totais": pedido.estados_totais()
                                        }
                        dic["pedidos"].append(pedido_info)

            else:
                for pedido in query:
                    tudo = pedido.numero + pedido.demandante + pedido.descricao + pedido.data_entrada.isoformat()
                    if search.lower() in tudo.lower():
                        pedido_info = { "numero": pedido.numero, 
                                        "demandante": pedido.demandante,        
                                        "descricao": pedido.descricao, 
                                        "data_entrada": pedido.data_entrada.isoformat(),
                                        "local": pedido.local,
                                        "estados_concluidos": pedido.estados_concluidos(),
                                        "estados_totais": pedido.estados_totais()
                                        }
                        dic["pedidos"].append(pedido_info)

            self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
            self.response.out.write(json.dumps(dict({'status':'Connected'}.items() + dic.items()), indent=2))



	

#HANDLERS DOS ESTADOS
     
#Legalidade
class LegalidadeHandler(webapp2.RequestHandler):
        def get(self):
     
            import json
     
            pedido = self.request.get("pedido")
            data_envio = dateutil.parser.parse(self.request.get("data_envio"))
            data_retorno = dateutil.parser.parse(self.request.get("data_retorno"))
            parecer = self.request.get("parecer")
           
            if pedido:
                pedido_em_questao = Pedido.get(pedido)
                if parecer: 
                    pedido_em_questao.legalidade_parecer = parecer
                if data_envio: pedido_em_questao.legalidade_data_envio = data_envio
                if data_retorno: pedido_em_questao.legalidade_data_retorno = data_retorno
            else:
                self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
                self.response.out.write(json.dumps(dict({'erro':'numero de pedido inexistente'}.items()) ))
     
            self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
            self.response.out.write(json.dumps(dict({'status':'Connected'}.items()), indent=2))
     
#Autorizacao
class AutorizacaoHandler(webapp2.RequestHandler):
        def get(self):
     
            import json
     
            pedido = self.request.get("pedido")
            parecer = self.request.get("parecer")
           
            if pedido:
                pedido_em_questao = Pedido.get(pedido)
                if parecer: pedido_em_questao.autorizacao_parecer = parecer
            else:
                self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
                self.response.out.write(json.dumps(dict({'erro':'numero de pedido inexistente'}.items()) ))
     
            self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
            self.response.out.write(json.dumps(dict({'status':'Connected'}.items()), indent=2))
     
#Corretude
class CorretudeHandler(webapp2.RequestHandler):
        def get(self):
     
            import json
     
            pedido = self.request.get("pedido")
            descricao = self.request.get("descricao")
            quantitativo = self.request.get("quantitativo")
            cotacao = self.request.get("cotacao")
            data = self.request.get("data")
           
            if pedido:
                pedido_em_questao = Pedido.get(pedido)
                if descricao: pedido_em_questao.corretude_descricao = descricao
                if quantitativo:pedido_em_questao.corretude_quantitativo = quantitativo
                if cotacao: pedido_em_questao.corretude_cotacao = cotacao
                if data: pedido_em_questao.corretude_data = data
            else:
                self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
                self.response.out.write(json.dumps(dict({'erro':'numero de pedido inexistente'}.items()) ))
     
            self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
            self.response.out.write(json.dumps(dict({'status':'Connected'}.items()), indent=2))
     
#Minuta
class MinutaHandler(webapp2.RequestHandler):
        def get(self):
     
            import json
     
            pedido = self.request.get("pedido")
            parecer = self.request.get("parecer")
            data_inicio = dateutil.parser.parse(self.request.get("data_inicio"))
            data_envio = dateutil.parser.parse(self.request.get("data_envio"))
            data_retorno = dateutil.parser.parse(self.request.get("data_retorno"))
     
            if pedido:
                pedido_em_questao = Pedido.get(pedido)
                if parecer: pedido_em_questao.minuta_parecer.append(parecer)
                if data_inicio: pedido_em_questao.minuta_data_inicio.append(data_inicio)
                if data_envio: pedido_em_questao.minuta_data_envio.append(data_envio)
                if data_retorno: pedido_em_questao.minuta_data_retorno.append(data_retorno)
            else:
                self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
                self.response.out.write(json.dumps(dict({'erro':'numero de pedido inexistente'}.items()) ))
     
            self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
            self.response.out.write(json.dumps(dict({'status':'Connected'}.items()), indent=2))
     
#Pregao
class PregaoHandler(webapp2.RequestHandler):
        def get(self):
     
            import json
     
            pedido = self.request.get("pedido")
            parecer = parecer = self.request.get("parecer")
            data = dateutil.parser.parse(self.request.get("data"))
            numero = self.request.get("numero")
            licitacao_data = dateutil.parser.parse(self.request.get("licitacao_data"))
           
            if pedido:
                pedido_em_questao = Pedido.get(pedido)
                if parecer: pedido_em_questao.pregao_parecer.append(parecer)
                if data: pedido_em_questao.pregao_data.append(data)
                if numero: pedido_em_questao.pregao_numero.append(numero)
                if licitacao_data: pedido_em_questao.pregao_licitacao_data.append(licitacao_data)
            else:
                self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
                self.response.out.write(json.dumps(dict({'erro':'numero de pedido inexistente'}.items()) ))
     
            self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
            self.response.out.write(json.dumps(dict({'status':'Connected'}.items()), indent=2))
     
#Adjudicacao
class AdjudicacaoHandler(webapp2.RequestHandler):
        def get(self):
     
            import json
     
            pedido = self.request.get("pedido")
            data = dateutil.parser.parse(self.request.get("data"))
     
            if pedido:
                pedido_em_questao = Pedido.get(pedido)
                if data: pedido_em_questao.adjudicacao_data = data
            else:
                self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
                self.response.out.write(json.dumps(dict({'erro':'numero de pedido inexistente'}.items()) ))
     
            self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
            self.response.out.write(json.dumps(dict({'status':'Connected'}.items()), indent=2))
     
#Homologacao
class HomologacaoHandler(webapp2.RequestHandler):
        def get(self):
     
            import json
     
            pedido = self.request.get("pedido")
            data = dateutil.parser.parse(self.request.get("data"))
     
            if pedido:
                pedido_em_questao = Pedido.get(pedido)
                if data: pedido_em_questao.homologacao_data = data
            else:
                self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
                self.response.out.write(json.dumps(dict({'erro':'numero de pedido inexistente'}.items()) ))
     
            self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
            self.response.out.write(json.dumps(dict({'status':'Connected'}.items()), indent=2))
     
#Publicacao
class PublicacaoHandler(webapp2.RequestHandler):
        def get(self):
     
            import json
     
            pedido = self.request.get("pedido")
            data = dateutil.parser.parse(self.request.get("data"))
     
            if pedido:
                pedido_em_questao = Pedido.get(pedido)
                if data: pedido_em_questao.publicacao_data = data
            else:
                self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
                self.response.out.write(json.dumps(dict({'erro':'numero de pedido inexistente'}.items()) ))
     
            self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
            self.response.out.write(json.dumps(dict({'status':'Connected'}.items()), indent=2))
     
#Detalhamento
class DetalhamentoHandler(webapp2.RequestHandler):
        def get(self):
     
            import json
     
            pedido = self.request.get("pedido")
            parecer = self.request.get("parecer")
            data = dateutil.parser.parse(self.request.get("data"))
     
            if pedido:
                pedido_em_questao = Pedido.get(pedido)
                if parecer: pedido_em_questao.detalhamento_parecer = parecer
                if data: pedido_em_questao.detalhamento_data = data
            else:
                self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
                self.response.out.write(json.dumps(dict({'erro':'numero de pedido inexistente'}.items()) ))
     
            self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
            self.response.out.write(json.dumps(dict({'status':'Connected'}.items()), indent=2))
     
#Empenho
class EmpenhoHandler(webapp2.RequestHandler):
        def get(self):
     
            import json
     
            pedido = self.request.get("pedido")
            data = dateutil.parser.parse(self.request.get("data"))
     
            if pedido:
                pedido_em_questao = Pedido.get(pedido)
                if data: pedido_em_questao.empenho_data = data
            else:
                self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
                self.response.out.write(json.dumps(dict({'erro':'numero de pedido inexistente'}.items()) ))
     
            self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
            self.response.out.write(json.dumps(dict({'status':'Connected'}.items()), indent=2))
     
#Nota do almoxarifado
class NotaAlmoxarifadoHandler(webapp2.RequestHandler):
        def get(self):
     
            import json
     
            pedido = self.request.get("pedido")
            data = dateutil.parser.parse(self.request.get("data"))
     
            if pedido:
                pedido_em_questao = Pedido.get(pedido)
                if data: pedido_em_questao.nota_almoxarifado_data = data
            else:
                self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
                self.response.out.write(json.dumps(dict({'erro':'numero de pedido inexistente'}.items()) ))
     
            self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
            self.response.out.write(json.dumps(dict({'status':'Connected'}.items()), indent=2))
     
#Patrimonio
class PatrimonioHandler(webapp2.RequestHandler):
        def get(self):
     
            import json
     
            pedido = self.request.get("pedido")
            data = dateutil.parser.parse(self.request.get("data"))
     
            if pedido:
                pedido_em_questao = Pedido.get(pedido)
                if data: pedido_em_questao.patrimonio_data = data
            else:
                self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
                self.response.out.write(json.dumps(dict({'erro':'numero de pedido inexistente'}.items()) ))
     
            self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
            self.response.out.write(json.dumps(dict({'status':'Connected'}.items()), indent=2))
     
#Nota da contabilidade
class NotaContabilidadeHandler(webapp2.RequestHandler):
        def get(self):
     
            import json
     
            pedido = self.request.get("pedido")
            data = dateutil.parser.parse(self.request.get("data"))
     
            if pedido:
                pedido_em_questao = Pedido.get(pedido)
                if data: pedido_em_questao.nota_contabilidade_data = data
            else:
                self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
                self.response.out.write(json.dumps(dict({'erro':'numero de pedido inexistente'}.items()) ))
     
            self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
            self.response.out.write(json.dumps(dict({'status':'Connected'}.items()), indent=2))
     
#Liquidacao
class LiquidacaoHandler(webapp2.RequestHandler):
        def get(self):
     
            import json
     
            pedido = self.request.get("pedido")
            data = dateutil.parser.parse(self.request.get("data"))
     
            if pedido:
                pedido_em_questao = Pedido.get(pedido)
                if data: pedido_em_questao.liquidacao_data = data
            else:
                self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
                self.response.out.write(json.dumps(dict({'erro':'numero de pedido inexistente'}.items()) ))
     
            self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
            self.response.out.write(json.dumps(dict({'status':'Connected'}.items()), indent=2))
     
#Pagamento
class PagamentoHandler(webapp2.RequestHandler):
        def get(self):
     
            import json
     
            pedido = self.request.get("pedido")
            data = dateutil.parser.parse(self.request.get("data"))
     
            if pedido:
                pedido_em_questao = Pedido.get(pedido)
                if data: pedido_em_questao.pagamento_data = data
            else:
                self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
                self.response.out.write(json.dumps(dict({'erro':'numero de pedido inexistente'}.items()) ))
     
            self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
            self.response.out.write(json.dumps(dict({'status':'Connected'}.items()), indent=2))




app = webapp2.WSGIApplication([('/', MainHandler), ('/LoginHandler', LoginHandler), ('/inicializar', InitSys), ('/Pedido', ListaPedido), ('/setpedido', SetPedido), ('/getpedido', GetPedido),('/searchpedido', SearchPedido),('/PedidosForTable', SearchPedido), ('/Permissoes', PermissoesHandler), ('/CadastraPedido', CadastraPedido)],debug=True)
