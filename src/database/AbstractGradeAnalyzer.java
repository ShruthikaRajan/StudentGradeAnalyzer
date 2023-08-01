package database;

import java.sql.*;
import java.util.Scanner;

public abstract class AbstractGradeAnalyzer implements GradeAnalyzer {
    protected Connection connection;

    public AbstractGradeAnalyzer(Connection connection) {
        this.connection = connection;
    }

    @Override
    public abstract void enterStudentMarks(Connection connection, Scanner scanner) throws SQLException;

    @Override
    public abstract void editStudentMarks(Connection connection, Scanner scanner) throws SQLException;
   
    public void calculateAverageMarksBySubject(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter the subject: ");
        String subject = scanner.next();

        String query = "SELECT AVG(marks) AS average_mark FROM mentor WHERE subjects = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, subject);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            double averageMark = resultSet.getDouble("average_mark");
            System.out.println("\nAverage Grade for Subject " + subject + ": " + averageMark);
        } else {
            System.out.println("No records found for the subject: " + subject);
        }

        resultSet.close();
        preparedStatement.close();
    }
    @Override
    public void calculateAverageMarksBySubject(Connection connection) throws SQLException {
    	 String query = "SELECT subjects, AVG(marks) AS average_mark FROM mentor GROUP BY subjects";
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(query);
         System.out.println("\nSubject\t\tAverage Grade");
         while (resultSet.next()) {
             String subjects = resultSet.getString("subjects");
             double averagemark = resultSet.getDouble("average_mark");
             System.out.printf("%s\t\t%.2f\n", subjects, averagemark);
         }

         resultSet.close();
         statement.close();
    }

    @Override
    public void calculateOverallAverage(Connection connection) throws SQLException {
        String query = "SELECT AVG(marks) AS overall_average FROM mentor";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            double overallAverage = resultSet.getDouble("overall_average");
            System.out.println("\nOverall Average Grade: " + overallAverage);
        } else {
            System.out.println("No records found in the exam table.");
        }

        resultSet.close();
        statement.close();
    }
}