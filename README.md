# CS160L-CoffeeShop-Project-Summer-2023

Instructions

Run class called Main Method

Follow the directions given in the IDE terminal

Enter an integer to select from the menu

after you are done creating your order select an option from the menu to perform executive actions

such as display the order log.

You are also able to check the ingredients.txt files data by calling read.

You are also able to manually update the inventory levels after each other by using the proper input

or you can simply exit the program and the inventory and order log will automatically update.

Step by Step Instructions:

1. run program
2. select new order from menu
3. pick a base for your coffee
4. select from CoffeeDecorators provided or choose no
5. add more coffee if needed or complete order
6. get receipt
7. exit and complete order or try any of the other options presented in the menu

Class Explanations

BlackCoffee class implement Coffee interface which overrides and returns the attributes of BlackCoffee

Coffee interface returns the cost, ingredients, and coffee.

CoffeeDecorator implements coffee interface.

CoffeeOrder is used to write to the filepath the order log

Espresso similar to BlackCoffee class implements Coffee interface and does the same thing but with different pricing and printCoffee name

WithFlavor class extends coffeeDecorator, it uses enum to store the flavors

WithHotWater adds water the coffee and extends coffeeDecorator

WithMilk adds milk to the ingredients list, adds the price and adds milk to print

WithSugar adds sugar to the list of ingredients so it can be subtracted from the inventory.txt file, returns pricing, and adds sugar to the print

withWhippedCream extends coffeeDecorator, returns price, ingredients and adds the ingredient to the print
