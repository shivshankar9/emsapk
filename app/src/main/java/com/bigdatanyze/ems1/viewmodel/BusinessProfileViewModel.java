package com.bigdatanyze.ems1.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bigdatanyze.ems1.model.BusinessProfile;
import com.bigdatanyze.ems1.repository.BusinessProfileRepository;

public class BusinessProfileViewModel extends AndroidViewModel {
	private BusinessProfileRepository repository;
	private LiveData<BusinessProfile> businessProfile;

	// Constructor to initialize repository and LiveData
	public BusinessProfileViewModel(Application application) {
		super(application);
		repository = new BusinessProfileRepository(application);  // Pass Application context to repository
		businessProfile = repository.getBusinessProfile();
	}

	// Getter for BusinessProfile LiveData
	public LiveData<BusinessProfile> getBusinessProfile() {
		return businessProfile;
	}

	// Update BusinessProfile data
	public void updateBusinessProfile(BusinessProfile profile) {
		repository.updateBusinessProfile(profile);
	}

	// Insert new or update existing BusinessProfile in the database
	public void saveBusinessProfile(BusinessProfile profile) {
		repository.insertOrUpdateBusinessProfile(profile);
	}
}
