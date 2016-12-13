package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;
import main.Block.BlockType;
import GameStates.GameStateManager;

public class World{

	public Player player1, player2;
	public Bot bot;
	public Block block;
	private GameStateManager gsm;
	
	//Used by enoughGoldPickedUp() to know how much gold was originally in place.
	private int totalGold;

	private static BufferedImage map;
	public static loadImage loader;
	
	private String worldName;
	
	public static CopyOnWriteArrayList<Block> blocks = new CopyOnWriteArrayList<Block>();

	public World(GameStateManager gsm){
		this.gsm = gsm;
	}
	
	public World(String worldName, GameStateManager gsm) {
		this.gsm = gsm;
	}

	public void init() {
		loader = new loadImage();

		player2 = new Player();
		player1 = new Player();
		
		player2.init(300,600,2);
		player1.init(500, 600, 1);
		

		bot = new Bot();
		bot.init(player1, player2);
		
	}


	public void update() {
		player1.update(blocks);
		player2.update(blocks);
		checkGoldTakenAndOpenDoor();
		bot.update(blocks);
		
	}
	private void checkGoldTakenAndOpenDoor(){
		if (enoughGoldPickedUp()) {
			for (Block i : blocks) {
				if (i.door)
					i.isVisible = true;
			}
		}
	}
	private boolean enoughGoldPickedUp() {
		int minimumPercentToOpenDoor = 50;
		int currentGold = returnCurrentGold();
		//System.out.println(currentGold + "  " + this.totalGold);
		if(currentGold == 0)
			return true;
		return (Math.abs((currentGold/(float) this.totalGold)*100) < minimumPercentToOpenDoor);
	}
	private int returnCurrentGold() {
		int gold = 0;
		for (Block i : blocks) {
			if (i.gold == true)
				gold ++;
		}
		return gold;
	}
	public void render(Graphics g) {
		for (Block i : blocks) {
			i.render(g);
		}
		player2.render(g, 3);
		if (player1.status == "facedown") {
			player1.render(g, 3);
		} else if (player1.status == "faceleft") {
			player1.render(g, 0);
		} else if (player1.status == "faceright") {
			player1.render(g, 1);
		} else if (player1.status == "faceup") {
			player1.render(g, 2);
		}
		
		if (bot.botState == "facedown") {
			bot.render(g, 6);
		} else if (bot.botState == "faceleft") {
			bot.render(g, 4);
		} else if (bot.botState == "faceright") {
			bot.render(g, 5);
		} else if (bot.botState == "faceup") {
			bot.render(g, 7);
		}
//		bot.render(g);
	}

	public void generate(String world_name) {
		// Generates the world from the map PNG image.
		map = null;
		// Syntactic sugarrrr - match with block height and width to avoid gaps.
		int blockSize = 25;
		try {
			map = loader.LoadImageFrom("/" + world_name + ".png");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Iterate through each pixel in the image.
		for (int x = 0; x < 50; x++) {
			for (int y = 0; y < 50; y++) {
				int mapColours = map.getRGB(x, y);

				switch (mapColours & 0xFFFFFF) {
				// If Grey set as floor/RECTANGLE
				case 0x808080:
					blocks.add(new Block(x * blockSize, y * blockSize, blockSize, BlockType.RECTANGLE));
					break;
				// If Black set as Wall.
				case 0x000000:
					blocks.add(new Block(x * blockSize, y * blockSize, blockSize, BlockType.WALL).isSolid(true));
					break;
				// If Yellow set as Gold.
				case 0xffff00:
					blocks.add(new Block(x * blockSize, y * blockSize, blockSize, BlockType.GOLD));
					break;
				// If Blue set as Door.
				case 0x0080FF:
					blocks.add(new Block(x * blockSize, y * blockSize, blockSize, BlockType.DOOR));
					break;
				}
			}
		}
		//Save total gold as an attribute, so it can be used to 
		//detect is enough gold is collected.
		this.totalGold = returnCurrentGold();
		System.out.println(returnCurrentGold());
	}
	public void addPlayer(Player player) {
		this.player1 = player;
	}
	
	public static void resetWorld(){
		blocks.clear();		
	}
	
	public void changeToWorld(String wn, String mn){
	
		if(wn != worldName){
			resetWorld();
			gsm.states.push(new LevelLoader(gsm, wn, mn));
			gsm.states.peek().init();
		} else {
			System.out.println("ALREADY IN THE WORLD");
		}
	}	
}
