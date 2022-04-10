//aljaafar hassan S1902221
package com.assign.aljaafar_hassan_S1902221.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.assign.aljaafar_hassan_S1902221.R;
import com.assign.aljaafar_hassan_S1902221.modelclasses.DataModel;
import com.assign.aljaafar_hassan_S1902221.ui.roadblock_detail.RoadBlockDetail;
import com.assign.aljaafar_hassan_S1902221.util.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<DataModel> localDataSet;
    private ArrayList<DataModel> dataFilter;
    private final Context context;
    private String mflag = "MainActivity";
    private String mOrientation = "landscape";
    private int limit = 3;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTitle, mDate, mDateEnd;
        private final ImageView ivDateEnd;
        private final ConstraintLayout clMain;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            mTitle = view.findViewById(R.id.tv_title);
            mDate = view.findViewById(R.id.tv_Date);
            mDateEnd = view.findViewById(R.id.tv_Date_end);
            ivDateEnd = view.findViewById(R.id.iv_calendar_end);
            clMain = view.findViewById(R.id.cl_main);
        }

        public TextView getTextView() {
            return mTitle;
        }

        public TextView getTextDate() {
            return mDate;
        }
    }

    public RecyclerAdapter(ArrayList<DataModel> dataSet, Context mContext, String flag, String orientation) {
        localDataSet = dataSet;
        dataFilter = localDataSet;
        context = mContext;
        this.mflag = flag;
        this.mOrientation = orientation;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_road_work, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(localDataSet.get(position).getTitle());

        String startDate = null, endDate = null;
        if (localDataSet.get(0).getDescription().contains("Start Date:")) {
            String[] startDateSplit, endDateSplit;
            String[] desDate;
            desDate = localDataSet.get(position).getDescription().split("<br />");
            startDateSplit = desDate[0].split("Start Date:");
            startDateSplit = startDateSplit[1].split("-");

            //endDateSplit = desDate[1].split("<br />");
            endDateSplit = desDate[1].split("End Date:");
            endDateSplit = endDateSplit[1].split("-");

            startDate = startDateSplit[0];
            endDate = endDateSplit[0];


            viewHolder.mDateEnd.setVisibility(View.VISIBLE);
            viewHolder.ivDateEnd.setVisibility(View.VISIBLE);
            viewHolder.getTextDate().setText("Start Date: " + startDate);
            viewHolder.mDateEnd.setText("End Date: " + endDate);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(" EEEE, dd MMM yyyy ");
            try {
                Date date1 = simpleDateFormat.parse(startDate);
                Date date2 = simpleDateFormat.parse(endDate);

                long daysDiff = new DateUtils().getDateDifference(date1, date2);
                if (daysDiff == 0) {
                    viewHolder.clMain.setBackgroundDrawable(context.getDrawable(R.drawable.bg_road_work_item));
                } else if (daysDiff == 1) {
                    viewHolder.clMain.setBackgroundDrawable(context.getDrawable(R.drawable.bg_road_work_item_orange));
                } else {
                    viewHolder.clMain.setBackgroundDrawable(context.getDrawable(R.drawable.bg_road_work_item_red));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else {
            viewHolder.getTextDate().setText(localDataSet.get(position).getPubDate());
            viewHolder.mDateEnd.setVisibility(View.GONE);
            viewHolder.ivDateEnd.setVisibility(View.GONE);
            viewHolder.clMain.setBackgroundDrawable(context.getDrawable(R.drawable.bg_road_work_item_current));
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //context.startActivity(new Intent(context, RoadBlockDetail.class));
                Intent intent = new Intent(context, RoadBlockDetail.class);
                intent.putExtra("title", localDataSet.get(viewHolder.getAdapterPosition()).getTitle());
                intent.putExtra("description", localDataSet.get(viewHolder.getAdapterPosition()).getDescription());
                intent.putExtra("link", localDataSet.get(viewHolder.getAdapterPosition()).getLink());
                intent.putExtra("pubDate", localDataSet.get(viewHolder.getAdapterPosition()).getPubDate());
                intent.putExtra("mLatLong", localDataSet.get(viewHolder.getAdapterPosition()).getLocPoints());
                context.startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (mflag.equals("MainActivity")) {
            if (mOrientation.equals("landscape")) {
                limit = 4;
                return Math.min(localDataSet.size(), limit);
            } else {
                limit = 3;
                return Math.min(localDataSet.size(), limit);
            }
        } else {
            return localDataSet.size();
        }
    }



    public void filterList(ArrayList<DataModel> filteredList) {
        this.localDataSet = filteredList;
        notifyDataSetChanged();
    }

}