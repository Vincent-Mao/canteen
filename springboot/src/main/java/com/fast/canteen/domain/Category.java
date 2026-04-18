package com.fast.canteen.domain;

import com.fast.system.general.annotation.Excel;
import com.fast.system.general.core.domain.BaseEntity;
import lombok.*;

/**
 * 菜品分类对象 category
 *
 * @author fast
 * @date 2025-11-19
 */
@Data
public class Category extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 分类ID */
    private String categoryId;

    /** 分类名称 */
    @Excel(name = "分类名称")
    private String name;

    /** 图片 */
    @Excel(name = "图片")
    private String image;

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;


}
