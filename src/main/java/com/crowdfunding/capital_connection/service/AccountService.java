package com.crowdfunding.capital_connection.service;

import com.crowdfunding.capital_connection.controller.dto.AccountRequest;
import com.crowdfunding.capital_connection.controller.dto.AddressRequest;
import com.crowdfunding.capital_connection.exception.AccountNotFoundException;
import com.crowdfunding.capital_connection.repository.AccountRepository;
import com.crowdfunding.capital_connection.repository.AddressRepository;
import com.crowdfunding.capital_connection.repository.EntrepreneurshipRepository;
import com.crowdfunding.capital_connection.repository.entity.AccountEntity;
import com.crowdfunding.capital_connection.repository.entity.AddressEntity;
import com.crowdfunding.capital_connection.repository.entity.EntrepreneurshipEntity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private EntrepreneurshipRepository entrepreneurshipRepository;

    @Transactional
    public void addFavorite(Long userId, Long entrepreneurshipId) {
        AccountEntity account = accountRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        EntrepreneurshipEntity entrepreneurship = entrepreneurshipRepository.findById(entrepreneurshipId)
                .orElseThrow(() -> new RuntimeException("Entrepreneurship not found"));

        account.getFavoriteEntrepreneurships().add(entrepreneurship);
        accountRepository.save(account);
    }

    @Transactional
    public void removeFavorite(Long userId, Long entrepreneurshipId) {
        AccountEntity account = accountRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        EntrepreneurshipEntity entrepreneurship = entrepreneurshipRepository.findById(entrepreneurshipId)
                .orElseThrow(() -> new RuntimeException("Entrepreneurship not found"));
        account.getFavoriteEntrepreneurships().remove(entrepreneurship);
        accountRepository.save(account);
    }

    @Transactional
    public List<EntrepreneurshipEntity> getFavorites(Long userId) {
        AccountEntity account = accountRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return account.getFavoriteEntrepreneurships();
    }

    @Transactional
    public AccountEntity getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }

    @Transactional
    public List<AccountEntity> getAllAccounts() {
        List<AccountEntity> accounts = accountRepository.findAll();
        if (accounts.isEmpty()) {
            throw new AccountNotFoundException("No accounts found");
        }
        return accounts;
    }

    @Transactional
    public AccountEntity createAccount(AccountEntity accountEntity) {

        if (accountEntity.getAddress() != null) {
            AddressEntity address = accountEntity.getAddress();
            address = addressRepository.save(address);
            accountEntity.setAddress(address);
        }

        return accountRepository.save(accountEntity);
    }

    @Transactional
    public AccountEntity updateAccount(AccountEntity accountEntity) {
        if (accountEntity.getAddress() != null) {
            AddressEntity addressEntity = accountEntity.getAddress();
            addressRepository.save(addressEntity);
        }
        return accountRepository.save(accountEntity);
    }

    @Transactional
    public AddressEntity updateAddress(AddressEntity addressEntity) {
        return addressRepository.save(addressEntity);
    }

    @Transactional
    public AccountEntity updateAccountPartial(Long accountId, AccountRequest accountRequest) {
        AccountEntity accountEntity = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + accountId));

        // Actualizar los campos que no son nulos
        if (accountRequest.getUsername() != null) {
            accountEntity.setUsername(accountRequest.getUsername());
        }
        if (accountRequest.getEmail() != null) {
            accountEntity.setEmail(accountRequest.getEmail());
        }
        if (accountRequest.getName() != null) {
            accountEntity.setName(accountRequest.getName());
        }
        if (accountRequest.getSurname() != null) {
            accountEntity.setSurname(accountRequest.getSurname());
        }
        if (accountRequest.getDateOfBirth() != null) {
            accountEntity.setDateOfBirth(LocalDate.parse(accountRequest.getDateOfBirth()));
        }
        if (accountRequest.getYearsOfExperience() != null) {
            accountEntity.setYearsOfExperience(accountRequest.getYearsOfExperience());
        }
        if (accountRequest.getIndustry() != null) {
            accountEntity.setIndustry(accountRequest.getIndustry());
        }
        if (accountRequest.getWallet() != null) {
            accountEntity.setWallet(accountRequest.getWallet());
        }

        return accountRepository.save(accountEntity);
    }

    @Transactional
    public AddressEntity updateAddressPartial(Long accountId, AddressRequest addressRequest) {
        AccountEntity accountEntity = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + accountId));

        AddressEntity addressEntity = addressRepository.findByAccount(accountEntity)
                .orElseThrow(() -> new EntityNotFoundException("Address not found for account with id: " + accountId));

        // Actualizar los campos que no son nulos en Address
        if (addressRequest.getStreet() != null) {
            addressEntity.setStreet(addressRequest.getStreet());
        }
        if (addressRequest.getNumber() != 0) {
            addressEntity.setNumber(addressRequest.getNumber());
        }
        if (addressRequest.getLocality() != null) {
            addressEntity.setLocality(addressRequest.getLocality());
        }
        if (addressRequest.getProvince() != null) {
            addressEntity.setProvince(addressRequest.getProvince());
        }
        if (addressRequest.getType() != null) {
            addressEntity.setType(addressRequest.getType());
        }

        return addressRepository.save(addressEntity);
    }

    @Transactional
    public void deactivateAccountAndAddress(Long accountId) {
        AccountEntity accountEntity = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + accountId));

        accountEntity.deactivate();

        if (accountEntity.getAddress() != null) {
            AddressEntity addressEntity = accountEntity.getAddress();
            addressEntity.deactivate();
            addressRepository.save(addressEntity);
        }

        accountRepository.save(accountEntity);
    }
}
