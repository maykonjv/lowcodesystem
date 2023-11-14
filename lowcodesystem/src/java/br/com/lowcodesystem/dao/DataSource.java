package br.com.lowcodesystem.dao;

import br.com.lowcodesystem.model.Database;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.util.HashMap;
import java.util.Map;

public class DataSource {

    private static Map<String, DataSource> datasource = new HashMap<String, DataSource>();
    private ComboPooledDataSource cpds;
    public static Map<String, Database> database = new HashMap<String, Database>();

    private DataSource(String ds) throws IOException, SQLException, PropertyVetoException, Exception {
        if (database.get(ds) == null || database.get(ds).getDriverClass().isEmpty() || database.get(ds).getJdbcUrl().isEmpty() || database.get(ds).getUser().isEmpty() || database.get(ds).getPass().isEmpty()) {
            throw new Exception("Database não configurado para o datasource: " + ds);
        }
        cpds = new ComboPooledDataSource();
        cpds.setDriverClass(database.get(ds).getDriverClass()); //loads the jdbc driver
        cpds.setJdbcUrl(database.get(ds).getJdbcUrl());
        cpds.setUser(database.get(ds).getUser());
        cpds.setPassword(database.get(ds).getPass());

        // the settings below are optional -- c3p0 can work with defaults
        if (database.get(ds).getMinPoolSize() != null) {
            cpds.setMinPoolSize(database.get(ds).getMinPoolSize());
        }
        if (database.get(ds).getMaxPoolSize() != null) {
            cpds.setMaxPoolSize(database.get(ds).getMaxPoolSize());
        }
        if (database.get(ds).getMaxStatements() != null) {
            cpds.setMaxStatements(database.get(ds).getMaxStatements());
        }
        if (database.get(ds).getIdleTestPeriod() != null) {
            cpds.setIdleConnectionTestPeriod(database.get(ds).getIdleTestPeriod());
        }
        if (database.get(ds).getTimeout() != null) {
            cpds.setMaxIdleTime(database.get(ds).getTimeout());
        }

    }

    public static DataSource getInstance(String ds) throws IOException, SQLException, PropertyVetoException, Exception {
        if (datasource.get(ds) == null) {
            datasource.put(ds, new DataSource(ds));
            return datasource.get(ds);
        } else {
            return datasource.get(ds);
        }
    }

    public static void refresh() throws IOException, SQLException, PropertyVetoException, Exception {
        for (String key : database.keySet()) {
            if (datasource.get(key) != null && datasource.get(key).cpds != null) {
                datasource.get(key).cpds.close();
            }
            datasource.put(key, new DataSource(key));
        }
    }

    public String showStatus() throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("<br/>NumConnectionsDefaultUser: ").append(cpds.getNumConnectionsDefaultUser());
        sb.append("<br/>NumBusyConnectionsDefaultUser: ").append(cpds.getNumBusyConnectionsDefaultUser());
        sb.append("<br/>NumIdleConnectionsDefaultUser: ").append(cpds.getNumIdleConnectionsDefaultUser());
        sb.append("<br/>NumBusyConnectionsAllUsers: ").append(cpds.getNumBusyConnectionsAllUsers());
        sb.append("<br/>NumConnectionsAllUsers: ").append(cpds.getNumConnectionsAllUsers());
        sb.append("<br/>NumUnclosedOrphanedConnections: ").append(cpds.getNumUnclosedOrphanedConnections());
        return sb.toString();
    }

    public Connection getConnection() throws SQLException {
        return this.cpds.getConnection();
    }

}
