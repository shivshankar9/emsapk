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

	// Query to fetch the business profile, returning LiveData for observing changes
	@Query("SELECT * FROM business_profile LIMIT 1")
	LiveData<BusinessProfile> getBusinessProfile();

	// Synchronous version of fetching the business profile, used for checking its existence in background threads
	@Query("SELECT * FROM business_profile LIMIT 1")
	BusinessProfile getBusinessProfileSync();

	// Insert the business profile, replacing if a conflict occurs (e.g., same primary key)
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insert(BusinessProfile profile);

	// Update an existing business profile
	@Update
	void update(BusinessProfile profile);
}
