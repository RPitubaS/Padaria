/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package produzconexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Pituba
 */
public class ConexaoFirebird {
    private static Connection conexao;
           
           public ConexaoFirebird(String conectar, String ip) throws ClassNotFoundException, SQLException {
               
           Class.forName("org.firebirdsql.jdbc.FBDriver");
           String url = "jdbc:firebirdsql:" + ip + "/3050:" + conectar;

        conexao = DriverManager.getConnection(url, "SYSDBA", "masterkey");
    }
            public static Connection getConnection(){
              
        try {
            
          return conexao;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro na conexão! "+ ex, "Bragança", JOptionPane.ERROR_MESSAGE);
        }
        return conexao;
}
    
        public static void closeConnection(Connection con){
       
                try {
                    if(con != null){
                    con.close();
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao fechar conexão! " + ex, "Bragança", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        public static void closeConnection(Connection con, PreparedStatement stmt){
       
            closeConnection(con);
                try {
                  if(stmt != null){
                    stmt.close();
                  }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao fechar conexão e/ou Statement! " + ex, "Bragança", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs){
       
            closeConnection(con, stmt);
                try {
                  if(rs != null){
                    rs.close();
                  }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao fechar conexão e/ou Statement e/ou Resultset! " + ex, "Bragança", JOptionPane.ERROR_MESSAGE);
            }
        }
}
