/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.antbaric.models;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author javert
 */
public class Log extends Model<Log> {

    private Integer id;
    public String korisnik;
    public String data;
    public String ipadresa;
    public Long trajanje;
    public String vrijeme;

    public Log() {
        super();

        this.tableName = "dnevnik";
    }

    public String getVrijeme() {
        return vrijeme;
    }

    public String getKorisnik() {
        return korisnik;
    }

    public String getData() {
        return data;
    }

    public String getIpadresa() {
        return ipadresa;
    }

    public Long getTrajanje() {
        return trajanje;
    }

    public Boolean write(String user, String command, String ip, Long trajanje) throws SQLException {
        this.korisnik = user;
        this.data = command;
        this.ipadresa = ip;
        this.trajanje = trajanje;

        return this.create();
    }

    @Override
    public Integer getPrimaryKey() {
        return this.id;
    }

    @Override
    Log mapResult(ResultSet result, Boolean batch) throws SQLException {
        if (!batch) {
            result.next();
        }

        Log model = new Log();

        for (Field field : Log.class.getFields()) {
            try {
                field.set(model, result.getObject(field.getName(), field.getType()));
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        model.id = result.getInt("id");

        return model;
    }

    @Override
    void setCreateStatement() throws SQLException {
        String fields = "";
        String values = "";

        for (Field field : Log.class.getFields()) {
            try {
                if (!fields.isEmpty()) {
                    fields += ("," + field.getName());
                    values += ",?";
                } else {
                    fields += field.getName();
                    values += "?";
                }

            } catch (IllegalArgumentException ex) {
                Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        this.preparedStatement = this.connection.prepareStatement("INSERT INTO " + this.tableName + " (" + fields + ") VALUES (" + values + ")");

        int i = 1;
        for (Field field : Log.class.getFields()) {
            try {
                this.preparedStatement.setObject(i++, field.get(this));
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    void setUpdateStatement() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
