//    MIT License
//
//    Copyright (c) 2021 Danil Kopylov
//
//    Permission is hereby granted, free of charge, to any person obtaining a copy
//    of this software and associated documentation files (the "Software"), to deal
//    in the Software without restriction, including without limitation the rights
//    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//    copies of the Software, and to permit persons to whom the Software is
//    furnished to do so, subject to the following conditions:
//
//    The above copyright notice and this permission notice shall be included in all
//    copies or substantial portions of the Software.
//
//    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//    SOFTWARE.

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
                    try {
                        Object o = clazz.getDeclaredField("INSTANCE").get(null);
                        try {
                            return (Page) method.invoke(o);
                        } catch (InvocationTargetException e2) {
                            e2.printStackTrace();
                        }
                    } catch (IllegalAccessException | NoSuchFieldException ex) {
                        ex.printStackTrace();
                    }
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
