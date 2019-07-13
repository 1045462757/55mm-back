package com.example.fivefivemm.service.Implements;

import com.example.fivefivemm.entity.tag.Tag;
import com.example.fivefivemm.repository.TagRepository;
import com.example.fivefivemm.service.TagService;
import com.example.fivefivemm.utility.BusinessResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 标签业务类
 *
 * @author tiga
 * @version 1.0
 * @since 2019年7月12日09:28:03
 */
@Service
public class TagServiceImplements implements TagService {

    @Resource
    private TagRepository tagRepository;

    private Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public BusinessResult CreateTag(Tag tag) {
        if (tag == null || tag.getName() == null || tag.getName().length() == 0) {
            return new BusinessResult(false, 60001, "参数无效:标签名称为空");
        }

        logger.info("保存新标签:[名称:" + tag.getName() + "]");
        return new BusinessResult(true, tagRepository.save(tag));
    }

    @Override
    public BusinessResult DeleteTag(Tag tag) {
        if (tag == null || tag.getTagId() == null) {
            return new BusinessResult(false, 60002, "参数无效:标签Id为空");
        }

        if (tagRepository.findByTagId(tag.getTagId()) == null) {
            return new BusinessResult(false, 60003, "不存在的标签");
        }

        tagRepository.deleteById(tag.getTagId());
        logger.info("删除标签:[Id:" + tag.getTagId() + "]");
        return new BusinessResult(true);
    }
}
