package br.edu.ufcg.pra.client;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.History;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.LabelBase;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;

import java.util.ArrayList;
import java.util.Date;

class PermissaoDoUsuario extends JavaScriptObject {
	protected PermissaoDoUsuario() {
	}

	// JSNI methods to get stock data.
	public final native String status() /*-{
		return this.status;
	}-*/;

	public final native String[] getPermissoes() /*-{
		return this.permissoes;
	}-*/;

}

class RequisicaoDePedidos extends JavaScriptObject {

	protected RequisicaoDePedidos() {
	}

	// JSNI methods to get stock data.
	public final native String getStatus() /*-{
		return this.status;
	}-*/;

	public final native String getCursor() /*-{
		return this.cursor;
	}-*/;

	public final native Pedido[] getPedidos() /*-{
		return this.pedidos;
	}-*/;

}

class LegalidadeDados extends JavaScriptObject {

	protected LegalidadeDados() {
	}

	public final native String getParecer() /*-{
		return this.parecer;
	}-*/;

	public final native String getDataEnvio() /*-{
		return this.data_envio;
	}-*/;

	public final native String getDataRetorno() /*-{
		return this.data_retorno;
	}-*/;
}

class AutorizacaoDados extends JavaScriptObject {

	protected AutorizacaoDados() {
	}

	public final native String getParecer() /*-{
		return this.parecer;
	}-*/;
}

class CorretudeDados extends JavaScriptObject {

	protected CorretudeDados() {
	}

	public final native String getDescricao() /*-{
		return this.descricao;
	}-*/;

	public final native String getQuantitativo() /*-{
		return this.quantitativo;
	}-*/;

	public final native String getCotacao() /*-{
		return this.cotacao;
	}-*/;

	public final native String getData() /*-{
		return this.data;
	}-*/;
}

class MinutaDados extends JavaScriptObject {

	protected MinutaDados() {
	}

	public final native String[] getParecer() /*-{
		return this.parecer;
	}-*/;

	public final native String[] getDataInicio() /*-{
		return this.data_inicio;
	}-*/;

	public final native String[] getDataEnvio() /*-{
		return this.data_envio;
	}-*/;

	public final native String[] getDataRetorno() /*-{
		return this.data_retorno;
	}-*/;

	public final String getParecer(int i) {
		if (i < getParecer().length && i > -1)
			return getParecer()[i];
		else
			return new String("");
	}

	public final String getDataInicio(int i) {
		if (i < getDataInicio().length && i > -1)
			return getDataInicio()[i];
		else
			return new String("");
	}

	public final String getDataEnvio(int i) {
		if (i < getDataEnvio().length && i > -1)
			return getDataEnvio()[i];
		else
			return new String("");
	}

	public final String getDataRetorno(int i) {
		if (i < getDataRetorno().length && i > -1)
			return getDataRetorno()[i];
		else
			return new String("");
	}

	public final int indiceAtual() {
		return Math.max(Math.max(
				Math.max(getParecer().length, getDataInicio().length),
				getDataEnvio().length), getDataRetorno().length) - 1;
	}

}

class PregaoDados extends JavaScriptObject {

	protected PregaoDados() {
	}

	public final native String[] getParecer() /*-{
		return this.parecer;
	}-*/;

	public final native String[] getData() /*-{
		return this.data;
	}-*/;

	public final native String[] getNumero() /*-{
		return this.numero;
	}-*/;

	public final native String[] getLicitacaoData() /*-{
		return this.licitacao_data;
	}-*/;

	public final native String getIndiceTexto() /*-{
		return this.indice;
	}-*/;

	public final int getIndice() {
		return Integer.parseInt(getIndiceTexto());
	}

	public final String getParecer(int i) {
		if (i < getParecer().length && i > -1)
			return getParecer()[i];
		else
			return new String("");
	}

	public final String getData(int i) {
		if (i < getData().length && i > -1)
			return getData()[i];
		else
			return new String("");
	}

	public final String getNumero(int i) {
		if (i < getNumero().length && i > -1)
			return getNumero()[i];
		else
			return new String("");
	}

	public final String getLicitacaoData(int i) {
		if (i < getLicitacaoData().length && i > -1)
			return getLicitacaoData()[i];
		else
			return new String("");
	}

	public final int indiceAtual() {
		return getIndice();
	}

}

class AdjudicacaoDados extends JavaScriptObject {

	protected AdjudicacaoDados() {
	}

	public final native String getData() /*-{
		return this.data;
	}-*/;
}

class HomologacaoDados extends JavaScriptObject {

	protected HomologacaoDados() {
	}

	public final native String getData() /*-{
		return this.data;
	}-*/;
}

class PublicacaoDados extends JavaScriptObject {

	protected PublicacaoDados() {
	}

	public final native String getData() /*-{
		return this.data;
	}-*/;
}

class DetalhamentoDados extends JavaScriptObject {

	protected DetalhamentoDados() {
	}

	public final native String getParecer() /*-{
		return this.parecer;
	}-*/;

	public final native String getData() /*-{
		return this.data;
	}-*/;
}

class EmpenhoDados extends JavaScriptObject {

	protected EmpenhoDados() {
	}

	public final native String getData() /*-{
		return this.data;
	}-*/;
}

class NotaAlmoxarifadoDados extends JavaScriptObject {

	protected NotaAlmoxarifadoDados() {
	}

	public final native String getData() /*-{
		return this.data;
	}-*/;
}

class PatrimonioDados extends JavaScriptObject {

	protected PatrimonioDados() {
	}

	public final native String getData() /*-{
		return this.data;
	}-*/;
}

class NotaContabilidadeDados extends JavaScriptObject {

	protected NotaContabilidadeDados() {
	}

	public final native String getData() /*-{
		return this.data;
	}-*/;
}

class LiquidacaoDados extends JavaScriptObject {

	protected LiquidacaoDados() {
	}

	public final native String getData() /*-{
		return this.data;
	}-*/;
}

class PagamentoDados extends JavaScriptObject {

	protected PagamentoDados() {
	}

	public final native String getData() /*-{
		return this.data;
	}-*/;
}

class Historico extends JavaScriptObject {

	protected Historico() {
	}

	public final native HistoricoDados getHistorico() /*-{
		return this.historico;
	}-*/;
}

class HistoricoDados extends JavaScriptObject {

	protected HistoricoDados() {
	}

	public final native String[] getData() /*-{
		return this.data;
	}-*/;

	public final native String[] getInfo() /*-{
		return this.info;
	}-*/;

	public final native String[] getUser() /*-{
		return this.user;
	}-*/;

	public final int size() {
		return getUser().length;
	}
}

class Temp {
	public static HistoricoDados temp;

	public Temp(HistoricoDados temp) {
		this.temp = temp;
	}
}

class ExtendedDialogBox extends DialogBox {
	ExtendedDialogBox(boolean autohide) {
		super(autohide);
	}

	@Override
	protected void onPreviewNativeEvent(NativePreviewEvent event) {
		super.onPreviewNativeEvent(event);
		switch (event.getTypeInt()) {
		case Event.ONKEYDOWN:
			if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ESCAPE) {
				hide();
			}
			break;
		}
	}
}

class Pedido extends JavaScriptObject {

	protected Pedido() {
	}

	// JSNI methods to get stock data.
	public final native String getStatus() /*-{
		return this.status;
	}-*/;
	
	public final native String getDemandante() /*-{
		return this.demandante;
	}-*/;

	public final native String getTipoDePedido() /*-{
		return this.tipo_pedido;
	}-*/;

	public final native String getNumero() /*-{
		return this.numero;
	}-*/;

	public final native String getEmail() /*-{
		return this.email_demandante;
	}-*/;

	public final native String getLocal() /*-{
		return this.local;
	}-*/;

	public final native String getData() /*-{
		return this.data_entrada;
	}-*/;

	public final native String getError() /*-{
		return this.error;
	}-*/;

	public final native int[] getValoresDosEstados() /*-{
		return this.estados_valores;
	}-*/;

	public final native String[] getNomesDosEstados() /*-{
		return this.estados_nomes;
	}-*/;
	
	public final native String getLastData() /*-{
		return this.ultima_atualizacao;
	}-*/;

	public final native String[] getLinksDosEstados() /*-{
		return this.estados_links;
	}-*/;

	public final String getDataFormatada() {
		DateTimeFormat format = DateTimeFormat
				.getFormat("yyyy-MM-dd'T'HH:mm:ss");
		DateTimeFormat format2 = DateTimeFormat
				.getFormat("dd/MM/yyyy' 'HH:mm:ss");
		return format2.format(format.parse(getData()));
	}

	public final native String getDescricao() /*-{
		return this.descricao;
	}-*/;

	public final native LegalidadeDados getLegalidade() /*-{
		return this.legalidade;
	}-*/;

	public final native AutorizacaoDados getAutorizacao() /*-{
		return this.autorizacao;
	}-*/;

	public final native CorretudeDados getCorretude() /*-{
		return this.corretude;
	}-*/;

	public final native MinutaDados getMinuta() /*-{
		return this.minuta;
	}-*/;

	public final native PregaoDados getPregao() /*-{
		return this.pregao;
	}-*/;

	public final native AdjudicacaoDados getAdjudicacao() /*-{
		return this.adjudicacao;
	}-*/;

	public final native HomologacaoDados getHomologacao() /*-{
		return this.homologacao;
	}-*/;

	public final native PublicacaoDados getPublicacao() /*-{
		return this.publicacao;
	}-*/;

	public final native DetalhamentoDados getDetalhamento() /*-{
		return this.detalhamento;
	}-*/;

	public final native EmpenhoDados getEmpenho() /*-{
		return this.empenho;
	}-*/;

	public final native NotaAlmoxarifadoDados getNotaAlmoxarifado() /*-{
		return this.nota_almoxarifado;
	}-*/;

	public final native PatrimonioDados getPatrimonio() /*-{
		return this.patrimonio;
	}-*/;

	public final native NotaContabilidadeDados getNotaContabilidade() /*-{
		return this.nota_contabilidade;
	}-*/;

	public final native LiquidacaoDados getLiquidacao() /*-{
		return this.liquidacao;
	}-*/;

	public final native PagamentoDados getPagamento() /*-{
		return this.pagamento;
	}-*/;

