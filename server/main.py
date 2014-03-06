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
from google.appengine.api import mail
from google.appengine.datastore.datastore_query import Cursor
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
            novo = Entrada(data=datetime.datetime.now(), user=users.get_current_user().email())
            novo.put()
            

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





class GetHistorico(webapp2.RequestHandler):
    def get(self):
            import json
            query = self.request.get("q")
            if query:
                dic = {}
                for pedido in db.GqlQuery("SELECT * FROM Pedido WHERE numero in ('"+query+"')"):
                    dic = {
                        "historico":{
                                "data": [ (data - datetime.timedelta(hours=3)).isoformat() for data in pedido.historico_data[::-1] ],
                                "info": pedido.historico_info[::-1],
                                "user": pedido.historico_user[::-1]
                           }                    
                        }

                self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
                self.response.out.write(json.dumps(dict({'status':'Connected'}.items() + dic.items()), indent=2)) 











#Fazendo
class GetPedido(webapp2.RequestHandler):
    def get(self):
            import json
            query = self.request.get("q")
            if query:
                dic = {}
                error = "True"
                for pedido in db.GqlQuery("SELECT * FROM Pedido WHERE numero in ('"+query+"')"):
                    error = "False"
                    dic = {
                        "numero": pedido.numero,
                        "demandante": pedido.demandante,
                        "descricao": pedido.descricao,
                        "data_entrada": pedido.data_entrada.isoformat(),
                        "email_demandante": pedido.email_demandante,
                        "local": pedido.local,
                        "legalidade": { 
                                    "parecer": pedido.legalidade_parecer, 
                                    "data_envio": pedido.legalidade_data_envio.isoformat() if pedido.legalidade_data_envio else "", 
                                    "data_retorno": pedido.legalidade_data_retorno.isoformat() if pedido.legalidade_data_retorno else "" },
                        "autorizacao": {"parecer":pedido.autorizacao_parecer },
                        "corretude": {
                                    "descricao":pedido.corretude_descricao, 
                                    "quantitativo":pedido.corretude_quantitativo, 
                                    "cotacao":pedido.corretude_cotacao, 
                                    "data":pedido.corretude_data.isoformat()  if pedido.corretude_data else "" }, 
                        "minuta":  { "parecer": pedido.minuta_parecer, 
                                    "data_inicio": [ data.isoformat() for data in pedido.minuta_data_inicio ], 
                                    "data_envio":  [ data.isoformat() for data in pedido.minuta_data_envio ], 
                                    "data_retorno": [ data.isoformat() for data in pedido.minuta_data_retorno ]}, 
                        "pregao": { "indice": pedido.pregao_indice,
                                    "parecer": pedido.pregao_parecer,
                                    "data": [ data.isoformat() for data in pedido.pregao_data ],
                                    "numero": pedido.pregao_numero,
                                    "licitacao_data": [ data.isoformat() for data in pedido.pregao_licitacao_data ], 
                                    },
                        "adjudicacao": { "data": pedido.adjudicacao_data.isoformat()  if pedido.adjudicacao_data else "" },
                        "homologacao":  { "data": pedido.homologacao_data.isoformat() if pedido.homologacao_data else "" },
                        "publicacao": { "data": pedido.publicacao_data.isoformat() if pedido.publicacao_data else "" },
                        "detalhamento": {"parecer":pedido.detalhamento_parecer, 
                                         "data":pedido.detalhamento_data.isoformat() if pedido.detalhamento_data else ""},
                        "empenho": { "data": pedido.empenho_data.isoformat() if pedido.empenho_data else "" },
                        "nota_almoxarifado": { "data": pedido.nota_almoxarifado_data.isoformat() if  pedido.nota_almoxarifado_data else ""},
                        "patrimonio": { "data": pedido.patrimonio_data.isoformat() if  pedido.patrimonio_data else "" },
                        "nota_contabilidade": { "data": pedido.nota_contabilidade_data.isoformat() if pedido.nota_contabilidade_data else "" },
                        "liquidacao": { "data": pedido.liquidacao_data.isoformat() if pedido.liquidacao_data else "" },
                        "pagamento":  { "data": pedido.pagamento_data.isoformat() if pedido.pagamento_data else "" }                        
                        }

                self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
                self.response.out.write(json.dumps(dict({'status':'Connected', 'error':error}.items() + dic.items()), indent=2)) 



class AdicionaPregao(webapp2.RequestHandler):
    def get(self):
        import json
        q = self.request.get("q")
        pedido_em_questao = searchkey(q) 
        if len(pedido_em_questao.pregao_parecer) == len(pedido_em_questao.pregao_data) == len(pedido_em_questao.pregao_numero) == len(pedido_em_questao.pregao_licitacao_data) and len(pedido_em_questao.pregao_parecer) != 0 and len(pedido_em_questao.pregao_parecer) + 1 > pedido_em_questao.pregao_indice and pedido_em_questao.pregao_parecer[-1] == False:
                pedido_em_questao.pregao_indice += 1
                pedido_em_questao.put()
        self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
        self.response.out.write(json.dumps(dict({'status':'Connected', 'i':pedido_em_questao.pregao_indice, 'io':len(pedido_em_questao.pregao_licitacao_data)}.items()), indent=2)) 

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
       
         for i in range(50):
                novo = Pedido(key_name="CG26388734"+str(i), demandante="Joao Pedro Ferreira de Melo Leoncio", 
                              data_entrada=datetime.datetime(2005, 7, 14, 12, 30), 
                              descricao="Pedido de exemplo para que o sistema funcione inicialmente", 
                              numero="CG26388734"+str(i), 
                              email_demandante="joopeeds@gmail.com")
                novo.historico_info.append("Pedido criado no sistema")
                novo.historico_data.append(datetime.datetime.now())
                novo.historico_user.append(users.get_current_user().email())
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
    def post(self):
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
                novo.historico_user.append(users.get_current_user().email())
                novo.put()
        elif self.request.get("numero"):
                pedido = searchkey(self.request.get("numero"))
                if self.request.get("demandante"): pedido.demandante = self.request.get("demandante")
                if self.request.get("data_entrada"): pedido.data_entrada = datetime.datetime.strptime(self.request.get("data_entrada"), "%Y-%m-%dT%H:%M:%S")
                if self.request.get("descricao"): pedido.descricao=self.request.get("descricao")
                if self.request.get("email_demandante"): pedido.email_demandante=self.request.get("email_demandante")
                #if self.request.get("legalidade"): pedido.legalidade_parecer = True if self.request.get("legalidade") ==  "True" else False
                pedido.put()
        


