package Snake;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Snake implements ActionListener, KeyListener {
	
	public JFrame jframe;
	public RenderPanel renderPanel;
	public Toolkit toolkit;
	public Timer timer = new Timer(20, this);
	
	public static Snake snake;
	public Point head, cherry;
	public ArrayList<Point> snakeParts = new ArrayList<Point>();
	
	public int ticks, score, taillength, direction;
	public boolean over, paused;
	
	public Random random;
	public Dimension dim;
	
	public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
	public static final int SCALE = 10;
	
	// Constructor:
	public Snake() {
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		jframe = new JFrame("Snake");
		jframe.setVisible(true);
		jframe.setSize(800, 700);
		jframe.setResizable(false);
		jframe.setLocation(dim.width / 2 - jframe.getWidth() / 2, 0);
		jframe.add(renderPanel = new RenderPanel());
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.addKeyListener(this);
		startGame();
	}
	
	public void startGame() {
		over = false;
		paused = false;
		ticks = 0;
		score = 0;
		taillength = 10;
		direction = DOWN;
		
		random = new Random();
		head = new Point(0,0);
		cherry = new Point(random.nextInt(dim.width),random.nextInt(dim.height));
		
		snakeParts.clear();
		for(int i = 0; i < taillength; i++) {			
			snakeParts.add(new Point(head.x, head.y));			
		}
		timer.start();	
	}
	
	@Override
	public void actionPerformed(ActionEvent args) {
		renderPanel.repaint();
		ticks++;
		
		if (ticks % 2 == 0 && head != null && !over && !paused) {
			
			// 1. determine next head point
			if (direction == UP) {
				if(head.y - 1 >= 0) {
					head = new Point(head.x, head.y - 1);
				} else {
					over = true;
				} 
			}
			if (direction == DOWN) {
				if(head.y + 1 <= dim.height / SCALE) {
					head = new Point(head.x, head.y + 1);
				} else {
					over = true;
				}
			}
			if (direction == LEFT) {
				if (head.x - 1 >= 0) {
					head = new Point(head.x - 1, head.y);
				} else {
					over = true;
				}
			}
			if (direction == RIGHT) {
				if (head.x + 1 <= dim.width / SCALE) {
					head = new Point(head.x + 1, head.y);

				} else {
					over = true;
				}
			}
			
			// 2. adjust snakeParts
			snakeParts.add(new Point(head.x, head.y)); // Add new dot to next position (head)
			if(snakeParts.size() > taillength) {
				snakeParts.remove(0);                  // Remove dot from last position (tail)
			}

			// 3. check if cherry reached
			if (cherry != null) {
				//if(head.x == cherry.x && head.y == cherry.y);
				if(head.equals(cherry)) {
					score += 10;
					taillength++;
					cherry.setLocation(random.nextInt(dim.width),random.nextInt(dim.height));
				}
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int i = e.getKeyCode();
		
		// Arrow keys to move snake
		if (!over && !paused) {
			if ((i == KeyEvent.VK_UP || i == KeyEvent.VK_W)
					&& direction != DOWN) {
				direction = UP;
			}
			if ((i == KeyEvent.VK_DOWN || i == KeyEvent.VK_S)
					&& direction != UP) {
				direction = DOWN;
			}
			if ((i == KeyEvent.VK_LEFT || i == KeyEvent.VK_A)
					&& direction != RIGHT) {
				direction = LEFT;
			} 
			if ((i == KeyEvent.VK_RIGHT || i == KeyEvent.VK_D)
					&& direction != LEFT) {
				direction = RIGHT;
			}
		}

		// Create new snake game
		if (i == KeyEvent.VK_SPACE) {
			if (over) {
				startGame();
			} else {
				paused = !paused;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
	}
	
	public static void main(String[] args) {
		snake = new Snake();
	}

	

	
}