	public final void getHistorico(final SimplePanel content) {

		RequestBuilder builder5 = new RequestBuilder(RequestBuilder.GET,
				"gethistorico?q=" + getNumero());

		try {
			Request request = builder5.sendRequest(null, new RequestCallback() {

				@Override
				public void onResponseReceived(Request request,
						Response response) {
					if (200 == response.getStatusCode()) {
						Historico historico = JsonUtils.safeEval(
								response.getText()).cast();
						HistoricoDados dados = historico.getHistorico();
						String historicoListagem = "";
						for (int i = 0; i < dados.size(); i++) {
							String data = dados.getData()[i];
							DateTimeFormat format1 = DateTimeFormat
									.getFormat("yyyy-MM-dd'T'HH:mm:ss");
							DateTimeFormat format2 = DateTimeFormat
									.getFormat("dd/MM/yyyy' 'HH:mm:ss");
							historicoListagem += format2.format(format1
									.parse(data.substring(0, 19)))
									+ " "
									+ dados.getInfo()[i]
									+ " por "
									+ dados.getUser()[i] + "<br>";

						}
						content.clear();
						content.add(new HTML(historicoListagem));
					}

				}

				@Override
				public void onError(Request request, Throwable exception) {
					// TODO Auto-generated method stub

				}

			});
		} catch (RequestException e) {

		}
	}

}

class Pedido2 extends JavaScriptObject {

	protected Pedido2() {
	}

	// JSNI methods to get stock data.
	public final native String getDemandante() /*-{
		return this.demandante;
	}-*/;

	public final native String getNumero() /*-{
		return this.numero;
	}-*/;

	public final native String getDescricao() /*-{
		return this.descricao;
	}-*/;

	public final native String getData() /*-{
		return this.data_entrada;
	}-*/;

}

class User extends JavaScriptObject {

	protected User() {
	}

	// JSNI methods to get stock data.
	public final native String getEmail() /*-{
		return this.user_email;
	}-*/;

	public final native String getNickname() /*-{
		return this.user_nickname;
	}-*/;

	public final native String getId() /*-{
		return this.user_user_id;
	}-*/;

	public final native String getStatus() /*-{
		return this.status;
	}-*/;

