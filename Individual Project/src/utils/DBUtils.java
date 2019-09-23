/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Users;

/**
 *
 * @author Panos
 */
public class DBUtils {
    
    private static final String USERNAME = "J7DRRLbWja";
    private static final String PASS = "UbCPGinl33";
    private static final String MYSQLURL = "jdbc:mysql://remotemysql.com:3306/J7DRRLbWja?zeroDateTimeBehavior=CONVERT_TO_NULL" +
                                                            "&useUnicode=true" +
                                                            "&useJDBCCompliantTimezoneShift=true" +
                                                            "&useLegacyDatetimeCode=false" +
                                                            "&serverTimezone=UTC" +
                                                            "&allowPublicKeyRetrieval=true" +
                                                            "&useSSL=false";
    
    public static Connection getConnection(){
        Connection con = null;
        try {
            con = DriverManager.getConnection(MYSQLURL, USERNAME, PASS);
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return con;
    }
    
    public static void closeConnection (Connection con) {
        try {
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static ArrayList <Users> getUsersLoginInfo() {
        Connection con = DBUtils.getConnection();
        String sql = "SELECT id,username, password, roleid FROM users";
        ArrayList <Users> users = new ArrayList();  
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            ResultSet rs = pstm.executeQuery();
         
            while (rs.next()){
                Users user = new Users();
                user.setId(rs.getInt(1));
                user.setUsername(rs.getString(2)); 
                user.setPassword(rs.getString(3)); 
                user.setRoleId(rs.getInt(4));
                users.add(user);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
            return users;
        }
    }
}
