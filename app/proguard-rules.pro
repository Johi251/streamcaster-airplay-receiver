# Add project-specific ProGuard rules here. By default, the flags in this file
# are appended to the built-in ProGuard rules. Keep UxPlay / ExoPlayer symbols.

# OkHttp
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }

# Keep native AirPlay bridge method names (UxPlay JNI)
-keep class com.streamcaster.receiver.** { *; }
