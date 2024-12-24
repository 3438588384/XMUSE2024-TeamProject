package com.smp.view;

import java.awt.*;

public class Start {
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        com.formdev.flatlaf.intellijthemes.FlatGradiantoMidnightBlueIJTheme.setup();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new LoginWindow();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
