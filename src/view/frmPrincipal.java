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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import org.firebirdsql.management.FBBackupManager;
import produzconexao.ConexaoFirebird;
import produzconexao.RefazerConexao;
import util.ConfigDB;
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
import static view.frmMovimento.btnCancelarreserva;
import static view.frmMovimento.btnExcluir;
import static view.frmMovimento.btnFecharMovimento;
import static view.frmMovimento.btnFecharcaixa;
import static view.frmMovimento.btnReservarcaixa;
import static view.frmMovimento.ftxtValor;
import static view.frmMovimento.tblMovimento;
import static view.frmMovimentodia.btnMovimentododiaSair;


/**
 *
 * @author Pituba
 */
public class frmPrincipal extends javax.swing.JFrame {
    GuardarUrl guardarurl = new GuardarUrl();
    GerenciadordeJanelas gerenciadordejanelas;
    //frmLogin frmlogin = new frmLogin();
    //frmMovimento frmusuario = new frmMovimento();
    frmMovimentodia frmmovimentodia;
    frmRelatorios frmrelatorios;
    //frmEntrar frmentrar = new frmEntrar();
    ControleMovimento controlemovimento = new ControleMovimento();
    //frmAdministrador administrador = new frmAdministrador();
    public frmPrincipal() {
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
        this.gerenciadordejanelas = new GerenciadordeJanelas(dtpDescktop);
        guardarurl.setContador(0);
        String resultado = guardarurl.GetProp("conectar"); 
        String ip = guardarurl.GetProp("IP");
        try {
            if (!resultado.equals("")) {
                ConexaoFirebird conect = new ConexaoFirebird(resultado, ip);
            } else {
                String servidor = JOptionPane.showInputDialog(null,"Digite aqui o IP do servidor, caso exista um!");
                UrlDao url = new UrlDao();
                //url.pegaurl();
                if(!"".equals(servidor)){
                url.pegaurl(servidor);
                }else{
                url.pegaurl("localhost");
                }
            }
        } catch (ClassNotFoundException ex) {
            //UrlDao url = new UrlDao();
            //url.pegaurl();
            String servidor = JOptionPane.showInputDialog(null,"Digite aqui o IP do servidor, caso exista um!");
                UrlDao url = new UrlDao();
                if(servidor != ""){
                url.pegaurl(servidor);
                }else{
                url.pegaurl("localhost");
                }
            
        } catch (SQLException ex) {
            System.exit(0);
        }  
        
        String resultsec = guardarurl.GetPropsecr("internalizar");
           if(!resultsec.equals("dentro")){
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
            //frmLogin frmlogin = new frmLogin();
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
            //frmlogin.setClosable(true);
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
             btnAdministrador.setEnabled(false);
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
        btnRelatorio = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        btnAdministrador = new javax.swing.JButton();
        btnFecharAdmin = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        btnBackup = new javax.swing.JButton();
        mnBarraMenu = new javax.swing.JMenuBar();
        mnMovimento = new javax.swing.JMenu();
        mnCaixa = new javax.swing.JMenuItem();
        mnFecharcaixa = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mnRelatorios = new javax.swing.JMenuItem();
        mnFecharRelatorios = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        mnSair = new javax.swing.JMenuItem();
        mnUsuarios = new javax.swing.JMenu();
        mnNovousuario = new javax.swing.JMenuItem();
        mnFecharNovousuario = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        mnEntrar = new javax.swing.JMenuItem();
        mnFecharEntrar = new javax.swing.JMenuItem();

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
        tbrBarraFerramentas.setEnabled(false);

        btnCaixa.setFont(new java.awt.Font("Tahoma", 1, 8)); // NOI18N
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

        btnEntrar.setFont(new java.awt.Font("Tahoma", 1, 8)); // NOI18N
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

        btnLogin.setFont(new java.awt.Font("Tahoma", 1, 8)); // NOI18N
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

        btnRelatorio.setFont(new java.awt.Font("Tahoma", 1, 8)); // NOI18N
        btnRelatorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/trade_report_reports_documents_2351.png"))); // NOI18N
        btnRelatorio.setText("relatório");
        btnRelatorio.setEnabled(false);
        btnRelatorio.setFocusable(false);
        btnRelatorio.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRelatorio.setMaximumSize(new java.awt.Dimension(50, 50));
        btnRelatorio.setMinimumSize(new java.awt.Dimension(50, 50));
        btnRelatorio.setPreferredSize(new java.awt.Dimension(50, 50));
        btnRelatorio.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRelatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRelatorioActionPerformed(evt);
            }
        });
        tbrBarraFerramentas.add(btnRelatorio);
        tbrBarraFerramentas.add(jSeparator4);

        btnAdministrador.setFont(new java.awt.Font("Tahoma", 1, 8)); // NOI18N
        btnAdministrador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/drop_box_folder_graphite_13844.png"))); // NOI18N
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

        btnFecharAdmin.setFont(new java.awt.Font("Tahoma", 1, 8)); // NOI18N
        btnFecharAdmin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/private_folder_13766.png"))); // NOI18N
        btnFecharAdmin.setText("fechar ad.");
        btnFecharAdmin.setToolTipText("");
        btnFecharAdmin.setEnabled(false);
        btnFecharAdmin.setFocusable(false);
        btnFecharAdmin.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFecharAdmin.setMaximumSize(new java.awt.Dimension(50, 50));
        btnFecharAdmin.setMinimumSize(new java.awt.Dimension(50, 50));
        btnFecharAdmin.setPreferredSize(new java.awt.Dimension(50, 50));
        btnFecharAdmin.setRequestFocusEnabled(false);
        btnFecharAdmin.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnFecharAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharAdminActionPerformed(evt);
            }
        });
        tbrBarraFerramentas.add(btnFecharAdmin);
        tbrBarraFerramentas.add(jSeparator5);
        tbrBarraFerramentas.add(jSeparator6);

        btnBackup.setFont(new java.awt.Font("Tahoma", 1, 8)); // NOI18N
        btnBackup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/blue_external_drive_backup(1).png"))); // NOI18N
        btnBackup.setText("Backup");
        btnBackup.setEnabled(false);
        btnBackup.setFocusable(false);
        btnBackup.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBackup.setMaximumSize(new java.awt.Dimension(50, 50));
        btnBackup.setMinimumSize(new java.awt.Dimension(50, 50));
        btnBackup.setPreferredSize(new java.awt.Dimension(50, 50));
        btnBackup.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBackup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackupActionPerformed(evt);
            }
        });
        tbrBarraFerramentas.add(btnBackup);

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

        mnRelatorios.setText("Relatórios");
        mnRelatorios.setEnabled(false);
        mnRelatorios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnRelatoriosActionPerformed(evt);
            }
        });
        mnMovimento.add(mnRelatorios);

        mnFecharRelatorios.setText("Fechar Relatórios");
        mnFecharRelatorios.setEnabled(false);
        mnMovimento.add(mnFecharRelatorios);
        mnMovimento.add(jSeparator3);

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
        if(btnFecharAdmin.isEnabled() && btnEntrar.isEnabled() 
              || btnFecharAdmin.isEnabled() && btnLogin.isEnabled()){
           btnFecharAdmin.setEnabled(false);
        }else{
            if(!ftxtValor.isEnabled()){
              tblMovimento.setEnabled(false);
              ftxtValor.setEnabled(false);
              btnReservarcaixa.setEnabled(false);
              btnFecharAdmin.setEnabled(true);
            }
        }
        btnFecharMovimento.doClick();
        frmmovimentodia = new frmMovimentodia();
        dtpDescktop.add(frmmovimentodia);
        frmmovimentodia.setVisible(true);
        frmmovimentodia.setPosicaodia();
        mnCaixa.setEnabled(false);
        mnFecharcaixa.setEnabled(true);
        mnFecharcaixa.setEnabled(true);
        btnCaixa.setEnabled(false);
        btnRelatorio.setEnabled(false);
        mnRelatorios.setEnabled(false);
        mnFecharRelatorios.setEnabled(false);
        btnBackup.setEnabled(false);
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
            //frmlogin.toFront();
            //frmlogin.setSelected(true);
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

    private void btnRelatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRelatorioActionPerformed
        try{
        btnMovimentododiaSair.doClick();
        }catch(Exception ex){
        }
        try{
          btnFecharMovimento.doClick();
        }catch(Exception ex){
        }
        //btnMovimentododiaSair.doClick();
        //btnFecharMovimento.doClick();
        frmrelatorios = new frmRelatorios();
        dtpDescktop.add(frmrelatorios);
        frmrelatorios.setVisible(true);
        frmrelatorios.setPosicaodia();
        mnRelatorios.setEnabled(false);
        mnFecharRelatorios.setEnabled(false);
        btnCaixa.setEnabled(false);
        btnRelatorio.setEnabled(false);
        btnBackup.setEnabled(false);
        
    }//GEN-LAST:event_btnRelatorioActionPerformed

    private void mnRelatoriosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnRelatoriosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnRelatoriosActionPerformed

    private void btnFecharAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharAdminActionPerformed
        if(btnCaixa.isEnabled()){
               btnEntrar.setEnabled(true);
               mnEntrar.setEnabled(true);
               mnFecharEntrar.setEnabled(false);
               btnLogin.setEnabled(true);
               mnNovousuario.setEnabled(true);
               mnFecharNovousuario.setEnabled(false);
               mnCaixa.setEnabled(false);
               mnFecharcaixa.setEnabled(false);
               btnCaixa.setEnabled(false);
               btnRelatorio.setEnabled(false);
               mnRelatorios.setEnabled(false);
               mnFecharRelatorios.setEnabled(false);
               btnAdministrador.setEnabled(true);
               btnFecharAdmin.setEnabled(false);
               btnBackup.setEnabled(false);
           if(!tblMovimento.isEnabled()){
               tblMovimento.setEnabled(true);
               ftxtValor.setEnabled(true);
               btnReservarcaixa.setEnabled(true);
               btnEntrar.setEnabled(false);
               mnEntrar.setEnabled(false);
               mnFecharEntrar.setEnabled(false);
               btnLogin.setEnabled(false);
               mnNovousuario.setEnabled(false);
               mnFecharNovousuario.setEnabled(false);
           }else{
               btnEntrar.setEnabled(true);
               mnEntrar.setEnabled(true);
               mnFecharEntrar.setEnabled(false);
               btnLogin.setEnabled(true);
               mnNovousuario.setEnabled(true);
               mnFecharNovousuario.setEnabled(false);
           }              
        }
    }//GEN-LAST:event_btnFecharAdminActionPerformed

    private void btnBackupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackupActionPerformed
       int respostaconfirma = JOptionPane.showConfirmDialog(null, "Confirma a restauração do banco de dados?",
                "Bragança", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
     if(respostaconfirma == 0){
          ConfigDB configdb = new ConfigDB();
          new Thread(){
               public void run(){
                   try {
	                FBBackupManager restore = new FBBackupManager();
	                restore.setUser("SYSDBA");
	                restore.setPassword("masterkey"); 
	                restore.setDatabase(configdb.porta_bd() + "dbBackup/MOVIMENTO.FDB");
	                restore.setHost("localhost");
	                restore.setBackupPath(configdb.porta_bd() + "Backu&Log/arquivo.bkp"); // caminho arquivo backup
	                restore.setVerbose(true);
	                restore.setLogger(new FileOutputStream(configdb.porta_bd() + "Backu&Log/log.rtf")); // caminho log
	                restore.setRestoreReplace(true);
	                restore.restoreDatabase();
                        JOptionPane.showMessageDialog(null,"Restaurado com sucesso!",
                                "Restaurado com sucesso!",JOptionPane.CANCEL_OPTION);
                   }catch (FileNotFoundException ex) {
                       JOptionPane.showMessageDialog(null, "ERRO:\n" + ex, "Bragança.",
                               JOptionPane.ERROR_MESSAGE);
                   } catch (SQLException ex) {
                       JOptionPane.showMessageDialog(null, "ERRO:\n" + ex, "Bragança.",
                               JOptionPane.ERROR_MESSAGE);
                   } 
               }
          }.start();
     }else{
           JOptionPane.showMessageDialog(null, "Nenhum banco de dados será restaurado!", "Bragança", 
                   JOptionPane.INFORMATION_MESSAGE);
     }
    }//GEN-LAST:event_btnBackupActionPerformed

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
    public static javax.swing.JButton btnBackup;
    public static javax.swing.JButton btnCaixa;
    public static javax.swing.JButton btnEntrar;
    public static javax.swing.JButton btnFecharAdmin;
    public static javax.swing.JButton btnLogin;
    public static javax.swing.JButton btnRelatorio;
    public static javax.swing.JDesktopPane dtpDescktop;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JMenuBar mnBarraMenu;
    public static javax.swing.JMenuItem mnCaixa;
    public static javax.swing.JMenuItem mnEntrar;
    public static javax.swing.JMenuItem mnFecharEntrar;
    public static javax.swing.JMenuItem mnFecharNovousuario;
    public static javax.swing.JMenuItem mnFecharRelatorios;
    public static javax.swing.JMenuItem mnFecharcaixa;
    public static javax.swing.JMenu mnMovimento;
    public static javax.swing.JMenuItem mnNovousuario;
    public static javax.swing.JMenuItem mnRelatorios;
    private javax.swing.JMenuItem mnSair;
    private javax.swing.JMenu mnUsuarios;
    private javax.swing.JToolBar tbrBarraFerramentas;
    // End of variables declaration//GEN-END:variables
}
