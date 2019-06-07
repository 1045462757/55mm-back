package com.example.fivefivemm.service.Implements;

import com.example.fivefivemm.entity.action.Action;
import com.example.fivefivemm.repository.ActionRepository;
import com.example.fivefivemm.service.ActionService;
import com.example.fivefivemm.utility.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

/**
 * 动态业务类
 * <p>
 * 增加获取全部动态
 * 2019年5月21日16:47:22
 * <p>
 * 优化代码
 * 2019年6月7日10:59:06
 *
 * @author tiga
 * @version 1.2
 * @since 2019年5月19日13:03:44
 */
@Service
public class ActionServiceImplements implements ActionService {

    @Resource
    private ActionRepository actionRepository;

    private Logger logger = LogManager.getLogger(this.getClass());

    @Override
    @Transactional
    public Result CreateAction(Action action) {
        if (action == null || action.getAuthor() == null || action.getAuthor().getUserId() == null
                || action.getTitle() == null || action.getAddress() == null || action.getCost() == null || action.getContent() == null) {
            return new Result(Result.failed, "参数无效");
        }

        action.setCollect(0);
        logger.info("发表新动态:[标题:" + action.getTitle() + "]");
        return new Result(Result.success, actionRepository.save(action));
    }

    @Override
    public Result RetrieveAction(Integer actionId) {
        if (actionId == null) {
            return new Result(Result.failed, "参数无效");
        }

        Action existAction = actionRepository.findByActionId(actionId);
        if (existAction == null) {
            return new Result(Result.failed, "该动态不存在");
        }
        logger.info("查询动态:[动态Id:" + existAction.getActionId() + "]");
        return new Result(Result.success, existAction);
    }

    @Override
    public List<Action> RetrieveAllAction() {
        return actionRepository.findAll();
    }

    @Override
    @Transactional
    public Result UpdateAction(Action action) {
        if (action == null || action.getActionId() == null || action.getAuthor() == null || action.getAuthor().getUserId() == null
                || action.getTitle() == null || action.getAddress() == null || action.getCost() == null || action.getContent() == null) {
            return new Result(Result.failed, "参数无效");
        }

        Action existAction = actionRepository.findByActionId(action.getActionId());
        if (existAction == null) {
            return new Result(Result.failed, "该动态不存在");
        }

        if (!existAction.getAuthor().getUserId().equals(action.getAuthor().getUserId())) {
            return new Result(Result.failed, "没有权限修改，您不是该动态的作者");
        }

        existAction.setTitle(action.getTitle());
        existAction.setAddress(action.getAddress());
        existAction.setCost(action.getCost());
        existAction.setContent(action.getContent());
        logger.info("修改动态:[动态Id:" + existAction.getActionId() + "]");
        return new Result(Result.success);
    }

    @Override
    @Transactional
    public Result DeleteAction(Action action) {
        if (action == null || action.getActionId() == null || action.getAuthor() == null || action.getAuthor().getUserId() == null) {
            return new Result(Result.failed, "参数无效");
        }

        Action existAction = actionRepository.findByActionId(action.getActionId());
        if (existAction == null) {
            return new Result(Result.failed, "该动态不存在");
        }

        if (!existAction.getAuthor().getUserId().equals(action.getAuthor().getUserId())) {
            return new Result(Result.failed, "没有权限删除，您不是该动态的作者");
        }

        actionRepository.deleteById(existAction.getActionId());
        logger.info("删除动态:[动态Id:" + existAction.getActionId() + "]");
        return new Result(Result.success);
    }
}
