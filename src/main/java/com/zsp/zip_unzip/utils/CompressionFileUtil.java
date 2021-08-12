package com.zsp.zip_unzip.utils;


import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.z.ZCompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author ：zsp
 */
public class CompressionFileUtil {
    /**
     * @param filePath  需要解压的zip文件的完成路径。
     * @param unzipPath 解压过后生成文件的存放路径
     * @description: 对zip文件进行解压。
     * @return: boolean
     */
    public static boolean zipUnCompress(String filePath, String unzipPath) throws IOException {

        System.out.println("开始解压ZIP..........");
        FileInputStream fis = null;
        ZipArchiveInputStream zis = null;
        try {
            File file = new File(filePath);
            fis = new FileInputStream(file);
            zis = new ZipArchiveInputStream(fis);
            ZipArchiveEntry nze = null;
            while ((nze = zis.getNextZipEntry()) != null) {
                FileOutputStream os = null;
                BufferedOutputStream bos = null;
                try {
                    System.out.println("正在解压....." + nze.getName());
                    //自动添加File.separator文件路径的分隔符，根据系统判断是\\还是/
                    String dir = unzipPath + File.separator + nze.getName(); //解压全路径
                    System.out.println("dir---" + dir);
                    File file1 = null;
                    if (nze.isDirectory()) {
                        file1 = new File(dir);
                        file1.mkdirs();
                    } else {
                        file1 = new File(dir);
                        os = new FileOutputStream(file1);
                        bos = new BufferedOutputStream(os);
                         /*byte [] bt = new byte[1024];
                         int len = 0;
                         while((len = zis.read(bt,0,1024)) != -1){
                             bos.write(bt,0,len);
                         }*/
                        IOUtils.copy(zis, bos); //作用与上面注释代码一样
                    }
                    System.out.println("解压完成......");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return false;
                } finally {
                    if (bos != null) {
                        bos.close();
                    }
                    if (os != null) {
                        os.close();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (zis != null) {
                zis.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return true;
    }

    /**
     * @param filesPathArray 多个文件的绝对路径，是一个数组。
     * @param zipFilePath    生成的压缩文件的位置，包括生成的文件名，如D:\zip\test.zip
     * @description: 将多个文件压缩成ZIP压缩包。
     * @return: boolean
     */
    public static boolean zipCompression(String[] filesPathArray, String zipFilePath) throws Exception {
        System.out.println("开始压缩ZIP文件");
        ZipArchiveOutputStream zos = null;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(zipFilePath));
            zos = new ZipArchiveOutputStream(fos);
            for (String filePath : filesPathArray) {
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    File file = new File(filePath);
                    // 第二个参数如果是文件全路径名,那么压缩时也会将路径文件夹也缩进去;
                    // 我们只压缩目标文件,而不压缩该文件所处位置的相关文件夹,所以这里我们用file.getName()
                    System.out.println("开始压缩..." + file.getName());
                    ZipArchiveEntry zae = new ZipArchiveEntry(file, file.getName());
                    zos.putArchiveEntry(zae);
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    int count;
                    byte[] bt = new byte[1024];
                    while ((count = bis.read(bt, 0, 1024)) != -1) {
                        zos.write(bt, 0, count);
                    }
                } finally {
                    zos.closeArchiveEntry();
                    if (bis != null)
                        bis.close();
                    if (fis != null)
                        fis.close();
                }
            }
        } finally {
            if (zos != null)
                zos.close();
            if (fos != null)
                fos.close();
        }
        System.out.println("压缩完成......");
        return true;
    }

    /**
     * @param inputStream 每种TAR文件用不同的输入流，unCompress方法中已注明
     * @param unTarPath   TAR文件解压后的存放路径
     * @description: 解压TAR类文件，包括.TAR .TAR.BZ2 .TAR.GZ
     * @return: void
     */
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
                        //这里如果是非tar格式的一定要创建好文件夹在用，血的教训
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

    public static boolean unCompress(String filePath,String unCompressPath) throws Exception {
        String fileType = filePath.toUpperCase();
        if(fileType.endsWith(".TAR")){
            System.out.println("解压的.TAR包");
            //.TAR包用一般的FileInputStream流读取
            unTar(new FileInputStream(filePath),unCompressPath);
        }
        else if (fileType.endsWith(".GZ")){
            String tar = useCommonToUnGZ(filePath);
            unCompress(tar,unCompressPath);
        }
        else if (fileType.endsWith(".Z")){
            String tar = useTurnZToTar(filePath);
            unCompress(tar,unCompressPath);
        }
        else if(fileType.endsWith(".TAR.GZ")){
            System.out.println("解压的.TAR.GZ包");
            //.TAR.GZ包要用GzipCompressorInputStream读取
            unTar(new GzipCompressorInputStream(new FileInputStream(filePath)),unCompressPath);
        }
        else if(fileType.endsWith(".TAR.BZ2")){
            System.out.println("解压的.TAR.BZ2包");
            unTar(new BZip2CompressorInputStream(new FileInputStream(filePath)),unCompressPath);
        }
        else if(fileType.endsWith(".ZIP")){
            System.out.println("解压的.ZIP包");
            zipUnCompress(filePath,unCompressPath);
        }
        else{
            System.out.println("暂不支持该种格式文件的解压");
        }
        return true;
    }


    public static String useCommonToUnGZ(String inputPath) throws IOException {
        InputStream fin = Files.newInputStream(Paths.get(inputPath));
        String replace = inputPath.replace(".gz", ".tar");
        BufferedInputStream in = new BufferedInputStream(fin);
        OutputStream out = Files.newOutputStream(Paths.get(replace));
        GzipCompressorInputStream gzIn = new GzipCompressorInputStream(in);
        int buffersize=1024;
        final byte[] buffer = new byte[buffersize];
        int n = 0;
        while (-1 != (n = gzIn.read(buffer))) {
            out.write(buffer, 0, n);
        }
        out.close();
        gzIn.close();

        return replace;
    }

    public static String useTurnZToTar(String inputPath) throws IOException {
        String replace = inputPath.replace(".z", ".tar");
        InputStream fin = Files.newInputStream(Paths.get(inputPath));
        BufferedInputStream in = new BufferedInputStream(fin);
        OutputStream out = Files.newOutputStream(Paths.get(replace));
        ZCompressorInputStream zIn = new ZCompressorInputStream(in);
        int buffersize=1024;
        final byte[] buffer = new byte[buffersize];
        int n = 0;
        while (-1 != (n = zIn.read(buffer))) {
            out.write(buffer, 0, n);
        }
        out.close();
        zIn.close();
        return replace;
    }

}