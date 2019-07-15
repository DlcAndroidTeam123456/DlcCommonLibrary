package cn.dlc.commonlibrary.utils;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

/**
 * 页面:刘华乾  on  2018/5/28.
 * 对接口:
 * 作用:向SD卡读写数据
 */

public class FileUtil {
    /**
     *  向SD卡写入数据  
     * @param context
     * @param content 写入文件的内容
     * @param path 文件夹路径
     * @param fileName 文件名
     */
    public static void writeSDcard(Context context, String content, String path, String fileName) {
        try {
           
            // 判断是否存在SD卡  
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                // 获取SD卡的目录  
                //File sdDire = Environment.getExternalStorageDirectory();
                File file = new File(path);
                if (!file.exists()) {
                    //按照指定的路径创建文件夹  
                    file.mkdirs();
                }
                File dir = new File(path +"/"+ fileName);
                if (!dir.exists()) {
                    try {
                        //在指定的文件夹中创建文件  
                        dir.createNewFile();
                    } catch (Exception e) {
                    }
                }
                FileOutputStream outFileStream = new FileOutputStream(path + "/"+fileName);
                outFileStream.write(content.getBytes());
                outFileStream.close();
                ToastUtil.show(context, "数据保存到" + path + "文件了");
            }
            else
                ToastUtil.show(context, "没有SD卡");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 从SD卡中读取数据  

    /**
     * 
     * @param context
     * @param path 文件路径
     * @return
     */
    public static String readSDcard(Context context, String path) {
        StringBuffer strsBuffer = new StringBuffer();
        try {
            // 判断是否存在SD  
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File file =
                    new File(path);
                // 判断是否存在该文件  
                if (file.exists()) {
                    // 打开文件输入流  
                    FileInputStream fileR = new FileInputStream(file);
                    BufferedReader reads = new BufferedReader(new InputStreamReader(fileR));
                    String st = null;
                    while ((st = reads.readLine()) != null) {
                        strsBuffer.append(st);
                    }
                    fileR.close();
                } else {
                    ToastUtil.show(context, "该目录下文件不存在");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strsBuffer.toString();
    }

    /**
     * 此方法为android程序写入sd文件文件，用到了android-annotation的支持库@
     *
     * @param buffer 写入文件的内容
     * @param folder 保存文件的文件夹名称,如log；可为null，默认保存在sd卡根目录
     * @param fileName 文件名称，默认app_log.txt
     * @param append 是否追加写入，true为追加写入，false为重写文件
     * @param autoLine 针对追加模式，true为增加时换行，false为增加时不换行
     */
    public synchronized static void writeFileToSDCard(@NonNull final byte[] buffer,
        @Nullable final String folder, @Nullable final String fileName, final boolean append,
        final boolean autoLine) {

        boolean sdCardExist =
            Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        String folderPath = "";
        if (sdCardExist) {
            //TextUtils为android自带的帮助类  
            if (TextUtils.isEmpty(folder)) {
                //如果folder为空，则直接保存在sd卡的根目录  
                folderPath = Environment.getExternalStorageDirectory() + File.separator;
            } else {
                folderPath = Environment.getExternalStorageDirectory()
                    + File.separator
                    + folder
                    + File.separator;
            }
        } else {
            return;
        }

        File fileDir = new File(folderPath);
        if (!fileDir.exists()) {
            if (!fileDir.mkdirs()) {
                return;
            }
        }
        File file;
        //判断文件名是否为空  
        if (TextUtils.isEmpty(fileName)) {
            file = new File(folderPath + "app_log.txt");
        } else {
            file = new File(folderPath + fileName);
        }
        RandomAccessFile raf = null;
        FileOutputStream out = null;
        try {
            if (append) {
                //如果为追加则在原来的基础上继续写文件  
                raf = new RandomAccessFile(file, "rw");
                raf.seek(file.length());
                raf.write(buffer);
                if (autoLine) {
                    raf.write("\n".getBytes());
                }
            } else {
                //重写文件，覆盖掉原来的数据  
                out = new FileOutputStream(file);
                out.write(buffer);
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (raf != null) {
                    raf.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
