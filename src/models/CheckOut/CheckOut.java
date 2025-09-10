package models.CheckOut;

import models.Cart.CartItem;
import models.Customer.Customer;
import models.Customer.Member;
import models.Inventory.Product;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public record CheckOut(Customer customer) {

    private static final double TAX_RATE = 0.065;

    public void processCheckout(Scanner scanner) {
        double totalSavings = 0;
        int receiptNumber = getReceiptNumber();
        String receiptId = String.format("%06d", receiptNumber);
        double subtotal = 0;
        double tax = 0;

        if (customer.getCart().getProducts().isEmpty()) {
            System.out.println("Your cart is empty");
            return;
        }

        int totalItems = customer.getCart().getProducts()
                .stream()
                .mapToInt(CartItem::getQuantity)
                .sum();

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

            if (customer instanceof Member) {
                totalSavings += (product.getRegularPrice() - unitPrice) * quantity;
            }
        }
        double total = subtotal + tax;

        receipt.append("--------------------------------\n");
        receipt.append("TOTAL NUMBER OF ITEMS SOLD: ").append(totalItems).append("\n");
        receipt.append(String.format("SUB-TOTAL: $%.2f\n", subtotal));
        receipt.append(String.format("TAX (6.5%%): $%.2f\n", tax));
        receipt.append(String.format("TOTAL: $%.2f\n", total));

        if (customer instanceof Member) {
            receipt.append(String.format("YOU SAVED: $%.2f\n", totalSavings));
        }

        double cash;

        while (true) {
            System.out.print("Enter cash received (or type 0 to cancel the transaction): $");
            String input = scanner.nextLine();

            if (input.trim().equals("0")) {
                System.out.println("checkout cancel");
                return;
            }

            try{
                cash = Double.parseDouble(input);
                if (cash >= total) {
                    break;
                }else{
                    System.out.println("Not enough cash. You need at least $" + String.format("%.2f", total));
                }
            }catch (NumberFormatException e){
                System.out.println("Invalid input. Please enter a number or 0 to cancel");
            }


        }
        double change = cash - total;

        receipt.append(String.format("CASH: $%.2f\n", cash));
        receipt.append(String.format("CHANGE: $%.2f\n", change));

        System.out.println(receipt);
        saveReceiptToFile("receipts/receipt_" + receiptId + ".txt", receipt.toString());

        customer.emptyCart();
    }

    private void saveReceiptToFile(String filePath, String receiptContent) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println(receiptContent);
            System.out.println("Receipt saved to " + filePath);
        } catch (IOException e) {
            System.out.println("Error writing receipt file: " + e.getMessage());
        }
    }

    private int getReceiptNumber(){
        File dir = new File("receipts");
        if(!dir.exists()) {dir.mkdirs();}
        String[] files = dir.list((d, name) -> name.endsWith(".txt"));
        return (files == null) ? 1 : files.length + 1;
    }


}
