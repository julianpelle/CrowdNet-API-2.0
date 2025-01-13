package com.crowdfunding.capital_connection.model.mapper;

import com.crowdfunding.capital_connection.controller.dto.AccountRequest;
import com.crowdfunding.capital_connection.repository.entity.AccountEntity;
import com.crowdfunding.capital_connection.repository.entity.AddressEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AccountMapper {

    public AccountEntity toEntity(AccountRequest dto) {
        AccountEntity account = new AccountEntity();
        account.setId(dto.getId());
        account.setUsername(dto.getUsername());
        account.setPassword(dto.getPassword());
        account.setEmail(dto.getEmail());
        account.setName(dto.getName());
        account.setSurname(dto.getSurname());
        account.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth()));
        account.setYearsOfExperience(dto.getYearsOfExperience());
        account.setIndustry(dto.getIndustry());
        account.setWallet(dto.getWallet());

        if (dto.getId_address() != null) {
            AddressEntity address = new AddressEntity();
            address.setId(dto.getId_address());
            account.setAddress(address);
        }

        return account;
    }

    public AccountRequest toDto(AccountEntity entity) {
        AccountRequest dto = new AccountRequest();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setEmail(entity.getEmail());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setDateOfBirth(String.valueOf(entity.getDateOfBirth()));
        dto.setYearsOfExperience(entity.getYearsOfExperience());
        dto.setIndustry(entity.getIndustry());
        dto.setWallet(entity.getWallet());

        if (entity.getAddress() != null) {
            dto.setId_address(entity.getAddress().getId());
        }

        return dto;
    }
}