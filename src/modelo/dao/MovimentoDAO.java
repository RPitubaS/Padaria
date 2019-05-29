
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.bean.CaixaInicialDia;
import modelo.bean.Datas;
import modelo.bean.Entradas;
import modelo.bean.Movimento;
import modelo.bean.Pagamentos;
import modelo.bean.RecebimentoPrazo;
import modelo.bean.ReservaDeCaixa;
import modelo.bean.TotalVendas;
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
            float recebimentoprazo, float cartao, float vale, float saque, float pagamentos, float movimento,
            float caixainicialtotal){
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        try{
            java.sql.Time sdf = new java.sql.Time(formatbrh.parse(horaagora).getTime());
            stmt = con.prepareStatement("insert into MOVIMENTO(MOV_ID_PONTO, HORA, VENDA_AVISTA, ENTREGA, RECEBIMENTO_PRAZO,"
                                        + " CARTAO, VALE, SAQUE, PAGAMENTOS, MOVIMENTO, CAIXAINICIALTOTAL) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            
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
            stmt.setFloat(11, caixainicialtotal);
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
    
    public void salvar_mot_pagt(int pgtomovi, String pagamento, String empresa){
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        try{
        stmt = con.prepareStatement("insert into PAGAMENTOS(PG_ID_MOVIMENTO, PG_PAGO, EMPRESA) "
                                   + "values(?, ?, ?)");
        stmt.setInt(1, pgtomovi);
        stmt.setString(2, pagamento);
        stmt.setString(3, empresa);
        stmt.executeUpdate();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro: " + ex + " ao tentar salvar o que foi pago!","Bragança",
                    JOptionPane.ERROR_MESSAGE);
        }finally{
            ConexaoFirebird.closeConnection(con, stmt);
        }
        
    }
    
     public void salvar_recebprazo(int pgtomovi, String cliente, String competencia){
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        try{
        stmt = con.prepareStatement("insert into RECEBIMENTOPRAZO(RP_ID_MOVIMENTO, CLIENTE_PAGANTE, "
                + "COMPETENCIA) values(?, ?, ?)");
        stmt.setInt(1, pgtomovi);
        stmt.setString(2, cliente);
        stmt.setString(3, competencia);
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
     
      public void salvar_entrega(int pgtomovi, String fregues){
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        try{
        stmt = con.prepareStatement("insert into ENTREGA(ET_ID_MOVIMENTO, FREGUES) values(?, ?)");
        stmt.setInt(1, pgtomovi);
        stmt.setString(2, fregues);
        stmt.executeUpdate();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro: " + ex + " ao tentar salvar entrega!","Bragança",
                    JOptionPane.ERROR_MESSAGE);
        }finally{
            ConexaoFirebird.closeConnection(con, stmt);
        }
        
    }
      
      public void salvar_contagem(int iddata, int quantidadefregues){
      
          Connection con = ConexaoFirebird.getConnection();
          PreparedStatement stmt = null;
          try{
              stmt = con.prepareStatement("insert into CONTA_FREGUES(CONTFREGUES_ID_DATA, CONTAGEM)"
                                         + "values(?, ?)");
              stmt.setInt(1, iddata);
              stmt.setInt(2, quantidadefregues);
              stmt.executeUpdate();
          }catch(SQLException ex){
              JOptionPane.showMessageDialog(null, "Erro: " + ex + " ao tentar salvar\n"
                                            + " a quantidade de Clientes!");
          }finally{
              ConexaoFirebird.closeConnection(con, stmt);
          }
      }
      
      public void salvar_reservadecaixa(int reseriddata, int reseridponto, float notas, float moedas){
      
          Connection con = ConexaoFirebird.getConnection();
          PreparedStatement stmt = null;
          try{
              stmt = con.prepareStatement("insert into RESERVADECAIXA(RESER_ID_DATA, RESER_ID_PONTO, NOTAS, MOEDAS)"
                                          + "values(?, ?, ?, ?)");
              stmt.setInt(1, reseriddata);
              stmt.setInt(2, reseridponto);
              stmt.setFloat(3, notas);
              stmt.setFloat(4, moedas);
              stmt.executeUpdate();
          }catch(SQLException ex){
              JOptionPane.showMessageDialog(null, "Erro: \n" + ex + "\n ao salvar reserva de caixa!");
          }finally{
              ConexaoFirebird.closeConnection(con, stmt);
          }
      }
      
      public void salvar_totalvem(int ttviddata, float vendasavista, float vendasmaisentra, float somamovimento){
            Connection con = ConexaoFirebird.getConnection();
            PreparedStatement stmt = null;
            try{
                stmt = con.prepareStatement("insert into TOTALVEM(TTV_ID_DATA, VENDAS_A_VISTA, "
                                          + "VENDAS_MAIS_ENTR, SOMAMOVIMENTO) values(?, ?, ?, ?)");
                stmt.setInt(1, ttviddata);
                stmt.setFloat(2, vendasavista);
                stmt.setFloat(3, vendasmaisentra);
                stmt.setFloat(4, somamovimento);
                stmt.executeUpdate();
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(null, "Erro: \n" + ex + "\n ao salvar totais!");
            }finally{
                ConexaoFirebird.closeConnection(con, stmt);
            }
      }
      
      public void salvar_caixainiciodia(int iddata, int idponto, float caixainicialnotas, float caixainicialmoedas, 
              float caixainicialtotal){
            Connection con = ConexaoFirebird.getConnection();
            PreparedStatement stmt = null;
            try{
                stmt = con.prepareStatement("insert into CAIXA_INICIO_DIA(CAIXAINI_ID_DATA, CAIXAINI_ID_PONTO, "
                                          + "CAIXA_INICIAL_NOTAS, CAIXA_INICIAL_MOEDAS, CAIXA_INICIAL_TOTAL)"
                                          + "values(?, ?, ?, ?, ?)");
                stmt.setInt(1, iddata);
                stmt.setInt(2, idponto);
                stmt.setFloat(3, caixainicialnotas);
                stmt.setFloat(4, caixainicialmoedas);
                stmt.setFloat(5, caixainicialtotal);
                stmt.executeUpdate();
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(null, "Erro: \n" + ex + "\n ao salvar totais!");
            }finally{
                ConexaoFirebird.closeConnection(con, stmt);
            }
      
      }
     
      public void atualizar_contagem(int iddata, int quantidadedefregues){
      
           Connection con = ConexaoFirebird.getConnection();
           PreparedStatement stmt = null;
           try{
               stmt = con.prepareStatement("update CONTA_FREGUES cf set cf.CONTAGEM = ?"
                                           + "where cf.CONTFREGUES_ID_DATA = ?");
               stmt.setInt(1, quantidadedefregues);
               stmt.setInt(2, iddata);
               stmt.executeUpdate();
           }catch(SQLException ex){
               JOptionPane.showMessageDialog(null, "Erro: " + ex + " ao tentar\n"
                                             + " atualizar contagem de clientes!");
           }finally{
           }
      }
    public void atualizar_ponto(int idponto, String horasaida, float caixasaida){
        
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        try{
             java.sql.Time sdf = new java.sql.Time(formatbrh.parse(horasaida).getTime());
            stmt = con.prepareStatement("UPDATE CARTAO_PONTO a SET a.HORA_SAIDA = ?, a.CAIXA_SAIDA = ?" +
                                        "WHERE a.ID_PONTO = ?");
            stmt.setTime(1, sdf);
            stmt.setFloat(2, caixasaida);
            stmt.setInt(3, idponto);
            stmt.executeUpdate();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao tentar fechar o ponto! " + ex,
                    "Bragança", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex + " no fechamento de ponto!");
        }finally{
            ConexaoFirebird.closeConnection(con, stmt);
        }
    }
    
    public void atualizar_reservadecaixa(int reseriddata, float notas, float moedas ){
     
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        try{
            stmt = con.prepareStatement("update RESERVADECAIXA rc SET rc.NOTAS = ?, rc.MOEDAS = ? "
                                        + "where rc.RESER_ID_DATA = ?");
            stmt.setFloat(1, notas);
            stmt.setFloat(2, moedas);
            stmt.setInt(3, reseriddata);
            stmt.executeUpdate();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro: " + ex + " ao\n"
                                          + "atualizar reserva de caixa\n"
                                          + "para o dia seguinte!");
        }finally{
            ConexaoFirebird.closeConnection(con, stmt);
        }
    }
    
    public void atualizar_totalvem(int ttviddata, float vendasavista, float vendasmaisentra, float somamovimento){
       Connection con = ConexaoFirebird.getConnection();
       PreparedStatement stmt = null;
       try{
           stmt = con.prepareStatement("update TOTALVEM ttv SET ttv.VENDAS_A_VISTA = ?, "
                                     + "ttv.VENDAS_MAIS_ENTR = ?, "
                                     + "ttv.SOMAMOVIMENTO = ? where ttv.TTV_ID_DATA = ?");
           
           stmt.setFloat(1, vendasavista);
           stmt.setFloat(2, vendasmaisentra);
           stmt.setFloat(3, somamovimento);
           stmt.setInt(4, ttviddata);
           stmt.executeUpdate();
       }catch(SQLException ex){
           JOptionPane.showMessageDialog(null, "Erro: " + ex + "\n ao atualizar totais!");
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
    
    public List<Pagamentos> selecionamotivopagto(int pgidmovimento){
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Pagamentos> selecionarmotivopagto = new ArrayList<>();
        try{
            stmt = con.prepareStatement("select PG_PAGO, EMPRESA from PAGAMENTOS where PG_ID_MOVIMENTO = ?");
            stmt.setInt(1, pgidmovimento);
            rs = stmt.executeQuery();
            Pagamentos pagamentos = new Pagamentos();
            while(rs.next()){
                  pagamentos.setMotivopago(rs.getString("PG_PAGO"));
                  pagamentos.setEmpresa(rs.getString("EMPRESA"));
                  selecionarmotivopagto.add(pagamentos);
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro: " + ex + " ao buscar o que foi pago!", "Bragança",
                    JOptionPane.ERROR_MESSAGE);
        }finally{
            return selecionarmotivopagto;
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
    
    public List<RecebimentoPrazo> selecionaclientepagamento(int pgidmovimento){
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<RecebimentoPrazo>selecionarclientepagamento = new ArrayList<>();
        try{
            stmt = con.prepareStatement("select CLIENTE_PAGANTE, COMPETENCIA from RECEBIMENTOPRAZO where RP_ID_MOVIMENTO = ?");
            stmt.setInt(1, pgidmovimento);
            rs = stmt.executeQuery();
            RecebimentoPrazo recebimentoprazo = new RecebimentoPrazo();
            while(rs.next()){
                  recebimentoprazo.setClientepagante(rs.getString("CLIENTE_PAGANTE"));
                  recebimentoprazo.setCompetencia(rs.getString("COMPETENCIA"));
                  selecionarclientepagamento.add(recebimentoprazo);
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro: " + ex + "\n ao buscar o nome do cliente!", "Bragança",
                    JOptionPane.ERROR_MESSAGE);
        }finally{
            return selecionarclientepagamento;
        }
    }
    
    public String selecionaclienteentrega(int pgidmovimento){
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String clienteentrega = null;
        try{
            stmt = con.prepareStatement("select FREGUES from ENTREGA where ET_ID_MOVIMENTO = ?");
            stmt.setInt(1, pgidmovimento);
            rs = stmt.executeQuery();
            while(rs.next()){
                  clienteentrega = rs.getString("FREGUES");
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro: " + ex + "\n ao buscar o nome do cliente!", "Bragança",
                    JOptionPane.ERROR_MESSAGE);
        }finally{
            return clienteentrega;
        }
    }
    
    public List<Entradas> selecionarentrada(String agora, int usuario){
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Entradas> selecionaentrada = new ArrayList<>();
        try{
            java.sql.Date sdf = new java.sql.Date(formatbr.parse(agora).getTime());
            stmt = con.prepareStatement("SELECT CP.ID_PONTO, CP.CAIXA_ENTRADA_NOTAS,\n" + 
                                        "CP.CAIXA_ENTRADA_MOEDAS,DT.ID_DATA, US.USUARIO FROM DATA DT JOIN CARTAO_PONTO CP\n" + 
                                        "ON CP.PT_DATA = DT.ID_DATA JOIN USUARIOS US ON US.ID = CP.PT_USUARIO\n" +
                                        "WHERE DT.DATA = ? AND US.ID = ? AND CP.HORA_SAIDA is null");
            stmt.setDate(1, sdf);
            stmt.setInt(2, usuario);
            rs = stmt.executeQuery();
            Entradas entradas = new Entradas();
              while(rs.next()){
                    entradas.setIddata(rs.getInt("ID_DATA"));
                    entradas.setIdponto(rs.getInt("ID_PONTO"));
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
    
    public Time selecionarmaxhoramovimento(int movidponto){
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Time selecionamaxhoramovimento = null;
        try{
        stmt = con.prepareStatement("select max(HORA) AS ULTIMA_HORA from MOVIMENTO \n" +
                             "where MOV_ID_PONTO = ?");
        stmt.setInt(1, movidponto);
        rs = stmt.executeQuery();
        while(rs.next()){
          selecionamaxhoramovimento = rs.getTime("ULTIMA_HORA");
        }
        }catch(SQLException ex){
               JOptionPane.showMessageDialog(null, "Erro: " + ex + " ao selecionar a última hora!");
        }finally{
               ConexaoFirebird.closeConnection(con, stmt, rs);
        }
        return selecionamaxhoramovimento;
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
                                        "CP.CAIXA_ENTRADA_MOEDAS,DT.ID_DATA, DT.DATA, US.ID, US.USUARIO FROM DATA DT JOIN CARTAO_PONTO CP\n" + 
                                        "ON CP.PT_DATA = DT.ID_DATA JOIN USUARIOS US ON US.ID = CP.PT_USUARIO\n" +
                                        "WHERE CP.HORA_SAIDA is null");
            rs = stmt.executeQuery();
            Entradas entradas = new Entradas();
            while(rs.next()){
                  entradas.setIdponto(rs.getInt("ID_PONTO"));
                  entradas.setIdusuario(rs.getInt("ID"));
                  entradas.setIddata(rs.getInt("ID_DATA"));
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
            stmt = con.prepareStatement("select ID_PONTO, PT_DATA from CARTAO_PONTO where ID_PONTO = (select max(ID_PONTO)"
                                        + "FROM CARTAO_PONTO)");
            rs = stmt.executeQuery();
            Entradas entradas = new Entradas();
            while(rs.next()){
            entradas.setIdusuario(rs.getInt("ID_PONTO"));
            entradas.setIddata(rs.getInt("PT_DATA"));
            selecionaultimoponto.add(entradas);
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro: " + ex + " ao tentar selecionar último ponto!");
        }finally{
            ConexaoFirebird.closeConnection(con, stmt, rs);
        }
        return selecionaultimoponto;
    }
    
    public int selecionacontagem(int iddata){
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int selecionacontfregues = 0;
         
        try{
            //java.sql.Date diadata = new java.sql.Date(formatbr.parse(agora).getTime());
            stmt = con.prepareStatement("select ctf.CONTAGEM from CONTA_FREGUES ctf join DATA dt on"
                                       + " ctf.CONTFREGUES_ID_DATA = dt.ID_DATA where dt.ID_DATA"
                                       + " = ?");
            stmt.setInt(1, iddata);
            rs = stmt.executeQuery();
            while(rs.next()){
               selecionacontfregues = rs.getInt("CONTAGEM");
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro: " + ex + "\n ao selecionar a contagem do dia!");
        }finally{
            ConexaoFirebird.closeConnection(con, stmt, rs);
        }
        return selecionacontfregues;
    }
    
    public int selecionacontagemtotal(int iddata){
       Connection con = ConexaoFirebird.getConnection();
       PreparedStatement stmt = null;
       ResultSet rs = null;
       int selecionatotalfregues = 0;
       
       try{
           stmt = con.prepareStatement("select sum(CONTAGEM) as TOTALFREGUES from CONTA_FREGUES\n" +
                                       "where CONTFREGUES_ID_DATA = ?");
           stmt.setInt(1, iddata);
           rs = stmt.executeQuery();
           while(rs.next()){
              selecionatotalfregues = rs.getInt("TOTALFREGUES");
           }
       }catch(SQLException ex){
           JOptionPane.showMessageDialog(null, "Erro: " + ex + "\n ao selecionar a contagem total do dia!");
       }finally{
           ConexaoFirebird.closeConnection(con, stmt, rs);
       }
       return selecionatotalfregues;
    }
    
     public int selecionaidcontagem(int iddata){
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int idcontagem = 0;
        try{
            stmt = con.prepareStatement("select CONTFREGUES_ID_DATA from CONTA_FREGUES " +
                                        "where CONTFREGUES_ID_DATA = ?");
            stmt.setInt(1, iddata);
            rs = stmt.executeQuery();
            while(rs.next()){
               idcontagem = rs.getInt("CONTFREGUES_ID_DATA");
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro: " + ex + "\n ao selecionar o id da contagem\n total do dia!");
        }finally{
            ConexaoFirebird.closeConnection(con, stmt, rs);
        }
        return idcontagem;
     }
     
    public int selecionariddata(int movidponto){
         Connection con = ConexaoFirebird.getConnection();
         PreparedStatement stmt = null;
         ResultSet rs = null;
         int selecionaiddata = 0;
         
         try{
             stmt = con.prepareStatement("select dt.ID_DATA from DATA dt join CARTAO_PONTO cp ON dt.ID_DATA"
                     + "= cp.PT_DATA join MOVIMENTO mv ON mv.MOV_ID_PONTO = cp.ID_PONTO where "
                     + "mv.MOV_ID_PONTO = ?");
             stmt.setInt(1, movidponto);
             rs = stmt.executeQuery();
             while(rs.next()){
                 selecionaiddata = rs.getInt("ID_DATA");
             }
         }catch(SQLException ex){
             JOptionPane.showMessageDialog(null, "Erro: " + ex + " ao selecionar a data\n"
                     + "para quantidade de vendas!");
         }finally{
             ConexaoFirebird.closeConnection(con, stmt, rs);
         }
         return selecionaiddata;
    }
    
    public List<ReservaDeCaixa> selecionarultimoreservadecaixa(){
    
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ReservaDeCaixa> selecionaultimoreservadecaixa = new ArrayList<>();
        try{
            stmt = con.prepareStatement("select rc.ID_RESERVA, rc.RESER_ID_PONTO, rc.NOTAS, rc.MOEDAS,"
                                        + " dt.DATA from RESERVADECAIXA rc join CARTAO_PONTO cp on "
                                        + "rc.RESER_ID_PONTO = cp.ID_PONTO "
                                        + "join DATA dt on dt.ID_DATA = cp.PT_DATA  "
                                        + "where ID_RESERVA = (select "
                                        + "max(ID_RESERVA) from RESERVADECAIXA)");
            rs = stmt.executeQuery();
            ReservaDeCaixa reservadecaixa = new ReservaDeCaixa();
            while(rs.next()){
                  reservadecaixa.setIdreserva(rs.getInt("ID_RESERVA"));
                  reservadecaixa.setReseridponto(rs.getInt("RESER_ID_PONTO"));
                  reservadecaixa.setNotas(rs.getFloat("NOTAS"));
                  reservadecaixa.setMoedas(rs.getFloat("MOEDAS"));
                  reservadecaixa.setData(rs.getDate("DATA"));
                  selecionaultimoreservadecaixa.add(reservadecaixa);
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro: " + ex + "\n ao selecionar última reserva de caixa!");
        }finally{
            ConexaoFirebird.closeConnection(con, stmt, rs);
        }
        return selecionaultimoreservadecaixa;
    }
    
    public List<ReservaDeCaixa> selecionarreservadecaixa(int iddata){
    
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ReservaDeCaixa> selecionareservadecaixa = new ArrayList<>();
        try{
            stmt = con.prepareStatement("select rc.ID_RESERVA, rc.RESER_ID_PONTO, rc.NOTAS, rc.MOEDAS, dt.DATA"
                    + " from RESERVADECAIXA rc join CARTAO_PONTO cp on rc.RESER_ID_PONTO = cp.ID_PONTO "
                    + " join DATA dt on dt.ID_DATA = cp.PT_DATA "
                    + "where dt.ID_DATA = ?");
            stmt.setInt(1, iddata);
            rs = stmt.executeQuery();
            ReservaDeCaixa reservadecaixa = new ReservaDeCaixa();
            while(rs.next()){
                  reservadecaixa.setIdreserva(rs.getInt("ID_RESERVA"));
                  reservadecaixa.setReseridponto(rs.getInt("RESER_ID_PONTO"));
                  reservadecaixa.setNotas(rs.getFloat("NOTAS"));
                  reservadecaixa.setMoedas(rs.getFloat("MOEDAS"));
                  reservadecaixa.setData(rs.getDate("DATA"));
                  selecionareservadecaixa.add(reservadecaixa);
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro: " + ex + "\n ao selecionar última reserva de caixa!");
        }finally{
            ConexaoFirebird.closeConnection(con, stmt, rs);
        }
        return selecionareservadecaixa;
    }
    
    public List<CaixaInicialDia> caixainicialdia(int iddata){
         Connection con = ConexaoFirebird.getConnection();
         PreparedStatement stmt = null;
         ResultSet rs = null;
         List<CaixaInicialDia> caixainiciodia = new ArrayList<>();         
         try{
             stmt = con.prepareStatement("select CAIXA_ENTRADA_NOTAS, CAIXA_ENTRADA_MOEDAS from CARTAO_PONTO\n" +
                                         "where ID_PONTO = (select min(ID_PONTO)from CARTAO_PONTO\n" +
                                         "where PT_DATA = ?)");
             stmt.setInt(1, iddata);
             rs = stmt.executeQuery();
             CaixaInicialDia caixainicialdia = new CaixaInicialDia();
             while(rs.next()){
                  caixainicialdia.setNotas(rs.getFloat("CAIXA_ENTRADA_NOTAS"));
                  caixainicialdia.setMoedas(rs.getFloat("CAIXA_ENTRADA_MOEDAS"));
                  caixainiciodia.add(caixainicialdia);
             }
         }catch(SQLException ex){
             JOptionPane.showMessageDialog(null, "Erro: " + ex + "\n ao selecionar o caixa inicial!");
         }finally{
             ConexaoFirebird.closeConnection(con, stmt, rs);
         }
         return caixainiciodia;
    }
    
    public List<TotalVendas> selecinatotalvem(int iddata){
         
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<TotalVendas> selecionartotalvem  = new ArrayList<>();
        try{
            stmt = con.prepareStatement("select TTV_ID_DATA, VENDAS_A_VISTA, VENDAS_MAIS_ENTR, "
                                      + "SOMAMOVIMENTO from TOTALVEM\n"
                                      + "where TTV_ID_DATA = ?");
            stmt.setInt(1, iddata);
            rs = stmt.executeQuery();
            TotalVendas totalvendas = new TotalVendas();
            while(rs.next()){
                 totalvendas.setIddata(rs.getInt("TTV_ID_DATA"));
                 totalvendas.setVendasavista(rs.getFloat("VENDAS_A_VISTA"));
                 totalvendas.setVendasmaisentregas(rs.getFloat("VENDAS_MAIS_ENTR"));
                 totalvendas.setSomamovimento(rs.getFloat("SOMAMOVIMENTO"));
                 selecionartotalvem.add(totalvendas);
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro: " + ex + "\n ao selecionar o total de vendas!");
        }finally{
            ConexaoFirebird.closeConnection(con, stmt, rs);
        }
        return selecionartotalvem;
    }
    
    public List<CaixaInicialDia> caixainiciodia(int iddata){
         Connection con = ConexaoFirebird.getConnection();
         PreparedStatement stmt = null;
         ResultSet rs = null;
         List<CaixaInicialDia> caixainiciodia = new ArrayList<>();         
         try{
             stmt = con.prepareStatement("select CAIXAINI_ID_DATA, CAIXAINI_ID_PONTO, CAIXA_INICIAL_NOTAS,\n"
                                       + "CAIXA_INICIAL_MOEDAS, CAIXA_INICIAL_TOTAL from CAIXA_INICIO_DIA\n" 
                                       + "where CAIXAINI_ID_DATA = ?)");
                                         
             stmt.setInt(1, iddata);
             rs = stmt.executeQuery();
             CaixaInicialDia caixainicialdia = new CaixaInicialDia();
             while(rs.next()){
                  caixainicialdia.setIddata(rs.getInt("CAIXAINI_ID_DATA"));
                  caixainicialdia.setIdponto(rs.getInt("CAIXAINI_ID_PONTO"));
                  caixainicialdia.setNotas(rs.getFloat("CAIXA_ENTRADA_NOTAS"));
                  caixainicialdia.setMoedas(rs.getFloat("CAIXA_ENTRADA_MOEDAS"));
                  caixainicialdia.setTotal(rs.getFloat("CAIXA_INICIAL_TOTAL"));
                  caixainiciodia.add(caixainicialdia);
             }
         }catch(SQLException ex){
             JOptionPane.showMessageDialog(null, "Erro: " + ex + "\n ao selecionar o caixa inicial do dia!");
         }finally{
             ConexaoFirebird.closeConnection(con, stmt, rs);
         }
         return caixainiciodia;
    }
    
    public void salvar_ponto(int iddata, int idusuario, float valorinicialnotas, float valorinicialmoedas){
    
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        
        try{
            stmt = con.prepareStatement("INSERT INTO CARTAO_PONTO(PT_DATA, PT_USUARIO,HORA_ENTRADA, CAIXA_ENTRADA)" +
                                        "VALUES(?, ?, 'time', ?, ?");
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
              JOptionPane.showMessageDialog(null, "Erro: \n" + ex + "\n ao tentar deletar movimento!");
          }finally{
              ConexaoFirebird.closeConnection(con, stmt);
          }
    }
    
    public void excluir_reservadecaixa(int idreserva){
    
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        
        try{
            stmt = con.prepareStatement("delete from RESERVADECAIXA where ID_RESERVA = ?");
            stmt.setInt(1, idreserva);
            stmt.executeUpdate();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro :" + ex + "\n ao tentar deletar reserva de caixa!");
        }finally{
            ConexaoFirebird.closeConnection(con, stmt);
        }
    }
}
