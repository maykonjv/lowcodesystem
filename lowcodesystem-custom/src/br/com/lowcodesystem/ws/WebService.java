/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lowcodesystem.ws;

import br.com.lowcodesystem.dao.Dao;
import br.com.lowcodesystem.util.LogWrite;
import com.sun.net.httpserver.HttpServer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maykon
 */
public class WebService {

    public static List<String> listEndpoints = new ArrayList();
    public static HttpServer httpServer;

    private static List<WS> getServices(Dao dao) {
        List<WS> services = new ArrayList<>();
//        services.add(new WS("calc", new CalculadoraServerImpl(dao)));
        return services;
    }

    public static void startWS(String port, String appName, Dao dao) {
        try {
//            System.out.println("start webservices... port:" + port);
//            LogWrite.info("Starting webservices... port:" + port);
//            httpServer = HttpServer.create(new InetSocketAddress(InetAddress.getByName("0.0.0.0"), Integer.parseInt(port)), 16);
//
//            for (WS ws : getServices(dao)) {
//                final Endpoint e = Endpoint.create(ws.o);
//                e.publish(httpServer.createContext("/" + appName + "/" + ws.name));
//                listEndpoints.add("http://localhost:" + port + "/" + appName + "/" + ws.name);
//            }
//
//            httpServer.start();
        } catch (Exception e) {
            e.printStackTrace();
            LogWrite.error("Error ao iniciar webservices ");
            LogWrite.error(e);
        }
    }

    public static void main(String[] args) {
        try {
//            final HttpServer httpServer = HttpServer.create(new InetSocketAddress(InetAddress.getByName("0.0.0.0"), 18888), 16);
//
//            final Endpoint e = Endpoint.create(new CalculadoraServerImpl(null));
//            e.publish(httpServer.createContext("/Servico/calc"));
//
//            final Endpoint e2 = Endpoint.create(new CalculadoraServerImpl(null));
//            e2.publish(httpServer.createContext("/Servico/calc2"));
//
//            httpServer.start();;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

class WS {

    public String name;
    public Object o;

    public WS(String name, Object o) {
        this.name = name;
        this.o = o;
    }
}
