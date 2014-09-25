import java.awt.*;
import java.util.Random;

/**
 * Created by jake on 25/09/14.
 */
public class SnakeObject {

    public static final int NORTH = 1;
    public static final int EAST = 2;
    public static final int SOUTH = 3;
    public static final int WEST = 4;

    private static final Color SNAKE_COLOR = Color.RED;
    private static final Color FOOD_COLOR = Color.GREEN;
    private static final Color TEXT_COLOR = Color.BLACK;

    private Point loc[];
    private int startIndex = 0;
    private int length = 3;
    private int direction = EAST;
    private int nextDirection = EAST;

    private int width;
    private int height;
    private GamePanel gamePanel;

    private Point food = new Point(2,2);
    private Random rand = new Random();


    public SnakeObject(GamePanel gamePanel, int width, int height) {
        this.gamePanel = gamePanel;
        this.width = width;
        this.height = height;
        loc = new Point[width*height];
        for(int i=0; i<loc.length; i++) {
            loc[i] = new Point(0,0);
        }
        loc[0].setLocation(0,0);
        loc[1].setLocation(1,0);
        loc[2].setLocation(2,0);
    }

    public void drawSnake(Graphics g, int width, int height) {
        g.setColor(FOOD_COLOR);
        g.fillRect(food.x*width+GamePanel.MARGIN, food.y*height+GamePanel.MARGIN,
                width-GamePanel.MARGIN*2, height-GamePanel.MARGIN*2);

        g.setColor(SNAKE_COLOR);
        for(int i =0; i<length; i++) {
            g.fillRect(loc[(startIndex+i)%loc.length].x*width+GamePanel.MARGIN,
                    loc[(startIndex+i)%loc.length].y*height+GamePanel.MARGIN,
                    width-GamePanel.MARGIN*2, height-GamePanel.MARGIN*2);
        }

        g.setColor(TEXT_COLOR);
        g.drawString("Score: "+(length-3), 4, 14);
     }

    public void step() {
        direction = nextDirection;

        int difX = 0;
        int difY = 0;
        if(direction == NORTH) {
            difY = -1;
        } else if(direction == SOUTH) {
            difY = 1;
        } else if(direction == EAST) {
            difX = 1;
        } else if(direction == WEST) {
            difX = -1;
        }
        loc[(startIndex+length)%loc.length].setLocation(loc[(startIndex + length - 1)%loc.length].x + difX, loc[(startIndex + length - 1)%loc.length].y + difY);
        if(loc[(startIndex+length)%loc.length].x < 0 || loc[(startIndex+length)%loc.length].x >= width ||
                loc[(startIndex+length)%loc.length].y < 0 || loc[(startIndex+length)%loc.length].y >= height
                ) {
            gamePanel.gameOver();
        }

        //Check if hit self
        for(int i =0; i<length; i++) {
            if(loc[(startIndex+length)%loc.length].x == loc[(startIndex+i)%loc.length].x &&
                    loc[(startIndex+length)%loc.length].y == loc[(startIndex+i)%loc.length].y
                    ) {
                gamePanel.gameOver();
            }
        }

        if(loc[(startIndex+length)%loc.length].x ==food.x && loc[(startIndex+length)%loc.length].y == food.y) {
            //food
            food.setLocation(rand.nextInt(width-1), rand.nextInt(height-1));
            length++;
            startIndex--;
        }
        startIndex = (startIndex+1)%loc.length;
    }

    public void setDirection(int d) {
        if((direction == EAST && d == WEST) ||
                (direction == WEST && d == EAST) ||
                (direction == NORTH && d == SOUTH) ||
                (direction == SOUTH && d == NORTH)) {
            return; //Not possible
        }
        this.nextDirection = d;
    }

    public int getLength() {
        return length;
    }
}
