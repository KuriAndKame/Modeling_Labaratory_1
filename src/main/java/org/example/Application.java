package org.example;

import javax.swing.*;

public class Application {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            SimulationUI simulationUI = new SimulationUI();

            simulationUI.start();

        });
    }
}
