package com.rb.core.picturechoose;

import android.app.Activity;
import android.content.Intent;

import java.io.File;

/**
 * @author zhaod
 * @date 2018/12/10
 */

public class PicturePickUtil {

    public static OnPicturePathListener listener;

    static final int TAKE_PHOTO = 1;
    static final int CROP_PHOTO = 2;
    static final int PICK_PHOTO = 3;

    static Integer aspectX, aspectY;

    static int imgWidth = 500, imgHeight = 500, fileSize = 500;

    static boolean creatNewFile = true;

    static int choosePicWay = 0;

    public static void pickByCamera(Activity activity, OnPicturePathListener listener) {
        PicturePickUtil.listener = listener;
        activity.startActivity(new Intent(activity, PictureActivity.class));
        choosePicWay = 0;
    }

    public static void pickByAlbum(Activity activity, OnPicturePathListener listener) {
        PicturePickUtil.listener = listener;
        activity.startActivity(new Intent(activity, PictureActivity.class));
        choosePicWay = 1;
    }

    /**
     * 初始化 authority
     *
     * @param authority
     */
    public static void init(String authority) {
        PhotoAlbum.init(authority);
    }

    /**
     * 图片压缩的大小
     *
     * @param width
     * @param height
     * @param size
     */
    public static void setPictureSize(int width, int height, int size) {
        imgWidth = width;
        imgHeight = height;
        fileSize = size;
    }


    /**
     * 设置裁剪图像比例
     *
     * @param aspectX
     * @param aspectY
     */
    public static void setPictureScale(Integer aspectX, Integer aspectY) {
        PicturePickUtil.aspectX = aspectX;
        PicturePickUtil.aspectY = aspectY;
    }


    static void pickResult(File file) {
        if (listener != null) {
            listener.getPicture(file);
        }
    }

    public static void setCreatNewFile(boolean creatNewFile) {
        PicturePickUtil.creatNewFile = creatNewFile;
    }

    public static void initPicture(boolean creatNewFile, Integer aspectX, Integer aspectY) {
        PicturePickUtil.init("com.star.core.fileProvider");
        PicturePickUtil.setPictureSize(500, 500, 500);
        //裁剪比例
        PicturePickUtil.setPictureScale(aspectX, aspectY);
        //不需要裁剪为false
        PicturePickUtil.setCreatNewFile(creatNewFile);
    }

}
