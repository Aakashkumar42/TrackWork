package com.example.trackwork;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Environment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.pdf.PdfDocument.PageInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import dalvik.system.DexFile;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class JobDetails extends AppCompatActivity {

    TextView txt_custName,txt_joid,txt_custmobile,txt_custAddress,txt_item,txt_issue,txt_remarks,tv_Date;
    int position;
    private Menu action;
    private int id;
    Button btn_printpdf;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        txt_joid = (TextView) findViewById(R.id.tv_jobid);
        txt_custName = (TextView) findViewById(R.id.tv_custName);
        txt_custmobile = (TextView) findViewById(R.id.tv_mobile);
        txt_custAddress = (TextView) findViewById(R.id.tv_address);
        txt_item = (TextView) findViewById(R.id.tv_item);
        txt_issue = (TextView) findViewById(R.id.tv_issue);
        txt_remarks = (TextView) findViewById(R.id.tv_Remarks);
        tv_Date = (TextView) findViewById(R.id.tv_Date);
        btn_printpdf=(Button) findViewById(R.id.btn_createPdf);


        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");

        txt_joid.setText("Job ID:\n" + MainActivity.modelList.get(position).getJobid());
        txt_custName.setText("Name:" + MainActivity.modelList.get(position).getCust_name());
        txt_custmobile.setText("Mobile:" + MainActivity.modelList.get(position).getCust_mobile());
        txt_custAddress.setText("Address:" + MainActivity.modelList.get(position).getCust_address());
        txt_item.setText("Item:" + MainActivity.modelList.get(position).getItem());
        txt_issue.setText("Issue:" + MainActivity.modelList.get(position).getIssue());
        txt_remarks.setText("Remarks:" + MainActivity.modelList.get(position).getRemarks());
        tv_Date.setText("Date:" + MainActivity.modelList.get(position).getDate());




        btn_printpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createPdf(txt_custName.getText().toString(),txt_custmobile.getText().toString(),txt_joid.getText().toString(),txt_issue.getText().toString());
            }
        });

     verifyStoragePermissions(this);


    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
    private void createPdf(String name, String mobile, String jobid ,String issue) {

            PdfDocument document=new PdfDocument();

            PageInfo pageInfo=new PdfDocument.PageInfo.Builder(300,600,1).create();

            int titleBaseLine=72;
            int leftmargin=42;

            PdfDocument.Page page=document.startPage(pageInfo);
            Canvas canvas=page.getCanvas();
            Paint paint=new Paint();
            paint.setColor(Color.GREEN);
            paint.setTextSize(20);
            canvas.drawText("New Era Corporation ",titleBaseLine,leftmargin,paint);
            canvas.drawLine(100,200,90,1900,paint);
            paint.setTextSize(15);
            paint.setColor(Color.BLACK);
            paint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(jobid,80,65,paint);
            paint.setColor(Color.BLACK);
            paint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(name,88,88,paint);
            paint.setColor(Color.BLACK);
            paint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(mobile,96,111,paint);
            paint.setColor(Color.BLACK);
            paint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(issue,104,134,paint);




        document.finishPage(page);

            java.io.File file=new java.io.File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/jobdedetails.pdf");





            if (!file.exists()){
                file.mkdirs();
            }
            String targetPdf=file+"test-2.pdf";
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        action=menu;

        if(id==0){
        action.findItem(R.id.menu_save).setVisible(true);
        action.findItem(R.id.menu_delete).setVisible(false);
        action.findItem(R.id.menu_edit).setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.invoice_add:
                Intent intent=new Intent(this,Add_Invoice.class).putExtra("position",position);
                startActivity(intent);

                break;
            case R.id.printpPDF:





                 break;
        }

        return super.onOptionsItemSelected(item);
    }

}