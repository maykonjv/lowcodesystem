package br.com.lowcodesystem.ctrl;

import static br.com.lowcodesystem.ctrl.Render.project;
import br.com.lowcodesystem.dao.Column;
import br.com.lowcodesystem.dao.Dao;
import br.com.lowcodesystem.dao.ResultSQL;
import br.com.lowcodesystem.model.ApiUser;
import br.com.lowcodesystem.model.Field;
import br.com.lowcodesystem.model.Page;
import br.com.lowcodesystem.model.Profile;
import br.com.lowcodesystem.model.ResultScriptBackend;
import br.com.lowcodesystem.util.FormataTexto;
import br.com.lowcodesystem.util.Log;
import br.com.lowcodesystem.util.Parse;
import br.com.lowcodesystem.util.ProjectLoad;
import br.com.lowcodesystem.util.Val;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.json.JSONObject;
import org.json.XML;

/**
 *
 * @author Maykon
 */
@WebServlet(name = "RestAPI", urlPatterns = {"/api/v1/*"})
public class RestAPI extends HttpServlet {

    Dao dao = new Dao();

    @Override
    public void init() throws ServletException {
        super.init();
        String warName = Custom.configName;
        ProjectLoad.load(warName);
        Log.config(project.getLogPath(), warName, (1024 * 1024 * 5));
    }

    @Override
    public String getServletInfo() {
        return "API REST - desenvolvida utilizando os metodos http (GET/POST/PUT/DELETE)";
    }

