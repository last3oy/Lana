package kmutt.senior.pet.manager;

import android.content.Context;

import kmutt.senior.pet.bus.Contextor;


public class SingletonTemplate {

    private static SingletonTemplate instance;

    public static  SingletonTemplate getInstance() {
        if (instance == null)
            instance = new SingletonTemplate();
        return instance;
    }

    private Context mContext;

    private SingletonTemplate() {
        mContext = Contextor.getInstance().getContext();
    }

}
