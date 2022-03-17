package com.dkqa.pages;

import com.dkqa.navigator.Page;
import com.dkqa.navigator.PageMarker;

import static com.dkqa.pages.Page3.dependentParamPage1Page2;
import static com.dkqa.pages.Page3.dependentParamPage3Page8;

@PageMarker(pageName = "Page8")
public class Page8 extends WebPage {

    @Override
    protected void pageNavigationInfo() {
        addDependentParam(dependentParamPage1Page2());
        addDependentParam(dependentParamPage3Page8());
        addNavigation(new Page3(), () -> {
            System.out.println("[GO] go to Page 3");
            currentPage = "3";
        });
        addNavigation(new Page9(), () -> {
            System.out.println("[GO] go to Page 9");
            currentPage = "9";
        });
        addNavigation(new Page10(), () -> {
            System.out.println("[GO] go to Page 10");
            currentPage = "10";
        });
    }

    @Override
    public Page registerPage() {
        return page();
    }

    @Override
    protected String determinantName() {
        return "8";
    }
}
