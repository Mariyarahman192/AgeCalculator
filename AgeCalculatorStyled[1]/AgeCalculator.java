import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
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
        frame.getContentPane().setBackground(new Color(230, 240, 255));  

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Color labelColor = new Color(25, 50, 100);

        JLabel dayLabel = new JLabel("Day:");
        dayLabel.setBounds(50, 30, 50, 25);
        dayLabel.setForeground(labelColor);
        dayLabel.setFont(labelFont);
        frame.add(dayLabel);

        JTextField dayField = createDateField(100, 30);
        frame.add(dayField);

        JLabel monthLabel = new JLabel("Month:");
        monthLabel.setBounds(160, 30, 50, 25);
        monthLabel.setForeground(labelColor);
        monthLabel.setFont(labelFont);
        frame.add(monthLabel);

        JTextField monthField = createDateField(220, 30);
        frame.add(monthField);

        JLabel yearLabel = new JLabel("Year:");
        yearLabel.setBounds(280, 30, 50, 25);
        yearLabel.setForeground(labelColor);
        yearLabel.setFont(labelFont);
        frame.add(yearLabel);

        JTextField yearField = createDateField(340, 30);
        frame.add(yearField);

        JButton calendarButton = new JButton("📅");
        calendarButton.setBounds(410, 30, 50, 25);
        calendarButton.setBackground(new Color(100, 150, 255));
        calendarButton.setForeground(Color.WHITE);
        calendarButton.setFocusPainted(false);
        frame.add(calendarButton);

        JCheckBox confirmCheck = new JCheckBox("I confirm my birth date is correct");
        confirmCheck.setBounds(50, 70, 300, 25);
        confirmCheck.setBackground(new Color(230, 240, 255));
        confirmCheck.setForeground(labelColor);
        confirmCheck.setFont(labelFont);
        frame.add(confirmCheck);

        JButton calcButton = new JButton("Calculate Age");
        calcButton.setBounds(140, 110, 150, 30);
        calcButton.setBackground(new Color(100, 150, 255));
        calcButton.setForeground(Color.WHITE);
        calcButton.setFocusPainted(false);
        frame.add(calcButton);

        JLabel resultLabel = new JLabel("Your age will appear here.");
        resultLabel.setBounds(50, 160, 400, 25);
        resultLabel.setForeground(new Color(20, 20, 70));
        resultLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        frame.add(resultLabel);

        JLabel birthdayLabel = new JLabel("");
        birthdayLabel.setBounds(50, 200, 350, 25);
        birthdayLabel.setForeground(new Color(200, 50, 50));
        birthdayLabel.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(birthdayLabel);

        calendarButton.addActionListener(e -> {
            Calendar today = Calendar.getInstance();
            showCalendarPicker(frame, dayField, monthField, yearField, today.get(Calendar.MONTH), today.get(Calendar.YEAR));
        });

        calcButton.addActionListener(e -> {
            if (!confirmCheck.isSelected()) {
                resultLabel.setText("Please confirm your birth date.");
                birthdayLabel.setText("");
                return;
            }
            try {
                int d = Integer.parseInt(dayField.getText());
                int m = Integer.parseInt(monthField.getText());
                int y = Integer.parseInt(yearField.getText());

                Calendar birth = Calendar.getInstance();
                birth.set(y, m - 1, d);
                Calendar today = Calendar.getInstance();

                if (birth.after(today)) {
                    resultLabel.setText("Birth date cannot be in the future.");
                    birthdayLabel.setText("");
                    return;
                }

                int years = today.get(Calendar.YEAR) - y;
                int months = today.get(Calendar.MONTH) + 1 - m;
                int days = today.get(Calendar.DAY_OF_MONTH) - d;

                if (days < 0) {
                    months--;
                    Calendar prevMonth = (Calendar) today.clone();
                    prevMonth.add(Calendar.MONTH, -1);
                    days += prevMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
                }
                if (months < 0) {
                    months += 12;
                    years--;
                }

                resultLabel.setText("You are " + years + " years, " + months + " months, " + days + " days old.");
                if (d == today.get(Calendar.DAY_OF_MONTH) && m == (today.get(Calendar.MONTH) + 1)) {
                    birthdayLabel.setText("🎉 Happy Birthday! 🎉");
                } else {
                    birthdayLabel.setText("");
                }
            } catch (Exception ex) {
                resultLabel.setText("Please select a valid date.");
                birthdayLabel.setText("");
            }
        });

        frame.setVisible(true);
    }

    private static JTextField createDateField(int x, int y) {
        JTextField field = new JTextField();
        field.setBounds(x, y, 50, 25);
        field.setEditable(false);
        field.setBackground(new Color(220, 230, 250));
        field.setForeground(new Color(25, 50, 100));
        return field;
    }

    private static void showCalendarPicker(JFrame parent, JTextField dayField, JTextField monthField, JTextField yearField, int initMonth, int initYear) {
        JDialog dialog = new JDialog(parent, "Select Date", true);
        dialog.setSize(350, 320);
        dialog.setLayout(new BorderLayout());

        JPanel top = new JPanel();
        top.setBackground(new Color(210, 230, 255));

        String[] months = {"January", "February", "March", "April", "May", "June",
                           "July", "August", "September", "October", "November", "December"};
        JComboBox<String> monthCombo = new JComboBox<>(months);
        monthCombo.setSelectedIndex(initMonth);

        SpinnerNumberModel yearModel = new SpinnerNumberModel(initYear, 1900, Calendar.getInstance().get(Calendar.YEAR), 1);
        JSpinner yearSpinner = new JSpinner(yearModel);

        top.add(monthCombo);
        top.add(yearSpinner);
        dialog.add(top, BorderLayout.NORTH);

        String[] columns = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        Object[][] data = new Object[6][7];

        JTable calendarTable = new JTable(data, columns) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        calendarTable.setRowHeight(30);
        calendarTable.setCellSelectionEnabled(true);
        calendarTable.getTableHeader().setBackground(new Color(100, 150, 255));
        calendarTable.getTableHeader().setForeground(Color.WHITE);
        calendarTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setForeground(new Color(25, 50, 100));
        centerRenderer.setFont(new Font("Arial", Font.PLAIN, 14));
        calendarTable.setDefaultRenderer(Object.class, centerRenderer);

        dialog.add(new JScrollPane(calendarTable), BorderLayout.CENTER);

        Runnable updateCalendar = () -> {
            Calendar cal = Calendar.getInstance();
            int year = (int) yearSpinner.getValue();
            int month = monthCombo.getSelectedIndex();

            cal.set(year, month, 1);
            int startDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
            int maxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

            for (int i = 0; i < 6; i++)
                for (int j = 0; j < 7; j++)
                    data[i][j] = "";

            int dayCount = 1;
            Calendar today = Calendar.getInstance();

            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    if (i == 0 && j < startDay) continue;
                    if (dayCount > maxDays) break;

                    boolean future = (year > today.get(Calendar.YEAR)) ||
                            (year == today.get(Calendar.YEAR) && month > today.get(Calendar.MONTH)) ||
                            (year == today.get(Calendar.YEAR) && month == today.get(Calendar.MONTH) && dayCount > today.get(Calendar.DAY_OF_MONTH));

                    data[i][j] = future ? "" : dayCount++;
                }
            }
            calendarTable.repaint();
        };

        updateCalendar.run();

        monthCombo.addActionListener(e -> updateCalendar.run());
        yearSpinner.addChangeListener(e -> updateCalendar.run());

        calendarTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = calendarTable.getSelectedRow();
                int col = calendarTable.getSelectedColumn();
                Object val = calendarTable.getValueAt(row, col);
                if (val != null && !val.toString().isEmpty()) {
                    int day = Integer.parseInt(val.toString());
                    int month = monthCombo.getSelectedIndex();
                    int year = (int) yearSpinner.getValue();

                    dayField.setText(String.valueOf(day));
                    monthField.setText(String.valueOf(month + 1));
                    yearField.setText(String.valueOf(year));

                    dialog.dispose();
                }
            }
        });

        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }
}