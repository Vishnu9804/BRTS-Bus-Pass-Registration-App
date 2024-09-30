package com.example.project;

import android.content.Context;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Fileee {
    public void saveAnswerFileFromUri(Uri uri, File destFile, Context context) {
        try {
            ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
            if (pfd != null) {
                FileDescriptor fd = pfd.getFileDescriptor();
                InputStream fileInputStream = new FileInputStream(fd);
                OutputStream fileOutputStream = new FileOutputStream(destFile);
                byte[] buffer = new byte[1024];
                int length = fileInputStream.read(buffer);
                while (fileInputStream.read(buffer) > 0) {
                    fileOutputStream.write(buffer, 0, length);
                }
                fileOutputStream.flush();
                fileInputStream.close();
                fileOutputStream.close();
                pfd.close();
            }
        } catch (IOException e) {}
    }
}
