package com.company;

import javax.swing.*;

public class Game {

    public static void main(String[] args) {
        int difficult = 3;
        String request = JOptionPane.showInputDialog(null,
                "Введите сложность от 1 до 7", "Выбор сложности", 3);
        try {
            difficult = Integer.parseInt(request);
        } catch (Exception e) {
            difficult = 3;
            JOptionPane.showMessageDialog(null, "Вы ввели не число! \nБыла установлена сложность по умолчанию = " + difficult, "Ошибка", 0);
        }
        if (difficult > 8 || difficult < 1) {
            difficult = 3;
            JOptionPane.showMessageDialog(null,
                    "Вы ввели неверную сложность! \nБыла установлена сложность по умолчанию = " + difficult, "Ошибка",
                    0);
        }
        new Window(difficult);
    }
}
