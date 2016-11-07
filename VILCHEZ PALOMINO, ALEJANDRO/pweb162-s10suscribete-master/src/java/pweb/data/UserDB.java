package pweb.data;

import java.sql.*;

import pweb.business.User;

public class UserDB 
{
    public static int insert(User user) 
    {
        Connection connection = null;
        PreparedStatement ps = null;        
        
        try 
        {
            // cargar el controlador
            Class.forName("org.sqlite.JDBC");

            // crear una conexion             
            String dbURL = "jdbc:sqlite:C:/sqlite3/EmailDB.db";
            connection = DriverManager.getConnection(dbURL);      
            
            String query = "INSERT INTO " 
                         + "USER (FIRST_NAME, LAST_NAME, EMAIL) "
                         + "VALUES (?, ?, ?)";
        
            ps = connection.prepareStatement(query);                        
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            return ps.executeUpdate();            
        } 
        catch (ClassNotFoundException e) {
            System.out.println(e);
            return 0;
        }         
        catch (SQLException e) {
            System.out.println(e);
            return 0;
        } 
        finally {
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(connection);            
        }
    }

    public static int update(User user) 
    {
        Connection connection = null;
        PreparedStatement ps = null;             
        
        try 
        {
            // cargar el controlador
            Class.forName("org.sqlite.JDBC");

            // crear una conexion             
            String dbURL = "jdbc:sqlite:C:/sqlite3/EmailDB.db";
            connection = DriverManager.getConnection(dbURL);    
            
            String query = "UPDATE USER SET "
                         + "FIRST_NAME = ?, "
                         + "LAST_NAME = ? "
                         + "WHERE EMAIL = ?";        
            
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            return ps.executeUpdate();
        } 
        catch (ClassNotFoundException e) {
            System.out.println(e);
            return 0;
        }         
        catch (SQLException e) {
            System.out.println(e);
            return 0;
        } 
        finally {
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(connection);            
        }
    }

    public static int delete(User user) 
    {
        Connection connection = null;
        PreparedStatement ps = null;                    
        
        try 
        {            
            // cargar el controlador
            Class.forName("org.sqlite.JDBC");

            // crear una conexion             
            String dbURL = "jdbc:sqlite:C:/sqlite3/EmailDB.db";
            connection = DriverManager.getConnection(dbURL); 
            
            String query = "DELETE FROM USER "
                         + "WHERE EMAIL = ?";
            
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getEmail());
            return ps.executeUpdate();
        } 
        catch (ClassNotFoundException e) {
            System.out.println(e);
            return 0;
        }         
        catch (SQLException e) {
            System.out.println(e);
            return 0;
        } 
        finally {
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(connection);            
        }
    }

    public static boolean emailExists(String email) 
    {
        Connection connection = null;
        PreparedStatement ps = null;             
        ResultSet rs = null;
        
        try 
        {
            // cargar el controlador
            Class.forName("org.sqlite.JDBC");

            // crear una conexion             
            String dbURL = "jdbc:sqlite:C:/sqlite3/EmailDB.db";
            connection = DriverManager.getConnection(dbURL); 
            
            String query = "SELECT EMAIL FROM USER "
                         + "WHERE EMAIL = ?";
            
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            return rs.next();
        } 
        catch (ClassNotFoundException e) {
            System.out.println(e);
            return false;
        }         
        catch (SQLException e) {
            System.out.println(e);
            return false;
        }             
        finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(connection);  
        }
    }

    public static User selectUser(String email) 
    {
        Connection connection = null;
        PreparedStatement ps = null;                     
        ResultSet rs = null;
        
        try 
        {            
            // cargar el controlador
            Class.forName("org.sqlite.JDBC");
            
            // crear una conexion                         
            String dbURL = "jdbc:sqlite:C:/sqlite3/EmailDB.db";
            connection = DriverManager.getConnection(dbURL); 
            
            String query = "SELECT * FROM USER "
                         + "WHERE EMAIL = ?";
            
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            
            User user = null;
            
            if (rs.next()) 
            {
                user = new User();
                user.setFirstName(rs.getString("FIRST_NAME"));
                user.setLastName(rs.getString("LAST_NAME"));
                user.setEmail(rs.getString("EMAIL"));
            }
            return user;
        }
        catch (ClassNotFoundException e) {
            System.out.println(e);
            return null;
        }  
        catch (SQLException e) {
            System.out.println(e);
            return null;
        } 
        finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(connection);  
        }
    }

}