package br.com.lowcodesystem.model;

import br.com.lowcodesystem.util.FormataTexto;
import java.io.File;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Maykon
 */
public class Project implements Serializable {

    private String projectName;
    private String projectID;
    private String projectNameCSS;
    private String projectVersion = "1.0.0";
    private String projectYear = FormataTexto.toString(new Date().getYear() + 1900);
    private String sqlLogin;
    private String opcoesLogin;
    private boolean loginExterno = false;
    private String urlLogin = "http://localhost:510/api/v1/loginusuario";
    private String pathUploadFiles;
    private String logPath;
    private boolean logEnable;
    private String datasource;
    private boolean renameFilesUpload = true;
    private boolean useImageLogo;
    private String cssGeral = "";
    private String jsGeral = "";
    private String wsPort = "18080";
    private String encode = "UTF-8";

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectNameCSS() {
        return projectNameCSS == null ? "" : projectNameCSS;
    }

    public void setProjectNameCSS(String projectNameCSS) {
        this.projectNameCSS = projectNameCSS;
    }

    public String getSqlLogin() {
        return sqlLogin == null ? "" : sqlLogin;
    }

    public void setSqlLogin(String sqlLogin) {
        this.sqlLogin = sqlLogin;
    }

    public String getPathUploadFiles() {
        if (pathUploadFiles != null && !pathUploadFiles.endsWith(File.separator)) {
            pathUploadFiles += File.separator;
        }
        return pathUploadFiles;
    }

    public void setPathUploadFiles(String pathUploadFiles) {
        this.pathUploadFiles = pathUploadFiles;
    }

    public String getLogPath() {
        if (logPath != null && !logPath.endsWith(File.separator)) {
            logPath += File.separator;
        }
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public boolean isLogEnable() {
        return logEnable;
    }

    public void setLogEnable(boolean logEnable) {
        this.logEnable = logEnable;
    }

    public String getProjectVersion() {
        return projectVersion;
    }

    public void setProjectVersion(String projectVersion) {
        this.projectVersion = projectVersion;
    }

    public String getDatasource() {
        return datasource == null ? "" : datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

    public boolean isRenameFilesUpload() {
        return renameFilesUpload;
    }

    public void setRenameFilesUpload(boolean renameFilesUpload) {
        this.renameFilesUpload = renameFilesUpload;
    }

    public boolean isUseImageLogo() {
        return useImageLogo;
    }

    public void setUseImageLogo(boolean useImageLogo) {
        this.useImageLogo = useImageLogo;
    }

    public String getCssGeral() {
        return cssGeral;
    }

    public void setCssGeral(String cssGeral) {
        this.cssGeral = cssGeral;
    }

    public boolean isLoginExterno() {
        return loginExterno;
    }

    public void setLoginExterno(boolean loginExterno) {
        this.loginExterno = loginExterno;
    }

    public String getJsGeral() {
        return jsGeral;
    }

    public void setJsGeral(String jsGeral) {
        this.jsGeral = jsGeral;
    }

    public String getUrlLogin() {
        if (urlLogin == null || urlLogin.equals("null")) {
            return "";
        }
        return urlLogin;
    }

    public void setUrlLogin(String urlLogin) {
        this.urlLogin = urlLogin;
    }

    public String getOpcoesLogin() {
        if (opcoesLogin == null || opcoesLogin.equals("null")) {
            return "";
        }
        return opcoesLogin;
    }

    public void setOpcoesLogin(String opcoesLogin) {
        this.opcoesLogin = opcoesLogin;
    }

    public String getWsPort() {
        return wsPort;
    }

    public void setWsPort(String wsPort) {
        this.wsPort = wsPort;
    }

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getProjectYear() {
        return projectYear;
    }

    public void setProjectYear(String projectYear) {
        this.projectYear = projectYear;
    }

}
