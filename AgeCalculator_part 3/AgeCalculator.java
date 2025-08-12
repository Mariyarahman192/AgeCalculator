import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;

public class AgeCalculator {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Age Calculator");
        frame.setSize(500, 400);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        
        JLabel dayLabel = new JLabel("Day:");
        dayLabel.setBounds(50, 30, 50, 25);
        frame.add(dayLabel);

        JTextField dayField = new JTextField();
        dayField.setBounds(100, 30, 50, 25);
        frame.add(dayField);

      
        JLabel monthLabel = new JLabel("Month:");
        monthLabel.setBounds(160, 30, 50, 25);
        frame.add(monthLabel);

        JTextField monthField = new JTextField();
        monthField.setBounds(220, 30, 50, 25);
        frame.add(monthField);

    
        JLabel yearLabel = new JLabel("Year:");
        yearLabel.setBounds(280, 30, 50, 25);
        frame.add(yearLabel);

        JTextField yearField = new JTextField();
        yearField.setBounds(340, 30, 60, 25);
        frame.add(yearField);

       
        JButton calendarButton = new JButton("📅");
        calendarButton.setBounds(410, 30, 50, 25);
        frame.add(calendarButton);

   
        JCheckBox confirmCheck = new JCheckBox("I confirm my birth date is correct");
        confirmCheck.setBounds(50, 70, 300, 25);
        frame.add(confirmCheck);

   
        JButton calcButton = new JButton("Calculate Age");
        calcButton.setBounds(140, 110, 150, 30);
        frame.add(calcButton);

    
        JLabel resultLabel = new JLabel("Your age will appear here.");
        resultLabel.setBounds(50, 160, 350, 25);
        frame.add(resultLabel);

   
        JLabel birthdayLabel = new JLabel("");
        birthdayLabel.setBounds(50, 200, 350, 25);
        frame.add(birthdayLabel);

      
        calendarButton.addActionListener(e -> {
            Calendar today = Calendar.getInstance();
            int month = today.get(Calendar.MONTH);
            int year = today.get(Calendar.YEAR);
            showCalendar(frame, dayField, monthField, yearField, month, year);
        });

       
        calcButton.addActionListener(e -> {
            if (!confirmCheck.isSelected()) {
                resultLabel.setText("Please confirm your birth date.");
                birthdayLabel.setText("");
                return;
            }

            try {
                int birthDay = Integer.parseInt(dayField.getText());
                int birthMonth = Integer.parseInt(monthField.getText());
                int birthYear = Integer.parseInt(yearField.getText());

                Calendar cal = Calendar.getInstance();
                int todayDay = cal.get(Calendar.DAY_OF_MONTH);
                int todayMonth = cal.get(Calendar.MONTH) + 1;
                int todayYear = cal.get(Calendar.YEAR);

                int ageYears = todayYear - birthYear;
                int ageMonths = todayMonth - birthMonth;
                int ageDays = todayDay - birthDay;

                if (ageDays < 0) {
                    ageDays += cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                    ageMonths--;
                }
                if (ageMonths < 0) {
                    ageMonths += 12;
                    ageYears--;
                }

                resultLabel.setText("You are " + ageYears + " years, " + ageMonths + " months, " + ageDays + " days old.");

                if (birthDay == todayDay && birthMonth == todayMonth) {
                    birthdayLabel.setText("🎉 Happy Birthday! 🎉");
                } else {
                    birthdayLabel.setText("");
                }

            } catch (NumberFormatException ex) {
                resultLabel.setText("Please enter valid numbers.");
                birthdayLabel.setText("");
            }
        });

        frame.setVisible(true);
    }

    
    private static void showCalendar(JFrame parent, JTextField dayField, JTextField monthField, JTextField yearField, int month, int year) {
        JDialog dialog = new JDialog(parent, "Select Date", true);
        dialog.setSize(300, 250);
        dialog.setLayout(new BorderLayout());

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);

        String[] columns = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        int startDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        Object[][] data = new Object[6][7];
        int dayCounter = 1;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (i == 0 && j < startDay) {
                    data[i][j] = "";
                } else if (dayCounter > daysInMonth) {
                    data[i][j] = "";
                } else {
                    data[i][j] = dayCounter++;
                }
            }
        }

        JTable table = new JTable(data, columns);
        table.setCellSelectionEnabled(true);
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                int col = table.getSelectedColumn();
                Object value = table.getValueAt(row, col);
                if (value != null && !value.toString().isEmpty()) {
                    dayField.setText(value.toString());
                    monthField.setText(String.valueOf(month + 1));
                    yearField.setText(String.valueOf(year));
                    dialog.dispose();
                }
            }
        });

        dialog.add(new JScrollPane(table), BorderLayout.CENTER);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }
}