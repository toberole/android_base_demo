package com.cat.zeus.utils;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public class IOUtil {
    public static String stream2Str(InputStream inputStream) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int len = 0;
        byte[] buffer = new byte[1024];
        try {
            while ((len = inputStream.read(buffer, 0, buffer.length)) > 0) {
                bos.write(buffer, 0, len);
                bos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new String(bos.toByteArray());
    }

    public static void closeQuietly(Closeable... closeable) {
        if (closeable != null) {
            try {
                for (Closeable c : closeable) {
                    if (null != c) {
                        c.close();
                    }
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}
