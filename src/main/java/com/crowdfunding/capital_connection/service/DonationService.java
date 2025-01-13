package com.crowdfunding.capital_connection.service;

import com.crowdfunding.capital_connection.repository.AccountRepository;
import com.crowdfunding.capital_connection.repository.DonationRepository;
import com.crowdfunding.capital_connection.repository.EntrepreneurshipRepository;
import com.crowdfunding.capital_connection.repository.entity.AccountEntity;
import com.crowdfunding.capital_connection.repository.entity.DonationEntity;
import com.crowdfunding.capital_connection.repository.entity.EntrepreneurshipEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
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

    @Transactional
    public DonationEntity createDonation(Long accountId, Long entrepreneurshipId, BigDecimal amount, Date date) {
        AccountEntity account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        EntrepreneurshipEntity entrepreneurship = entrepreneurshipRepository.findById(entrepreneurshipId)
                .orElseThrow(() -> new RuntimeException("Entrepreneurship not found"));

        DonationEntity donation = new DonationEntity();
        donation.setAccount(account);
        donation.setEntrepreneurship(entrepreneurship);
        donation.setAmount(amount);
        donation.setDate((java.sql.Date) date);

        return donationRepository.save(donation);
    }

    @Transactional
    public DonationEntity getDonationById(Long id) {
        return donationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found"));
    }

    @Transactional
    public List<DonationEntity> getAllDonations() {
        return donationRepository.findAll();
    }

    @Transactional
    public List<DonationEntity> getDonationsByAccountId(Long accountId) {
        AccountEntity account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        return donationRepository.findAll().stream()
                .filter(donation -> donation.getAccount().equals(account))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<DonationEntity> getDonationsByEntrepreneurshipId(Long entrepreneurshipId) {
        EntrepreneurshipEntity entrepreneurship = entrepreneurshipRepository.findById(entrepreneurshipId)
                .orElseThrow(() -> new RuntimeException("Entrepreneurship not found"));

        return donationRepository.findAll().stream()
                .filter(donation -> donation.getEntrepreneurship().equals(entrepreneurship))
                .collect(Collectors.toList());
    }

    @Transactional
    public DonationEntity updateDonation(Long id, BigDecimal amount, Date date) {
        DonationEntity donation = donationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found"));

        donation.setAmount(amount);
        donation.setDate((java.sql.Date) date);

        return donationRepository.save(donation);
    }

    @Transactional
    public void deleteDonation(Long id) {
        DonationEntity donation = donationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found"));

        donationRepository.delete(donation);
    }

}
