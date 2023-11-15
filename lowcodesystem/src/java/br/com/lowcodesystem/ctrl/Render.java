package br.com.lowcodesystem.ctrl;

import static br.com.lowcodesystem.ctrl.Scheduler.listThreadNames;
import br.com.lowcodesystem.dao.Column;
import br.com.lowcodesystem.dao.Dao;
import br.com.lowcodesystem.dao.ResultSQL;
import br.com.lowcodesystem.model.ApiUser;
import br.com.lowcodesystem.model.Field;
import br.com.lowcodesystem.model.Menu;
import br.com.lowcodesystem.model.Page;
import br.com.lowcodesystem.model.Profile;
import br.com.lowcodesystem.model.Project;
import br.com.lowcodesystem.model.ResultScriptBackend;
import br.com.lowcodesystem.util.ChartBuilder;
import br.com.lowcodesystem.util.FormataTexto;
import br.com.lowcodesystem.util.Log;
import br.com.lowcodesystem.util.LogWrite;
import br.com.lowcodesystem.util.ManterXML;
import br.com.lowcodesystem.util.ProjectLoad;
import br.com.lowcodesystem.ws.WebService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 *
 * @author Maykon Servlet responsavel por renderizar os formularios gerados
 */
public class Render extends HttpServlet implements ServletContextListener {

    private static final String ACTION_LOGOUT = "logout";
    public static final String ACTION_ADD = "Cadastro";
    public static final String ACTION_LIST = "Listagem";
    public static final String ACTION_SEARCH = "Pesquisa";
    public static final String ACTION_ALTER = "Alterar";
    public static final String ACTION_VIEW = "Visualizar";
    public static final String ACTION_DELETE = "Excluir";
    public static final String ACTION_ACTIVATE = "Inativar";
    public static final String ACTION_ALL = "Todos";
    public static final String ACTION_BASE = "Base";
    public static final String ACTION_LOG = "action_log";
    public static final String PAGE_LOG = "page_log";

    public static List<Page> pages = new ArrayList<Page>();
    public static List<Menu> menus = new ArrayList<Menu>();
    public static Project project = new Project();
    public static List<Profile> profiles = new ArrayList<Profile>();
    public static List<ApiUser> apiUsers = new ArrayList<ApiUser>();
    public static String ADMINISTRADOR = "ADMINISTRADOR";
    private final Dao dao = new Dao();

