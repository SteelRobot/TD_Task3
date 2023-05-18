package org.ui;

import java.awt.*;

public class MyButton {
    //Классные кнопки

    public int x, y, width, height, id;
    private String text;
    private Rectangle bounds;
    private Boolean mouseOver = false;
    private Boolean mousePressed = false;

    public MyButton(String text, int x, int y, int width, int height) {
        //Нормальные кнопки

        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = -1;

        initBounds();
    }

    public MyButton(String text, int x, int y, int width, int height, int id) {
        //Кнопки плиток и башен

        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;

        initBounds();
    }

    private void initBounds() {
        this.bounds = new Rectangle(x, y, width, height);
    }

    public void draw(Graphics g) {

        drawBody(g);

        drawBorder(g);

        drawText(g);
    }

    private void drawBorder(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
        if (mousePressed) {
            g.drawRect(x + 1, y + 1, width - 2, height - 2);
            g.drawRect(x + 2, y + 2, width - 4, height - 4);
        }
    }

    private void drawBody(Graphics g) {
        if (mouseOver) {
            g.setColor(Color.GRAY);
        } else {
            g.setColor(Color.WHITE);
        }
        g.fillRect(x, y, width, height);
    }

    private void drawText(Graphics g) {
        int w = g.getFontMetrics().stringWidth(text);
        int h = g.getFontMetrics().getHeight();
        g.drawString(text, x - w / 2 + width / 2, y + h / 2 + height / 2);
    }

    public void resetBooleans() {
        this.mouseOver = false;
        this.mousePressed = false;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }
    public boolean isMouseOver () {
        return mouseOver;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }
    public boolean isMousePressed() {
        return mousePressed;
    }
    public Rectangle getBounds() {
        return bounds;
    }

    public int getId() {
        return id;
    }

}
