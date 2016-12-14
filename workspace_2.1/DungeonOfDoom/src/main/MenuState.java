package main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import GameStates.GameState;
import GameStates.GameStateManager;
/**
* MenuState class, called when the game is started.
* Uses HighScoreState, CreditsState, HowToState and QuitState classes to give user further interaction.
* Uses GameStateButton class to allow the user to change the game states when clicked on the buttons.
* Uses MouseInput class to allow user to use the mouse for interaction.
* (Extends GameState to use the init(), update() and render() functions which connect to the GameLoop and JFrame respectively.)
*
* @version 1.0
* @release 13/12/2016
* @See MenuState.java
*/
public class MenuState extends GameState {
	//Classes Declared
	GameStateButton play;
	GameStateButton quit;
	GameStateButton highScore;
	MouseInput mi;
	loadImage loader;
	//Header Image
	BufferedImage backTitle,logo;
	/**
	 * Constructor. Sets the field values.
	 * @param GameStateManager
	 * 		calls the GameStateManager class
	 */
	public MenuState(GameStateManager gsm) {
		super(gsm);
	}
	/**
	 * Part of GameLoop, Initialises the declared classes and fields.
	 * Specifies the GameStates to change to, for each GameStateButton.
	 * @exception IOException
	 * 		calls the exception when no header image found
	 * @see GameStates.GameState#init()
	 */
	@Override
	public void init() {
		mi = new MouseInput();
		
		loader = new loadImage();
		try {
			backTitle = loader.LoadImageFrom("/dooomFINAL.png");
			logo = loader.LoadImageFrom("/LOGO1.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		play = new GameStateButton((Main.width / 2) - (GameStateButton.width / 2), 200, new LevelLoader(gsm), "PLAY");
		highScore = new GameStateButton((Main.width / 2) - (GameStateButton.width / 2), 350, new HighScoreState(gsm), "HIGH SCORE");
		quit = new GameStateButton((Main.width / 2) - (GameStateButton.width / 2), 500, new QuitState(gsm), "QUIT");
		
	}
	/**
	 * Part of GameLoop, Updates the declared classes and fields (60 FPS)
	 * Keeps track of mouse position
	 * @see GameStates.GameState#update()
	 */
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
	/**
	 * Part of GameLoop, Sets the graphics for JFrame
	 * Draws the buttons on the Screen and sets their colour
	 * @see GameStates.GameState#render(java.awt.Graphics)
	 */
	@Override
	public void render(Graphics g) {
		Font font1 = new Font("Bloodthirsty", Font.PLAIN, 40);
		g.setColor(Color.pink);
		g.setFont(font1);
		g.drawString("kk", 100, 100);
		g.setColor(Color.white);
		g.drawImage(backTitle, Main.width/4, 30, null);
		g.drawImage(logo, Main.width/4+240, Main.height-150, null);
		play.render(g);
		highScore.render(g);
		quit.render(g);
		g.clipRect(0,  0, Main.width, Main.height);
	}
}
