package org.inputs;

import org.main.Game;
import org.main.GameStates;
import org.scenes.GameScene;
import org.scenes.Settings;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static org.main.GameStates.*;

public class KeyboardListener implements KeyListener {
    private Game game;

    public KeyboardListener(Game game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameState == EDIT) {
            game.getEditor().keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
