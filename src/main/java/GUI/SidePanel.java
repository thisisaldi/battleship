package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SidePanel extends JPanel {
    public JButton newGameButton;
    public JButton newCustomButton;
    public JButton aboutButton;

    public JLabel shipsCounter;
    public JLabel missCounter;
    public int miss;

    public JFrame frame;
    public GameGrid grid;

    private ImageIcon newGameIdle;
    private ImageIcon newGameHover;
    private ImageIcon newCustomIdle;
    private ImageIcon newCustomHover;

    public SidePanel(GameGrid grid, JFrame frame) {
        super(null);
        this.frame = frame;
        this.grid = grid;
        this.miss = 0;
        this.setPreferredSize(new Dimension(Config.SIDE_PANEL_WIDTH, Config.SIDE_PANEL_HEIGHT));
        this.setBounds(Config.SIDE_PANEL_X, Config.SIDE_PANEL_Y, Config.SIDE_PANEL_WIDTH, Config.SIDE_PANEL_HEIGHT);
        this.setBackground(Color.decode(Config.BUTTON_PANEL_BG));

        this.addTitle();
        this.addButtons();
        this.addStatsLabels();
    }

    private void addButtons() {
        this.addNewGameButton();
        this.addNewCustombutton();
        this.addAboutButton();
    }

    public void showCredits() {
        JDialog credits = new JDialog(this.frame, "About");
        credits.setSize(300, 150);
        credits.setBackground(Color.decode(Config.GAME_PANEL_BG));
        credits.setLocationRelativeTo(null);

        JLabel title = new JLabel("Proyek UAS Prak. PBO");
        JLabel aldi = new JLabel("2110511054 - M. Naufaldi Fadhlirrahman");
        JLabel niay = new JLabel("2110511120 - Ni Ayu Diandra Puspasari");
        JLabel shasa = new JLabel("2110511123 - Siti Shakiva Hilya Soerono");
        JLabel[] names = new JLabel[] {aldi, niay, shasa};

        title.setFont(new Font(Config.FONT_NAME, Font.BOLD, 15));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.NORTH);
        title.setSize(300, 50);
        title.setLocation(0, 0);
        title.setForeground(Color.decode(Config.BUTTON_PANEL_BG));

        for(int i = 0; i < names.length; i++) {
            names[i].setFont(new Font(Config.FONT_NAME, Font.PLAIN, 10));
            names[i].setHorizontalAlignment(JLabel.LEFT);
            names[i].setSize(300, 30);
            names[i].setLocation(30, 30+(15*i));
            names[i].setForeground(Color.decode(Config.BUTTON_PANEL_BG));
            credits.add(names[i]);
        }

        credits.add(title);

        credits.setVisible(true);
    }

    private void addTitle() {
        JLabel title1 = new JLabel("BATTLE");
        title1.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
        title1.setHorizontalAlignment(JLabel.CENTER);
        title1.setSize(100, 30);
        title1.setLocation(25, 25);
        title1.setForeground(Color.decode(Config.GAME_PANEL_BG));

        JLabel title2 = new JLabel("SHIP");
        title2.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 30));
        title2.setHorizontalAlignment(JLabel.CENTER);
        title2.setSize(100, 30);
        title2.setLocation(25, 40);
        title2.setForeground(Color.decode(Config.GAME_PANEL_BG));

        this.add(title1);
        this.add(title2);
    }

    private void addAboutButton() {
        this.aboutButton = new JButton("About");

        this.aboutButton.setBounds(25, 550, 100, 50);
        this.aboutButton.setForeground(Color.decode(Config.GAME_PANEL_BG));
        this.aboutButton.setOpaque(false);
        this.aboutButton.setContentAreaFilled(false);
        this.aboutButton.setBorderPainted(false);
        this.aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCredits();
            }
        });
        this.add(this.aboutButton);
    }

    private void addNewGameButton() {

        ImageIcon icon1 = new ImageIcon(getClass().getResource("/images/new-game-idle.png"));
        Image img1 = icon1.getImage().getScaledInstance(100, 50, Image.SCALE_SMOOTH);

        ImageIcon icon2 = new ImageIcon(getClass().getResource("/images/new-game-hover.png"));
        Image img2 = icon2.getImage().getScaledInstance(100, 50, Image.SCALE_SMOOTH);

        this.newGameIdle = new ImageIcon(img1);
        this.newGameHover = new ImageIcon(img2);

        this.newGameButton = new JButton(this.newGameIdle);
        this.newGameButton.setBounds(25, 100, 100, 50);

        this.newGameButton.setOpaque(false);
        this.newGameButton.setContentAreaFilled(false);
        this.newGameButton.setBorderPainted(false);
        this.newGameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                newGameButton.setIcon(newGameHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                newGameButton.setIcon(newGameIdle);
            }
        });

        this.add(this.newGameButton);
    }

    private void addNewCustombutton() {

        ImageIcon icon1 = new ImageIcon(getClass().getResource("/images/new-custom-idle.png"));
        Image img1 = icon1.getImage().getScaledInstance(100, 50, Image.SCALE_SMOOTH);

        ImageIcon icon2 = new ImageIcon(getClass().getResource("/images/new-custom-hover.png"));
        Image img2 = icon2.getImage().getScaledInstance(100, 50, Image.SCALE_SMOOTH);

        this.newCustomIdle = new ImageIcon(img1);
        this.newCustomHover = new ImageIcon(img2);

        this.newCustomButton = new JButton(this.newCustomIdle);
        this.newCustomButton.setBounds(25, 175, 100, 50);

        this.newCustomButton.setOpaque(false);
        this.newCustomButton.setContentAreaFilled(false);
        this.newCustomButton.setBorderPainted(false);
        this.newCustomButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                newCustomButton.setIcon(newCustomHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                newCustomButton.setIcon(newCustomIdle);
            }
        });
        this.add(this.newCustomButton);
    }

    private void addStatsLabels() {
        JLabel shipCellText = new JLabel("SHIP CELLS");
        shipCellText.setFont(new Font(Config.FONT_NAME, Font.PLAIN, 15));
        shipCellText.setHorizontalAlignment(JLabel.CENTER);
        shipCellText.setSize(100, 30);
        shipCellText.setLocation(25, 300);
        shipCellText.setBackground(Color.decode(Config.DARKEST_BLUE));
        shipCellText.setForeground(Color.decode(Config.GAME_PANEL_BG));
        shipCellText.setOpaque(true);

        this.shipsCounter = new JLabel(Integer.toString(this.grid.shipCellsCount));
        this.shipsCounter.setFont(new Font(Config.FONT_NAME, Font.PLAIN, 20));
        this.shipsCounter.setHorizontalAlignment(JLabel.CENTER);
        this.shipsCounter.setSize(100, 40);
        this.shipsCounter.setLocation(25, shipCellText.getY()+shipCellText.getHeight());
        this.shipsCounter.setBackground(Color.decode(Config.GAME_PANEL_BG));
        this.shipsCounter.setForeground(Color.decode(Config.DARKEST_BLUE));
        this.shipsCounter.setOpaque(true);

        JLabel missText = new JLabel("MISS");
        missText.setFont(new Font(Config.FONT_NAME, Font.PLAIN, 15));
        missText.setHorizontalAlignment(JLabel.CENTER);
        missText.setSize(100, 30);
        missText.setLocation(25, this.shipsCounter.getY()+this.shipsCounter.getHeight()+25);
        missText.setBackground(Color.decode(Config.DARKEST_BLUE));
        missText.setForeground(Color.decode(Config.GAME_PANEL_BG));
        missText.setOpaque(true);

        this.missCounter = new JLabel(Integer.toString(this.miss));
        this.missCounter.setFont(new Font(Config.FONT_NAME, Font.PLAIN, 20));
        this.missCounter.setHorizontalAlignment(JLabel.CENTER);
        this.missCounter.setSize(100, 40);
        this.missCounter.setLocation(25, missText.getY()+missText.getHeight());
        this.missCounter.setBackground(Color.decode(Config.GAME_PANEL_BG));
        this.missCounter.setForeground(Color.decode(Config.DARKEST_BLUE));
        this.missCounter.setOpaque(true);

        this.add(shipCellText);
        this.add(missText);

        this.add(this.shipsCounter);
        this.add(this.missCounter);
    }
}
