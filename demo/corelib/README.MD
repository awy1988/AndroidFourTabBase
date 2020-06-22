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