def searchkey(key):
       return list(db.GqlQuery("SELECT * FROM Pedido WHERE numero in ('"+key+"')"))[0]

class SearchPedido(webapp2.RequestHandler):

    # TODO Ajeitar esta seboseira
    def get(self):
            import json
            dic = {"pedidos": []}
            search = self.request.get("q")
            query = Pedido.all() #db.GqlQuery("SELECT * FROM Pedido ORDER BY data_entrada DESC")
            if search.strip() != "":
                query.filter("numero = ", search)
            query.with_cursor(self.request.get('cursor',default_value=None))
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
                for pedido in query.fetch(10):
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



            if not users.get_current_user():
                self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
                self.response.out.write(json.dumps(dict({'status':'Disconnected', "cursor": query.cursor()}.items() + dic.items()), indent=2))
            else:
                 self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
                 self.response.out.write(json.dumps(dict({'status':'Connected', "cursor": query.cursor()}.items() + dic.items()), indent=2))



	

def notifica(pedido_em_questao, mensagem):
        mail.send_mail(sender="SAPO@UFCG <nao-responda@pra-ufcg.appspotmail.com>",
                                   to=pedido_em_questao.email_demandante,
                                   subject="Seu pedido foi modificado no SAPO@UFCG",
                                   body='Caro(a) '+pedido_em_questao.demandante+':\n\n'+mensagem+'. Acesse\nhttp://pra-ufcg.appspot.com e logue usando sua conta google\npara saber mais.\n\n\nSAPO@UFCG')

#HANDLERS DOS ESTADOS
   
#Legalidade
class LegalidadeHandler(webapp2.RequestHandler):
        def post(self):
     
            import json
     
            pedido = self.request.get("pedido")
            data_envio = self.request.get("data_envio")
            data_retorno = self.request.get("data_retorno")
            parecer = self.request.get("parecer")
           
            if pedido:
                pedido_em_questao = searchkey(pedido) 
                if parecer:
                    if pedido_em_questao.legalidade_parecer == True:
                        parecer_legalidade_anterior = "legal"
                    elif pedido_em_questao.legalidade_parecer == False:
                        parecer_legalidade_anterior = "ilegal"
                    elif pedido_em_questao.legalidade_parecer == None:
                        parecer_legalidade_anterior = "nada"
                    pedido_em_questao.legalidade_parecer = True if parecer == "True" else False
                    if pedido_em_questao.legalidade_parecer:
                        parecer_legalidade_atual = "legal"
                    else:
                        parecer_legalidade_atual = "ilegal"                   
                    pedido_em_questao.historico_info.append("Parecer de legalidade alterado de "+parecer_legalidade_anterior+" para "+parecer_legalidade_atual)
                    pedido_em_questao.historico_data.append(datetime.datetime.now())
                    pedido_em_questao.historico_user.append(users.get_current_user().email())
                    notifica(pedido_em_questao, "O status de legalidade do seu pedido foi alterado")
                if data_envio:
                    if pedido_em_questao.legalidade_data_envio == None:
                        data_legalidade_anterior = "nenhuma"
                    else:
                        data_legalidade_anterior = pedido_em_questao.legalidade_data_envio.strftime("%d/%m/%Y")
                    pedido_em_questao.legalidade_data_envio = datetime.datetime.strptime(data_envio, "%Y-%m-%dT%H:%M:%S")
                    pedido_em_questao.historico_info.append("Data de envio de legalidade alterado de "+data_legalidade_anterior+ " para "+pedido_em_questao.legalidade_data_envio.strftime("%d/%m/%Y"))
                    pedido_em_questao.historico_data.append(datetime.datetime.now())
                    pedido_em_questao.historico_user.append(users.get_current_user().email())
                if data_retorno:
                    if pedido_em_questao.legalidade_data_retorno == None:
                        data_retorno_legalidade_anterior = "nenhuma"
                    else:
                        data_retorno_legalidade_anterior = pedido_em_questao.legalidade_data_retorno.strftime("%d/%m/%Y")
                    pedido_em_questao.legalidade_data_retorno = datetime.datetime.strptime(data_retorno, "%Y-%m-%dT%H:%M:%S")
                    pedido_em_questao.historico_info.append("Data de retorno de legalidade alterado de "+data_retorno_legalidade_anterior+" para "+pedido_em_questao.legalidade_data_retorno.strftime("%d/%m/%Y"))
                    pedido_em_questao.historico_data.append(datetime.datetime.now())
                    pedido_em_questao.historico_user.append(users.get_current_user().email())
                pedido_em_questao.put()
            else:
                self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
                self.response.out.write(json.dumps(dict({'erro':'numero de pedido inexistente'}.items()) ))
     
            self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
            self.response.out.write(json.dumps(dict({'status':'Connected'}.items()), indent=2))
     
