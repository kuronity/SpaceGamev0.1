package org.example;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import static org.example.Screen.*;

public class TestGame {
    private Position playerPos = new Position(35, 22);
    private Position enemyPos = new Position(30,1);
    Screen screen;
    public TestGame(Screen screen) {
        this.screen = screen;
        screen.putChar(35,22, '\u1468',GREEN,GREEN);
    }

    public boolean handleKey (KeyStroke keyStroke) {
        String result = "";
        if (keyStroke == null) return false;

        KeyType kt = keyStroke.getKeyType();
        switch (kt) {
            case ArrowRight,ArrowLeft:
                result = tryMove(kt);
                break;
            default:
                //ignore all other key!!
                return false;
        }
        if (result.equals("Continue")) {
            return true;
        } else {
            screen.putString(10,15,result);
            screen.putString(10,17, "End of game. Score = ");
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                // ignore
            }
            System.exit(0);
            return false;
        }

    }
    private String tryMove(KeyType kt)  {
        int row = playerPos.getRow();
        int col = playerPos.getCol();
        switch(kt) {
            case ArrowLeft:
                col--;
                break;
            case ArrowRight:
                col++;
                break;
            default:
                //ignore illegal keystrokes
                return "Continue";
        }
        char next = screen.getChar(col,row);
        switch (next) {
            case '|':
                return "Continue";
            case BLOCK:
                return "Collided with obstacle!";
            default: movePlayer(col,row);
        }
        return "Continue";
    }

    private int checkDirection(int col){
        if(playerPos.col > col){
            return 1;
        }
        return -1;
    }
    private void movePlayer(int col, int row){
        //System.out.println("move" + col + " " + row);
        int x = checkDirection(col);
        playerPos = new Position(col,row);
        screen.putChar(playerPos.getCol(), playerPos.getRow(), '\u1468', GREEN, GREEN); // målar spelar ny pos
        screen.putChar(playerPos.getCol()+x, playerPos.getRow(), ' ', BLACK,BLACK); // suddar föregående pos
    }
    public void moveEnemy(int row){
        enemyPos = new Position(enemyPos.getCol(),row);
        screen.putChar(enemyPos.getCol(), enemyPos.getRow(), '\u1468', RED, RED); // målar enemy ny pos
        screen.putChar(enemyPos.getCol(), enemyPos.getRow()-1, ' ', BLACK,BLACK); // suddar föregående pos
    }

}

