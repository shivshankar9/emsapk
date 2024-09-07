package com.bigdatanyze.ems1.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.bigdatanyze.ems1.model.Expense;
import com.bigdatanyze.ems1.repository.ExpenseRepository;

import java.util.List;

public class ExpenseViewModel extends AndroidViewModel {

	private final ExpenseRepository repository;
	private final LiveData<List<Expense>> allExpenses;

	// Constructor
	public ExpenseViewModel(@NonNull Application application) {
		super(application);
		repository = new ExpenseRepository(application);
		allExpenses = repository.getAllExpense();  // Fetching all expenses from repository
	}

	// Get all expenses
	public LiveData<List<Expense>> getAllExpenses() {
		return allExpenses;
	}

	// Get expense by ID
	public LiveData<Expense> getExpenseById(Long id) {
		return repository.getExpenseById(id);
	}

	// Insert a new expense
	public void insert(Expense expense) {
		repository.insert(expense);
	}

	// Update an existing expense
	public void update(Expense expense) {
		repository.update(expense);
	}

	// Delete an expense
	public void delete(Expense expense) {
		repository.delete(expense);
	}

	// Factory class to create instances of ExpenseViewModel
	public static class Factory extends ViewModelProvider.NewInstanceFactory {

		@NonNull
		private final Application application;

		// Constructor for the Factory
		public Factory(@NonNull Application application) {
			this.application = application;
		}

		@NonNull
		@Override
		@SuppressWarnings("unchecked")
		public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
			if (modelClass.isAssignableFrom(ExpenseViewModel.class)) {
				return (T) new ExpenseViewModel(application);
			}
			throw new IllegalArgumentException("Unknown ViewModel class");
		}
	}
}
