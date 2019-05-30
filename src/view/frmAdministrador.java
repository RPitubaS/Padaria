/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;


import java.awt.Dimension;
import java.awt.Toolkit;
import static java.lang.Thread.sleep;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import modelo.bean.Datas;
import modelo.bean.Entradas;
import modelo.bean.Usuario;
import modelo.dao.MovimentoDAO;
import modelo.dao.UsuariosDAO;
import produzconexao.RefazerConexao;
import util.EntradaNovo;
import util.GerenciadordeJanelas;
import util.GuardarUrl;
import util.SelecionandoReservaDeCaixa;
import static view.frmMovimento.btnExcluir;
import static view.frmMovimento.btnReservarcaixa;
import static view.frmMovimento.ftxtValor;
import static view.frmMovimento.tblMovimento;
import static view.frmMovimento.txtAtendentecaixa;
import static view.frmMovimento.txtCaixainicial;
import static view.frmMovimento.txtMoedasinicio;
import static view.frmMovimento.txtNotasinicio;
import static view.frmMovimento.txtVendas;
import static view.frmPrincipal.btnAdministrador;
import static view.frmPrincipal.btnCaixa;
import static view.frmPrincipal.btnEntrar;
import static view.frmPrincipal.btnFecharAdmin;
import static view.frmPrincipal.btnLogin;
import static view.frmPrincipal.dtpDescktop;
import static view.frmPrincipal.mnCaixa;
import static view.frmPrincipal.mnEntrar;
import static view.frmPrincipal.mnFecharEntrar;
import static view.frmPrincipal.mnFecharNovousuario;
import static view.frmPrincipal.mnFecharRelatorios;
import static view.frmPrincipal.mnFecharcaixa;
import static view.frmPrincipal.mnMovimento;
import static view.frmPrincipal.mnNovousuario;
import static view.frmPrincipal.mnRelatorios;
import static view.frmReservaDeCaixa.ftxtConfirmanotasreservadas;
import static view.frmPrincipal.btnRelatorio;


/**
 * @author Pituba
 */
public class frmAdministrador extends javax.swing.JInternalFrame {

   GerenciadordeJanelas gerenciadordejanelas = new GerenciadordeJanelas(dtpDescktop);
   //frmMovimento frmmovimento; //= new frmMovimento();
   //frmMovimentodia frmmovimentodia;
   GuardarUrl guardarurl = new GuardarUrl();
    private static frmAdministrador frmadministrador;
    private static JDesktopPane jdesktoppane;
    String nomeusuario = "", tipousuario = "";
    int iddata;
    Date datahoje;
    int idusuario;                                        
    String agora, horaagora, hoje, dataagora;
    
   
    public static frmAdministrador getInstancia(){
          if(frmadministrador == null){
             frmadministrador = new frmAdministrador();
          }
        return frmadministrador;
    }
    
    public frmAdministrador() {
        initComponents();
        btnAdmLogEntrar.setEnabled(true);   
        
    }   
    
    
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblNome = new javax.swing.JLabel();
        txtAdmLognickentrar = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnAdmLogEntrar = new javax.swing.JButton();
        txtAdmLogsenhaentrar = new javax.swing.JPasswordField();

        setClosable(true);
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
        getContentPane().setLayout(null);

        jPanel2.setBackground(new java.awt.Color(255, 153, 0));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/customer_person_people_woman_you_1627.png"))); // NOI18N

        jPanel3.setBackground(new java.awt.Color(255, 255, 204));

        lblNome.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNome.setForeground(new java.awt.Color(0, 102, 255));
        lblNome.setText("Nome:");

