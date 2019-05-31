/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.Float.parseFloat;
import static java.lang.Thread.sleep;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import modelo.bean.Entradas;
import modelo.bean.Movimento;
import modelo.bean.Pagamentos;
import modelo.bean.RecebimentoPrazo;
import modelo.bean.TotalVendas;
import modelo.bean.Vales;
import modelo.dao.MovimentoDAO;
import org.firebirdsql.management.FBBackupManager;
import produzconexao.RefazerConexao;
import util.ConfigDB;
import util.FecharCaixa;
import util.GerenciadordeJanelas;
import util.GuardarUrl;
import util.SelecionandoReservaDeCaixa;
import util.SelecionarReservaDeCaixa;
import util.SoNumeros;
import static view.frmMovimentodia.tblMovimentodia;
import static view.frmPrincipal.btnAdministrador;
import static view.frmPrincipal.btnCaixa;
import static view.frmPrincipal.btnEntrar;
import static view.frmPrincipal.btnFecharAdmin;
import static view.frmPrincipal.btnLogin;
import static view.frmPrincipal.mnCaixa;
import static view.frmPrincipal.mnEntrar;
import static view.frmPrincipal.mnFecharEntrar;
import static view.frmPrincipal.mnFecharNovousuario;
import static view.frmPrincipal.mnFecharcaixa;
import static view.frmPrincipal.mnNovousuario;
import static view.frmPrincipal.dtpDescktop;
import static view.frmPrincipal.mnFecharRelatorios;
import static view.frmPrincipal.mnRelatorios;
import static view.frmPrincipal.btnRelatorio;


/**
 *
 * @author Pituba
 */
public class frmMovimento extends javax.swing.JInternalFrame {
    private frmEntrar veiodoentrar;
    GerenciadordeJanelas gerenciadordejanelas;
    private static frmMovimento frmmovimento; 
    int idponto, iddata;
    int movidponto = 0, idmovimento = 0, contafregues = 0, iddatacontafregues, iddatatotalvem = 0;
    String horaagora, datahoje, motivopagamento, clienteparaentrega, clienterecebprazo, funcionariovale,
           motivosaque, competencia, empresa;
    float vendaavista, entrega, recebimentoprazo, cartao, vale, saque, pagamentos, movimento, 
            totalvendasavista, vendasmaisentregas, somamovimento, encerrarmovimento;
    Time horasaida;
    
    DecimalFormat df = new DecimalFormat();
    List<Movimento> selecionamovimentousuario = new ArrayList<>();
    public static frmMovimento getInstancia(){
          if(frmmovimento == null){
             frmmovimento = new frmMovimento();
          }
        return frmmovimento;
    }
    
    public void setPosicao(){
    
         Dimension dimensao = dtpDescktop.getSize();
           this.setSize(dimensao);
               this.toFront();               
    }
    
    public frmMovimento() {
        initComponents();
        currentTime();
        btnExcluir.setEnabled(false);
        rbAvista.setSelected(true);
        ftxtValor.requestFocus();
        btnCancelarreserva.setEnabled(false);
        btnFecharMovimento.setVisible(false);
        ftxtNotasreserva.setDocument(new SoNumeros());
        ftxtMoedasreserva.setDocument(new SoNumeros());
        ftxtValor.setDocument(new SoNumeros());
        JTableHeader tabela = tblMovimento.getTableHeader();
        tabela.setFont(new Font("Tahoma", Font.BOLD,12)); 
        ((DefaultTableCellRenderer)tabela.getDefaultRenderer()).setHorizontalAlignment(JLabel.RIGHT);
     
        
    }
    
   
    void lertabela() {
        int contador = 0;
        DefaultTableModel modelo = (DefaultTableModel) tblMovimento.getModel();
        SimpleDateFormat formatbr = new SimpleDateFormat("HH:mm.ss");
        DecimalFormat dfa = new DecimalFormat();
        DecimalFormat dfc = new DecimalFormat();
        df.applyPattern("##,##0.00");
        dfa.applyPattern("-##,##0.00");
        dfc.applyPattern("*##,##0.00");
        modelo.setNumRows(0);
        MovimentoDAO movdao = new MovimentoDAO();
        selecionamovimentousuario = movdao.selecionarmovimentousua(movidponto);
        for (Movimento m : selecionamovimentousuario) {
           //if(m.getMovimento() != 0 || m.getEntrega() != 0 || m.getRecebapraso() != 0 || m.getVale() != 0
                   //|| m.getSaque() != 0 || m.getPagamentos() != 0 || m.getMovimento() != 0){
            if(m.getMovimento() < 0){
                float absoluto;
                absoluto = - m.getMovimento();
                modelo.addRow(new Object[]{
                m.getHora(),
                df.format(m.getVendaavista()),
                df.format(m.getEntrega()),
                df.format(m.getRecebapraso()),
                df.format(m.getVale()),
                df.format(m.getSaque()),
                df.format(m.getPagamentos()),
                dfa.format(absoluto),
                df.format(m.getCartao()),
                });
                
                   cornalinha();
            }else{
               modelo.addRow(new Object[]{
                m.getHora(),
                df.format(m.getVendaavista()),
                df.format(m.getEntrega()),
                df.format(m.getRecebapraso()),
                df.format(m.getVale()),
                df.format(m.getSaque()),
                df.format(m.getPagamentos()),
                df.format(m.getMovimento()),
                df.format(m.getCartao()),
            });  
               
                   cornalinha(); 
                }
           
            horasaida = m.getHora();
            movidponto = m.getMovidponto();
          //}
        } 
     
        Object[] dados1 = {"Caixa inicial:", "0,00", "0,00", "0,00", "0,00", "0,00", "0,00", "0,00",
                           "0,00"},
                 dados = {"Total:", "", "", "", "", "", "", "", ""};
        modelo.insertRow(modelo.getRowCount(), dados1);
        modelo.insertRow(modelo.getRowCount(), dados);

        String pd = txtCaixainicial.getText().substring(8, txtCaixainicial.getText().length());
        modelo.setValueAt(pd, modelo.getRowCount() - 2, 7);
        
                            float total = 0, vendaavista = 0, entrega = 0,recebimentoprazo = 0, cartao = 0,
                                    vale = 0, saque = 0, pagamentos = 0, movimento = 0;
                            for (int i = 0; i < modelo.getRowCount() - 1; i++) {
                                vendaavista += Float.parseFloat(modelo.getValueAt(i, 1).toString()
                                    .replaceAll("\\.", "").replaceAll(",", "."));
                                entrega += Float.parseFloat(modelo.getValueAt(i, 2).toString()
                                    .replaceAll("\\.", "").replaceAll(",", "."));
                                recebimentoprazo += Float.parseFloat(modelo.getValueAt(i, 3).toString()
                                    .replaceAll("\\.", "").replaceAll(",", "."));
                                vale += Float.parseFloat(modelo.getValueAt(i, 4).toString()
                                    .replaceAll("\\.", "").replaceAll(",", "."));
                                saque += Float.parseFloat(modelo.getValueAt(i, 5).toString()
                                    .replaceAll("\\.", "").replaceAll(",", "."));
                                pagamentos += Float.parseFloat(modelo.getValueAt(i, 6).toString()
                                    .replaceAll("\\.", "").replaceAll(",", "."));
                                movimento += Float.parseFloat(modelo.getValueAt(i, 7).toString()
                                    .replaceAll("\\.", "").replaceAll(",", "."));
                                cartao += Float.parseFloat(modelo.getValueAt(i, 8).toString()
                                    .replaceAll("\\.", "").replaceAll(",", "."));
                                
                                String va = df.format(vendaavista);
                                String et = df.format(entrega);
                                String rp = df.format(recebimentoprazo);
                                String vl = df.format(vale);
                                String sq = df.format(saque);
                                String pg = df.format(pagamentos);
                                String mv = df.format(movimento);
                                String ct = dfc.format(cartao);
                                
                                modelo.setValueAt(va, modelo.getRowCount() - 1, 1);
                                modelo.setValueAt(et, modelo.getRowCount() - 1, 2);
                                modelo.setValueAt(rp, modelo.getRowCount() - 1, 3);
                                modelo.setValueAt(vl, modelo.getRowCount() - 1, 4);
                                modelo.setValueAt(sq, modelo.getRowCount() - 1, 5);
                                modelo.setValueAt(pg, modelo.getRowCount() - 1, 6);
                                modelo.setValueAt(mv, modelo.getRowCount() - 1, 7); 
                                modelo.setValueAt(ct, modelo.getRowCount() - 1, 8);
                                encerrarmovimento = movimento;
                                
                            }
                        
                           RefazerConexao refc11 = new RefazerConexao();
                           refc11.refazerconexao();
                           MovimentoDAO movdao31 = new MovimentoDAO();
                           iddata = movdao31.selecionariddata(movidponto);
                           
                      if(iddata >= 1){
                           RefazerConexao refc12 = new RefazerConexao();
                           refc12.refazerconexao();
                           MovimentoDAO movdao32 = new MovimentoDAO();
                           txtVendas.setText("Vendas:  " + movdao32.selecionacontagem(iddata));
                        }    
                       }
    
