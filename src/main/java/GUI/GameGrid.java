package GUI;

import Game.Board;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GameGrid extends JPanel {
    public int cellHeight;
    public int cellWidth;
    public GameCell[][] cell;
    public GamePanel gamepanel;
    public Board board;
    public int shipCellsCount;
    public int shipEditorCount;

    public boolean editMode;
    public boolean isHorizontalEdit;
    public boolean validPlace;
    public boolean isPlaying;

    public GameGrid(GamePanel gamepanel) {
        super(null);
        this.gamepanel = gamepanel;

        this.cell = new GameCell[10][10];
        this.setPreferredSize(new Dimension(500, 500));
        this.setBounds(50, 50, 500, 500);

        this.editMode = false;
        this.shipCellsCount = 0;
        this.shipEditorCount = 0;
        this.isHorizontalEdit = true;
        this.validPlace = true;
        this.isPlaying = true;
        this.board = new Board();

        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                this.cell[i][j] = new GameCell(i, j, board.getGrid()[i][j], this);
                this.add(this.cell[i][j]);
            }
        }
    }



    public void setAllCellVisible(boolean visible) {
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                this.cell[i][j].setVisible(visible);
            }
        }
    }

    public void finishScreen() {
        this.setAllCellVisible(false);

        JLabel text = new JLabel("You finished!");
        JLabel missScore = new JLabel("Missed : " + this.gamepanel.buttons.miss);

        text.setFont(new Font(Config.FONT_NAME, Font.BOLD, 30));
        text.setHorizontalAlignment(JLabel.CENTER);
        text.setVerticalAlignment(JLabel.CENTER);
        text.setSize(250, 30);
        text.setLocation(125, 200);
        text.setForeground(Color.decode(Config.DARKEST_BLUE));

        missScore.setFont(new Font(Config.FONT_NAME, Font.BOLD, 20));
        missScore.setHorizontalAlignment(JLabel.CENTER);
        missScore.setVerticalAlignment(JLabel.CENTER);
        missScore.setSize(250, 30);
        missScore.setLocation(125, 250);
        missScore.setForeground(Color.decode(Config.DARKEST_BLUE));

        this.add(text);
        this.add(missScore);
    }

    public void emptyBoard() {
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                this.board.getGrid()[i][j] = Board.EMPTY;
                this.cell[i][j].setState(Board.EMPTY);
                this.cell[i][j].isClicked = false;
                this.cell[i][j].reset = true;
                this.cell[i][j].setBackground(Color.decode(Config.GAME_PANEL_BG));
                this.cell[i][j].repaint();
            }
        }
        this.shipCellsCount = 0;
    }

    public void toEditMode() {
        this.emptyBoard();
        this.editMode = true;
        this.shipEditorCount = 0;
    }

    public void randomBoard() {
        Random rand = new Random();
        int[] length = {5, 4, 3, 3, 2};

        this.emptyBoard();

        for(int i: length) {
            boolean valid = false;
            while(!valid) {
                int row = rand.nextInt(10);
                int col = rand.nextInt(10);
                boolean horizontal = rand.nextBoolean();
                valid = this.board.placeShip(i, row, col, horizontal);

                if(valid) {
//                    HashMap<Integer, Integer> coord = new HashMap<Integer, Integer>();
//                    coord.put(row, col);
//
//                    HashMap<Integer, Boolean> attr = new HashMap<Integer, Boolean>();
//                    attr.put(i, horizontal);
//                    this.shipsPos.put(coord, attr);
                    this.shipCellsCount += i;
                }
            }
        }

        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                this.cell[i][j].setState(this.board.getGrid()[i][j]);
            }
        }
        this.gamepanel.buttons.shipsCounter.setText(Integer.toString(this.shipCellsCount));
        this.board.printBoard(false);
    }

}
