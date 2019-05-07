package com.onboard.demo.service;

import com.onboard.demo.model.Resource;
import com.onboard.demo.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Override
    public List<Resource> find(String filter) {
        return resourceRepository.findAll();
    }

    @Override
    public List<Resource> findAll() {
        return resourceRepository.findAll();
    }

    @Override
    public Resource get(Long id) {
        return resourceRepository.getOne(id);
    }

    @Override
    public Resource save(Resource resource) {
        return resourceRepository.save(resource);
    }

    @Override
    public Resource update(Long id, Resource resource) {
        Optional<Resource> optionalResource = resourceRepository.findById(id);
        if (optionalResource.isPresent()) {
            return null;
        }
        resource.setId(id);
        return resourceRepository.save(resource);
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
