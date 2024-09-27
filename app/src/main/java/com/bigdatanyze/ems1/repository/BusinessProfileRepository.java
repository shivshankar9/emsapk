package com.bigdatanyze.ems1.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.bigdatanyze.ems1.dao.BusinessProfileDao;
import com.bigdatanyze.ems1.database.AppDatabase;
import com.bigdatanyze.ems1.model.BusinessProfile;

public class BusinessProfileRepository {
	private final BusinessProfileDao businessProfileDao; // Assume you have a DAO

	public BusinessProfileRepository(Application application) {
		AppDatabase db = AppDatabase.getDatabase(application); // Assuming a Room Database
		businessProfileDao = db.businessProfileDao(); // Getting the DAO instance
	}

	public LiveData<BusinessProfile> getBusinessProfile() {
		return businessProfileDao.getBusinessProfile(); // Fetch the business profile
	}

	public void insert(BusinessProfile profile) {
		AppDatabase.databaseWriteExecutor.execute(() -> businessProfileDao.insert(profile));
	}

	public void update(BusinessProfile profile) {
		AppDatabase.databaseWriteExecutor.execute(() -> businessProfileDao.update(profile));
	}

	public void delete(BusinessProfile profile) {
		AppDatabase.databaseWriteExecutor.execute(() -> businessProfileDao.delete(profile));
	}
}
