/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lowcodesystem.ctrl;

import br.com.lowcodesystem.dao.Dao;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import static org.apache.http.HttpHeaders.USER_AGENT;

/**
 * Para download de arquivo, retornar os parametros (ContentLength, ContentType,
 * HeaderKey, HeaderValue)
 *
 * @author Maykon
 */
public class Custom {
    //nome do arquivo de configuracao do caminho do schema da aplicação

    public static String configName = "lowcodesystem";
    public static String datasource = "lowcodesystem-ds";

    /**
     * Somente será executado quando for chamado alguma api de uma pagina
     * diferente de "custom"
     *
     * @param json
     * @param param
     * @param dao
     * @return
     */
    public JsonObject apiRest(Map<String, String> param, Dao dao) {
        JsonObject json = new JsonObject();
        try {
            System.out.println("apirest");
            System.out.println(param);
        } catch (Exception ex) {
            ex.printStackTrace();
            param.put("error", ex.getMessage());
        }
        return json;
    }

    public Map<String, String> custom(Map<String, String> param, Dao dao) throws Exception {
        try {
            System.out.println("custom");
            System.out.println(param);
        } catch (Exception ex) {
            ex.printStackTrace();
            param.put("error", ex.getMessage());
        }
        return param;
    }

    /**
     * Método que intercepta a rotina que grava o registro na base. Se esse
     * metodo retornar null, a rotina será automaticamente finalizada antes de
     * executar o sql de insert no banco de dados e apresentará a mensagem de
     * sucesso. Caso seja necessário retornar alguma mensagem de erro, utilize
     * throw new Exception();
     *
     * @param param
     * @param dao
     * @param out
     * @return
     * @throws java.lang.Exception
     */
    public Map<String, String> save(Map<String, String> param, Dao dao) throws Exception {
        try {
            System.out.println("save");
            System.out.println(param);
        } catch (Exception ex) {
            ex.printStackTrace();
            param.put("error", ex.getMessage());
        }
        return param;
    }

    public Map<String, String> toSave(Map<String, String> param, Dao dao) throws Exception {
        try {
            System.out.println("toSave");
            System.out.println(param);
        } catch (Exception ex) {
            ex.printStackTrace();
            param.put("error", ex.getMessage());
        }
        return param;
    }

    /**
     * Método que intercepta a rotina que altera o registro na base. Se esse
     * metodo retornar null, a rotina será automaticamente finalizada antes de
     * executar o sql de update no banco de dados e apresentará a mensagem de
     * sucesso. Caso seja necessário retornar alguma mensagem de erro, utilize
     * throw new Exception();
     *
     * @param param
     * @param dao
     * @param out
     * @return
     * @throws java.lang.Exception
     */
    public Map<String, String> update(Map<String, String> param, Dao dao) throws Exception {
        try {
            System.out.println("update");
            System.out.println(param);
        } catch (Exception ex) {
            ex.printStackTrace();
            param.put("error", ex.getMessage());
        }
        return param;
    }

    public Map<String, String> toUpdate(Map<String, String> param, Dao dao) throws Exception {
        try {
            System.out.println("toUpdate");
            System.out.println(param);
        } catch (Exception ex) {
            ex.printStackTrace();
            param.put("error", ex.getMessage());
        }
        return param;
    }

    /**
     * Método que intercepta a rotina que exclui o registro na base. Se esse
     * metodo retornar null, a rotina será automaticamente finalizada antes de
     * executar o sql de delete no banco de dados e apresentará a mensagem de
     * sucesso. Caso seja necessário retornar alguma mensagem de erro, utilize
     * throw new Exception();
     *
     * @param param
     * @param dao
     * @param out
     * @return
     * @throws java.lang.Exception
     */
    public Map<String, String> delete(Map<String, String> param, Dao dao) throws Exception {
        try {
            System.out.println("delete");
            System.out.println(param);
        } catch (Exception ex) {
            ex.printStackTrace();
            param.put("error", ex.getMessage());
        }
        return param;
    }

    /**
     * Método que intercepta a rotina que ativa/inativa o registro na base. Se
     * esse metodo retornar null, a rotina será automaticamente finalizada antes
     * de executar o sql de update no banco de dados e apresentará a mensagem de
     * sucesso. Caso seja necessário retornar alguma mensagem de erro, utilize
     * throw new Exception();
     *
     * @param param
     * @param dao
     * @param out
     * @return
     * @throws java.lang.Exception
     */
    public Map<String, String> active(Map<String, String> param, Dao dao) throws Exception {
        try {
            System.out.println("active");
            System.out.println(param);
        } catch (Exception ex) {
            ex.printStackTrace();
            param.put("error", ex.getMessage());
        }
        return param;
    }

    /**
     * Método que intercepta a rotina que pesquisa o registro na base. Se esse
     * metodo retornar null, a rotina será automaticamente finalizada antes de
     * executar o sql de consulta no banco de dados e não apresentará a grid.
     * Caso seja necessário retornar alguma mensagem de erro, utilize throw new
     * Exception();
     *
     * @param param
     * @param dao
     * @param out
     * @return
     * @throws java.lang.Exception
     */
    public Map<String, String> search(Map<String, String> param, Dao dao) throws Exception {
        try {
            System.out.println("search");
            System.out.println(param);
        } catch (Exception ex) {
            ex.printStackTrace();
            param.put("error", ex.getMessage());
        }
        return param;
    }

    /**
     * Método que intercepta a rotina que apresenta o registro. Se esse metodo
     * retornar null, a rotina será automaticamente finalizada antes de executar
     * o sql de consulta no banco de dados. Caso seja necessário retornar alguma
     * mensagem de erro, utilize throw new Exception();
     *
     * @param param
     * @param dao
     * @param out
     * @return
     * @throws java.lang.Exception
     */
    public Map<String, String> view(Map<String, String> param, Dao dao) throws Exception {
        try {
            System.out.println("view");
            System.out.println(param);
        } catch (Exception ex) {
            ex.printStackTrace();
            param.put("error", ex.getMessage());
        }
        return param;
    }

    // HTTP POST request
    private String sendPost(Map<String, String> param) throws Exception {

        String url = "http://localhost:8080/IntegradorGSView/template";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "";

        if (param != null) {
            for (String key : param.keySet()) {
                if (urlParameters.isEmpty()) {
                    urlParameters += key + "=" + param.get(key);
                    continue;
                }
                urlParameters += "&" + key + "=" + param.get(key);
            }
        }

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }
}
