package home.project.group.financetracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import home.project.group.financetracker.EntityClass.ExpenseTransactionModel;

public class MainActivity extends AppCompatActivity {

    EditText expenseName, amount, category;
    Button save, getData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_transaction, R.id.navigation_calendar)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        expenseName = findViewById(R.id.txtExpenseName);
        amount = findViewById(R.id.txtAmount);
        category = findViewById(R.id.txtCategory);
        save = findViewById(R.id.btnSave);
        getData = findViewById(R.id.btnGetData);

        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), GetData.class));
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }

    private void saveData() {
        String expenseName_txt = expenseName.getText().toString().trim();
        String amount_txt = amount.getText().toString().trim();
        String category_txt = category.getText().toString().trim();

        ExpenseTransactionModel model = new ExpenseTransactionModel();

        model.setExpenseName(expenseName_txt);
        model.setAmount(amount_txt);
        model.setCategory(category_txt);

        DatabaseClass.getDatabase(getApplicationContext()).getDao().insertAllData(model);

        expenseName.setText("");
        amount.setText("");
        category.setText("");
        Toast.makeText(this, "Data successfully saved", Toast.LENGTH_SHORT).show();

    }


}