package com.crowdfunding.capital_connection.service;

import com.crowdfunding.capital_connection.exception.AccountNotFoundException;
import com.crowdfunding.capital_connection.repository.AccountRepository;
import com.crowdfunding.capital_connection.repository.EntrepreneurshipRepository;
import com.crowdfunding.capital_connection.repository.entity.AccountEntity;
import com.crowdfunding.capital_connection.repository.entity.EntrepreneurshipEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EntrepreneurshipRepository entrepreneurshipRepository;

    @Transactional
    public void addFavorite(Long userId, Long entrepreneurshipId) {
        AccountEntity account = accountRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User dont finded"));
        EntrepreneurshipEntity entrepreneurship = entrepreneurshipRepository.findById(entrepreneurshipId)
                .orElseThrow(() -> new RuntimeException("Entrepreneurship dont finded"));

        account.getFavoriteEntrepreneurships().add(entrepreneurship);
        accountRepository.save(account);
    }

    @Transactional
    public void removeFavorite(Long userId, Long entrepreneurshipId) {
        AccountEntity account = accountRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User dont finded"));
        EntrepreneurshipEntity entrepreneurship = entrepreneurshipRepository.findById(entrepreneurshipId)
                .orElseThrow(() -> new RuntimeException("Entrepreneurship dont finded"));
        account.getFavoriteEntrepreneurships().remove(entrepreneurship);
        accountRepository.save(account);
    }

    @Transactional
    public List<EntrepreneurshipEntity> getFavorites(Long userId) {
        AccountEntity account = accountRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User dont finded"));
        return account.getFavoriteEntrepreneurships();
    }

    @Transactional
    public AccountEntity getAccountByID(Long userId) {
        return accountRepository.findById(userId)
                .orElseThrow(() -> new AccountNotFoundException("Account does not exist with ID: " + userId));
    }



}
