import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

class GradeAnalyzerPro extends JFrame {
    private ArrayList<Double> grades = new ArrayList<>();
    private DefaultTableModel tableModel;
    private JLabel statsLabel;
    private DecimalFormat df = new DecimalFormat("#.##");

    public GradeAnalyzerPro() {
        setTitle("Grade Analyzer Pro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        add(mainPanel);

        JPanel inputPanel = createInputPanel();
        mainPanel.add(inputPanel, BorderLayout.NORTH);

        JPanel tablePanel = createTablePanel();
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        JPanel statsPanel = createStatsPanel();
        mainPanel.add(statsPanel, BorderLayout.SOUTH);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setVisible(true);
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Grade Entry"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel gradeLabel = new JLabel("Enter Grade (0-100):");
        JTextField gradeField = new JTextField(10);
        JButton addButton = new JButton("Add Grade");
        JButton clearButton = new JButton("Clear All");
        JButton generateButton = new JButton("Generate Random (10)");

        addButton.setBackground(new Color(70, 130, 180));
        addButton.setForeground(Color.WHITE);
        clearButton.setBackground(new Color(220, 20, 60));
        clearButton.setForeground(Color.WHITE);
        generateButton.setBackground(new Color(34, 139, 34));
        generateButton.setForeground(Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(gradeLabel, gbc);

        gbc.gridx = 1;
        panel.add(gradeField, gbc);

        gbc.gridx = 2;
        panel.add(addButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        panel.add(clearButton, gbc);

        gbc.gridy = 2;
        panel.add(generateButton, gbc);

        addButton.addActionListener(e -> {
            try {
                double grade = Double.parseDouble(gradeField.getText());
                if (grade >= 0 && grade <= 100) {
                    grades.add(grade);
                    updateTable();
                    updateStatistics();
                    gradeField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Please enter a grade between 0 and 100",
                            "Invalid Input", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number",
                        "Invalid Input", JOptionPane.WARNING_MESSAGE);
            }
            gradeField.requestFocus();
        });

        clearButton.addActionListener(e -> {
            grades.clear();
            updateTable();
            updateStatistics();
        });

        generateButton.addActionListener(e -> {
            grades.clear();
            for (int i = 0; i < 10; i++) {
                grades.add(Math.round(Math.random() * 500) / 5.0); // Generates grades between 0-100 in 0.2 increments
            }
            updateTable();
            updateStatistics();
        });

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Grade List"));

        String[] columnNames = {"#", "Grade", "Letter Grade"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(1).setMaxWidth(100);

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Statistics"));

        statsLabel = new JLabel("No grades entered yet.");
        statsLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        statsLabel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel chartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (grades.isEmpty()) return;

                int width = getWidth();
                int height = getHeight();
                int padding = 20;
                int chartWidth = width - 2 * padding;
                int chartHeight = height - 2 * padding;

                g.setColor(Color.BLACK);
                g.drawLine(padding, height - padding, width - padding, height - padding); // X-axis
                g.drawLine(padding, height - padding, padding, padding); // Y-axis

                // Draw labels
                g.drawString("0", padding - 5, height - padding + 15);
                g.drawString("100", width - padding - 15, height - padding + 15);
                g.drawString("Grades", width / 2 - 20, height - padding + 15);
                g.drawString("Distribution", padding - 10, height / 2);

                int[] ranges = new int[5];
                for (double grade : grades) {
                    int index = (int) (grade / 20);
                    if (index > 4) index = 4;
                    ranges[index]++;
                }

                int maxCount = 0;
                for (int count : ranges) {
                    if (count > maxCount) maxCount = count;
                }
                if (maxCount == 0) return;

                int barWidth = chartWidth / ranges.length;
                Color[] colors = {new Color(31, 119, 180), new Color(255, 127, 14),
                        new Color(44, 160, 44), new Color(214, 39, 40),
                        new Color(148, 103, 189)};

                for (int i = 0; i < ranges.length; i++) {
                    int barHeight = (int) ((double) ranges[i] / maxCount * chartHeight);
                    g.setColor(colors[i % colors.length]);
                    g.fillRect(padding + i * barWidth, height - padding - barHeight,
                            barWidth - 5, barHeight);

                    g.setColor(Color.BLACK);
                    g.drawString(String.valueOf(ranges[i]),
                            padding + i * barWidth + barWidth/2 - 5,
                            height - padding - barHeight - 5);

                    g.drawString((i*20) + "-" + ((i+1)*20),
                            padding + i * barWidth + barWidth/2 - 15,
                            height - padding + 15);
                }
            }
        };
        chartPanel.setPreferredSize(new Dimension(0, 150));

        panel.add(statsLabel, BorderLayout.CENTER);
        panel.add(chartPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        for (int i = 0; i < grades.size(); i++) {
            double grade = grades.get(i);
            String letterGrade = getLetterGrade(grade);
            tableModel.addRow(new Object[]{i + 1, df.format(grade), letterGrade});
        }
    }

    private String getLetterGrade(double grade) {
        if (grade >= 90) return "A";
        if (grade >= 80) return "B";
        if (grade >= 70) return "C";
        if (grade >= 60) return "D";
        return "F";
    }

    private void updateStatistics() {
        if (grades.isEmpty()) {
            statsLabel.setText("No grades entered yet.");
            repaint();
            return;
        }

        double total = 0;
        double highest = Collections.max(grades);
        double lowest = Collections.min(grades);

        for (double grade : grades) {
            total += grade;
        }

        double average = total / grades.size();
        double median = calculateMedian();

        StringBuilder statsText = new StringBuilder("<html><b>Grade Statistics:</b><br>");
        statsText.append(String.format("Total grades: %d<br>", grades.size()));
        statsText.append(String.format("Average: %.2f<br>", average));
        statsText.append(String.format("Median: %.2f<br>", median));
        statsText.append(String.format("Highest: %.2f<br>", highest));
        statsText.append(String.format("Lowest: %.2f<br>", lowest));
        statsText.append("</html>");

        statsLabel.setText(statsText.toString());
        repaint();
    }

    private double calculateMedian() {
        ArrayList<Double> sorted = new ArrayList<>(grades);
        Collections.sort(sorted);

        int size = sorted.size();
        if (size % 2 == 1) {
            return sorted.get(size / 2);
        } else {
            return (sorted.get(size / 2 - 1) + sorted.get(size / 2)) / 2.0;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GradeAnalyzerPro());
    }
}