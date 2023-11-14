/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lowcodesystem.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Maykon
 */
public class ResultSQL {

    public List<Map<String, Object>> dados = new ArrayList<Map<String, Object>>();
    public List<Column> column = new ArrayList<Column>();

    @Override
    public String toString() {
        return dados.toString();
    }

}