    public void cornalinha(){
              
             DefaultTableCellRenderer rightrenderer = new DefaultTableCellRenderer();
             DefaultTableCellRenderer rightrenderer1 = new DefaultTableCellRenderer();
             
             tblMovimento.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){ 
                     @Override
                     public Component getTableCellRendererComponent(JTable table,
                             Object value, boolean isSelected, boolean hasfocus, int row, int column){
                             JLabel label = (JLabel) super.getTableCellRendererComponent(table, value,
                                     isSelected, hasfocus, row, column);
                             
                              Color c = Color.BLACK;
                              
                              Object texto = table.getValueAt(row, 7);
                              Object textocar = table.getValueAt(row, 8);
                              if(texto != null && 0 > Float.parseFloat((texto.toString().
                                                        replaceAll("\\.", "").replaceAll(",", ".")))){
                                 c = Color.RED;
                              }
                             
                              if(textocar != null && 0 < Float.parseFloat((textocar.toString().
                                 replaceAll("\\*", "").replaceAll("\\.", "").replaceAll(",", ".")))
                                      && !"*".equals(textocar.toString().substring(0, 1))){                                 
                                  c = Color.BLUE; 
                                  
                              }
                                                            
                                 label.setForeground(c);
                                 
                                 rightrenderer.setForeground(c);
                                
                                 rightrenderer.setHorizontalAlignment(JLabel.RIGHT);
                                 rightrenderer1.setHorizontalAlignment(JLabel.RIGHT);
                                 rightrenderer1.setForeground(Color.BLUE);
                                 tblMovimento.getColumnModel().getColumn(1).setCellRenderer(rightrenderer);
                                 tblMovimento.getColumnModel().getColumn(2).setCellRenderer(rightrenderer);
                                 tblMovimento.getColumnModel().getColumn(3).setCellRenderer(rightrenderer);
                                 tblMovimento.getColumnModel().getColumn(4).setCellRenderer(rightrenderer);
                                 tblMovimento.getColumnModel().getColumn(5).setCellRenderer(rightrenderer);
                                 tblMovimento.getColumnModel().getColumn(6).setCellRenderer(rightrenderer);
                                 tblMovimento.getColumnModel().getColumn(7).setCellRenderer(rightrenderer);
                                 tblMovimento.getColumnModel().getColumn(8).setCellRenderer(rightrenderer1);
                                                                   
                                  return label;
                                   
                              }                            
                       }); 
    }
    
    public void excluir(){
        
        String valor;
        
        if(Double.parseDouble((tblMovimento.getValueAt(tblMovimento.getSelectedRow(), 7)).toString().replaceAll(",", ".")) != 0.0){
          valor = (String) tblMovimento.getValueAt(tblMovimento.getSelectedRow(), 7);
        }else{
          valor = (String) tblMovimento.getValueAt(tblMovimento.getSelectedRow(), 8);
        }
   
        if(tblMovimento.getSelectedRow()!= -1 && tblMovimento.getSelectedRow()!= tblMovimento.getRowCount() - 2){
           if(tblMovimento.getSelectedRow()!= -1 && tblMovimento.getSelectedRow()!= (tblMovimento.getRowCount() - 1)){
            int resultconfirm = JOptionPane.showConfirmDialog(null, "Confirma a exclusão de R$ "
                + valor + " do seu arguivo de origem?",
                "Bragança", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(resultconfirm == 0){      
                   RefazerConexao refc11 = new RefazerConexao();
                   refc11.refazerconexao();
                   MovimentoDAO movdao31 = new MovimentoDAO();
                   iddata = movdao31.selecionariddata(movidponto);
                            
                   RefazerConexao refc12 = new RefazerConexao();
                   refc12.refazerconexao();
                   MovimentoDAO movdao32 = new MovimentoDAO();
                   contafregues = movdao32.selecionacontagem(iddata);
                   
                   RefazerConexao rfc = new RefazerConexao();
                   rfc.refazerconexao();
                   MovimentoDAO movdao = new MovimentoDAO();
                   movdao.excluir_entrada(selecionamovimentousuario.get(tblMovimento.getSelectedRow()).getIdmovimento());
                   RefazerConexao rfc1 = new RefazerConexao();
                   
                   
                   List<TotalVendas> selecionatotalvendas = new ArrayList<>();
                            RefazerConexao rfctotalvem = new RefazerConexao();
                            rfctotalvem.refazerconexao();
                            MovimentoDAO movdaottv = new MovimentoDAO();
                            selecionatotalvendas = movdaottv.selecinatotalvem(iddata);
  
                            for(TotalVendas totalvendas : selecionatotalvendas){
                                totalvendasavista = totalvendas.getVendasavista();
                                vendasmaisentregas = totalvendas.getVendasmaisentregas();
                                somamovimento = totalvendas.getSomamovimento();
                            }
                            
                   if(selecionamovimentousuario.get(tblMovimento.getSelectedRow()).getVendaavista() > 0){
                      totalvendasavista -= selecionamovimentousuario.get(tblMovimento.getSelectedRow()).getVendaavista();
                   }
                   
                   if(selecionamovimentousuario.get(tblMovimento.getSelectedRow()).getVendaavista() > 0){
                      vendasmaisentregas -= selecionamovimentousuario.get(tblMovimento.getSelectedRow()).getVendaavista();
                   }
                   
                   if(selecionamovimentousuario.get(tblMovimento.getSelectedRow()).getEntrega() > 0){
                      vendasmaisentregas -= selecionamovimentousuario.get(tblMovimento.getSelectedRow()).getEntrega();
                   }
                   if(selecionamovimentousuario.get(tblMovimento.getSelectedRow()).getCartao() == 0 &&
                       selecionamovimentousuario.get(tblMovimento.getSelectedRow()).getRecebapraso() == 0){
                          somamovimento -= selecionamovimentousuario.get(tblMovimento.getSelectedRow()).getMovimento();
                   }else{
                      somamovimento -= selecionamovimentousuario.get(tblMovimento.getSelectedRow()).getCartao();
                   }
                   
                   RefazerConexao rfctotal = new RefazerConexao();
                   rfctotal.refazerconexao();
                   MovimentoDAO movdaototal = new MovimentoDAO();
                   movdaototal.atualizar_totalvem(iddata, totalvendasavista, vendasmaisentregas, somamovimento);
                  
                   if(selecionamovimentousuario.get(tblMovimento.getSelectedRow()).getVendaavista() != 0 ||
                           selecionamovimentousuario.get(tblMovimento.getSelectedRow()).getEntrega() > 0){
                   RefazerConexao refc13 = new RefazerConexao();
                   refc13.refazerconexao();
                   MovimentoDAO movdao33 = new MovimentoDAO();
                   movdao33.atualizar_contagem(iddata, contafregues - 1);
                   
                   RefazerConexao refc14 = new RefazerConexao();
                   refc14.refazerconexao();
                   MovimentoDAO movdao34 = new MovimentoDAO();
                   txtVendas.setText("Vendas:  " + movdao34.selecionacontagem(iddata));
                   }
                   rfc1.refazerconexao();
                   lertabela();
                   
                   }else{
                   tblMovimento.setSelectionMode(0);
            }
            ftxtValor.requestFocus();
            btnExcluir.setEnabled(false);
           }
        }

    }

    private void currentTime() {

        Thread clock = new Thread() {

            @Override
            public void run() {
                for (;;) {
                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Brazil/East"));
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    int month = (cal.get(Calendar.MONTH))+1;
                    int year = cal.get(Calendar.YEAR);
                    int second = cal.get(Calendar.SECOND);
                    int minute = cal.get(Calendar.MINUTE);
                    int hour = cal.get(Calendar.HOUR_OF_DAY);
                    txtData.setText(String.format("Data: %02d/%02d/%02d", day, month, year));
                    txtRelogio.setText(String.format("%02d:%02d:%02d hs", hour, minute, second));
                    horaagora = String.format("%02d:%02d:%02d", hour, minute, second);
                    datahoje = String.format("%02d.%02d.%02d", day, month, year);
                    try {
                        sleep(1000);
                    } catch (InterruptedException ex) {
                        JOptionPane.showMessageDialog(null, "ERRO: " + ex, "Bragança",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

            }

        };
        clock.start();
    }     
    
    public void alinhamentocelula(){
        DefaultTableCellRenderer rightrenderer = new DefaultTableCellRenderer();
        rightrenderer.setHorizontalAlignment(JLabel.RIGHT);
         DefaultTableCellRenderer rightrenderer1 = new DefaultTableCellRenderer();
         rightrenderer1.setHorizontalAlignment(JLabel.RIGHT);
        rightrenderer1.setForeground(Color.BLUE);
        tblMovimento.getColumnModel().getColumn(1).setCellRenderer(rightrenderer);
        tblMovimento.getColumnModel().getColumn(2).setCellRenderer(rightrenderer);
        tblMovimento.getColumnModel().getColumn(3).setCellRenderer(rightrenderer);
        tblMovimento.getColumnModel().getColumn(4).setCellRenderer(rightrenderer);
        tblMovimento.getColumnModel().getColumn(5).setCellRenderer(rightrenderer);
        tblMovimento.getColumnModel().getColumn(6).setCellRenderer(rightrenderer);
        tblMovimento.getColumnModel().getColumn(7).setCellRenderer(rightrenderer);
        tblMovimento.getColumnModel().getColumn(8).setCellRenderer(rightrenderer1);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        txtMoedasinicio = new javax.swing.JTextField();
        txtData = new javax.swing.JTextField();
        txtRelogio = new javax.swing.JTextField();
        txtAtendentecaixa = new javax.swing.JTextField();
        txtCaixainicial = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtNotasinicio = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMovimento = new javax.swing.JTable();
        txtVendas = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        rbAvista = new javax.swing.JRadioButton();
        rbAprazo = new javax.swing.JRadioButton();
        rbCartao = new javax.swing.JRadioButton();
        rbPagamentos = new javax.swing.JRadioButton();
        lblValor = new javax.swing.JLabel();
        lblFormadepagamento = new javax.swing.JLabel();
        btnExcluir = new javax.swing.JButton();
        ftxtValor = new javax.swing.JFormattedTextField();
        rbEntrega = new javax.swing.JRadioButton();
        rbVale = new javax.swing.JRadioButton();
        rbSaque = new javax.swing.JRadioButton();
        btnFecharcaixa = new javax.swing.JButton();
        btnFecharMovimento = new javax.swing.JButton();
        rdDiferencaNeg = new javax.swing.JRadioButton();
        rdDiferencaPos = new javax.swing.JRadioButton();
        pnlCaixareservado = new javax.swing.JPanel();
        lblNotas = new javax.swing.JLabel();
        lblMoedas = new javax.swing.JLabel();
        btnConfirmareserva = new javax.swing.JButton();
        btnCorrigereserva = new javax.swing.JButton();
        ftxtNotasreserva = new javax.swing.JFormattedTextField();
        ftxtMoedasreserva = new javax.swing.JFormattedTextField();
        lblReservadoem = new javax.swing.JLabel();
        btnReservarcaixa = new javax.swing.JButton();
        btnCancelarreserva = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();

        setClosable(true);
        setPreferredSize(new java.awt.Dimension(1340, 793));
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameActivated(evt);
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));

        txtMoedasinicio.setBackground(new java.awt.Color(255, 153, 0));
        txtMoedasinicio.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtMoedasinicio.setText("Moedas: ");
        txtMoedasinicio.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtMoedasinicio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMoedasinicioKeyPressed(evt);
            }
        });

        txtData.setEditable(false);
        txtData.setBackground(new java.awt.Color(255, 153, 0));
        txtData.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtData.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtData.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataActionPerformed(evt);
            }
        });
        txtData.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDataKeyPressed(evt);
            }
        });

        txtRelogio.setEditable(false);
        txtRelogio.setBackground(new java.awt.Color(255, 153, 0));
        txtRelogio.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtRelogio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtRelogio.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtRelogio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRelogioKeyPressed(evt);
            }
        });

        txtAtendentecaixa.setBackground(new java.awt.Color(255, 153, 0));
        txtAtendentecaixa.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtAtendentecaixa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAtendentecaixa.setText("Caixa:");
        txtAtendentecaixa.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtAtendentecaixa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAtendentecaixaKeyPressed(evt);
            }
        });

        txtCaixainicial.setBackground(new java.awt.Color(255, 153, 0));
        txtCaixainicial.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtCaixainicial.setText("Início: ");
        txtCaixainicial.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtCaixainicial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCaixainicialKeyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("=");

        txtNotasinicio.setBackground(new java.awt.Color(255, 153, 0));
        txtNotasinicio.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtNotasinicio.setText("Notas: ");
        txtNotasinicio.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtNotasinicio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNotasinicioKeyPressed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("+");

        tblMovimento.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tblMovimento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Hora", "Venda à vista", "Entrega", "Receb. à prazo", "Vale", "Saque", "Pagamentos", "Movimento", "Cartão"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMovimento.getTableHeader().setReorderingAllowed(false);
        tblMovimento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMovimentoMouseClicked(evt);
            }
        });
        tblMovimento.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                tblMovimentoComponentShown(evt);
            }
        });
        tblMovimento.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tblMovimentoPropertyChange(evt);
            }
        });
        tblMovimento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblMovimentoKeyPressed(evt);
            }
        });
        tblMovimento.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                tblMovimentoVetoableChange(evt);
            }
        });
        jScrollPane1.setViewportView(tblMovimento);
        if (tblMovimento.getColumnModel().getColumnCount() > 0) {
            tblMovimento.getColumnModel().getColumn(0).setResizable(false);
            tblMovimento.getColumnModel().getColumn(1).setResizable(false);
            tblMovimento.getColumnModel().getColumn(2).setResizable(false);
            tblMovimento.getColumnModel().getColumn(3).setResizable(false);
            tblMovimento.getColumnModel().getColumn(4).setResizable(false);
            tblMovimento.getColumnModel().getColumn(5).setResizable(false);
            tblMovimento.getColumnModel().getColumn(6).setResizable(false);
            tblMovimento.getColumnModel().getColumn(7).setResizable(false);
            tblMovimento.getColumnModel().getColumn(8).setResizable(false);
        }

        txtVendas.setBackground(new java.awt.Color(255, 153, 0));
        txtVendas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtVendas.setText("Vendas:");
        txtVendas.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtVendas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtVendasKeyPressed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        buttonGroup2.add(rbAvista);
        rbAvista.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        rbAvista.setText("Venda à vista");
        rbAvista.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                rbAvistaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                rbAvistaFocusLost(evt);
            }
        });
        rbAvista.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rbAvistaKeyPressed(evt);
            }
        });

        buttonGroup2.add(rbAprazo);
        rbAprazo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        rbAprazo.setText("Receb. à prazo");
        rbAprazo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                rbAprazoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                rbAprazoFocusLost(evt);
            }
        });
        rbAprazo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rbAprazoKeyPressed(evt);
            }
        });

        buttonGroup2.add(rbCartao);
        rbCartao.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        rbCartao.setText("Cartão");
        rbCartao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                rbCartaoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                rbCartaoFocusLost(evt);
            }
        });
        rbCartao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbCartaoActionPerformed(evt);
            }
        });
        rbCartao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rbCartaoKeyPressed(evt);
            }
        });

        buttonGroup2.add(rbPagamentos);
        rbPagamentos.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        rbPagamentos.setText("Pagamentos");
        rbPagamentos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                rbPagamentosFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                rbPagamentosFocusLost(evt);
            }
        });
        rbPagamentos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rbPagamentosKeyPressed(evt);
            }
        });

        lblValor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblValor.setText("Valor:  R$");

        lblFormadepagamento.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblFormadepagamento.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFormadepagamento.setText("Forma de Pagamento:");

        btnExcluir.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete_21455.png"))); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.setEnabled(false);
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });
        btnExcluir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnExcluirKeyPressed(evt);
            }
        });

        ftxtValor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        ftxtValor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ftxtValor.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        ftxtValor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ftxtValorFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                ftxtValorFocusLost(evt);
            }
        });
        ftxtValor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ftxtValorMouseClicked(evt);
            }
        });
        ftxtValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ftxtValorActionPerformed(evt);
            }
        });
        ftxtValor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ftxtValorKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ftxtValorKeyReleased(evt);
            }
        });

        buttonGroup2.add(rbEntrega);
        rbEntrega.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        rbEntrega.setText("Entrega");
        rbEntrega.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                rbEntregaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                rbEntregaFocusLost(evt);
            }
        });
        rbEntrega.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rbEntregaKeyPressed(evt);
            }
        });

        buttonGroup2.add(rbVale);
        rbVale.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        rbVale.setText("Vale");
        rbVale.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                rbValeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                rbValeFocusLost(evt);
            }
        });
        rbVale.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rbValeKeyPressed(evt);
            }
        });

        buttonGroup2.add(rbSaque);
        rbSaque.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        rbSaque.setText("Saque");
        rbSaque.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                rbSaqueFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                rbSaqueFocusLost(evt);
            }
        });
        rbSaque.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rbSaqueKeyPressed(evt);
            }
        });

        btnFecharcaixa.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnFecharcaixa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Accounting_icon-icons.com_74682.png"))); // NOI18N
        btnFecharcaixa.setText("Fechar Caixa");
        btnFecharcaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharcaixaActionPerformed(evt);
            }
        });
        btnFecharcaixa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnFecharcaixaKeyPressed(evt);
            }
        });

        btnFecharMovimento.setText("fechar movimento");
        btnFecharMovimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharMovimentoActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdDiferencaNeg);
        rdDiferencaNeg.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        rdDiferencaNeg.setForeground(new java.awt.Color(255, 51, 0));
        rdDiferencaNeg.setText("Diferença -");
        rdDiferencaNeg.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                rdDiferencaNegFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                rdDiferencaNegFocusLost(evt);
            }
        });
        rdDiferencaNeg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rdDiferencaNegKeyPressed(evt);
            }
        });

        buttonGroup2.add(rdDiferencaPos);
        rdDiferencaPos.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        rdDiferencaPos.setForeground(new java.awt.Color(51, 51, 255));
        rdDiferencaPos.setText("Diferença +");
        rdDiferencaPos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                rdDiferencaPosFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                rdDiferencaPosFocusLost(evt);
            }
        });
        rdDiferencaPos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rdDiferencaPosKeyPressed(evt);
            }
        });

        pnlCaixareservado.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Caixa reservado.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        pnlCaixareservado.setEnabled(false);

        lblNotas.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblNotas.setText("Notas:");
        lblNotas.setEnabled(false);

        lblMoedas.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMoedas.setText("Moedas:");
        lblMoedas.setEnabled(false);

        btnConfirmareserva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/button_ok2-32x32x24.png"))); // NOI18N
        btnConfirmareserva.setText("Comfirma");
        btnConfirmareserva.setEnabled(false);
        btnConfirmareserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmareservaActionPerformed(evt);
            }
        });
        btnConfirmareserva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnConfirmareservaKeyPressed(evt);
            }
        });

        btnCorrigereserva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Selecionar.png"))); // NOI18N
        btnCorrigereserva.setText("Corrige");
        btnCorrigereserva.setEnabled(false);
        btnCorrigereserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCorrigereservaActionPerformed(evt);
            }
        });
        btnCorrigereserva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnCorrigereservaKeyPressed(evt);
            }
        });

        ftxtNotasreserva.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        ftxtNotasreserva.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ftxtNotasreserva.setEnabled(false);
        ftxtNotasreserva.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        ftxtNotasreserva.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ftxtNotasreservaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                ftxtNotasreservaFocusLost(evt);
            }
        });
        ftxtNotasreserva.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ftxtNotasreservaMouseClicked(evt);
            }
        });
        ftxtNotasreserva.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                ftxtNotasreservaInputMethodTextChanged(evt);
            }
        });
        ftxtNotasreserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ftxtNotasreservaActionPerformed(evt);
            }
        });
        ftxtNotasreserva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ftxtNotasreservaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ftxtNotasreservaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ftxtNotasreservaKeyTyped(evt);
            }
        });

        ftxtMoedasreserva.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        ftxtMoedasreserva.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ftxtMoedasreserva.setEnabled(false);
        ftxtMoedasreserva.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        ftxtMoedasreserva.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ftxtMoedasreservaMouseClicked(evt);
            }
        });
        ftxtMoedasreserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ftxtMoedasreservaActionPerformed(evt);
            }
        });
        ftxtMoedasreserva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ftxtMoedasreservaKeyPressed(evt);
            }
        });

        lblReservadoem.setText("Reservado em: ");
        lblReservadoem.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblReservadoem.setEnabled(false);

        javax.swing.GroupLayout pnlCaixareservadoLayout = new javax.swing.GroupLayout(pnlCaixareservado);
        pnlCaixareservado.setLayout(pnlCaixareservadoLayout);
        pnlCaixareservadoLayout.setHorizontalGroup(
            pnlCaixareservadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCaixareservadoLayout.createSequentialGroup()
                .addGroup(pnlCaixareservadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCaixareservadoLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblNotas, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ftxtNotasreserva, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblMoedas))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCaixareservadoLayout.createSequentialGroup()
                        .addComponent(lblReservadoem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnConfirmareserva)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCaixareservadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ftxtMoedasreserva)
                    .addComponent(btnCorrigereserva, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)))
        );
        pnlCaixareservadoLayout.setVerticalGroup(
            pnlCaixareservadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCaixareservadoLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(pnlCaixareservadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ftxtNotasreserva, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlCaixareservadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ftxtMoedasreserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNotas)
                        .addComponent(lblMoedas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(2, 2, 2)
                .addGroup(pnlCaixareservadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCorrigereserva, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConfirmareserva, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblReservadoem, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        btnReservarcaixa.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnReservarcaixa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/note_edit_48x48(1)(1).png"))); // NOI18N
        btnReservarcaixa.setText("Reservar caixa");
        btnReservarcaixa.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnReservarcaixaFocusLost(evt);
            }
        });
        btnReservarcaixa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReservarcaixaMouseClicked(evt);
            }
        });
        btnReservarcaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReservarcaixaActionPerformed(evt);
            }
        });
        btnReservarcaixa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnReservarcaixaKeyPressed(evt);
            }
        });

        btnCancelarreserva.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCancelarreserva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/1486504346-cancel-close-delete-exit-remove-x_81304.png"))); // NOI18N
        btnCancelarreserva.setText("Cancelar reserva");
        btnCancelarreserva.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnCancelarreservaFocusLost(evt);
            }
        });
        btnCancelarreserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarreservaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFecharcaixa, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(rbEntrega, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rbAprazo)
                            .addComponent(rbAvista, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rbSaque)
                                    .addComponent(rbVale, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(44, 44, 44)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(rdDiferencaNeg, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(rbCartao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(36, 36, 36))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(rbPagamentos, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(rdDiferencaPos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnFecharMovimento, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(lblValor, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ftxtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(9, 9, 9))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(92, 92, 92)
                                .addComponent(lblFormadepagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnReservarcaixa, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelarreserva, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlCaixareservado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnFecharMovimento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ftxtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblValor, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(2, 3, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReservarcaixa, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelarreserva))
                .addGap(3, 3, 3)
                .addComponent(pnlCaixareservado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblFormadepagamento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rbVale, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rbCartao))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(rbAvista, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGap(4, 4, 4)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdDiferencaNeg, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(rbSaque)
                                .addComponent(rbEntrega)))
                        .addGap(3, 3, 3)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdDiferencaPos)
                            .addComponent(rbPagamentos)
                            .addComponent(rbAprazo))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(btnFecharcaixa, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addComponent(jSeparator1)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtRelogio, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtAtendentecaixa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCaixainicial, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNotasinicio, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMoedasinicio, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtVendas, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtRelogio)
                        .addComponent(txtAtendentecaixa)
                        .addComponent(txtCaixainicial)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtNotasinicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(txtMoedasinicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtVendas, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    public void recebemovidponto( int movidponto, int iddata){
        this.movidponto = movidponto;
        RefazerConexao rfc = new RefazerConexao();
        rfc.refazerconexao();
        lertabela();
        alinhamentocelula();
        this.iddata = iddata;
    }
    
    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        
    }//GEN-LAST:event_formInternalFrameActivated

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        
    }//GEN-LAST:event_formInternalFrameOpened

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
                        
    }//GEN-LAST:event_formFocusGained

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        ConfigDB configdb = new ConfigDB();
        tblMovimento.setEnabled(true);
        ftxtValor.setEnabled(true);
        btnReservarcaixa.setEnabled(true);
        mnNovousuario.setEnabled(true);       
        mnFecharNovousuario.setEnabled(false);
        mnEntrar.setEnabled(true);
        mnFecharEntrar.setEnabled(false);
        btnLogin.setEnabled(true);
        btnEntrar.setEnabled(true);
        btnCaixa.setEnabled(false);
        mnCaixa.setEnabled(false);
        mnFecharcaixa.setEnabled(false);
        btnRelatorio.setEnabled(false);
        mnRelatorios.setEnabled(false);
        mnFecharRelatorios.setEnabled(false);
        btnAdministrador.setEnabled(true);
        btnFecharAdmin.setEnabled(false);
        //String resultadoreduz = resultado.replaceAll("MOVIMENTO.FDB", "");
    new Thread(){
        OutputStream respostaBKP;
        @Override
        public void run(){
try {
     respostaBKP = new FileOutputStream(configdb.porta_bd() + "Backu&Log/log.rtf");
        try {
            //String porta = configBD.getCbd_porta();
             FBBackupManager backup = new FBBackupManager();
             backup.setUser("SYSDBA");
             backup.setPassword("masterkey");
             backup.setDatabase(configdb.getResultado());
             backup.setPort(3050);
             backup.setHost("localhost");
             backup.setBackupPath(configdb.porta_bd() + "Backu&Log/arquivo.bkp");
             backup.setVerbose(true);
             backup.setLogger(respostaBKP);
             backup.backupDatabase();
        }catch (SQLException ex) {
             JOptionPane.showMessageDialog(null,"ERRO:\n" + ex, "Bragança",JOptionPane.ERROR_MESSAGE);
        } finally {
                    try {
                         respostaBKP.close();
                    } catch (IOException ex) {
                         JOptionPane.showMessageDialog(null,"ERRO:\n" + ex, "Bragança",JOptionPane.ERROR_MESSAGE);
                    }
        }
}catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null,"ERRO:\n" + ex, "Bragança",JOptionPane.ERROR_MESSAGE);
}
        }
    }.start();  
        
    }//GEN-LAST:event_formInternalFrameClosing

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        
    }//GEN-LAST:event_formInternalFrameClosed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
               
               
    }//GEN-LAST:event_formKeyPressed

    private void tblMovimentoVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_tblMovimentoVetoableChange

    }//GEN-LAST:event_tblMovimentoVetoableChange

    private void tblMovimentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblMovimentoKeyPressed
        switch(evt.getKeyCode()){
            case 27:
                btnFecharcaixa.doClick();
                break;
            case 127:
                btnExcluir.doClick();
                break;
        }
    }//GEN-LAST:event_tblMovimentoKeyPressed

    private void tblMovimentoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tblMovimentoPropertyChange

    }//GEN-LAST:event_tblMovimentoPropertyChange

    private void tblMovimentoComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tblMovimentoComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_tblMovimentoComponentShown

    private void tblMovimentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMovimentoMouseClicked
        String motivopagamentos = "", empresas = "", competencias = "", usuario = "",
                saquefuncionario = null, valefuncionario = null,
        clientepagamento = null, clienteentrega = null;
        if(tblMovimento.getSelectedRow()!= -1 && tblMovimento.getSelectedRow()!= tblMovimento.getRowCount() - 2){
           if(tblMovimento.getSelectedRow()!= -1 && tblMovimento.getSelectedRow()!= (tblMovimento.getRowCount() - 1)){
              btnExcluir.setEnabled(true);
           }
        }
        if(tblMovimento.getSelectedRow() != -1 && tblMovimento.getSelectedRow() != (tblMovimento.getRowCount() -1)
            && tblMovimento.getSelectedRow() != tblMovimento.getRowCount() - 2){
            if(selecionamovimentousuario.get(tblMovimento.getSelectedRow()).getMovimento() < 0
                && selecionamovimentousuario.get(tblMovimento.getSelectedRow()).getPagamentos() > 0){
                RefazerConexao rfc = new RefazerConexao();
                rfc.refazerconexao();
                MovimentoDAO movdao = new MovimentoDAO();
                List<Pagamentos> motivopagamento = new ArrayList<>();
                motivopagamento = movdao.selecionamotivopagto(selecionamovimentousuario.get
                    (tblMovimento.getSelectedRow()).getIdmovimento());
                for(Pagamentos pg : motivopagamento){
                    motivopagamentos = pg.getMotivopago();
                    empresas = pg.getEmpresa();
                }
                JOptionPane.showMessageDialog(null, "Pagamento de: " + motivopagamentos + ",\n da empresa: " + empresas,"Bragança", JOptionPane.INFORMATION_MESSAGE);
            }
            if(selecionamovimentousuario.get(tblMovimento.getSelectedRow()).getMovimento() < 0
                && selecionamovimentousuario.get(tblMovimento.getSelectedRow()).getVale() > 0){
               RefazerConexao rfc = new RefazerConexao();
                rfc.refazerconexao();
                MovimentoDAO movdao = new MovimentoDAO();
                List<Vales>valesfuncionario = new ArrayList<>();
                valesfuncionario = movdao.selecionafunvionariovale(selecionamovimentousuario.get
                    (tblMovimento.getSelectedRow()).getIdmovimento());
                for(Vales vs : valesfuncionario){
                    valefuncionario = vs.getFuncionario();
                    usuario = vs.getUsuario();
                }
                JOptionPane.showMessageDialog(null, "Vale feito por: " + valefuncionario,"Bragança", JOptionPane.INFORMATION_MESSAGE);
            }
            if(selecionamovimentousuario.get(tblMovimento.getSelectedRow()).getMovimento() < 0
                && selecionamovimentousuario.get(tblMovimento.getSelectedRow()).getSaque() > 0){
                RefazerConexao rfc = new RefazerConexao();
                rfc.refazerconexao();
                MovimentoDAO movdao = new MovimentoDAO();
                List<Vales> saquesfuncionario = new ArrayList<>();
                saquesfuncionario = movdao.selecionafuncionariosaque(selecionamovimentousuario.get
                    (tblMovimento.getSelectedRow()).getIdmovimento());
                for(Vales vs : saquesfuncionario){
                    saquefuncionario = vs.getFuncionario();
                    usuario = vs.getUsuario();
                }
                JOptionPane.showMessageDialog(null, "Saque feito por: " + saquefuncionario,"Bragança", JOptionPane.INFORMATION_MESSAGE);
            }
            if(selecionamovimentousuario.get(tblMovimento.getSelectedRow()).getMovimento() > 0
                && selecionamovimentousuario.get(tblMovimento.getSelectedRow()).getRecebapraso() > 0){
                RefazerConexao rfc = new RefazerConexao();
                rfc.refazerconexao();
                MovimentoDAO movdao = new MovimentoDAO();
                List<RecebimentoPrazo> clientepagamentos = new ArrayList<>();
                clientepagamentos = movdao.selecionaclientepagamento(selecionamovimentousuario.get
                    (tblMovimento.getSelectedRow()).getIdmovimento());
                for(RecebimentoPrazo rp : clientepagamentos){
                    clientepagamento = rp.getClientepagante();
                    competencias = rp.getCompetencia();
                }
                JOptionPane.showMessageDialog(null, "Pagamento feito por: " + clientepagamento + ","
                        + "\n referente a: " + competencias,"Bragança", JOptionPane.INFORMATION_MESSAGE);
            }
            if(selecionamovimentousuario.get(tblMovimento.getSelectedRow()).getMovimento() > 0
                && selecionamovimentousuario.get(tblMovimento.getSelectedRow()).getEntrega() > 0){
                RefazerConexao rfc = new RefazerConexao();
                rfc.refazerconexao();
               MovimentoDAO movdao = new MovimentoDAO();
               List<Vales> clientesentrega = new ArrayList<>();
               clientesentrega = movdao.selecionaclienteentrega(selecionamovimentousuario.get
                                                            (tblMovimento.getSelectedRow()).getIdmovimento());
               for(Vales vl : clientesentrega){
                  clienteentrega = vl.getFuncionario();
                  usuario = vl.getUsuario();
               }
                JOptionPane.showMessageDialog(null, "Entrega feita por: " + clienteentrega,"Bragança", JOptionPane.INFORMATION_MESSAGE);
            }
            
//            RefazerConexao refselcentrada = new RefazerConexao();
//            refselcentrada.refazerconexao();
//            List<Entradas> selecionarentrada = new ArrayList<>();
//            MovimentoDAO movdaocelentr = new MovimentoDAO();
//            selecionarentrada = movdaocelentr.selecionarentrada(datahoje, );
        }
    }//GEN-LAST:event_tblMovimentoMouseClicked

    private void txtDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataActionPerformed

    private void txtDataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDataKeyPressed
      
         if(evt.getKeyCode() == evt.VK_ESCAPE){
              btnFecharcaixa.doClick();
         }
          
    }//GEN-LAST:event_txtDataKeyPressed

    private void txtRelogioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRelogioKeyPressed
        if(evt.getKeyCode() == evt.VK_ESCAPE){
             btnFecharcaixa.doClick();
        }
    }//GEN-LAST:event_txtRelogioKeyPressed

    private void txtAtendentecaixaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAtendentecaixaKeyPressed
        if(evt.getKeyCode() == evt.VK_ESCAPE){
            btnFecharcaixa.doClick();
        }
    }//GEN-LAST:event_txtAtendentecaixaKeyPressed

    private void txtCaixainicialKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCaixainicialKeyPressed
        if(evt.getKeyCode() == evt.VK_ESCAPE){
              btnFecharcaixa.doClick();
        }
    }//GEN-LAST:event_txtCaixainicialKeyPressed

    private void txtNotasinicioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNotasinicioKeyPressed
       if(evt.getKeyCode() == evt.VK_ESCAPE){
              btnFecharcaixa.doClick();
        }
    }//GEN-LAST:event_txtNotasinicioKeyPressed

    private void txtMoedasinicioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMoedasinicioKeyPressed
        if(evt.getKeyCode() == evt.VK_ESCAPE){
              btnFecharcaixa.doClick();
        }
    }//GEN-LAST:event_txtMoedasinicioKeyPressed

    private void txtVendasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtVendasKeyPressed
        if(evt.getKeyCode() == evt.VK_ESCAPE){
              btnFecharcaixa.doClick();
        }
    }//GEN-LAST:event_txtVendasKeyPressed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked

    }//GEN-LAST:event_formMouseClicked

    private void btnCancelarreservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarreservaActionPerformed
         
           RefazerConexao refc10 = new RefazerConexao();
           refc10.refazerconexao();
           List<Entradas> selecionasaidanula1 = new ArrayList<>();
           MovimentoDAO movdao30 = new MovimentoDAO();
           selecionasaidanula1 = movdao30.selecionarsaidanull();

                   for(Entradas entradas : selecionasaidanula1){
                           RefazerConexao refc13 = new RefazerConexao();
                           refc13.refazerconexao();
                           MovimentoDAO movdao32 = new MovimentoDAO();
                           iddata = movdao32.selecionariddata(entradas.getIdponto());
                           
                           SelecionandoReservaDeCaixa selecionandoreservadecaixa = new SelecionandoReservaDeCaixa();
                           selecionandoreservadecaixa.SelecionandoReservaDeCaixa(datahoje, entradas.getIdponto(), iddata);

                   }
        
        pnlCaixareservado.setEnabled(false);
        lblNotas.setEnabled(false);
        ftxtNotasreserva.setEnabled(false);
        lblMoedas.setEnabled(false);
        ftxtMoedasreserva.setEnabled(false);
        btnConfirmareserva.setEnabled(false);
        btnCorrigereserva.setEnabled(false);
        lblReservadoem.setEnabled(false);
        btnReservarcaixa.setEnabled(true);
        btnCancelarreserva.setEnabled(false);
    }//GEN-LAST:event_btnCancelarreservaActionPerformed

    private void btnCancelarreservaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnCancelarreservaFocusLost
 
    }//GEN-LAST:event_btnCancelarreservaFocusLost

    private void btnReservarcaixaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnReservarcaixaKeyPressed

    }//GEN-LAST:event_btnReservarcaixaKeyPressed

    private void btnReservarcaixaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReservarcaixaActionPerformed
        pnlCaixareservado.setEnabled(true);
        lblNotas.setEnabled(true);
        ftxtNotasreserva.setEnabled(true);
        lblMoedas.setEnabled(true);
        ftxtMoedasreserva.setEnabled(true);
        btnConfirmareserva.setEnabled(true);
        btnCorrigereserva.setEnabled(true);
        ftxtNotasreserva.setText("");
        ftxtMoedasreserva.setText("");
        ftxtNotasreserva.setDocument(new SoNumeros());
        ftxtNotasreserva.requestFocus();
        btnCancelarreserva.setEnabled(true);
        lblReservadoem.setEnabled(true);
        btnReservarcaixa.setEnabled(false);       

    }//GEN-LAST:event_btnReservarcaixaActionPerformed

    private void btnReservarcaixaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReservarcaixaMouseClicked

    }//GEN-LAST:event_btnReservarcaixaMouseClicked

    private void btnReservarcaixaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnReservarcaixaFocusLost
        ftxtNotasreserva.setDocument(new SoNumeros());
    }//GEN-LAST:event_btnReservarcaixaFocusLost

    private void ftxtMoedasreservaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ftxtMoedasreservaKeyPressed
        if(ftxtMoedasreserva.getText().equals("")){
            ftxtMoedasreserva.setDocument(new SoNumeros());
        }
        if(evt.getKeyCode()== evt.VK_ENTER){
            if(!ftxtMoedasreserva.getText().equals("")){
                btnConfirmareserva.requestFocus();
            }
        }
    }//GEN-LAST:event_ftxtMoedasreservaKeyPressed

    private void ftxtMoedasreservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ftxtMoedasreservaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ftxtMoedasreservaActionPerformed

    private void ftxtMoedasreservaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ftxtMoedasreservaMouseClicked
        ftxtMoedasreserva.requestFocus();
    }//GEN-LAST:event_ftxtMoedasreservaMouseClicked

    private void ftxtNotasreservaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ftxtNotasreservaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_ftxtNotasreservaKeyTyped

    private void ftxtNotasreservaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ftxtNotasreservaKeyReleased

    }//GEN-LAST:event_ftxtNotasreservaKeyReleased

    private void ftxtNotasreservaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ftxtNotasreservaKeyPressed
        if(ftxtNotasreserva.getText().equals("")){
            ftxtNotasreserva.setDocument(new SoNumeros());
        }
        if(evt.getKeyCode()== evt.VK_ENTER){
            if(!ftxtNotasreserva.getText().equals("")){
                ftxtMoedasreserva.requestFocus();
            }
        }
    }//GEN-LAST:event_ftxtNotasreservaKeyPressed

    private void ftxtNotasreservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ftxtNotasreservaActionPerformed

    }//GEN-LAST:event_ftxtNotasreservaActionPerformed

    private void ftxtNotasreservaInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_ftxtNotasreservaInputMethodTextChanged

    }//GEN-LAST:event_ftxtNotasreservaInputMethodTextChanged

    private void ftxtNotasreservaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ftxtNotasreservaMouseClicked
        ftxtNotasreserva.setDocument(new SoNumeros());
    }//GEN-LAST:event_ftxtNotasreservaMouseClicked

    private void ftxtNotasreservaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ftxtNotasreservaFocusLost
        ftxtMoedasreserva.requestFocus();
        ftxtMoedasreserva.setDocument(new SoNumeros());
    }//GEN-LAST:event_ftxtNotasreservaFocusLost

    private void ftxtNotasreservaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ftxtNotasreservaFocusGained

    }//GEN-LAST:event_ftxtNotasreservaFocusGained

    private void btnCorrigereservaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCorrigereservaKeyPressed
        if(evt.getKeyCode()== evt.VK_ENTER){
            if(!ftxtMoedasreserva.getText().equals("") && !ftxtNotasreserva.getText().equals("")){
                btnCorrigereserva.doClick();
            }else{
                if(evt.getKeyCode()== evt.VK_SPACE){
                    ftxtNotasreserva.setText("");
                    ftxtMoedasreserva.setText("");
                    ftxtNotasreserva.requestFocus();
                }
            }
        }
    }//GEN-LAST:event_btnCorrigereservaKeyPressed

    private void btnConfirmareservaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnConfirmareservaKeyPressed
        if(evt.getKeyCode()== evt.VK_ENTER){
            if(!ftxtMoedasreserva.getText().equals("") && !ftxtNotasreserva.getText().equals("")){
                btnConfirmareserva.doClick();
            }else{
                if(evt.getKeyCode()== evt.VK_SPACE){
                    btnCorrigereserva.requestFocus();
                }
            }
        }
    }//GEN-LAST:event_btnConfirmareservaKeyPressed

    private void btnConfirmareservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmareservaActionPerformed
        if(!ftxtNotasreserva.getText().equals("") || !ftxtMoedasreserva.getText().equals("")){
//            RefazerConexao rfcid = new RefazerConexao();
//            rfcid.refazerconexao();
//            MovimentoDAO movdaoiddata = new MovimentoDAO();
//            iddata = movdaoiddata.selecionariddata(idponto);
//            idmovimento = movdaoid.selecionarmaxmovimento(movidponto);
            RefazerConexao rfc = new RefazerConexao();
            rfc.refazerconexao();
            MovimentoDAO movdao = new MovimentoDAO();
            movdao.salvar_reservadecaixa(iddata, movidponto, parseFloat(ftxtNotasreserva.getText()
                .replaceAll("\\.", "").replaceAll(",",".")),
            parseFloat(ftxtMoedasreserva.getText().replaceAll("\\.", "")
                .replaceAll(",",".")));
            pnlCaixareservado.setEnabled(false);
            lblNotas.setEnabled(false);
            ftxtNotasreserva.setEnabled(false);
            lblMoedas.setEnabled(false);
            ftxtMoedasreserva.setEnabled(false);
            btnConfirmareserva.setEnabled(false);
            btnCorrigereserva.setEnabled(false);
            btnCancelarreserva.setEnabled(false);
            btnReservarcaixa.setEnabled(true);
            lblReservadoem.setEnabled(false);
            ftxtValor.requestFocus();
            
            SelecionandoReservaDeCaixa selecionandoreservadecaixa = new SelecionandoReservaDeCaixa();
            selecionandoreservadecaixa.SelecionandoReservaDeCaixa(datahoje, idponto, iddata);
        }else{
            JOptionPane.showMessageDialog(null, "É preciso apresentar um\n valor a ser guardado!");
        }
    }//GEN-LAST:event_btnConfirmareservaActionPerformed

    private void rdDiferencaPosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rdDiferencaPosKeyPressed
        switch(evt.getKeyCode()){
            case 37:
            rbPagamentos.requestFocus(true);
            rbPagamentos.setSelected(true);
            break;
            case 38:
            rdDiferencaNeg.requestFocus(true);
            rdDiferencaNeg.setSelected(true);
            break;
            case 39:
            rbAprazo.requestFocus(true);
            rbAprazo.setSelected(true);
            break;
            case 40:
            rbCartao.requestFocus(true);
            rbCartao.setSelected(true);
            break;
        }
        if(evt.getKeyCode()== evt.VK_ENTER){
            ftxtValor.setEnabled(true);
            ftxtValor.requestFocus(true);
        }else{
            if(evt.getKeyCode() == evt.VK_SPACE){
                rbAvista.requestFocus(true);
                rbAvista.setSelected(true);
            }else{
                if(evt.getKeyCode() == evt.VK_ESCAPE){
                    btnFecharcaixa.doClick();
                }
            }
        }
    }//GEN-LAST:event_rdDiferencaPosKeyPressed

    private void rdDiferencaPosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rdDiferencaPosFocusLost
        ftxtValor.setText("");
        ftxtValor.setDocument(new SoNumeros());
    }//GEN-LAST:event_rdDiferencaPosFocusLost

    private void rdDiferencaPosFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rdDiferencaPosFocusGained
        ftxtValor.setText("");
    }//GEN-LAST:event_rdDiferencaPosFocusGained

    private void rdDiferencaNegKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rdDiferencaNegKeyPressed
        switch(evt.getKeyCode()){
            case 37:
            rbSaque.requestFocus(true);
            rbSaque.setSelected(true);
            break;
            case 38:
            rbCartao.requestFocus(true);
            rbCartao.setSelected(true);
            break;
            case 39:
            rbEntrega.requestFocus(true);
            rbEntrega.setSelected(true);
            break;
            case 40:
            rdDiferencaPos.requestFocus(true);
            rdDiferencaPos.setSelected(true);
            break;
        }
        if(evt.getKeyCode()== evt.VK_ENTER){
            ftxtValor.setEnabled(true);
            ftxtValor.requestFocus(true);
        }else{
            if(evt.getKeyCode() == evt.VK_SPACE){
                rdDiferencaPos.requestFocus(true);
                rdDiferencaPos.setSelected(true);
            }else{
                if(evt.getKeyCode() == evt.VK_ESCAPE){
                    btnFecharcaixa.doClick();
                }
            }
        }
    }//GEN-LAST:event_rdDiferencaNegKeyPressed

    private void rdDiferencaNegFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rdDiferencaNegFocusLost
        ftxtValor.setText("");
        ftxtValor.setDocument(new SoNumeros());
    }//GEN-LAST:event_rdDiferencaNegFocusLost

    private void rdDiferencaNegFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rdDiferencaNegFocusGained
        ftxtValor.setText("");
    }//GEN-LAST:event_rdDiferencaNegFocusGained

    private void btnFecharMovimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharMovimentoActionPerformed
        this.setVisible(false);
        dtpDescktop.remove(this);
        this.dispose();
    }//GEN-LAST:event_btnFecharMovimentoActionPerformed

    private void btnFecharcaixaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnFecharcaixaKeyPressed
        if(evt.getKeyCode() == evt.VK_ESCAPE){
            btnFecharcaixa.doClick();
        }
    }//GEN-LAST:event_btnFecharcaixaKeyPressed

    private void btnFecharcaixaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharcaixaActionPerformed
        SimpleDateFormat formatbr = new SimpleDateFormat("HH:mm:ss");
        RefazerConexao rfc = new RefazerConexao();
        rfc.refazerconexao();
        lertabela();
        FecharCaixa fecharcaixa = new FecharCaixa();
        if(fecharcaixa.FecharCaixa(datahoje, movidponto, horaagora, encerrarmovimento) == true){
            mnCaixa.setEnabled(false);
            mnFecharcaixa.setEnabled(false);
            btnCaixa.setEnabled(false);
            btnRelatorio.setEnabled(false);
            mnRelatorios.setEnabled(false);
            mnFecharRelatorios.setEnabled(false);
            mnNovousuario.setEnabled(true);
            mnFecharNovousuario.setEnabled(false);
            mnEntrar.setEnabled(true);
            mnFecharEntrar.setEnabled(false);
            btnEntrar.setEnabled(true);
            btnLogin.setEnabled(true);
            btnAdministrador.setEnabled(true);
            btnFecharAdmin.setEnabled(false);
            this.setVisible(false);
            dtpDescktop.remove(this);
            this.dispose();
        }else{
            ftxtValor.requestFocus();
        }

        btnExcluir.setEnabled(false);
        tblMovimento.clearSelection();
    }//GEN-LAST:event_btnFecharcaixaActionPerformed

    private void rbSaqueKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbSaqueKeyPressed
        switch(evt.getKeyCode()){
            case 37:
            rbEntrega.requestFocus(true);
            rbEntrega.setSelected(true);
            break;
            case 38:
            rbVale.requestFocus(true);
            rbVale.setSelected(true);
            break;
            case 39:
            rdDiferencaNeg.requestFocus(true);
            rdDiferencaNeg.setSelected(true);
            break;
            case 40:
            rbPagamentos.requestFocus(true);
            rbPagamentos.setSelected(true);
            break;
        }
        if(evt.getKeyCode()== evt.VK_ENTER){
            ftxtValor.setEnabled(true);
            ftxtValor.requestFocus(true);
        }else{
            if(evt.getKeyCode() == evt.VK_SPACE){
                rbPagamentos.requestFocus(true);
                rbPagamentos.setSelected(true);
            }else{
                if(evt.getKeyCode() == evt.VK_ESCAPE){
                    btnFecharcaixa.doClick();
                }
            }
        }
    }//GEN-LAST:event_rbSaqueKeyPressed

    private void rbSaqueFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rbSaqueFocusLost
        ftxtValor.setText("");
        ftxtValor.setDocument(new SoNumeros());
    }//GEN-LAST:event_rbSaqueFocusLost

    private void rbSaqueFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rbSaqueFocusGained
        ftxtValor.setText("");
    }//GEN-LAST:event_rbSaqueFocusGained

    private void rbValeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbValeKeyPressed
        switch(evt.getKeyCode()){
            case 37:
            rbAvista.requestFocus(true);
            rbAvista.setSelected(true);
            break;
            case 38:
            rbPagamentos.requestFocus(true);
            rbPagamentos.setSelected(true);
            break;
            case 39:
            rbCartao.requestFocus(true);
            rbCartao.setSelected(true);
            break;
            case 40:
            rbSaque.requestFocus(true);
            rbSaque.setSelected(true);
            break;
        }
        if(evt.getKeyCode()== evt.VK_ENTER){
            ftxtValor.setEnabled(true);
            ftxtValor.requestFocus(true);
        }else{
            if(evt.getKeyCode() == evt.VK_SPACE){
                rbSaque.requestFocus(true);
                rbSaque.setSelected(true);
            }else{
                if(evt.getKeyCode() == evt.VK_ESCAPE){
                    btnFecharcaixa.doClick();
                }
            }
        }
    }//GEN-LAST:event_rbValeKeyPressed

    private void rbValeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rbValeFocusLost
        ftxtValor.setText("");
        ftxtValor.setDocument(new SoNumeros());
    }//GEN-LAST:event_rbValeFocusLost

    private void rbValeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rbValeFocusGained
        ftxtValor.setText("");
    }//GEN-LAST:event_rbValeFocusGained

    private void rbEntregaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbEntregaKeyPressed
        switch(evt.getKeyCode()){
            case 37:
            rdDiferencaNeg.requestFocus(true);
            rdDiferencaNeg.setSelected(true);
            break;
            case 38:
            rbAvista.requestFocus(true);
            rbAvista.setSelected(true);
            break;
            case 39:
            rbSaque.requestFocus(true);
            rbSaque.setSelected(true);
            break;
            case 40:
            rbAprazo.requestFocus(true);
            rbAprazo.setSelected(true);
            break;
        }
        if(evt.getKeyCode()== evt.VK_ENTER){
            ftxtValor.setEnabled(true);
            ftxtValor.requestFocus(true);
        }else{
            if(evt.getKeyCode() == evt.VK_SPACE){
                rbAprazo.requestFocus(true);
                rbAprazo.setSelected(true);
            }else{
                if(evt.getKeyCode() == evt.VK_ESCAPE){
                    btnFecharcaixa.doClick();
                }
            }
        }
    }//GEN-LAST:event_rbEntregaKeyPressed

    private void rbEntregaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rbEntregaFocusLost
        ftxtValor.setText("");
        ftxtValor.setDocument(new SoNumeros());
    }//GEN-LAST:event_rbEntregaFocusLost

    private void rbEntregaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rbEntregaFocusGained
        ftxtValor.setText("");
    }//GEN-LAST:event_rbEntregaFocusGained

    private void ftxtValorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ftxtValorKeyReleased
        
    }//GEN-LAST:event_ftxtValorKeyReleased

    private void ftxtValorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ftxtValorKeyPressed

        if(ftxtValor.getText().equals("")){
            ftxtValor.setDocument(new SoNumeros());
        }
        switch(evt.getKeyCode()){
            case 37:
            rbAvista.requestFocus(true);
            rbAvista.setSelected(true);
            break;
            case 27:
            btnFecharcaixa.doClick();
            break;
        }
        
        if(evt.getKeyCode()== evt.VK_ENTER){
            if(!ftxtValor.getText().equals("")){;
                String valor = ftxtValor.getText();
                if(!ftxtValor.getText().equals("")){
                    rbAvista.setActionCommand("1");
                    rbEntrega.setActionCommand("2");
                    rbAprazo.setActionCommand("3");
                    rbCartao.setActionCommand("4");
                    rbVale.setActionCommand("5");
                    rbSaque.setActionCommand("6");
                    rbPagamentos.setActionCommand("7");
                    rdDiferencaNeg.setActionCommand("8");
                    rdDiferencaPos.setActionCommand("9");
            
                    df.applyPattern("##,##0.00");
                    RefazerConexao rfz = new RefazerConexao();
                    rfz.refazerconexao();
                    List<Entradas> selecionaultimoponto = new ArrayList<>();
                    MovimentoDAO movimdao = new MovimentoDAO();
                    selecionaultimoponto = movimdao.selecionarultimoponto();
                    for(Entradas entradas : selecionaultimoponto){
                        movidponto = entradas.getIdusuario();
                        iddatacontafregues = entradas.getIddata();
                    }
//                           RefazerConexao refc13 = new RefazerConexao();
//                           refc13.refazerconexao();
//                           MovimentoDAO movdao32 = new MovimentoDAO();
//                           iddata = movdao32.selecionariddata(movidponto);
                           
                           RefazerConexao refc11 = new RefazerConexao();
                           refc11.refazerconexao();
                           MovimentoDAO movdao31 = new MovimentoDAO();
                           contafregues = movdao31.selecionacontagem(iddatacontafregues);
                           
                           
                            List<TotalVendas> selecionatotalvendas = new ArrayList<>();
                            RefazerConexao rfctotalvem = new RefazerConexao();
                            rfctotalvem.refazerconexao();
                            MovimentoDAO movdaottv = new MovimentoDAO();
                            selecionatotalvendas = movdaottv.selecinatotalvem(iddatacontafregues);
  
                            for(TotalVendas totalvendas : selecionatotalvendas){
                               iddatatotalvem = totalvendas.getIddata();
                               if(iddatatotalvem == 0){
                                totalvendasavista = 0;
                                vendasmaisentregas = 0;
                                somamovimento = 0;
                            }else{
                                totalvendasavista = totalvendas.getVendasavista();
                                vendasmaisentregas = totalvendas.getVendasmaisentregas();
                                somamovimento = totalvendas.getSomamovimento();
                            }
                            }
                           
                    switch(Integer.parseInt(buttonGroup2.getSelection().getActionCommand())){
                        case 1:
                        vendaavista = Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                        entrega = 0;
                        recebimentoprazo = 0;
                        cartao = 0;
                        vale = 0;
                        saque = 0;
                        pagamentos = 0;
                        movimento = Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                        totalvendasavista += Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",", "."));
                        somamovimento += Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                        vendasmaisentregas += Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                        contafregues += 1;
                        break;
                        case 2:
                        vendaavista = 0;
                        entrega = Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                        recebimentoprazo = 0;
                        cartao = 0;
                        vale = 0;
                        saque = 0;
                        pagamentos = 0;
                        movimento = Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                        somamovimento += Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                        vendasmaisentregas += Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                        contafregues += 1;
                        do{
                            clienteparaentrega = JOptionPane.showInputDialog(null, "Por favor digite aqui, o nome\n"
                                + "do entregador.");
                        }while("".equals(clienterecebprazo));
                        break;
                        case 3:
                        vendaavista = 0;
                        entrega = 0;
                        recebimentoprazo = Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                        cartao = 0;
                        vale = 0;
                        saque = 0;
                        pagamentos = 0;
                        movimento = Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                        //somamovimento += Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                        do{
                            clienterecebprazo = JOptionPane.showInputDialog(null, "Por favor digite aqui o nome cliente.");
                        }while("".equals(clienterecebprazo));
                            competencia = JOptionPane.showInputDialog(null, "Por favor digite aqui a competência.");
                        break;
                        case 4:
                        vendaavista = 0;
                        entrega = 0;
                        recebimentoprazo = 0;
                        cartao = Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                        vale = 0;
                        saque = 0;
                        pagamentos = 0;
                        movimento = 0;
                        somamovimento += Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                        break;
                        case 5:
                        vendaavista = 0;
                        entrega = 0;
                        recebimentoprazo = 0;
                        cartao = 0;
                        vale = Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                        saque = 0;
                        pagamentos = 0;
                        movimento = - Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                        somamovimento -= Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                        do{
                            funcionariovale = JOptionPane.showInputDialog(null, "Por favor digite aqui para quem é o vale.");
                        }while("".equals(funcionariovale));
                        break;
                        case 6:
                        vendaavista = 0;
                        entrega = 0;
                        recebimentoprazo = 0;
                        cartao = 0;
                        vale = 0;
                        saque = Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                        pagamentos = 0;
                        movimento = - Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                        somamovimento -= Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                        do{
                            motivosaque = JOptionPane.showInputDialog(null, "Por favor digite aqui o motivo do saque.");
                        }while("".equals(motivosaque));
                        break;
                        case 7:
                        vendaavista = 0;
                        entrega = 0;
                        recebimentoprazo = 0;
                        cartao = 0;
                        vale = 0;
                        saque = 0;
                        pagamentos = Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                        movimento = - Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                        somamovimento -= Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                        do{
                            motivopagamento = JOptionPane.showInputDialog(null, "Por favor digite aqui o que foi pago com este valor.");
                        }while("".equals(motivopagamento));
                            empresa = JOptionPane.showInputDialog(null, "Por favor digite aqui o nome da empresa");
                        break;
                        case 8:
                        vendaavista = - Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                        entrega = 0;
                        recebimentoprazo = 0;
                        cartao = 0;
                        vale = 0;
                        saque = 0;
                        pagamentos = 0;
                        movimento = - Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                        somamovimento -= Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                        vendasmaisentregas += - Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                        break;
                        case 9:
                        vendaavista =  Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                        entrega = 0;
                        recebimentoprazo = 0;
                        cartao = 0;
                        vale = 0;
                        saque = 0;
                        pagamentos = 0;
                        movimento = Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                        somamovimento += Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                        vendasmaisentregas += Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                        break;
                    }
                    if((recebimentoprazo != 0 && clienterecebprazo == null) || (vale != 0 && funcionariovale == null)
                        || (saque != 0 && motivosaque == null) || (pagamentos != 0 && motivopagamento == null)){
                        JOptionPane.showMessageDialog(null, "Nenhum cliente foi informado para pagamento ou\n"
                            + "nenhum motivo para uma retirada foi apresentado,\n"
                            + "não será contabilizado o valor de R$ "
                            + df.format(Float.parseFloat(valor.replaceAll("\\.", "").replaceAll(",","."))));
                    }else{
                        int resultconfirm = JOptionPane.showConfirmDialog(null, "Somar o valor de R$ " +
                            df.format(Float.parseFloat(valor.replaceAll("\\.", "").replaceAll(",","."))),
                            "Bragança", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if(resultconfirm == 0){
                            RefazerConexao rfc = new RefazerConexao();
                            rfc.refazerconexao();
                            MovimentoDAO movdao = new MovimentoDAO();
                            movdao.salvar_entrada_movimento(movidponto, horaagora, vendaavista,
                                entrega, recebimentoprazo, cartao, vale, saque, pagamentos, movimento, 0);
                            
                            RefazerConexao rfctotal = new RefazerConexao();
                            rfctotal.refazerconexao();
                            if(iddatatotalvem == 0){                                   
                                            MovimentoDAO movdaototal = new MovimentoDAO();
                                            movdaototal.salvar_totalvem(iddatacontafregues, totalvendasavista, vendasmaisentregas, somamovimento);
                            }else{
                                            MovimentoDAO movdaototal = new MovimentoDAO();
                                            movdaototal.atualizar_totalvem(iddatacontafregues, totalvendasavista, vendasmaisentregas, somamovimento);
                            }
                            
                            RefazerConexao refidcont = new RefazerConexao();
                            refidcont.refazerconexao();
                            MovimentoDAO movcontid = new MovimentoDAO();
                            
                            if(movcontid.selecionaidcontagem(iddatacontafregues) == 0){
                                RefazerConexao refcont = new RefazerConexao();
                                refcont.refazerconexao();
                                MovimentoDAO movdaocont = new MovimentoDAO();
                                movdaocont.salvar_contagem(iddatacontafregues, contafregues);
                            }else{
                                RefazerConexao refcont = new RefazerConexao();
                                refcont.refazerconexao();
                                MovimentoDAO movdaocont = new MovimentoDAO();
                                movdaocont.atualizar_contagem(iddatacontafregues, contafregues);
                            }

                            if(motivopagamento != null){
                                RefazerConexao rfc1 = new RefazerConexao();
                                rfc1.refazerconexao();
                                idmovimento = movdao.selecionarmaxmovimento(movidponto);
                                RefazerConexao rfc2 = new RefazerConexao();
                                rfc2.refazerconexao();
                                movdao.salvar_mot_pagt(idmovimento, motivopagamento, empresa);
                                motivopagamento = null;
                                empresa = null;
                            }

                            if(clienterecebprazo != null){
                                RefazerConexao rfc1 = new RefazerConexao();
                                rfc1.refazerconexao();
                                idmovimento = movdao.selecionarmaxmovimento(movidponto);
                                RefazerConexao rfc2 = new RefazerConexao();
                                rfc2.refazerconexao();
                                movdao.salvar_recebprazo(idmovimento, clienterecebprazo, competencia);
                                clienterecebprazo = null;
                                competencia = null;
                            }

                            if(funcionariovale != null){
                                RefazerConexao rfc1 = new RefazerConexao();
                                rfc1.refazerconexao();
                                idmovimento = movdao.selecionarmaxmovimento(movidponto);
                                RefazerConexao rfc2 = new RefazerConexao();
                                rfc2.refazerconexao();
                                movdao.salvar_vale(idmovimento, funcionariovale);
                                funcionariovale = null;
                            }

                            if(motivosaque != null){
                                RefazerConexao rfc1 = new RefazerConexao();
                                rfc1.refazerconexao();
                                idmovimento = movdao.selecionarmaxmovimento(movidponto);
                                RefazerConexao rfc2 = new RefazerConexao();
                                rfc2.refazerconexao();
                                movdao.salvar_saque(idmovimento,motivosaque);
                                motivosaque = null;
                            }

                            if(clienteparaentrega != null){
                                RefazerConexao rfc1 = new RefazerConexao();
                                rfc1.refazerconexao();
                                idmovimento = movdao.selecionarmaxmovimento(movidponto);
                                RefazerConexao rfc2 = new RefazerConexao();
                                rfc2.refazerconexao();
                                movdao.salvar_entrega(idmovimento,clienteparaentrega);
                                clienteparaentrega = null;
                            }

                            rfc.refazerconexao();
                            lertabela();
                            alinhamentocelula();
                        }
                    }
                    ftxtValor.setText("");
                    rbAvista.setSelected(true);
                    btnFecharcaixa.setEnabled(true);
                    ftxtValor.requestFocus();
                }

            }else{
                if(evt.getKeyCode() == evt.VK_ESCAPE){
                    btnFecharcaixa.doClick();
                }else{
                    ftxtValor.requestFocus();
                }
            }
        }
    }//GEN-LAST:event_ftxtValorKeyPressed

    private void ftxtValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ftxtValorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ftxtValorActionPerformed

    private void ftxtValorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ftxtValorMouseClicked
        ftxtValor.setDocument(new SoNumeros());
    }//GEN-LAST:event_ftxtValorMouseClicked

    private void ftxtValorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ftxtValorFocusLost

    }//GEN-LAST:event_ftxtValorFocusLost

    private void ftxtValorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ftxtValorFocusGained
        tblMovimento.clearSelection();
        btnExcluir.setEnabled(false);
    }//GEN-LAST:event_ftxtValorFocusGained

    private void btnExcluirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnExcluirKeyPressed
        if(evt.getKeyCode() == evt.VK_ESCAPE){
            btnFecharcaixa.doClick();
        }
    }//GEN-LAST:event_btnExcluirKeyPressed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        excluir();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void rbPagamentosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbPagamentosKeyPressed
        switch(evt.getKeyCode()){
            case 37:
            rbAprazo.requestFocus(true);
            rbAprazo.setSelected(true);
            break;
            case 38:
            rbSaque.requestFocus(true);
            rbSaque.setSelected(true);
            break;
            case 39:
            rdDiferencaPos.requestFocus(true);
            rdDiferencaPos.setSelected(true);
            break;
            case 40:
            rbVale.requestFocus(true);
            rbVale.setSelected(true);
            break;
        }
        if(evt.getKeyCode()== evt.VK_ENTER){
            ftxtValor.setEnabled(true);
            ftxtValor.requestFocus(true);
        }else{
            if(evt.getKeyCode() == evt.VK_SPACE){
                rbCartao.requestFocus(true);
                rbCartao.setSelected(true);
            }else{
                if(evt.getKeyCode() == evt.VK_ESCAPE){
                    btnFecharcaixa.doClick();
                }
            }
        }
    }//GEN-LAST:event_rbPagamentosKeyPressed

    private void rbPagamentosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rbPagamentosFocusLost
        ftxtValor.setText("");
        ftxtValor.setDocument(new SoNumeros());
    }//GEN-LAST:event_rbPagamentosFocusLost

    private void rbPagamentosFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rbPagamentosFocusGained
        ftxtValor.setText("");
    }//GEN-LAST:event_rbPagamentosFocusGained

    private void rbCartaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbCartaoKeyPressed
        switch(evt.getKeyCode()){
            case 37:
            rbVale.requestFocus(true);
            rbVale.setSelected(true);
            break;
            case 38:
            rdDiferencaPos.requestFocus(true);
            rdDiferencaPos.setSelected(true);
            break;
            case 39:
            rbAvista.requestFocus(true);
            rbAvista.setSelected(true);
            break;
            case 40:
            rdDiferencaNeg.requestFocus(true);
            rdDiferencaNeg.setSelected(true);
            break;
        }
        if(evt.getKeyCode()== evt.VK_ENTER){
            ftxtValor.setEnabled(true);
            ftxtValor.requestFocus(true);
        }else{
            if(evt.getKeyCode() == evt.VK_SPACE){
                rdDiferencaNeg.requestFocus(true);
                rdDiferencaNeg.setSelected(true);
            }else{
                if(evt.getKeyCode() == evt.VK_ESCAPE){
                    btnFecharcaixa.doClick();
                }
            }
        }
    }//GEN-LAST:event_rbCartaoKeyPressed

    private void rbCartaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbCartaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbCartaoActionPerformed

    private void rbCartaoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rbCartaoFocusLost
        ftxtValor.setText("");
        ftxtValor.setDocument(new SoNumeros());
    }//GEN-LAST:event_rbCartaoFocusLost

    private void rbCartaoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rbCartaoFocusGained
        ftxtValor.setText("");
    }//GEN-LAST:event_rbCartaoFocusGained

    private void rbAprazoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbAprazoKeyPressed
        switch(evt.getKeyCode()){
            case 37:
            rdDiferencaPos.requestFocus(true);
            rdDiferencaPos.setSelected(true);
            break;
            case 38:
            rbEntrega.requestFocus(true);
            rbEntrega.setSelected(true);
            break;
            case 39:
            rbPagamentos.requestFocus(true);
            rbPagamentos.setSelected(true);
            break;
            case 40:
            rbAvista.requestFocus(true);
            rbAvista.setSelected(true);
            break;
        }
        if(evt.getKeyCode()== evt.VK_ENTER){
            ftxtValor.setEnabled(true);
            ftxtValor.requestFocus(true);
        }else{
            if(evt.getKeyCode() == evt.VK_SPACE){
                rbVale.requestFocus(true);
                rbVale.setSelected(true);
            }else{
                if(evt.getKeyCode() == evt.VK_ESCAPE){
                    btnFecharcaixa.doClick();
                }
            }
        }
    }//GEN-LAST:event_rbAprazoKeyPressed

    private void rbAprazoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rbAprazoFocusLost
        ftxtValor.setText("");
        ftxtValor.setDocument(new SoNumeros());
    }//GEN-LAST:event_rbAprazoFocusLost

    private void rbAprazoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rbAprazoFocusGained
        ftxtValor.setText("");
    }//GEN-LAST:event_rbAprazoFocusGained

    private void rbAvistaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbAvistaKeyPressed
        switch(evt.getKeyCode()){
            case 37:
            rbCartao.requestFocus(true);
            rbCartao.setSelected(true);
            break;
            case 38:
            rbAprazo.requestFocus(true);
            rbAprazo.setSelected(true);
            break;
            case 39:
            rbVale.requestFocus(true);
            rbVale.setSelected(true);
            break;
            case 40:
            rbEntrega.requestFocus(true);
            rbEntrega.setSelected(true);
            break;
        }
        if(evt.getKeyCode()== evt.VK_ENTER){
            ftxtValor.setEnabled(true);
            ftxtValor.requestFocus(true);
        }else{
            if(evt.getKeyCode() == evt.VK_SPACE){
                rbEntrega.requestFocus(true);
                rbEntrega.setSelected(true);
            }else{
                if(evt.getKeyCode() == evt.VK_ESCAPE){
                    btnFecharcaixa.doClick();
                }
            }
        }
    }//GEN-LAST:event_rbAvistaKeyPressed

    private void rbAvistaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rbAvistaFocusLost
        ftxtValor.setText("");
        ftxtValor.setDocument(new SoNumeros());
    }//GEN-LAST:event_rbAvistaFocusLost

    private void rbAvistaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rbAvistaFocusGained
        ftxtValor.setText("");
    }//GEN-LAST:event_rbAvistaFocusGained

    private void btnCorrigereservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCorrigereservaActionPerformed
        if(!ftxtNotasreserva.getText().equals("") || !ftxtMoedasreserva.getText().equals("")){
            RefazerConexao rfcreser = new RefazerConexao();
            rfcreser.refazerconexao();
            SelecionarReservaDeCaixa selecionarreservadecaixa = new SelecionarReservaDeCaixa();
            //idponto = selecionarreservadecaixa.SelecionaridpontoUltimoCaixaReservado();
            RefazerConexao rfcid = new RefazerConexao();
            rfcid.refazerconexao();
            MovimentoDAO movdaoid = new MovimentoDAO();
            //idmovimento = movdaoid.selecionarmaxmovimento(movidponto);
            RefazerConexao rfc = new RefazerConexao();
            rfc.refazerconexao();
            MovimentoDAO movdao = new MovimentoDAO();
            movdao.atualizar_reservadecaixa(iddata, parseFloat(ftxtNotasreserva.getText()
                .replaceAll("\\.", "").replaceAll(",",".")),
            parseFloat(ftxtMoedasreserva.getText().replaceAll("\\.", "")
                .replaceAll(",",".")));
            pnlCaixareservado.setEnabled(false);
            lblNotas.setEnabled(false);
            ftxtNotasreserva.setEnabled(false);
            lblMoedas.setEnabled(false);
            ftxtMoedasreserva.setEnabled(false);
            btnConfirmareserva.setEnabled(false);
            btnCorrigereserva.setEnabled(false);
            btnCancelarreserva.setEnabled(false);
            btnReservarcaixa.setEnabled(true);
            lblReservadoem.setEnabled(false);
            ftxtValor.requestFocus();
        }else{
            JOptionPane.showMessageDialog(null, "É preciso apresentar um\n valor a ser guardado!");
        }
    }//GEN-LAST:event_btnCorrigereservaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnCancelarreserva;
    private javax.swing.JButton btnConfirmareserva;
    private javax.swing.JButton btnCorrigereserva;
    public static javax.swing.JButton btnExcluir;
    public static javax.swing.JButton btnFecharMovimento;
    public static javax.swing.JButton btnFecharcaixa;
    public static javax.swing.JButton btnReservarcaixa;
    private javax.swing.ButtonGroup buttonGroup2;
    public static javax.swing.JFormattedTextField ftxtMoedasreserva;
    public static javax.swing.JFormattedTextField ftxtNotasreserva;
    public static javax.swing.JFormattedTextField ftxtValor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblFormadepagamento;
    private javax.swing.JLabel lblMoedas;
    private javax.swing.JLabel lblNotas;
    public static javax.swing.JLabel lblReservadoem;
    private javax.swing.JLabel lblValor;
    private javax.swing.JPanel pnlCaixareservado;
    public static javax.swing.JRadioButton rbAprazo;
    public static javax.swing.JRadioButton rbAvista;
    public static javax.swing.JRadioButton rbCartao;
    public static javax.swing.JRadioButton rbEntrega;
    public static javax.swing.JRadioButton rbPagamentos;
    public static javax.swing.JRadioButton rbSaque;
    public static javax.swing.JRadioButton rbVale;
    public static javax.swing.JRadioButton rdDiferencaNeg;
    public static javax.swing.JRadioButton rdDiferencaPos;
    public static javax.swing.JTable tblMovimento;
    public static javax.swing.JTextField txtAtendentecaixa;
    public static javax.swing.JTextField txtCaixainicial;
    public static javax.swing.JTextField txtData;
    public static javax.swing.JTextField txtMoedasinicio;
    public static javax.swing.JTextField txtNotasinicio;
    public static javax.swing.JTextField txtRelogio;
    public static javax.swing.JTextField txtVendas;
    // End of variables declaration//GEN-END:variables
}
