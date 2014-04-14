package br.edu.ufcg.pra.client;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.HTML;

public class MontadorReduzidoSemNomes implements MontadorDeBarrinha {

	@Override
	public HTML monta(Pedido p) {
		String barraProgresso = "<table border='0' bordecolor='#000000' " +
				"style='background-color:#999999' width='100%' " +
				"cellpadding='10' cellspacing='1'><tr>";

		/*//LEGALIDADE
if (p.getLegalidade().getParecer().equals("null")) {
barraProgresso += "<td style='background-color:#FFFF33' title='Legalidade parecer: em andamento'>1</td>";
}
else if (p.getLegalidade().getParecer().equals("true")) {
barraProgresso += "<td style='background-color:#33CC33' title='Legalidade parecer: legal<br>Data de envio a PJ: " + p.getLegalidade().getDataEnvio() + "<br>Data de retorno da PJ: " + p.getLegalidade().getDataRetorno() + "'>1</td>";
}
else {
barraProgresso += "<td style='background-color:#FF0000' title='Legalidade parecer: ilegal<br>Data de envio a PJ: " +
p.getLegalidade().getDataEnvio() + "<br>Data de retorno da PJ: " + p.getLegalidade().getDataRetorno() + "'>1</td>";
}

//AUTORIZACAO
if (p.getAutorizacao().getParecer().equals("null") && p.getLegalidade().getParecer().equals("null")) {
barraProgresso += "<td style='background-color:#999999' title='Autorizacao parecer: indefinido'>2</td>";
}
else if (p.getAutorizacao().getParecer().equals("null")) {
barraProgresso += "<td style='background-color:#FFFF33' title='Autorizacao parecer: em andamento'>2</td>";
}
else if (p.getAutorizacao().getParecer().equals("true")) {
barraProgresso += "<td style='background-color:#33CC33' title='Autorizacao parecer: autorizado'>2</td>";
}
else {
barraProgresso += "<td style='background-color:#FF0000' title='Autorizacao parecer: nao autorizado'>2</td>";
}

//CORRETUDE


if (p.getCorretude().getDescricao().equals("true") && p.getCorretude().getQuantitativo().equals("true") && p.getCorretude().getCotacao().equals("true")) {
barraProgresso += "<td style='background-color:#33CC33' title='Corretude: completo<br>Data da definicao: " +
p.getCorretude().getData() + "'>3</td>";
}
else if ((p.getCorretude().getDescricao().equals("false") == false && p.getCorretude().getQuantitativo().equals("false") == false && p.getCorretude().getCotacao().equals("false") == false)) {
if (p.getAutorizacao().getParecer().equals("null"))
barraProgresso += "<td style='background-color:#999999' title='Corretude: indefinida'>3</td>";
else
barraProgresso += "<td style='background-color:#FFFF33' title='Corretude: em andamento'>3</td>";
}
else {
String statuss = "";
if (p.getCorretude().getDescricao().equals("true"))
statuss += "Corretude da descricao: correto <br>";
else
statuss += "Corretude da descricao: incorreto<br>";

if (p.getCorretude().getQuantitativo().equals("true"))
statuss += "Corretude do quantitativo: correto<br>";
else
statuss +="Corretude do quantitativo: incorreto<br>";

if (p.getCorretude().getCotacao().equals("true"))
statuss += "Corretude da cotacao: correto<br>";
else
statuss += "Corretude da cotacao: incorreto<br>";
barraProgresso += "<td style='background-color:#FF0000' title='"+statuss+"'>3</td>";

}

//MINUTA


int iInicio = p.getMinuta().getDataInicio().length - 1;
int iEnvio = p.getMinuta().getDataEnvio().length - 1;
int iRetorno = p.getMinuta().getDataRetorno().length - 1;
int iParecerMinuta = p.getMinuta().getParecer().length - 1;

//gambiarra {
String dataMinutaInicio = "";
if (p.getMinuta().getDataInicio().length > 0) dataMinutaInicio = p.getMinuta().getDataInicio()[iInicio];
String dataMinutaEnvio = "";
if (p.getMinuta().getDataEnvio().length > 0) dataMinutaEnvio = p.getMinuta().getDataEnvio()[iEnvio];
String dataMinutaRetorno = "";
if (p.getMinuta().getDataRetorno().length > 0) dataMinutaRetorno = p.getMinuta().getDataRetorno()[iRetorno];
//} gambiarra

if (p.getMinuta().getParecer().length > 0) {
if (p.getMinuta().getParecer()[iParecerMinuta].equals("null") && p.getCorretude().getCotacao().equals("null")) {
barraProgresso += "<td style='background-color:#999999' title='Minuta parecer: indefinido'>4</td>";
}
else if (p.getMinuta().getParecer()[iParecerMinuta].equals("null")) {
barraProgresso += "<td style='background-color:#FFFF33' title='Minuta parecer: em andamento'>4</td>";
}
else if (p.getMinuta().getParecer()[iParecerMinuta].equals("true")) {
barraProgresso += "<td style='background-color:#33CC33' title='Minuta parecer: de acordo<br>Data do inicio da elaboracao: " +
dataMinutaInicio + "<br>Data de envio a PJ: " + dataMinutaEnvio + "<br>Data de retorno: " +
dataMinutaRetorno + "'>4</td>";
}
else {
barraProgresso += "<td style='background-color:#FF0000' title='Minuta parecer: nao de acordo<br>Data do inicio da elaboracao" +
": " + dataMinutaInicio + "<br>Data de envio a PJ: " + dataMinutaEnvio +
"<br>Data de retorno: " + dataMinutaRetorno + "'>4</td>";
}
}
else {
barraProgresso += "<td style='background-color:#999999' title='Minuta parecer: indefinido'>4</td>";
}

//PREGAO

int iParecerPregao = p.getPregao().getParecer().length - 1;
int iParecerData = p.getPregao().getData().length - 1;

//TODO gambiarra {
String dataPregao = "";
if (p.getPregao().getData().length > 0) dataPregao = p.getPregao().getData()[iParecerData];
//TODO } gambiarra

if (p.getPregao().getParecer().length > 0) {
if (p.getPregao().getParecer()[iParecerPregao].equals("null") && p.getMinuta().getParecer()[iParecerMinuta].equals("null")) {
barraProgresso += "<td style='background-color:#999999' title='Pregao parecer: indefinido'>5</td>";
}
else if (p.getPregao().getParecer()[iParecerPregao].equals("null")) {
barraProgresso += "<td style='background-color:#FFFF33' title='Pregao parecer: em andamento'>5</td>";
}
else if (p.getPregao().getParecer()[iParecerPregao].equals("true")) {
barraProgresso += "<td style='background-color:#33CC33' title='Pregao parecer: realizado<br>Data de realizacao: " +
dataPregao + "'>5</td>";
}
else {
barraProgresso += "<td style='background-color:#FF0000' title='Pregao parecer: nao realizado'>5</td>";
}
}
else {
barraProgresso += "<td style='background-color:#999999' title='Minuta parecer: indefinido'>4</td>";
}

//ADJUDICACAO
if (p.getAdjudicacao().getData().equals("") && (p.getPregao().getParecer(iParecerPregao).equals("null") || p.getPregao().getParecer(iParecerPregao).equals("")) ) {
barraProgresso += "<td style='background-color:#999999' title='Adjudicacao: indefinida'>6</td>";
}
else if (p.getAdjudicacao().getData().equals("")) {
barraProgresso += "<td style='background-color:#FFFF33' title='Adjudicacao: em andamento'>6</td>";
}
else {
barraProgresso += "<td style='background-color:#33CC33' title='Adjudicao: concluida<br>Data: " +
p.getAdjudicacao().getData() + "'>6</td>";
}

//HOMOLOGACAO
if (p.getHomologacao().getData().equals("") && p.getAdjudicacao().getData().equals("")) {
barraProgresso += "<td style='background-color:#999999' title='Homologacao: indefinida'>7</td>";
}
else if (p.getHomologacao().getData().equals("")) {
barraProgresso += "<td style='background-color:#FFFF33' title='Homologacao: em andamento'>7</td>";
}
else {
barraProgresso += "<td style='background-color:#33CC33' title='Homologacao: concluida<br>Data: " +
p.getHomologacao().getData() + "'>7</td>";
}

//PUBLICACAO
if (p.getPublicacao().getData().equals("") && p.getHomologacao().getData().equals("")) {
barraProgresso += "<td style='background-color:#999999' title='Publicacao: indefinida'>8</td>";
}
else if (p.getPublicacao().getData().equals("")) {
barraProgresso += "<td style='background-color:#FFFF33' title='Publicacao: em andamento'>8</td>";
}
else {
barraProgresso += "<td style='background-color:#33CC33' title='Publicacao: concluida<br>Data: " +
p.getPublicacao().getData() + "'>8</td>";
}

//LIQUIDACAO
if (p.getLiquidacao().getData().equals("") && p.getPublicacao().getData().equals("")) {
barraProgresso += "<td style='background-color:#999999' title='Liquidacao: indefinida'>9</td>";
}
else if (p.getLiquidacao().getData().equals("")) {
barraProgresso += "<td style='background-color:#FFFF33' title='Liquidacao: em andamento'>9</td>";
}
else {
barraProgresso += "<td style='background-color:#33CC33' title='Liquidacao: concluida<br>Data: " +
p.getHomologacao().getData() + "'>9</td>";
}


//PAGAMENTO
if (p.getPagamento().getData().equals("") && p.getLiquidacao().getData().equals("")) {
barraProgresso += "<td style='background-color:#999999' title='Pagamento: indefinida'>10</td>";
}
else if (p.getPagamento().getData().equals("")) {
barraProgresso += "<td style='background-color:#FFFF33' title='Pagamento: em andamento'>10</td>";
}
else {
barraProgresso += "<td style='background-color:#33CC33' title='Pagamento: concluido<br>Data: " +
p.getHomologacao().getData() + "'>10</td>";
}

		 */


		ArrayList<Integer> estados = new ArrayList<Integer>();

		//LEGALIDADE
		if (p.getLegalidade().getParecer().equals("null")) {
			barraProgresso += "<td style='background-color:#FFFF33'><a id='anchorBarra' title='Legalidade parecer: em andamento'>Legalidade</a></td>";
			estados.add(1);
		}
		else if (p.getLegalidade().getParecer().equals("true")) {
			barraProgresso += "<td style='background-color:#33CC33'><a id='anchorBarra' title='Legalidade parecer: legal. Data de envio a PJ: " + p.getLegalidade().getDataEnvio() + " Data de retorno da PJ: " + p.getLegalidade().getDataRetorno() + "'>Legalidade</a></td>";
			estados.add(2);
		}
		else {
			barraProgresso += "<td style='background-color:#FF0000'><a id='anchorBarra' title='Legalidade parecer: ilegal. Data de envio a PJ: " +
					p.getLegalidade().getDataEnvio() + " Data de retorno da PJ: " + p.getLegalidade().getDataRetorno() + "'>Legalidade</a></td>";
			estados.add(3);
		}

		

		//CORRETUDE


		if (p.getCorretude().getDescricao().equals("true") && p.getCorretude().getQuantitativo().equals("true") && p.getCorretude().getCotacao().equals("true")) {
			barraProgresso += "<td style='background-color:#33CC33'><a id='anchorBarra' title='Corretude: completo. Data da definicao: " +
					p.getCorretude().getData() + "'>Corretude</a></td>";
			estados.add(2);
		}
		else if ((p.getCorretude().getDescricao().equals("false") == false && p.getCorretude().getQuantitativo().equals("false") == false && p.getCorretude().getCotacao().equals("false") == false)) {
			if (p.getAutorizacao().getParecer().equals("null")) {
				barraProgresso += "<td style='background-color:#999999'><a id='anchorBarra' title='Corretude: indefinida'>Corretude</a></td>";
				estados.add(0);
			}else {
				barraProgresso += "<td style='background-color:#FFFF33'><a id='anchorBarra' title='Corretude: em andamento'>Corretude</a></td>";
				estados.add(1);
			}
		}
		else {
			String statuss = "";
			if (p.getCorretude().getDescricao().equals("true"))
				statuss += "Corretude da descricao: correto <br>";
			else
				statuss += "Corretude da descricao: incorreto<br>";

			if (p.getCorretude().getQuantitativo().equals("true"))
				statuss += "Corretude do quantitativo: correto<br>";
			else
				statuss +="Corretude do quantitativo: incorreto<br>";

			if (p.getCorretude().getCotacao().equals("true"))
				statuss += "Corretude da cotacao: correto<br>";
			else
				statuss += "Corretude da cotacao: incorreto<br>";
			barraProgresso += "<td style='background-color:#FF0000'><a id='anchorBarra' title='"+statuss+"'>Corretude</a></td>";

			if(!(p.getCorretude().getDescricao().equals("null") || p.getCorretude().getQuantitativo().equals("null") || p.getCorretude().getCotacao().equals("null")))
				if((p.getCorretude().getDescricao().equals("false") || p.getCorretude().getQuantitativo().equals("false") || p.getCorretude().getCotacao().equals("false")))
					estados.add(3);
				else if(p.getCorretude().getDescricao().equals("null") && p.getCorretude().getQuantitativo().equals("null") && p.getCorretude().getCotacao().equals("null"))
					estados.add(0);
				else
					estados.add(1);

		}

		
		//PREGAO

		int iParecerPregao = p.getPregao().getParecer().length - 1;
		int iParecerData = p.getPregao().getData().length - 1;
		boolean passou = false;
		//TODO gambiarra {
		String dataPregao = "";
		if (p.getPregao().getData().length > 0) dataPregao = p.getPregao().getData()[iParecerData];
		//TODO } gambiarra

		if (p.getPregao().getParecer().length > 0) {
			if (p.getPregao().getParecer()[iParecerPregao].equals("null") && p.getCorretude().getDescricao().equals("null") && p.getCorretude().getQuantitativo().equals("null") && p.getCorretude().getCotacao().equals("null")) {
				barraProgresso += "<td style='background-color:#999999'><a id='anchorBarra' title='Pregao parecer: indefinido'>Pregão</a></td>";
				estados.add(0);
				passou = true;
			}
			else if (p.getPregao().getParecer()[iParecerPregao].equals("null")) {
				barraProgresso += "<td style='background-color:#FFFF33'><a id='anchorBarra' title='Pregao parecer: em andamento'>Pregão</a></td>";
				estados.add(1);
			}
			else if (p.getPregao().getParecer()[iParecerPregao].equals("true")) {
				barraProgresso += "<td style='background-color:#33CC33'><a id='anchorBarra' title='Pregao parecer: realizado. Data de realizacao: " +
						dataPregao + "'>Pregão</a></td>";
				estados.add(2);
			}
			else {
				barraProgresso += "<td style='background-color:#FF0000'><a id='anchorBarra' title='Pregao parecer: nao realizado'>Pregão</a></td>";
				estados.add(3);
			}
		}
		else {
			barraProgresso += "<td style='background-color:#999999'><a id='anchorBarra' title='Pregao parecer: indefinido'>Pregão</a></td>";
			estados.add(0);
			passou = true;
		}


		//PUBLICACAO
		if (p.getPublicacao().getData().equals("") && passou) {
			barraProgresso += "<td style='background-color:#999999'><a id='anchorBarra' title='Publicacao: indefinida'>Publicação</a></td>";
			estados.add(0);
		}
		else if (p.getPublicacao().getData().equals("")) {
			barraProgresso += "<td style='background-color:#FFFF33'><a id='anchorBarra' title='Publicacao: em andamento'>Publicação</a></td>";
			estados.add(1);
		}
		else {
			barraProgresso += "<td style='background-color:#33CC33'><a id='anchorBarra' title='Publicacao: concluida Data: " +
					p.getPublicacao().getData() + "'>Publicação</a></td>";
			estados.add(2);
		}

		//LIQUIDACAO
		if (p.getLiquidacao().getData().equals("") && p.getPublicacao().getData().equals("")) {
			barraProgresso += "<td style='background-color:#999999'><a id='anchorBarra' title='Empenho: indefinido'>Empenho</a></td>";
			estados.add(0);
		}
		else if (p.getLiquidacao().getData().equals("")) {
			barraProgresso += "<td style='background-color:#FFFF33'><a id='anchorBarra' title='Empenho: em andamento'>Empenho</a></td>";
			estados.add(1);
		}
		else {
			barraProgresso += "<td style='background-color:#33CC33'><a id='anchorBarra' title='Empenho: concluido Data: " +
					p.getHomologacao().getData() + "'>Empenho</a></td>";
			estados.add(2);
		}


		//PAGAMENTO
		if (p.getPagamento().getData().equals("") && p.getLiquidacao().getData().equals("")) {
			barraProgresso += "<td style='background-color:#999999'><a id='anchorBarra' title='Pagamento: indefinida'>Pagamento</a></td>";
			estados.add(0);
		}
		else if (p.getPagamento().getData().equals("")) {
			barraProgresso += "<td style='background-color:#FFFF33'><a id='anchorBarra' title='Pagamento: em andamento'>Pagamento</a></td>";
			estados.add(1);
		}
		else {
			barraProgresso += "<td style='background-color:#33CC33'><a id='anchorBarra' title='Pagamento: concluido Data: " +
					p.getHomologacao().getData() + "'>Pagamento</a></td>";
			estados.add(2);
		}


		barraProgresso += "</tr></table><br>";


		String[] titles = {
				"Legalidade",  "Corretude",  "Pregão", 
				"Publicação", "Empenho", "Pagamento"	
		};
		String[] links = {
				"legalidade",  "corretude",  "pregao", 
				 "publicacao", "liquidacao", "pagamento"	
		};

		barraProgresso =  "<div class=\"progress_2\">";
		for(int i = 0; i < estados.size();i++){
			String label = "&#10003;";
			String classe = "circle done";
			if(estados.get(i) == 0 || estados.get(i) == 1) {
				label = Integer.toString(i+1);
				if(estados.get(i) == 1) classe = "circle active";
				else classe = "circle";
			}
			else if(estados.get(i) == 3){ 
				classe = "circle fail";
				label = "x";
			}
			if(i!=0) barraProgresso += "  <span class=\"bar_2\"></span>";
			barraProgresso += "      <a\" title=\""+titles[i]+"\">    <div class=\""+classe+"\">" + 
					"    <span class=\"label\">"+label+"</span>" + 
					"  </div></a>";


		}

		barraProgresso +="</div>";

		return new HTML(barraProgresso);
	}

}
