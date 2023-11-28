package br.com.lowcodesystem.model;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maykon
 */
public class Page implements Serializable {

    @Expose
    private String id;
    @Expose
    private String name;
    private String javascript;
    private String javascriptBack = ""
            + "function validSave(){\n"
            + "   msg = '';\n"
            + "   return true;\n"
            + "}\n"
            + "\n"
            + "function validEdit(){\n"
            + "   msg = '';\n"
            + "   return true;\n"
            + "}"
            + "\n"
            + "function validDelete(){\n"
            + "   msg = '';\n"
            + "   return true;\n"
            + "}"
            + "\n"
            + "function validActivate(){\n"
            + "   msg = '';\n"
            + "   return true;\n"
            + "}";
    private String sqlPesquisa;
    private String sqlExport;
    private String sqlAPI;
    private String sqlEdit;
    private String sqlSave;
    private String sqlUpdateView;
    private String sqlActivite;
    private String sqlDelete;
    private String sqlPesquisaDS;
    private String sqlExportDS;
    private String sqlAPIDS;
    private String sqlEditDS;
    private String sqlSaveDS;
    private String sqlUpdateViewDS;
    private String sqlActiviteDS;
    private String sqlDeleteDS;
    @Expose
    private List<Field> fields = new ArrayList<Field>();
    @Expose
    private boolean hasList;
    @Expose
    private boolean hasUpdate;
    @Expose
    private boolean hasDelete;
    @Expose
    private boolean hasActive;
    @Expose
    private boolean hasSearch;
    @Expose
    private boolean hasNew;
    @Expose
    private boolean hasView;

    private boolean clearData;

    private boolean tbPaging = true;
    private boolean tbOrdering = true;
    private boolean tbInfo = true;
    private boolean tbCheck;
    private boolean tbSearching = true;

    public Page() {
    }

    public Page(String id) {
        this.id = id;
    }

