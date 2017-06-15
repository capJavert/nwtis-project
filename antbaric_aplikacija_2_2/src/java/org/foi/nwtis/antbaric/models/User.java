package org.foi.nwtis.antbaric.models;

/**
 * User model
 *
 * @author javert
 */
public class User {

    private Integer id;
    public String username;
    public String password;
    public String name;

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrimaryKey() {
        return this.id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
