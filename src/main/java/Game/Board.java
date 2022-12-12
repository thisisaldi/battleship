package Game; /**
 * Class Game.Board merepresentasikan sebuah papan dalam permainan battleship di mana seorang pemain meletakkan kapal-kapal dan pemain lainnya menebak posisi kapal-kapal tersebut.
 */
import java.util.*;

public class Board
{
    /** ukuran default papan */
    public static final int DEFAULT_GRID_SIZE = 10;

    /** panjang minimum kapal */
    public static final int MIN_SHIP_LENGTH = 2;

    // flag/tanda/status dari masing-masing sel pada board
    public static final char EMPTY = '.';
    public static final char SHIP = 'S';
    public static final char HIT = 'H';
    public static final char MISS = 'M';

    public static final boolean HORIZONTAL = true;
    public static final boolean VERTICAL = false;

    /** jumlah baris dalam grid */
    private int height;

    /** jumlah kolom dalam grid */
    private int width;

    /** grid / papan bermain */
    private char[][] grid;

    public int getTotalShipCells() {
        return totalShipCells;
    }

    /** jumlah sel yang berisi kapal */
    private int totalShipCells;

    /** jumlah sel yang berisi kapal dan tertembak */
    private int totalHits;

    /**
     * Membuat sebuah board baru yang kosong dengan ukuran default.
     */
    public Board ()
    {
        this (DEFAULT_GRID_SIZE, DEFAULT_GRID_SIZE);
    }


    /**
     * Membuat sebuah board baru yang kosong.
     */
    public Board (int height, int width)
    {
        this.height = height;
        this.width = width;

        this.totalHits = 0;
        this.totalShipCells = 0;

        this.grid = new char[height][width];
        for(char[] row: this.grid) {
            Arrays.fill(row, EMPTY);
        }
    }


    /**
     * Mengembalikan lebar dari grid.
     *
     * @return jumlah kolom
     */
    public int getWidth ()
    {
        return this.width;
    }


    /**
     * Mengembalikan tinggi dari grid.
     *
     * @return jumlah baris
     */
    public int getHeight ()
    {
        return this.height;
    }

    public char[][] getGrid() {
        return this.grid;
    }

    private char[][] copyGrid(char[][] grid) {
        char[][] newGrid = new char[grid.length][grid[0].length];

        for(int i = 0; i < newGrid.length; i++) {
            for(int j = 0; j < newGrid[i].length; j++) {
                newGrid[i][j] = grid[i][j];
            }
        }

        return newGrid;
    }

    /**
     * Meletakkan sebuah kapal dengan panjang, koordinat paling kiri dan atas,
     * dan arah yang diberikan.
     *
     * FULL SCORE: Jika kapal tidak bisa diletakkan (sudah ada kapal lain),
     * board harus dikembalikan ke kondisi awal sebelum diubah.
     *
     * @param length
     *            panjang kapal harus >= 2
     * @param topRow
     *            koordinat paling atas kapal
     * @param leftCol
     *            koordinat paling kiri kapal
     * @param isHorizontal
     *            true jika horizontal, false jika vertikal
     * @return mengembalikan true jika kapal bisa diletakkan tanpa
     *         overlap/tumpang tindih dengan kapal lain.
     */
    public boolean placeShip (int length, int topRow, int leftCol,
            boolean isHorizontal)
    {
        char[][] new_grid = this.copyGrid(this.grid);

        if(length < 2) return false;

        if(isHorizontal) {
            for(int i = 0; i < length; i++) {
                if((leftCol+i) >= new_grid[topRow].length) return false;
                if(new_grid[topRow][leftCol+i] == 'S') return false;
                new_grid[topRow][leftCol+i] = SHIP;
            }
        } else {
            for(int i = 0; i < length; i++) {
                if((topRow+i) >= new_grid.length) return false;
                if(new_grid[topRow+i][leftCol] == 'S') return false;
                new_grid[topRow+i][leftCol] = SHIP;
            }
        }

        this.grid = new_grid;
        this.totalShipCells += length;
        return true;
    }


