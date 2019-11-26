package Snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class RenderPanel extends JPanel {

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Snake snake = Snake.snake;
		String string;

		//Background
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, Snake.FRAMESIZE, Snake.FRAMESIZE);

		//Draw Snake
		if (snake != null) {
			for(Point point : snake.snakeParts) {
				g.setColor(Color.black);	
				g.drawRect(point.x * Snake.SCALE, point.y * Snake.SCALE, Snake.SCALE, Snake.SCALE);
				g.setColor(Color.green);	
				g.fillRect(point.x * Snake.SCALE, point.y * Snake.SCALE, Snake.SCALE, Snake.SCALE);
			}

			//Draw Cherry
			g.setColor(Color.red);
			g.fillRect(snake.cherry.x * Snake.SCALE, snake.cherry.y * Snake.SCALE, Snake.SCALE, Snake.SCALE);

			//Score:
			g.setColor(Color.cyan);
			g.drawString("Score: " + snake.score, Snake.FRAMESIZE - 100, Snake.FRAMESIZE - 50);

			//Game Over
			if (snake.over) {
				string = "Game Over!";
				g.setColor(Color.pink);
				g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), (int) snake.dim.getHeight() / 3);

			}

			if (snake.paused && !snake.over) {
				string = "Paused";
				g.setColor(Color.pink);
				g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), (int) snake.dim.getHeight() / 3);
			}
		
		}
	}
}


