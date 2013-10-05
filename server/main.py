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

    historico_data = db.ListProperty() 
    historico_info = db.StringListProperty() 

    legalidade_parecer = db.BooleanProperty()
    legalidade_data_envio = db.DateTimeProperty()
    legalidade_data_retorno = db.DateTimeProperty()    

    autorizacao_parecer = db.BooleanProperty()

    corretude_descricao = db.BooleanProperty()
    corretude_quantitativo = db.BooleanProperty()
    corretude_cotacao = db.BooleanProperty()
    corretude_data = db.DateTimeProperty()  
    
    #Listas em que um indice representa um ciclo completo de elaboracao da minuta
    minuta_parecer = db.ListProperty()
    minuta_data_inicio = db.ListProperty()     
    minuta_data_envio = db.ListProperty()
    minuta_data_retorno = db.ListProperty()
    
    #ciclo do pregao
    pregao_parecer = db.ListProperty()
    pregao_data = db.ListProperty()
    pregao_numero = db.ListProperty()
    licitacao_data = db.ListProperty()

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
        body = open("client_js/base.html").read()
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
            query = self.request.get("q")
            if query:
                dic = {}
                for pedido in db.GqlQuery("SELECT * FROM Pedido WHERE numero in ('"+query+"')"):
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
            








class CadastraPedido(webapp2.RequestHandler):
    def post(self):
        #if users.get_current_user(): #and 21 in users_permission[users.get_current_user()]:
            novo = Pedido(demandante=self.request.get("demandante"), 
                          data_entrada=self.request.get("data_entrada"), 
                          descricao=self.request.get("descricao"), 
                          numero=self.request.get("numero"), 
                          email_demandante=self.request.get("email_demandante"))
            novo.put()
            






app = webapp2.WSGIApplication([('/', MainHandler), ('/LoginHandler', LoginHandler), ('/Pedido', ListaPedido),('/PedidosForTable', ListaPedidoForTable), ('/Permissoes', PermissoesHandler), ('/CadastraPedido', CadastraPedido)],debug=True)
