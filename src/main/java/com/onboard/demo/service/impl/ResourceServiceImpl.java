package com.onboard.demo.service.impl;

import com.onboard.demo.error.BadRequestException;
import com.onboard.demo.error.ResourceNotFoundException;
import com.onboard.demo.model.Category;
import com.onboard.demo.model.Resource;
import com.onboard.demo.model.request.ResourceRequest;
import com.onboard.demo.repository.CategoryRepository;
import com.onboard.demo.repository.ResourceRepository;
import com.onboard.demo.repository.TypeRepository;
import com.onboard.demo.repository.UserRepository;
import com.onboard.demo.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    public Resource save(ResourceRequest resourceRequest) throws BadRequestException {
        if (StringUtils.isEmpty(resourceRequest.getName())) {
            throw new BadRequestException("Resource name is not found");
        }

        Category category = categoryRepository.findOneByName(resourceRequest.getCategory())
                .orElseThrow(() -> new BadRequestException("Resource category is not found"));

        Resource resource = new Resource();
        resource.setName(resourceRequest.getName());
        resource.setAddress(resourceRequest.getAddress());
        resource.setActive(resourceRequest.getActive());
        resource.setCategory(category);

        typeRepository.findOneByName(resourceRequest.getType()).ifPresent(resource::setType);

        userRepository.findOneByName(resourceRequest.getUser()).ifPresent(resource::setUser);

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
