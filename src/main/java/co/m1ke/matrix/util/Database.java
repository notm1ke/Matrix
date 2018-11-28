package co.m1ke.matrix.util;

import co.m1ke.matrix.logging.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Database {

    public static class Preferences {

        private String database;
        private String databaseHost;
        private String databasePort;
        private String databaseUser;
        private String databasePassword;

        public Preferences(String database, String databaseHost, String databasePort, String databaseUser, String databasePassword) {
            this.database = database;
            this.databaseHost = databaseHost;
            this.databasePort = databasePort;
            this.databaseUser = databaseUser;
            this.databasePassword = databasePassword;
        }

        public String getDatabase() {
            return database;
        }

        public void setDatabase(String database) {
            this.database = database;
        }

        public String getDatabaseHost() {
            return databaseHost;
        }

        public void setDatabaseHost(String databaseHost) {
            this.databaseHost = databaseHost;
        }

        public String getDatabasePort() {
            return databasePort;
        }

        public void setDatabasePort(String databasePort) {
            this.databasePort = databasePort;
        }

        public String getDatabaseUser() {
            return databaseUser;
        }

        public void setDatabaseUser(String databaseUser) {
            this.databaseUser = databaseUser;
        }

        public String getDatabasePassword() {
            return databasePassword;
        }

        public void setDatabasePassword(String databasePassword) {
            this.databasePassword = databasePassword;
        }
    }

    private Connection connection;
    private Logger logger;
    private Preferences preferences;

    public Database(Preferences preferences) {
        this.preferences = preferences;
        this.logger = new Logger("Database");
        if (!isConnected()) {
            connect();
        }
    }

    public Connection connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:mysql://" + preferences.getDatabaseHost() + ":" + preferences.getDatabasePort() + "/" + preferences.getDatabase() + "?autoReconnect=true", preferences.getDatabaseUser(), preferences.getDatabasePassword());
            logger.info("Connected to the database.");
        } catch (Exception e) {
            logger.severe("Error connecting to the database.");
            System.exit(-1);
        }
        return null;
    }

    public boolean disconnect() {
        try {
            connection.close();
            logger.info("Disconnected from the database.");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isConnected() {
        return connection != null;
    }

    public Connection getConnection() {
        return connection;
    }

    public static int queries = 0;
    public static int updates = 0;

    public ResultSet query(String sql) {
        queries++;
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<ResultSet> fut = service.submit(() -> {
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery();
                service.shutdown();
                return rs;
            } catch (SQLException e) {
                logger.warning("Error executing query: " + e.getMessage() + " (" + e.getClass().getName() + ")");
                service.shutdown();
                return null;
            }
        });
        try {
            service.awaitTermination(1, TimeUnit.MINUTES);
            return fut.get();
        } catch (InterruptedException | ExecutionException ignored) {}
        return null;
    }

    public boolean update(String sql) {
        updates++;
        ExecutorService service = Executors.newCachedThreadPool();
        service.submit(() -> {
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.executeUpdate();
                statement.close();
            } catch (Exception e) {
                logger.warning("Error executing update: " + e.getMessage() + " (" + e.getClass().getName() + ")");
            }
        });
        return true;
    }

    public static void close(ResultSet rs) {
        queries--;
        try {
            if (rs.isClosed()) {
                return;
            }
            Statement statement = rs.getStatement();
            if (rs != null) rs.close();
            if (statement != null) statement.close();
        } catch (Exception e) {
        }
    }

    public static int getQueries() {
        return queries;
    }

    public static int getUpdates() {
        return updates;
    }

}