#Autorizacao
class AutorizacaoHandler(webapp2.RequestHandler):
        def post(self):
     
            import json
     
            pedido = self.request.get("pedido")
            parecer = self.request.get("parecer")
           
            if pedido:
                pedido_em_questao = searchkey(pedido) 
                if parecer:
                    if pedido_em_questao.autorizacao_parecer == True:
                        parecer_autorizacao_anterior = "legal"
                    elif pedido_em_questao.autorizacao_parecer == False:
                        parecer_autorizacao_anterior = "ilegal"
                    elif pedido_em_questao.autorizacao_parecer == None:
                        parecer_autorizacao_anterior = "nada"
                    pedido_em_questao.autorizacao_parecer = True if parecer == "True" else False
                    if pedido_em_questao.autorizacao_parecer:
                        parecer_autorizacao_atual = "legal"
                    else:
                        parecer_autorizacao_atual = "ilegal"
                    pedido_em_questao.historico_info.append("Parecer de autorizacao alterado de "+parecer_autorizacao_anterior+" para "+parecer_autorizacao_atual)
                    pedido_em_questao.historico_data.append(datetime.datetime.now())
                    pedido_em_questao.historico_user.append(users.get_current_user().email())
                    notifica(pedido_em_questao, "O status de autorizacao do seu pedido foi alterado")
                pedido_em_questao.put()
            else:
                self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
                self.response.out.write(json.dumps(dict({'erro':'numero de pedido inexistente'}.items()) ))
     
            self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
            self.response.out.write(json.dumps(dict({'status':'Connected'}.items()), indent=2))
     
#Corretude
class CorretudeHandler(webapp2.RequestHandler):
        def post(self):
     
            import json
     
            pedido = self.request.get("pedido")
            descricao = self.request.get("descricao")
            quantitativo = self.request.get("quantitativo")
            cotacao = self.request.get("cotacao")
            data = self.request.get("data")
           
            if pedido:
                pedido_em_questao = searchkey(pedido) 
                if descricao:
                    if pedido_em_questao.corretude_descricao == True:
                        corretude_descricao_anterior = "correta"
                    elif pedido_em_questao.corretude_descricao == False:
                        corretude_descricao_anterior = "incorreta"
                    elif pedido_em_questao.corretude_descricao == None:
                        corretude_descricao_anterior = "nada"
                    pedido_em_questao.corretude_descricao = True if descricao == "True" else False
                    if pedido_em_questao.corretude_descricao:
                        corretude_descricao_atual = "correta"
                    else:
                        corretude_descricao_atual = "incorreta"
                    pedido_em_questao.historico_info.append("A corretude da descricao foi definida de "+corretude_descricao_anterior+" para "+corretude_descricao_atual)
                    pedido_em_questao.historico_data.append(datetime.datetime.now())
                    pedido_em_questao.historico_user.append(users.get_current_user().email())
                    notifica(pedido_em_questao, "O status de corretude do seu pedido foi alterado")
                if quantitativo:
                    if pedido_em_questao.corretude_quantitativo == True:
                        corretude_quantitativo_anterior = "correta"
                    elif pedido_em_questao.corretude_quantitativo == False:
                        corretude_quantitativo_anterior = "incorreta"
                    elif pedido_em_questao.corretude_quantitativo == None:
                        corretude_quantitativo_anterior = "nada"
                    pedido_em_questao.corretude_quantitativo = True if quantitativo == "True" else False
                    if pedido_em_questao.corretude_quantitativo:
                        corretude_quantitativo_atual = "correta"
                    else:
                        corretude_quantitativo_atual = "incorreta"
                    pedido_em_questao.historico_info.append("A corretude do quantitativo foi definida de "+corretude_quantitativo_anterior+" para "+corretude_quantitativo_atual)
                    pedido_em_questao.historico_data.append(datetime.datetime.now())
                    pedido_em_questao.historico_user.append(users.get_current_user().email())
                    notifica(pedido_em_questao, "O status de corretude do seu pedido foi alterado")
                if cotacao:
                    if pedido_em_questao.corretude_cotacao == True:
                        corretude_cotacao_anterior = "correta"
                    elif pedido_em_questao.corretude_cotacao == False:
                        corretude_cotacao_anterior = "incorreta"
                    elif pedido_em_questao.corretude_cotacao == None:
                        corretude_cotacao_anterior = "nada"
                    pedido_em_questao.corretude_cotacao = True if cotacao == "True" else False
                    if pedido_em_questao.corretude_cotacao:
                        corretude_cotacao_atual = "correta"
                    else:
                        corretude_cotacao_atual = "incorreta"
                    pedido_em_questao.historico_info.append("A corretude da cotacao foi definida de "+corretude_cotacao_anterior+" para "+corretude_cotacao_atual)
                    pedido_em_questao.historico_data.append(datetime.datetime.now())
                    pedido_em_questao.historico_user.append(users.get_current_user().email())
                    notifica(pedido_em_questao, "O status de corretude do seu pedido foi alterado")
                if data:
                    if pedido_em_questao.corretude_data == None:
                        data_corretude_anterior = "nenhuma"
                    else:
                        data_corretude_anterior = pedido_em_questao.corretude_data.strftime("%d/%m/%Y")
                    pedido_em_questao.corretude_data = datetime.datetime.strptime(data, "%Y-%m-%dT%H:%M:%S")
                    pedido_em_questao.historico_info.append("A data da definicao de corretude foi definida de "+data_corretude_anterior+" para "+pedido_em_questao.corretude_data.strftime("%d/%m/%Y"))
                    pedido_em_questao.historico_data.append(datetime.datetime.now())
                    pedido_em_questao.historico_user.append(users.get_current_user().email())
                    notifica(pedido_em_questao, "A data de corretude do seu pedido foi alterado")
                pedido_em_questao.put()
            else:
                self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
                self.response.out.write(json.dumps(dict({'erro':'numero de pedido inexistente'}.items()) ))
     
            self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
            self.response.out.write(json.dumps(dict({'status':'Connected'}.items()), indent=2))
     
