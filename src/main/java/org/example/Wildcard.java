package org.example;

import lombok.var;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class Wildcard {

    @NotNull
    private String reg = "";

    @NotNull
    private final String pattern;

    public Wildcard(
            @NotNull
            String pattern
    ) {

        this.pattern = pattern;

        convertPatternToRegex();

        processRegTailingSlash();

    }

    private void convertPatternToRegex() {
        var patternParts = pattern.split("/");

        Arrays.stream(patternParts).filter(item -> !item.isEmpty()).forEach(part -> {
            if (part.equals("**")) {
                addMatchAllReg();
            } else if (part.contains("*")) {
                addMatchAllButPathSeparatorReg(part);
            } else {
                addPartItself(part);
            }
        });

        removeTailingSplash();
    }

    private void addMatchAllReg() {
        reg += ".*";
    }

    private void addMatchAllButPathSeparatorReg(@NotNull String origin) {
        String replace = origin.replace("*", "[^/]+");
        reg += replace + "/";
    }
    //  reg += "[^/]+/";
    private void addPartItself(
            @NotNull
            String part
    ) {
        reg += (part + "/");
    }

    private void removeTailingSplash() {
        reg = reg.substring(0, reg.length() - 1);
    }

    private void processRegTailingSlash() {

        boolean patternMustBeADir = pattern.endsWith("/");
        if (patternMustBeADir) {
            addMatchAllSubContentsButDirItselfReg();
        } else {
            addMatchAllSubContentsReg();
        }
    }

    private void addMatchAllSubContentsButDirItselfReg() {
        reg += "/.*";
    }

    private void addMatchAllSubContentsReg() {
        reg += "(/.*)?";
    }

    public boolean match(
            @NotNull
            String str
    ) {
        return str.matches(reg);
    }
}
