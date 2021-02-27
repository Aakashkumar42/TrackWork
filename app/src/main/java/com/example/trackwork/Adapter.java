package com.example.trackwork;

import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.zip.Inflater;

public class Adapter extends ArrayAdapter<Model> {

  public List<Model> modelList;
  Context context;

    public Adapter(List<Model> modelList,Context mCtx) {
        super(mCtx,R.layout.custom_layout,modelList);
        this.modelList=modelList;
        this.context=mCtx;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout,null,true);

        TextView txtjobId=listItemView.findViewById(R.id.txt_jobid);
        TextView txtCust_Name=listItemView.findViewById(R.id.txt_CustName);
        TextView txtdate=listItemView.findViewById(R.id.txt_date);
        TextView txtItemName=listItemView.findViewById(R.id.txt_item);

        Model model=modelList.get(position);
        txtjobId.setText(modelList.get(position).getJobid());
        txtCust_Name.setText(modelList.get(position).getCust_name());
        txtdate.setText(modelList.get(position).getDate());
        txtItemName.setText(modelList.get(position).getItem());

        return listItemView;

    }
}
