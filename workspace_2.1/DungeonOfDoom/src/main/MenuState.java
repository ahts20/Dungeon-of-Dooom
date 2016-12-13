package main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import GameStates.GameState;
import GameStates.GameStateManager;

public class MenuState extends GameState {
	
	GameStateButton play;
	GameStateButton quit;
	GameStateButton highScore;
	MouseInput mi;
	loadImage loader;
	BufferedImage backTitle;
	
	public MenuState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void init() {
		mi = new MouseInput();
		
		loader = new loadImage();
		try {
			backTitle = loader.LoadImageFrom("/dooomFINAL.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		play = new GameStateButton((Main.width / 2) - (GameStateButton.width / 2), 200, new LevelLoader(gsm), gsm, "PLAY");
		highScore = new GameStateButton((Main.width / 2) - (GameStateButton.width / 2), 350, new HighScoreState(gsm), gsm, "HIGH SCORE");
		quit = new GameStateButton((Main.width / 2) - (GameStateButton.width / 2), 500, new QuitState(gsm), gsm, "QUIT");
		
	}

	@Override
	public void update() {
		mi.update();
		play.update();
		highScore.update();
		quit.update();
		
		if(quit.isHeldOver()){
			if(quit.isPressed()){
				System.exit(1);
			}
		}
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.drawImage(backTitle, Main.width/4, 30, null);
		play.render(g);
		highScore.render(g);
		quit.render(g);
		g.clipRect(0,  0, Main.width, Main.height);
	}
}
