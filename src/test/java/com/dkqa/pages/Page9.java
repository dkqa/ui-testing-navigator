package com.dkqa.pages;

import com.dkqa.navigator.Page;
import com.dkqa.navigator.PageMarker;

import static com.dkqa.pages.Page3.dependentParamPage1Page2;
import static com.dkqa.pages.Page3.dependentParamPage3Page8;

@PageMarker(pageName = "Page9")
public class Page9 extends WebPage {

    @Override
    protected void pageNavigationInfo() {
        addPreviousPage(new Page8());
        addDependentParam(dependentParamPage1Page2());
        addDependentParam(dependentParamPage3Page8());
        addNavigation(new Page8(), () -> {
            System.out.println("[GO] go to Page 8");
            currentPage = "8";
        });
    }

    @Override
    public Page registerPage() {
        return page();
    }

    @Override
    protected String determinantName() {
        return "9";
    }
}
