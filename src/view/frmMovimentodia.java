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
import java.text.DateFormat;
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
import modelo.dao.MovimentoDAO;
import produzconexao.RefazerConexao;
import util.GerenciadordeJanelas;
import static view.frmMovimento.btnNovo;
import static view.frmMovimento.tblMovimento;
import static view.frmMovimento.txtAtendentecaixa;
import static view.frmMovimento.txtCaixainicial;
import static view.frmMovimento.txtMoedasinicio;
import static view.frmMovimento.txtNotasinicio;
import static view.frmPrincipal.dtpDescktop;
import static view.frmPrincipal.mnCaixa;
import static view.frmPrincipal.mnFecharcaixa;

/**
 *
 * @author Pituba
 */
public class frmMovimentodia extends javax.swing.JInternalFrame {

    String agora, horaagora;;
    Time horasaida;
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
        //lertabela();
    }
    
    void lertabeladia() {       
        int contador = 0;
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
            
        } 
     
        //Object[] dados1 = {"Caixa inicial:", "0,00", "0,00", "0,00", "0,00", "0,00", "0,00", "0,00",
                          // "0,00"},
         Object[] dados = {"Total:", "", "", "", "", "", "", "", ""};
       // modelo.insertRow(modelo.getRowCount(), dados1);
        modelo.insertRow(modelo.getRowCount(), dados);

        //String pd = txtCaixainicialdia.getText().substring(8, txtCaixainicialdia.getText().length());
        //modelo.setValueAt(pd, modelo.getRowCount() - 2, 7);
        
       //// DecimalFormat obj_formato = new DecimalFormat();
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
                                //JOptionPane.showMessageDialog(null, movimento);
                             
                                //obj_formato.applyPattern("##, ##0.00");
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
                                //encerrarmovimento = movimento;
                               //}
                            }
                                  if(selecionamovimentodia.isEmpty()){
                                      modelo.setNumRows(0);
                                      JOptionPane.showMessageDialog(null, "Relatório vazio para:\n"
                                                                    + diaformatado);
                                  }
                       }
    
    public void cornalinha(){
             //String mudacordelinha = ct;
              
             DefaultTableCellRenderer rightrenderer = new DefaultTableCellRenderer();
             DefaultTableCellRenderer rightrenderer1 = new DefaultTableCellRenderer();
             
             tblMovimentodia.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){ 
                     @Override
                     public Component getTableCellRendererComponent(JTable table,
                             Object value, boolean isSelected, boolean hasfocus, int row, int column){
                             JLabel label = (JLabel) super.getTableCellRendererComponent(table, value,
                                     isSelected, hasfocus, row, column);
                             //label.setFont(new Font("Tahoma", Font.BOLD,11));
                              //label.setHorizontalAlignment(JLabel.RIGHT);
                              //rightrenderer.setBackground(Color.YELLOW);
                              //tblMovimento.getColumnModel().getColumn(5).setCellRenderer(rightrenderer);
                              Color c = Color.BLACK;
                              
                              //label.setFont(new Font("Tahoma", Font.BOLD,11));
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
                                  //rightrenderer1.setForeground(Color.BLUE);
                              }
                                                            
                                 label.setForeground(c);
                                 
                                 rightrenderer.setForeground(c);
                                 
                                 //if(value.equals("*4,00")){
                                   //rightrenderer1.setForeground(Color.BLUE);
                                 //}
                                                        
                                 //rightrenderer.setForeground(c);
                                
                                 //tblMovimento.getColumnModel().getColumn(1).setCellRenderer(rightrenderer);
                                 //tblMovimento.getColumnModel().getColumn(2).setCellRenderer(rightrenderer);
                                 //tblMovimento.getColumnModel().getColumn(3).setCellRenderer(rightrenderer);
                                 //tblMovimento.getColumnModel().getColumn(4).setCellRenderer(rightrenderer);
                                 //tblMovimento.getColumnModel().getColumn(5).setCellRenderer(rightrenderer);
                                 //tblMovimento.getColumnModel().getColumn(6).setCellRenderer(rightrenderer);
                                 //tblMovimento.getColumnModel().getColumn(7).setCellRenderer(rightrenderer);
                                 //tblMovimento.getColumnModel().getColumn(8).setCellRenderer(rightrenderer);
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
                                 
                                  //label.setForeground(c);
                                 //label.setFont(new Font("Tahoma", Font.BOLD,11));                                  
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1091, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(dtcMovimentodia, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtRelogiodia, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(txtAtendentecaixadia)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(18, 18, 18))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                            .addComponent(txtRelogiodia)
                            .addComponent(txtAtendentecaixadia)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(dtcMovimentodia, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(8, 8, 8)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 788, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
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
        String motivopagamento = null, saquefuncionario = null, valefuncionario = null, clientepagamento = null;
        //btnExcluir.setEnabled(true);
        //btnNovo.setEnabled(false);
        if(tblMovimentodia.getSelectedRow() != -1 && tblMovimentodia.getSelectedRow() != 
                (tblMovimentodia.getRowCount() -1)){
            if(selecionamovimentodia.get(tblMovimentodia.getSelectedRow()).getMovimento() < 0
                && selecionamovimentodia.get(tblMovimentodia.getSelectedRow()).getPagamentos() > 0){
                RefazerConexao rfc = new RefazerConexao();
                rfc.refazerconexao();
                MovimentoDAO movdao = new MovimentoDAO();
                motivopagamento = movdao.selecionamotivopagto(selecionamovimentodia.get
                    (tblMovimentodia.getSelectedRow()).getIdmovimento());
                JOptionPane.showMessageDialog(null, "Pagamento de: " + motivopagamento,"Bragança", JOptionPane.INFORMATION_MESSAGE);
                //tblMovimento.clearSelection();
            }
            if(selecionamovimentodia.get(tblMovimentodia.getSelectedRow()).getMovimento() < 0
                && selecionamovimentodia.get(tblMovimentodia.getSelectedRow()).getVale() > 0){
                RefazerConexao rfc = new RefazerConexao();
                rfc.refazerconexao();
                MovimentoDAO movdao = new MovimentoDAO();
                valefuncionario = movdao.selecionafunvionariovale(selecionamovimentodia.get
                    (tblMovimentodia.getSelectedRow()).getIdmovimento());
                JOptionPane.showMessageDialog(null, "Vale feito por: " + valefuncionario,"Bragança", JOptionPane.INFORMATION_MESSAGE);
                //tblMovimento.clearSelection();
            }
            if(selecionamovimentodia.get(tblMovimentodia.getSelectedRow()).getMovimento() < 0
                && selecionamovimentodia.get(tblMovimentodia.getSelectedRow()).getSaque() > 0){
                RefazerConexao rfc = new RefazerConexao();
                rfc.refazerconexao();
                MovimentoDAO movdao = new MovimentoDAO();
                saquefuncionario = movdao.selecionafuncionariosaque(selecionamovimentodia.get
                    (tblMovimentodia.getSelectedRow()).getIdmovimento());
                JOptionPane.showMessageDialog(null, "Saque feito por: " + saquefuncionario,"Bragança", JOptionPane.INFORMATION_MESSAGE);
                //tblMovimento.clearSelection();
            }
            if(selecionamovimentodia.get(tblMovimentodia.getSelectedRow()).getMovimento() > 0
                && selecionamovimentodia.get(tblMovimentodia.getSelectedRow()).getRecebapraso() > 0){
                RefazerConexao rfc = new RefazerConexao();
                rfc.refazerconexao();
                MovimentoDAO movdao = new MovimentoDAO();
                clientepagamento = movdao.selecionaclientepagamento(selecionamovimentodia.get
                    (tblMovimentodia.getSelectedRow()).getIdmovimento());
                JOptionPane.showMessageDialog(null, "Pagamento feito por: " + clientepagamento,"Bragança", JOptionPane.INFORMATION_MESSAGE);
                //tblMovimento.clearSelection();
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
              
       DefaultTableModel modelo = (DefaultTableModel) tblMovimentodia.getModel();
       modelo.setNumRows(0);
              //mnCaixa.setEnabled(true);
              //mnFecharcaixa.setEnabled(false);
              //frmmovimento = new frmMovimento();
              //dtpDescktop.add(frmmovimento);
              //frmmovimento.setPosicao();
              
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
                     btnNovo.requestFocus();
                   for(Entradas entradas : selecionasaidanula1){
                       //if(entradas.getIdusuario() == idusuario){
                           txtAtendentecaixa.setText("Caixa: " + entradas.getUsuario());
                           txtNotasinicio.setText("Notas: " + String.format("%,.2f", entradas.getValorinicialcedula()));
                           txtMoedasinicio.setText("Moedas: " + String.format("%,.2f", entradas.getValorinicialmoedas()));
                           txtCaixainicial.setText("Início: " + String.format("%,.2f", entradas.getValorinicialcedula() + entradas.getValorinicialmoedas()));
                           frmmovimento.recebemovidponto(entradas.getIdponto());
                           idpontoentrada = entradas.getIdusuario();
                           //data = formatbr.format(entradas.getData());
                           //String[] agoradiv = agora.split("\\.");
                           //agora = String.format("%02d/%02d/%02d", Integer.parseInt(agoradiv[0])
                                                                // , Integer.parseInt(agoradiv[1])
                                                                // , Integer.parseInt(agoradiv[2]));
                      //}
            }
        }
              
    }//GEN-LAST:event_formInternalFrameClosing

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        
        dtpDescktop.remove(this);
    }//GEN-LAST:event_formInternalFrameClosed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser dtcMovimentodia;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable tblMovimentodia;
    public static javax.swing.JTextField txtAtendentecaixadia;
    public static javax.swing.JTextField txtRelogiodia;
    // End of variables declaration//GEN-END:variables
}
