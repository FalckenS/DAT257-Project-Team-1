package com.example.dat257_project_team_1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<DataModel> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView adress;
        RelativeLayout expandedView;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.expandedView = (RelativeLayout) itemView.findViewById(R.id.expanded_view);
            this.adress = (TextView) itemView.findViewById(R.id.cardAddress);
            this.name = (TextView) itemView.findViewById(R.id.locationName);
        }
    }

    public CustomAdapter(ArrayList<DataModel> data){
        this.dataSet = data;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_expandable_card, parent, false);
        view.setOnClickListener(MainActivity.myOnClickListener);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        TextView address = holder.adress;
        TextView name = holder.name;
        RelativeLayout expandedView = holder.expandedView;

        name.setText(dataSet.get(position).getName());
        address.setText(dataSet.get(position).getAddress());

    }

    @Override
    public int getItemCount() {
        System.out.println(dataSet.size());
        return dataSet.size();
    }
}
