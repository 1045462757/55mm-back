package com.example.fivefivemm.repository;

import com.example.fivefivemm.entity.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 标签仓库
 *
 * @author tiga
 * @version 1.0
 * @since 2019年7月12日11:19:20
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

    /**
     * 通过标签Id查找标签
     *
     * @param tagId 标签Id
     * @return tag对象或null
     */
    Tag findByTagId(Integer tagId);

//    /**
//     * 通过名称查找标签
//     *
//     * @param name 标签名称
//     * @return tag对象或null
//     */
//    Tag findByName(String name);
}
