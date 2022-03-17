package com.dkqa.pages;

import com.dkqa.navigator.Page;
import com.dkqa.navigator.PageMarker;
import com.dkqa.navigator.param.PageParamBuilder;
import com.dkqa.navigator.param.PageParamExecutor;

@PageMarker(pageName = "Page1")
public class Page1 extends WebPage {

    public static String currentUser;

    public static PageParamExecutor paramToPage2(String user) {
        return new PageParamBuilder(new Page1(), new Page2())
                .executor()
                .values(user)
                .step(() -> {
                    System.out.println("[PARAM] Page1 -> Page2 (" + user + ")");
                    currentUser = user;
                })
                .build();
    }

    @Override
    protected void pageNavigationInfo() {
        addNavigation(new Page2(), paramToPage2("Default"), () -> {
            System.out.println("[GO] go to Page 2");
            currentPage = "2";
        });
    }

    @Override
    public Page registerPage() {
        return page();
    }

    @Override
    protected String determinantName() {
        return "1";
    }
}
