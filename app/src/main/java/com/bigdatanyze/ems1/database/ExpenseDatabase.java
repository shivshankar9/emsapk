//package com.bigdatanyze.ems1.database;
//
//import android.content.Context;
//import androidx.room.Database;
//import androidx.room.Room;
//import androidx.room.RoomDatabase;
//
//import com.bigdatanyze.ems1.dao.ExpenseDao;
//import com.bigdatanyze.ems1.model.Expense;
//
//@Database(entities = {Expense.class}, version = 1, exportSchema = false)
//public abstract class ExpenseDatabase extends RoomDatabase {
//
//	// Singleton instance
//	private static ExpenseDatabase instance;
//
//	// Abstract method to access the DAO
//	public abstract ExpenseDao expenseDao();
//
//	// Synchronized method to get the instance of the database
//	public static synchronized ExpenseDatabase getInstance(Context context) {
//		if (instance == null) {
//			instance = Room.databaseBuilder(context.getApplicationContext(),
//							ExpenseDatabase.class, "expense_database")
//					.fallbackToDestructiveMigration()
//					.build();
//		}
//		return instance;
//	}
//}
