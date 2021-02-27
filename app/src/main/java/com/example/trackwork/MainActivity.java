package com.example.trackwork;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity  {
    private static final String URL_LINK="http://thetechtalks.in/rerivte.php";
    FloatingActionButton floatingActionButton;
    public static List<Model> modelList;
    ListView listView;

    Model model;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

   listView=(ListView) findViewById(R.id.listView);

    floatingActionButton=(FloatingActionButton) findViewById(R.id.floating_btn);
    modelList=new ArrayList<>();

    floatingActionButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        Intent intent=new Intent(getApplicationContext(),addLead.class);
        startActivity(intent);

        }
    });

    loadData();


   listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
       @Override
       public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
           AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
           ProgressDialog progressDialog=new ProgressDialog(view.getContext());

           CharSequence[] dialogItem={"View Data","Edit Data","Delete Data"};
           builder.setTitle(modelList.get(position).getCust_name());
           builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {
                   switch (i){
                       case 0:
                           startActivity(new Intent(getApplicationContext(),JobDetails.class).putExtra("position",position));
                            break;

                       case 1:
                           startActivity(new Intent(getApplicationContext(),EditJob_Activity.class)
                           .putExtra("position",position));
                           break;
                       case 2:
                           deleteData(modelList.get(position).getJobid());
                           break;
                   }
               }
           });
            builder.create().show();
       }
   });


    }

    private void deleteData(final String jobId) {

        StringRequest request=new StringRequest(Request.Method.POST, "http://www.thetechtalks.in/delete.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equalsIgnoreCase("Data Deleted")){
                    Toast.makeText(MainActivity.this, "Data Deleted Successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Data Not Deleted", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }



        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params=new HashMap<String,String>();
                params.put("jobId",jobId);
                return params;
            }
        };

        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(request);
    }


    private void loadData() {

        final ProgressBar progressBar=(ProgressBar) findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);

        StringRequest request=new StringRequest(Request.Method.GET, URL_LINK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressBar.setVisibility(View.INVISIBLE);
                try{
                    JSONObject object=new JSONObject(response);
                    String success=object.getString("success");
                    JSONArray array=object.getJSONArray("addJob");

                    if(success.equals("1")){

                        for(int i=0;i<array.length();i++){
                          JSONObject obj=array.getJSONObject(i);

                          String jobid=obj.getString("jobId");
                          String cust_name=obj.getString("cust_name");
                          String cust_mobile=obj.getString("cust_mobile");
                          String cust_address=obj.getString("cust_address");
                          String item=obj.getString("item");
                          String issue=obj.getString("issue");
                          String service_charge=obj.getString("service_charge");
                          String remarks=obj.getString("remarks");
                          String date=obj.getString("date");
                          String job_status=obj.getString("job_status");


                          model=new Model(jobid,cust_name,cust_mobile,cust_address,item,issue,service_charge,remarks,date,job_status);
                          modelList.add(model);

                        }
                    }

                    Adapter adapter=new Adapter(modelList,MainActivity.this);
                    listView.setAdapter(adapter);

            }catch (JSONException e){
                   e.printStackTrace();
               }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(request);

    }
    private void createPdf(String name, String mobile, String jobid) {

        PdfDocument document=new PdfDocument();

        PdfDocument.PageInfo pageInfo=new PdfDocument.PageInfo.Builder(300,600,1).create();

        PdfDocument.Page page=document.startPage(pageInfo);
        Canvas canvas=page.getCanvas();
        Paint paint=new Paint();
        paint.setTextSize(40);
        paint.setColor(Color.BLACK);
        canvas.drawText(name,80,50,paint);
        canvas.drawText(mobile,80,50,paint);
        canvas.drawText(jobid,80,50,paint);

        document.finishPage(page);

        String directory_path=Environment.getExternalStorageDirectory()+ "/myPdf";

        File file=new File(directory_path);

        if (!file.exists()){
            file.mkdirs();
        }
        String targetPdf=directory_path+"test-2.pdf";
        File filepath=new File(targetPdf);

        try {
            document.writeTo(new FileOutputStream(filepath));
            Toast.makeText(this,"Done",Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            Log.e("main","error"+e.toString());
            Toast.makeText(this,"Something went wrong:" +e.toString(),Toast.LENGTH_SHORT).show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        document.close();
    }


}