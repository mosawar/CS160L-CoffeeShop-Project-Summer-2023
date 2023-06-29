import java.util.ArrayList;
import java.util.List;

public class WithFlavor extends CoffeeDecorator {

    // uses e num for the flavors of the syrup
    enum Syrup {
        CARAMEL,
        MOCHA,
        VANILLA
    }

    private Syrup flavor;

    public WithFlavor(Coffee c, Syrup s) {
        super(c);
        flavor = s;
    }

    @Override
    public double getCost() {
        return super.getCost() + 0.35;
    }

    @Override
    public List<String> getIngredients() {
        List<String> ingredients = new ArrayList<>(super.getIngredients());
        ingredients.add(flavor + " Syrup");
        return ingredients;
    }

    // returns coffee with the flavor from enum
    @Override
    public String printCoffee() {
        return super.printCoffee() + " with " + flavor;
    }
}
