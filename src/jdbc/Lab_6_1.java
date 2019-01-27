
package jdbc;

import com.microsoft.sqlserver.jdbc.SQLServerConnection;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.*;

public class Lab_6_1 {
    public static void main(String[] args) {
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser("darzak");
        ds.setServerName("morfeusz.wszib.edu.pl");
        ds.setPortNumber(1433);
        ds.setDatabaseName("AdventureWorks");
        ds.setPassword("??????????????????");

        String sql = "select top 10(LastName), FirstName from Person.Contact where LastName='Anderson'";
        String sql1 = "select distinct title from HumanResources.Employee";
        String sql2 = "select CustomerID, COUNT(*) as cnt from Sales.SalesOrderHeader group by CustomerID order by cnt desc";



       try (Connection con = ds.getConnection(); Statement stmt = con.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);

            String lastName;
            String firstName;
            while (rs.next()){
                if ((lastName = rs.getString("LastName")) != null) {
                    lastName = rs.getString("LastName");
                    firstName = rs.getString("FirstName");
                    System.out.println(lastName + "  " + firstName);
                }
            }
           System.out.println("--------------------------------------------------------------");

        } catch (SQLException e){
            System.out.println("Problemy z bazą danych: " + e.getMessage());
        }
        try (Connection con = ds.getConnection(); Statement stmt = con.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql1);

            String title;
            while (rs.next()){
                if ((title = rs.getString("title")) != null) {
                    title = rs.getString("title");
                    System.out.println(title);
                }
            }
            System.out.println("--------------------------------------------------------------");

        } catch (SQLException e){
            System.out.println("Problemy z bazą danych: " + e.getMessage());
        }
        try (Connection con = ds.getConnection(); Statement stmt = con.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql2);

            String cnt;
            String custID;
            while (rs.next()){
                if ((cnt = rs.getString("cnt")) != null) {
                    cnt = rs.getString("cnt");
                    custID = rs.getString("CustomerID");
                    System.out.println(cnt + "  " + custID);

                }
            }

        } catch (SQLException e){
            System.out.println("Problemy z bazą danych: " + e.getMessage());
        }
    }
}
