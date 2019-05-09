package com.onboard.demo.service.impl;

import com.onboard.demo.model.Resource;
import com.onboard.demo.model.request.ResourceRequest;
import com.onboard.demo.repository.CategoryRepository;
import com.onboard.demo.repository.ResourceRepository;
import com.onboard.demo.repository.TypeRepository;
import com.onboard.demo.repository.UserRepository;
import com.onboard.demo.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private UserRepository userRepository;

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
    public Resource save(ResourceRequest resourceRequest) {

        Resource resource = new Resource();
        resource.setName(resourceRequest.getName());
        resource.setAddress(resourceRequest.getAddress());
        resource.setActive(resourceRequest.getActive());

        categoryRepository.findOneByName(resourceRequest.getCategory())
                .ifPresent(category -> resource.setCategory(category));

        typeRepository.findOneByName(resourceRequest.getType()).ifPresent(type -> resource.setType(type));

        userRepository.findOneByName(resourceRequest.getUser()).ifPresent(user -> resource.setUser(user));

        resourceRepository.save(resource);

        return resource;
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
        resourceRepository.deleteById(id);
        return true;
    }
}
