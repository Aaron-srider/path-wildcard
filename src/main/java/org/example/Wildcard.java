package org.example;

import lombok.var;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Wildcard {
    @NotNull
    private String pattern;

    private List<String > patterns = new ArrayList<>();

    public Wildcard(@NotNull String pattern) {
        this.pattern = pattern;
        var l = new ArrayList<String>();
        var s = pattern.split("/");

        Arrays.stream(s).filter(item -> !item.isEmpty()).forEach(item -> {
            if(item.equals("**")) {
                l.add(".*");
            } else if (item.equals("*")) {
                l.add("[^/]+");
            } else {
                l.add(item);
            }
        });

        AtomicReference<String> reg = new AtomicReference<>("");
        l.stream().forEach(
                item -> {
                    if(item.equals(".*")) {
                        reg.set(reg.get() + item );
                        return;
                    }
                    reg.set(reg.get() + item + "/" );
                }
        );

        var re = reg.get();
        re = re.substring(0, re.length() - 1);


        if(pattern.endsWith("/")) {
            patterns.add( re + "/.*");
        } else {
            patterns.add( re + "(/.*)?");
        }
    }

    public boolean match(@NotNull String str) {

        return patterns.stream().anyMatch(item -> {
            return str.matches(item);
        });
    }
}
