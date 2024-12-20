package com.example.New_bazario.services;

import com.example.New_bazario.entities.CartItem;
import com.example.New_bazario.entities.Order;
import com.example.New_bazario.repositories.OrderRepository;
import com.example.New_bazario.security.user.User;
import com.example.New_bazario.security.user.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository,UserRepository userRepository) {
        this.orderRepository = orderRepository;
		this.userRepository = userRepository;
    }
    @Transactional
    public Order createOrderFromCart(User user) {
    	Set<CartItem> cartItems = new HashSet<>(user.getCart().getCartItems());
        
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty. Cannot create an order.");
        }

        // Create the order
        Order newOrder = new Order();
        newOrder.setOrderItems(new HashSet<>(cartItems));
        newOrder.setCreatedAt(LocalDateTime.now());
        newOrder.setPaid(false);
        
        // Save the order first
        Order savedOrder = orderRepository.save(newOrder);
        
        // Add the order to user's orders set
        if (user.getOrders() == null) {
            user.setOrders(new HashSet<>());
        }
        user.getOrders().add(savedOrder);
        
        // Save the updated user
        userRepository.save(user);
        
        return savedOrder;
    }
    // Create an order for a user from selected cart items
    public Order createOrderForUser(User user, List<Integer> selectedCartItemIds) {
        // Extract the cart items from the user's cart
        Set<CartItem> selectedItems = user.getCart().getCartItems();

        if (selectedItems.isEmpty()) {
            throw new RuntimeException("No valid cart items selected for order.");
        }

        // Create a new order
        Order order = new Order();
        order.setOrderItems(selectedItems);
        order.setCreatedAt(LocalDateTime.now());
        order.setPaid(false);

        // Save the order and link it to the user
        user.getOrders().add(order);
        return orderRepository.save(order);
    }

    // Get an order by ID for a specific user
    public Order getOrderByIdForUser(User user, Integer orderId) {
        return user.getOrders().stream()
                .filter(order -> order.getOrderId().equals(orderId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Order not found for the user."));
    }

    // Get all orders for a specific user
    public List<Order> getOrdersByUser(User user) {
        return List.copyOf(user.getOrders());
    }
    public List<CartItem> getCartItemsByOrderIdForUser(User user, Integer orderId) {
        Order order = getOrderByIdForUser(user, orderId); // Reuse the existing method
        return new ArrayList<>(order.getOrderItems());
    }
    
    public Order removeItemFromOrder(Integer orderId, Integer cartItemId, Integer userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));


        CartItem itemToRemove = order.getOrderItems().stream()
                .filter(item -> item.getCartItemId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found in the order"));

        order.getOrderItems().remove(itemToRemove);
        return orderRepository.save(order);
    }
  


}
