package com.google.gwt.sample.stockwatcher.client;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;


class ListaPedidos extends JavaScriptObject {                              // (1)
	  // Overlay types always have protected, zero argument constructors.
	  protected ListaPedidos() {}                                              // (2)

	  // JSNI methods to get stock data.
	  public final native String status() /*-{ return this.status; }-*/; // (3)
	  public final native String[] getPedidos() /*-{ return this.pedidos; }-*/;

	 
	}


class ListaPermissoes extends JavaScriptObject {                              // (1)
	  // Overlay types always have protected, zero argument constructors.
	  protected ListaPermissoes() {}                                              // (2)

	  // JSNI methods to get stock data.
	  public final native String status() /*-{ return this.status; }-*/; // (3)
	  public final native String[] getPermissoes() /*-{ return this.permissoes; }-*/;

	 
	}

class ListaPedidosForTable extends JavaScriptObject {                              // (1)
	  // Overlay types always have protected, zero argument constructors.
	  protected ListaPedidosForTable() {}                                              // (2)

	  // JSNI methods to get stock data.
	  public final native String status() /*-{ return this.status; }-*/; // (3)
	  public final native Pedido[] getPedidos() /*-{ return this.pedidos; }-*/;

	 
	}

class Pedido extends JavaScriptObject {                              // (1)
	  // Overlay types always have protected, zero argument constructors.
	  protected Pedido() {}                                              // (2)

	  // JSNI methods to get stock data.
	  public final native String getDemandante() /*-{ return this.demandante; }-*/; // (3)
	  public final native String getNumero() /*-{ return this.numero; }-*/;
	  public final native String getEmail() /*-{ return this.email_demandante; }-*/;
	  public final native String getLocal() /*-{ return this.local; }-*/;
	  public final native String getData() /*-{ return this.data_entrada; }-*/;
	  public final native String getDescricao() /*-{ return this.descricao; }-*/;
	  public final native String getLegalidade() /*-{ return this.legalidade; }-*/;
	  public final native String getAutorizacao() /*-{ return this.autorizacao; }-*/;
	  public final native String getConferencia() /*-{ return this.conferencia; }-*/;
	  public final native String getMinuta() /*-{ return this.minuta; }-*/;
	  public final native String getLegalidadeMateriais() /*-{ return this.legalidade_materiais; }-*/;
	  public final native String getPregao() /*-{ return this.pregao; }-*/;
	  public final native String getLicitacao() /*-{ return this.licitacao; }-*/;
	  public final native String getAdjudicacao() /*-{ return this.adjudicacao; }-*/;
	  public final native String getHomologacao() /*-{ return this.homologacao; }-*/;
	  public final native String getPublicacao() /*-{ return this.publicacao; }-*/;
	  public final native String getMinutaEmpenho() /*-{ return this.minuta_empenho; }-*/;
	  public final native String getDetalhamento() /*-{ return this.detalhamento; }-*/;
	  public final native String getEmpenho() /*-{ return this.empenho; }-*/;
	  public final native String getNotaAlmoxarifado() /*-{ return this.almoxarifado; }-*/;
	  public final native String getTombamento() /*-{ return this.tombamento; }-*/;
	  public final native String getContabilidade() /*-{ return this.contabilidade; }-*/;
	  public final native String getLiquidacao() /*-{ return this.liquidacao; }-*/;
	  public final native String getPagamento() /*-{ return this.pagamento; }-*/;
	}


class Pedido2 extends JavaScriptObject {                              // (1)
	  // Overlay types always have protected, zero argument constructors.
	  protected Pedido2() {}                                              // (2)

	  // JSNI methods to get stock data.
	  public final native String getDemandante() /*-{ return this.demandante; }-*/; // (3)
	  public final native String getNumero() /*-{ return this.numero; }-*/;
	  public final native String getDescricao() /*-{ return this.descricao; }-*/;
	  public final native String getData() /*-{ return this.data_entrada; }-*/;
	  	
	}



class User extends JavaScriptObject {                              // (1)
	  // Overlay types always have protected, zero argument constructors.
	  protected User() {}                                              // (2)

