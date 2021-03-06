package com.example.fivefivemm.utility;

import com.example.fivefivemm.entity.action.Action;
import com.example.fivefivemm.entity.message.Message;
import com.example.fivefivemm.entity.relation.ActionCollection;
import com.example.fivefivemm.entity.relation.UserCollection;
import com.example.fivefivemm.entity.user.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 *
 * @author tiga
 * @version 1.0
 * @since 2019年5月14日17:13:28
 */
public class Utility {

    /**
     * md5加密算法
     *
     * @param password 密码
     * @return 加密后的密码
     */
    public static String md5(String password) {
        if (password != null && password.length() != 0) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                //需要加密的信息
                byte[] input = password.getBytes();
                //加密过的信息
                byte[] output = messageDigest.digest(input);
                //将md5处理后的output结果转出字符串
                //利用Base64算法转出字符串
                return Base64.encodeBase64String(output);
            } catch (NoSuchAlgorithmException e) {
                System.out.println("加密失败");
                return "";
            }
        } else {
            return "";
        }
    }

    /**
     * 生成10位随机密码
     *
     * @return 生成的随机密码
     */
    public static String randomPassword() {
        String val = "";
        Random random = new Random();
        //length为几位密码
        for (int i = 0; i < 10; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    /**
     * 生成6位数验证码
     *
     * @return 验证码
     */
    public static String VerificationCode() {
        String val = "";
        Random random = new Random();
        //length为几位验证码
        for (int i = 0; i < 6; i++) {
            val += String.valueOf(random.nextInt(10));
        }
        return val;
    }

    /**
     * 业务Json构造
     */
    public static String ResultBody(int status, String message, Object data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", status);
        if (message != null) {
            jsonObject.put("message", message);
        }
        if (data != null) {
            jsonObject.put("data", data);
        }
        return jsonObject.toString();
    }

    /**
     * 单个动态信息Json构造
     */
    public static Map<String, Object> ActionBody(Action action) {
        Map<String, Object> actionMap = new HashMap<>();
        Map<String, Object> authorMap = new HashMap<>();
        JSONArray imageArray = new JSONArray();
        if (action.getActionId() != null) {
            actionMap.put("actionId", action.getActionId());
        } else {
            actionMap.put("actionId", "");
        }

        if (action.getTitle() != null) {
            actionMap.put("title", action.getTitle());
        } else {
            actionMap.put("title", "");
        }

        if (action.getCost() != null) {
            actionMap.put("cost", action.getCost());
        } else {
            actionMap.put("cost", "");
        }

        if (action.getContent() != null) {
            actionMap.put("content", action.getContent());
        } else {
            actionMap.put("content", "");
        }

        if (action.getAddress() != null) {
            actionMap.put("address", action.getAddress());
        } else {
            actionMap.put("address", "");
        }

        if (action.getCreateTime() != null) {
            actionMap.put("time", action.getCreateTime().toString());
        } else {
            actionMap.put("time", getNowDate());
        }

        //获取内容中的图片
        String images[] = getImageAddress(action.getContent());
        if (getImageAddress(action.getContent()).length != 0) {
            for (String img : images) {
                imageArray.add(Setting.imageAddress() + img);
            }
            actionMap.put("images", imageArray);
        }

        if (action.getAuthor().getName() != null) {
            authorMap.put("name", action.getAuthor().getName());
        } else {
            authorMap.put("name", "");
        }

        if (action.getAuthor().getUserId() != null) {
            authorMap.put("userId", action.getAuthor().getUserId());
        } else {
            authorMap.put("userId", "");
        }

        if (action.getAuthor().getAvatar() != null) {
            authorMap.put("avatar", action.getAuthor().getAvatar());
        } else {
            authorMap.put("avatar", "");
        }

        if (action.getAuthor().getType() != null) {
            authorMap.put("type", action.getAuthor().getType());
        } else {
            authorMap.put("type", "");
        }

        if (action.getAuthor().getSex() != null) {
            authorMap.put("sex", action.getAuthor().getSex());
        } else {
            authorMap.put("sex", "");
        }
        actionMap.put("author", authorMap);
        return actionMap;
    }

    /**
     * 多个动态信息Json构造
     */
    public static JSONArray ActionListBody(List<Action> actionList) {
        if (actionList != null && actionList.size() > 0) {
            JSONArray actionArray = new JSONArray();
            for (Action action : actionList) {
                actionArray.add(ActionBody(action));
            }
            return actionArray;
        } else {
            return new JSONArray();
        }
    }

    /**
     * 单个用户信息Json构造(完整 type=1 部分 type=2)
     */
    public static Map<String, Object> userBody(User user, Integer type) {
        Map<String, Object> userMap = new HashMap<>();
        //基础信息
        if (user.getUserId() != null) {
            userMap.put("userId", user.getUserId());
        } else {
            userMap.put("userId", "");
        }

        if (user.getName() != null) {
            userMap.put("name", user.getName());
        } else {
            userMap.put("name", "");
        }

        if (user.getSex() != null) {
            userMap.put("sex", user.getSex());
        } else {
            userMap.put("sex", "");
        }

        if (user.getAge() != null) {
            userMap.put("age", user.getAge());
        } else {
            userMap.put("age", "");
        }

        if (user.getIntroduction() != null) {
            userMap.put("introduction", user.getIntroduction());
        } else {
            userMap.put("introduction", "");
        }

        if (user.getAvatar() != null) {
            userMap.put("avatar", user.getAvatar());
        } else {
            userMap.put("avatar", "");
        }

        if (user.getType() != null) {
            userMap.put("type", user.getType());
        } else {
            userMap.put("type", "");
        }

        if (user.getBirthday() != null) {
            userMap.put("birthday", user.getBirthday().toString().substring(0, 10));
        } else {
            userMap.put("birthday", "");
        }

        //隐私信息
        if (type == 1) {
            if (user.getEmail() != null) {
                userMap.put("email", user.getEmail());
            } else {
                userMap.put("email", "");
            }

            if (user.getPhone() != null) {
                userMap.put("phone", user.getPhone());
            } else {
                userMap.put("phone", "");
            }

            if (user.getQq() != null) {
                userMap.put("qq", user.getQq());
            } else {
                userMap.put("qq", "");
            }

            if (user.getWeChat() != null) {
                userMap.put("weChat", user.getWeChat());
            } else {
                userMap.put("weChat", "");
            }
        }

        return userMap;
    }

    /**
     * 多个用户信息构造
     *
     * @param userList 用户列表
     * @return JSONArray
     */
    public static JSONArray userListBody(List<User> userList) {
        if (userList != null && userList.size() > 0) {
            JSONArray actionArray = new JSONArray();
            for (User user : userList) {
                actionArray.add(userBody(user, 2));
            }
            return actionArray;
        } else {
            return new JSONArray();
        }
    }

    /**
     * 后台用户信息管理
     */
    public static JSONArray adminUserListBody(List<User> userList) {
        if (userList != null && userList.size() > 0) {
            JSONArray actionArray = new JSONArray();
            for (User user : userList) {
                actionArray.add(adminUserBody(user));
            }
            return actionArray;
        } else {
            return new JSONArray();
        }
    }

    public static Map<String, Object> adminUserBody(User user) {
        Map<String, Object> userMap = new HashMap<>();
        //基础信息
        if (user.getUserId() != null) {
            userMap.put("userId", user.getUserId());
        } else {
            userMap.put("userId", "");
        }

        if (user.getAccount() != null) {
            userMap.put("account", user.getAccount());
        } else {
            userMap.put("account", "");
        }

        if (user.getRegisterTime() != null) {
            userMap.put("registerTime", user.getRegisterTime().toString());
        } else {
            userMap.put("registerTime", "");
        }

        if (user.getLastLoginTime() != null) {
            userMap.put("lastLoginTime", user.getLastLoginTime().toString());
        } else {
            userMap.put("lastLoginTime", "");
        }

        if (user.getName() != null) {
            userMap.put("name", user.getName());
        } else {
            userMap.put("name", "");
        }

        if (user.getSex() != null) {
            userMap.put("sex", user.getSex());
        } else {
            userMap.put("sex", "");
        }

        if (user.getType() != null) {
            userMap.put("type", user.getType());
        } else {
            userMap.put("type", "");
        }

        if (user.getBirthday() != null) {
            userMap.put("birthday", user.getBirthday().toString().substring(0, 10));
        } else {
            userMap.put("birthday", "");
        }

        if (user.getEmail() != null) {
            userMap.put("email", user.getEmail());
        } else {
            userMap.put("email", "");
        }

        if (user.getPhone() != null) {
            userMap.put("phone", user.getPhone());
        } else {
            userMap.put("phone", "");
        }

        if (user.getQq() != null) {
            userMap.put("qq", user.getQq());
        } else {
            userMap.put("qq", "");
        }

        if (user.getWeChat() != null) {
            userMap.put("weChat", user.getWeChat());
        } else {
            userMap.put("weChat", "");
        }

        return userMap;
    }

    /**
     * 头像地址构造
     */
    private static String avatarAddress(String fileName) {
        return Setting.serverAddress() + "avatar/" + fileName;
    }

    /**
     * 动态图片地址构造
     */
    private static String ActionAddress(String fileName) {
        return Setting.serverAddress() + "action/" + fileName;
    }

    /**
     * 根据生日计算年龄
     *
     * @param birthday 生日
     * @return 年龄
     */
    public static int getAgeByBirth(Date birthday) {
        int age;
        try {
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());// 当前时间
            Calendar birth = Calendar.getInstance();
            birth.setTime(birthday);
            if (birth.after(now)) {//如果传入的时间，在当前时间的后面，返回0岁
                age = 0;
            } else {
                age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
                if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
                    age += 1;
                }
            }
            return age;
        } catch (Exception e) {//兼容性更强,异常后返回数据
            return 0;
        }
    }

    /**
     * 获取现在时间
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getNowDate() {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ft.format(dNow);
    }

    /**
     * 保存图片
     *
     * @param image 头像图片
     * @param type  图片类型 1.用户头像 2.动态图片
     * @return 头像图片地址
     */
    public static String saveImage(MultipartFile image, Integer type) {
        if (image == null) {
            return null;
        }
        String fileName = UUID.randomUUID() + image.getOriginalFilename();
        try {
            if (type == 1) {
                image.transferTo(new File(Setting.AVATAR_PATH + fileName));
                return avatarAddress(fileName);
            } else if (type == 2) {
                image.transferTo(new File(Setting.ACTION_IMAGE_PATH + fileName));
                return ActionAddress(fileName);
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param s
     * @return 获得图片
     */
    public static List<String> getImg(String s) {
        String regex;
        List<String> list = new ArrayList<>();
        regex = "src=\"(.*?)\"";
        Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        Matcher ma = pa.matcher(s);
        while (ma.find()) {
            list.add(ma.group());
        }
        return list;
    }

    /**
     * 返回存有图片地址的数组
     *
     * @param tar
     * @return
     */
    public static String[] getImageAddress(String tar) {
        List<String> imgList = getImg(tar);

        String res[] = new String[imgList.size()];

        if (imgList.size() > 0) {
            for (int i = 0; i < imgList.size(); i++) {
                int begin = imgList.get(i).indexOf("\"") + 1;
                int end = imgList.get(i).lastIndexOf("\"");
                String url[] = imgList.get(i).substring(begin, end).split("/");
                res[i] = url[url.length - 1];
            }
        } else {
        }
        return res;
    }

    /**
     * 消息Json构造
     *
     * @param message 消息
     * @param type    1:消息接收者 2.消息发起者
     * @return Map
     */
    public static Map<String, Object> MessageBody(Message message, Integer type) {
        Map<String, Object> messageMap = new HashMap<>();
        Map<String, Object> actionMap = new HashMap<>();

        messageMap.put("messageId", message.getMessageId());
        messageMap.put("time", message.getCreateTime().toString());
        messageMap.put("isRead", message.isRead());
        messageMap.put("isAccept", message.isAccept());

        actionMap.put("actionId", message.getAction().getActionId());
        actionMap.put("title", message.getAction().getTitle());

        if (type == 1) {
            Map<String, Object> watcherMap = new HashMap<>();
            watcherMap.put("userId", message.getUser().getUserId());
            watcherMap.put("name", message.getUser().getName());
            watcherMap.put("avatar", message.getUser().getAvatar());
            watcherMap.put("sex", message.getUser().getSex());
            watcherMap.put("type", message.getUser().getType());
            messageMap.put("watcher", watcherMap);
        } else {
            Map<String, Object> actionAuthorMap = new HashMap<>();
            actionAuthorMap.put("userId", message.getAction().getAuthor().getUserId());
            actionAuthorMap.put("name", message.getAction().getAuthor().getName());
            actionAuthorMap.put("avatar", message.getAction().getAuthor().getAvatar());
            actionAuthorMap.put("sex", message.getAction().getAuthor().getSex());
            actionAuthorMap.put("type", message.getAction().getAuthor().getType());
            actionMap.put("author", actionAuthorMap);
        }

        messageMap.put("action", actionMap);

        return messageMap;
    }

    /**
     * 多个消息json构造
     */
    public static JSONArray MessageListBody(List<Message> messageList, Integer type) {
        if (messageList != null && messageList.size() > 0) {
            JSONArray messageArray = new JSONArray();
            for (Message message : messageList) {
                messageArray.add(MessageBody(message, type));
            }
            return messageArray;
        } else {
            return new JSONArray();
        }
    }

    /**
     * 获取未读消息条数
     *
     * @param messageList 消息集合
     * @return 未读消息条数
     */
    public static Integer getNotReadMessage(List<Message> messageList) {
        if (messageList != null && messageList.size() > 0) {
            int num = 0;
            for (Message message : messageList) {
                if (!message.isRead()) {
                    num++;
                }
            }
            return num;
        } else {
            return 0;
        }
    }

    /**
     * 用户收藏分页对象转换
     *
     * @param userPage
     * @return
     */
    public static Map<String, Object> PageUserMap(Page<UserCollection> userPage) {
        if (userPage == null) {
            return null;
        }
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("totalPages", userPage.getTotalPages());
//            map.put("totalElements", actionPage.getTotalElements());
        userMap.put("currentPage", (userPage.getNumber() + 1));
        List<User> userList = new ArrayList<>();
        for (UserCollection userCollection : userPage) {
            userList.add(userCollection.getFans());
        }
        userMap.put("users", Utility.userListBody(userList));
        return userMap;
    }

    /**
     * 动态分页对象转换
     *
     * @param actionPage
     * @return
     */
    public static Map<String, Object> PageActionMap(Page<Action> actionPage) {
        if (actionPage == null) {
            return null;
        }
        Map<String, Object> actionMap = new HashMap<>();
        actionMap.put("totalPages", actionPage.getTotalPages());
        actionMap.put("totalElements", actionPage.getTotalElements());
        actionMap.put("currentPage", (actionPage.getNumber() + 1));
        actionMap.put("elementsForCurrentPage", actionPage.getNumberOfElements());
        actionMap.put("actions", Utility.ActionListBody(actionPage.getContent()));
        return actionMap;
    }

    /**
     * 动态收藏分页对象转换
     *
     * @param actionCollectionsPage
     * @return
     */
    public static Map<String, Object> PageActionCollectionMap(Page<ActionCollection> actionCollectionsPage) {
        if (actionCollectionsPage == null) {
            return null;
        }
        Map<String, Object> actionMap = new HashMap<>();
        actionMap.put("totalPages", actionCollectionsPage.getTotalPages());
        actionMap.put("totalElements", actionCollectionsPage.getTotalElements());
        actionMap.put("currentPage", (actionCollectionsPage.getNumber() + 1));
        actionMap.put("elementsForCurrentPage", actionCollectionsPage.getNumberOfElements());
        List<Action> actionList = new ArrayList<>();
        for (ActionCollection actionCollection : actionCollectionsPage) {
            actionList.add(actionCollection.getCollectAction());
        }
        actionMap.put("actions", Utility.ActionListBody(actionList));
        return actionMap;
    }

    public static Map<String, Object> PageMessageMap(Page<Message> messagePage, Integer type) {
        if (messagePage == null) {
            return null;
        }
        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("totalPages", messagePage.getTotalPages());
        messageMap.put("totalElements", messagePage.getTotalElements());
        messageMap.put("currentPage", (messagePage.getNumber() + 1));
        messageMap.put("elementsForCurrentPage", messagePage.getNumberOfElements());
        messageMap.put("messages", Utility.MessageListBody(messagePage.getContent(), type));
        return messageMap;
    }
}