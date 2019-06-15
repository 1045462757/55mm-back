package com.example.fivefivemm.service.Implements;

import com.example.fivefivemm.entity.action.Action;
import com.example.fivefivemm.entity.relation.ActionWatch;
import com.example.fivefivemm.entity.user.User;
import com.example.fivefivemm.repository.ActionRepository;
import com.example.fivefivemm.repository.ActionWatchRepository;
import com.example.fivefivemm.repository.UserRepository;
import com.example.fivefivemm.service.ActionWatchService;
import com.example.fivefivemm.utility.BusinessResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * 约拍信息业务类
 * <p>
 * 优化代码
 * 2019年6月7日11:12:03
 * <p>
 * 代码大优化
 * 2019年6月14日19:28:23
 *
 * @author tiga
 * @version 1.2
 * @since 2019年5月20日18:28:16
 */
@Service
public class ActionWatchServiceImplements implements ActionWatchService {

    @Resource
    private ActionWatchRepository actionWatchRepository;

    @Resource
    private ActionRepository actionRepository;

    @Resource
    private UserRepository userRepository;

    private Logger logger = LogManager.getLogger(this.getClass());

    @Override
    @Transactional
    public BusinessResult CreateActionWatch(ActionWatch actionWatch) {
        if (actionWatch == null || actionWatch.getAction() == null || actionWatch.getWatcher() == null || actionWatch.getAction().getActionId() == null
                || actionWatch.getWatcher().getUserId() == null) {
            return new BusinessResult(false, 30001, "参数无效:动态Id,约拍者用户Id为空");
        }

        Action existAction = actionRepository.findByActionId(actionWatch.getAction().getActionId());
        if (existAction == null) {
            return new BusinessResult(false, 30002, "不存在的动态");
        }

        User existUser = userRepository.findByUserId(actionWatch.getWatcher().getUserId());
        if (existUser == null) {
            return new BusinessResult(false, 30003, "不存在的用户");
        }

        ActionWatch existActionWatch = actionWatchRepository.findByActionAndWatcher(actionWatch.getAction(), actionWatch.getWatcher());
        if (existActionWatch != null) {
            return new BusinessResult(false, 30004, "约拍记录已存在");
        }
        actionWatchRepository.save(actionWatch);
        logger.info("生成新的约拍记录:[动态Id:" + actionWatch.getAction().getActionId() + ",约拍者Id:" + actionWatch.getWatcher().getUserId() + "]");
        return new BusinessResult(true);
    }

    @Override
    public boolean RetrieveActionWatch(ActionWatch actionWatch) {
        if (actionWatch == null || actionWatch.getAction() == null || actionWatch.getWatcher() == null
                || actionWatch.getAction().getActionId() == null || actionWatch.getWatcher().getUserId() == null) {
            return false;
        }

        ActionWatch existActionWatch = actionWatchRepository.findByActionAndWatcher(actionWatch.getAction(), actionWatch.getWatcher());
        if (existActionWatch == null) {
            return false;
        }

        logger.info("查询约拍记录:[动态Id:" + existActionWatch.getAction().getActionId() + ",约拍者Id:" + actionWatch.getWatcher().getUserId() + "]");
        return true;
    }

    @Override
    @Transactional
    public BusinessResult DeleteActionWatch(ActionWatch actionWatch) {
        if (actionWatch == null || actionWatch.getAction() == null || actionWatch.getWatcher() == null || actionWatch.getAction().getActionId() == null
                || actionWatch.getWatcher().getUserId() == null) {
            return new BusinessResult(false, 30011, "参数无效:动态Id,约拍者用户Id为空");
        }

        ActionWatch existActionWatch = actionWatchRepository.findByActionAndWatcher(actionWatch.getAction(), actionWatch.getWatcher());
        if (existActionWatch == null) {
            return new BusinessResult(false, 30012, "该约拍记录不存在");
        }

        actionWatchRepository.deleteById(existActionWatch.getActionWatchId());
        logger.info("删除约拍记录:[约拍记录Id:" + existActionWatch + "]");
        return new BusinessResult(true);
    }
}