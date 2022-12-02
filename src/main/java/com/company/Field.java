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
    Timer tm, tmUpdate, tmBomb;
    public Field(int diff) {
        try {
            end = ImageIO.read(getClass().getResource("/resources/end.png"));
            fon = ImageIO.read(getClass().getResource("/resources/fon.jpeg"));
            pkg = ImageIO.read(getClass().getResource("/resources/box.png"));
        } catch (Exception e) {
            System.out.println("File not found");
        }
        tm = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                repaint();
            }
        });
        tm.start();
    }
    public void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        gr.drawImage(pkg, x, 470, 100, 100, null);
    }
}