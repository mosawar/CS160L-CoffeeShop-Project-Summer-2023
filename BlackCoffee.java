import java.util.ArrayList;
import java.util.List;


public class BlackCoffee implements Coffee {
    // implements coffee class and overrides methods based on class
    @Override
    public double getCost() {
        return 1.0;
    }

    public List<String> getIngredients() {
        List<String> ingredients = new ArrayList<>();
        ingredients.add("Black Coffee");
        return ingredients;
    }

    @Override
    public String printCoffee() {
        return "A black coffee";
    }
}
