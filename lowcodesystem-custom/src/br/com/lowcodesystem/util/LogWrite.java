/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lowcodesystem.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Maykon
 */
public class LogWrite {

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
    private static String teste = "";

    public static void config(String path, String logName, long size, String logDate, String hoje, String teste) {
        LogWrite.path = path;
        LogWrite.logName = logName;
        LogWrite.size = size;
        LogWrite.logDate = logDate;
        LogWrite.hoje = hoje;
        LogWrite.teste = teste;
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

    private static void write(String msg, String level, Exception ex) {
        try {
            if (levels.get(level) != null && levels.get(level) || level.equals(ERROR)) {
                FileWriter fw = new FileWriter(file(), true);
                PrintWriter pw = new PrintWriter(fw, true);
                if (msg != null && !msg.isEmpty()) {
                    pw.write("\n" + level + teste + " | " + new SimpleDateFormat("HH:mm:ss SSS").format(new Date()) + " | " + Thread.currentThread().getId() + " | " + msg + "");
                }
                if (ex != null) {
                    pw.write("\n" + level + teste + " | " + new SimpleDateFormat("HH:mm:ss SSS").format(new Date()) + " | " + Thread.currentThread().getId() + " | ");
                    ex.printStackTrace(pw);
                }
                pw.close();
                fw.close();
            }
        } catch (IOException ex1) {
            System.out.println("Erro em gerar log.\n" + ex1.getMessage());
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
}
