package com.example.fivefivemm.service.Implements;

import com.example.fivefivemm.entity.action.Action;
import com.example.fivefivemm.entity.relation.ActionCollection;
import com.example.fivefivemm.repository.ActionCollectionRepository;
import com.example.fivefivemm.repository.ActionRepository;
import com.example.fivefivemm.repository.UserRepository;
import com.example.fivefivemm.service.ActionService;
import com.example.fivefivemm.utility.BusinessResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * 动态业务类
 * <p>
 * 增加获取全部动态
 * 2019年5月21日16:47:22
 * <p>
 * 优化代码
 * 2019年6月7日10:59:06
 * <p>
 * 优化大代码
 * 2019年6月14日16:35:04
 *
 * @author tiga
 * @version 1.3
 * @since 2019年5月19日13:03:44
 */
@Service
public class ActionServiceImplements implements ActionService {

    @Resource
    private ActionRepository actionRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private ActionCollectionRepository actionCollectionRepository;

    private Logger logger = LogManager.getLogger(this.getClass());

    @Override
    @Transactional
    public BusinessResult CreateAction(Action action) {
        if (action == null || action.getAuthor() == null || action.getAuthor().getUserId() == null
                || action.getTitle() == null || action.getAddress() == null || action.getCost() == null || action.getContent() == null) {
            return new BusinessResult(false, 20001, "参数无效:作者用户Id,标题,地区,价格,内容为空");
        }

        action.setCollect(0);
        logger.info("发表新动态:[标题:" + action.getTitle() + "]");
        return new BusinessResult(true, actionRepository.save(action));
    }

    @Override
    public BusinessResult RetrieveAction(Integer actionId) {
        if (actionId == null) {
            return new BusinessResult(false, 20011, "参数无效:动态Id为空");
        }

        Action existAction = actionRepository.findByActionId(actionId);
        if (existAction == null) {
            return new BusinessResult(false, 20012, "该动态不存在");
        }
        logger.info("查询动态:[动态Id:" + existAction.getActionId() + "]");
        return new BusinessResult(true, existAction);
    }

    @Override
    @Transactional
    public BusinessResult UpdateAction(Action action) {
        if (action == null || action.getActionId() == null || action.getAuthor() == null || action.getAuthor().getUserId() == null
                || action.getTitle() == null || action.getAddress() == null || action.getCost() == null || action.getContent() == null) {
            return new BusinessResult(false, 20021, "参数无效:动态Id,作者用户Id,标题,地区,价格,内容为空");
        }

        Action existAction = actionRepository.findByActionId(action.getActionId());
        if (existAction == null) {
            return new BusinessResult(false, 20022, "该动态不存在");
        }

        if (!existAction.getAuthor().getUserId().equals(action.getAuthor().getUserId())) {
            return new BusinessResult(false, 20023, "没有权限修改，您不是该动态的作者");
        }

        existAction.setTitle(action.getTitle());
        existAction.setAddress(action.getAddress());
        existAction.setCost(action.getCost());
        existAction.setContent(action.getContent());
        logger.info("修改动态:[动态Id:" + existAction.getActionId() + "]");
        return new BusinessResult(true);
    }

    @Override
    @Transactional
    public BusinessResult DeleteAction(Action action) {
        if (action == null || action.getActionId() == null || action.getAuthor() == null || action.getAuthor().getUserId() == null) {
            return new BusinessResult(false, 20031, "参数无效:动态Id,作者Id为空");
        }

        Action existAction = actionRepository.findByActionId(action.getActionId());
        if (existAction == null) {
            return new BusinessResult(false, 20032, "该动态不存在");
        }

        if (!existAction.getAuthor().getUserId().equals(action.getAuthor().getUserId())) {
            return new BusinessResult(false, 20033, "没有权限删除，您不是该动态的作者");
        }

        actionRepository.deleteById(existAction.getActionId());
        logger.info("删除动态:[动态Id:" + existAction.getActionId() + "]");
        return new BusinessResult(true);
    }

    @Override
    @SuppressWarnings("all")
    public BusinessResult findActionByPage(Integer pageIndex, String address, String authorType, String authorSex, Integer minCost, Integer maxCost) {
        if (pageIndex == null) {
            return new BusinessResult(false, 20041, "参数无效:页数为空");
        }

        Page<Action> actionPage = actionRepository.findAll((Root<Action> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
                    List<Predicate> predicates = new ArrayList<>();

                    //按地址查询
                    if (address != null && address.length() != 0) {
                        predicates.add(criteriaBuilder.equal(root.get("address").as(String.class), address));
                    }

                    //按价格查询
                    if (minCost != null && maxCost != null) {
                        predicates.add(criteriaBuilder.between(root.get("cost").as(Integer.class), minCost, maxCost));
                    }

                    //作者身份查询
                    if (authorType != null && authorType.length() != 0) {
//                        root.join("author", JoinType.INNER);
                        predicates.add(criteriaBuilder.equal(root.get("author").get("type").as(String.class), authorType));
                    }

                    //作者性别查询
                    if (authorSex != null && authorSex.length() != 0) {
//                        root.join("author", JoinType.INNER);
                        predicates.add(criteriaBuilder.equal(root.get("author").get("sex").as(String.class), authorSex));
                    }
                    //两种方法都可以
                    return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
//                    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                },
                PageRequest.of(pageIndex - 1, 10, new Sort(Sort.Direction.DESC, "actionId")));
        return new BusinessResult(true, actionPage);
    }

    @Override
    public BusinessResult findActionByAuthor(Integer authorId, Integer pageIndex) {
        if (authorId == null || pageIndex == null) {
            return new BusinessResult(false, 20051, "参数无效:作者用户Id,页数为空");
        }

        if (userRepository.findByUserId(authorId) == null) {
            return new BusinessResult(false, 20052, "不存在的用户");
        }

        Page<Action> actionPage = actionRepository.findAll((Root<Action> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) ->
                        (criteriaBuilder.equal(root.get("author").get("userId"), authorId)),
                PageRequest.of(pageIndex - 1, 10, new Sort(Sort.Direction.DESC, "actionId")));
        return new BusinessResult(true, actionPage);
    }

    @Override
    public BusinessResult findActionByCollection(Integer collectorId, Integer pageIndex) {
        if (collectorId == null || pageIndex == null) {
            return new BusinessResult(false, 20061, "参数无效:收藏者用户Id,页数为空");
        }

        if (userRepository.findByUserId(collectorId) == null) {
            return new BusinessResult(false, 20062, "不存在的用户");
        }

        Page<ActionCollection> actionCollectionPage = actionCollectionRepository.findAll(
                (Root<ActionCollection> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) ->
                        (criteriaBuilder.equal(root.get("collector").get("userId"), collectorId)),
                PageRequest.of(pageIndex - 1, 10, new Sort(Sort.Direction.DESC, "createTime")));
        return new BusinessResult(true, actionCollectionPage);
    }
}
