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


    @Test
    public void test5() {
        Wildcard wildcard = new Wildcard("test/**/build");

        Assertions.assertTrue(wildcard.match("test/x/x/build"));
        Assertions.assertTrue(wildcard.match("test/xx/build"));
        Assertions.assertTrue(wildcard.match("test/build"));

        Assertions.assertFalse(wildcard.match("build"));
        Assertions.assertFalse(wildcard.match("ttt/build/"));
        Assertions.assertFalse(wildcard.match("ttt/test/build/"));
    }

    @Test
    public void test6() {
        Wildcard wildcard = new Wildcard("../test/sample*.txt");

        Assertions.assertTrue(wildcard.match("../test/sampleHAHAHA.txt"));

        Assertions.assertFalse(wildcard.match("../test/sampabc.txt"));
        Assertions.assertFalse(wildcard.match("../test/sample/abc.txt"));
    }

    @Test
    public void test7() {
        Wildcard wildcard = new Wildcard("test/plan?Used/text");

        Assertions.assertTrue(wildcard.match("test/planAUsed/text"));

        Assertions.assertFalse(wildcard.match("test/planUsed/text"));
        Assertions.assertFalse(wildcard.match("test/planAbUsed/text"));
    }


}
