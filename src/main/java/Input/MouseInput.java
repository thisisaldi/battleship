package Input;

import GUI.Config;
import GUI.GameCell;
import GUI.GameGrid;
import Game.Board;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInput implements MouseListener, MouseMotionListener {

    private GameCell cell;
    private GameGrid grid;
    private int[] lengthPlace;

    public MouseInput(GameCell cell, GameGrid grid) {
        this.cell = cell;
        this.grid = grid;
        this.lengthPlace = new int[] {5, 4, 3, 3, 2};
    }

    private void clearAllCells(boolean showShips) {
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                if(showShips) {
                    if(this.grid.cell[i][j].getState() != Board.SHIP)
                        this.grid.cell[i][j].setBackground(Color.decode(Config.GAME_PANEL_BG));
                } else {
                    this.grid.cell[i][j].setBackground(Color.decode(Config.GAME_PANEL_BG));
                }
            }
        }
    }

    private void drawShipShadow(int row, int col, int length) {
        if(checkShipPlaceable(row, col, length, this.grid.isHorizontalEdit)) {
            if(this.grid.isHorizontalEdit) {
                for(int i = 0; i < length; i++) {
                    this.grid.cell[row][col+i].setBackground(Color.decode(Config.CELL_HOVER_BG));
                }
            } else {
                for(int i = 0; i < length; i++) {
                    this.grid.cell[row+i][col].setBackground(Color.decode(Config.CELL_HOVER_BG));
                }
            }

            this.grid.validPlace = true;
        } else {
            if(this.grid.isHorizontalEdit) {
                for(int i = 0; i < length; i++) {
                    if(col + i < 10) {
                        this.grid.cell[row][col+i].setBackground(Color.decode(Config.ERROR_RED));
                    }
                }
            } else {
                for(int i = 0; i < length; i++) {
                    if(row + i < 10) {
                        this.grid.cell[row+i][col].setBackground(Color.decode(Config.ERROR_RED));
                    }
                }
            }

            this.grid.validPlace = false;
        }
    }

    private boolean checkShipPlaceable(int row, int col, int length, boolean hor) {
        for(int i = 0; i < length; i++) {
            if(hor) {
                if(checkAdjacentCells(row, col+i)) return false;
            } else {
                if(checkAdjacentCells(row+i, col)) return false;
            }
        }

        return true;
    }

    private boolean checkAdjacentCells(int row, int col) {
        if(row >= 10) return true;
        if(col >= 10) return true;
        if(this.grid.cell[row][col].getState() == Board.SHIP) return true;

        return false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(this.grid.editMode) {
            if(e.getButton() == MouseEvent.BUTTON3) {
                this.clearAllCells(true);
                if(this.grid.isHorizontalEdit) {
                    this.grid.isHorizontalEdit = false;
                }
                else {
                    this.grid.isHorizontalEdit = true;
                }
                this.drawShipShadow(this.cell.getRow(), this.cell.getCol(), this.lengthPlace[this.grid.shipEditorCount]);
            } else if(e.getButton() == MouseEvent.BUTTON1) {
                if(this.grid.validPlace) {
                    for(int i = 0; i < this.lengthPlace[this.grid.shipEditorCount]; i++) {
                        if(this.grid.isHorizontalEdit){
                            this.grid.cell[this.cell.getRow()][this.cell.getCol()+i].setState(Board.SHIP);
                            this.grid.cell[this.cell.getRow()][this.cell.getCol()+i].setBackground(Color.decode(Config.CELL_PLACED_BG));
                        } else {
                            this.grid.cell[this.cell.getRow()+i][this.cell.getCol()].setState(Board.SHIP);
                            this.grid.cell[this.cell.getRow()+i][this.cell.getCol()].setBackground(Color.decode(Config.CELL_PLACED_BG));
                        }
                    }

                    this.grid.shipCellsCount += this.lengthPlace[this.grid.shipEditorCount];
                    this.grid.gamepanel.buttons.shipsCounter.setText(Integer.toString(this.grid.shipCellsCount));
                    this.grid.shipEditorCount++;

                    if(this.grid.shipEditorCount >= 5) {
                        this.grid.editMode = false;
                        this.clearAllCells(false);
                    }
                }
            }
        } else {
            if(this.cell.getState() == Board.SHIP && !this.cell.isClicked) {
                this.grid.shipCellsCount -= 1;
                this.grid.gamepanel.buttons.shipsCounter.setText(Integer.toString(this.grid.shipCellsCount));
                if(this.grid.shipCellsCount == 0) this.grid.gamepanel.gameOver();
            } else if(!this.cell.isClicked) {
                this.grid.gamepanel.buttons.miss += 1;
                this.grid.gamepanel.buttons.missCounter.setText(Integer.toString(this.grid.gamepanel.buttons.miss));
            }
            this.cell.isClicked = true;
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.cell.hovered = true;

        if(this.grid.editMode) {
            this.drawShipShadow(this.cell.getRow(), this.cell.getCol(), this.lengthPlace[this.grid.shipEditorCount]);
        } else {
            this.cell.setBackground(Color.decode(Config.CELL_HOVER_BG));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.cell.hovered = false;
        this.cell.setBackground(Color.decode(Config.GAME_PANEL_BG));

        if(this.grid.editMode) {
            for(int i = 0; i < this.lengthPlace[this.grid.shipEditorCount]; i++) {
                if(this.grid.isHorizontalEdit) {
                    if(this.cell.getCol()+i < 10)
                        if(this.grid.cell[this.cell.getRow()][this.cell.getCol()+i].getState() == Board.SHIP)
                            this.grid.cell[this.cell.getRow()][this.cell.getCol()+i].setBackground(Color.decode(Config.CELL_PLACED_BG));
                        else
                            this.grid.cell[this.cell.getRow()][this.cell.getCol()+i].setBackground(Color.decode(Config.GAME_PANEL_BG));
                } else {
                    if(this.cell.getRow()+i < 10)
                        if(this.grid.cell[this.cell.getRow()+i][this.cell.getCol()].getState() == Board.SHIP)
                            this.grid.cell[this.cell.getRow()+i][this.cell.getCol()].setBackground(Color.decode(Config.CELL_PLACED_BG));
                        else
                            this.grid.cell[this.cell.getRow()+i][this.cell.getCol()].setBackground(Color.decode(Config.GAME_PANEL_BG));
                }
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
