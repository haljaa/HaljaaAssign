//aljaafar hassan S1902221
package com.assign.aljaafar_hassan_S1902221.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.assign.aljaafar_hassan_S1902221.R;
import com.assign.aljaafar_hassan_S1902221.adapters.RecyclerAdapter;
import com.assign.aljaafar_hassan_S1902221.modelclasses.DataModel;
import com.assign.aljaafar_hassan_S1902221.networking.RunnerClass;
import com.assign.aljaafar_hassan_S1902221.networking.actions.GetTrafficData;
import com.assign.aljaafar_hassan_S1902221.ui.roadblock_detail_list.RoadBlockDetailList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<DataModel> arrayListCurrent = new ArrayList<>();
    private ArrayList<DataModel> arrayList = new ArrayList<>();
    private ArrayList<DataModel> arrayListPlanned = new ArrayList<>();
    private ProgressBar pbRoadworks, pbCurrent, pbPlanned;
    private RecyclerView roadworksIncidents;
    private TextView mtvViewAllRoadWorks, mTvCurrent, mTvPlanned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pbRoadworks = findViewById(R.id.pb_roadworks);
        pbCurrent = findViewById(R.id.pb_current);
        pbPlanned = findViewById(R.id.pb_planned);
        roadworksIncidents = findViewById(R.id.rv_roadworks);
        mTvCurrent = findViewById(R.id.tv_view_all_incidents);
        mtvViewAllRoadWorks = findViewById(R.id.tv_view_all_road_works);
        mTvPlanned = findViewById(R.id.tv_view_all_road_works_planned);

        callingApis();
        setListeners();
    }

    private void callingApis() {
        try {
            RunnerClass runnerClass1 = new RunnerClass();
            runnerClass1.executeAsync(new GetTrafficData("https://trafficscotland.org/rss/feeds/currentincidents.aspx"), (data) -> {
                pbCurrent.setVisibility(View.GONE);
                if (data != null) {
                    arrayListCurrent = data;
                    if (arrayListCurrent.size() <= 3) {
                        mTvCurrent.setVisibility(View.GONE);
                    } else {
                        mTvCurrent.setVisibility(View.VISIBLE);
                    }
                    setCurrentAdapters(arrayListCurrent);
                } else {
                    Toast.makeText(this, "Internet Connectivity Issue", Toast.LENGTH_SHORT).show();
                }
            });

            RunnerClass runnerClass = new RunnerClass();
            runnerClass.executeAsync(new GetTrafficData("https://trafficscotland.org/rss/feeds/roadworks.aspx"), (data) -> {
                pbRoadworks.setVisibility(View.GONE);
                if (data != null) {
                    mtvViewAllRoadWorks.setVisibility(View.VISIBLE);
                    arrayList = data;
                    setRoadWordsAdapter(arrayList);
                }
            });

            RunnerClass runnerClass2 = new RunnerClass();
            runnerClass2.executeAsync(new GetTrafficData("https://trafficscotland.org/rss/feeds/plannedroadworks.aspx"), (data) -> {
                pbPlanned.setVisibility(View.GONE);
                if (data != null) {
                    mTvPlanned.setVisibility(View.VISIBLE);
                    arrayListPlanned = data;
                    setPlannedAdapter(arrayListPlanned);
                }

            });
        } catch (IOException e) {
            Toast.makeText(this, "Internet Connectivity Issue", Toast.LENGTH_SHORT).show();
        }

    }

    private void setCurrentAdapters(ArrayList<DataModel> Current) {
        RecyclerView rvIncidents = findViewById(R.id.rv_incidents);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            RecyclerAdapter recyclerAdapter = new RecyclerAdapter(Current, this, "MainActivity", "portraite");
            rvIncidents.setAdapter(recyclerAdapter);
            rvIncidents.setLayoutManager(new LinearLayoutManager(this));
        } else {
            RecyclerAdapter recyclerAdapter = new RecyclerAdapter(Current, this, "MainActivity", "landscape");
            rvIncidents.setAdapter(recyclerAdapter);
            rvIncidents.setLayoutManager(new GridLayoutManager(this, 2));
        }

    }

    private void setRoadWordsAdapter(ArrayList<DataModel> roadWorks) {
        List<String> list = new ArrayList();
        list.add("5");
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            RecyclerAdapter recyclerAdapter1 = new RecyclerAdapter(roadWorks, this, "MainActivity", "portraite");
            roadworksIncidents.setAdapter(recyclerAdapter1);
            roadworksIncidents.setLayoutManager(new LinearLayoutManager(this));
        } else {
            RecyclerAdapter recyclerAdapter1 = new RecyclerAdapter(roadWorks, this, "MainActivity", "landscape");
            roadworksIncidents.setAdapter(recyclerAdapter1);
            roadworksIncidents.setLayoutManager(new GridLayoutManager(this, 2));
        }
    }

    private void setPlannedAdapter(ArrayList<DataModel> planned) {
        RecyclerView rvPlannedRoadWorks = findViewById(R.id.rv_planned_roadworks);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            RecyclerAdapter recyclerAdapter2 = new RecyclerAdapter(planned, this, "MainActivity", "portraite");
            rvPlannedRoadWorks.setAdapter(recyclerAdapter2);
            rvPlannedRoadWorks.setLayoutManager(new LinearLayoutManager(this));
        } else {
            RecyclerAdapter recyclerAdapter2 = new RecyclerAdapter(planned, this, "MainActivity", "landscape");
            rvPlannedRoadWorks.setAdapter(recyclerAdapter2);
            rvPlannedRoadWorks.setLayoutManager(new GridLayoutManager(this, 2));
        }
    }

    private void setListeners() {
        findViewById(R.id.tv_view_all_incidents).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "" + arrayListCurrent.size(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, RoadBlockDetailList.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("arraylist", arrayListCurrent);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        findViewById(R.id.tv_view_all_road_works).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(MainActivity.this, RoadBlockDetailList.class));
                Intent intent = new Intent(MainActivity.this, RoadBlockDetailList.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("arraylist", arrayList);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        findViewById(R.id.tv_view_all_road_works_planned).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(MainActivity.this, RoadBlockDetailList.class));
                Intent intent = new Intent(MainActivity.this, RoadBlockDetailList.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("arraylist", arrayListPlanned);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_refresh) {
            //TextViewAll
            mtvViewAllRoadWorks.setVisibility(View.INVISIBLE);
            mTvCurrent.setVisibility(View.INVISIBLE);
            mTvPlanned.setVisibility(View.INVISIBLE);

            //ProgressBar
            pbRoadworks.setVisibility(View.VISIBLE);
            pbCurrent.setVisibility(View.VISIBLE);
            pbPlanned.setVisibility(View.VISIBLE);
            callingApis();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}