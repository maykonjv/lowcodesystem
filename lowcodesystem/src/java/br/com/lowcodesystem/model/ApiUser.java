/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lowcodesystem.model;

import br.com.lowcodesystem.util.CriptoMD5;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maykon
 */
public class ApiUser {

    private String name = "";
    private String perfil = "";
    private String senha = "";
    private List<String> ips = new ArrayList<String>();
    private String hash = "";

    public ApiUser() {
    }

    public ApiUser(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<String> getIps() {
        return ips;
    }

    public void setIps(List<String> ips) {
        this.ips = ips;
    }

    public String getHash() {
        if (name != null && !name.isEmpty()) {
            try {
                hash = CriptoMD5.encryptMD5(name + perfil + senha);
            } catch (NoSuchAlgorithmException ex) {
                ex.printStackTrace();
            }
        }
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ApiUser other = (ApiUser) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

}
