/*******
 * CSCI 185
 * Antoun Elabd & Mohamed Youssef
 * Dr. Wenjia Li
 * Final Programming Project
 * 5/8/2025
 *******/

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Display extends JFrame {
    private Canvas canvas;
    private BufferStrategy bs;
    private Graphics2D g;
    private Background bg;
    private Image logoImage;
    private boolean splashActive = true;
    private long splashStartTime;
    private static final long SPLASH_DURATION = 5000L;
    private ScoreDisplay scoreDisplay;

    private JPanel controlPanel;
    private JButton startButton, pauseButton, saveButton;
    private JLabel nameLabel;
    private JTextField nameField;

    private Game currentGame;
    private boolean paused = false;

    public Display(int width, int height) {
        super("Space Liberators");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        setLayout(new BorderLayout());

        controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        startButton = new JButton("Start");
        pauseButton = new JButton("Pause");
        nameLabel = new JLabel("Name:");
        nameField = new JTextField(10);
        saveButton = new JButton("Save Score");

        controlPanel.add(startButton);
        controlPanel.add(pauseButton);
        controlPanel.add(nameLabel);
        controlPanel.add(nameField);
        controlPanel.add(saveButton);

        add(controlPanel, BorderLayout.NORTH);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        add(canvas, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        logoImage = new ImageIcon("window/window/Images/Logo.png").getImage();
        canvas.createBufferStrategy(2);
        bs = canvas.getBufferStrategy();

        bg = new Background();
        bg.setSize(width, height);
        bg.startDeceleration();
        splashStartTime = System.currentTimeMillis();

        startButton.addActionListener(e -> {
            if (splashActive) {
                splashActive = false;
                bg.startDeceleration();
                paused = false;
                pauseButton.setText("Pause");
                canvas.requestFocusInWindow();
            }
        });

        pauseButton.addActionListener(e -> {
            paused = !paused;
            pauseButton.setText(paused ? "Resume" : "Pause");
        });

        saveButton.addActionListener(e -> {
            if (currentGame == null) {
                JOptionPane.showMessageDialog(this,
                        "Game isn’t running yet!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String name = nameField.getText().trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Please enter your name above!", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    try (PrintWriter out = new PrintWriter(
                            new BufferedWriter(new FileWriter("scores.txt", true)))) {
                        out.println(name + " – " + currentGame.getScore());
                        JOptionPane.showMessageDialog(this,
                                "Score saved!", "Done", JOptionPane.INFORMATION_MESSAGE);
                        saveButton.setEnabled(false);
                        nameField.setEnabled(false);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this,
                                "Error saving score:\n" + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    public void setGame(Game game) {
        this.currentGame = game;
        this.scoreDisplay = new ScoreDisplay(game);
    }

    public boolean isSplashActive() { return splashActive; }
    public boolean isPaused()       { return paused; }
    public boolean isBackgroundSlowing() { return bg.isSlowing(); }

    public void render(Game game) {
        if (currentGame == null) {
            setGame(game);
        }

        g = (Graphics2D) bs.getDrawGraphics();
        startButton.setEnabled(splashActive);

        if (game.isGameOver()) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            String msg = "Enter name above and Save Score unless you have done so already";
            FontMetrics fm = g.getFontMetrics();
            int tx = (canvas.getWidth() - fm.stringWidth(msg)) / 2;
            int ty = canvas.getHeight() / 2;
            g.drawString(msg, tx, ty);

            g.dispose();
            bs.show();
            return;
        }

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        long now = System.currentTimeMillis();

        if (splashActive) {
            int w = logoImage.getWidth(null), h = logoImage.getHeight(null);
            g.drawImage(logoImage,
                    (canvas.getWidth() - w) / 2,
                    (canvas.getHeight() - h) / 2,
                    null
            );
            if (now - splashStartTime >= SPLASH_DURATION) {
                splashActive = false;
                bg.startDeceleration();
            }
            g.dispose();
            bs.show();
            return;
        }

        bg.setSize(canvas.getWidth(), canvas.getHeight());
        bg.update(canvas.getHeight());
        bg.paintComponent(g);

        game.getWaves().render(g);
        if (scoreDisplay != null) {
            scoreDisplay.render(g);
        }
        for (GameObject go : game.getGameObjects()) {
            g.drawImage(
                    go.getSprite(),
                    go.getPosition().getX(),
                    go.getPosition().getY(),
                    go.getSize().getWidth(),
                    go.getSize().getHeight(),
                    null
            );
        }

        if (paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.drawString("PAUSED",
                    canvas.getWidth() / 2 - 100,
                    canvas.getHeight() / 2
            );
        }

        g.dispose();
        bs.show();
    }

    public Canvas getCanvas() {
        return canvas;
    }
}