package org.example;

public class ScreenDemo {
    public static void main(String[] args) {
        Screen screen = new Screen();
        screen.putChar(3,3,'X', Screen.RED, Screen.WHITE);
        screen.putChar(6,6,'O');
        screen.putString(9,9,"Hello, world!");
        screen.putChar(12,12, '\u263B');
        if (screen.getChar(3,3)== 'X') {
            screen.putString(15,15,"Yes!");
        }

    }
}
