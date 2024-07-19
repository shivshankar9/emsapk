package com.bigdatanyze.ems1.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.bigdatanyze.ems1.dao.EmployeeDao;
import com.bigdatanyze.ems1.model.Employee;

@Database(entities = {Employee.class}, version = 1, exportSchema = false)
public abstract class EmployeeDatabase extends RoomDatabase {
	private static EmployeeDatabase instance;

	public abstract EmployeeDao employeeDao();

	public static synchronized EmployeeDatabase getInstance(Context context) {
		if (instance == null) {
			instance = Room.databaseBuilder(context.getApplicationContext(),
							EmployeeDatabase.class, "employee_database")
					.fallbackToDestructiveMigration()
					.build();
		}
		return instance;
	}
}
