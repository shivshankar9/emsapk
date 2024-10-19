package com.bigdatanyze.ems1.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.bigdatanyze.ems1.model.BusinessProfile;
import com.bigdatanyze.ems1.repository.BusinessProfileRepository;

public class BusinessProfileViewModel extends ViewModel {
	private BusinessProfileRepository repository;
	private LiveData<BusinessProfile> businessProfile;

	public BusinessProfileViewModel() {
		repository = new BusinessProfileRepository();
		businessProfile = repository.getBusinessProfile();
	}

	public LiveData<BusinessProfile> getBusinessProfile() {
		return businessProfile;
	}

	public void updateBusinessProfile(BusinessProfile profile) {
		repository.updateBusinessProfile(profile);
	}
}
