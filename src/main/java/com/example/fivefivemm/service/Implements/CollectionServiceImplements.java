package com.example.fivefivemm.service.Implements;

import com.example.fivefivemm.entity.action.Action;
import com.example.fivefivemm.entity.relation.ActionCollection;
import com.example.fivefivemm.entity.relation.UserCollection;
import com.example.fivefivemm.entity.user.User;
import com.example.fivefivemm.repository.ActionCollectionRepository;
import com.example.fivefivemm.repository.ActionRepository;
import com.example.fivefivemm.repository.UserCollectionRepository;
import com.example.fivefivemm.repository.UserRepository;
import com.example.fivefivemm.service.CollectionService;
import com.example.fivefivemm.utility.BusinessResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * 收藏业务类
 * <p>
 * 优化代码
 * 2019年6月7日11:45:22
 * <p>
 * 优化大代码
 * 2019年6月15日10:00:55
 *
 * @author tiga
 * @version 1.0
 * @since 2019年5月27日12:35:00
 */
@Service
public class CollectionServiceImplements implements CollectionService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private ActionRepository actionRepository;

    @Resource
    private ActionCollectionRepository actionCollectionRepository;

    @Resource
    private UserCollectionRepository userCollectionRepository;

    private Logger logger = LogManager.getLogger(this.getClass());

    @Override
    @Transactional
    public BusinessResult addActionCollection(Integer userId, Integer actionId) {
        if (userId == null || actionId == null) {
            return new BusinessResult(false, 50001, "参数无效:用户Id,动态Id为空");
        }

        User validUser = userRepository.findByUserId(userId);
        if (validUser == null) {
            return new BusinessResult(false, 50002, "不存在的用户");
        }

        Action validAction = actionRepository.findByActionId(actionId);
        if (validAction == null) {
            return new BusinessResult(false, 50003, "不存在的动态");
        }

        ActionCollection actionCollection = actionCollectionRepository.findByCollectorAndCollectAction(validUser, validAction);
        if (actionCollection != null) {
            return new BusinessResult(false, 50004, "已存在的收藏关系");
        }

        int collect;
        if (validAction.getCollect() == null) {
            collect = 0;
        } else {
            collect = validAction.getCollect();
        }
        validAction.setCollect(collect + 1);
        actionCollectionRepository.save(new ActionCollection(validUser, validAction));
        logger.info("保存收藏:[收藏用户Id:" + validUser.getUserId() + ", 收藏动态Id:" + validAction.getActionId() + "]");
        return new BusinessResult(true);
    }

    @Override
    @Transactional
    public BusinessResult removeActionCollection(Integer userId, Integer actionId) {
        if (userId == null || actionId == null) {
            return new BusinessResult(false, 50011, "参数无效:用户Id,动态Id为空");
        }

        Action validAction = actionRepository.findByActionId(actionId);
        if (validAction == null) {
            return new BusinessResult(false, 50012, "不存在的动态");
        }

        ActionCollection actionCollection = actionCollectionRepository.findByCollectorAndCollectAction(new User(userId), validAction);
        if (actionCollection == null) {
            return new BusinessResult(false, 50013, "不存在的收藏关系");
        }

        int collect;
        if (validAction.getCollect() == null) {
            collect = 0;
        } else {
            collect = validAction.getCollect();
        }
        validAction.setCollect(collect - 1);
        actionCollectionRepository.deleteById(actionCollection.getActionCollectionId());
        logger.info("删除收藏:[收藏关系Id:" + actionCollection.getActionCollectionId() + "]");
        return new BusinessResult(true);
    }

    @Override
    public boolean findActionCollection(Integer userId, Integer actionId) {
        if (userId == null || actionId == null) {
            return false;
        }
        ActionCollection actionCollection = actionCollectionRepository.findByCollectorAndCollectAction(new User(userId), new Action(actionId));
        return (actionCollection != null);
    }

    @Override
    @Transactional
    public BusinessResult addUserCollection(Integer focusId, Integer fansId) {
        if (focusId == null || fansId == null) {
            return new BusinessResult(false, 50021, "参数无效:关注者用户Id,粉丝用户Id为空");
        }

        User focus = userRepository.findByUserId(focusId);
        User fans = userRepository.findByUserId(fansId);
        if (focus == null || fans == null) {
            return new BusinessResult(false, 50022, "不存在的用户");
        }

        UserCollection userCollection = userCollectionRepository.findByFocusAndFans(focus, fans);
        if (userCollection != null) {
            return new BusinessResult(false, 50023, "已存在的关注关系");
        }

        userCollectionRepository.save(new UserCollection(focus, fans));
        logger.info("保存关注:[关注用户Id:" + focus.getUserId() + ", 粉丝Id:" + fans.getUserId() + "]");
        return new BusinessResult(true);
    }

    @Override
    @Transactional
    public BusinessResult removeUserCollection(Integer focusId, Integer fansId) {
        if (focusId == null || fansId == null) {
            return new BusinessResult(false, 50031, "参数无效:关注者用户Id,粉丝用户Id为空");
        }

        UserCollection userCollection = userCollectionRepository.findByFocusAndFans(new User(focusId), new User(fansId));
        if (userCollection == null) {
            return new BusinessResult(false, 50032, "不存在的关注关系");
        }
        userCollectionRepository.deleteById(userCollection.getUserCollectionId());
        logger.info("删除关注:[关注关系Id:" + userCollection.getUserCollectionId() + "]");
        return new BusinessResult(true);
    }

    @Override
    public boolean findUserCollection(Integer focusId, Integer fansId) {
        if (focusId == null || fansId == null) {
            return false;
        }
        UserCollection userCollection = userCollectionRepository.findByFocusAndFans(new User(focusId), new User(fansId));
        return (userCollection != null);
    }
}