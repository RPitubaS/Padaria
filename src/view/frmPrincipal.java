/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

//import controle.ControleMovimento;
import controle.ControleMovimento;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import modelo.bean.Usuario;
import modelo.dao.UrlDao;
import modelo.dao.UsuariosDAO;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JRViewer;
import produzconexao.ConexaoFirebird;
import produzconexao.RefazerConexao;
import util.GerenciadordeJanelas;
import util.GuardarUrl;
import static view.frmEntrar.txtLognickentrar;
import static view.frmLogin.btnCadastro;
import static view.frmLogin.btnNovoEntrar;
import static view.frmLogin.cbxAdministrador;
import static view.frmLogin.txtConfsenha;
import static view.frmLogin.txtLognick;
import static view.frmLogin.txtNome;
import static view.frmLogin.txtSenha;
import static view.frmLogin.txtLogsenha;
import static view.frmMovimento.btnFecharMovimento;
import static view.frmMovimentodia.btnMovimentododiaSair;


/**
 *
 * @author Pituba
 */
public class frmPrincipal extends javax.swing.JFrame {
    GuardarUrl guardarurl = new GuardarUrl();
    GerenciadordeJanelas gerenciadordejanelas;
    frmLogin frmlogin = new frmLogin();
    frmMovimento frmusuario = new frmMovimento();
    frmMovimentodia frmmovimentodia;
    frmEntrar frmentrar = new frmEntrar();
    ControleMovimento controlemovimento = new ControleMovimento();
    frmAdministrador administrador = new frmAdministrador();
    public frmPrincipal() {
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
        this.gerenciadordejanelas = new GerenciadordeJanelas(dtpDescktop);
        guardarurl.setContador(0);
        String resultado = guardarurl.GetProp("conectar");       
        try {
            if (!resultado.equals("")) {
                ConexaoFirebird conect = new ConexaoFirebird(resultado);
            } else {
                UrlDao url = new UrlDao();
                url.pegaurl();
            }
        } catch (ClassNotFoundException ex) {
            UrlDao url = new UrlDao();
            url.pegaurl();
        } catch (SQLException ex) {
            System.exit(0);
        }  
        
        List<Usuario> selecionandousuario = new ArrayList<>();
        UsuariosDAO usdao = new UsuariosDAO();
        selecionandousuario = usdao.selecionaradmin();
        if(!selecionandousuario.isEmpty()){
           abrirentrar(); 
           txtLognickentrar.requestFocus();
           mnEntrar.setEnabled(false);
           mnFecharEntrar.setEnabled(true);
           mnNovousuario.setEnabled(true);
           mnFecharNovousuario.setEnabled(false);
           btnEntrar.setEnabled(false);

           if(frmLogin.getInstancia().isSelected()){
              gerenciadordejanelas.fecharjanelas(frmLogin.getInstancia());
           }
        }
        if(selecionandousuario.isEmpty()){
            abrirlogin();
            txtLognick.requestFocus();
            cbxAdministrador.setSelected(false);
            txtNome.setEnabled(true);
            txtNome.requestFocus();
            txtSenha.setEnabled(true);
            txtConfsenha.setEnabled(true);
            cbxAdministrador.setEnabled(true);
            btnCadastro.setEnabled(true);
            txtLognick.setText("");
            txtLogsenha.setText("");
            frmlogin.setClosable(true);
            mnNovousuario.setEnabled(false);
            mnFecharNovousuario.setEnabled(true);
            mnEntrar.setEnabled(true);
            mnFecharEntrar.setEnabled(false);
            btnEntrar.setEnabled(false);
            btnLogin.setEnabled(false);
            
            RefazerConexao rfcinicio = new RefazerConexao();
            rfcinicio.refazerconexao();
            List<Usuario> selecionandoadministrador = new ArrayList<>();
            UsuariosDAO admindao = new UsuariosDAO();
            selecionandoadministrador = admindao.selecionaradmin();
            if(selecionandoadministrador.isEmpty()){
              cbxAdministrador.setSelected(true);
              cbxAdministrador.setEnabled(false);
            }
            if(frmEntrar.getInstancia().isSelected()){
               gerenciadordejanelas.fecharjanelas(frmEntrar.getInstancia());
            }
           
        }
    }
    
