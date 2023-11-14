/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package br.com.lowcodesystem.ctrl;

import br.com.lowcodesystem.dao.Column;
import br.com.lowcodesystem.dao.Dao;
import br.com.lowcodesystem.dao.ResultSQL;
import br.com.lowcodesystem.util.CriptoMD5;
import br.com.lowcodesystem.util.FormataTexto;
import br.com.lowcodesystem.util.ManterTXT;
import br.com.lowcodesystem.util.ManterXML;
import br.com.lowcodesystem.util.UtilAPI;
import br.com.lowcodesystem.util.Val;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author mayko
 */
@WebServlet(name = "Auth", urlPatterns = {"/auth"})
public class Auth extends HttpServlet {

    private static final String ACTION_LOGIN = "login";
    private static final String ACTION_LOGIN_ADMIN = "login-admin";
    private final Dao dao = new Dao();

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
        System.out.println("Get::Auth");
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("login.jsp").include(request, response);
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
        System.out.println("Post::Auth");
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        RequestDispatcher rd = request.getRequestDispatcher("render");
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        String page = request.getParameter("page");

        request.getServletContext().setAttribute("__notification_link", "#");
        request.getServletContext().setAttribute("__notification", "0");

        if (action.equals(ACTION_LOGIN)) {
            try {
                if (!Render.project.isLoginExterno()) {
                    String user = request.getParameter("user");
                    String pass = request.getParameter("pass");
                    ResultSQL result;
                    String script = Render.project.getSqlLogin();
                    String opcoesLogin = Render.project.getOpcoesLogin();
                    boolean logado = false;
                    if (!Val.isNullOrEmpy(opcoesLogin) && !opcoesLogin.equals("null")) {
                        String[] userLogins = opcoesLogin.replace("\r", "").split("\n");
                        for (String ul : userLogins) {
                            String[] userLogin = ul.split(":");
                            if (userLogin.length < 3) {
                                throw new Exception("Erro na configuração de login.<br> Consulte o administrador.");
                            }
                            if (user.equals(userLogin[1]) && pass.equals(userLogin[2])) {
                                request.getSession(true).setAttribute("authenticated", true);
                                request.getSession(true).setAttribute("perfil", userLogin[0]);
                                request.getSession(true).setAttribute("__user", userLogin[1]);
                                logado = true;
                                break;
                            }
                        }
                        if (!logado) {
                            request.setAttribute("error", "Usuário e/ou senha inválidos");
                        }
                    } else if (script != null && !script.trim().isEmpty() && !script.equals("null")) {
                        pass = CriptoMD5.encryptMD5(pass);
                        script = script.replaceAll("\\#\\{user}", FormataTexto.addslashes(user)).replaceAll("\\#\\{pass}", FormataTexto.addslashes(pass));
                        result = dao.select(Render.project.getDatasource(), script);
                        boolean first = true;
                        if (!result.dados.isEmpty()) {
                            for (Column col : result.column) {
                                System.out.println("Session:" + col + "=" + result.dados.get(0).get(col.name));
                                if (first) {
                                    request.getSession(true).setAttribute("perfil", result.dados.get(0).get(col.name));
                                    first = false;
                                } else {
                                    request.getSession(true).setAttribute(col.name, result.dados.get(0).get(col.name));
                                }
                            }
                            request.getSession(true).setAttribute("authenticated", true);
                        } else {
                            request.setAttribute("error", "Usuário e/ou senha inválidos");
                        }
                    } else {
                        request.getSession(true).setAttribute("authenticated", true);
                        request.getSession(true).setAttribute("__user", "user");
                        request.getSession(true).setAttribute("perfil", Render.ADMINISTRADOR);
                    }
                } else {
                    String user = request.getParameter("user");
                    String pass = request.getParameter("pass");
                    String url = Render.project.getUrlLogin();
                    if (Val.isNullOrEmpy(url)) {
                        request.setAttribute("error", "Falha na configuraçao de autenticação.<br>Informe a url do serviço de autenticação.");
                    } else {
                        Map<String, String> param = new HashMap<>();
                        param.put("usuario", user);
                        param.put("senha", pass);
                        param.put("hash", CriptoMD5.encryptMD5(user + "-" + pass + "-" + new SimpleDateFormat("dd/MM/yyyy").format(new Date())));
                        try {
                            String jsonLogin = UtilAPI.post(param, url, null);
                            System.out.println(jsonLogin);
                            if (!Val.isNullOrEmpy(jsonLogin)) {
                                JSONObject j = new JSONObject(jsonLogin);
                                if (j.keySet().contains("perfil")) {
                                    request.getSession(true).setAttribute("authenticated", true);
                                    request.getSession(true).setAttribute("perfil", j.get("perfil"));
                                } else {
                                    request.setAttribute("error", j.get("ExceptionMessage"));
                                }
                            } else {
                                request.setAttribute("error", "Usuário e/ou senha inválidos");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            request.setAttribute("error", "Erro na autenticação. Verifique a URL de autenticação externa: " + e.getMessage());
                        }
                    }
                }
            } catch (java.net.UnknownHostException ex) {
                ex.printStackTrace();
                request.setAttribute("error", "A aplicação não está acessando o servidor para autenticação: " + ex.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
                request.setAttribute("error", ex.getMessage());
            }
        } else if (action.equals(ACTION_LOGIN_ADMIN)) {
            try {
                String pass = request.getParameter("password");
                String username = request.getParameter("username");

                ////########## SENHA DEFAULT PARA ACESSO AO MODO DESENVOLVEDOR #######################################
                String resp = CriptoMD5.encryptMD5("LowcodeSystem - " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
                if (ManterXML.pasta != null && ManterXML.pasta.length() > 0) {
                    if (new File(ManterXML.pasta + "pass.txt").exists()) {
                        resp = ManterTXT.readFile(new File(ManterXML.pasta + "pass.txt"), StandardCharsets.ISO_8859_1.name()).toString();
                    }
                }
                if (resp.equalsIgnoreCase(pass.trim()) && username.equalsIgnoreCase("dev")) {
                    request.getSession(true).setAttribute("dev", true);
                    rd = request.getRequestDispatcher("adminpath.jsp");
                } else {
                    if (!username.equalsIgnoreCase("dev")) {
                        request.setAttribute("error", "Usuário inválido");
                    } else {
                        request.setAttribute("error", "Senha inválida");
                    }
                    rd = request.getRequestDispatcher("config.jsp");
                }
                rd.forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
                request.setAttribute("error", ex.getMessage());
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
