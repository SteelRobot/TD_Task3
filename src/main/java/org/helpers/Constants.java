package org.helpers;


import java.util.ArrayList;

import static org.main.langTexts.*;

public class Constants {

    public static class Direction {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class Enemies {
        public static final int GOLEM = 0;
        public static final int SLIME = 1;
        public static final int BOAR = 2;
        public static final int GOBLIN = 3;

        public static int getAward(int enemyType) {
            return switch (enemyType) {
                case GOLEM -> 5;
                case SLIME -> 25;
                case BOAR -> 5;
                case GOBLIN -> 10;
                default -> 0;
            };
        }

        public static float getSpeed(int enemyType) {
            return switch (enemyType) {
                case GOLEM -> 0.5f;
                case SLIME -> 0.65f;
                case BOAR -> 0.3f;
                case GOBLIN -> 0.75f;
                default -> 0;

            };
        }

        public static int getStartHealth(int enemyType) {
            return switch (enemyType) {
                case GOLEM -> 100;
                case SLIME -> 40;
                case BOAR -> 250;
                case GOBLIN -> 85;
                default -> 0;
            };
        }
    }

    public static class Towers {
        public static final int CANNON = 0;
        public static final int ARCHER = 1;
        public static final int WIZARD = 2;

        public static int getStartDamage(int towerType) {
            return switch (towerType) {
                case CANNON -> 20;
                case ARCHER -> 5;
                case WIZARD -> 2;
                default -> 0;
            };
        }

        public static float getStartRange(int towerType) {
            return switch (towerType) {
                case CANNON -> 50;
                case ARCHER -> 100;
                case WIZARD -> 75;
                default -> 0;
            };
        }

        public static float getDefaultCooldown(int towerType) {
            return switch (towerType) {
                case CANNON -> 120;
                case ARCHER -> 40;
                case WIZARD -> 35;
                default -> 0;
            };
        }


        public static String getName(int towerType) {
            return switch (towerType) {
                case CANNON -> cannon.toString();
                case ARCHER -> archer.toString();
                case WIZARD -> wizard.toString();
                default -> "";
            };
        }

        public static int getTowerCost(int towerType) {
            return switch (towerType) {
                case CANNON -> 65;
                case ARCHER -> 30;
                case WIZARD -> 45;
                default -> 0;
            };
        }
    }

    public static class Tiles {
        public static final int WATER_TILE = 0;
        public static final int GRASS_TILE = 1;
        public static final int ROAD_TILE = 2;
    }

    public static class Projectiles {
        public static final int ARROW = 0;
        public static final int CHAINS = 1;
        public static final int BOMB = 2;

        public static float getSpeed(int type) {
            return switch (type) {
                case ARROW -> 8f;
                case BOMB -> 4f;
                case CHAINS -> 6f;
                default -> 0f;
            };
        }
    }
}
