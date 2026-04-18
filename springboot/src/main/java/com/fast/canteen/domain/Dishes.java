package com.fast.canteen.domain;

import java.math.BigDecimal;
import com.fast.system.general.annotation.Excel;
import com.fast.system.general.core.domain.BaseEntity;
import lombok.*;

/**
 * 菜品对象 dishes
 *
 * @author fast
 * @date 2025-11-19
 */
@Data
public class Dishes extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 菜品ID */
    private String dishesId;

    /** 菜品名称 */
    @Excel(name = "菜品名称")
    private String name;

    /** 描述 */
    @Excel(name = "描述")
    private String description;

    /** 价格 */
    @Excel(name = "价格")
    private BigDecimal price;

    /** 图片 */
    @Excel(name = "图片")
    private String image;

    /** 分类ID */
    @Excel(name = "分类ID")
    private String categoryId;

    //分类名称
    private String categoryName;


}
