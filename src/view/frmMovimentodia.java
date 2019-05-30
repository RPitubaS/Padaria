/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.beans.PropertyVetoException;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import modelo.bean.CaixaInicialDia;
import modelo.bean.Entradas;
import modelo.bean.Movimento;
import modelo.bean.Pagamentos;
import modelo.bean.RecebimentoPrazo;
import modelo.bean.Usuario;
import modelo.bean.Vales;
import modelo.dao.MovimentoDAO;
import modelo.dao.UsuariosDAO;
import produzconexao.RefazerConexao;
import util.GerenciadordeJanelas;
import util.SelecionandoReservaDeCaixa;
import static view.frmEntrar.txtLognickentrar;
import static view.frmMovimento.btnCancelarreserva;
import static view.frmMovimento.btnExcluir;
import static view.frmMovimento.btnFecharcaixa;
import static view.frmMovimento.btnReservarcaixa;
import static view.frmMovimento.ftxtValor;
import static view.frmMovimento.tblMovimento;
import static view.frmMovimento.txtAtendentecaixa;
import static view.frmMovimento.txtCaixainicial;
import static view.frmMovimento.txtMoedasinicio;
import static view.frmMovimento.txtNotasinicio;
import static view.frmMovimento.txtVendas;
import static view.frmPrincipal.btnCaixa;
import static view.frmPrincipal.btnFecharAdmin;
import static view.frmPrincipal.dtpDescktop;
import static view.frmPrincipal.mnCaixa;
import static view.frmPrincipal.mnFecharRelatorios;
import static view.frmPrincipal.mnFecharcaixa;
import static view.frmPrincipal.mnRelatorios;
import static view.frmPrincipal.btnRelatorio;

/**
 *
 * @author Pituba
 */
public class frmMovimentodia extends javax.swing.JInternalFrame {

    String agora, horaagora;;
    Time horasaida;
    int iddata, idponto;
    float encerrarmovimento;
    private static frmMovimentodia frmmovimentodia;
    frmMovimento frmmovimento = new frmMovimento();
    GerenciadordeJanelas gerenciadordejanelas;
    DecimalFormat df = new DecimalFormat();
    List<Movimento> selecionamovimentodia = new ArrayList<>();
    
    public static frmMovimentodia getInstancia(){
          if(frmmovimentodia == null){
             frmmovimentodia = new frmMovimentodia();
          }
        return frmmovimentodia;
    }
    
    public void setPosicaodia(){
    
         Dimension dimensao = dtpDescktop.getSize();
         
           this.setSize(dimensao);
               this.toFront();
               
    }
    
