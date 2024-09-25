package com.bigdatanyze.ems1.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.bigdatanyze.ems1.model.Item;
import com.bigdatanyze.ems1.repository.ItemRepository;
import java.util.List;

public class ItemViewModel extends AndroidViewModel {

	private ItemRepository repository;
	private LiveData<List<Item>> allItems;

	public ItemViewModel(Application application) {
		super(application);
		repository = new ItemRepository(application);
		allItems = repository.getAllItems();
	}

	public void insert(Item item) {
		repository.insert(item);
	}

	public LiveData<List<Item>> getAllItems() {
		return allItems;
	}
}
