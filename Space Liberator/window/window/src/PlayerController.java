/*******
 * CSCI 185
 * Antoun Elabd & Mohamed Youssef
 * Dr. Wenjia Li
 * Final Programming Project
 * 5/8/2025
 *******/

import java.awt.event.KeyEvent;

public class PlayerController implements Controller {

    private Controls controls;

    public PlayerController(Controls controls) {
        this.controls = controls;
    }

    @Override
    public boolean isrequestingup() {
        return controls.isKeyPressed(KeyEvent.VK_UP);
    }

    @Override
    public boolean isrequestingdown() {
        return controls.isKeyPressed(KeyEvent.VK_DOWN);
    }

    @Override
    public boolean isrequestingright() {
        return controls.isKeyPressed(KeyEvent.VK_RIGHT);
    }

    @Override
    public boolean isrequestingleft() {
        return controls.isKeyPressed(KeyEvent.VK_LEFT);
    }

    @Override
    public boolean isrequestingshooting() {
        return controls.isKeyPressed(KeyEvent.VK_SPACE);
    }

}
