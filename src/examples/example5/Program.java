package examples.example5;

import db.DB;
import db.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Program {
    public static void main(String[] args) {

        Connection connection = null;
        Statement statement = null;

        try {
            connection = DB.getConnection();
            connection.setAutoCommit(false);

            statement = connection.createStatement();

            int rows1 = statement.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 1");

            // faking an exception
            int x = 1;
            if (x < 2) {
                throw new SQLException("I'm a totally legit exception (:");
            }

            int rows2 = statement.executeUpdate("UPDATE seller SET BaseSalary = 3090 WHERE DepartmentId = 2");

            connection.commit();

            System.out.println("Rows1: " + rows1);
            System.out.println("Rows2: " + rows2);

        }
        catch (SQLException e) {
            try {
                connection.rollback();
                throw new DbException("The transaction was unsuccessful; changes were rolled back. Cause: " + e.getMessage());
            } catch (SQLException ex) {
                throw new DbException("Rollback couldn't be finished. " + ex.getMessage());
            }
        }
        finally {
            DB.closeStatement(statement);
            DB.closeConnection();
        }
    }
}
