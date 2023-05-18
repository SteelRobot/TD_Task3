package org.helpers;


public class Constants {

    public static class Temp {
        public static final String lvlName = "New Level";
    }

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

        public static float GetSpeed(int enemyType) {
            return switch (enemyType) {
                case GOLEM -> 0.5f;
                case SLIME -> 0.65f;
                case BOAR -> 0.3f;
                case GOBLIN -> 0.75f;
                default -> 0;

            };
        }

        public static int GetStartHealth(int enemyType) {
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
                case CANNON -> 15;
                case ARCHER -> 5;
                case WIZARD -> 2;
                default -> 0;
            };
        }

        public static float getStartRange(int towerType) {
            return switch (towerType) {
                case CANNON -> 100;
                case ARCHER -> 100;
                case WIZARD -> 100;
                default -> 0;
            };
        }

        public static float getDefaultCooldown(int towerType) {
            return switch (towerType) {
                case CANNON -> 120;
                case ARCHER -> 25;
                case WIZARD -> 40;
                default -> 0;
            };
        }


        public static String GetName(int towerType) {
            return switch (towerType) {
                case CANNON -> "Cannon";
                case ARCHER -> "Archer";
                case WIZARD -> "Wizard";
                default -> "";
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

        public static float GetSpeed(int type) {
            return switch (type) {
                case ARROW -> 8f;
                case BOMB -> 4f;
                case CHAINS -> 6f;
                default -> 0f;
            };
        }
    }
}
