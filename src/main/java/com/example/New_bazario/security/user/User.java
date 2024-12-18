package com.example.New_bazario.security.user;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.New_bazario.entities.Cart;
import com.example.New_bazario.entities.CartItem;
import com.example.New_bazario.entities.Order;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "_user")
public class User implements UserDetails {

    public User() {
        super();
    }

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;

    private String firstname;
    private String lastname;
    private String email;
    private String password;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cartId", referencedColumnName = "cartId")
    private Cart cart;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id", referencedColumnName = "id") // Reference cartId in CartItem
    private Set<Order> orders;
   
    
    public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	@Enumerated(EnumType.STRING)
    private Role role;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Activity activity;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructor
    private User(Builder builder) {
        this.id = builder.id;
        this.firstname = builder.firstname;
        this.lastname = builder.lastname;
        this.email = builder.email;
        this.password = builder.password;
        this.cart = builder.cart;
        this.role = builder.role;
        this.phoneNumber = builder.phoneNumber;
        this.activity = builder.activity;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    // Builder Pattern
    public static class Builder {
        private Integer id;
        private String firstname;
        private String lastname;
        private String email;
        private String password;
        private Cart cart; // Added cart
        private Role role;
        private String phoneNumber;
        private Activity activity;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder firstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public Builder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder cart(Cart cart) { // Setter for cart in builder
            this.cart = cart;
            return this;
        }

        public Builder role(Role role) {
            this.role = role;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder activity(Activity activity) {
            this.activity = activity;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    // Static method to access the builder
    public static Builder builder() {
        return new Builder();
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    // UserDetails interface methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
