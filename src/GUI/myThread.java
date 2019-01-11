package GUI;

import java.io.IOException;
import java.util.ArrayList;

import GIS.game;
import GUI.guiGame;
import Map.converts;
import Map.pix;

public class myThread extends Thread {

	volatile boolean moved;
	
	public myThread(boolean m) {
		
		moved=m;
	}
	
	public void run() {
		
		while(moved) {		
		
		pix player=new pix(guiGame.player.getX(),guiGame.player.getY());
		pix pressed=new pix(guiGame.x,guiGame.y);
	
		double angle=converts.angleBet2Pixels(player,pressed,guiGame.HEIGHT,guiGame.WIDTH);
			guiGame.getPlay1().rotate(angle);
			System.out.println(guiGame.getPlay1().getStatistics());
			
			//game!
			ArrayList<String> gameAfterStep=guiGame.getPlay1().getBoard();
			game.clear();
			game.createNewGame(gameAfterStep);
			try {
				game.paintGame(guiGame.HEIGHT, guiGame.WIDTH);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	}
	
	}
}
