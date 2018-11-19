package com.akhutornoy.shoppinglist.settings.dbbackup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import timber.log.Timber;

public class Zipper {

    /**
     * note: @param outputStream will be closed here
     */
    public void zipAll(File sourceFolder, OutputStream outputStream) {
        try(ZipOutputStream zipOut = new ZipOutputStream(outputStream)) {
            //put only files of sourceFolder but don't create sourceFolder in zip
            if (sourceFolder.isDirectory()) {
                for (File file : sourceFolder.listFiles()) {
                    zipFile(file, file.getName(), zipOut);
                }
            } else {
                zipFile(sourceFolder, sourceFolder.getName(), zipOut);
            }
            zipOut.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }

        try(FileInputStream fis = new FileInputStream(fileToZip)){
            ZipEntry zipEntry = new ZipEntry(fileName);
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            Timber.d("Backup of: %s", fileToZip.getName());
        }
    }

    /**
     * note: @param inputStream will be closed here
     */
    public void unzipAll(File destDir, InputStream inputStream) {
        byte[] buffer = new byte[1024];
        try (ZipInputStream zis = new ZipInputStream(inputStream)) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                File newFile = new File(destDir, zipEntry.getName());
                try(FileOutputStream fos = new FileOutputStream(newFile)){
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                }
                Timber.d("Restored file: %s", newFile.getName());
                zipEntry = zis.getNextEntry();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
