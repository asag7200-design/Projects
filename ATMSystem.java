import java.util.Scanner;

public class ATMSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Account account = new Account("Sagar ", "181772", 650000.0);
        ATM atm = new ATM(account);

        System.out.println("Welcome to the Simple ATM Simulator!");

        while (true) {
            System.out.print("Enter your PIN: ");
            String pin = scanner.nextLine();

            if (atm.login(pin)) {
                System.out.println("Login successful.\n");
                break;
            } else {
                System.out.println("Invalid PIN. Please try again.\n");
            }
        }

        boolean running = true;
        while (running) {
            System.out.println("ATM Menu:");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Exit");
            System.out.print("Choose an option (1-4): ");

            String choice = scanner.nextLine();
            System.out.println();

            switch (choice) {
                case "1":
                    System.out.printf("Your current balance is: $%.2f\n\n", atm.checkBalance());
                    break;
                case "2":
                    System.out.print("Enter amount to deposit: $");
                    double depositAmount = readPositiveAmount(scanner);
                    if (depositAmount > 0) {
                        atm.deposit(depositAmount);
                        System.out.printf("Deposit successful. New balance: $%.2f\n\n", atm.checkBalance());
                    } else {
                        System.out.println("Invalid amount. Deposit failed.\n");
                    }
                    break;
                case "3":
                    System.out.print("Enter amount to withdraw: $");
                    double withdrawAmount = readPositiveAmount(scanner);
                    if (withdrawAmount > 0) {
                        if (atm.withdraw(withdrawAmount)) {
                            System.out.printf("Withdrawal successful. New balance: $%.2f\n\n", atm.checkBalance());
                        } else {
                            System.out.println("Withdrawal failed. Insufficient balance or amount exceeds limit.\n");
                        }
                    } else {
                        System.out.println("Invalid amount. Withdrawal failed.\n");
                    }
                    break;
                case "4":
                    System.out.println("Thank you for using the ATM Simulator. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose a number from 1 to 4.\n");
            }
        }

        scanner.close();
    }

    private static double readPositiveAmount(Scanner scanner) {
        try {
            double amount = Double.parseDouble(scanner.nextLine());
            if (amount > 0) {
                return amount;
            }
        } catch (NumberFormatException e) {

        }
        return -1;
    }
}

class Account {
    private String ownerName;
    private String pin;
    private double balance;

    public Account(String ownerName, String pin, double balance) {
        this.ownerName = ownerName;
        this.pin = pin;
        this.balance = balance;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
}

class ATM {
    private Account account;
    private final double withdrawalLimit = 50000.0;

    public ATM(Account account) {
        this.account = account;
    }

    public boolean login(String pin) {
        return account.getPin().equals(pin);
    }

    public double checkBalance() {
        return account.getBalance();
    }

    public void deposit(double amount) {
        account.deposit(amount);
    }

    public boolean withdraw(double amount) {
        if (amount > withdrawalLimit) {
            System.out.printf("Amount exceeds the withdrawal limit of $%.2f.\n", withdrawalLimit);
            return false;
        }
        return account.withdraw(amount);
    }
}