package com.uece.coffeebreak.config;

import com.uece.coffeebreak.entity.*;
import com.uece.coffeebreak.entity.enums.*;
import com.uece.coffeebreak.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CompProductStockRepository compProductStockRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Category c1 = new Category(null, "Expresso", "Café preto forte de 50ml", "img/cafe.jpeg", Instant.parse("2019-06-20T19:53:07Z"));
        Category c2 = new Category(null, "Mocha", null, null, Instant.parse("2019-06-20T19:53:07Z"));

        Stock s1 = new Stock(null, StockType.DRY, 150, 26.0, "Muito seco");
        Stock s2 = new Stock(null, StockType.REFRIGERATOR, 30, null, null);

        Ingredient i1 = new Ingredient(null, "Banana", IngredientType.PERISHABLE, "kg", "Bananeiro LTDA", 5);
        Ingredient i2 = new Ingredient(null, "Chocolate", IngredientType.POWDERED, null, null, 20);

        Product p1 = new Product(null, "Triforce Trio", 23.90, Available.AVAILABLE, "img/teste", 30);
        Product p2 = new Product(null, "Expresso do amanha", 23.90, Available.AVAILABLE, null, null);

        categoryRepository.saveAll(Arrays.asList(c1, c2));
        productRepository.saveAll(Arrays.asList(p1, p2));

        p1.setCategory(c2);
        p2.setCategory(c1);

        productRepository.saveAll(Arrays.asList(p1, p2));
        stockRepository.saveAll(Arrays.asList(s1, s2));
        ingredientRepository.saveAll(Arrays.asList(i1, i2));

        User u1 = new User(null, "victor", "victor@gmail.com", passwordEncoder.encode("12345678"), "85994260736", Role.CLIENT, "Brasil");
        User u2 = new User(null, "jose", "jose@gmail.com", passwordEncoder.encode("senha12345"), "85994260736", Role.ADMIN, "Brasil");
        User u3 = new User(null, "carlos", "carlos@gmail.com", passwordEncoder.encode("senhasenha"), "85994260736", Role.CLIENT, "Brasil");

        Order o1 = new Order(null, Instant.parse("2019-06-20T19:53:07Z"), OrderStatus.PAID, WithdrawalMethod.DELIVERY, u1);
        Order o2 = new Order(null, Instant.parse("2019-07-21T03:42:10Z"),OrderStatus.DELIVERED, WithdrawalMethod.PICKUP, u2);
        Order o3 = new Order(null, Instant.parse("2019-07-22T15:21:22Z"), OrderStatus.PREPARING, WithdrawalMethod.DELIVERY, u3);

        userRepository.saveAll(Arrays.asList(u1,u2,u3));
        orderRepository.saveAll(Arrays.asList(o1,o2,o3));

        OrderProduct op1 = new OrderProduct(o1, p1, 2, p1.getPrice(), 10.0, "Leite desnatado");
        OrderProduct op2 = new OrderProduct(o1, p2, 3, p2.getPrice(), null, null);
        OrderProduct op3 = new OrderProduct(o2, p1, 1, p1.getPrice(), 15.50, null);
        OrderProduct op4 = new OrderProduct(o3, p2, 4, p2.getPrice(), null, "");

        CompProductStock comp1 = new CompProductStock(p1, s2, i1, 1, true, "Base", false);
        CompProductStock comp2 = new CompProductStock(p1, s1, i2, 5, true, "Cobertura", null);
        CompProductStock comp3 = new CompProductStock(p2, s1, i1, 1, false, null, false);

        orderProductRepository.saveAll(Arrays.asList(op1,op2,op3,op4));
        compProductStockRepository.saveAll(Arrays.asList(comp1,comp2, comp3));

        Payment pay1 = new Payment(null, Instant.parse("2025-01-30T10:24:45Z"), o1, PayMethod.PIX, null);
        Payment pay2 = new Payment(null, Instant.parse("2025-01-30T10:24:45Z"), o3, PayMethod.CREDIT, 3);
        o1.setPayment(pay1);
        o3.setPayment(pay2);

        orderRepository.save(o1);
    }
}
