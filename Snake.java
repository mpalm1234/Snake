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

	public static Snake snake;
	public JFrame jframe;
	public RenderPanel renderPanel;

	public Timer timer = new Timer(20, this);
	public ArrayList<Point> snakeParts = new ArrayList<Point>();


	public Point head, cherry;
	public int ticks, score, taillength, direction;
	public boolean over, paused;

	public Random random;
	public Dimension dim;

	public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
	public static final int FRAMESIZE = 750, SCALE = 10;
	public static final int XMAX = FRAMESIZE / SCALE;
	public static final int YMAX = (FRAMESIZE / SCALE) - 2;

	// Constructor:
	public Snake() {
		dim = Toolkit.getDefaultToolkit().getScreenSize();

		jframe = new JFrame("Snake");
		jframe.setSize(FRAMESIZE, FRAMESIZE);
		jframe.setLocation(dim.width / 2 - jframe.getWidth() / 2, 0);
		jframe.add(renderPanel = new RenderPanel());
		jframe.setResizable(false);
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.addKeyListener(this);
		startGame();
	}

	// Start Game:
	public void startGame() {
		over = false;
		paused = false;
		ticks = 0;
		score = 0;
		taillength = 10;
		direction = DOWN;

		random = new Random();
		head = new Point(0,-1);
		snakeParts.clear();
		cherry = new Point(random.nextInt(XMAX),random.nextInt(YMAX));
		timer.start();	
	}

	// What to perform on every tick:
	@Override
	public void actionPerformed(ActionEvent args) {
		renderPanel.repaint();
		ticks++;
		
		if (ticks % 2 == 0 && head != null && !over && !paused) {

			// 1. determine next head point
			if (direction == UP) {
				if (noTailAt(head.x, head.y - 1) && head.y - 1 >= 0) {
					head = new Point(head.x, head.y - 1);
				} else {
					over = true;
				}
			}
			if (direction == DOWN) {
				if (noTailAt(head.x, head.y + 1) && head.y + 1 < YMAX) {
					head = new Point(head.x, head.y + 1);
				} else {
					over = true;
				}
			}
			if (direction == LEFT) {
				if (noTailAt(head.x - 1, head.y) && head.x - 1 >= 0) {
					head = new Point(head.x - 1, head.y);
				} else {
					over = true;
				}
			}
			if (direction == RIGHT) {
				if (noTailAt(head.x + 1, head.y) && head.x + 1 < XMAX) {
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
			if (cherry != null && head.equals(cherry)) {
				score += 10;
				taillength++;
				cherry.setLocation(random.nextInt(XMAX),random.nextInt(YMAX));
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int i = e.getKeyCode();

		// Arrow keys to move snake
		if (!over && !paused) {
			if ((i == KeyEvent.VK_UP || i == KeyEvent.VK_W) && direction != DOWN) {
				direction = UP;
			}
			if ((i == KeyEvent.VK_DOWN || i == KeyEvent.VK_S) && direction != UP) {
				direction = DOWN;
			}
			if ((i == KeyEvent.VK_LEFT || i == KeyEvent.VK_A) && direction != RIGHT) {
				direction = LEFT;
			} 
			if ((i == KeyEvent.VK_RIGHT || i == KeyEvent.VK_D) && direction != LEFT) {
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
	
	// Collision with snake body
	public boolean noTailAt(int x, int y) {
		for (Point point : snakeParts) {
			if (point.equals(new Point(x, y))) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		snake = new Snake();
	}

}
