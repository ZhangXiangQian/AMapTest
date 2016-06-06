# AMapTest
高德地图Demo
###工程配置
mouble中引入工程则不需要在自己的工程另行配置，因此只需将Amap Mouble中的apiKey替换为自己申请的即可
```java
  <meta-data
            android:name="com.amap.api.v2.apikey"
            android:value="7da75c748c5e3ea7793e46e3ffd8eb76" />
```
###MapView 调用
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
 
###定位
####（一）定位
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


