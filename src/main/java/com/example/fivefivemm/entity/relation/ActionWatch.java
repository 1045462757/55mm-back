package com.example.fivefivemm.entity.relation;

import com.example.fivefivemm.entity.action.Action;
import com.example.fivefivemm.entity.user.User;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 约拍记录表
 *
 * @author tiga
 * @version 1.0
 * @since 2019年5月20日18:30:03
 */
@Entity
@Table(name = "action_watch")
public class ActionWatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer actionWatchId;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createTime;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "watcherId", referencedColumnName = "userId")
    private User watcher;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Action.class)
    @JoinColumn(name = "actionId", referencedColumnName = "actionId")
    private Action action;

    public ActionWatch() {
    }

    public ActionWatch(User watcher, Action action) {
        this.watcher = watcher;
        this.action = action;
    }

    public Integer getActionWatchId() {
        return actionWatchId;
    }

    public void setActionWatchId(Integer actionWatchId) {
        this.actionWatchId = actionWatchId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public User getWatcher() {
        return watcher;
    }

    public void setWatcher(User watcher) {
        this.watcher = watcher;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "[约拍记录: 动态Id:" + action.getActionId() + ",关注者Id:" + watcher.getUserId() + "]";
    }
}
