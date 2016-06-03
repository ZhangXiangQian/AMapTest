package com.github.zxq.amap;

import com.amap.api.location.AMapLocation;

/**
 *
 * Created by zhang on 2016/6/3.
 */
public interface AMapLocationResultListener {

    public void onLocationResult(AMapLocation aMapLocation);
    public void onLocationError(int code,String errMsg);
}
