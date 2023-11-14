/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lowcodesystem.ws;

import br.com.lowcodesystem.dao.Dao;
import java.util.logging.Level;
import java.util.logging.Logger;

//@WebService(endpointInterface = "br.com.mjv.template.ws.CalculadoraServer")
public class CalculadoraServerImpl implements CalculadoraServer {

    private Dao dao;

    public CalculadoraServerImpl(Dao dao) {
        this.dao = dao;
    }

    public float soma(float num1, float num2) {
        try {
            System.out.println(dao.select("zoombox-ds", "select * from tipovenda").dados.get(0));
        } catch (Exception ex) {
            Logger.getLogger(CalculadoraServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return num1 + num2;
    }

    public float subtracao(float num1, float num2) {
        return num1 - num2;
    }

    public float multiplicacao(float num1, float num2) {
        return num1 * num2;
    }

    public float divisao(float num1, float num2) {
        return num1 / num2;
    }

}
