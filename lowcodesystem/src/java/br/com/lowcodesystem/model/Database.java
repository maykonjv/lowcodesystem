package br.com.lowcodesystem.model;

import java.io.Serializable;

/**
 *
 * @author Maykon
 */
public class Database implements Serializable {

    private String database;
    private String driverClass;
    private String jdbcUrl;
    private String user;
    private String pass;
    private Integer minPoolSize = 3;
    private Integer maxPoolSize = 20;
    private Integer maxStatements = 50;
    private Integer timeout = 1800;
    private Integer idleTestPeriod = 3000;
    private String sqlShowTables; //SELECT table_schema || '.' || table_name FROM information_schema.tables WHERE table_type = 'BASE TABLE' AND table_schema NOT IN ('pg_catalog', 'information_schema');
    private String sqlShowColumns; //SELECT column_name, data_type || '(' || coalesce(character_maximum_length, 0) || ')' as type FROM information_schema.columns WHERE table_schema = 'public' AND table_name   = 'usuario';

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getDriverClass() {
        return driverClass == null ? "" : driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getJdbcUrl() {
        return jdbcUrl == null ? "" : jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getUser() {
        return user == null ? "" : user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass == null ? "" : pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Integer getMinPoolSize() {
        return minPoolSize;
    }

    public void setMinPoolSize(Integer minPoolSize) {
        this.minPoolSize = minPoolSize;
    }

    public Integer getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(Integer maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public Integer getMaxStatements() {
        return maxStatements;
    }

    public void setMaxStatements(Integer maxStatements) {
        this.maxStatements = maxStatements;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getIdleTestPeriod() {
        return idleTestPeriod;
    }

    public void setIdleTestPeriod(Integer idleTestPeriod) {
        this.idleTestPeriod = idleTestPeriod;
    }

    public String getSqlShowTables() {
        return sqlShowTables;
    }

    public void setSqlShowTables(String sqlShowTables) {
        this.sqlShowTables = sqlShowTables;
    }

    public String getSqlShowColumns() {
        return sqlShowColumns;
    }

    public void setSqlShowColumns(String sqlShowColumns) {
        this.sqlShowColumns = sqlShowColumns;
    }

}
