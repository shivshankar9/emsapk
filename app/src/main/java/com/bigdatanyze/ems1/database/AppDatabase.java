package com.bigdatanyze.ems1.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import com.bigdatanyze.ems1.model.Employee;
import com.bigdatanyze.ems1.dao.EmployeeDao;

import java.util.concurrent.Executor;

@Database(entities = {Employee.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

	public static Executor databaseWriteExecutor;

	public abstract EmployeeDao employeeDao();

	private static AppDatabase instance;

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
