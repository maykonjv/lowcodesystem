/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lowcodesystem.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe que gerar arquivo de log
 *
 * @author usuario
 */
public class ManterLog {

    /**
     * variavel que define se a aplicação deve printar as mensagens de debug.
     */
    public static boolean debug;
//    public static boolean debugDB;

    /**
     * informe a exception que seja printar. Esta mensagem sempre será printada
     * no log em forma de stacktrace
     *
     * @param ex
     */
    public static void erro(Exception ex) {
        try {
            File file = new File("log-" + new SimpleDateFormat("dd-MM-yy").format(new Date()) + ".txt");
            FileWriter fw = new FileWriter(file, true);
            PrintWriter pw = new PrintWriter(fw, true);
            pw.write("\n##ERROR#| " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss ").format(new Date()));
            ex.printStackTrace(pw);
            pw.close();
            fw.close();
        } catch (IOException ex1) {
            System.out.println("Erro em gerar log.\n" + ex1.getMessage());
        }
    }

    /**
     * informe a mensagem de erro que seja printar. Esta mensagem sempre será
     * printada no log
     *
     * @param ex
     */
    public static void erro(String ex) {
        try {
            File file = new File("log-" + new SimpleDateFormat("dd-MM-yy").format(new Date()) + ".txt");
            FileWriter fw = new FileWriter(file, true);
            PrintWriter pw = new PrintWriter(fw, true);
            pw.write("\n##ERROR#| " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss ").format(new Date()) + ": " + ex);
            pw.close();
            fw.close();
        } catch (IOException ex1) {
            System.out.println("Erro em gerar log.\n" + ex1.getMessage());
        }
    }

    /**
     * informe a mensagem de informação que seja printar. Esta mensagem sempre
     * será printada no log
     *
     * @param msg
     */
    public static void info(String msg) {
        try {
            File file = new File("log-" + new SimpleDateFormat("dd-MM-yy").format(new Date()) + ".txt");
            FileWriter fw = new FileWriter(file, true);
            PrintWriter pw = new PrintWriter(fw, true);
            pw.write("\n##INFO#| " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss ").format(new Date()) + ": " + msg);
            pw.close();
            fw.close();
        } catch (IOException ex1) {
            System.out.println("Erro em gerar log.\n" + ex1.getMessage());
        }
    }

    /**
     * informe a mensagem que seja printar quanto o sistema estiver em modo
     * debug, ou seja, ManterLog.debug = true;
     *
     * @param msg
     */
    public static void debug(String msg) {
        if (debug) {
            try {
                File file = new File("log-" + new SimpleDateFormat("dd-MM-yy").format(new Date()) + ".txt");
                FileWriter fw = new FileWriter(file, true);
                PrintWriter pw = new PrintWriter(fw, true);
                pw.write("\n##DEBUG#| " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss ").format(new Date()) + ": " + msg + "\n");
                pw.close();
                fw.close();
            } catch (IOException ex1) {
                System.out.println("Erro em gerar log.\n" + ex1.getMessage());
            }
        }
    }

    public static void debug(String ordem, String operacao, String cpf, String loja, String venda, String qtditens) {
        debug("|" + ordem + "|" + operacao + "|cpf:" + cpf + "|loja:" + loja + "|venda:" + venda + "|qtd:" + qtditens);
    }

}
