/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lowcodesystem.model;

/**
 *
 * @author Maykon
 */
public class DataSet {

    public String group = "";
    public String label = "";
    public String value = "";
    public String color = "";

    @Override
    public String toString() {
        return "DataSet{" + "group=" + group + ", label=" + label + ", value=" + value + ", color=" + color + '}';
    }
}
