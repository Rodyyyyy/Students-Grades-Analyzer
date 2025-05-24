Grade Analyzer Pro - Java Swing Application
Overview
Grade Analyzer Pro is a comprehensive Java Swing application designed to help educators and students analyze grade distributions. The application provides a user-friendly interface for entering, managing, and visualizing grade data with detailed statistics and graphical representations.

Features
1. Grade Management
Add Grades: Enter individual grades (0-100) via a text field

Clear All: Remove all entered grades with a single click

Generate Random Grades: Quickly populate with 10 random grades for demonstration

2. Data Visualization
Grade Table: Displays all entered grades with:

Entry number

Numerical grade (formatted to 2 decimal places)

Corresponding letter grade (A-F scale)

Statistics Panel: Shows key metrics including:

Total number of grades

Average grade

Median grade

Highest and lowest grades

Distribution Chart: A bar graph visualizing grade distribution across five ranges:

0-20, 20-40, 40-60, 60-80, and 80-100

3. Technical Implementation
GUI Framework: Built using Java Swing with a modern system look-and-feel

Data Structure: Uses ArrayList<Double> to store grades

Table Management: DefaultTableModel for efficient table updates

Error Handling: Validates input to ensure grades are within 0-100 range

Decimal Formatting: Consistent number formatting throughout the application

Class Structure
Main Class: GradeAnalyzerPro
Extends JFrame and serves as the main application window.

Key Components:
Fields:

grades: ArrayList storing all entered grades

tableModel: Data model for the grades table

statsLabel: JLabel for displaying statistics

df: DecimalFormat for consistent number display

Constructor:

Sets up the main window properties

Initializes all UI components

Applies system look-and-feel

UI Creation Methods:

createInputPanel(): Builds the grade entry controls

createTablePanel(): Creates the grades table with scroll pane

createStatsPanel(): Constructs the statistics display and chart

Utility Methods:

updateTable(): Refreshes the table with current grade data

getLetterGrade(): Converts numerical grades to letter grades

updateStatistics(): Calculates and displays current statistics

calculateMedian(): Computes the median grade

Usage Instructions
Adding Grades:

Enter a grade (0-100) in the text field

Click "Add Grade" or press Enter

Invalid entries will display warning messages

Generating Random Data:

Click "Generate Random (10)" to create 10 random grades

Useful for quick testing and demonstration

Clearing Data:

Click "Clear All" to remove all grades and reset the application

Viewing Statistics:

Statistics update automatically as grades are added/removed

The distribution chart visually represents grade ranges

Technical Details
Grade Conversion Scale
A: 90-100

B: 80-89.99

C: 70-79.99

D: 60-69.99

F: 0-59.99

Random Grade Generation
Generates grades between 0-100 in 0.2 increments using:

java
Math.round(Math.random() * 500) / 5.0
Distribution Chart
Divides grades into five 20-point ranges

Uses distinct colors for each range

Automatically scales to show relative quantities

Displays exact counts above each bar

Requirements
Java Runtime Environment (JRE) 8 or later

Swing libraries (included in standard Java distributions)

How to Run
Compile and execute the application by running:

java
public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new GradeAnalyzerPro());
}
Customization Options
Grading Scale: Modify the getLetterGrade() method to implement different grading scales

Color Scheme: Adjust the colors in the chart panel's colors array

Decimal Precision: Change the DecimalFormat pattern in the constructor

Random Generation: Alter the number of random grades or generation algorithm

License
This application is provided as-is for educational and demonstration purposes. Free to use and modify under standard open-source guidelines.
