package com.frode.lien.SysProgUppgift1.controller;


import com.frode.lien.SysProgUppgift1.model.CartItem;
import com.frode.lien.SysProgUppgift1.service.CartService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3010")
@RequestMapping("/api/v1/")
public class CartController {

    @Autowired
    CartService cartService;

    @GetMapping("carts/{id}")
    public ResponseEntity<Object> getAllInCart(@PathVariable int id) {
        if (id == 1) {
            return ResponseEntity.ok().body(cartService.selectAllItemsInCart());
        } else {
            return ResponseEntity.status(401).body("Your Id is wrong pleas try again");
        }
    }
    @PatchMapping("carts/{id}")
    public ResponseEntity<Object> addOrRemoveFromCart(@PathVariable int id,
                                              @RequestParam @Nullable String action,
                                              @RequestBody CartItem cartItem) {
        if (id == 1) {
            int result;
            result = cartService.addOrRemoveFromCart(action, cartItem);
            switch (result) {
                case -3:
                    return ResponseEntity.badRequest().body("The requestParam is Null");
                case -2:
                    return ResponseEntity.status(401).body("The cart i empty or we are out of stock");
                case -1:
                    return ResponseEntity.badRequest().body("Bad request please try again");
                case 1:
                    return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.status(401).body("Your Id is wrong pleas try again");
    }

    @DeleteMapping("carts/{id}")
    public ResponseEntity<Object> emptyCart(@PathVariable int id,
                                    @RequestParam(required = false) boolean buyout) {
        if (id == 1) {
            cartService.emptyCart(buyout);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(401).body("Your Id is wrong pleas try again");
        }
    }
}
