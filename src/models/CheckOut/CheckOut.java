package models.CheckOut;

import models.Cart.CartItem;
import models.Customer.Customer;
import models.Inventory.Product;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public record CheckOut(Customer customer) {
    private static final double TAX_RATE = 0.065;

    public void processCheckout() {
        if (customer.getCart().getProducts().isEmpty()) {
            System.out.println("Your cart is empty");
            return;
        }

        int totalItems = customer.getCart().getProducts()
                .stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
        double subtotal = 0;
        double tax = 0;
        Scanner scanner = new Scanner(System.in);
        StringBuilder receipt = new StringBuilder();

        receipt.append("\n=== CHECKOUT RECEIPT ===\n");
        receipt.append("Customer: ").append(customer.getName()).append("\n");
        receipt.append("Date: ").append(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
        receipt.append("--------------------------------\n");

        receipt.append(String.format("%-20s %-10s %-12s %-12s\n", "ITEM", "QUANTITY", "UNIT PRICE", "TOTAL"));

        for (CartItem p : customer.getCart().getProducts()) {
            Product product = p.getProduct();
            int quantity = p.getQuantity();
            double unitPrice = customer.getPrice(product);
            double lineTotal = unitPrice * quantity;
            receipt.append(String.format("%-20s %-10d $%-11.2f $%-11.2f\n",
                    product.getName(), quantity, unitPrice, lineTotal));

            subtotal += lineTotal;

            if (product.getTaxable().equalsIgnoreCase("Taxable")) {
                tax += lineTotal * TAX_RATE;
            }
        }
        double total = subtotal + tax;

        receipt.append("--------------------------------\n");
        receipt.append("TOTAL NUMBER OF ITEMS SOLD: ").append(totalItems).append("\n");
        receipt.append(String.format("SUB-TOTAL: $%.2f\n", subtotal));
        receipt.append(String.format("TAX (6.5%%): $%.2f\n", tax));
        receipt.append(String.format("TOTAL: $%.2f\n", total));

        System.out.print("Enter cash received: $");
        double cash = scanner.nextDouble();
        double change = cash - total;

        receipt.append(String.format("CASH: $%.2f\n", cash));
        receipt.append(String.format("CHANGE: $%.2f\n", change));

        System.out.println(receipt);
        saveReceiptToFile(receipt.toString());

        customer.emptyCart();
    }

    private void saveReceiptToFile(String receiptContent) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("receipt.txt", true))) {
            writer.println(receiptContent);
            writer.println("\n====================================\n");
            System.out.println("Receipt saved to receipt.txt");
        } catch (IOException e) {
            System.out.println("Error writing receipt file: " + e.getMessage());
        }
    }


}
