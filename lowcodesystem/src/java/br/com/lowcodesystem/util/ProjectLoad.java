/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lowcodesystem.util;

import br.com.lowcodesystem.ctrl.Builder;
import static br.com.lowcodesystem.ctrl.Builder.SESSION_DATABASE;
import static br.com.lowcodesystem.ctrl.Builder.SESSION_FORM;
import static br.com.lowcodesystem.ctrl.Builder.SESSION_MENU;
import static br.com.lowcodesystem.ctrl.Builder.SESSION_PROJECT;
import br.com.lowcodesystem.ctrl.Render;
import static br.com.lowcodesystem.ctrl.Render.menus;
import static br.com.lowcodesystem.ctrl.Render.pages;
import static br.com.lowcodesystem.ctrl.Render.project;
import br.com.lowcodesystem.dao.DataSource;
import br.com.lowcodesystem.model.ApiUser;
import br.com.lowcodesystem.model.Database;
import br.com.lowcodesystem.model.Menu;
import br.com.lowcodesystem.model.Page;
import br.com.lowcodesystem.model.Profile;
import br.com.lowcodesystem.model.Project;
import static br.com.lowcodesystem.util.ManterXML.pasta;
import br.com.lowcodesystem.ctrl.Scheduler;
import static br.com.lowcodesystem.ctrl.Scheduler.listThreadNames;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.servlet.ServletContext;

/**
 *
 * @author Maykon
 */
public class ProjectLoad {

    public static boolean rotinasAgendadasRunning;
    public static Properties properties = new Properties();

    public static void load(String fileName) {
        try {
            System.out.println("Project loading... (" + fileName + ")");
            menus = (List<Menu>) ManterXML.readXML(SESSION_MENU, fileName);
            pages = (List<Page>) ManterXML.readXML(SESSION_FORM, fileName);
            Render.profiles = (List<Profile>) ManterXML.readXML(Builder.SESSION_PROFILE, fileName);
            Render.apiUsers = (List<ApiUser>) ManterXML.readXML(Builder.SESSION_API_USER, fileName);
            project = (Project) ManterXML.readXML(SESSION_PROJECT, fileName);
            DataSource.database = (Map<String, Database>) ManterXML.readXML(SESSION_DATABASE, fileName);
            if (menus == null || menus.isEmpty()) {
                menus = new ArrayList<Menu>();
                Menu menu = new Menu();
                menu.setIcon("fa fa-tachometer");
                menu.setId("menu_dashboard");
                menu.setLabel("Dashboard");
                menu.setPage("page_dashboard");
                menus.add(menu);
            }
            if (Render.profiles == null || Render.profiles.isEmpty()) {
                Render.profiles = new ArrayList<Profile>();
                Render.profiles.add(new Profile(Render.ADMINISTRADOR));
            }
            if (Render.apiUsers == null) {
                Render.apiUsers = new ArrayList<ApiUser>();
            }
            if (pages == null) {
                pages = new ArrayList<Page>();
            }
            if (project == null) {
                project = new Project();
                project.setProjectName("LowcodeSystem"); // TODO pegar o nome do projeto
            }
            if (project.getProjectVersion() == null || project.getProjectVersion().isEmpty()) {
                project.setProjectVersion("1.0.0");
            }
            if (project.getLogPath() == null || project.getLogPath().isEmpty()) {
                if (pasta != null && !pasta.endsWith(File.separator)) {
                    pasta += File.separator;
                }
                Render.project.setLogPath(pasta + "_log");
                Log.config(project.getLogPath(), fileName, (1024 * 1024 * 5));
            }
            if (DataSource.database == null) {
                DataSource.database = new HashMap<String, Database>();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void writeProperties() {
        try {
            File file = new File(pasta + "schema" + File.separator + "config.properties");
            FileOutputStream fileOut = new FileOutputStream(file);
            properties.store(fileOut, "Configuracoes do projeto " + Render.project.getProjectName());
            fileOut.close();
            Scheduler.setProperties(properties);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadProperties(ServletContext servletContext) {
        try {
            if (pasta == null) {
                return;
            }
            File file = new File(pasta + "schema" + File.separator + "config.properties");
            System.out.println("Load Properties: " + file.getAbsolutePath());
            if (!file.exists()) {
                file.createNewFile();
            }
            FileInputStream fileInput = new FileInputStream(file);
            properties.load(fileInput);
            fileInput.close();

            Enumeration<String> attrsApp = servletContext.getAttributeNames();
            while (attrsApp.hasMoreElements()) {
                String key = (String) attrsApp.nextElement();
                if (!key.contains(".")) {
                    servletContext.removeAttribute(key);
                }
            }

            Enumeration enuKeys = properties.keys();
            while (enuKeys.hasMoreElements()) {
                String key = (String) enuKeys.nextElement();
                String value = properties.getProperty(key);
                if (!key.contains(".")) {
                    Log.debug("ADD configs attributes: " + key + ": " + value);
                    servletContext.setAttribute(key, value);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void startThread() {

        try {
            if (!rotinasAgendadasRunning) {
                Thread t = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        rotinasAgendadasRunning = true;
                        while (rotinasAgendadasRunning) {
                            try {
                                if (new SimpleDateFormat("HH:mm").format(new Date()).equals("00:00")) {
                                    File temp = new File(Render.project.getPathUploadFiles() + File.separator + "temp");
                                    for (File file : temp.listFiles()) {
                                        file.delete();
                                    }
                                }
                                Thread.sleep(60000);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                });
                t.setName("@@->THREAD LowcodeSystem - limpar temp (0:00)");
                listThreadNames.add(t.getName());
                t.start();
            }

        } catch (Exception e) {
            rotinasAgendadasRunning = false;
            e.printStackTrace();
        }
    }
}
