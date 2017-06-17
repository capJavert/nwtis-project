package org.foi.nwtis.antbaric.components;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SyntaxValidator {

    /**
     * Return rules
     *
     * @return Returns list of rules
     */
    private static List<String> rules() {
        return Stream.of(
            "USER (.*); PASSWD (.*);() (PAUSE|START|STOP|STATUS);",
            "USER (.*); PASSWD (.*); (IoT_Master) (START|STOP|WORK|WAIT|LOAD|CLEAR|STATUS|LIST);",
            "USER (.*); PASSWD (.*); (IoT) (\\d{1,6}) (ADD\\s(.{1,})\\s(.{1,})|WORK|WAIT|REMOVE|STATUS);"
        ).collect(Collectors.toList());
    }

    /**
     *
     * @param match String to match
     * @return Matcher
     */
    public static Matcher validate(String match) {
        for(String rule : rules()) {
            Pattern pattern = Pattern.compile(rule);
            Matcher m = pattern.matcher(match);

            if(m.matches()) {
                return m;
            }
        }

        return null;
    }
}
