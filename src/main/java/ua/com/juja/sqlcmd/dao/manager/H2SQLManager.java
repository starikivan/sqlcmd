package ua.com.juja.sqlcmd.dao.manager;

import ua.com.juja.sqlcmd.dao.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseConnection;

import java.sql.*;
import java.util.*;
import java.util.Date;

//@Component
public class H2SQLManager implements DatabaseManager {
    private final String DATABASE_JDBC_DRIVER = "jdbc:h2:mem:";
    private Connection connection;
    private DatabaseConnection databaseConnection;

    @Override
    public DatabaseConnection getDatabaseConnection() {
        return databaseConnection;
    }

    @Override
    public void connect(String server, String port, String databaseName, String userName, String password) {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new DatabaseManagerException("H2 SQL driver not found", e);
        }
        try {
            if (connection != null) {
                connection.close();
            }
            connection = DriverManager.getConnection(DATABASE_JDBC_DRIVER+databaseName,userName, password);
            databaseConnection = new DatabaseConnection();
            databaseConnection.setDatabaseName(databaseName);
            databaseConnection.setUserName(userName);
        } catch (SQLException e) {
            connection = null;
            databaseConnection = null;
            throw new DatabaseManagerException(
                    String.format("Failed to connect to database: %s, user: %s", databaseName, userName), e);
        }
    }

    @Override
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DatabaseManagerException("Failed to disconnect from database", e);
            }
            connection = null;
            databaseConnection = null;
        } else {
            throw new DatabaseManagerException("Disconnect failed. You are not connected to any database.");
        }
    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }

    @Override
    //TODO At start list of databases is empty
    public String currentDatabase() {
        String databaseName = "";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT current_database()")) {
             if (rs.next()) {
                 databaseName = rs.getString(1);
             }
             return rs.getString(1);
        } catch (SQLException e) {
            throw new DatabaseManagerException(
                    String.format("Error getting current database."), e);
        }
    }

    @Override
    public void createDatabase(String databaseName) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(String.format("CREATE DATABASE %s",databaseName));
        } catch (SQLException e) {
            throw new DatabaseManagerException(String.format("Error creating a database '%s'", databaseName), e);
        }
    }

    @Override
    public void dropDatabase(String databaseName) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(String.format("DROP DATABASE IF EXISTS %s", databaseName));
        } catch (SQLException e) {
            throw new DatabaseManagerException(String.format("Error deleting database '%s'", databaseName), e);

        }
    }

    @Override
    public Set<String> getDatabaseNames() {
        Set<String> databases = new LinkedHashSet<>();
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(
                     "SELECT datname AS db_name FROM pg_database WHERE datistemplate = false ORDER BY datname")) {
            while (rs.next()) {
                databases.add(rs.getString("db_name"));
            }
            return databases;
        } catch (SQLException e) {
            throw new DatabaseManagerException("Impossible to get list of databases.", e);
        }
    }

    @Override
    public void createTable(String tableName, String query) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(String.format(" CREATE TABLE %s (%s)", tableName, query));
        } catch (SQLException e) {
            throw new DatabaseManagerException(String.format("Error creating table '%s'. Query: %s",
                    tableName, query));
        }
    }

    @Override
    public void dropTable(String tableName) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(String.format("DROP TABLE IF EXISTS public.%s", tableName));
        } catch (SQLException e) {
            throw new DatabaseManagerException(String.format("It's impossible to drop a table '%s'", tableName), e);
        }
    }

    @Override
    public Set<String> getTableColumns(String tableName) {
        Set<String> columns = new LinkedHashSet<>();
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(
            String.format("SELECT column_name FROM information_schema.columns" +
                    " WHERE  table_name = '%s'", tableName.toUpperCase()))) {
            while ((rs.next())) {
                columns.add(rs.getString("column_name").toLowerCase());
            }


            return columns;
        } catch (SQLException e) {
            throw new DatabaseManagerException(
                    String.format("Error getting table columns from table '%s'", tableName), e);
        }
    }

    @Override
    public List<DataSet> getTableData(String tableName) {
        try (Statement statement = connection.createStatement();
             //TODO tables without id
             ResultSet rs = statement.executeQuery(String.format("SELECT * FROM %s ORDER BY id", tableName))) {
            return getDataSets(rs);
        } catch (SQLException e) {
            throw new DatabaseManagerException(
                    String.format("Error getting data from table '%s'", tableName), e);
        }
    }

    @Override
    public int getTableSize(String tableName) {
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(String.format("SELECT COUNT(*) FROM %s", tableName))) {
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new DatabaseManagerException(
                    String.format("Error getting size of table '%s'", tableName), e);
        }
    }

    @Override
    public Set<String> getTableNames() {
        Set<String> tables = new TreeSet<>();
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(
                     "SELECT table_name FROM information_schema.tables WHERE table_type = 'TABLE'")) {
            while ((rs.next())) {
                tables.add(rs.getString("table_name").toLowerCase());
            }
            return tables;
        } catch (SQLException e) {
            throw new DatabaseManagerException("Error getting table names", e);
        }
    }

    @Override
    public void clearTable(String tableName) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(String.format("DELETE FROM %s", tableName));
        } catch (SQLException e) {
            throw new DatabaseManagerException(
                    String.format("Error deleting data from table '%s'", tableName), e);
        }
    }

    @Override
    public boolean existRecord(String tableName, String field, String parameter) {
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(String.format(
                     "SELECT COUNT(*) FROM %s WHERE %s = %s", tableName, field, parameter))) {
            rs.next();
            int size = rs.getInt(1);
            return size != 0;
        } catch (SQLException e) {
            throw new DatabaseManagerException(
                    String.format("Error getting record from table '%s' where %s = %s",
                            tableName, field, parameter), e);
        }
    }

    @Override
    public DataSet getRecordData(String tableName, int id) {
        DataSet dataSet = new DataSet();
        try (Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(String.format(
                     "SELECT * FROM %s WHERE id = %d", tableName, id))) {
            ResultSetMetaData rsmd = rs.getMetaData();
            if (rs.next()) {
                for (int index = 1; index <= rsmd.getColumnCount(); index++) {
                    dataSet.put(rsmd.getColumnName(index), rs.getObject(index));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseManagerException(
                    String.format("Error getting record from table '%s' where id = %s",
                            tableName, id), e);
        }
        return dataSet;
    }

    @Override
    public void createRecord(String tableName, DataSet input) {
        try (Statement statement = connection.createStatement()) {
            String columns = "";
            for (String name : input.getNames()) {
                columns = columns + name + ",";
            }
            columns = columns.substring(0, columns.length() - 1);
            String values = "";
            for (Object value : input.getValues()) {
                values += "'" + value.toString() + "',";
            }
            values = values.substring(0, values.length() - 1);
            statement.executeUpdate(String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columns, values));
        } catch (SQLException e) {
            throw new DatabaseManagerException(
                    String.format("Error creating record in table '%s'. Input: %s", tableName, input), e);
        }
    }

    @Override
    public void updateRecord(String tableName, int id, DataSet input) {
        String fields = "";
        for (String name : input.getNames()) {
            fields += String.format("%s =? ", name) + ",";
        }
        fields = fields.substring(0, fields.length() - 1);
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                String.format("UPDATE %s SET %s WHERE id=?", tableName, fields))) {
            int index = 1;
            for (Object value : input.getValues()) {
                preparedStatement.setObject(index++, value);
            }
            preparedStatement.setInt(index, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseManagerException(
                    String.format("Error update record in table '%s' where id = %d, input: %s", tableName, id, input), e);
        }
    }

    @Override
    public void deleteRecord(String tableName, int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                String.format("DELETE FROM %s WHERE id=?", tableName))) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseManagerException(
                    String.format("Error deleting data from table '%s' where id = %d", tableName, id), e);
        }
    }

    @Override
    public List<DataSet> executeQuery(String query) {
        if (query.toLowerCase().startsWith("select")) {
            try (Statement statement = connection.createStatement();
                 ResultSet rs = statement.executeQuery(query)) {
                return getDataSets(rs);
            } catch (SQLException e) {
                throw new DatabaseManagerException(
                        String.format("Error execute query '%s'", query),e);
            }
        } else {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(query);
                return new ArrayList<>();
            } catch (SQLException e) {
                throw new DatabaseManagerException(
                        String.format("Error execute query '%s'", query), e);
            }
        }
    }

    @Override
    public void createAction(Date date, String userName, String dbName, String action) {

    }

    private List<DataSet> getDataSets(ResultSet rs) {
        List<DataSet> result = new ArrayList<>();
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            while ((rs.next())) {
                DataSet dataSet = new DataSet();
                for (int index = 1; index <= rsmd.getColumnCount(); index++) {
                    //TODO h2 columns names is Upper case
                    dataSet.put(rsmd.getColumnName(index).toLowerCase(), rs.getObject(index));
                }
                result.add(dataSet);
            }
        } catch (SQLException e) {
            throw new DatabaseManagerException("Error getting dataSets", e);
        }
        return result;
    }
}
