package com.fast.canteen.domain;

import com.fast.system.general.annotation.Excel;
import com.fast.system.general.core.domain.BaseEntity;
import lombok.*;

/**
 * 意见反馈对象 feedback
 *
 * @author fast
 * @date 2025-11-23
 */
@Data
public class Feedback extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 意见反馈ID */
    private String feedbackId;

    /** 反馈类型 */
    @Excel(name = "反馈类型")
    private String type;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String contact;

    /** 反馈内容 */
    @Excel(name = "反馈内容")
    private String content;

    /** 反馈用户ID */
    @Excel(name = "反馈用户ID")
    private Long userId;

    //反馈用户
    private String userName;


}
