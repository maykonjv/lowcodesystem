package br.com.lowcodesystem.ctrl;

import br.com.lowcodesystem.dao.Column;
import br.com.lowcodesystem.dao.Dao;
import br.com.lowcodesystem.dao.DataSource;
import br.com.lowcodesystem.dao.ResultSQL;
import br.com.lowcodesystem.model.ApiUser;
import br.com.lowcodesystem.model.Database;
import br.com.lowcodesystem.model.Field;
import br.com.lowcodesystem.model.Menu;
import br.com.lowcodesystem.model.Page;
import br.com.lowcodesystem.model.Profile;
import br.com.lowcodesystem.model.ProfileAction;
import br.com.lowcodesystem.util.FormataTexto;
import br.com.lowcodesystem.util.Log;
import br.com.lowcodesystem.util.ManterXML;
import br.com.lowcodesystem.util.ProjectLoad;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Maykon Servlet responsavel por construir os formularios
 */
public class Builder extends HttpServlet {

    public static final String SESSION_JS_GLOBAL = "js-global";
    public static final String SESSION_CSS_GLOBAL = "css-global";

    public static final String SESSION_MENU = "menu";
    public static final String SESSION_MENU_OPEN = "menu-open";
    public static final String SESSION_MENU_ADD = "menu-add";
    public static final String SESSION_MENU_REMOVE = "menu-remove";
    public static final String SESSION_MENU_UP = "menu-up";

    public static final String SESSION_FORM = "form";
    public static final String SESSION_FORM_REMOVE = "form-remove";
    public static final String SESSION_FORM_SCRIPT = "form-script";
    public static final String SESSION_FORM_SCRIPT_BACK = "form-script-back";
    public static final String SESSION_FORM_SQL_UPDATEVIEW = "form-sql-updateview";
    public static final String SESSION_FORM_SQL_PESQUISA = "form-sql-pesquisa";
    public static final String SESSION_FORM_SQL_EXPORT = "form-sql-export";
    public static final String SESSION_FORM_SQL_API = "form-sql-api";
    public static final String SESSION_FORM_SAVE = "form-sql-save";
    public static final String SESSION_FORM_EDIT = "form-sql-edit";
    public static final String SESSION_FORM_ACTIVATE = "form-sql-activate";
    public static final String SESSION_FORM_DELETE = "form-sql-delete";

    public static final String SESSION_FIELD = "field";
    public static final String SESSION_FIELD_OPEN = "field-open";
    public static final String SESSION_FIELD_ADD = "field-add";
    public static final String SESSION_FIELD_REMOVE = "field-remove";
    public static final String SESSION_FIELD_UP = "field-up";

    public static final String SESSION_LOGIN = "login";
    public static final String SESSION_PROJECT = "project";
    public static final String SESSION_DATABASE = "database";
    public static final String SESSION_ADMIN_A = "adminA";
    public static final String SESSION_ADMIN_B = "adminB";
    private static final String SESSION_ADD_DS = "addDS";
    public static final String SESSION_SCRIPT = "script";

    public static final String SESSION_PROFILE = "profile";
    public static final String SESSION_API_USER = "user-api";
    private final Dao dao = new Dao();

