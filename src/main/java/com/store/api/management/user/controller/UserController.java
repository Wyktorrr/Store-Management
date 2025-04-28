package com.store.api.management.user.controller;

import com.store.api.management.product.model.ProductDTO;
import com.store.api.management.product.service.ProductService;
import com.store.api.management.user.model.UserDTO;
import com.store.api.management.user.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserServiceImpl userServiceImpl;
    private final ProductService productService;

    public UserController(UserServiceImpl userService, ProductService productService) {
        this.userServiceImpl = userService;
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return userServiceImpl.findById(id)
                .map(user -> {
                    logger.info("User retrieved successfully: {}", user.getUsername());
                    return ResponseEntity.ok(user);
                })
                .orElseGet(() -> {
                    logger.warn("User with ID '{}' not found.", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userServiceImpl.findAll();
        logger.info("Retrieved all users, count: {}", users.size());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<ProductDTO>> getProductsByUserId(@PathVariable Long id) {
        UserDTO user = userServiceImpl.findById(id).orElse(null);
        if (user == null) {
            logger.warn("User with ID '{}' not found while fetching products.", id);
            return ResponseEntity.notFound().build();
        }
        List<ProductDTO> products = productService.findByUserId(id);
        logger.info("Retrieved products for user ID '{}', count: {}", id, products.size());
        return ResponseEntity.ok(products);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        userDTO.setId(id);
        UserDTO updatedUser = userServiceImpl.updateUser(userDTO);
        logger.info("User with ID '{}' updated successfully.", id);
        return ResponseEntity.ok(updatedUser);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userServiceImpl.deleteById(id);
        logger.info("User with ID '{}' deleted successfully.", id);
        return ResponseEntity.ok().build();
    }
}
