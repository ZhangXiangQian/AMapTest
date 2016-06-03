package com.github.zxq.amap;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;

/**定位
 * Created by zhang on 2016/6/3.
 */
public class AMapLocationInMap implements LocationSource, AMapLocationListener {
    private AMapLocationClient aMapLocationClient;
    private AMapLocationClientOption aMapLocationClientOption;
    private OnLocationChangedListener mOnLocationChangedListener;
    private Context context;
    private AMap aMap;
    public static final String TAG = "AMapLocationInMap";

    public AMapLocationInMap(Context context, AMap aMap) {
        this.context = context;
        this.aMap = aMap;
        init();
    }


    private void init() {
        if (aMap != null) {
            aMap.setLocationSource(this);
            //显示默认定位按钮显示
            aMap.getUiSettings().setMyLocationButtonEnabled(true);
            //显示定位图层并可显示，false隐藏定位图层并不可显示
            aMap.setMyLocationEnabled(true);
            aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        }
    }

    /**
     * 定位成功之后回调函数
     *
     * @param aMapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mOnLocationChangedListener != null && aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                mOnLocationChangedListener.onLocationChanged(aMapLocation);
                Log.i(TAG, "address:" + aMapLocation.getAddress());
            } else {
                Log.i(TAG, "location error !\n" + "errCode:" + aMapLocation.getErrorCode() + ",errMSG:" + aMapLocation.getErrorInfo());
            }
        }
    }

    /**
     * 激活定位
     *
     * @param onLocationChangedListener
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mOnLocationChangedListener = onLocationChangedListener;
        if (aMapLocationClient == null) {
            aMapLocationClient = new AMapLocationClient(context);
            aMapLocationClientOption = new AMapLocationClientOption();
            aMapLocationClient.setLocationListener(this);
            aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            aMapLocationClient.setLocationOption(aMapLocationClientOption);
            aMapLocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mOnLocationChangedListener = null;
        if (aMapLocationClient != null) {
            aMapLocationClient.stopLocation();
            aMapLocationClient.onDestroy();
        }
        aMapLocationClient = null;
    }

    /**
     * 销毁
     */
    public void onDstory() {
        if (null != aMapLocationClient) {
            aMapLocationClient.onDestroy();
        }
    }
}
