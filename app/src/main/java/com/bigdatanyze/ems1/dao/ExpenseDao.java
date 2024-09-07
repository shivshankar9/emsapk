package com.bigdatanyze.ems1.dao;

import com.bigdatanyze.ems1.model.Expense;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.lifecycle.LiveData;
import java.util.List;

@Dao
public interface ExpenseDao {

	@Query("SELECT * FROM expense")
	LiveData<List<Expense>> getAllExpense(); // Return LiveData

	@Query("SELECT * FROM expense WHERE id = :id")
	LiveData<Expense> getExpenseById(Long id); // Return LiveData

	@Insert
	void insertExpense(Expense expense);

	@Update
	void updateExpense(Expense expense);

	@Delete
	void deleteExpense(Expense expense);
}
