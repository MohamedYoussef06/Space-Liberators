/*******
 * CSCI 185
 * Antoun Elabd & Mohamed Youssef
 * Dr. Wenjia Li
 * Final Programming Project
 * 5/8/2025
 *******/

import javax.swing.*;
import java.awt.*;

public class Background extends JPanel {
    private Image backgroundImage = new ImageIcon("window/window/Images/SpaceBackground.png").getImage();
    private int offset = 0;

    private final int startmoving       = 2;
    private final int endmoving         = 40;
    private int CurrentMovement         = startmoving;
    private int FrameCount              = 0;

    private boolean slowing             = false;
    private long SlowingStartingTime    = 0L;
    private final long FastDuration     = 1200L;
    private final long slowDuration     = 3000L;

    public Background() {
        setFocusable(true);
    }

    public void startDeceleration() {
        slowing             = true;
        SlowingStartingTime = System.currentTimeMillis();
        CurrentMovement     = startmoving;
        FrameCount          = 0;
    }

    public void update(int height) {
        long elapsed = System.currentTimeMillis() - SlowingStartingTime;

        if (slowing) {
            if (elapsed <= FastDuration) {
                CurrentMovement = startmoving;
            } else if (elapsed <= FastDuration + slowDuration) {
                double t = (double)(elapsed - FastDuration) / slowDuration;
                CurrentMovement = startmoving +
                        (int)(t * (endmoving - startmoving));
            } else {
                CurrentMovement = endmoving;
                slowing         = false;
            }
        }

        FrameCount++;
        if (FrameCount >= CurrentMovement) {
            offset     = (offset + 1) % height;
            FrameCount = 0;
        }
    }

    public boolean isSlowing() {
        return slowing;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = getWidth(), h = getHeight();
        g.drawImage(backgroundImage, 0, offset,     w, h, this);
        g.drawImage(backgroundImage, 0, offset - h, w, h, this);
    }
}
