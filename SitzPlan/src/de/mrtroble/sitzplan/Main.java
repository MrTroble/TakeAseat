package de.mrtroble.sitzplan;

import java.awt.Desktop;
import java.io.*;
import java.net.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.beans.value.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.*;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.StringConverter;

public class Main extends Application{

	public static ArrayList<String> datenbank = new ArrayList<String>(); 
    public static ArrayList<ClassM8> plane = new ArrayList<ClassM8>(); 
    public static ArrayList<BanndPaare> bannlist = new ArrayList<BanndPaare>();
    public static ArrayList<String> exlist = new ArrayList<String>(); 
	private Group root = new Group();
	private Button creatplane;
	private static String[] argss;
	
	public static void main(String[] args) {
		argss = args;
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		if(initStage(stage) != 0)return;
		Scene sc = new Scene(root);
		sc.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(event.getCode().equals(KeyCode.F1)){
					System.exit(0);
				}
			}
		});
		stage.setResizable(false);
		stage.setScene(sc);
		stage.initStyle(StageStyle.UNIFIED);
		stage.centerOnScreen();
		stage.getIcons().add(new Image(Main.class.getResourceAsStream("back2.png")));
        stage.show();
	}

	private int initStage(final Stage st){
		if(argss.length > 0){
			File fl = new File(argss[0]);
			if(fl.exists()){
				try {
					initData(fl);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				initPlane();
				st.close();
				return 1;
			}
		}
		GridPane pane = new GridPane();
		pane.setVgap(10D);
		pane.setHgap(10D);	
		
		Button btn = new Button("Open Name List");
		buttonSettings(btn, Color.GRAY, 0, 5);
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				FileChooser chooser = new FileChooser();
				chooser.getExtensionFilters().add(new ExtensionFilter("ListFiles", "*.lis"));
				File fl = chooser.showOpenDialog(st);
				try {
					initData(fl);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				creatplane.setDisable(false);
			}
		});
		pane.add(btn, 0, 0);
		pane.setTranslateX(15);
		pane.setTranslateY(15);
		
		Button close = new Button("Close");
		buttonSettings(close, Color.GRAY, 0, 5);
		close.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});
		pane.add(close, 0, 1);
		
		Button lab = new Button("by MrTroble");
		buttonSettings(lab, Color.GRAY, 0, 5);
		lab.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					Desktop.getDesktop().browse(new URI("https://github.com/MrTroble"));
				} catch (Exception e) {
					Alert al = new Alert(AlertType.ERROR);
					al.setHeaderText(e.toString());
					al.setContentText(e.getMessage());
					al.showAndWait();
				}
			}
		});
		
		pane.add(lab, 1, 0);
		
		creatplane = new Button("Creat Plane");
		buttonSettings(creatplane, Color.GRAY, 0, 5);
		creatplane.setDisable(true);
		creatplane.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
			     initPlane();	
			}
		});
		pane.add(creatplane, 1, 1);
		
		root.getChildren().add(pane);
		return 0;
	}
	
	private void buttonSettings(Labeled btn ,Color cl,double inst,double radii){
		btn.setBackground(new Background(new BackgroundFill(cl, new CornerRadii(radii), new Insets(inst))));
		btn.setFont(new Font(20));
		btn.hoverProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(btn.isHover()){
					btn.setBackground(new Background(new BackgroundFill(cl.brighter(), new CornerRadii(radii-3), new Insets(inst))));
				}else{
					if(btn.isHover()){
						btn.setBackground(new Background(new BackgroundFill(cl.brighter(), new CornerRadii(radii-3), new Insets(inst))));
					}else{
						btn.setBackground(new Background(new BackgroundFill(cl, new CornerRadii(radii), new Insets(inst))));
					}
				}
			}
		});
		btn.pressedProperty().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(btn.isPressed()){
					btn.setBackground(new Background(new BackgroundFill(cl.darker(), new CornerRadii(radii), new Insets(inst))));
				}else{
					if(btn.isHover()){
						btn.setBackground(new Background(new BackgroundFill(cl.brighter(), new CornerRadii(radii-3), new Insets(inst))));
					}else{
						btn.setBackground(new Background(new BackgroundFill(cl, new CornerRadii(radii), new Insets(inst))));
					}	
				}
			}
		});
	}
	
	private void buttonSettings(TextField btn ,Color cl,double inst,double radii){
		btn.setBackground(new Background(new BackgroundFill(cl, new CornerRadii(radii), new Insets(inst))));
		btn.setFont(new Font(20));
		btn.hoverProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(btn.isHover()){
					btn.setBackground(new Background(new BackgroundFill(cl.brighter(), new CornerRadii(radii-3), new Insets(inst))));
				}else{
					if(btn.isHover()){
						btn.setBackground(new Background(new BackgroundFill(cl.brighter(), new CornerRadii(radii-3), new Insets(inst))));
					}else{
						btn.setBackground(new Background(new BackgroundFill(cl, new CornerRadii(radii), new Insets(inst))));
					}
				}
			}
		});
		btn.pressedProperty().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(btn.isPressed()){
					btn.setBackground(new Background(new BackgroundFill(cl.darker(), new CornerRadii(radii), new Insets(inst))));
				}else{
					if(btn.isHover()){
						btn.setBackground(new Background(new BackgroundFill(cl.brighter(), new CornerRadii(radii-3), new Insets(inst))));
					}else{
						btn.setBackground(new Background(new BackgroundFill(cl, new CornerRadii(radii), new Insets(inst))));
					}	
				}
			}
		});
	}
	
	private void buttonSettings(DatePicker btn ,Color cl,double inst,double radii){
		btn.setBackground(new Background(new BackgroundFill(cl, new CornerRadii(radii), new Insets(inst))));
		buttonSettings(btn.getEditor(), cl, inst, radii);
		btn.setEditable(false);
		btn.hoverProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(btn.isHover()){
					btn.setBackground(new Background(new BackgroundFill(cl.brighter(), new CornerRadii(radii-3), new Insets(inst))));
				}else{
					if(btn.isHover()){
						btn.setBackground(new Background(new BackgroundFill(cl.brighter(), new CornerRadii(radii-3), new Insets(inst))));
					}else{
						btn.setBackground(new Background(new BackgroundFill(cl, new CornerRadii(radii), new Insets(inst))));
					}
				}
			}
		});
		btn.pressedProperty().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(btn.isPressed()){
					btn.setBackground(new Background(new BackgroundFill(cl.darker(), new CornerRadii(radii), new Insets(inst))));
				}else{
					if(btn.isHover()){
						btn.setBackground(new Background(new BackgroundFill(cl.brighter(), new CornerRadii(radii-3), new Insets(inst))));
					}else{
						btn.setBackground(new Background(new BackgroundFill(cl, new CornerRadii(radii), new Insets(inst))));
					}	
				}
			}
		});
	}
	private void initData(File fl) throws FileNotFoundException {
		if(fl == null)return;
		Scanner sc = new Scanner(fl);
		while(sc.hasNextLine()){
			String str = sc.nextLine();
			if(str.equals("<EXCEPTION>"))break;
			datenbank.add(str);
		}
		while(sc.hasNextLine()){
			String str = sc.nextLine();
			if(str.equals("<BANNLIST>"))break;
			exlist.add(str);
		}
		while(sc.hasNextLine()){
			String[] str = sc.nextLine().split(":");
			bannlist.add(new BanndPaare(str[0], str[1]));
		}
		sc.close();
		this.fl = fl;
	}
	
	private File fl;
	
	private void initPlane() {
		plane.clear();
		for(String name : datenbank){
			ClassM8 clasM = new ClassM8(name, getRandomPlace(name)); 
			plane.add(clasM);
		}
		for(ClassM8 m8 : plane){
			System.out.println(m8);
		}
		
		Stage second = new Stage(StageStyle.UNIFIED);	
		
		Group gr = new Group();
				
		DatePicker pick = new DatePicker();
	    buttonSettings(pick, Color.GRAY, 0, 5);
		StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
	            DateTimeFormatter dateFormatter = 
	                DateTimeFormatter.ofPattern("dd-MM-yyyy");
	            @Override
	            public String toString(LocalDate date) {
	                if (date != null) {
	                    return dateFormatter.format(date);
	                } else {
	                    return "";
	                }
	            }
	            @Override
	            public LocalDate fromString(String string) {
	                if (string != null && !string.isEmpty()) {
	                    return LocalDate.parse(string, dateFormatter);
	                } else {
	                    return null;
	                }
	            }
	        };             
	    pick.setConverter(converter);
	    pick.setTranslateX(100);
		gr.getChildren().add(pick);
	  
		Button lab = new Button("by MrTroble");
		buttonSettings(lab, Color.GRAY, 0, 5);
		lab.setTranslateX(400);
		lab.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					Desktop.getDesktop().browse(new URI("https://github.com/MrTroble"));
				} catch (Exception e) {
					Alert al = new Alert(AlertType.ERROR);
					al.setHeaderText(e.toString());
					al.setContentText(e.getMessage());
					al.showAndWait();
				}
			}
		});
		gr.getChildren().add(lab);
		
		ClassPlane plan = new ClassPlane();
		plan.setVgap(10);
		plan.setHgap(10);
		plan.setTranslateY(80);
		gr.getChildren().add(plan);
		
	    Button btn = new Button("Save");
		buttonSettings(btn, Color.GRAY, 0, 5);
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				handleSaveFiles(plan, pick.getEditor().getText());
			}
		});
		btn.setDefaultButton(true);
		gr.getChildren().add(btn);
		
		Scene sc2 = new Scene(gr);
		second.setScene(sc2);
		second.setResizable(false);
		second.getIcons().add(new Image(Main.class.getResourceAsStream("back2.png")));
		second.show();
	}
	
	public void handleSaveFiles(ClassPlane plan, String text) {
		saveAsPng(plan, text);
		for(Node n : plan.getChildren()){
			if(n instanceof SitzBank){
				SitzBank ban = (SitzBank) n;
				bannlist.add(new BanndPaare(ban.getNachbar1().getName(), ban.getNachbar2().getName()));
			}
		}
		ArrayList<String> file = new ArrayList<String>();
		for (String string : datenbank) {
			file.add(string);
		}
		file.add("<EXCEPTION>");
		for (String string : exlist) {
			file.add(string);
		}
		file.add("<BANNLIST>");
		for (BanndPaare string : bannlist) {
			file.add(string.toString());
		}
		fl.delete();
		try {
			fl.createNewFile();
			FileWriter wr = new FileWriter(fl);
			for (String string : file) {
				wr.write(string + String.format("%n"));
				wr.flush();
			}
			wr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private short getRandomPlace(String m){
		int place = new Random().nextInt(datenbank.size());
		try{
		for(ClassM8 md : plane){
			if(md.getPlace() == place){
				return getRandomPlace(m);
			}
		}
		if(place%2 == 0){
			ClassM8 m8 = getM8((short) (place - 1));
			if(m8 != null){
				for (BanndPaare ban : bannlist) {
					if(ban.isBanned(m8.getName(), m)){
						System.out.println(ban);
						return getRandomPlace(m);
					}
				}
			}
		}else{
			ClassM8 m8 = getM8((short) (place - 1));
			if(m8 != null){
				for (BanndPaare ban : bannlist) {
					if(ban.isBanned(m8.getName(), m)){
						System.out.println(ban);
						return getRandomPlace(m);
					}
				}
			}
		}
		}catch(VirtualMachineError ex){
			System.err.println("An Error was noticed:");
			if(ex.getMessage() != null)System.err.println(ex.getMessage());
			System.err.println(ex.toString());
			if(ex instanceof StackOverflowError){
			System.err.println("It is a 0x000001 Warning Continuing Normalie");
			System.err.println("Return to Main Thread");
			initPlane();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return (short) place;
		
	}
	
	public static ClassM8 getM8(short zahl){
		for(ClassM8 m9 : plane){
			if(m9.getPlace() == zahl){
				return m9;
			}
		}
		return null;
	}
	
	@FXML
	public void saveAsPng(Node n,String dat) {
		SnapshotParameters par = new SnapshotParameters();
	    WritableImage image = n.snapshot(par, null);
	    
	    File file = new File("Sitzplan-" + dat + ".png");
	    
	    try {
		    file.createNewFile();
	        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
