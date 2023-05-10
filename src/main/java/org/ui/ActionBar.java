package org.ui;

import org.objects.Tile;
import org.scenes.Playing;

import java.awt.*;

import static org.main.GameStates.MENU;
import static org.main.GameStates.SetGameState;

public class ActionBar extends Bar {
    private MyButton bMenu;
    private Playing playing;


    public ActionBar(int x, int y, int width, int height, Playing playing) {
        super(x, y, width, height);

        this.playing = playing;

        initButtons();
    }

    private void initButtons() {
        bMenu = new MyButton("Меню", 2, 642, 100, 30);
    }

    private void drawButtons(Graphics g) {
        bMenu.draw(g);
    }

    public void draw(Graphics g) {

        g.setColor(new Color(220, 123, 14));
        g.fillRect(x, y, width, height);

        drawButtons(g);
    }


    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            SetGameState(MENU);
        }
    }


    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);

        if (bMenu.getBounds().contains(x, y))
            bMenu.setMouseOver(true);
    }


    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y))
            bMenu.setMousePressed(true);

    }


    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();
    }
}
