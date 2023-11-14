package br.com.lowcodesystem.dao;

import br.com.lowcodesystem.model.Database;
import br.com.lowcodesystem.util.FormataTexto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Maykon
 */
public class Dao {

    public ResultSQL select(String sql, Object... params) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ResultSQL result = new ResultSQL();
        try {
            connection = DataSource.getInstance().getConnection();

            statement = connection.prepareStatement(sql.trim());
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    statement.setObject(i + 1, params[i]);
                }
            }
            resultSet = statement.executeQuery();

            ResultSetMetaData metaData = resultSet.getMetaData();
            int numcols = metaData.getColumnCount();
            for (int i = 1; i <= numcols; i++) {
                result.column.add(new Column(metaData.getColumnName(i), metaData.getColumnType(i)));
            }

            while (resultSet.next()) {
                Map<String, Object> object = new HashMap<String, Object>();
                for (int i = 1; i <= numcols; i++) {
                    object.put(metaData.getColumnName(i), resultSet.getObject(metaData.getColumnName(i)));
                }
                result.dados.add(object);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            String error = "<strong>DATABASE ERROR: </strong>" + ex.getErrorCode() + " - " + ex.getMessage() + "<br/>";
            if (ex.getSQLState() != null) {
                error += ex.getSQLState();
            }
            Throwable cause = ex.getCause();
            while (cause != null) {
                error += cause.getMessage() + "<br>";
                cause = cause.getCause();
            }
            throw new Exception(error);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public int insertDeletUpdate(String sql, Object... params) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        int result = 0;
        try {
            connection = DataSource.getInstance().getConnection();

            statement = connection.prepareStatement(sql.trim());
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    statement.setObject(i + 1, params[i]);
                }
            }
            result = statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            String error = "<strong>DATABASE ERROR: </strong>" + ex.getErrorCode() + " - " + ex.getMessage() + "<br/>";
            if (ex.getSQLState() != null) {
                error += ex.getSQLState();
            }
            Throwable cause = ex.getCause();
            while (cause != null) {
                error += cause.getMessage() + "<br>";
                cause = cause.getCause();
            }
            throw new Exception(error);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        try {
            DataSource.database = new Database();
            DataSource.database.setDriverClass("org.postgresql.Driver");
            DataSource.database.setJdbcUrl("jdbc:postgresql://localhost:5432/projectx");
            DataSource.database.setUser("postgres");
            DataSource.database.setPass("postgres");

            Dao dao = new Dao();
//            int i = dao.insertDeletUpdate("delete from usuario where id=?", 1);
//            System.out.println("Registros deletados:" + i);
//            i = dao.insertDeletUpdate("insert into usuario (id, login, senha) values (?, ?, ?)", 1, "maykon", "123");
//            System.out.println("Registros inseridos:" + i);
//            i = dao.insertDeletUpdate("update usuario set login=?, senha=? where id=?", "test", "202cb962ac59075b964b07152d234b70", 1);
//            System.out.println("Registros alterados:" + i);
//            List list = dao.select("select * from usuario").dados;
//            for (Object o : list) {
//                System.out.println(o);
//            }
            String param = "'\' or '='; --";
//            String param = "CASADO'";
            String sql = "select * from estado_civil where descricao like '" + FormataTexto.addslashes(param) + "'";
            ResultSQL r = dao.select(sql);
            System.out.println(r.dados);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
