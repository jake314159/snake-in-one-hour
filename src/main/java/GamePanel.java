import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by jake on 25/09/14.
 */
public class GamePanel extends JPanel implements KeyListener {

    public static final int GRID_SIZE_X = 10;
    public static final int GRID_SIZE_Y = 14;
    public static final int MARGIN = 1;
    private static final long DELAY = 200;

    private SnakeObject snake;

    private boolean gameOver = false;

    public GamePanel() {
        snake = new SnakeObject(this, GRID_SIZE_X, GRID_SIZE_Y);
        this.addKeyListener(this);
        setFocusable(true);
        requestFocus();
        new Thread() {
            @Override
            public void run() {
                super.run();
                while(true) {
                    try {
                        Thread.sleep(DELAY);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(!gameOver) {
                        snake.step();
                    }
                    repaint();
                }
            }
        }.start();
    }

    @Override
    public void paintComponent(Graphics g) {

        int width = getWidth()/GRID_SIZE_X;
        int height = getHeight()/GRID_SIZE_Y;
        g.setColor(Color.white);
        g.fillRect(0,0,getWidth(), getHeight());

        snake.drawSnake(g, width, height);

        if(gameOver) {
            g.setColor(Color.white);
            g.fillRect(0,80,getWidth(), 90);
            g.setColor(Color.black);
            g.drawRect(0,80,getWidth(), 90);
            g.drawString("GAME OVER", 10, 100);
            //-3 to take into account start length
            g.drawString("SCORE: "+(snake.getLength()-3), 10, 130);

            g.drawString("PRESS SPACE TO CONTINUE", 10, 150);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            snake.setDirection(SnakeObject.NORTH);
        } else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            snake.setDirection(SnakeObject.SOUTH);
        } else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            snake.setDirection(SnakeObject.EAST);
        } else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            snake.setDirection(SnakeObject.WEST);
        } else if(gameOver && e.getKeyCode() == KeyEvent.VK_SPACE) {
            //New snake
            snake = new SnakeObject(this, GRID_SIZE_X, GRID_SIZE_Y);
            gameOver = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void gameOver() {
        gameOver = true;
    }
}
