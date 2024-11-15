package com.game.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.game.gfx.Texture;
import com.game.main.Game;
import com.game.object.util.Handler;
import com.game.object.util.ObjectId;

public class Player extends GameObject {
	private static final float WIDTH = 16;
	private static final float HEIGHT = 32;
	
	private Handler handler;
	private Texture tex;
	private BufferedImage[] spriteL,spriteS;
	
	private boolean jumped = false;
	private int health =2;
	
	public Player(float x, float y, int scale, Handler handler) {
		super(x,y, ObjectId.Player, WIDTH, HEIGHT, scale);
		this.handler = handler;
		tex= Game.getTexture(); 
		spriteL = tex.getMarioL();
		spriteS = tex.getMarioS();
	}

	@Override // ghi de tu lop cha GameObject
	public void tick() {
		setX(getVelX() + getX());
		setY(getVelY() + getY());
		
		applyGravity(); // cho vật thể đi xuống tắt đi thì obj đứng yên 
		
		collision(); // update va cham
		
	}

	@Override
	public void render(Graphics g) {
	    if (health == 1) {
	        g.drawImage(spriteS[0], (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight() / 2, null);
	    } else if (health == 2) {
	        g.drawImage(spriteL[0], (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
	    }
	    
	    // show hitbox
	    //showBounds(g);
	}
	
	private void collision() {
		for(int i = 0; i< handler.getGameObjs().size(); i++) {
			GameObject temp = handler.getGameObjs().get(i);
			
			if (temp.getId() == ObjectId.Block || temp.getId() == ObjectId.Pipe) {
				
				// xét xem có bị chạm dưới hay không
				if(getBounds().intersects(temp.getBounds())) {
					setY(temp.getY() - getHeight());
					setVelY(0);
					jumped = false;
				}
				
				// xxét xem có bị chạm trên hay không
				if (getBoundsTop().intersects(temp.getBounds())) {
					setY(temp.getY() + temp.getHeight());
					setVelY(0);
				}
				
				//xét xem có bị chạm phải hay không
				if (getBoundsRight().intersects(temp.getBounds())) {
					setX(temp.getX() - getWidth());
				}
				
				
				//xét xem có bị chạm trái hay không
				if (getBoundsLeft().intersects(temp.getBounds())) {
					setX(temp.getX() + getWidth());
				}
				
			}
			
		}
	}
	
	
	

	@Override
	public Rectangle getBounds() {
		return new Rectangle( (int) (getX() + getWidth()/2 - getWidth()/4),
				(int) (getY() + getHeight()/2),
				(int) getWidth()/2,
				(int) getHeight()/2);
	} // bản chất thì hàm này đã tạo hitbox dưới cho obj
	
	// cach tao hinh chu nhat tren 2d
	//ham Rectangle(int x, int y, int width, int height)
	// voi x,y la toa do cua dinh dau tien cua hinh chu nhat voi toaj do Oxy có gốc là đỉnh màn hình bên trái
	// Width và height lần lượt là cạnh và chiều cao 
	// ** chieu cao height tính xuống dưới 
	
	
	// ve hitbox tren object
	public Rectangle getBoundsTop() {
		return new Rectangle( (int) (getX() + getWidth()/2 - getWidth()/4),
				(int) getY(),
				(int) getWidth()/2,
				(int) getHeight()/2);
	}
	
	// tao hitbox hinh chu nhat ben phai obj
	public Rectangle getBoundsRight() {
		return new Rectangle( (int) (getX() + getWidth() -5),
				(int) getY() + 5,
				5,
				(int) getHeight() - 10);
	}
	
	// tao hitbox hinh chu nhat ben trai obj
	public Rectangle getBoundsLeft() {
		return new Rectangle((int) getX(),
				(int) (getY() + 5),
				5,
				(int) (getHeight() - 10));
	}
	
	private void showBounds(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g.setColor(Color.red);
		g2d.draw(getBounds());
		g2d.draw(getBoundsRight());
		g2d.draw(getBoundsLeft());
		g2d.draw(getBoundsTop());
			
	}
	
	public boolean hasJumped() {
		return jumped;
	}
	
	public void setJumped(boolean hasJumped) {
		jumped = hasJumped;
	}
	
	
	
}