    public frmMovimentodia() {
        initComponents();
        JTableHeader tabeladia = tblMovimentodia.getTableHeader();
        tabeladia.setFont(new Font("Tahoma", Font.BOLD,12)); 
        ((DefaultTableCellRenderer)tabeladia.getDefaultRenderer()).setHorizontalAlignment(JLabel.RIGHT);
        btnMovimentododiaSair.setVisible(false);
        
        
        Thread clock = new Thread() {

            @Override
            public void run() {
                for (;;) {
                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Brazil/East"));
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    int month = (cal.get(Calendar.MONTH)) + 1;
                    int year = cal.get(Calendar.YEAR);
                    int second = cal.get(Calendar.SECOND);
                    int minute = cal.get(Calendar.MINUTE);
                    int hour = cal.get(Calendar.HOUR_OF_DAY);
                    agora = String.format("%02d.%02d.%02d", day, month, year);
                    horaagora = String.format("%02d:%02d:%02d", hour, minute, second);
                    txtRelogiodia.setText(String.format("%02d:%02d:%02d hs", hour, minute, second));
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
    
    void lertabeladia() {       
        int contador = 0, movidponto = 0, iddata;
        float caixanotasdia = 0, caixamoedasdia = 0;
        String diaformatado;
        DefaultTableModel modelo = (DefaultTableModel) tblMovimentodia.getModel();
        SimpleDateFormat formatbr = new SimpleDateFormat("HH:mm.ss");
        SimpleDateFormat formatdia = new SimpleDateFormat("dd.MM.yyyy");
        DecimalFormat dfa = new DecimalFormat();
        DecimalFormat dfc = new DecimalFormat();
        df.applyPattern("##,##0.00");
        dfa.applyPattern("-##,##0.00");
        dfc.applyPattern("*##,##0.00");
        modelo.setNumRows(0);
        RefazerConexao rfc = new RefazerConexao();
        rfc.refazerconexao();
        MovimentoDAO movdao = new MovimentoDAO();
        diaformatado = formatdia.format(dtcMovimentodia.getDate());
        selecionamovimentodia = movdao.selecionarmovimentodia(diaformatado);
        for (Movimento m : selecionamovimentodia) {
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
        RefazerConexao refc13 = new RefazerConexao();
        refc13.refazerconexao();
        MovimentoDAO movdao32 = new MovimentoDAO();
        iddata = movdao32.selecionariddata(movidponto);
        
        RefazerConexao rfcini = new RefazerConexao();
        rfcini.refazerconexao();
        List<CaixaInicialDia> caixadodia = new ArrayList<>();
        MovimentoDAO movdaoini = new MovimentoDAO();
        caixadodia = movdao.caixainicialdia(iddata);
        for(CaixaInicialDia cid : caixadodia){
            caixanotasdia = cid.getNotas();
            caixamoedasdia = cid.getMoedas();
        }
        df.format(caixanotasdia + caixamoedasdia);
        
         Object[] dados = {"Subtotal:", "", "", "", "", "", "", "", ""},
                  dados1 = {"Caixa inicial:", "0,00", "0,00", "0,00", "0,00", "0,00", "0,00",
                           df.format(caixanotasdia + caixamoedasdia), "0,00"},
                  dados2 = {"Total :", "0,00", "0,00", "0,00", "0,00", "0,00", "0,00", "",
                           "0,00"};
                  
        modelo.insertRow(modelo.getRowCount(), dados);
        modelo.insertRow(modelo.getRowCount(), dados1);
        modelo.insertRow(modelo.getRowCount(), dados2);

                            float vendaavista = 0, entrega = 0,recebimentoprazo = 0, cartao = 0,
                                    vale = 0, saque = 0, pagamentos = 0, movimento = 0, total = 0;
                            for (int i = 0; i < modelo.getRowCount() - 3; i++) {
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

                                
                                modelo.setValueAt(va, modelo.getRowCount() - 3, 1);
                                modelo.setValueAt(et, modelo.getRowCount() - 3, 2);
                                modelo.setValueAt(rp, modelo.getRowCount() - 3, 3);
                                modelo.setValueAt(vl, modelo.getRowCount() - 3, 4);
                                modelo.setValueAt(sq, modelo.getRowCount() - 3, 5);
                                modelo.setValueAt(pg, modelo.getRowCount() - 3, 6);
                                modelo.setValueAt(mv, modelo.getRowCount() - 3, 7); 
                                modelo.setValueAt(ct, modelo.getRowCount() - 3, 8);

                            }
                            
                            for (int i = modelo.getRowCount() - 3; i < modelo.getRowCount() - 1; i++) {
                                total += Float.parseFloat(modelo.getValueAt(i, 7).toString()
                                    .replaceAll("\\.", "").replaceAll(",", "."));
                                
                                String tt = df.format(total);
                                
                                modelo.setValueAt(tt, modelo.getRowCount() -1, 7);
                            }
                            
                           
                                  RefazerConexao refc11 = new RefazerConexao();
                                  refc11.refazerconexao();
                                  MovimentoDAO movdao31 = new MovimentoDAO();
                                  txtVendasdia.setText("Vendas:  " + movdao31.selecionacontagemtotal(iddata));
                                  
                                  if(selecionamovimentodia.isEmpty()){
                                      modelo.setNumRows(0);
                                      JOptionPane.showMessageDialog(null, "Relatório vazio para:\n"
                                                                    + diaformatado);
                                  }
                                  
                       }
    
    public void cornalinha(){
              
             DefaultTableCellRenderer rightrenderer = new DefaultTableCellRenderer();
             DefaultTableCellRenderer rightrenderer1 = new DefaultTableCellRenderer();
             
             tblMovimentodia.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){ 
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
                                 tblMovimentodia.getColumnModel().getColumn(1).setCellRenderer(rightrenderer);
                                 tblMovimentodia.getColumnModel().getColumn(2).setCellRenderer(rightrenderer);
                                 tblMovimentodia.getColumnModel().getColumn(3).setCellRenderer(rightrenderer);
                                 tblMovimentodia.getColumnModel().getColumn(4).setCellRenderer(rightrenderer);
                                 tblMovimentodia.getColumnModel().getColumn(5).setCellRenderer(rightrenderer);
                                 tblMovimentodia.getColumnModel().getColumn(6).setCellRenderer(rightrenderer);
                                 tblMovimentodia.getColumnModel().getColumn(7).setCellRenderer(rightrenderer);
                                 tblMovimentodia.getColumnModel().getColumn(8).setCellRenderer(rightrenderer1);
                              
                                  return label;
                                   
                              }                            
                       }); 
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtRelogiodia = new javax.swing.JTextField();
        txtAtendentecaixadia = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMovimentodia = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        dtcMovimentodia = new com.toedter.calendar.JDateChooser();
        txtVendasdia = new javax.swing.JTextField();
        btnMovimentododiaSair = new javax.swing.JButton();

        setClosable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
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
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));

        txtRelogiodia.setEditable(false);
        txtRelogiodia.setBackground(new java.awt.Color(255, 153, 0));
        txtRelogiodia.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtRelogiodia.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtRelogiodia.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtAtendentecaixadia.setBackground(new java.awt.Color(255, 153, 0));
        txtAtendentecaixadia.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtAtendentecaixadia.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAtendentecaixadia.setText("Caixa");
        txtAtendentecaixadia.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblMovimentodia.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tblMovimentodia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

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
        tblMovimentodia.getTableHeader().setReorderingAllowed(false);
        tblMovimentodia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMovimentodiaMouseClicked(evt);
            }
        });
        tblMovimentodia.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                tblMovimentodiaComponentShown(evt);
            }
        });
        tblMovimentodia.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tblMovimentodiaPropertyChange(evt);
            }
        });
        tblMovimentodia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblMovimentodiaKeyPressed(evt);
            }
        });
        tblMovimentodia.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                tblMovimentodiaVetoableChange(evt);
            }
        });
        jScrollPane1.setViewportView(tblMovimentodia);
        if (tblMovimentodia.getColumnModel().getColumnCount() > 0) {
            tblMovimentodia.getColumnModel().getColumn(0).setResizable(false);
            tblMovimentodia.getColumnModel().getColumn(1).setResizable(false);
            tblMovimentodia.getColumnModel().getColumn(2).setResizable(false);
            tblMovimentodia.getColumnModel().getColumn(3).setResizable(false);
            tblMovimentodia.getColumnModel().getColumn(4).setResizable(false);
            tblMovimentodia.getColumnModel().getColumn(5).setResizable(false);
            tblMovimentodia.getColumnModel().getColumn(6).setResizable(false);
            tblMovimentodia.getColumnModel().getColumn(7).setResizable(false);
            tblMovimentodia.getColumnModel().getColumn(8).setResizable(false);
        }

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 0, 51));
        jButton2.setText("Encher Tabela");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        dtcMovimentodia.setBackground(new java.awt.Color(255, 153, 0));
        dtcMovimentodia.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        txtVendasdia.setBackground(new java.awt.Color(255, 153, 0));
        txtVendasdia.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtVendasdia.setText("Vendas:");
        txtVendasdia.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtVendasdia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtVendasdiaActionPerformed(evt);
            }
        });

        btnMovimentododiaSair.setText("Sair");
        btnMovimentododiaSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMovimentododiaSairActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1091, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(dtcMovimentodia, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtRelogiodia, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAtendentecaixadia)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtVendasdia, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnMovimentododiaSair)
                .addGap(78, 78, 78)
                .addComponent(jButton2)
                .addGap(18, 18, 18))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtVendasdia, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtRelogiodia)
                            .addComponent(txtAtendentecaixadia)))
                    .addComponent(dtcMovimentodia, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 792, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(btnMovimentododiaSair))
                .addGap(6, 6, 6))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblMovimentodiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMovimentodiaMouseClicked
        String motivopagamentos = "", empresas = "", competencias = "", usuario = "", 
                saquefuncionario = null, valefuncionario = null,
               clientepagamento = null, clienteentrega = null;

        if(tblMovimentodia.getSelectedRow() != -1 && tblMovimentodia.getSelectedRow() != 
                (tblMovimentodia.getRowCount() -1)){
            if(selecionamovimentodia.get(tblMovimentodia.getSelectedRow()).getMovimento() < 0
                && selecionamovimentodia.get(tblMovimentodia.getSelectedRow()).getPagamentos() > 0){
                RefazerConexao rfc = new RefazerConexao();
                rfc.refazerconexao();
                MovimentoDAO movdao = new MovimentoDAO();
                List<Pagamentos> motivopagamento = new ArrayList<>();
                motivopagamento = movdao.selecionamotivopagto(selecionamovimentodia.get
                    (tblMovimentodia.getSelectedRow()).getIdmovimento());
                for(Pagamentos pg : motivopagamento){
                    motivopagamentos = pg.getMotivopago();
                    empresas = pg.getEmpresa();
                    usuario = pg.getUsuario();
                    
                }
                JOptionPane.showMessageDialog(null, "Atendente: " + usuario + ".\nPagamento de: " + motivopagamentos +
                        "\nda empresa: " + empresas,"Bragança", JOptionPane.INFORMATION_MESSAGE);

            }
            if(selecionamovimentodia.get(tblMovimentodia.getSelectedRow()).getMovimento() < 0
                && selecionamovimentodia.get(tblMovimentodia.getSelectedRow()).getVale() > 0){
                RefazerConexao rfc = new RefazerConexao();
                rfc.refazerconexao();
                MovimentoDAO movdao = new MovimentoDAO();
                List<Vales> valesfuncionario = new ArrayList<>();
                valesfuncionario = movdao.selecionafunvionariovale(selecionamovimentodia.get
                    (tblMovimentodia.getSelectedRow()).getIdmovimento());
                for(Vales vs : valesfuncionario){
                    valefuncionario = vs.getFuncionario();
                    usuario = vs.getUsuario();
                }
                JOptionPane.showMessageDialog(null, "Atendente: " + usuario + ".\nVale feito por: " + valefuncionario,"Bragança", JOptionPane.INFORMATION_MESSAGE);

            }
            if(selecionamovimentodia.get(tblMovimentodia.getSelectedRow()).getMovimento() < 0
                && selecionamovimentodia.get(tblMovimentodia.getSelectedRow()).getSaque() > 0){
                RefazerConexao rfc = new RefazerConexao();
                rfc.refazerconexao();
                MovimentoDAO movdao = new MovimentoDAO();
                List<Vales> saquesfuncionario = new ArrayList<>();
                saquesfuncionario = movdao.selecionafuncionariosaque(selecionamovimentodia.get
                    (tblMovimentodia.getSelectedRow()).getIdmovimento());
                for(Vales vs : saquesfuncionario){
                    saquefuncionario = vs.getFuncionario();
                    usuario = vs.getUsuario();
                }
                JOptionPane.showMessageDialog(null, "Atendente: " + usuario + ".\nSaque feito por: " + saquefuncionario,"Bragança", JOptionPane.INFORMATION_MESSAGE);
            }
            if(selecionamovimentodia.get(tblMovimentodia.getSelectedRow()).getMovimento() > 0
                && selecionamovimentodia.get(tblMovimentodia.getSelectedRow()).getRecebapraso() > 0){
                RefazerConexao rfc = new RefazerConexao();
                rfc.refazerconexao();
                MovimentoDAO movdao = new MovimentoDAO();
                List<RecebimentoPrazo> clientepagamentos = new ArrayList<>();
                clientepagamentos = movdao.selecionaclientepagamento(selecionamovimentodia.get
                    (tblMovimentodia.getSelectedRow()).getIdmovimento());
                for(RecebimentoPrazo rp : clientepagamentos){
                    clientepagamento = rp.getClientepagante();
                    competencias = rp.getCompetencia();
                    usuario = rp.getUsuario();
                }
                JOptionPane.showMessageDialog(null, "Atendente: " + usuario + ".\nPagamento feito por: " + clientepagamento + ","
                        + "\n referente a: " + competencias,"Bragança", JOptionPane.INFORMATION_MESSAGE);
            }
            if(selecionamovimentodia.get(tblMovimentodia.getSelectedRow()).getMovimento() > 0
               && selecionamovimentodia.get(tblMovimentodia.getSelectedRow()).getEntrega() > 0){
               RefazerConexao rfc = new RefazerConexao();
               rfc.refazerconexao();
               MovimentoDAO movdao = new MovimentoDAO();
               List<Vales> clientesentrega = new ArrayList<>();
               clientesentrega = movdao.selecionaclienteentrega(selecionamovimentodia.get
                                                            (tblMovimentodia.getSelectedRow()).getIdmovimento());
               for(Vales vl : clientesentrega){
                  clienteentrega = vl.getFuncionario();
                  usuario = vl.getUsuario();
               }
               JOptionPane.showMessageDialog(null, "Atendente: " + usuario + ".\nEntrega feita por: " + clienteentrega,"Bragança", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_tblMovimentodiaMouseClicked

    private void tblMovimentodiaComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tblMovimentodiaComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_tblMovimentodiaComponentShown

    private void tblMovimentodiaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tblMovimentodiaPropertyChange

    }//GEN-LAST:event_tblMovimentodiaPropertyChange

    private void tblMovimentodiaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblMovimentodiaKeyPressed
       
    }//GEN-LAST:event_tblMovimentodiaKeyPressed

    private void tblMovimentodiaVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_tblMovimentodiaVetoableChange

    }//GEN-LAST:event_tblMovimentodiaVetoableChange

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        lertabeladia();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
              String tipousuario = "", usuario = "";
              
       DefaultTableModel modelo = (DefaultTableModel) tblMovimentodia.getModel();
       modelo.setNumRows(0);
              
           RefazerConexao refc10 = new RefazerConexao();
           refc10.refazerconexao();
           List<Entradas> selecionasaidanula1 = new ArrayList<>();
           MovimentoDAO movdao30 = new MovimentoDAO();
           selecionasaidanula1 = movdao30.selecionarsaidanull();
           if(!selecionasaidanula1.isEmpty()){
               java.sql.Date sdf;
               
                   int idpontoentrada = 0;
                   
                     frmmovimento = new frmMovimento();
                     dtpDescktop.add(frmmovimento);
                     frmmovimento.setVisible(true);
                     frmmovimento.setPosicao();
                     ftxtValor.requestFocus();
                   for(Entradas entradas : selecionasaidanula1){
                           txtAtendentecaixa.setText("Caixa: " + entradas.getUsuario());
                           txtNotasinicio.setText("Notas: " + String.format("%,.2f", entradas.getValorinicialcedula()));
                           txtMoedasinicio.setText("Moedas: " + String.format("%,.2f", entradas.getValorinicialmoedas()));
                           txtCaixainicial.setText("Início: " + String.format("%,.2f", entradas.getValorinicialcedula() + entradas.getValorinicialmoedas()));
                           frmmovimento.recebemovidponto(entradas.getIdponto(), entradas.getIddata());
                           idpontoentrada = entradas.getIdusuario();
                           SelecionandoReservaDeCaixa selecionandoreservadecaixa = new SelecionandoReservaDeCaixa();
                           selecionandoreservadecaixa.SelecionandoReservaDeCaixa(agora, idpontoentrada, entradas.getIddata());
                           iddata = entradas.getIddata();
                           usuario = entradas.getUsuario();
                           idponto = entradas.getIdponto();
            }
                             RefazerConexao refc13 = new RefazerConexao();
                             refc13.refazerconexao();
                             MovimentoDAO movdao31 = new MovimentoDAO();
                             txtVendas.setText("Vendas:  " + movdao31.selecionacontagem(iddata));
        }
           SelecionandoReservaDeCaixa selecionandoreservadecaixa = new SelecionandoReservaDeCaixa();
           selecionandoreservadecaixa.SelecionandoReservaDeCaixa(agora, idponto, iddata);
           
           RefazerConexao rfc = new RefazerConexao();
           rfc.refazerconexao();
           List<Usuario> selecionandousuario = new ArrayList<>();
           UsuariosDAO usdao = new UsuariosDAO();
           selecionandousuario = usdao.selecionarusuario(usuario);

        for(Usuario usuarios : selecionandousuario){
                       tipousuario = usuarios.getAdmin();
                      }
           if(tipousuario.equals("sim")){
              mnCaixa.setEnabled(true);
              btnCaixa.setEnabled(true);
              mnFecharcaixa.setEnabled(false);
              mnRelatorios.setEnabled(true);
              btnRelatorio.setEnabled(true);
              mnFecharRelatorios.setEnabled(false);
              //btnExcluir.setEnabled(true);
              //btnFecharcaixa.setEnabled(true);
              //ftxtValor.setEnabled(true);
              //btnReservarcaixa.setEnabled(true);
              //btnCancelarreserva.setEnabled(true);
           }else{
              mnCaixa.setEnabled(true);
              btnCaixa.setEnabled(true);
              mnFecharcaixa.setEnabled(false);
              mnRelatorios.setEnabled(true);
              btnRelatorio.setEnabled(true);
              mnFecharRelatorios.setEnabled(false);
                if(btnFecharAdmin.isEnabled()){
                     tblMovimento.setEnabled(false);
                     ftxtValor.setEnabled(false);
                     btnReservarcaixa.setEnabled(false);
                }
           }   
              
              
    }//GEN-LAST:event_formInternalFrameClosing

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        
        dtpDescktop.remove(this);
    }//GEN-LAST:event_formInternalFrameClosed

    private void txtVendasdiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtVendasdiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtVendasdiaActionPerformed

    private void btnMovimentododiaSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMovimentododiaSairActionPerformed
        try {
            this.setClosed(true);
        } catch (PropertyVetoException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex + "\n ao tentar fechar Movimento do dia!");
        }
    }//GEN-LAST:event_btnMovimentododiaSairActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnMovimentododiaSair;
    private com.toedter.calendar.JDateChooser dtcMovimentodia;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable tblMovimentodia;
    public static javax.swing.JTextField txtAtendentecaixadia;
    public static javax.swing.JTextField txtRelogiodia;
    private javax.swing.JTextField txtVendasdia;
    // End of variables declaration//GEN-END:variables
}
