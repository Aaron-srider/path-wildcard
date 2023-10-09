package org.example;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AppTest {

    @Test
    public void test() {
        Wildcard wildcard = new Wildcard("build");
        Assertions.assertTrue(wildcard.match("build"));
        Assertions.assertTrue(wildcard.match("build/"));
        Assertions.assertTrue(wildcard.match("build/xxx"));

        Assertions.assertFalse(wildcard.match("buildxx"));
        Assertions.assertFalse(wildcard.match("xx/build"));
    }

    @Test
    public void test1() {
        Wildcard wildcard = new Wildcard("build/");
        Assertions.assertFalse(wildcard.match("build"));
        Assertions.assertTrue(wildcard.match("build/"));
        Assertions.assertTrue(wildcard.match("build/xxx"));
        Assertions.assertTrue(wildcard.match("build/xx/x"));
    }

    @Test
    public void test2() {
        Wildcard wildcard = new Wildcard("*/build");

        Assertions.assertTrue(wildcard.match("test/build/"));
        Assertions.assertTrue(wildcard.match("test/build"));

        Assertions.assertFalse(wildcard.match("build/"));
        Assertions.assertFalse(wildcard.match("build"));
        Assertions.assertFalse(wildcard.match("test/test/build/"));
        Assertions.assertFalse(wildcard.match("test/test/build"));
    }

    @Test
    public void test3() {
        Wildcard wildcard = new Wildcard("*/build/*");

        Assertions.assertTrue(wildcard.match("test/build/xxx"));
        Assertions.assertTrue(wildcard.match("test/build/xx/x"));

        Assertions.assertFalse(wildcard.match("build/"));
        Assertions.assertFalse(wildcard.match("build"));
        Assertions.assertFalse(wildcard.match("test/build"));
        Assertions.assertFalse(wildcard.match("test/build/"));
    }

    @Test
    public void test4() {
        Wildcard wildcard = new Wildcard("**/build");

        Assertions.assertTrue(wildcard.match("build/"));
        Assertions.assertTrue(wildcard.match("build"));
        Assertions.assertTrue(wildcard.match("test/build/"));
        Assertions.assertTrue(wildcard.match("test/build"));
        Assertions.assertTrue(wildcard.match("test/test/build/"));
        Assertions.assertTrue(wildcard.match("test/test/build"));
    }
}