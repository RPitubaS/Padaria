/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import modelo.dao.MovimentoDAO;
import produzconexao.ConexaoFirebird;

/**
 *
 * @author Pituba
 */
public class ControleMovimento {
    SimpleDateFormat formatbr = new SimpleDateFormat("dd/MM/yyyy");
    MovimentoDAO movdao;
    
     public ResultSet movimentousuario(int idponto){
    
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            stmt = con.prepareStatement("select * from MOVIMENTO where MOV_ID_PONTO = ?"
                                        + "order by ID_MOVIMENTO");
            stmt.setInt(1, idponto);
            rs = stmt.executeQuery();
        }catch(SQLException ex){
               JOptionPane.showMessageDialog(null, "Erro: " + ex + " ao tentar selecionar o movimento!");

        }
        if(rs != null){
           return rs;
        }else{
           return null;
        }
    }
     
     public ResultSet movimentodiario(String data){
      
         Connection con = ConexaoFirebird.getConnection();
         PreparedStatement stmt = null;
         ResultSet rs = null;
         
         try{
             java.sql.Date inicio = new java.sql.Date(formatbr.parse(data).getTime());
             
             stmt = con.prepareStatement("select DT.DATA, US.USUARIO,MV.HORA, MV.VENDA_AVISTA, ENTREGA, "
                     + "RECEBIMENTO_PRAZO, CARTAO, VALE, SAQUE, PAGAMENTOS, MOVIMENTO\n" +
                      "from MOVIMENTO MV join CARTAO_PONTO CP on MV.MOV_ID_PONTO = CP.ID_PONTO"
                     + " join DATA DT on CP.PT_DATA = DT.ID_DATA join USUARIOS US on CP.PT_USUARIO = US.ID"
                     + " where DT.DATA = ?");
             stmt.setDate(1, inicio);
             rs = stmt.executeQuery();
         }catch(Exception e){
             JOptionPane.showMessageDialog(null, "Erro: " + e + " ao tentar selecionar o movimento do dia!");
         }
            if(rs != null){
               return rs;
            }else{
               return null;
            }
     }
    
}
