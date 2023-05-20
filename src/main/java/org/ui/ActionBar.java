package org.ui;

import org.helpers.Constants;
import org.helpers.LoadSave;
import org.objects.Tower;
import org.scenes.Playing;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import static org.main.GameStates.MENU;
import static org.main.GameStates.SetGameState;
import static org.main.LangStates.langState;

public class ActionBar extends Bar {
    private MyButton bMenu;
    private Playing playing;
    private MyButton[] towerButtons;
    private MyButton bUpgradeTower, bSellTower, bPause;
    private Tower selectedTower;
    private Tower displayedTower;
    private BufferedImage selectedTowerBorder;
    private Color rangeColor = new Color(1, 1, 1, .4f);
    private DecimalFormat formatter;

    private int gold = 100;

    private boolean showTowerCost;
    private int towerCostType;
    private String bMenuStr, bPauseStr, bUnpauseStr, bSellStr, bUpgradeStr, pauseStr, noMoneyStr, costStr,
            moneyCountStr, waveStr, enemyCountStr, timeCounterStr, currencyStr, upgradeLevelStr;


    public ActionBar(int x, int y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        formatter = new DecimalFormat("0.0");
        initButtons();
        setLangTexts();

        selectedTowerBorder = getSelectedTowerBorder();
    }

    public void setLangTexts() {
        switch (langState) {
            case RUSSIAN -> {
                bMenuStr = "Меню";
                bPauseStr = "Пауза";
                bUnpauseStr = "Продолжить";
                bSellStr = "Продать";
                bUpgradeStr = "Улучшить";
                pauseStr = "НА ПАУЗЕ";
                noMoneyStr = "Нет денег";
                costStr = "Цена: ";
                moneyCountStr = "Денег: ";
                waveStr = "Волна ";
                enemyCountStr = "Врагов осталось: ";
                timeCounterStr = "Времени: ";
                currencyStr = "р";
                upgradeLevelStr = "Уровень: ";
            }
            case ENGLISH -> {
                bMenuStr = "Menu";
                bPauseStr = "Pause";
                bUnpauseStr = "Unpause";
                bSellStr = "Sell";
                bUpgradeStr = "Upgrade";
                pauseStr = "PAUSED";
                noMoneyStr = "No money";
                costStr = "Cost: ";
                moneyCountStr = "Money: ";
                waveStr = "Wave ";
                enemyCountStr = "Enemies left: ";
                timeCounterStr = "Time til wave: ";
                currencyStr = "g";
                upgradeLevelStr = "Level: ";
            }
        }
    }

    private void initButtons() {
        //Кнопочки башен
        bMenu = new MyButton("", 2, 642, 100, 30);
        bPause = new MyButton("", 2, 682, 100, 30);

        towerButtons = new MyButton[3];

        int w = 50;
        int h = 50;
        int xStart = 110;
        int yStart = 650;
        int xOffset = (int) (w * 1.1f);

        for (int i = 0; i < towerButtons.length; i++) {
            towerButtons[i] = new MyButton("", xStart + xOffset * i, yStart, w, h, i);
        }
        bSellTower = new MyButton("", 420, 702, 80, 25);
        bUpgradeTower = new MyButton("", 545, 702, 80, 25);
    }

    public void setButtonText() {
        bMenu.setText(bMenuStr);
        bPause.setText(bPauseStr);
        bSellTower.setText(bSellStr);
        bUpgradeTower.setText(bUpgradeStr);
    }

    private void drawButtons(Graphics g) {

        bMenu.draw(g);
        bPause.draw(g);

        for (MyButton b : towerButtons) {
            g.setColor(Color.GRAY);
            g.fillRect(b.x, b.y, b.width, b.height);
            g.drawImage(playing.getTowerManager().getTowerImgs()[b.getId()], b.x, b.y, b.width, b.height, null);
            drawButtonFeedback(g, b);
        }
    }

    public void draw(Graphics g) {

        g.setColor(new Color(220, 123, 14));
        g.fillRect(x, y, width, height);

        drawButtons(g);

        drawDisplayedTower(g);

        drawWaveInfo(g);

        drawGoldAmount(g);
        if (showTowerCost)
            drawTowerCost(g);

        if (playing.isGamePaused()) {
            g.setColor(Color.YELLOW);
            g.drawString(pauseStr, 110, 790);
        }
    }

