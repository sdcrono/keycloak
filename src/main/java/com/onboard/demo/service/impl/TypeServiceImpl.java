package com.onboard.demo.service.impl;

import com.onboard.demo.model.Type;
import com.onboard.demo.repository.TypeRepository;
import com.onboard.demo.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRepository typeRepository;

    @Override
    public List<Type> find(String filter) {
        return typeRepository.findAll();
    }

    @Override
    public List<Type> findAll() {
        return typeRepository.findAll();
    }

    @Override
    public Type get(Long id) {
        return typeRepository.getOne(id);
    }

    @Override
    public Type save(Type type) {
        return typeRepository.save(type);
    }

    @Override
    public Type update(Long id, Type type) {
        Optional<Type> optionalType = typeRepository.findById(id);
        if (optionalType.isPresent()) {
            return null;
        }
        type.setId(id);
        return typeRepository.save(type);
    }

    @Override
    public boolean delete(Long id) {
        typeRepository.deleteById(id);
        return true;
    }
}