#Minuta
class MinutaHandler(webapp2.RequestHandler):
        def post(self):
     
            import json
     
            pedido = self.request.get("pedido")
            parecer = self.request.get("parecer")
            data_inicio = self.request.get("data_inicio")
            data_envio = self.request.get("data_envio")
            data_retorno = self.request.get("data_retorno")
     
            if pedido:
                pedido_em_questao = searchkey(pedido) 
                if parecer:
                    if len(pedido_em_questao.minuta_parecer) == 0:
                        parecer_minuta_anterior = "nada"
                    elif pedido_em_questao.minuta_parecer[-1] == True:
                        parecer_minuta_anterior = "legal"
                    elif pedido_em_questao.minuta_parecer[-1] == False:
                        parecer_minuta_anterior = "ilegal"
                    pedido_em_questao.minuta_parecer.append(True if parecer == "True" else False)
                    if pedido_em_questao.minuta_parecer[-1] == True:
                        parecer_minuta_atual = "legal"
                    else:
                        parecer_minuta_atual = "ilegal"
                    pedido_em_questao.historico_info.append("O parecer de elaboracao da minuta foi definido de "+parecer_minuta_anterior+" para "+parecer_minuta_atual)
                    pedido_em_questao.historico_data.append(datetime.datetime.now())
                    pedido_em_questao.historico_user.append(users.get_current_user().email())
                    notifica(pedido_em_questao, "O status da elaboracao da minuta do seu pedido foi alterado")
                if data_inicio:
                    if len(pedido_em_questao.minuta_data_inicio) == 0:
                        data_inicio_minuta_anterior = "nenhuma"
                    else:
                        data_inicio_minuta_anterior = pedido_em_questao.minuta_data_inicio[-1].strftime("%d/%m/%Y")
                    pedido_em_questao.minuta_data_inicio.append( datetime.datetime.strptime(data_inicio, "%Y-%m-%dT%H:%M:%S"))
                    data_inicio_minuta_atual = pedido_em_questao.minuta_data_inicio[-1].strftime("%d/%m/%Y")
                    pedido_em_questao.historico_info.append("A data de inicio de elaboracao da minuta foi definida de "+data_inicio_minuta_anterior+" para "+data_inicio_minuta_atual)
                    pedido_em_questao.historico_data.append(datetime.datetime.now())
                    pedido_em_questao.historico_user.append(users.get_current_user().email())
                if data_envio:
                    if len(pedido_em_questao.minuta_data_envio) == 0:
                        data_envio_minuta_anterior = "nenhuma"
                    else:
                        data_envio_minuta_anterior = pedido_em_questao.minuta_data_envio[-1].strftime("%d/%m/%Y")
                    pedido_em_questao.minuta_data_envio.append( datetime.datetime.strptime(data_envio, "%Y-%m-%dT%H:%M:%S"))
                    data_envio_minuta_atual = pedido_em_questao.minuta_data_envio[-1].strftime("%d/%m/%Y")
                    pedido_em_questao.historico_info.append("A data de envio de elaboracao da minuta foi definida de "+data_envio_minuta_anterior+" para "+data_envio_minuta_atual)
                    pedido_em_questao.historico_data.append(datetime.datetime.now())
                    pedido_em_questao.historico_user.append(users.get_current_user().email())
                if data_retorno:
                    if len(pedido_em_questao.minuta_data_retorno) == 0:
                        data_retorno_minuta_anterior = "nenhuma"
                    else:
                        data_retorno_minuta_anterior = pedido_em_questao.minuta_data_retorno[-1].strftime("%d/%m/%Y")
                    pedido_em_questao.minuta_data_retorno.append( datetime.datetime.strptime(data_retorno, "%Y-%m-%dT%H:%M:%S"))
                    data_retorno_minuta_atual = pedido_em_questao.minuta_data_retorno[-1].strftime("%d/%m/%Y")
                    pedido_em_questao.historico_info.append("A data de retorno de elaboracao da minuta foi definida de "+data_retorno_minuta_anterior+" para "+data_retorno_minuta_atual)
                    pedido_em_questao.historico_data.append(datetime.datetime.now())
                    pedido_em_questao.historico_user.append(users.get_current_user().email())
                pedido_em_questao.put()
            else:
                self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
                self.response.out.write(json.dumps(dict({'erro':'numero de pedido inexistente'}.items()) ))
     
            self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
            self.response.out.write(json.dumps(dict({'status':'Connected'}.items()), indent=2))
     
