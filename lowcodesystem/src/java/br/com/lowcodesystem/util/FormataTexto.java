package br.com.lowcodesystem.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author maykon
 */
public class FormataTexto {

    /**
     * Este metodo aplica a mascara em uma string (ex. maskApply("__.___-__*_",
     * "1234567891234") resulta: 12.345-67*891234
     *
     * @param mask
     * @param value
     * @return
     */
    public static String applyMask(String mask, String value) {
        if (mask == null || mask.isEmpty()) {
            return value;
        }
        if (value == null || value.isEmpty()) {
            return "";
        }
        int max = value.length() < (mask.length() - mask.replace("_", "").length()) ? value.length() : mask.length();
        StringBuilder result = new StringBuilder(value);
        for (int i = 0; i < max; i++) {
            if (mask.charAt(i) != '_') {
                result.insert(i, mask.charAt(i));
            }
        }
        return result.toString();
    }

    /**
     * <p>
     * Método responsável por converter a primeria letra da palavra para
     * maiuscula
     * <p>
     *
     * @param palavra
     * @return
     */
    public static String firstUpperCase(String palavra) {
        if (palavra != null && !palavra.equals("")) {
            return palavra.substring(0, 1).toUpperCase() + palavra.substring(1);
        } else {
            return "";
        }
    }

    /**
     * <p>
     * Método responsável por converter a primeria letra da palavra para
     * minuscula
     * <p>
     *
     * @param palavra
     * @return
     */
    public static String firstLowerCase(String palavra) {
        if (palavra != null && !palavra.equals("")) {
            return palavra.substring(0, 1).toLowerCase() + palavra.substring(1);
        } else {
            return "";
        }
    }

    public static String addslashes(String s) {
        if (s != null) {
            s = s.replaceAll("[']", "''");
            return s;
        }
        return "null";
    }

    public static String replaceScript(String script, Map<String, String> param) {
        script = script.replace("\n", "");
        Matcher m = Pattern.compile("\\#\\{\\w+\\b}").matcher(script);
        while (m.find()) {
            String value = param.get(m.group().replace("#{", "").replace("}", ""));
            if (value != null && !value.isEmpty()) {
                script = script.replace(m.group(), FormataTexto.addslashes(value));
            } else {
                script = script.replace(m.group(), "null");
            }
        }
        return script;
    }

    public static String replaceScriptWithValidation(String script, Map<String, String> param) {
        script = script.replace("\n", "");
        Matcher m1 = Pattern.compile("\\[(.*?)\\]").matcher(script);
        List<String> clauseConditional = new ArrayList<String>();
        while (m1.find()) {
            clauseConditional.add(m1.group());
        }
        Matcher m = Pattern.compile("\\#\\{\\w+\\b}").matcher(script);
        while (m.find()) {
            Object valueObj = param.get(m.group().replace("#{", "").replace("}", ""));
            if (valueObj != null && !valueObj.toString().isEmpty()) {
                script = script.replace(m.group(), FormataTexto.addslashes(valueObj.toString()));
            } else {
//                boolean a = false;
                for (String cc : clauseConditional) {
                    if (cc.contains(m.group())) {
                        script = script.replace(cc, "");
//                        a = true;
                    }
                }
//                if (!a) {
                script = script.replace(m.group(), "null");
//                }
            }
        }
        script = script.replaceAll("\\[", "").replaceAll("\\]", "");
        return script;
    }

    public static String formatHTML(Object value) {
        String html = "";
        if (value != null) {
            html = value.toString();
            html = html.replace("<", "&lt;").replace(">", "&gt;");
        }
        return html;
    }

    public static String onlyNumber(String var) {
        if (var == null) {
            return "";
        }
        return var.replaceAll("[^0-9]", "");
    }

    public static String toString(Object val) {
        if (val == null) {
            return "";
        }
        return val.toString();
    }

    public static String toString(Object val, String valDefault) {
        if (val == null || val.equals("")) {
            return valDefault;
        }
        return val.toString();
    }

}
