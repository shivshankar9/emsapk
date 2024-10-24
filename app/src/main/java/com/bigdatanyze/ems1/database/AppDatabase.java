package com.bigdatanyze.ems1.database;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.bigdatanyze.ems1.dao.*;
import com.bigdatanyze.ems1.model.*;
import com.bigdatanyze.ems1.util.Converters;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Employee.class, Expense.class, Invoice.class, InvoiceItem.class, Party.class, Item.class, BusinessProfile.class}, version = 10, exportSchema = true)
@TypeConverters({Converters.class})  // Register the Converters class here
public abstract class AppDatabase extends RoomDatabase {

	// Singleton instance
	private static AppDatabase instance;

	// Abstract methods to access DAOs
	public abstract EmployeeDao employeeDao();
	public abstract ExpenseDao expenseDao();
	public abstract InvoiceDao invoiceDao();
	public abstract PartyDao partyDao();
	public abstract ItemDao itemDao();
	public abstract InvoiceItemDao invoiceItemDao();
	public abstract BusinessProfileDao businessProfileDao();

	// Executor for database write operations
	private static final int NUMBER_OF_THREADS = 4;
	public static final ExecutorService databaseWriteExecutor =
			Executors.newFixedThreadPool(NUMBER_OF_THREADS);

	// Synchronized method to get the instance of the database
	public static synchronized AppDatabase getDatabase(Context context) {
		if (instance == null) {
			instance = Room.databaseBuilder(context.getApplicationContext(),
							AppDatabase.class, "app_database")  // Renamed to a generic name
					.fallbackToDestructiveMigration()
					.build();
		}
		return instance;
	}

}