#Pregao
class PregaoHandler(webapp2.RequestHandler):
        def post(self):
     
            import json
     
            pedido = self.request.get("pedido")
            parecer = self.request.get("parecer")
            data = self.request.get("data")
            numero = self.request.get("numero")
            licitacao_data = self.request.get("licitacao_data")
           
            if pedido:
                pedido_em_questao = searchkey(pedido) 
                i = pedido_em_questao.pregao_indice 
                if parecer: 
                    if len(pedido_em_questao.pregao_parecer) == i+1:
                        parecer_pregao_anterior = "nada"
                        pedido_em_questao.pregao_parecer.append(True if parecer == "True" else False)
                        if pedido_em_questao.pregao_parecer[-1] == True:
                            parecer_pregao_atual = "comprado"
                        else:
                            parecer_pregao_atual = "nao comprado"
                        pedido_em_questao.historico_info.append("O parecer do pregao foi definido de "+parecer_pregao_anterior+" para "+parecer_pregao_atual)
                        notifica(pedido_em_questao, "O status do pregao do seu pedido foi alterado")
                        pedido_em_questao.historico_data.append(datetime.datetime.now())
                        pedido_em_questao.historico_user.append(users.get_current_user().email())
                    elif len(pedido_em_questao.pregao_parecer) == i+2:
                        if pedido_em_questao.pregao_parecer[-1] == True:
                            parecer_pregao_anterior = "comprado"
                        else:
                            parecer_pregao_anterior = "nao comprado"
                        pedido_em_questao.pregao_parecer = pedido_em_questao.pregao_parecer[:-1] + [True if parecer == "True" else False]
                        if pedido_em_questao.pregao_parecer:
                            parecer_pregao_atual= "comprado"
                        else:
                            parecer_pregao_atual = "nao comprado"
                        pedido_em_questao.pregao_parecer[i+1] = True if parecer == "True" else False
                        if pedido_em_questao.pregao_parecer[-1] == True:
                             parecer_pregao_atual = "comprado"
                        else:
                             parecer_pregao_atual = "nao comprado"
                        if pedido_em_questao.pregao_parecer[-1] == True:
                            parecer_pregao_atual = "comprado"
                        else:
                            parecer_pregao_atual = "nao comprado"
                        pedido_em_questao.historico_info.append("O parecer do pregao foi definido de "+parecer_pregao_anterior+" para "+parecer_pregao_atual)
                        notifica(pedido_em_questao, "O status do pregao do seu pedido foi alterado")
                        pedido_em_questao.historico_data.append(datetime.datetime.now())
                        pedido_em_questao.historico_user.append(users.get_current_user().email())   
                if data: 
                    if len(pedido_em_questao.pregao_data) == i+1:
                        data_pregao_anterior = "nenhuma"
                        pedido_em_questao.pregao_data.append(datetime.datetime.strptime(data, "%Y-%m-%dT%H:%M:%S"))
                        data_pregao_atual = pedido_em_questao.pregao_data[-1].strftime("%d/%m/%Y")
                        pedido_em_questao.historico_info.append("A data do pregao foi definida de "+data_pregao_anterior+" para "+data_pregao_atual)
                        pedido_em_questao.historico_data.append(datetime.datetime.now())
                        pedido_em_questao.historico_user.append(users.get_current_user().email())
                    elif len(pedido_em_questao.pregao_data) == i+2:
                        data_pregao_anterior = pedido_em_questao.pregao_data[-1].strftime("%d/%m/%Y")
                        pedido_em_questao.pregao_data[i+1] = datetime.datetime.strptime(data, "%Y-%m-%dT%H:%M:%S")
                        data_pregao_atual = pedido_em_questao.pregao_data[-1].strftime("%d/%m/%Y")
                        pedido_em_questao.historico_info.append("A data do pregao foi definida de "+data_pregao_anterior+" para "+data_pregao_atual)
                        pedido_em_questao.historico_data.append(datetime.datetime.now())
                        pedido_em_questao.historico_user.append(users.get_current_user().email())
                if numero: 
                    if len(pedido_em_questao.pregao_numero) == i+1:
                        numero_pregao_anterior = "nenhum"
                        pedido_em_questao.pregao_numero.append(numero)
                        numero_pregao_atual = str(pedido_em_questao.pregao_numero[-1])
                        pedido_em_questao.historico_info.append("O numero do pregao foi definido de "+numero_pregao_anterior+" para "+numero_pregao_atual)
                        pedido_em_questao.historico_data.append(datetime.datetime.now())
                        pedido_em_questao.historico_user.append(users.get_current_user().email())
                    elif len(pedido_em_questao.pregao_numero) == i+2:
                        numero_pregao_anterior = str(pedido_em_questao.pregao_numero[-1])
                        pedido_em_questao.pregao_numero[i+1] = numero
                        numero_pregao_atual = str(pedido_em_questao.pregao_numero[-1])
                        pedido_em_questao.historico_info.append("O numero do pregao foi definido de "+numero_pregao_anterior+" para "+numero_pregao_atual)
                        pedido_em_questao.historico_data.append(datetime.datetime.now())
                        pedido_em_questao.historico_user.append(users.get_current_user().email())
                if licitacao_data: 
                    if len(pedido_em_questao.pregao_licitacao_data) == i+1:
                        licitacao_data_anterior = "nenhuma"
                        pedido_em_questao.pregao_licitacao_data.append(datetime.datetime.strptime(licitacao_data, "%Y-%m-%dT%H:%M:%S"))
                        licitacao_data_atual = pedido_em_questao.pregao_licitacao_data[-1].strftime("%d/%m/%Y")
                        pedido_em_questao.historico_info.append("A data de licitacao do pregao foi definida de "+licitacao_data_anterior+" para "+licitacao_data_atual)
                        pedido_em_questao.historico_data.append(datetime.datetime.now())
                        pedido_em_questao.historico_user.append(users.get_current_user().email())
                    elif len(pedido_em_questao.pregao_licitacao_data) == i+2:
                        licitacao_data_anterior = pedido_em_questao.pregao_licitacao_data[-1].strftime("%d/%m/%Y")
                        pedido_em_questao.pregao_licitacao_data[i+1] = datetime.datetime.strptime(licitacao_data, "%Y-%m-%dT%H:%M:%S")
                        licitacao_data_atual = pedido_em_questao.pregao_licitacao_data[-1].strftime("%d/%m/%Y")
                        pedido_em_questao.historico_info.append("A data de licitacao do pregao foi definida de "+licitacao_data_anterior+" para "+licitacao_data_atual)
                        pedido_em_questao.historico_data.append(datetime.datetime.now())
                        pedido_em_questao.historico_user.append(users.get_current_user().email())
                pedido_em_questao.put()
            else:
                self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
                self.response.out.write(json.dumps(dict({'erro':'numero de pedido inexistente'}.items()) ))
     
            self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
            self.response.out.write(json.dumps(dict({'status':'Connected'}.items()), indent=2))
     
