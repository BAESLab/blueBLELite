# Android SDK for BLELite Version 1.0

##### Feature:
- Scan Mode, Can be scan BLELtite devices around 50-70m. Return RSSI, Battery, UUID, Major, Minor.
- Connect Mode, Can connect to BLELite Device.
- Config Mode, Can be config BLELite Devices. UUID, Major, Minor and Time uses for iBeacon.

##### How to import lib aar to Android Studio:
 1. Copy file blueBLE.aar to  YourWorkSpace/app/libs
 2. Open Project Structure [File -> Project Structure]
 3. Push "+" on top left of window
 4. Select Import .JAR or .AAR Package
 5. Brown file .aar -> OK

##### How to use:

```Java
blueBLE  ble = new blueBLE(); // Use Libarary blueBLE 
```
```Java
ble.blueBLE_init(this);  //  Inital Function Require Activity or Context
                          /*  1.Check Bluetooth enable. if not enable show dialog
                              2.Inital Bluetooth Manager
```
```Java
ble.scanBLE(0, this); // this function is scan mode  (time,activity)
                      // time --> 0 is infinity loop
```
This function is callback from library blueBLEList Object
```Java
ble.setOnScanner(new blueBLE.bleListener() {
            @Override
            public void scanbleListener(ArrayList<blueBLEList> items) {
                
            }
        });
```
The blueBLEList Object: 
```Java 
public class blueBLEList {
      private String NAME;
      private String ADDRESS;
      private int RSSI;
      private String UUID;
      private int MAJOR;
      private int MINOR;
      private int POWER;
    }
```

