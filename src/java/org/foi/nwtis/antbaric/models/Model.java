/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.antbaric.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.antbaric.components.DbManager;

/**
 * Base class for db entities CRUD
 *
 * @author javert
 */
public abstract class Model<T> {

    protected final transient Connection connection;
    protected transient PreparedStatement preparedStatement;
    protected transient String tableName;

    public Model() {
        DbManager dbManager = new DbManager();
        this.connection = dbManager.getConnection();
    }

    abstract Integer getPrimaryKey();

    public boolean save() throws SQLException {
        if (this.getPrimaryKey() != null) {
            return this.update();
        } else {
            return this.create();
        }
    }

    public T findOne(Integer primaryKey) throws SQLException {
        this.setGetStatement(primaryKey);

        try {
            return this.mapResult(this.preparedStatement.executeQuery(), false);
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public T findOne(String field, Object value) throws SQLException {
        this.setGetStatement(field, value);

        try {
            return this.mapResult(this.preparedStatement.executeQuery(), false);
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public ArrayList<T> findAll() throws SQLException {
        this.setGetAllStatement();

        try {
            return this.mapResults(this.preparedStatement.executeQuery());
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ArrayList<>();
    }

    public ArrayList<T> findAll(String field, Object value) throws SQLException {
        this.setGetAllStatement(field, value);

        try {
            return this.mapResults(this.preparedStatement.executeQuery());
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ArrayList<>();
    }

    public boolean create() throws SQLException {
        this.setCreateStatement();

        try {
            this.preparedStatement.executeUpdate();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean update() throws SQLException {
        this.setUpdateStatement();

        try {
            this.preparedStatement.executeUpdate();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean delete() {
        return false;
    }

    abstract T mapResult(ResultSet result, Boolean batch) throws SQLException;

    public ArrayList<T> mapResults(ResultSet result) throws SQLException {
        ArrayList<T> models = new ArrayList<>();

        while (result.next()) {
            models.add(this.mapResult(result, true));
        }

        return models;
    }

    protected void setGetAllStatement() throws SQLException {
        this.preparedStatement = this.connection.prepareStatement("SELECT * FROM " + this.tableName);
    }

    protected void setGetAllStatement(String name, Object value) throws SQLException {
        this.preparedStatement = this.connection.prepareStatement("SELECT * FROM " + this.tableName + " WHERE  " + name + "=?");
        this.preparedStatement.setObject(1, value);
    }

    protected void setGetStatement(Integer primaryKey) throws SQLException {
        this.preparedStatement = this.connection.prepareStatement("SELECT * FROM " + this.tableName + " WHERE id=?");
        this.preparedStatement.setInt(1, primaryKey);
    }

    protected void setGetStatement(String name, Object value) throws SQLException {
       this.preparedStatement = this.connection.prepareStatement("SELECT * FROM " + this.tableName + " WHERE  " + name + "=? LIMIT 1");
        this.preparedStatement.setObject(1, value);
    }

    abstract void setCreateStatement() throws SQLException;

    abstract void setUpdateStatement() throws SQLException;
}
