package com.example.covid19.dao;


import com.example.covid19.model.Review;

import java.util.List;
import java.util.Map;

public abstract class ReviewDao implements AbstractDao<Review> {


    @Override
    public  abstract boolean save(Review entity);
    @Override
    public abstract Review update(Review newEntity, Integer id);

    @Override
    public abstract void delete(Integer id);

    @Override
    public abstract Review getById(Integer id);

    @Override
    public abstract Map<String, Object> getAll(Integer page, Integer size);

    public abstract  List<Review> getAllByIdStructure(Integer idStructure);

    public abstract Double getAvgRating(Integer idStructure);

}
