package com.crowdfunding.capital_connection.model.mapper;


import com.crowdfunding.capital_connection.controller.dto.AddressRequest;
import com.crowdfunding.capital_connection.repository.entity.AddressEntity;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public AddressEntity toEntity(AddressRequest dto) {
        AddressEntity address = new AddressEntity();
        address.setId(dto.getId());
        address.setStreet(dto.getStreet());
        address.setNumber(dto.getNumber());
        address.setLocality(dto.getLocality());
        address.setProvince(dto.getProvince());
        address.setType(dto.getType());
        return address;
    }

    public AddressRequest toDto(AddressEntity entity) {
        AddressRequest dto = new AddressRequest();
        dto.setId(entity.getId());
        dto.setStreet(entity.getStreet());
        dto.setNumber(entity.getNumber());
        dto.setLocality(entity.getLocality());
        dto.setProvince(entity.getProvince());
        dto.setType(entity.getType());
        return dto;
    }
}