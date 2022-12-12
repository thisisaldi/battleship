package GUI;

import Game.Board;

import Input.MouseInput;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class GameCell extends JPanel {
    private int row;
    private int col;
    private char state;
    public boolean hovered;

    public boolean isClicked;
    public boolean reset;
    public boolean editMode;

    MouseInput mouseInput;

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public char getState() {
        return state;
    }

    public void setState(char state) {
        this.state = state;
    }

    public GameCell(int row, int col, char state, GameGrid grid) {
        super(null);

        this.row = row;
        this.col = col;
        this.state = state;
        this.isClicked = false;
        this.editMode = false;
        this.hovered = false;
        this.reset = false;

        this.setPreferredSize(new Dimension(Config.CELL_SIZE, Config.CELL_SIZE));
        this.setBounds(col*Config.CELL_SIZE, row*Config.CELL_SIZE, Config.CELL_SIZE, Config.CELL_SIZE);
        this.setBackground(Color.decode(Config.GAME_PANEL_BG));

        mouseInput = new MouseInput(this, grid);
        this.addMouseListener(mouseInput);
        this.addMouseMotionListener(mouseInput);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        ArrayList<Line2D> cross = new ArrayList<Line2D>();
        cross.add(new Line2D.Float(0, 0, getWidth(), getHeight()));
        cross.add(new Line2D.Float(getWidth(), 0, 0, getHeight()));

        g.setColor(Color.decode(Config.BUTTON_PANEL_BG));
        g.drawRect(0, 0, this.getWidth(), this.getHeight());

        if(this.hovered && this.editMode) {

        }

        if(this.isClicked) {
            if(this.state == Board.SHIP) {

                g.setColor(Color.decode("#FF6973"));

                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(2));

                for(Line2D line: cross) {
                    g2.draw(line);
                }

            } else {
                setBackground(Color.decode(Config.BUTTON_PANEL_BG));
            }
        }

    }
}
