# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\develop\android_sdk/tools/proguard/proguard-android.txt
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

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


-dontoptimize
-dontusemixedcaseclassnames
-renamesourcefileattribute SourceFile
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,EnclosingMethod
-dontpreverify
-verbose

# Preserve all annotations.
-keepattributes *Annotation*

# Preserve all native method names and the names of their classes.
-keepclasseswithmembernames class * {
    native <methods>;
}

# Preserve the special static methods that are required in all enumeration
# classes.

-keepclassmembers class * extends java.lang.Enum {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class com.sogou.speech.longasr.main.imple.DictationProcessManager {
    public <fields>;
    public <methods>;
}

-keep class com.sogou.speech.longasr.main.imple.DictationProcessManager$Builder{
    public <methods>;
}

-keep class com.sogou.speech.longasr.main.IDictationProcessListener{
    public <methods>;
}

-keep class com.sogou.speech.longasr.main.IDictationProcessManager {
    public <methods>;
}

-keep class com.sogou.speech.longasr.util.LogUtil{
    public <methods>;
}

-keep class com.sogou.speech.longasr.util.AudioSaver{
    public <methods>;
}
-keep class com.sogou.speech.settings.IErrorCode{
   *;
}
-keep class com.sogou.speech.longasr.util.CommonSharedPreference{
    public <methods>;
}

-keep class com.sogou.speech.longasr.util.FetchTokenTask{
    public <methods>;
}

-keep class com.sogou.speech.longasr.speex.SpeexEncoder{
    public <methods>;
}

-keep class com.sogou.speech.longasr.speex.SpeexDecoder{
    public <methods>;
}

-keep class com.sogou.speech.utils.LogUtil {
    public <methods>;
}

-keep class com.sogou.speech.utils.LogUtil$DefaultLogger {
    *;
}

-keep class com.sogou.speech.longasr.util.FetchTokenTask{
    public *;
}

-keep class com.sogou.speech.recorder.TrPenMusicRecorder{
    public *;
}



-keep class com.sogou.speech.longasr.util.CommonSharedPreference{
    public *;
}

-keep class com.sogou.speech.longasr.util.CommonUtils{
    public *;
}

-keep class com.sogou.speech.longasr.util.FileUtils{
    public *;
}

-keep class com.sogou.speech.longasr.util.FileUtil{
    public *;
}

-keep class com.sogou.speech.longasr.util.RingBuffer{
    *;
}

-keep class com.sogou.speech.longasr.util.RingBufferFlip{
    *;
}

-keep class com.sogou.speech.longasr.util.WavUtil{
    public *;
}

-keep interface com.sogou.speech.settings.ISettingUtils{
    public *;
}

-keep class com.sogou.speech.main.SogouAsrTTSEngine{
    public *;
    public <methods>;
    public <fields>;
}

-keep class com.sogou.speech.main.SogouAsrTTSEngine$*{
    *;
}

-keep interface com.sogou.speech.main.TRCallback{
    *;
}

-keep interface com.sogou.speech.longasr.util.FetchTokenTask$TokenFetchListener{
    *;
}
-keep interface com.sogou.speech.oggopus.IOggOpusEncoder{
    *;
}

-keep class com.sogou.speech.longasr.main.rmt.AppConstant{
   public <methods>;
}

-keep class com.sogou.speech.longasr.bean.HookBean{
   public <methods>;
}

-keep class com.sogou.voice.SogouFront{
   <fields>;
   <methods>;
}

-keep class com.sogou.voice.AfeFd{
   <fields>;
   <methods>;
}

-keep class com.sogou.voice.SgAlg{
   <fields>;
   <methods>;
}


-keep class com.sogou.voice.SogouFrontSimple{
   <fields>;
   <methods>;
}

-keep class com.sogou.voice.SogouFrontWrapper{
   <fields>;
   <methods>;
}


-keep class com.sogou.speech.longasr.util.AlarmUtil {
    public <methods>;
}

-keep class com.sogou.speech.longasr.util.AlarmReceiver {
    public <methods>;
}

-keep class com.sogou.speech.longasr.bean.HookBean {
    public <methods>;
}

########################################################
-keep class com.sogou.speech.oggopus.FrameSizeConstants{
   <fields>;
   <methods>;
}

-keep class com.sogou.speech.SDKInitiator{
   <fields>;
   <methods>;
}

-keep class com.sogou.speech.oggopus.IOggOpusEncoder{
   <fields>;
   <methods>;
}

-keep class com.sogou.speech.oggopus.OggOpusEncoder{
   <fields>;
   <methods>;
}

-keep enum com.sogou.speech.enums.AudioEncoding{
    *;
}

-keep enum com.sogou.speech.enums.DenoiseMode{
    *;
}

-keep enum com.sogou.speech.enums.OpenMode{
    *;
}

-keep enum com.sogou.speech.enums.TRPenAudioRecoder{
    *;
}

-keep enum com.sogou.speech.enums.TRPenAudioRecoderMode{
    *;
}

-keep interface com.sogou.speech.recoder.TrPenAudioRecoderHandler$ITrPenRecorderTaskCallback{
    *;
}

-keep class com.sogou.speech.RecognizeTask{
   <fields>;
   <methods>;
}

-keep interface com.sogou.speech.RecognizeTask$RecognizeTaskCallback{
    *;
}

-keep class com.sogou.speech.longasr.bean.NonSpeechSoundMsg{
    *;
}

-keep class com.sogou.speech.longasr.bean.SpeakerMsg{
    *;
}

-keep class com.sogou.speech.recorder.TrPenMicEventConstants{
    *;
}

-keep class com.sogou.speech.recorder.TrPenDriver {
    *;
}

-keep class com.sogou.speech.longasr.main.imple.BaseManager$Builder {
    public <methods>;
}

-keep class com.sogou.speech.recorder.TrPenAudioRecoderHandler$ITrPenRecorderTaskCallback {
    public <methods>;
}

-keep class com.sogou.speech.recorder.ITrPenRecorderCallback {
    public <methods>;
}

-keep class com.sogou.speech.recorder.ITrPenRecorder {
    public <methods>;
}

-keep class com.sogou.speech.recorder.TrPenAudioRecoderHandler {
    public <methods>;
    public static <methods>;
}

-keep class com.sogou.speech.recorder.TrPenReocrderConfig {
    *;
}





#
#-keep class com.sogou.speech.utils.LogUtil {
#    public <methods>;
#}
#
#-keep class com.sogou.speech.utils.FormatJSONStr {
#    public <methods>;
#}
#
#-keep class com.sogou.speech.utils.FileOperator {
#    public <methods>;
#}
#
#-keep class com.sogou.speech.utils.Detectwav {
#    public static <methods>;
#}
#
#-keep class com.sogou.speech.settings.CustomizeSetting {
#    public static <methods>;
#}
#
#-keep class com.sogou.speech.entity.SpeechError {
#    public <fields>;
#    public <methods>;
#}
#
#-keep class com.sogou.speech.entity.SpeechSemResult {
#    public <fields>;
#    public <methods>;
#}
#
#-keep class com.sogou.speech.listener.SpeechUserListener {
#    public <fields>;
#    public <methods>;
#}
#
#-keep class com.sogou.speech.main.SogouAsrSemEngine {
#    public <fields>;
#    public <methods>;
#}
#
#-keep class com.sogou.speech.wakeup.IWakeUpStateListener {
#    public <fields>;
#    public <methods>;
#}
#
#-keep class com.sogou.speech.wakeup.WakeUpManager {
#    public <fields>;
#    public <methods>;
#}
#
#-keep class com.sogou.speech.main.SogouAsrSemEngine$* {
#    <fields>;
#    <methods>;
#}
#
#-keep class com.sogou.speech.pocketapi.PocketJNIInterface {
#    <fields>;
#    <methods>;
#}
#
#-keep class com.sogou.speech.entity.SemResult {
#    <fields>;
#    <methods>;
#}
#
#-keep class com.sogou.speech.listener.SemUserListener {
#    <fields>;
#    <methods>;
#}
#
#-keep class com.sogou.speech.sem.SemMananger {
#    public <fields>;
#    public <methods>;
#}
#
#-keep class com.sogou.speech.sem.SemMananger$* {
#    <fields>;
#    <methods>;
#}
#
#-keep class com.sogou.speech.sem.OnlineSemQuery {
#    public <fields>;
#    public <methods>;
#}
#
#-keep class com.sogou.speech.offline.sem.OfflineSemJniInterface {
#    public <fields>;
#    public <methods>;
#}

#-keepclassmembers class com.sohu.inputmethod.voice.encode.SpeexIMEInterface{
#    *;
#}
-keepclassmembers class com.sohu.inputmethod.voice.encrypt.EncryptIMEInterface{
   *;
}


-keep class com.sogou.speech.asr.v1.** {*;}
-keep class com.sogou.speech.auth.v1.** {*;}
-keep class com.sogou.speech.mt.v1.** {*;}
-keep class com.sogou.speech.rmt.v1alpha1.** {*;}
-keep class com.sogou.speech.tts.v1.** {*;}
-keep class com.google.v1.rpc.** {*;}

# Keep names - Native method names. Keep all native class/method names.
-keepclasseswithmembers,allowshrinking class * {
    native <methods>;
}



################## tm ###################


-keep class com.sogou.speech.tm.main.TRCallbackTM{
    public <methods>;
}


# Keep names - Native method names. Keep all native class/method names.
-keepclasseswithmembers,allowshrinking class * {
    native <methods>;
}


-keep class com.sogou.speech.tm.main.SogouAsrTTSEngineTM{
    public <fields>;
    public <methods>;
}

-keep class com.sogou.speech.tm.main.SogouAsrTTSEngineTM$*{
    public <fields>;
    public <methods>;
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep public class com.google.gson.**
-keep public class com.google.gson.** {public private protected *;}

-keepattributes Signature
-keepattributes *Annotation*
-keep public class com.project.mocha_patient.login.SignResponseData { private *; }

-keep class com.sogou.speech.tm.utils.LogUtil {
    public <methods>;
}

-keep class com.sogou.speech.tm.model.GlobalParam{
    *;
}
-keep class com.sogou.speech.tm.model.NlpParam{
    *;
}

-keep class com.sogou.speech.tm.utils.FileOperator {
    public <methods>;
}


-keep class com.sogou.speech.tm.settings.ISettingUtils {
    public <fields>;
}

# audio
-keep class com.sogou.speech.tm.audio.subject.impl.ConcreteAudioSubject{
    public <fields>;
    public <methods>;
}

-keep class com.sogou.speech.tm.audio.source.AbstractAudioSource{
    public <fields>;
    public <methods>;
}

-keep class com.sogou.speech.tm.utils.AppConstant{
    *;
}

-keep class com.sogou.speech.tm.utils.AppConstant$*{
    *;
}

-keep class com.sogou.speech.ssasr.Engine{
    *;
}

-keep class com.sogou.speech.trans.Engine{
    *;
}

-keep class com.sohu.inputmethod.voice.encode.SpeexIMEInterface {
    *;
}

-keep class com.sogou.speech.tm.utils.CommonUtils{
    *;
}

-keep interface com.sogou.speech.tm.utils.CommonUtils$PathProxy{
    *;
}

-keep class com.sogou.speech.entity.ExtraInfo{
    *;
}





################## tm ###################