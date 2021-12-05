package com.example.agroguru;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.Recyclervh> {
    List<ViewModel> mviewlist;
    ViewModel viewModel;
    private Context mContext;

    public ViewAdapter(Context c, List<ViewModel> viewModels) {
        this.mContext = c;
        this.mviewlist = viewModels;
    }

    public Recyclervh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        android.view.View v = LayoutInflater.from(mContext).inflate(R.layout.viewrecycler, parent, false);
        return new Recyclervh(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Recyclervh holder, int position) {
        viewModel = mviewlist.get(position);
        holder.mPeriod.setText("Month & Year : "+ viewModel.getPeriod());
        holder.mCrop_type.setText("Crop Type : "+ viewModel.getCropType());
        holder.mProduction.setText("Approx Production : "+ viewModel.getProduction());
        holder.mRegion.setText("Region : "+ viewModel.getRegion());
        holder.mArea.setText("Cultivated Area : "+ viewModel.getArea());
    }

    @Override
    public int getItemCount() {
        return mviewlist.size();
    }

    public class Recyclervh extends RecyclerView.ViewHolder {
        TextView mPeriod, mCrop_type, mProduction, mRegion, mArea;
        public Recyclervh(View itemview) {
            super(itemview);
            mPeriod = (TextView) itemview.findViewById(R.id.pid);
            mCrop_type = (TextView) itemview.findViewById(R.id.cid);
            mProduction = (TextView) itemview.findViewById(R.id.prid);
            mRegion = (TextView) itemview.findViewById(R.id.rid);
            mArea = (TextView) itemview.findViewById(R.id.aid);
        }
    }
}
