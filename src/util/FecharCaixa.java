/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Time;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.bean.Entradas;
import modelo.dao.MovimentoDAO;
import produzconexao.RefazerConexao;
import view.frmMovimento;
import static view.frmMovimento.btnFecharcaixa;
import static view.frmMovimento.ftxtValor;
import static view.frmMovimento.tblMovimento;
import static view.frmMovimento.txtAtendentecaixa;
import static view.frmMovimento.txtCaixainicial;
import static view.frmMovimento.txtMoedasinicio;
import static view.frmMovimento.txtNotasinicio;
import static view.frmMovimento.txtRelogio;
import static view.frmPrincipal.dtpDescktop;
import static view.frmMovimento.btnExcluir;

/**
 *
 * @author Pituba
 */
public class FecharCaixa {
    String horadasaida;
    Time horasaidanull = null;
    frmMovimento frmmovimento;
    GerenciadordeJanelas gerenciadordejanelas = new GerenciadordeJanelas(dtpDescktop);
    DecimalFormat df = new DecimalFormat();
    DefaultTableModel modelo = (DefaultTableModel) tblMovimento.getModel();
    public boolean FecharCaixa(String datahoje, int movidponto, String horasaida, float caixasaida){
        java.sql.Date sdf;
        java.sql.Date data;
        df.applyPattern("R$ ##,##0.00");
        boolean fechandocaixa = true;
        
        SimpleDateFormat formatbr = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat formathr = new SimpleDateFormat("HH:mm:hh");

        int fechacaixa = JOptionPane.showConfirmDialog(null,"Deseja realmente fechar o caixa, com o valor de: "
                                                       + df.format(caixasaida) + " ?", "Fechamento."
                                                       , JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
        switch(fechacaixa){
                       case 0:
                           fechandocaixa = true;
        try {
            data = new java.sql.Date(formatbr.parse("27.03.2019").getTime());
            sdf = new java.sql.Date(formatbr.parse(datahoje).getTime());
                           RefazerConexao rfc = new RefazerConexao();
                           rfc.refazerconexao();
                           List<Entradas> selecionasaida = new ArrayList<>();
                           MovimentoDAO movdao = new MovimentoDAO();
                           selecionasaida = movdao.selecionarsaida(movidponto);
                             for(Entradas entradas : selecionasaida){
                                 data = entradas.getData();
                             }
                     
                 if(data.before(sdf)){
                     RefazerConexao rfc2 = new RefazerConexao();
                     rfc2.refazerconexao();
                     MovimentoDAO movimdao2 = new MovimentoDAO();
                     horasaidanull = movimdao2.selecionarmaxhoramovimento(movidponto);
                     if(horasaidanull == null){
                     horadasaida = JOptionPane.showInputDialog(null, "Por favor informe a hora de sua\n"
                                                                   + "saida. Somente números e dois\n"
                                                                   + "pontos no formato 'hh:mm:ss'\n"
                                                                   + "são aceitos!");

                     RefazerConexao rfc1 = new RefazerConexao();
                     rfc1.refazerconexao();
                     MovimentoDAO movimdao = new MovimentoDAO();
                     movimdao.atualizar_ponto(movidponto, horadasaida, caixasaida);
                     }else{
                     RefazerConexao rfc1 = new RefazerConexao();
                     rfc1.refazerconexao();
                     MovimentoDAO movimdao = new MovimentoDAO();
                     movimdao.atualizar_ponto(movidponto, horasaida, caixasaida);
                     }
                 }else{
                     RefazerConexao rfc1 = new RefazerConexao();
                     rfc1.refazerconexao();
                     MovimentoDAO movimdao = new MovimentoDAO();
                     movimdao.atualizar_ponto(movidponto, horasaida.toString(), caixasaida);
                 }            
                      modelo.setNumRows(0);
                      txtAtendentecaixa.setText("Caixa: ");
                      txtNotasinicio.setText("Notas: ");
                      txtMoedasinicio.setText("Moedas: ");
                      txtCaixainicial.setText("Início: ");
                      ftxtValor.setEnabled(false);
                      btnExcluir.setEnabled(false);
                      btnFecharcaixa.setEnabled(false);
                      ftxtValor.setEnabled(false);

        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex);
        }
                           break;
                       case 1:
                           fechandocaixa = false;
                           ftxtValor.setEnabled(true);                          
        try {
            data = new java.sql.Date(formatbr.parse("27.03.2019").getTime());
            sdf = new java.sql.Date(formatbr.parse(datahoje).getTime());
                           RefazerConexao rfc = new RefazerConexao();
                           rfc.refazerconexao();
                           List<Entradas> selecionasaida = new ArrayList<>();
                           MovimentoDAO movdao = new MovimentoDAO();
                           selecionasaida = movdao.selecionarsaida(movidponto);
                             for(Entradas entradas : selecionasaida){
                                 data = entradas.getData();
                             }

                 if(data.before(sdf)){
                     JOptionPane.showMessageDialog(null,"Este caixa tem data anterior a de hoje e precisa ser"
                             + " fechado, por favor confirme os valores e feche o caixa.", "Bragança"
                             , JOptionPane.INFORMATION_MESSAGE);
                 }
                           
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex);
        }
                          break;
        }
        return fechandocaixa;
    }
    
}
