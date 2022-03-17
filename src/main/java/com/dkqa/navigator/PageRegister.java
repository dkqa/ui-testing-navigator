package com.dkqa.navigator;

import net.sf.corn.cps.CPScanner;
import net.sf.corn.cps.ClassFilter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.dkqa.navigator.PageNavigatorFactoryMethod.getPageName;

class PageRegister {

    public static HashMap<String, Page> parsePages(String navigatorName, String... pagePaths) {
        HashMap<String, Page> mapPages = new HashMap<>();
        List<Class<?>> pageClasses = new ArrayList<>();

        if (pagePaths.length == 0) {
            pageClasses.addAll(getPageClasses("*"));
        }

        for (String path : pagePaths) {
            pageClasses.addAll(getPageClasses(path));
        }

        for (Class<?> clazz : pageClasses) {
            String name = getPageName(clazz);
            Page page = getPageObject(clazz);
            if (page != null && page.navigatorBuilder().getName().equals(navigatorName)) {
                if (mapPages.get(name) == null) {
                    System.out.println("Parse page - " + name);
                    mapPages.put(name, page);
                } else if (!mapPages.get(name).getClass().getName().equals(clazz.getName())) {
                    throw new UniqueNameException(name, clazz.getName(), mapPages.get(name).getClass().getName());
                }
            }
        }

        return mapPages;
    }

    private static List<Class<?>> getPageClasses(String path) {
        return CPScanner.scanClasses(
                new ClassFilter()
                        .packageName(path)
                        .annotation(PageMarker.class)
        );
    }

    private static Page getPageObject(Class clazz) {
        Method method = getMethod(clazz, "registerPage");
        try {
            return (Page) method.invoke(null);
        } catch (NullPointerException e) {
            try {
                Object o = clazz.newInstance();
                try {
                    return (Page) method.invoke(o);
                } catch (InvocationTargetException e1) {
                    e1.printStackTrace();
                }
            } catch (InstantiationException | IllegalAccessException e1) {
                e1.printStackTrace();
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Method getMethod(Class clazz, String name) {
        try {
            return clazz.getDeclaredMethod(name);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    static class UniqueNameException extends RuntimeException {
        UniqueNameException(String nameOfRegistration, String classNameOfRegistration, String classNameAlreadyRegistered) {
            super("The page name of the PageMarker annotation must be unique for each page class\n"
                    + "Duplicate name '" + nameOfRegistration + "' in the class '" + classNameOfRegistration
                    + "', the same name has the class '" + classNameAlreadyRegistered  + "'"
            );
        }
    }

}
