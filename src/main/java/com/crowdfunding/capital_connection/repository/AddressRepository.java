package com.crowdfunding.capital_connection.repository;

import com.crowdfunding.capital_connection.repository.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
}
