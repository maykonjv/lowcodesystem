package br.com.lowcodesystem.util;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package br.com.mjv.template.util;
//
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.logging.ConsoleHandler;
//import java.util.logging.FileHandler;
//import java.util.logging.Formatter;
//import java.util.logging.Handler;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import java.util.logging.SimpleFormatter;
//import java.util.logging.XMLFormatter;
//
///**
// *
// * @author Maykon
// */
//public class MyLogger {
//
//    static private FileHandler fileTxt;
//    static private SimpleFormatter formatterTxt;
//
//    static private FileHandler fileXML;
//    static private XMLFormatter formatterXML;
//
//    static private FileHandler fileHTML;
//    static private Formatter formatterHTML;
//    //limit size file = 10 MB
//    public static final int FILE_SIZE = 1024 * 1024 * 10;
//
//    static public void setup(String projectName) throws IOException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        // get the global logger to configure it
//        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
//
//        // suppress the logging output to the console
//        Logger rootLogger = Logger.getLogger("");
//        Handler[] handlers = rootLogger.getHandlers();
//        if (handlers[0] instanceof ConsoleHandler) {
//            rootLogger.removeHandler(handlers[0]);
//        }
//
//        logger.setLevel(Level.INFO);
//        fileTxt = new FileHandler("log-" + projectName + "-" + sdf.format(new Date()) + ".txt", true);
//        fileXML = new FileHandler("log-" + projectName + "-" + sdf.format(new Date()) + ".xml", true);
//        fileHTML = new FileHandler("log-" + projectName + "-" + sdf.format(new Date()) + ".html", FILE_SIZE, 10, true);
//
//        // create a TXT formatter
//        formatterTxt = new SimpleFormatter();
//        fileTxt.setFormatter(formatterTxt);
//        logger.addHandler(fileTxt);
//
//        // create a XML formatter
//        formatterXML = new XMLFormatter();
//        fileXML.setFormatter(formatterXML);
//        logger.addHandler(fileXML);
//
//        // create an HTML formatter
//        formatterHTML = new MyHtmlFormatter();
//        fileHTML.setFormatter(formatterHTML);
//        logger.addHandler(fileHTML);
//    }
//
//}
