package com.bigdatanyze.ems1.database;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DatabaseBackup {

	private static final String DATABASE_NAME = "app_database"; // Replace with your actual database name

	// Backup the database
	public static void backupDatabase(Context context) {
		try {
			File dbFile = context.getDatabasePath(DATABASE_NAME);
			File backupDir = new File(Environment.getExternalStorageDirectory(), "Documents/Finverge/backup");

			// Create the backup directory if it doesn't exist
			if (!backupDir.exists() && !backupDir.mkdirs()) {
				Log.e("DatabaseBackup", "Failed to create backup directory");
				return;
			}

			// Create a unique backup file name using timestamp
			String timestamp = String.valueOf(System.currentTimeMillis());
			File backupFile = new File(backupDir, "app_database_backup_" + timestamp + ".db");

			// Check if the database file exists
			if (!dbFile.exists()) {
				Log.e("DatabaseBackup", "Database file does not exist: " + dbFile.getAbsolutePath());
				return;
			}

			// Copy the database file to the backup location
			try (FileInputStream fis = new FileInputStream(dbFile);
			     FileOutputStream fos = new FileOutputStream(backupFile)) {

				byte[] buffer = new byte[1024];
				int length;

				while ((length = fis.read(buffer)) > 0) {
					fos.write(buffer, 0, length);
				}

				fos.flush();
				Log.d("DatabaseBackup", "Backup successful: " + backupFile.getAbsolutePath());
				Toast.makeText(context, "Backup saved to: " + backupFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
			}
		} catch (IOException e) {
			Log.e("DatabaseBackup", "Error during backup: " + e.getMessage());
		}
	}

	// Restore the database from a backup
	public static void restoreDatabase(Context context, Uri backupUri) {
		try {
			File dbFile = context.getDatabasePath(DATABASE_NAME);

			// Restore the database from the backup file
			try (InputStream inputStream = context.getContentResolver().openInputStream(backupUri);
			     FileOutputStream outputStream = new FileOutputStream(dbFile)) {

				if (inputStream == null) {
					Log.e("DatabaseBackup", "Input stream is null, failed to restore.");
					return;
				}

				byte[] buffer = new byte[1024];
				int length;

				while ((length = inputStream.read(buffer)) > 0) {
					outputStream.write(buffer, 0, length);
				}

				outputStream.flush();
				Log.d("DatabaseBackup", "Restore successful: " + dbFile.getAbsolutePath());
				Toast.makeText(context, "Database restored from: " + backupUri.getPath(), Toast.LENGTH_LONG).show();

				// Reinitialize the database to reflect restored data
				reloadDatabase(context);
			}
		} catch (IOException e) {
			Log.e("DatabaseBackup", "Error during restore: " + e.getMessage());
		}
	}

	// Reloads the database after restoration
	private static void reloadDatabase(Context context) {
		AppDatabase db = AppDatabase.getDatabase(context);
		Log.d("DatabaseBackup", "Database reloaded successfully.");
		Toast.makeText(context, "Database reloaded", Toast.LENGTH_SHORT).show();
	}
}