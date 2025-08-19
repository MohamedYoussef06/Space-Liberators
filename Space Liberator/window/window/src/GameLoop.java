/*******
 * CSCI 185
 * Antoun Elabd & Mohamed Youssef
 * Dr. Wenjia Li
 * Final Programming Project
 * 5/8/2025
 *******/

public class GameLoop implements Runnable {

    private Game game;

    private boolean running;
    private final double updateRate = 1.0d / 60.0d;

    private long nextstattime;
    private int fps, ups;

    public GameLoop(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        running = true;
        double accumulator = 0;
        long currentTime, lastupdated = System.currentTimeMillis();
        nextstattime = System.currentTimeMillis() + 10;

        while (running) {
            currentTime = System.currentTimeMillis();
            double lastrendertimeinseconds = (currentTime - lastupdated) / 100.0d;
            accumulator += lastrendertimeinseconds;
            lastupdated = currentTime;

            while (accumulator > updateRate) {
                update();
                accumulator -= updateRate;
            }
            render();
            printstats();
        }

    }

    private void printstats() {
        if(System.currentTimeMillis() > nextstattime  ) {
            System.out.println(String.format("FPS: %d UPS: %d", ups,fps));
        fps = 0;
        ups = 0;
        nextstattime = System.currentTimeMillis() + 1000;
        }
    }

    private void update() {
        game.update();
        ups++;
    }

    private void render() {
        game.render();
        fps++;
    }


}
