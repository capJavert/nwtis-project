package org.foi.nwtis.antbaric.components;

/**
 * Used for managing state of Thread
 * @author javert
 */
public class Status {
    private String name;
    private Integer logItemsCount;

    public Status() {

        this.name = "START";
        this.logItemsCount = 0;
    }

    public String get() {
        return name;
    }

    public void set(String name) {
        this.name = name;
    }

    public Integer getLogItemsCount() {
        return logItemsCount;
    }

    public void resetLogItemCount() {
        this.logItemsCount = 0;
    }

    public void setLogItemsCount() {
        this.logItemsCount++;
    }
}
