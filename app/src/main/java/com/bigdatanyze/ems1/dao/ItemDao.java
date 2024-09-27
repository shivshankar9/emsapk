package com.bigdatanyze.ems1.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.bigdatanyze.ems1.model.Item;
import java.util.List;

@Dao
public interface ItemDao {

	@Insert
	void insert(Item item);

	@Query("SELECT * FROM item_table ORDER BY itemName ASC")
	LiveData<List<Item>> getAllItems();
}
