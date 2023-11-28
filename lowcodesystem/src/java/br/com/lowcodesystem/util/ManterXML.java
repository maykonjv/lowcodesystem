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
import java.io.StringReader;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 *
 * @author Maykon
 */
public class ManterXML {

    public static String pasta;
    public static String pastaRaiz;
    public static String SistemaOperacional = System.getProperty("os.name");

    public static String path(String fileName) throws FileNotFoundException {
        String pastaConf = System.getenv("CATALINA_HOME");
        if (pastaConf == null || pastaConf.isEmpty()) {
            if (SistemaOperacional.contains("Windows")) {
                String diretorioRaiz = System.getProperty("user.dir");
                pastaConf = diretorioRaiz.substring(0, 3);
            } else {
                pastaConf = File.separator;
            }
        }
        return pastaConf + File.separator + fileName + ".xml";

    }

    public static void writePathXML(String path, String fileName) throws IOException {
        pasta = path;
        File file = new File(path(fileName));
        if (!file.exists() && file.getParentFile() != null) {
            file.getParentFile().mkdirs();
            System.out.println("criando arquivo:" + file.getAbsolutePath());
        }
        if (!pasta.endsWith("\\") && !pasta.endsWith("/")) {
            pasta += File.separator;
        }
        XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(file)));
        encoder.writeObject(path);
        encoder.close();
        
        File dirProject = new File(pasta);
        if(!dirProject.exists()){
            dirProject.mkdirs();
            new File(pasta + "_log").mkdir();
            new File(pasta + "schema").mkdir();
            new File(pasta + "upload").mkdir();
            System.out.println("Schema created!");
        }
    }

    public static void writeXML(String session, Object classe) throws FileNotFoundException, IOException {
        System.out.println(pasta + "schema" + File.separator + session + ".xml");
        File file = new File(pasta + "schema" + File.separator + session + ".xml");
        writeXML(file, classe);
    }

    public static Object readXML(String session, String fileName) throws FileNotFoundException {
        if (pasta == null) {
            pastaRaiz = path(fileName);
            pasta = (String) readXML(new File(pastaRaiz));
        }
        File file = new File(pasta + "schema" + File.separator + session + ".xml");
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
            Log.error(ex);
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
            Log.error(ex);
            ex.printStackTrace();
            throw ex;
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                System.out.println("Erro ao criar xml. ");
                Log.error(ex);
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
            Log.error(ex);
            ex.printStackTrace();
            throw ex;
        }
        return classe;
    }

    public static Document readXMLtoDocument(String xml) throws Exception {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new StringReader(xml));
        return document;
    }
}
