package com.zsp.zip_unzip;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;
import com.zsp.zip_unzip.utils.*;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@SpringBootTest
class ZipUnzipApplicationTests {
    private static String Path="D:\\zipDemo\\";
    @Test
    void unZipByMyUtils() throws Exception {
        //自己写个解压缩工具类,一定要提前创建好文件夹，一定要！！
        //官方包有bug，tar zip，都没事，但是其他格式不提前创建就报错。
//        CompressionFileUtil.unCompress("zipFile.zip", "unzip"); //zip
//        CompressionFileUtil.unCompress("FileName.tar","untar"); //tar
//        CompressionFileUtil.unCompress("FileName.tar.gz","untarGZ");// .tar.gz
//        CompressionFileUtil.unCompress("FileName.gz","unGZ"); //这里一定要创建好文件夹在用，血的教训
//         CompressionFileUtil.unCompress("FileName.Z","unZ");
    }



    @Test
    void zipByHutool() { //压缩可以压缩到各种格式中
        //1、把当前目录作为一个zip文件压缩起来，当前目录有多少文件就压缩少
//        ZipUtil.zip("C:\\Users\\zhusp\\Desktop\\笔记\\zip_unzip\\src\\main\\resources\\zipDemo");
        //2、把当前目录作为一个zip压缩目录，将其压缩后存储到一个zip上，zip要自己命名
//        ZipUtil.zip("C:\\Users\\zhusp\\Desktop\\笔记\\zip_unzip\\src\\main\\resources\\zipDemo"
//                    ,"D:\\zipDemo\\zipDemo.zip");
        //3、可以选择包含不包含打包目录，true包含，false不包含。就是有没有zipDemo这个文件夹
        ZipUtil.zip( "C:\\Users\\zhusp\\Desktop\\笔记\\zip_unzip\\src\\main\\resources\\zipDemo",
                Path+"zipFile.tar", false);
//        4、也可以选择多个文件/目录压缩到指定目录，覆盖会报错
//        ZipUtil.zip(FileUtil.file("D:\\zipDemo\\zipMatch.z"),
//                false,
//                FileUtil.file("C:\\Users\\zhusp\\Desktop\\笔记\\zip_unzip\\src\\main\\resources\\zipDemo"),
//                FileUtil.file("C:\\Users\\zhusp\\Desktop\\笔记\\zip_unzip\\src\\main\\resources\\zipDemo.zip")
//        );
    }
    @Test
    void unzipByHutool(){ //解压只能解压zip，非常鸡肋
        //可以实现，但是只能实现zip，较为鸡肋。
        ZipUtil.unzip("D:\\zipDemo\\zipFile.tar","D:\\zipDemo\\test");
    }

}
