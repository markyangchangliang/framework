package com.markyang.framework.util;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.URL;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * IO操作工具类
 *
 * @author yangchangliang
 * @version 1
 */
@Slf4j
public final class IoOperationUtils {

    /**
     * Base64图片头
     */
    private static final String BASE64_IMAGE_HEADER = "data:image/%s;base64,";

    /**
     * 将一个URL对应的资源转为Byte数组
     * @param url 链接
     * @return Byte数组Optional对象
     */
    public static Optional<byte[]> getBytesFromUrl(String url) {
        try {
            return Optional.of(IOUtils.toByteArray(new URL(url)));
        } catch (IOException e) {
            log.error("转换[{}]为ByteArray时出错：" + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * 对图片进行Base64编码
     * @param url url
     * @return 编码字符串Optional对象
     */
    public static Optional<String> convertImageToBase64(String url) {
        String extension = FilenameUtils.getExtension(url);
        Base64.Encoder encoder = Base64.getEncoder();
        if (StringUtils.isBlank(extension)) {
            return Optional.empty();
        }
        return getBytesFromUrl(url).map(bytes -> String.format(BASE64_IMAGE_HEADER, extension).concat(encoder.encodeToString(bytes)));
    }

    /**
     * 将输入流图片转为Base64
     * @param extension 图片类型后缀
     * @param inputStream 输入流
     * @return 编码字符串Optional
     */
    public static Optional<String> convertImageToBase64(String extension, InputStream inputStream) {
        Base64.Encoder encoder = Base64.getEncoder();
        if (StringUtils.isBlank(extension) || Objects.isNull(inputStream)) {
            return Optional.empty();
        }
        try {
            return Optional.of(String.format(BASE64_IMAGE_HEADER, extension).concat(encoder.encodeToString(IOUtils.toByteArray(inputStream))));
        } catch (IOException e) {
            log.error("转换InputStream流对象为ByteArray时出错：" + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * 压缩成ZIP
     *
     * @param source 压缩文件路径
     * @param destination 压缩后的文件路径
     */
    public static void compressToZip(String source, String destination) {
        try (
            ZipOutputStream zipOutputStream = new ZipOutputStream(FileUtils.openOutputStream(FileUtils.getFile(destination)))
        ) {
            File sourceFile = FileUtils.getFile(source);
            doCompress(sourceFile, zipOutputStream, sourceFile.getName());
        } catch (IOException e) {
            log.error("压缩文件出现错误：" + e.getMessage());
        }
    }

    /**
     * 递归压缩方法
     *
     * @param sourceFile 源文件
     * @param zipOutputStream zip输出流
     * @param name 压缩后的名称
     * @throws IOException 异常
     */
    private static void doCompress(File sourceFile, ZipOutputStream zipOutputStream, String name) throws IOException {
        if (sourceFile.isFile()) {
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zipOutputStream.putNextEntry(new ZipEntry(name));
            IOUtils.copy(FileUtils.openInputStream(sourceFile), zipOutputStream);
            zipOutputStream.closeEntry();
        } else {
            File[] files = sourceFile.listFiles();
            if (ArrayUtils.isEmpty(files)) {
                // 需要对空文件夹进行处理
                // 空文件夹的处理
                zipOutputStream.putNextEntry(new ZipEntry(name + "/"));
                zipOutputStream.closeEntry();
                return;
            }
            for (File file : files) {
                // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                doCompress(file, zipOutputStream, name + "/" + file.getName());
            }
        }
    }

    /**
     * 根据指定大小压缩图片
     *
     * @param imageBytes  源图片字节数组
     * @param expectedSize 指定图片大小，单位kb
     * @return 压缩质量后的图片字节数组
     */
    public static byte[] compressImage(byte[] imageBytes, long expectedSize) {
        long expectedByteSize = expectedSize * 1024;
        int imageSize = imageBytes.length;
        if (ArrayUtils.isEmpty(imageBytes) || imageSize < expectedByteSize) {
            return imageBytes;
        }
        double accuracy = getAccuracy(imageSize / 1024);
        try {
            while (imageSize > expectedByteSize) {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageSize);
                Thumbnails.of(inputStream)
                    .scale(accuracy)
                    .outputQuality(accuracy)
                    .toOutputStream(outputStream);
                imageBytes = outputStream.toByteArray();
                imageSize = imageBytes.length;
                // 关闭流
                inputStream.close();
                outputStream.close();
            }
        } catch (IOException e) {
            log.error("图片压缩失败：{}", e.getMessage());
        }
        return imageBytes;
    }

    /**
     * 自动调节精度(经验数值)
     *
     * @param size 源图片大小
     * @return 图片压缩质量比
     */
    private static double getAccuracy(long size) {
        double accuracy;
        if (size < 900) {
            accuracy = 0.85;
        } else if (size < 2047) {
            accuracy = 0.6;
        } else if (size < 3275) {
            accuracy = 0.44;
        } else {
            accuracy = 0.4;
        }
        return accuracy;
    }
}
