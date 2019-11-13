package Snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class RenderPanel extends JPanel {
		
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);       // do this first
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, 800, 700);
		
		//Draw Snake
		Snake snake = Snake.snake;
		g.setColor(Color.green);	
		for(Point point : snake.snakeParts) {
			g.fillRect(point.x * Snake.SCALE, point.y * Snake.SCALE,
					Snake.SCALE, Snake.SCALE);
		}
		g.fillRect(snake.head.x * snake.SCALE, snake.head.y * Snake.SCALE,
				Snake.SCALE, Snake.SCALE);
		
		//Draw Cherry
		g.setColor(Color.red);
		g.fillRect(snake.cherry.x * Snake.SCALE, snake.cherry.y * Snake.SCALE,
				Snake.SCALE, Snake.SCALE);
		
	}

}


