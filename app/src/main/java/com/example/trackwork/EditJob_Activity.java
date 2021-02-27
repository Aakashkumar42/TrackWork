package com.example.trackwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class EditJob_Activity extends AppCompatActivity {
    EditText ed_custName,ed_custMobile,ed_custAddress,ed_item,ed_servicecharge,ed_remarks;
    Spinner spin_status,spin_item;
    Button edit_btn;
    int position;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_job_);

         ed_custName=findViewById(R.id.edtCust_Name_ed);
         ed_custMobile=findViewById(R.id.edtCust_mobile_ed);
         ed_custAddress=findViewById(R.id.edtCust_address_ed);
         ed_servicecharge=findViewById(R.id.edtService_charge_ed);
         ed_remarks=findViewById(R.id.edtRemarks_ed);
          edit_btn=findViewById(R.id.btn_edit);
          spin_item=findViewById(R.id.spinner_items_ed);
          spin_status=findViewById(R.id.spinner_status_ed);

        Intent intent=new Intent();
        position=intent.getIntExtra("position",position);
        value=getIntent().getExtras().getString("Value");
        ed_custName.setText(MainActivity.modelList.get(position).getCust_name());
        ed_custMobile.setText(MainActivity.modelList.get(position).getCust_mobile());
        ed_custAddress.setText(MainActivity.modelList.get(position).getCust_address());
        ed_servicecharge.setText(MainActivity.modelList.get(position).getService_charge());
        ed_remarks.setText(MainActivity.modelList.get(position).getRemarks());

    }

}