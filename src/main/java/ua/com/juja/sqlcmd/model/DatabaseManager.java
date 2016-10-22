package ua.com.juja.sqlcmd.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface DatabaseManager {
    void connect(String database, String userName, String password) throws SQLException;

    List<DataSet> getTableData(String tableName) throws SQLException;

    int getSize(String tableName) throws SQLException;

    Set<String> getTableNames() throws SQLException;

    void clear(String tableName) throws SQLException;

    void create(String tableName, DataSet input) throws SQLException;

    void update(String tableName, int id, DataSet input) throws SQLException;

    Set<String> getTableColumns(String tableName) throws SQLException;

    boolean isConnected();
}
