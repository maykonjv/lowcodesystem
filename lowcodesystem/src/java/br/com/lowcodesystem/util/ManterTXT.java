/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lowcodesystem.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maykon
 */
public class ManterTXT {

    /**
     * retorna uma lista de String, na qual, cada item da lista representa uma
     * linha do arquivo;
     *
     * @param arquivo
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static List<String> readListLine(File arquivo) throws FileNotFoundException, IOException {
        List<String> list = new ArrayList<String>();
        if (arquivo.exists()) {
            BufferedReader in = new BufferedReader(new FileReader(arquivo));
            String str;
            while (in.ready()) {
                str = in.readLine();
                list.add(str);
            }
            in.close();
        }
        return list;
    }

    /**
     * retorna uma StringBuilder com todo o conteudo do arquivo
     *
     * @param arquivo
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static StringBuilder readFile(File arquivo) throws FileNotFoundException, IOException {
        return readFile(arquivo, null);
    }

    public static StringBuilder readFile(File arquivo, String charset) throws FileNotFoundException, IOException {
        if (!arquivo.exists()) {
            return new StringBuilder();
        }
        StringBuilder sb = new StringBuilder();
        FileInputStream fis = new FileInputStream(arquivo);

        if (charset == null) {
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);
            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str).append("\n");
            }
        } else {
            InputStreamReader isr = new InputStreamReader(fis, charset);
            BufferedReader reader = new BufferedReader(isr);
            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
        }
        return sb;
    }

    /**
     * Retorna um String representando o conteudo da linha informada no arquivo
     *
     * @param arquivo
     * @param line
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String readLine(File arquivo, int line) throws FileNotFoundException, IOException {
        if (!arquivo.exists()) {
            return "";
        }
        String s = "";
        BufferedReader in = new BufferedReader(new FileReader(arquivo));
        String str;
        int i = 0;
        while (in.ready()) {
            i++;
            str = in.readLine();
            if (i == line) {
                s = str;
            }
        }
        in.close();
        return s;
    }

    /**
     * Escreve o conteudo da StringBuilder no arquivo informado. Charset UTF-8
     *
     * @param sb
     * @param arquivo
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void write(StringBuilder sb, File arquivo) throws FileNotFoundException, IOException {
        write(sb, arquivo, null);
    }

    /**
     * Escreve o conteudo da StringBuilder no arquivo informado.
     *
     * @param sb
     * @param arquivo
     * @param encode
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void write(StringBuilder sb, File arquivo, String encode) throws FileNotFoundException, IOException {
        if (arquivo.getParentFile() != null && !arquivo.getParentFile().exists()) {
            arquivo.getParentFile().mkdirs();
        }
        if (!arquivo.exists()) {
            arquivo.createNewFile();
        }
        Writer out;
        if (encode == null || encode.isEmpty()) {
            encode = StandardCharsets.UTF_8.name();
        }
        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arquivo), encode));
        out.append(sb.toString());
        out.flush();
        out.close();
    }
}
