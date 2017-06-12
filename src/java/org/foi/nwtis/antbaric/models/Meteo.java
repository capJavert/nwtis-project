package org.foi.nwtis.antbaric.models;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Meteo model
 *
 * @author javert
 */
public class Meteo extends Model<Meteo> {

    private Integer id_meteo;
    public Integer id;
    public String adresa_stanice;
    public Double latitude;
    public Double longitude;
    public String vrijeme;
    public String vrijeme_opis;
    public Double temp;
    public Double temp_min;
    public Double temp_max;
    public Double vlaga;
    public Double tlak;
    public Double vjetar;
    public Double vjetar_smjer;
    public String preuzeto;

    public Meteo() {
        super();

        this.tableName = "meteo";
    }

    @Override
    public Integer getPrimaryKey() {
        return this.id_meteo;
    }

    @Override
    Meteo mapResult(ResultSet result, Boolean batch) throws SQLException {
        if (!batch) {
            result.next();
        }

        Meteo model = new Meteo();

        for (Field field : Meteo.class.getFields()) {
            try {
                field.set(model, result.getObject(field.getName(), field.getType()));
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(Meteo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return model;
    }

    @Override
    void setCreateStatement() throws SQLException {
        String fields = "";
        String values = "";

        for (Field field : Meteo.class.getFields()) {
            try {
                if (!fields.isEmpty()) {
                    fields += ("," + field.getName());
                    values += ",?";
                } else {
                    fields += field.getName();
                    values += "?";
                }

            } catch (IllegalArgumentException ex) {
                Logger.getLogger(Meteo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        this.preparedStatement = this.connection.prepareStatement("INSERT INTO " + this.tableName + " (" + fields + ") VALUES (" + values + ")");

        int i = 1;
        for (Field field : Meteo.class.getFields()) {
            try {
                this.preparedStatement.setObject(i++, field.get(this));
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(Meteo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    void setUpdateStatement() throws SQLException {
        String updates = "";

        for (Field field : Meteo.class.getFields()) {
            try {
                if (!updates.isEmpty()) {
                    updates += ("," + field.getName() + "=?");
                } else {
                    updates += (field.getName() + "=?");
                }

            } catch (IllegalArgumentException ex) {
                Logger.getLogger(Meteo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.preparedStatement = this.connection.prepareStatement("UPDATE " + this.tableName + " SET " + updates + " WHERE id_meteo=?");

        int i = 1;
        for (Field field : Meteo.class.getFields()) {
            try {
                this.preparedStatement.setObject(i++, field.get(this));
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(Meteo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        this.preparedStatement.setObject(i++, this.id_meteo);
    }

    @Override
    protected void setGetStatement(String name, Object value) throws SQLException {
        this.preparedStatement = this.connection.prepareStatement("SELECT * FROM " + this.tableName + " WHERE  " + name + "=? ORDER BY id DESC LIMIT 1");
        this.preparedStatement.setObject(1, value);
    }

    public ArrayList<Meteo> findAll(String field, Object value, Integer limit) throws SQLException {
        this.setGetAllStatement(field, value, limit);

        try {
            return this.mapResults(this.preparedStatement.executeQuery());
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ArrayList<>();
    }

    private void setGetAllStatement(String name, Object value, Integer limit) throws SQLException {
        this.preparedStatement = this.connection.prepareStatement("SELECT * FROM " + this.tableName + " WHERE " + name + "=? ORDER BY id DESC LIMIT " + limit);
        this.preparedStatement.setObject(1, value);
    }

    public ArrayList<Meteo> findAll(String field, Object value, Timestamp from, Timestamp to) throws SQLException {
        this.setGetAllStatement(field, value, from, to);

        try {
            return this.mapResults(this.preparedStatement.executeQuery());
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ArrayList<>();
    }

    private void setGetAllStatement(String name, Object value, Timestamp from, Timestamp to) throws SQLException {
        this.preparedStatement = this.connection.prepareStatement("SELECT * FROM " + this.tableName + " WHERE " + name + "=?"
                + " AND preuzeto > ? AND preuzeto < ?"
                + " ORDER BY id DESC");

        this.preparedStatement.setObject(1, value);
        this.preparedStatement.setTimestamp(2, from);
        this.preparedStatement.setTimestamp(3, to);
    }

    public Integer getId() {
        return id;
    }

    public String getAdresa_stanice() {
        return adresa_stanice;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getVrijeme() {
        return vrijeme;
    }

    public String getVrijeme_opis() {
        return vrijeme_opis;
    }

    public Double getTemp() {
        return temp;
    }

    public Double getTemp_min() {
        return temp_min;
    }

    public Double getTemp_max() {
        return temp_max;
    }

    public Double getVlaga() {
        return vlaga;
    }

    public Double getTlak() {
        return tlak;
    }

    public Double getVjetar() {
        return vjetar;
    }

    public Double getVjetar_smjer() {
        return vjetar_smjer;
    }

    public String getPreuzeto() {
        return preuzeto;
    }
    
}
