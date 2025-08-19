/*******
 * CSCI 185
 * Antoun Elabd & Mohamed Youssef
 * Dr. Wenjia Li
 * Final Programming Project
 * 5/8/2025
 *******/

import javax.swing.*;
import java.awt.*;

public class ScoreDisplay {

    private JPanel scoreDisplay;
    private Font score;
    private Game game;
    private Font GameOver;
    private Color Color;

    public ScoreDisplay(Game game) {
        this.game = game;
        this.score = new Font("Nebula", Font.BOLD, 20);
        this.GameOver = new Font("Nebula", Font.BOLD, 20);
        this.Color = Color.GREEN;
    }


    public void render(Graphics graphics) {

        graphics.setColor(Color);
        graphics.setFont(score);
        graphics.drawString("Score: " + game.getScore(), 20, 30);
    }

    public void renderGameOver(Graphics graphics) {
        graphics.setFont(GameOver);
        graphics.setColor(Color.BLACK);

        String gameover = "Game Over";
        String finalscore = "Final Score: " + game.getScore();
        graphics.drawString(gameover, 20, 30);
        graphics.drawString(finalscore, 20, 40);
    }

    
}
