package com.dkqa.pages;

import com.dkqa.navigator.NavigatorBuilder;
import com.dkqa.navigator.Page;
import com.dkqa.navigator.action.PageDeterminant;

public abstract class WebPage extends Page {

    public static String currentPage = "1";

    protected abstract String determinantName();

    @Override
    protected NavigatorBuilder navigatorBuilder() {
        return new NavigatorBuilder("WebNavigation", "com.dkqa.pages*", "com.dkqa.pages*");
    }

    @Override
    protected PageDeterminant pageDeterminant() {
        return  () -> {
            return currentPage.equals(determinantName());
        };
    }


}
