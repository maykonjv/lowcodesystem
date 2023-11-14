package br.com.lowcodesystem.ctrl;

import com.google.common.io.Files;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
 

/**
 *
 * @author Maykon
 */
@WebServlet(name = "Upload", urlPatterns = {"/upload/*"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 20, // 20MB
        maxFileSize = 1024 * 1024 * 100, // 100MB
        maxRequestSize = 1024 * 1024 * 500)
public class Upload extends HttpServlet {

    public static String css;

    /**
     * Extracts file name from HTTP header content-disposition
     */
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }

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
        String requestedImage = request.getPathInfo();
        boolean isCustom = false;
        if (requestedImage == null) {
            response.sendError(404);
            return;
        } else {
            if (requestedImage.startsWith("/custom/")) {
                requestedImage = requestedImage.replace("/custom", "");
                isCustom = true;
            }
        }
        css = "";
        File image = new File(Render.project.getPathUploadFiles() + File.separator, URLDecoder.decode(requestedImage, "UTF-8"));
        if (!image.exists()) {
            if (isCustom) {
                image = new File(getServletContext().getRealPath("/") + "/img/logo1x1.png");
                css = "display: none;";
            } else {
                response.sendError(404);
                return;
            }
        }
        String contentType = getServletContext().getMimeType(image.getName());
        if ((contentType == null) || (!contentType.startsWith("image"))) {
            response.sendError(404);
            return;
        }
        response.reset();
        response.setContentType(contentType);
        response.setHeader("Content-Length", String.valueOf(image.length()));
        Files.copy(image, response.getOutputStream());
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
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Max-Age", "86400");

        JsonObject json = new JsonObject();

        if (Render.project.getPathUploadFiles() == null || Render.project.getPathUploadFiles().isEmpty()) {
            json.addProperty("error", "Caminho para upload de arquivo nao configurado.");
            out.println(json.toString());
            out.close();
            return;
        }
        try {
            // constructs path of the directory to save uploaded file
            String savePath = Render.project.getPathUploadFiles() + File.separator + "temp";

            // creates the save directory if it does not exists
            File fileSaveDir = new File(savePath);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdirs();
            }
            int i = 0;
            for (Part part : request.getParts()) {
                if (part.getContentType() != null) {
                    String ext = extractFileName(part);
                    String fileName;
                    if (Render.project.isRenameFilesUpload()) {
                        ext = ext.substring(ext.lastIndexOf("."));
                        fileName = new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date()) + "_" + i++ + ext.toLowerCase();
                    } else {
                        fileName = ext;
                    }
                    json.addProperty("name", fileName);
                    part.write(savePath + File.separator + fileName);
                }
            }
            out.println(json.toString());
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            json.addProperty("error", e.getMessage());
            out.println(json.toString());
            out.close();
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
