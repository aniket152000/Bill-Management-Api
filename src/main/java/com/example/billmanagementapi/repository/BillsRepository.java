package com.example.billmanagementapi.repository;

import java.sql.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.billmanagementapi.entity.Bill;

@Repository
public interface BillsRepository extends JpaRepository<Bill, Long> {
	
	Page<Bill> findByUserIdAndCategory(Long userId, String category, Pageable page);
	
	Page<Bill> findByUserIdAndNameContaining(Long userId, String keyword, Pageable page);
	
	Page<Bill> findByUserIdAndDateBetween(Long userId, Date startDate, Date endDate, Pageable page);
	
	Page<Bill> findByUserId(Long userId, Pageable page);
	
	Optional<Bill> findByUserIdAndId(Long userId, Long expenseId);

	
}
