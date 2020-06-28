#  使用说明

## gradle文件
根路径下的 `build.gradle` 中添加：
```groovy
allprojects {
   repositories {
      jcenter()
      maven { url "https://jitpack.io" }
   }
}
```

根据需要，在 AndroidManifest.xml 中配置对应的功能页面 Activity ：
```xml
<application>
    ......
    <!-- 二维码扫描 -->
    <activity android:name="com.demo.corelib.util.zxing.qrcode.CaptureActivity" />
    <!-- 选择图片 -->
    <activity android:name="com.demo.corelib.util.image.ImageSingleSelectActivity" />
    <!-- 预览图片 -->
    <activity android:name="com.demo.corelib.util.image.ImagePagerActivity" />
    ......
</application>
```

