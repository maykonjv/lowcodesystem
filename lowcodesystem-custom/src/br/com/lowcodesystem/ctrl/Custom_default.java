/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lowcodesystem.ctrl;

import br.com.lowcodesystem.dao.Dao;
import com.google.gson.JsonObject;
import java.util.Map;

/**
 * Para download de arquivo, retornar os parametros (ContentLength, ContentType,
 * HeaderKey, HeaderValue)
 *
 * @author Maykon
 */
public class Custom_default {

    public JsonObject apiRest(JsonObject json, Map<String, String> param, Dao dao) {
        try {

        } catch (Exception ex) {
            json.addProperty("error", ex.getMessage());
        }
        return json;
    }

    public Object webservice(Map<String, String> param, Dao dao) {
        return null;
    }

    public Map<String, String> custom(Map<String, String> param, Dao dao) throws Exception {
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
        return param;
    }
}
