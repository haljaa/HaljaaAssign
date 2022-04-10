//aljaafar hassan S1902221
package com.assign.aljaafar_hassan_S1902221.ui.roadblock_detail_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.assign.aljaafar_hassan_S1902221.R;
import com.assign.aljaafar_hassan_S1902221.adapters.RecyclerAdapter;
import com.assign.aljaafar_hassan_S1902221.modelclasses.DataModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RoadBlockDetailList extends AppCompatActivity {
    private ArrayList<DataModel> arrayList = new ArrayList<>();
    private RecyclerView rvIncidents;
    private EditText etSearch;
    private RecyclerAdapter recyclerAdapter;
    private ImageView ivSelectDate;
    private String[] desDate;
    private String[] startDateSplit, endDateSplit;
    private String startDate, endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_road_block_detail_list);

        rvIncidents = findViewById(R.id.rv_incidents_detail_list);
        etSearch = findViewById(R.id.et_search);
        ivSelectDate = findViewById(R.id.iv_select_date);

        Bundle bundle = getIntent().getExtras();
        arrayList = (ArrayList<DataModel>) bundle.getSerializable("arraylist");

        /*String str = arrayList.get(0).getDescription();
        if (arrayList.get(0).getDescription().contains("Start Date:")) {
            for (int i = 0; i < arrayList.size(); i++) {
                desDate = arrayList.get(i).getDescription().split("<br />");
                startDateSplit = desDate[0].split("Start Date:");
                startDateSplit = startDateSplit[1].split("-");

                //endDateSplit = desDate[1].split("<br />");
                endDateSplit = desDate[1].split("End Date:");
                endDateSplit = endDateSplit[1].split("-");

                startDate = startDateSplit[0];
                endDate = endDateSplit[0];
            }
        }*/

        editTextListener();
        setAdapter();
        clickListener();
    }

    private void getListBetweenTwoDates(long DatePassed, long endDatePassed) {
        ArrayList<DataModel> filteredList = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            desDate = arrayList.get(i).getDescription().split("<br />");
            startDateSplit = desDate[0].split("Start Date:");
            startDateSplit = startDateSplit[1].split("-");

            endDateSplit = desDate[1].split("End Date:");
            endDateSplit = endDateSplit[1].split("-");

            startDate = startDateSplit[0];
            endDate = endDateSplit[0];

            try {
                //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                //Date dateStart = sdf.parse(startDate);
                //Date dateEnd = sdf.parse(endDate);

                SimpleDateFormat inputFormat = new SimpleDateFormat(" EEEE, d MMM yyyy ");
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

                Date dateStart = inputFormat.parse(startDate);
                startDate = outputFormat.format(dateStart);

                Date dateEnd = inputFormat.parse(endDate);
                endDate = outputFormat.format(dateEnd);

                long startDateLong = dateStart.getTime();
                long endDateLong = dateEnd.getTime();

                if (DatePassed >= startDateLong && DatePassed <= endDateLong) {
                    filteredList.add(arrayList.get(i));
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        recyclerAdapter.filterList(filteredList);
    }

    private void clickListener() {
        ivSelectDate.setOnClickListener(v -> {
            try {
                showDateDialog(RoadBlockDetailList.this);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }

    private void editTextListener() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence query, int start, int before, int count) {
                filter(query.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void filter(String text) {
        ArrayList<DataModel> filteredList = new ArrayList<>();

        for (DataModel item : arrayList) {
            if (item.getTitle() != null) {
                if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }
        }

        recyclerAdapter.filterList(filteredList);
    }

    private void setAdapter() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerAdapter = new RecyclerAdapter(arrayList, this, "", "");
            rvIncidents.setAdapter(recyclerAdapter);
            rvIncidents.setLayoutManager(new LinearLayoutManager(this));
        } else {
            recyclerAdapter = new RecyclerAdapter(arrayList, this, "", "");
            rvIncidents.setAdapter(recyclerAdapter);
            rvIncidents.setLayoutManager(new GridLayoutManager(this, 2));
        }
    }

    public void showDateDialog(Activity activity) throws ParseException {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.date_dialog);

        TextView tvSubmit = dialog.findViewById(R.id.tv_submit);
        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        ImageView ivCancel = dialog.findViewById(R.id.iv_cancel);
        DatePicker datePicker = dialog.findViewById(R.id.date_picker);


        tvSubmit.setOnClickListener(view -> {

            int day = datePicker.getDayOfMonth();
            int month = (datePicker.getMonth() + 1);
            int year = datePicker.getYear();

            //tvDate.setText(year + "-" + month + "-" + day);
            Toast.makeText(activity, "" + year + "-" + month + "-" + day, Toast.LENGTH_SHORT).show();

            String dateselect = +year + "-" + month + "-" + day;
            String inputFormat = "yyyy-MM-dd";
            String OutPutFormat = "EEEE, dd MMM yyyy";
            String formats1 = null;
            try {
                SimpleDateFormat formatter5 = new SimpleDateFormat("yyyy-MM-dd");

                long startDateSelectLong, endDateSelectLong;
                Date dateStart = formatter5.parse(dateselect);
                Date dateEnd = formatter5.parse("2022-3-28");
                startDateSelectLong = dateStart.getTime();
                endDateSelectLong = dateEnd.getTime();

                getListBetweenTwoDates(startDateSelectLong, endDateSelectLong);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            dialog.dismiss();
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}