package com.bigdatanyze.ems1.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.bigdatanyze.ems1.dao.ExpenseDao;
import com.bigdatanyze.ems1.database.AppDatabase;
import com.bigdatanyze.ems1.model.Expense;

import java.util.List;

public class ExpenseRepository {
	private ExpenseDao expenseDao;
	private LiveData<List<Expense>> allExpense;

	public ExpenseRepository(Application application) {
		AppDatabase db = AppDatabase.getDatabase(application);
		expenseDao = db.expenseDao();
		allExpense = expenseDao.getAllExpense(); // Use LiveData
	}

	public LiveData<List<Expense>> getAllExpense() {
		return allExpense;
	}

	public LiveData<Expense> getExpenseById(Long id) {
		return expenseDao.getExpenseById(id); // Return LiveData
	}

	public void insert(Expense expense) {
		AppDatabase.databaseWriteExecutor.execute(() -> expenseDao.insertExpense(expense));
	}

	public void update(Expense expense) {
		AppDatabase.databaseWriteExecutor.execute(() -> expenseDao.updateExpense(expense));
	}

	public void delete(Expense expense) {
		AppDatabase.databaseWriteExecutor.execute(() -> expenseDao.deleteExpense(expense));
	}
}
