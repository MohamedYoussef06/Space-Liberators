/*******
 * CSCI 185
 * Antoun Elabd & Mohamed Youssef
 * Dr. Wenjia Li
 * Final Programming Project
 * 5/8/2025
 *******/

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Game {

    private Display display;
    private List<GameObject> gameObjects;
    private Player player;
    private Controls controls;
    private PlayerController playerController;
    private List<Enemy> Mobs;
    private List<Bullets> Bullets;
    private Random random;
    private Waves waves;
    private int score;
    private boolean gameOver;

    public Game(int width, int height) {
        display = new Display(width, height);
        display.setGame(this);

        gameObjects = new ArrayList<>();
        Mobs = new ArrayList<>();
        Bullets = new ArrayList<>();
        random = new Random();
        controls = new Controls();
        score = 0;

        display.getCanvas().addKeyListener(controls);
        display.getCanvas().setFocusable(true);
        display.getCanvas().requestFocusInWindow();

        playerController = new PlayerController(controls);
        player = new Player(playerController);
        gameObjects.add(player);

        waves = new Waves(display, gameObjects);
    }

    public void update() {
        if (display.isSplashActive() || display.isPaused()) {
            return;
        }

        waves.update();

        for (GameObject obj : gameObjects) {
            obj.update();
        }

        int canvasW = display.getCanvas().getWidth();
        int canvasH = display.getCanvas().getHeight();
        int barrierY = canvasH / 2 - 50;
        for (GameObject obj : gameObjects) {
            if (obj instanceof Enemy) {
                int x = obj.getPosition().getX();
                int y = obj.getPosition().getY();
                x = Math.max(0, Math.min(x, canvasW - obj.getSize().getWidth()));
                y = Math.max(0, Math.min(y, barrierY));
                obj.setPosition(new Position(x, y));
            }
        }

        if (player.canShoot()) {
            Bullets b = player.shoot();
            Bullets.add(b);
            gameObjects.add(b);
        }

        CollisionsCheck();
        removeDeadObjects();

        if (waves.getCurrentWave() == 2
                && waves.isWaveDone()
                && waves.numOfEnemies() == 0) {
            setGameOver(true);
        }
    }

    private void CollisionsCheck() {
        List<GameObject> objectsToRemove = new ArrayList<>();

        for (GameObject bullet : gameObjects) {
            if (!(bullet instanceof Bullets)) continue;

            Rectangle bulletRect = new Rectangle(
                    bullet.getPosition().getX(),
                    bullet.getPosition().getY(),
                    bullet.getSize().getWidth(),
                    bullet.getSize().getHeight()
            );

            for (GameObject enemy : gameObjects) {
                if (!(enemy instanceof Enemy)) continue;

                Rectangle enemyRect = new Rectangle(
                        enemy.getPosition().getX(),
                        enemy.getPosition().getY(),
                        enemy.getSize().getWidth(),
                        enemy.getSize().getHeight()
                );

                if (bulletRect.intersects(enemyRect)) {
                    objectsToRemove.add(enemy);
                    objectsToRemove.add(bullet);
                    setScore(100);
                    break;
                }
            }
        }

        gameObjects.removeAll(objectsToRemove);
        Mobs.removeIf(e -> !gameObjects.contains(e));
        Bullets.removeIf(b -> !gameObjects.contains(b));
    }

    private void removeDeadObjects() {
        int canvasH = display.getCanvas().getHeight();
        Iterator<GameObject> it = gameObjects.iterator();
        while (it.hasNext()) {
            GameObject obj = it.next();
            if (obj instanceof Bullets) {
                int y = obj.getPosition().getY();
                if (y < 0 || y > canvasH) {
                    it.remove();
                    Bullets.remove(obj);
                }
            }
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int points) {
        score += points;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void render() {
        display.render(this);
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public Waves getWaves() {
        return waves;
    }
}
