import java.util.List;

// coffee interface
public interface Coffee {
    // gets costs of coffee
    public double getCost();

    // gets ingredients
    public List<String> getIngredients();

    // prints the coffee and ingredients
    public String printCoffee();

}
