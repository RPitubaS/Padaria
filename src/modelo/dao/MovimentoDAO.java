/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.bean.Datas;
import modelo.bean.Entradas;
import modelo.bean.Movimento;
import produzconexao.ConexaoFirebird;

/**
 *
 * @author Pituba
 */
public class MovimentoDAO {
    
    SimpleDateFormat formatbr = new SimpleDateFormat("dd.MM.yyyy");
    SimpleDateFormat formatbrh = new SimpleDateFormat("HH:mm:ss");
    public void salvar_data(String agoraini){
                    
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
       
        try {       
            //SimpleDateFormat formatbr = new SimpleDateFormat("dd.MM.yyyy");
            java.sql.Date sdf = new java.sql.Date(formatbr.parse(agoraini).getTime());
            stmt = con.prepareStatement("INSERT INTO DATA(DATA) VALUES(?)");
            stmt.setDate(1, sdf);//.setDate(1, sdf);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar salvar data de hoje! " + ex,
                    "Bragança", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex + " ao tentar salvar data de hoje! ",
                    "Bragança", JOptionPane.ERROR_MESSAGE);
        }finally{
            ConexaoFirebird.closeConnection(con, stmt);
        }
    }
    
    public void salvar_ponto_entrada(int data, int usuario, String horaentrada, float caixaentradanotas, 
                                  float caixaentradamoedas){
    
        Connection con = ConexaoFirebird.getConnection();
        
        PreparedStatement stmt = null;
           
        try{
            //SimpleDateFormat formatbr = new SimpleDateFormat("HH:mm:ss");
            java.sql.Time sdf = new java.sql.Time(formatbrh.parse(horaentrada).getTime());
            stmt = con.prepareStatement("INSERT INTO CARTAO_PONTO(PT_DATA, PT_USUARIO, HORA_ENTRADA, CAIXA_ENTRADA_NOTAS," +
                                        "CAIXA_ENTRADA_MOEDAS) VALUES(?,?,?,?,?)");
            
            stmt.setInt(1, data);
            stmt.setInt(2, usuario);
            stmt.setTime(3, sdf);
            stmt.setFloat(4, caixaentradanotas);
            stmt.setFloat(5, caixaentradamoedas);
            stmt.executeUpdate();          
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao tentar salvar entrada! " + ex,
                    "Bragança", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex + " ao tentar salvar hora de entrada! ",
                    "Bragança", JOptionPane.ERROR_MESSAGE);
        }finally{
                 ConexaoFirebird.closeConnection(con, stmt);
        }
    
    }
    
    public void salvar_entrada_movimento(int movidponto, String horaagora, float vendaavista, float entrega, 
            float recebimentoprazo, float cartao, float vale, float saque, float pagamentos, float movimento){
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        try{
            //SimpleDateFormat formatbr = new SimpleDateFormat("HH:mm:ss");
            java.sql.Time sdf = new java.sql.Time(formatbrh.parse(horaagora).getTime());
            stmt = con.prepareStatement("insert into MOVIMENTO(MOV_ID_PONTO, HORA, VENDA_AVISTA, ENTREGA, RECEBIMENTO_PRAZO,"
                                        + " CARTAO, VALE, SAQUE, PAGAMENTOS, MOVIMENTO) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            
            stmt.setInt(1, movidponto);
            stmt.setTime(2, sdf);
            stmt.setFloat(3, vendaavista);
            stmt.setFloat(4, entrega);
            stmt.setFloat(5, recebimentoprazo);
            stmt.setFloat(6, cartao);
            stmt.setFloat(7, vale);
            stmt.setFloat(8, saque);
            stmt.setFloat(9, pagamentos);
            stmt.setFloat(10, movimento);
            stmt.executeUpdate();
        }catch(SQLException ex){
               JOptionPane.showMessageDialog(null, "Erro ao tentar salvar entrada de movimento! " + ex,
                    "Bragança", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
               JOptionPane.showMessageDialog(null, "Erro: " + ex + " ao tentar salvar hora da entrada de movimento! ",
                    "Bragança", JOptionPane.ERROR_MESSAGE);
        }finally{
            ConexaoFirebird.closeConnection(con, stmt);
        }
    }
    
    public void salvar_mot_pagt(int pgtomovi, String pagamento){
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        try{
        stmt = con.prepareStatement("insert into PAGAMENTOS(PG_ID_MOVIMENTO, PG_PAGO) values(?, ?)");
        stmt.setInt(1, pgtomovi);
        stmt.setString(2, pagamento);
        stmt.executeUpdate();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro: " + ex + " ao tentar salvar o que foi pago!","Bragança",
                    JOptionPane.ERROR_MESSAGE);
        }finally{
            ConexaoFirebird.closeConnection(con, stmt);
        }
        
    }
    
     public void salvar_recebprazo(int pgtomovi, String cliente){
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        try{
        stmt = con.prepareStatement("insert into RECEBIMENTOPRAZO(RP_ID_MOVIMENTO, CLIENTE_PAGANTE) values(?, ?)");
        stmt.setInt(1, pgtomovi);
        stmt.setString(2, cliente);
        stmt.executeUpdate();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro: " + ex + " ao tentar salvar pagamento do cliente!","Bragança",
                    JOptionPane.ERROR_MESSAGE);
        }finally{
            ConexaoFirebird.closeConnection(con, stmt);
        }
        
    }
    
     public void salvar_vale(int pgtomovi, String funcionario){
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        try{
        stmt = con.prepareStatement("insert into VALES(VL_ID_MOVIMENTO, FUNCIONARIO) values(?, ?)");
        stmt.setInt(1, pgtomovi);
        stmt.setString(2, funcionario);
        stmt.executeUpdate();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro: " + ex + " ao tentar salvar vale!","Bragança",
                    JOptionPane.ERROR_MESSAGE);
        }finally{
            ConexaoFirebird.closeConnection(con, stmt);
        }
        
    }
     
     public void salvar_saque(int pgtomovi, String funcionario){
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        try{
        stmt = con.prepareStatement("insert into SAQUES(SQ_ID_MOVIMENTO, FUNCIONARIOSAQUE) values(?, ?)");
        stmt.setInt(1, pgtomovi);
        stmt.setString(2, funcionario);
        stmt.executeUpdate();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro: " + ex + " ao tentar salvar vale!","Bragança",
                    JOptionPane.ERROR_MESSAGE);
        }finally{
            ConexaoFirebird.closeConnection(con, stmt);
        }
        
    }
     
    public void atualizar_ponto(int idponto, Time horasaida, float caixasaida){
        
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        try{
            //SimpleDateFormat formatbr = new SimpleDateFormat("HH:mm:ss");
            //java.sql.Time sdf = new java.sql.Time(formatbr.parse(horasaida).getTime());
            stmt = con.prepareStatement("UPDATE CARTAO_PONTO a SET a.HORA_SAIDA = ?, a.CAIXA_SAIDA = ?" +
                                        "WHERE a.ID_PONTO = ?");
            stmt.setTime(1, horasaida);
            stmt.setFloat(2, caixasaida);
            stmt.setInt(3, idponto);
            stmt.executeUpdate();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao tentar fechar o ponto! " + ex,
                    "Bragança", JOptionPane.ERROR_MESSAGE);
        //} catch (ParseException ex) {
             //JOptionPane.showMessageDialog(null, "Erro ao tentar fechar o movimento! " + ex,
                    //"Bragança", JOptionPane.ERROR_MESSAGE);
        }finally{
            ConexaoFirebird.closeConnection(con, stmt);
        }
    }
    public List<Datas> selecionardata(String agora){
    
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Datas> selecionadataatual = new ArrayList<>();
        try {
            //SimpleDateFormat formatbr = new SimpleDateFormat("dd.MM.yyyy");
            java.sql.Date sdf = new java.sql.Date(formatbr.parse(agora).getTime());
            stmt = con.prepareStatement("SELECT * FROM DATA WHERE DATA = ?");
            stmt.setDate(1, sdf);
            rs = stmt.executeQuery();
            Datas datas = new Datas();
            while(rs.next()){
                             datas.setId(rs.getInt("ID_DATA"));
                             datas.setData(rs.getDate("DATA"));
                             selecionadataatual.add(datas);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro: "+ ex +" ao selecionar o dia de hoje!","Bragança",
                                          JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex + " ao tentar encontrar data de entrada! ",
                    "Bragança", JOptionPane.ERROR_MESSAGE);
        }finally{
            ConexaoFirebird.closeConnection(con, stmt, rs);
        }
         return selecionadataatual;
    }
    
    public String selecionamotivopagto(int pgidmovimento){
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String motivopago = null;
        try{
            stmt = con.prepareStatement("select PG_PAGO from PAGAMENTOS where PG_ID_MOVIMENTO = ?");
            stmt.setInt(1, pgidmovimento);
            rs = stmt.executeQuery();
            while(rs.next()){
                  motivopago = rs.getString("PG_PAGO");
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro: " + ex + " ao buscar o que foi pago!", "Bragança",
                    JOptionPane.ERROR_MESSAGE);
        }finally{
            return motivopago;
        }
    }
    
    public String selecionafunvionariovale(int pgidmovimento){
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String funcionariovale = null;
        try{
            stmt = con.prepareStatement("select FUNCIONARIO from VALES where VL_ID_MOVIMENTO = ?");
            stmt.setInt(1, pgidmovimento);
            rs = stmt.executeQuery();
            while(rs.next()){
                  funcionariovale = rs.getString("FUNCIONARIO");
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro: " + ex + "\n ao buscar o nome do funcionário!", "Bragança",
                    JOptionPane.ERROR_MESSAGE);
        }finally{
            return funcionariovale;
        }
    }
    
    public String selecionafuncionariosaque(int pgidmovimento){
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String funcionariosaque = null;
        try{
            stmt = con.prepareStatement("select FUNCIONARIOSAQUE from SAQUES where SQ_ID_MOVIMENTO = ?");
            stmt.setInt(1, pgidmovimento);
            rs = stmt.executeQuery();
            while(rs.next()){
                  funcionariosaque = rs.getString("FUNCIONARIOSAQUE");
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro: " + ex + "\n ao buscar o nome do funcionário!", "Bragança",
                    JOptionPane.ERROR_MESSAGE);
        }finally{
            return funcionariosaque;
        }
    }
    
    public String selecionaclientepagamento(int pgidmovimento){
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String clientepagamento = null;
        try{
            stmt = con.prepareStatement("select CLIENTE_PAGANTE from RECEBIMENTOPRAZO where RP_ID_MOVIMENTO = ?");
            stmt.setInt(1, pgidmovimento);
            rs = stmt.executeQuery();
            while(rs.next()){
                  clientepagamento = rs.getString("CLIENTE_PAGANTE");
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro: " + ex + "\n ao buscar o nome do cliente!", "Bragança",
                    JOptionPane.ERROR_MESSAGE);
        }finally{
            return clientepagamento;
        }
    }
    
    public List<Entradas> selecionarentrada(String agora, int usuario){
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Entradas> selecionaentrada = new ArrayList<>();
        try{
            //SimpleDateFormat formatbr = new SimpleDateFormat("dd.MM.yyyy");
            java.sql.Date sdf = new java.sql.Date(formatbr.parse(agora).getTime());
            stmt = con.prepareStatement("SELECT CP.CAIXA_ENTRADA_NOTAS,\n" + 
                                        "CP.CAIXA_ENTRADA_MOEDAS, US.USUARIO FROM DATA DT JOIN CARTAO_PONTO CP\n" + 
                                        "ON CP.PT_DATA = DT.ID_DATA JOIN USUARIOS US ON US.ID = CP.PT_USUARIO\n" +
                                        "WHERE DT.DATA = ? AND US.ID = ? AND CP.HORA_SAIDA is null");
            stmt.setDate(1, sdf);
            stmt.setInt(2, usuario);
            rs = stmt.executeQuery();
            Entradas entradas = new Entradas();
              while(rs.next()){
                    entradas.setUsuario(rs.getString("USUARIO"));
                    entradas.setValorinicialcedula(rs.getFloat("CAIXA_ENTRADA_NOTAS"));
                    entradas.setValorinicialmoedas(rs.getFloat("CAIXA_ENTRADA_MOEDAS"));
                    selecionaentrada.add(entradas);
              }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro: " + ex + " ao tentar acessar informações de hoje!");
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null,"Erro: " + ex + " ao tentar selecionar informações de hoje!");
        }finally{
           ConexaoFirebird.closeConnection(con, stmt, rs);
        }
        return selecionaentrada;
    }
    
    public int selecionarmaxmovimento(int movidponto){
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int selecionamaxmovimento = 0;
        try{
        stmt = con.prepareStatement("select max(ID_MOVIMENTO) AS ULTIMO_MOV from MOVIMENTO \n" +
                             "where MOV_ID_PONTO = ?");
        stmt.setInt(1, movidponto);
        rs = stmt.executeQuery();
        while(rs.next()){
          selecionamaxmovimento = rs.getInt("ULTIMO_MOV");
        }
        }catch(SQLException ex){
               JOptionPane.showMessageDialog(null, "Erro: " + ex + " ao selecionar a última saída!");
        }finally{
               ConexaoFirebird.closeConnection(con, stmt, rs);
        }
        return selecionamaxmovimento;
    }
    
    public List<Entradas> selecionarsaida(int idponto){
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Entradas> selecionasaida = new ArrayList<>();
        try{
            stmt = con.prepareStatement("select CP.HORA_ENTRADA, CP.HORA_SAIDA, CP.CAIXA_SAIDA, DT.DATA"
                                        + " from CARTAO_PONTO CP JOIN DATA DT ON CP.PT_DATA = DT.ID_DATA"
                                        + " where CP.ID_PONTO = ?");
            stmt.setInt(1, idponto);
            rs = stmt.executeQuery();
            while(rs.next()){
                  Entradas entradas = new Entradas();
                  entradas.setHoraagora(rs.getTime("HORA_ENTRADA"));
                  entradas.setHorasaida(rs.getTime("HORA_SAIDA"));
                  entradas.setCaixasaida(rs.getFloat("CAIXA_SAIDA"));
                  entradas.setData(rs.getDate("DATA"));
                  selecionasaida.add(entradas);
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro: " + ex + " ao tentar acessar informações de hoje!");
        }finally{
            ConexaoFirebird.closeConnection(con, stmt, rs);
        }
        return selecionasaida;
    }
    
    public List<Entradas> selecionarsaidanull (){
    
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Entradas> selecionasaidanula = new ArrayList<>();
        try{
            stmt = con.prepareStatement("SELECT CP.ID_PONTO, CP.CAIXA_ENTRADA_NOTAS,\n" + 
                                        "CP.CAIXA_ENTRADA_MOEDAS,DT.DATA, US.ID, US.USUARIO FROM DATA DT JOIN CARTAO_PONTO CP\n" + 
                                        "ON CP.PT_DATA = DT.ID_DATA JOIN USUARIOS US ON US.ID = CP.PT_USUARIO\n" +
                                        "WHERE CP.HORA_SAIDA is null");
            rs = stmt.executeQuery();
            Entradas entradas = new Entradas();
            while(rs.next()){
                  entradas.setIdponto(rs.getInt("ID_PONTO"));
                  entradas.setIdusuario(rs.getInt("ID"));
                  entradas.setData(rs.getDate("DATA"));
                  entradas.setUsuario(rs.getString("USUARIO"));
                  entradas.setValorinicialcedula(rs.getFloat("CAIXA_ENTRADA_NOTAS"));
                  entradas.setValorinicialmoedas(rs.getFloat("CAIXA_ENTRADA_MOEDAS"));
                  selecionasaidanula.add(entradas);
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro: " + ex + " ao tentar acessar informações de hoje!");
        }finally{
            ConexaoFirebird.closeConnection(con, stmt, rs);
        }
        return selecionasaidanula;
    }
    
    public List<Movimento> selecionarmovimentousua(int idponto){
    
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Movimento> selecionamovimentousua = new ArrayList<>();
        try{
            stmt = con.prepareStatement("select * from MOVIMENTO where MOV_ID_PONTO = ?"
                                        + "order by ID_MOVIMENTO");
            stmt.setInt(1, idponto);
            rs = stmt.executeQuery();
            while(rs.next()){
                  Movimento movimento = new Movimento();
                  movimento.setIdmovimento(rs.getInt("ID_MOVIMENTO"));
                  movimento.setMovidponto(rs.getInt("MOV_ID_PONTO"));
                  movimento.setHora(rs.getTime("HORA"));
                  movimento.setVendaavista(rs.getFloat("VENDA_AVISTA"));
                  movimento.setEntrega(rs.getFloat("ENTREGA"));
                  movimento.setRecebapraso(rs.getFloat("RECEBIMENTO_PRAZO"));
                  movimento.setVale(rs.getFloat("VALE"));
                  movimento.setSaque(rs.getFloat("SAQUE"));
                  movimento.setPagamentos(rs.getFloat("PAGAMENTOS"));
                  movimento.setMovimento(rs.getFloat("MOVIMENTO"));
                  movimento.setCartao(rs.getFloat("CARTAO"));
                  selecionamovimentousua.add(movimento);
            }
        }catch(SQLException ex){
               JOptionPane.showMessageDialog(null, "Erro: " + ex + " ao tentar selecionar o movimento!");
        }finally{
                 ConexaoFirebird.closeConnection(con, stmt, rs);
        }
        return selecionamovimentousua;
    }
    
    public List<Movimento> selecionarmovimentodia(String diahoje){
    
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Movimento> selecionamovimentodia = new ArrayList<>();
        try{
            java.sql.Date diadata = new java.sql.Date(formatbr.parse(diahoje).getTime());
            stmt = con.prepareStatement("select MV.ID_MOVIMENTO, MV.MOV_ID_PONTO,MV.HORA, MV.VENDA_AVISTA, "
                    + "MV.ENTREGA, MV.RECEBIMENTO_PRAZO,"
                    + "MV.VALE, MV.SAQUE, MV.PAGAMENTOS, MV.MOVIMENTO, MV.CARTAO from MOVIMENTO MV join "
                    + "CARTAO_PONTO CP ON  MV.MOV_ID_PONTO = CP.ID_PONTO join DATA DT on CP.PT_DATA = "
                    + "DT.ID_DATA where DT.DATA = ? order by ID_MOVIMENTO");
            stmt.setDate(1, diadata);
            rs = stmt.executeQuery();
            while(rs.next()){
                  Movimento movimento = new Movimento();
                  movimento.setIdmovimento(rs.getInt("ID_MOVIMENTO"));
                  movimento.setMovidponto(rs.getInt("MOV_ID_PONTO"));
                  movimento.setHora(rs.getTime("HORA"));
                  movimento.setVendaavista(rs.getFloat("VENDA_AVISTA"));
                  movimento.setEntrega(rs.getFloat("ENTREGA"));
                  movimento.setRecebapraso(rs.getFloat("RECEBIMENTO_PRAZO"));
                  movimento.setVale(rs.getFloat("VALE"));
                  movimento.setSaque(rs.getFloat("SAQUE"));
                  movimento.setPagamentos(rs.getFloat("PAGAMENTOS"));
                  movimento.setMovimento(rs.getFloat("MOVIMENTO"));
                  movimento.setCartao(rs.getFloat("CARTAO"));
                  selecionamovimentodia.add(movimento);
            }
        }catch(SQLException ex){
               JOptionPane.showMessageDialog(null, "Erro: " + ex + " ao tentar selecionar o movimento do dia!");
        } catch (ParseException ex) {
               JOptionPane.showMessageDialog(null, "Erro: " + ex + " ao informar o dia!");
        }finally{
                 ConexaoFirebird.closeConnection(con, stmt, rs);
        }
        return selecionamovimentodia;
    }
    
    public List<Entradas> selecionarultimocaixa(){
    
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Entradas> selecionaultimocaixa = new ArrayList<>();
        try{
            stmt = con.prepareStatement("SELECT CAIXA_SAIDA AS ULTIMOCAIXA FROM CARTAO_PONTO\n" +
                                        "WHERE PT_DATA = (SELECT MAX(PT_DATA) FROM CARTAO_PONTO)\n" + 
                                        "AND HORA_SAIDA = (SELECT MAX(HORA_SAIDA) FROM (SELECT * FROM\n" +
                                        "CARTAO_PONTO WHERE PT_DATA = (SELECT MAX(PT_DATA) FROM CARTAO_PONTO)))");
            rs = stmt.executeQuery();
            Entradas entradas = new Entradas();
            while(rs.next()){
            entradas.setValorinicialcedula(rs.getFloat("ULTIMOCAIXA"));
            selecionaultimocaixa.add(entradas);
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro: " + ex + " ao tentar selecionar último caixa!");
        }finally{
                 ConexaoFirebird.closeConnection(con, stmt, rs);
        }
        return selecionaultimocaixa;
    }
     
    public List<Entradas> selecionarultimoponto(){
       
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Entradas> selecionaultimoponto = new ArrayList<>();
        try{
            stmt = con.prepareStatement("select ID_PONTO from CARTAO_PONTO where ID_PONTO = (select max(ID_PONTO)"
                                        + "FROM CARTAO_PONTO)");
            rs = stmt.executeQuery();
            Entradas entradas = new Entradas();
            while(rs.next()){
            entradas.setIdusuario(rs.getInt("ID_PONTO"));
            selecionaultimoponto.add(entradas);
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro: " + ex + " ao tentar selecionar último ponto!");
        }finally{
            ConexaoFirebird.closeConnection(con, stmt, rs);
        }
        return selecionaultimoponto;
    }
    
    public void salvar_ponto(int iddata, int idusuario, float valorinicialnotas, float valorinicialmoedas){
    
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        
        try{
            stmt = con.prepareStatement("INSERT INTO CARTAO_PONTO(PT_DATA, PT_USUARIO,HORA_ENTRADA, CAIXA_ENTRADA)" +
                                        "VALUES(?, ?, 'time', ?,?");
            stmt.setInt(1, iddata);
            stmt.setInt(2, idusuario);
            stmt.setFloat(4, valorinicialmoedas);
            stmt.setFloat(5, valorinicialnotas);
            stmt.executeUpdate();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao tentar salvar entrada! " + ex,
                    "Bragança", JOptionPane.ERROR_MESSAGE);
        }finally{
            ConexaoFirebird.closeConnection(con, stmt);
        }
        
    
    }
    
    public void excluir_entrada(int idmovimento){
    
          Connection con = ConexaoFirebird.getConnection();
          PreparedStatement stmt = null;
          
          try{
              stmt = con.prepareStatement("delete from MOVIMENTO where ID_MOVIMENTO = ?");
              stmt.setInt(1, idmovimento);
              stmt.executeUpdate();
          }catch(SQLException ex){
              JOptionPane.showMessageDialog(null, "Erro: " + ex + " ao tentr deletar!");
          }finally{
              ConexaoFirebird.closeConnection(con, stmt);
          }
    }
}
