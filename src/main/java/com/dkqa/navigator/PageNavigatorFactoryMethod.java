package com.dkqa.navigator;

public class PageNavigatorFactoryMethod {

    public static String getPageName(Class<?> clazz) {
        String name = clazz.getAnnotation(PageMarker.class).pageName();
        if (name == null || name.equals("")) throw new RuntimeException(clazz.getName() + " should have @PageMarker");
        return name;
    }

}
