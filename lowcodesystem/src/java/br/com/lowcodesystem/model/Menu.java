package br.com.lowcodesystem.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maykon
 */
public class Menu implements Serializable {

    private String id;
    private String label;
    private String page;
    private String param;
    private String icon;
    private List<Menu> childrenId = new ArrayList<Menu>();
    private String parentId;

    public Menu() {
    }

    public Menu(String id, String label) {
        this.id = id;
        this.label = label;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label == null ? "" : label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPage() {
        return page == null ? "" : page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getIcon() {
        return icon == null ? "" : icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<Menu> getChildrenId() {
        return childrenId;
    }

    public void setChildrenId(List<Menu> childrenId) {
        this.childrenId = childrenId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParam() {
        if (param != null && !param.trim().isEmpty() && !param.startsWith("&")) {
            param = "&" + param;
        }
        return param == null ? "" : param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Menu other = (Menu) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Menu{" + "id=" + id + ", label=" + label + ", page=" + page + ", icon=" + icon + ", parentId=" + parentId + '}';
    }

}
