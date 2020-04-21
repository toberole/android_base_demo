package com.xiaoge.org;

import android.Manifest;
import android.media.AudioFormat;
import android.os.Environment;

public class Constant {
    public static final String[] PS = new String[]{
            Manifest.permission.REQUEST_INSTALL_PACKAGES,
            Manifest.permission.CAMERA,
            Manifest.permission.INTERNET,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static final int MIN_BUFFER = 2000;

    public static final int DEFAULT_SAMPLE_RATE = 16000;

    public static final int DEFAULT_CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;

    public static final int DEFAULT_ENCODE_FORMAT = AudioFormat.ENCODING_PCM_16BIT;

    public static final String TEMP_DIR = Environment.getExternalStorageDirectory() + "/dbl_test";

    public static final String DREAM_LOG = "\n" +
            "\n^^^^^^^^^^^^^less code,less bug^^^^^^^^^^^^^^\n" +
            "                   _ooOoo_\n" +
            "                  o8888888o\n" +
            "                  88\" . \"88\n" +
            "                  (| -_- |)\n" +
            "                  O\\  =  /O\n" +
            "               ____/`---'\\____\n" +
            "             .'  \\\\|     |//  `.\n" +
            "            /  \\\\|||  :  |||//  \\\n" +
            "           /  _||||| -:- |||||-  \\\n" +
            "           |   | \\\\\\  -  /// |   |\n" +
            "           | \\_|  ''\\---/''  |   |\n" +
            "           \\  .-\\__  `-`  ___/-. /\n" +
            "         ___`. .'  /--.--\\  `. . __\n" +
            "      .\"\" '<  `.___\\_<|>_/___.'  >'\"\".\n" +
            "     | | :  `- \\`.;`\\ _ /`;.`/ - ` : | |\n" +
            "     \\  \\ `-.   \\_ __\\ /__ _/   .-` /  /\n" +
            "======`-.____`-.___\\_____/___.-`____.-'======\n" +
            "                   `=---='\n" +
            "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n" +
            "            佛祖保佑       永无BUG\n" +
            "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n";

}
