package com.fast.canteen.service.impl;

import java.util.List;

import com.fast.system.general.utils.DateUtils;
import com.fast.system.general.utils.SecurityUtils;
import com.fast.system.general.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fast.canteen.mapper.CartMapper;
import com.fast.canteen.domain.Cart;
import com.fast.canteen.service.ICartService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.util.CollectionUtils;

import static com.fast.system.general.utils.SecurityUtils.getUserId;

/**
 * 购物车Service业务层处理
 *
 * @author fast
 * @date 2025-11-20
 */
@Service
public class CartServiceImpl implements ICartService {
    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 查询购物车
     *
     * @param cartId 购物车主键
     * @return 购物车
     */
    @Override
    public Cart selectCartByCartId(String cartId) {
        return cartMapper.selectCartByCartId(cartId);
    }

    /**
     * 查询购物车列表
     *
     * @param cart 购物车
     * @return 购物车
     */
    @Override
    public List<Cart> selectCartList(Cart cart) {
        return cartMapper.selectCartList(cart);
    }

    /**
     * 新增购物车
     *
     * @param cart 购物车
     * @return 结果
     */
    @Override
    public int insertCart(Cart cart) {
        cart.setCreateTime(DateUtils.getNowDate());
        cart.setCartId(IdUtils.fastSimpleUUID());
        cart.setUserId(getUserId());

        //查询当前用户的购物车中有没有该菜品
        Boolean isThere = cartMapper.selectIsThereByDishesIdAndUserId(cart.getDishesId(), getUserId());

        //如果购物车中有该菜品, 在原有数量上+1, 如果没有, 直接新增
        if (isThere) {
            //根据用户ID和菜品ID查询购物车信息
            Cart updCart = cartMapper.selectCartByDishesIdAndUserId(cart.getDishesId(), getUserId());
            updCart.setQuantity(updCart.getQuantity() + 1);
            return cartMapper.updateCart(updCart);
        } else {
            return cartMapper.insertCart(cart);
        }
    }

    /**
     * 批量新增购物车
     *
     * @param carts 购物车List
     * @return 结果
     */
    @Override
    public int batchInsertCart(List<Cart> carts) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int count = 0;
        if (!CollectionUtils.isEmpty(carts)) {
            try {
                for (int i = 0; i < carts.size(); i++) {
                    int row = cartMapper.insertCart(carts.get(i));
                    // 防止内存溢出，每100次提交一次,并清除缓存
                    boolean bool = (i > 0 && i % 100 == 0) || i == carts.size() - 1;
                    if (bool) {
                        sqlSession.commit();
                        sqlSession.clearCache();
                    }
                    count = i + 1;
                }
            } catch (Exception e) {
                e.printStackTrace();
                // 没有提交的数据可以回滚
                sqlSession.rollback();
            } finally {
                sqlSession.close();
                return count;
            }
        }
        return count;
    }

    /**
     * 修改购物车
     *
     * @param cart 购物车
     * @return 结果
     */
    @Override
    public int updateCart(Cart cart) {
        return cartMapper.updateCart(cart);
    }

    /**
     * 批量删除购物车
     *
     * @param cartIds 需要删除的购物车主键
     * @return 结果
     */
    @Override
    public int deleteCartByCartIds(String[] cartIds) {
        return cartMapper.deleteCartByCartIds(cartIds);
    }

    /**
     * 删除购物车信息
     *
     * @param cartId 购物车主键
     * @return 结果
     */
    @Override
    public int deleteCartByCartId(String cartId) {
        return cartMapper.deleteCartByCartId(cartId);
    }
}
