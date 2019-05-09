package com.onboard.demo.service;

import java.util.List;

public interface CRUDService<V> {

    List<V> find(String filter);

    List<V> findAll();

    V get(Long id);

    V save(V entity);

    V update(Long id, V entity);

    boolean delete(Long id);
}
