package com.example.billmanagementapi.service;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.billmanagementapi.entity.Bill;

public interface ExpenseService {
	
	Page<Bill> getAllBills(Pageable page);
	
	Bill getBillsById(Long id);
	
	void deleteBillsById(Long id);

	Bill saveBillDetails(Bill expense);
	
	Bill updateBillDetails(Long id, Bill expense);
	
	List<Bill> readByCategory(String category, Pageable page);
	
	List<Bill> readByName(String keyword, Pageable page); 
	
	List<Bill> readByDate(Date startDate, Date endDate, Pageable page);
}
