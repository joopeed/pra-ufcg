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
    id = db.IntegerProperty(required=True)
    demandante = db.StringProperty(required=True)
    data_entrada = db.StringProperty(required=True)
    descricao = db.StringProperty(required=True)
    numero = db.StringProperty(required=True)
    email_demandante = db.EmailProperty(required=True)
    local = db.StringProperty(default = "PRA")
    data_pregao = db.DateTimeProperty()
    data_resultado = db.DateTimeProperty()

    legalidade = db.StringProperty(
        default = "indefinido",
        choices = set([
            "indefinido",
            "legal",
            "ilegal"
        ])
    )

    autorizacao = db.StringProperty(
        default = "indefinido",
        choices = set([
            "indefinido",
            "autorizado",
            "nao autorizado"
        ])
    )

    conferencia = db.StringProperty(
        default = "indefinido",
        choices = set([
            "indefinido",
            "incompleto",
            "completo"
        ])
    )

    minuta = db.StringProperty(
        default = "indefinido",
        choices = set([
            "indefinido",
            "de acordo",
            "nao de acordo"
        ])
    )


    legalidade_materiais = db.StringProperty(
        default = "indefinido",
        choices = set([
            "indefinido",
            "legal",
            "ilegal"
        ])
    )
	
    pregao = db.StringProperty(
        default = "indefinido",
        choices = set([
            "indefinido",
            "iniciado"
        ])
    )


    adjudicacao = db.StringProperty(
        default = "indefinido",
        choices = set([
            "indefinido",
            "sim",
            "nao"
        ])
    )

    licitacao = db.StringProperty(
        default = "indefinido",
        choices = set([
            "indefinido",
            "realizado",
            "homologado"
        ])
    )

    publicacao = db.StringProperty(
        default = "indefinido",
        choices = set([
            "indefinido",
            "publicado"
        ])
    )

    minuta_empenho = db.StringProperty(
        default = "indefinido",
        choices = set([
            "indefinido",
            "elaborada"
        ])
    )

    detalhamento = db.StringProperty(
        default = "indefinido",
        choices = set([
            "indefinido",
            "de acordo",
            "nao de acordo"
        ])
    )
	
    empenho = db.StringProperty(
        default = "indefinido",
        choices = set([
            "indefinido",
            "empenhado",
            "no empenho"
        ])
    )

    nota_almoxarifado = db.StringProperty(
        default = "indefinido",
        choices = set([
            "indefinido",
            "enviado"
        ])
    )

    patrimonio = db.StringProperty(
        default = "indefinido",
        choices = set([
            "indefinido",
            "no patrimonio", 
	    "tombado"
        ])
    )

    nota_contabilidade = db.StringProperty(
        default = "indefinido",
        choices = set([
            "indefinido",
            "enviado"
        ])
    )
     
    liquidacao = db.StringProperty(
        default = "indefinido",
        choices = set([
            "indefinido",
            "liquidado"
        ])
    )

    pagamento = db.StringProperty(
        default = "indefinido",
        choices = set([
            "indefinido",
            "pago"
        ])
    )



