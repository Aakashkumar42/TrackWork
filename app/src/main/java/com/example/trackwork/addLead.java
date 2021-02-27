package com.example.trackwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class addLead extends AppCompatActivity {

  EditText edtCustname,edtCustMobile,edtCustAddress,edtItem,edtIssue,edtServiceCharge,edtRemarks;
  private int id;
  Button btn_Add;
  private boolean vaild=true;
  Spinner mspinner;
  Spinner spin_status;
  private Menu action;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lead);

        edtCustname=(EditText) findViewById(R.id.edtCust_Name);
        edtCustMobile=(EditText) findViewById(R.id.edtCust_mobile);
        edtCustAddress=(EditText) findViewById(R.id.edtCust_address);
        mspinner=(Spinner) findViewById(R.id.spinner_items);
        edtIssue=(EditText) findViewById(R.id.edtIssue);
        edtServiceCharge=(EditText) findViewById(R.id.edtService_charge);
        edtRemarks=(EditText) findViewById(R.id.edtRemarks);
        spin_status=(Spinner) findViewById(R.id.spinner_status);
        btn_Add=(Button) findViewById(R.id.btn_submit);
        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insetData();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        action=menu;
        action.findItem(R.id.menu_save).setVisible(true);
    if (id == 0){
        action.findItem(R.id.menu_delete).setVisible(false);
        action.findItem(R.id.menu_edit).setVisible(false);
        action.findItem(R.id.menu_save).setVisible(true);
    }

   return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.menu_edit:
                editMode();

                InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edtCustname,InputMethodManager.SHOW_IMPLICIT);

                action.findItem(R.id.menu_delete).setVisible(false);
                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);
                action.findItem(R.id.menu_save).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                       insetData();
                        return true;
                    }
                });
                return true;
            case R.id.menu_save:

        }

    return true;

    }

    private void editMode() {
    }

    private void insetData() {
        final String cust_name=edtCustname.getText().toString().trim();
        final String cust_mobile=edtCustMobile.getText().toString().trim();
        final String cust_address=edtCustAddress.getText().toString().trim();
        final String spin=mspinner.getSelectedItem().toString();
        final String issue=edtIssue.getText().toString().trim();
        final String service_charge=edtServiceCharge.getText().toString().trim();
        final String remarks=edtRemarks.getText().toString().trim();
        final String spinner=spin_status.getSelectedItem().toString();

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading.....");


        if (TextUtils.isEmpty(cust_name)){
            edtCustname.setError("Enter Customer Name");
            vaild=false;
        }
         else if(TextUtils.isEmpty(cust_mobile)){
            edtCustMobile.setError("Enter Mobile No");
            vaild=false;
        }
        else if(TextUtils.isEmpty(cust_address)){
            edtCustAddress.setError("Enter Address");
            vaild=false;
        }
        else if(TextUtils.isEmpty(spin)){
            edtItem.setError("Please pick a item");
            vaild=false;
        }
        else if(TextUtils.isEmpty(issue)){
            edtIssue.setError("Enter Issue");
            vaild=false;
        }
        else if (TextUtils.isEmpty(service_charge)){
            edtServiceCharge.setError("Enter Customer Name");
            vaild=false;
        }
        else if (TextUtils.isEmpty(remarks)){
            edtRemarks.setError("Enter Customer Name");
            vaild=false;
        }
        else{
            progressDialog.show();
            StringRequest request=new StringRequest(Request.Method.POST, "http://www.thetechtalks.in/insert.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equalsIgnoreCase("Data Inserted ")){
                                Toast.makeText(addLead.this,"Data Inserted",Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            }
                            else{
                                Toast.makeText(addLead.this,response,Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(addLead.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params=new HashMap<String,String>();

                    params.put("cust_name",cust_name);
                    params.put("cust_mobile",cust_mobile);
                    params.put("cust_address",cust_address);
                    params.put("item",spin);
                    params.put("issue",issue);
                    params.put("service_charge",service_charge);
                    params.put("remarks",remarks);
                    params.put("job_status",spinner);

                    return params;
                }
            };

            RequestQueue queue= Volley.newRequestQueue(addLead.this);
            queue.add(request);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}