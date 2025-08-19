/*******
 * CSCI 185
 * Antoun Elabd & Mohamed Youssef
 * Dr. Wenjia Li
 * Final Programming Project
 * 5/8/2025
 *******/

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;


public class Player extends GameObject {
    private Image sprite;

    private Controller controller;
    private int speedX = 2;
    private int speedY = 1;
    private long lastshot;
    private static final long Shotcooldown = 300;

    public Player(Controller controller) {
        this.controller = controller;

        size = new Size(80, 80);


        int startX = (800 - size.getWidth()) / 2;
        int startY = 600 - size.getHeight();
        position = new Position(startX, startY);

        lastshot = 0;

        Image raw = new ImageIcon("window/window/Images/ship.png").getImage();
        sprite = raw.getScaledInstance(size.getWidth(), size.getHeight(), Image.SCALE_SMOOTH);
    }




    @Override
    public void update() {
        int x = position.getX();
        int y = position.getY();
        long time = System.currentTimeMillis();


        if (controller.isrequestingleft())  x -= speedX;
        if (controller.isrequestingright()) x += speedX;

        if (controller.isrequestingup())    y -= speedY;
        if (controller.isrequestingdown())  y += speedY;


        int barrierY = 600 / 2 - 50;
        if (y < barrierY) {
            y = barrierY;
        }


        x = Math.max(0, Math.min(x, 800 - size.getWidth()));
        y = Math.max(barrierY, Math.min(y, 600 - size.getHeight()));


        position = new Position(x, y);
    }

    @Override
    public Image getSprite() {
        return sprite;
    }


    public boolean canShoot(){
        long time = System.currentTimeMillis();
        if(controller.isrequestingshooting() && (time - lastshot > Shotcooldown)){
            lastshot = time;
            return true;
        }
        return false;
    }


    public Bullets shoot(){
    int bulletWidth = 10;
    int bulletX = position.getX() + (size.getWidth() - bulletWidth) / 2;
    int bulletY = position.getY();

    return new Bullets(bulletX,bulletY);

    }
    /** using the sprite above
    @Override
    public Image getSprite() {
        BufferedImage image = new BufferedImage(
                size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D graphics = image.createGraphics();

        graphics.setColor(Color.RED);
        graphics.fillRect(0, 0, size.getWidth(), size.getHeight());

        graphics.dispose();
        return image;

    }
     **/
}
