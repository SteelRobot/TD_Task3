package org.main;

public enum GameStates {
//Перечисление всех состояний программы
    PLAYING, MENU, SETTINGS, EDIT;

    public static GameStates gameState = MENU;

    public static void SetGameState(GameStates state) {
        gameState = state;
    }

}
