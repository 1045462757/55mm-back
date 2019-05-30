package com.example.fivefivemm.entity.action;

import com.example.fivefivemm.entity.message.Message;
import com.example.fivefivemm.entity.relation.ActionWatch;
import com.example.fivefivemm.entity.user.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

/**
 * 动态表
 *
 * @author tiga
 * @version 1.0
 * @since 2019年5月10日19:44:26
 */
@Entity
@Table(name = "action")
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer actionId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "text", nullable = false)
    private String content;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createTime;

    //作者Id从数据库中查找,其余信息懒加载
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    @JoinColumn(name = "authorId", referencedColumnName = "userId")
    private User author;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Integer cost;

    private Integer collect;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = ActionWatch.class, mappedBy = "action", cascade = CascadeType.REMOVE)
    private Set<ActionWatch> actionWatches;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Message.class, mappedBy = "action", cascade = CascadeType.REMOVE)
    private Set<Message> messages;

    public Action() {

    }

    public Action(Integer actionId) {
        this.actionId = actionId;
        this.collect = 0;
    }

    public Action(User author, Integer actionId) {
        this.author = author;
        this.actionId = actionId;
        this.collect = 0;
    }

    public Action(User author, String title, String address, Integer cost, String content) {
        this.author = author;
        this.title = title;
        this.address = address;
        this.cost = cost;
        this.content = content;
        this.collect = 0;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Set<ActionWatch> getActionWatches() {
        return actionWatches;
    }

    public void setActionWatches(Set<ActionWatch> actionWatches) {
        this.actionWatches = actionWatches;
    }

    public Integer getCollect() {
        return collect;
    }

    public void setCollect(Integer collect) {
        this.collect = collect;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "[Action:actionId:" + actionId + ",title:" + title + ",createTime:" + createTime + ",address:" + address + ",cost:" + cost + ",content:" + content + ",authorId:" + author.getUserId() +
                "]";
    }
}
