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
    licitacao_data = db.ListProperty(datetime.datetime, indexed=True, default=[]) 

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
                                    "licitacao_data": [ data.isoformat() for data in pedido.licitacao_data ], 
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
            novo = Pedido(key_name="CG26388734", demandante="Joao Pedro Ferreira de Melo Leoncio", 
                          data_entrada=datetime.datetime(2005, 7, 14, 12, 30), 
                          descricao="Pedido de exemplo para que o sistema funcione inicialmente", 
                          numero="CG26388734", 
                          email_demandante="joopeeds@gmail.com")
            novo.historico_info.append("Pedido criado no sistema")
            novo.historico_data.append(datetime.datetime(2013, 10, 5, 20, 27))
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
            






app = webapp2.WSGIApplication([('/', MainHandler), ('/LoginHandler', LoginHandler), ('/inicializar', InitSys), ('/Pedido', ListaPedido), ('/getpedido', GetPedido),('/PedidosForTable', ListaPedidoForTable), ('/Permissoes', PermissoesHandler), ('/CadastraPedido', CadastraPedido)],debug=True)
