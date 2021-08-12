package com.zsp.zip_unzip.utils;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.z.ZCompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestUtils {



    public static void unTar(InputStream inputStream, String unTarPath) throws IOException {

        FileInputStream fis = null;
        TarArchiveInputStream tis = null;
        try {
            tis = new TarArchiveInputStream(inputStream);
            TarArchiveEntry nte = null;
            System.out.println("开始解压......");
            while ((nte = tis.getNextTarEntry()) != null) {
                String dir = unTarPath + File.separator + nte.getName();
                System.out.println("正在解压......" + dir);
                FileOutputStream fos = null;
                BufferedOutputStream bos = null;
                try {
                    if (nte.isDirectory()) {
                        File file1 = new File(dir);
                        file1.mkdirs();
                    } else {
                        File file2 = new File(dir);
                        fos = new FileOutputStream(file2);
                        bos = new BufferedOutputStream(fos);
                        IOUtils.copy(tis, bos);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bos != null) {
                        bos.close();
                    }
                    if (fos != null) {
                        fos.close();
                    }
                }
            }
            System.out.println("解压完成......");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (tis != null) {
                tis.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
    }




    public static void useCommonToUnGZ() throws IOException {
        InputStream fin = Files.newInputStream(Paths.get("D:\\zipDemo\\FileName.gz"));
        BufferedInputStream in = new BufferedInputStream(fin);
        OutputStream out = Files.newOutputStream(Paths.get("D:\\zipDemo\\FileName.tar"));
        GzipCompressorInputStream gzIn = new GzipCompressorInputStream(in);
        int buffersize=1024;
        final byte[] buffer = new byte[buffersize];
        int n = 0;
        while (-1 != (n = gzIn.read(buffer))) {
            out.write(buffer, 0, n);
        }
        out.close();
        gzIn.close();
    }


    public static void useTurnZToTar() throws IOException {
        InputStream fin = Files.newInputStream(Paths.get("FileName.tar.Z"));
        BufferedInputStream in = new BufferedInputStream(fin);
        OutputStream out = Files.newOutputStream(Paths.get("FileName.tar"));
        ZCompressorInputStream zIn = new ZCompressorInputStream(in);
        int buffersize=1024;
        final byte[] buffer = new byte[buffersize];
        int n = 0;
        while (-1 != (n = zIn.read(buffer))) {
            out.write(buffer, 0, n);
        }
        out.close();
        zIn.close();
    }






    public static File getSelectedArchiverFile() {
        return new File("D:\\配置表执行步骤.pdf.gz");
    }
}
