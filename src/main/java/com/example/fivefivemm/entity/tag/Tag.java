package com.example.fivefivemm.entity.tag;

import com.example.fivefivemm.entity.relation.ActionTag;

import javax.persistence.*;
import java.util.Set;

/**
 * 标签表
 *
 * @author tiga
 * @version 2019年6月29日12:35:35
 */
@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tagId;

    @Column(nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = ActionTag.class, mappedBy = "tag", cascade = CascadeType.REMOVE)
    private Set<ActionTag> actionTags;

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tag() {
    }

    public Tag(Integer tagId){
        this.tagId = tagId;
    }

    public Tag(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "[标签:Id:" + tagId + ",名称:" + name + "]";
    }
}
