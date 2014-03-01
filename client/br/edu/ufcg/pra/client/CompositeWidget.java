package br.edu.ufcg.pra.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class CompositeWidget extends Composite implements ClickHandler{
	private CheckBox checkBox = new CheckBox();
	private HorizontalPanel hPanel = new HorizontalPanel();
	
	SubFolhaPanel subfolha, subfolha2;
	public CompositeWidget(SubFolhaPanel subfolha, int i) {
		checkBox.setStyleName("checkBoxComp");
		checkBox.setText("Mostrar Pregão");
		subfolha.setVisible(false);
		subfolha2 = new SubFolhaPanel(i+"º Pregão não comprado");
		subfolha2.add(checkBox);
		this.subfolha = subfolha;
		subfolha.setWidth("745px");
		subfolha2.add(subfolha);
		checkBox.addClickHandler(this);
		hPanel.add(subfolha2);
		hPanel.setSpacing(0);
		initWidget(hPanel);
		
		setStyleName("compositeWidget");
	}
	@Override
	public void onClick(ClickEvent event) {
		if (event.getSource() == checkBox){
			subfolha.setVisible(checkBox.getValue());
			if (!subfolha.isVisible()){
				subfolha2.setHeight("80px");
				setCaption("Mostrar Pregão");
			}else{
				subfolha2.setHeight("304px");
				setCaption("");
			}
		}
	}
	
	public void setCaption(String caption){
		checkBox.setText(caption);
	}
	
	public String getCaption(){
		return checkBox.getText();
	}
}
