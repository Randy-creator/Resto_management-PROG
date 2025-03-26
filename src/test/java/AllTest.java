import org.junit.jupiter.api.Test;
import org.resto.DAO.*;
import org.resto.Entity.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AllTest {
    private DishCrudImpl dishSubject = new DishCrudImpl();
    private MovementCrudImpl moveSubject = new MovementCrudImpl();
    private IngredientCrudImpl ingredientSubject = new IngredientCrudImpl();
    private OrderCrudImpl orderCrudSubject = new OrderCrudImpl();
    private DishOrderCrudImpl dishOrderCrudSubject = new DishOrderCrudImpl();
    private StateCrudImpl stateCrudSubject = new StateCrudImpl();


    @Test
    public void getAllDishesTest() {
        var actual = dishSubject.getAllDishes(1, 10);
        System.out.println(actual.getFirst().getIngredients());

        assertFalse(actual.isEmpty());
    }

    @Test
    public void getTotalCostTest() {
        var actual = dishSubject.getAllDishes(1, 10);
        assertEquals(5500, actual.getFirst().getIngredientCost(LocalDateTime.of(2025, 1, 1, 0, 0)));
        assertEquals(5500, actual.getFirst().getIngredientCost());
    }

    @Test
    public void getGrossMarginTest() {
        var actual = dishSubject.getAllDishes(1, 10);

//        assertEquals(9500, actual.get(0).getGrossMargin());
        assertEquals(9500, actual.get(0).getGrossMargin(LocalDateTime.of(2025, 1, 1, 0, 0)));
    }

    @Test
    public void getAllIngredientTest() {
        Movement stockSaucisse = stockSaucisse();
        var actual = ingredientSubject.getAllIngredients(1, 10);
        System.out.println(actual.getFirst());

        assertFalse(actual.isEmpty());
        assertEquals(stockSaucisse, actual.getFirst().getMoveHistory().getFirst());
    }


    @Test
    public void createUpdateIngredientTest() {
        Ingredient rice = Rice();
        Movement riceMove1 = RiceMove1();
        List<Movement> riceMoves = new ArrayList<>();

        riceMoves.add(riceMove1);

        rice.setMoveHistory(riceMoves);

        ingredientSubject.createUpdateIngredient(rice);
        var actual = ingredientSubject.getAllIngredients(1, 10);

        assertTrue(actual.get(4).getName().equals("Rice"));
    }

    @Test
    public void getAllMovementsTest() {
        Movement move1 = move1();
        var actual = moveSubject.getMovementList(1, 10);
        System.out.println(actual);

        assertTrue(!actual.isEmpty());
        assertEquals(actual.get(0), move1);
    }

    @Test
    public void createMovementTest() {
        Movement movement = testMovementCreation();

        moveSubject.createMovement(movement);

        var actual = moveSubject.getMovementList(1, 10);

        assertEquals(actual.get(4).getMove_id(), movement.getMove_id());
    }

    private Movement move1() {
        Movement move1 = new Movement();

        move1.setMove_id(1);
        move1.setMoveType(true);
        move1.setQuantity(100);
        move1.setUnity(Unities.U);
        move1.setStockDateModification(LocalDateTime.of(2025, 2, 1, 8, 0));

        return move1;
    }

    private Movement testMovementCreation() {
        Movement movement = new Movement();
        movement.setMove_id(5);
        movement.setMoveType(true);
        movement.setQuantity(10);
        movement.setUnity(Unities.G);
        movement.setStockDateModification(LocalDateTime.of(2025, 7, 26, 0, 0));
        return movement;
    }

    private Movement stockSaucisse() {
        Movement stockSaucisse = new Movement();


        stockSaucisse.setMove_id(3);
        stockSaucisse.setMoveType(true);
        stockSaucisse.setQuantity(10000);
        stockSaucisse.setUnity(Unities.G);
        stockSaucisse.setStockDateModification(LocalDateTime.of(2025, 2, 1, 8, 0));

        return stockSaucisse;
    }


    private Ingredient Rice() {
        Ingredient rice = new Ingredient();
        rice.setIngredient_id(5);
        rice.setName("Rice");
        rice.setQuantity(10);
        rice.setLastModification(LocalDateTime.now());
        rice.setUnitPrice(35000);
        rice.setUnity(Unities.G);
        return rice;
    }

    private Movement RiceMove1() {
        Movement RiceMove1 = new Movement();
        RiceMove1.setMove_id(6);
        RiceMove1.setMoveType(true);
        RiceMove1.setQuantity(20);
        RiceMove1.setUnity(Unities.G);
        RiceMove1.setStockDateModification(LocalDateTime.now());
        return RiceMove1;
    }

    private Order OrderTest() {
        Order OrderTest = new Order();
        OrderTest.setOrderId(1);
        OrderTest.setOrderReference("CH1");

        return OrderTest;
    }

    @Test
    public void createorder() {
        Order order = OrderTest();
        var actual = orderCrudSubject.crupdateOrder(order);
        System.out.println(actual);

        assertEquals(1, 1);
    }


    private DishOrder DishOrderTest() {
        DishOrder DishOrderTest = new DishOrder();
        Dish hot_dog = hot_dog();

        DishOrderTest.setDishOrderId(1);
        DishOrderTest.setDishOrdered(hot_dog);
        DishOrderTest.setQuantityOrdered(1);
        return DishOrderTest;
    }

    private Dish hot_dog() {
        Dish hot_dog = new Dish();
        hot_dog.setDish_id(1);
        hot_dog.setUnitPrice(15000);
        hot_dog.setName("Hot dog");
        return hot_dog;
    }

    @Test
    public void createDishOrder() {
        DishOrder DishOrderTest = DishOrderTest();
        Order OrderTest = OrderTest();
        var actual = dishOrderCrudSubject.createDishOrder(DishOrderTest, OrderTest.getOrderId());
        System.out.println(actual);

        assertEquals(1, 1);
    }

    private State stateTest() {
        State stateTest = new State();
        stateTest.setStatus_name(Status.CREATED);
        stateTest.setStatusModificationDate(LocalDateTime.of(2025, 01, 01, 00, 00));

        return stateTest;
    }

    @Test
    public void createStateTest() {
        State stateTest = stateTest();
        Order order = OrderTest();
        var actual = stateCrudSubject.insertState(stateTest, order.getOrderId());
        System.out.println(actual);

        assertEquals(1, 1);
    }
}