#Adjudicacao
class AdjudicacaoHandler(webapp2.RequestHandler):
        def post(self):
     
            import json
     
            pedido = self.request.get("pedido")
            data = self.request.get("data")
     
            if pedido:
                pedido_em_questao = searchkey(pedido) 
                if data:
                    if pedido_em_questao.adjudicacao_data == None:
                        data_adjudicacao_anterior = "nenhuma"
                    else:
                        data_adjudicacao_anterior = pedido_em_questao.adjudicacao_data.strftime("%d/%m/%Y")
                    pedido_em_questao.adjudicacao_data = datetime.datetime.strptime(data, "%Y-%m-%dT%H:%M:%S")
                    data_adjudicacao_atual = pedido_em_questao.adjudicacao_data.strftime("%d/%m/%Y")
                    pedido_em_questao.historico_info.append("A data de adjudicacao foi definida de "+data_adjudicacao_anterior+" para "+data_adjudicacao_atual)
                    pedido_em_questao.historico_data.append(datetime.datetime.now())
                    pedido_em_questao.historico_user.append(users.get_current_user().email())
                    notifica(pedido_em_questao, "O status de adjudicacao do seu pedido foi alterado")
                pedido_em_questao.put()
            else:
                self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
                self.response.out.write(json.dumps(dict({'erro':'numero de pedido inexistente'}.items()) ))
     
            self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
            self.response.out.write(json.dumps(dict({'status':'Connected'}.items()), indent=2))
     
#Homologacao
class HomologacaoHandler(webapp2.RequestHandler):
        def post(self):
     
            import json
     
            pedido = self.request.get("pedido")
            data = self.request.get("data")
     
            if pedido:
                pedido_em_questao = searchkey(pedido) 
                if data:
                    if pedido_em_questao.homologacao_data == None:
                        data_homologacao_anterior = "nenhuma"
                    else:
                        data_homologacao_anterior = pedido_em_questao.homologacao_data.strftime("%d/%m/%Y")
                    pedido_em_questao.homologacao_data = datetime.datetime.strptime(data, "%Y-%m-%dT%H:%M:%S")
                    data_homologacao_atual = pedido_em_questao.homologacao_data.strftime("%d/%m/%Y")
                    pedido_em_questao.historico_info.append("A data de homologacao foi definida de "+data_homologacao_anterior+" para "+data_homologacao_atual)
                    pedido_em_questao.historico_data.append(datetime.datetime.now())
                    pedido_em_questao.historico_user.append(users.get_current_user().email())
                    notifica(pedido_em_questao, "O status de homologacao do seu pedido foi alterado")
                pedido_em_questao.put()
            else:
                self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
                self.response.out.write(json.dumps(dict({'erro':'numero de pedido inexistente'}.items()) ))
     
            self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
            self.response.out.write(json.dumps(dict({'status':'Connected'}.items()), indent=2))
     
#Publicacao
class PublicacaoHandler(webapp2.RequestHandler):
        def post(self):
     
            import json
     
            pedido = self.request.get("pedido")
            data = self.request.get("data")
     
            if pedido:
                pedido_em_questao = searchkey(pedido) 
                if data:
                    if pedido_em_questao.publicacao_data == None:
                        data_publicacao_anterior = "nenhuma"
                    else:
                        data_publicacao_anterior = pedido_em_questao.publicacao_data.strftime("%d/%m/%Y")
                    pedido_em_questao.publicacao_data = datetime.datetime.strptime(data, "%Y-%m-%dT%H:%M:%S")
                    data_publicacao_atual = pedido_em_questao.publicacao_data.strftime("%d/%m/%Y")
                    pedido_em_questao.historico_info.append("A data de publicacao foi definida de "+data_publicacao_anterior+" para "+data_publicacao_atual)
                    pedido_em_questao.historico_data.append(datetime.datetime.now())
                    pedido_em_questao.historico_user.append(users.get_current_user().email())
                    notifica(pedido_em_questao, "O status de publicacao do seu pedido foi alterado")
                pedido_em_questao.put()
            else:
                self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
                self.response.out.write(json.dumps(dict({'erro':'numero de pedido inexistente'}.items()) ))
     
            self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
            self.response.out.write(json.dumps(dict({'status':'Connected'}.items()), indent=2))
     
