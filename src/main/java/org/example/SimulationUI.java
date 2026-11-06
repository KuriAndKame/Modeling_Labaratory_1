
package org.example;

import javax.swing.*;
import java.awt.*;

public class SimulationUI {

    private JFrame frame;

    private JTextField fieldMass;
    private JTextField fieldCharge;
    private JTextField fieldMagnit;
    private JTextField fieldElectricField;
    private JTextField fieldStartVelocity;

    private JButton startButton;
    private JButton resetButton;

    private GraphPanel graphPanel;

    private Simulation simulation;

    private Timer timer;

    private static final double DT = 0.016;

    public void start() {
        createAndShowGUI();
    }

    private void createAndShowGUI() {

        frame = new JFrame("Лабораторная работа № 1");

        frame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        frame.setLayout(new BorderLayout(10, 10));

        // Панель ввода параметров
        JPanel panelInput = new JPanel(
                new GridLayout(5, 2, 8, 8)
        );

        panelInput.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );

        fieldMass = createField("1.0");
        fieldCharge = createField("1.0");
        fieldMagnit = createField("2.0");
        fieldElectricField = createField("50000.0");
        fieldStartVelocity = createField("70000.0");

        addInput(panelInput, "Масса:", fieldMass);
        addInput(panelInput, "Заряд:", fieldCharge);
        addInput(panelInput, "Магнитное поле B:", fieldMagnit);
        addInput(panelInput, "Электрическое поле E:", fieldElectricField);
        addInput(panelInput, "Начальная скорость:", fieldStartVelocity);


        graphPanel = new GraphPanel();

        // Кнопки управления
        JPanel controlPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 15, 10)
        );

        startButton = new JButton("Запустить симуляцию");
        resetButton = new JButton("Сбросить");

        startButton.setFont(
                new Font("Times New Roman", Font.PLAIN, 16)
        );

        resetButton.setFont(
                new Font("Times New Roman", Font.PLAIN, 16)
        );

        controlPanel.add(startButton);
        controlPanel.add(resetButton);


        timer = new Timer(16, e -> updateSimulation());

        timer.setCoalesce(true);

        startButton.addActionListener(e -> {

            if (timer.isRunning()) {

                timer.stop();

                startButton.setText(
                        "Продолжить симуляцию"
                );

            } else {

                try {

                    if (simulation == null) {
                        simulation = createSimulation();
                    }

                    timer.start();

                    startButton.setText("Остановить");

                } catch (IllegalArgumentException ex) {

                    JOptionPane.showMessageDialog(
                            frame,
                            "Ошибка ввода: " + ex.getMessage(),
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        // Обработчик сброса
        resetButton.addActionListener(e -> {

            timer.stop();

            simulation = null;

            graphPanel.clearTrajectory();

            startButton.setText(
                    "Запустить симуляцию"
            );
        });

        frame.add(panelInput, BorderLayout.NORTH);
        frame.add(graphPanel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);

        frame.setSize(1000, 750);

        frame.setMinimumSize(new Dimension(700, 500));

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    private JTextField createField(String value) {

        JTextField field = new JTextField(15);

        field.setText(value);

        field.setFont(
                new Font("Times New Roman", Font.PLAIN, 14)
        );

        return field;
    }

    private void addInput(JPanel panel, String text, JTextField field) {

        JLabel label = new JLabel(text);

        label.setFont(
                new Font("Times New Roman", Font.PLAIN, 14)
        );

        panel.add(label);
        panel.add(field);
    }

    private Simulation createSimulation() {

        double mass = Double.parseDouble(
                fieldMass.getText().trim()
        );

        double charge = Double.parseDouble(
                fieldCharge.getText().trim()
        );

        double magneticField = Double.parseDouble(
                fieldMagnit.getText().trim()
        );

        double electricField = Double.parseDouble(
                fieldElectricField.getText().trim()
        );

        double velocity = Double.parseDouble(
                fieldStartVelocity.getText().trim()
        );

        SimulationParametrs params = new SimulationParametrs(
                        mass,
                        charge,
                        electricField,
                        magneticField,
                        velocity
                );

        return new Simulation(params);
    }

    private void updateSimulation() {

        try {

            simulation.step(DT);

            Particle particle = simulation.getParticle();

            graphPanel.setParticle(particle);

        } catch (RuntimeException ex) {

            timer.stop();

            startButton.setText(
                    "Продолжить симуляцию"
            );

            JOptionPane.showMessageDialog(
                    frame,
                    "Ошибка вычисления: " + ex.getMessage(),
                    "Ошибка симуляции",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
