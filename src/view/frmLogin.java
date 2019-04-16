/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.bean.Usuario;
import modelo.dao.UsuariosDAO;
import produzconexao.RefazerConexao;
import util.GerenciadordeJanelas;
import util.GuardarUrl;
import static view.frmPrincipal.btnLogin;
import static view.frmPrincipal.mnEntrar;
import static view.frmPrincipal.mnFecharEntrar;
import static view.frmPrincipal.mnFecharNovousuario;
import static view.frmPrincipal.mnNovousuario;
import static view.frmEntrar.btnLogEntrar;
import static view.frmPrincipal.btnEntrar;
import static view.frmPrincipal.dtpDescktop;


/**
 *
 * @author Pituba
 */
public class frmLogin extends javax.swing.JInternalFrame {

   GerenciadordeJanelas gerenciadordejanelas;
    private static frmLogin frmlogin;
    String nomeusuario = "", tipousuario = "";
    
   
    public static frmLogin getInstancia(){
          if(frmlogin == null){
             frmlogin = new frmLogin();
          }
        return frmlogin;
    }
    
    public frmLogin() {
        initComponents();
        btnNovoEntrar.setEnabled(true);
       
    }   
   
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        btnCadastro = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtSenha = new javax.swing.JPasswordField();
        jLabel11 = new javax.swing.JLabel();
        txtConfsenha = new javax.swing.JPasswordField();
        cbxAdministrador = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtLognick = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnNovoEntrar = new javax.swing.JButton();
        txtLogsenha = new javax.swing.JPasswordField();

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

        jPanel1.setBackground(new java.awt.Color(255, 102, 102));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Usuários");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 204));
        jLabel2.setText("Nome:");

