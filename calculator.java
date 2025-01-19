import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class calculator extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField display; // Text field to display the current input and result
    private double num1 = 0; // First operand for calculations
    private String operator = ""; // Stores the operator (+, -, *, /)
    private StringBuilder currentInput = new StringBuilder(); // Holds the current input from the user
    private boolean calculating = false; // Flag to check if the calculation is ongoing

    // Set up the frame for the calculator
    public calculator() {
        setTitle("Calculator");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Display panel to show the current input and result
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setEditable(false);
        display.setBackground(new Color(241, 241, 241)); 
        display.setForeground(new Color(51, 51, 51)); 
        add(display, BorderLayout.NORTH);
      
        // Panel to hold the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 5, 5)); 
        buttonPanel.setBackground(new Color(241, 241, 241)); 

        // Define  the calculator buttons
        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "C", "0", "=", "+"
        };

        // Add the buttons to the panel
        for (String text : buttons) {
            JButton button = new RoundButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 16)); 
            button.setBackground(new Color(168, 230, 207)); 
            button.setForeground(Color.WHITE); 
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
    }


    // Custom button class to create buttons
    static class RoundButton extends JButton {
        private static final long serialVersionUID = 1L;

        public RoundButton(String label) {
            super(label);
            setContentAreaFilled(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (getModel().isArmed()) {
                g.setColor(new Color(129, 199, 132)); 
            } else {
                g.setColor(getBackground());
            }
            g.fillOval(0, 0, getWidth(), getHeight());
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            g.setColor(getForeground());
            g.drawOval(0, 0, getWidth() - 1, getHeight() - 1);
        }
        
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(50, 50); 
        }
    }

    
    private class ButtonClickListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if ("0123456789".contains(command)) {
                
                if (calculating) {
                    currentInput.setLength(0); 
                    calculating = false;
                }
                currentInput.append(command); // Sayıyı ekle
                display.setText(currentInput.toString());
            } else if ("+-*/".contains(command)) {
                if (!currentInput.toString().isEmpty()) {
                    num1 = Double.parseDouble(currentInput.toString());
                    operator = command;
                    currentInput.append(" " + operator + " "); 
                    display.setText(currentInput.toString());
                }
            } else if (command.equals("=")) {
                if (!currentInput.toString().isEmpty()) {
                    String[] parts = currentInput.toString().split(" "); 
                    double num2 = Double.parseDouble(parts[parts.length - 1]); 
                    double result = 0;

                     // Perform the calculation based on the operator
                    switch (operator) {
                        case "+": result = num1 + num2; break;
                        case "-": result = num1 - num2; break;
                        case "*": result = num1 * num2; break;
                        case "/": result = num2 != 0 ? num1 / num2 : 0; break;
                    }

                    
                    if (result == (int) result) {
                        currentInput.append(" = " + (int) result); 
                    } else {
                        currentInput.append(" = " + result); 
                    }

                    display.setText(currentInput.toString()); 
                    calculating = true;
                }
            } else if (command.equals("C")) {   // Clear button click
                currentInput.setLength(0); 
                operator = "";
                num1 = 0;
                calculating = false;
                display.setText("");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            calculator calculator = new calculator();
            calculator.setVisible(true);
        });
    }
}
