package com.zsp.zip_unzip.utils.strategy_pattern;

import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.util.ZipUtil;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;

/**
 * @author xmzsp
 * 如果解压成功就返回true，解压失败就返回false。压缩同理
 */
public class ZipAndUnzipUtils implements CompressAndUncompressUtils{
    @Override
    public boolean compress(String filePath,String zipPath) {
        try {
            ZipUtil.zip(filePath,zipPath);
            return true;
        } catch (UtilException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean uncompress(String filePath,String unCompressPath) {
        System.out.println("解压的.ZIP包");
        try {
            return zipUnCompress(filePath,unCompressPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }



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

}
