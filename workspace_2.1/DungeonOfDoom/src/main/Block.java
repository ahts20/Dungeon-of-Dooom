package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Block extends Rectangle {
	/*
	It is wise to declare it because if you don't declare one then when changing 
	the class it will get a different one generated automatically and the 
	serialisation will stop working.
	*/
	private static final long serialVersionUID = 1L;
	
	private BlockType blocktype;
	public int x, y;
	public int width;
	public int height;
	public boolean isVisible;
	
	public boolean rectangle, wall, gold, door;
	
	private boolean isSolid;

	public Block(int x, int y) {
		setBounds((int) x, (int) y, width, height);
		this.x = x;
		this.y = y;

	}

	public Block(int x, int y, int blockSize, BlockType blocktype) {
		setBounds((int) x, (int) y, width, height);
		this.x = x;
		this.y = y;
		this.width = blockSize;
		this.height = blockSize;
		this.blocktype = blocktype;
		init();
	}

	public void init() {
		switch (blocktype) {
		case RECTANGLE:
			rectangle = true;
			break;
		case WALL:
			wall = true;
			break;
		case GOLD:
			gold = true;
			break;
		case DOOR:
			door = true;
			isVisible = false;
			break;
		}
	}

	public void update() {
		setBounds((int) x, (int) y, width, height);
	}

	public void render(Graphics g) {
		if (rectangle == true) {
			g.setColor(Color.RED);
			g.drawRect((int) x, (int) y, width, height);
			// System.out.println("HEY");
		}

		if (wall == true) {
			g.setColor(Color.WHITE);
			g.drawRect((int) x, (int) y, width, height);
		}
		if (gold == true) {
			g.drawImage(Player.p[8], (int) x, (int) y, width, height, null);
			// g.setColor(Color.YELLOW);
			// g.drawRect((int)x, (int)y, width, height);
		}
		if (door == true && isVisible == false) {
			g.setColor(Color.WHITE);
			g.drawRect((int) x, (int) y, width, height);
		}
		if (door == true && isVisible == true) {
			g.setColor(Color.BLUE);
			g.drawRect((int) x, (int) y, width, height);

		}

	}

	public void changeGoldToFloor() {
		// Changes block type to floor - e.g. when it was gold and gets picked
		// up.
		this.rectangle = true;
		this.gold = false;
		this.wall = false;
	}

	public enum BlockType {
		RECTANGLE, WALL, GOLD, DOOR
	}
	
	public boolean isSolid() {
		return isSolid;
	}
	
	public Block isSolid(boolean isSolid){
		this.isSolid = isSolid;
		return this;
	}
	

}
