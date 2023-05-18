package org.enemies;

import static org.helpers.Constants.Enemies.GOLEM;

public class Golem extends Enemy {

    public Golem(float x, float y, int ID) {
        super(x, y, ID, GOLEM);
        health = 50;
    }
}
