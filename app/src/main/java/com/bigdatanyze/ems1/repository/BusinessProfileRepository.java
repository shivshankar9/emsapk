package com.bigdatanyze.ems1.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.bigdatanyze.ems1.dao.BusinessProfileDao;
import com.bigdatanyze.ems1.database.AppDatabase;
import com.bigdatanyze.ems1.model.BusinessProfile;

import java.util.concurrent.ExecutorService;

public class BusinessProfileRepository {

	private final BusinessProfileDao businessProfileDao;
	private final ExecutorService executorService;

	// Constructor that takes the application context to get the database instance
	public BusinessProfileRepository(Context context) {
		AppDatabase db = AppDatabase.getDatabase(context);
		businessProfileDao = db.businessProfileDao();
		executorService = AppDatabase.databaseWriteExecutor;
	}
	public void insertOrUpdateBusinessProfile(BusinessProfile profile) {
		AppDatabase.databaseWriteExecutor.execute(() -> {
			if (businessProfileDao.getBusinessProfileSync() != null) {
				businessProfileDao.update(profile);
			} else {
				businessProfileDao.insert(profile);
			}
		});
	}


	// Fetch the business profile from the database
	public LiveData<BusinessProfile> getBusinessProfile() {
		return businessProfileDao.getBusinessProfile();
	}

	// Update the business profile in the database
	public void updateBusinessProfile(BusinessProfile profile) {
		executorService.execute(() -> {
			// Check if the profile exists, and either update or insert it
			BusinessProfile existingProfile = businessProfileDao.getBusinessProfileSync();
			if (existingProfile != null) {
				profile.setId(existingProfile.getId());  // Ensure the profile has the same ID if updating
				businessProfileDao.update(profile);
			} else {
				businessProfileDao.insert(profile);
			}
		});

	}
}
