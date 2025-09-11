# Jerry's Quick Mart

A **Java console application** that simulates a supermarket with the following features:
 - Inventory management with the ability to read .txt documents and upload them as inventory.
- Regular and member customers.
- Shopping cart system (adding products to the cart and removing them, and view the cart).
- Checkout process with receipt generation on.txt(this receipts are going to be generated on a folder called receipts).
- Automatic inventory updates after each purchase.

## Assumptions made

- The inventory is managed through a .txt file, which acts as a lightweight database. Each checkout automatically updates the product quantities, and items are removed once their stock reaches zero.
- The program includes a menu system for easy navigation. There are two menus: a main menu and a store menu. Every transaction is tied to a specific customer, so after completing a checkout, the program returns to the main menu to start a new transaction.
- The inventory system accepts any .txt file with the correct format. For testing purposes, a sample file is provided at src/inventory.txt.
- The inventory file is provided to the system when the first menu option is selected. This design allows flexibility in case multiple inventories exist and a transaction needs to use a different one.
- Since there’s no dedicated database for customer information, the program asks for the customer’s name and whether they are a member at the start of each transaction.
- Receipts include details of all purchases. Only members receive an additional line showing savings, as they have access to discounted prices.


## Requirements

- **Java JDK 17+**  
  (On macOS it usually comes pre-installed. On Linux/macOS check with `java -version`)
if it's not install used this command

### For LINUX
```bash
sudo apt install openjdk-17-jdk-headless --fix-missing
```
### For MAC
1. install HomeBrew
```bash
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

2. install JDK 17
```bash
brew install openjdk@17
```

3. Vinculation
```bash
sudo ln -sfn /opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-17.jdk
```

4. Environment variables configuration 
```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
export PATH=$JAVA_HOME/bin:$PATH
```

its no necesary any IDE for JAVA but the jdk is necessary

## How to Compile and Run

1 Clone this repository

2 Open a terminal inside the project

3 Compile the java files with the next line
```bash
find src -name "*.java" -print | xargs javac
```
4 Run the java program with the next line
```bash
java -cp src Main
```

## Creator

Sebastian Cobos
