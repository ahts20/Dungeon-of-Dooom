package main;

import java.awt.event.KeyEvent;
import java.util.concurrent.CopyOnWriteArrayList;

public class Player2 extends Player {

	@Override
	public void keyPressed(KeyEvent e) {
		String Xdirection = "";
		String Ydirection = "";
		// Set player direction according to the key presses.
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_RIGHT) {
			P2Xdirection = "RIGHT";
			// System.out.println("Set X direction to Right: " +
			// this.Xdirection);

		}
		if (key == KeyEvent.VK_LEFT) {
			P2Xdirection = "LEFT";
		}
		if (key == KeyEvent.VK_UP) {
			P2Ydirection = "UP";
		}
		if (key == KeyEvent.VK_DOWN) {
			P2Ydirection = "DOWN";
		}

		if (key == KeyEvent.VK_R) {
			World.resetWorld();
			gsm.states.push(new LevelLoader(gsm, "Not", "map2"));
			gsm.states.peek().init();
		}
		
		
		if (key == KeyEvent.VK_ESCAPE) {
			System.exit(1);
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// Stop moving when player stops pressing the button.
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_RIGHT) {
			this.P2Xdirection = "faceright";
			// System.out.println("Facing right");
		}
		if (key == KeyEvent.VK_LEFT) {
			this.P2Xdirection = "faceleft";
		}
		if (key == KeyEvent.VK_UP) {
			this.P2Ydirection = "faceup";
		}
		if (key == KeyEvent.VK_DOWN) {
			this.P2Ydirection = "facedown";
		}
		//Overwrite to prevent constant walking.

	}

	@Override
	public void keyTyped(KeyEvent k) {

	}

	protected void movePlayer(CopyOnWriteArrayList<Block> blocks) {
		// Make player move in desired direction.
		String Xdirection = "";
		String Ydirection = "";


		Xdirection = P2Xdirection;
		Ydirection = P2Ydirection;

		if (Xdirection == "RIGHT") {
			if (detectTouchingWall(blocks)) {
				Xdirection = "LEFT";
				this.X -= this.speed;
			} else {
				this.X += this.speed;
				this.status = "faceright";
				if(detectTouchingDoor(blocks)){
					World.resetWorld();
					gsm.states.push(new LevelLoader(gsm, "Not", "map2"));
					gsm.states.peek().init();
				}
			}
		}
		if (Xdirection.equals("LEFT")) {
			if (detectTouchingWall(blocks)) {
				Xdirection.equals("RIGHT");
				this.X += this.speed;
			} else {
				this.X -= this.speed;
				this.status = "faceleft";
				if(detectTouchingDoor(blocks)){
					World.resetWorld();
					gsm.states.push(new LevelLoader(gsm, "Not", "map2"));
					gsm.states.peek().init();
				}
			}
		}
		if (Ydirection == "UP") {
			if (detectTouchingWall(blocks)) {
				Ydirection = "DOWN";
				this.Y += this.speed;
			} else {
				this.Y -= this.speed;
				this.status = "faceup";
				if(detectTouchingDoor(blocks)){
					World.resetWorld();
					gsm.states.push(new LevelLoader(gsm, "Not", "map2"));
					gsm.states.peek().init();
				}
			}
		}
		if (Ydirection == "DOWN") {
			if (detectTouchingWall(blocks)) {
				Ydirection = "UP";
				this.Y += this.speed;
			} else {
				this.Y += this.speed;
				this.status = "facedown";
				if(detectTouchingDoor(blocks)){
					World.resetWorld();
					gsm.states.push(new LevelLoader(gsm, "Not", "map2"));
					gsm.states.peek().init();
				}
			}
		}
	}

}
