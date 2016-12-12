import static org.junit.Assert.*;

import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.Before;
import org.junit.Test;

import GameStates.GameStateManager;
import main.Block;
import main.Block.BlockType;
import main.Bot;
import main.Player;
import main.World;

public class TestBot {
	Bot bot;
	Player p;
	World world;
	
	CopyOnWriteArrayList<Block> blocks = new CopyOnWriteArrayList<Block>();
	
	@Before
	public void setUpTestVariables() throws Exception {
		bot = new Bot();
		p = new Player();
		bot.init(p);
		
		blocks.add(new Block(0,0,10,BlockType.RECTANGLE));
		// Create Level loader object and add player and blocks.
		world = new World("testWorld", new GameStateManager());
		world.player = p;
		world.blocks = blocks;
		world.bot = bot;
		
	}

	@Test
	public void testMoveToPlayerLeft() {
		bot.setX(500);
		bot.setY(10);
		p.setX(10);
		p.setY(10);
				
		world.update();

		assertEquals("LEFT", bot.getBotXdirection());
	}

	@Test
	public void testMoveToPlayerRight() {
		bot.setX(10);
		bot.setY(10);
		p.setX(500);
		p.setY(10);

		world.update();

		assertEquals("RIGHT", bot.getBotXdirection());
	}
	@Test
	public void testMoveToPlayerUp() {
		bot.setX(10);
		bot.setY(500);
		p.setX(10);
		p.setY(10);

		world.update();

		assertEquals("UP", bot.getBotYdirection());
	}
	@Test
	public void testMoveToPlayerDown() {
		bot.setX(10);
		bot.setY(100);
		p.setX(10);
		p.setY(500);

		world.update();

		assertEquals("DOWN", bot.getBotYdirection());
	}
	@Test
	public void testBotStealsGold() {
		int prevGold = 50;
		p.setScore(prevGold);
		
		bot.setX(10);
		bot.setY(10);
		p.setX(10);
		p.setY(10);

		world.update();

		assertEquals(true, p.getScore()<prevGold);
	}
	@Test
	public void testBotBecomesInactive() {
		
		bot.setX(10);
		bot.setY(10);
		p.setX(10);
		p.setY(10);
		//Active before
		assertEquals(true, bot.getActive());
		//Run logic
		world.update();
		//Becomes inactive
		assertEquals(false, bot.getActive());
	}
	@Test
	public void testBotPushesPlayerDown() {
		bot.setX(10);
		bot.setY(10);
		p.setX(10);
		p.setY(11);
		//Run logic
		world.update();
		//Becomes inactive.
		assertEquals(true, p.getY() > 10);
	}
	@Test
	public void testBotPushesPlayerUp() {
		bot.setX(10);
		bot.setY(10);
		p.setX(10);
		p.setY(9);
		//Run logic
		world.update();
		//Becomes inactive.
		assertEquals(true, p.getY() < 10);
	}
	@Test
	public void testBotPushesPlayerRight() {
		bot.setX(10);
		bot.setY(10);
		p.setX(11);
		p.setY(10);
		//Run logic
		world.update();
		//Becomes inactive.
		assertEquals(true, p.getX() > 10);
	}
	@Test
	public void testBotPushesPlayerLeft() {
		bot.setX(10);
		bot.setY(10);
		p.setX(9);
		p.setY(10);
		//Run logic
		world.update();
		//Becomes inactive.
		assertEquals(true, p.getX() < 10);
	}
}