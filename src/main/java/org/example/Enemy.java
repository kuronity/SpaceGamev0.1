
package org.example;

import static org.example.Main.screen;

public abstract class Enemy {

    int size;
    Position position;

    public Enemy(Position position, int size) {
        this.position = position;
        this.size = size;
    }

    public Enemy(int size) {
        this.size = size;
    }

    void spawn() {
        int startRow = 1;
        //screen.putChar();
    }

    abstract void move();

}
