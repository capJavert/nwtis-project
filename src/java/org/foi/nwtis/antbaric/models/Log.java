/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.antbaric.models;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author javert
 */
public class Log extends Model {

    private Integer id;
    private String korisnik;
    private String url;
    private String ipadresa;
    private Integer trajanje;
    private Integer status;

    public Log() {
        super();

        this.tableName = "dnevnik";
    }
    
    public void write(String user, String url, String ip, Integer trajanje, Integer status) throws SQLException {
        this.korisnik = user;
        this.url = url;
        this.ipadresa = ip;
        this.trajanje = trajanje;
        this.status = status;
        
        this.save();
    }
    
    @Override
    Integer getPrimaryKey() {
        return this.id;
    }

    @Override
    Model mapResult(ResultSet result, Boolean batch) throws SQLException {
          if (!batch) {
            result.next();
        }

        User model = new User();

        for (Field field : User.class.getFields()) {
            try {
                field.set(model, result.getObject(field.getName(), field.getType()));
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return model;
    }

    @Override
    void setCreateStatement() throws SQLException {
        this.preparedStatement = this.connection.prepareStatement("INSERT INTO " + this.tableName + " VALUES (? ? ? ? ? ?)");

        this.preparedStatement.setString(1, this.korisnik);
        this.preparedStatement.setString(2, this.url);
        this.preparedStatement.setString(3, this.ipadresa);
        this.preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
        this.preparedStatement.setInt(5, this.trajanje);
        this.preparedStatement.setInt(6, this.status);
    }

    @Override
    void setUpdateStatement() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
