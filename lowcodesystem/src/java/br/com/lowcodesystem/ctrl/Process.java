package br.com.lowcodesystem.ctrl;

import static br.com.lowcodesystem.ctrl.Builder.SESSION_FORM;
import br.com.lowcodesystem.dao.Dao;
import br.com.lowcodesystem.model.Menu;
import br.com.lowcodesystem.model.Page;
import br.com.lowcodesystem.util.ManterXML;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Maykon Servlet responsavel por processar as requisições feitas pelos
 * formulários gerados
 */
public class Process extends HttpServlet {


    private static final String ACTION_DOWNLOAD_PAGE = "downloadPage";
    private static final String ACTION_UPLOAD_PAGE = "uploadPage";
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
        request.setCharacterEncoding("UTF-8");
        RequestDispatcher rd = request.getRequestDispatcher("render");
        String action = request.getParameter("action");
        if (action != null && action.equals(ACTION_DOWNLOAD_PAGE)) {
            String id = request.getParameter("id");
            Page page = null;
            for (Page p : Render.pages) {
                if (id != null && p.getId().equals(id)) {
                    page = p;
                    break;
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            XMLEncoder encoder = new XMLEncoder(baos);
            encoder.writeObject(page);
            encoder.close();

            response.setContentLength(baos.size());

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + id + ".xml");
            PrintWriter out = response.getWriter();
            out.write(baos.toString());
            out.flush();
        }
        rd.forward(request, response);
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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        RequestDispatcher rd = request.getRequestDispatcher("render");
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        String page = request.getParameter("page");

        request.getServletContext().setAttribute("__notification_link", "#");
        request.getServletContext().setAttribute("__notification", "0");

        if (action != null && action.equals(ACTION_UPLOAD_PAGE)) {
            String xml = request.getParameter("xml_page");
            if (xml != null) {
                try {
                    XMLDecoder decoder = new XMLDecoder(new ByteArrayInputStream(xml.getBytes("UTF-8")));
                    Page p = (Page) decoder.readObject();
                    if (p == null) {
                        request.setAttribute("error", "Formato de XML incorreto.");
                    } else {
                        p = getNewPage(p);
                        Render.pages.add(p);
                        ManterXML.writeXML(SESSION_FORM, Render.pages);
                        Menu m = new Menu();
                        m.setId("menu_" + p.getId());
                        m.setLabel(p.getName());
                        m.setPage(p.getId());
                        Render.menus.add(m);
                        ManterXML.writeXML(Builder.SESSION_MENU, Render.menus);
                        decoder.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("error", "Formato de XML incorreto." + e.getMessage());
                }
                rd = request.getRequestDispatcher("render?page=all");
            }
        }

        rd.forward(request, response);
    }

    private Page getNewPage(Page p) {
        if (Render.pages.contains(p)) {
            p.setId(p.getId() + "_1");
            p.setName(p.getName() + "_1");
            p = getNewPage(p);
        }
        return p;
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
