package br.edu.ufcg.pra.client;

import java.util.ArrayList;

import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.UIObject;

public class MaisButton extends Button {

	private String cursorInterno = "";
	final private ArrayList<Pedido> listaa;
	final private CellTable table;
	
	public MaisButton(String cursor, final ArrayList<Pedido> listaa, final CellTable table, final HTML sobra) {
		super("Mais Resultados");
		this.cursorInterno = cursor;
		this.listaa = listaa;
		this.table = table;
		addClickHandler(new ClickHandler() {
			FocusWidget sender = (FocusWidget) event.getSource();
			sender.setEnabled(false);
			
			@Override
			public void onClick(ClickEvent event) {
				
				RequestBuilder builder5;
				builder5 = new RequestBuilder(RequestBuilder.GET, "searchpedido?cursor="+cursorInterno);
		        try {
		          Request request = builder5.sendRequest(null, new RequestCallback() {
		            

					

					

					public void onError(Request request, Throwable exception) {
		            }

		            public void onResponseReceived(Request request, Response response) {
		              if (200 == response.getStatusCode()) {
		            	 RequisicaoDePedidos lista = JsonUtils.safeEval(response.getText()).cast();
		            	 
		            	 boolean connected = lista.getStatus().equals("Connected")? true: false;
		            	 Pedido[] cada = lista.getPedidos();
		            	 sobra.removeFromParent();
		            	 removeFromParent();
		            	 if(lista.getPedidos().length!=0){
		            		 
		            		 RootPanel.get("main_bottom").add(new MaisButton(lista.getCursor(), listaa, table, sobra));
		            		 
		            	 }
		            	 else {
		            		 RootPanel.get("main_bottom").add(new Label("Não há mais pedidos"));
		            		 
		            	 }
		            	 RootPanel.get("main_bottom").add(sobra);
		            	 ArrayList<Pedido> cada2 = new ArrayList<Pedido>();
		            	 for (int i = 0; i < cada.length; i++) {
		            		 listaa.add(cada[i]);
						
		            		 table.setWidth("100%");
		     			    // Set the total row count. This isn't strictly necessary, but it affects
		     			    // paging calculations, so its good habit to keep the row count up to date.
		            		 table.setRowCount(listaa.size(), true);

		     			    // Push the data into the widget.
		     			 	table.setRowData(0, listaa);
		     			 	table.setPageSize(listaa.size());
		     				//geraLista("", cursor);
		     				
		            	 }
		            		 
		              	
		            	 
		            	 
		              } else {
		            	  
		              }
		            }
		          });
		        } catch (RequestException e) {
		        }
				
				
				
				
				
				
				
				
				
				
				
			}

			
		});
		
		
	}
	
	
	
	public String getCursorInterno() {
		return cursorInterno;
	}

	public void setCursorInterno(String cursorInterno) {
		this.cursorInterno = cursorInterno;
	}

	
	
}
