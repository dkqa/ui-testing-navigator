package com.dkqa;

import com.dkqa.pages.*;
import org.junit.Test;

public class ExecuteTest {

    @Test
    public void test() {
//        WebPage.currentPage = "3";
        new Page9().navigate();
        new Page9()
                .param(Page3.paramPage3Page8("11112"))
                .param(Page1.paramToPage2("Default"))
                .param(Page3.paramPage3Page8("11112"))
                .navigate();
//        new Page9()
//                .param(Page1.paramToPage2("Default"))
//                .param(Page3.paramPage3Page8("11112"))
//                .navigate();
    }

    @Test
    public void test2() {
        new Page9().navigate();
        new Page1().navigate();
        new Page9().navigate();
    }

}
