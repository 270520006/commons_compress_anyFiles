package com.zsp.zip_unzip.utils.strategy_pattern;

public interface CompressAndUncompressUtils {
    boolean compress(String filePath,String zipPath);
    boolean uncompress(String filePath,String unCompressPath);
}
