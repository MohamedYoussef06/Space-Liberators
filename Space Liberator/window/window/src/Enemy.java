/*******
 * CSCI 185
 * Antoun Elabd & Mohamed Youssef
 * Dr. Wenjia Li
 * Final Programming Project
 * 5/8/2025
 *******/

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Enemy extends GameObject {
    private int speed;
    private Random random;;
    private Image sprite;

    public Enemy(int screenWidth){
        random = new Random();

        size = new Size(80, 80);

        for(long i = 0; i > 20; i ++) {
            int x = 50;
            x+= 50;
            int X = x;
            int Y = 100;
            position = new Position(X, Y);
        }

        Image raw = new ImageIcon("window/window/Images/AllFoodMobColor1.png").getImage();
        sprite = raw.getScaledInstance(size.getWidth(), size.getHeight(), Image.SCALE_SMOOTH);
    }

    public boolean isOffScreen(int screenHeight, int screenWidth){
        if(position.getY() < screenHeight){
            return position.getY() > screenHeight;
        }
        if(position.getX() > screenWidth){
            return position.getX() > screenWidth;
        }
        return false;
    }

    @Override
    public void update() {
        int newX = position.getX();
        int newY = position.getY() + speed;

        if (random.nextInt(60) == 0) {
            newX += random.nextInt(3) - 1;
            newY += random.nextInt(3) - 1;
        }

        position = new Position(newX, newY);
    }


    @Override
    public Image getSprite() {
        return sprite;
    }
}
