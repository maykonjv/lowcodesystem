package br.com.lowcodesystem.model;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author Maykon
 */
public class Project implements Serializable {

    private String projectName;
    private String projectNameCSS;
    private String version;
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
    private String encode = "ISO-8859-1";

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

}
