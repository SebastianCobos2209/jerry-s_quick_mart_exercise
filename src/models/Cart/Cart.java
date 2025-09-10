package models.Cart;

import models.Customer.Customer;
import models.Inventory.Product;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final List<CartItem> items = new ArrayList<>();

    public void addProduct(Product p, int quantity) {
        if (p.getStock() < quantity) {
            System.out.println("Not enough stock for " + p.getName() +
                    ". Available: " + p.getStock());
            return;
        }

        for(CartItem item: items){
            if(item.getProduct().getName().equalsIgnoreCase(p.getName())){
                int newQuantity = item.getQuantity() + quantity;
                if (newQuantity<=p.getStock()){
                    item.setQuantity(newQuantity);
                }else{
                    System.out.println("cannot add that quantity. There's not enough stock for " + p.getName());
                }
                return;
            }
        }
        items.add(new CartItem(p, quantity));
    }

    public void removeProduct(Product p, int quantity) {
        for (CartItem item: items){
            if(item.getProduct().getName().equalsIgnoreCase(p.getName())){
                if(item.getQuantity() > quantity){
                    item.setQuantity(item.getQuantity()-quantity);
                }else {
                    items.remove(item);
                }
                return;
             }
        }
    }
    public List<CartItem> getProducts(){
        return items;
    }

    public double calculateTotal(Customer customer) {
        double total = 0;
        for (CartItem cartItem : items) {
            total += cartItem.getTotalPrice(customer);
        }
        return total;
    }

    public CartItem findInCart(String name) {
        if(!items.isEmpty()) {
            for (CartItem cartItem : items) {
                if (cartItem.getProduct().getName().equalsIgnoreCase(name)) {
                    return cartItem;
                }
            }
        }else {
            System.out.println("Cart does not have any products");
        }

        return null;
    }


    public void viewCart(Customer customer) {
        if(!items.isEmpty())
        {
            System.out.printf("%-20s %-10s %-12s %-12s\n", "ITEM", "QUANTITY", "UNIT PRICE", "TOTAL");
            for (CartItem cartItem : items) {
                Product product = cartItem.getProduct();
                int quantity = cartItem.getQuantity();
                double unitPrice = customer.getPrice(product);
                double lineTotal = unitPrice * quantity;
                System.out.printf("%-20s %-10d $%-11.2f $%-11.2f\n",
                        product.getName(), quantity, unitPrice, lineTotal);
            }
            System.out.printf("TOTAL: $%.2f\n", calculateTotal(customer));
        }else {
            System.out.println("Cart does not have any products");
        }
    }

}
