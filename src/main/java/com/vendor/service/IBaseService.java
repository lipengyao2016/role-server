package com.vendor.service;

import com.vendor.utils.DataNotFoundException;

import java.util.List;

public interface IBaseService<T,QY_T> {
     public T create(T obj);
     public  T get(String uuid);
     public List<T> list(QY_T queryObj);
     public  T update(String uuid,T updateObj) throws DataNotFoundException;
     public  T delete(String uuid);
}