    public void abrirlogin(){
        try {
             gerenciadordejanelas.abrirlogin(frmLogin.getInstancia());
             frmLogin.getInstancia().setSelected(true);
        } catch (PropertyVetoException ex) {
             Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void abrirentrar(){
        try {
             gerenciadordejanelas.abrirentrar(frmEntrar.getInstancia());
             frmEntrar.getInstancia().setSelected(true);
        } catch (PropertyVetoException ex) {
             Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        dtpDescktop = new javax.swing.JDesktopPane();
        tbrBarraFerramentas = new javax.swing.JToolBar();
        btnCaixa = new javax.swing.JButton();
        btnEntrar = new javax.swing.JButton();
        btnLogin = new javax.swing.JButton();
        btnAdministrador = new javax.swing.JButton();
        mnBarraMenu = new javax.swing.JMenuBar();
        mnMovimento = new javax.swing.JMenu();
        mnCaixa = new javax.swing.JMenuItem();
        mnFecharcaixa = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mnSair = new javax.swing.JMenuItem();
        mnUsuarios = new javax.swing.JMenu();
        mnNovousuario = new javax.swing.JMenuItem();
        mnFecharNovousuario = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        mnEntrar = new javax.swing.JMenuItem();
        mnFecharEntrar = new javax.swing.JMenuItem();
        mnRelatorios = new javax.swing.JMenu();
        mnRelatorioMovimento = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout dtpDescktopLayout = new javax.swing.GroupLayout(dtpDescktop);
        dtpDescktop.setLayout(dtpDescktopLayout);
        dtpDescktopLayout.setHorizontalGroup(
            dtpDescktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 913, Short.MAX_VALUE)
        );
        dtpDescktopLayout.setVerticalGroup(
            dtpDescktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 727, Short.MAX_VALUE)
        );

        tbrBarraFerramentas.setFloatable(false);
        tbrBarraFerramentas.setRollover(true);

        btnCaixa.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnCaixa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cash_icon-icons.com_51028.png"))); // NOI18N
        btnCaixa.setText("caixa");
        btnCaixa.setEnabled(false);
        btnCaixa.setFocusable(false);
        btnCaixa.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCaixa.setMaximumSize(new java.awt.Dimension(50, 50));
        btnCaixa.setMinimumSize(new java.awt.Dimension(50, 50));
        btnCaixa.setPreferredSize(new java.awt.Dimension(50, 50));
        btnCaixa.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCaixaActionPerformed(evt);
            }
        });
        tbrBarraFerramentas.add(btnCaixa);

        btnEntrar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnEntrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/App-login-manager1-icon.png"))); // NOI18N
        btnEntrar.setText("Login");
        btnEntrar.setFocusable(false);
        btnEntrar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEntrar.setMaximumSize(new java.awt.Dimension(50, 50));
        btnEntrar.setMinimumSize(new java.awt.Dimension(50, 50));
        btnEntrar.setPreferredSize(new java.awt.Dimension(50, 50));
        btnEntrar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEntrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntrarActionPerformed(evt);
            }
        });
        tbrBarraFerramentas.add(btnEntrar);

        btnLogin.setFont(new java.awt.Font("Tahoma", 1, 9)); // NOI18N
        btnLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/person_user_customer_man_male_man_boy_people_1687.png"))); // NOI18N
        btnLogin.setText("novo usu");
        btnLogin.setFocusable(false);
        btnLogin.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLogin.setMaximumSize(new java.awt.Dimension(50, 50));
        btnLogin.setMinimumSize(new java.awt.Dimension(50, 50));
        btnLogin.setPreferredSize(new java.awt.Dimension(50, 50));
        btnLogin.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        tbrBarraFerramentas.add(btnLogin);

        btnAdministrador.setFont(new java.awt.Font("Tahoma", 1, 9)); // NOI18N
        btnAdministrador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconfinder_Downloads-Folder-Icon_70751 (2).png"))); // NOI18N
        btnAdministrador.setText("admin.");
        btnAdministrador.setFocusable(false);
        btnAdministrador.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAdministrador.setMaximumSize(new java.awt.Dimension(50, 50));
        btnAdministrador.setMinimumSize(new java.awt.Dimension(50, 50));
        btnAdministrador.setPreferredSize(new java.awt.Dimension(50, 50));
        btnAdministrador.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAdministrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdministradorActionPerformed(evt);
            }
        });
        tbrBarraFerramentas.add(btnAdministrador);

        mnMovimento.setText("Arquivo");
        mnMovimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnMovimentoActionPerformed(evt);
            }
        });

        mnCaixa.setText("Movimento");
        mnCaixa.setEnabled(false);
        mnCaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnCaixaActionPerformed(evt);
            }
        });
        mnMovimento.add(mnCaixa);

        mnFecharcaixa.setText("Fechar Movimento");
        mnFecharcaixa.setEnabled(false);
        mnFecharcaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnFecharcaixaActionPerformed(evt);
            }
        });
        mnMovimento.add(mnFecharcaixa);
        mnMovimento.add(jSeparator1);

        mnSair.setText("Sair");
        mnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnSairActionPerformed(evt);
            }
        });
        mnMovimento.add(mnSair);

        mnBarraMenu.add(mnMovimento);

        mnUsuarios.setText("Usuários");
        mnUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnUsuariosActionPerformed(evt);
            }
        });

        mnNovousuario.setText("Novo usuário");
        mnNovousuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnNovousuarioActionPerformed(evt);
            }
        });
        mnUsuarios.add(mnNovousuario);

        mnFecharNovousuario.setText("Fechar Novo usuário");
        mnFecharNovousuario.setEnabled(false);
        mnFecharNovousuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnFecharNovousuarioActionPerformed(evt);
            }
        });
        mnUsuarios.add(mnFecharNovousuario);
        mnUsuarios.add(jSeparator2);

        mnEntrar.setText("Login");
        mnEntrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnEntrarActionPerformed(evt);
            }
        });
        mnUsuarios.add(mnEntrar);

        mnFecharEntrar.setText("Fechar Login");
        mnFecharEntrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnFecharEntrarActionPerformed(evt);
            }
        });
        mnUsuarios.add(mnFecharEntrar);

        mnBarraMenu.add(mnUsuarios);

        mnRelatorios.setText("Relatórios");

        mnRelatorioMovimento.setText("Movimento");
        mnRelatorioMovimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnRelatorioMovimentoActionPerformed(evt);
            }
        });
        mnRelatorios.add(mnRelatorioMovimento);

        jMenuItem2.setText("jMenuItem2");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        mnRelatorios.add(jMenuItem2);

        mnBarraMenu.add(mnRelatorios);

        setJMenuBar(mnBarraMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dtpDescktop)
            .addComponent(tbrBarraFerramentas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(tbrBarraFerramentas, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(dtpDescktop))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnSairActionPerformed
        System.exit(0);
    }//GEN-LAST:event_mnSairActionPerformed

    private void mnCaixaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnCaixaActionPerformed

        btnCaixa.doClick();
    }//GEN-LAST:event_mnCaixaActionPerformed

    private void mnFecharcaixaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnFecharcaixaActionPerformed
        btnMovimentododiaSair.doClick();
        mnFecharcaixa.setEnabled(false);
        mnCaixa.setEnabled(true);
    }//GEN-LAST:event_mnFecharcaixaActionPerformed

    private void mnMovimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnMovimentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnMovimentoActionPerformed

    private void btnCaixaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCaixaActionPerformed

        btnFecharMovimento.doClick();
        frmmovimentodia = new frmMovimentodia();
        dtpDescktop.add(frmmovimentodia);
        frmmovimentodia.setVisible(true);
        frmmovimentodia.setPosicaodia();
        mnCaixa.setEnabled(false);
        mnFecharcaixa.setEnabled(true);
        mnFecharcaixa.setEnabled(true);
        btnCaixa.setEnabled(false);
    }//GEN-LAST:event_btnCaixaActionPerformed

    private void mnUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnUsuariosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnUsuariosActionPerformed

    private void mnEntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnEntrarActionPerformed
        btnEntrar.doClick();
    }//GEN-LAST:event_mnEntrarActionPerformed

    private void mnNovousuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnNovousuarioActionPerformed
        btnLogin.doClick();
    }//GEN-LAST:event_mnNovousuarioActionPerformed

    private void mnFecharEntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnFecharEntrarActionPerformed
        gerenciadordejanelas.fecharjanelas(frmEntrar.getInstancia());
        mnEntrar.setEnabled(true);
        mnFecharEntrar.setEnabled(false);
        btnEntrar.setEnabled(true);
        btnLogin.setEnabled(true);
    }//GEN-LAST:event_mnFecharEntrarActionPerformed

    private void mnFecharNovousuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnFecharNovousuarioActionPerformed
        gerenciadordejanelas.fecharjanelas(frmLogin.getInstancia());
        mnNovousuario.setEnabled(true);
        mnFecharNovousuario.setEnabled(false);
        btnEntrar.setEnabled(true);
        btnLogin.setEnabled(true);
    }//GEN-LAST:event_mnFecharNovousuarioActionPerformed

    private void mnRelatorioMovimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnRelatorioMovimentoActionPerformed
        int idponto = 0;
        RefazerConexao rfc = new RefazerConexao();
        rfc.refazerconexao();
        idponto = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o id"));
        try{
            Map parametros = new HashMap();
            parametros.put(null,idponto);
            InputStream inputstream = getClass().getResourceAsStream("/RelatMovimento/RelatorioMovimento.jasper");
            JRResultSetDataSource resultset = new JRResultSetDataSource(controlemovimento.movimentousuario(idponto));
            JasperPrint jpprint = JasperFillManager.fillReport(inputstream, parametros, resultset);
            JInternalFrame frmrelatorios = new JInternalFrame();
            frmrelatorios.getContentPane().add(new JRViewer(jpprint));
            frmrelatorios.pack();
            dtpDescktop.add(frmrelatorios);
            frmrelatorios.setBounds(0, 0, 1280, 910);
            frmrelatorios.setClosable(true);
            frmrelatorios.setVisible(true);
            frmrelatorios.toFront();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage() + " ao gerar o relatório!");
        }
    }//GEN-LAST:event_mnRelatorioMovimentoActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        try {
            gerenciadordejanelas.abrirjanelas(frmRelatorios.getInstancia());
        } catch (PropertyVetoException ex) {
            JOptionPane.showMessageDialog( null,"Erro: " + ex + " ao abrir a janela de relatórios.");
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void btnEntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntrarActionPerformed
        try {
            if(frmLogin.getInstancia().isSelected()){
               gerenciadordejanelas.fecharjanelas(frmLogin.getInstancia());
               mnNovousuario.setEnabled(true);
               mnFecharNovousuario.setEnabled(false);
            }
            if(frmAdministrador.getInstancia().isSelected()){
               gerenciadordejanelas.fecharjanelas(frmAdministrador.getInstancia());
            }
            gerenciadordejanelas.abrirentrar(frmEntrar.getInstancia());
            mnEntrar.setEnabled(false);
            mnFecharEntrar.setEnabled(true);
            btnEntrar.setEnabled(false);
            btnLogin.setEnabled(true);
        } catch (PropertyVetoException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex + " ao abrir a janela de login.");
        }
    }//GEN-LAST:event_btnEntrarActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
       try {
            if(frmEntrar.getInstancia().isSelected()){
               gerenciadordejanelas.fecharjanelas(frmEntrar.getInstancia());
               mnEntrar.setEnabled(true);
               mnFecharEntrar.setEnabled(false);
            }
            
            if(frmAdministrador.getInstancia().isSelected()){
               gerenciadordejanelas.fecharjanelas(frmAdministrador.getInstancia());
            }
            
            txtNome.setText("");
            txtNome.setEnabled(false);
            txtSenha.setText("");
            txtSenha.setEnabled(false);
            txtConfsenha.setText("");
            txtConfsenha.setEnabled(false);
            cbxAdministrador.setSelected(false);
            cbxAdministrador.setEnabled(false);
            btnCadastro.setEnabled(false);
            gerenciadordejanelas.abrirlogin(frmLogin.getInstancia());
            frmlogin.toFront();
            frmlogin.setSelected(true);
            mnNovousuario.setEnabled(false);
            mnFecharNovousuario.setEnabled(true);
            btnLogin.setEnabled(false);
            btnEntrar.setEnabled(true);
            btnNovoEntrar.setEnabled(true);
        } catch (PropertyVetoException ex) {
            JOptionPane.showMessageDialog( null,"Erro: " + ex + " ao abrir a janela de login.");
        }
       
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnAdministradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdministradorActionPerformed
        try {
            if(frmEntrar.getInstancia().isSelected()){
               gerenciadordejanelas.fecharjanelas(frmEntrar.getInstancia());
               mnEntrar.setEnabled(true);
               mnFecharEntrar.setEnabled(false);
               
            }
            
            if(frmLogin.getInstancia().isSelected()){
               gerenciadordejanelas.fecharjanelas(frmLogin.getInstancia());
               mnNovousuario.setEnabled(true);
               mnFecharNovousuario.setEnabled(false);
               btnLogin.setEnabled(true);
            }
            
            gerenciadordejanelas.abriradministrador(frmAdministrador.getInstancia());
            //btnEntrar.setEnabled(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAdministradorActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnAdministrador;
    public static javax.swing.JButton btnCaixa;
    public static javax.swing.JButton btnEntrar;
    public static javax.swing.JButton btnLogin;
    public static javax.swing.JDesktopPane dtpDescktop;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JMenuBar mnBarraMenu;
    public static javax.swing.JMenuItem mnCaixa;
    public static javax.swing.JMenuItem mnEntrar;
    public static javax.swing.JMenuItem mnFecharEntrar;
    public static javax.swing.JMenuItem mnFecharNovousuario;
    public static javax.swing.JMenuItem mnFecharcaixa;
    public static javax.swing.JMenu mnMovimento;
    public static javax.swing.JMenuItem mnNovousuario;
    private javax.swing.JMenuItem mnRelatorioMovimento;
    private javax.swing.JMenu mnRelatorios;
    private javax.swing.JMenuItem mnSair;
    private javax.swing.JMenu mnUsuarios;
    private javax.swing.JToolBar tbrBarraFerramentas;
    // End of variables declaration//GEN-END:variables
}
