package org.objects;

import org.helpers.Constants;

import static org.helpers.Constants.Towers.*;

public class Tower {
    private int x, y, id, towerType, cdTick, dmg, tier;
    private float range, cooldown;
    private boolean side = false; //false - лево, true - право

    public Tower(int x, int y, int id, int towerType, boolean side) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.towerType = towerType;
        this.side = side;
        tier = 1;
        setDefaultDamage();
        setDefaultRange();
        setDefaultCooldown();
    }

    public void update() {
        cdTick++;
    }

    public void upgradeTower() {
        tier++;

        switch (towerType) {
            case ARCHER:
                dmg += 2;
                range += 20;
                cooldown -= 5;
                break;
            case CANNON:
                dmg += 5;
                range += 20;
                cooldown -= 15;
                break;
            case WIZARD:
                dmg += 1;
                range += 20;
                cooldown -= 10;
                break;
        }
    }

    public boolean isCooldownOver() {
        return (cdTick >= cooldown);
    }

    public void getResetCooldown() {
        cdTick = 0;
    }

    private void setDefaultCooldown() {
        cooldown = Constants.Towers.getDefaultCooldown(towerType);
    }

    private void setDefaultRange() {
        range = Constants.Towers.getStartRange(towerType);
    }

    private void setDefaultDamage() {
        dmg = Constants.Towers.getStartDamage(towerType);
    }
    public void setSide(boolean side) {this.side = side;}


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTowerType() {
        return towerType;
    }


    public float getDmg() {
        return dmg;
    }

    public float getRange() {
        return range;
    }

    public boolean getSide() {
        return side;
    }

    public int getTier() {
        return tier;
    }
}
