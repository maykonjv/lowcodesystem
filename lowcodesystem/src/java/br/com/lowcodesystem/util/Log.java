/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lowcodesystem.util;

import jakarta.servlet.jsp.JspWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Maykon
 */
public class Log {

    private static String path, logName, logDate;
    private static long size = 0;
    public static final String ERROR = "ERROR";
    public static final String WARNING = "WARNING";
    public static final String INFO = "INFO";
    public static final String CONFIG = "CONFIG";
    public static final String DEBUG = "DEBUG";
    public static final String TIME = "TIME";
    public static final String REQUEST = "REQUEST";
    private static String hoje = "";
    public static SimpleDateFormat sdfHoje = new SimpleDateFormat("yyyy-MM-dd");
    public static Map<String, Boolean> levels = new HashMap<String, Boolean>();
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HHmm");

    public static void config(String path, String logName, long size) {
        Log.path = path;
        Log.logName = logName;
        Log.size = size;
        info("Criando arquivo log:" + path + logName + "...");
        configAPI();
    }

    public static void configAPI() {
        String teste = "";
        try {
            if (Parse.toBoolean(ProjectLoad.properties.get("ambienteTeste"), false)) {
                teste = "-TESTE";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogWrite.config(path, logName, size, logDate, hoje, teste);
    }

    public static void error(String msg) {
        write(msg, ERROR, null);
    }

    public static void error(Exception ex) {
        write("", ERROR, ex);
    }

    public static void warning(String msg) {
        write(msg, WARNING, null);
    }

    public static void info(String msg) {
        write(msg, INFO, null);
    }

    public static void config(String msg) {
        write(msg, CONFIG, null);
    }

    public static void debug(String msg) {
        write(msg, DEBUG, null);
    }

    public static void request(String msg) {
        write(msg, REQUEST, null);
    }

    public static void time(String msg, long begin) {
        msg += "<i><b> (time:" + (System.currentTimeMillis() - begin) + ")</b></i>";
        write(msg, TIME, null);
    }

    public static long begin() {
        return System.currentTimeMillis();
    }

    public static void print(JspWriter out, File file, boolean isError, boolean isWarning, boolean isInfo, boolean isConfig, boolean isDebug, boolean isTime, boolean isRequest) {
        try {
            if (file.exists()) {
                List<String> list = ManterTXT.readListLine(file);
                out.println("<div style='font-size:small'><pre style='white-space:pre-wrap;word-break: break-word;'>");
                boolean ultima = false;
                for (String s : list) {
                    if (s.startsWith(ERROR) && isError) {
                        ultima = true;
                        out.println("</pre><pre style='white-space:pre-wrap;word-break: break-word;background: #F5A9A9;'>" + codeToHtml(s));
                    } else if (s.startsWith(WARNING) && isWarning) {
                        ultima = true;
                        out.println("</pre><pre style='white-space:pre-wrap;word-break: break-word;background: #F5ECCE;'>" + codeToHtml(s));
                    } else if (s.startsWith(CONFIG) && isConfig) {
                        ultima = true;
                        out.println("</pre><pre style='white-space:pre-wrap;word-break: break-word;background: #ffffff;'>" + codeToHtml(s));
                    } else if (s.startsWith(INFO) && isInfo) {
                        ultima = true;
                        out.println("</pre><pre style='white-space:pre-wrap;word-break: break-word;background: #FAFAFA;'>" + codeToHtml(s));
                    } else if (s.startsWith(DEBUG) && isDebug) {
                        ultima = true;
                        out.println("</pre><pre style='white-space:pre-wrap;word-break: break-word;background: #EFFBF8;'>" + codeToHtml(s));
                    } else if (s.startsWith(TIME) && isTime) {
                        ultima = true;
                        out.println("</pre><pre style='white-space:pre-wrap;word-break: break-word;background: #F2E0F7'>" + (s));
                    } else if (s.startsWith(REQUEST) && isRequest) {
                        ultima = true;
                        out.println("</pre><pre style='white-space:pre-wrap;word-break: break-word;background: #E6E6E6;'>" + codeToHtml(s));
                    } else if (!s.startsWith(ERROR) && !s.startsWith(WARNING) && !s.startsWith(CONFIG) && !s.startsWith(INFO) && !s.startsWith(TIME) && !s.startsWith(DEBUG) && !s.startsWith(REQUEST) && ultima) {
                        ultima = true;
                        out.println(codeToHtml(s));
                    } else {
                        ultima = false;
                    }
                }
                out.println("</pre></div>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void write(String msg, String level, Exception ex) {
        try {
            if (levels.get(level) != null && levels.get(level) || level.equals(ERROR)) {
                FileWriter fw = new FileWriter(file(), true);
                PrintWriter pw = new PrintWriter(fw, true);
                String teste = "";
                try {
                    if (Parse.toBoolean(ProjectLoad.properties.get("ambienteTeste"), false)) {
                        teste = "-TESTE";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (msg != null && !msg.isEmpty()) {
                    pw.write("\n" + level + teste + " | " + new SimpleDateFormat("HH:mm:ss SSS").format(new Date()) + " | " + Thread.currentThread().getId() + " | " + msg + "");
                }
                if (ex != null) {
                    pw.write("\n" + level + teste + " | " + new SimpleDateFormat("HH:mm:ss SSS").format(new Date()) + " | " + Thread.currentThread().getId());
                    ex.printStackTrace(pw);
                }
                pw.close();
                fw.close();
            }
        } catch (IOException ex1) {
            System.out.println("Erro em gerar log.\n");
            ex1.printStackTrace();
        }
    }

    private static File file() {
        if (logDate == null || logDate.isEmpty() || !hoje.equals(sdfHoje.format(new Date()))) {
            backup();
        }
        File file = new File(path + File.separator + logName + "-" + logDate + ".log");
        if (file.length() > size) {
            backup();
            return file();
        }
        return file;
    }

    public static void backup() {
        logDate = sdf.format(new Date());
        hoje = sdfHoje.format(new Date());
    }

    public static void delete(String fileName) {
        if (fileName != null && !fileName.isEmpty()) {
            File file = new File(fileName);
            if (file.exists()) {
                file.delete();
            }
            System.out.println(file.getAbsolutePath());
        }
    }

    public static String codeToHtml(String code) {
        return code.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }
}
