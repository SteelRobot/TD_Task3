package org.managers;

import org.enemies.*;
import org.helpers.LoadSave;
import org.objects.PathPoint;
import org.scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static org.helpers.Constants.Direction.*;
import static org.helpers.Constants.Enemies.*;
import static org.helpers.Constants.Tiles.*;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[] enemyImgs;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private PathPoint start, end;
    private int hpBarWidth = 20;

    public EnemyManager(Playing playing, PathPoint start, PathPoint end) {
        this.playing = playing;
        this.start = start;
        this.end = end;
        enemyImgs = new BufferedImage[4];

        addEnemy(GOLEM);
        addEnemy(BOAR);
        addEnemy(SLIME);
        addEnemy(GOBLIN);

        loadEnemyImgs();
    }

    private void loadEnemyImgs() {
        //Загружает картинки врагов из png со спрайтами
        BufferedImage atlas = LoadSave.getSpriteAtlas();

        for (int i = 0; i < 4; i++) {
            enemyImgs[i] = atlas.getSubimage(i * 32, 32, 32, 32);
        }

    }

    public void update() {
        for (Enemy e : enemies)
            if (e.isAlive())
                updateEnemyMove(e);
    }

    public void updateEnemyMove(Enemy e) {
        //Двигает если есть куда двигаться, а если стоит на базе, то стоит и ну короче -жизни
        if (e.getLastDir() == -1)
            setNewDirectionAndMove(e);

        int newX = (int) (e.getX() + getSpeedAndWidth(e.getLastDir(), e.getEnemyType()));
        int newY = (int) (e.getY() + getSpeedAndHeight(e.getLastDir(), e.getEnemyType()));

        if (getTileType(newX, newY) == ROAD_TILE) {
            e.move(GetSpeed(e.getEnemyType()), e.getLastDir());
        } else if (isAtEnd(e)) {
            System.out.println("-жизнь :(");
        } else {
            setNewDirectionAndMove(e);
        }
    }

    private void setNewDirectionAndMove(Enemy e) {
        //Говорит куда идти в самый начальный момент появления
        int dir = e.getLastDir();

        int xCord = (int) (e.getX() / 32);
        int yCord = (int) (e.getY() / 32);

        fixEnemyOffsetTile(e, dir, xCord, yCord);

        if (isAtEnd(e))
            return;

        if (dir == LEFT || dir == RIGHT) {
            int newY = (int) (e.getY() + getSpeedAndHeight(UP, e.getEnemyType()));
            if (getTileType((int) e.getX(), newY) == ROAD_TILE)
                e.move(GetSpeed(e.getEnemyType()), UP);
            else
                e.move(GetSpeed(e.getEnemyType()), DOWN);
        } else {
            int newX = (int) (e.getX() + getSpeedAndWidth(RIGHT, e.getEnemyType()));
            if (getTileType(newX, (int) e.getY()) == ROAD_TILE)
                e.move(GetSpeed(e.getEnemyType()), RIGHT);
            else
                e.move(GetSpeed(e.getEnemyType()), LEFT);
        }
    }

    private void fixEnemyOffsetTile(Enemy e, int dir, int xCord, int yCord) {
        //Чинит всякие оффсеты, если враг спавнится слева или сверху от дороги
        switch (dir) {
            case RIGHT -> {
                if (xCord < 19)
                    xCord++;
            }
            case DOWN -> {
                if (yCord < 19)
                    yCord++;
            }
        }
        e.setPos(xCord * 32, yCord * 32);
    }

    private boolean isAtEnd(Enemy e) {
        return e.getX() == end.getxCord() * 32 && e.getY() == end.getyCord() * 32;
    }

    private int getTileType(int x, int y) {
        return playing.getTileType(x, y);
    }

    private float getSpeedAndHeight(int dir, int enemyType) {
        if (dir == UP) {
            return -GetSpeed(enemyType);
        } else if (dir == DOWN) {
            return GetSpeed(enemyType) + 32;
        }
        return 0;
    }

    private float getSpeedAndWidth(int dir, int enemyType) {
        if (dir == LEFT) {
            return -GetSpeed(enemyType);
        } else if (dir == RIGHT) {
            return GetSpeed(enemyType) + 32;
        }
        return 0;
    }

    public void addEnemy(int enemyType) {

        int x = start.getxCord() * 32;
        int y = start.getyCord() * 32;

        switch (enemyType) {
            case GOLEM -> enemies.add(new Golem(x, y, 0));
            case SLIME -> enemies.add(new Slime(x, y, 0));
            case BOAR -> enemies.add(new Boar(x, y, 0));
            case GOBLIN -> enemies.add(new Goblin(x, y, 0));
        }
    }

    public void draw(Graphics g) {
        for (Enemy e : enemies) {
            if (e.isAlive()) {
                drawEnemy(e, g);
                drawHealthBar(e, g);
            }
        }
    }

    private void drawHealthBar(Enemy e, Graphics g) {
        g.setColor(Color.RED);
        g.fillRect((int) e.getX() + 16 - (getNewBarWidth(e) / 2), (int) e.getY() - 10, getNewBarWidth(e), 3);
    }

    private int getNewBarWidth(Enemy e) {
        return (int) (hpBarWidth * e.getHealthBarFloat());
    }

    private void drawEnemy(Enemy e, Graphics g) {
        g.drawImage(enemyImgs[e.getEnemyType()], (int) e.getX(), (int) e.getY(), null);
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
}
