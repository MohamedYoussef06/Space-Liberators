/*******
 * CSCI 185
 * Antoun Elabd & Mohamed Youssef
 * Dr. Wenjia Li
 * Final Programming Project
 * 5/8/2025
 *******/

import java.awt.Image;

public abstract class GameObject {
    protected Position position;
    protected Size size;

    public GameObject() {
        position = new Position(50, 50);
        size     = new Size(50, 50);
    }

    public Position getPosition() {
        return position;
    }
    public Size getSize() {
        return size;
    }

    public void setPosition(Position newPos) {
        this.position = newPos;
    }

    public abstract void update();
    public abstract Image getSprite();
}
