# shoppingcart
It is simple demonstration of how different discounts model can be applied on retail store cart system. Business usually offers various discount policies to lure their customer and increase sale.

Here, Chain of responsibility design patternis used to implement discount model. Chain of responsibilty allows loose coupling between different models and provide ease to add or remove discount models.

## Project Run
Using maven, build jar. Run the jar, it will execute main class: org.techntravels.cart.Runner

> **~$ mvn clean install**

> **~$ java -jar target/ShoppingCart-0.0.1.jar**

## Test Run
It has plugin to generate test report and coverage report

- Run test cases
  - **~$ mvn clean verify**
- Check test report
  - target/site/surefire-report.html
- Check coverage report
  - target/site/jacoco/index.html


**Domains**

![Domain UML](https://github.com/aman-jft/shoppingcart/blob/images/domain.gif)

**Discount Model**


![Discount UML](https://github.com/aman-jft/shoppingcart/blob/images/discount.gif)