class CriarHandler(webapp2.RequestHandler):
    def get(self):
	user = users.get_current_user() 
	if user and user in users_permission and "criar" in users_permission[user]:
		self.response.out.write("""
					<html>
					<head>
					<link rel="stylesheet" type="text/css" href="/stylesheets/styles.css">
					<link rel="stylesheet" type="text/css" href="/stylesheets/div_styles.css">
					<title>Processos - PRA - UFCG</title>
					<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.js"></script>
					<script type="text/javascript" src="js/progressbar.js"></script>
					<script type="text/javascript" src="js/div_hide.js"></script>
					<link rel="stylesheet" type="text/css" href="/stylesheets/progressbar.css">
					</head>
					""")
		self.response.write("""
			<div style="background-color:#660000;position:absolute;left:0px;right:0px;top:0px;height:100px;padding:10px;font-size:30px; color: white; ">PRA - UFCG</div><div style="background-color:white;position:relative;left:90%;top:0px;padding:10px;color:white;">"""+('<a href="%s">Sair</a>' %
		                (users.create_logout_url('/')))+"""</div>
			<div style="position:absolute;left:0px;right:0px;top:80px;height:30px;padding:0px;z-index:1;">
					<div id="progressBar" class="big-green"><div></div></div>
			</div>
				<form action="/salvaPedido" method="post">""")
		opened = open("sequence.txt").read().split("\n")[:-1]
		for i in range(len(opened)):
			start = 'class="hidden"'
			back = """<a href="javascript:unhide('form"""+str(i-1)+"""');unhide('form"""+str(i)+"""');progressBar("""+str(100/len(opened)*(i-1))+""", $('#progressBar'));">Back</a>"""
			next = """<a href="javascript:unhide('form"""+str(i+1)+"""');unhide('form"""+str(i)+"""');progressBar("""+str(100/len(opened)*(i+1))+""", $('#progressBar'));">Next</a>"""
			submit = ""
			if i==0:  
				start = ' class="unhidden"'
				back = ""
			if i==len(opened)-1: 
				next = ""
				submit = '<input type="submit" value="Enviar"></input>'		
			self.response.write("""
			<div id="form"""+str(i)+"""" """+start+"""style="background-color:#dfdfdf;position:absolute;left:0px;right:0px;top:100px;padding:25px;">
						"""+generate_form(opened[i].strip("\n") +".xml")+back+next+submit+"""
					</div>""")
		self.response.write("""</form><script>
			progressBar(2, $('#progressBar'));
			</script>
				""")
		self.response.out.write("</html>")
	else:
            self.redirect( users.create_login_url('/') )

"""
def iterate_over_xml(xml_file):
        import xml.etree.ElementTree as xml
        tree = xml.parse(xml_file)
        root = tree.getroot()
        def iterate_over_node(node, level, dic):
                if len(node) == 0: return 
                for child in node:
                        if child.text:
                                print "-"*level, child.tag+"="+child.text
                        else:
                                print "-"*level, child.tag
			dic[child.tag] = child.text, otherdic
                        iterate_over_node(child, level+1, otherdic)
        dic ={}
	iterate_over_node(root, 0, dic)
	print dic
"""


class SalvaPedidoHandler(webapp2.RequestHandler):
    def post(self):
	query = list(db.GqlQuery("SELECT * FROM Pedido ORDER BY id DESC"))
	if(len(query)==0):
		id = 0
	else:
		id = query[0].id+1
	novo = Pedido( id=id,
			demandante=self.request.get("demandante"),
			data_entrada=self.request.get("data_entrada"),
			descricao=self.request.get("descricao"),
			numero=self.request.get("numero"),
			email_demandante=self.request.get("email_demandante"),
			local="PRA",
			)
	novo.put()
	self.redirect("/")

class AutorizaPedidoHandler(webapp2.RequestHandler):
    def get(self):
	for pedido in db.GqlQuery("SELECT * FROM Pedido ORDER BY id DESC"):
		if pedido.numero == self.request.get("numero"):
			pedido.autorizacao = str(self.request.get("estado"))
		db.put(pedido)
	self.redirect("/")

class LicitaPedidoHandler(webapp2.RequestHandler):
    def get(self):
	for pedido in db.GqlQuery("SELECT * FROM Pedido ORDER BY id DESC"):
		if pedido.numero == self.request.get("numero"):
			pedido.licitacao = str(self.request.get("estado"))
		db.put(pedido)
	self.redirect("/")

