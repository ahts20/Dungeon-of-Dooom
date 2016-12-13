import static org.junit.Assert.*;

import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.Before;
import org.junit.Test;

import GameStates.GameStateManager;
import main.Block;
import main.Bot;
import main.Player;
import main.Player1;
import main.Player2;
import main.World;
import main.Block.BlockType;

public class AvatarTest {
	Bot bot;
	Player p, p2;
	World world;
	
	CopyOnWriteArrayList<Block> blocks = new CopyOnWriteArrayList<Block>();
	
	@Before
	public void setUpTestVariables() throws Exception {
		bot = new Bot();
		p = new Player1();
		p2 = new Player2();
		bot.init(p, p2, 500, 500);
		
		blocks.add(new Block(0,0,10,BlockType.RECTANGLE));
		// Create Level loader object and add player and blocks.
		world = new World("testWorld", new GameStateManager());
		world.player1 = p;
		world.blocks = blocks;
		world.bot = bot;
	}

}
