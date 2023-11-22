package julianf.dev.anamar;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.transaction.Transactional;
import java.util.Set;
import julianf.dev.anamar.item.Item;
import julianf.dev.anamar.order.OrderRepository;
import julianf.dev.anamar.order.Orders;
import julianf.dev.anamar.product.Product;
import julianf.dev.anamar.product.ProductRepository;
import julianf.dev.anamar.product.ProductType;
import julianf.dev.anamar.table.RestaurantTable;
import julianf.dev.anamar.table.TableRepository;
import julianf.dev.anamar.waiter.Waiter;
import julianf.dev.anamar.waiter.WaiterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
@OpenAPIDefinition(
    info = @Info(title = "Ana mar API", version = "0.2"),
    servers = {
      @Server(url = "http://localhost:8080", description = "Servidor local"),
      @Server(url = "https://dev.example.com", description = "Servidor de desarrollo"),
      @Server(url = "https://nonprod.example.com", description = "Servidor de producción")
    })
@SecurityScheme(type = SecuritySchemeType.HTTP, scheme = "basic", name = "basicAuth")
public class AnaMarApplication implements CommandLineRunner {
  private final TableRepository tableRepository;
  private final OrderRepository orderRepository;
  private final WaiterRepository waiterRepository;
  private final ProductRepository productRepository;

  @Autowired
  public AnaMarApplication(
      OrderRepository orderRepository,
      WaiterRepository waiterRepository,
      ProductRepository productRepository,
      TableRepository tableRepository) {
    this.orderRepository = orderRepository;
    this.waiterRepository = waiterRepository;
    this.productRepository = productRepository;
    this.tableRepository = tableRepository;
  }

  public static void main(String[] args) {
    SpringApplication.run(AnaMarApplication.class, args);
    Orders order = new Orders();
    order.setTotal(0.0);
  }

  @Override
  @Transactional
  public void run(String... args) {
    var cocaCola = new Product();
    cocaCola.setName("Coca Cola");
    cocaCola.setDescription("Refresco de cola");
    cocaCola.setUnitPrice(1.5);
    var item = new Item();
    item.setProduct((cocaCola));
    item.setAmount(2);
    var sprite = new Product();
    sprite.setName("Sprite");
    sprite.setDescription("Refresco de lima-limón");
    sprite.setUnitPrice(1.75);
    sprite.setStock(10);

    var fanta = new Product();
    fanta.setName("Fanta");
    fanta.setDescription("Refresco de naranja");
    fanta.setUnitPrice(1.6);
    fanta.setStock(15);

    var pepsi = new Product();
    pepsi.setName("Pepsi");
    pepsi.setDescription("Refresco de cola");
    pepsi.setUnitPrice(1.8);
    pepsi.setStock(5);

    var consome = new Product();
    consome.setName("Consome");
    consome.setDescription("Consome de pollo");
    consome.setUnitPrice(1.8);
    consome.setType(ProductType.COMIDA);
    consome.setStock(5);

    var item1 = new Item();
    item1.setProduct(cocaCola);
    item1.setAmount(2);

    var item2 = new Item();
    item2.setProduct(sprite);
    item2.setAmount(3);

    var item3 = new Item();
    item3.setProduct(fanta);
    item3.setAmount(1);

    var item4 = new Item();
    item4.setProduct(pepsi);
    item4.setAmount(4);

    var item5 = new Item();
    item5.setProduct(sprite);
    item5.setAmount(1);

    var order = new Orders();

    var table = new RestaurantTable();
    table.setCapacity((short) 4);
    table.setOccupied(true);
    var waiter = new Waiter();
    waiter.setName("Julian");
    waiter.setEmail("Fernandez");
    waiter.setEmail("eljulian2070@gmail.com");

    table.setAssignedWaiter(waiter);
    order.setAssociatedTable(table);
    tableRepository.save(table);
    waiterRepository.save(waiter);
    order.setOrderFinished(false);
    order.setCustomerName("Julian");
    order.setItems(Set.of(item1, item2, item3, item4));
    pepsi.setType(ProductType.BEBIDA);
    productRepository.save(consome);
    orderRepository.save(order);
    var order23 = orderRepository.findById(1L);
    log.info("order: {}", order23);
    orderRepository.deleteById(2L);
    log.info("order after delete {}", orderRepository.findById(1L));
  }
}
