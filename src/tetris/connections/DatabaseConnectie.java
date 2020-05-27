package tetris.connections;

/*
Verbinding met de database wordt opgezet. Verschillende database functies worden hier geinitieerd.
Maken van een connectie met de database;
opslaan van een gespeeld spel;
opslaan van een speler;
ophalen van spelerID dmv spelernaam;
ophalen top 5 highscores;
printen van data;
 */

import java.sql.*;
import java.util.Arrays;

public class DatabaseConnectie {
    private static Connection con;


    // connectie met database maken
    public static Connection maakconnectie(){
        try {
            //verbinding leggen met database te vinden op localhost port 3306 met database naam Tetris
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Tetris", "root", "");
            return con;
        } catch (java.sql.SQLException e) {
            System.out.println("er is iets mis met de verbinding");
            System.out.println(e.toString());
        }
        return null;
    }

    //gespeeld spel toevoegen gekoppeld aan speler
    public static void insertspel(int score, boolean alleen, boolean finishid, int SpelerID) {
        try {
            con = maakconnectie();
            Statement state = con.createStatement();
            Statement stmt = con.createStatement();
            Statement statement = con.createStatement();

            //volgende spelID
            ResultSet rs = state.executeQuery("SELECT max(SpelID) as spel FROM spel");
            rs.next();
            int spelID = rs.getInt(1) + 1;

            //nieuw spel toevoegen
            stmt.execute("INSERT INTO spel VALUE (" + spelID + ", "  + score + "," + alleen + "," + finishid + "," + SpelerID + ");");

            //controleren of score hoger is dan huidige highscore van de speler
            ResultSet resultSet = statement.executeQuery("SELECT max(score) as highscore,speler.SpelerID FROM speler join spel on spel.SpelerID = speler.SpelerID " +
                    "WHERE speler.SpelerID = " + SpelerID + " ORDER BY SpelerID;");
            String highscore = null;
            while(resultSet.next()) {
                highscore = resultSet.getString(1);
            }

            //highscore eventueel aanpassen in 'speler'
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

    //ID van een speler ophalen
    public static int getSpelerID(String naam){
        int spelerID = 0;
        try {
            con = maakconnectie();
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery("SELECT SpelerID FROM `speler` WHERE naam = \"" + naam + "\"");
            while (result.next()) {
                spelerID = result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return spelerID;
    }

    //nieuwe speler aanmaken
    public static void maakspeler(String naam){
        try {
            con = maakconnectie();
            Statement stmt = con.createStatement();

            if (getSpelerID(naam) == 0) { //als spelernaam nog niet bestaat

                //opvolgende spelerID ophalen voor nieuwe speler
                ResultSet rs = stmt.executeQuery("SELECT max(SpelerID) as speler FROM speler");
                rs.next();
                int newSpelerID = rs.getInt(1) + 1;

                //speler opslaan in database
                stmt.execute("INSERT INTO speler VALUES (" + newSpelerID + ", '" + naam + "',0);");
                con.close();
            }
        }
        catch (SQLException | NullPointerException e){
            System.out.println(e.toString());
        }
    }


    // top 5 highscores voor ranking ophalen
    public static String[] getHighscores() {
        Connection con = maakconnectie();
        String[] highscore = new String[5]; //top 5
        try {
            int i = 0;
            Statement stmt = con.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT naam,highscore from speler order by highscore DESC");
            while (rs.next()) {
                if (i < 5) {
                    String gegevens = rs.getString(1) + "   " + rs.getString(2);
                    highscore[i] = gegevens;
                }
                i++;
            }
            return highscore;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return highscore;
    }

/*
/////niet in gebruik//////

    //resultaten printen
    public static void printData(ResultSet res){
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


    //alle gegevens uit de database teruggeven
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
    */
}
