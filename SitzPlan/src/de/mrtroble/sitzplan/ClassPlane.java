package de.mrtroble.sitzplan;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class ClassPlane extends GridPane{

	public ClassPlane() {
		int x = 0,y = 1;
		int i;
		for(i = 0;i < Main.plane.size();i += 2){
			if(i + 1 >= Main.plane.size()){add(new SitzBank(Main.getM8((short) i),new ClassM8("", (short) (i + 1))), x, y);break;}
			add(new SitzBank(Main.getM8((short) i),Main.getM8((short) (i + 1))), x, y);
			y++;
			if(y > 4)break;
		}
		y = 0;
		x = 1;
		for(i += 2;i < Main.plane.size();i += 2){
			if(i + 1 >= Main.plane.size()){add(new SitzBank(Main.getM8((short) i),new ClassM8("", (short) (i + 1))), x, y);break;}
			add(new SitzBank(Main.getM8((short) i),Main.getM8((short) (i + 1))), x, y);
			y++;
			if(y > 4)break;
		}
		y = 0;
		x = 2;
		for(i += 2;i < Main.plane.size();i += 2){
			if(i + 1 >= Main.plane.size()){add(new SitzBank(Main.getM8((short) i),new ClassM8("", (short) (i + 1))), x, y);break;}
			add(new SitzBank(Main.getM8((short) i),Main.getM8((short) (i + 1))), x, y);
			y++;
		}
		
	    setTranslateX(10);
	    setTranslateY(10);
		setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), new Insets(0))));
	}
	
	
	@Override
	public final void add(Node child, int columnIndex, int rowIndex) {
		if(!(child instanceof SitzBank))return;
		super.add(child, columnIndex, rowIndex);
	}
	
	@Override
	public void add(Node child, int columnIndex, int rowIndex, int colspan, int rowspan) {
		if(!(child instanceof SitzBank))return;
		super.add(child, columnIndex, rowIndex, colspan, rowspan);
	}
	
	public final void add(SitzBank child, int columnIndex, int rowIndex) {
		super.add(child, columnIndex, rowIndex);
	}
}
