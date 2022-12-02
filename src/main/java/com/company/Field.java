package com.company;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Field extends JPanel {
    Image fon, pkg, hp, end;
    int x = 400, y;
    int difficult;
    int score = 0;
    int life = 3;
    int randomBombTime;
    int lifeScore = 0;
    Item[] item;
    Timer tm, tmUpdate, tmBomb;
    Item bomb;
    boolean gameOver = false;
    public Field(int diff) {
        item = new Item[7];
        for (int i = 0; i < 7; i++) {
            try {
                item[i] = new Item(ImageIO.read(getClass().getResource("/resources/item" + i + ".png")));
            } catch (Exception e) {
                System.out.println("Не загружен подарок с названием item" + i);
            }
        }
        try {
            end = ImageIO.read(getClass().getResource("/resources/end.png"));
            fon = ImageIO.read(getClass().getResource("/resources/fon.jpeg"));
            pkg = ImageIO.read(getClass().getResource("/resources/box.png"));
            hp = ImageIO.read(getClass().getResource("/resources/heart.png"));
            bomb = new Item(ImageIO.read(getClass().getResource("/resources/bomb.png")));
        } catch (Exception e) {
        }
        tm = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                repaint();
            }
        });
        tm.start();
        tmUpdate = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                updateStart();
            }
        });
        tmUpdate.start();
        randomBombTime = (int) ((Math.random() * 10) + 1) *
                1000;
        tmBomb = new Timer(randomBombTime, new
                ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        if (bomb.isActive == false) {
                            bomb.start();
                        }
                    }
                });
        tmBomb.start();
        this.difficult = diff;
    }
    private void updateStart() {
        int count = 0;
        for (int i = 0; i < 7; i++) {
            if (item[i].isActive == false) {
                if (count < difficult) {
                    item[i].start();
                    break;
                }
            } else
                count++;
        }
    }
    public void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        gr.drawImage(fon, 0, 0,800,600, null);
        gr.drawImage(pkg, x, 470, 100, 100, null);
        Font fntScore = new Font("Verdana", 0, 30);
        gr.setFont(fntScore);
        gr.setColor(Color.red);
        gr.drawString("" + score, 10, 25);
        if (lifeScore == 1000) {
            life++;
            lifeScore = 0;
        }
        if (drawBomb(gr) || drawItem(gr)) {
            gameOver = true;
            repaint();
        }
    }
    boolean drawBomb(Graphics gr) {
        bomb.draw(gr);
        return false;
    }
    boolean drawItem(Graphics gr) {
        for (int i = 0; i < 7; i++) {
            item[i].draw(gr);
        }
        return false;
    }
}