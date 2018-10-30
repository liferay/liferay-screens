# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/silvio/Documents/Tools/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-keep class com.lsjwzh.widget.recyclerviewpager.**
-dontwarn com.lsjwzh.widget.recyclerviewpager.**

-keep class com.daimajia.swipe.SwipeLayout.**
-dontwarn com.daimajia.swipe.SwipeLayout.**

-keep class com.snappydb.** { *; }
-dontwarn com.snappydb.**

-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault
-dontwarn javax.xml.bind.DatatypeConverter
-dontwarn java.lang.invoke.SerializedLambda

-dontwarn okio.**

-dontwarn sun.misc.**
-dontwarn sun.reflect.**
-dontwarn sun.nio.ch.**
-dontwarn java.beans.**
#-keep,allowshrinking class com.esotericsoftware.** {
#   <fields>;
#   <methods>;
#}
-keep,allowshrinking class java.beans.** { *; }
-keep,allowshrinking class sun.reflect.** { *; }
#-keep,allowshrinking class com.esotericsoftware.kryo.** { *; }
-keep,allowshrinking class sun.nio.ch.** { *; }
-keep class com.esotericsoftware.kryo.** { *; }

-keep class org.apache.cordova.** { *; }
-keep class * extends org.apache.cordova.CordovaPlugin

-keepclassmembers class ** {
    public void onEvent*(***);
}

# Only required if you use AsyncExecutor
-keepclassmembers class * extends de.greenrobot.event.util.ThrowableFailureEvent {
    public <init>(java.lang.Throwable);
}

# Don't warn for missing support classes
-dontwarn de.greenrobot.event.util.*$Support
-dontwarn de.greenrobot.event.util.*$SupportManagerFragment

-keep public class com.google.android.gms.* { public *; }
-dontwarn com.google.android.gms.**

-ignorewarnings