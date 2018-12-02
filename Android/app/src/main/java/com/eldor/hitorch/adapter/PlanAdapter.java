package com.eldor.hitorch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eldor.hitorch.library.OnInitSelectedPosition;

import java.util.ArrayList;
import java.util.List;

import com.eldor.hitorch.R;

public class PlanAdapter<T> extends BaseAdapter{


    private Context mContext;
    private List<T> mDataList;

    public PlanAdapter(Context mContext) {
        this.mContext = mContext;
        this.mDataList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int i) {
        return mDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertview, ViewGroup viewGroup) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.tag_item, null);

        TextView textView = view.findViewById(R.id.tv_tag);
        T t = mDataList.get(i);

        if (t instanceof String) {
            textView.setText((String) t);
        }
        return view;

    }

    public void onlyAddAll(List<T> datas) {
        mDataList.addAll(datas);
        notifyDataSetChanged();
    }

    public void clearAndAddAll(List<T> datas) {
        mDataList.clear();
        onlyAddAll(datas);
    }
}
