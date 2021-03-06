package org.csu.mypetstore.service;

import org.csu.mypetstore.domain.CartItem;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.persistence.CartItemMapper;
import org.csu.mypetstore.persistence.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartItemMapper cartItemMapper;


    public List<CartItem> getCart(String username)
    {
        return cartItemMapper.getCartItemList(username);
    }

    //获取购物车中特定的商品
    public CartItem getCartItem(String username, String itemId)
    {
        return cartItemMapper.getCartItem(username, itemId);
    }

   //修改商品数量并更新购物车商品状态
    public void updateCartItemQuantity(CartItem cartItem)
    {
        CartItem _cartItem=cartItemMapper.getCartItem(cartItem.getUsername(), cartItem.getItemId());
        if(cartItem.getQuantity() > 0)
        {
            if( cartItem== null)
            {
                cartItemMapper.insertCartItem(cartItem);
            }
            else
            {
                cartItemMapper.updateQuantity(cartItem.getUsername(), cartItem.getItemId(), cartItem.getQuantity());
            }

        }
        else if(cartItem != null)
        {
            cartItemMapper.deleteCartItem(cartItem.getUsername(), cartItem.getItemId());
        }
    }

    //向购物车中添加商品
    public void addItem(String username, String itemId)
    {
        System.out.println(username +" "+ itemId);
            CartItem cartItem = cartItemMapper.getCartItem(username, itemId);
          //  System.out.println(cartItem);
            if(cartItem == null)
            {
                cartItemMapper.insertCartItem(new CartItem(username,itemId,1));
            }
            else
            {
                cartItemMapper.updateQuantity(username,itemId,cartItem.getQuantity()+1);
            }

    }

    //从购物车中删除商品
    public void removeItem(String username,String itemId)
    {
        cartItemMapper.deleteCartItem(username,itemId);
    }

    public void deleteCart(String username)
    {
        cartItemMapper.deleteCartItemByCart(username);
    }


}
