package budget;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.math.BigDecimal;
import java.sql.*;
import java.text.NumberFormat;

public class HomeBudged {

    private static  final  String DB_HOST = "morfeusz.wszib.edu.pl";
    private static  final  int DB_Port = 1433;
    private static  final  String DB_USER = "darzak";
    private static  final  String DB_PASS = "Anakonda2016";
    private static  final  String DB_NAME = "darzak";

    private static final String INSERT_ENTRY_SQL = "insert into Budged.BudgedEntries (EntryName, Amount) values (?, ?)";
    private static final String GET_BALANCE_SQL = "select SUM(Amount) as Balance from Budged.BudgedEntries";
    private static final String LAST_TEN_TRANSACTIONS = "select top 10 * from Budged.BudgedEntries order by EntryDate desc";


    public static void main(String[] args) {


        // do zrobiebnia walidacja wpisów od użytkownika
        /*if (args[0] != null && args[1] != null){
            Connection con = ;
            try {
                PreparedStatement ps1 = con.prepareStatement(LAST_TEN_TRANSACTIONS);
            } catch (SQLException e) {
                System.out.println();
            }*/

        BudgedEntry be = new BudgedEntry();
        be.setEntryName(args[0]);
        be.setAmount(new BigDecimal(args[1]));
        HomeBudged hb = new HomeBudged();

        try (Connection con = hb.connect(DB_USER, DB_PASS, DB_NAME);
        Statement stmt = con.createStatement())
        {
            PreparedStatement ps = con.prepareStatement(INSERT_ENTRY_SQL);
            ps.setString(1, be.getEntryName());
            ps.setBigDecimal(2, be.getAmount());
            ps.execute();

            BigDecimal balance;
            ResultSet rs = stmt.executeQuery(GET_BALANCE_SQL);

           if (rs.next()){
                balance = rs.getBigDecimal("balance");
            }else {
               balance = new BigDecimal(0);
           }

            System.out.print("Zapisano! kwota: " + be.getEntryName());
            System.out.print(", kwota: " + hb.currencyFormat(be.getAmount()));
            System.out.print(", saldo: " + hb.currencyFormat(balance));


        } catch (SQLException e) {
            System.out.println("Wystąpił błąd bazy danych: " + e.getMessage());
        }
    }

    public Connection connect(String username, String password, String dbName) throws SQLServerException {
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setServerName(DB_HOST);
        ds.setPortNumber(DB_Port);
        ds.setUser(username);
        ds.setPassword(password);
        ds.setDatabaseName(dbName);
        return ds.getConnection();
    }

    public String currencyFormat (BigDecimal bd){
        return NumberFormat.getCurrencyInstance().format(bd);
    }

}


