package com.bigdatanyze.ems1.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bigdatanyze.ems1.model.BusinessProfile;

public class BusinessProfileRepository {
	private MutableLiveData<BusinessProfile> businessProfile;

	public BusinessProfileRepository() {
		businessProfile = new MutableLiveData<>();
		fetchBusinessProfile();
	}

	private void fetchBusinessProfile() {
		// Simulate fetching data from a database or API
		BusinessProfile profile = new BusinessProfile();
		profile.setBusinessName("Sample Business");
		profile.setLogoUri("your_logo_uri"); // Replace with an actual URI if you have one
		profile.setCompanyAddress("123 Main St, City, Country");
		profile.setPhoneNumber("123-456-7890");
		profile.setEmail("contact@samplebusiness.com");
		businessProfile.setValue(profile);
	}

	public LiveData<BusinessProfile> getBusinessProfile() {
		return businessProfile;
	}

	public void updateBusinessProfile(BusinessProfile profile) {
		businessProfile.setValue(profile);
	}
}
