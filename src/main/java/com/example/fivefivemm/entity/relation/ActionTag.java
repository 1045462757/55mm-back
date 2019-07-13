package com.example.fivefivemm.entity.relation;

import com.example.fivefivemm.entity.action.Action;
import com.example.fivefivemm.entity.tag.Tag;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 动态标签表，保存动态的标签
 *
 * @author tiga
 * @version 1.0
 * @since 2019年6月29日12:55:18
 */
@Entity
@Table(name = "action_tag")
public class ActionTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer actionTagId;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createTime;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Action.class)
    @JoinColumn(name = "actionId", referencedColumnName = "actionId")
    private Action action;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Tag.class)
    @JoinColumn(name = "tagId", referencedColumnName = "tagId")
    private Tag tag;

    public ActionTag(){}

    public ActionTag(Action action,Tag tag){
        this.action = action;
        this.tag = tag;
    }

    public Integer getActionTagId() {
        return actionTagId;
    }

    public void setActionTagId(Integer actionTagId) {
        this.actionTagId = actionTagId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "[动态标签关系: Id:" + actionTagId + ", 动态Id:" + action.getActionId() + ", 标签Id:" + tag.getTagId() + "]";
    }
}
