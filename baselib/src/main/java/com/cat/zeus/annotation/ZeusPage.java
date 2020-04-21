package com.cat.zeus.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ZeusPage {
    boolean hideTitleBar() default false;

    boolean hideLeftIV() default false;

    int titleBarLayout() default 0;

    int layout() default 0;

    String title() default "";
}
