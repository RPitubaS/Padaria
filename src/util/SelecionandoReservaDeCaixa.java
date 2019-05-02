/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.ArrayList;
import java.util.List;
import modelo.bean.Movimento;
import modelo.dao.MovimentoDAO;
import produzconexao.RefazerConexao;

/**
 *
 * @author Pituba
 */
public class SelecionandoReservaDeCaixa {
    
    public void SelecionandoReservaDeCaixa(String hoje, int idponto, int iddata){
           List<Movimento> selecionamovimentodia = new ArrayList<>();
                           RefazerConexao refc15 = new RefazerConexao();
                           refc15.refazerconexao();
                           MovimentoDAO movdao35 = new MovimentoDAO();
                           selecionamovimentodia = movdao35.selecionarmovimentodia(hoje);
                           if(!selecionamovimentodia.isEmpty()){
                               List<Movimento> selecionamovimentousua = new ArrayList<>();
                               RefazerConexao refc16 = new RefazerConexao();
                               refc16.refazerconexao();
                               MovimentoDAO movdao36 = new MovimentoDAO();
                               selecionamovimentousua = movdao36.selecionarmovimentousua(idponto);
                                  if(selecionamovimentousua.isEmpty()){
                                     SelecionarReservaDeCaixa selecionarreservadecaixa = new SelecionarReservaDeCaixa();
                                     selecionarreservadecaixa.SelecionarUltimoReservaDeCaixa();
                                  }else{
                                     SelecionarReservaDeCaixa selecionarreservadecaixa = new SelecionarReservaDeCaixa();
                                     selecionarreservadecaixa.SelecionarReservaDeCaixa(iddata);
                                  }
                           }else{
                                SelecionarReservaDeCaixa selecionarreservadecaixa = new SelecionarReservaDeCaixa();
                                selecionarreservadecaixa.SelecionarReservaDeCaixa(iddata);
                           }
    }
}
