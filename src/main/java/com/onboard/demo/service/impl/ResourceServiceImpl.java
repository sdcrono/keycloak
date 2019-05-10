package com.onboard.demo.service.impl;

import com.onboard.demo.error.BadRequestException;
import com.onboard.demo.error.ResourceNotFoundException;
import com.onboard.demo.model.Category;
import com.onboard.demo.model.Email;
import com.onboard.demo.model.Phone;
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

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

//    @Override
//    public List<Resource> find(String filter) {
//        return resourceRepository.findAll();
//    }

    @Override
    public List<Resource> findAll() {
        return resourceRepository.findAll();
    }

    @Override
    public Resource get(Long id) throws ResourceNotFoundException {
        try(Resource resource = resourceRepository.getOne(id)) {
            return resource;
        } catch (Exception e) {
            throw new ResourceNotFoundException("Resource is not found", e);
        }
    }

    @Override
    public Resource save(ResourceRequest resourceRequest) throws Exception {
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

        resource.setPhones(getPhones(resourceRequest));

        resource.setEmails(getEmails(resourceRequest));

        typeRepository.findOneByName(resourceRequest.getType()).ifPresent(resource::setType);

        userRepository.findOneByName(resourceRequest.getUser()).ifPresent(resource::setUser);

        resourceRepository.save(resource);

        return resource;
    }

    @Override
    public Resource update(Long id, ResourceRequest resourceRequest) throws Exception {
        Optional<Resource> optionalResource = resourceRepository.findById(id);
        if (!optionalResource.isPresent()) {
            throw new ResourceNotFoundException("Resource is not found");
        } else {
            Resource resource = optionalResource.get();
            Optional.ofNullable(resourceRequest.getName()).ifPresent(resource::setName);
            resource.setAddress(resourceRequest.getAddress());
            resource.setActive(resourceRequest.getActive());
            resource.setPhones(getPhones(resourceRequest));
            resource.setEmails(getEmails(resourceRequest));

            categoryRepository.findOneByName(resourceRequest.getCategory()).ifPresent(resource::setCategory);

            typeRepository.findOneByName(resourceRequest.getType()).ifPresent(resource::setType);

            userRepository.findOneByName(resourceRequest.getUser()).ifPresent(resource::setUser);

            resource.setId(id);

            resourceRepository.save(resource);

            return resource;
        }
    }

    private Set<Email> getEmails(ResourceRequest resourceRequest) {
        return Optional
                .ofNullable(resourceRequest.getEmail())
                .map(emails -> emails
                        .stream()
                        .map(Email::new)
                        .collect(Collectors.toSet()))
                .orElseGet(Collections::emptySet);
    }

    @Override
    public boolean delete(Long id) throws ResourceNotFoundException {
        try {
            resourceRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new ResourceNotFoundException("Resource is not found", e);
        }
    }

    private Set<Phone> getPhones(ResourceRequest resourceRequest) {
        return Optional
                .ofNullable(resourceRequest.getPhone())
                .map(phones -> phones
                        .stream()
                        .map(Phone::new)
                        .collect(Collectors.toSet()))
                .orElseGet(Collections::emptySet);
    }
}
