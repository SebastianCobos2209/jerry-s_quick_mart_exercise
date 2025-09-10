package models.Customer.models.Cart;

import models.Customer.models.Customer.Customer;
import models.Customer.models.Inventory.Product;

public class CartItem {
    private final Product product;
    private int quantity;
    private Customer customer;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice(Customer customer) {
        return customer.getPrice(product) * quantity;
    }

    @Override
    public String toString() {
        return product.getName() + "x" + quantity + "$" + customer.getPrice(product);
    }
}
