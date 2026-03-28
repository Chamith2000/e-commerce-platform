package com.ecommerce.cartservice.service;

import com.ecommerce.cartservice.dto.CartItemDTO;
import com.ecommerce.cartservice.model.Cart;
import com.ecommerce.cartservice.model.CartItem;
import com.ecommerce.cartservice.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));
    }

    public Cart getOrCreateCart(Long userId) {
        return cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setUserId(userId);
            return cartRepository.save(cart);
        });
    }

    public Cart addItemToCart(Long userId, CartItemDTO dto) {
        Cart cart = getOrCreateCart(userId);

        // Check if item already in cart
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(dto.getProductId()))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + dto.getQuantity());
            item.setSubtotal(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProductId(dto.getProductId());
            newItem.setProductName(dto.getProductName());
            newItem.setProductImageUrl(dto.getProductImageUrl());
            newItem.setQuantity(dto.getQuantity());
            newItem.setUnitPrice(dto.getUnitPrice());
            newItem.setSubtotal(dto.getUnitPrice().multiply(BigDecimal.valueOf(dto.getQuantity())));
            cart.getItems().add(newItem);
        }

        recalculateTotal(cart);
        return cartRepository.save(cart);
    }

    public Cart updateItemQuantity(Long userId, Long productId, int quantity) {
        Cart cart = getCartByUserId(userId);
        cart.getItems().stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                    if (quantity <= 0) {
                        cart.getItems().remove(item);
                    } else {
                        item.setQuantity(quantity);
                        item.setSubtotal(item.getUnitPrice().multiply(BigDecimal.valueOf(quantity)));
                    }
                });
        recalculateTotal(cart);
        return cartRepository.save(cart);
    }

    public Cart removeItemFromCart(Long userId, Long productId) {
        Cart cart = getCartByUserId(userId);
        cart.getItems().removeIf(i -> i.getProductId().equals(productId));
        recalculateTotal(cart);
        return cartRepository.save(cart);
    }

    public void clearCart(Long userId) {
        Cart cart = getCartByUserId(userId);
        cart.getItems().clear();
        cart.setTotalAmount(BigDecimal.ZERO);
        cartRepository.save(cart);
    }

    private void recalculateTotal(Cart cart) {
        BigDecimal total = cart.getItems().stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalAmount(total);
    }
}
