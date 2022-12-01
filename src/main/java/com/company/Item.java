package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Item {
    public Image img;
    public int x, y;
    public boolean isActive;
    Timer tmUpdate;
    public Item(Image img) {
        tmUpdate = new Timer(150, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                down();
            }
        });
        this.img = img;
        isActive = false;
    }
    public void down() {
        if (isActive) {
            y += 5;
        }
        if (y + img.getHeight(null) >= 470) {
            tmUpdate.stop();
        }
    }
    public void draw(Graphics gr) {
        if (isActive)
            gr.drawImage(img, x, y, null);
    }
    public void start() {
        tmUpdate.start();
        y = 0;
        x = (int) (Math.random() * 700);
        isActive = true;
    }
}
