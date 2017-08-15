package com.example.liujs.testannotation;

import android.graphics.Color;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by liujs on 2017/5/30.
 * 自定义一个注解
 */

public class TestAnnotation {
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface testMethod{
        String userName() default "liujs";
        int age() ;
    }
}
