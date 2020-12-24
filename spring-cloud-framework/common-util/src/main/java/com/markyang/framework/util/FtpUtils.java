package com.markyang.framework.util;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * FTP操作工具类
 *
 * @author yangchangliang
 * @version 1
 */
@Slf4j
public final class FtpUtils {

    /**
     * 连接 FTP 服务器
     *
     * @param hostname FTP 服务器 IP 地址
     * @param port FTP 服务器端口号
     * @param username 登录用户名
     * @param password 登录密码
     * @return FTP连接客户端
     */
    public static FTPClient connectFtpServer(String hostname, int port, String username, String password) {
        FTPClient ftpClient = new FTPClient();
        try {
            // 设置文件传输的编码
            ftpClient.setControlEncoding(StandardCharsets.UTF_8.name());

            // 连接 FTP 服务器
            // 如果连接失败，则此时抛出异常，如ftp服务器服务关闭时
            ftpClient.connect(hostname, port);

            // 登录 FTP 服务器
            // 如果传入的账号为空，则使用匿名登录，此时账号使用 "Anonymous"，密码为空即可
            if (StringUtils.isBlank(username)) {
                ftpClient.login("Anonymous", "");
            } else {
                ftpClient.login(username, password);
            }

            // 设置传输的文件类型
            // BINARY_FILE_TYPE：二进制文件类型
            // ASCII_FILE_TYPE：ASCII传输方式，这是默认的方式
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

            // 确认应答状态码是否正确完成响应
            // 凡是 2开头的 isPositiveCompletion 都会返回 true，因为它底层判断是：
            // return (reply >= 200 && reply < 300)
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                // 如果 FTP 服务器响应错误 中断传输、断开连接
                // abort：中断文件正在进行的文件传输，成功时返回 true,否则返回 false
                // disconnect：断开与服务器的连接，并恢复默认参数值
                ftpClient.abort();
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            log.error("FTP服务器连接登录失败，请检查连接参数是否正确，或者网络是否通畅：{}", e.getMessage());
        }
        return ftpClient;
    }

    /**
     * 下载 FTP 服务器上指定的单个文件，而且本地存放的文件相对部分路径 会与 FTP 服务器结构保持一致
     *
     * @param ftpClient ：连接成功有效的 FTP客户端连接
     * @param absoluteLocalPath ：本地存储文件的绝对路径
     * @param relativeRemotePath ：ftpFile 文件在服务器所在的绝对路径
     * @return bool
     */
    public static boolean downloadFileFromServer(FTPClient ftpClient, String absoluteLocalPath, String relativeRemotePath) {
        // 如果 FTP 连接已经关闭，或者连接无效，则直接返回
        if (!ftpClient.isConnected() || !ftpClient.isAvailable()) {
            log.error("FTP服务器连接已经关闭或者连接无效");
            return false;
        }
        if (StringUtils.isBlank(absoluteLocalPath) || StringUtils.isBlank(relativeRemotePath)) {
            log.error("下载时遇到本地存储路径或者ftp服务器文件路径为空，放弃...");
            return false;
        }
        try {
            // 没有对应路径时，FTPFile[] 大小为0，不会为null
            FTPFile[] ftpFiles = ftpClient.listFiles(relativeRemotePath);
            if (ArrayUtils.isEmpty(ftpFiles)) {
                return false;
            }
            FTPFile ftpFile = ftpFiles[0];
            if (Objects.isNull(ftpFile) || !ftpFile.isFile()) {
                return false;
            }
            // ftpFile.getName():获取的是文件名称，如 123.mp4
            // 必须保证文件存放的父目录必须存在，否则 retrieveFile 保存文件时报错
            File localFile = FileUtils.getFile(absoluteLocalPath, relativeRemotePath);
            FileUtils.forceMkdirParent(localFile);
            OutputStream outputStream = FileUtils.openOutputStream(localFile);
            String workDir = FilenameUtils.getPath(relativeRemotePath);
            if (StringUtils.isBlank(workDir)) {
                workDir = "/";
            }
            // 文件下载前，FTPClient工作目录必须切换到文件所在的目录，否则下载失败
            // "/" 表示用户根目录
            ftpClient.changeWorkingDirectory(workDir);
            // 下载指定的 FTP 文件 到本地
            // 1)注意只能是文件，不能直接下载整个目录
            // 2)如果文件本地已经存在，默认会重新下载
            // 3)下载文件之前，ftpClient 工作目录必须是下载文件所在的目录
            // 4)下载成功返回 true，失败返回 false
            ftpClient.retrieveFile(ftpFile.getName(), outputStream);
            outputStream.flush();
            outputStream.close();
            log.info("FTP服务器文件下载完毕：{}", ftpFile.getName());
            return true;
        } catch (IOException e) {
            log.error("下载文件出现错误：{}", e.getMessage());
            return false;
        }
    }