class AcompanharHandler(webapp2.RequestHandler):
    def get(self):
	import operator
	user = users.get_current_user()
	
        if user and user in users_permission and "acompanhar" in users_permission[user]:
		self.response.write("""
					<html>
					<head>
					<link rel="stylesheet" type="text/css" href="/stylesheets/styles.css">
					<link rel="stylesheet" type="text/css" href="/stylesheets/div_styles.css">
					<title>Processos - PRA - UFCG</title>
					<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.js"></script>
					<script type="text/javascript" src="js/progressbar.js"></script>
					<script type="text/javascript" src="js/div_hide.js"></script>
					<link rel="stylesheet" type="text/css" href="/stylesheets/progressbar.css">
					</head>

					<div style="background-color:#660000;position:absolute;left:0px;right:0px;top:0px;height:100px;padding:10px;font-size:30px; color: white; ">PRA - UFCG</div>
<div style="background-color:white;position:relative;left:90%;top:0px;padding:10px;color:white;">"""+('<a href="%s">Sair</a>' %
                        (users.create_logout_url('/')))+"""</div>
<div style="background-color:#dfdfdf;position:absolute;left:0px;right:0px;top:100px;padding:25px;">
""")
		if len(list(db.GqlQuery("SELECT * FROM Pedido ORDER BY id DESC"))) > 0:
			i = 0	
			for tal in db.GqlQuery("SELECT * FROM Pedido ORDER BY id DESC"):
				description = "<br>Demandante: "+tal.demandante + "<br>" + "Data de Entrada: " + tal.data_entrada + "<br>" + "Descricao: " + tal.descricao + "<br>"+"Numero: " + tal.numero + "<br>"+"Email do demandante: " + str(tal.email_demandante) + "<br>"
				image = ""
				if tal.licitacao == "a licitar" and tal.autorizacao == "autorizado":
					image = '<img border="0" src="/images/pedido_a_licitar.png">'
				elif tal.licitacao == "licitacao concluida" and tal.autorizacao == "autorizado" :
					image = '<img border="0" src="/images/pedido_licitacao_concluida.png">'
				elif tal.licitacao == "em licitacao" and tal.autorizacao == "autorizado" :
					image = '<img border="0" src="/images/pedido_em_licitacao.png">'
				if tal.autorizacao == "indefinido" and tal.licitacao == "a licitar":
					image = '<img border="0" src="/images/pedido_criado.png">'			
				elif tal.autorizacao == "autorizado" and tal.licitacao == "a licitar":
					 image = '<img border="0" src="/images/pedido_autorizado.png">'
				elif tal.autorizacao == "nao autorizado" and tal.licitacao == "a licitar":
					 image = '<img border="0" src="/images/pedido_nao_autorizado.png">'
				self.response.write("""<a href="javascript:unhide('process"""+str(i)+"""');">"""+str(tal.numero) + '</a><br><div id="process'+str(i)+'" class="hidden">'+image+description+'</div>')
				i+=1	
		else:
			self.response.write("Não há processos para acompanhar")
        else:
            self.redirect( users.create_login_url('/') )







