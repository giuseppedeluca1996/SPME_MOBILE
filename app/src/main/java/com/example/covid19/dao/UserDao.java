package com.example.covid19.dao;

import com.example.covid19.model.User;

import java.util.Map;


public abstract class UserDao implements AbstractDao<User> {

    @Override
    public abstract boolean save(User entity);

    @Override
    public abstract User update(User newEntity, Integer id);

    @Override
    public abstract void delete(Integer id);

    @Override
    public abstract User getById(Integer id);

    @Override
    public abstract Map<String,Object> getAll(Integer page, Integer size);

    public abstract  Boolean checkDisponibilityEmail(String email);

    public abstract  Boolean checkDisponibilityUsername(String username);


}
