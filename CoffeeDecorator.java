import java.util.List;

public abstract class CoffeeDecorator implements Coffee {
    private Coffee coffee;

    // constructor
    public CoffeeDecorator(Coffee c) {
        coffee = c;
    }

    public double getCost() {
        return coffee.getCost();
    }

    // returns the ingredients
    public List<String> getIngredients() {
        return coffee.getIngredients();
    }

    public String printCoffee() {
        return coffee.printCoffee();
    }
}
