package com.dkqa.pages;

import com.dkqa.navigator.NavigatorBuilder;
import com.dkqa.navigator.Page;
import com.dkqa.navigator.action.PageDeterminant;

import java.util.ArrayList;
import java.util.List;

public abstract class TestPage extends Page {

    public static String currentPage = "1";
    public static List<String> assertLog = new ArrayList<>();

    protected abstract String determinantName();

    @Override
    protected NavigatorBuilder navigatorBuilder() {
        return new NavigatorBuilder("WebNavigation", "com.dkqa.pages*")
                .setActionLog((s -> {
                    //20.03.22 16:50
                    assertLog.add(s.replaceAll("\\d{2}\\.\\d{2}\\.\\d{2} \\d{2}:\\d{2} ", ""));
                    System.out.println(s);
                }));
    }

    @Override
    protected PageDeterminant pageDeterminant() {
        return  () -> {
            return currentPage.equals(determinantName());
        };
    }


}
