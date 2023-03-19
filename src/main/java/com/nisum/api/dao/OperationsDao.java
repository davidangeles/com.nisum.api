package com.nisum.api.dao;

import java.util.List;
import java.util.Optional;

public interface OperationsDao<T> {

  List<T> findAll();

  Optional<T> findOne(String attributeId, String value);

  T saveOrUpdate(T t);

  void delete(T t);

}
