package com.example.fivefivemm.entity.relation;

import com.example.fivefivemm.entity.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 用户关注表,保存用户之间的关注信息
 *
 * @author tiga
 * @version 1.0
 * @since 2019年5月27日11:18:18
 */
@Entity
@Table(name = "user_collection")
public class UserCollection implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userCollectionId;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createTime;

    //关注用户信息急加载
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    @JoinColumn(name = "focusId", referencedColumnName = "userId")
    private User focus;

    //粉丝信息急加载
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    @JoinColumn(name = "fansId", referencedColumnName = "userId")
    private User fans;

    public UserCollection() {
    }

    public UserCollection(User focus, User fans) {
        this.focus = focus;
        this.fans = fans;
    }

    public Integer getUserCollectionId() {
        return userCollectionId;
    }

    public void setUserCollectionId(Integer userCollectionId) {
        this.userCollectionId = userCollectionId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public User getFocus() {
        return focus;
    }

    public void setFocus(User focus) {
        this.focus = focus;
    }

    public User getFans() {
        return fans;
    }

    public void setFans(User fans) {
        this.fans = fans;
    }

    @Override
    public String toString() {
        return "[用户关注表: Id:" + userCollectionId + ", 关注用户Id:" + focus.getUserId() + ", 粉丝用户Id:" + fans.getUserId() + "]";
    }
}
