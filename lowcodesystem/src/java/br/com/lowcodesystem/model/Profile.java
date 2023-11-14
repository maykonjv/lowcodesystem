/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lowcodesystem.model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Maykon
 */
public class Profile {

    private String name;
    private Map<String, ProfileAction> actions = new HashMap<String, ProfileAction>();

    public Profile() {
    }

    public Profile(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, ProfileAction> getActions() {
        return actions;
    }

    public void setActions(Map<String, ProfileAction> actions) {
        this.actions = actions;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + (this.name != null ? this.name.hashCode() : 0);
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
        final Profile other = (Profile) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Profile{" + "name=" + name + ", actions=" + actions + '}';
    }

}
