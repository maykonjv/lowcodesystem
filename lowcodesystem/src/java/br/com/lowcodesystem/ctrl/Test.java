package br.com.lowcodesystem.ctrl;

import br.com.lowcodesystem.dao.Dao;
import br.com.lowcodesystem.dao.DataSource;
import br.com.lowcodesystem.model.Database;
import br.com.lowcodesystem.util.FormataTexto;
import br.com.lowcodesystem.util.LogWrite;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptException;

/**
 * Features: API Rest (Customizável), controle de perfil, relatórios, graficos,
 * dashboard, download de arquivos, logs, css página/campo Settings: trilha de
 * auditoria,
 *
 * @author Maykon
 */
public class Test {

    public static void main(String[] args) {
        System.out.println(new Timestamp(Calendar.getInstance().getTime().getTime()));
    }
    public static void main2(String[] args) throws FileNotFoundException, ScriptException, NoSuchMethodException {
        try {
            Database database = new Database();
            database.setDriverClass("org.postgresql.Driver");
            database.setJdbcUrl("jdbc:postgresql://localhost:5432/projectx");
            database.setUser("postgres");
            database.setPass("postgres");

            DataSource.database.put("default", database);
            Dao dao = new Dao();

            String sql = "select 1, count(1) as Qtd, idtipovenda as Status from venda where to_char(datagravacao, 'YYYY-MM-DD') ='#{page_rel_vendas_data}' group by idtipovenda order by 2";
            Map<String, String> param = new HashMap<String, String>();
            param.put("page_rel_vendas_data", "2016-07-11");
            sql = FormataTexto.replaceScriptWithValidation(sql, param);
            System.out.println(dao.select("default", sql).dados.get(0));
        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//        String result = ChartBuilder.bar(f, dao, new HashMap<String, String>());
//        System.out.println(result);
//        String script = "var error = ''; function message() {return error;}";
//        script += "function valid() {error = 'Erro na validação do cpf!'; return false;} ";
//        ScriptEngineManager factory = new ScriptEngineManager();
//        ScriptEngine jsEngine = factory.getEngineByName("javascript");
//        jsEngine.put("out", System.out);
//        jsEngine.eval(script);
//        Invocable invocableEngine = (Invocable) jsEngine;
//
////        Object o = invocableEngine.invokeFunction("add", 5, 6);
//        boolean valida = (Boolean) invocableEngine.invokeFunction("valid");
//        Object oMsg = invocableEngine.invokeFunction("message");
//        if (!valida) {
//            System.out.println("msg:" + oMsg);
//        }
//        List l = (List) o;
//        for (Object obj : l) {
//            System.out.println(obj);
//        }
//    }
//    public static void main(String[] args) throws FileNotFoundException, ScriptException, NoSuchMethodException {
//        String script = "function add() {print(\"teste\");}";
//        ScriptEngineManager factory = new ScriptEngineManager();
//        ScriptEngine jsEngine = factory.getEngineByName("javascript");
//        jsEngine.put("out", System.out);
//        jsEngine.eval(script);
//        Invocable invocableEngine = (Invocable) jsEngine;
//
////        Object o = invocableEngine.invokeFunction("add", 5, 6);
//        Object o = invocableEngine.invokeFunction("add");
////        List l = (List) o;
////        for (Object obj : l) {
////            System.out.println(obj);
////        }
//    }
//    public static void main(String[] args) throws FileNotFoundException, ScriptException, NoSuchMethodException {
//        File jsFile = new File("demo.js");
////        File jsFile = new File("demo.txt");
//        ScriptEngineManager factory = new ScriptEngineManager();
//        ScriptEngine jsEngine = factory.getEngineByName("javascript");
//        jsEngine.put("out", System.out);
//        Reader reader = new FileReader(jsFile);
//        jsEngine.eval(reader);
//        Invocable invocableEngine = (Invocable) jsEngine;
//
////        Object o = invocableEngine.invokeFunction("add", 5, 6);
//        Object o = invocableEngine.invokeFunction("add");
//        List l = (List) o;
//        for (Object obj : l) {
//            System.out.println(obj);
//        }
//    }
    // use the classname for the logger, this way you can refactor

    public void doSomeThingAndLog() {
        long begin = LogWrite.begin();
        LogWrite.error("severe error Log");
        LogWrite.warning("warning Log");
        LogWrite.info("Info Log");
        LogWrite.config("Config Log");
        LogWrite.time("Really not important", begin);
    }

//    public static void main(String[] args) {
//        long total = 0;
//        for (int i = 0; i < 100; i++) {
//            long time = System.currentTimeMillis();
//            Test tester = new Test();
//            Log.levels.put(Log.CONFIG, true);
//            Log.levels.put(Log.DEBUG, true);
//            Log.levels.put(Log.ERROR, true);
//            Log.levels.put(Log.INFO, true);
//            Log.levels.put(Log.TIME, true);
//            Log.levels.put(Log.WARNING, true);
//            Log.config("e://", "log", 1000);
//            tester.doSomeThingAndLog();
//            total += System.currentTimeMillis() - time;
//        }
//        System.out.println("TIME: " + (total / 100));
//    }
}
