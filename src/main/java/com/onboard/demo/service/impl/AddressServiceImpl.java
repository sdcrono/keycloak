package com.onboard.demo.service.impl;

import com.onboard.demo.model.Address;
import com.onboard.demo.repository.AddressRepository;
import com.onboard.demo.repository.AddressRepository;
import com.onboard.demo.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<Address> find(String filter) {
        return addressRepository.findAll();
    }

    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    @Override
    public Address get(Long id) {
        return addressRepository.getOne(id);
    }

    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address update(Long id, Address address) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isPresent()) {
            return null;
        }
        address.setId(id);
        return addressRepository.save(address);
    }

    @Override
    public boolean delete(Long id) {
        addressRepository.deleteById(id);
        return true;
    }
}
