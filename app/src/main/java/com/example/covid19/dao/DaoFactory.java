package com.example.covid19.dao;


import android.content.Context;

import com.example.covid19.util.Util;


import java.io.IOException;

public abstract class  DaoFactory {
    protected static Context context;
    private static String daoType;

    public abstract UserDao getUserDao();

    public abstract StructureDao getStructureDao();

    public abstract ReviewDao getReviewDao();

    public static DaoFactory getDaoFactory(Context context){
        DaoFactory.context=context;
        try {
            daoType= Util.getProperty("dao.type", context);
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (daoType) {
            case "Spring" : {
                return new SpringDaoFactory();
            }
            default : {
                return null;
            }
        }
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        DaoFactory.context = context;
    }
}
