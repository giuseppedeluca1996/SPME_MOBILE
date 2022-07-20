package com.example.covid19.dao;


import com.example.covid19.dao.impl.SpringReviewDao;
import com.example.covid19.dao.impl.SpringStructureDao;
import com.example.covid19.dao.impl.SpringUserDao;

public class SpringDaoFactory extends DaoFactory {

    @Override
    public UserDao getUserDao() {
        return SpringUserDao.getInstance(context);
    }

    @Override
    public StructureDao getStructureDao() {
        return SpringStructureDao.getInstance(context);
    }

    @Override
    public ReviewDao getReviewDao() {
        return SpringReviewDao.getInstance(context);
    }

}
