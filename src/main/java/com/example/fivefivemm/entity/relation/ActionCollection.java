package com.example.fivefivemm.entity.relation;


import com.example.fivefivemm.entity.action.Action;
import com.example.fivefivemm.entity.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 动态收藏表,保存用户与动态之间的收藏关系
 *
 * @author tiga
 * @version 1.0
 * @since 2019年5月27日11:16:12
 */
@Entity
@Table(name = "action_collection")
public class ActionCollection implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer actionCollectionId;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createTime;

    //收藏用户信息懒加载
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "collectorId", referencedColumnName = "userId")
    private User collector;

    //收藏博客信息急加载
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Action.class)
    @JoinColumn(name = "actionId", referencedColumnName = "actionId")
    private Action collectAction;

    public ActionCollection() {
    }

    public ActionCollection(User collector, Action collectAction) {
        this.collector = collector;
        this.collectAction = collectAction;
    }

    public Integer getActionCollectionId() {
        return actionCollectionId;
    }

    public void setActionCollectionId(Integer actionCollectionId) {
        this.actionCollectionId = actionCollectionId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public void setCollector(User collector) {
        this.collector = collector;
    }

    public Action getCollectAction() {
        return collectAction;
    }

    public void setCollectAction(Action collectAction) {
        this.collectAction = collectAction;
    }

    @Override
    public String toString() {
        return "[动态收藏关系: Id:" + actionCollectionId + ", 收藏用户Id:" + collector.getUserId() + ", 收藏动态Id:" + collectAction.getActionId() + "]";
    }
}
