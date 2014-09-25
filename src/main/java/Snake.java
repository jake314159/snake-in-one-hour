import javax.swing.*;
import java.awt.*;

/**
 * Created by jake on 25/09/14.
 */
public class Snake extends JFrame {

    public static void main(String args[]) {
        Snake snake = new Snake("Snake in one hour");
        snake.setSize(300,400);
        snake.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        snake.setContentPane(new GamePanel());
        snake.setVisible(true);
    }

    public Snake(String title) {
        super(title);
    }
}
