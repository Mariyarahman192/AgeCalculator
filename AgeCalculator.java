import javax.swing.*;

public class AgeCalculator{
    public static void main(String[] args) {
        
        JFrame frame = new JFrame("Age Calculator");
        frame.setSize(400, 300);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        
        JLabel birthYearLabel = new JLabel(" Birth year:");
        birthYearLabel.setBounds(50, 30, 80, 25);
        frame.add(birthYearLabel);

        
        JTextField birthYearField = new JTextField();
        birthYearField.setBounds(120, 30, 100, 25);
        frame.add(birthYearField);
     
      
        frame.setVisible(true);
    }
}
