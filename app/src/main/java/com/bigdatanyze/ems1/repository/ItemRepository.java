package com.bigdatanyze.ems1.repository;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import com.bigdatanyze.ems1.database.AppDatabase;
import com.bigdatanyze.ems1.model.Item;
import com.bigdatanyze.ems1.dao.ItemDao;
import java.util.List;

public class ItemRepository {

	private ItemDao itemDao;
	private LiveData<List<Item>> allItems;

	public ItemRepository(Application application) {
		AppDatabase database = AppDatabase.getDatabase(application);
		itemDao = database.itemDao();
		allItems = itemDao.getAllItems();
	}

	public void insert(Item item) {
		new InsertItemAsyncTask(itemDao).execute(item);
	}

	public LiveData<List<Item>> getAllItems() {
		return allItems;
	}

	private static class InsertItemAsyncTask extends AsyncTask<Item, Void, Void> {
		private ItemDao itemDao;

		private InsertItemAsyncTask(ItemDao itemDao) {
			this.itemDao = itemDao;
		}

		@Override
		protected Void doInBackground(Item... items) {
			itemDao.insert(items[0]);
			return null;
		}
	}
}
