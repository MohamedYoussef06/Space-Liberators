/*******
 * CSCI 185
 * Antoun Elabd & Mohamed Youssef
 * Dr. Wenjia Li
 * Final Programming Project
 * 5/8/2025
 *******/

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.List;
import javax.swing.ImageIcon;

public class Waves {
    private final Display display;
    private final List<GameObject> gameObjects;

    private boolean StartWave = false;
    private long WaveStart = 0L;
    private final long WaveDisp = 2000L;
    private boolean waveTextend = false;
    private boolean waveEnemy = false;
    private int currentWave = 1;
    private Image waveImg = new ImageIcon("window/window/Images/Wave 1.png").getImage();

    public Waves(Display display, List<GameObject> gameObjects) {
        this.display = display;
        this.gameObjects = gameObjects;
    }

    public void update() {
        if (!StartWave && !display.isBackgroundSlowing()) {
            StartWave = true;
            WaveStart = System.currentTimeMillis();
        }

        if (StartWave && !waveTextend) {
            if (System.currentTimeMillis() - WaveStart >= WaveDisp) {
                waveTextend = true;
            }
            return;
        }

        if (waveTextend && !waveEnemy) {
            if (currentWave == 1) {
                spawnWave1();
            } else if (currentWave == 2) {
                spawnWave2();
            }
            waveEnemy = true;
        }
        if (waveEnemy && numOfEnemies() == 0) {
            if (currentWave < 2) {
                nextWave();
            }
        }
    }

    public int numOfEnemies() {
        int numOfEnemies = 0;
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Enemy) {
                numOfEnemies++;
            }

        }
        return numOfEnemies;
    }

    public void nextWave() {
        currentWave++;
        waveTextend = false;
        waveEnemy = false;
        StartWave = false;

        if (currentWave == 1) {


            waveImg = new ImageIcon("window/window/Images/Wave 1.png").getImage();
        }
        else if (currentWave == 2) {
            waveImg = new ImageIcon("window/window/Images/Wave 2.png").getImage();
        }
    }



    public void render(Graphics2D g) {
        if (StartWave && !waveTextend) {
            int iw = waveImg.getWidth(null);
            int ih = waveImg.getHeight(null);
            int cx = display.getCanvas().getWidth();
            int cy = display.getCanvas().getHeight();
            g.drawImage(waveImg, (cx - iw) / 2, (cy - ih) / 2, null);
        }
    }

    private void spawnWave1() {
        int firstRowCount = 7;
        int secondRowCount = 4;
        int gap = 89;
        int row1Y = 50;
        int row2Y = 130;

        int x = 100;
        for (int i = 0; i < firstRowCount; i++) {
            Enemy e = new Enemy(display.getCanvas().getWidth());
            e.setPosition(new Position(x, row1Y));
            gameObjects.add(e);
            x += gap;
        }
        x = 100;

        for (int i = 0; i < secondRowCount; i++) {
            Enemy enemy = new Enemy(display.getCanvas().getWidth());
            enemy.setPosition(new Position(x, row2Y));
            gameObjects.add(enemy);
            x += gap;

        }

    }


        private void spawnWave2 () {
            int NextRowCount = 7;
            int NextRowCont = 4;
            int nextgap = 89;
            int row1_2Y = 50;
            int row2_2Y = 130;

            int x_1 = 100;

            for (int i = 0; i < NextRowCount; i++) {
                Enemy enemy = new Enemy(display.getCanvas().getWidth());
                enemy.setPosition(new Position(x_1, row1_2Y));
                gameObjects.add(enemy);
                x_1 += nextgap;
            }

            x_1 = 100;

            for (int i = 0; i < NextRowCont; i++) {
                Enemy enemy = new Enemy(display.getCanvas().getWidth());
                enemy.setPosition(new Position(x_1, row2_2Y));
                gameObjects.add(enemy);
                x_1 += nextgap;

            }

        }




    public boolean isWaveDone() {
        return waveEnemy;
    }

    public int getCurrentWave() {
        return currentWave;
    }

}
