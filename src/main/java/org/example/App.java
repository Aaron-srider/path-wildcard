package org.example;


import java.util.Arrays;

/**
 * Hello world!
 */
public class App {


    public void test() {

    }


    public static void main(String[] args) {
        Arrays.stream("test/".split("/")).forEach(item -> {
            System.out.println(item + " xx");
        });

    }
}