#Detalhamento
class DetalhamentoHandler(webapp2.RequestHandler):
        def post(self):
     
            import json
     
            pedido = self.request.get("pedido")
            parecer = self.request.get("parecer")
            data = self.request.get("data")
     
            if pedido:
                pedido_em_questao = searchkey(pedido) 
                if parecer:
                    if pedido_em_questao.detalhamento_parecer == True:
                        parecer_detalhamento_anterior = "autorizado"
                    elif pedido_em_questao.detalhamento_parecer == False:
                        parecer_detalhamento_anterior = "nao autorizado"
                    elif pedido_em_questao.detalhamento_parecer == None:
                        parecer_detalhamento_anterior = "nada"
                    pedido_em_questao.detalhamento_parecer = True if parecer == "True" else False
                    if pedido_em_questao.detalhamento_parecer:
                        parecer_detalhamento_atual = "autorizado"
                    else:
                        parecer_detalhamento_atual = "nao autorizado"
                    pedido_em_questao.historico_info.append("O parecer do detalhamento de credito foi definido de "+parecer_detalhamento_anterior+" para "+parecer_detalhamento_atual)
                    pedido_em_questao.historico_data.append(datetime.datetime.now())
                    pedido_em_questao.historico_user.append(users.get_current_user().email())
                  
                    notifica(pedido_em_questao, "O status de detalhamento de credito do seu pedido foi alterado")
                if data:
                    if pedido_em_questao.detalhamento_data == None:
                        data_detalhamento_anterior = "nenhuma"
                    else:
                        data_detalhamento_anterior = pedido_em_questao.detalhamento_data.strftime("%d/%m/%Y")
                    pedido_em_questao.detalhamento_data = datetime.datetime.strptime(data, "%Y-%m-%dT%H:%M:%S")
                    data_detalhamento_atual = pedido_em_questao.detalhamento_data.strftime("%d/%m/%Y")
                    pedido_em_questao.historico_info.append("A data do detalhamento de credito foi definida de "+data_detalhamento_anterior+" para "+data_detalhamento_atual)
                    pedido_em_questao.historico_data.append(datetime.datetime.now())
                    pedido_em_questao.historico_user.append(users.get_current_user().email())
                pedido_em_questao.put()
            else:
                self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
                self.response.out.write(json.dumps(dict({'erro':'numero de pedido inexistente'}.items()) ))
     
            self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
            self.response.out.write(json.dumps(dict({'status':'Connected'}.items()), indent=2))
     
#Empenho
class EmpenhoHandler(webapp2.RequestHandler):
        def post(self):
     
            import json
     
            pedido = self.request.get("pedido")
            data = self.request.get("data")
     
            if pedido:
                pedido_em_questao = searchkey(pedido) 
                if data:
                    if pedido_em_questao.empenho_data == None:
                        data_empenho_anterior = "nenhuma"
                    else:
                        data_empenho_anterior = pedido_em_questao.empenho_data.strftime("%d/%m/%Y")
                    pedido_em_questao.empenho_data = datetime.datetime.strptime(data, "%Y-%m-%dT%H:%M:%S")
                    data_empenho_atual = pedido_em_questao.empenho_data.strftime("%d/%m/%Y")
                    pedido_em_questao.historico_info.append("A data de empenho foi definida de "+data_empenho_anterior+" para "+data_empenho_atual)
                    pedido_em_questao.historico_data.append(datetime.datetime.now())
                    pedido_em_questao.historico_user.append(users.get_current_user().email())
                    notifica(pedido_em_questao, "O status de empenho do seu pedido foi alterado")
                pedido_em_questao.put()
            else:
                self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
                self.response.out.write(json.dumps(dict({'erro':'numero de pedido inexistente'}.items()) ))
     
            self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
            self.response.out.write(json.dumps(dict({'status':'Connected'}.items()), indent=2))
     
#Nota do almoxarifado
class NotaAlmoxarifadoHandler(webapp2.RequestHandler):
        def post(self):
     
            import json
     
            pedido = self.request.get("pedido")
            data = self.request.get("data")
     
            if pedido:
                pedido_em_questao = searchkey(pedido) 
                if data:
                    if pedido_em_questao.nota_almoxarifado_data == None:
                        data_almoxarifado_anterior = "nenhuma"
                    else:
                        data_almoxarifado_anterior = pedido_em_questao.nota_almoxarifado_data.strftime("%d/%m/%Y")
                    pedido_em_questao.nota_almoxarifado_data = datetime.datetime.strptime(data, "%Y-%m-%dT%H:%M:%S")
                    data_almoxarifado_atual = pedido_em_questao.nota_almoxarifado_data.strftime("%d/%m/%Y")
                    pedido_em_questao.historico_info.append("A data de envio da nota ao almoxarifado foi definida de "+data_almoxarifado_anterior+" para "+data_almoxarifado_atual)
                    pedido_em_questao.historico_data.append(datetime.datetime.now())
                    pedido_em_questao.historico_user.append(users.get_current_user().email())
                pedido_em_questao.put()
            else:
                self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
                self.response.out.write(json.dumps(dict({'erro':'numero de pedido inexistente'}.items()) ))
     
            self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
            self.response.out.write(json.dumps(dict({'status':'Connected'}.items()), indent=2))
     
#Patrimonio
class PatrimonioHandler(webapp2.RequestHandler):
        def post(self):
     
            import json
     
            pedido = self.request.get("pedido")
            data = self.request.get("data")
     
            if pedido:
                pedido_em_questao = searchkey(pedido) 
                if data:
                    if pedido_em_questao.patrimonio_data == None:
                        data_patrimonio_anterior = "nenhuma"
                    else:
                        data_patrimonio_anterior = pedido_em_questao.patrimonio_data.strftime("%d/%m/%Y")
                    pedido_em_questao.patrimonio_data = datetime.datetime.strptime(data, "%Y-%m-%dT%H:%M:%S")
                    data_patrimonio_atual = pedido_em_questao.patrimonio_data.strftime("%d/%m/%Y")
                    pedido_em_questao.historico_info.append("A data de envio ao patrimonio foi definida de "+data_patrimonio_anterior+" para "+data_patrimonio_atual)
                    pedido_em_questao.historico_data.append(datetime.datetime.now())
                    pedido_em_questao.historico_user.append(users.get_current_user().email())
                pedido_em_questao.put()
            else:
                self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
                self.response.out.write(json.dumps(dict({'erro':'numero de pedido inexistente'}.items()) ))
     
            self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
            self.response.out.write(json.dumps(dict({'status':'Connected'}.items()), indent=2))
     
