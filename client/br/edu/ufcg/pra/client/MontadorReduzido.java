package br.edu.ufcg.pra.client;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.HTML;

public class MontadorReduzido implements MontadorDeBarrinha {

	@Override
	public HTML monta(Pedido p) {
		   
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
       
        
       
        //PREGAO
        
        int iParecerPregao = p.getPregao().getParecer().length - 1;
        int iParecerData = p.getPregao().getData().length - 1;
        
        //TODO gambiarra {
        String dataPregao = "";
        if (p.getPregao().getData().length > 0) dataPregao = p.getPregao().getData()[iParecerData];
        //TODO } gambiarra
 
        if (p.getPregao().getParecer().length > 0) {
            if (p.getPregao().getParecer()[iParecerPregao].equals("null") && p.getCorretude().getDescricao().equals("null") && p.getCorretude().getQuantitativo().equals("null") && p.getCorretude().getCotacao().equals("null")) {
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
                barraProgresso += "<td style='background-color:#999999'><a href='#bottom' id='anchorBarra' title='Empenho: indefinido'>Empenho</a></td>";
                estados.add(0);
        }
        else if (p.getLiquidacao().getData().equals("")) {
                barraProgresso += "<td style='background-color:#FFFF33'><a href='#bottom' id='anchorBarra' title='Empenho: em andamento'>Empenho</a></td>";
                estados.add(1);
                }
        else {
                barraProgresso += "<td style='background-color:#33CC33'><a href='#bottom' id='anchorBarra' title='Empenho: concluido Data: " +
                p.getHomologacao().getData() + "'>Empenho</a></td>";
                estados.add(2);
                }
 
        
      //PAGAMENTO
        if (p.getPagamento().getData().equals("") && p.getLiquidacao().getData().equals("")) {
                barraProgresso += "<td style='background-color:#999999'><a href='#bottom' id='anchorBarra' title='Recebimento: indefinida'>Recebimento</a></td>";
                estados.add(0);
                }
        else if (p.getPagamento().getData().equals("")) {
                barraProgresso += "<td style='background-color:#FFFF33'><a href='#bottom' id='anchorBarra' title='Recebimento: em andamento'>Recebimento</a></td>";
                estados.add(1);
                }
        else {
                barraProgresso += "<td style='background-color:#33CC33'><a href='#bottom' id='anchorBarra' title='Recebimento: concluido Data: " +
                p.getHomologacao().getData() + "'>Recebimento</a></td>";
                estados.add(2);
                }
 
        
        barraProgresso += "</tr></table><br>";
       
        String[] titles = {
        		"Legalidade",  "Corretude",  "Pregão", 
        		"Publicação", "Empenho", "Recebimento"	
        };
        String[] links = {
        		"legalidade",  "corretude",  "pregao", 
        		"publicacao", "empenho", "recebimento"	
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

        
        
        return new HTML(barraProgresso);
	}

}
