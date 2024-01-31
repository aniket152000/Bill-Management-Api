package com.example.billmanagementapi.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.billmanagementapi.entity.Bill;
import com.example.billmanagementapi.exceptions.ResourceNotFoundException;
import com.example.billmanagementapi.repository.BillsRepository;

@Service
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	private BillsRepository expenseRepo;
	
	@Autowired
	private UserService userService;
	
	@Override
	public Page<Bill> getAllBills(Pageable page) {
		return expenseRepo.findByUserId(userService.getLoggedInUser().getId(), page);
	}

	@Override
	public Bill getBillsById(Long id){
		Optional<Bill> expense = expenseRepo.findByUserIdAndId(userService.getLoggedInUser().getId(), id);
		if (expense.isPresent()) {
			return expense.get();
		}
		throw new ResourceNotFoundException("Expense is not found for the id "+id);
	}

	@Override
	public void deleteBillsById(Long id) {
		Bill expense = getBillsById(id);
		expenseRepo.delete(expense);
	}

	@Override
	public Bill saveBillDetails(Bill expense) {
		expense.setUser(userService.getLoggedInUser());
		return expenseRepo.save(expense);
	}

	@Override
	public Bill updateBillDetails(Long id, Bill expense){
		Bill existingExpense = getBillsById(id);
		existingExpense.setName(expense.getName() != null ? expense.getName() : existingExpense.getName());
		existingExpense.setDescription(expense.getDescription() != null ? expense.getDescription() : existingExpense.getDescription());
		existingExpense.setCategory(expense.getCategory() != null ? expense.getCategory() : existingExpense.getCategory());
		existingExpense.setDate(expense.getDate() != null ? expense.getDate() : existingExpense.getDate());
		existingExpense.setAmount(expense.getAmount() != null ? expense.getAmount() : existingExpense.getAmount());
		return expenseRepo.save(existingExpense);
	}

	@Override
	public List<Bill> readByCategory(String category, Pageable page) {
		return expenseRepo.findByUserIdAndCategory(userService.getLoggedInUser().getId(), category, page).toList();
	}

	@Override
	public List<Bill> readByName(String keyword, Pageable page) {
		return expenseRepo.findByUserIdAndNameContaining(userService.getLoggedInUser().getId(), keyword, page).toList();
	}

	@Override
	public List<Bill> readByDate(Date startDate, Date endDate, Pageable page) {
		
		if (startDate == null) {
			startDate = new Date(0);
		}
		
		if (endDate == null) {
			endDate = new Date(System.currentTimeMillis());
		}
		
		return expenseRepo.findByUserIdAndDateBetween(userService.getLoggedInUser().getId(), startDate, endDate, page).toList();
	}

}



























