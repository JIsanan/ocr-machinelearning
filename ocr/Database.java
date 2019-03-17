/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ocr;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Yee
 */
public class Database {
    private Connection conn;
    private Statement statement;
    
    public Connection openConnection()
    {
        if(conn == null)
        {
            String url= "jdbc:mysql://localhost/";
            String dbName="ocr_desu";
            String driver="com.mysql.jdbc.Driver";
            String username="root";
            String password="";
            try{
                Class.forName(driver);
                this.conn = (Connection)DriverManager.getConnection(url+dbName,username,password);
                System.out.println("Connection established");
            }catch(ClassNotFoundException | SQLException sqle){
                System.out.println("Connection cannot be established");
            }
        }
        return conn;
    }
    
    public void execute(Connection con, String query) throws SQLException
    {
            Statement stmt = null;

            try {
                stmt = con.createStatement();
                int rs = stmt.executeUpdate(query);
            } catch (SQLException e ) {
                System.out.println(e);
            } finally {
                if (stmt != null) { stmt.close(); }
            }
    }
    
    public int[] retrieve(Connection con, String query) throws SQLException
    {
            Statement stmt = null;
            ResultSet rs = null;
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                
                while(rs.next()){
                    int[] arr = {1,2,3};
                    arr[0] = rs.getInt(1);
                    arr[1] = rs.getInt(2);
                    arr[2] = rs.getInt(3);
                    return arr;
                }
                
            } catch (SQLException e ) {
                System.out.println(e);
            } finally {
                if (stmt != null) { stmt.close(); }
            }
            
            int[] dumm = {1,2,3};
            return dumm;
    }
}
