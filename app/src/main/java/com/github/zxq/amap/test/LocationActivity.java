package com.github.zxq.amap.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.github.zxq.amap.AMapLocationResultListener;
import com.github.zxq.amap.MaMapLocation;


public class LocationActivity extends AppCompatActivity{
    private MaMapLocation maMapLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        maMapLocation = new MaMapLocation(this, new AMapLocationResultListener() {
            @Override
            public void onLocationResult(AMapLocation aMapLocation) {
                Toast.makeText(LocationActivity.this,"定位结果：" + aMapLocation.getAddress(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLocationError(int code, String errMsg) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        maMapLocation.onDstory();
    }
}
