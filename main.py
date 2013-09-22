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


class Pregao(db.Model):
    detalhes = db.StringProperty(required=True)
    data = db.DateTimeProperty(required=True)



class Pedido(db.Model):
    demandante = db.StringProperty(required=True)
    data_entrada = db.StringProperty(required=True) #db.DateTimeProperty(auto_now_add=True)
    descricao = db.StringProperty(required=True)
    numero = db.StringProperty(required=True)
    email_demandante = db.StringProperty(required=True)
    local = db.StringProperty(default = "PRA/Gabinete")

    legalidade_choices = (
            "indefinido",
            "legal",
            "ilegal"
        )

    legalidade = db.StringProperty(
        default = "indefinido",
        choices = set(legalidade_choices)
    )
    

    autorizacao_choices = (
            "indefinido",
            "autorizado",
            "nao autorizado"
        )

    autorizacao = db.StringProperty(
        default = "indefinido",
        choices = set(autorizacao_choices)
    )

    conferencia_choices = (
            "indefinido",
            "incompleto",
            "completo"
    )

    conferencia = db.StringProperty(
        default = "indefinido",
        choices = set(conferencia_choices)
    )

    minuta_choices = (
            "indefinido",
            "elaborado"
        )

    minuta = db.StringProperty(
        default = "indefinido",
        choices = set(minuta_choices)
    )


    legalidade_materiais_choices = (
            "indefinido",
            "legal",
            "ilegal"
        )

    legalidade_materiais = db.StringProperty(
        default = "indefinido",
        choices = set(legalidade_materiais_choices)
    )
    
    pregao_choices = (
            "indefinido",
            "informado no SIASE e Comprasnet",
            "Edital publicado",
            "Data marcada",
            "Realizado"
        )

    pregao = db.StringProperty(
        default = "indefinido",
        choices = set(pregao_choices)
    )

    licitacao_choices = (
            "indefinido",
            "concluida",
            "em avaliacao"
        )

    licitacao = db.StringProperty(
        default = "indefinido",
        choices = set(licitacao_choices)
    )

    adjudicacao_choices = (
            "indefinido",
            "termo elaborado"
        )

    adjudicacao = db.StringProperty(
        default = "indefinido",
        choices = set(adjudicacao_choices)
    )

    homologacao_choices = (
        "indefinido", 
            "homologado",
            "nao homologado"
        )

    homologacao = db.StringProperty(
        default = "indefinido",
        choices = set(homologacao_choices)
    )    

    publicacao_choices = (
            "indefinido",
            "publicado"
        )

    publicacao = db.StringProperty(
        default = "indefinido",
        choices = set(publicacao_choices)
    )

    minuta_empenho_choices = (
            "indefinido",
            "elaborada"
        )

    minuta_empenho = db.StringProperty(
        default = "indefinido",
        choices = set(minuta_empenho_choices)
    )

    detalhamento_choices = (
            "indefinido",
            "detalhado",
            "no SEPLAN"
        )

    detalhamento = db.StringProperty(
        default = "indefinido",
        choices = set(detalhamento_choices)
    )

    empenho_choices = (
            "indefinido",
            "empenhado",
            "recebido"
        )
    
    empenho = db.StringProperty(
        default = "indefinido",
        choices = set(empenho_choices)
    )

    nota_almoxarifado_choices = (
            "indefinido",
            "enviado"
        )

    nota_almoxarifado = db.StringProperty(
        default = "indefinido",
        choices = set( nota_almoxarifado_choices)
    )

    tombamento_choices = (
            "indefinido",
        "tombado"
        )

    tombamento = db.StringProperty(
        default = "indefinido",
        choices = set( tombamento_choices)
    )

    nota_contabilidade_choices = (
            "indefinido",
            "enviado"
        )

    nota_contabilidade = db.StringProperty(
        default = "indefinido",
        choices = set(nota_contabilidade_choices )
    )

    liquidacao_choices = (
            "indefinido",
        "servico atestado", 
            "liquidado"
        )
     
    liquidacao = db.StringProperty(
        default = "indefinido",
        choices = set(liquidacao_choices )
    )

    pagamento_choices = (
            "indefinido",
            "pago"
        )

    pagamento = db.StringProperty(
        default = "indefinido",
        choices = set(pagamento_choices)
    )







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
10:'pagamento',
21:'criacao'
}

