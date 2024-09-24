package com.example.project

import android.content.Context
import android.net.Uri
import android.os.ParcelFileDescriptor
import java.io.*

class FileFunc {

        public  fun saveAnswerFileFromUri(uri: Uri, destFile: File?, context: Context) {
            try {
                val pfd: ParcelFileDescriptor = context.contentResolver.openFileDescriptor(uri, "r")!!
                if (pfd != null) {
                    val fd: FileDescriptor = pfd.getFileDescriptor()
                    val fileInputStream: InputStream = FileInputStream(fd)
                    val fileOutputStream: OutputStream = FileOutputStream(destFile)
                    val buffer = ByteArray(1024)
                    var length: Int
                    while (fileInputStream.read(buffer).also { length = it } > 0) {
                        fileOutputStream.write(buffer, 0, length)
                    }
                    fileOutputStream.flush()
                    fileInputStream.close()
                    fileOutputStream.close()
                    pfd.close()
                }
            } catch (e: IOException) {
            }
        }

}