	  // JSNI methods to get stock data.
	  public final native String getEmail() /*-{ return this.user_email; }-*/; // (3)
	  public final native String getNickname() /*-{ return this.user_nickname; }-*/;
	  public final native String getId() /*-{ return this.user_user_id; }-*/;
	  public final native String getStatus() /*-{ return this.status; }-*/;
	  public final native String getUrl() /*-{ return this.url; }-*/;
}

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class StockWatcher implements EntryPoint {
	
	protected String url = "";
	protected String status = "";
	
	
	
		  
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {


			 //     new Contact("John", new Date(80, 4, 12), "123 Fourth Avenue"),
			  //    new Contact("Joe", new Date(85, 2, 22), "22 Lance Ln"),
			  //    new Contact("George", new Date(46, 6, 6), "1600 Pennsylvania Avenue"));
			
		
		geraLista("");
		
		
		

	    
		final Label nameField = new Label();
		nameField.setText("");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("user_on_top_bar").add(nameField);
		
		//pedidosPanel();
		
		
		
		
		
		
		
		
		
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, "/LoginHandler");
		//RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, "/teste.json");
		
        try {
          Request request = builder.sendRequest(null, new RequestCallback() {
            

		

			public void onError(Request request, Throwable exception) {
            }

            public void onResponseReceived(Request request, Response response) {
              if (200 == response.getStatusCode()) {
            	 
            	 User user = JsonUtils.safeEval(response.getText()).cast();
            	 status = user.getStatus();
            	 if(status.equals("Connected")){
            		 nameField.setText(user.getEmail());
            		 url = user.getUrl();
            	 }
            	 else	
            		 url = user.getUrl();
            	 
              } else {
            	  
              }
            }
          });
        } catch (RequestException e) {
        }
		
        String nameButton;
		if(status.equals("Connected")) nameButton = "Logout";
		else nameButton = "Login";
        	 Button sendButton = new Button(nameButton, new ClickHandler() {
  		      public void onClick(ClickEvent event) {
  		    	  Window.Location.replace(url);
  		        }
  		      });
        	RootPanel.get("user_on_top_bar").add(sendButton);
        
        
        

		

        RequestBuilder builder6 = new RequestBuilder(RequestBuilder.GET, "/Permissoes");

        try {
          Request request = builder6.sendRequest(null, new RequestCallback() {
            

			private String[] listaa;

			public void onError(Request request, Throwable exception) {
            }

            public void onResponseReceived(Request request, Response response) {
              if (200 == response.getStatusCode()) {
            	 ListaPermissoes lista = JsonUtils.safeEval(response.getText()).cast();
            	 listaa = lista.getPermissoes();
            	 HorizontalPanel hPanel = new HorizontalPanel();
            	 final String[] listaa = lista.getPermissoes();
              	 /*for (int i = 0; i < listaa.length; i++) {
              		 if(listaa[i].equals("acompanhar_qualquer")||listaa[i].equals("acompanhar_proprios")){
	              		 hPanel.add(new Button("Pedidos",  new ClickHandler() {
	           		        public void onClick(ClickEvent event) {
	              		    	RootPanel.get("main_right").clear();
	              		    	RootPanel.get("main_left").clear();	
	              		    	pedidosPanel();
	           		        }}));
              		} else if(listaa[i].equals("criacao")) {
              			hPanel.add(new Button("Cadastrar pedido",  new ClickHandler() {
	           		        public void onClick(ClickEvent event) {
	              		    	RootPanel.get("main_right").clear();
	              		    	RootPanel.get("main_left").clear();	
	              		    	cadastraPanel();
	           		        }}));
              			
              			
              		} else hPanel.add(new Button(listaa[i]));
              		
              	 RootPanel.get("top_bar").add(hPanel);
              	}*/
              	
            	 
            	 
              } else {
            	  
              }
            }
          });
        } catch (RequestException e) {
        }
		

        
        
        
		
		
		
	}



	private void geraLista(String search) {
		RequestBuilder builder5 = new RequestBuilder(RequestBuilder.GET, "PedidosForTable?search="+search);

        try {
          Request request = builder5.sendRequest(null, new RequestCallback() {
            

			private String[] listaa;

			public void onError(Request request, Throwable exception) {
            }

            public void onResponseReceived(Request request, Response response) {
              if (200 == response.getStatusCode()) {
            	 ListaPedidosForTable lista = JsonUtils.safeEval(response.getText()).cast();
            	 Pedido[] cada = lista.getPedidos();
            	 final ArrayList<Pedido> listaa = new ArrayList<Pedido>();
            	 for(Pedido pedido: cada) listaa.add(pedido);
            	 
            	 CriaExibeTable(listaa);
              	
            	 
            	 
              } else {
            	  
              }
            }
          });
        } catch (RequestException e) {
        }
	}



	private void CriaExibeTable(List<? extends Pedido> listaa) {
		
		// Create a CellTable.
	    CellTable<Pedido> table = new CellTable<Pedido>();
	    //table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
	    RootPanel.get("main_bottom").clear();
	    // Add a text column to show the name.
	    TextColumn<Pedido> nameColumn = new TextColumn<Pedido>() {
	   
		@Override
		public String getValue(Pedido object) {
			// TODO Auto-generated method stub
			return object.getDemandante();
		}
	    };
	    table.addColumn(nameColumn, "Name");

	   /* // Add a date column to show the birthday.
	    DateCell dateCell = new DateCell();
	    Column<Contact, Date> dateColumn = new Column<Contact, Date>(dateCell) {
	      @Override
	      public Date getValue(Contact object) {
	        return object.birthday;
	      }
	    };
	    table.addColumn(dateColumn, "Birthday");
*/
	    // Add a text column to show the address.
	    TextColumn<Pedido> addressColumn = new TextColumn<Pedido>() {
	      @Override
	      public String getValue(Pedido object) {
	        return object.getNumero();
	      }
	    };
	    table.addColumn(addressColumn, "Numero");


	    // Add a text column to show the address.
	    TextColumn<Pedido> descricaoColumn = new TextColumn<Pedido>() {
	      @Override
	      public String getValue(Pedido object) {
	        return object.getDescricao().substring(0, 20);
	      }
	    };
	    table.addColumn(descricaoColumn, "Descricao");


	    // Add a text column to show the address.
	    TextColumn<Pedido> dataColumn = new TextColumn<Pedido>() {
	      @Override
	      public String getValue(Pedido object) {
	        return object.getData();
	      }
	    };
	    table.addColumn(dataColumn, "Data");
	    
	    //ProgressBar exampleBar1 = new ProgressBar(30, 100, 30);
	    
	    // Add a selection model to handle user selection.
	    final SingleSelectionModel<Pedido> selectionModel = new SingleSelectionModel<Pedido>();
	    table.setSelectionModel(selectionModel);
	    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
	      public void onSelectionChange(SelectionChangeEvent event) {
	       Pedido selected = selectionModel.getSelectedObject();
	        if (selected != null) {
	        // Window.alert("You selected: Pedido " + selected.getNumero());
	         Widget barra = barrinha(selected);
	         // Create the dialog box
	         final DialogBox dialogBox = new DialogBox();
	         dialogBox.center();
	         dialogBox.setText("Detalhes do pedido");

	         // Create a table to layout the content
	         VerticalPanel dialogContents = new VerticalPanel();
	         dialogContents.setSpacing(4);
	         dialogBox.setWidget(dialogContents);

	         // Add some text to the top of the dialog
	         dialogContents.add(barra);
	         dialogContents.add(new Label("Numero do pedido: "+selected.getNumero()));
	         dialogContents.add(new Label("Nome do demandante: "+selected.getDemandante()));
	         dialogContents.add(new Label("Email do demandante: "+selected.getEmail()));
	         dialogContents.add(new Label("Data de entrada: "+selected.getData()));
	         dialogContents.add(new Label("Local atual: "+selected.getLocal()));
	         dialogContents.add(new Label("Descrição do pedido: "+selected.getDescricao()));
	         dialogContents.setCellHorizontalAlignment(
	             barra, HasHorizontalAlignment.ALIGN_CENTER);

	         dialogBox.setGlassEnabled(true);
	         dialogBox.setAnimationEnabled(true);
	         dialogBox.show();
	         Button closeButton = new Button("Fechar", new ClickHandler() {
	        	          public void onClick(ClickEvent event) {
	        	            dialogBox.hide();
	        	          }
	        	        });
	        	    dialogContents.add(closeButton);
	       }
	      }
	    });

	    table.setWidth("100%");
	    // Set the total row count. This isn't strictly necessary, but it affects
	    // paging calculations, so its good habit to keep the row count up to date.
	    table.setRowCount(listaa.size(), true);

	    // Push the data into the widget.
	    table.setRowData(0, listaa);

	    // Add it to the root panel.
	    
	    
	    RootPanel.get("main_bottom").add(table);
	   // RootPanel.get("main_bottom").add(exampleBar1);
	  
		
		
	    
		
		
	    // Create a Pager to control the table.
	    SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
	    SimplePager pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
	    pager.setDisplay(table);
	    RootPanel.get("main_bottom").add(pager);
	    
	    HorizontalPanel hPanel = new HorizontalPanel();
	    
	    final TextBox campoPesquisa = new TextBox();
	    campoPesquisa.setSize("400px", "30px");
	    Button botaoPesquisa = new Button("Pesquisar", new ClickHandler() {
		        public void onClick(ClickEvent event) { geraLista(campoPesquisa.getText()); }});
	    botaoPesquisa.setSize("80px", "30px");
	    hPanel.add(campoPesquisa);
	    hPanel.add(botaoPesquisa);
	    Button cadastrarButton = new Button("Cadastrar pedido",  new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	RootPanel.get("main_bottom").clear();
  		    	cadastraPanel();
	        }});
	    cadastrarButton.setSize("140px", "30px");
	    hPanel.add(cadastrarButton);
	    RootPanel.get("main_top").clear();
		RootPanel.get("main_top").add(hPanel);
	}
	
	
	
	private void alteraPedidoPanel() {
		final VerticalPanel vPanel = new VerticalPanel();
		
		vPanel.add(new HTML("Carregando pedidos...<br>"));
		
		RequestBuilder builder2 = new RequestBuilder(RequestBuilder.GET, "/Pedido");
      try {
        Request request = builder2.sendRequest(null, new RequestCallback() {
          

			public void onError(Request request, Throwable exception) {
          }

          public void onResponseReceived(Request request, Response response) {
            if (200 == response.getStatusCode()) {
          	 ListaPedidos lista = JsonUtils.safeEval(response.getText()).cast();
          	 vPanel.clear();
          	vPanel.add(new HTML("Pedidos<br>"));
          	 final String[] listaa = lista.getPedidos();
          	 for (int i = 0; i < listaa.length; i++) {
          		 final RequestBuilder builder3 = new RequestBuilder(RequestBuilder.GET, "/Pedido?numero="+listaa[i]);
          		 vPanel.add(new Button("Pedido "+ listaa[i], new ClickHandler() {
       		        public void onClick(ClickEvent event) {
       		    	RootPanel.get("main_right").clear();
       		    	final DecoratedPopupPanel simplePopup = new DecoratedPopupPanel(true);
       		    	simplePopup.ensureDebugId("cwBasicPopup-simplePopup");
       		    	simplePopup.setWidth("150px");
       		     	simplePopup.setWidget(new HTML("Carregando <br> por favor, espere!"));
       		     	// Reposition the popup relative to the button
       	            Widget source = (Widget) event.getSource();
       	            int left = source.getAbsoluteLeft() + 10;
       	            int top = source.getAbsoluteTop() + 10;
       	            simplePopup.setPopupPosition(left, top);

       	            // Show the popup
       	            simplePopup.show();
       		    	  
       		      try {
       		        Request request = builder3.sendRequest(null, new RequestCallback() {
       		          

       					public void onError(Request request, Throwable exception) {
       		          }

       		          public void onResponseReceived(Request request, Response response) {
       		            if (200 == response.getStatusCode()) {
       		            	simplePopup.hide();
       		            	RootPanel.get("main_right").clear();
       		            	
       		            	DecoratorPanel decPanel = new DecoratorPanel();
       		            	final FlexTable layout = new FlexTable();
       		            	layout.setCellSpacing(6);
       		            	User user = JsonUtils.safeEval(response.getText()).cast();
       		            	Pedido pedido = JsonUtils.safeEval(response.getText()).cast();
       		            	Label Nome = new Label();
       		            	Nome.setText(pedido.getDemandante());
       		            	Label numero = new Label();
       		            	numero.setText(pedido.getNumero());
       		            	Label email = new Label();
       		            	email.setText(pedido.getEmail());
       		            	Label local = new Label();
       		            	local.setText(pedido.getLocal());
       		            	Label descricao = new Label();
       		            	descricao.setText(pedido.getDescricao());
       		     		    layout.setWidget(0, 1, numero);
       		     		    layout.setWidget(1, 1, Nome);
       		     		    layout.setWidget(2, 1, email);
       		     		    layout.setWidget(3, 1, local);
       		     		    layout.setWidget(4, 1, descricao);
       		            	decPanel.setWidget(layout);
       		            	RootPanel.get("main_right").add(decPanel);
       		          	 
       		            } else {
       		          	  
       		            }
       		          }
       		        });
       		      } catch (RequestException e) {
       		      }
       		    	  
       		    	  
       		    	  
       		    	  
    		        }
    		      }));
          		
          		 
          	 }
          	 
            } else {
          	  
            }
          }
        });
      } catch (RequestException e) {
      }
		
		
		
		
		
		RootPanel.get("main_left").add(vPanel);
	}
	
	
	private HTML barrinha(Pedido p){
       	
       	
       	
       	
       	
       	
       	
       	String barraProgresso = "<table border='0' bordecolor='#000000' " +
       							 "style='background-color:#999999' width='100%' " +
       							 "cellpadding='10' cellspacing='1'><tr>";
       	
       	//LEGALIDADE
       	if (p.getLegalidade().equals("indefinido")) {
       		barraProgresso += "<td style='background-color:#FFFF33' title='Legalidade: em andamento'>1</td>";
       	}
       	else if (p.getLegalidade().equals("legal")) {
       		barraProgresso += "<td style='background-color:#33CC33' title='Legalidade: legal'>1</td>";
       	}
       	else {
       		barraProgresso += "<td style='background-color:#FF0000' title='Legalidade: ilegal'>1</td>";
       	}
       	
       	//AUTORIZACAO
       	if (p.getAutorizacao().equals("indefinido") && p.getLegalidade().equals("indefinido")) {
       		barraProgresso += "<td style='background-color:#999999' title='Autorizacao: indefinido'>2</td>";
       	}
       	else if (p.getAutorizacao().equals("indefinido")) {
       		barraProgresso += "<td style='background-color:#FFFF33' title='Autorizacao: em andamento'>2</td>";
       	}
       	else if (p.getAutorizacao().equals("autorizado")) {
       		barraProgresso += "<td style='background-color:#33CC33' title='Autorizacao: autorizado'>2</td>";
       	}
       	else {
       		barraProgresso += "<td style='background-color:#FF0000' title='Autorizacao: nao autorizado'>2</td>";
       	}
       	
       	//CONFERENCIA
       	if (p.getConferencia().equals("indefinido") && p.getAutorizacao().equals("indefinido")) {
       		barraProgresso += "<td style='background-color:#999999' title='Conferencia: indefinido'>3</td>";
       	}
       	else if (p.getConferencia().equals("indefinido")) {
       		barraProgresso += "<td style='background-color:#FFFF33' title='Conferencia: em andamento'>3</td>";
       	}
       	else if (p.getConferencia().equals("completo")) {
       		barraProgresso += "<td style='background-color:#33CC33' title='Conferencia: completo'>3</td>";
       	}
       	else {
       		barraProgresso += "<td style='background-color:#FF0000' title='Conferencia: incompleto'>3</td>";
       	}
       	
       	//MINUTA
       	if (p.getMinuta().equals("indefinido") && p.getConferencia().equals("indefinido")) {
       		barraProgresso += "<td style='background-color:#999999' title='Minuta: indefinido'>4</td>";
       	}
       	else if (p.getMinuta().equals("indefinido")) {
       		barraProgresso += "<td style='background-color:#FFFF33' title='Minuta: em andamento'>4</td>";
       	}
       	else {
       		barraProgresso += "<td style='background-color:#33CC33' title='Minuta: elaborada'>4</td>";
       	}
       	
       	//LEGALIDADE DOS MATERIAIS
       	if (p.getLegalidadeMateriais().equals("indefinido") && p.getMinuta().equals("indefinido")) {
       		barraProgresso += "<td style='background-color:#999999' title='Legalidade dos materiais: indefinido'>5</td>";
       	}
       	else if (p.getLegalidadeMateriais().equals("indefinido")) {
       		barraProgresso += "<td style='background-color:#FFFF33' title='Legalidade dos materiais: em andamento'>5</td>";
       	}
       	else if (p.getLegalidadeMateriais().equals("legal")) {
       		barraProgresso += "<td style='background-color:#33CC33' title='Legalidade dos materiais: legal'>5</td>";
       	}
       	else {
       		barraProgresso += "<td style='background-color:#FF0000' title='Legalidade dos materiais: ilegal'>5</td>";
       	}
       	
       	//PREGAO
       	if (p.getPregao().equals("indefinido") && p.getLegalidadeMateriais().equals("indefinido")) {
       		barraProgresso += "<td style='background-color:#999999' title='Pregao: indefinido'>6</td>";
       	}
       	else if (p.getPregao().equals("indefinido")) {
       		barraProgresso += "<td style='background-color:#FFFF33' title='Pregao: em andamento'>6</td>";
       	}
       	else if (p.getPregao().equals("informado no SIASE e Comprasnet")) {
       		barraProgresso += "<td style='background-color:#FFFF33' title='Pregao: informado no SIASE e Comprasnet'>6</td>";
       	}
       	else if (p.getPregao().equals("Edital publicado")) {
       		barraProgresso += "<td style='background-color:#FFFF33' title='Pregao: edital publicado'>6</td>";
       	}
       	else if (p.getPregao().equals("Data marcada")) {
       		barraProgresso += "<td style='background-color:#FFFF33' title='Pregao: data marcada'>6</td>";
       	}
       	else {
       		barraProgresso += "<td style='background-color:#33CC33' title='Pregao: realizado'>6</td>";
       	}
       	
       	//LICITACAO
       	if (p.getLicitacao().equals("indefinido") && p.getPregao().equals("indefinido")) {
       		barraProgresso += "<td style='background-color:#999999' title='Licitacao: indefinido'>7</td>";
       	}
       	else if (p.getLicitacao().equals("indefinido")) {
       		barraProgresso += "<td style='background-color:#FFFF33' title='Licitacao: em andamento'>7</td>";
       	}
       	else if (p.getLicitacao().equals("em avaliacao")) {
       		barraProgresso += "<td style='background-color:#FFFF33' title='Licitacao: em avaliacao'>7</td>";
       	}
       	else {
       		barraProgresso += "<td style='background-color:#33CC33' title='Licitacao: concluida'>7</td>";
       	}
       	
       	//ADJUDICACAO
       	if (p.getAdjudicacao().equals("indefinido") && p.getLicitacao().equals("indefinido")) {
       		barraProgresso += "<td style='background-color:#999999' title='Adjudicacao: indefinido'>8</td>";
       	}
       	else if (p.getAdjudicacao().equals("indefinido")) {
       		barraProgresso += "<td style='background-color:#FFFF33' title='Adjudicacao: em andamento'>8</td>";
       	}
       	else {
       		barraProgresso += "<td style='background-color:#33CC33' title='Adjudicacao: termo elaborado'>8</td>";
       	}
       	
       	//HOMOLOGACAO
       	if (p.getHomologacao().equals("indefinido") && p.getAdjudicacao().equals("indefinido")) {
       		barraProgresso += "<td style='background-color:#999999' title='Homologacao: indefinido'>9</td>";
       	}
       	else if (p.getHomologacao().equals("indefinido")) {
       		barraProgresso += "<td style='background-color:#FFFF33' title='Homologacao: em andamento'>9</td>";
       	}
       	else if (p.getHomologacao().equals("homologado")) {
       		barraProgresso += "<td style='background-color:#33CC33' title='Homologacao: homologado'>9</td>";
       	}
       	else {
       		barraProgresso += "<td style='background-color:#FF0000' title='Homologacao: nao homologado'>9</td>";
       	}
       	
       	//PUBLICACAO
       	if (p.getPublicacao().equals("indefinido") && p.getHomologacao().equals("indefinido")) {
       		barraProgresso += "<td style='background-color:#999999' title='Publicacao: indefinido'>10</td>";
       	}
       	else if (p.getPublicacao().equals("indefinido")) {
       		barraProgresso += "<td style='background-color:#FFFF33' title='Publicacao: em andamento'>10</td>";
       	}
       	else {
       		barraProgresso += "<td style='background-color:#33CC33' title='Publicacao: publicado'>10</td>";
       	}
       	
       	//MINUTA DE EMPENHO
       	if (p.getMinutaEmpenho().equals("indefinido") && p.getPublicacao().equals("indefinido")) {
       		barraProgresso += "<td style='background-color:#999999' title='Minuta de empenho: indefinido'>11</td>";
       	}
       	else if (p.getMinutaEmpenho().equals("indefinido")) {
       		barraProgresso += "<td style='background-color:#FFFF33' title='Minuta de empenho: em andamento'>11</td>";
       	}
       	else {
       		barraProgresso += "<td style='background-color:#33CC33' title='Minuta de empenho: elaborada'>11</td>";
       	}
       	
       	//DETALHAMENTO
       	if (p.getDetalhamento().equals("indefinido") && p.getMinutaEmpenho().equals("indefinido")) {
       		barraProgresso += "<td style='background-color:#999999' title='Detalhamento: indefinido'>12</td>";
       	}
       	else if (p.getDetalhamento().equals("indefinido")) {
       		barraProgresso += "<td style='background-color:#FFFF33' title='Detalhamento: em andamento'>12</td>";
       	}
       	else if (p.getDetalhamento().equals("no SEPLAN")) {
       		barraProgresso += "<td style='background-color:#FFFF33' title='Detalhamento: no SEPLAN'>12</td>";
       	}
       	else {
       		barraProgresso += "<td style='background-color:#33CC33' title='Detalhamento: detalhado'>12</td>";
       	}
       	
       	//EMPENHO
       	if (p.getEmpenho().equals("indefinido") && p.getDetalhamento().equals("indefinido")) {
       		barraProgresso += "<td style='background-color:#999999' title='Empenho: indefinido'>13</td>";
       	}
       	else if (p.getEmpenho().equals("indefinido")) {
       		barraProgresso += "<td style='background-color:#FFFF33' title='Empenho: em andamento'>13</td>";
       	}
       	else if (p.getEmpenho().equals("recebido")) {
       		barraProgresso += "<td style='background-color:#FFFF33' title='Empenho: recebido'>13</td>";
       	}
       	else {
       		barraProgresso += "<td style='background-color:#33CC33' title='Empenho: empenhado'>13</td>";
       	}
       	
       	//NOTA DO ALMOXARIFADO
       	if (p.getNotaAlmoxarifado().equals("indefinido") && p.getEmpenho().equals("indefinido")) {
       		barraProgresso += "<td style='background-color:#999999' title='Nota do almoxarifado: indefinido'>14</td>";
       	}
       	else if (p.getNotaAlmoxarifado().equals("indefinido")) {
       		barraProgresso += "<td style='background-color:#FFFF33' title='Nota do almoxarifado: em andamento'>14</td>";
       	}
       	else {
       		barraProgresso += "<td style='background-color:#33CC33' title='Nota do almoxarifado: enviada'>14</td>";
       	}
       	
       	//TOMBAMENTO
       	if (p.getTombamento().equals("indefinido") && p.getNotaAlmoxarifado().equals("indefinido")) {
       		barraProgresso += "<td style='background-color:#999999' title='Tombamento: indefinido'>15</td>";
       	}
       	else if (p.getTombamento().equals("indefinido")) {
       		barraProgresso += "<td style='background-color:#FFFF33' title='Tombamento: em andamento'>15</td>";
       	}
       	else {
       		barraProgresso += "<td style='background-color:#33CC33' title='Tombamento: tombado'>15</td>";
       	}
       	
       	//NOTA DA CONTABILIDADE
       	if (p.getContabilidade().equals("indefinido") && p.getTombamento().equals("indefinido")) {
       		barraProgresso += "<td style='background-color:#999999' title='Nota da contabilidade: indefinido'>16</td>";
       	}
       	else if (p.getContabilidade().equals("indefinido")) {
       		barraProgresso += "<td style='background-color:#FFFF33' title='Nota da contabilidade: em andamento'>16</td>";
       	}
       	else {
       		barraProgresso += "<td style='background-color:#33CC33' title='Nota da contabilidade: enviada'>16</td>";
       	}
       	
       	//LIQUIDACAO
       	if (p.getLiquidacao().equals("indefinido") && p.getContabilidade().equals("indefinido")) {
       		barraProgresso += "<td style='background-color:#999999' title='Liquidacao: indefinido'>17</td>";
       	}
       	else if (p.getLiquidacao().equals("indefinido")) {
       		barraProgresso += "<td style='background-color:#FFFF33' title='Liquidacao: em andamento'>17</td>";
       	}
       	else if (p.getLiquidacao().equals("servico atestado")) {
       		barraProgresso += "<td style='background-color:#FFFF33' title='Liquidacao: servico atestado'>17</td>";
       	}
       	else {
       		barraProgresso += "<td style='background-color:#33CC33' title='Liquidacao: liquidado</td>";
       	}
       	
       	//PAGAMENTO
       	if (p.getPagamento().equals("indefinido") && p.getLiquidacao().equals("indefinido")) {
       		barraProgresso += "<td style='background-color:#999999' title='Pagamento: indefinido'>18</td>";
       	}
       	else if (p.getPagamento().equals("indefinido")) {
       		barraProgresso += "<td style='background-color:#FFFF33' title='Pagamento: em andamento'>18</td>";
       	}
       	else {
       		barraProgresso += "<td style='background-color:#33CC33' title='Pagamento: pago'>18</td>";
       	}
       	
       	barraProgresso += "</tr></table><br>";
       	
       	
       	return new HTML(barraProgresso);
       	
	}
	
	

	private VerticalPanel pedidosPanel() {
		final VerticalPanel vPanel = new VerticalPanel();
		
		vPanel.add(new HTML("Carregando pedidos...<br>"));
		
		RequestBuilder builder2 = new RequestBuilder(RequestBuilder.GET, "/Pedido");
      try {
        Request request = builder2.sendRequest(null, new RequestCallback() {
          

			public void onError(Request request, Throwable exception) {
          }

          public void onResponseReceived(Request request, Response response) {
            if (200 == response.getStatusCode()) {
          	 ListaPedidos lista = JsonUtils.safeEval(response.getText()).cast();
          	 vPanel.clear();
          	vPanel.add(new HTML("Pedidos<br>"));
          	 final String[] listaa = lista.getPedidos();
          	 for (int i = 0; i < listaa.length; i++) {
          		 final RequestBuilder builder3 = new RequestBuilder(RequestBuilder.GET, "/Pedido?numero="+listaa[i]);
          		 vPanel.add(new Button("Pedido "+ listaa[i], new ClickHandler() {
       		        public void onClick(ClickEvent event) {
       		    	RootPanel.get("main_right").clear();
       		    	final DecoratedPopupPanel simplePopup = new DecoratedPopupPanel(true);
       		    	simplePopup.ensureDebugId("cwBasicPopup-simplePopup");
       		    	simplePopup.setWidth("150px");
       		     	simplePopup.setWidget(new HTML("Carregando <br> por favor, espere!"));
       		     	// Reposition the popup relative to the button
       	            Widget source = (Widget) event.getSource();
       	            int left = source.getAbsoluteLeft() + 10;
       	            int top = source.getAbsoluteTop() + 10;
       	            simplePopup.setPopupPosition(left, top);

       	            // Show the popup
       	            simplePopup.show();
       		    	  
       		      try {
       		        Request request = builder3.sendRequest(null, new RequestCallback() {
       		          

       					public void onError(Request request, Throwable exception) {
       		          }

       		          public void onResponseReceived(Request request, Response response) {
       		            if (200 == response.getStatusCode()) {
       		            	simplePopup.hide();
       		            	RootPanel.get("main_right").clear();
       		            	DecoratorPanel decPanel = new DecoratorPanel();
       		            	final FlexTable layout = new FlexTable();
       		            	layout.setCellSpacing(6);
       		            	Pedido pedido = JsonUtils.safeEval(response.getText()).cast();
       		            	Label Nome = new Label();
       		            	Nome.setText(pedido.getDemandante());
       		            	Label numero = new Label();
       		            	numero.setText(pedido.getNumero());
       		            	Label email = new Label();
       		            	email.setText(pedido.getEmail());
       		            	Label local = new Label();
       		            	local.setText(pedido.getLocal());
       		            	Label descricao = new Label();
       		            	descricao.setText(pedido.getDescricao());
       		     		    layout.setWidget(0, 1, numero);
       		     		    layout.setWidget(1, 1, Nome);
       		     		    layout.setWidget(2, 1, email);
       		     		    layout.setWidget(3, 1, local);
       		     		    layout.setWidget(4, 1, descricao);
       		            	decPanel.setWidget(layout);
       		            	RootPanel.get("main_right").add(decPanel);
       		          	 
       		            } else {
       		          	  
       		            }
       		          }
       		        });
       		      } catch (RequestException e) {
       		      }
       		    	  
       		    	  
       		    	  
       		    	  
    		        }
    		      }));
          		
          		 
          	 }
          	 
            } else {
          	  
            }
          }
        });
      } catch (RequestException e) {
      }
		
		
		
		
		
		RootPanel.get("main_left").add(vPanel);
		return vPanel;
	}
	

	
	
	private void cadastraPanel() {
		 
		
					
				VerticalPanel vPanel = new VerticalPanel();
				final TextBox numero = new TextBox();
				HorizontalPanel hnumero = new HorizontalPanel();
				hnumero.add(new HTML("Numero do processo:"));
				hnumero.add(numero);
				final TextBox demandante = new TextBox();
				HorizontalPanel hdemandante = new HorizontalPanel();
				hdemandante.add(new HTML("Nome do demandante:"));
				hdemandante.add(demandante);
				final TextBox data_entrada = new TextBox();
				HorizontalPanel hdata_entrada = new HorizontalPanel();
				hdata_entrada.add(new HTML("Data de entrada:"));
				hdata_entrada.add(data_entrada);
				final TextArea descricao = new TextArea();
				HorizontalPanel hdescricao = new HorizontalPanel();
				hdescricao.add(new HTML("Descricao:"));
				hdescricao.add(descricao);
				final TextBox email_demandante = new TextBox();
				HorizontalPanel hemail = new HorizontalPanel();
				hemail.add(new HTML("Email do demandante:"));
				hemail.add(email_demandante);
				vPanel.add(new HTML("Cadastro de Pedidos<br>"));
				vPanel.add(hnumero);
				vPanel.add(hdemandante);
				vPanel.add(hemail);
				vPanel.add(hdata_entrada);
				vPanel.add(hdescricao);
				vPanel.add(new Button("Enviar", new ClickHandler() {
       		        public void onClick(ClickEvent event) {
       		        	String parameters = "numero="+numero.getText()+
       		        			"&demandante="+demandante.getText()+
       		        			"&data_entrada="+data_entrada.getText()+
       		        			"&descricao="+descricao.getText()+
       		        			"&email_demandante="+email_demandante.getText();
       		        	enviaForm(parameters);}}));
				RootPanel.get("main_bottom").clear();
				RootPanel.get("main_bottom").add(vPanel);
				
				
		
       		     
       		    	  
       		    	  
       		
	}
		
	public void enviaForm(String parameters) {
		 try {
		        
		    	final DialogBox dialogBox = new DialogBox();
		    	dialogBox.setText("Aguarde");
		    	VerticalPanel dialogContents = new VerticalPanel();
		    	dialogBox.setWidget(dialogContents);
		    	dialogBox.setGlassEnabled(true);
		    	dialogBox.center();
		    	dialogBox.setAnimationEnabled(true);  
		    	dialogBox.show();
		    	HTML details = new HTML("Enviando...");
		    	dialogContents.add(details);
		    	dialogContents.setCellHorizontalAlignment(
		    	        details, HasHorizontalAlignment.ALIGN_CENTER);
		    	  
		    	final RequestBuilder builder3 = new RequestBuilder(RequestBuilder.POST, "/CadastraPedido"); 
		    	builder3.setHeader("Content-type", "application/x-www-form-urlencoded");
		    
				Request request = builder3.sendRequest(parameters, new RequestCallback() {
		          

				public void onError(Request request, Throwable exception) {
		          }

		          public void onResponseReceived(Request request, Response response) {
		            if (200 == response.getStatusCode()) {
		            	dialogBox.clear();
		            	dialogBox.add(new HTML("Enviado!"));
		            	dialogBox.hide();
		            	RootPanel.get("main_bottom").clear();
		            	
		          	 
		            } else {
		          	  
		            }
		          }
		        });
		      } catch (RequestException e) {
		      }
	}
	
}




