package com.example.fivefivemm.repository;

import com.example.fivefivemm.entity.action.Action;
import com.example.fivefivemm.entity.relation.ActionTag;
import com.example.fivefivemm.entity.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 动态标签绑定仓库
 *
 * @author tiga
 * @version 1.0
 * @since 2019年7月12日11:20:34
 */
@Repository
public interface ActionTagRepository extends JpaRepository<ActionTag, Integer>, JpaSpecificationExecutor<ActionTag> {

    /**
     * 通过动态和标签查找绑定关系
     *
     * @param action 动态
     * @param tag    标签
     * @return 动态标签绑定对象或null
     */
    ActionTag findByActionAndTag(Action action, Tag tag);
}
