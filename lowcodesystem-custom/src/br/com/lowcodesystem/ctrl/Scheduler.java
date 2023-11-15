/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lowcodesystem.ctrl;

import br.com.lowcodesystem.dao.Dao;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 *
 * @author Maykon
 */
public class Scheduler {

    public static Set<String> listThreadNames = new HashSet<String>();
    public static boolean rotinasAgendadasRunning;
    public static boolean rotinasContinuasRunning;

    private static Dao dao;
    private static Properties properties;

    public Scheduler(Dao dao, Properties properties) {
        Scheduler.dao = dao;
        setProperties(properties);
    }

    public static void setProperties(Properties properties) {
        Scheduler.properties = properties;
    }

    public static void setDao(Dao dao) {
        Scheduler.dao = dao;
    }

    private static void startThread() {

        try {
            if (!rotinasAgendadasRunning) {
                Thread t = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        rotinasAgendadasRunning = true;
                        while (rotinasAgendadasRunning) {
                            try {
                                Scheduler.run(new SimpleDateFormat("HH:mm").format(new Date()));
                                Thread.sleep(60000);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                });
                t.setName("@@->THREAD LowcodeSystem - rotinas agendadas");
                t.start();
            }

        } catch (Exception e) {
            rotinasAgendadasRunning = false;
            e.printStackTrace();
        }
    }

    private static void run(String horaMin) {
        if (horaMin.equals("00:00")) {
        }
    }

}
