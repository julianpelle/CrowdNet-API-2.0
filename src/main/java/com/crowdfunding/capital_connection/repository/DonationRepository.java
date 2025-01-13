package com.crowdfunding.capital_connection.repository;

import com.crowdfunding.capital_connection.repository.entity.DonationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationRepository extends JpaRepository<DonationEntity, Long> {

}
