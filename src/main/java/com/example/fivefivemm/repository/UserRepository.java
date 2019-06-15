package com.example.fivefivemm.repository;

import com.example.fivefivemm.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 用户仓库
 *
 * @author tiga
 * @version 1.0
 * @since 2019年5月13日18:30:37
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    /**
     * 通过账号查找用户
     *
     * @param account 账号
     * @return 存在该账号则返回该用户，不存在返回null
     */
    User findByAccount(String account);

    /**
     * 通过邮箱查找用户
     *
     * @param email 邮箱
     * @return 存在该邮箱则返回该用户，不存在返回null
     */
    User findByEmail(String email);

    /**
     * 通过用户Id查找用户
     *
     * @param userId 邮箱
     * @return 存在该用户Id则返回该用户，不存在返回null
     */
    User findByUserId(Integer userId);
}