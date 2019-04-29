package com.example.yap.customrecycler;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<People> peopleList;
    private ArrayList<People.LastNames> nameList;

    public RecyclerAdapter(ArrayList<People> peopleList)
    {
        this.peopleList = peopleList;
        nameList = new ArrayList<>();

        for (int i=0; i<peopleList.size(); i++)
        {
            nameList.add(null);

            for (int j=0; j<peopleList.get(i).getLastNames().size(); j++)
            {
                nameList.add(peopleList.get(i).getLastNames().get(j));
            }
        }
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);

        return new RecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Timber.d("position = " + position);
        Timber.d("peopleList size = " + peopleList.size());

        if (nameList.get(position) == null)
            return;
        else
            holder.bind(nameList.get(position));
    }

    @Override
    public int getItemCount() {

        return nameList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_age)
        TextView tv_age;

        public ViewHolder(final View itemView) {

            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void bind(People.LastNames item)
        {
            tv_name.setText(item.getLastName());
            tv_age.setText(item.getAge());
        }

        public RecyclerAdapter.ViewHolder getHolder()
        {
            return this;
        }
    }
}