    @Override
    public void init() throws ServletException {
        super.init();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String warName = Custom.configName;
        for (String key : request.getParameterMap().keySet()) {
            System.out.println("Builder-" + key + ":" + request.getParameter(key));
        }
        try {
            String session = request.getParameter("session");
            String page = request.getParameter("page");
            if (session != null) {

                if (session.equals(SESSION_MENU_ADD)) {
                    Render.menus.add(new Menu("menu_" + Render.menus.size(), "MENU " + Render.menus.size()));
                    ManterXML.writeXML(SESSION_MENU, Render.menus);

                }
                if (session.equals(SESSION_MENU_UP)) {
                    String idMenu = request.getParameter("idMenu").replace("#", "");
                    int index = Render.menus.indexOf(new Menu(idMenu, ""));
                    Menu menu = Render.menus.get(index);
                    if (menu.getParentId() != null && !menu.getParentId().isEmpty()) {
                        index = Render.menus.indexOf(new Menu(menu.getParentId(), ""));
                        List<Menu> menus = Render.menus.get(index).getChildrenId();
                        index = menus.indexOf(new Menu(idMenu, ""));
                        if (index > 0) {
                            Menu menuAnterior = menus.get(index - 1);
                            menus.set(index - 1, menu);
                            menus.set(index, menuAnterior);
                            ManterXML.writeXML(SESSION_MENU, Render.menus);
                        }
                    } else {
                        if (index > 0) {
                            Menu menuAnterior = Render.menus.get(index - 1);
                            Render.menus.set(index - 1, menu);
                            Render.menus.set(index, menuAnterior);
                            ManterXML.writeXML(SESSION_MENU, Render.menus);
                        }
                    }
                }
                if (session.equals(SESSION_MENU_REMOVE)) {
                    String idMenu = request.getParameter("idMenu").replace("#", "");
                    List<Menu> menus = Render.menus;
                    for (Menu m : menus) {
                        if (m.getId().equals(idMenu)) {
                            if (m.getChildrenId() != null && !m.getChildrenId().isEmpty()) {
                                for (Menu m2 : menus) {
                                    if (m2.getId().equals(m.getChildrenId())) {
                                        Render.menus.remove(m2);
                                    }
                                }
                            }
                            Render.menus.remove(m);
                            break;
                        }
                        if (m.getChildrenId() != null && !m.getChildrenId().isEmpty()) {
                            m.getChildrenId().remove(new Menu(idMenu, ""));
                        }
                    }
                    ManterXML.writeXML(SESSION_MENU, Render.menus);
                }
                if (session.equals(SESSION_MENU_OPEN)) {
                    String idMenu = request.getParameter("idMenu").replace("#", "");

                    Menu menu = null;
                    for (Menu m : Render.menus) {
                        if (m.getId().equals(idMenu)) {
                            menu = m;
                            break;
                        }
                    }
                    PrintWriter out = response.getWriter();
                    response.setContentType("text/html");
                    response.setHeader("Cache-control", "no-cache, no-store");
                    response.setHeader("Pragma", "no-cache");
                    response.setHeader("Expires", "-1");

                    response.setHeader("Access-Control-Allow-Origin", "*");
                    response.setHeader("Access-Control-Allow-Methods", "POST");
                    response.setHeader("Access-Control-Allow-Headers", "Content-Type");
                    response.setHeader("Access-Control-Max-Age", "86400");

                    Gson gson = new Gson();
                    JsonObject myObj = new JsonObject();

                    JsonElement menuElement = gson.toJsonTree(menu);

                    myObj.add("menu", menuElement);
                    out.println(myObj.toString());

                    out.close();
                    return;
                }
                if (session.equals(SESSION_MENU)) {
                    String idMenuOld = request.getParameter("idMenuOld").replace("#", "");
                    for (Menu menu : Render.menus) {
                        if (menu.getId().equals(idMenuOld)) {
                            menu.setIcon(request.getParameter("icon"));
                            menu.setId(request.getParameter("idMenu"));
                            menu.setLabel(request.getParameter("label"));
                            menu.setPage(request.getParameter("pageReferencia"));
                            menu.setParam(request.getParameter("pageParam"));
                            String parentId = request.getParameter("idparent");
                            if (menu.getParentId() != null && !menu.getParentId().equals(parentId)) {
                                for (Menu m : Render.menus) {
                                    if (m.getId().equals(menu.getParentId()) && m.getChildrenId().contains(menu)) {
                                        m.getChildrenId().remove(menu);
                                        break;
                                    }
                                }
                            }
                            menu.setParentId(parentId);
                            if (parentId != null && !parentId.isEmpty()) {
                                for (Menu m : Render.menus) {
                                    if (m.getId().equals(parentId) && !m.getChildrenId().contains(menu)) {
                                        m.getChildrenId().add(menu);
                                        break;
                                    }
                                }
                            }
                            if (menu.getPage() != null && !menu.getPage().isEmpty()) {
                                boolean novo = true;
                                for (Page p : Render.pages) {
                                    if (p.getId().equals(menu.getPage())) {
                                        novo = false;
                                        break;
                                    }
                                }
                                if (novo) {
                                    Render.pages.add(new Page(menu.getPage(), menu.getLabel()));
                                    ManterXML.writeXML(SESSION_FORM, Render.pages);
                                }
                            }
                            break;
                        }
                    }

                    ManterXML.writeXML(SESSION_MENU, Render.menus);
                }
                if (session.equals(SESSION_FORM_REMOVE)) {
                    List<Page> pages = Render.pages;
                    for (Page p : pages) {
                        if (p.getId().equals(page)) {
                            Render.pages.remove(p);
                            List<Field> fields = p.getFields();
                            for (Field f : fields) {
                                if (f.getScope().equals(Field.SCOPE_APPLICATION)) {
                                    ProjectLoad.properties.remove(f.getId());
                                }
                            }
                            break;
                        }
                    }
                    ProjectLoad.writeProperties();
                    ProjectLoad.loadProperties(getServletConfig().getServletContext());
                    ManterXML.writeXML(SESSION_FORM, Render.pages);
                }
                if (session.equals(SESSION_FORM)) {
                    for (Page pg : Render.pages) {
                        if (pg.getId().equals(page)) {
                            pg.setName(request.getParameter("nameForm"));
                            pg.setId(request.getParameter("idForm"));
                            pg.setHasActive(request.getParameter("hasActive") != null);
                            pg.setHasDelete(request.getParameter("hasDelete") != null);
                            pg.setHasList(request.getParameter("hasList") != null);
                            pg.setHasNew(request.getParameter("hasNew") != null);
                            pg.setHasSearch(request.getParameter("hasSearch") != null);
                            pg.setHasUpdate(request.getParameter("hasUpdate") != null);
                            pg.setHasView(request.getParameter("hasView") != null);
                            ManterXML.writeXML(SESSION_FORM, Render.pages);
                            break;
                        }
                    }
                }
                if (session.equals(SESSION_FORM_SCRIPT)) {
                    for (Page pg : Render.pages) {
                        if (pg.getId().equals(page)) {
                            pg.setJavascript(request.getParameter("javascript"));
                            ManterXML.writeXML(SESSION_FORM, Render.pages);
                            break;
                        }
                    }
                }
                if (session.equals(SESSION_FORM_SCRIPT_BACK)) {
                    for (Page pg : Render.pages) {
                        if (pg.getId().equals(page)) {
                            pg.setJavascriptBack(request.getParameter("javascriptBack"));
                            ManterXML.writeXML(SESSION_FORM, Render.pages);
                            break;
                        }
                    }
                }
                if (session.equals(SESSION_FORM_SQL_UPDATEVIEW)) {
                    for (Page pg : Render.pages) {
                        if (pg.getId().equals(page)) {
                            pg.setSqlUpdateView(request.getParameter("sqlUpdateView"));
                            pg.setSqlUpdateViewDS(request.getParameter("sqlUpdateViewDS"));
                            ManterXML.writeXML(SESSION_FORM, Render.pages);
                            break;
                        }
                    }
                }
                if (session.equals(SESSION_FORM_SAVE)) {
                    for (Page pg : Render.pages) {
                        if (pg.getId().equals(page)) {
                            pg.setSqlSave(request.getParameter("sqlSave"));
                            pg.setSqlSaveDS(request.getParameter("sqlSaveDS"));
                            pg.setClearData(request.getParameter("clearData") != null);
                            ManterXML.writeXML(SESSION_FORM, Render.pages);
                            break;
                        }
                    }
                }
                if (session.equals(SESSION_FORM_EDIT)) {
                    for (Page pg : Render.pages) {
                        if (pg.getId().equals(page)) {
                            pg.setSqlEdit(request.getParameter("sqlEdit"));
                            pg.setSqlEditDS(request.getParameter("sqlEditDS"));
                            ManterXML.writeXML(SESSION_FORM, Render.pages);
                            break;
                        }
                    }
                }
                if (session.equals(SESSION_FORM_ACTIVATE)) {
                    for (Page pg : Render.pages) {
                        if (pg.getId().equals(page)) {
                            pg.setSqlActivite(request.getParameter("sqlActivate"));
                            pg.setSqlActiviteDS(request.getParameter("sqlActivateDS"));
                            ManterXML.writeXML(SESSION_FORM, Render.pages);
                            break;
                        }
                    }
                }
                if (session.equals(SESSION_FORM_DELETE)) {
                    for (Page pg : Render.pages) {
                        if (pg.getId().equals(page)) {
                            pg.setSqlDelete(request.getParameter("sqlDelete"));
                            pg.setSqlDeleteDS(request.getParameter("sqlDeleteDS"));
                            ManterXML.writeXML(SESSION_FORM, Render.pages);
                            break;
                        }
                    }
                }
                if (session.equals(SESSION_FORM_SQL_PESQUISA)) {
                    for (Page pg : Render.pages) {
                        if (pg.getId().equals(page)) {
                            pg.setSqlPesquisa(request.getParameter("sqlSearch"));
                            pg.setSqlPesquisaDS(request.getParameter("sqlSearchDS"));
                            pg.setTbCheck(request.getParameter("_hasCheck") != null);
                            pg.setTbPaging(request.getParameter("_hasPaging") != null);
                            pg.setTbOrdering(request.getParameter("_hasOrdering") != null);
                            pg.setTbInfo(request.getParameter("_hasInfo") != null);
                            pg.setTbSearching(request.getParameter("_hasSearching") != null);
                            ManterXML.writeXML(SESSION_FORM, Render.pages);
                            break;
                        }
                    }
                }
                if (session.equals(SESSION_FORM_SQL_EXPORT)) {
                    for (Page pg : Render.pages) {
                        if (pg.getId().equals(page)) {
                            pg.setSqlExport(request.getParameter("sqlExport"));
                            pg.setSqlExportDS(request.getParameter("sqlExportDS"));
                            ManterXML.writeXML(SESSION_FORM, Render.pages);
                            break;
                        }
                    }
                }
                if (session.equals(SESSION_FORM_SQL_API)) {
                    for (Page pg : Render.pages) {
                        if (pg.getId().equals(page)) {
                            pg.setSqlAPI(request.getParameter("sqlAPI"));
                            pg.setSqlAPIDS(request.getParameter("sqlAPIDS"));
                            ManterXML.writeXML(SESSION_FORM, Render.pages);
                            break;
                        }
                    }
                }
                if (session.equals(SESSION_FIELD_ADD)) {
                    for (Page p : Render.pages) {
                        if (p.getId().equals(page)) {
                            p.getFields().add(new Field(p.getId() + "_field" + p.getFields().size()));
                            break;
                        }
                    }
                    ManterXML.writeXML(SESSION_FORM, Render.pages);
                }
                if (session.equals(SESSION_FIELD_UP)) {
                    String idField = request.getParameter("idField").replace("#", "");
                    int i = Render.pages.indexOf(new Page(page));

                    if (i > -1) {
                        int index = Render.pages.get(i).getFields().indexOf(new Field(idField));

                        if (index > 0) {
                            Field fieldAnterior = Render.pages.get(i).getFields().get(index - 1);
                            Render.pages.get(i).getFields().set(index - 1, Render.pages.get(i).getFields().get(index));
                            Render.pages.get(i).getFields().set(index, fieldAnterior);
                            ManterXML.writeXML(SESSION_FORM, Render.pages);
                        }

                    }
                    return;
                }
                if (session.equals(SESSION_FIELD_REMOVE)) {
                    String idField = request.getParameter("idField").replace("#", "");
                    for (Page p : Render.pages) {
                        if (p.getId().equals(page)) {
                            List<Field> fields = p.getFields();
                            for (Field f : fields) {
                                if (f.getId().equals(idField)) {
                                    p.getFields().remove(f);
                                    ProjectLoad.properties.remove(f.getId());
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    ProjectLoad.writeProperties();
                    ProjectLoad.loadProperties(getServletConfig().getServletContext());
                    ManterXML.writeXML(SESSION_FORM, Render.pages);
                }
                if (session.equals(SESSION_FIELD_OPEN)) {
                    String idField = request.getParameter("idField").replace("#", "");
                    Page pg = null;
                    for (Page p : Render.pages) {
                        if (p.getId().equals(page)) {
                            pg = p;
                            break;
                        }
                    }
                    Field field = null;
                    if (pg != null) {
                        for (Field f : pg.getFields()) {
                            if (f.getId().equals(idField)) {
                                field = f;
                                break;
                            }
                        }
                        PrintWriter out = response.getWriter();
                        response.setContentType("text/html");
                        response.setHeader("Cache-control", "no-cache, no-store");
                        response.setHeader("Pragma", "no-cache");
                        response.setHeader("Expires", "-1");

                        response.setHeader("Access-Control-Allow-Origin", "*");
                        response.setHeader("Access-Control-Allow-Methods", "POST");
                        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
                        response.setHeader("Access-Control-Max-Age", "86400");

                        Gson gson = new Gson();
                        JsonObject myObj = new JsonObject();

                        myObj.add("field", gson.toJsonTree(field));
                        myObj.add("page", gson.toJsonTree(pg));
                        out.println(myObj.toString());

                        out.close();
                        return;
                    }
                }
                if (session.equals(SESSION_FIELD)) {
                    Field field = new Field();
                    String idFieldOld = request.getParameter("idFieldOld").replace("#", "");
                    for (Page p : Render.pages) {
                        if (p.getId().equals(page)) {
                            List<Field> fields = p.getFields();
                            for (Field f : fields) {
                                if (f.getId().equals(idFieldOld)) {
                                    field = f;
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    field.setId(request.getParameter("idField"));
                    field.setScope(request.getParameter("scopeField"));
                    field.setLabel(request.getParameter("labelField"));
                    field.setSqlOptions(request.getParameter("sqlOptionsField"));
                    field.setDatasource(request.getParameter("datasourceField"));
                    field.setComponent(request.getParameter("componentField"));
                    field.setDefaultValue(request.getParameter("defaultValueField"));
                    if (request.getParameter("sizeField") != null && !request.getParameter("sizeField").isEmpty()) {
                        field.setSize(Integer.parseInt(request.getParameter("sizeField")));
                    } else {
                        field.setSize(0);
                    }
                    if (request.getParameter("widthField") != null && !request.getParameter("widthField").isEmpty()) {
                        field.setWidth(Integer.parseInt(request.getParameter("widthField")));
                    } else {
                        field.setWidth(3);
                    }
                    field.setRequired(request.getParameter("requiredField") != null);
                    field.setSubmitOnchange(request.getParameter("submitOnchange") != null);
                    field.getOptions().clear();
                    if (request.getParameter("optionsField") != null) {
                        for (String opt : request.getParameter("optionsField").split("\n")) {
                            if (!opt.replace(",", "").isEmpty()) {
                                field.getOptions().add(opt.replace(",", ""));
                            }
                        }
                    }
                    field.setTitle(request.getParameter("titleField"));
                    field.setFormat(request.getParameter("formatField"));
                    field.setMask(request.getParameter("maskField"));
                    field.setPlaceholder(request.getParameter("placeholderField"));
                    field.setStyle(request.getParameter("styleField"));
                    field.setClassStyle(request.getParameter("classStyleField"));
                    field.setType(request.getParameter("typeField"));
                    field.setHasNew(request.getParameter("hasNewField") != null);
                    field.setHasSearch(request.getParameter("hasSearchField") != null);
                    field.setHasUpdate(request.getParameter("hasUpdateField") != null);
                    field.setHasView(request.getParameter("hasViewField") != null);

                    field.setTbCheck(request.getParameter("hasCheck") != null);
                    field.setTbPaging(request.getParameter("hasPaging") != null);
                    field.setTbOrdering(request.getParameter("hasOrdering") != null);
                    field.setTbInfo(request.getParameter("hasInfo") != null);
                    field.setTbSearching(request.getParameter("hasSearching") != null);
                    ManterXML.writeXML(SESSION_FORM, Render.pages);
                }
                if (session.equals(SESSION_JS_GLOBAL)) {
                    Render.project.setJsGeral(request.getParameter("js_global"));
                    ManterXML.writeXML(SESSION_PROJECT, Render.project);
                }
                if (session.equals(SESSION_CSS_GLOBAL)) {
                    Render.project.setCssGeral(request.getParameter("css_global"));
                    ManterXML.writeXML(SESSION_PROJECT, Render.project);
                }
                if (session.equals(SESSION_LOGIN)) {
                    Render.project.setSqlLogin(request.getParameter("sqlLogin"));
                    Render.project.setOpcoesLogin(request.getParameter("opcoesLogin"));
                    Render.project.setDatasource(request.getParameter("sqlLoginDS"));
                    ManterXML.writeXML(SESSION_PROJECT, Render.project);
                }
                if (session.equals(SESSION_ADMIN_A)) {
                    // esta apagando o arquivo config.properties quando configura 
                    String pathSchema = request.getParameter("pathSchema");
                    String projectID = request.getParameter("projectID");
                    String projectName = request.getParameter("projectName");
                    String projectVersion = request.getParameter("projectVersion");
                    String projectYear = request.getParameter("projectYear");
                    if (!pathSchema.endsWith("/") && !pathSchema.endsWith("\\")) {
                        pathSchema += File.separator;
                    }
//                    pathSchema += projectID + File.separator;
                    ProjectLoad.properties.clear();
                    ManterXML.writePathXML(pathSchema, warName);
                    ProjectLoad.load(warName);
                    Render.project.setProjectName(projectName);
                    Render.project.setProjectID(projectID);
                    Render.project.setProjectVersion(projectVersion);
                    Render.project.setProjectYear(projectYear);
                    Render.project.setPathUploadFiles(pathSchema + "upload");
                    Render.project.setLoginExterno(request.getParameter("loginExterno") != null);
                    Render.project.setUrlLogin(request.getParameter("urlLogin"));
                    Render.project.setWsPort(request.getParameter("wsport"));
                    Render.project.setEncode(FormataTexto.toString(request.getParameter("encode"), "UTF-8"));
                    ProjectLoad.loadProperties(getServletConfig().getServletContext());
                    ProjectLoad.properties.setProperty("upload", Render.project.getPathUploadFiles());
                    ProjectLoad.properties.setProperty("ambienteTeste", String.valueOf(request.getParameter("ambienteTeste") != null));
                    ProjectLoad.properties.setProperty("encode", Render.project.getEncode());
                    ProjectLoad.writeProperties();
                    ProjectLoad.loadProperties(getServletConfig().getServletContext());
                    Render.project.setLogPath(pathSchema + "_log");
                    File fLog = new File(pathSchema + "_log");
                    if (!fLog.exists()) {
                        fLog.mkdirs();
                    }
                    File fUpload = new File(pathSchema + "upload");
                    if (!fUpload.exists()) {
                        fUpload.mkdirs();
                    }
                    Render.project.setLogEnable(request.getParameter("logEnable") != null);
                    Log.configAPI();
                    ManterXML.writeXML(SESSION_PROJECT, Render.project);
                    try {
                        DataSource.refresh();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    request.setAttribute("msg1", "Gravado com sucesso");
                    RequestDispatcher rd = request.getRequestDispatcher("render");
                    rd.forward(request, response);
                    return;
                }

                if (session.equals(SESSION_ADD_DS)) {
                    String ds = request.getParameter("input_datasource");
                    if (!DataSource.database.containsKey(ds)) {
                        DataSource.database.put(ds, new Database());
                        ManterXML.writeXML(SESSION_DATABASE, DataSource.database);
                    }
                    request.setAttribute("msg", "Gravado com sucesso");
                    response.setContentType("text/html;charset=UTF-8");
                    String retorno = "{\"msg\": \"Gravado com sucesso\"}";
                    response.getWriter().write(retorno);
//                    RequestDispatcher rd = request.getRequestDispatcher("admindb.jsp");
//                    rd.forward(request, response);
                    return;
                }
                if (session.equals(SESSION_ADMIN_B)) {
                    String ds = request.getParameter("datasource");
                    if (ds == null || ds.isEmpty() || ds.equals("null")) {
//                        response.sendRedirect("admindb.jsp");
                        return;
                    }
                    if (!DataSource.database.containsKey(ds)) {
                        DataSource.database.put(ds, new Database());
                    }
                    if (request.getParameter("btn_gravar") != null) {
                        DataSource.database.get(ds).setDriverClass(request.getParameter("driverClass"));
                        DataSource.database.get(ds).setDatabase(request.getParameter("database"));
                        if (!request.getParameter("idleTestPeriod").isEmpty()) {
                            DataSource.database.get(ds).setIdleTestPeriod(Integer.parseInt(request.getParameter("idleTestPeriod")));
                        } else {
                            DataSource.database.get(ds).setIdleTestPeriod(null);
                        }
                        DataSource.database.get(ds).setJdbcUrl(request.getParameter("jdbcUrl"));
                        if (!request.getParameter("maxPoolSize").isEmpty()) {
                            DataSource.database.get(ds).setMaxPoolSize(Integer.parseInt(request.getParameter("maxPoolSize")));
                        } else {
                            DataSource.database.get(ds).setMaxPoolSize(null);
                        }
                        if (!request.getParameter("maxStatements").isEmpty()) {
                            DataSource.database.get(ds).setMaxStatements(Integer.parseInt(request.getParameter("maxStatements")));
                        } else {
                            DataSource.database.get(ds).setMaxStatements(null);
                        }
                        if (!request.getParameter("minPoolSize").isEmpty()) {
                            DataSource.database.get(ds).setMinPoolSize(Integer.parseInt(request.getParameter("minPoolSize")));
                        } else {
                            DataSource.database.get(ds).setMinPoolSize(null);
                        }
                        DataSource.database.get(ds).setPass(request.getParameter("passDB"));
                        if (!request.getParameter("timeout").isEmpty()) {
                            DataSource.database.get(ds).setTimeout(Integer.parseInt(request.getParameter("timeout")));
                        } else {
                            DataSource.database.get(ds).setTimeout(null);
                        }
                        DataSource.database.get(ds).setUser(request.getParameter("userDB"));
                        ManterXML.writeXML(SESSION_DATABASE, DataSource.database);
                        Scheduler.setDao(new Dao());
                        try {
                            DataSource.refresh();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        request.setAttribute("msg", "Gravado com sucesso");
                    } else if (request.getParameter("excluir") != null) {
                        ds = request.getParameter("datasource");
                        System.out.println("excluindo ds: " + ds);
                        DataSource.database.remove(ds);
                        ManterXML.writeXML(SESSION_DATABASE, DataSource.database);
                        DataSource.refresh();
                    } else if (request.getParameter("btn_testar") != null) {
                        Object sql = request.getParameter("testSQL");
                        if (sql != null && !sql.toString().isEmpty()) {
                            try {
                                ResultSQL r = dao.select(ds, sql.toString());
                                response.getWriter().write(r.toString());
                            } catch (Exception e) {
                                response.getWriter().write(e.getMessage());
                            }
                        }
                        return;
                    }
                    Database db = DataSource.database.get(ds);
                    JsonElement json = new Gson().toJsonTree(db);
                    response.getWriter().write(json.toString());
                    return;
                }
                // script SQL executado em clientdb.jsp
                if (session.equals(SESSION_SCRIPT)) {
                    String ds = request.getParameter("scriptDS");
                    String script = request.getParameter("script");
                    String dbInfo = request.getParameter("btn_db_info");
                    ResultSQL r = null;
                    if (dbInfo != null && !dbInfo.isEmpty()) {
                        if (DataSource.database.get(ds).getDatabase().equals("Postgres")) {
                            r = dao.select(ds, " SELECT table_name, column_name, data_type, character_maximum_length FROM information_schema.columns where table_schema='public'");
                        }
                        if (DataSource.database.get(ds).getDatabase().equals("Sqlite")) {
                            r = dao.select(ds, "SELECT  "
                                    + "  m.name as table_name,  "
                                    + "p.* "
                                    + "FROM  "
                                    + "  sqlite_master AS m "
                                    + "JOIN  "
                                    + "  pragma_table_info(m.name) AS p "
                                    + "WHERE "
                                    + "  m.type = 'table'  "
                                    + "ORDER BY  "
                                    + "  m.name,  "
                                    + "  p.cid");
                        }
                    } else if (script != null && !script.trim().isEmpty() && !script.equals("null")) {
                        try {
                            if (script.trim().toUpperCase().startsWith("SELECT")) {
                                r = dao.select(ds, script);
                            } else {
                                int rows = dao.insertDeletUpdate(ds, script);
                                request.setAttribute("result", rows + " linhas afetadas.");
                                RequestDispatcher rd = request.getRequestDispatcher("clientdb.jsp");
                                rd.forward(request, response);
                                return;
                            }
                        } catch (Exception e) {
                            request.setAttribute("result", e.getMessage());
                        }
                    }
                    if (r != null) {
                        String result = r.dados.size() + " linhas retornadas";
                        result += "<div class=\"form-row p-2\">";
                        result += "<div>";
                        result += "<div id=\"DataTables_Table_0_wrapper\" class=\"dataTables_wrapper\" role=\"grid\">";

                        result += "<table class=\"display order-column cell-border\"style=\"width:100%\" id=\"_tbDataTables_Table_0\">";

                        result += "<thead>";
                        result += "<tr role=\"row\">";
                        for (Column key : r.column) {
                            result += "<th class=\"sorting_asc\" role=\"columnheader\" tabindex=\"0\" aria-controls=\"DataTables_Table_0\" rowspan=\"1\" colspan=\"1\" aria-sort=\"ascending\" >" + key.name.toUpperCase() + "</th>";
                        }
                        result += "</tr>";
                        result += "</thead>";

                        if (!r.dados.isEmpty()) {
                            result += "<tbody role=\"alert\" aria-live=\"polite\" aria-relevant=\"all\">";
                            for (Map<String, Object> item : r.dados) {
                                result += "<tr class=\"odd\">";
                                for (Column key : r.column) {
                                    result += "<td>" + item.get(key.name) + "</td>";
                                }
                                result += "</tr>";
                            }
                            result += "</tbody>";
                        }
                        result += "</table>";
                        result += "</div>";
                        result += "<div class=\"clearfix\">"
                                + "<script>"
                                + "    new DataTable('#_tbDataTables_Table_0', {\n"
                                + "        responsive: true,\n"
                                //                        + "        dom: 'Bfrtip',\n"
                                //                        + "        buttons: [\n"
                                //                        + "            {\n"
                                //                        + "                extend: 'collection',\n"
                                //                        + "                className: 'btn btn-success custom-html-collection ',\n"
                                //                        + "                text: \"Exportar\",\n"
                                //                        + "                buttons: [\n"
                                //                        + "                    '<h3>Exportar</h3>',\n"
                                //                        + "                    'copy',\n"
                                //                        + "                    'pdf',\n"
                                //                        + "                    'csv',\n"
                                //                        + "                    'excel',\n"
                                //                        + "                ]\n"
                                //                        + "            },\n"
                                //                        + "            {\n"
                                //                        + "                extend: 'collection',\n"
                                //                        + "                className: 'btn btn-success custom-html-collection ',\n"
                                //                        + "                text: \"Filtrar\",\n"
                                //                        + "                buttons: [\n"
                                //                        + "                    '<h3 class=\"not-top-heading\">Filtro de colunas</h3>',\n"
                                //                        + "                    'columnsToggle'\n"
                                //                        + "                ]\n"
                                //                        + "            }\n"
                                //                        + "        ],"
                                + "        \"paging\": true,\n"
                                + "        \"ordering\": true,\n"
                                + "        \"info\": true,\n"
                                + "        \"searching\": true,\n"
                                + "        \"scrollX\": true,\n"
                                + "        \"sDom\": \"<'row'<'col-md-6'l><'col-md-6'f>r>t<'row'<'col-md-12'i><'col-md-12 center-block'p>>\",\n"
                                + "        \"oLanguage\": {\n"
                                + "            \"sLengthMenu\": \"_MENU_ registros por página\",\n"
                                + "            \"sZeroRecords\": \"Nenhum registro encontrado\",\n"
                                + "            \"sInfo\": \"De _START_ a _END_ do total de _TOTAL_ registros\",\n"
                                + "            \"sInfoEmpty\": \"De 0 a 0 do total de 0 registro\",\n"
                                + "            \"sInfoFiltered\": \"(filtrado de _MAX_ do total registros)\",\n"
                                + "            \"sSearch\": \"Filtrar:\",\n"
                                + "             \"oPaginate\": {\n"
                                + "                 \"sFirst\":      \"Inicio\",\n"
                                + "                 \"sLast\":       \"Fim\",\n"
                                + "                 \"sNext\":       \"Próximo\",\n"
                                + "                 \"sPrevious\":   \"Anterior\"\n"
                                + "             },"
                                + "        },"
                                + "    } );\n"
                                + "</script>"
                                + "</div>";
                        request.setAttribute("result", result);
                    }
                    RequestDispatcher rd = request.getRequestDispatcher("clientdb.jsp");
                    rd.forward(request, response);
                    return;

                } else if (session.equals(SESSION_PROFILE)) {
                    request.setAttribute("perfil_nome", request.getParameter("perfil_nome"));
                    if (request.getParameter("perfil_evento") != null) {
                        if (request.getParameter("perfil_add_nome") != null && !request.getParameter("perfil_add_nome").isEmpty()) {
                            Render.profiles.add(new Profile(request.getParameter("perfil_add_nome")));
                        }
                    } else if (request.getParameter("gravar_perfil") != null) {
                        Profile profile = new Profile(request.getParameter("perfil_nome"));
                        for (Page p : Render.pages) {
                            ProfileAction action = new ProfileAction();
                            action.setNamePage(p.getId());
                            action.setAll(request.getParameter(p.getId()) != null);
                            if (request.getParameter(p.getId()) != null) {
                                action.setActive(true);
                                action.setAdd(true);
                                action.setCustom(true);
                                action.setDelete(true);
                                action.setList(true);
                                action.setSearch(true);
                                action.setUpdate(true);
                                action.setView(true);
                            } else {
                                action.setCustom(request.getParameter("perfil_custom_" + p.getId()) != null);
                                action.setActive(request.getParameter("perfil_ativar_" + p.getId()) != null);
                                action.setAdd(request.getParameter("perfil_cadastrar_" + p.getId()) != null);
                                action.setDelete(request.getParameter("perfil_excluir_" + p.getId()) != null);
                                action.setList(request.getParameter("perfil_listar_" + p.getId()) != null);
                                action.setView(request.getParameter("perfil_visualizar_" + p.getId()) != null);
                                action.setSearch(request.getParameter("perfil_pesquisar_" + p.getId()) != null);
                                action.setUpdate(request.getParameter("perfil_alterar_" + p.getId()) != null);
                            }
                            profile.getActions().put(p.getId(), action);
                        }
                        if (Render.profiles.contains(profile)) {
                            Render.profiles.set(Render.profiles.indexOf(profile), profile);
                        } else {
                            Render.profiles.add(profile);
                        }
                    } else if (request.getParameter("remover_perfil") != null && request.getParameter("perfil_nome") != null) {
                        if (!request.getParameter("perfil_nome").isEmpty() && !request.getParameter("perfil_nome").equals(Render.ADMINISTRADOR)) {
                            Profile profile = new Profile(request.getParameter("perfil_nome"));
                            Render.profiles.remove(profile);
                            request.removeAttribute("perfil_nome");
                        }
                    }
                    ManterXML.writeXML(SESSION_PROFILE, Render.profiles);
                } else if (session.equals(SESSION_API_USER)) {
                    if (request.getParameter("remover_usuario") == null && request.getParameter("excluirIp") == null) {
                        if (!request.getParameter("passAPI").isEmpty() && !request.getParameter("perfil_nome").isEmpty()) {
                            ApiUser apiUser = new ApiUser();
                            if (request.getParameter("userAlter").isEmpty()) {
                                apiUser.setName(request.getParameter("user"));
                            } else {
                                apiUser.setName(request.getParameter("userAlter"));
                            }
                            apiUser.setPerfil(request.getParameter("perfil_nome"));
                            apiUser.setSenha(request.getParameter("passAPI"));
                            if (Render.apiUsers.contains(apiUser)) {
                                int index = Render.apiUsers.indexOf(apiUser);
                                apiUser.setIps(Render.apiUsers.get(index).getIps());
                                if (!request.getParameter("ip").isEmpty()) {
                                    apiUser.getIps().add(request.getParameter("ip"));
                                }
                                Render.apiUsers.set(index, apiUser);
                            } else {
                                if (!request.getParameter("ip").isEmpty()) {
                                    apiUser.getIps().add(request.getParameter("ip"));
                                }
                                Render.apiUsers.add(apiUser);
                            }
                            ManterXML.writeXML(SESSION_API_USER, Render.apiUsers);
                        }
                    } else if (request.getParameter("remover_usuario") != null) {
                        if (!request.getParameter("usuarios").isEmpty()) {
                            Render.apiUsers.remove(new ApiUser(request.getParameter("usuarios")));
                            ManterXML.writeXML(SESSION_API_USER, Render.apiUsers);
                        }
                    } else if (request.getParameter("excluirIp") != null) {
                        if (request.getParameterValues("ips").length != 0 && !request.getParameter("usuarios").isEmpty()) {
                            int index = Render.apiUsers.indexOf(new ApiUser(request.getParameter("usuarios")));
                            ApiUser apiUser = Render.apiUsers.get(index);
                            apiUser.getIps().removeAll(Arrays.asList(request.getParameterValues("ips")));
                            Render.apiUsers.set(index, apiUser);
                            ManterXML.writeXML(SESSION_API_USER, Render.apiUsers);
                        }
                    }
                    RequestDispatcher rd = request.getRequestDispatcher("adminapi.jsp");
                    rd.forward(request, response);
                    return;
                }
                RequestDispatcher rd = request.getRequestDispatcher("render");
                rd.forward(request, response);
//                response.sendRedirect("render" + ((page != null && !page.isEmpty()) ? "?page=" + page : ""));
            } else {
                System.out.println("ERROR>>> não informou a SESSION");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
        }
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

}
