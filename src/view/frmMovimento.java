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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.lang.Thread.sleep;
import java.sql.Date;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.ParseException;
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
import util.CapturarTeclado;
import util.EntradaNovo;
import util.FecharCaixa;
import util.GerenciadordeJanelas;
import static view.frmPrincipal.btnCaixa;
import static view.frmPrincipal.btnEntrar;
import static view.frmPrincipal.btnLogin;
import static view.frmPrincipal.dtpDescktop;
import static view.frmPrincipal.mnCaixa;
import static view.frmPrincipal.mnEntrar;
import static view.frmPrincipal.mnFecharEntrar;
import static view.frmPrincipal.mnFecharNovousuario;
import static view.frmPrincipal.mnFecharcaixa;
import static view.frmPrincipal.mnNovousuario;
import static view.frmEntrar.btnLogEntrar;
import static view.frmPrincipal.dtpDescktop;


/**
 *
 * @author Pituba
 */
public class frmMovimento extends javax.swing.JInternalFrame {
    private frmEntrar veiodoentrar;
    GerenciadordeJanelas gerenciadordejanelas;
    private static frmMovimento frmmovimento; 
    int idponto;
    int movidponto = 0, idmovimento = 0;
    String horaagora, datahoje, motivopagamento, clienteparaentrega, clienterecebprazo, funcionariovale,
           motivosaque;
    float vendaavista, entrega, recebimentoprazo, cartao, vale, saque, pagamentos, movimento, encerrarmovimento;
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
         //this.setLocation((dimensao.width - this.getSize().width),
                         //(dimensao.height - this.getSize().height));
           this.setSize(dimensao);
               this.toFront();
               
    }
    
    public frmMovimento() {
        initComponents();
        currentTime();
        ftxtValor.setEnabled(false);
        rbAvista.setEnabled(false);
        rbEntrega.setEnabled(false);
        rbAprazo.setEnabled(false);
        rbCartao.setEnabled(false);
        rbSaque.setEnabled(false);
        rbVale.setEnabled(false);
        rbPagamentos.setEnabled(false);
        rdDiferencaNeg.setEnabled(false);
        btnExcluir.setEnabled(false);
        btnNovo.requestFocus(true);
        rbAvista.setSelected(true);
        lblValor.setEnabled(false);
        lblFormadepagamento.setEnabled(false);
        btnNovo.requestFocus();
        btnFecharMovimento.setVisible(false);
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
        } 
     
        Object[] dados1 = {"Caixa inicial:", "0,00", "0,00", "0,00", "0,00", "0,00", "0,00", "0,00",
                           "0,00"},
                 dados = {"Total:", "", "", "", "", "", "", "", ""};
        modelo.insertRow(modelo.getRowCount(), dados1);
        modelo.insertRow(modelo.getRowCount(), dados);

        String pd = txtCaixainicial.getText().substring(8, txtCaixainicial.getText().length());
        modelo.setValueAt(pd, modelo.getRowCount() - 2, 7);
        
       // DecimalFormat obj_formato = new DecimalFormat();
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
                                encerrarmovimento = movimento;
                               //}
                            }
                            
                       }
    
    public void cornalinha(){
             //String mudacordelinha = ct;
              
             DefaultTableCellRenderer rightrenderer = new DefaultTableCellRenderer();
             DefaultTableCellRenderer rightrenderer1 = new DefaultTableCellRenderer();
             
             tblMovimento.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){ 
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
                                 tblMovimento.getColumnModel().getColumn(1).setCellRenderer(rightrenderer);
                                 tblMovimento.getColumnModel().getColumn(2).setCellRenderer(rightrenderer);
                                 tblMovimento.getColumnModel().getColumn(3).setCellRenderer(rightrenderer);
                                 tblMovimento.getColumnModel().getColumn(4).setCellRenderer(rightrenderer);
                                 tblMovimento.getColumnModel().getColumn(5).setCellRenderer(rightrenderer);
                                 tblMovimento.getColumnModel().getColumn(6).setCellRenderer(rightrenderer);
                                 tblMovimento.getColumnModel().getColumn(7).setCellRenderer(rightrenderer);
                                 tblMovimento.getColumnModel().getColumn(8).setCellRenderer(rightrenderer1);
                                 
                                  //label.setForeground(c);
                                 //label.setFont(new Font("Tahoma", Font.BOLD,11));                                  
                                  return label;
                                   
                              }                            
                       }); 
    }
    
    public void excluir(){
         
        RefazerConexao rfc = new RefazerConexao();
        rfc.refazerconexao();
        MovimentoDAO movdao = new MovimentoDAO();
   
        if(tblMovimento.getSelectedRow()!= -1 && tblMovimento.getSelectedRow()!= tblMovimento.getRowCount() - 2){
           if(tblMovimento.getSelectedRow()!= -1 && tblMovimento.getSelectedRow()!= (tblMovimento.getRowCount() - 1)){
            int resultconfirm = JOptionPane.showConfirmDialog(null, "Confirma a exclusão de R$ "
                + (String) tblMovimento.getValueAt(tblMovimento.getSelectedRow(), 7) + 
                " do seu arguivo de origem?",
                "Bragança", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(resultconfirm == 0){              
                   movdao.excluir_entrada(selecionamovimentousuario.get(tblMovimento.getSelectedRow()).getIdmovimento());
                   RefazerConexao rfc1 = new RefazerConexao();
                   rfc1.refazerconexao();
                   lertabela();
                   }else{
                   tblMovimento.setSelectionMode(0);
            }
            btnExcluir.setEnabled(false);
           }
        }
       //btnNovo.setEnabled(true);
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
        jPanel2 = new javax.swing.JPanel();
        rbAvista = new javax.swing.JRadioButton();
        rbAprazo = new javax.swing.JRadioButton();
        rbCartao = new javax.swing.JRadioButton();
        rbPagamentos = new javax.swing.JRadioButton();
        lblValor = new javax.swing.JLabel();
        lblFormadepagamento = new javax.swing.JLabel();
        btnNovo = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        ftxtValor = new javax.swing.JFormattedTextField();
        rbEntrega = new javax.swing.JRadioButton();
        rbVale = new javax.swing.JRadioButton();
        rbSaque = new javax.swing.JRadioButton();
        btnFecharcaixa = new javax.swing.JButton();
        btnFecharMovimento = new javax.swing.JButton();
        rdDiferencaNeg = new javax.swing.JRadioButton();
        rdDiferencaPos = new javax.swing.JRadioButton();

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

        txtRelogio.setEditable(false);
        txtRelogio.setBackground(new java.awt.Color(255, 153, 0));
        txtRelogio.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtRelogio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtRelogio.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtAtendentecaixa.setBackground(new java.awt.Color(255, 153, 0));
        txtAtendentecaixa.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtAtendentecaixa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAtendentecaixa.setText("Caixa:");
        txtAtendentecaixa.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtCaixainicial.setBackground(new java.awt.Color(255, 153, 0));
        txtCaixainicial.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtCaixainicial.setText("Início: ");
        txtCaixainicial.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("=");

        txtNotasinicio.setBackground(new java.awt.Color(255, 153, 0));
        txtNotasinicio.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtNotasinicio.setText("Notas: ");
        txtNotasinicio.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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

        lblFormadepagamento.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblFormadepagamento.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFormadepagamento.setText("Forma de Pagamento:");

        btnNovo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cash_icon-icons.com_51028.png"))); // NOI18N
        btnNovo.setText("Novo");
        btnNovo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnNovoFocusGained(evt);
            }
        });
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });
        btnNovo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnNovoKeyPressed(evt);
            }
        });

        btnExcluir.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete_21455.png"))); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.setEnabled(false);
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        ftxtValor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        ftxtValor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ftxtValor.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        ftxtValor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                ftxtValorFocusLost(evt);
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

        btnFecharMovimento.setText("jButton1");
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btnFecharcaixa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbAprazo)
                    .addComponent(rbAvista))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbVale, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rbSaque))
                        .addGap(54, 54, 54)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbCartao)
                            .addComponent(rdDiferencaNeg)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(rbPagamentos, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdDiferencaPos)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(lblValor, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ftxtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnFecharMovimento)))
                .addGap(33, 33, 33))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblFormadepagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(358, 358, 358))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(lblFormadepagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblValor, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(ftxtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(13, 13, 13))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(rbAvista)
                                .addComponent(rbVale)
                                .addComponent(rbCartao))
                            .addGap(5, 5, 5)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(rbEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(rbSaque)
                                .addComponent(rdDiferencaNeg))
                            .addGap(7, 7, 7)))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnFecharcaixa, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnExcluir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                            .addComponent(btnNovo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbAprazo)
                    .addComponent(rbPagamentos)
                    .addComponent(rdDiferencaPos))
                .addContainerGap(21, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnFecharMovimento)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtRelogio, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(txtAtendentecaixa, javax.swing.GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(txtCaixainicial, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNotasinicio, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMoedasinicio, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane1)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtRelogio)
                            .addComponent(txtAtendentecaixa)
                            .addComponent(txtMoedasinicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNotasinicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCaixainicial))
                        .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2))
                .addGap(3, 3, 3)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
    
    public void recebemovidponto( int movidponto){
        this.movidponto = movidponto;
        RefazerConexao rfc = new RefazerConexao();
        rfc.refazerconexao();
        lertabela();
        alinhamentocelula();
    }
    
    public void botaonovo(){
    
        ftxtValor.setText("");
        ftxtValor.setEnabled(true);
        rbAvista.setEnabled(true);
        rbEntrega.setEnabled(true);
        rbAprazo.setEnabled(true);
        rbCartao.setEnabled(true);
        rbSaque.setEnabled(true);
        rbVale.setEnabled(true);
        rbPagamentos.setEnabled(true);
        rdDiferencaNeg.setEnabled(true);
        rbAvista.setSelected(true);
        rbAvista.requestFocus(true);
        btnNovo.setEnabled(false);
        btnExcluir.setEnabled(false);
        lblValor.setEnabled(true);
        lblFormadepagamento.setEnabled(true);
        ftxtValor.setEnabled(true);
        rbAvista.requestFocus(true);
        ftxtValor.setText("");
        btnFecharcaixa.setEnabled(false);
        tblMovimento.clearSelection();
    
    }
    private void txtDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataActionPerformed

    private void rbCartaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbCartaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbCartaoActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed

        botaonovo();
        
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        excluir();                
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void ftxtValorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ftxtValorFocusLost
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
        //DecimalFormat df = new DecimalFormat();
            df.applyPattern("##,##0.00");
        RefazerConexao rfz = new RefazerConexao();
        rfz.refazerconexao();
        List<Entradas> selecionaultimoponto = new ArrayList<>();
        MovimentoDAO movimdao = new MovimentoDAO();
        selecionaultimoponto = movimdao.selecionarultimoponto();
            for(Entradas entradas : selecionaultimoponto){
               movidponto = entradas.getIdusuario();                            
            }
       
        switch(Integer.parseInt(buttonGroup2.getSelection().getActionCommand())){
            case 1:
                //horaagora = txtRelogio.getText();
                vendaavista = Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                entrega = 0;
                recebimentoprazo = 0;
                cartao = 0;
                vale = 0;
                saque = 0;
                pagamentos = 0;
                movimento = Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                break;
            case 2:
                //horaagora = txtRelogio.getText();
                vendaavista = 0;
                entrega = Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                recebimentoprazo = 0;
                cartao = 0;
                vale = 0;
                saque = 0;
                pagamentos = 0;
                movimento = Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                do{
                clienteparaentrega = JOptionPane.showInputDialog(null, "Por favor digite aqui, o nome\n"
                                                                       + "do cliente para entrega.");
                }while("".equals(clienterecebprazo));
                break;
            case 3:
                //horaagora = txtRelogio.getText();
                vendaavista = 0;
                entrega = 0;
                recebimentoprazo = Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                cartao = 0;
                vale = 0;
                saque = 0;
                pagamentos = 0;
                movimento = Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                do{
                clienterecebprazo = JOptionPane.showInputDialog(null, "Por favor digite aqui o nome cliente.");
                }while("".equals(clienterecebprazo));
                break;
            case 4:
                //horaagora = txtRelogio.getText();
                vendaavista = 0;
                entrega = 0;
                recebimentoprazo = 0;
                cartao = Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                vale = 0;
                saque = 0;
                pagamentos = 0;
                movimento = 0;
                break;
            case 5:
                //horaagora = txtRelogio.getText();
                vendaavista = 0;
                entrega = 0;
                recebimentoprazo = 0;
                cartao = 0;
                vale = Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                saque = 0;
                pagamentos = 0;
                movimento = - Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                do{
                funcionariovale = JOptionPane.showInputDialog(null, "Por favor digite aqui para quem é o vale.");
                }while("".equals(funcionariovale));
                break;
            case 6:
                //horaagora = txtRelogio.getText();
                vendaavista = 0;
                entrega = 0;
                recebimentoprazo = 0;
                cartao = 0;
                vale = 0;
                saque = Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                pagamentos = 0;
                movimento = - Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                do{
                motivosaque = JOptionPane.showInputDialog(null, "Por favor digite aqui o motivo do saque.");
                }while("".equals(motivosaque));
                break;
            case 7:
                //horaagora = txtRelogio.getText();
                vendaavista = 0;
                entrega = 0;
                recebimentoprazo = 0;
                cartao = 0;
                vale = 0;
                saque = 0;
                pagamentos = Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                movimento = - Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                do{
                motivopagamento = JOptionPane.showInputDialog(null, "Por favor digite aqui o que foi pago com este valor.");            
                }while("".equals(motivopagamento));
                break;
            case 8:
                //horaagora = txtRelogio.getText();
                vendaavista = - Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                entrega = 0;
                recebimentoprazo = 0;
                cartao = 0;
                vale = 0;
                saque = 0;
                pagamentos = 0;
                movimento = - Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                break;
            case 9:
                //horaagora = txtRelogio.getText();
                vendaavista =  Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
                entrega = 0;
                recebimentoprazo = 0;
                cartao = 0;
                vale = 0;
                saque = 0;
                pagamentos = 0;
                movimento =  Float.parseFloat(ftxtValor.getText().replaceAll("\\.", "").replaceAll(",","."));
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
                                       entrega, recebimentoprazo, cartao, vale, saque, pagamentos, movimento); 
        
                                       if(motivopagamento != null){ 
                                          RefazerConexao rfc1 = new RefazerConexao();
                                          rfc1.refazerconexao();
                                          idmovimento = movdao.selecionarmaxmovimento(movidponto);
                                          RefazerConexao rfc2 = new RefazerConexao();
                                          rfc2.refazerconexao();
                                          movdao.salvar_mot_pagt(idmovimento, motivopagamento);
                                          motivopagamento = null;
                                       }
                                       
                                       if(clienterecebprazo != null){
                                          RefazerConexao rfc1 = new RefazerConexao();
                                          rfc1.refazerconexao();
                                          idmovimento = movdao.selecionarmaxmovimento(movidponto);
                                          RefazerConexao rfc2 = new RefazerConexao();
                                          rfc2.refazerconexao();
                                          movdao.salvar_recebprazo(idmovimento, clienterecebprazo);
                                          clienterecebprazo = null;
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
                ftxtValor.setEnabled(false);
                rbAvista.setEnabled(false);
                rbEntrega.setEnabled(false);
                rbAprazo.setEnabled(false);
                rbCartao.setEnabled(false);
                rbVale.setEnabled(false);
                rbSaque.setEnabled(false);
                rdDiferencaNeg.setEnabled(false);
                rdDiferencaPos.setEnabled(false);
                rbPagamentos.setEnabled(false);
                btnExcluir.setEnabled(false);
                lblValor.setEnabled(false);
                lblFormadepagamento.setEnabled(false);
                btnNovo.setEnabled(true);
                rbAvista.setSelected(true);
                btnFecharcaixa.setEnabled(true);
                btnNovo.requestFocus();
        //if(!ftxtValor.getText().equals("") && !ftxtValor.getText().equals("0,00") && !ftxtValor.getText().equals("0")){
           //JOptionPane.showMessageDialog(null, "Correto " + ftxtValor.getText());
        //}else{
           //JOptionPane.showMessageDialog(null, "Errado");
        //}
      }
    }//GEN-LAST:event_ftxtValorFocusLost

    private void tblMovimentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMovimentoMouseClicked
        String motivopagamento = null, saquefuncionario = null, valefuncionario = null,
               clientepagamento = null, clienteentrega = null;
        btnExcluir.setEnabled(true);
        //btnNovo.setEnabled(false);
        if(tblMovimento.getSelectedRow() != -1 && tblMovimento.getSelectedRow() != (tblMovimento.getRowCount() -1)
           && tblMovimento.getSelectedRow() != tblMovimento.getRowCount() - 2){
            if(selecionamovimentousuario.get(tblMovimento.getSelectedRow()).getMovimento() < 0
               && selecionamovimentousuario.get(tblMovimento.getSelectedRow()).getPagamentos() > 0){
               RefazerConexao rfc = new RefazerConexao();
               rfc.refazerconexao();
               MovimentoDAO movdao = new MovimentoDAO();
               motivopagamento = movdao.selecionamotivopagto(selecionamovimentousuario.get
                                                            (tblMovimento.getSelectedRow()).getIdmovimento());
               JOptionPane.showMessageDialog(null, "Pagamento de: " + motivopagamento,"Bragança", JOptionPane.INFORMATION_MESSAGE);
               //tblMovimento.clearSelection();
            }
            if(selecionamovimentousuario.get(tblMovimento.getSelectedRow()).getMovimento() < 0
               && selecionamovimentousuario.get(tblMovimento.getSelectedRow()).getVale() > 0){
               RefazerConexao rfc = new RefazerConexao();
               rfc.refazerconexao();
               MovimentoDAO movdao = new MovimentoDAO();
               valefuncionario = movdao.selecionafunvionariovale(selecionamovimentousuario.get
                                                            (tblMovimento.getSelectedRow()).getIdmovimento());
               JOptionPane.showMessageDialog(null, "Vale feito por: " + valefuncionario,"Bragança", JOptionPane.INFORMATION_MESSAGE);
               //tblMovimento.clearSelection();
            }
            if(selecionamovimentousuario.get(tblMovimento.getSelectedRow()).getMovimento() < 0
               && selecionamovimentousuario.get(tblMovimento.getSelectedRow()).getSaque() > 0){
               RefazerConexao rfc = new RefazerConexao();
               rfc.refazerconexao();
               MovimentoDAO movdao = new MovimentoDAO();
               saquefuncionario = movdao.selecionafuncionariosaque(selecionamovimentousuario.get
                                                            (tblMovimento.getSelectedRow()).getIdmovimento());
               JOptionPane.showMessageDialog(null, "Saque feito por: " + saquefuncionario,"Bragança", JOptionPane.INFORMATION_MESSAGE);
               //tblMovimento.clearSelection();
            }
            if(selecionamovimentousuario.get(tblMovimento.getSelectedRow()).getMovimento() > 0
               && selecionamovimentousuario.get(tblMovimento.getSelectedRow()).getRecebapraso() > 0){
               RefazerConexao rfc = new RefazerConexao();
               rfc.refazerconexao();
               MovimentoDAO movdao = new MovimentoDAO();
               clientepagamento = movdao.selecionaclientepagamento(selecionamovimentousuario.get
                                                            (tblMovimento.getSelectedRow()).getIdmovimento());
               JOptionPane.showMessageDialog(null, "Pagamento feito por: " + clientepagamento,"Bragança", JOptionPane.INFORMATION_MESSAGE);
               //tblMovimento.clearSelection();
            }
            if(selecionamovimentousuario.get(tblMovimento.getSelectedRow()).getMovimento() > 0
               && selecionamovimentousuario.get(tblMovimento.getSelectedRow()).getEntrega() > 0){
               RefazerConexao rfc = new RefazerConexao();
               rfc.refazerconexao();
               MovimentoDAO movdao = new MovimentoDAO();
               clienteentrega = movdao.selecionaclienteentrega(selecionamovimentousuario.get
                                                            (tblMovimento.getSelectedRow()).getIdmovimento());
               JOptionPane.showMessageDialog(null, "Pagamento feito por: " + clienteentrega,"Bragança", JOptionPane.INFORMATION_MESSAGE);
               //tblMovimento.clearSelection();
            }
        }
    }//GEN-LAST:event_tblMovimentoMouseClicked

    private void ftxtValorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ftxtValorKeyPressed
        if(evt.getKeyCode()== evt.VK_ENTER){
             if(!ftxtValor.getText().equals("")){
                btnNovo.requestFocus();
                rbAvista.setEnabled(false);
             }else{
                 ftxtValor.requestFocus();
             }
        }
    }//GEN-LAST:event_ftxtValorKeyPressed

    private void rbAvistaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbAvistaKeyPressed
        if(evt.getKeyCode()== evt.VK_ENTER){
                ftxtValor.setEnabled(true);
                ftxtValor.requestFocus(true);
        }else{
              if(evt.getKeyCode() == evt.VK_SPACE){
                 rbEntrega.requestFocus(true);
                 rbEntrega.setSelected(true);   
              }
        }
    }//GEN-LAST:event_rbAvistaKeyPressed

    private void rbAprazoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbAprazoKeyPressed
        if(evt.getKeyCode()== evt.VK_ENTER){
                ftxtValor.setEnabled(true);
                ftxtValor.requestFocus(true);
        }else{
              if(evt.getKeyCode() == evt.VK_SPACE){
                 rbVale.requestFocus(true);
                 rbVale.setSelected(true);   
              }
        }
    }//GEN-LAST:event_rbAprazoKeyPressed

    private void rbCartaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbCartaoKeyPressed
        if(evt.getKeyCode()== evt.VK_ENTER){
                ftxtValor.setEnabled(true);
                ftxtValor.requestFocus(true);
        }else{
              if(evt.getKeyCode() == evt.VK_SPACE){
                 rdDiferencaNeg.requestFocus(true);
                 rdDiferencaNeg.setSelected(true);   
              }
        }
    }//GEN-LAST:event_rbCartaoKeyPressed

    private void rbPagamentosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbPagamentosKeyPressed
        if(evt.getKeyCode()== evt.VK_ENTER){
                ftxtValor.setEnabled(true);
                ftxtValor.requestFocus(true);
        }else{
              if(evt.getKeyCode() == evt.VK_SPACE){
                 rbCartao.requestFocus(true);
                 rbCartao.setSelected(true);   
              }
        }
    }//GEN-LAST:event_rbPagamentosKeyPressed

    private void btnNovoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnNovoKeyPressed
        if(evt.getKeyCode()== evt.VK_ENTER){
                botaonovo();
        }
    }//GEN-LAST:event_btnNovoKeyPressed

    private void tblMovimentoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tblMovimentoPropertyChange
         
    }//GEN-LAST:event_tblMovimentoPropertyChange

    private void tblMovimentoComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tblMovimentoComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_tblMovimentoComponentShown

    private void tblMovimentoVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_tblMovimentoVetoableChange
        
    }//GEN-LAST:event_tblMovimentoVetoableChange

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        
    }//GEN-LAST:event_formInternalFrameActivated

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        
    }//GEN-LAST:event_formInternalFrameOpened

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
                        
    }//GEN-LAST:event_formFocusGained

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
            mnNovousuario.setEnabled(true);
            mnFecharNovousuario.setEnabled(false);
            mnEntrar.setEnabled(true);
            mnFecharEntrar.setEnabled(false);
            btnEntrar.setEnabled(true);
            btnLogin.setEnabled(true);
            this.setVisible(false);
            dtpDescktop.remove(this);
            this.dispose();
        }else{
            btnNovo.requestFocus();
        }
        
           btnExcluir.setEnabled(false);
           tblMovimento.clearSelection();
        

        //java.sql.Date sdf;
        //java.sql.Date data;
        //df.applyPattern("R$ ##,##0.00");

        
        //SimpleDateFormat formatbr = new SimpleDateFormat("dd.MM.yyyy");
        
        //int fechacaixa = JOptionPane.showConfirmDialog(null,"Deseja realmente fechar o caixa, com o valor de: "
                                                       //+ df.format(encerrarmovimento) + " ?", "Fechamento."
                                                       //, JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
        //switch(fechacaixa){
                       //case 0:
                           
        //try {
            //data = new java.sql.Date(formatbr.parse("27.03.2019").getTime());
            //sdf = new java.sql.Date(formatbr.parse(datahoje).getTime());
                           //RefazerConexao rfc = new RefazerConexao();
                           //rfc.refazerconexao();
                           //List<Entradas> selecionasaida = new ArrayList<>();
                           //MovimentoDAO movdao = new MovimentoDAO();
                           //selecionasaida = movdao.selecionarsaida(movidponto);
                             //for(Entradas entradas : selecionasaida){
                                 //data = entradas.getData();
                             //}

                //java.sql.Time sdfhorasaida = new java.sql.Time(formatbr.parse(txtRelogio.getText()).getTime());
                     //RefazerConexao rfc1 = new RefazerConexao();
                     //rfc1.refazerconexao();
                     //MovimentoDAO movimdao = new MovimentoDAO();
                 //if(data.before(sdf)){
                     //movimdao.atualizar_ponto(movidponto, horasaida, encerrarmovimento);
                 //}else{
                     //movimdao.atualizar_ponto(movidponto, sdfhorasaida, encerrarmovimento);
                 //}
                     //this.setVisible(false);
                     //dtpDescktop.remove(this);                     
                     //this.dispose();
        //} catch (ParseException ex) {
            //JOptionPane.showMessageDialog(null, "Erro: " + ex);
        //}
                           //break;
                       //case 1:
                           //btnNovo.setEnabled(true);                          
        //try {
            //data = new java.sql.Date(formatbr.parse("27.03.2019").getTime());
            //sdf = new java.sql.Date(formatbr.parse(datahoje).getTime());
                           //RefazerConexao rfc = new RefazerConexao();
                           //rfc.refazerconexao();
                           //List<Entradas> selecionasaida = new ArrayList<>();
                           //MovimentoDAO movdao = new MovimentoDAO();
                           //selecionasaida = movdao.selecionarsaida(movidponto);
                             //for(Entradas entradas : selecionasaida){
                                 //data = entradas.getData();
                             //}

                 //if(data.before(sdf)){
                     //JOptionPane.showMessageDialog(null,"Este caixa tem data anterior a de hoje e precisa ser"
                             //+ " fechado, por favor confirme os valores e feche o caixa.", "Bragança"
                             //, JOptionPane.INFORMATION_MESSAGE);
                     //btnNovo.setEnabled(false);
                 //}
                           
        //} catch (ParseException ex) {
            //JOptionPane.showMessageDialog(null, "Erro: " + ex);
        //}
                          //break;
        //}                    
    }//GEN-LAST:event_btnFecharcaixaActionPerformed

    private void tblMovimentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblMovimentoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblMovimentoKeyPressed

    private void ftxtValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ftxtValorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ftxtValorActionPerformed

    private void btnNovoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnNovoFocusGained
        ftxtValor.setText("");
    }//GEN-LAST:event_btnNovoFocusGained

    private void rbAvistaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rbAvistaFocusLost
        ftxtValor.setText("");
    }//GEN-LAST:event_rbAvistaFocusLost

    private void rbAprazoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rbAprazoFocusLost
        ftxtValor.setText("");
    }//GEN-LAST:event_rbAprazoFocusLost

    private void rbSaqueFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rbSaqueFocusLost
        ftxtValor.setText("");
    }//GEN-LAST:event_rbSaqueFocusLost

    private void rbEntregaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rbEntregaFocusLost
        ftxtValor.setText("");
    }//GEN-LAST:event_rbEntregaFocusLost

    private void rbCartaoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rbCartaoFocusLost
        ftxtValor.setText("");
    }//GEN-LAST:event_rbCartaoFocusLost

    private void rbValeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rbValeFocusLost
       ftxtValor.setText("");
    }//GEN-LAST:event_rbValeFocusLost

    private void rbPagamentosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rbPagamentosFocusLost
        ftxtValor.setText("");
    }//GEN-LAST:event_rbPagamentosFocusLost

    private void rbAvistaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rbAvistaFocusGained
        ftxtValor.setText("");
    }//GEN-LAST:event_rbAvistaFocusGained

    private void rbAprazoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rbAprazoFocusGained
        ftxtValor.setText("");
    }//GEN-LAST:event_rbAprazoFocusGained

    private void rbSaqueFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rbSaqueFocusGained
        ftxtValor.setText("");
    }//GEN-LAST:event_rbSaqueFocusGained

    private void rbEntregaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rbEntregaFocusGained
        ftxtValor.setText("");
    }//GEN-LAST:event_rbEntregaFocusGained

    private void rbCartaoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rbCartaoFocusGained
        ftxtValor.setText("");
    }//GEN-LAST:event_rbCartaoFocusGained

    private void rbValeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rbValeFocusGained
        ftxtValor.setText("");
    }//GEN-LAST:event_rbValeFocusGained

    private void rbPagamentosFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rbPagamentosFocusGained
        ftxtValor.setText("");
    }//GEN-LAST:event_rbPagamentosFocusGained

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing

        mnNovousuario.setEnabled(true);       
        mnFecharNovousuario.setEnabled(false);
        mnEntrar.setEnabled(true);
        mnFecharEntrar.setEnabled(false);
        btnLogin.setEnabled(true);
        btnEntrar.setEnabled(true);
        btnCaixa.setEnabled(false);
        mnCaixa.setEnabled(false);
        mnFecharcaixa.setEnabled(false);
        
    }//GEN-LAST:event_formInternalFrameClosing

    private void rbEntregaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbEntregaKeyPressed
        if(evt.getKeyCode()== evt.VK_ENTER){
                ftxtValor.setEnabled(true);
                ftxtValor.requestFocus(true);
        }else{
              if(evt.getKeyCode() == evt.VK_SPACE){
                 rbAprazo.requestFocus(true);
                 rbAprazo.setSelected(true);   
              }
        }
    }//GEN-LAST:event_rbEntregaKeyPressed

    private void rbValeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbValeKeyPressed
        if(evt.getKeyCode()== evt.VK_ENTER){
                ftxtValor.setEnabled(true);
                ftxtValor.requestFocus(true);
        }else{
              if(evt.getKeyCode() == evt.VK_SPACE){
                 rbSaque.requestFocus(true);
                 rbSaque.setSelected(true);   
              }
        }
    }//GEN-LAST:event_rbValeKeyPressed

    private void rbSaqueKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbSaqueKeyPressed
        if(evt.getKeyCode()== evt.VK_ENTER){
                ftxtValor.setEnabled(true);
                ftxtValor.requestFocus(true);
        }else{
              if(evt.getKeyCode() == evt.VK_SPACE){
                 rbPagamentos.requestFocus(true);
                 rbPagamentos.setSelected(true);   
              }
        }
    }//GEN-LAST:event_rbSaqueKeyPressed

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        
    }//GEN-LAST:event_formInternalFrameClosed

    private void btnFecharMovimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharMovimentoActionPerformed
        this.setVisible(false);
        dtpDescktop.remove(this);
        this.dispose();
    }//GEN-LAST:event_btnFecharMovimentoActionPerformed

    private void rdDiferencaNegKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rdDiferencaNegKeyPressed
        if(evt.getKeyCode()== evt.VK_ENTER){
                ftxtValor.setEnabled(true);
                ftxtValor.requestFocus(true);
        }else{
              if(evt.getKeyCode() == evt.VK_SPACE){
                 rdDiferencaPos.requestFocus(true);
                 rdDiferencaPos.setSelected(true);   
              }
        }
    }//GEN-LAST:event_rdDiferencaNegKeyPressed

    private void rdDiferencaNegFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rdDiferencaNegFocusGained
       ftxtValor.setText("");
    }//GEN-LAST:event_rdDiferencaNegFocusGained

    private void rdDiferencaNegFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rdDiferencaNegFocusLost
        ftxtValor.setText("");
    }//GEN-LAST:event_rdDiferencaNegFocusLost

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
    
    }//GEN-LAST:event_formKeyPressed

    private void rdDiferencaPosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rdDiferencaPosKeyPressed
        if(evt.getKeyCode()== evt.VK_ENTER){
                ftxtValor.setEnabled(true);
                ftxtValor.requestFocus(true);
        }else{
              if(evt.getKeyCode() == evt.VK_SPACE){
                 rbAvista.requestFocus(true);
                 rbAvista.setSelected(true);   
              }
        }
    }//GEN-LAST:event_rdDiferencaPosKeyPressed

    private void rdDiferencaPosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rdDiferencaPosFocusLost
        ftxtValor.setText("");
    }//GEN-LAST:event_rdDiferencaPosFocusLost

    private void rdDiferencaPosFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rdDiferencaPosFocusGained
        ftxtValor.setText("");
    }//GEN-LAST:event_rdDiferencaPosFocusGained


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnExcluir;
    public static javax.swing.JButton btnFecharMovimento;
    public static javax.swing.JButton btnFecharcaixa;
    public static javax.swing.JButton btnNovo;
    private javax.swing.ButtonGroup buttonGroup2;
    public static javax.swing.JFormattedTextField ftxtValor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFormadepagamento;
    private javax.swing.JLabel lblValor;
    private javax.swing.JRadioButton rbAprazo;
    private javax.swing.JRadioButton rbAvista;
    private javax.swing.JRadioButton rbCartao;
    private javax.swing.JRadioButton rbEntrega;
    private javax.swing.JRadioButton rbPagamentos;
    private javax.swing.JRadioButton rbSaque;
    private javax.swing.JRadioButton rbVale;
    private javax.swing.JRadioButton rdDiferencaNeg;
    private javax.swing.JRadioButton rdDiferencaPos;
    public static javax.swing.JTable tblMovimento;
    public static javax.swing.JTextField txtAtendentecaixa;
    public static javax.swing.JTextField txtCaixainicial;
    private javax.swing.JTextField txtData;
    public static javax.swing.JTextField txtMoedasinicio;
    public static javax.swing.JTextField txtNotasinicio;
    public static javax.swing.JTextField txtRelogio;
    // End of variables declaration//GEN-END:variables
}
