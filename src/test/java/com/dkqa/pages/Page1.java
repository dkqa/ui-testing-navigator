package com.dkqa.pages;

import com.dkqa.navigator.Page;
import com.dkqa.navigator.PageMarker;
import com.dkqa.navigator.param.PageParamBuilder;
import com.dkqa.navigator.param.PageParamExecutor;

@PageMarker(pageName = "1")
public class Page1 extends TestPage {

    public static String param1 = "";

    public static PageParamExecutor param1(String login, String pas) {
        return new PageParamBuilder(new Page1(), new Page2())
                .executor()
                .values(login)
                .step(() -> param1 = login)
                .build();
    }

    @Override
    protected String determinantName() {
        return "1";
    }

    @Override
    protected void pageNavigationInfo() {
        addNavigation(
                new Page2(),
                param1("default_p1_p2", "default_password"),
                () -> currentPage = "2"
        );
    }

    @Override
    public Page registerPage() {
        return page();
    }
}
