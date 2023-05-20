package org.scenes;

import org.helpers.LoadSave;
import org.main.Game;
import org.ui.MyButton;

import java.awt.*;

import static org.main.GameStates.*;
import static org.main.LangStates.*;

public class Menu extends GameScene implements SceneMethods {
    private MyButton bPlaying, bSettings, bQuit, bEdit, bEng, bRus;

    public Menu(Game game) {
        super(game);
        initButtons();
    }

    private void initButtons() {
        int w = 150;
        int h = w / 2;
        int x = 640 / 2 - w / 2;
        int y = 150;
        int yOffset = 100;
        bPlaying = new MyButton("Играть", x, y, w, h);
        bEdit = new MyButton("Редактор", x, y + yOffset, w, h);
        bSettings = new MyButton("Настройки", x, y + yOffset * 2, w, h);
        bQuit = new MyButton("Выход", x, y + yOffset * 3, w, h);
        bEng = new MyButton("", 245, 25, 50, 50);
        bRus = new MyButton("", 345, 25, 50, 50);
    }

    private void buttonsSetText() {
        switch (langState) {
            case RUSSIAN -> {
                bPlaying.setText("Играть");
                bEdit.setText("Редактор");
                bSettings.setText("Настройки");
                bQuit.setText("Выход");
            }
            case ENGLISH -> {
                bPlaying.setText("Play");
                bEdit.setText("Editor");
                bSettings.setText("Settings");
                bQuit.setText("Quit");
            }
        }
    }

    @Override
    public void render(Graphics g) {
        drawButtons(g);
    }

    private void drawButtons(Graphics g) {
        bPlaying.draw(g);
        bEdit.draw(g);
        bSettings.draw(g);
        bQuit.draw(g);

        drawFlags(g);

        drawButtonFeedback(g, bEng);
        drawButtonFeedback(g, bRus);
    }

    private void drawFlags(Graphics g) {
        g.drawImage(LoadSave.getFlagImage("British.jpg"), bEng.x, bEng.y, bEng.width, bEng.height, null);
        g.drawImage(LoadSave.getFlagImage("Russian.jpg"), bRus.x, bRus.y, bRus.width, bRus.height, null);
    }

    protected void drawButtonFeedback(Graphics g, MyButton b) {
        //Рисует границы на кнопке, чтобы показать, что она интерактивная
        if (b.isMouseOver()) {
            g.setColor(Color.white);
        } else {
            g.setColor(Color.black);
        }
        g.drawRect(b.x, b.y, b.width, b.height);
        if (b.isMousePressed()) {
            g.drawRect(b.x + 1, b.y + 1, b.width - 2, b.height - 2);
            g.drawRect(b.x + 2, b.y + 2, b.width - 4, b.height - 4);
        }
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (bPlaying.getBounds().contains(x, y)) {
            getGame().getPlaying().getActionBar().setLangTexts();
            getGame().getPlaying().getActionBar().setButtonText();
            SetGameState(PLAYING);
        } else if (bEdit.getBounds().contains(x, y)) {
            SetGameState(EDIT);
        } else if (bSettings.getBounds().contains(x, y)) {
            SetGameState(SETTINGS);
        } else if (bEng.getBounds().contains(x, y)) {
            SetLangState(ENGLISH);
            buttonsSetText();
        } else if (bRus.getBounds().contains(x, y)) {
            SetLangState(RUSSIAN);
            buttonsSetText();
        } else if (bQuit.getBounds().contains(x, y)) {
            System.exit(0);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        bPlaying.setMouseOver(false);
        bEdit.setMouseOver(false);
        bSettings.setMouseOver(false);
        bQuit.setMouseOver(false);
        bEng.setMouseOver(false);
        bRus.setMouseOver(false);
        if (bPlaying.getBounds().contains(x, y)) {
            bPlaying.setMouseOver(true);
        } else if (bEdit.getBounds().contains(x, y)) {
            bEdit.setMouseOver(true);
        } else if (bSettings.getBounds().contains(x, y)) {
            bSettings.setMouseOver(true);
        } else if (bEng.getBounds().contains(x, y)) {
            bEng.setMouseOver(true);
        } else if (bRus.getBounds().contains(x, y)) {
            bRus.setMouseOver(true);
        } else if (bQuit.getBounds().contains(x, y)) {
            bQuit.setMouseOver(true);
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if (bPlaying.getBounds().contains(x, y)) {
            bPlaying.setMousePressed(true);
        } else if (bEdit.getBounds().contains(x, y)) {
            bEdit.setMousePressed(true);
        } else if (bSettings.getBounds().contains(x, y)) {
            bSettings.setMousePressed(true);
        } else if (bEng.getBounds().contains(x, y)) {
            bEng.setMousePressed(true);
        } else if (bRus.getBounds().contains(x, y)) {
            bRus.setMousePressed(true);
        } else if (bQuit.getBounds().contains(x, y)) {
            bQuit.setMousePressed(true);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        resetButtons();
    }

    @Override
    public void mouseDragged(int x, int y) {

    }

    private void resetButtons() {
        bPlaying.resetBooleans();
        bEdit.resetBooleans();
        bSettings.resetBooleans();
        bQuit.resetBooleans();
        bEng.resetBooleans();
        bRus.resetBooleans();
    }
}
