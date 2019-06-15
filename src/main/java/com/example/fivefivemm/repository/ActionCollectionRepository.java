package com.example.fivefivemm.repository;

import com.example.fivefivemm.entity.action.Action;
import com.example.fivefivemm.entity.relation.ActionCollection;
import com.example.fivefivemm.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionCollectionRepository extends JpaRepository<ActionCollection, Integer>, JpaSpecificationExecutor<ActionCollection> {

    /**
     * 通过用户和动态查找收藏关系
     *
     * @param collector     用户
     * @param collectAction 动态
     * @return 收藏关系对象或null
     */
    ActionCollection findByCollectorAndCollectAction(User collector, Action collectAction);
}
