package com.bigdatanyze.ems1.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bigdatanyze.ems1.model.BusinessProfile;
import com.bigdatanyze.ems1.repository.BusinessProfileRepository;

public class BusinessProfileViewModel extends AndroidViewModel {

	private final BusinessProfileRepository repository;
	private final LiveData<BusinessProfile> businessProfile;

	public BusinessProfileViewModel(Application application) {
		super(application);
		repository = new BusinessProfileRepository(application);
		businessProfile = repository.getBusinessProfile();
	}

	public LiveData<BusinessProfile> getBusinessProfile() {
		return businessProfile;
	}

	public void insert(BusinessProfile profile) {
		repository.insert(profile);
	}

	public void update(BusinessProfile profile) {
		repository.update(profile);
	}

	public void delete(BusinessProfile profile) {
		repository.delete(profile);
	}
}
