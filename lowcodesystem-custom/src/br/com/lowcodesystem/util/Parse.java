/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lowcodesystem.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.Node;
import org.json.JSONObject;
import org.json.XML;

/**
 *
 * @author Maykon
 */
public class Parse {

    /**
     * Converte o objeto para o tipo Date utilizando o formato informado. Caso o
     * objeto seja null retorna o valor definido como _default.
     *
     * @param obj
     * @param format
     * @param _default
     * @return
     * @throws Exception
     */
    public static Date toDate(Object obj, String format, Date _default) throws Exception {
        if (obj == null || String.valueOf(obj).isEmpty()) {
            return _default;
        } else {
            try {
                if (!Val.isNullOrEmpy(format)) {
                    return new SimpleDateFormat(format).parse(obj.toString());
                } else {
                    return (Date) obj;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("Falha na conversão de object(" + obj + ") para Date usando format(" + format + ").");
            }
        }
    }

    public static java.sql.Date toDate(Object obj, String format, java.sql.Date _default) throws Exception {
        if (obj == null || String.valueOf(obj).isEmpty()) {
            return _default;
        } else {
            try {
                if (!Val.isNullOrEmpy(format)) {
                    return new java.sql.Date(new SimpleDateFormat(format).parse(obj.toString()).getTime());
                } else {
                    return (java.sql.Date) obj;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("Falha na conversão de object(" + obj + ") para Date usando format(" + format + ").");
            }
        }
    }

    public static java.sql.Timestamp toTimestamp(Object obj, String format, java.sql.Timestamp _default) throws Exception {
        if (obj == null || String.valueOf(obj).isEmpty()) {
            return _default;
        } else {
            try {
                if (!Val.isNullOrEmpy(format)) {
                    return new java.sql.Timestamp(new SimpleDateFormat(format).parse(obj.toString()).getTime());
                } else {
                    return (java.sql.Timestamp) obj;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("Falha na conversão de object(" + obj + ") para Date usando format(" + format + ").");
            }
        }
    }

    public static String toString(Date obj, String format, String _default) {
        if (obj == null) {
            return _default;
        } else {
            if (!Val.isNullOrEmpy(format)) {
                return new SimpleDateFormat(format).format(obj.toString());
            } else {
                return obj.toLocaleString();
            }
        }
    }

    public static String toString(Object obj, String _default) {
        if (obj == null) {
            return _default;
        } else {
            return obj.toString();
        }
    }

    public static String toStringTrim(Object obj, String _default) {
        if (obj == null) {
            return _default;
        } else {
            return obj.toString().trim();
        }
    }

    public static Integer toInt(Object obj, Integer _default) throws Exception {
        if (obj == null || String.valueOf(obj).isEmpty()) {
            return _default;
        } else {
            try {
                return Integer.parseInt(obj.toString().trim());
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("Falha na conversão de object(" + obj + ") para Integer.");
            }
        }
    }

    public static Long toLong(Object obj, Long _default) throws Exception {
        if (obj == null || String.valueOf(obj).isEmpty()) {
            return _default;
        } else {
            try {
                return Long.parseLong(obj.toString().trim());
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("Falha na conversão de object(" + obj + ") para Long.");
            }
        }
    }

    public static Boolean toBoolean(Object obj, Boolean _default) throws Exception {
        if (obj == null || String.valueOf(obj).isEmpty()) {
            return _default;
        } else {
            try {
                return Boolean.parseBoolean(obj.toString().trim());
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("Falha na conversão de object(" + obj + ") para Boolean.");
            }
        }
    }

    public static String toJson(String xml) {
        if (xml == null || String.valueOf(xml).isEmpty()) {
            return "{}";
        }
        JSONObject retorno = XML.toJSONObject(xml);

        return retorno.toString();
    }

    public static String xmlToText(Document doc, String nodes) {
        Node node = doc.selectSingleNode(nodes);
        if (node != null) {
            return node.getText();
        }
        return "";
    }

    public static List<String> xmlToListText(Document doc, String nodes) {
        List<Node> nodeList = (List<Node>) doc.selectNodes(nodes);
        List<String> retorno = new ArrayList<>();
        if (nodeList != null) {
            nodeList.forEach((n) -> {
                retorno.add(n.getText());
            });
        }
        return retorno;
    }

    public static String codeToHtml(String code) {
        return "<pre>" + code.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;") + "</pre>";
    }
}
