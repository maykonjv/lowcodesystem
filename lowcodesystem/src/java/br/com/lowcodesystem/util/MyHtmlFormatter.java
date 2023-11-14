package br.com.lowcodesystem.util;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package br.com.mjv.template.util;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.logging.Formatter;
//import java.util.logging.Handler;
//import java.util.logging.Level;
//import java.util.logging.LogRecord;
//
///**
// *
// * @author Maykon
// */
//public class MyHtmlFormatter extends Formatter {
//
//    // this method is called for every log records
//    public String format(LogRecord rec) {
//        StringBuffer buf = new StringBuffer(1000);
//        buf.append("<tr>\n");
//        buf.append("\t<td>");
//        buf.append(rec.getThreadID());
//        buf.append("</td>\n");
//        if (rec.getLevel().intValue() == Level.SEVERE.intValue()) {
//            buf.append("\t<td style=\"color:red\">");
//            buf.append("<b title='").append(rec.getThrown()).append("'>");
//            buf.append(rec.getLevel());
//            buf.append("</b>");
//        } else if (rec.getLevel().intValue() == Level.WARNING.intValue()) {
//            buf.append("\t<td style=\"color:orange\">");
//            buf.append("<b>");
//            buf.append(rec.getLevel());
//            buf.append("</b>");
//        } else if (rec.getLevel().intValue() == Level.FINE.intValue()) {
//            buf.append("\t<td style=\"color:green\">");
//            buf.append("<b>");
//            buf.append(rec.getLevel());
//            buf.append("</b>");
//        } else {
//            buf.append("\t<td>");
//            buf.append(rec.getLevel());
//        }
//
//        buf.append("</td>\n");
//        buf.append("\t<td>");
//        buf.append(calcDate(rec.getMillis()));
//        buf.append("</td>\n");
//        buf.append("\t<td>");
//        buf.append(formatMessage(rec));
//        if (rec.getThrown() != null) {
//            buf.append("<br><span style='color:red'>" + rec.getThrown().getMessage() + "<span><br>");
//            for (int i = 0; i < rec.getThrown().getStackTrace().length; i++) {
//                buf.append(rec.getThrown().getStackTrace()[i] + "<br>");
//            }
//        }
//        buf.append("</td>\n");
//        buf.append("</tr>\n");
//
//        return buf.toString();
//    }
//
//    private String calcDate(long millisecs) {
//        SimpleDateFormat date_format = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss SSS");
//        Date resultdate = new Date(millisecs);
//        return date_format.format(resultdate);
//    }
//
//    // this method is called just after the handler using this
//    // formatter is created
//    public String getHead(Handler h) {
//        return "<!DOCTYPE html>\n<head>\n<style>\n"
//                + "table { width: 100% }\n"
//                + "th { font:bold 10pt Tahoma; }\n"
//                + "td { font:normal 8pt Tahoma; }\n"
//                + "h1 {font:normal 11pt Tahoma;}\n"
//                + "</style>\n"
//                + "<script src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js\"></script>\n"
//                + "</head>\n"
//                + "<body>\n"
//                + "<h1>" + (new Date().toLocaleString()) + "</h1>\n"
//                + "<input type=\"text\" id=\"search\" placeholder=\"Filtrar\">\n"
//                + "<table border=\"0\" cellpadding=\"5\" cellspacing=\"3\" id=\"table\">\n"
//                + "<tr align=\"left\">\n"
//                + "\t<th style=\"width:1%\">Th</th>\n"
//                + "\t<th style=\"width:5%\">Level</th>\n"
//                + "\t<th style=\"width:11%\">Time</th>\n"
//                + "\t<th style=\"width:83%\">Log Message</th>\n"
//                + "</tr>\n";
//    }
//
//    // this method is called just after the handler using this
//    // formatter is closed
//    public String getTail(Handler h) {
//        return "</table>\n"
//                + "<script type=\"text/javascript\">\n"
//                + "var $rows = $('#table tr');\n"
//                + "$('#search').keyup(function() {\n"
//                + "    var val = $.trim($(this).val()).replace(/ +/g, ' ').toLowerCase();\n"
//                + "    \n"
//                + "    $rows.show().filter(function() {\n"
//                + "        var text = $(this).text().replace(/\\s+/g, ' ').toLowerCase();\n"
//                + "        return !~text.indexOf(val);\n"
//                + "    }).hide();\n"
//                + "});"
//                + "</script>\n"
//                + "</body>\n</html>";
//    }
//}
