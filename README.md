# circleProgressbar
圆形进度条的实现
* 关于如何发布开源库 : [Android 发布开源库到 JitPack](https://gold.xitu.io/entry/58811ffe128fe1006822a213)

> PS:这是我发布的第一个开源库，蟹蟹

* 如何使用：gradle:
  <pre>
  <code>
    allprojects {
      repositories {
        maven { url 'https://jitpack.io' }
      }
    }
  </code>
  </pre>
 `compile 'com.github.duckAndroid:circleProgressbar:x.y.z'` <br/><br/> 目前最新版本`compile 'com.github.duckAndroid:circleProgressbar:0.0.2'` <span>0.0.2可能已经不是最新版本</span>
 
 ```xml
 
    <span>布局文件中:</span>
    <com.pythoncat.circleprogressbar.CircleProgressBar
            xmlns:app="http://schemas.android.com/apk/res-auto"
            android:id="@+id/circle_progressBar"
            android:layout_width="80dp"
            android:layout_height="80dp"
            app:borderColor="@color/colorPrimary"
            app:borderVisible="true"
            app:bordersWidth="1dp"
            app:progress="0"
            app:progressColor="@color/colorAccent"
            app:progressWidth="8dp"
            app:textColor="@color/colorPrimaryDark"
            app:textSize="14sp"
            app:textVisible="false"
            app:useCenter="true"/>
 
 ```
 
 * 关于 circleProgressbar 本身：[android:圆形进度条的实现](http://blog.csdn.net/ducklikejava/article/details/54708879)
   
