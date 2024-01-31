package com.example.billmanagementapi.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.billmanagementapi.entity.Bill;
import com.example.billmanagementapi.service.ExpenseService;
import jakarta.validation.Valid;

@RestController
public class BillController {

	@Autowired
	private ExpenseService expenseService;

	@GetMapping("/bills")
	public List<Bill> getAllBills(Pageable page) {
		return expenseService.getAllBills(page).toList();
	}

	@GetMapping("/bills/{id}")
	public Bill getBillsById(@PathVariable Long id) {
		return expenseService.getBillsById(id);
	}

	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@DeleteMapping("/bills")
	public void deleteBillsById(@RequestParam Long id) {
		expenseService.deleteBillsById(id);
	}

	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping("/bills")
	public Bill saveBillDetails(@Valid @RequestBody Bill expense) {
		return expenseService.saveBillDetails(expense);
	}

	@PutMapping("/bills/{id}")
	public Bill updateBillDetails(@RequestBody Bill expense, @PathVariable Long id) {
		return expenseService.updateBillDetails(id, expense);
	}

	@GetMapping("/bills/category")
	public List<Bill> getBillsByCategory(@RequestParam String category, Pageable page) {
		return expenseService.readByCategory(category, page);
	}

	@GetMapping("/bills/name")
	public List<Bill> getBillsByName(@RequestParam String keyword, Pageable page) {
		return expenseService.readByName(keyword, page);
	}

	@GetMapping("/bills/date")
	public List<Bill> getBillsByDates(@RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate,
			Pageable page) {
		return expenseService.readByDate(startDate, endDate, page);
	}
}
