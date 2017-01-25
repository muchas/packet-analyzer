package pl.edu.agh.iisg.to.filter.entities;


import javafx.beans.property.BooleanProperty;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Filter {
    private String name;
    private String body;
    private Boolean isActive;

    private static final Pattern HEADER_PATTERN = Pattern.compile("function (?<NAME>[A-z]+)\\(packet\\)");

    public static final String SAMPLE_CODE = String.join("\n", new String[]{
            " /*",
            "  * packet argument is available",
            "  */",
            "  if(packet === 'tcp') {",
            "     return true;",
            "  }",
            "",
            "  return false;",
    });


    public Filter(String code) {
        String lines[] = code.split("\\r?\\n");

        assert Array.getLength(lines) >= 3;

        String bodyLines[] = Arrays.copyOfRange(lines, 1, Array.getLength(lines) - 1);

        this.name = extractFunctionName(lines[0]);
        this.body = String.join("\n", bodyLines);
        this.isActive = false;
    }

    public Filter(String name, String body) {
        this.name = name;
        this.body = body;
        this.isActive = false;
    }

    public String getCode() {
        return this.addToFunctionScope(body);
    }

    public String getBody() {
        return body;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    private String extractFunctionName(String code) {
        Matcher matcher = HEADER_PATTERN.matcher(code);

        if (matcher.find()) {
            return matcher.group("NAME");
        }

        return "";
    }

    private String addToFunctionScope(String functionBody) {
        return String.join("\n", new String[]{
                "function " + name + "(packet) { ",
                functionBody,
                "}"
        });
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
