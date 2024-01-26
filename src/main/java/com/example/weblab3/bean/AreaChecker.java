package com.example.weblab3.bean;

import static java.lang.Math.pow;

public class AreaChecker {
    public static boolean isHit(float x, float y, float r) {

        return rectangle(x,y,r) || triangle(x,y,r)|| circle(x,y,r);
    }
    public static boolean rectangle(float x,float y,float r){
        return  (x >= 0 && x <= r/2 && y <= 0 && y >= -r);
    }
    public static boolean triangle(float x,float y,float r){
        return  (x >= 0 && y >= 0 && x <= r  && y <= -1/2 * x + r/2) ;
    }
    public static boolean circle(float x,float y,float r){
        return  (x <= 0 && y >= 0 && pow(x, 2) + pow(y, 2) <= pow(r, 2));
    }
}