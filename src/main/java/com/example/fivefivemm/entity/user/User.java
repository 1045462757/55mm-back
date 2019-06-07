package com.example.fivefivemm.entity.user;

import com.example.fivefivemm.entity.action.Action;
import com.example.fivefivemm.entity.relation.ActionCollection;
import com.example.fivefivemm.entity.relation.UserCollection;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

/**
 * 用户表
 *
 * @author tiga
 * @version 1.0
 * @since 2019年5月10日19:41:59
 */
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(nullable = false)
    private String account;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp registerTime;

    private Date lastLoginTime;

    private String name;

    private String sex;

    @Column(columnDefinition = "date")
    private Date birthday;

    private Integer age;

    private String type;

    @Column(nullable = false)
    private String email;

    private String phone;

    private String qq;

    private String weChat;

    private String avatar;

    private String introduction;

    //EAGER急加载,加载用户信息就加载这条属性
    //LAZY懒加载,在加载该属性时加上事务注解，已存在的属性不用加载
    @OneToMany(fetch = FetchType.LAZY, targetEntity = Action.class, mappedBy = "author")
    private Set<Action> myActions;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = ActionCollection.class, mappedBy = "collector")
    private Set<ActionCollection> actionCollections;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = UserCollection.class, mappedBy = "focus")
    private Set<UserCollection> fans;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = UserCollection.class, mappedBy = "fans")
    private Set<UserCollection> focus;

    public User() {

    }

    public User(Integer userId) {
        this.userId = userId;
    }

    public User(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public User(Integer userId, String avatar) {
        this.userId = userId;
        this.avatar = avatar;
    }

    public User(String account, String password, String email) {
        this.account = account;
        this.password = password;
        this.email = email;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Timestamp registerTime) {
        this.registerTime = registerTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Set<Action> getMyActions() {
        return myActions;
    }

    public void setMyActions(Set<Action> myActions) {
        this.myActions = myActions;
    }

    public Set<ActionCollection> getActionCollections() {
        return actionCollections;
    }

    public void setActionCollections(Set<ActionCollection> actionCollections) {
        this.actionCollections = actionCollections;
    }

    public Set<UserCollection> getFans() {
        return fans;
    }

    public void setFans(Set<UserCollection> fans) {
        this.fans = fans;
    }

    public Set<UserCollection> getFocus() {
        return focus;
    }

    public void setFocus(Set<UserCollection> focus) {
        this.focus = focus;
    }

    @Override
    public String toString() {
        return "[用户信息:用户Id:" + userId + ",账号:" + account + ",姓名:" + name + "]";
    }
}