class AutorizarHandler(webapp2.RequestHandler):
    def get(self):
	import operator
	user = users.get_current_user()
	
        if user and user in users_permission and "acompanhar" in users_permission[user]:
		self.response.write("""
					<html>
					<head>
					<link rel="stylesheet" type="text/css" href="/stylesheets/styles.css">
					<link rel="stylesheet" type="text/css" href="/stylesheets/div_styles.css">
					<title>Processos - PRA - UFCG</title>
					<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.js"></script>
					<script type="text/javascript" src="js/progressbar.js"></script>
					<script type="text/javascript" src="js/div_hide.js"></script>
					<link rel="stylesheet" type="text/css" href="/stylesheets/progressbar.css">
					</head>

					<div style="background-color:#660000;position:absolute;left:0px;right:0px;top:0px;height:100px;padding:10px;font-size:30px; color: white; ">PRA - UFCG</div>
<div style="background-color:white;position:relative;left:90%;top:0px;padding:10px;color:white;">"""+('<a href="%s">Sair</a>' %
                        (users.create_logout_url('/')))+"""</div>
<div style="background-color:#dfdfdf;position:absolute;left:0px;right:0px;top:100px;padding:25px;">
""")
		if len(list(db.GqlQuery("SELECT * FROM Pedido ORDER BY id DESC"))) > 0:
			i = 0	
			for tal in db.GqlQuery("SELECT * FROM Pedido ORDER BY id DESC"):
				if tal.autorizacao == "indefinido":
					description ="<br>Demandante: "+tal.demandante + "<br>" + "Data de Entrada: " + tal.data_entrada + "<br>" + "Descricao: " + tal.descricao + "<br>"+"Numero: " + tal.numero + "<br>"+"Email do demandante: " + str(tal.email_demandante)+ "<br>"
					image = ""
					if tal.licitacao == "a licitar" and tal.autorizacao == "autorizado":
						image = '<img border="0" src="/images/pedido_a_licitar.png">'
					elif tal.licitacao == "licitacao concluida" and tal.autorizacao == "autorizado" :
						image = '<img border="0" src="/images/pedido_licitacao concluida.png">'
					elif tal.licitacao == "em licitacao" and tal.autorizacao == "autorizado" :
						image = '<img border="0" src="/images/pedido_em_licitacao.png">'
					if tal.autorizacao == "indefinido" and tal.licitacao == "a licitar":
						image = '<img border="0" src="/images/pedido_criado.png">'			
					elif tal.autorizacao == "autorizado" and tal.licitacao == "a licitar":
						 image = '<img border="0" src="/images/pedido_autorizado.png">'
					elif tal.autorizacao == "nao autorizado" and tal.licitacao == "a licitar":
						 image = '<img border="0" src="/images/pedido_nao_autorizado.png">'
					self.response.write("""<a href="javascript:unhide('process"""+str(i)+"""');">"""+str(tal.numero) + '</a><br><div id="process'+str(i)+'" class="hidden">'+description+image+'&nbsp;<form action="autorizaPedido"><input type="hidden" name="numero" value="'+tal.numero+'"><select name="estado"><option value="autorizado">Autorizar</option><option value="nao autorizado">Nao Autorizar</option></select><input type="submit" value="Mudar estado de autorizacao"></input></form></div>')
					i+=1	
			if i == 0:
			 self.response.write("Não há processos para autorizar")
		else:
			self.response.write("Não há processos para autorizar")
        else:
            self.redirect( users.create_login_url('/') )








class LicitarHandler(webapp2.RequestHandler):
    def get(self):
	import operator
	user = users.get_current_user()
        if user and user in users_permission and "licitar" in users_permission[user]:
		self.response.write("""
					<html>
					<head>
					<link rel="stylesheet" type="text/css" href="/stylesheets/styles.css">
					<link rel="stylesheet" type="text/css" href="/stylesheets/div_styles.css">
					<title>Processos - PRA - UFCG</title>
					<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.js"></script>
					<script type="text/javascript" src="js/progressbar.js"></script>
					<script type="text/javascript" src="js/div_hide.js"></script>
					<link rel="stylesheet" type="text/css" href="/stylesheets/progressbar.css">
					</head>

					<div style="background-color:#660000;position:absolute;left:0px;right:0px;top:0px;height:100px;padding:10px;font-size:30px; color: white; ">PRA - UFCG</div>
<div style="background-color:white;position:relative;left:90%;top:0px;padding:10px;color:white;">"""+('<a href="%s">Sair</a>' %
                        (users.create_logout_url('/')))+"""</div>
<div style="background-color:#dfdfdf;position:absolute;left:0px;right:0px;top:100px;padding:25px;">
""")
		if len(list(db.GqlQuery("SELECT * FROM Pedido ORDER BY id DESC"))) > 0:
			i = 0	
			for tal in db.GqlQuery("SELECT * FROM Pedido ORDER BY id DESC"):
				if tal.autorizacao == "autorizado" and tal.licitacao in ["em licitacao", "a licitar"]:
					description ="<br>Demandante: "+tal.demandante + "<br>" + "Data de Entrada: " + tal.data_entrada + "<br>" + "Descricao: " + tal.descricao + "<br>"+"Numero: " + tal.numero + "<br>"+"Email do demandante: " + str(tal.email_demandante)+ "<br>"
							
					image = ""
					if tal.licitacao == "a licitar" and tal.autorizacao == "autorizado":
						image = '<img border="0" src="/images/pedido_a_licitar.png">'
					elif tal.licitacao == "licitacao concluida" and tal.autorizacao == "autorizado" :
						image = '<img border="0" src="/images/pedido_licitacao_concluida.png">'
					elif tal.licitacao == "em licitacao" and tal.autorizacao == "autorizado" :
						image = '<img border="0" src="/images/pedido_em_licitacao.png">'
					if tal.autorizacao == "indefinido" and tal.licitacao == "a licitar":
						image = '<img border="0" src="/images/pedido_criado.png">'			
					elif tal.autorizacao == "autorizado" and tal.licitacao == "a licitar":
						 image = '<img border="0" src="/images/pedido_autorizado.png">'
					elif tal.autorizacao == "nao autorizado" and tal.licitacao == "a licitar":
						 image = '<img border="0" src="/images/pedido_nao_autorizado.png">'
					self.response.write("""<a href="javascript:unhide('process"""+str(i)+"""');">"""+str(tal.numero) + '</a><br><div id="process'+str(i)+'" class="hidden">'+image+description+'&nbsp;<form action="licitaPedido"><input type="hidden" name="numero" value="'+tal.numero+'"><select name="estado"><option value="em licitacao">Em Licitacao</option><option value="licitacao concluida">licitacao concluida</option></select><input type="submit" value="Mudar estado de licitacao"></input></form></div>')
					i+=1	
			if i == 0:
			 self.response.write("Não há processos para licitar")
		else:
			self.response.write("Não há processos para licitar")
        else:
            self.redirect( users.create_login_url('/') )
        
