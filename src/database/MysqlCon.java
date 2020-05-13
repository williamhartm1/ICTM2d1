package database;
import java.awt.*;
import java.net.ConnectException;
import java.sql.*;
import java.util.Arrays;


public class MysqlCon {
    private static Connection con;
    private static boolean insert = false;

    public static void main(String[] args) {
        //ResultSet rs = getGegevens(0);
        //printdata(rs);
        //maakspeler(2,"Rogier");
        //insertspel(16,700,true,true,2);
        //ResultSet rs2 = getGegevens(2);
        //printdata(rs2);
        try {
            con.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }
    public static void insertspel(int spelID, int score, boolean alleen, boolean finishid, int SpelerID) {
        try {
            con = maakconnectie();
            Statement stmt = con.createStatement();
            Statement statement = con.createStatement();
            stmt.execute("INSERT INTO spel VALUE (" + spelID + ", "  + score + "," + alleen + "," + finishid + "," + SpelerID + ");");
            ResultSet resultSet = statement.executeQuery("SELECT max(score) as highscore,speler.SpelerID FROM speler join spel on spel.SpelerID = speler.SpelerID " +
                    "WHERE speler.SpelerID = " + SpelerID + " ORDER BY SpelerID;");
            //printdata(resultSet);
            String highscore = null;
            while(resultSet.next()) {
                highscore = resultSet.getString(1);
            }
            stmt.execute("UPDATE speler set speler.highscore = " + highscore + " WHERE speler.SpelerID = " + SpelerID + ";");
            con.close();
        } catch (java.sql.SQLException ex) {
            System.out.println("er is iets mis met de statement");
            System.out.println(ex.toString() + Arrays.toString(ex.getStackTrace()));
        }
        catch (NullPointerException nul){
            System.out.println(nul.toString());
        }
    }
    public static void maakspeler(int SpelerID,String naam){
        try {
            con = maakconnectie();
            Statement stmt = con.createStatement();
            stmt.execute("INSERT INTO speler VALUES (" + SpelerID + ", '" + naam + "',0);");
            con.close();
        }
        catch (SQLException | NullPointerException e){
            System.out.println(e.toString());
        }
    }
    public static ResultSet getGegevens(int spelerID){
        try {
            con = maakconnectie();
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM speler JOIN spel on spel.SpelerID = speler.SpelerID" +
                    " WHERE speler.spelerID = " + spelerID + ";");
            return res;
        }catch (java.sql.SQLException ex){
            System.out.println("er is iets mis met de query");
            System.out.println(ex.toString());
            return null;
        }
        catch(NullPointerException nul){
            System.out.println(nul.toString());
            return null;
        }
    }
    public static Connection maakconnectie(){
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Tetris", "root", "");
            return con;
        } catch (java.sql.SQLException e) {
            System.out.println("er is iets mis met de verbinding");
            System.out.println(e.toString());
        }
        return null;
    }
    public static void printdata(ResultSet res){
        if (res == null) {
            System.out.println("rs is null");
        } else {
            try {
                ResultSetMetaData rsmd = res.getMetaData();
                int columnummer = rsmd.getColumnCount();
                System.out.println(columnummer);
                while (res.next()) {
                    for (int i = 1; i <= columnummer; i++) {
                        if (i > 1) {
                            System.out.print(",  ");
                        }

                            System.out.println(rsmd.getColumnName(i) + ": " + res.getString(i));
                    }
                    System.out.println("");
                }
            } catch (Exception e) {
                System.out.println("er is iets fout gegaan bij het uitprinten van de gegevens " + e.toString());
            }
        }
    }
    public static void excecuteStatement(String statement){
        Connection con = maakconnectie();
        try {
            Statement stmt = con.createStatement();
            try{
                stmt.execute(statement);
            }
            catch(java.sql.SQLException jex){
                System.out.println(jex.toString());
                jex.printStackTrace();
                System.out.println("er is iets mis me de query");
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }
}
