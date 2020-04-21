package com.cat.zeus.utils;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class CommonUtil {
    public static void fileCopyWithFileChannel_1(File srcFile, File disFile) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        FileChannel fileChannelInput = null;
        FileChannel fileChannelOutput = null;

        try {
            fileInputStream = new FileInputStream(srcFile);
            fileOutputStream = new FileOutputStream(disFile);
            fileChannelInput = fileInputStream.getChannel();
            fileChannelOutput = fileOutputStream.getChannel();

            fileChannelInput.transferTo(0, fileChannelInput.size(), fileChannelOutput);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(fileInputStream, fileOutputStream, fileChannelInput, fileChannelOutput);
        }
    }

    public static void fileCopyWithFileChannel(File srcFile, File disFile) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        FileChannel fileChannelInput = null;
        FileChannel fileChannelOutput = null;

        try {
            fileInputStream = new FileInputStream(srcFile);
            fileOutputStream = new FileOutputStream(disFile);
            fileChannelInput = fileInputStream.getChannel();
            fileChannelOutput = fileOutputStream.getChannel();

            fileChannelInput.transferTo(0, fileChannelInput.size(), fileChannelOutput);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(fileInputStream, fileOutputStream, fileChannelInput, fileChannelOutput);
        }
    }

    public static void close(Closeable... closeables) {
        if (closeables != null) {
            for (Closeable c : closeables) {
                try {
                    c.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
