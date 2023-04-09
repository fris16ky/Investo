package com.senior.capstone.Investo;

import android.app.Application;
import android.content.Context;

public class Contex extends Application{
    private static Contex contex;

    public void onCreate() {
        super.onCreate();
        Contex.contex = (Contex) getApplicationContext();

    }

    public static Contex getContex() {
        return Contex.contex;
    }
}
