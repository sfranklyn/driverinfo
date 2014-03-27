/*
 * Copyright 2014 Samuel Franklyn <sfranklyn at gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gamya.driverinfo.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Samuel Franklyn <sfranklyn at gmail.com>
 */
public class LogIn {

    private Connection con;
    private String message;

    public Connection getCon() {
        return con;
    }

    public String getMessage() {
        return message;
    }

    public Connection logIn(String system, String database, String user, String password) {
        con = null;
        message = "";

        if (system.isEmpty()) {
            message = "System must not blank";
            return con;
        }

        if (database.isEmpty()) {
            message = "Database must not blank";
            return con;
        }

        if (user.isEmpty()) {
            message = "User must not blank";
            return con;
        }

        if (password.isEmpty()) {
            message = "Password must not blank";
            return con;
        }

        if (message.isEmpty()) {
            String jdbcUrl = "jdbc:as400://" + system + "/" + database;
            try {
                Class.forName("com.ibm.as400.access.AS400JDBCDriver");
                con = DriverManager.getConnection(jdbcUrl, user, password);
            } catch (ClassNotFoundException | SQLException ex) {
                message = ex.getMessage();
                return con;
            }
        }

        return con;
    }

}
