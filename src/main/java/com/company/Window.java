package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Window extends JFrame {
        private Field gameF;
        private class MyKey implements KeyListener {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == 27)
                    System.exit(0);
                else if (keyCode == 37) {
                    if (gameF.x - 30 > -48)
                        gameF.x -= 10;
                    else
                        gameF.x = 752;
                } else if (keyCode == 39) {
                    if (gameF.x + 30 < 752)
                        gameF.x += 10;
                    else
                        gameF.x = -48;
                }
            }
            public void keyReleased(KeyEvent e) {
            }
            public void keyTyped(KeyEvent e) {
            }
        }
        public Window(int diff) {
            MyKey myKey = new MyKey();
            addKeyListener(myKey);
            setFocusable(true);
            setTitle("New year game");
            setBounds(0, 0, 800, 600);
            setResizable(false);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameF = new Field(diff);
            Container cont = getContentPane();
            cont.add(gameF);
            setVisible(true);
        }
}