        txtNome.setBackground(new java.awt.Color(255, 255, 204));
        txtNome.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNome.setToolTipText("Digite seu nome");
        txtNome.setEnabled(false);
        txtNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNomeKeyPressed(evt);
            }
        });

        btnCadastro.setBackground(new java.awt.Color(255, 255, 0));
        btnCadastro.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCadastro.setForeground(new java.awt.Color(0, 0, 102));
        btnCadastro.setText("Incluir");
        btnCadastro.setToolTipText("Incluir o Usuário");
        btnCadastro.setEnabled(false);
        btnCadastro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastroActionPerformed(evt);
            }
        });
        btnCadastro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnCadastroKeyPressed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 204));
        jLabel10.setText("Senha:");

        txtSenha.setBackground(new java.awt.Color(255, 255, 204));
        txtSenha.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtSenha.setToolTipText("Senha de no mínimo 6 digitos");
        txtSenha.setEnabled(false);
        txtSenha.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSenhaFocusGained(evt);
            }
        });
        txtSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSenhaKeyPressed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 204));
        jLabel11.setText("Conf. Senha:");

        txtConfsenha.setBackground(new java.awt.Color(255, 255, 204));
        txtConfsenha.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtConfsenha.setToolTipText("Redigite a Senha");
        txtConfsenha.setEnabled(false);
        txtConfsenha.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtConfsenhaFocusLost(evt);
            }
        });
        txtConfsenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtConfsenhaKeyPressed(evt);
            }
        });

        cbxAdministrador.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cbxAdministrador.setForeground(new java.awt.Color(255, 255, 204));
        cbxAdministrador.setText("Administrador");
        cbxAdministrador.setToolTipText("Marque caso seja um administrador");
        cbxAdministrador.setEnabled(false);
        cbxAdministrador.setOpaque(false);
        cbxAdministrador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbxAdministradorKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(65, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNome)
                            .addComponent(txtSenha)
                            .addComponent(txtConfsenha, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(btnCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(128, 128, 128)
                        .addComponent(cbxAdministrador)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtConfsenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(cbxAdministrador)
                .addGap(39, 39, 39)
                .addComponent(btnCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 380, 440);

        jPanel2.setBackground(new java.awt.Color(255, 153, 0));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/App-login-manager-icon.png"))); // NOI18N

        jPanel3.setBackground(new java.awt.Color(255, 255, 204));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 102, 255));
        jLabel6.setText("Nome:");

        txtLognick.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtLognick.setToolTipText("Informe o Nome");
        txtLognick.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtLognickKeyPressed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 102, 204));
        jLabel7.setText("Senha:");

        jLabel8.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 102, 204));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Log In");

        btnNovoEntrar.setBackground(new java.awt.Color(255, 153, 0));
        btnNovoEntrar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnNovoEntrar.setText("Entrar");
        btnNovoEntrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoEntrarActionPerformed(evt);
            }
        });
        btnNovoEntrar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnNovoEntrarKeyPressed(evt);
            }
        });

        txtLogsenha.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtLogsenha.setToolTipText("Informe a Senha");
        txtLogsenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtLogsenhaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtLognick)
                            .addComponent(txtLogsenha, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(jLabel8)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnNovoEntrar, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(94, 94, 94))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLognick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLogsenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(btnNovoEntrar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                .addGap(121, 121, 121))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(jPanel2);
        jPanel2.setBounds(380, 0, 391, 440);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCadastroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastroActionPerformed
        
       //try {
           //EnviarEMail enviaremail = new EnviarEMail(txtEmail.getText(), "e_mail enviado do programa", "Este e-mail foi enviado a t�tulo de teste!");
       //} catch (MessagingException ex) {
           //JOptionPane.showMessageDialog(null,"Ocorreu um erro, por favor confira o endere�o de e-mail e tente novamente! ERRO: " + ex);
       //}
       String selecao;
       if(txtNome.getText().equals("")){
         JOptionPane.showMessageDialog(null, "É necessário um nome de usuário/administrador!");
         txtNome.requestFocus();
       } else {
               if(txtSenha.getText().equals("")){
                 JOptionPane.showMessageDialog(null, "É necessário uma senha!");
                 txtSenha.requestFocus();
               }else{
                     if(txtConfsenha.getText().equals("")){
                       JOptionPane.showMessageDialog(null, "As senhas não conferem ou o campo de confirmação de senha esta em branco!");
                       txtSenha.requestFocus();
                       btnCadastro.setEnabled(false);
                     }else{
                              RefazerConexao rfc = new RefazerConexao();
                              rfc.refazerconexao();
                           if(cbxAdministrador.isSelected()){
                               selecao = "sim"; 
                           }else{
                               selecao = "nao";
                           }
                           try{
                              RefazerConexao rfc1 = new RefazerConexao();
                              rfc1.refazerconexao();
                              List<Usuario> selecionandousuario = new ArrayList<>();
                              UsuariosDAO usdao = new UsuariosDAO();
                              selecionandousuario = usdao.selecionarusuario(txtNome.getText());
                            if(!selecionandousuario.isEmpty()){
                                JOptionPane.showMessageDialog(null, "Já exite um usuário com este nome\n"
                                        + " cadastrado!");
                                txtNome.setText("");
                                txtNome.requestFocus();
                            }else{
                              rfc1.refazerconexao();
                              usdao.salvar_usuarios(txtNome.getText(), txtConfsenha.getText(), selecao);
                              txtLognick.setText(txtNome.getText());
                              txtLogsenha.setText(txtConfsenha.getText());
                              txtNome.setText("");
                              txtNome.setEnabled(false);
                              txtSenha.setText("");
                              txtSenha.setEnabled(false);
                              txtConfsenha.setText("");
                              txtConfsenha.setEnabled(false);
                              cbxAdministrador.setSelected(false);
                              cbxAdministrador.setEnabled(false);
                              btnCadastro.setEnabled(false);
                              mnEntrar.setEnabled(true);
                              mnFecharEntrar.setEnabled(false);
                              mnNovousuario.setEnabled(true);
                              mnFecharNovousuario.setEnabled(false);
                              btnLogin.setEnabled(true);
                              btnNovoEntrar.setEnabled(true);
                              btnNovoEntrar.doClick();
                              btnEntrar.setEnabled(true);
                              this.dispose();
                            }
                           }catch(Exception e){
                                 JOptionPane.showMessageDialog(null,"Erro: " + e + " ao tentar salvar, "
                                      + "confira as informações e tente novamente!");
                                }
                           }
                    }
              }
        
    }//GEN-LAST:event_btnCadastroActionPerformed

    private void txtConfsenhaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtConfsenhaFocusLost
        if(!txtConfsenha.getText().equals(txtSenha.getText())){
           JOptionPane.showMessageDialog(null, "As senhas não conferem!");
           txtConfsenha.setText("");
           txtSenha.setText("");
           txtSenha.requestFocus();
           btnCadastro.setEnabled(false);
        }else{
           btnCadastro.setEnabled(true);
        }
    }//GEN-LAST:event_txtConfsenhaFocusLost

    private void txtSenhaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSenhaFocusGained
        txtConfsenha.setText("");
    }//GEN-LAST:event_txtSenhaFocusGained

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
                              frmlogin.setClosable(false);  
                              txtNome.setText("");
                              txtSenha.setText("");
                              txtConfsenha.setText("");
                              cbxAdministrador.setSelected(false);
                              txtNome.setEnabled(false);
                              txtSenha.setEnabled(false);
                              txtConfsenha.setEnabled(false);
                              cbxAdministrador.setEnabled(false);
                              btnCadastro.setEnabled(false);
                              btnNovoEntrar.setEnabled(true);
                              txtLognick.setText("");
                              txtLogsenha.setText("");
                              mnNovousuario.setEnabled(true);
                              mnFecharNovousuario.setEnabled(false);
                              btnLogin.setEnabled(true);
                              
    }//GEN-LAST:event_formInternalFrameClosing

    private void txtNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeKeyPressed
       if(evt.getKeyCode()== evt.VK_ENTER){
                txtSenha.requestFocus();
        }
    }//GEN-LAST:event_txtNomeKeyPressed

    private void txtSenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSenhaKeyPressed
       if(evt.getKeyCode()== evt.VK_ENTER){
                txtConfsenha.requestFocus();
        }
    }//GEN-LAST:event_txtSenhaKeyPressed

    private void txtConfsenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtConfsenhaKeyPressed
        if(evt.getKeyCode()== evt.VK_ENTER){
                cbxAdministrador.requestFocus();
        }
    }//GEN-LAST:event_txtConfsenhaKeyPressed

    private void cbxAdministradorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbxAdministradorKeyPressed
         if(evt.getKeyCode()== evt.VK_ENTER){
                btnCadastro.requestFocus();
         }
    }//GEN-LAST:event_cbxAdministradorKeyPressed

    private void btnCadastroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCadastroKeyPressed
        if(!txtNome.getText().equals("") && !txtSenha.getText().equals("") && !txtConfsenha.getText().equals("")){
           if(evt.getKeyCode()== evt.VK_ENTER){
                btnCadastro.doClick();
        }
        }
    }//GEN-LAST:event_btnCadastroKeyPressed

    private void txtLogsenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLogsenhaKeyPressed
        if(evt.getKeyCode()== evt.VK_ENTER){
            btnNovoEntrar.requestFocus();
        }
    }//GEN-LAST:event_txtLogsenhaKeyPressed

    private void btnNovoEntrarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnNovoEntrarKeyPressed
        if(!txtLognick.getText().equals("") && !txtLogsenha.getText().equals("")){
            if(evt.getKeyCode()== evt.VK_ENTER){
                btnNovoEntrar.doClick();
            }
        }
    }//GEN-LAST:event_btnNovoEntrarKeyPressed

    private void btnNovoEntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoEntrarActionPerformed
        int idusuario;
        String senha = "";
        if(!txtLognick.getText().equals("") && !txtLogsenha.getText().equals("")){
            GuardarUrl guardarurl = new GuardarUrl();
            String resultado = guardarurl.GetProp("conectar");
            RefazerConexao rfc = new RefazerConexao();
            rfc.refazerconexao();
            List<Usuario> selecionandousuario = new ArrayList<>();
            UsuariosDAO usdao = new UsuariosDAO();
            selecionandousuario = usdao.selecionarusuario(txtLognick.getText());

            for(Usuario usuario : selecionandousuario){
                idusuario = usuario.getId();
                nomeusuario = usuario.getUsuario();
                senha = usuario.getSenha();
                tipousuario = usuario.getAdmin();
                }
            //SingletonModel.ABC obj = SingletonModel.ABC.INSTANCE;
            //obj.i = tipousuario;
            if(tipousuario.equals("sim") && senha.equals(txtLogsenha.getText())){
                cbxAdministrador.setSelected(false);
                txtNome.setEnabled(true);
                txtSenha.setEnabled(true);
                txtConfsenha.setEnabled(true);
                cbxAdministrador.setEnabled(true);
                btnCadastro.setEnabled(true);
                txtLognick.setText("");
                txtLogsenha.setText("");
                frmlogin.setClosable(true);
                //btnNovodocumento.setEnabled(true);
                //btnAbrirarquivos.setEnabled(true);
                //btnLogin.setEnabled(true);
                btnNovoEntrar.setEnabled(false);
                //btnEntrarlogin.setEnabled(true);
                //lblUsuario.setText("Usu�rio: " + nomeusuario);
            }else{
                if(tipousuario.equals("nao") && senha.equals(txtLogsenha.getText())){
                    txtLognick.setText("");
                    txtLogsenha.setText("");
                    //btnNovodocumento.setEnabled(true);
                    //btnAbrirarquivos.setEnabled(true);
                    //btnLogin.setEnabled(false);
                    frmlogin.setClosable(false);
                    //btnEntrarlogin.setEnabled(true);
                    //lblUsuario.setText("Usu�rio: " + nomeusuario);
                    JOptionPane.showMessageDialog(null, "Se não é um administrador, vá em Login para logar!", "Bragança", JOptionPane.OK_OPTION);
                    mnNovousuario.setEnabled(true);
                    mnFecharNovousuario.setEnabled(false);
                    btnLogin.setEnabled(true);
                    this.dispose();
                }else{
                    JOptionPane.showMessageDialog(null, "Usuário e senha não conferem!");
                    txtLognick.setText("");
                    txtLogsenha.setText("");
                    txtLognick.requestFocus(true);
                    //btnLogin.setEnabled(false);
                }
            }
        }else{
            txtLognick.requestFocus(true);
        }
    }//GEN-LAST:event_btnNovoEntrarActionPerformed

    private void txtLognickKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLognickKeyPressed
        if(evt.getKeyCode()== evt.VK_ENTER){
            txtLogsenha.requestFocus();
        }
    }//GEN-LAST:event_txtLognickKeyPressed

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
       dtpDescktop.remove(this);
    }//GEN-LAST:event_formInternalFrameClosed

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
       
    }//GEN-LAST:event_formInternalFrameActivated

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        
    }//GEN-LAST:event_formInternalFrameOpened


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnCadastro;
    public static javax.swing.JButton btnNovoEntrar;
    private javax.swing.ButtonGroup buttonGroup1;
    public static javax.swing.JCheckBox cbxAdministrador;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    public static javax.swing.JPasswordField txtConfsenha;
    public static javax.swing.JTextField txtLognick;
    public static javax.swing.JPasswordField txtLogsenha;
    public static javax.swing.JTextField txtNome;
    public static javax.swing.JPasswordField txtSenha;
    // End of variables declaration//GEN-END:variables
}
