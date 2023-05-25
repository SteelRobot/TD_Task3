package org.main;

import static org.main.LangStates.*;

public enum langTexts {
    bMenuStr("Меню", "Menu"), bPlayingStr("Играть", "Play"), bEditStr("Редактор", "Editor"),
    bQuitStr("Выход", "Quit"), bReplayStr("Заново", "Replay"), bPauseStr("Пауза", "Pause"),
    bUnpauseStr("Продолжить", "Unpause"), bSaveStr("Сохранить", "Save"),
    bSellStr("Продать", "Sell"), bUpgradeStr("Улучшить", "Upgrade"),
    pauseStr("НА ПАУЗЕ", "PAUSED"),
    noMoneyStr("Нет денег", "No money"), costStr("Цена: ", "Cost: "),
    moneyCountStr("Денег: ", "Money: "),
    waveStr("Волна ", "Wave "), enemyCountStr("Врагов осталось: ", "Enemies left: "),
    timeCounterStr("Времени: ", "Time til wave: "), currencyStr("р", "g"),
    upgradeLevelStr("Уровень: ", "Level: "),
    gameOverStr("Игра окончена", "Game Over!"), gameWinStr("Победа", "Victory!"),
    livesStr("Жизни: ", "Lives: "), archer("Лучник", "Archer"),
    cannon("Пушка!!", "Cannon"), wizard("Маг", "Wizard"),
    ;


    langTexts(String ru, String en) {
        ru_value = ru;
        en_value = en;
    }

    private String ru_value;
    private String en_value;

    @Override
    public String toString() {
        if (langState == RUSSIAN)
            return ru_value;
        else if (langState == ENGLISH)
            return en_value;
        return "";
    }
}
