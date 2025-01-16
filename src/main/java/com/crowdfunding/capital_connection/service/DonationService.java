package com.crowdfunding.capital_connection.service;

import com.crowdfunding.capital_connection.model.mapper.DonationMapper;
import com.crowdfunding.capital_connection.repository.AccountRepository;
import com.crowdfunding.capital_connection.repository.DonationRepository;
import com.crowdfunding.capital_connection.repository.EntrepreneurshipRepository;
import com.crowdfunding.capital_connection.repository.entity.DonationEntity;
import com.crowdfunding.capital_connection.controller.dto.DonationRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DonationService {

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EntrepreneurshipRepository entrepreneurshipRepository;

    @Autowired
    private DonationMapper donationMapper;

    /**
     * Crea una nueva donación a partir de un objeto DonationRequest.
     */
    @Transactional
    public DonationRequest createDonation(DonationRequest donationRequest) {
        accountRepository.findById(donationRequest.getId_user())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        entrepreneurshipRepository.findById(donationRequest.getId_entrepreneurship())
                .orElseThrow(() -> new RuntimeException("Entrepreneurship not found"));

        DonationEntity donationEntity = donationMapper.toEntity(donationRequest);

        DonationEntity savedEntity = donationRepository.save(donationEntity);

        return donationMapper.toDto(savedEntity);
    }

    /**
     * Obtiene todas las donaciones realizadas por un usuario específico.
     */
    @Transactional
    public List<DonationRequest> getDonationsByAccountId(Long accountId) {
        accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        return donationRepository.findAll().stream()
                .filter(donation -> donation.getAccount().getId().equals(accountId))
                .map(donationMapper::toDto)
                .collect(Collectors.toList());
    }
}
