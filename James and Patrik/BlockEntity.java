package my.mm.generator;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import my.project.gop.main.Vector2F;

public class BlockEntity extends Rectangle{

	private Vector2F pos;
	private BufferedImage block_image;
	private double rotation;
	private double rotation_speed = 0.8;
	private double blockSize = 24;
	private boolean isAlive;
	private int lifeTime;
	private boolean isDying;
	private float lifeFade = 1;
	
	public BlockEntity(Vector2F pos, BufferedImage block_image) {
		this.pos = pos;
		this.block_image = block_image;
		rotation = new Random().nextInt(180);
		lifeTime = new Random().nextInt(300);
		if(lifeTime < 300){
			lifeTime = new Random().nextInt(500);
		}
		setBounds((int)pos.xpos, (int)pos.ypos, (int)blockSize, (int)blockSize);
		isAlive = true;
	}

	public void tick(double deltaTime){
		if(isAlive){
			setBounds((int)pos.xpos, (int)pos.ypos, (int)blockSize, (int)blockSize);
			rotation -= rotation_speed;
			
			if(!isDying){
				if(lifeTime != 0){
					lifeTime--;
				}
				if(lifeTime == 0){
					isDying = true;
					
				}
			}
			
			if(isDying){
				if(lifeFade != 0.00001000){
					lifeFade -= 0.2;
				}
				
				if(lifeFade < 0.8){
					blockSize -= 0.2;
					pos.xpos += 0.1;
					pos.ypos += 0.1;
				}
				
				if(lifeFade <= 0.00001000){
					isAlive = false;
					World.removeDroppedBlockEntity(this);
				}
			}
		}
	}
	
	public void render(Graphics2D g){
		
		g.drawImage(block_image, 
				(int)pos.getWorldLocation().xpos, 
				(int)pos.getWorldLocation().ypos, 
				(int)blockSize, 
				(int)blockSize, null);
		
		g.drawRect((int)pos.getWorldLocation().xpos, 
				(int)pos.getWorldLocation().ypos, 
				(int)blockSize, 
				(int)blockSize);
		
		if(isAlive){
			if(isDying){
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, lifeFade));
			}
			g.rotate(Math.toRadians(rotation), 
					 pos.getWorldLocation().xpos + blockSize / 2, 
					 pos.getWorldLocation().ypos + blockSize / 2);
			//////////////////////////////////////////////////////
			
			g.drawImage(block_image, 
					(int)pos.getWorldLocation().xpos, 
					(int)pos.getWorldLocation().ypos, 
					(int)blockSize, 
					(int)blockSize, null);
			
//			g.drawRect((int)pos.getWorldLocation().xpos, 
//					(int)pos.getWorldLocation().ypos, 
//					(int)blockSize, 
//					(int)blockSize);
			
			//////////////////////////////////////////////////////
			g.rotate(-Math.toRadians(rotation), 
					 pos.getWorldLocation().xpos + blockSize / 2, 
					 pos.getWorldLocation().ypos + blockSize / 2);
		
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		}
	}
	
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
}
