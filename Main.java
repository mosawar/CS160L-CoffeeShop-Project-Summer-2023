import java.io.*;
import java.util.*;

public class Main {
    protected static Map<String, Integer> inventory = new TreeMap<String, Integer>();
    protected static List<CoffeeOrder> orders = new ArrayList<CoffeeOrder>();
    protected static String logFile = "OrderLog.txt";
    protected static String inventoryFile = "Inventory.txt";

    //checks if ingredient is in the inventory
    private static boolean isInInventory(String ingredient) {
        if (inventory.containsKey(ingredient)) {
            int quantity = inventory.get(ingredient);
            return quantity > 0;
        }
        return false;
    }

    //  main method
    public static void main(String[] args) {
        System.out.println("Welcome to Java Coffee Co.!");
        try (Scanner input = new Scanner(System.in)) {
            // loop variable
            boolean exit = false;
            // loops until exit variable of type boolean is switched to true
            while (!exit) {
                // start menu options
                System.out.println("\nMenu Options:");
                System.out.println("1. New Order");
                System.out.println("2. Reload Inventory");
                System.out.println("3. Update Inventory");
                System.out.println("4. Update Order Log");
                System.out.println("5. Exit Application");

                int option = 0;

                // while loop makes sure entered value is within range
                while (option < 1 || option > 5) {
                    if (!input.hasNextInt()) {
                        System.out.println("Please enter a valid number.");
                        input.nextLine();
                    } else {
                        option = input.nextInt();
                        if (option < 1 || option > 5) {
                            System.out.println("Please enter a valid option.");
                        }
                    }
                }
                input.nextLine();

                // switch case used to call methods to perform those tasks requested by user
                switch (option) {
                    case 1: // New Order
                        CoffeeOrder order = buildOrder();
                        orders.add(order);
                        System.out.println(order.printOrder());
                        break;
                    case 2: // Reload Inventory
                        inventory = readInventory(inventoryFile);
                        System.out.println("Current Inventory:");
                        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
                            System.out.println(entry.getKey() + ": " + entry.getValue());
                        }
                        break;
                    case 3: // Update Inventory
                        writeInventory(inventoryFile);
                        break;
                    case 4: // Update Order Log
                        writeOrderLog(logFile);
                        break;
                    case 5: // Exit Application
                        writeInventory(inventoryFile);
                        writeOrderLog(logFile);
                        exit = true;
                        break;
                }
            }
            // catches exception and prints error message
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        if (!orders.isEmpty()) {
            writeOrderLog(logFile);
        }
    }