	public final native String getUrl() /*-{
		return this.url;
	}-*/;
}

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Sistema_PRA implements EntryPoint {

	protected String url = "";
	protected String status = "";

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			public void onValueChange(ValueChangeEvent<String> event) {
				String token = event.getValue();

				try {
					if (token.substring(0,7).equals("/pedido")) {

						String numeroPedido = token.substring(8);
						Window.setTitle("SAPO@UFCG - Detalhes do Pedido "
								+ numeroPedido);
						final RequestBuilder builder3 = new RequestBuilder(
								RequestBuilder.GET, "/getpedido?q="
										+ numeroPedido);
						try {
							Request request = builder3.sendRequest(null,
									new RequestCallback() {

										public void onError(Request request,
												Throwable exception) {
										}

										public void onResponseReceived(
												Request request,
												Response response) {
										
											if (200 == response.getStatusCode()) {
												boolean c = true;
												// User user =
												// JsonUtils.safeEval(response.getText()).cast();
												Pedido pedido = JsonUtils
														.safeEval(
																response.getText())
														.cast();
												if(!(pedido.getStatus().equals("Connected"))){
													c = false;
												}
												if (pedido.getError().equals(
														"True")) {
													final ExtendedDialogBox aviso = new ExtendedDialogBox(
															true);
													aviso.center();
													aviso.add(new Label(
															"Pedido nao encontrado."));
													aviso.setGlassEnabled(true);
													aviso.setAnimationEnabled(true);
													aviso.show();
												} else
													exibeTelaCompleta(pedido,c);
											} else {

											}
										}
									});
						} catch (RequestException e) {
						}

					} else if (token.trim().equals("cadastro")) {
						cadastraPanel();
						Window.setTitle("SAPO@UFCG - Cadastro de novo pedido");
					} else if (token.substring(0, 8).equals("pesquisa")) {
						String tokenBusca = token.substring(8);
						Window.setTitle("SAPO@UFCG - Pesquisando por: "
								+ tokenBusca);
						geraLista(tokenBusca, "");
					} else if (token.trim().equals("") || token == null) {
						Window.setTitle("SAPO@UFCG - Início");
						index();
					}

				} catch (IndexOutOfBoundsException e) {

				}
			}
		});
		History.fireCurrentHistoryState();
	}

	private void index() {

		FlowPanel hPanel = new FlowPanel();

		// hPanel.setStyleName("input-append");
		final TextBox campoPesquisa = new TextBox();
		campoPesquisa.setSize("450px", "30px");
		campoPesquisa.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					History.newItem("pesquisa" + campoPesquisa.getText());
					// geraLista(campoPesquisa.getText(), "");

				}
			}
		});
		// campoPesquisa.setStyleName("span2");
		// DOM.setElementAttribute(campoPesquisa.getElement(), "id",
		// "appendedInputButton");
		Button botaoPesquisa = new Button(
				"<img src=\"images/search.png\"></a> Pesquisar",
				new ClickHandler() {
					public void onClick(ClickEvent event) {
						final SimplePanel buscando = new SimplePanel();
						buscando.addStyleName("buscando");
						buscando.add(new HTML(
								"<img src=\"images/415.gif\" width=\"4%\"></a>"));
						RootPanel.get().add(buscando);
						History.newItem("pesquisa" + campoPesquisa.getText());
						// geraLista(campoPesquisa.getText(), "");
						Timer timer = new Timer() {

							@Override
							public void run() {
								buscando.removeFromParent();
							}
						};
						timer.schedule(500);
					}
				});
		// botaoPesquisa.setStyleName("btn");
		botaoPesquisa.setHeight("30px");
		hPanel.add(campoPesquisa);
		hPanel.add(botaoPesquisa);

		RootPanel.get("main_top").clear();
		RootPanel.get("main_top").add(hPanel);

		geraLista("", "");

		HTML carregando = new HTML(
				"<div id=\"carregando\"><center>Carregando...</center></div>");
		RootPanel.get().add(carregando);
		final Label nameField = new Label();
		nameField.setText("");

		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET,
				"/LoginHandler");
		// RequestBuilder builder = new RequestBuilder(RequestBuilder.GET,
		// "/teste.json");

		try {
			Request request = builder.sendRequest(null, new RequestCallback() {

				public void onError(Request request, Throwable exception) {
				}

				public void onResponseReceived(Request request,
						Response response) {
					if (200 == response.getStatusCode()) {
						Anchor sendAnchor;
						ClickHandler clickAnchor = new ClickHandler() {
							public void onClick(ClickEvent event) {
								Window.Location.replace(url);
							}
						};
						User user = JsonUtils.safeEval(response.getText())
								.cast();
						status = user.getStatus();
						if (status.equals("Connected")) {
							sendAnchor = new Anchor("Logout");
							nameField.setText(user.getEmail());
							url = user.getUrl();
							HorizontalPanel cadastrarPanel = new HorizontalPanel();
							Button cadastrarButton = new Button(
									"Cadastrar pedido", new ClickHandler() {
										public void onClick(ClickEvent event) {
											RootPanel.get("main_bottom")
													.clear();
											RootPanel.get("main_bottom_in")
													.clear();
											History.newItem("cadastro");
											// cadastraPanel();
										}
									});
							cadastrarButton.setSize("140px", "30px");
							cadastrarPanel.add(cadastrarButton);
							RootPanel.get("main_cadastrar").clear();
							RootPanel.get("main_cadastrar").add(cadastrarPanel);
						} else {
							url = user.getUrl();
							sendAnchor = new Anchor("Login");
						}
						sendAnchor.addClickHandler(clickAnchor);
						RootPanel.get("user_on_top_bar").clear();
						RootPanel.get("user_on_top_bar").add(nameField);
						RootPanel.get("user_on_top_bar").add(sendAnchor);

					} else {

					}

				}
			});
		} catch (RequestException e) {
		}

		RequestBuilder builder6 = new RequestBuilder(RequestBuilder.GET,
				"/Permissoes");

		try {
			Request request = builder6.sendRequest(null, new RequestCallback() {

				private String[] listaa;

				public void onError(Request request, Throwable exception) {
				}

				public void onResponseReceived(Request request,
						Response response) {
					if (200 == response.getStatusCode()) {
						PermissaoDoUsuario lista = JsonUtils.safeEval(
								response.getText()).cast();
						listaa = lista.getPermissoes();
						HorizontalPanel hPanel = new HorizontalPanel();
						final String[] listaa = lista.getPermissoes();
						/*
						 * for (int i = 0; i < listaa.length; i++) {
						 * if(listaa[i]
						 * .equals("acompanhar_qualquer")||listaa[i].
						 * equals("acompanhar_proprios")){ hPanel.add(new
						 * Button("Pedidos", new ClickHandler() { public void
						 * onClick(ClickEvent event) {
						 * RootPanel.get("main_right").clear();
						 * RootPanel.get("main_left").clear(); pedidosPanel();
						 * }})); } else if(listaa[i].equals("criacao")) {
						 * hPanel.add(new Button("Cadastrar pedido", new
						 * ClickHandler() { public void onClick(ClickEvent
						 * event) { RootPanel.get("main_right").clear();
						 * RootPanel.get("main_left").clear(); cadastraPanel();
						 * }}));
						 * 
						 * 
						 * } else hPanel.add(new Button(listaa[i]));
						 * 
						 * RootPanel.get("top_bar").add(hPanel); }
						 */

					} else {

					}
				}
			});
		} catch (RequestException e) {
		}
		RootPanel.get().remove(carregando);
	}

	private void geraLista(String search, String cursor) {
		RequestBuilder builder5;
		if (cursor.equals(""))
			builder5 = new RequestBuilder(RequestBuilder.GET, "searchpedido?q="
					+ search);
		else
			builder5 = new RequestBuilder(RequestBuilder.GET, "searchpedido?q="
					+ search + "&cursor=" + cursor);
		try {
			Request request = builder5.sendRequest(null, new RequestCallback() {

				private String[] listaa;

				public void onError(Request request, Throwable exception) {
				}

				public void onResponseReceived(Request request,
						Response response) {
					if (200 == response.getStatusCode()) {
						RequisicaoDePedidos lista = JsonUtils.safeEval(
								response.getText()).cast();
						boolean connected = lista.getStatus().equals(
								"Connected") ? true : false;
						Pedido[] cada = lista.getPedidos();
						final ArrayList<Pedido> listaa = new ArrayList<Pedido>();
						for (Pedido pedido : cada)
							listaa.add(pedido);

						CriaExibeTable(listaa, connected, lista.getCursor());

					} else {

					}
				}
			});
		} catch (RequestException e) {
		}
	}

	private void CriaExibeTable(final ArrayList<Pedido> listaa,
			final boolean connected, final String cursor) {

		// Create a CellTable.
		final CellTable<Pedido> table = new CellTable<Pedido>();
		// table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		RootPanel.get("main_bottom").clear();
		RootPanel.get("main_bottom_in").clear();
		// Add a text column to show the name.
		TextColumn<Pedido> nameColumn = new TextColumn<Pedido>() {

			@Override
			public String getValue(Pedido object) {
				// TODO Auto-generated method stub
				return object.getDemandante();
			}
		};
		table.addColumn(nameColumn, "Nome do demandante");

		Column<Pedido, SafeHtml> barraColumn = new Column<Pedido, SafeHtml>(
				new SafeHtmlCell()) {
			@Override
			public SafeHtml getValue(Pedido p) {
				SafeHtmlBuilder bd = new SafeHtmlBuilder();
				bd.appendHtmlConstant(new MontadorPequeno().monta(p).getHTML()); // "<table border=\"1\"><tr><td>Jill</td><td>Smith</td><td>50</td></tr></table>"
				return bd.toSafeHtml();
			}
		};
		barraColumn.setHorizontalAlignment(HasAlignment.ALIGN_RIGHT);

		table.addColumn(barraColumn, "");

		// Add a text column to show the address.
		TextColumn<Pedido> addressColumn = new TextColumn<Pedido>() {
			@Override
			public String getValue(Pedido object) {
				return object.getNumero();
			}
		};
		table.addColumn(addressColumn, "Número");

		// Add a text column to show the address.
		TextColumn<Pedido> descricaoColumn = new TextColumn<Pedido>() {
			@Override
			public String getValue(Pedido object) {
				return object.getDescricao().substring(0, 20);
			}
		};
		table.addColumn(descricaoColumn, "Descrição");

		// Add a text column to show the address.
		TextColumn<Pedido> dataColumn = new TextColumn<Pedido>() {
			@Override
			public String getValue(Pedido object) {
				DateTimeFormat format = DateTimeFormat
						.getFormat("yyyy-MM-dd'T'HH:mm:ss");
				DateTimeFormat format2 = DateTimeFormat.getFormat("dd/MM/yyyy");
				return format2.format(format.parse(object.getData()));
			}
		};
		table.addColumn(dataColumn, "Data de criação");
		
		// Add a text column to show the address.
		TextColumn<Pedido> atualizColumn = new TextColumn<Pedido>() {
			@Override
			public String getValue(Pedido object) {
				DateTimeFormat format = DateTimeFormat
						.getFormat("yyyy-MM-dd'T'HH:mm:ss");
				Date last = format.parse(object.getLastData());
				Date now = new Date();
				long diff = now.getTime() - last.getTime();
				String tempoResposta = "";
				diff /= 1000; //transforma em segundos
				if(diff < 60) {
					tempoResposta = Double.toString(diff) + " s";
				} else {
					diff /= 60; //transforma em minutos
					if(diff < 60) {
						tempoResposta = Double.toString(diff) + " min";
					} else {
						diff /= 60; //transforma em horas
						if(diff < 24) {
							tempoResposta = Double.toString(diff) + " h";
						} else {
							diff /= 24; //transforma em dias
							if(diff < 30) {
								String plural = diff == 1? "": "s";
								if (plural.equals(""))
									tempoResposta="ontem";
								else
									tempoResposta = Double.toString(diff) + " dia" + plural;
							} else {
								diff /= 30; //transforma em meses
								if(diff < 12) {
									String plural = diff == 1? "": "es";
									tempoResposta = Double.toString(diff) + " mes" + plural;
								} else {
									diff /= 12; //transforma em ano
									String plural = diff == 1? "": "s";
									tempoResposta = Double.toString(diff) + " ano" + plural;
									
								}
							}
						}
					}
				}
				return !tempoResposta.equals("ontem")?("há " + tempoResposta):tempoResposta;
			}
		};
		
		atualizColumn.setHorizontalAlignment(HasAlignment.ALIGN_RIGHT);
		table.addColumn(atualizColumn, "Atualização");
		table.setColumnWidth(atualizColumn, "50px");
		// ProgressBar exampleBar1 = new ProgressBar(30, 100, 30);
		final NoSelectionModel<Pedido> selectionModel = new NoSelectionModel<Pedido>();
		// Add a selection model to handle user selection.
		// final SingleSelectionModel<Pedido> selectionModel = new
		// SingleSelectionModel<Pedido>();
		table.setSelectionModel(selectionModel);
		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
					public void onSelectionChange(SelectionChangeEvent event) {
						Pedido selected = selectionModel
								.getLastSelectedObject();
						if (selected != null) {
							if (!connected)
								exibeDialogBox(selected);
							else
								History.newItem("/pedido/" + selected.getNumero());
							// exibeTelaCompleta(selected);

						}
					}

					private void exibeDialogBox(final Pedido selected) {

						RequestBuilder builder6 = new RequestBuilder(
								RequestBuilder.GET, "getpedido?q="
										+ selected.getNumero());
						try {
							Request request = builder6.sendRequest(null,
									new RequestCallback() {

										public void onError(Request request,
												Throwable exception) {
										}

										public void onResponseReceived(
												Request request,
												Response response) {
											if (200 == response.getStatusCode()) {
												Pedido todo = JsonUtils
														.safeEval(
																response.getText())
														.cast();
												// Window.alert("You selected: Pedido "
												// + selected.getNumero());
												Barrinha barra = new Barrinha(
														todo.getNumero());
												barra.setMontador(new MontadorPadraoSemNomes());
												// Create the dialog box
												final ExtendedDialogBox dialogBox = new ExtendedDialogBox(
														true);
												dialogBox.center();
												dialogBox
														.setText("Detalhes do pedido");
												// Create a table to layout the
												// content
												VerticalPanel dialogContents = new VerticalPanel();
												dialogContents.setSpacing(4);
												dialogBox
														.setWidget(dialogContents);
												// Add some text to the top of
												// the dialog
												dialogContents.add(barra);
												dialogContents.add(new Label(
														"Número do pedido: "
																+ todo.getNumero()));
												dialogContents.add(new Label(
														"Tipo de pedido: "
																+ todo.getTipoDePedido()));
												dialogContents.add(new Label(
														"Nome do demandante: "
																+ todo.getDemandante()));
												dialogContents.add(new Label(
														"Email do demandante: "
																+ todo.getEmail()));
												dialogContents.add(new Label(
														"Data de entrada: "
																+ todo.getDataFormatada()));
												dialogContents.add(new Label(
														"Local atual: "
																+ todo.getLocal()));
												dialogContents.add(new Label(
														"Descrição do pedido: "
																+ todo.getDescricao()));
												dialogContents
														.setCellHorizontalAlignment(
																barra,
																HasHorizontalAlignment.ALIGN_CENTER);

												dialogBox.setGlassEnabled(true);
												dialogBox
														.setAnimationEnabled(true);
												dialogBox.show();

											} else {

											}
										}

									});
						} catch (RequestException e) {
						}

					}
				});

		table.setWidth("100%");
		// Set the total row count. This isn't strictly necessary, but it
		// affects
		// paging calculations, so its good habit to keep the row count up to
		// date.
		table.setRowCount(listaa.size(), true);

		// Push the data into the widget.
		table.setRowData(0, listaa);

		table.setPageSize(listaa.size());
		// table.setVisibleRange(0, listaa.size());
		// Add it to the root panel.

		RootPanel.get("main_bottom").add(new HTML("<br><br>"));
		RootPanel.get("main_bottom").add(table);
		// RootPanel.get("main_bottom").add(exampleBar1);

		HTML sobra = new HTML("<br><br><br>");
		final MaisButton mais = new MaisButton(cursor, listaa, table, sobra);
		RootPanel.get("main_bottom").add(mais);

	}

	/*
	 * private HTML barrinha(Pedido p){
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * String barraProgresso = "<table border='0' bordecolor='#000000' " +
	 * "style='background-color:#999999' width='100%' " +
	 * "cellpadding='10' cellspacing='1'><tr>";
	 * 
	 * //LEGALIDADE if (p.getLegalidade().equals("indefinido")) { barraProgresso
	 * +=
	 * "<td style='background-color:#FFFF33' title='Legalidade: em andamento'>1</td>"
	 * ; } else if (p.getLegalidade().equals("legal")) { barraProgresso +=
	 * "<td style='background-color:#33CC33' title='Legalidade: legal'>1</td>";
	 * } else { barraProgresso +=
	 * "<td style='background-color:#FF0000' title='Legalidade: ilegal'>1</td>";
	 * }
	 * 
	 * //AUTORIZACAO if (p.getAutorizacao().equals("indefinido") &&
	 * p.getLegalidade().equals("indefinido")) { barraProgresso +=
	 * "<td style='background-color:#999999' title='Autorizacao: indefinido'>2</td>"
	 * ; } else if (p.getAutorizacao().equals("indefinido")) { barraProgresso +=
	 * "<td style='background-color:#FFFF33' title='Autorizacao: em andamento'>2</td>"
	 * ; } else if (p.getAutorizacao().equals("autorizado")) { barraProgresso +=
	 * "<td style='background-color:#33CC33' title='Autorizacao: autorizado'>2</td>"
	 * ; } else { barraProgresso +=
	 * "<td style='background-color:#FF0000' title='Autorizacao: nao autorizado'>2</td>"
	 * ; }
	 * 
	 * //CONFERENCIA if (p.getConferencia().equals("indefinido") &&
	 * p.getAutorizacao().equals("indefinido")) { barraProgresso +=
	 * "<td style='background-color:#999999' title='Conferencia: indefinido'>3</td>"
	 * ; } else if (p.getConferencia().equals("indefinido")) { barraProgresso +=
	 * "<td style='background-color:#FFFF33' title='Conferencia: em andamento'>3</td>"
	 * ; } else if (p.getConferencia().equals("completo")) { barraProgresso +=
	 * "<td style='background-color:#33CC33' title='Conferencia: completo'>3</td>"
	 * ; } else { barraProgresso +=
	 * "<td style='background-color:#FF0000' title='Conferencia: incompleto'>3</td>"
	 * ; }
	 * 
	 * //MINUTA if (p.getMinuta().equals("indefinido") &&
	 * p.getConferencia().equals("indefinido")) { barraProgresso +=
	 * "<td style='background-color:#999999' title='Minuta: indefinido'>4</td>";
	 * } else if (p.getMinuta().equals("indefinido")) { barraProgresso +=
	 * "<td style='background-color:#FFFF33' title='Minuta: em andamento'>4</td>"
	 * ; } else { barraProgresso +=
	 * "<td style='background-color:#33CC33' title='Minuta: elaborada'>4</td>";
	 * }
	 * 
	 * //LEGALIDADE DOS MATERIAIS if
	 * (p.getLegalidadeMateriais().equals("indefinido") &&
	 * p.getMinuta().equals("indefinido")) { barraProgresso +=
	 * "<td style='background-color:#999999' title='Legalidade dos materiais: indefinido'>5</td>"
	 * ; } else if (p.getLegalidadeMateriais().equals("indefinido")) {
	 * barraProgresso +=
	 * "<td style='background-color:#FFFF33' title='Legalidade dos materiais: em andamento'>5</td>"
	 * ; } else if (p.getLegalidadeMateriais().equals("legal")) { barraProgresso
	 * +=
	 * "<td style='background-color:#33CC33' title='Legalidade dos materiais: legal'>5</td>"
	 * ; } else { barraProgresso +=
	 * "<td style='background-color:#FF0000' title='Legalidade dos materiais: ilegal'>5</td>"
	 * ; }
	 * 
	 * //PREGAO if (p.getPregao().equals("indefinido") &&
	 * p.getLegalidadeMateriais().equals("indefinido")) { barraProgresso +=
	 * "<td style='background-color:#999999' title='Pregao: indefinido'>6</td>";
	 * } else if (p.getPregao().equals("indefinido")) { barraProgresso +=
	 * "<td style='background-color:#FFFF33' title='Pregao: em andamento'>6</td>"
	 * ; } else if (p.getPregao().equals("informado no SIASE e Comprasnet")) {
	 * barraProgresso +=
	 * "<td style='background-color:#FFFF33' title='Pregao: informado no SIASE e Comprasnet'>6</td>"
	 * ; } else if (p.getPregao().equals("Edital publicado")) { barraProgresso
	 * +=
	 * "<td style='background-color:#FFFF33' title='Pregao: edital publicado'>6</td>"
	 * ; } else if (p.getPregao().equals("Data marcada")) { barraProgresso +=
	 * "<td style='background-color:#FFFF33' title='Pregao: data marcada'>6</td>"
	 * ; } else { barraProgresso +=
	 * "<td style='background-color:#33CC33' title='Pregao: realizado'>6</td>";
	 * }
	 * 
	 * //LICITACAO if (p.getLicitacao().equals("indefinido") &&
	 * p.getPregao().equals("indefinido")) { barraProgresso +=
	 * "<td style='background-color:#999999' title='Licitacao: indefinido'>7</td>"
	 * ; } else if (p.getLicitacao().equals("indefinido")) { barraProgresso +=
	 * "<td style='background-color:#FFFF33' title='Licitacao: em andamento'>7</td>"
	 * ; } else if (p.getLicitacao().equals("em avaliacao")) { barraProgresso +=
	 * "<td style='background-color:#FFFF33' title='Licitacao: em avaliacao'>7</td>"
	 * ; } else { barraProgresso +=
	 * "<td style='background-color:#33CC33' title='Licitacao: concluida'>7</td>"
	 * ; }
	 * 
	 * //ADJUDICACAO if (p.getAdjudicacao().equals("indefinido") &&
	 * p.getLicitacao().equals("indefinido")) { barraProgresso +=
	 * "<td style='background-color:#999999' title='Adjudicacao: indefinido'>8</td>"
	 * ; } else if (p.getAdjudicacao().equals("indefinido")) { barraProgresso +=
	 * "<td style='background-color:#FFFF33' title='Adjudicacao: em andamento'>8</td>"
	 * ; } else { barraProgresso +=
	 * "<td style='background-color:#33CC33' title='Adjudicacao: termo elaborado'>8</td>"
	 * ; }
	 * 
	 * //HOMOLOGACAO if (p.getHomologacao().equals("indefinido") &&
	 * p.getAdjudicacao().equals("indefinido")) { barraProgresso +=
	 * "<td style='background-color:#999999' title='Homologacao: indefinido'>9</td>"
	 * ; } else if (p.getHomologacao().equals("indefinido")) { barraProgresso +=
	 * "<td style='background-color:#FFFF33' title='Homologacao: em andamento'>9</td>"
	 * ; } else if (p.getHomologacao().equals("homologado")) { barraProgresso +=
	 * "<td style='background-color:#33CC33' title='Homologacao: homologado'>9</td>"
	 * ; } else { barraProgresso +=
	 * "<td style='background-color:#FF0000' title='Homologacao: nao homologado'>9</td>"
	 * ; }
	 * 
	 * //PUBLICACAO if (p.getPublicacao().equals("indefinido") &&
	 * p.getHomologacao().equals("indefinido")) { barraProgresso +=
	 * "<td style='background-color:#999999' title='Publicacao: indefinido'>10</td>"
	 * ; } else if (p.getPublicacao().equals("indefinido")) { barraProgresso +=
	 * "<td style='background-color:#FFFF33' title='Publicacao: em andamento'>10</td>"
	 * ; } else { barraProgresso +=
	 * "<td style='background-color:#33CC33' title='Publicacao: publicado'>10</td>"
	 * ; }
	 * 
	 * //MINUTA DE EMPENHO if (p.getMinutaEmpenho().equals("indefinido") &&
	 * p.getPublicacao().equals("indefinido")) { barraProgresso +=
	 * "<td style='background-color:#999999' title='Minuta de empenho: indefinido'>11</td>"
	 * ; } else if (p.getMinutaEmpenho().equals("indefinido")) { barraProgresso
	 * +=
	 * "<td style='background-color:#FFFF33' title='Minuta de empenho: em andamento'>11</td>"
	 * ; } else { barraProgresso +=
	 * "<td style='background-color:#33CC33' title='Minuta de empenho: elaborada'>11</td>"
	 * ; }
	 * 
	 * //DETALHAMENTO if (p.getDetalhamento().equals("indefinido") &&
	 * p.getMinutaEmpenho().equals("indefinido")) { barraProgresso +=
	 * "<td style='background-color:#999999' title='Detalhamento: indefinido'>12</td>"
	 * ; } else if (p.getDetalhamento().equals("indefinido")) { barraProgresso
	 * +=
	 * "<td style='background-color:#FFFF33' title='Detalhamento: em andamento'>12</td>"
	 * ; } else if (p.getDetalhamento().equals("no SEPLAN")) { barraProgresso +=
	 * "<td style='background-color:#FFFF33' title='Detalhamento: no SEPLAN'>12</td>"
	 * ; } else { barraProgresso +=
	 * "<td style='background-color:#33CC33' title='Detalhamento: detalhado'>12</td>"
	 * ; }
	 * 
	 * //EMPENHO if (p.getEmpenho().equals("indefinido") &&
	 * p.getDetalhamento().equals("indefinido")) { barraProgresso +=
	 * "<td style='background-color:#999999' title='Empenho: indefinido'>13</td>"
	 * ; } else if (p.getEmpenho().equals("indefinido")) { barraProgresso +=
	 * "<td style='background-color:#FFFF33' title='Empenho: em andamento'>13</td>"
	 * ; } else if (p.getEmpenho().equals("recebido")) { barraProgresso +=
	 * "<td style='background-color:#FFFF33' title='Empenho: recebido'>13</td>";
	 * } else { barraProgresso +=
	 * "<td style='background-color:#33CC33' title='Empenho: empenhado'>13</td>"
	 * ; }
	 * 
	 * //NOTA DO ALMOXARIFADO if (p.getNotaAlmoxarifado().equals("indefinido")
	 * && p.getEmpenho().equals("indefinido")) { barraProgresso +=
	 * "<td style='background-color:#999999' title='Nota do almoxarifado: indefinido'>14</td>"
	 * ; } else if (p.getNotaAlmoxarifado().equals("indefinido")) {
	 * barraProgresso +=
	 * "<td style='background-color:#FFFF33' title='Nota do almoxarifado: em andamento'>14</td>"
	 * ; } else { barraProgresso +=
	 * "<td style='background-color:#33CC33' title='Nota do almoxarifado: enviada'>14</td>"
	 * ; }
	 * 
	 * //TOMBAMENTO if (p.getTombamento().equals("indefinido") &&
	 * p.getNotaAlmoxarifado().equals("indefinido")) { barraProgresso +=
	 * "<td style='background-color:#999999' title='Tombamento: indefinido'>15</td>"
	 * ; } else if (p.getTombamento().equals("indefinido")) { barraProgresso +=
	 * "<td style='background-color:#FFFF33' title='Tombamento: em andamento'>15</td>"
	 * ; } else { barraProgresso +=
	 * "<td style='background-color:#33CC33' title='Tombamento: tombado'>15</td>"
	 * ; }
	 * 
	 * //NOTA DA CONTABILIDADE if (p.getContabilidade().equals("indefinido") &&
	 * p.getTombamento().equals("indefinido")) { barraProgresso +=
	 * "<td style='background-color:#999999' title='Nota da contabilidade: indefinido'>16</td>"
	 * ; } else if (p.getContabilidade().equals("indefinido")) { barraProgresso
	 * +=
	 * "<td style='background-color:#FFFF33' title='Nota da contabilidade: em andamento'>16</td>"
	 * ; } else { barraProgresso +=
	 * "<td style='background-color:#33CC33' title='Nota da contabilidade: enviada'>16</td>"
	 * ; }
	 * 
	 * //LIQUIDACAO if (p.getLiquidacao().equals("indefinido") &&
	 * p.getContabilidade().equals("indefinido")) { barraProgresso +=
	 * "<td style='background-color:#999999' title='Liquidacao: indefinido'>17</td>"
	 * ; } else if (p.getLiquidacao().equals("indefinido")) { barraProgresso +=
	 * "<td style='background-color:#FFFF33' title='Liquidacao: em andamento'>17</td>"
	 * ; } else if (p.getLiquidacao().equals("servico atestado")) {
	 * barraProgresso +=
	 * "<td style='background-color:#FFFF33' title='Liquidacao: servico atestado'>17</td>"
	 * ; } else { barraProgresso +=
	 * "<td style='background-color:#33CC33' title='Liquidacao: liquidado</td>";
	 * }
	 * 
	 * //PAGAMENTO if (p.getPagamento().equals("indefinido") &&
	 * p.getLiquidacao().equals("indefinido")) { barraProgresso +=
	 * "<td style='background-color:#999999' title='Pagamento: indefinido'>18</td>"
	 * ; } else if (p.getPagamento().equals("indefinido")) { barraProgresso +=
	 * "<td style='background-color:#FFFF33' title='Pagamento: em andamento'>18</td>"
	 * ; } else { barraProgresso +=
	 * "<td style='background-color:#33CC33' title='Pagamento: pago'>18</td>"; }
	 * 
	 * barraProgresso += "</tr></table><br>";
	 * 
	 * 
	 * return new HTML(barraProgresso);
	 * 
	 * }
	 */

	private void cadastraPanel() {

		RootPanel.get("main_cadastrar").clear();
		SimplePanel folha = new SimplePanel();
		folha.setStyleName("folha");
		VerticalPanel pedacos = new VerticalPanel();
		pedacos.setSpacing(10);
		folha.add(pedacos);
		RootPanel.get("main_bottom_in").clear();
		RootPanel.get("main_bottom_in").add(folha);
		SubFolhaPanel subfolha = new SubFolhaPanel("Cadastro de Pedidos");
		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setStylePrimaryName("cadastroFolha");
		subfolha.add(vPanel);
		pedacos.add(subfolha);
		final TextBox numero = new TextBox();
		numero.setWidth("350");
		HTML space = new HTML();
		space.setWidth("50");
		HorizontalPanel hnumero = new HorizontalPanel();
		hnumero.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		hnumero.add(space);
		hnumero.add(new HTML("<p>Número do processo:*</p>"));
		hnumero.add(numero);
		HorizontalPanel htipo = new HorizontalPanel();
		htipo.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		htipo.add(space);
		htipo.add(new HTML("<p>Tipo do pedido:*</p>"));
		final HorizontalPanel tipo = new HorizontalPanel();
		htipo.add(tipo);
		final RadioButton radioPregao = new RadioButton("tipo_pedido",
				"Pregão Eletrônico");
		final RadioButton radioDispensa = new RadioButton("tipo_pedido",
				"Dispensa");
		final RadioButton radioInexi = new RadioButton("tipo_pedido",
				"Inexigibilidade");
		final RadioButton radioSessao = new RadioButton("tipo_pedido",
				"Sessão Pública");
		tipo.add(radioPregao);
		tipo.add(radioDispensa);
		tipo.add(radioInexi);
		tipo.add(radioSessao);
		tipo.setWidth("350");
		final TextBox demandante = new TextBox();
		demandante.setWidth("350");
		HorizontalPanel hdemandante = new HorizontalPanel();
		hdemandante.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		hdemandante.add(space);
		hdemandante.add(new HTML("<p>Nome do demandante:* </p>"));
		hdemandante.add(demandante);
		final DateBox data_entrada = new DateBox();
		data_entrada.setWidth("350");
		HorizontalPanel hdata_entrada = new HorizontalPanel();
		hdata_entrada
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		hdata_entrada.add(space);
		hdata_entrada.add(new HTML("<p>Data de entrada:* </p>"));
		hdata_entrada.add(data_entrada);
		final TextArea descricao = new TextArea();
		descricao.setWidth("350");
		HorizontalPanel hdescricao = new HorizontalPanel();
		hdescricao.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		hdescricao.add(space);
		hdescricao.add(new HTML("<p>Descrição:* </p>"));
		hdescricao.add(descricao);
		final TextBox email_demandante = new TextBox();
		email_demandante.setWidth("350");
		HorizontalPanel hemail = new HorizontalPanel();
		hemail.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		hemail.add(space);
		hemail.add(new HTML("<p>Email do demandante: </p>"));
		hemail.add(email_demandante);
		// vPanel.add(new HTML("Cadastro de Pedidos<br>"));
		HorizontalPanel hObrigatorio = new HorizontalPanel();
		hObrigatorio.add(space);
		hObrigatorio.add(new HTML("* = campos obrigatórios"));
		vPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		vPanel.add(hnumero);
		vPanel.add(htipo);
		vPanel.add(hdemandante);
		vPanel.add(hemail);
		vPanel.add(hdata_entrada);
		vPanel.add(hdescricao);
		vPanel.add(hObrigatorio);
		vPanel.add(new Button("Enviar", new ClickHandler() {
			public void onClick(ClickEvent event) {

				if (numero.getText().equals("")
						|| demandante.getText().equals("")
						|| descricao.getText().equals("")
						|| data_entrada.getValue() == null
						|| !(radioPregao.getValue() || radioDispensa.getValue()
								|| radioInexi.getValue() || radioSessao
									.getValue())) {
					final ExtendedDialogBox aviso = new ExtendedDialogBox(true);
					aviso.center();
					aviso.add(new Label(
							"Todos os campos obrigatorios devem ser preenchidos"));
					aviso.setGlassEnabled(true);
					aviso.setAnimationEnabled(true);
					aviso.show();
				} else if (email_demandante.getText().equals("")) {
					DateTimeFormat format = DateTimeFormat
							.getFormat("yyyy-MM-dd'T'HH:mm:ss");
					String tipo = "pregao";
					if (radioDispensa.getValue())
						tipo = "dispensa";
					else if (radioInexi.getValue())
						tipo = "inexigibilidade";
					else if (radioSessao.getValue())
						tipo = "sessao";
					String parameters = "numero=" + numero.getText()
							+ "&tipo_pedido=" + tipo + "&demandante="
							+ demandante.getText() + "&data_entrada="
							+ format.format(data_entrada.getValue())
							+ "&descricao=" + descricao.getText()
							+ "&email_demandante=" + "Nenhum";
					enviaForm(parameters, numero.getText());
				} else {
					DateTimeFormat format = DateTimeFormat
							.getFormat("yyyy-MM-dd'T'HH:mm:ss");
					String tipo = "pregao";
					if (radioDispensa.getValue())
						tipo = "dispensa";
					else if (radioInexi.getValue())
						tipo = "inexigibilidade";
					else if (radioSessao.getValue())
						tipo = "sessao";
					String parameters = "numero=" + numero.getText()
							+ "&tipo_pedido=" + tipo + "&demandante="
							+ demandante.getText() + "&data_entrada="
							+ format.format(data_entrada.getValue())
							+ "&descricao=" + descricao.getText()
							+ "&email_demandante=" + email_demandante.getText();
					enviaForm(parameters, numero.getText());
				}
			}
		}));

		RootPanel.get("main_bottom").clear();
		/*
		 * RootPanel.get("main_bottom_in").clear();
		 * RootPanel.get("main_bottom").add(vPanel);
		 */

	}

	private void exibeTelaCompleta(Pedido selected, final boolean c) {
		RootPanel.get("main_cadastrar").clear();
		// Create the dialog box
		final Label nameField = new Label();
		
		nameField.setText("");
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET,
				"/LoginHandler");
		// RequestBuilder builder = new RequestBuilder(RequestBuilder.GET,
		// "/teste.json");

		try {
			Request request = builder.sendRequest(null, new RequestCallback() {

				public void onError(Request request, Throwable exception) {
				}

				public void onResponseReceived(Request request,
						Response response) {
					if (200 == response.getStatusCode()) {
						Anchor sendAnchor;
						ClickHandler clickAnchor = new ClickHandler() {
							public void onClick(ClickEvent event) {
								Window.Location.replace(url);
							}
						};
						User user = JsonUtils.safeEval(response.getText())
								.cast();
						status = user.getStatus();
						if (status.equals("Connected")) {
							sendAnchor = new Anchor("Logout");
							nameField.setText(user.getEmail());
							url = user.getUrl();
						} else {
							url = user.getUrl();
							sendAnchor = new Anchor("Login");
						}
						sendAnchor.addClickHandler(clickAnchor);
						RootPanel.get("user_on_top_bar").clear();
						RootPanel.get("user_on_top_bar").add(nameField);
						RootPanel.get("user_on_top_bar").add(sendAnchor);

					} else {

					}

				}
			});
		} catch (RequestException e) {
		}
		final SimplePanel buscando = new SimplePanel();
		buscando.addStyleName("buscando");
		buscando.add(new HTML("<img src=\"images/495.gif\" width=\"4%\"></a>"));
		RootPanel.get().add(buscando);

		RequestBuilder builder5 = new RequestBuilder(RequestBuilder.GET,
				"getpedido?q=" + selected.getNumero());

		try {
			Request request = builder5.sendRequest(null, new RequestCallback() {

				public void onError(Request request, Throwable exception) {
				}

				@SuppressWarnings("deprecation")
				public void onResponseReceived(Request request,
						Response response) {
					if (200 == response.getStatusCode()) {
						final Pedido pedido = JsonUtils.safeEval(
								response.getText()).cast();
						VerticalPanel vPanel = new VerticalPanel();
						SimplePanel folha = new SimplePanel();
						final Barrinha barrinha = new Barrinha(pedido
								.getNumero());
						;
						barrinha.setMontador(new MontadorPadrao());
						ArrayList<SimplePanel> aRemover = new ArrayList<SimplePanel>();
						folha.setStyleName("folha");
						VerticalPanel pedacos = new VerticalPanel();
						pedacos.setSpacing(10);
						folha.add(pedacos);
						RootPanel.get("main_bottom_in").clear();
						RootPanel.get("main_bottom_in").add(folha);
						DecoratorPanel decPanel = new DecoratorPanel();
						// DADOS BASICOS
						SubFolhaPanel subfolha = new SubFolhaPanel(
								"Dados básicos");
						pedacos.add(subfolha);
						subfolha.add(new Label(pedido.getNumero()),
								"Número do pedido: ");

						// subfolha.add(createTextBox(barrinha, "tipo_pedido",
						// pedido.getTipoDePedido(), pedido.getNumero()),
						// "Tipo de pedido: ");
						subfolha.add(
								createTextBox(barrinha, "demandante",
										pedido.getDemandante(),
										pedido.getNumero(),c),
								"Nome do demandante: ");
						subfolha.add(
								createTextBox(barrinha, "email_demandante",
										pedido.getEmail(), pedido.getNumero(),c),
								"Email do demandante: ");
						subfolha.add(
								createTextBox(barrinha, "descricao",
										pedido.getDescricao(),
										pedido.getNumero(),c),
								"Descrição do pedido: ");

						DateTimeFormat dateFormat = DateTimeFormat
								.getMediumDateTimeFormat();
						final DateTimeFormat format = DateTimeFormat
								.getFormat("yyyy-MM-dd'T'HH:mm:ss");

						subfolha.add(
								criaDatePicker(
										barrinha,
										pedido.getData(),
										"setpedido?numero="
												+ pedido.getNumero()
												+ "&data_entrada=",c),
								"Data de entrada: ");
						subfolha.add(new Label(""), "");
						subfolha.addToBottom(
								createRadioGroupTipo(aRemover, barrinha,
										pedido, true, pedido.getTipoDePedido(),c),
								"Tipo de Pedido: ");

						/*
						 * final ListBox lb = new ListBox();
						 * lb.addItem("legal"); lb.addItem("ilegal");
						 * lb.addChangeHandler(new ChangeHandler() {
						 * 
						 * @Override public void onChange(ChangeEvent event) {
						 * if
						 * (lb.getItemText(lb.getSelectedIndex()).equals("legal"
						 * )) AlteraEstado("parecer=True", pedido.getNumero());
						 * else if
						 * (lb.getItemText(lb.getSelectedIndex()).equals(
						 * "ilegal")) AlteraEstado("parecer=False",
						 * pedido.getNumero()); } }); //Set as dropdown
						 * lb.setVisibleItemCount(lb.getItemCount());
						 * lb.setSelectedIndex
						 * (pedido.getLegalidade().getParecer(
						 * ).equals("false")?1:0);
						 */
						HorizontalPanel legalidade = createRadioGroup(barrinha,
								pedido, "LegalidadeHandler",
								"LegalidadeHandler", pedido.getLegalidade()
										.getParecer(), "parecer", "Legal",
								"Ilegal",c);
						HorizontalPanel autorizacao = createRadioGroup(
								barrinha, pedido, "AutorizacaoHandler",
								"AutorizacaoHandler", pedido.getAutorizacao()
										.getParecer(), "parecer", "Sim", "Não",c);
						HorizontalPanel corretudeDescricao = createRadioGroup(
								barrinha, pedido, "CorretudeHandler",
								"CorretudeDescHandler", pedido.getCorretude()
										.getDescricao(), "descricao",
								"Correta", "Incorreta",c);
						HorizontalPanel corretudeQuantitativo = createRadioGroup(
								barrinha, pedido, "CorretudeHandler",
								"CorretudeQuantHandler", pedido.getCorretude()
										.getQuantitativo(), "quantitativo",
								"Correta", "Incorreta",c);
						HorizontalPanel corretudeCotacao = createRadioGroup(
								barrinha, pedido, "CorretudeHandler",
								"CorretudeCotHandler", pedido.getCorretude()
										.getCotacao(), "cotacao", "Correta",
								"Incorreta",c);

						subfolha = new SubFolhaPanel("Legalidade");
						pedacos.add(subfolha);
						subfolha.add(legalidade, "Parecer: ");
						subfolha.add(new Label(), "");
						subfolha.add(
								criaDatePicker(
										barrinha,
										pedido.getLegalidade().getDataEnvio(),
										"LegalidadeHandler?pedido="
												+ pedido.getNumero()
												+ "&data_envio=",c),
								"Data de envio: ");

						subfolha.add(
								criaDatePicker(
										barrinha,
										pedido.getLegalidade().getDataRetorno(),
										"LegalidadeHandler?pedido="
												+ pedido.getNumero()
												+ "&data_retorno=",c),
								"Data de retorno: ");

						// AUTORIZACAO
						subfolha = new SubFolhaPanel("Autorização");
						if (pedido.getTipoDePedido().equalsIgnoreCase(
								"dispensa")
								|| pedido.getTipoDePedido().equalsIgnoreCase(
										"inexigibilidade"))
							subfolha.setVisible(false);
						pedacos.add(subfolha);
						aRemover.add(subfolha);

						subfolha.add(autorizacao, "Parecer: ");

						subfolha = new SubFolhaPanel("Corretude");
						pedacos.add(subfolha);
						subfolha.add(corretudeDescricao, "Descrição: ");
						subfolha.add(new Label(), "");
						subfolha.add(corretudeQuantitativo, "Quantitativo: ");
						subfolha.add(new Label(), "");
						subfolha.add(corretudeCotacao, "Cotação: ");
						subfolha.add(new Label(), "");
						// CORRETUDE

						subfolha.add(
								criaDatePicker(barrinha, pedido.getCorretude()
										.getData(), "CorretudeHandler?pedido="
										+ pedido.getNumero() + "&data=",c),
								"Data de definição: ");

						// MINUTA DO EDITAL
						int indiceMinuta = pedido.getMinuta().indiceAtual();

						HorizontalPanel minuta = createRadioGroup(barrinha,
								pedido, "MinutaHandler", "Minuta", pedido
										.getMinuta().getParecer(indiceMinuta),
								"parecer", "Legal", "Ilegal",c);

						subfolha = new SubFolhaPanel("Minuta do Edital");
						if (pedido.getTipoDePedido().equalsIgnoreCase(
								"dispensa")
								|| pedido.getTipoDePedido().equalsIgnoreCase(
										"inexigibilidade"))
							subfolha.setVisible(false);
						pedacos.add(subfolha);
						aRemover.add(subfolha);
						subfolha.add(minuta, "Parecer: ");

						subfolha.add(
								criaDatePicker(
										barrinha,
										pedido.getMinuta().getDataInicio(
												indiceMinuta),
										"MinutaHandler?pedido="
												+ pedido.getNumero()
												+ "&data_inicio=",c),
								"Data de inicio de elaboração: ");

						subfolha.add(
								criaDatePicker(
										barrinha,
										pedido.getMinuta().getDataEnvio(
												indiceMinuta),
										"MinutaHandler?pedido="
												+ pedido.getNumero()
												+ "&data_envio=",c),
								"Data de envio: ");

						subfolha.add(
								criaDatePicker(
										barrinha,
										pedido.getMinuta().getDataRetorno(
												indiceMinuta),
										"MinutaHandler?pedido="
												+ pedido.getNumero()
												+ "&data_retorno=",c),
								"Data de retorno: ");

						int indicePregao = pedido.getPregao().indiceAtual();

						StackPanel stack = new StackPanel();
						stack.setWidth("787px");

						for (int i = 0; i <= indicePregao + 1; i++) {

							subfolha = new SubFolhaPanel("");
							boolean enabled = true;
							if (i != indicePregao + 1) {
								enabled = false;
								stack.add(
										subfolha,
										"Pregão <span style = 'color: #FF0000'>não comprado</span>, nº: "
												+ pedido.getPregao().getNumero(
														i), true);
							} else {
								stack.add(subfolha, "Pregão atual");
								stack.showStack(indicePregao + 1);
								pedacos.add(stack);
							}

							// PREGAO

							final HorizontalPanel pregao = createRadioGroup(
									barrinha, pedido, enabled, "PregaoHandler",
									"Pregao" + i, pedido.getPregao()
											.getParecer(i), "parecer",
									"Comprado", "Não comprado");

							final TextBox t = new TextBox();
							t.setEnabled(c);
							t.setText(pedido.getPregao().getNumero(i));
							t.addValueChangeHandler(new ValueChangeHandler<String>() {

								@Override
								public void onValueChange(
										ValueChangeEvent<String> event) {
									RequestBuilder builder6 = new RequestBuilder(
											RequestBuilder.POST,
											"PregaoHandler?pedido="
													+ pedido.getNumero()
													+ "&numero=" + t.getText());
									try {
										Request request = builder6.sendRequest(
												null, new RequestCallback() {

													public void onError(
															Request request,
															Throwable exception) {
													}

													public void onResponseReceived(
															Request request,
															Response response) {
														if (200 == response
																.getStatusCode()) {
															barrinha.atualizar();
														} else {

														}
													}

												});
									} catch (RequestException e) {
									}
								}
							});
							/*
							 * t.addChangeHandler(new ChangeHandler() {
							 * 
							 * @Override public void onChange(ChangeEvent event)
							 * {
							 * 
							 * 
							 * } });
							 */
							subfolha.add(t, "Número: ");
							if (i != indicePregao + 1) {
								subfolha.add(new HTML(), "");
							} else {
								subfolha.add(new HTML("<a class='anc5' name ='"
										+ "pregao" + "'></a>"), "");
							}
							final HorizontalPanel definicao = criaDatePicker(
									barrinha,
									pedido.getPregao().getData(i),
									enabled,
									"PregaoHandler?pedido="
											+ pedido.getNumero() + "&data=");
							final HorizontalPanel abertura = criaDatePicker(
									barrinha,
									pedido.getPregao().getLicitacaoData(i),
									enabled,
									"PregaoHandler?pedido="
											+ pedido.getNumero()
											+ "&licitacao_data=");
							subfolha.add(definicao, "Data de definição: ");
							subfolha.add(abertura, "Data de abertura: ");

							subfolha.add(pregao, "Parecer: ");

							final Button adicionaPregao = new Button(
									"Adicionar Pregão");
							adicionaPregao.addClickHandler(new ClickHandler() {

								@Override
								public void onClick(ClickEvent event) {
									RequestBuilder builder5 = new RequestBuilder(
											RequestBuilder.GET,
											"AdicionaPregao?q="
													+ pedido.getNumero());

									try {
										Window.Location.replace("/#pregao");
										builder5.sendRequest(null,
												new RequestCallback() {

													public void onError(
															Request request2,
															Throwable exception) {
													}

													@SuppressWarnings("deprecation")
													public void onResponseReceived(
															Request request2,
															Response response) {
														if (200 == response
																.getStatusCode()) {
															// Pedido pedido2 =
															// JsonUtils.safeEval(response.getText()).cast();
															// History.newItem("pedido"+pedido.getNumero());
															Window.Location.replace("/#/pedido/"
																	+ pedido.getNumero());

														}
													}
												});
									} catch (RequestException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									Window.Location.replace("/#pregao");
								}
							});

							Timer timer = new Timer() {

								@Override
								public void run() {
									if ((!((RadioButton) pregao.getWidget(0))
											.getValue())
											|| ((DateBox) definicao
													.getWidget(0)).getValue()
													.toString().equals("")
											|| ((DateBox) abertura.getWidget(0))
													.getValue().toString()
													.equals("")
											|| ((TextBox) t).getValue().equals(
													"")) {
										adicionaPregao.setEnabled(false);
									} else {
										adicionaPregao.setEnabled(true);
									}

								}
							};
							timer.scheduleRepeating(1000);

							if (i == indicePregao + 1)
								subfolha.add(adicionaPregao);

						}

						subfolha = new SubFolhaPanel("Adjudicação");
						if (pedido.getTipoDePedido().equalsIgnoreCase(
								"dispensa")
								|| pedido.getTipoDePedido().equalsIgnoreCase(
										"inexigibilidade"))
							subfolha.setVisible(false);
						pedacos.add(subfolha);
						aRemover.add(subfolha);

						subfolha.add(
								criaDatePicker(
										barrinha,
										pedido.getAdjudicacao().getData(),
										"AdjudicacaoHandler?pedido="
												+ pedido.getNumero() + "&data=",c),
								"Data: ");

						subfolha = new SubFolhaPanel("Homologação");
						if (pedido.getTipoDePedido().equalsIgnoreCase(
								"dispensa")
								|| pedido.getTipoDePedido().equalsIgnoreCase(
										"inexigibilidade"))
							subfolha.setVisible(false);
						pedacos.add(subfolha);
						aRemover.add(subfolha);

						subfolha.add(
								criaDatePicker(
										barrinha,
										pedido.getHomologacao().getData(),
										"HomologacaoHandler?pedido="
												+ pedido.getNumero() + "&data=",c),
								"Data: ");

						subfolha = new SubFolhaPanel("Publicação");
						pedacos.add(subfolha);

						subfolha.add(
								criaDatePicker(barrinha, pedido.getPublicacao()
										.getData(), "PublicacaoHandler?pedido="
										+ pedido.getNumero() + "&data=",c),
								"Data: ");

						subfolha = new SubFolhaPanel("Empenho");
						pedacos.add(subfolha);

						subfolha.add(
								criaDatePicker(barrinha, pedido.getEmpenho()
										.getData(), "EmpenhoHandler?pedido="
										+ pedido.getNumero() + "&data=",c),
								"Data: ");

						subfolha.add(
								criaDatePicker(barrinha, pedido
										.getNotaAlmoxarifado().getData(),
										"NotaAlmoxarifadoHandler?pedido="
												+ pedido.getNumero() + "&data=",c),
								"Data de envio ao almoxarifado");

						subfolha.add(
								criaDatePicker(barrinha, pedido.getPatrimonio()
										.getData(), "PatrimonioHandler?pedido="
										+ pedido.getNumero() + "&data=",c),
								"Data de envio ao Patrimônio: ");

						/*
						 * subfolha = new SubFolhaPanel("Crédito Orçamentário");
						 * pedacos.add(subfolha);
						 * 
						 * //DETALHAMENTO HorizontalPanel detalhamento =
						 * createRadioGroup(barrinha, pedido,
						 * "DetalhamentoHandler", "Detalhamento",
						 * pedido.getDetalhamento().getParecer(), "parecer",
						 * "Autorizado", "Não autorizado");
						 * subfolha.add(detalhamento, "Parecer: ");
						 * 
						 * subfolha.add(criaDatePicker(barrinha,
						 * pedido.getDetalhamento().getData(),
						 * "DetalhamentoHandler?pedido="
						 * +pedido.getNumero()+"&data="), "Data: ");
						 */

						subfolha = new SubFolhaPanel("Recebimento");
						pedacos.add(subfolha);

						subfolha.add(
								criaDatePicker(barrinha, pedido
										.getNotaContabilidade().getData(),
										"NotaContabilidadeHandler?pedido="
												+ pedido.getNumero() + "&data=",c),
								"Data provisória: ");

						subfolha.add(
								criaDatePicker(barrinha, pedido.getLiquidacao()
										.getData(), "LiquidacaoHandler?pedido="
										+ pedido.getNumero() + "&data=",c),
								"Data definitiva: ");

						// subfolha.add(criaDatePicker(barrinha,
						// pedido.getPagamento().getData(),
						// "PagamentoHandler?pedido="+pedido.getNumero()+"&data="),
						// "Data de Pagamento: " );

						subfolha = new SubFolhaHistoricoPanel(pedido);
						pedacos.add(subfolha);
						/*
						 * Tree to = new Tree(); // HTML title = new HTML(); //
						 * final TreeItem root = new TreeItem(title);
						 * 
						 * TreeItem item =
						 * to.addTextItem("Histórico de alteraçães no pedido");
						 * 
						 * // Temporarily add an item so we can expand this node
						 * item.addTextItem("");
						 * 
						 * //HISTORICO //to.addItem(root); // final TreeItem
						 * item = new TreeItem(); //root.addItem(item);
						 * to.addOpenHandler(new OpenHandler<TreeItem>() {
						 * 
						 * @Override public void onOpen(OpenEvent<TreeItem>
						 * event) { TreeItem item = event.getTarget(); if
						 * (item.getChildCount() == 1) { // Close the item
						 * immediately item.setState(false, false);
						 * 
						 * 
						 * String historico = ""; for(int i = 0; i <
						 * pedido.getHistorico().size(); i++){ String data =
						 * pedido.getHistorico().getData()[i]; DateTimeFormat
						 * format1 =
						 * DateTimeFormat.getFormat("yyyy-MM-dd'T'HH:mm:ss");
						 * DateTimeFormat format2 =
						 * DateTimeFormat.getFormat("dd/MM/yyyy' 'HH:mm:ss");
						 * historico +=
						 * format2.format(format1.parse(data.substring(0, 19)))
						 * + " " + pedido.getHistorico().getInfo()[i] + " por "
						 * + pedido.getHistorico().getUser()[i] + "<br>";
						 * 
						 * } historico += "<br><br><br>";
						 * 
						 * item.addItem(new TreeItem(new HTML(historico)));
						 * 
						 * // Remove the temporary item when we finish loading
						 * item.getChild(0).remove();
						 * 
						 * // Reopen the item item.setState(true, false); }
						 * 
						 * 
						 * if(open.equals(root)) { String historico = "";
						 * for(int i = 0; i < pedido.getHistorico().size();
						 * i++){ String data =
						 * pedido.getHistorico().getData()[i]; DateTimeFormat
						 * format1 =
						 * DateTimeFormat.getFormat("yyyy-MM-dd'T'HH:mm:ss");
						 * DateTimeFormat format2 =
						 * DateTimeFormat.getFormat("dd/MM/yyyy' 'HH:mm:ss");
						 * historico +=
						 * format2.format(format1.parse(data.substring(0, 19)))
						 * + " " + pedido.getHistorico().getInfo()[i] + " por "
						 * + pedido.getHistorico().getUser()[i] + "<br>";
						 * 
						 * } historico += "<br><br><br>";
						 * 
						 * item.setWidget(new HTML(historico)); } } });
						 * 
						 * pedacos.add(to);
						 */
						pedacos.add(new HTML("<br><br><br>"));
						decPanel.setWidget(vPanel);
						// folha.add(vPanel);
						RootPanel.get("main_bottom").clear();
						// RootPanel.get("main_bottom_in").add(decPanel);
						RootPanel.get("main_top").clear();

						RootPanel.get("main_top").add(barrinha);

						buscando.removeFromParent();

					} else {

					}
				}

				private HorizontalPanel createRadioGroupTipo(
						final ArrayList<SimplePanel> aRemover,
						final Barrinha barrinha, final Pedido pedido,
						boolean enabled, String atual, final boolean c) {
					final HorizontalPanel vp = new HorizontalPanel();
					// VerticalPanel vvp = new VerticalPanel();
					// HorizontalPanel fp = new HorizontalPanel();
					// HorizontalPanel sp = new HorizontalPanel();
					// fp.setSpacing(20);
					// sp.setSpacing(20);
					// vp.add(vvp);
					// vvp.add(fp);
					// vvp.add(sp);
					final SimplePanel uploading = new SimplePanel();
					uploading.add(new HTML("<img src=\"images/up.gif\"></a>"));

					RadioButton radioPregao = new RadioButton("tipo",
							"Pregão Eletrônico");
					radioPregao.setEnabled(c);

					radioPregao.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							vp.add(uploading);
							AlteraEstadoTipo("tipo_pedido=pregao",
									pedido.getNumero(), "setpedido", uploading);
							for (SimplePanel a : aRemover) {
								a.setVisible(true);
							}
							barrinha.setMontador(new MontadorPadrao());
							barrinha.atualizar();
						}
					});

					RadioButton radioDispensa = new RadioButton("tipo",
							"Dispensa");
					radioDispensa.setEnabled(c);

					radioDispensa.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							vp.add(uploading);
							AlteraEstadoTipo("tipo_pedido=dispensa",
									pedido.getNumero(), "setpedido", uploading);
							for (SimplePanel a : aRemover) {
								a.setVisible(false);
							}
							barrinha.setMontador(new MontadorPadrao());
							barrinha.atualizar();
						}
					});

					RadioButton radioInexi = new RadioButton("tipo",
							"Inexigibilidade");
					radioInexi.setEnabled(c);

					radioInexi.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							vp.add(uploading);
							AlteraEstadoTipo("tipo_pedido=inexigibilidade",
									pedido.getNumero(), "setpedido", uploading);
							for (SimplePanel a : aRemover) {
								a.setVisible(false);
							}
							barrinha.setMontador(new MontadorPadrao());
							barrinha.atualizar();
						}
					});

					RadioButton radioSessao = new RadioButton("tipo",
							"Sessão Pública");
					radioSessao.setEnabled(c);

					radioSessao.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							vp.add(uploading);
							AlteraEstadoTipo("tipo_pedido=sessao",
									pedido.getNumero(), "setpedido", uploading);
							for (SimplePanel a : aRemover) {
								a.setVisible(true);
							}
							barrinha.setMontador(new MontadorPadrao());
							barrinha.atualizar();
						}
					});

					if (atual.equals("pregao")) {
						radioPregao.setValue(true);
						radioDispensa.setValue(false);
						radioInexi.setValue(false);
						radioSessao.setValue(false);
					} else if (atual.equals("dispensa")) {
						radioPregao.setValue(false);
						radioDispensa.setValue(true);
						radioInexi.setValue(false);
						radioSessao.setValue(false);
					} else if (atual.equals("inexigibilidade")) {
						radioPregao.setValue(false);
						radioDispensa.setValue(false);
						radioInexi.setValue(true);
						radioSessao.setValue(false);
					} else if (atual.equals("sessao")) {
						radioPregao.setValue(false);
						radioDispensa.setValue(false);
						radioInexi.setValue(false);
						radioSessao.setValue(true);
					} else {
						radioPregao.setValue(false);
						radioDispensa.setValue(false);
						radioInexi.setValue(false);
						radioSessao.setValue(false);
					}
					vp.setSpacing(20);
					vp.add(radioPregao);
					vp.add(radioDispensa);
					vp.add(radioInexi);
					vp.add(radioSessao);

					return vp;
				}

				private HorizontalPanel createRadioGroup(Barrinha barrinha,
						final Pedido pedido, final String handler,
						String group, String atual, final String dado,
						String valorVerdade, String valorFalso,final boolean c) {
					return createRadioGroup(barrinha, pedido, true, handler,
							group, atual, dado, valorVerdade, valorFalso);
				}

				private HorizontalPanel createRadioGroup(
						final Barrinha barrinha, final Pedido pedido,
						boolean enabled, final String handler, String group,
						String atual, final String dado, String valorVerdade,
						String valorFalso) {
					final HorizontalPanel vp = new HorizontalPanel();
					RadioButton radioLegal = new RadioButton(group,
							valorVerdade);
					radioLegal.setEnabled(c);
					final SimplePanel uploading = new SimplePanel();
					uploading.add(new HTML("<img src=\"images/up.gif\"></a>"));

					radioLegal.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							barrinha.atualizar();
							vp.add(uploading);
							AlteraEstado(dado + "=True", pedido.getNumero(),
									handler, uploading);

						}
					});
					RadioButton radioIlegal = new RadioButton(group, valorFalso);
					radioIlegal.setEnabled(c);
					radioIlegal.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							barrinha.atualizar();
							vp.add(uploading);
							AlteraEstado(dado + "=False", pedido.getNumero(),
									handler, uploading);
						}
					});
					if (atual.equals("null") || atual == null
							|| atual.equals("")) {
						radioLegal.setValue(false);
						radioIlegal.setValue(false);
					} else if (atual.equals("false")) {
						radioLegal.setValue(false);
						radioIlegal.setValue(true);
					} else {
						radioLegal.setValue(true);
						radioIlegal.setValue(false);
					}
					vp.add(radioIlegal);
					vp.add(radioLegal);

					return vp;
				}

				private HorizontalPanel criaDatePicker(Barrinha barrinha,
						String dataAtual, final String parameters, boolean c) {
					return criaDatePicker(barrinha, dataAtual, true, parameters);
				}

				private HorizontalPanel criaDatePicker(final Barrinha barrinha,
						String dataAtual, boolean enabled,
						final String parameters) {
					DateTimeFormat dateFormat = DateTimeFormat
							.getMediumDateTimeFormat();
					final HorizontalPanel novo = new HorizontalPanel();
					// DefaultDateTimeFormatInfo info = new
					// DefaultDateTimeFormatInfo();
					// DateTimeFormat datef = new DateTimeFormat("dd-MM-YYYY",
					// info) {};
					// Date teste = datef.parse("31-10-2013");
					final SimplePanel uploading = new SimplePanel();
					uploading.add(new HTML("<img src=\"images/up.gif\"></a>"));

					DateBox dateBox = new DateBox();
					dateBox.setEnabled(c);
					novo.add(dateBox);
					dateBox.setFormat(new DateBox.DefaultFormat(DateTimeFormat
							.getFormat("dd/MM/yyyy")));
					// dateBox.setFormat(new DateBox.DefaultFormat(dateFormat));
					// dateBox.setValue(teste);
					final DateTimeFormat format = DateTimeFormat
							.getFormat("yyyy-MM-dd'T'HH:mm:ss");
					try {
						Date selDate = (Date) format.parse(dataAtual);
						dateBox.getDatePicker().setValue(selDate, true);
					} catch (Exception pe) {
						// setting current date
						dateBox.getDatePicker().setValue(null, true);
					}
					// final Label teste = new
					// Label(format.format(dateBox.getValue()));
					// dialogContents.add(teste);
					// dateBox.setValue(new Date(2013, 12, 31));
					dateBox.addValueChangeHandler(new ValueChangeHandler<Date>() {

						@Override
						public void onValueChange(ValueChangeEvent<Date> event) {
							novo.add(uploading);
							RequestBuilder builder6 = new RequestBuilder(
									RequestBuilder.POST, parameters
											+ format.format(event.getValue()));
							try {
								Request request = builder6.sendRequest(null,
										new RequestCallback() {

											public void onError(
													Request request,
													Throwable exception) {
											}

											public void onResponseReceived(
													Request request,
													Response response) {
												if (200 == response
														.getStatusCode()) {
													barrinha.atualizar();
													uploading.clear();
													uploading
															.add(new HTML(
																	"<img src=\"images/ok.gif\" width=\"20px\"></a>"));
													Timer timer = new Timer() {

														@Override
														public void run() {
															uploading
																	.removeFromParent();

														}
													};
													timer.schedule(2000);

												} else {

												}
											}

										});
							} catch (RequestException e) {
							}

						}
					});
					return novo;
				}

				private HorizontalPanel createTextBox(final Barrinha barrinha,
						final String parameter, String text,
						final String numPedido, final boolean c) {

					final HorizontalPanel novo = new HorizontalPanel();
					final SimplePanel uploading = new SimplePanel();
					uploading.add(new HTML("<img src=\"images/up.gif\"></a>"));

					final TextBox tal = new TextBox();
					novo.add(tal);
					tal.setEnabled(c);

					tal.setText(text);
					// tal.setReadOnly(true);
					tal.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							// tal.setReadOnly(false);
						}
					});
					tal.addChangeHandler(new ChangeHandler() {

						@Override
						public void onChange(ChangeEvent event) {
							novo.add(uploading);
							// tal.setReadOnly(true);
							RequestBuilder builder6 = new RequestBuilder(
									RequestBuilder.POST, "setpedido?numero="
											+ numPedido + "&" + parameter + "="
											+ tal.getText());
							try {
								Request request = builder6.sendRequest(null,
										new RequestCallback() {

											public void onError(
													Request request,
													Throwable exception) {
											}

											public void onResponseReceived(
													Request request,
													Response response) {
												if (200 == response
														.getStatusCode()) {
													barrinha.atualizar();
													uploading.clear();
													uploading
															.add(new HTML(
																	"<img src=\"images/ok.gif\" width=\"20px\"></a>"));
													Timer timer = new Timer() {

														@Override
														public void run() {
															uploading
																	.removeFromParent();

														}
													};
													timer.schedule(2000);
												} else {

												}
											}

										});
							} catch (RequestException e) {
							}

						}
					});
					return novo;
				}
			});
		} catch (RequestException e) {
		}

	}

	private void AlteraEstado(String parameter, String numPedido,
			String handler, final SimplePanel uploading) {

		RequestBuilder builder6 = new RequestBuilder(RequestBuilder.POST,
				handler + "?pedido=" + numPedido + "&" + parameter);
		try {
			Request request = builder6.sendRequest(null, new RequestCallback() {

				public void onError(Request request, Throwable exception) {
				}

				public void onResponseReceived(Request request,
						Response response) {
					if (200 == response.getStatusCode()) {
						uploading.clear();
						uploading
								.add(new HTML(
										"<img src=\"images/ok.gif\" width=\"20px\"></a>"));
						Timer timer = new Timer() {

							@Override
							public void run() {
								uploading.removeFromParent();

							}
						};
						timer.schedule(2000);
					} else {

					}
				}

			});
		} catch (RequestException e) {
		}
	}

	private void AlteraEstadoTipo(String parameter, String numPedido,
			String handler, final SimplePanel uploading) {

		RequestBuilder builder6 = new RequestBuilder(RequestBuilder.POST,
				handler + "?numero=" + numPedido + "&" + parameter);
		try {
			Request request = builder6.sendRequest(null, new RequestCallback() {

				public void onError(Request request, Throwable exception) {
				}

				public void onResponseReceived(Request request,
						Response response) {
					if (200 == response.getStatusCode()) {
						uploading.clear();
						uploading
								.add(new HTML(
										"<img src=\"images/ok.gif\" width=\"20px\"></a>"));
						Timer timer = new Timer() {

							@Override
							public void run() {
								uploading.removeFromParent();

							}
						};
						timer.schedule(2000);
					} else {

					}
				}

			});
		} catch (RequestException e) {
		}
	}

	public void enviaForm(String parameters, final String numero) {
		final DialogBox dialogBox = new DialogBox(true);
		try {

			dialogBox.setText("Aguarde");
			VerticalPanel dialogContents = new VerticalPanel();
			dialogBox.setWidget(dialogContents);
			dialogBox.setGlassEnabled(true);
			dialogBox.center();
			dialogBox.setAnimationEnabled(true);
			dialogBox.show();
			HTML details = new HTML("Enviando...");
			dialogContents.add(details);
			dialogContents.setCellHorizontalAlignment(details,
					HasHorizontalAlignment.ALIGN_CENTER);

			final RequestBuilder builder3 = new RequestBuilder(
					RequestBuilder.POST, "/setpedido");
			builder3.setHeader("Content-type",
					"application/x-www-form-urlencoded");

			Request request = builder3.sendRequest(parameters,
					new RequestCallback() {

						public void onError(Request request, Throwable exception) {
						}

						public void onResponseReceived(Request request,
								Response response) {
							if (200 == response.getStatusCode()) {

								RootPanel.get("main_bottom").clear();
								RootPanel.get("main_bottom_in").clear();

							} else {

							}
						}
					});
		} catch (RequestException e) {
		}
		dialogBox.clear();
		dialogBox.add(new HTML(
				"Enviado! Carregando seu pedido recém criado...."));

		Timer t = new Timer() {
			@Override
			public void run() {
				RequestBuilder builder5 = new RequestBuilder(
						RequestBuilder.GET, "getpedido?q=" + numero);

				try {
					builder5.sendRequest(null, new RequestCallback() {

						public void onError(Request request2,
								Throwable exception) {
						}

						@SuppressWarnings("deprecation")
						public void onResponseReceived(Request request2,
								Response response) {
							if (200 == response.getStatusCode()) {
								dialogBox.hide();
								Pedido pedido = JsonUtils.safeEval(
										response.getText()).cast();
								History.newItem("/pedido/" + pedido.getNumero());
							}
						}
					});
				} catch (RequestException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		t.schedule(1000);
	}
}