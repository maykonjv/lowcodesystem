/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lowcodesystem.dao;

import br.com.lowcodesystem.model.Database;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSource {

    private static DataSource datasource;
    private ComboPooledDataSource cpds;
    public static Database database;

    private DataSource() throws IOException, SQLException, PropertyVetoException, Exception {
        if (database == null || database.getDriverClass().isEmpty() || database.getJdbcUrl().isEmpty() || database.getUser().isEmpty() || database.getPass().isEmpty()) {
            throw new Exception("Database não configurado.");
        }
        cpds = new ComboPooledDataSource();
        cpds.setDriverClass(database.getDriverClass()); //loads the jdbc driver
        cpds.setJdbcUrl(database.getJdbcUrl());
        cpds.setUser(database.getUser());
        cpds.setPassword(database.getPass());

        // the settings below are optional -- c3p0 can work with defaults
        if (database.getMinPoolSize() != null) {
            cpds.setMinPoolSize(database.getMinPoolSize());
        }
        if (database.getMaxPoolSize() != null) {
            cpds.setMaxPoolSize(database.getMaxPoolSize());
        }
        if (database.getMaxStatements() != null) {
            cpds.setMaxStatements(database.getMaxStatements());
        }
        if (database.getIdleTestPeriod() != null) {
            cpds.setIdleConnectionTestPeriod(database.getIdleTestPeriod());
        }
        if (database.getTimeout() != null) {
            cpds.setMaxIdleTime(database.getTimeout());
        }

    }

    public static DataSource getInstance() throws IOException, SQLException, PropertyVetoException, Exception {
        if (datasource == null) {
            datasource = new DataSource();
            return datasource;
        } else {
            return datasource;
        }
    }

    public void refresh() throws IOException, SQLException, PropertyVetoException, Exception {
        if (cpds != null) {
            cpds.close();
        }
        datasource = new DataSource();
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
