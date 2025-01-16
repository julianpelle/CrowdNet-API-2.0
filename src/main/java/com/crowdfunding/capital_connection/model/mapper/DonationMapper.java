package com.crowdfunding.capital_connection.model.mapper;

import com.crowdfunding.capital_connection.controller.DonationController;
import com.crowdfunding.capital_connection.controller.dto.AddressRequest;
import com.crowdfunding.capital_connection.controller.dto.DonationRequest;
import com.crowdfunding.capital_connection.controller.dto.EntrepreneurshipRequest;
import com.crowdfunding.capital_connection.repository.entity.AccountEntity;
import com.crowdfunding.capital_connection.repository.entity.AddressEntity;
import com.crowdfunding.capital_connection.repository.entity.DonationEntity;
import com.crowdfunding.capital_connection.repository.entity.EntrepreneurshipEntity;
import com.crowdfunding.capital_connection.service.AccountService;
import com.crowdfunding.capital_connection.service.EntrepreneurshipService;
import org.springframework.stereotype.Component;

@Component
public class DonationMapper {

    public DonationEntity toEntity(DonationRequest donationRequest) {
        DonationEntity donationEntity = new DonationEntity();
        donationEntity.setId(donationRequest.getId());
        donationEntity.setAmount(donationRequest.getAmount());
        donationEntity.setDate(donationRequest.getDate());
        donationEntity.setIsActivated(true);



        EntrepreneurshipService entrepreneurshipService = new EntrepreneurshipService();
        EntrepreneurshipRequest entrepreneurshipRequest = entrepreneurshipService.getEntrepreneurshipById(donationRequest.getId_entrepreneurship());
        EntrepreneurshipMapper emapper = new EntrepreneurshipMapper();
        EntrepreneurshipEntity entrepreneurshipEntity = emapper.toEntity(entrepreneurshipRequest);
        donationEntity.setEntrepreneurship(entrepreneurshipEntity);

        AccountService accountService = new AccountService();
        AccountEntity accountEntity = accountService.getAccountById(donationRequest.getId_user());
        donationEntity.setAccount(accountEntity);


        return donationEntity;
    }

    public DonationRequest toDto(DonationEntity donationEntity) {
        DonationRequest dto = new DonationRequest();
        dto.setId(donationEntity.getId());
        dto.setDate(donationEntity.getDate());
        dto.setAmount(donationEntity.getAmount());
        dto.setIsActivated(donationEntity.getIsActivated());
        dto.setId_user(donationEntity.getAccount().getId());
        dto.setId_entrepreneurship(donationEntity.getEntrepreneurship().getId());
        return dto;
    }
}