    private void drawTowerCost(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(280, 650, 120, 50);
        g.setColor(Color.BLACK);
        g.drawRect(280, 650, 120, 50);

        g.drawString(getTowerCostName(), 285, 670);
        if (!isGoldEnoughForTower(towerCostType)) {
            g.drawString(noMoneyStr, 285, 725);
            g.setColor(Color.RED);
        } else
            g.setColor(Color.BLACK);
        g.drawString(costStr + getTowerCost() + currencyStr, 285, 695);
    }

    private int getTowerCost() {
        return Constants.Towers.getTowerCost(towerCostType);
    }

    private String getTowerCostName() {
        return Constants.Towers.getName(towerCostType);
    }

    private void drawGoldAmount(Graphics g) {
        g.drawString(moneyCountStr + gold + currencyStr, 110, 725);
    }

    private void drawWaveInfo(Graphics g) {
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        drawWaveTimerInfo(g);
        drawEnemiesLeftInfo(g);
        drawWaveLeftInfo(g);
    }

    private void drawWaveLeftInfo(Graphics g) {
        int current = playing.getWaveManager().getWaveIndex();
        int size = playing.getWaveManager().getWaves().size();
        g.drawString(waveStr + (current + 1) + "/" + size, 425, 770);
    }

    private void drawEnemiesLeftInfo(Graphics g) {
        g.setColor(Color.BLACK);
        int remaining = playing.getEnemyManager().getAmountOfAllLiveEnemies();
        g.drawString(enemyCountStr + remaining, 425, 790);
    }

    private void drawWaveTimerInfo(Graphics g) {
        if (playing.getWaveManager().isWaveTimerStarted()) {
            g.setColor(Color.BLACK);
            float timeLeft = playing.getWaveManager().getTimeLeft();
            String formattedText = formatter.format(timeLeft);
            g.drawString(timeCounterStr + formattedText, 425, 750);
        }
    }

    public BufferedImage getSelectedTowerBorder() {
        //Загружает штуку для выделения выбранной башни, выглядит криво если честно
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        return atlas.getSubimage(9 * 32, 2 * 32, 32, 32);
    }

    private void drawDisplayedTower(Graphics g) {
        //Рисует всю инфу о выбранной башне в нижней части экрана
        if (displayedTower != null) {
            g.setColor(Color.gray);
            g.fillRect(410, 645, 220, 85);
            g.setColor(Color.black);
            g.drawRect(410, 645, 220, 85);
            g.drawRect(420, 650, 50, 50);
            g.drawImage(playing.getTowerManager().getTowerImgs()[displayedTower.getTowerType()], 420, 650, 50, 50, null);
            g.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
            g.drawString(Constants.Towers.getName(displayedTower.getTowerType()), 480, 660);
            g.drawString("ID: " + displayedTower.getId(), 480, 675);
            g.drawString(upgradeLevelStr + displayedTower.getTier(), 540, 660);

            drawDisplayedTowerBorder(g);
            drawDisplayedTowerRange(g);

            //Продажа
            bSellTower.draw(g);
            drawButtonFeedback(g, bSellTower);

            //Улучшение
            if (displayedTower.getTier() < 3 && gold >= getUpgradeAmount(displayedTower)) {
                bUpgradeTower.draw(g);
                drawButtonFeedback(g, bUpgradeTower);
            }
            if (bSellTower.isMouseOver()) {
                g.setColor(Color.RED);
                g.drawString(bSellStr + ": " + getSellAmount(displayedTower) + currencyStr, 480, 695);
            } else if (bUpgradeTower.isMouseOver() && gold >= getUpgradeAmount(displayedTower)) {
                g.setColor(Color.BLUE);
                g.drawString(bUpgradeStr + ": " + getUpgradeAmount(displayedTower) + currencyStr, 480, 695);
            }
        }
    }

    private int getUpgradeAmount(Tower displayedTower) {
        return (int) (Constants.Towers.getTowerCost(displayedTower.getTowerType()) * 0.3f);
    }

    private int getSellAmount(Tower displayedTower) {
        int upgradeCost = (displayedTower.getTier() - 1) * getUpgradeAmount(displayedTower);
        upgradeCost *= 0.5f;
        return Constants.Towers.getTowerCost(displayedTower.getTowerType()) / 2 + upgradeCost;
    }

    private void drawDisplayedTowerBorder(Graphics g) {
        //Рисует вон ту кривую картинку выделения
        g.drawImage(selectedTowerBorder, displayedTower.getX(), displayedTower.getY(), null);
    }

