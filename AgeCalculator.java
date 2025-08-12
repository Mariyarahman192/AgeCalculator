
import javax.swing.*;

public class AgeCalculator{
    public static void main(String[] args) {

        JFrame frame = new JFrame("Age Calculator");
        frame.setSize(400, 300);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);


        JLabel birthYearLabel = new JLabel(" birth year:");
        birthYearLabel.setBounds(50, 30, 80, 25);
        frame.add(birthYearLabel);


        JTextField birthYearField = new JTextField();
        birthYearField.setBounds(120, 30, 100, 25);
        frame.add(birthYearField);

        JLabel monthLabel = new JLabel("Month:");
        monthLabel.setBounds(50, 70, 50, 25);
        frame.add(monthLabel);

        JTextField monthField = new JTextField();
        monthField.setBounds(100, 70, 80, 25);
        frame.add(monthField);


        JLabel dayLabel = new JLabel("Day:");
        dayLabel.setBounds(50, 110, 50, 25);
        frame.add(dayLabel);

        JTextField dayField = new JTextField();
        dayField.setBounds(100, 110, 80, 25);
        frame.add(dayField);


        JCheckBox confirmCheck = new JCheckBox("I confirm my birth date is correct");
        confirmCheck.setBounds(50, 150, 300, 25);
        frame.add(confirmCheck);


        JButton calcButton = new JButton("Calculate Age");
        calcButton.setBounds(140, 190, 150, 30);
        frame.add(calcButton);


        frame.setVisible(true);
    }
}
