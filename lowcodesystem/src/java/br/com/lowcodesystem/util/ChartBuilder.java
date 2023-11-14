/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lowcodesystem.util;

import br.com.lowcodesystem.util.FormataTexto;
import br.com.lowcodesystem.dao.Dao;
import br.com.lowcodesystem.dao.ResultSQL;
import br.com.lowcodesystem.model.DataSet;
import br.com.lowcodesystem.model.Field;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Maykon
 */
public class ChartBuilder {

    public static String bar(Field f, Dao dao, Map<String, String> param) {
        StringBuilder sqlBuilder = new StringBuilder();
        ResultSQL r = null;
        String script = f.getSqlOptions();
        if (script != null && !script.trim().isEmpty() && !script.equals("null")) {
            try {
                if (script.trim().toUpperCase().startsWith("SELECT")) {
                    script = FormataTexto.replaceScriptWithValidation(script, param);
                    r = dao.select(f.getDatasource(), script);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (r != null) {
            String labels = "", datasets = "";
            Map<Object, DataSet> datas = new HashMap<Object, DataSet>();
            if (f.getDefaultValue() != null && !f.getDefaultValue().isEmpty()) {
                datasets = f.getDefaultValue();
            } else {
                for (Map<String, Object> d : r.dados) {
                    System.out.println(d.get("group") + "-" + d.get("label") + "-" + d.get("value"));
                    if (labels.isEmpty()) {
                        labels += "'" + d.get("label") + "'";
                    } else if (!labels.contains(Parse.toString(d.get("label"), ""))) {
                        labels += ", '" + d.get("label") + "'";
                    }
                    if (!datas.containsKey(Parse.toString(d.get("group"), ""))) {
                        datas.put(d.get("group"), new DataSet());
                    }
                }
                for (Map<String, Object> d : r.dados) {
                    boolean empty = true;
                    for (Object key : datas.keySet()) {
                        if (key != null && key.equals(d.get("group"))) {
                            datas.get(key).value += (datas.get(key).value.isEmpty()) ? d.get("value") : ", " + d.get("value");
                            datas.get(key).group = Parse.toString(d.get("group"), "");
                            empty = false;
                        }
                    }
                    if (empty) {
                        datas.get(d.get("group")).value += (datas.get(d.get("group")).value.isEmpty()) ? "0" : ", 0";
                    }
                }
//                int i = 0;
                for (Object key : datas.keySet()) {
                    if (!datasets.isEmpty()) {
                        datasets += ",";
                    }
                    datasets += "\n 	{";
                    if (f.getType().equals("multi")) {
                        datasets += "\n 	type: 'bar',";
                    }
                    datasets += "\n 	label:'" + datas.get(key).group + "',";
                    datasets += "\n 	data:[" + datas.get(key).value + "],";
                    datasets += "\n 	backgroundColor: chartColor('" + datas.get(key).group + "')\n 	}";
                }
            }
            sqlBuilder.append("\n<canvas id=\"").append(f.getId()).append("\"></canvas>");
            sqlBuilder.append("\n<!-- http://www.chartjs.org/docs/ -->");
            sqlBuilder.append("\n<script>");
            sqlBuilder.append("\n var ctx = document.getElementById('").append(f.getId()).append("'); ");
            sqlBuilder.append("\n var ").append(f.getId()).append(" = new Chart(ctx, { ");
            sqlBuilder.append("\n     type: '").append(f.getType()).append("', ");
            sqlBuilder.append("\n     data: { ");
            sqlBuilder.append("\n       labels: [").append(labels).append("], ");
            sqlBuilder.append("\n 	datasets: [ ");
            sqlBuilder.append(datasets);
            sqlBuilder.append("\n] ");
            sqlBuilder.append("\n     }, ");
            sqlBuilder.append("\n     options: { ");
            //inclusão de label de valor fixo no grafico
//          sqlBuilder.append("\n          + "animation: {\n" +
//"                        onComplete: function () {\n" +
//"                            var chartInstance = this.chart;\n" +
//"                            var ctx = chartInstance.ctx;\n" +
//"                            ctx.textAlign = \"center\";\n" +
//"\n" +
//"                            Chart.helpers.each(this.data.datasets.forEach(function (dataset, i) {\n" +
//"                                var meta = chartInstance.controller.getDatasetMeta(i);\n" +
//"                                Chart.helpers.each(meta.data.forEach(function (bar, index) {\n" +
//"                                    ctx.fillText(dataset.data[index], bar._model.x, bar._model.y - 10);\n" +
//"                                }),this)\n" +
//"                            }),this);\n" +
//"                        }\n" +
//"                    },");
            sqlBuilder.append("\n       responsive:true, ");
            sqlBuilder.append("\n       scales: {\n"
                    + "            yAxes: [{\n"
                    + "                ticks: {\n"
                    + "                    beginAtZero:true\n"
                    + "                }\n"
                    + "            }]\n"
                    + "        }, ");
            sqlBuilder.append("\n 	title: { ");
            sqlBuilder.append("\n                 display: true, ");
            sqlBuilder.append("\n                 text: '").append(f.getLabel()).append("' ");
            sqlBuilder.append("\n                 } ");
            sqlBuilder.append("\n             } ");
            sqlBuilder.append("\n     }); ");
            sqlBuilder.append("\n</script>");
            sqlBuilder.append("\n  ");
        }
        return sqlBuilder.toString();
    }
}
