package com.frode.lien.SysProgUppgift1.service;

import com.frode.lien.SysProgUppgift1.model.CartItem;
import com.frode.lien.SysProgUppgift1.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    CartRepository cartDatabase;

    public List<CartItem> selectAllItemsInCart() {
        return cartDatabase.selectAllItems();
    }

    public int addOrRemoveFromCart(String actionToExecute, CartItem cartItem) {
        if (actionToExecute == null) {
            return -3;
        } else {
            if (actionToExecute.equalsIgnoreCase("add")) {
                return cartDatabase.insertOrIncrementItem(cartItem);
            } else if (actionToExecute.equalsIgnoreCase("remove")) {
                return cartDatabase.deleteOrDecrementItem(cartItem);
            } else {
                return -1;
            }
        }
    }

    public void emptyCart(boolean buyout) {
        cartDatabase.deleteAllItems(!buyout);
    }
}
