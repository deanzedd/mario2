package com.game.object.util;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


//extending KeyAdapter allows the extending class (Keyinput) to become a KeyEvent Listener 
// A KeyEvent Listener takes action when certain events take place (keyPress, keyRelease)
public class KeyInput extends KeyAdapter {

	
	private boolean[] keyDown = new boolean[4];
	private Handler handler;
	
	public KeyInput(Handler handler) {
		this.handler = handler;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_ESCAPE) { //VK == virtual key
			System.exit(0);
		}
		
		
		// ý tưởng là khi thực hiện nhập từ bàn phím dichj chuyển tọa độ của obj
		// để ý tick method ở trước. ta chỉ cần update player velocity 1 cách tự động với tick 
		
		
		// jump
		if (key == KeyEvent.VK_W) {
			if(!handler.getPlayer().hasJumped()) {
				handler.getPlayer().setVelY(-15);
				keyDown[0] = true;
				handler.getPlayer().setJumped(true);
			}
		}
		
		// move left
		if (key == KeyEvent.VK_A) {
			handler.getPlayer().setVelX(-8);
			keyDown[1] = true;
		}
		
		// move right
		if (key == KeyEvent.VK_D) {
			handler.getPlayer().setVelX(8);
			keyDown[2] = true;
		}	
		
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_W) {
			keyDown[0] = false;
		}
		
		if (key == KeyEvent.VK_A) {
			keyDown[1] = false;
		}
		
		if (key == KeyEvent.VK_D) {
			keyDown[2] = false;
		}
		
		if (!keyDown[1] && !keyDown[2]) {
			handler.getPlayer().setVelX(0);
		}
	}
	
}
