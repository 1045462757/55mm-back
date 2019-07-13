package com.example.fivefivemm.service;

import com.example.fivefivemm.entity.tag.Tag;
import com.example.fivefivemm.utility.BusinessResult;

public interface TagService {

    /**
     * 增加标签
     *
     * @param tag 标签对象,需包含标签名称
     * @return data:保存的标签 errorCode:60001 errorMessage:参数无效:标签名称为空
     */
    BusinessResult CreateTag(Tag tag);

    /**
     * 删除标签
     *
     * @param tag 标签对象:需包含标签Id
     * @return errorCode:60002 errorMessage:参数无效:标签Id为空 errorCode:60003 errorMessage:不存在的标签
     */
    BusinessResult DeleteTag(Tag tag);
}