    // build order method used to create coffee
    protected static CoffeeOrder buildOrder() {
        CoffeeOrder order = new CoffeeOrder();

        // try n catch statement used to make sure user input is within range
        try {
            Scanner input = new Scanner(System.in);
            boolean addCoffee = true;
            while (addCoffee) {
                // prompt user to select base coffee type
                System.out.println("Select coffee type:");
                System.out.println("\t1. Black Coffee");
                System.out.println("\t2. Espresso");
                Coffee coffee;

                // makes sure user types in a number that's in range
                int option = 0;
                while (option < 1 || option > 2) {
                    if (!input.hasNextInt()) {
                        System.out.println("Please enter a valid number.");
                        input.nextLine();
                    } else {
                        option = input.nextInt();
                        if (option < 1 || option > 2) System.out.println("Please enter a valid option.");
                    }
                }
                input.nextLine();
                if (option == 2) {
                    
                    coffee = new Espresso();
                } else {

                    coffee = new BlackCoffee();
                }

                // prompt user for any customizations
                while (option != 0) {
                    System.out.println(String.format("Coffee brewing: %s.", coffee.printCoffee()));
                    System.out.println("Would you like to add anything to your coffee?");
                    System.out.println("\t1. Flavored Syrup");
                    System.out.println("\t2. Hot Water");
                    System.out.println("\t3. Milk");
                    System.out.println("\t4. Sugar");
                    System.out.println("\t5. Whipped Cream");
                    System.out.println("\t0. NO - Finish Coffee");

                    // if user input isnt a num asks them to input a valid number
                    while (!input.hasNextInt()) {
                        System.out.println("Please enter a valid number.");
                        input.nextLine();
                    }
                    option = input.nextInt();
                    input.nextLine();
                    // switch statement to call certain class
                    coffee = switch (option) {
                        case 1 -> {
                            System.out.println("Please select a flavor:");
                            for (WithFlavor.Syrup flavor : WithFlavor.Syrup.values()) {
                                System.out.println("\t" + String.format("%d. %s", flavor.ordinal() + 1, flavor));
                            }
                            int max = WithFlavor.Syrup.values().length;
                            option = 0;
                            while (option < 1 || option > max) {
                                if (!input.hasNextInt()) {
                                    System.out.println("Please enter a valid number.");
                                    input.nextLine();
                                } else {
                                    option = input.nextInt();
                                    if (option < 1 || option > max) System.out.println("Please enter a valid option.");
                                }
                            }
                            input.nextLine();
                            WithFlavor.Syrup flavor = WithFlavor.Syrup.values()[option - 1];
                            option = 1;
                            yield new WithFlavor(coffee, flavor);
                        }
                        case 2 -> new WithHotWater(coffee);
                        case 3 -> new WithMilk(coffee);
                        case 4 -> new WithSugar(coffee);
                        case 5 -> new WithWhippedCream(coffee);
                        default -> {
                            if (option != 0) System.out.println("Please enter valid option.");
                            yield coffee;
                        }
                    };
                }
                order.addCoffee(coffee);

                // prompts user to enter another number
                System.out.println("Would you like to order another coffee (Y or N)?");
                String yn = input.nextLine();

                // checks for user input while ignoring case
                while (!(yn.equalsIgnoreCase("N") || yn.equalsIgnoreCase("Y"))) {
                    System.out.println("Please enter Y or N.");
                    yn = input.nextLine();
                }
                addCoffee = !yn.equalsIgnoreCase("N");
            }
        } catch (Exception e) {
            System.out.println("Error building order: " + e.getMessage());
        }
        return order;
    }

    // reads inventory using buffered reader
    protected static Map<String, Integer> readInventory(String filePath) {
        Map<String, Integer> inventory = new TreeMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" = ");
                if (parts.length == 2) {
                    String ingredient = parts[0];
                    int quantity = Integer.parseInt(parts[1]);
                    inventory.put(ingredient, quantity);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading inventory: " + e.getMessage());
        }

        return inventory;
    }

    // writes to the filepath that updates the ingredients quantity
    protected static void writeInventory(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            Map<String, Integer> updatedInventory = new TreeMap<>();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" = ");
                if (parts.length == 2) {
                    String ingredient = parts[0];
                    int quantity = Integer.parseInt(parts[1]);
                    updatedInventory.put(ingredient, quantity);
                }
            }

            // uses enhanced for loop to check if ingredient is available
            for (CoffeeOrder order : orders) {
                List<Coffee> coffees = order.getCoffees();
                for (Coffee coffee : coffees) {
                    List<String> ingredients = coffee.getIngredients();
                    for (String ingredient : ingredients) {
                        if (isInInventory(ingredient)) {
                            int quantity = updatedInventory.getOrDefault(ingredient, 0);
                            updatedInventory.put(ingredient, quantity - 1);
                        } else {
                            System.out.println("The ingredient " + ingredient + " is not available in the inventory.");
                        }
                    }
                }
            }

            // writes to the filepath updating quantity
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (Map.Entry<String, Integer> entry : updatedInventory.entrySet()) {
                    String ingredient = entry.getKey();
                    int quantity = entry.getValue();
                    writer.write(ingredient + " = " + quantity);
                    writer.newLine();
                }
            }

            System.out.println("Inventory updated successfully.");
        } catch (IOException e) {
            System.out.println("Error updating inventory: " + e.getMessage());
        }
    }

    // writes the order log to the filepath using try and catch block
    protected static void writeOrderLog(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            for (CoffeeOrder order : orders) {
                writer.write(order.printOrder());
                writer.newLine();
            }
            orders.clear();
        } catch (Exception e) {
            System.out.println("Error writing order log: " + e.getMessage());
        }
    }

}