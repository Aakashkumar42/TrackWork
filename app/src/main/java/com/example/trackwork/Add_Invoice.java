package com.example.trackwork;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Add_Invoice extends AppCompatActivity {
    TextView tvdate,tvJobid;
    EditText amount_edt;
    Spinner mspin;
    Button btn_add_amt;
    int mposition;
    ProgressBar progressBar;
   private String URL="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__invoice);
    tvdate=(TextView) findViewById(R.id.date);
    tvJobid=(TextView) findViewById(R.id.jobId);
    btn_add_amt=(Button) findViewById(R.id.btn_create);
    mspin=(Spinner) findViewById(R.id.pay_type);
    amount_edt=(EditText) findViewById(R.id.Amount);
    Intent intent=getIntent();
    mposition=intent.getExtras().getInt("position");


    tvdate.setText(MainActivity.modelList.get(mposition).getDate());
    tvJobid.setText(MainActivity.modelList.get(mposition).getJobid());
    amount_edt.setText(MainActivity.modelList.get(mposition).getService_charge());
    btn_add_amt.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            insertData();
            
        }
    });



    }

    private void insertData() {
        final String jobId=tvJobid.getText().toString().trim();
        final String service_charge=amount_edt.getText().toString().trim();
        final String pay_type=mspin.getSelectedItem().toString();

       final ProgressDialog progressDialog=new ProgressDialog(this);
       progressDialog.setMessage("Updating .....");
       progressDialog.show();


        StringRequest request=new StringRequest(Request.Method.POST, "http://thetechtalks.in/update_payment.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
             Toast.makeText(Add_Invoice.this,response,Toast.LENGTH_SHORT).show();
             startActivity(new Intent(getApplicationContext(),MainActivity.class));
             progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            Toast.makeText(Add_Invoice.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String ,String> params=new HashMap<String,String>();
                params.put("jobId",jobId);
                params.put("service_charge",service_charge);
                params.put("pay_type",pay_type);
                return  params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(Add_Invoice.this);
        requestQueue.add(request);
    }
}