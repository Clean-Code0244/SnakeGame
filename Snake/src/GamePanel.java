import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.nio.charset.UnsupportedCharsetException;

import javax.print.attribute.standard.PresentationDirection;
import javax.swing.*;

import java.util.Random;
import java.util.Random.*;

import javax.swing.JPanel;
import javax.swing.plaf.InternalFrameUI;

public class GamePanel extends JPanel implements ActionListener{
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNIT = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE);
	static final int DELAY = 100;
	final int[] X = new int[GAME_UNIT];
	final int[] Y = new int[GAME_UNIT];
	int bodyparts = 6;
	int appleEaten ;
	int appleX;
	int appleY;
	static char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
public GamePanel() {
	random = new Random();
	this.setPreferredSize(new Dimension(SCREEN_WIDTH ,SCREEN_HEIGHT ));
	this.setFocusable(true);
	this.setBackground(Color.black);
	this.addKeyListener(new MyKeyAdaptor());
	startGame();
	
	
}
public void startGame() {
	newApple();
	running = true;
	timer =new Timer(DELAY, this);
	timer.start();
}
public void paintComponent(Graphics g) {
super.paintComponent(g);
draw(g);
}
public void draw(Graphics g) {
	
	if(running) {
	for(int i = 0; i <= SCREEN_HEIGHT / UNIT_SIZE ; i++) {
		g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
		g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
	}
	g.setColor(Color.red);
	g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
	
	for(int i = 0; i < bodyparts ; i++) {
		if(i == 0) {
			/*int x = (int) (Math.random() * 255);
			int y = (int) (Math.random() * 255);
			int z = (int) (Math.random() * 255);*/
			g.setColor(Color.yellow);
			g.fillRect(X[i], Y[i], UNIT_SIZE, UNIT_SIZE);
			
		}
		else {
			g.setColor(new Color(45, 180, 0));
			g.fillRect(X[i], Y[i], UNIT_SIZE, UNIT_SIZE);
		}
	}
	g.setColor(Color.red);
	g.setFont(new Font("Ink Free", Font.BOLD, 30));
	FontMetrics metrics = getFontMetrics(g.getFont());
	g.drawString("SCOR : " + appleEaten, (SCREEN_WIDTH - metrics.stringWidth("SCOR : " + appleEaten)) / 2, g.getFont().getSize());
	}
	else {
		GameOver(g);
	}
}
public void newApple() {
	appleX = random.nextInt(((int)SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
	appleY = random.nextInt(((int)SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
}
public void move() {
	for(int i = bodyparts ; i > 0 ; i--) {
		X[i] = X[i - 1];
		Y[i] = Y[i - 1];
	}
	switch(direction) {
	case 'U' : 
		Y[0] = Y[0] - UNIT_SIZE;
		break;
	case 'D' :
		Y[0] = Y[0] + UNIT_SIZE;
		break;
	case 'L' :
		X[0] = X[0] - UNIT_SIZE;
		break;
	case 'R' :
		X[0] = X[0] + UNIT_SIZE;
		break;
	
			
	}
	
}
public void checkApple() {
	
	if((X[0] == appleX) && (Y[0] == appleY)) {
		bodyparts++;
		appleEaten++;
		newApple();
	}
}
public void checkCollısıon() {
for(int i = bodyparts ; i < 0 ; i--) {
	
	if((X[0] == X[i]) && Y[0] == Y[i]) {
		running = false;
	}
}
	if(X[0] < 0) {
		running = false;
	}
    if(X[0] >= SCREEN_WIDTH) {
		running = false;
	}
    if(Y[0] < 0) {
		running = false;
	}
    if(Y[0] >= SCREEN_HEIGHT) {
		running = false;
	}
    if(!running) {
    	timer.stop();
    }

}
public void GameOver(Graphics g) {
	g.setColor(Color.red);
	g.setFont(new Font("Ink Free", Font.BOLD, 30));
	FontMetrics metrics1 = getFontMetrics(g.getFont());
	g.drawString("SCOR : " + appleEaten, (SCREEN_WIDTH - metrics1.stringWidth("SCOR : " + appleEaten)) / 2, g.getFont().getSize());
	g.setColor(Color.red);
	g.setFont(new Font("Ink Free", Font.BOLD, 75));
	FontMetrics metrics = getFontMetrics(g.getFont());
	g.drawString("GAME OVER", (SCREEN_WIDTH - metrics.stringWidth("GAME OVER")) / 2, SCREEN_HEIGHT / 2);
}

@Override
public void actionPerformed(ActionEvent e) {
	if(running) {
		move();
		checkApple();
	checkCollısıon();
	}
	repaint();
	
}

}
 class MyKeyAdaptor extends KeyAdapter{

	@Override
	public void keyPressed(KeyEvent e) {
		
		super.keyPressed(e);
		
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT : 
			if(GamePanel.direction != 'R') {
				GamePanel.direction = 'L';
			}
				break;
				
			case KeyEvent.VK_RIGHT : 
				if(GamePanel.direction != 'L') {
					GamePanel.direction = 'R';
				}
					break;
				case KeyEvent.VK_UP : 
					if(GamePanel.direction != 'D') {
						GamePanel.direction = 'U';
					}
						break;
					case KeyEvent.VK_DOWN : 
						if(GamePanel.direction != 'U') {
							GamePanel.direction = 'D';
							break;
			}
		}
	}
	
}
