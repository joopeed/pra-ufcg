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
	private MontadorDeBarrinha montador;

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
			        	 panel.add(montador.monta(pedido));
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
					        	 panel.add(montador.monta(pedido));
					          }}});
				} catch (RequestException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

			}
		};
     timer.schedule(1000);
		
	}

	public void setMontador(MontadorDeBarrinha novoMontador) {
		montador = novoMontador;
	}
	

	
}
