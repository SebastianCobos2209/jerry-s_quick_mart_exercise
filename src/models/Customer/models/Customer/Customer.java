package models.Customer.models.Customer;

import models.Customer.models.Cart.Cart;
import models.Customer.models.Inventory.Product;

public abstract class Customer {
    protected String name;
    protected Cart cart = new Cart();


    public Customer(String name){
        this.name = name;
    }

    public void addToCart(Product p, int quantity ){
        cart.addProduct(p, quantity);
    }
    public void removeFromCart(Product p, int quantity){
        cart.removeProduct(p, quantity);
    }

    public void viewCart(){
        cart.viewCart(this);
    }

    public void emptyCart(){
        cart = new Cart();
    }

    public abstract double getPrice(Product p);


    public Cart getCart() {
        return cart;
    }
    public String getName() {
        return name;
    }
}

