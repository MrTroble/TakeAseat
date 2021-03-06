package de.mrtroble.sitzplan;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class SitzBank extends GridPane {

	private String nachbar1,nachbar2;
	
	public SitzBank(String m,String m1) {
		super();
		int ysize = 120;
		
		nachbar1 = m;
		nachbar2 = m1;
		Label lab = new Label(m);
		lab.setTextFill(Color.BLACK);
        lab.setFont(new Font(20));
		
		Label lab2 = new Label(m1);
		lab2.setTextFill(Color.BLACK);
        lab2.setFont(new Font(20));
        
        Image img = new Image(SitzBank.class.getResourceAsStream("back.png"),300,120,true,true);
        ImageView view = new ImageView(img);
        
        add(view, 0, 0);
		add(lab, 0, 0);
		add(lab2, 0, 0);
	    
		lab.setTranslateX((ysize - lab.layoutBoundsProperty().get().getWidth())/4);
		lab2.setTranslateX(ysize + (ysize - lab2.layoutBoundsProperty().get().getWidth())/4);
	}

	public String getNachbar1() {
		return nachbar1;
	}

	public void setNachbar1(String nachbar1) {
		this.nachbar1 = nachbar1;
	}

	public String getNachbar2() {
		return nachbar2;
	}

	public void setNachbar2(String nachbar2) {
		this.nachbar2 = nachbar2;
	}
	
	
}
