package org.example;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

/** A Screen wraps a Lanterna default terminal
 * in a simplifying façade
 * Main purpose is to add getChar that remembers
 * what char was put in each position
 * (and get rid of all checked exceptions)
 */
public class Screen {
    public static final int COLS = 80, ROWS = 24 ;
    static final char BLOCK = '\u2588';
    public static final TextColor
            WHITE = new TextColor.RGB(255,255,255),
            BLACK = new TextColor.RGB(0,0,0),
            BLUE = new TextColor.RGB(0,0,255),
            YELLOW = new TextColor.RGB(255,255,0),
            GREEN = new TextColor.RGB(0,255,0),
            RED = new TextColor.RGB(255,0,0);

    private char [][] array = new char[COLS][ROWS]; // remembers the chars in screen

    private DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
    private Terminal terminal;

    public Screen(){
        try {
            terminal = terminalFactory.createTerminal();
            terminal.setCursorVisible(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     *convenience method for white on black chars
     */
    public void putChar(int col, int row, char c){
        putChar(col,row,c,WHITE,BLACK);
    }

    /**
     *
     *  outputs a whole string at the position
     */
    public void putString(int col, int row, String string){
        for (int i = 0; i < string.length(); i++) {
            putChar(col+i, row, string.charAt(i));
        }
    }

    /**
     * put a char in a position and remember it being there
     */
    public void putChar(int col, int row, char c,
                        TextColor foreGround, TextColor backGround) {
        checkColRow(col,row);
        try {
            terminal.setCursorPosition(col, row);
            terminal.setForegroundColor(foreGround);
            terminal.setBackgroundColor(backGround);
            terminal.putCharacter(c);
            array[col][row] =c;
            terminal.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * get the char that earlier was put in the position
     */
    public char getChar(int col, int row){
        checkColRow(col,row);
        return array[col][row];

    }

    private void checkColRow(int col, int row) {
        if (col < 0 || col >= COLS || row < 0 || row >= ROWS) {
            throw new IllegalArgumentException("col=" + col + ", row=" + row);
        }
    }

    /**
     * just forwards the getting of a keystroke from Lanterna
     * Always returns immediately, null if no key was pressed
     */
    public KeyStroke getKeyStroke()  {
        try {
            return terminal.pollInput();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    private void close() throws IOException {
        terminal.close();
    }

    private void flush()  {
        try {
            terminal.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * puts a border of blocks around the screen
     */
    public void border() {
        for (int row = 0; row <ROWS; row++) {
            putChar(0, row, BLOCK);
            putChar(COLS-1, row, BLOCK);
        }
        for (int col = 0; col <COLS; col++) {
            putChar(col, 0, BLOCK);
            putChar(col, ROWS-1, BLOCK);
        }
    }
}
