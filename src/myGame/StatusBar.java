package myGame;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import popup.PopupAttack;
import troop.Catapult;
import troop.Knight;
import troop.Spearman;

public class StatusBar {
	private Text textCastleName = new Text();
	private Text textCastleOwner = new Text();
	private Text textCastleLevel = new Text();
	private Text textCastleMoney = new Text();
	
	private Text textCastleSpear = new Text();
	private Text textCastleKnight = new Text();
	private Text textCastleCatapult = new Text();
	private Text textCastleTroopTotal = new Text();
	
	private Text textProductions[] = new Text[Settings.NUM_PRODUCTION_SHOWN];
	
	private GridPane statusBar = new GridPane();
	private GridPane statsCastle = new GridPane();
	private GridPane statsTroops = new GridPane();
	private GridPane statsProduction = new GridPane();
    
	private Button addSpearmanButton = new Button("+");
	private Button addKnightButton = new Button("+");
	private Button addCatapultButton = new Button("+");
	private Button addLevelButton = new Button("+");
	private Button attackButton = new Button("Attaque");
	
	private PopupAttack popupAttack;
	
	/**
	 * Not sure what this warning was about, I searched on the Internet but
	 * all example used it in a similar way as me.
	 */
	@SuppressWarnings("static-access")
	StatusBar(Group root) {

		statusBar.getStyleClass().add("statusBar");
		statusBar.relocate(0, Settings.SCENE_HEIGHT - Settings.STATUS_BAR_HEIGHT);
		statusBar.setPrefSize(Settings.SCENE_WIDTH * 1.1f, Settings.STATUS_BAR_HEIGHT * 1.1f);
		
		statusBar.getColumnConstraints().add(new ColumnConstraints(700));
		statusBar.getColumnConstraints().add(new ColumnConstraints(500));
		statusBar.getColumnConstraints().add(new ColumnConstraints(500));
		
	    root.getChildren().add(statusBar);
	    
	    statusBar.add(statsCastle, 0, 0);
	    statusBar.add(statsTroops, 1, 0);
	    statusBar.add(statsProduction, 2, 0);
		
	    textCastleName.getStyleClass().add("title");
	    attackButton.getStyleClass().add("normal");
		textCastleOwner.getStyleClass().add("subtitle");
		textCastleLevel.getStyleClass().add("normal");
		textCastleMoney.getStyleClass().add("normal");
		
		textCastleSpear.getStyleClass().add("normal");
		textCastleKnight.getStyleClass().add("normal");
		textCastleCatapult.getStyleClass().add("normal");
		textCastleTroopTotal.getStyleClass().add("normal");
		
		// Castle Stats
		statsCastle.getColumnConstraints().add(new ColumnConstraints(150));
		statsCastle.getColumnConstraints().add(new ColumnConstraints(150));
		addLevelButton.getStyleClass().add("addButton");
		
		addLevelButton.setOnAction(value ->  {
			Main.selectedCastle.levelUp();
        	refreshStatusBar();
        });
		
		addLevelButton.setTooltip(new Tooltip(""));
		
		statsCastle.add(attackButton, 3, 0);
		statsCastle.add(textCastleName, 0, 0, 2, 1);
		statsCastle.add(textCastleOwner, 0, 1, 2, 1);
		statsCastle.add(textCastleLevel, 0, 2);
		statsCastle.add(textCastleMoney, 0, 3);
		statsCastle.add(addLevelButton, 1, 2);
		
		statsCastle.setMargin(textCastleOwner, new Insets(0,0,20,0));
		
		popupAttack = new PopupAttack(root);
		attackButton.setOnAction(value ->  {
        	popupAttack.show();
        	popupAttack.refresh();
        });
		
		//Troops Stats
		statsTroops.add(textCastleSpear, 0, 0);
		statsTroops.add(textCastleKnight, 0, 1);
		statsTroops.add(textCastleCatapult, 0, 2);
		statsTroops.add(textCastleTroopTotal, 0, 3);
        
        addSpearmanButton.getStyleClass().add("addButton");
        addKnightButton.getStyleClass().add("addButton");
        addCatapultButton.getStyleClass().add("addButton");
        
        
        // Adds tooltips
        Spearman tmpSpear = new Spearman();        
        addSpearmanButton.setTooltip(new Tooltip( "Vitesse : " + tmpSpear.getSpeed()
        									   	+ "\nVie : " + tmpSpear.getHealth()
        										+ "\nDégats : " + tmpSpear.getDamage()
        										+ "\n\nCoût: " + tmpSpear.getCostProduction()
        										+ "\nTemps : " + tmpSpear.getTimeProduction()));

        Knight tmpKnight = new Knight(); 
        addKnightButton.setTooltip(new Tooltip( "Vitesse : " + tmpKnight.getSpeed()
											   	+ "\nVie : " + tmpKnight.getHealth()
												+ "\nDégats : " + tmpKnight.getDamage()
												+ "\n\nCoût: " + tmpKnight.getCostProduction()
												+ "\nTemps : " + tmpKnight.getTimeProduction()));
        
        Catapult tmpCatapult = new Catapult(); 
        addCatapultButton.setTooltip(new Tooltip( "Vitesse : " + tmpCatapult.getSpeed()
											   	+ "\nVie : " + tmpCatapult.getHealth()
												+ "\nDégats : " + tmpCatapult.getDamage()
												+ "\n\nCoût: " + tmpCatapult.getCostProduction()
												+ "\nTemps : " + tmpCatapult.getTimeProduction()));
        
        
        statsTroops.getColumnConstraints().add(new ColumnConstraints(150));

        addSpearmanButton.setOnAction(value ->  {
        	Main.selectedCastle.addProduction(new Spearman());
        	refreshStatusBar();
        });

        addKnightButton.setOnAction(value ->  {
        	Main.selectedCastle.addProduction(new Knight());
        	refreshStatusBar();
        });

        addCatapultButton.setOnAction(value ->  {
        	Main.selectedCastle.addProduction(new Catapult());
        	refreshStatusBar();
        });
		
		statsTroops.add(addSpearmanButton, 1, 0);
		statsTroops.add(addKnightButton, 1, 1);
		statsTroops.add(addCatapultButton, 1, 2);
		
		statsTroops.setMargin(textCastleTroopTotal, new Insets(20,0,0,0));
		
		// Production stats
		/* Initialize the text arrays */		
		for(int i = 0; i < textProductions.length; i++) {textProductions[i] = new Text();}
		
		int index = 0;
		for (Text text: textProductions) {
			
			text.getStyleClass().add("normal");
			statsProduction.add(text, 0, index);
			index++;
		}
	}
	
	
	/* Refresh all the content of the Text element in the statusBar.
	 * All the information is regarding the castle: selectedCastle.*/
	public void refreshStatusBar() {
		
		/* Show more information if the castle is owned by the player */
		boolean bool = Main.selectedCastle.getOwner().isPlayer();
		attackButton.setVisible(bool);
		statsProduction.setVisible(bool);
		addSpearmanButton.setVisible(bool);
		addKnightButton.setVisible(bool);
		addCatapultButton.setVisible(bool);
		addLevelButton.setVisible(bool);
				
		// Castle Stats
		textCastleName.setText(Main.selectedCastle.getNickname());
		textCastleName.setFill(Main.selectedCastle.getOwner().getColor());
		if (Main.selectedCastle.getOwner().isNeutral()) {
			textCastleOwner.setText("Appartient à un baron sans ambition");
		} else {
			textCastleOwner.setText("Appartient à : " + Main.selectedCastle.getOwner().toString());
		}
		textCastleLevel.setText("Niveau : " + Integer.toString(Main.selectedCastle.getLevel()));
		textCastleMoney.setText("Trésor : " + Integer.toString(Main.selectedCastle.getMoney()));
		
		//Troops Stats
		textCastleSpear.setText("Piquier : " + Integer.toString(Main.selectedCastle.getTroops(new Spearman()).size()));
		textCastleKnight.setText("Chevalier : " + Integer.toString(Main.selectedCastle.getTroops(new Knight()).size()));
		textCastleCatapult.setText("Onagre : " + Integer.toString(Main.selectedCastle.getTroops(new Catapult()).size()));
		textCastleTroopTotal.setText("Total : " + Integer.toString(Main.selectedCastle.getTroops().size()));
		
		//Production Stats
		Production tmp;
		int index = 0;
		for (Text text: textProductions) {
			tmp = Main.selectedCastle.getProduction(index);
			text.setText("");
			if (tmp != null) {
				text.setText(tmp.getName() + " (" + (tmp.getTotalTime() - tmp.getTimeRemaining()) + "/" + tmp.getTotalTime() + ")");
			}
			index++;
		}
		
		// Refresh Tooltip
		addLevelButton.getTooltip().setText("Coût : " + Main.selectedCastle.costToLevel() + "\nTemps : " + Main.selectedCastle.timeToLevel());;
		
	}

	public PopupAttack getPopupAttack() {
		return popupAttack;
	}
	
	
}
