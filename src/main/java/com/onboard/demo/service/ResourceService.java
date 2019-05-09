package com.onboard.demo.service;

import com.onboard.demo.error.BadRequestException;
import com.onboard.demo.model.Resource;
import com.onboard.demo.model.request.ResourceRequest;

import java.util.List;

public interface ResourceService {

    List<Resource> find(String filter);

    List<Resource> findAll();

    Resource get(Long id);

    Resource save(ResourceRequest entity) throws BadRequestException;

    Resource update(Long id, Resource entity);

    boolean delete(Long id);

}
