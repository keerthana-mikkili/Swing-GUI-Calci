package edu.neu.csye6200;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorSwing extends JFrame {

    private JTextField display;
    private double operand1, operand2, result;
    private char operator;

    public CalculatorSwing() {
        setTitle("Group 9 Calculator");
        setSize(350, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
    }

    private void initComponents() {
        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.PLAIN, 24));

        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 5, 5)); // 0 rows to allow any number of rows, 1 column

        // Add buttons for digits 0-9
        for (int i = 0; i <= 9; i++) {
            addButton(buttonPanel, String.valueOf(i), Color.WHITE);
        }

        JPanel operationPanel = new JPanel(new GridLayout(0, 1, 5, 5)); // 0 rows to allow any number of rows, 1 column

        // Add buttons for operations with  colors
        addButton(operationPanel, "+", Color.ORANGE);
        addButton(operationPanel, "-", Color.ORANGE);
        addButton(operationPanel, "*", Color.ORANGE);
        addButton(operationPanel, "/", Color.ORANGE);

        // Set layout
        setLayout(new BorderLayout());
        add(display, BorderLayout.NORTH);

        JPanel combinedPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        combinedPanel.add(buttonPanel);
        combinedPanel.add(operationPanel);

        add(combinedPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new GridLayout(3, 1, 5, 5));

        // Add equals button
        addButton(controlPanel, "=", Color.YELLOW);

        // Add backspace button
        addButton(controlPanel, "\u2190", Color.GRAY);

        // Add clear button
        addButton(controlPanel, "C", Color.RED);

        add(controlPanel, BorderLayout.SOUTH);
    }

    private void addButton(Container container, String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setBackground(color);
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonClick(text);
            }
        });
        container.add(button);
    }

    private void handleButtonClick(String buttonText) {
        if (Character.isDigit(buttonText.charAt(0))) {
            display.setText(display.getText() + buttonText);
        } else if (buttonText.equals("+") || buttonText.equals("-") || buttonText.equals("*") || buttonText.equals("/")) {
            operator = buttonText.charAt(0);
            operand1 = Double.parseDouble(display.getText());
            display.setText("");
        } else if (buttonText.equals("=")) {
            calculate();
        } else if (buttonText.equals("C")) {
            clear();
        } else if (buttonText.equals("\u2190")) {
            deleteCharacter();
        }
    }

    private void calculate() {
        if (!display.getText().isEmpty()) {
            operand2 = Double.parseDouble(display.getText());

            try {
                switch (operator) {
                    case '+':
                        result = operand1 + operand2;
                        break;
                    case '-':
                        result = operand1 - operand2;
                        break;
                    case '*':
                        result = operand1 * operand2;
                        break;
                    case '/':
                        if (operand2 != 0) {
                            result = operand1 / operand2;
                        } else {
                            JOptionPane.showMessageDialog(this, "Cannot divide by zero", "Error", JOptionPane.ERROR_MESSAGE);
                            clear();
                            return;
                        }
                        break;
                }

                display.setText(String.valueOf(result));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
                clear();
            }
        }
    }

    private void clear() {
        display.setText("");
        operand1 = 0;
        operand2 = 0;
        result = 0;
        operator = '\0';
    }

    private void deleteCharacter() {
        String currentText = display.getText();
        if (!currentText.isEmpty()) {
            display.setText(currentText.substring(0, currentText.length() - 1));
        }
    }

    public static void demo() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CalculatorSwing().setVisible(true);
            }
        });
    }
}
