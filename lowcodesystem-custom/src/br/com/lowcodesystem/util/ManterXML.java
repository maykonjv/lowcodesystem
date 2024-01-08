/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lowcodesystem.util;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 *
 * @author Maykon
 */
public class ManterXML {

    public static String pasta;
    private static final String barra = System.getProperty("file.separator");
    public static String SistemaOperacional = System.getProperty("os.name");

    public static String path() throws FileNotFoundException {
        String pastaConf = System.getenv("CATALINA_HOME") + barra;
        if (pastaConf == null || pastaConf.isEmpty()) {
            if (SistemaOperacional.contains("Windows")) {
                String diretorioRaiz = System.getProperty("user.dir");
                pastaConf = diretorioRaiz.substring(0, 3);
            } else {
                pastaConf = barra;
            }
        }
        return pastaConf + "path.xml";

    }

    public static void writePathXML(String path) throws IOException {
        pasta = path;
        File file = new File(path());
        if (!file.exists() && file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(file)));
        encoder.writeObject(path);
        encoder.close();
    }

    public static void writeXML(String session, Object classe) throws FileNotFoundException, IOException {
        System.out.println(pasta + "schema" + barra + session + ".xml");
        File file = new File(pasta + "schema" + barra + session + ".xml");
        writeXML(file, classe);
    }

    public static Object readXML(String session) throws FileNotFoundException {
        if (pasta == null) {
            pasta = (String) readXML(new File(path()));
        }
        File file = new File(pasta + "schema" + barra + session + ".xml");
        return readXML(file);
    }

    /**
     * Escreve um xml a partir da classe passada por parametro no arquivo (file)
     *
     * @param file
     * @param classe
     * @throws java.io.FileNotFoundException
     */
    public static void writeXML(File file, Object classe) throws FileNotFoundException, IOException {
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(file)));
            encoder.writeObject(classe);
            encoder.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Erro ao criar xml. ");
            ManterLog.erro(ex);
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * Escreve um xml a partir de um document passada por parametro no arquivo
     * (file)
     *
     * @param doc
     * @param file
     * @throws java.io.IOException
     */
    public static void writeXML(Document doc, File file) throws IOException {
        FileWriter out = null;
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            out = new FileWriter(file);
            XMLWriter writer = new XMLWriter(out, OutputFormat.createPrettyPrint());
            writer.write(doc);
            writer.close();
        } catch (IOException ex) {
            System.out.println("Erro ao criar xml. ");
            ManterLog.erro(ex);
            ex.printStackTrace();
            throw ex;
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                System.out.println("Erro ao criar xml. ");
                ManterLog.erro(ex);
                ex.printStackTrace();
            }
        }
    }

    /**
     * leitura de um arquivo xml
     *
     * @param file
     * @return
     * @throws java.io.FileNotFoundException
     */
    public static Object readXML(File file) throws FileNotFoundException {
        Object classe = null;
        try {
            if (file.exists()) {
                XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(file)));
                classe = decoder.readObject();
                decoder.close();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Erro ao ler xml");
            ManterLog.erro(ex);
            ex.printStackTrace();
            throw ex;
        }
        return classe;
    }
}