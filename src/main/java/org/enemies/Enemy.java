package org.enemies;

import org.helpers.Constants;

import java.awt.*;
import static org.helpers.Constants.Direction.*;

public abstract class Enemy {

    protected float x, y;
    protected Rectangle bounds;
    protected int health;
    protected int maxHealth;
    protected int ID;
    protected int enemyType;
    protected int lastDir;
    protected boolean alive = true;

    public Enemy(float x, float y, int ID, int enemyType) {
        this.x = x;
        this.y = y;
        this.ID = ID;
        this.enemyType = enemyType;
        bounds = new Rectangle((int) x, (int) y, 32, 32);
        lastDir = -1;
        setStartHealth();
    }

    private void setStartHealth() {
        health = Constants.Enemies.GetStartHealth(enemyType);
        maxHealth = health;
    }

    public void hurt(int damage) {
        this.health -= damage;
        if (health <= 0) {
            alive = false;
        }
    }

    public void move(float speed, int dir) {
        lastDir = dir;
        switch (dir) {
            case LEFT -> this.x -= speed;
            case UP -> this.y -= speed;
            case RIGHT -> this.x += speed;
            case DOWN -> this.y += speed;
        }
        updateHitbox();
    }

    private void updateHitbox() {
        //Для проверки коллизии снарядов
        bounds.x = (int) x;
        bounds.y = (int) y;
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public float getHealthBarFloat() {
        return health / (float) maxHealth;
    }
    //Нужен для визуализации здоровья

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getHealth() {
        return health;
    }

    public int getID() {
        return ID;
    }

    public int getEnemyType() {
        return enemyType;
    }

    public int getLastDir() {
        return lastDir;
    }
    public boolean isAlive() {return alive;}
}