    /*
     * API REST - Metodo GET - utilizado para consulta
     * url: http://host:port/projetoName/api/v1/custom?page=page_id&[filtros...]&format=json
     * format: json/xml
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        if (path.equals("/doc")) {
            Profile profile = auth(request, response, request.getParameter("hash"));
            if (profile == null) {
                return;//nao autenticado
            }
            String hash = request.getParameter("hash");
            String url = request.getRequestURL().toString();
            Log.info(url);
            request.setAttribute("info", "");
            request.setAttribute("json", doc(profile, url, hash));
            RequestDispatcher rd = request.getRequestDispatcher("/doc.jsp");
            rd.forward(request, response);
            return;
        }
        if (path.equals("/doc2")) {
            Profile profile = auth(request, response, request.getParameter("hash"));
            if (profile == null) {
                return;//nao autenticado
            }
            String hash = request.getParameter("hash");
            String url = request.getRequestURL().toString();
            String page = request.getParameter("page");
            request.setAttribute("info", doc2(profile, url, hash, page));
            RequestDispatcher rd = request.getRequestDispatcher("/doc2.jsp");
            rd.forward(request, response);
            return;
        }
        executeRequest(request, response, "GET");
    }

    /*
     * API REST - Metodo PUT - utilizado para alteracao de dados
     * url: http://host:port/projetoName/api/v1/custom?page=page_id&[parametros...]&format=json
     * format: json/xml
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        executeRequest(request, response, "PUT");
    }

    /*
     * API REST - Metodo DELETE - utilizado para exclusao de dados
     * url: http://host:port/projetoName/api/v1/custom?page=page_id&[parametros...]&format=json
     * format: json/xml
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        executeRequest(req, resp, "DELETE");
    }

    /*
     * API REST - Metodo POST - utilizado para inclusao de dados
     * url: http://host:port/projetoName/api/v1/custom?page=page_id&[parametros...]&format=json
     * format: json/xml
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        executeRequest(request, response, "POST");
    }

    private void executeRequest(HttpServletRequest request, HttpServletResponse response, String method) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Log.debug("Executando servico API - " + method);
        long time = System.currentTimeMillis();
        String path = "";
        try {
            //ParameterMap
            Map<String, String> param = new HashMap<>();
            StringBuilder jb = new StringBuilder();
            String line;
            try {
                BufferedReader reader = request.getReader();
                while ((line = reader.readLine()) != null) {
                    jb.append(line);
                }
                param.put("content-request", jb.toString());
                if (!Val.isNullOrEmpy(jb.toString())) {
                    JSONObject jsonPost = new JSONObject(jb.toString());
                    for (String key : jsonPost.keySet()) {
                        param.put(key, Parse.toString(jsonPost.get(key), ""));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            String hash = null;
            if (!Val.isNullOrEmpy(param.get("hash"))) {
                hash = param.get("hash");
            } else {
                hash = request.getParameter("hash");
            }
            JsonObject json = new JsonObject();
            Profile profile = auth(request, response, hash);
            if (profile == null) {
                //nao autenticado
                return;
            }
            String format = "json";
            String encode = "windows-1252";
            if (request.getParameter("format") != null && !request.getParameter("format").isEmpty()) {
                format = request.getParameter("format").toLowerCase();
            }
            if (request.getParameter("encode") != null && !request.getParameter("encode").isEmpty()) {
                encode = request.getParameter("encode").toLowerCase();
            }
            response.setContentType("text/" + format + ";charset=" + encode);
            response.setHeader("Content-type", "application/" + format);
            response.setHeader("Cache-control", "no-cache, no-store");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "-1");

            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", method);
            response.setHeader("Access-Control-Allow-Headers", "Content-Type");
            response.setHeader("Access-Control-Max-Age", "86400");

            path = request.getPathInfo();
            if (path != null) {
                if (path.contains("js/") || path.contains("css/") || path.contains("bower_components/") || path.contains("https:/")) {
                    return;
                }
                param.put("path", path);
                for (String key : request.getParameterMap().keySet()) {
                    String value = new String (request.getParameter(key).getBytes("windows-1252"), "UTF-8");
                    request.setAttribute(key, value);
                    param.put(key, value);
                }
                //Attribute
                Enumeration<String> attrs = request.getAttributeNames();
                while (attrs.hasMoreElements()) {
                    String key = (String) attrs.nextElement();
                    param.put(key, request.getAttribute(key).toString());
                }
                //Session
                Enumeration varSession = request.getSession(true).getAttributeNames();
                while (varSession.hasMoreElements()) {
                    String key = (String) varSession.nextElement();
                    if (!key.contains("javamelody")) {
                        param.put("session_" + key, request.getSession(true).getAttribute(key).toString());
                    }
                }
                //Application
                Enumeration<String> attrsApp = getServletConfig().getServletContext().getAttributeNames();
                while (attrsApp.hasMoreElements()) {
                    String key = (String) attrsApp.nextElement();
                    if (!key.contains(".")) {
                        param.put(key, getServletConfig().getServletContext().getAttribute(key).toString());
                    }
                }
                Log.request("RequestAPI - " + param);
                if (path.equals("/custom")) {
                    String page = param.get("page");
                    int indice = Render.pages.indexOf(new Page(page));
                    if (indice == -1) {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "O parametro page (" + page + ") esta incorreto.");
                        return;
                    }
                    Page p = Render.pages.get(indice);
                    String msgError = "";
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
                            request.getSession(true).setAttribute(f.getId(), param.get(f.getId()));
                        } else if (f.getScope().equals(Field.SCOPE_APPLICATION) && param.get(f.getId()) != null) {
                            getServletConfig().getServletContext().setAttribute(f.getId(), param.get(f.getId()));
                            ProjectLoad.properties.setProperty(f.getId(), param.get(f.getId()));
                            alterPage = true;
                        }
                    }
                    if (alterPage && msgError.isEmpty()) {
                        ProjectLoad.writeProperties();
                    }
                    if (!msgError.isEmpty()) {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, msgError);
                        return;
                    }
                    if (method.equals("GET")) {
                        if (profile.getActions().get(p.getId()) != null && profile.getActions().get(p.getId()).isSearch() || profile.getActions().get(p.getId()).isList()) {
                            if (p.isHasSearch() || p.isHasList()) {
                                json.add(page, list(param, p, dao));
                            } else {
                                response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "Nao existe LISTAGEM DE DADOS para essa page.");
                            }
                        } else {
                            response.sendError(HttpServletResponse.SC_NON_AUTHORITATIVE_INFORMATION, "Acesso nao autorizado a esse servico.");
                        }
                    } else if (method.equals("POST")) {
                        if (profile.getActions().get(p.getId()) != null && profile.getActions().get(p.getId()).isAdd()) {
                            if (p.isHasNew()) {
                                json.add(page, save(param, p, dao));
                            } else {
                                response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "Nao existe INSERT DE DADOS para essa page.");
                            }
                        } else {
                            response.sendError(HttpServletResponse.SC_NON_AUTHORITATIVE_INFORMATION, "Acesso nao autorizado a esse servico.");
                        }
                    } else if (method.equals("PUT")) {
                        if (profile.getActions().get(p.getId()) != null && profile.getActions().get(p.getId()).isUpdate()) {
                            if (p.isHasUpdate()) {
                                json.add(page, update(param, p, dao));
                            } else {
                                response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "Nao existe UPDATE DE DADOS para essa page.");
                            }
                        } else {
                            response.sendError(HttpServletResponse.SC_NON_AUTHORITATIVE_INFORMATION, "Acesso nao autorizado a esse servico.");
                        }
                    } else if (method.equals("DELETE")) {
                        if (profile.getActions().get(p.getId()) != null && profile.getActions().get(p.getId()).isDelete()) {
                            if (p.isHasDelete()) {
                                json.add(page, delete(param, p, dao));
                            } else {
                                response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "Nao existe DELETE DE DADOS para essa page.");
                            }
                        } else {
                            response.sendError(HttpServletResponse.SC_NON_AUTHORITATIVE_INFORMATION, "Acesso nao autorizado a esse servico.");
                        }
                    }
                } else {
                    json = new Custom().apiRest(param, dao);
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Caminho inválido. Consulte a documentação em /api/v1/doc");
                return;
            }
            if (request.getParameter("format") == null || request.getParameter("format").isEmpty()) {
                out.println(json);
            } else if (format.equals("json")) {
                String result = jsonFormatted(json);
                response.setHeader("Content-Length", result.length() + "");
                out.print(result);
            } else {
                JSONObject jsonX = new JSONObject(jsonFormatted(json));
                String xml = "<result>" + XML.toString(jsonX) + "</result>";
                response.setHeader("Content-Length", xml.length() + "");
                out.print(xml);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro nao tratado. " + e.getMessage());
        } finally {
            out.close();
        }
        if (request.getParameter("page") != null) {
            Log.time("API /api/v1/custom?page=" + request.getParameter("page"), time);
        } else {
            Log.time("API /api/v1/" + path, time);
        }
    }

    private JsonObject doc(Profile profile, String url, String hash) {
        JsonObject jsonAcao = new JsonObject();
        JsonObject jsonConfig = new JsonObject();
        jsonConfig.addProperty("format", "xml/json");
        jsonConfig.addProperty("encode", "uft-8");
        jsonAcao.add("extra_param", jsonConfig);
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        for (Page p : Render.pages) {
            JsonObject jsonPage = new JsonObject();
            if (!profile.getActions().containsKey(p.getId()) || (!profile.getActions().get(p.getId()).isAdd() && !profile.getActions().get(p.getId()).isDelete() && !profile.getActions().get(p.getId()).isList() && !profile.getActions().get(p.getId()).isUpdate() && !profile.getActions().get(p.getId()).isSearch())) {
                continue;
            }
            jsonPage.addProperty("page", p.getId());
            if (profile.getActions().get(p.getId()).isSearch() || profile.getActions().get(p.getId()).isList()) {
                if (p.isHasList()) {
                    JsonArray arrayParam = new JsonArray();
                    String fields = "";
                    for (Field f : p.getFields()) {
                        if (f.isHasSearch()) {
                            JsonObject jsonParam = new JsonObject();
                            jsonParam.addProperty("id", f.getId());
                            jsonParam.addProperty("label", f.getLabel());
                            jsonParam.addProperty("type", f.getType());
                            jsonParam.addProperty("required", f.isRequired());
                            jsonParam.addProperty("size", f.getSize());
                            jsonParam.addProperty("description", f.getTitle());
                            arrayParam.add(jsonParam);
                            fields += "&" + f.getId() + "=value";
                        }
                    }
                    JsonObject jsonParam = new JsonObject();
                    jsonParam.add("params", arrayParam);
                    url = url.replace("api/v1/doc", "api/v1/custom");
                    jsonPage.addProperty("Example1", url + "?page=" + p.getId() + fields + "&hash=" + hash);
                    jsonPage.addProperty("Example2", url + "?page=" + p.getId() + fields + "&hash=" + hash + "&format=json&encode=utf-8");
                    jsonPage.addProperty("Example3", url + "?page=" + p.getId() + fields + "&hash=" + hash + "&format=xml");
                    jsonPage.add("GET (search)", jsonParam);
                }
            }
            if (profile.getActions().get(p.getId()).isAdd()) {
                if (p.isHasNew()) {
                    JsonArray arrayParam = new JsonArray();
                    for (Field f : p.getFields()) {
                        if (f.isHasNew()) {
                            JsonObject jsonParam = new JsonObject();
                            jsonParam.addProperty("id", f.getId());
                            jsonParam.addProperty("label", f.getLabel());
                            jsonParam.addProperty("type", f.getType());
                            jsonParam.addProperty("required", f.isRequired());
                            jsonParam.addProperty("size", f.getSize());
                            jsonParam.addProperty("description", f.getTitle());
                            arrayParam.add(jsonParam);
                        }
                    }
                    JsonObject jsonParam = new JsonObject();
                    jsonParam.add("params", arrayParam);
                    jsonPage.add("POST (insert)", jsonParam);
                }
            }
            if (profile.getActions().get(p.getId()).isUpdate()) {
                if (p.isHasUpdate()) {
                    JsonArray arrayParam = new JsonArray();
                    for (Field f : p.getFields()) {
                        if (f.isHasUpdate()) {
                            JsonObject jsonParam = new JsonObject();
                            jsonParam.addProperty("id", f.getId());
                            jsonParam.addProperty("label", f.getLabel());
                            jsonParam.addProperty("type", f.getType());
                            jsonParam.addProperty("required", f.isRequired());
                            jsonParam.addProperty("size", f.getSize());
                            arrayParam.add(jsonParam);
                        }
                    }
                    JsonObject jsonParamId = new JsonObject();
                    jsonParamId.addProperty("id", "id");
                    jsonParamId.addProperty("label", "Codigo");
                    jsonParamId.addProperty("type", "any");
                    jsonParamId.addProperty("required", "true");
                    jsonParamId.addProperty("size", "");
                    jsonParamId.addProperty("description", "Id utilizado para localizar o registro a ser excluido.");
                    arrayParam.add(jsonParamId);

                    JsonObject jsonParam = new JsonObject();
                    jsonParam.add("params", arrayParam);
                    jsonPage.add("PUT (update)", jsonParam);
                }
            }
            if (profile.getActions().get(p.getId()).isDelete()) {
                JsonObject jsonP = new JsonObject();
                if (p.isHasDelete()) {
                    JsonObject jsonParamId = new JsonObject();
                    jsonParamId.addProperty("id", "id");
                    jsonParamId.addProperty("label", "Codigo");
                    jsonParamId.addProperty("type", "any");
                    jsonParamId.addProperty("required", "true");
                    jsonParamId.addProperty("size", "");
                    jsonParamId.addProperty("description", "Id utilizado para localizar o registro a ser excluido.");
                    jsonP.add("params", jsonParamId);
                    jsonPage.add("DELETE (delete)", jsonP);
                }
            }
            jsonAcao.add("service:" + p.getName(), gson.toJsonTree(jsonPage));
        }
        return jsonAcao;
    }

    private String doc2(Profile profile, String url, String hash, String page) {
        StringBuilder docs = new StringBuilder();
        docs.append("<br>Nome do projeto: ").append(Render.project.getProjectName());
        docs.append("<br>Versão do projeto: ").append(Render.project.getProjectVersion());
        docs.append("<br>Data atual: ").append(new Date().toLocaleString());
        docs.append("<br><br>");
        if (page == null) {
            docs.append("API Rest - TODAS");
            for (Page p : Render.pages) {
                if (!profile.getActions().containsKey(p.getId()) || (!profile.getActions().get(p.getId()).isAdd() && !profile.getActions().get(p.getId()).isDelete() && !profile.getActions().get(p.getId()).isList() && !profile.getActions().get(p.getId()).isUpdate() && !profile.getActions().get(p.getId()).isSearch())) {
                    continue;
                }
                docs.append("<li><a href='").append(url).append("?hash=").append(hash).append("&page=").append(p.getId()).append("'>").append(p.getName()).append("</a></li>");
            }
        } else {
            Page p = Render.pages.get(Render.pages.indexOf(new Page(page)));
            docs.append("API Rest - ").append(p.getName());
            if (profile.getActions().get(p.getId()).isSearch() || profile.getActions().get(p.getId()).isList()) {
                if (p.isHasList()) {
                    String fields = "";
                    docs.append("<br><b>Método: GET</b>");
                    for (Field f : p.getFields()) {
                        if (f.isHasSearch()) {
                            docs.append("<br><br>ID=").append(f.getId());
                            docs.append("<br>Rótulo=").append(f.getLabel());
                            docs.append("<br>Tipo=").append(f.getType());
                            docs.append("<br>Obrigatório=").append(f.isRequired());
                            docs.append("<br>Tamanho=").append(f.getSize());
                            docs.append("<br>Descrição=").append(f.getTitle());
                            fields += "&" + f.getId() + "=value";
                        }
                    }
                    url = url.replace("api/v1/doc2", "api/v1/custom");
                    docs.append("<br><br><b>Ex. ").append(url).append("?page=").append(p.getId()).append(fields).append("&hash=").append(hash).append("&format=json");
                }
            }
            if (profile.getActions().get(p.getId()).isAdd()) {
                if (p.isHasNew()) {
                    String fields = "";
                    docs.append("<br><b>Método: POST</b>");
                    for (Field f : p.getFields()) {
                        if (f.isHasSearch()) {
                            docs.append("<br><br>ID=").append(f.getId());
                            docs.append("<br>Rótulo=").append(f.getLabel());
                            docs.append("<br>Tipo=").append(f.getType());
                            docs.append("<br>Obrigatório=").append(f.isRequired());
                            docs.append("<br>Tamanho=").append(f.getSize());
                            docs.append("<br>Descrição=").append(f.getTitle());
                            fields += "&" + f.getId() + "=value";
                        }
                    }
                }
            }
            if (profile.getActions().get(p.getId()).isUpdate()) {
                if (p.isHasUpdate()) {
                    String fields = "";
                    docs.append("<br><b>Método: PUT</b>");
                    for (Field f : p.getFields()) {
                        if (f.isHasSearch()) {
                            docs.append("<br><br>ID=").append(f.getId());
                            docs.append("<br>Rótulo=").append(f.getLabel());
                            docs.append("<br>Tipo=").append(f.getType());
                            docs.append("<br>Obrigatório=").append(f.isRequired());
                            docs.append("<br>Tamanho=").append(f.getSize());
                            docs.append("<br>Descrição=").append(f.getTitle());
                            fields += "&" + f.getId() + "=value";
                        }
                    }
                }
            }
            if (profile.getActions().get(p.getId()).isDelete()) {
                if (p.isHasDelete()) {
                    String fields = "";
                    docs.append("<br><b>Método: DELETE</b>");
                    for (Field f : p.getFields()) {
                        if (f.isHasSearch()) {
                            docs.append("<br><br>ID=").append(f.getId());
                            docs.append("<br>Rótulo=").append(f.getLabel());
                            docs.append("<br>Tipo=").append(f.getType());
                            docs.append("<br>Obrigatório=").append(f.isRequired());
                            docs.append("<br>Tamanho=").append(f.getSize());
                            docs.append("<br>Descrição=").append(f.getTitle());
                            fields += "&" + f.getId() + "=value";
                        }
                    }
                }
            }
        }
//        JsonObject jsonAcao = new JsonObject();
//        JsonObject jsonConfig = new JsonObject();
//        jsonConfig.addProperty("format", "xml/json");
//        jsonConfig.addProperty("encode", "uft-8");
//        jsonAcao.add("extra_param", jsonConfig);
//        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
//        for (Page p : Render.pages) {
//            JsonObject jsonPage = new JsonObject();
//            if (!profile.getActions().containsKey(p.getId()) || (!profile.getActions().get(p.getId()).isAdd() && !profile.getActions().get(p.getId()).isDelete() && !profile.getActions().get(p.getId()).isList() && !profile.getActions().get(p.getId()).isUpdate() && !profile.getActions().get(p.getId()).isSearch())) {
//                continue;
//            }
//            jsonPage.addProperty("page", p.getId());
//            if (profile.getActions().get(p.getId()).isSearch() || profile.getActions().get(p.getId()).isList()) {
//                if (p.isHasList()) {
//                    JsonArray arrayParam = new JsonArray();
//                    String fields = "";
//                    for (Field f : p.getFields()) {
//                        if (f.isHasSearch()) {
//                            JsonObject jsonParam = new JsonObject();
//                            jsonParam.addProperty("id", f.getId());
//                            jsonParam.addProperty("label", f.getLabel());
//                            jsonParam.addProperty("type", f.getType());
//                            jsonParam.addProperty("required", f.isRequired());
//                            jsonParam.addProperty("size", f.getSize());
//                            jsonParam.addProperty("description", f.getTitle());
//                            arrayParam.add(jsonParam);
//                            fields += "&" + f.getId() + "=value";
//                        }
//                    }
//                    JsonObject jsonParam = new JsonObject();
//                    jsonParam.add("params", arrayParam);
//                    url = url.replace("api/v1/doc", "api/v1/custom");
//                    jsonPage.addProperty("Example1", url + "?page=" + p.getId() + fields + "&hash=" + hash);
//                    jsonPage.addProperty("Example2", url + "?page=" + p.getId() + fields + "&hash=" + hash + "&format=json&encode=utf-8");
//                    jsonPage.addProperty("Example3", url + "?page=" + p.getId() + fields + "&hash=" + hash + "&format=xml");
//                    jsonPage.add("GET (search)", jsonParam);
//                }
//            }
//            if (profile.getActions().get(p.getId()).isAdd()) {
//                if (p.isHasNew()) {
//                    JsonArray arrayParam = new JsonArray();
//                    for (Field f : p.getFields()) {
//                        if (f.isHasNew()) {
//                            JsonObject jsonParam = new JsonObject();
//                            jsonParam.addProperty("id", f.getId());
//                            jsonParam.addProperty("label", f.getLabel());
//                            jsonParam.addProperty("type", f.getType());
//                            jsonParam.addProperty("required", f.isRequired());
//                            jsonParam.addProperty("size", f.getSize());
//                            jsonParam.addProperty("description", f.getTitle());
//                            arrayParam.add(jsonParam);
//                        }
//                    }
//                    JsonObject jsonParam = new JsonObject();
//                    jsonParam.add("params", arrayParam);
//                    jsonPage.add("POST (insert)", jsonParam);
//                }
//            }
//            if (profile.getActions().get(p.getId()).isUpdate()) {
//                if (p.isHasUpdate()) {
//                    JsonArray arrayParam = new JsonArray();
//                    for (Field f : p.getFields()) {
//                        if (f.isHasUpdate()) {
//                            JsonObject jsonParam = new JsonObject();
//                            jsonParam.addProperty("id", f.getId());
//                            jsonParam.addProperty("label", f.getLabel());
//                            jsonParam.addProperty("type", f.getType());
//                            jsonParam.addProperty("required", f.isRequired());
//                            jsonParam.addProperty("size", f.getSize());
//                            arrayParam.add(jsonParam);
//                        }
//                    }
//                    JsonObject jsonParamId = new JsonObject();
//                    jsonParamId.addProperty("id", "id");
//                    jsonParamId.addProperty("label", "Codigo");
//                    jsonParamId.addProperty("type", "any");
//                    jsonParamId.addProperty("required", "true");
//                    jsonParamId.addProperty("size", "");
//                    jsonParamId.addProperty("description", "Id utilizado para localizar o registro a ser excluido.");
//                    arrayParam.add(jsonParamId);
//
//                    JsonObject jsonParam = new JsonObject();
//                    jsonParam.add("params", arrayParam);
//                    jsonPage.add("PUT (update)", jsonParam);
//                }
//            }
//            if (profile.getActions().get(p.getId()).isDelete()) {
//                JsonObject jsonP = new JsonObject();
//                if (p.isHasDelete()) {
//                    JsonObject jsonParamId = new JsonObject();
//                    jsonParamId.addProperty("id", "id");
//                    jsonParamId.addProperty("label", "Codigo");
//                    jsonParamId.addProperty("type", "any");
//                    jsonParamId.addProperty("required", "true");
//                    jsonParamId.addProperty("size", "");
//                    jsonParamId.addProperty("description", "Id utilizado para localizar o registro a ser excluido.");
//                    jsonP.add("params", jsonParamId);
//                    jsonPage.add("DELETE (delete)", jsonP);
//                }
//            }
//            jsonAcao.add("service:" + p.getName(), gson.toJsonTree(jsonPage));
//        }
        return docs.toString();
    }

    private JsonArray list(Map<String, String> param, Page p, Dao dao) {
        JsonArray result = new JsonArray();
        try {
            String script = p.getSqlAPI();
            if (script != null && !script.trim().isEmpty() && !script.equals("null")) {
                if (script.trim().toUpperCase().contains("SELECT")) {
                    script = FormataTexto.replaceScriptWithValidation(script, param);
                    if (script.contains("#{")) {
                        throw new Exception("Parametro requerido nao informado");
                    }
                    ResultSQL r = dao.select(p.getSqlAPIDS(), script);
                    if (!r.dados.isEmpty()) {
                        for (Map<String, Object> item : r.dados) {
                            JsonObject obj = new JsonObject();
                            for (Column key : r.column) {
                                if (item.get(key.name) != null) {
                                    obj.add(key.name.toLowerCase(), new Gson().toJsonTree(item.get(key.name)));
                                } else {
                                    obj.add(key.name.toLowerCase(), null);
                                }
                            }
                            result.add(obj);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Log.error(ex);
            JsonObject jsonErr = new JsonObject();
            jsonErr.addProperty("error", ex.getMessage());
            result.add(jsonErr);
        }
        return result;
    }

    private JsonElement save(Map<String, String> param, Page p, Dao dao) {
        JsonObject json = new JsonObject();
        //valida
        ResultScriptBackend result = validScriptBackEnd("validSave", p.getJavascriptBack(), param);
        if (result.valid) {
            try {
                param = new Custom().save(param, dao);
                String script = p.getSqlSave();
                if (param != null) {
                    if (script != null && !script.trim().isEmpty() && !script.equals("null")) {
                        script = FormataTexto.replaceScript(script, param);
                        if (script.contains("#{")) {
                            throw new Exception("Parametro requerido nao informado");
                        }
                        dao.insertDeletUpdate(p.getSqlSaveDS(), script);
                    }
                    File temp = new File(Render.project.getPathUploadFiles() + File.separator + "temp");
                    if (temp.exists()) {
                        for (Field f : p.getFields()) {
                            if (f.getComponent().equals("file")) {
                                String name = param.get(f.getId());
                                File file = new File(temp.getAbsolutePath() + File.separator + name);
                                file.renameTo(new File(temp.getParent() + File.separator + name));
                            }
                        }
                    }
                }
                json.addProperty("success", "Registro inserido com sucesso!\n" + ((result.msg != null) ? result.msg : ""));
            } catch (Exception ex) {
                ex.printStackTrace();
                Log.error(ex);
                json.addProperty("error", ex.getMessage());
            }
        } else {
            json.addProperty("error", result.msg);
        }
        return json;
    }

    private JsonElement update(Map<String, String> param, Page p, Dao dao) {
        JsonObject json = new JsonObject();
        //valida
        ResultScriptBackend result = validScriptBackEnd("validEdit", p.getJavascriptBack(), param);
        if (result.valid) {
            try {
                param = new Custom().update(param, dao);
                String script = p.getSqlEdit();
                if (param != null) {
                    if (script != null && !script.trim().isEmpty() && !script.equals("null")) {
                        script = FormataTexto.replaceScript(script, param);
                        if (script.contains("#{")) {
                            throw new Exception("Parametro requerido nao informado");
                        }
                        dao.insertDeletUpdate(p.getSqlEditDS(), script);
                        File temp = new File(Render.project.getPathUploadFiles() + File.separator + "temp");
                        if (temp.exists()) {
                            for (Field f : p.getFields()) {
                                if (f.getComponent().equals("file")) {
                                    String name = param.get(f.getId());
                                    File file = new File(temp.getAbsolutePath() + File.separator + name);
                                    file.renameTo(new File(temp.getParent() + File.separator + name));
                                }
                            }
                        }
                    }
                }
                json.addProperty("success", "Registro alterado com sucesso!\n" + ((result.msg != null) ? result.msg : ""));

            } catch (Exception ex) {
                ex.printStackTrace();
                Log.error(ex);
                json.addProperty("error", ex.getMessage());
            }
        } else {
            json.addProperty("error", result.msg);
        }
        return json;
    }

    private JsonElement delete(Map<String, String> param, Page p, Dao dao) {
        JsonObject json = new JsonObject();
        //valida
        ResultScriptBackend result = validScriptBackEnd("validDelete", p.getJavascriptBack(), param);
        if (result.valid) {
            try {
                param = new Custom().delete(param, dao);
                String script = p.getSqlDelete();
                if (param != null) {
                    if (script != null && !script.trim().isEmpty() && !script.equals("null")) {
                        script = FormataTexto.replaceScript(script, param);
                        if (script.contains("#{")) {
                            throw new Exception("Parametro requerido nao informado");
                        }
                        dao.insertDeletUpdate(p.getSqlDeleteDS(), script);
                    }
                }
                json.addProperty("success", "Registro excluído com sucesso!\n" + ((result.msg != null) ? result.msg : ""));
            } catch (Exception e) {
                e.printStackTrace();
                Log.error(e);
                json.addProperty("error", e.getMessage());
            }
        } else {
            json.addProperty("error", result.msg);
        }
        return json;
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
            Log.error(e);
            result.msg = e.getMessage();
        } catch (ScriptException ex) {
            ex.printStackTrace();
            Log.error(ex);
            result.msg = ex.getMessage();
        }
        return result;
    }

    private String jsonFormatted(JsonObject obj) {
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        String json = gson.toJson(obj);
        return json;
//        return obj.toString();
    }

    private Profile auth(HttpServletRequest request, HttpServletResponse response, String hash) throws IOException {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        Log.info(ipAddress);
        if (ipAddress == null || ipAddress.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Nao foi possivel identificar o IP do cliente. ");
            return null;
        }
        if (Val.isNullOrEmpy(hash)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Parametro hash nao informado. ");
            return null;
        }
        boolean authorized = false;
        Profile profile = null;
        for (ApiUser au : Render.apiUsers) {
            if (au.getHash().equalsIgnoreCase(hash)) {
                int idProfile = Render.profiles.indexOf(new Profile(au.getPerfil()));
                if (idProfile == -1) {
                    response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "Pefil de acesso nao existe. Entre em contato com o administrador do sistema. ");
                    return null;
                }
                profile = Render.profiles.get(idProfile);
                if (au.getIps().isEmpty()) {
                    authorized = true;
                    break;
                }
                for (String ip : au.getIps()) {
                    if (ip.equals(ipAddress)) {
                        authorized = true;
                        break;
                    }
                }
            }
            if (profile != null) {
                break;
            }
        }
        if (profile == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Acesso nao autorizado.");
            return null;
        }
        if (!authorized) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Esse terminal nao tem permissao de acesso ao servico.");
            return null;
        }
        return profile;
    }
}
