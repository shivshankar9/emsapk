package com.bigdatanyze.ems1.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.bigdatanyze.ems1.model.BusinessProfile;

@Dao
public interface BusinessProfileDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE) // Replace existing profile if conflicts
	void insert(BusinessProfile profile);

	@Update
	void updateBusinessProfile(BusinessProfile profile); // Update method for the profile

	@Query("SELECT * FROM business_profile LIMIT 1")
	LiveData<BusinessProfile> getBusinessProfile();
}