    private void drawDisplayedTowerRange(Graphics g) {
        //Рисует окружность радиуса башни, с поддержкой прозрачности!
        g.setColor(Color.WHITE);
        g.drawOval(displayedTower.getX() + 16 - (int) (displayedTower.getRange() * 2) / 2,
                displayedTower.getY() + 16 - (int) (displayedTower.getRange() * 2) / 2,
                (int) displayedTower.getRange() * 2, (int) displayedTower.getRange() * 2);

        g.setColor(rangeColor);
        g.fillOval(displayedTower.getX() + 16 - (int) (displayedTower.getRange() * 2) / 2,
                displayedTower.getY() + 16 - (int) (displayedTower.getRange() * 2) / 2,
                (int) displayedTower.getRange() * 2, (int) displayedTower.getRange() * 2);
    }

    public void displayTower(Tower t) {
        displayedTower = t;
    }

    private boolean isGoldEnoughForTower(int towerType) {
        return gold >= Constants.Towers.getTowerCost(towerType);
    }

    private void sellTowerClicked() {
        playing.removeTower(displayedTower);
        this.gold += getSellAmount(displayedTower);
        displayedTower = null;
    }

    private void upgradeTowerClicked() {
        if (gold >= getUpgradeAmount(displayedTower)) {
            playing.upgradeTower(displayedTower);
            this.gold -= getUpgradeAmount(displayedTower);
        }
    }

    private void togglePause() {
        playing.setGamePaused(!playing.isGamePaused());

        if (playing.isGamePaused())
            bPause.setText(bUnpauseStr);
        else
            bPause.setText(bPauseStr);
    }

    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            SetGameState(MENU);
        } else if (bPause.getBounds().contains(x, y))
            togglePause();
        else {
            if (displayedTower != null) {
                if (bSellTower.getBounds().contains(x, y)) {
                    sellTowerClicked();
                    return;
                } else if (bUpgradeTower.getBounds().contains(x, y) && displayedTower.getTier() < 3) {
                    upgradeTowerClicked();
                    return;
                }
            }
            for (MyButton b : towerButtons) {
                if (b.getBounds().contains(x, y)) {
                    if (!isGoldEnoughForTower(b.getId()))
                        return;
                    selectedTower = new Tower(0, 0, -1, b.getId(), false);
                    playing.setSelectedTower(selectedTower);
                    return;
                }
            }
        }
    }

    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        bPause.setMouseOver(false);
        bSellTower.setMouseOver(false);
        bUpgradeTower.setMouseOver(false);
        showTowerCost = false;
        for (MyButton b : towerButtons) {
            b.setMouseOver(false);
        }
        if (bMenu.getBounds().contains(x, y))
            bMenu.setMouseOver(true);
        else if (bPause.getBounds().contains(x, y))
            bPause.setMouseOver(true);
        else {
            if (displayedTower != null) {
                if (bSellTower.getBounds().contains(x, y)) {
                    bSellTower.setMouseOver(true);
                    return;
                } else if (bUpgradeTower.getBounds().contains(x, y) && displayedTower.getTier() < 3) {
                    bUpgradeTower.setMouseOver(true);
                    return;
                }
            }
            for (MyButton b : towerButtons) {
                if (b.getBounds().contains(x, y)) {
                    b.setMouseOver(true);
                    showTowerCost = true;
                    towerCostType = b.getId();
                    return;
                }
            }
        }
    }


    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y))
            bMenu.setMousePressed(true);
        else if (bPause.getBounds().contains(x, y))
            bPause.setMousePressed(true);
        else {
            if (displayedTower != null) {
                if (bSellTower.getBounds().contains(x, y)) {
                    bSellTower.setMousePressed(true);
                    return;
                } else if (bUpgradeTower.getBounds().contains(x, y) && displayedTower.getTier() < 3) {
                    bUpgradeTower.setMousePressed(true);
                    return;
                }
            }
            for (MyButton b : towerButtons) {
                if (b.getBounds().contains(x, y)) {
                    b.setMousePressed(true);
                    return;
                }
            }
        }
    }


    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();
        bPause.resetBooleans();
        for (MyButton b : towerButtons)
            b.resetBooleans();
        bSellTower.resetBooleans();
        bUpgradeTower.resetBooleans();
    }

    public void setDisplayedTower(Tower displayedTower) {
        this.displayedTower = displayedTower;
    }

    public void payForTower(int towerType) {
        this.gold -= Constants.Towers.getTowerCost(towerType);
    }

    public void addGold(int award) {
        this.gold += award;
    }

    public String getbMenuStr() {
        return bMenuStr;
    }
}
