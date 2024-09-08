package com.bigdatanyze.ems1.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.bigdatanyze.ems1.dao.EmployeeDao;
import com.bigdatanyze.ems1.dao.ExpenseDao;
import com.bigdatanyze.ems1.model.Employee;
import com.bigdatanyze.ems1.model.Expense;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Employee.class, Expense.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

	// Singleton instance
	private static AppDatabase instance;

	// Abstract methods to access DAOs
	public abstract EmployeeDao employeeDao();
	public abstract ExpenseDao expenseDao();

	// Executor for database write operations
	private static final int NUMBER_OF_THREADS = 4;
	public static final ExecutorService databaseWriteExecutor =
			Executors.newFixedThreadPool(NUMBER_OF_THREADS);

	// Synchronized method to get the instance of the database
	public static synchronized AppDatabase getDatabase(Context context) {
		if (instance == null) {
			instance = Room.databaseBuilder(context.getApplicationContext(),
							AppDatabase.class, "employee_database")
					.fallbackToDestructiveMigration()
					.build();
		}
		return instance;
	}
}
