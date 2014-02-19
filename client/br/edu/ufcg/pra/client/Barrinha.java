package br.edu.ufcg.pra.client;

import java.util.ArrayList;

import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;

public class Barrinha extends SimplePanel {

	
	private String pedido;
	private SimplePanel panel;

	public Barrinha(String pedido) {
		this.pedido = pedido;
		this.panel = new SimplePanel();
		this.add(panel);
		RequestBuilder builder6 = new RequestBuilder(RequestBuilder.GET, "getpedido?q="+pedido);
		try {
			Request request = builder6.sendRequest(null, new RequestCallback() {

					public void onError(Request request, Throwable exception) {
			        }

			        @SuppressWarnings("deprecation")
					public void onResponseReceived(Request request, Response response) {
			          if (200 == response.getStatusCode()) {
			        	 final Pedido pedido = JsonUtils.safeEval(response.getText()).cast();
			        	 panel.clear();
			        	 panel.add(barrinha(pedido));
			          }}});
		} catch (RequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void atualizar() {
		Timer timer = new Timer() {

			@Override
			public void run() {
				RequestBuilder builder6 = new RequestBuilder(RequestBuilder.GET, "getpedido?q="+pedido);
				try {
					Request request = builder6.sendRequest(null, new RequestCallback() {

							public void onError(Request request, Throwable exception) {
					        }

					        @SuppressWarnings("deprecation")
							public void onResponseReceived(Request request, Response response) {
					          if (200 == response.getStatusCode()) {
					        	 final Pedido pedido = JsonUtils.safeEval(response.getText()).cast();
					        	 panel.clear();
					        	 panel.add(barrinha(pedido));
					          }}});
				} catch (RequestException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

			}
		};
     timer.schedule(1000);
		
	}
	
	
private HTML barrinha(Pedido p) {
        
        String barraProgresso = "<table border='0' bordecolor='#000000' " +
                                                         "style='background-color:#999999' width='100%' " +
                                                         "cellpadding='10' cellspacing='1'><tr>";
       
        ArrayList<Integer> estados = new ArrayList<Integer>();
        
        //LEGALIDADE
        if (p.getLegalidade().getParecer().equals("null")) {
                barraProgresso += "<td style='background-color:#FFFF33'><a href='#legalidade' id='anchorBarra' title='Legalidade parecer: em andamento'>Legalidade</a></td>";
                estados.add(1);
        }
        else if (p.getLegalidade().getParecer().equals("true")) {
                barraProgresso += "<td style='background-color:#33CC33'><a href='#legalidade'id='anchorBarra' title='Legalidade parecer: legal. Data de envio a PJ: " + p.getLegalidade().getDataEnvio() + " Data de retorno da PJ: " + p.getLegalidade().getDataRetorno() + "'>Legalidade</a></td>";
                estados.add(2);
        }
        else {
                barraProgresso += "<td style='background-color:#FF0000'><a href='#legalidade' id='anchorBarra' title='Legalidade parecer: ilegal. Data de envio a PJ: " +
                p.getLegalidade().getDataEnvio() + " Data de retorno da PJ: " + p.getLegalidade().getDataRetorno() + "'>Legalidade</a></td>";
                estados.add(3);
        }
       
        //AUTORIZACAO
        if (p.getAutorizacao().getParecer().equals("null") && p.getLegalidade().getParecer().equals("null")) {
                barraProgresso += "<td style='background-color:#999999'><a href='#autorizacao' id='anchorBarra' title='Autorizacao parecer: indefinido'>Autorização</a></td>";
                estados.add(0);
        }
        else if (p.getAutorizacao().getParecer().equals("null")) {
                barraProgresso += "<td style='background-color:#FFFF33'><a href='#autorizacao' id='anchorBarra' title='Autorizacao parecer: em andamento'>Autorização</a></td>";
                estados.add(1);
        }
        else if (p.getAutorizacao().getParecer().equals("true")) {
                barraProgresso += "<td style='background-color:#33CC33'><a href='#autorizacao' id='anchorBarra' title='Autorizacao parecer: autorizado'>Autorização</a></td>";
                estados.add(2);
        }
        else {
                barraProgresso += "<td style='background-color:#FF0000'><a href='#autorizacao' id='anchorBarra' title='Autorizacao parecer: nao autorizado'>Autorização</a></td>";
                estados.add(3);
    		   }
       
        //CORRETUDE
        
 
        if (p.getCorretude().getDescricao().equals("true") && p.getCorretude().getQuantitativo().equals("true") && p.getCorretude().getCotacao().equals("true")) {
                barraProgresso += "<td style='background-color:#33CC33'><a href='#corretude' id='anchorBarra' title='Corretude: completo. Data da definicao: " +
                p.getCorretude().getData() + "'>Corretude</a></td>";
                estados.add(2);
        }
        else if ((p.getCorretude().getDescricao().equals("false") == false && p.getCorretude().getQuantitativo().equals("false") == false && p.getCorretude().getCotacao().equals("false") == false)) {
                if (p.getAutorizacao().getParecer().equals("null")) {
                        barraProgresso += "<td style='background-color:#999999'><a href='#corretude' id='anchorBarra' title='Corretude: indefinida'>Corretude</a></td>";
                        estados.add(0);
                }else {
                        barraProgresso += "<td style='background-color:#FFFF33'><a href='#corretude' id='anchorBarra' title='Corretude: em andamento'>Corretude</a></td>";
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
                barraProgresso += "<td style='background-color:#FF0000'><a href='#corretude' id='anchorBarra' title='"+statuss+"'>Corretude</a></td>";
               
                if(!(p.getCorretude().getDescricao().equals("null") || p.getCorretude().getQuantitativo().equals("null") || p.getCorretude().getCotacao().equals("null")))
                	if((p.getCorretude().getDescricao().equals("false") || p.getCorretude().getQuantitativo().equals("false") || p.getCorretude().getCotacao().equals("false")))
                		estados.add(3);
                else if(p.getCorretude().getDescricao().equals("null") && p.getCorretude().getQuantitativo().equals("null") && p.getCorretude().getCotacao().equals("null"))
                	estados.add(0);
                else
                	estados.add(1);
                
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
                    barraProgresso += "<td style='background-color:#999999'><a href='#minuta' id='anchorBarra' title='Minuta parecer: indefinido'>Minuta</a></td>";
                    estados.add(0);
            }
            else if (p.getMinuta().getParecer()[iParecerMinuta].equals("null")) {
                    barraProgresso += "<td style='background-color:#FFFF33'><a href='#minuta' id='anchorBarra' title='Minuta parecer: em andamento'>Minuta</a></td>";
                    estados.add(1);
            }
            else if (p.getMinuta().getParecer()[iParecerMinuta].equals("true")) {
                    barraProgresso += "<td style='background-color:#33CC33'><a href='#minuta' id='anchorBarra' title='Minuta parecer: de acordo. Data do inicio da elaboracao: " +
                    dataMinutaInicio + " Data de envio a PJ: " + dataMinutaEnvio + " Data de retorno: " +
                    dataMinutaRetorno + "'>Minuta</a></td>";
                    estados.add(2);
            }
            else {
                    barraProgresso += "<td style='background-color:#FF0000'><a href='#minuta' id='anchorBarra' title='Minuta parecer: nao de acordo. Data do inicio da elaboracao" +
                    ": " + dataMinutaInicio + " Data de envio a PJ: " + dataMinutaEnvio +
                    " Data de retorno: " + dataMinutaRetorno + "'>Minuta</a></td>";
                    estados.add(3);
            }
        }
        else {
        	barraProgresso += "<td style='background-color:#999999'><a href='#minuta' id='anchorBarra' title='Minuta parecer: indefinido'>Minuta</a></td>";
        	estados.add(0);
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
                    barraProgresso += "<td style='background-color:#999999'><a href='#pregao' id='anchorBarra' title='Pregao parecer: indefinido'>Pregão</a></td>";
                    estados.add(0);
            }
            else if (p.getPregao().getParecer()[iParecerPregao].equals("null")) {
                    barraProgresso += "<td style='background-color:#FFFF33'><a href='#pregao' id='anchorBarra' title='Pregao parecer: em andamento'>Pregão</a></td>";
                    estados.add(1);
            }
            else if (p.getPregao().getParecer()[iParecerPregao].equals("true")) {
                    barraProgresso += "<td style='background-color:#33CC33'><a href='#pregao' id='anchorBarra' title='Pregao parecer: realizado. Data de realizacao: " +
                    dataPregao + "'>Pregão</a></td>";
                    estados.add(2);
            }
            else {
                    barraProgresso += "<td style='background-color:#FF0000'><a href='#pregao' id='anchorBarra' title='Pregao parecer: nao realizado'>Pregão</a></td>";
                    estados.add(3);
            }
        }
        else {
        	barraProgresso += "<td style='background-color:#999999'><a href='#pregao' id='anchorBarra' title='Pregao parecer: indefinido'>Pregão</a></td>";
        	estados.add(0);
        }
       
        //ADJUDICACAO
        if (p.getAdjudicacao().getData().equals("") && (p.getPregao().getParecer(iParecerPregao).equals("null") || p.getPregao().getParecer(iParecerPregao).equals("")) ) {
                barraProgresso += "<td style='background-color:#999999'><a href='#adjudicacao' id='anchorBarra' title='Adjudicacao: indefinida'>Adjudicação</a></td>";
                estados.add(0);
        }
        else if (p.getAdjudicacao().getData().equals("")) {
                barraProgresso += "<td style='background-color:#FFFF33'><a href='#adjudicacao' id='anchorBarra' title='Adjudicacao: em andamento'>Adjudicação</a></td>";
                estados.add(1);
        }
        else {
                barraProgresso += "<td style='background-color:#33CC33'><a href='#adjudicacao' id='anchorBarra' title='Adjudicacao: concluida Data: " +
                p.getAdjudicacao().getData() + "'>Adjudicação</a></td>";
                estados.add(2);
        }
 	
        //HOMOLOGACAO
        if (p.getHomologacao().getData().equals("") && p.getAdjudicacao().getData().equals("")) {
                barraProgresso += "<td style='background-color:#999999'><a href='#homologacao' id='anchorBarra' title='Homologacao: indefinida'>Homologação</a></td>";
                estados.add(0);
        }
        else if (p.getHomologacao().getData().equals("")) {
                barraProgresso += "<td style='background-color:#FFFF33'><a href='#homologacao' id='anchorBarra' title='Homologacao: em andamento'>Homologação</a></td>";
                estados.add(1);
        }
        else {
                barraProgresso += "<td style='background-color:#33CC33'><a href='#homologacao' id='anchorBarra' title='Homologacao: concluida Data: " +
                p.getHomologacao().getData() + "'>Homologação</a></td>";
                estados.add(2);
        }
 
        //PUBLICACAO
        if (p.getPublicacao().getData().equals("") && p.getHomologacao().getData().equals("")) {
                barraProgresso += "<td style='background-color:#999999'><a href='#publicacao' id='anchorBarra' title='Publicacao: indefinida'>Publicação</a></td>";
                estados.add(0);
        }
        else if (p.getPublicacao().getData().equals("")) {
                barraProgresso += "<td style='background-color:#FFFF33'><a href='#publicacao' id='anchorBarra' title='Publicacao: em andamento'>Publicação</a></td>";
                estados.add(1);
        }
        else {
                barraProgresso += "<td style='background-color:#33CC33'><a href='#publicacao' id='anchorBarra' title='Publicacao: concluida Data: " +
                p.getPublicacao().getData() + "'>Publicação</a></td>";
                estados.add(2);
        }
        
      //LIQUIDACAO
        if (p.getLiquidacao().getData().equals("") && p.getPublicacao().getData().equals("")) {
                barraProgresso += "<td style='background-color:#999999'><a href='#bottom' id='anchorBarra' title='Liquidacao: indefinida'>Liquidação</a></td>";
                estados.add(0);
        }
        else if (p.getLiquidacao().getData().equals("")) {
                barraProgresso += "<td style='background-color:#FFFF33'><a href='#bottom' id='anchorBarra' title='Liquidacao: em andamento'>Liquidação</a></td>";
                estados.add(1);
                }
        else {
                barraProgresso += "<td style='background-color:#33CC33'><a href='#bottom' id='anchorBarra' title='Liquidacao: concluida Data: " +
                p.getHomologacao().getData() + "'>Liquidação</a></td>";
                estados.add(2);
                }
 
        
      //PAGAMENTO
        if (p.getPagamento().getData().equals("") && p.getLiquidacao().getData().equals("")) {
                barraProgresso += "<td style='background-color:#999999'><a href='#bottom' id='anchorBarra' title='Pagamento: indefinida'>Pagamento</a></td>";
                estados.add(0);
                }
        else if (p.getPagamento().getData().equals("")) {
                barraProgresso += "<td style='background-color:#FFFF33'><a href='#bottom' id='anchorBarra' title='Pagamento: em andamento'>Pagamento</a></td>";
                estados.add(1);
                }
        else {
                barraProgresso += "<td style='background-color:#33CC33'><a href='#bottom' id='anchorBarra' title='Pagamento: concluido Data: " +
                p.getHomologacao().getData() + "'>Pagamento</a></td>";
                estados.add(2);
                }
 
        
        barraProgresso += "</tr></table><br>";
       
        String[] titles = {
        		"Legalidade", "Autorização", "Corretude", "Minuta", "Pregão", 
        		"Adjudicação", "Homologação", "Publicação", "Liquidação", "Pagamento"	
        };
        String[] links = {
        		"legalidade", "autorizacao", "corretude", "minuta", "pregao", 
        		"adjudicacao", "homologacao", "publicacao", "liquidacao", "pagamento"	
        };
        
        barraProgresso =  "<div class=\"progress\">";
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
        	if(i!=0) barraProgresso += "  <span class=\"bar\"></span>";
        	barraProgresso += "      <a href=\"#"+links[i]+"\" title=\""+titles[i]+"\">    <div class=\""+classe+"\">" + 
        			"    <span class=\"label\">"+label+"</span>" + 
        			"    <span class=\"title\">"+titles[i]+"</span>" + 
            		"  </div></a>";
        	
        	
        }
        
        barraProgresso +="</div>";
        //NOVA BARRA
//      
//        		
//        		"  <div class=\"circle done\">" + 
//        		"    <span class=\"label\">2</span>" + 
//        		"    <span class=\"title\">Autorização</span>" + 
//        		"  </div>" + 
//        		"  <span class=\"bar half\"></span>" + 
//        		"  <div class=\"circle active\">" + 
//        		"    <span class=\"label\">3</span>" + 
//        		"    <span class=\"title\">Corretude</span>" + 
//        		"  </div>" + 
//        		
//        		"  <div class=\"circle\">" + 
//        		"    <span class=\"label\">4</span>" + 
//        		"    <span class=\"title\">Minuta</span>" + 
//        		"  </div>" + 
//        		"  <span class=\"bar\"></span>" + 
//        		"  <div class=\"circle\">" + 
//        		"    <span class=\"label\">5</span>" + 
//        		"    <span class=\"title\">Pregão</span>" + 
//        		"  </div>" + 
//        		"  <span class=\"bar\"></span>" + 
//        		"  <div class=\"circle\">" + 
//        		"    <span class=\"label\">6</span>" + 
//        		"    <span class=\"title\">Adjudicação</span>" + 
//        		"  </div>" + 
//        		"  <span class=\"bar\"></span>" + 
//        		"  <div class=\"circle\">" + 
//        		"    <span class=\"label\">7</span>" + 
//        		"    <span class=\"title\">Homologação</span>" + 
//        		"  </div>" + 
//        		"  <span class=\"bar\"></span>" + 
//        		"  <div class=\"circle\">" + 
//        		"    <span class=\"label\">8</span>" + 
//        		"    <span class=\"title\">Publicação</span>" + 
//        		"  </div>" + 
//        		"  <span class=\"bar\"></span>" + 
//        		"  <div class=\"circle\">" + 
//        		"    <span class=\"label\">9</span>" + 
//        		"    <span class=\"title\">Liquidação</span>" + 
//        		"  </div>" + 
//        		"  <span class=\"bar\"></span>" + 
//        		"  <div class=\"circle\">" + 
//        		"    <span class=\"label\">10</span>" + 
//        		"    <span class=\"title\">Pagamento</span>" + 
//        		"  </div>" + 
//        		"</div>";
//
//        
//        
        
        
        
        return new HTML(barraProgresso);
        }

	
}
