/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.api.services.samples.youtube.cmdline.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Qih
 */
public class ConnectionFactory {
        public Connection getConnection() throws ClassNotFoundException{
            try{
                Class.forName("org.postgresql.Driver");
                return DriverManager.getConnection(
					"jdbc:postgresql://127.0.0.1:5432/DBLP", "postgre",
					"postgre");
                //Class.forName("com.mysql.jdbc.Driver");
                //return DriverManager.getConnection("jdbc:mysql://localhost/tcc", "root", "");
            }catch(SQLException e){
                throw new RuntimeException(e);
            }
        }
        
    
}