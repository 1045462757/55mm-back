package com.example.fivefivemm.utility;

public class Setting {

    /**
     * 1为生产环境，0为本地测试
     */
    private static Integer MODEL = 0;

    /**
     * 头像图片保存地址
     */
    public static String AVATAR_PATH = "F:/55mm/avatar/";

    /**
     * 约拍信息图片保存地址
     */
    public static String ACTION_IMAGE_PATH = "F:/55mm/action/";

    /**
     * 本地图片服务器
     */
    private static String LOCAL_SERVER_ADDRESS = "http://127.0.0.1:8888/";

    /**
     * 本地默认头像
     */
    private static String LOCAL_DEFAULT_AVATAR = "http://127.0.0.1:8888/avatar/default-avatar.png";

    /**
     * 本地图片访问地址
     */
    private static String LOCAL_IMAGE_ADDRESS = "http://127.0.0.1:8888/action/";

    /**
     * 生产环境图片服务器
     */
    private static String ONLINE_SERVER_ADDRESS = "https://hylovecode.cn/";

    /**
     * 生产环境默认头像
     */
    private static String ONLINE_DEFAULT_AVATAR = "https://hylovecode.cn/avatar/default-avatar.png";

    /**
     * 生产环境图片存储地址
     */
    private static String ONLINE_IMAGE_ADDRESS = "https://hylovecode.cn/action/";

    public static String serverAddress() {
        if (MODEL == 1) {
            return ONLINE_SERVER_ADDRESS;
        } else {
            return LOCAL_SERVER_ADDRESS;
        }
    }

    public static String defaultAvatar() {
        if (MODEL == 1) {
            return ONLINE_DEFAULT_AVATAR;
        } else {
            return LOCAL_DEFAULT_AVATAR;
        }
    }

    public static String imageAddress() {
        if (MODEL == 1) {
            return ONLINE_IMAGE_ADDRESS;
        } else {
            return LOCAL_IMAGE_ADDRESS;
        }
    }
}
