
package com.dialog.esr.tests.common.webdriver.db;


import com.dialog.esr.tests.common.webdriver.env.Profile;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class PostgreDatabaseHelper extends Database {
    private static final Logger LOG = Logger.getLogger(PostgreDatabaseHelper.class.getName());
    private String dbPrefix = "jdbc:postgresql://";
    private Connection con = null;
    private ResultSet resultSet = null;
    Statement statement;
    Profile profile;

    public void setConnection(String databaseName) {
        profile = new Profile();

        HashMap<String, List<String>> map = profile.getDatabases();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            if (databaseName.equals(entry.getKey())) {
                LOG.info("Setting Database connection to " + entry.getValue().get(1));
                setDbHost(dbPrefix + entry.getValue().get(0) + "/" + entry.getValue().get(1));
                setDbUser(entry.getValue().get(2));
                setDbPassword(entry.getValue().get(3));
            }
        }
        try {
            con = DriverManager.getConnection(getDbHost(), getDbUser(), getDbPassword());
        } catch (SQLException e) {
            LOG.warning("Error occurred while connecting to Database " + e);
        }
    }

    public ResultSet runQuery(String query) {
        try {
            statement = con.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            LOG.info("Error executing query: " + e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    LOG.info("Error closing the connection" + e);
                }
            }

        }
        return resultSet;
    }

    public void closeConnection() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            LOG.info("Error closing the connection : " + e);
        }
    }

}

