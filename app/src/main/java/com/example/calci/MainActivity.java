package com.example.calci;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_view, btn_add;
    EditText edit1, edit2;
    Switch switch1;
    ListView Lv_cust;

    ArrayAdapter customerArray;
    database db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit1 = (EditText) findViewById(R.id.edit1);
        edit2 = (EditText) findViewById(R.id.edit2);
        switch1 = (Switch) findViewById(R.id.switch1);
        Lv_cust = (ListView) findViewById(R.id.lview);
        btn_add = (Button) findViewById(R.id.btnadd);
        btn_view = (Button) findViewById(R.id.btn_viewall);
        db = new database(MainActivity.this);
        List<customermodel> everyone = db.getEveryone();
        ShowCustomers(db);

        btn_add.setOnClickListener((view) -> {
            customermodel cust_model;
            try {
                cust_model = new customermodel(-1, edit1.getText().toString(), Integer.parseInt(edit2.getText().toString()), switch1.isChecked());
                Toast.makeText(MainActivity.this, cust_model.toString(), Toast.LENGTH_SHORT).show();
            } catch (Exception E) {
                Toast.makeText(MainActivity.this, "Error creating customer", Toast.LENGTH_SHORT).show();
                cust_model = new customermodel(-1, "error", 0, false);
            }


            db = new database(MainActivity.this);

            boolean success = db.add_one(cust_model);
            Toast.makeText(MainActivity.this, "Success =" + success, Toast.LENGTH_SHORT).show();

            ShowCustomers(db);

        });

        btn_view.setOnClickListener((view) -> {

            database db = new database(MainActivity.this);

            ShowCustomers(db);
            //Toast.makeText(MainActivity.this, everyone.toString(), Toast.LENGTH_LONG).show();


        });
        Lv_cust.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                customermodel click_customer = (customermodel) adapterView.getItemAtPosition(i);
                db.delete_one(click_customer);
                ShowCustomers(db);
                Toast.makeText(MainActivity.this, "Deleted the "+click_customer, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void ShowCustomers(database db2) {
        customerArray = new ArrayAdapter<customermodel>(MainActivity.this, android.R.layout.simple_list_item_1, db2.getEveryone());
        Lv_cust.setAdapter(customerArray);
    }

    @Override
    public void onClick(View view) {

    }
}
