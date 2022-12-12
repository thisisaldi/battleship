package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel {
    public GameGrid grid;
    public SidePanel buttons;

    public GamePanel(JFrame frame) {
        super(null);
        this.setPreferredSize(new Dimension(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT));
        this.setBounds(0, 0, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        this.setBackground(Color.decode(Config.GAME_PANEL_BG));

        this.grid = new GameGrid(this);
        this.buttons = new SidePanel(this.grid, frame);
        this.grid.randomBoard();

        this.setGridLabels();
        this.setButtonListener();

        this.add(this.grid);
        this.add(this.buttons);
    }

    public void gameOver() {
        this.grid.finishScreen();
    }

    private void setButtonListener() {
        buttons.newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(grid.editMode) grid.editMode = false;
                grid.setAllCellVisible(true);
                grid.randomBoard();
                buttons.miss = 0;
                buttons.missCounter.setText(Integer.toString(buttons.miss));
            }
        });

        buttons.newCustomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                grid.setAllCellVisible(true);
                grid.toEditMode();
                buttons.miss = 0;
                buttons.shipsCounter.setText(Integer.toString(grid.shipCellsCount));
                buttons.missCounter.setText(Integer.toString(buttons.miss));
                System.out.println("edit mode");
            }
        });
    }

    private void setGridLabels() {
        JLabel[] colLabel = new JLabel[10];
        JLabel[] rowLabel = new JLabel[10];

        for(int i = 0; i < 10; i++) {
            colLabel[i] = new JLabel(Character.toString((char) (65 + i)));
            colLabel[i].setFont(new Font(Config.FONT_NAME, Font.PLAIN, 15));
            colLabel[i].setHorizontalAlignment(JLabel.CENTER);
            colLabel[i].setForeground(Color.decode("#46425E"));
            colLabel[i].setSize(50, 50);
            colLabel[i].setLocation(50+(i*50), 0);
            this.add(colLabel[i]);
        }

        for(int i = 0; i < 10; i++) {
            rowLabel[i] = new JLabel(Integer.toString(i+1));
            rowLabel[i].setFont(new Font(Config.FONT_NAME, Font.PLAIN, 15));
            rowLabel[i].setHorizontalAlignment(JLabel.CENTER);
            rowLabel[i].setForeground(Color.decode("#46425E"));
            rowLabel[i].setSize(50, 50);
            rowLabel[i].setLocation(0, 50+(i*50));
            this.add(rowLabel[i]);
        }

    }
}
