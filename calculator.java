import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class calculator extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField display;
    private double num1 = 0;
    private String operator = "";
    private StringBuilder currentInput = new StringBuilder();
    private boolean calculating = false;

    public calculator() {
        setTitle("Calculator");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Ekran (Üst kısmı göstermek için)
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setEditable(false);
        display.setBackground(new Color(241, 241, 241)); // Ekran arka planı: #F1F1F1 (Pastel gri)
        display.setForeground(new Color(51, 51, 51)); // Ekran yazı rengi: #333333 (Siyah)
        add(display, BorderLayout.NORTH);

        // Tuşlar için panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 5, 5)); // Tuşlar arasındaki boşluğu daraltalım
        buttonPanel.setBackground(new Color(241, 241, 241)); // Panel arka planı da pastel gri

        // Tuşlar
        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "C", "0", "=", "+"
        };

        for (String text : buttons) {
            JButton button = new RoundButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 16)); // Tuş boyutunu daha küçük yapmak için fontu değiştirdik
            button.setBackground(new Color(168, 230, 207)); // Açık yeşil tonları: #A8E6CF
            button.setForeground(Color.WHITE); // Tuş yazı rengi beyaz
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
    }

    // Yuvarlak buton sınıfı
    static class RoundButton extends JButton {
        private static final long serialVersionUID = 1L;

        public RoundButton(String label) {
            super(label);
            setContentAreaFilled(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (getModel().isArmed()) {
                g.setColor(new Color(129, 199, 132)); // Koyu yeşil hover rengi: #81C784
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
            return new Dimension(50, 50); // Tuş boyutunu daha da küçülttük
        }
    }

    // Tuş işleyici
    private class ButtonClickListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if ("0123456789".contains(command)) {
                // Eğer son işlem yapıldıysa, yeni bir numara girilmeden önce ekranı temizleyelim
                if (calculating) {
                    currentInput.setLength(0); // StringBuilder'ı sıfırlayarak temizle
                    calculating = false;
                }
                currentInput.append(command); // Sayıyı ekle
                display.setText(currentInput.toString());
            } else if ("+-*/".contains(command)) {
                if (!currentInput.toString().isEmpty()) {
                    num1 = Double.parseDouble(currentInput.toString());
                    operator = command;
                    currentInput.append(" " + operator + " "); // İşlem kısmını ekle
                    display.setText(currentInput.toString());
                }
            } else if (command.equals("=")) {
                if (!currentInput.toString().isEmpty()) {
                    String[] parts = currentInput.toString().split(" "); // İşlem ve sayıları ayır
                    double num2 = Double.parseDouble(parts[parts.length - 1]); // İkinci sayıyı al
                    double result = 0;

                    switch (operator) {
                        case "+": result = num1 + num2; break;
                        case "-": result = num1 - num2; break;
                        case "*": result = num1 * num2; break;
                        case "/": result = num2 != 0 ? num1 / num2 : 0; break;
                    }

                    // Sonucun tam sayı olup olmadığını kontrol ediyoruz
                    if (result == (int) result) {
                        currentInput.append(" = " + (int) result); // Tam sayı sonucu göster
                    } else {
                        currentInput.append(" = " + result); // Ondalık sonucu göster
                    }

                    display.setText(currentInput.toString()); // Ekranı güncelle
                    calculating = true;
                }
            } else if (command.equals("C")) {
                currentInput.setLength(0); // Temizleme işlemi
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
