package com.github.zxq.amap.test;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.github.zxq.amap.AMapLocationInMap;

public class MainActivity extends AppCompatActivity {
    private RadioGroup mRadioGroup;
    private AMap aMap;
    private MapView mapView;
    private AMapLocationInMap maMapLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView = (MapView) findViewById(R.id.aMap);
        mapView.onCreate(savedInstanceState);
        if(aMap == null){
            aMap = mapView.getMap();
        }
        maMapLocation = new AMapLocationInMap(this, aMap);

        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        mRadioGroup = (RadioGroup) findViewById(R.id.rg);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_location:
                        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
                        break;
                    case R.id.rb_follow:
                        aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_FOLLOW);
                        break;
                    case R.id.rb_rotation:
                        aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_ROTATE);
                        break;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        maMapLocation.deactivate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        maMapLocation.onDstory();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
