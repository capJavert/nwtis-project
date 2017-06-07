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
public class Device extends Model<Device> {

    private Integer oldId;
    public Integer id;
    public String naziv;
    public Float latitude;
    public Float longitude;
    public Integer status;
    public Timestamp vrijeme_promjene;
    public Timestamp vrijeme_kreiranja;

    public Device() {
        super();

        this.tableName = "uredaji";
    }

    public void setOldId(Integer oldId) {
        this.oldId = oldId;
    }

    @Override
    public Integer getPrimaryKey() {
        return this.id;
    }

    @Override
    Device mapResult(ResultSet result, Boolean batch) throws SQLException {
        if (!batch) {
            result.next();
        }

        Device model = new Device();

        for (Field field : Device.class.getFields()) {
            try {
                field.set(model, result.getObject(field.getName(), field.getType()));
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(Device.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return model;
    }

    @Override
    void setCreateStatement() throws SQLException {
        String fields = "";
        String values = "";

        for (Field field : Device.class.getFields()) {
            try {
                if (!fields.isEmpty()) {
                    fields += ("," + field.getName());
                    values += ",?";
                } else {
                    fields += field.getName();
                    values += "?";
                }

            } catch (IllegalArgumentException ex) {
                Logger.getLogger(Device.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        this.preparedStatement = this.connection.prepareStatement("INSERT INTO " + this.tableName + " (" + fields + ") VALUES (" + values + ")");

        int i = 1;
        for (Field field : Device.class.getFields()) {
            try {
                this.preparedStatement.setObject(i++, field.get(this));
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(Device.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    void setUpdateStatement() throws SQLException {
        String updates = "";

        for (Field field : Device.class.getFields()) {
            try {
                if (!updates.isEmpty()) {
                    updates += ("," + field.getName() + "=?");
                } else {
                    updates += (field.getName() + "=?");
                }

            } catch (IllegalArgumentException ex) {
                Logger.getLogger(Device.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.preparedStatement = this.connection.prepareStatement("UPDATE " + this.tableName + " SET " + updates + " WHERE id=?");

        int i = 1;
        for (Field field : Device.class.getFields()) {
            try {
                this.preparedStatement.setObject(i++, field.get(this));
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(Device.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        this.preparedStatement.setObject(i++, this.oldId);
    }

}
