package julianf.dev.anamar;

import julianf.dev.anamar.order.Orders;
import julianf.dev.anamar.order.OrderRepository;
import julianf.dev.anamar.item.Item;
import julianf.dev.anamar.item.ItemRepository;
import julianf.dev.anamar.product.Product;
import julianf.dev.anamar.product.ProductRepository;
import julianf.dev.anamar.waiter.WaiterRepository;
import julianf.dev.anamar.waiter.Waiter;
import julianf.dev.anamar.table.RestaurantTable;
import julianf.dev.anamar.waiter.dto.WaiterDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
@Slf4j
public class AnaMarApplication implements CommandLineRunner {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private WaiterRepository waiterRepository;
    @Autowired
    private ProductRepository productRepository;

    public static void main(String[] args) {
        SpringApplication.run(AnaMarApplication.class, args);

    }


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // TODO Move to a data.sql file
        var cocaCola = new Product();
        cocaCola.setName("Coca Cola");
        cocaCola.setDescription("Refresco de cola");
        cocaCola.setUnitPrice(1.5);
        var item = new Item();
        item.setProduct(cocaCola);
        item.setAmount(2);
        var order = new Orders();
        order.setOrderFinished(false);
        order.setItems(List.of(item));
        orderRepository.save(order);

        WaiterDTO waiterDTO = new WaiterDTO(2L, "Hector", "Fernandez", "julianf");
        log.info("waiter: {}", waiterDTO);
        log.info("order: {}", orderRepository.findById(1L).orElseThrow());

    }
}