    /**
     * Mencetak status terakhir dari papan permainan ke console (System.out).
     * Jika hideShips == true, sembunyikan sel-sel yang berisi kapal.
     *
     * @param hideShips
     *            jika true, jangan tampilkan sel-sel yang berisi kapal.
     */
    public void printBoard (boolean hideShips)
    {
        for(int i = 0; i < height+1; i++) {
            if(i == 0) {
                System.out.printf(" %2s |", "");
                for(int j = 0; j < width; j++) {
                    System.out.printf(" %s |", (char) (65 + j));
                }
                System.out.println();
            } else {
                System.out.printf(" %2d |", i);

                for(int j = 0; j < width; j++) {
                    if(this.grid[i-1][j] == 'S' && hideShips) {
                        System.out.printf(" . |");
                    } else {
                        System.out.printf(" %c |", this.grid[i-1][j]);
                    }
                }
                System.out.println();

            }

        }
    }


    /**
     * Menembak pada koordinat baris dan kolom yang diberikan. Game.Board pada
     * koordinat tersebut harus diubah jika hit dan mis. Diperbolehkan untuk
     * menembak pada koordinat yang sama lebih dari satu kali.
     *
     * @param row
     *            koordinat baris (0 ... height - 1)
     * @param col
     *            koordinat kolom (0 ... width - 1)
     * @return true jika hit atau koordinat invalid, false jika miss
     */
    public boolean hit(int row, int col)
    {
        if(!this.isValidCoordinate(row, col)) return true;

        if(this.grid[row][col] == SHIP) {
            this.grid[row][col] = HIT;
            this.totalShipCells -= 1;
            this.totalHits += 1;
            return true;
        } else if(this.grid[row][col] == EMPTY) {
            this.grid[row][col] = MISS;
            return false;
        }

        return true;
    }


    /**
     * Menembak pada koordinat yang diberikan. Game.Board pada koordinat tersebut
     * harus diubah jika hit dan mis. Diperbolehkan untuk menembak pada
     * koordinat yang sama lebih dari satu kali.
     *
     * @param coord
     *            koordinat seperti "A1", "J10", "a1", "j10"
     * @return true jika hit atau koordinat invalid, false jika miss
     */
    public boolean hit (String coord)
    {
        return hit (coordToRow (coord), coordToCol (coord));
    }

        // hit("A1"); ->> hit(

    /**
     * Memeriksa apakah koordinat yang diberikan valid.
     *
     * @param row
     *            koordinat baris (0 ... height - 1)
     * @param col
     *            koordinat kolom (0 ... width - 1)
     * @return true jika koordinat valid, false jika koordinat invalid
     */
    public boolean isValidCoordinate (int row, int col)
    {
        if(row < 0 || row >= this.height) return false;
        if(col < 0 || col >= this.width) return false;

        return true;
    }


    /**
     * Mendapatkan posisi baris dari koordinat yang diberikan. Contoh:
     * coordToRow("B3") mengembalikan 2, coordToRow("G10") mengembalikan 9.
     *
     * @param coord
     *            koordinat seperti "A1", "J10", "a1", "j10"
     * @return posisi baris (0 ... height - 1)
     */
    public static int coordToRow (String coord)
    {
        String row = coord.substring(1);

        // A1
        // row = "1"
        // "1" -> 1 - 1 = 0

        return Integer.parseInt(row) - 1;
    }


    /**
     * Mendapatkan posisi kolom dari koordinat yang diberikan. Contoh:
     * coordToCol("B3") mengembalikan 1, coordToRow("G10") mengembalikan 6.
     *
     * @param coord
     *            koordinat seperti "A1", "J10", "a1", "j10"
     * @return posisi kolom (0 ... width - 1)
     */
    public static int coordToCol (String coord)
    {

        // "B3", "b3"
        // lowered = "b3"
        // col = 'b'
        // return 98 - 97 = 1

        String lowered = coord.toLowerCase();
        char col = lowered.charAt(0);

        return ((int) col) - 97;
    }


    /**
     * Memeriksa apakah semua sel yang berisi kapal sudah tertembak.
     *
     * @return mengembalikan true jika semua kapal sudah dilumpuhkan total.
     */
    public boolean isAllShipSunk ()
    {
        if(this.totalShipCells == 0) return true;

        return false;
    }

}
