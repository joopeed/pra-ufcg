package br.edu.ufcg.pra.client;

import java.util.ArrayList;


import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SubFolhaPanel extends SimplePanel {
	
    private VerticalPanel linhas;
    private HorizontalPanel colunas;
    private ArrayList<VerticalPanel> coluna;
    private int ultimaColuna = 0;
	
	public SubFolhaPanel(String title){
		linhas = new VerticalPanel();
		linhas.add(new HTML("<h2>"+title+"</h2>"));
		colunas =  new HorizontalPanel();
		//colunas.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		colunas.setSpacing(10);
		coluna = new ArrayList<VerticalPanel>();
		coluna.add(new VerticalPanel());
		coluna.add(new VerticalPanel());
		colunas.add(coluna.get(0));
		colunas.add(coluna.get(1));
		linhas.add(colunas);
		super.add(linhas);
		this.setStyleName("subfolha");
	}
	@Override
	public void add(Widget w) {
		linhas.add(w);
	}
	
	public void add(Widget w, String title) {
		HorizontalPanel campoEtexto = new HorizontalPanel();
		campoEtexto.setSpacing(10);
		Label ti = new Label(title);
		ti.setWidth("140px");
		campoEtexto.add(ti);
		w.setWidth("200px");
		campoEtexto.add(w);
		coluna.get(ultimaColuna).add(campoEtexto);
		if(ultimaColuna == 0) ultimaColuna = 1;
		else ultimaColuna = 0;
	}
}