    /**
     * 遍历 FTP 服务器指定目录下的所有文件(包含子孙文件)
     *
     * @param ftpClient ：连接成功有效的 FTP客户端连接
     * @param remotePath ：查询的 FTP 服务器目录，如果文件，则视为无效，使用绝对路径，如"/"、"/video"、"\\"、"\\video"
     * @param relativePaths ：返回查询结果，其中为服务器目录下的文件相对路径，如：\1.png、\docs\overview-tree.html 等
     * @return 文件路径列表
     */
    public static List<String> lookupServerPath(FTPClient ftpClient, String remotePath, List<String> relativePaths) {
        // 如果 FTP 连接已经关闭，或者连接无效，则直接返回
        if (!ftpClient.isConnected() || !ftpClient.isAvailable()) {
            log.error("ftp 连接已经关闭或者连接无效......");
            return Collections.emptyList();
        }
        if (Objects.isNull(relativePaths)) {
            relativePaths = Lists.newArrayListWithExpectedSize(128);;
        }
        try {
            // 转移到FTP服务器根目录下的指定子目录
            // 1)"/"：表示用户的根目录，为空时表示不变更
            // 2)参数必须是目录，当是文件时改变路径无效
			ftpClient.changeWorkingDirectory(remotePath);
			// 如果目录不存在，切换目录机会失败，导致程序递归陷入死循环，使用此方法截止掉
            if(remotePath.length() > 200){
                return Collections.emptyList();
            }
            // listFiles：获取FtpClient连接的当前下的一级文件列表(包括子目录)
            // 1）FTPFile[] ftpFiles = ftpClient.listFiles("/docs/info");
            // 获取服务器指定目录下的子文件列表(包括子目录)，以 FTP 登录用户的根目录为基准，与 FTPClient 当前连接目录无关
            // 2）FTPFile[] ftpFiles = ftpClient.listFiles("/docs/info/springmvc.txt");
            // 获取服务器指定文件，此时如果文件存在时，则 FTPFile[] 大小为 1，就是此文件
            FTPFile[] ftpFiles = ftpClient.listFiles();
            if (ArrayUtils.isNotEmpty(ftpFiles)) {
                for (FTPFile ftpFile : ftpFiles) {
                    if (ftpFile.isFile()) {
                        relativePaths.add(remotePath + File.pathSeparator + ftpFile.getName());
                    } else {
                        lookupServerPath(ftpClient, remotePath + File.pathSeparator + ftpFile.getName(), relativePaths);
                    }
                }
            }
        } catch (IOException e) {
            log.error("遍历FTP服务器指定目录[{}]下的文件时出错：{}", remotePath, e.getMessage());
            return Collections.emptyList();
        }
        return relativePaths;
    }

