package com.bigdatanyze.ems1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.bigdatanyze.ems1.model.BusinessProfile;
import com.bigdatanyze.ems1.viewmodel.BusinessProfileViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class EditBusinessProfileActivity extends AppCompatActivity {
	private BusinessProfileViewModel viewModel;
	private TextInputEditText etBusinessName, etCompanyAddress, etPhoneNumber, etEmail;
	private MaterialButton btnSave;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_business_profile);

		etBusinessName = findViewById(R.id.et_business_name);
		etCompanyAddress = findViewById(R.id.et_company_address);
		etPhoneNumber = findViewById(R.id.et_phone_number);
		etEmail = findViewById(R.id.et_email);
		btnSave = findViewById(R.id.btn_save);

		// Initialize ViewModel
		viewModel = new ViewModelProvider(this).get(BusinessProfileViewModel.class);

		// Observe the LiveData from the ViewModel
		viewModel.getBusinessProfile().observe(this, businessProfile -> {
			if (businessProfile != null) {
				etBusinessName.setText(businessProfile.getBusinessName());
				etCompanyAddress.setText(businessProfile.getCompanyAddress());
				etPhoneNumber.setText(businessProfile.getPhoneNumber());
				etEmail.setText(businessProfile.getEmail());
			}
		});

		btnSave.setOnClickListener(v -> {
			String businessName = etBusinessName.getText().toString();
			String companyAddress = etCompanyAddress.getText().toString();
			String phoneNumber = etPhoneNumber.getText().toString();
			String email = etEmail.getText().toString();

			if (!businessName.isEmpty()) {
				BusinessProfile updatedProfile = new BusinessProfile();
				updatedProfile.setBusinessName(businessName);
				updatedProfile.setCompanyAddress(companyAddress);
				updatedProfile.setPhoneNumber(phoneNumber);
				updatedProfile.setEmail(email);
				// Optionally set the logo URI here as well

				// Update the ViewModel
				viewModel.updateBusinessProfile(updatedProfile);

				// Return updated profile
				Intent intent = new Intent();
				intent.putExtra("updatedProfile", updatedProfile);
				setResult(RESULT_OK, intent);
				finish();
			} else {
				// Show an error message if needed
			}
		});
	}
}
