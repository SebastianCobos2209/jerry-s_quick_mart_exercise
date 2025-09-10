package models.Menus;

import models.Cart.CartItem;
import models.CheckOut.CheckOut;
import models.Customer.Customer;
import models.Customer.Member;
import models.Customer.Regular;
import models.Inventory.Inventory;
import models.Inventory.Product;

import java.io.IOException;
import java.util.Scanner;

public class StoreMenu {
    private final Scanner scanner;

    public StoreMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void RunStoreMenu(){
        Inventory inventory = new Inventory();
        Customer customer;

        System.out.println("JERRY'S QUICK MARKET");

        try {
            System.out.print("Upload the route of the inventory (ex: src/inventory.txt): ");
            String route = scanner.nextLine();
            inventory.UploadProductsData(route);
            System.out.println("Upload of the inventory successfull\n");
            System.out.print("add the name of the client: ");
            String name = scanner.nextLine();

            System.out.print("is the client a member? (y/n): ");
            String type = scanner.nextLine();
            if (type.equalsIgnoreCase("y")) {
                customer = new Member(name);
            } else {
                customer = new Regular(name);
            }

            int option;
            do {
                System.out.println("\n=== MENU ===");
                System.out.println("1. show products");
                System.out.println("2. add product to cart");
                System.out.println("3. remove product from cart");
                System.out.println("4. cart");
                System.out.println("5. Checkout");
                System.out.println("6. exit");
                System.out.print("choose option: ");
                option = scanner.nextInt();
                scanner.nextLine();

                switch(option) {
                    case 1:
                        System.out.println("\n=== ALL PRODUCTS ===");
                        for(Product p : inventory.getProducts()) {
                            System.out.println(p);
                        }
                        pause(scanner);
                        break;

                    case 2:
                        System.out.print("\n=== ADD PRODUCT TO CART ===");
                        for(Product p : inventory.getProducts()) {
                            System.out.println(p);
                        }
                        System.out.println("Enter the name of the product");
                        String addProductName = scanner.nextLine();
                        Product addProduct = inventory.searchProduct(addProductName);
                        System.out.println("Enter the amount to be added to the cart");
                        int quantity = scanner.nextInt();

                        if(addProduct != null) {
                            customer.addToCart(addProduct, quantity);
                        }else{
                            System.out.println("Product not found");
                        }
                        break;

                    case 3:
                        System.out.println("\n=== REMOVE PRODUCT TO CART ===");
                        if(customer.getCart().getProducts().isEmpty()){
                            System.out.println("No products found");
                        }else {
                            customer.viewCart();
                            System.out.print("Enter the name of the product to remove ");
                            String removeProductName = scanner.nextLine();
                            CartItem toRemoveProduct = customer.getCart().findInCart(removeProductName);
                            if (toRemoveProduct != null) {
                                System.out.print("Enter quantity to remove: ");
                                int qtyToRemove = scanner.nextInt();
                                scanner.nextLine();
                                if(qtyToRemove<=0){
                                    System.out.println("Quantity must be greater than zero");
                                }else{
                                    customer.removeFromCart(toRemoveProduct.getProduct(),qtyToRemove);
                                }
                                System.out.println(toRemoveProduct.getProduct().getName() + " has been removed");
                            } else {
                                System.out.println("product not found");
                            }
                        }
                        break;
                    case 4:
                        System.out.println("\n=== CART ===");
                        customer.viewCart();
                        pause(scanner);
                        break;

                    case 5:
                        System.out.println("\n=== CHECKOUT ===");
                        CheckOut checkOut = new CheckOut(customer);
                        checkOut.processCheckout(scanner);
                        break;

                    case 6:
                        System.out.println("\n=== GOOD BYE ===");
                        break;

                    default:
                        System.out.println("NOT VALID OPTION");
                }

            } while(option != 5);

        } catch (IOException e) {
            System.out.println("CANNOT READ THE FILE: " + e.getMessage());
        }
    }
    public static void pause(Scanner scanner){
        System.out.println("Press any key to continue...");
        scanner.nextLine();
    }
}