    /**
     * 同步本地目录与 FTP 服务器目录 1）约定：FTP 服务器有，而本地没有的，则下载下来；本地有，而ftp服务器没有的，则将本地多余的删除 2）始终确保本地与 ftp 服务器内容一致 2）让 FTP 服务器与 本地目录保持结构一致，如 服务器上是 /docs/overview-tree.html，则本地也是 localDir/docs/overview-tree.html
     *
     * @param ftpClient 连接成功有效的 FTPClient
     * @param localSyncDirectory ：与 FTP 目录进行同步的本地目录
     */
    public static void syncLocalDirectory(FTPClient ftpClient, String localSyncDirectory) throws IOException {
        // 如果 FTP 连接已经关闭，或者连接无效，则直接返回
        if (!ftpClient.isConnected() || !ftpClient.isAvailable() || StringUtils.isBlank(localSyncDirectory)) {
            log.error("FTP服务器连接已经关闭或者连接无效");
            return;
        }

        File file = FileUtils.getFile(localSyncDirectory);
        if (!file.exists() || !file.isDirectory()) {
            log.error("指定目录[{}]不存在或者不是一个文件夹", localSyncDirectory);
            return;
        }

        // 获取本地存储目录下的文件
        Collection<File> files = FileUtils.listFiles(file, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        log.info("本地存储目录共有文件数量：{}", files.size());

        // 获取 FTP 服务器下的相对路径
        List<String> relativePaths = lookupServerPath(ftpClient, "", null);
        log.info("FTP 服务器端共有文件数量：{}", relativePaths.size());

        // 遍历 本地存储目录下的文件
        // 1）如果本地文件在 FTP 服务器上不存在，则删除它
        // 2）如果本地文件在 FTP 服务器上存在，则比较两种大小
        // 如果大小不一致，则重新下载
        for (File localFile : files) {
            String localFilePath = localFile.getPath();
            String localFileSuffix = localFilePath.replace(localSyncDirectory, "");
            if (relativePaths.contains(localFileSuffix)) {
                // 本地此文件在 FTP 服务器存在
                // 1)比较大小，如果本地文件与服务器文件大小一致，则跳过
                // 2）如果大小不一致，则删除本地文件，重新下载
                // 3）最后都要删除relativePathList中的此元素，减轻后一次循环的压力*/
                FTPFile[] ftpFiles = ftpClient.listFiles(localFileSuffix);
                log.info("本地文件 在 FTP 服务器已存在：{}", localFile.getPath());
                if (ArrayUtils.isNotEmpty(ftpFiles) && localFile.length() != ftpFiles[0].getSize()) {
                    downloadFileFromServer(ftpClient, localSyncDirectory, localFileSuffix);
                    log.info("本地文件与 FTP 服务器文件大小不一致，重新下载：{}", localFile.getPath());
                }
                relativePaths.remove(localFileSuffix);
            } else {
                log.info("本地文件在 FTP 服务器不存在，删除本地文件：{}", localFile.getPath());
                // 本地此文件在 FTP 服务器不存在
                // 1)删除本地文件
                // 2）如果当前文件所在目录下文件已经为空，则将此父目录也一并删除*/
                FileUtils.forceDelete(localFile);
                File parentFile = localFile.getParentFile();
                while (ArrayUtils.isEmpty(parentFile.list())) {
                    FileUtils.forceDelete(parentFile);
                    parentFile = parentFile.getParentFile();
                }
            }
        }
        for (String relativePath : relativePaths) {
            log.info("FTP 服务器存在新文件，准备下载：{}", relativePath);
            downloadFileFromServer(ftpClient, localSyncDirectory, relativePath);
        }
    }

    /**
     * 上传本地文件 或 目录 至 FTP 服务器----保持 FTP 服务器与本地 文件目录结构一致
     *
     * @param ftpClient 连接成功有效的 FTPClient
     * @param file 待上传的文件 或 文件夹(此时会遍历逐个上传)
     * @return bool
     */
    public static boolean uploadFileToServer(FTPClient ftpClient, File file) {
        // 如果 FTP 连接已经关闭，或者连接无效，则直接返回
        if (!ftpClient.isConnected() || !ftpClient.isAvailable()) {
            log.error("FTP服务器连接已经关闭或者连接无效*****放弃文件上传****");
            return false;
        }
        if (Objects.isNull(file) || !file.exists()) {
            log.error("待上传文件为空或者文件不存在*****放弃文件上传****");
            return true;
        }
        try {
            if (file.isDirectory()) {
                // 如果被上传的是目录时
                // makeDirectory：在 FTP 上创建目录(方法执行完，服务器就会创建好目录，如果目录本身已经存在，则不会再创建)
                // 1）可以是相对路径，即不以"/"开头，相对的是 FTPClient 当前的工作路径，如 "video"、"视频" 等，会在当前工作目录进行新建目录
                // 2）可以是绝对路径，即以"/"开头，与 FTPCLient 当前工作目录无关，如 "/images"、"/images/2018"
                // 3）注意多级目录时，必须确保父目录存在，否则创建失败，
                // 如 "video/201808"、"/images/2018" ，如果 父目录 video与images不存在，则创建失败
                ftpClient.makeDirectory(file.getName());
                // 变更 FTPClient 工作目录到新目录
                // 1)不以"/"开头表示相对路径，新目录以当前工作目录为基准，即当前工作目录下不存在此新目录时，变更失败
                // 2)参数必须是目录，当是文件时改变路径无效*/
                ftpClient.changeWorkingDirectory(file.getName());

                File[] listFiles = file.listFiles();
                if (ArrayUtils.isEmpty(listFiles)) {
                    return true;
                }
                for (File listFile : listFiles) {
                    if (listFile.isDirectory()) {
                        // 如果有子目录，则迭代调用方法进行上传
                        uploadFileToServer(ftpClient, listFile);
                        // changeToParentDirectory：将 FTPClient 工作目录移到上一层
                        // 这一步细节很关键，子目录上传完成后，必须将工作目录返回上一层，否则容易导致文件上传后，目录不一致
                        ftpClient.changeToParentDirectory();
                    } else {
                        // 如果目录中全是文件，则直接上传*/
                        FileInputStream input = new FileInputStream(listFile);
                        ftpClient.storeFile(listFile.getName(), input);
                        input.close();
                        log.info("文件上传成功：{}", listFile.getPath());
                    }
                }
            } else {
                // 如果被上传的是文件时
                // storeFile:将本地文件上传到服务器
                // 1）如果服务器已经存在此文件，则不会重新覆盖,即不会再重新上传
                // 2）如果当前连接FTP服务器的用户没有写入的权限，则不会上传成功，但是也不会报错抛异常
                ftpClient.storeFile(file.getName(), FileUtils.openInputStream(file));
                log.info("文件上传成功：{}", file.getPath());
            }
            return true;
        } catch (IOException e) {
            log.error("上传文件出现错误：{}", e.getMessage());
			return false;
        }
    }


    /**
     * 上传本地文件 或 目录 至 FTP 服务器----保持 FTP 服务器与本地 文件目录结构一致
     *
     * @param ftpClient 连接成功有效的 FTPClient
     * @param file 待上传的文件 或 文件夹(此时会遍历逐个上传)
     * @return bool
     */
    public static boolean uploadFileToServer(FTPClient ftpClient, String basePath, String uploadPath, File file) {
        // 如果 FTP 连接已经关闭，或者连接无效，则直接返回
        if (!ftpClient.isConnected() || !ftpClient.isAvailable()) {
            log.error("FTP服务器连接已经关闭或者连接无效*****放弃文件上传");
            return false;
        }
        if (Objects.isNull(file) || !file.exists()) {
            log.error("待上传文件为空或者文件不存在*****放弃文件上传");
            return true;
        }
        try {
        	if (!StringUtils.isEmpty(uploadPath)) {
				ftpClient.makeDirectory(basePath);
				ftpClient.makeDirectory(uploadPath);
			}
            if (file.isDirectory()) {
                // 如果被上传的是目录时
                // makeDirectory：在 FTP 上创建目录(方法执行完，服务器就会创建好目录，如果目录本身已经存在，则不会再创建)
                // 1）可以是相对路径，即不以"/"开头，相对的是 FTPClient 当前的工作路径，如 "video"、"视频" 等，会在当前工作目录进行新建目录
                // 2）可以是绝对路径，即以"/"开头，与 FTPCLient 当前工作目录无关，如 "/images"、"/images/2018"
                // 3）注意多级目录时，必须确保父目录存在，否则创建失败，
                // 如 "video/201808"、"/images/2018" ，如果 父目录 video与images不存在，则创建失败
				ftpClient.makeDirectory(file.getName());
                // 变更 FTPClient 工作目录到新目录
                // 1)不以"/"开头表示相对路径，新目录以当前工作目录为基准，即当前工作目录下不存在此新目录时，变更失败
                // 2)参数必须是目录，当是文件时改变路径无效*/
                ftpClient.changeWorkingDirectory(file.getName());

                File[] listFiles = file.listFiles();
                if (ArrayUtils.isNotEmpty(listFiles)) {
                    for (File listFile : listFiles) {
                        if (listFile.isDirectory()) {
                            // 如果有子目录，则迭代调用方法进行上传*/
                            uploadFileToServer(ftpClient, listFile);
                            // changeToParentDirectory：将 FTPClient 工作目录移到上一层
                            // 这一步细节很关键，子目录上传完成后，必须将工作目录返回上一层，否则容易导致文件上传后，目录不一致
                            ftpClient.changeToParentDirectory();
                        } else {
                            // 如果目录中全是文件，则直接上传
                            ftpClient.storeFile(listFile.getName(), FileUtils.openInputStream(listFile));
                            log.info("文件上传成功：{}", listFile.getPath());
                        }
                    }
                }
            } else {
				if(!StringUtils.isEmpty(uploadPath)){
					ftpClient.changeWorkingDirectory(uploadPath);
				}

				// 如果被上传的是文件时
                // storeFile:将本地文件上传到服务器
                // 1）如果服务器已经存在此文件，则不会重新覆盖,即不会再重新上传
                // 2）如果当前连接FTP服务器的用户没有写入的权限，则不会上传成功，但是也不会报错抛异常
                ftpClient.storeFile(file.getName(), FileUtils.openInputStream(file));
                log.info("文件上传成功：{}", file.getPath());
            }
            return true;
        } catch (IOException e) {
            log.error("上传文件出现错误：{}", e.getMessage());
            return false;
        }
    }

    /**
     * 删除服务器的文件
     *
     * @param ftpClient 连接成功且有效的 FTP客户端
     * @param file 待删除的文件或者目录，为目录时，会逐个删除， 路径必须是绝对路径，如 "/1.png"、"/video/3.mp4"、"/images/2018" "/" 表示用户根目录,则删除所有内容
     */
    public static void deleteServerFile(FTPClient ftpClient, String file) {
        // 如果 FTP 连接已经关闭，或者连接无效，则直接返回
        if (!ftpClient.isConnected() || !ftpClient.isAvailable()) {
            log.error("FTP服务器连接已经关闭或者连接无效*****放弃文件上传****");
            return;
        }
        try {
            // 尝试改变当前工作目录到 file
            // 1）changeWorkingDirectory：变更FTPClient当前工作目录，变更成功返回true，否则失败返回false
            // 2）如果变更工作目录成功，则表示 file 为服务器已经存在的目录
            // 3）否则变更失败，则认为 file 是文件，是文件时则直接删除
            boolean changeFlag = ftpClient.changeWorkingDirectory(file);
            if (changeFlag) {
                // 当被删除的是目录时
                FTPFile[] ftpFiles = ftpClient.listFiles();
                for (FTPFile ftpFile : ftpFiles) {
                    log.info("当前目录：{}", ftpClient.printWorkingDirectory());
                    if (ftpFile.isFile()) {
                        boolean deleteFlag = ftpClient.deleteFile(ftpFile.getName());
                        if (deleteFlag) {
                            log.info("删除服务器文件成功：{}", ftpFile.getName());
                        } else {
                            log.info("删除服务器文件失败：{}", ftpFile.getName());
                        }
                    } else {
                        // printWorkingDirectory：获取 FTPClient 客户端当前工作目录
                        // 然后开始迭代删除子目录
                        deleteServerFile(ftpClient, ftpClient.printWorkingDirectory() + "/" + ftpFile.getName());
                    }
                }
                // printWorkingDirectory：获取 FTPClient 客户端当前工作目录
                // removeDirectory：删除FTP服务端的空目录，注意如果目录下存在子文件或者子目录，则删除失败
                // 运行到这里表示目录下的内容已经删除完毕，此时再删除当前的为空的目录，同时将工作目录移动到上移层级
                ftpClient.removeDirectory(ftpClient.printWorkingDirectory());
                ftpClient.changeToParentDirectory();
            } else {
                // file：删除FTP服务器上的文件
                // 1）只用于删除文件而不是目录，删除成功时，返回 true
                // 2）删除目录时无效,方法返回 false
                // 3）待删除文件不存在时，删除失败，返回 false
                boolean deleteFlag = ftpClient.deleteFile(file);
                if (deleteFlag) {
                    log.info("删除服务器文件成功：{}", file);
                } else {
                    log.info("删除服务器文件失败：{}", file);
                }
            }
        } catch (IOException e) {
            log.error("删除文件失败：{}", e.getMessage());
        }
    }

    /**
     * 使用完毕，应该及时关闭连接 终止 ftp 传输 断开 ftp 连接
     * @param ftpClient FTP连接客户端
     */
    public static FTPClient closeFTPConnect(FTPClient ftpClient) {
        try {
            if (Objects.nonNull(ftpClient) && ftpClient.isConnected()) {
                ftpClient.abort();
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            log.error("关闭FTP服务器出现错误：{}", e.getMessage());
        }
        return ftpClient;
    }
}
