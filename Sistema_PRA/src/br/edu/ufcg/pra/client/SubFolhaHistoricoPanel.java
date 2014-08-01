package br.edu.ufcg.pra.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;


public class SubFolhaHistoricoPanel extends SubFolhaPanel {

	
	
	public SubFolhaHistoricoPanel(final Pedido pedido) {
		super("");
		linhas.removeFromParent();
		linhas = new VerticalPanel();
		addToSuper(linhas);
		HorizontalPanel head = new HorizontalPanel();
		head.setSpacing(15);
		head.add(new HTML("<h2>Histórico</h2>"));
		Button atualizador = new Button("<img src=\"images/refresh.png\"></a>");
		head.add(atualizador);
		linhas.add(head);
		final SimplePanel content = new SimplePanel();
		linhas.add(content);
		atualizador.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				content.clear();
		        content.add(new HTML("<center><img src=\"images/495.gif\" width=\"25px\"></a></center>"));
				//pedido.getHistorico();
		       pedido.getHistorico(content);
				
		       
			}
		});
		
	}
}
