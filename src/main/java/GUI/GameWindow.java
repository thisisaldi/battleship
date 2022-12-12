package GUI;

import javax.swing.*;
import java.awt.*;

public class GameWindow {
    private JFrame frame;

    public GameWindow() {

        this.frame = new JFrame();
        this.frame.setLayout(null);
        this.frame.setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        this.frame.setBackground(Color.BLACK);

        GamePanel panel = new GamePanel(this.frame);
        this.frame.add(panel);

        Image icon = new ImageIcon(getClass().getResource("/images/icon.png")).getImage();

        this.frame.setIconImage(icon);

        this.frame.setLocationRelativeTo(null);
        this.frame.setTitle("BattleShip");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setResizable(false);
        this.frame.setVisible(true);
    }
}
