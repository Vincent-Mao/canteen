package com.fast.canteen.domain.vo;

import lombok.Data;

/**
 * 菜品分类VO(用于下拉选择分类)
 */
@Data
public class CategoryVO {
    //分类ID
    private String categoryId;
    //分类名称
    private String name;
}
