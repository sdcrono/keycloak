package com.onboard.demo.service;

import com.onboard.demo.model.Resource;

import java.util.List;

public interface ResourceService {

    List<Resource> find(String filter);

    List<Resource> findAll();

    Resource get(Long id);

    Resource save(Resource entity);

    Resource update(Long id, Resource entity);

    boolean delete(Long id);

}
