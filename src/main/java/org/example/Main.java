package org.example;

import com.googlecode.lanterna.input.KeyStroke;

import static org.example.Screen.ROWS;

public class Main {
    static Screen screen = new Screen();
    static TestGame testGame = new TestGame(screen);

    public static void main(String[] args) throws InterruptedException {
        linePainter();
        screen.border();
        spawnEnemy();
        for (int i = 1; i < 24; i++) {
            singleKey();
            testGame.moveEnemy(i);
            Thread.sleep(250);
            singleKey();
        }


    }
    private static void singleKey() throws InterruptedException {
        KeyStroke keyStroke;
        while (true) {
            keyStroke = screen.getKeyStroke();
            testGame.handleKey(keyStroke);
            Thread.sleep(5);
            break;
        }
    }

    public static void linePainter(){
        for (int i = 0; i < ROWS; i++) {
            screen.putChar(Constants.LEFT_BORDER,i,'|',Screen.WHITE, Screen.WHITE);
            screen.putChar(Constants.RIGHT_BORDER,i,'|',Screen.WHITE, Screen.WHITE);
        }
    }
    public static void spawnEnemy(){
        int startRow = 1;
        screen.putChar(30,1,'H',Screen.RED,Screen.RED);


    }

}