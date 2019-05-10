package com.onboard.demo.service;

import com.onboard.demo.error.ResourceNotFoundException;
import com.onboard.demo.model.Resource;
import com.onboard.demo.model.request.ResourceRequest;

import java.util.List;

public interface ResourceService {

//    List<Resource> find(String filter);

    List<Resource> findAll();

    Resource get(Long id) throws ResourceNotFoundException;

    Resource save(ResourceRequest entity) throws Exception;

    Resource update(Long id, ResourceRequest entity) throws Exception;

    boolean delete(Long id) throws ResourceNotFoundException;

}
