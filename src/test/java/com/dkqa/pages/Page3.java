package com.dkqa.pages;

import com.dkqa.navigator.Page;
import com.dkqa.navigator.PageMarker;
import com.dkqa.navigator.param.PageParamBuilder;
import com.dkqa.navigator.param.PageParamDependent;
import com.dkqa.navigator.param.PageParamExecutor;

@PageMarker(pageName = "Page3")
public class Page3 extends WebPage {

    public static String currentNumber;

    public static PageParamDependent dependentParamPage1Page2() {
        return new PageParamBuilder(new Page1(), new Page2())
                .dependent()
                .value(() -> Page1.currentUser)
                .build();
    }

    public static PageParamExecutor paramPage3Page8(String number) {
        return new PageParamBuilder(new Page3(), new Page8())
                .executor()
                .values(number)
                .step(() -> {
                    currentNumber = number;
                })
                .build();
    }

    public static PageParamDependent dependentParamPage3Page8() {
        return new PageParamBuilder(new Page3(), new Page8())
                .dependent()
                .value(() -> currentNumber)
                .build();
    }

    @Override
    protected void pageNavigationInfo() {
        addDependentParam(dependentParamPage1Page2());
        addNavigation(new Page2(), () -> {
            currentPage = "2";
        });
        addNavigation(new Page4(), () -> {
            currentPage = "4";
        });
        addNavigation(new Page7(), () -> {
            currentPage = "7";
        });
        addNavigation(new Page8(), paramPage3Page8("1111"), () -> {
            currentPage = "8";
        });
    }

    @Override
    public Page registerPage() {
        return page();
    }

    @Override
    protected String determinantName() {
        return "3";
    }
}
