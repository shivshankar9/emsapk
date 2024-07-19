import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bigdatanyze.ems1.adapter.EmployeeAdapter;
import com.bigdatanyze.ems1.model.Employee;
import com.bigdatanyze.ems1.viewmodel.EmployeeViewModel;
import java.util.List;

public class MainActivity extends AppCompatActivity {
	private EmployeeViewModel employeeViewModel;
	private RecyclerView recyclerView;
	private EmployeeAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		recyclerView = findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		adapter = new EmployeeAdapter();
		recyclerView.setAdapter(adapter);

		employeeViewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);
		employeeViewModel.getAllEmployees().observe(this, new Observer<List<Employee>>() {
			@Override
			public void onChanged(List<Employee> employees) {
				adapter.setEmployeeList(employees);
			}
		});

		Button buttonAddEmployee = findViewById(R.id.button_add_employee);
		buttonAddEmployee.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, AddEmployeeActivity.class);
				startActivity(intent);
			}
		});
	}
}
