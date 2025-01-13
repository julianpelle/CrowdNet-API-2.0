package com.crowdfunding.capital_connection.repository;

import com.crowdfunding.capital_connection.repository.entity.EntrepreneurshipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntrepreneurshipRepository extends JpaRepository<EntrepreneurshipEntity, Long> {
}
