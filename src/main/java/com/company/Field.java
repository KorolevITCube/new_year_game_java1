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
        try {
// Звуковой файл
            Clip clipSound;
//Воспроизводимый файл
            File soundFile = new File((getClass().getResource("/resources/music.wav")).toURI());
//получение информации о файле
            AudioFileFormat aff = AudioSystem.getAudioFileFormat(soundFile);
            AudioFormat af = aff.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, af);
            System.out.println(info.toString());
//проверка на возможность воспроизвести данный файл
            if (AudioSystem.isLineSupported(info)) {
//создание потока
                clipSound = (Clip) AudioSystem.getLine(info);
                AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
//открытие потока
                clipSound.open(ais);
//установка на циклическое воспроизведение
                clipSound.loop(Clip.LOOP_CONTINUOUSLY);
//воспроизведение файла
                clipSound.start();
            }
        } catch (Exception exc) {
            System.out.println("fail to find and read");
            System.out.println(exc.getMessage());
        }
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
        if (gameOver) {
            gr.drawImage(end, 300, 100, 200, 200, this);
            Font fntScore = new Font("Verdana", 0, 60);
            gr.setFont(fntScore);
            gr.setColor(Color.red);
            gr.drawString("Ваш счёт: " + score, 100, 300);
        } else {
            gr.drawImage(fon, 0, 0,800,600, null);
            gr.drawImage(pkg, x, 470, 100, 100, null);
            for (int i = 1; i <= life; i++) {
                gr.drawImage(hp, 780 - (35 * i), 25, 30,
                        30, null);
            }
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
    }
    boolean drawBomb(Graphics gr) {
        bomb.draw(gr);
        if (bomb.isActive) {
            if ((bomb.y + bomb.img.getHeight(null)) >= 470)
            {
                if (Math.abs(bomb.x - x) > 100) {
                    bomb.isActive = false;
                } else {
                    tm.stop();
                    tmUpdate.stop();
                    tmBomb.stop();
                    return true;
                }
            }
        }
        return false;
    }
    boolean drawItem(Graphics gr) {
        for (int i = 0; i < 7; i++) {
            item[i].draw(gr);
            if (item[i].isActive) {
                if (item[i].y + item[i].img.getHeight(null)
                        >= 470) {
                    if (Math.abs(item[i].x - x) > 100) {
                        if (life == 1) {
                            tm.stop();
                            tmUpdate.stop();
                            tmBomb.stop();
                            return true;
                        } else {
                            life--;
                            item[i].isActive = false;
                        }
                    } else {
                        item[i].isActive = false;
                        score += 100;
                        lifeScore += 1000;
                    }
                }
            }
        }
        return false;
    }
}