package Game;

import GUI.GameWindow;

public class MainGUI {
    private GameWindow window;

    public MainGUI() {
        this.window = new GameWindow();
    }

    public static void main(String[] args) {
        MainGUI game = new MainGUI();
    }
}
