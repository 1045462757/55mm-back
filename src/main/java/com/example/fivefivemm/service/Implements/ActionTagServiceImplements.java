package com.example.fivefivemm.service.Implements;

import com.example.fivefivemm.entity.action.Action;
import com.example.fivefivemm.entity.relation.ActionTag;
import com.example.fivefivemm.entity.tag.Tag;
import com.example.fivefivemm.repository.ActionRepository;
import com.example.fivefivemm.repository.ActionTagRepository;
import com.example.fivefivemm.repository.TagRepository;
import com.example.fivefivemm.service.ActionTagService;
import com.example.fivefivemm.utility.BusinessResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * 动态标签绑定业务类
 *
 * @author tiga
 * @version 1.0
 * @since 2019年7月12日09:55:54
 */
@Service
public class ActionTagServiceImplements implements ActionTagService {

    @Resource
    private ActionRepository actionRepository;

    @Resource
    private TagRepository tagRepository;

    @Resource
    private ActionTagRepository actionTagRepository;

    @Override
    public BusinessResult CreateActionTag(Integer actionId, List<Integer> tagList) {
        if (actionId == null || tagList == null || tagList.size() == 0) {
            return new BusinessResult(false, 70001, "参数无效:动态Id,标签Id为空");
        }

        Action validAction = actionRepository.findByActionId(actionId);
        if (validAction == null) {
            return new BusinessResult(false, 70002, "不存在的动态");
        }

        List<ActionTag> actionTags = new ArrayList<>();
        for (Integer tagId : tagList) {
            Tag validTag = tagRepository.findByTagId(tagId);
            if (validTag == null) {
                return new BusinessResult(false, 70003, "不存在的标签");
            }
            actionTags.add(new ActionTag(new Action(actionId), new Tag(tagId)));
        }

        actionTagRepository.saveAll(actionTags);

        return new BusinessResult(true);
    }

    @Override
    public BusinessResult findTags(Integer actionId) {
        if (actionId == null) {
            return new BusinessResult(false, 70004, "动态Id为空");
        }

        if (actionRepository.findByActionId(actionId) == null) {
            return new BusinessResult(false, 70005, "不存在的动态");
        }

        List<ActionTag> actionTags = actionTagRepository.findAll((Root<ActionTag> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) ->
                (criteriaBuilder.equal(root.get("action").get("actionId"), actionId)));

        List<Tag> tags = new ArrayList<>();
        for (ActionTag actionTag : actionTags) {
            tags.add(actionTag.getTag());
        }
        return new BusinessResult(true, tags);
    }
}
