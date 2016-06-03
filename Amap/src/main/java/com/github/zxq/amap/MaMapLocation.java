package com.github.zxq.amap;

import android.content.Context;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * Created by zhang on 2016/6/3.
 */
public class MaMapLocation implements AMapLocationListener {
    private Context context;
    private AMapLocationClient aMapLocationClient;
    private AMapLocationClientOption aMapLocationClientOption;
    private AMapLocationResultListener aMapLocationResultListener;
    public MaMapLocation(Context context,AMapLocationResultListener aMapLocationResultListener) {
        this.context = context;
        this.aMapLocationResultListener = aMapLocationResultListener;
        if (aMapLocationClient == null) {
            aMapLocationClient = new AMapLocationClient(context);
            aMapLocationClientOption = new AMapLocationClientOption();
            aMapLocationClient.setLocationListener(this);
            aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            aMapLocationClient.setLocationOption(aMapLocationClientOption);
            aMapLocationClient.startLocation();
        }
    }
    @Override
    public void onLocationChanged(com.amap.api.location.AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                if (aMapLocationResultListener != null) {
                    aMapLocationResultListener.onLocationResult(aMapLocation);
                }
            } else {
                if (aMapLocationResultListener != null) {
                    aMapLocationResultListener.onLocationError(aMapLocation.getErrorCode(), aMapLocation.getErrorInfo());
                }
            }
        }
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
