//aljaafar hassan S1902221
package com.assign.aljaafar_hassan_S1902221.ui.roadblock_detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.assign.aljaafar_hassan_S1902221.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class RoadBlockDetail extends AppCompatActivity implements OnMapReadyCallback {
    private TextView mTvTitle, mTvDes, mTvLink, mTvDate;
    private GoogleMap map;
    private Intent intent;
    private String mTitle, mDes, mLink, mDate;
    private Double mLat, mLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_road_block_detail);

        mTvTitle = findViewById(R.id.tv_title);
        mTvDes = findViewById(R.id.tv_description);
        mTvLink = findViewById(R.id.tv_link);
        mTvDate = findViewById(R.id.tv_date);

        SupportMapFragment mFrag = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mFrag != null;
        mFrag.getMapAsync(this);

        setData();
    }

    private void setData() {
        intent = getIntent();
        mTitle = intent.getStringExtra("title");
        mDes = intent.getStringExtra("description");
        mLink = intent.getStringExtra("link");
        mDate = intent.getStringExtra("pubDate");
        String mLatLong = intent.getStringExtra("mLatLong");
        mLat = Double.parseDouble(mLatLong.split(" ")[0]);
        mLong = Double.parseDouble(mLatLong.split(" ")[1]);

        mTvTitle.setText(mTitle);
        mTvDes.setText(Html.fromHtml(mDes));
        mTvLink.setText(mLink);
        mTvDate.setText(mDate);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        LatLng sydney = new LatLng(mLat, mLong);
        map.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney"));
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLat, mLong), 13f));
    }
}