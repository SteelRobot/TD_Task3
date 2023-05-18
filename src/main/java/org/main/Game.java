package org.main;

import org.helpers.LoadSave;
import org.managers.TileManager;
import org.scenes.Editing;
import org.scenes.Menu;
import org.scenes.Playing;
import org.scenes.Settings;

import javax.swing.*;

import static org.helpers.Constants.Temp.lvlName;

public class Game extends JFrame implements Runnable {

    private GameScreen gameScreen;
    private Thread gameThread;

    private final double FPS_SET = 120.0;
    private final double UPS_SET = 60.0;

    //Classes
    private Render render;
    private Menu menu;
    private Playing playing;
    private Settings settings;
    private Editing editing;

    private TileManager tileManager;

    public Game() {
        //Создание уровня
        createDefaultLevel();
        initClasses();
        //Создание самого окна
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        render = new Render(this);
        gameScreen = new GameScreen(this);
        setResizable(false);
        add(gameScreen);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initClasses() {
        tileManager = new TileManager();
        render = new Render(this);
        gameScreen = new GameScreen(this);
        menu = new Menu(this);
        playing = new Playing(this);
        settings = new Settings(this);
        editing = new Editing(this);
    }

    private void createDefaultLevel() {
        //Класс, состоящий только из травы
        int[] arr = new int[400];
        for (int i = 0; i < arr.length; i++)
            arr[i] = 0;

        LoadSave.CreateLevel(lvlName, arr);
    }

    private void start() {
        gameThread = new Thread(this) {
        };
        gameThread.start();
    }


    private void updateGame() {
        //Для обновления важных данных
        switch(GameStates.gameState) {
            case PLAYING -> playing.update();
            case MENU -> {}
            case SETTINGS -> {}
            case EDIT -> editing.update();
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.gameScreen.initInputs();
        game.start();
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long lastFrame = System.nanoTime();
        long lastUpdate = System.nanoTime();
        long lastTimeCheck = System.currentTimeMillis();

        int frames = 0;
        int updates = 0;

        long now;

        while (true) {
            now = System.nanoTime();
            if (now - lastFrame >= timePerFrame) {
                repaint();
                lastFrame = now;
                frames++;
            }
            if (System.nanoTime() - lastUpdate >= timePerUpdate) {
                updateGame();
                lastUpdate = now;
                updates++;
            }

            if (System.currentTimeMillis() - lastTimeCheck >= 1000) {
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
                lastTimeCheck = System.currentTimeMillis();
            }
        }
    }

    public Render getRender() {
        return render;
    }
    public Menu getMenu() {
        return menu;
    }
    public Settings getSettings() {
        return settings;
    }
    public Playing getPlaying() {
        return playing;
    }
    public Editing getEditor() {return editing;}
    public TileManager getTileManager() {return tileManager;}
}

