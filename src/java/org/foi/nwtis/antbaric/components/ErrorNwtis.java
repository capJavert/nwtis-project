package org.foi.nwtis.antbaric.components;

public class ErrorNwtis {

    /**
     *
     * @param code
     * @return
     */
    public static String getMessage(String code) {
        switch (code) {
            default:
                return "ERROR; Doslo je do nepoznate pogreske u sustavu";
        }
    }
}
