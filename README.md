# AMapTest
高德地图Demo
### 工程配置
mouble中引入工程则不需要在自己的工程另行配置，因此只需将Amap Mouble中的apiKey替换为自己申请的即可
```java
  <meta-data
            android:name="com.amap.api.v2.apikey"
            android:value="7da75c748c5e3ea7793e46e3ffd8eb76" />
```
### MapView 调用
  和百度地图相比高德地图的调用相对复杂，XML示例代码：
```xml
   <com.amap.api.maps.MapView
        android:id="@+id/aMap"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```
 Activity中的配置：
```java
    private AMap aMap;
    private MapView mapView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView = (MapView) findViewById(R.id.aMap);
        //必须调用
        mapView.onCreate(savedInstanceState); 
        if(aMap == null){
            aMap = mapView.getMap();
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
         //必须调用
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
         //必须调用
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
         //必须调用
        mapView.onDestroy();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
         //必须调用
        mapView.onSaveInstanceState(outState);
    }
```
 
### 定位
#### （一）定位
  仅获取当前地理位置，不显示Map
```java
  MaMapLocation maMapLocation = new MaMapLocation(this, new AMapLocationResultListener() {
            @Override
            public void onLocationResult(AMapLocation aMapLocation) {
                Toast.makeText(LocationActivity.this,"定位结果：" + aMapLocation.getAddress(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLocationError(int code, String errMsg) {

            }
        });
```
 同时在onDestory中调用
```java
 @Override
 protected void onDestroy() {
        super.onDestroy();
        maMapLocation.onDstory();
 }
```
#### （二）定位且在地图上标记
  1、MapView配置参考前面调用即可
 
  2、初始化即可无需另调用其它方法
```java
 AMapLocationInMap maMapLocation = new AMapLocationInMap(this, aMap);
```
```java
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        //必须调用
        maMapLocation.deactivate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
         //必须调用
        maMapLocation.onDstory();
    }
```
  3、若要单独处理定位结果则
```java
   maMapLocation.setaMapLocationResultListener(new AMapLocationResultListener() {
            @Override
            public void onLocationResult(AMapLocation aMapLocation) {
                
            }

            @Override
            public void onLocationError(int code, String errMsg) {

            }
        });
```
#### 热点搜索（PoiSearch）
  1、MapView配置参考前面即可
  
  2、初始化
 ```java
  aMapPoiSearch = new AMapPoiSearch(this,aMap);
 ```
  3、设置监听，监听搜索结果
 ```java
   aMapPoiSearch.setaMapPoiSearchListener(new AMapPoiSearchListener() {
            @Override
            public void onPoiSearchError(int code, String errMsg) {
              //搜索错误，搜索结果为null也在这里返回
            }

            @Override
            public void onPoiSuggestResult(List<SuggestionCity> list) {
              //在指定城市未搜索到结果，返回其它城市的搜索结果
            }

            @Override
            public void onInputtipsResult(List<String> list) {
               //实时监听用户输入操作并提供搜索建议，这里返回这个结果
                Log.i("MainActivity", list.size() + list.get(0));
                mAdapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,list);
                autoCompleteTextView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        });
 ```
  4、POI搜索
 ```java
     /**
     * 开启搜索
     * @param keyWord  关键字
     * @param type     搜索类型
     * @param city     搜索城市，若为""则在全国范围搜索
     * @param pageNum  搜索第几页
     * @param pageSize 一页显示多少条结果
     */
   aMapPoiSearch.startPoiSearch(autoCompleteTextView.getText().toString(),"","北京",1,10);
 ```
5、搜索建议
 一般在TextWatcher中调用
```java
  /**
     * 依据关键字提供搜索建议
     * @param keyWord 关键字
     * @param city  城市
  */
  aMapPoiSearch.startSuggestSearch(s.toString(),"北京");
```