from google.appengine.api import users

users_permission = { users.User("usuario_qualquer@example.com"): ['acompanhar'],
users.User("usuario_protocolo@example.com"): ['criar', 'acompanhar'],
users.User("usuario_autorizar@example.com"): ['autorizar', 'acompanhar'],
users.User("daltonserey@gmail.com"):  ['criar', 'licitar', 'autorizar', 'acompanhar'], 
users.User("jcafigueiredo@gmail.com"):  ['criar', 'licitar', 'autorizar', 'acompanhar'],
users.User("usuario_licitar@example.com"): ['licitar','acompanhar'], 
users.User("joopeeds@gmail.com"): ['autorizar', 'acompanhar'],
users.User("franco.tejo@ccc.ufcg.edu.br"):  ['criar', 'acompanhar']}
	
class MainHandler(webapp2.RequestHandler):
    def get(self):
	
	user = users.get_current_user()

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

        self.response.out.write('<html><body>%s</body></html>' % greeting)




def xmltodict(xml_file):
    import xml.etree.ElementTree as ElementTree
    tree = ElementTree.parse(xml_file)
    element = tree.getroot()
    def xmltodict_handler(parent_element):
        result = dict()
        for element in parent_element:
            if len(element):
                obj = xmltodict_handler(element)
            else:
                obj = element.text

            if result.get(element.tag):
                if hasattr(result[element.tag], "append"):
                    result[element.tag].append(obj)
                else:
                    result[element.tag] = [result[element.tag], obj]
            else:
                result[element.tag] = [obj]
        return result

    return {element.tag: xmltodict_handler(element)}

def generate_form(xml_file):
	form = "<table>"
	generated_dict = xmltodict(xml_file)
	for key, value in generated_dict.items():
		form += "<h1>" + generated_dict[key]["title"][0] + "</h1>\n"
		for subkey, subvalue in value.items():
			if subkey == "field":
				for things in subvalue:
					form += '<tr><td align="right">'+things["label"][0]+'</td>\n'+'<td><input type="'+things["type"][0]+'" name="'+things["name"][0]+'"></td><tr>\n'
	return form + "</table>"

		


app = webapp2.WSGIApplication([
    ('/', MainHandler), ('/criar', CriarHandler), ('/salvaPedido', SalvaPedidoHandler) , ('/acompanhar', AcompanharHandler), 
('/autorizar', AutorizarHandler), ('/autorizaPedido', AutorizaPedidoHandler), ('/licitar', LicitarHandler), ('/licitaPedido', LicitaPedidoHandler)
], debug=True)
