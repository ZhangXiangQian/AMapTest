package com.github.zxq.amap;

import android.content.Context;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.overlay.PoiOverlay;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import java.util.List;

/**
 * Created by zhang on 2016/6/3.
 */
public class AMapPoiSearch implements PoiSearch.OnPoiSearchListener,Inputtips.InputtipsListener,AMap.OnMarkerClickListener,AMap.InfoWindowAdapter{
    private Context context;
    private AMap aMap;
    private PoiSearch.Query query;
    private PoiResult poiResult;
    public AMapPoiSearch(Context context, AMap aMap){
        this.context = context;
        this.aMap = aMap;
        init();
    }

    private void init(){
        aMap.setOnMarkerClickListener(this);
        aMap.setInfoWindowAdapter(this);
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int rCode) {
        if(rCode == 1000){
            if(poiResult != null && poiResult.getQuery() != null){
                if(query.equals(poiResult.getQuery())){
                    this.poiResult = poiResult;
                    List<PoiItem> poiItems = poiResult.getPois();
                    List<SuggestionCity> suggestionCities = poiResult.getSearchSuggestionCitys();
                    if(poiItems != null && poiItems.size() > 0){
                        aMap.clear();
                        PoiOverlay mPoiOverlay = new PoiOverlay(aMap,poiItems);
                        mPoiOverlay.removeFromMap();
                        mPoiOverlay.addToMap();
                        mPoiOverlay.zoomToSpan();
                    }else if(suggestionCities != null && suggestionCities.size() > 0){

                    }else {

                    }
                }
            }
        }

    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void onGetInputtips(List<Tip> list, int rCode) {
        if(rCode == 1000){

        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return false;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
