/*******
 * CSCI 185
 * Antoun Elabd & Mohamed Youssef
 * Dr. Wenjia Li
 * Final Programming Project
 * 5/8/2025
 *******/

import javax.swing.*;
import java.awt.*;

public class Bullets extends GameObject{
    private Image sprite;

    private int speed = -1;

    public Bullets(int x, int y) {
        position = new Position(x, y);
        size = new Size(10, 20);
        this.speed = speed;

        Image raw = new ImageIcon("window/window/Images/Bullet.png").getImage();
        sprite = raw.getScaledInstance(size.getWidth(), size.getHeight(), Image.SCALE_SMOOTH);

    }


    @Override
    public void update() {
        int Y = position.getY() + speed;
        position = new Position(position.getX(), Y);
    }

    @Override
    public Image getSprite() {
        return sprite;
    }
}