    @Override
    public void init() throws ServletException {
        super.init();
        String warName = Custom.configName;
        System.out.println(warName);
        ProjectLoad.load(warName);
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Log.info("Inicializando server....");
        System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) + "Inicializando server...");
        String warName = Custom.configName;
        System.out.println(warName);
        ProjectLoad.load(warName);
        Log.config(project.getLogPath(), warName, (1024 * 1024 * 5));
        ProjectLoad.startThread();
        ProjectLoad.loadProperties(sce.getServletContext());
        new Scheduler(dao, ProjectLoad.properties);

        if (project.getWsPort() != null && !project.getWsPort().isEmpty() && WebService.httpServer == null) {
            WebService.startWS(project.getWsPort(), Custom.configName, dao);
        } else {
            Log.error("Erro ao iniciar webservices: porta não configurada ou webservice já ativo.");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Log.info("Finalizando server....");
        ProjectLoad.rotinasAgendadasRunning = false;
        Scheduler.rotinasAgendadasRunning = false;
        if (WebService.httpServer != null) {
            WebService.httpServer.stop(0);
        }
        //stoping threads
        Thread[] a = new Thread[1000];
        int n = Thread.enumerate(a);
        for (int i = 0; i < n; i++) {
            for (String name : listThreadNames) {
                if (a[i].getName().equals(name)) {
                    a[i].stop();
                    a[i].interrupt();
                    System.out.println("kill thread " + name + "...");
                    break;
                }
            }
        }
    }

    /**
     * Renderes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long begin = Log.begin();
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(true);
        ServletContext application = getServletConfig().getServletContext();
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        String page = request.getParameter("page");
        if (action.contains("§")) {
            action = new String(action.getBytes("ISO-8859-1"));
        }

        request.setAttribute("projectName", project.getProjectName());
        request.setAttribute("projectNameCSS", project.getProjectNameCSS());
        /////// SOMENTE DURANTE DESENVOLVIMENTO
//        if (request.getParameter(Render.ADMINISTRADOR) != null) {
//            session.setAttribute("dev", Boolean.parseBoolean(request.getParameter(Render.ADMINISTRADOR)));
//        session.setAttribute("dev", true);
//        }

        if (ManterXML.pasta == null || ManterXML.pasta.equals("null") || ManterXML.pasta.isEmpty()) {
            RequestDispatcher rd = request.getRequestDispatcher("config.jsp");
            rd.forward(request, response);
            return;
        }

        //Valida se o usuario esta autenticado
        Object auth = session.getAttribute("authenticated");
        if (auth == null || auth.toString().equals("false") || (page != null && page.equals("login"))) {
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            request.setAttribute("sqlLogin", project.getSqlLogin());
            request.setAttribute("opcoesLogin", project.getOpcoesLogin());
            rd.forward(request, response);
            return;
        }

        //ParameterMap
        Map<String, String> param = new HashMap<String, String>();
        for (String key : request.getParameterMap().keySet()) {
            request.setAttribute(key, request.getParameter(key));
            param.put(key, request.getParameter(key));
        }
        //Attribute
        Enumeration<String> attrs = request.getAttributeNames();
        while (attrs.hasMoreElements()) {
            String key = (String) attrs.nextElement();
            param.put(key, request.getAttribute(key).toString());
        }

        param.put("message_result", "");
        param.put("message_error", "");
        if (page != null && !page.equals("page_log")) {
            Log.request("Request - " + param);
        }
        RequestDispatcher rd = request.getRequestDispatcher("template.jsp");
        String msgError = "";
        if (page != null) {
            int idPage = Render.pages.indexOf(new Page(page));
            if (idPage != -1) {
                Page p = Render.pages.get(idPage);
                boolean alterPage = false;
                for (Field f : p.getFields()) {
                    if (f.getType().equals("date") && param.get(f.getId()) != null && !param.get(f.getId()).isEmpty()) {
                        try {
                            new SimpleDateFormat(f.getFormat()).parse(param.get(f.getId()));
                            if (Pattern.compile("[a-zA-Z]").matcher(param.get(f.getId())).find()) {
                                msgError += "<li>O campo <b>" + f.getLabel() + "</b> espera uma data no formato (" + f.getFormat() + "). Valor informado: (" + param.get(f.getId()) + ")</li>";
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.error(e);
                            msgError += "<li>O campo <b>" + f.getLabel() + "</b> espera uma data no formato (" + f.getFormat() + "). Valor informado: (" + param.get(f.getId()) + ")</li>";
                        }
                    } else {
                        if (f.getFormat() != null && !f.getFormat().isEmpty() && param.get(f.getId()) != null) {
                            param.put(f.getId(), param.get(f.getId()).replaceAll(f.getFormat(), ""));
                        }
                    }
                    if (f.getType().equals("numeric") && param.get(f.getId()) != null && !param.get(f.getId()).isEmpty()) {
                        try {
                            new BigDecimal(param.get(f.getId()));
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.error(e);
                            msgError += "<li>O campo <b>" + f.getLabel() + "</b> espera um valor numérico. Valor informado: (" + param.get(f.getId()) + ")</li>";
                        }
                    }
                    if (f.getType().equals("boolean") && param.get(f.getId()) != null && !param.get(f.getId()).isEmpty()) {
                        try {
                            new Boolean(param.get(f.getId()));
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.error(e);
                            msgError += "<li>O campo <b>" + f.getLabel() + "</b> espera um valor true/false. Valor informado: (" + param.get(f.getId()) + ")</li>";
                        }
                    }
                    if (f.getScope().equals(Field.SCOPE_SESSION) && param.get(f.getId()) != null) {
                        session.setAttribute(f.getId(), param.get(f.getId()));
                    } else if (f.getScope().equals(Field.SCOPE_APPLICATION) && param.get(f.getId()) != null) {
                        application.setAttribute(f.getId(), param.get(f.getId()));
                        ProjectLoad.properties.setProperty(f.getId(), param.get(f.getId()));
                        alterPage = true;
                    }
                }
                if (alterPage && msgError.isEmpty()) {
                    ProjectLoad.writeProperties();
                }
            }
        }
        //Session
        Enumeration varSession = request.getSession(true).getAttributeNames();
        while (varSession.hasMoreElements()) {
            String key = (String) varSession.nextElement();
            if (!key.contains("javamelody")) {
                param.put(key, request.getSession(true).getAttribute(key).toString());
            }
        }
        //Application
        Enumeration<String> attrsApp = application.getAttributeNames();
        while (attrsApp.hasMoreElements()) {
            String key = (String) attrsApp.nextElement();
            if (!key.contains(".")) {
                param.put(key, application.getAttribute(key).toString());
            }
        }
        if (!msgError.isEmpty()) {
            request.setAttribute("error", msgError);
        } else if (action.equals(ACTION_LOGOUT)) {
            request.getSession().invalidate();
        } else if (page != null && page.equals(PAGE_LOG)) {
            if (ACTION_LOG.equals(action)) {
                if (param.containsKey("btnDelete")) {
                    Log.delete(param.get("logFile"));
                    System.out.println("Excluindo:" + param.get("logFile"));
                } else if (param.containsKey("btnBackup")) {
                    Log.backup();
                    System.out.println("Backup:" + param.get("logFile"));
                } else {
                    Log.levels.put(Log.ERROR, true);
                    Log.levels.put(Log.DEBUG, param.containsKey("hasDebugRegister") || param.containsKey("hasAllRegister"));
                    Log.levels.put(Log.CONFIG, param.containsKey("hasConfigRegister") || param.containsKey("hasAllRegister"));
                    Log.levels.put(Log.INFO, param.containsKey("hasInfoRegister") || param.containsKey("hasAllRegister"));
                    Log.levels.put(Log.TIME, param.containsKey("hasTimeRegister") || param.containsKey("hasAllRegister"));
                    Log.levels.put(Log.WARNING, param.containsKey("hasWarningRegister") || param.containsKey("hasAllRegister"));
                    Log.levels.put(Log.REQUEST, param.containsKey("hasRequestRegister") || param.containsKey("hasAllRegister"));
                    LogWrite.levels.put(Log.ERROR, true);
                    LogWrite.levels.put(Log.DEBUG, param.containsKey("hasDebugRegister") || param.containsKey("hasAllRegister"));
                    LogWrite.levels.put(Log.CONFIG, param.containsKey("hasConfigRegister") || param.containsKey("hasAllRegister"));
                    LogWrite.levels.put(Log.INFO, param.containsKey("hasInfoRegister") || param.containsKey("hasAllRegister"));
                    LogWrite.levels.put(Log.TIME, param.containsKey("hasTimeRegister") || param.containsKey("hasAllRegister"));
                    LogWrite.levels.put(Log.WARNING, param.containsKey("hasWarningRegister") || param.containsKey("hasAllRegister"));
                    LogWrite.levels.put(Log.REQUEST, param.containsKey("hasRequestRegister") || param.containsKey("hasAllRegister"));
                }
                if (Log.levels.containsKey(Log.DEBUG) && Log.levels.get(Log.DEBUG)) {
                    param.put("hasDebugRegister", "on");
                }
                if (Log.levels.containsKey(Log.CONFIG) && Log.levels.get(Log.CONFIG)) {
                    param.put("hasConfigRegister", "on");
                }
                if (Log.levels.containsKey(Log.INFO) && Log.levels.get(Log.INFO)) {
                    param.put("hasInfoRegister", "on");
                }
                if (Log.levels.containsKey(Log.TIME) && Log.levels.get(Log.TIME)) {
                    param.put("hasTimeRegister", "on");
                }
                if (Log.levels.containsKey(Log.WARNING) && Log.levels.get(Log.WARNING)) {
                    param.put("hasWarningRegister", "on");
                }
                if (Log.levels.containsKey(Log.REQUEST) && Log.levels.get(Log.REQUEST)) {
                    param.put("hasRequestRegister", "on");
                }
                if (param.containsKey("hasAll")) {
                    param.put("hasError", "on");
                    param.put("hasDebug", "on");
                    param.put("hasConfig", "on");
                    param.put("hasInfo", "on");
                    param.put("hasTime", "on");
                    param.put("hasWarning", "on");
                    param.put("hasRequest", "on");
                }
            } else {
                if (Log.levels.containsKey(Log.DEBUG) && Log.levels.get(Log.DEBUG)) {
                    param.put("hasDebugRegister", "on");
                }
                if (Log.levels.containsKey(Log.CONFIG) && Log.levels.get(Log.CONFIG)) {
                    param.put("hasConfigRegister", "on");
                }
                if (Log.levels.containsKey(Log.INFO) && Log.levels.get(Log.INFO)) {
                    param.put("hasInfoRegister", "on");
                }
                if (Log.levels.containsKey(Log.TIME) && Log.levels.get(Log.TIME)) {
                    param.put("hasTimeRegister", "on");
                }
                if (Log.levels.containsKey(Log.WARNING) && Log.levels.get(Log.WARNING)) {
                    param.put("hasWarningRegister", "on");
                }
                if (Log.levels.containsKey(Log.REQUEST) && Log.levels.get(Log.REQUEST)) {
                    param.put("hasRequestRegister", "on");
                }

            }

        } else if (action.equals(ACTION_BASE)) {
            try {
                param = new Custom().custom(param, dao);
                updateSessionApplication(param, request, application);
                Page p = Render.pages.get(Render.pages.indexOf(new Page(page)));
                if (executeScriptButton(p, param)) {
                    request.setAttribute("success", "Rotina executada com sucesso!\n");
                }
                if (!param.get("message_result").isEmpty()) {
                    request.setAttribute("success", param.get("message_result"));
                }
                if (!param.get("message_error").isEmpty()) {
                    request.setAttribute("error", param.get("message_error"));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                request.setAttribute("error", ex.getMessage());
            }
        } else if (action.equals(ACTION_ADD)) {
            if (param.get("btnGravar") != null) {
                Page p = Render.pages.get(Render.pages.indexOf(new Page(page)));
                //valida
                ResultScriptBackend result = validScriptBackEnd("validSave", p.getJavascriptBack(), param);
                if (result.valid) {
                    try {
                        param = new Custom().save(param, dao);
                        updateSessionApplication(param, request, application);
                        String script = p.getSqlSave();
                        if (param != null) {
                            String msg = param.get("message_result");
                            if (script != null && !script.trim().isEmpty() && !script.equals("null")) {
                                script = FormataTexto.replaceScript(script, param);
                                dao.insertDeletUpdate(p.getSqlSaveDS(), script);
                            }
                            File temp = new File(Render.project.getPathUploadFiles() + File.separator + "temp");
                            if (temp.exists()) {
                                for (Field f : p.getFields()) {
                                    if (f.getComponent().equals("file")) {
                                        String name = param.get(f.getId());
                                        File file = new File(temp.getAbsolutePath() + File.separator + name);
                                        File destino = new File(temp.getParent() + File.separator + name);
                                        if (destino.exists()) {
                                            destino.delete();
                                        }
                                        file.renameTo(destino);
                                    }
                                }
                            }
                            if (p.isClearData()) {
                                param.clear();
                                param.put("message_result", msg);
                                param.put("projectName", project.getProjectName());
                                param.put("projectNameCSS", project.getProjectNameCSS());
                            }
                        }
                        if (p.isClearData()) {
                            Enumeration<String> keys = request.getAttributeNames();
                            while (keys.hasMoreElements()) {
                                request.removeAttribute(keys.nextElement());
                            }
                            //Session
                            varSession = request.getSession(true).getAttributeNames();
                            while (varSession.hasMoreElements()) {
                                String key = (String) varSession.nextElement();
                                if (!key.contains("javamelody")) {
                                    param.put(key, request.getSession(true).getAttribute(key).toString());
                                }
                            }
                            //Application
                            attrsApp = application.getAttributeNames();
                            while (attrsApp.hasMoreElements()) {
                                String key = (String) attrsApp.nextElement();
                                if (!key.contains(".")) {
                                    param.put(key, application.getAttribute(key).toString());
                                }
                            }
                        }
                        if (param.get("message_result").isEmpty()) {
                            request.setAttribute("success", "Registro inserido com sucesso!\n" + ((result.msg != null) ? result.msg : ""));
                        } else {
                            request.setAttribute("success", param.get("message_result"));

                            if (!param.get("message_error").isEmpty()) {
                                request.setAttribute("error", param.get("message_error"));
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        request.setAttribute("error", ex.getMessage());
                    }
                } else {
                    request.setAttribute("error", result.msg);
                }
            } else {
                try {
                    param = new Custom().toSave(param, dao);
                    updateSessionApplication(param, request, application);
                    Page p = Render.pages.get(Render.pages.indexOf(new Page(page)));
                    if (executeScriptButton(p, param)) {
                        request.setAttribute("success", "Rotina executada com sucesso!\n");
                    }
                    if (!param.get("message_result").isEmpty()) {
                        request.setAttribute("success", param.get("message_result"));
                    }
                    if (!param.get("message_error").isEmpty()) {
                        request.setAttribute("error", param.get("message_error"));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    request.setAttribute("error", ex.getMessage());
                }
            }
        } else if (action.equals(ACTION_ALTER)) {
            if (param.get("btnGravar") != null) {
                Page p = Render.pages.get(Render.pages.indexOf(new Page(page)));
                //valida
                ResultScriptBackend result = validScriptBackEnd("validEdit", p.getJavascriptBack(), param);
                if (result.valid) {
                    try {
                        param = new Custom().update(param, dao);
                        updateSessionApplication(param, request, application);
                        File temp = new File(Render.project.getPathUploadFiles() + File.separator + "temp");
                        if (temp.exists()) {
                            for (Field f : p.getFields()) {
                                if (f.getComponent().equals("file")) {
                                    String name = param.get(f.getId());
                                    String newName = param.get("tmp_name_" + f.getId());
                                    if (newName == null || newName.isEmpty()) {
                                        newName = param.get(f.getId());
                                    }
                                    File file = new File(temp.getAbsolutePath() + File.separator + name);
                                    if (file.exists()) {
                                        File destino = new File(temp.getParent() + File.separator + newName);
                                        if (destino.exists()) {
                                            destino.delete();
                                        }
                                        file.renameTo(destino);
                                        param.put(f.getId(), newName);
                                    }
                                }
                            }
                        }
                        String script = p.getSqlEdit();
                        if (param != null) {
                            if (script != null && !script.trim().isEmpty() && !script.equals("null")) {
                                script = FormataTexto.replaceScriptWithValidation(script, param);
                                dao.insertDeletUpdate(p.getSqlEditDS(), script);

                            }
                        }
                        if (param.get("message_result").isEmpty()) {
                            request.setAttribute("success", "Registro alterado com sucesso!\n" + ((result.msg != null) ? result.msg : ""));
                        } else {
                            request.setAttribute("success", param.get("message_result"));
                            if (!param.get("message_error").isEmpty()) {
                                request.setAttribute("error", param.get("message_error"));
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        request.setAttribute("error", ex.getMessage());
                    }
                } else {
                    request.setAttribute("error", result.msg);
                }
            } else {
                try {
                    param = new Custom().toUpdate(param, dao);
                    updateSessionApplication(param, request, application);
                    Page p = Render.pages.get(Render.pages.indexOf(new Page(page)));
                    if (executeScriptButton(p, param)) {
                        request.setAttribute("success", "Rotina executada com sucesso!\n");
                    }
                    if (!param.get("message_result").isEmpty()) {
                        request.setAttribute("success", param.get("message_result"));
                    }
                    if (!param.get("message_error").isEmpty()) {
                        request.setAttribute("error", param.get("message_error"));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    request.setAttribute("error", ex.getMessage());
                }
            }
        } else if (action.equals(ACTION_ACTIVATE)) {
            Page p = Render.pages.get(Render.pages.indexOf(new Page(page)));
            //valida
            ResultScriptBackend result = validScriptBackEnd("validActivate", p.getJavascriptBack(), param);
            if (result.valid) {
                String script = p.getSqlActivite();
                try {
                    param = new Custom().active(param, dao);
                    updateSessionApplication(param, request, application);
                    if (param != null) {
                        if (script != null && !script.trim().isEmpty() && !script.equals("null")) {
                            script = FormataTexto.replaceScript(script, param);
                            dao.insertDeletUpdate(p.getSqlActiviteDS(), script);
                        }
                    }
                    if (param.get("message_result").isEmpty()) {
                        request.setAttribute("success", "Registro inserido com sucesso!\n" + ((result.msg != null) ? result.msg : ""));
                    } else {
                        request.setAttribute("success", param.get("message_result"));
                        if (!param.get("message_error").isEmpty()) {
                            request.setAttribute("error", param.get("message_error"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("error", e.getMessage());
                }
            } else {
                request.setAttribute("error", result.msg);
            }
        } else if (action.equals(ACTION_DELETE)) {
            Page p = Render.pages.get(Render.pages.indexOf(new Page(page)));
            //valida
            ResultScriptBackend result = validScriptBackEnd("validDelete", p.getJavascriptBack(), param);
            if (result.valid) {
                try {
                    param = new Custom().delete(param, dao);
                    updateSessionApplication(param, request, application);
                    String script = p.getSqlDelete();
                    if (param != null) {
                        if (script != null && !script.trim().isEmpty() && !script.equals("null")) {
                            script = FormataTexto.replaceScript(script, param);
                            dao.insertDeletUpdate(p.getSqlDeleteDS(), script);
                            File file = new File(Render.project.getPathUploadFiles());
                            if (file.exists()) {
//                                ResultSQL rs = null;
//                                for (Field f : p.getFields()) {
//                                    if (f.getComponent().equals("file")) {
//                                        if(rs == null){
//                                            rs = dao.select(p.getSqlUpdateView(), params)
//                                        }
//                                        //TODO delete image for object
//                                    }
//                                }
                            }
                        }
                    }
                    if (param.get("message_result").isEmpty()) {
                        request.setAttribute("success", "Registro excluído com sucesso!\n" + ((result.msg != null) ? result.msg : ""));
                    } else {
                        request.setAttribute("success", param.get("message_result"));
                        if (!param.get("message_error").isEmpty()) {
                            request.setAttribute("error", param.get("message_error"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("error", e.getMessage());
                }
            } else {
                request.setAttribute("error", result.msg);
            }
        } else if (action.equals(ACTION_LIST)) {
            if (exportXLSX(response, request, param)) {
                return;
            }
        } else if (action.equals(ACTION_SEARCH)) {
            try {
                param = new Custom().search(param, dao);
                updateSessionApplication(param, request, application);
                Page p = Render.pages.get(Render.pages.indexOf(new Page(page)));
                if (executeScriptButton(p, param)) {
                    request.setAttribute("success", "Rotina executada com sucesso!\n");
                }
                if (exportXLSX(response, request, param)) {
                    return;
                }
                param.remove(ACTION_SEARCH);
                session.setAttribute(ACTION_SEARCH, param);
            } catch (Exception ex) {
                ex.printStackTrace();
                request.setAttribute("error", ex.getMessage());
            }
        }

        if (page != null) {

            for (Menu menu : menus) {
                if (menu.getPage() != null && menu.getPage().equals(page)) {
                    request.setAttribute("menu", menu);
                    break;
                }
                if (menu.getChildrenId() != null && !menu.getChildrenId().isEmpty()) {
                    for (Menu m : menu.getChildrenId()) {
                        if (m.getPage().equals(page)) {
                            request.setAttribute("menu", m);
                            break;
                        }
                    }
                }
            }

            for (Page p : pages) {
                if (p.getId().equals(page)) {
                    request.setAttribute("p", p);
                    //retorna a action da pagina selecionada
                    if (action.isEmpty() || action.equals(Render.ACTION_ACTIVATE) || action.equals(Render.ACTION_DELETE)) {
                        if (p.isHasSearch()) {
                            action = Render.ACTION_SEARCH;
                        } else if (p.isHasList() || p.isHasActive() || p.isHasDelete()) {
                            action = Render.ACTION_LIST;
                        } else if (p.isHasUpdate()) {
                            action = Render.ACTION_ALTER;
                        } else if (p.isHasView()) {
                            action = Render.ACTION_VIEW;
                        } else if (p.isHasNew()) {
                            action = Render.ACTION_ADD;
                        } else {
                            action = Render.ACTION_BASE;
                        }
                    }
                    param.put("action", action);
                    if (action.equals(Render.ACTION_SEARCH)) {
                        Object sessionObj = session.getAttribute(Render.ACTION_SEARCH);
                        if (sessionObj instanceof Map && sessionObj != null) {
                            Map<String, String> sessionParam = (Map) sessionObj;
                            if (sessionParam.containsKey("page") && sessionParam.get("page").equals(page) && request.getParameter("clear") == null) {
                                for (String key : sessionParam.keySet()) {
                                    request.setAttribute(key, sessionParam.get(key));
                                    param.put(key, sessionParam.get(key));
                                }
                            } else {
                                session.removeAttribute(ACTION_SEARCH);
                            }
                        }
                    }
                    if (action.equals(Render.ACTION_VIEW)) {
                        try {
                            param = new Custom().view(param, dao);
                            updateSessionApplication(param, request, application);
                            if (executeScriptButton(p, param)) {
                                request.setAttribute("success", "Rotina executada com sucesso!\n");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            request.setAttribute("error", e.getMessage());
                        }
                    }
                    request.setAttribute("action", action);
                    //preenche os combobox com dados do banco
                    for (Field f : p.getFields()) {
                        if (f.getComponent().equals("select") && !f.getSqlOptions().isEmpty()) {
                            try {
                                String script = f.getSqlOptions();
                                script = FormataTexto.replaceScriptWithValidation(script, param);
                                ResultSQL result = dao.select(f.getDatasource(), script);
                                f.getOptions().clear();
                                if (result.column.size() == 2) {
                                    for (Map<String, Object> opt : result.dados) {
                                        f.getOptions().add(opt.get(result.column.get(0).name) + "=" + opt.get(result.column.get(1).name) + "\r\n");
                                    }
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                request.setAttribute("error", ex.getMessage());
                            }
                        }
                    }
                    //monta a grid
                    if ((action.equals(Render.ACTION_LIST) || action.equals(Render.ACTION_SEARCH) || action.equals(Render.ACTION_ALL))) {
                        try {
                            String script = FormataTexto.replaceScriptWithValidation(p.getSqlPesquisa(), param);
                            String perfil = session.getAttribute("perfil").toString();
                            String tabela = montarGrid(param, perfil, p.getId(), p, p.getSqlPesquisaDS(), script, dao, true, p.isTbPaging(), p.isTbOrdering(), p.isTbInfo(), p.isTbCheck(), p.isTbSearching());
                            request.setAttribute("result", tabela);
                        } catch (Exception e) {
                            e.printStackTrace();
                            request.setAttribute("error", e.getMessage());
                        }
                    }
                    //preenche campos
                    if (!action.isEmpty() && (action.equals(Render.ACTION_ALTER) || action.equals(Render.ACTION_VIEW))) {
                        String script = p.getSqlUpdateView();
//                        String script = "SELECT descricao as page_usuario_nome, id as page_usuario_field4 from estado_civil where id = #{id}";
                        if (script != null && !script.trim().isEmpty() && !script.equals("null")) {
                            try {
                                if (script.trim().toUpperCase().startsWith("SELECT")) {
                                    script = FormataTexto.replaceScript(script, param);
                                    ResultSQL r = dao.select(p.getSqlUpdateViewDS(), script);
                                    if (!r.dados.isEmpty()) {
                                        for (Map<String, Object> item : r.dados) {
                                            for (Column key : r.column) {
                                                request.setAttribute(key.name, item.get(key.name));
                                            }
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                request.setAttribute("error", e.getMessage());
                            }
                        }
                    }
                    //gera graficos
                    for (Field f : p.getFields()) {
                        if (f.getComponent().equals("chart")) {
                            param.put(f.getId(), ChartBuilder.bar(f, dao, param));
                        }
                        if (f.getComponent().equals("table")) {
                            try {
                                String script = FormataTexto.replaceScriptWithValidation(f.getSqlOptions(), param);
                                String perfil = session.getAttribute("perfil").toString();
                                param.put(f.getId(), montarGrid(param, perfil, f.getId(), null, f.getDatasource(), script, dao, false, f.isTbPaging(), f.isTbOrdering(), f.isTbInfo(), f.isTbCheck(), f.isTbSearching()));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                request.setAttribute("error", ex.getMessage());
                            }
                        }
                    }
                    break;
                }
            }
            if (param != null) {
                for (String key : param.keySet()) {
                    request.setAttribute(key, param.get(key));
                }
            }
            downloadFile(response, param);
        }
        if (page != null && !page.equals("page_log")) {
            Log.time("Processamento servlet Render", begin);
        }
        rd.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void downloadFile(HttpServletResponse response, Map<String, String> param) throws IOException {
        if (param.containsKey("download")) {
            Log.request("downloading...");

            File file = new File(param.get("download"));

            if (param.containsKey("content_length")) {
                response.setContentLength(Integer.parseInt(param.get("content_length")));
            }
            if (param.containsKey("content_type")) {
                String encode = "";
                if (param.containsKey("encode")) {
                    encode = "; charset=" + param.get("encode");
                }
                response.setContentType(param.get("content_type") + encode);
            }
            if (param.containsKey("header_key") && param.containsKey("header_value")) {
                response.setHeader(param.get("header_key"), param.get("header_value"));
            }
            if (param.containsKey("encode")) {
                response.setCharacterEncoding(param.get("encode"));
            }
            PrintWriter out = response.getWriter();
            FileInputStream fis = new FileInputStream(file);
            int ln;
            while ((ln = fis.read()) != -1) {
                out.write((char) ln);
            }

            out.flush();
            Log.request("Downloaded file:" + param.get("download"));
        }
    }

    private ResultScriptBackend validScriptBackEnd(String functionName, String javascript, Map<String, String> param) {
        ResultScriptBackend result = new ResultScriptBackend();
        try {
            String script = "var msg = ''; function message() {return msg;} ";
            script += javascript;
            ScriptEngineManager factory = new ScriptEngineManager();
            ScriptEngine jsEngine = factory.getEngineByName("javascript");
            jsEngine.put("out", System.out);
            for (String key : param.keySet()) {
                jsEngine.put(key, param.get(key));
            }
            jsEngine.eval(script);
            Invocable invocableEngine = (Invocable) jsEngine;
            result.valid = (Boolean) invocableEngine.invokeFunction(functionName);
            result.msg = (String) invocableEngine.invokeFunction("message");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            result.msg = e.getMessage();
        } catch (ScriptException ex) {
            ex.printStackTrace();
            result.msg = ex.getMessage();
        }
        return result;
    }

    private boolean exportXLSX(HttpServletResponse response, HttpServletRequest request, Map<String, String> param) {
        boolean retorno = false;
        if (param.containsKey("btnExportXLSX") && param.get("btnExportXLSX").equals("true")) {
            retorno = true;
            param.put("btnExportXLSX", "false");
            try {
                String page = param.get("page");
                Page p = Render.pages.get(Render.pages.indexOf(new Page(page)));

                SXSSFWorkbook wb = new SXSSFWorkbook();

                SXSSFSheet sh = wb.createSheet(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
                String script = p.getSqlExport();
                if (script.trim().toUpperCase().startsWith("SELECT")) {
                    script = FormataTexto.replaceScriptWithValidation(script, param);
                    ResultSQL resultSQL = dao.select(p.getSqlExportDS(), script);

                    Row header = sh.createRow(0);
                    int i = 0;
                    for (Column key : resultSQL.column) {
                        Cell cell = header.createCell(i++);
                        cell.setCellValue(key.name);
                    }

                    int r = 1;
                    for (Map<String, Object> item : resultSQL.dados) {
                        Row row = sh.createRow(r++);
                        int c = 0;
                        for (Column key : resultSQL.column) {
                            Cell cell = row.createCell(c++);
                            if (item.get(key.name) != null) {
                                if (key.type == java.sql.Types.DECIMAL || key.type == java.sql.Types.NUMERIC
                                        || key.type == java.sql.Types.FLOAT || key.type == java.sql.Types.DOUBLE) {
                                    cell.setCellValue(Double.parseDouble(item.get(key.name).toString()));
                                } else if (key.type == java.sql.Types.BOOLEAN) {
                                    cell.setCellValue(Boolean.parseBoolean(item.get(key.name).toString()));
                                } else {
                                    cell.setCellValue(String.valueOf(item.get(key.name)));
                                }
                            }
                        }
                    }
                }

                // write it as an excel attachment
                ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
                wb.write(outByteStream);
                byte[] outArray = outByteStream.toByteArray();
                response.setContentType("application/ms-excel");
                response.setContentLength(outArray.length);
                response.setHeader("Expires:", "0"); // eliminates browser caching
                response.setHeader("Content-Disposition", "attachment; filename=" + p.getName() + ".xlsx");
                OutputStream outStream = response.getOutputStream();
                outStream.write(outArray);
                outStream.flush();
                wb.dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                request.setAttribute("error", ex.getMessage());
            }
        }
        return retorno;
    }

    public static String montarGrid(Map<String, String> param, String perfil, String id, Page p, String ds, String script, Dao dao, boolean withAction, boolean tbPaging, boolean tbOrdering, boolean tbInfo, boolean tbCheck, boolean tbSearching) throws Exception {
        if (script != null && !script.trim().isEmpty() && !script.equals("null")) {
            if (script.trim().toUpperCase().startsWith("SELECT")) {
                Profile profile = null;
                int index = Render.profiles.indexOf(new Profile(perfil));
                if (index >= 0) {
                    profile = Render.profiles.get(index);
                }

                ResultSQL r = dao.select(ds, script);
                String result = "<br>";

                result += "<div class=\"form-row\">";
                result += "<div>";
                result += "<div id=\"DataTables_Table_0_wrapper\" class=\"dataTables_wrapper\" role=\"grid\">";
                result += "<table class=\"table table-striped table-bordered bootstrap-datatable datatable responsive dataTable\" width=\"100%\" id=\"_tb" + id + "\" aria-describedby=\"DataTables_Table_0_info\">";

                result += "<thead>";
                result += "<tr role=\"row\">";
                if (tbCheck) {
                    result += "<th><input id='ckeckbox_all' type='checkbox' onclick='checkTableAll(this);'\\></th>";
                }
                boolean isID = r.column.size() > 1;
                for (Column key : r.column) {
                    if (!isID) {
                        result += "<th class=\"sorting_asc\" role=\"columnheader\" tabindex=\"0\" aria-controls=\"DataTables_Table_0\" rowspan=\"1\" colspan=\"1\" aria-sort=\"ascending\" >" + key.name.toUpperCase() + "</th>";
                    }
                    isID = false;
                }
                if (p != null && (p.isHasActive() || p.isHasView() || p.isHasUpdate() || p.isHasDelete())) {
                    result += "<th role=\"columnheader\" tabindex=\"0\" aria-controls=\"DataTables_Table_0\" rowspan=\"1\" colspan=\"1\" aria-sort=\"ascending\" style=\"width: 18%;\">AÇÃO</th>";
                }
                result += "</tr>";
                result += "</thead>";
                if (!r.dados.isEmpty()) {
                    result += "<tbody role=\"alert\" aria-live=\"polite\" aria-relevant=\"all\">";
                    for (Map<String, Object> item : r.dados) {
                        result += "<tr class=\"odd\">";
                        isID = true;
                        for (Column key : r.column) {
                            if (!isID) {
                                result += "<td>" + item.get(key.name) + "</td>";
                            } else if (tbCheck) {
                                result += "<td onclick=\"checkTable('#row_" + item.get(key.name) + "');\"><input type='checkbox' onclick=\"checkTable('#row_" + item.get(key.name) + "');\" class='row_chk' id=\"row_" + item.get(key.name) + "\" name=\"row_" + item.get(key.name) + "\" " + (param.get("row_" + item.get(key.name)) != null ? "checked" : "") + " /></td>";
                            }
                            isID = false;
                        }
                        if (r.column.size() == 1) {
                            for (Column key : r.column) {
                                if (!isID) {
                                    result += "<td>" + item.get(key.name) + "</td>";
                                } else if (tbCheck) {
                                    result += "<td onclick=\"checkTable('#row_" + item.get(key.name) + "');\"><input type='checkbox' onclick=\"checkTable('#row_" + item.get(key.name) + "');\" class='row_chk' id=\"row_" + item.get(key.name) + "\" name=\"row_" + item.get(key.name) + "\" " + (param.get("row_" + item.get(key.name)) != null ? "checked" : "") + " /></td>";
                                }
                            }
                        }
                        if (p != null && (p.isHasActive() || p.isHasView() || p.isHasUpdate() || p.isHasDelete())) {
                            result += "<td class=\"center \">";
                            if (p.isHasView() && (profile.getActions().get(p.getId()) != null && profile.getActions().get(p.getId()).isView() || perfil.equals(Render.ADMINISTRADOR))) {
                                result += "<a id=\"tb_btn_view_" + item.get(r.column.get(0).name) + "\" class=\"btn btn-success tb_row_view\" href=\"render?page=" + p.getId() + "&action=" + Render.ACTION_VIEW + "&id=" + item.get(r.column.get(0).name) + "\" data-toggle=\"tooltip\" title=\"Visualizar\" style=\"padding: 4px 8px; margin: 0 3px;\">";
                                result += "    <i class=\"glyphicon glyphicon-zoom-in icon-white\"></i>";
                                result += "</a>";
                            }
                            if (p.isHasUpdate() && (profile.getActions().get(p.getId()) != null && profile.getActions().get(p.getId()).isUpdate() || perfil.equals(Render.ADMINISTRADOR))) {
                                result += "<a id=\"tb_btn_alter_" + item.get(r.column.get(0).name) + "\" class=\"btn btn-info tb_row_alter\" href=\"render?page=" + p.getId() + "&action=" + Render.ACTION_ALTER + "&id=" + item.get(r.column.get(0).name) + "\" data-toggle=\"tooltip\" title=\"Editar\" style=\"padding: 4px 8px; margin: 0 3px;\">";
                                result += "    <i class=\"glyphicon glyphicon-edit icon-white\"></i>";
                                result += "</a>";
                            }
                            if (p.isHasDelete() && (profile.getActions().get(p.getId()) != null && profile.getActions().get(p.getId()).isDelete() || perfil.equals(Render.ADMINISTRADOR))) {
                                result += "<a id=\"tb_btn_del_" + item.get(r.column.get(0).name) + "\" class=\"btn btn-danger tb_row_del\" href=\"render?page=" + p.getId() + "&action=" + Render.ACTION_DELETE + "&id=" + item.get(r.column.get(0).name) + "\" onclick=\"if(!confirm('Deseja confirmar a exclusão?')) {return false;}\" data-toggle=\"tooltip\" title=\"Excluir\" style=\"padding: 4px 8px; margin: 0 3px;\">";
                                result += "    <i class=\"glyphicon glyphicon-trash icon-white\"></i>";
                                result += "</a>";
                            }
                            if (p.isHasActive() && (profile.getActions().get(p.getId()) != null && profile.getActions().get(p.getId()).isActive() || perfil.equals(Render.ADMINISTRADOR))) {
                                result += "<a id=\"tb_btn_active_" + item.get(r.column.get(0).name) + "\" class=\"btn btn-primary tb_row_active\" href=\"render?page=" + p.getId() + "&action=" + Render.ACTION_ACTIVATE + "&id=" + item.get(r.column.get(0).name) + "\" onclick=\"if(!confirm('Deseja confirmar a ativação/inativação?')) {return false;}\" data-toggle=\"tooltip\" title=\"Ativar/Inativar\" style=\"padding: 4px 8px; margin: 0 3px;\">";
                                result += "    <i class=\"glyphicon glyphicon-eye-close icon-white\"></i>";
                                result += "</a>";
                            }
                            result += "</td>";
                        }
                        result += "</tr>";
                    }
                    result += "</tbody>";
                }
                result += "</table>";
                result += "</div>";
                result += "</div>";
                result += "</div>";
                result += "<div class=\"clearfix\">"
                        + "<script type=\"text/javascript\">"
                        + "$(document).ready(function() {\n"
                        + "    $('#_tb" + id + "').DataTable( {\n"
                        + "        \"paging\":   " + tbPaging + ",\n"
                        + "        \"ordering\": " + tbOrdering + ",\n"
                        + "        \"info\":     " + tbInfo + ",\n"
                        + "        \"searching\":     " + tbSearching + ",\n"
                        + "        \"scrollX\": true,\n"
                        + "        \"sDom\": \"<'row'<'col-md-6'l><'col-md-6'f>r>t<'row'<'col-md-12'i><'col-md-12 center-block'p>>\",\n"
                        + "        \"sPaginationType\": \"bootstrap\",\n"
                        + "        \"oLanguage\": {\n"
                        + "            \"sLengthMenu\": \"_MENU_ registros por pagina\",\n"
                        + "            \"sZeroRecords\": \"Nenhum registro encontrado\",\n"
                        + "            \"sInfo\": \"De _START_ a _END_ do total de _TOTAL_ registros\",\n"
                        + "            \"sInfoEmpty\": \"De 0 a 0 do total de 0 registro\",\n"
                        + "            \"sInfoFiltered\": \"(filtrado de _MAX_ do total registros)\",\n"
                        + "            \"sSearch\": \"Filtrar:\"\n"
                        + "        }"
                        + "    } );\n"
                        + "} );"
                        + "</script>"
                        + "</div>";
                return result;
            }
        }

        return "";
    }

    private boolean executeScriptButton(Page p, Map<String, String> param) throws Exception {
        for (Field f : p.getFields()) {
            if (f.getComponent().equals("script")) {
                if (param.containsKey(f.getId())) {
                    try {
                        String script = f.getSqlOptions();
                        if (script != null && !script.trim().isEmpty() && !script.equals("null")) {
                            script = FormataTexto.replaceScript(script, param);
                            dao.insertDeletUpdate(f.getDatasource(), script);
                        }
                        return true;
                    } catch (Exception ex) {
                        throw ex;
                    }
                }
            }
        }
        return false;
    }

    private void updateSessionApplication(Map<String, String> param, HttpServletRequest request, ServletContext application) {

        Enumeration varSession = request.getSession(true).getAttributeNames();
        while (varSession.hasMoreElements()) {
            String key = (String) varSession.nextElement();
            if (!key.contains("javamelody") && param.containsKey(key)) {
                if (param.get(key).equalsIgnoreCase("true") || param.get(key).equalsIgnoreCase("false")) {
                    request.getSession(true).setAttribute(key, Boolean.parseBoolean(param.get(key)));
                } else {
                    request.getSession(true).setAttribute(key, param.get(key));
                }
            }
        }
        //Application
        Enumeration<String> attrsApp = application.getAttributeNames();
        while (attrsApp.hasMoreElements()) {
            String key = (String) attrsApp.nextElement();
            if (!key.contains(".") && param.containsKey(key)) {
                if (param.get(key).equalsIgnoreCase("true") || param.get(key).equalsIgnoreCase("false")) {
                    application.setAttribute(key, Boolean.parseBoolean(param.get(key)));
                } else {
                    application.setAttribute(key, param.get(key));
                }
            }
        }
    }
}
