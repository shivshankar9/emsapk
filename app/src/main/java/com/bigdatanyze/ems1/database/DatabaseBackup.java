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
	private static final String BACKUP_FILE_NAME = "app_database_backup.db";

	public static void backupDatabase(Context context) {
		try {
			File dbFile = context.getDatabasePath(DATABASE_NAME);
			File backupDir = new File(Environment.getExternalStorageDirectory(), "Documents/Finverge/backup");

			// Create the backup directory if it doesn't exist
			if (!backupDir.exists()) {
				if (!backupDir.mkdirs()) {
					Log.e("DatabaseBackup", "Failed to create backup directory");
					return;
				}
			}

			File backupFile = new File(backupDir, BACKUP_FILE_NAME);

			// Check if the database file exists
			if (!dbFile.exists()) {
				Log.e("DatabaseBackup", "Database file does not exist: " + dbFile.getAbsolutePath());
				return;
			}

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

	public static void restoreDatabase(Context context, Uri backupUri) {
		try {
			File dbFile = context.getDatabasePath(DATABASE_NAME);
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
			}
		} catch (IOException e) {
			Log.e("DatabaseBackup", "Error during restore: " + e.getMessage());
		}
	}
}
