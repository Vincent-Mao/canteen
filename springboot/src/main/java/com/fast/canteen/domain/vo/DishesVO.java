package com.fast.canteen.domain.vo;

import lombok.Data;

/**
 * 菜品VO(用于下拉选择菜品)
 */
@Data
public class DishesVO {
    //菜品ID
    private String dishesId;
    //菜品名称
    private String name;
}
