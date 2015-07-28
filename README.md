

# Android SDK for BLELite Version 1.0
![aa](https://cloud.githubusercontent.com/assets/13532332/8931090/06413ebe-355e-11e5-88cb-e318dcb0efa6.png)
#### Hardware Specifications:
Specifications
- High-Performance SoC and Low-Power 8051 Microcontroller Core.
- In-System-Programmable Flash, 256 KB.
- 8-KB SRAM  
- Frequency: 2.4GHz
-	Modulation: GFSK, Bluetooth low power, V4.0
- Input Voltage: +3.3 DC
- Operating temperature： -10 ℃ ~ +65 ℃ 
- Battery and Temperature Sensor build-in.

Dimension
- Size: 31mm * 31mm

![as](https://cloud.githubusercontent.com/assets/13532332/8931069/d52bf9b8-355d-11e5-9302-838190b8107d.png)

Battery
- 225mAh CR2032 coin cell battery included in all blueBLE Lite
- Battery is easily replaceable without damaging the blueBLE Lite
- Up to 1 year with full iBeacon profile and 24-hours daily usage (900ms interval)

#### Software Features:
Features  *blueBLE Lite  Version 1.0
- Support both Application and BLE (iBeacon) modes.
- Configurable UUID, Major, Minor, Time uses for iBeacon.
- Battery Level and Temperature Value.
- Long range; 50-100 meters in the open space.
- Provided BlueBLE Android SDK.

#### How to import lib aar to Android Studio:
 1. Copy file blueBLE.aar to  YourWorkSpace/app/libs
 2. Open Project Structure [File -> Project Structure]
 3. Push "+" on top left of window
 4. Select Import .JAR or .AAR Package
 5. Brown file .aar -> OK

#### How to use blueBLE API:
ส่วนนี้จะอธิบายวิธีการใช้งาน SDK Android สำหรับนักพัฒนา โดยจะอธิบายถึงวิธีการเรียกใช้งาน Library รวมถึงตัวอย่างการเขียนโปรแกรมโดยละเอียด
- blueBLE SDK Initialization
```Java
import com.baeslabiot.blueblelite_sdk.blueBLE;
```
```Java
blueBLE  ble = new blueBLE(); // Use Libarary blueBLE 
```
ฟังก์ชั่นนี้จะทำการตรวจสอบว่ามีการเปิดใช้งาน Bluetooth แล้วหรือไม่ ถ้าตรวจสอบแล้วยังไม่มีการเปิดใช้งาน Bluetooth จะทำการแจ้งหน้าต่างเตือนเพื่อให้ผู้ใช้งานเปิดใช้งาน Bluetooth 
```Java
ble.blueBLE_init(this);  //  Inital Function Require Activity or Context
                          /*  1.Check Bluetooth enable. if not enable show dialog
                              2.Inital Bluetooth Manager
```
##### Scan Mode
ฟังก์ชั่นสำหรับทำการค้นหาอุปกรณ์ที่ทำงานในโหมดของ Advertising ข้อมูล
```Java
ble.scanBLE(0, this); // this function is scan mode  (time,activity)
                      // time --> 0 is infinity loop
```


Callback Function  

ทำการเรียกใช้ฟังก์ชั่น ble.scanBLE และเรียกใช้งาน callback setOnScanner เมื่อตัวโปรแกรมค้นหาเจออุปกรณ์จะทำการคืนข้อมูลกลับมาให้ในรูปแบบของ blueBLEList Object หรือ byte[] 
```Java
ble.setOnScanner(new blueBLE.bleListener() {
     @Override
     public void scanbleListener(ArrayList<blueBLEList> device,byte[] data) {
			// do someting
           }
     });	

```


The blueBLEList Object: 
```Java 
{
    private String NAME;
    private String ADDRESS;
    private int RSSI;
    private String UUID;
    private int MAJOR;
    private int MINOR;
    private int POWER;
    private String TIMESTAMP;
    private int BATTERY;
}
```


Get Advertising Message Data 

เมื่อทำการ Scan อุปกรณ์ที่อยู่ใกล้เคียง จากฟังก์ชั่น SetOnScanner ด้านบนจะทำการ callback รายการตัวอุปกรณ์กลับคืนมา ซึงสามารถที่จะดึงรายละเอียดของอุปกณ์ออกมาได้เช่น
```Java
{
device.get(0).getADDRESS(); 
device.get(0).getUUID();
device.get(0).getNAME();
device.get(0).getMAJOR();
device.get(0).getMINOR();
device.get(0).getRSSI();
device.get(0).getPOWER();
device.get(0).getTIMESTAMP();	
}
```


##### blueBLE Lite Configuration

Device Connection

การตั้งค่าให้กับตัวอุปกรณ์ blueBLE Lite ผ่านทาง Android Application จำเป็นจะต้องทำการเชื่อมต่อกับอุปกรณ์ก่อน ดังนั้นส่วนแรกนี้จะอธิบายวิธีการเชื่อมต่อกับ blueBLE Tag โดยเรียกใช้ฟังก์ชั่นดังนี้
```Java
int cSt = ble.connect((bleBLEList) device);   
//OR
int cSt = ble.connect(“C4:BE:84:22:44:BE”);
```
```
Return Status:
VALUE         STATUS	           DESCRIPTION
0	         BLE_CONNECT_FAIL    	Not Respond or Time out
1	         BLE_CONNECTED	       Can be connect device
```


Configuration command sets

ส่วนนี้จะอธิบายเกี่ยวกับการกำหนดค่าหรืออ่านค่าต่างๆของกับตัวอุปกรณ์ ซึ่งจะมีรายละเอียดดังตารางด้านล่าง 
```
COMMAND	 ACTION	                    Type	       DESCRIPTION
0x01	    ble_set_name	             String	     ตั้งค่าชื่อของอุปกรณ์
0x02	    ble_set_uuid	             String	     ตั้งค่า UUID ของอุปกรณ์
0x03	    ble_set_major	            int        	ตั้งค่า major ของอุปกรณ์
0x04     ble_set_minor	            int        	ตั้งค่า minor ของอุปกรณ์
0x06    	ble_set_data	             String	     ตั้งค่าข้อมูลของอุปกรณ์
0x07    	ble_set_time_advertising	 Long 	      ตั้งค่าเวลาที่ใช้ในการปล่อยสัญญาณของอุปกรณ์ (ms)
```

การตั้งค่าต่างๆให้กับตัวอุปกรณ์จะต้องทำการเชื่อมต่อกับอุปกรณ์ตัวนั้นๆแล้วเท่านั้นเมื่อทำการเชื่อมต่อแล้วให้ใช้คำสั่งดังนี้ในการตั้งค่า

```Java
ble.setConf(int action,Object data);  
```
Argument  action สามารถดูได้จากตารางที่ 2 ซึ่งใน Library ได้มีการกำหนดไว้แล้วโดยเรียกผ่าน class ดังนี้
```Java
public class blueBLEConfig {
public static int ble_set_name = 0x01;
public static int ble_set_uuid = 0x02;
public static int ble_set_major = 0x03;
public static int ble_set_minor = 0x04;
public static int ble_set_tx_power = 0x05;
public static int ble_set_data = 0x06;
public static int ble_set_time_advertising = 0x07;
}
```

#### Examples
//  กำหนดชื่อให้กับอุปกรณ์เป็นชื่อ   “BAESLab Tag”
```Java
ble.setConf(blueBLEConfig.ble_set_name,(String) "BAESLab Tag"); 
```
	//  กำหนด UUID ให้กับอุปกรณ์
```Java
ble.setConf(blueBLEConfig.ble_set_uuid,(String) "4F032EE72EE711A00101424145530000");
```
	//  กำหนด Major หรือ minorให้กับอุปกรณ์
```Java
ble.setConf(blueBLEConfig.ble_set_major,(int) 44);
```
//  กำหนดข้อมูลที่ฝังอยู่ภายในอุปกรณ์
```Java
ble.setConf(blueBLEConfig.ble_set_data,(String) "http://www.baeslab.com");
```
//  กำหนด Interval time สำหรับการส่งสัญญาณ Beacon
```Java
ble.setConf(blueBLEConfig.ble_set_time_advertising,(long) 1000); // 1000ms = 1s
```	

#### Code Examples


ตัวอย่าง Android Code การเรียกใช้งาน blueBLE SDK เพื่อทำการค้นหาอุปกรณ์หรือติดต่อกับ blueBLE Tag 
Permission:  ทำการเพิ่ม Permission ในไฟล์ AndroidManifest.xml
```Java
<uses-feature android:name="android.hardware.bluetooth_le" android:required="true"/>
<uses-permission android:name="android.permission.BLUETOOTH"/>
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN"/>
```
```Java
// MainActivity.java

package com.baeslabiot.androidsdk_blueblelite;

import com.baeslabiot.blueblelite_sdk.blueBLE;
import com.baeslabiot.engin.blueBLEConfig;
import com.baeslabiot.engin.blueBLEList;

public class MainActivity extends Activity {

blueBLE  ble;
listDevices adapter;
ListView list;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ble = new blueBLE();
    ble.blueBLE_init(this);

    	  adapter = new listDevices(getApplicationContext());
    	  list = (ListView) findViewById(R.id.list);
    	  list.setAdapter(adapter);

    	  ble.setOnScanner(new blueBLE.bleListener() {
        		@Override
        		public void scanbleListener(ArrayList<blueBLEList> device) {
                   adapter.setAdapter(device);
            	    adapter.notifyDataSetChanged();
                 }
    	  });

 	}
}
```
```Java
// Activity_main.xml

<ListView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools" android:layout_width="match_parent"
    android:layout_height="match_parent" android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    android:paddingBottom="@dimen/activity_vertical_margin" tools:context=".MainActivity"
    android:id="@+id/list"
    android:background="#ff646464">

</ListView>


```
##### Android Application
![device-2015-07-28-102339](https://cloud.githubusercontent.com/assets/13532332/8930634/c15db998-3559-11e5-9dc3-5090be5a1d38.png)
![device-2015-07-28-102547](https://cloud.githubusercontent.com/assets/13532332/8930649/d6f8d2b0-3559-11e5-9466-c3cd540a3511.png)
![device-2015-07-28-102508](https://cloud.githubusercontent.com/assets/13532332/8930650/d6fbd46a-3559-11e5-9422-2b26108f44d6.png)
![device-2015-07-28-102626](https://cloud.githubusercontent.com/assets/13532332/8930651/d702dee0-3559-11e5-84ca-b66fa9d8e0ea.png)
