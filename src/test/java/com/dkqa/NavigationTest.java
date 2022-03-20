package com.dkqa;

import com.dkqa.navigator.Navigator;
import com.dkqa.pages.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static com.dkqa.pages.TestPage.assertLog;
import static com.dkqa.pages.TestPage.currentPage;

public class NavigationTest {

    @Before
    public void beforeMethod() {
        assertLog = new ArrayList<>();
    }

    private String assertLogValue() {
        return String.join("\n", assertLog);
    }

    @Test
    public void testNoRoute() {
        currentPage = "1";
        try {
            new Page111().navigate();
            Assert.fail("Page111 must not have route");
        } catch (Exception e) {
            Assert.assertEquals(
                    "It is impossible to build a route from the '1' page to the '111' page",
                    e.getMessage()
            );
        }
    }

    @Test
    public void testSameParamsAll() {
        currentPage = "4";
        Page1.param1 = "same_param_1";
        Page2.param2 = "same_param_2";
        new Page4()
                .param(Page1.param1("same_param_1", "pas"))
                .param(Page2.param2("same_param_2"))
                .navigate();
        Assert.assertEquals(
                "\n[NAVIGATION][START] Navigate to '4' (current page '4')\n" +
                        "[NAVIGATION][CHECK PARAM][TRUE] Param '1 -> 2' on page '4'\n" +
                        "[NAVIGATION][CHECK PARAM][TRUE] Param '2 -> 3' on page '4'\n" +
                        "[NAVIGATION][END]",
                assertLogValue()
        );

    }

    @Test
    public void testSameParamsOneOfTwo() {
        currentPage = "4";
        Page1.param1 = "same_param_1";
        Page2.param2 = "param_2";
        new Page4()
                .param(Page1.param1("same_param_1", "pas"))
                .param(Page2.param2("another_param_2"))
                .navigate();
        Assert.assertEquals(
                "\n[NAVIGATION][START] Navigate to '4' (current page '4')\n" +
                        "[NAVIGATION][CHECK PARAM][TRUE] Param '1 -> 2' on page '4'\n" +
                        "[NAVIGATION][CHECK PARAM][FALSE] Param '2 -> 3' on page '4'\n" +
                        "[NAVIGATION][ROUTE] 4 -> 3 -> 2\n" +
                        "[NAVIGATION][WAY] Go to 3\n" +
                        "[NAVIGATION][WAY] Go to 2\n" +
                        "[NAVIGATION][ROUTE] 2 -> 3 -> 4\n" +
                        "[NAVIGATION][SET PARAM] 2 -> 3 (another_param_2)\n" +
                        "[NAVIGATION][WAY] Go to 3\n" +
                        "[NAVIGATION][WAY] Go to 4\n" +
                        "[NAVIGATION][END]",
                assertLogValue()
        );
    }

    @Test
    public void testNotSameParams() {
        currentPage = "4";
        Page1.param1 = "param_1";
        Page2.param2 = "param_2";
        new Page4()
                .param(Page2.param2("another_param_2"))
                .param(Page1.param1("another_param_1", "pas"))
                .navigate();
        Assert.assertEquals(
                "\n[NAVIGATION][START] Navigate to '4' (current page '4')\n" +
                        "[NAVIGATION][CHECK PARAM][FALSE] Param '2 -> 3' on page '4'\n" +
                        "[NAVIGATION][ROUTE] 4 -> 3 -> 2\n" +
                        "[NAVIGATION][WAY] Go to 3\n" +
                        "[NAVIGATION][WAY] Go to 2\n" +
                        "[NAVIGATION][CHECK PARAM][FALSE] Param '1 -> 2' on page '2'\n" +
                        "[NAVIGATION][ROUTE] 2 -> 1\n" +
                        "[NAVIGATION][WAY] Go to 1\n" +
                        "[NAVIGATION][ROUTE] 1 -> 2 -> 3 -> 4\n" +
                        "[NAVIGATION][SET PARAM] 1 -> 2 (another_param_1)\n" +
                        "[NAVIGATION][WAY] Go to 2\n" +
                        "[NAVIGATION][SET PARAM] 2 -> 3 (another_param_2)\n" +
                        "[NAVIGATION][WAY] Go to 3\n" +
                        "[NAVIGATION][WAY] Go to 4\n" +
                        "[NAVIGATION][END]",
                assertLogValue()
        );
    }

    @Test
    public void testSameParamsButNeedToCheckOnPageWhichHasDependent() {
        currentPage = "5";
        Page1.param1 = "param_1";
        Page2.param2 = "param_2";
        new Page5()
                .param(Page2.param2("param_2"))
                .param(Page1.param1("param_1", "pas"))
                .navigate();
        Assert.assertEquals(
                "\n[NAVIGATION][START] Navigate to '5' (current page '5')\n" +
                        "[NAVIGATION][ROUTE] 5 -> 3\n" +
                        "[NAVIGATION][WAY] Go to 3\n" +
                        "[NAVIGATION][CHECK PARAM][TRUE] Param '2 -> 3' on page '3'\n" +
                        "[NAVIGATION][CHECK PARAM][TRUE] Param '1 -> 2' on page '3'\n" +
                        "[NAVIGATION][ROUTE] 3 -> 5\n" +
                        "[NAVIGATION][WAY] Go to 5\n" +
                        "[NAVIGATION][END]",
                assertLogValue()
        );
    }

    @Test
    public void testPreviousPage() {
        currentPage = "same (8,10)";
        Assert.assertEquals("unknown page", new Page1().defineCurrentPage());

        currentPage = "7";
        new Page1().defineCurrentPage(); // for history (7 will be written to history)
        currentPage = "same (8,10)";
        Assert.assertEquals("8", new Page1().defineCurrentPage());

        currentPage = "9";
        new Page1().defineCurrentPage(); // for history (9 will be written to history)
        currentPage = "same (8,10)";
        Assert.assertEquals("10", new Page1().defineCurrentPage());
    }

    @Test
    public void testBrokenNavigation() {
        currentPage = "2";
        try {
            new Page11().navigate();
            Assert.fail("Navigation to 11 page should be broken");
        } catch (Navigator.NavigationException e) {
            Assert.assertEquals(
                    "\n[NAVIGATION][START] Navigate to '11' (current page '2')\n" +
                            "[NAVIGATION][ROUTE] 2 -> 11\n" +
                            "[NAVIGATION][WAY] Go to 11",
                    assertLogValue()
            );
        }

    }

    @Test
    public void testWeight() {
        currentPage = "13";
        new Page2().navigate();
        Assert.assertEquals(
                "\n[NAVIGATION][START] Navigate to '2' (current page '13')\n" +
                        "[NAVIGATION][ROUTE] 13 -> 14 -> 15 -> 16 -> 17 -> 12 -> 2\n" +
                        "[NAVIGATION][WAY] Go to 14\n" +
                        "[NAVIGATION][WAY] Go to 15\n" +
                        "[NAVIGATION][WAY] Go to 16\n" +
                        "[NAVIGATION][WAY] Go to 17\n" +
                        "[NAVIGATION][WAY] Go to 12\n" +
                        "[NAVIGATION][WAY] Go to 2\n" +
                        "[NAVIGATION][END]",
                assertLogValue()
        );
    }

}
