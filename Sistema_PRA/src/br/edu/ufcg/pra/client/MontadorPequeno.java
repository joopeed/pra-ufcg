package br.edu.ufcg.pra.client;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.HTML;

public class MontadorPequeno implements MontadorDeBarrinha {

	@Override
	public HTML monta(Pedido p) {
		
		 int[] estados = p.getValoresDosEstados();
		String barraProgresso = "<table border=\"0\" cellspacing=\"0\" title=\"\" width=\"70px\"> " +
            "<tr>";
   
    
    for(int i = 0; i < estados.length;i++){
    	String classe = "green";
		if(estados[i] == 0 || estados[i] == 1) {
    		if(estados[i] == 1) classe = "yellow";
    		else classe = "grey";
    	}
    	else if(estados[i] == 3){ 
    		classe = "red";
    		}
    	barraProgresso += "<td bgcolor='"+classe+"' height='10px'></td>";
    	
    	
    }
    
    barraProgresso +="</tr></table>";

    
    
    return new HTML(barraProgresso);
	}

}
