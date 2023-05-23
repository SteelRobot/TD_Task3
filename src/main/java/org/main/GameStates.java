package org.main;

public enum GameStates {
//Перечисление всех состояний программы
    PLAYING, MENU, SETTINGS, EDIT, GAME_OVER;

    public static GameStates gameState = MENU;

    public static void SetGameState(GameStates state) {
        gameState = state;
    }

}
