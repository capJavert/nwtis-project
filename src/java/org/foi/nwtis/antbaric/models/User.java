package org.foi.nwtis.antbaric.models;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User model
 *
 * @author javert
 */
public class User extends Model<User> {

    private Integer id;
    public String username;
    public String password;
    public String name;

    public User() {
        super();

        this.tableName = "users";
    }

    public User authenticate(String username, String password) throws SQLException {
        this.preparedStatement = this.connection.prepareStatement("SELECT * FROM " + this.tableName + " WHERE username = ? AND password = ?");
        this.preparedStatement.setString(1, username);
        this.preparedStatement.setString(2, password);

        return (User) this.mapResult(this.preparedStatement.executeQuery(), false);
    }

    @Override
    public Integer getPrimaryKey() {
        return this.id;
    }

    @Override
    User mapResult(ResultSet result, Boolean batch) throws SQLException {
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
        String fields = "";
        String values = "";

        for (Field field : User.class.getFields()) {
            try {
                if (!fields.isEmpty()) {
                    fields += ("," + field.getName());
                    values += ",?";
                } else {
                    fields += field.getName();
                    values += "?";
                }

            } catch (IllegalArgumentException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        this.preparedStatement = this.connection.prepareStatement("INSERT INTO " + this.tableName + " (" + fields + ") VALUES (" + values + ")");

        int i = 1;
        for (Field field : User.class.getFields()) {
            try {
                this.preparedStatement.setObject(i++, field.get(this));
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    void setUpdateStatement() throws SQLException {
        String updates = "";

        for (Field field : User.class.getFields()) {
            try {
                if (!updates.isEmpty()) {
                    updates += ("," + field.getName() + "=?");
                } else {
                    updates += (field.getName() + "=?");
                }

            } catch (IllegalArgumentException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.preparedStatement = this.connection.prepareStatement("UPDATE " + this.tableName + " SET " + updates + " WHERE id=?");

        int i = 1;
        for (Field field : User.class.getFields()) {
            try {
                this.preparedStatement.setObject(i++, field.get(this));
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        this.preparedStatement.setObject(i++, this.id);
    }

}