    public Page(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public boolean isHasList() {
        return hasList;
    }

    public void setHasList(boolean hasList) {
        this.hasList = hasList;
    }

    public boolean isHasUpdate() {
        return hasUpdate;
    }

    public void setHasUpdate(boolean hasUpdate) {
        this.hasUpdate = hasUpdate;
    }

    public boolean isHasDelete() {
        return hasDelete;
    }

    public void setHasDelete(boolean hasDelete) {
        this.hasDelete = hasDelete;
    }

    public boolean isHasActive() {
        return hasActive;
    }

    public void setHasActive(boolean hasActive) {
        this.hasActive = hasActive;
    }

    public boolean isHasSearch() {
        return hasSearch;
    }

    public void setHasSearch(boolean hasSearch) {
        this.hasSearch = hasSearch;
    }

    public boolean isHasNew() {
        return hasNew;
    }

    public void setHasNew(boolean hasNew) {
        this.hasNew = hasNew;
    }

    public boolean isHasView() {
        return hasView;
    }

    public void setHasView(boolean hasView) {
        this.hasView = hasView;
    }

    public String getJavascript() {
        return javascript == null ? "" : javascript;
    }

    public void setJavascript(String javascript) {
        this.javascript = javascript;
    }

    public String getSqlPesquisa() {
        return sqlPesquisa == null ? "" : sqlPesquisa;
    }

    public void setSqlPesquisa(String sqlPesquisa) {
        this.sqlPesquisa = sqlPesquisa;
    }

    public String getSqlExport() {
        return sqlExport == null ? sqlPesquisa : sqlExport;
    }

    public void setSqlExport(String sqlExport) {
        this.sqlExport = sqlExport;
    }

    public String getSqlUpdateView() {
        return sqlUpdateView == null ? "" : sqlUpdateView;
    }

    public void setSqlUpdateView(String sqlUpdateView) {
        this.sqlUpdateView = sqlUpdateView;
    }

    public String getJavascriptBack() {
        return javascriptBack == null ? "" : javascriptBack;
    }

    public void setJavascriptBack(String javascriptBack) {
        this.javascriptBack = javascriptBack;
    }

    public String getSqlEdit() {
        return sqlEdit == null ? "" : sqlEdit;
    }

    public void setSqlEdit(String sqlEdit) {
        this.sqlEdit = sqlEdit;
    }

    public String getSqlSave() {
        return sqlSave == null ? "" : sqlSave;
    }

    public void setSqlSave(String sqlSave) {
        this.sqlSave = sqlSave;
    }

    public String getSqlActivite() {
        return sqlActivite == null ? "" : sqlActivite;
    }

    public void setSqlActivite(String sqlActivite) {
        this.sqlActivite = sqlActivite;
    }

    public String getSqlDelete() {
        return sqlDelete == null ? "" : sqlDelete;
    }

    public void setSqlDelete(String sqlDelete) {
        this.sqlDelete = sqlDelete;
    }

    public String getSqlPesquisaDS() {
        return sqlPesquisaDS == null ? "" : sqlPesquisaDS;
    }

    public void setSqlPesquisaDS(String sqlPesquisaDS) {
        this.sqlPesquisaDS = sqlPesquisaDS;
    }

    public String getSqlExportDS() {
        return sqlExportDS == null ? "" : sqlExportDS;
    }

    public void setSqlExportDS(String sqlExportDS) {
        this.sqlExportDS = sqlExportDS;
    }

    public String getSqlEditDS() {
        return sqlEditDS == null ? "" : sqlEditDS;
    }

    public void setSqlEditDS(String sqlEditSD) {
        this.sqlEditDS = sqlEditSD;
    }

    public String getSqlSaveDS() {
        return sqlSaveDS == null ? "" : sqlSaveDS;
    }

    public void setSqlSaveDS(String sqlSaveSD) {
        this.sqlSaveDS = sqlSaveSD;
    }

    public String getSqlUpdateViewDS() {
        return sqlUpdateViewDS == null ? "" : sqlUpdateViewDS;
    }

    public void setSqlUpdateViewDS(String sqlUpdateViewDS) {
        this.sqlUpdateViewDS = sqlUpdateViewDS;
    }

    public String getSqlActiviteDS() {
        return sqlActiviteDS == null ? "" : sqlActiviteDS;
    }

    public void setSqlActiviteDS(String sqlActiviteDS) {
        this.sqlActiviteDS = sqlActiviteDS;
    }

    public String getSqlDeleteDS() {
        return sqlDeleteDS == null ? "" : sqlDeleteDS;
    }

    public void setSqlDeleteDS(String sqlDeleteDS) {
        this.sqlDeleteDS = sqlDeleteDS;
    }

    public boolean isTbPaging() {
        return tbPaging;
    }

    public void setTbPaging(boolean tbPaging) {
        this.tbPaging = tbPaging;
    }

    public boolean isTbOrdering() {
        return tbOrdering;
    }

    public void setTbOrdering(boolean tbOrdering) {
        this.tbOrdering = tbOrdering;
    }

    public boolean isTbInfo() {
        return tbInfo;
    }

    public void setTbInfo(boolean tbInfo) {
        this.tbInfo = tbInfo;
    }

    public boolean isTbCheck() {
        return tbCheck;
    }

    public void setTbCheck(boolean tbCheck) {
        this.tbCheck = tbCheck;
    }

    public boolean isTbSearching() {
        return tbSearching;
    }

    public void setTbSearching(boolean tbSearching) {
        this.tbSearching = tbSearching;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Page other = (Page) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    public boolean isClearData() {
        return clearData;
    }

    public void setClearData(boolean clearData) {
        this.clearData = clearData;
    }

    public String getSqlAPI() {
        return sqlAPI == null ? getSqlExport() : sqlAPI;
    }

    public void setSqlAPI(String sqlAPI) {
        this.sqlAPI = sqlAPI;
    }

    public String getSqlAPIDS() {
        return sqlAPIDS == null ? getSqlExportDS() : sqlAPIDS;
    }

    public void setSqlAPIDS(String sqlAPIDS) {
        this.sqlAPIDS = sqlAPIDS;
    }

    @Override
    public String toString() {
        return "Page{" + "id=" + id + ", name=" + name + ", javascript=" + javascript + ", javascriptBack=" + javascriptBack + ", sqlPesquisa=" + sqlPesquisa + ", sqlExport=" + sqlExport + ", sqlAPI=" + sqlAPI + ", sqlEdit=" + sqlEdit + ", sqlSave=" + sqlSave + ", sqlUpdateView=" + sqlUpdateView + ", sqlActivite=" + sqlActivite + ", sqlDelete=" + sqlDelete + ", sqlPesquisaDS=" + sqlPesquisaDS + ", sqlExportDS=" + sqlExportDS + ", sqlAPIDS=" + sqlAPIDS + ", sqlEditDS=" + sqlEditDS + ", sqlSaveDS=" + sqlSaveDS + ", sqlUpdateViewDS=" + sqlUpdateViewDS + ", sqlActiviteDS=" + sqlActiviteDS + ", sqlDeleteDS=" + sqlDeleteDS + ", fields=" + fields + ", hasList=" + hasList + ", hasUpdate=" + hasUpdate + ", hasDelete=" + hasDelete + ", hasActive=" + hasActive + ", hasSearch=" + hasSearch + ", hasNew=" + hasNew + ", hasView=" + hasView + ", clearData=" + clearData + ", tbPaging=" + tbPaging + ", tbOrdering=" + tbOrdering + ", tbInfo=" + tbInfo + ", tbCheck=" + tbCheck + ", tbSearching=" + tbSearching + '}';
    }

}
