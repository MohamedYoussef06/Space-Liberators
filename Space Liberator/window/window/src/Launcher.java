/*******
 * CSCI 185
 * Antoun Elabd & Mohamed Youssef
 * Dr. Wenjia Li
 * Final Programming Project
 * 5/8/2025
 *******/

import javax.swing.*;

public class Launcher {
    public static void main(String[] args) {
        new Thread(new GameLoop(new Game(800,600))).start();

    }
}