users_permission = { 
users.User("usuario_qualquer@example.com"): [2],
users.User("joopeeds@gmail.com"): [1, 21]
}
    
class MainHandler(webapp2.RequestHandler):
    def get(self):
    
        """user = users.get_current_user()

        if user:
            greeting = ('Seja bem vindo ao sistema PRA, %s! (<a href="%s">sign out</a>)' %
                        (user.nickname(), users.create_logout_url('/')))
        if user in users_permission:
         for perm in users_permission[user]:
         greeting += ('<br><a href="%s">%s</a>' % (perm, perm))
        else:
        greeting += "<br>Você não tem nenhuma permissão<br>"
        else:
            greeting = ('<a href="%s">Sign in or register</a>.' %
                        users.create_login_url('/'))
"""
        greeting = """
<!doctype html>
<!-- The DOCTYPE declaration above will set the     -->
<!-- browser's rendering engine into                -->
<!-- "Standards Mode". Replacing this declaration   -->
<!-- with a "Quirks Mode" doctype is not supported. -->

<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">

    <!--                                                               -->
    <!-- Consider inlining CSS to reduce the number of requested files -->
    <!--                                                               -->
    <link type="text/css" rel="stylesheet" href="stylesheets/StockWatcher.css">

    <!--                                           -->
    <!-- Any title is fine                         -->
    <!--                                           -->
     <title>SISTEMA PRA@UFCG</title>
    
    <!--                                           -->
    <!-- This script loads your compiled module.   -->
    <!-- If you add any GWT meta tags, they must   -->
    <!-- be added before this line.                -->
    <!--                                           -->
    <script type="text/javascript" language="javascript" src="js/stockwatcher.nocache.js"></script>
  </head>

  <!--                                           -->
  <!-- The body can have arbitrary html, or      -->
  <!-- you can leave the body empty if you want  -->
  <!-- to create a completely dynamic UI.        -->
  <!--                                           -->
  <body>

    <!-- OPTIONAL: include this if you want history support -->
    <iframe src="javascript:''" id="__gwt_historyFrame" tabIndex='-1' style="position:absolute;width:0;height:0;border:0"></iframe>
    
    <!-- RECOMMENDED if your web app will not function without JavaScript enabled -->
    <noscript>
      <div style="width: 22em; position: absolute; left: 50%; margin-left: -11em; color: red; background-color: white; border: 1px solid red; padding: 4px; font-family: sans-serif">
        Your web browser must have JavaScript enabled
        in order for this application to display correctly.
      </div>
    </noscript>

    
<div id="top_bar">
  <h2 style="padding-left: 1%"><img src="images/spra-ufcg-branco.svg" width="10%"></h2>
    <div id="user_on_top_bar">  
    </div>    
</div>
<div id="main"><div id="main_top"></div><div id="main_left"><br><br></div><div id="main_right"><br><br></div></div>
<div id="bottom_bar"><div id="bottom_bar_in">Pr&oacute;-Reitoria de Gest&atilde;o Administrativo-Financeira @ Universidade Federal de Campina Grande - 2013</div></div>
    
  </body>
</html>



















"""
    
        self.response.out.write('<html><body>%s</body></html>' % greeting)





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

class CadastraPedido(webapp2.RequestHandler):
    def post(self):
        if users.get_current_user() and 21 in users_permission[users.get_current_user()]:
            novo = Pedido(demandante=self.request.get("demandante"), 
                          data_entrada=self.request.get("data_entrada"), 
                          descricao=self.request.get("descricao"), 
                          numero=self.request.get("numero"), 
                          email_demandante=self.request.get("email_demandante"))
            novo.put()
            






app = webapp2.WSGIApplication([('/', MainHandler), ('/LoginHandler', LoginHandler), ('/Pedido', ListaPedido), ('/Permissoes', PermissoesHandler), ('/CadastraPedido', CadastraPedido)],debug=True)