        txtAdmLognickentrar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtAdmLognickentrar.setToolTipText("Informe o Nome");
        txtAdmLognickentrar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAdmLognickentrarKeyPressed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 102, 204));
        jLabel7.setText("Senha:");

        jLabel8.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 102, 204));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Adm Log");

        btnAdmLogEntrar.setBackground(new java.awt.Color(255, 153, 0));
        btnAdmLogEntrar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAdmLogEntrar.setText("Entrar");
        btnAdmLogEntrar.setToolTipText("Click aqui para logar");
        btnAdmLogEntrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdmLogEntrarActionPerformed(evt);
            }
        });
        btnAdmLogEntrar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAdmLogEntrarKeyPressed(evt);
            }
        });

        txtAdmLogsenhaentrar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtAdmLogsenhaentrar.setToolTipText("Informe a Senha");
        txtAdmLogsenhaentrar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAdmLogsenhaentrarKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 117, Short.MAX_VALUE)
                .addComponent(btnAdmLogEntrar, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(94, 94, 94))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtAdmLognickentrar)
                            .addComponent(txtAdmLogsenhaentrar, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAdmLognickentrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNome, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAdmLogsenhaentrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(btnAdmLogEntrar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                .addGap(121, 121, 121))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(175, 175, 175))
        );

        getContentPane().add(jPanel2);
        jPanel2.setBounds(0, 0, 377, 440);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdmLogEntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdmLogEntrarActionPerformed
       
            String usuario = "", senha = "";
            String data = "";
            int iddousuario = 0;
            SimpleDateFormat formatbr = new SimpleDateFormat("dd/MM/yyyy");
            
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
                    hoje = String.format("%02d.%02d.%02d", day, month, year);
                    agora = String.format("%02d.%02d.%02d", day, month, year);
                    horaagora = String.format("%02d:%02d:%02d", hour, minute, second);
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
        
        if(!txtAdmLognickentrar.getText().equals("") && !String.valueOf(txtAdmLogsenhaentrar.getPassword()).equals("")){
        
        RefazerConexao rfc = new RefazerConexao();
        rfc.refazerconexao();
        List<Usuario> selecionandousuario = new ArrayList<>();
        UsuariosDAO usdao = new UsuariosDAO();
        selecionandousuario = usdao.selecionarusuario(txtAdmLognickentrar.getText());

        for(Usuario usuarios : selecionandousuario){
                       idusuario = usuarios.getId();
                       nomeusuario = usuarios.getUsuario();
                       senha = usuarios.getSenha();
                       tipousuario = usuarios.getAdmin();
                      }

        if(senha.equals(String.valueOf(txtAdmLogsenhaentrar.getPassword()))){
           txtAdmLognickentrar.setText("");
           txtAdmLogsenhaentrar.setText("");
           btnAdmLogEntrar.setEnabled(true);
           btnExcluir.setEnabled(false);
           
           //JOptionPane.showMessageDialog(null, dtpDescktop.getComponentListeners());
           if(tipousuario.equals("sim")){
              if(!btnEntrar.isEnabled() && !btnLogin.isEnabled()){
                   tblMovimento.setEnabled(false);
                   ftxtValor.setEnabled(false);
                   btnReservarcaixa.setEnabled(false);
              }
              btnFecharAdmin.setEnabled(true);
              btnCaixa.setEnabled(true);
              mnCaixa.setEnabled(true);
              mnFecharcaixa.setEnabled(false);              
              mnNovousuario.setEnabled(false);       
              mnFecharNovousuario.setEnabled(false);
              mnEntrar.setEnabled(false);
              mnFecharEntrar.setEnabled(false);
              btnLogin.setEnabled(false);
              btnEntrar.setEnabled(false);             
              btnRelatorio.setEnabled(true);
              mnRelatorios.setEnabled(true);
              mnFecharRelatorios.setEnabled(false);
              btnAdministrador.setEnabled(false);
              mnMovimento.setEnabled(true);
           }
                   //this.dispose();
                   
                   
                   this.dispose();                         
        }else{
                    JOptionPane.showMessageDialog(null, "Usuário e/ou senha não conferem!");
                    txtAdmLognickentrar.setText("");
                    txtAdmLogsenhaentrar.setText("");
                    txtAdmLognickentrar.requestFocus(true);
                   }
     }else{
           txtAdmLognickentrar.requestFocus(true);
     }
    }//GEN-LAST:event_btnAdmLogEntrarActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
                
    }//GEN-LAST:event_formInternalFrameClosing

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
       
    }//GEN-LAST:event_formInternalFrameActivated

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
       
    }//GEN-LAST:event_formInternalFrameOpened

    private void txtAdmLognickentrarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAdmLognickentrarKeyPressed
        if(evt.getKeyCode()== evt.VK_ENTER){
                txtAdmLogsenhaentrar.requestFocus();
        }
    }//GEN-LAST:event_txtAdmLognickentrarKeyPressed

    private void txtAdmLogsenhaentrarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAdmLogsenhaentrarKeyPressed
        if(evt.getKeyCode()== evt.VK_ENTER){
                btnAdmLogEntrar.requestFocus();
        }
    }//GEN-LAST:event_txtAdmLogsenhaentrarKeyPressed

    private void btnAdmLogEntrarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAdmLogEntrarKeyPressed
        if(!txtAdmLognickentrar.getText().equals("") && !String.valueOf(txtAdmLogsenhaentrar.getPassword()).equals("")){
           if(evt.getKeyCode()== evt.VK_ENTER){
                btnAdmLogEntrar.doClick();
        }
        }
    }//GEN-LAST:event_btnAdmLogEntrarKeyPressed

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        dtpDescktop.remove(this);
    }//GEN-LAST:event_formInternalFrameClosed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnAdmLogEntrar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblNome;
    public static javax.swing.JTextField txtAdmLognickentrar;
    public static javax.swing.JPasswordField txtAdmLogsenhaentrar;
    // End of variables declaration//GEN-END:variables
}