#Nota da contabilidade
class NotaContabilidadeHandler(webapp2.RequestHandler):
        def post(self):
     
            import json
     
            pedido = self.request.get("pedido")
            data = self.request.get("data")
     
            if pedido:
                pedido_em_questao = searchkey(pedido) 
                if data:
                    if pedido_em_questao.nota_contabilidade_data == None:
                        data_nota_anterior = "nenhuma"
                    else:
                        data_nota_anterior = pedido_em_questao.nota_contabilidade_data.strftime("%d/%m/%Y")
                    pedido_em_questao.nota_contabilidade_data = datetime.datetime.strptime(data, "%Y-%m-%dT%H:%M:%S")
                    data_nota_atual = pedido_em_questao.nota_contabilidade_data.strftime("%d/%m/%Y")
                    pedido_em_questao.historico_info.append("A data de envio da nota a contabilidade foi definida de "+data_nota_anterior+" para "+data_nota_atual)
                    pedido_em_questao.historico_data.append(datetime.datetime.now())
                    pedido_em_questao.historico_user.append(users.get_current_user().email())
                    
                pedido_em_questao.put()
            else:
                self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
                self.response.out.write(json.dumps(dict({'erro':'numero de pedido inexistente'}.items()) ))
     
            self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
            self.response.out.write(json.dumps(dict({'status':'Connected'}.items()), indent=2))
     
#Liquidacao
class LiquidacaoHandler(webapp2.RequestHandler):
        def post(self):
     
            import json
     
            pedido = self.request.get("pedido")
            data = self.request.get("data")
     
            if pedido:
                pedido_em_questao = searchkey(pedido) 
                if data:
                    if pedido_em_questao.liquidacao_data == None:
                        data_liquidacao_anterior = "nenhuma"
                    else:
                        data_liquidacao_anterior = pedido_em_questao.liquidacao_data.strftime("%d/%m/%Y")
                    pedido_em_questao.liquidacao_data = datetime.datetime.strptime(data, "%Y-%m-%dT%H:%M:%S")
                    data_liquidacao_atual = pedido_em_questao.liquidacao_data.strftime("%d/%m/%Y")
                    pedido_em_questao.historico_info.append("A data de liquidacao foi definida de "+data_liquidacao_anterior+" para "+data_liquidacao_atual)
                    pedido_em_questao.historico_data.append(datetime.datetime.now())
                    pedido_em_questao.historico_user.append(users.get_current_user().email())
                    notifica(pedido_em_questao, "O status de liquidacao do seu pedido foi alterado")
                pedido_em_questao.put()
            else:
                self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
                self.response.out.write(json.dumps(dict({'erro':'numero de pedido inexistente'}.items()) ))
     
            self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
            self.response.out.write(json.dumps(dict({'status':'Connected'}.items()), indent=2))
     
#Pagamento
class PagamentoHandler(webapp2.RequestHandler):
        def post(self):
     
            import json
     
            pedido = self.request.get("pedido")
            data = self.request.get("data")
     
            if pedido:
                pedido_em_questao = searchkey(pedido) 
                if data:
                    if pedido_em_questao.pagamento_data == None:
                        data_pagamento_anterior = "nenhuma"
                    else:
                        data_pagamento_anterior = pedido_em_questao.pagamento_data.strftime("%d/%m/%Y")
                    pedido_em_questao.pagamento_data = datetime.datetime.strptime(data, "%Y-%m-%dT%H:%M:%S")
                    data_pagamento_atual = pedido_em_questao.pagamento_data.strftime("%d/%m/%Y")
                    pedido_em_questao.historico_info.append("A data de pagamento foi definida de "+data_pagamento_anterior+" para "+data_pagamento_atual)
                    pedido_em_questao.historico_data.append(datetime.datetime.now())
                    pedido_em_questao.historico_user.append(users.get_current_user().email())
                    notifica(pedido_em_questao, "O status de pagamento do seu pedido foi alterado")
                pedido_em_questao.put()
            else:
                self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
                self.response.out.write(json.dumps(dict({'erro':'numero de pedido inexistente'}.items()) ))
     
            self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
            self.response.out.write(json.dumps(dict({'status':'Connected'}.items()), indent=2))


app = webapp2.WSGIApplication([('/', MainHandler), ('/LoginHandler', LoginHandler), ('/AdicionaPregao', AdicionaPregao), ('/inicializar', InitSys), ('/Pedido', ListaPedido), ('/setpedido', SetPedido), ('/getpedido', GetPedido),('/gethistorico', GetHistorico),('/searchpedido', SearchPedido),('/PedidosForTable', SearchPedido), ('/Permissoes', PermissoesHandler), ('/CadastraPedido', CadastraPedido), ('/LegalidadeHandler', LegalidadeHandler), ('/AutorizacaoHandler', AutorizacaoHandler), ('/CorretudeHandler', CorretudeHandler), ('/MinutaHandler', MinutaHandler), ('/PregaoHandler', PregaoHandler), ('/AdjudicacaoHandler', AdjudicacaoHandler), ('/HomologacaoHandler', HomologacaoHandler), ('/PublicacaoHandler', PublicacaoHandler), ('/DetalhamentoHandler', DetalhamentoHandler), ('/EmpenhoHandler', EmpenhoHandler), ('/NotaAlmoxarifadoHandler', NotaAlmoxarifadoHandler), ('/PatrimonioHandler', PatrimonioHandler), ("/NotaContabilidadeHandler", NotaContabilidadeHandler), ('/LiquidacaoHandler', LiquidacaoHandler), ('/PagamentoHandler', PagamentoHandler)],debug=True)

#app = webapp2.WSGIApplication([('/', MainHandler), ('/LoginHandler', LoginHandler), ('/inicializar', InitSys), ('/Pedido', ListaPedido), ('/setpedido', SetPedido), ('/getpedido', GetPedido),('/searchpedido', SearchPedido),('/PedidosForTable', SearchPedido), ('/Permissoes', PermissoesHandler), ('/CadastraPedido', CadastraPedido)],debug=True)
