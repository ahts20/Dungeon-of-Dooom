package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class Player extends Rectangle implements KeyListener{

	public static int playerWidth = 40;
	public static int playerHeight = 40;
	private float X = (Main.width / 2)  - (playerWidth / 2);
	private float Y = (Main.height / 2) - (playerHeight / 2);
	private static String Xdirection = "";
	private static String Ydirection = "";
	private static float speed = 5;
	public static String staus = "standing";
	private static int score = 0;
	
	//Dimensions of Square/Screen following player.
	private int renderDistanceW = 20;
	private int renderDistanceH = 20;
	
	public static Rectangle render;
	
	private BufferedImage spriteSheet = null;
	private BufferedImage[] p=new BufferedImage[9];

	public void init() {
		loadImage loader = new loadImage();
		try {
			spriteSheet = loader.LoadImageFrom("/SpriteSheet1.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Assign animation images to the array p array.
		SpriteSheet ss = new SpriteSheet(spriteSheet);
		for (int i=1;i<=8;i++){
			p[i] = ss.grabImage(i, 1, 145/8, 32);
		}	
		
		//Draw large rectangle following player.
		render = new Rectangle(
				(int)X - ((renderDistanceW * 20) / 2), 
				(int)Y - ((renderDistanceH * 20) / 2), 
				renderDistanceW * 20, 
				renderDistanceH * 20);
	}

	public void update(CopyOnWriteArrayList<Block> blocks) {
		
		//Update the large rectangle coordinates.
		render = new Rectangle(
				(int)X - ((renderDistanceW * 20) / 2), 
				(int)Y - ((renderDistanceH * 20) / 2), 
				renderDistanceW * 20, 
				renderDistanceH * 20);
		
		//If player reaches the boundaries stop moving.
		if(X >= Main.width - 100 || X <= -10){
			Xdirection = "";
		}
		if(Y >= Main.height - 100 || Y <= -10){
			Ydirection = "";
		}
		//Update player bounds
		setBounds((int)x, (int)y, width, height);
		//Check for gold collision and update score and gold.
		checkGoldTouch(blocks);
		//make player change its co-ordinates.
		movePlayer();
		
	}
	
	private void checkGoldTouch(CopyOnWriteArrayList<Block> blocks){
		for (Block i : blocks){
			//System.out.println("Checking " + i);
			if (i.gold && isTouching(i.x, i.y, i.width, i.height)){
				//contains and intersects isn't working - rest does though and changes as needed
				//i.intersects(this.X, this.Y, this.width, this.height)
				//isTouching(i.X, this.Y, this.width, this.height)
				i.changeToFloor();
				//System.out.println("Detected gold");
				this.score += 10;
			}
		}
	}
	
	private boolean isTouching (int x, int y, int width, int height){
		if (this.X >= x && this.X <= x + width){
			if (this.Y >= y && this.X <= y + height){
				//System.out.println("touching gold");
				return true;
			}
		}
		return false;
	}
	
	public void render(Graphics g, int i) {
//		g.setColor(Color.WHITE);
//		g.drawRect((int)X, (int)Y, playerWidth, playerHeight);
		
		//Draw the player to the graphics object
		g.drawImage(p[i], (int) X, (int) Y, null);
		
		//Draw the rectangle to the graphics object
		g.drawRect((int)(X) - ((renderDistanceW * 20) / 2), 
				(int)(Y) - ((renderDistanceH * 20) / 2), 
				renderDistanceW * 20, 
				renderDistanceH * 20);
	
	}
	
	//Move player.
	private void movePlayer(){
		//Make player move in desired direction.
		if(Xdirection == "RIGHT"){
			X += speed;
			staus = "moveright";
		}
		if(Xdirection == "LEFT"){
			X -= speed; 
			staus = "moveleft";
		}
		if(Ydirection == "UP"){
			Y -= speed;
			staus = "moveup";
		}
		if(Ydirection == "DOWN"){
			Y += speed; 
			staus = "movedown";
		}
		if ((Xdirection == "") && (Ydirection == "")){
			staus = "standing";
		}
		
	}
	
	//getters
	public double getX(){
		return X;
	}
	public double getY(){
		return Y;
	}
	//Setters
	public void setX(float x){
		X = x;
	}
	public void setY(float y){
		Y = y;
	}
	 // Control and use player input.
	@Override
	public void keyPressed(KeyEvent e) {
		//Set player direction according to the key presses.
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_D){
			Xdirection = "RIGHT";
		}
		if(key == KeyEvent.VK_A){
			Xdirection = "LEFT";
		}
		if(key == KeyEvent.VK_W){
			Ydirection = "UP";
		}
		if(key == KeyEvent.VK_S){
			Ydirection = "DOWN";
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//Stop moving when player stops pressing the button.
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_D){
			Xdirection = "";
		}
		if(key == KeyEvent.VK_A){
			Xdirection = "";
		}
		if(key == KeyEvent.VK_W){
			Ydirection = "";
		}
		if(key == KeyEvent.VK_S){
			Ydirection = "";
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}