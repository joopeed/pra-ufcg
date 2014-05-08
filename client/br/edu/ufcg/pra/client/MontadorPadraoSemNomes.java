package br.edu.ufcg.pra.client;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.HTML;

public class MontadorPadraoSemNomes implements MontadorDeBarrinha {

	@Override
	public HTML monta(Pedido p) {String barraProgresso = "<table border='0' bordecolor='#000000' " +
            "style='background-color:#999999' width='100%' " +
            "cellpadding='10' cellspacing='1'><tr>";
    barraProgresso += "</tr></table><br>";
    int[] estados = p.getValoresDosEstados();
    String[] titles = p.getNomesDosEstados();
    String[] links = p.getLinksDosEstados();
    
    barraProgresso =  "<div class=\"progress\">";
    for(int i = 0; i < estados.length;i++){
    	String label = "&#10003;";
    	String classe = "circle done";
		if(estados[i] == 0 || estados[i] == 1) {
    		label = Integer.toString(i+1);
    		if(estados[i] == 1) classe = "circle active";
    		else classe = "circle";
    	}
    	else if(estados[i] == 3){ 
    		classe = "circle fail";
    		label = "x";
    		}
    	if(i!=0) barraProgresso += "  <span class=\"bar\"></span>";
    	barraProgresso += "      <a href=\"#"+links[i]+"\" title=\""+titles[i]+"\">    <div class=\""+classe+"\">" + 
    			"    <span class=\"label\">"+label+"</span>" + 
        		"  </div></a>";
    	
    	
    }
    
    barraProgresso +="</div>";

    
    
    return new HTML(barraProgresso);
	}

}
