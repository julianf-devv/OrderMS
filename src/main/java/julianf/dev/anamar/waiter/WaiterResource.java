package julianf.dev.anamar.waiter;

import io.swagger.v3.oas.annotations.Hidden;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1")
@Hidden
// Todo: Add Swagger documentation, Add DTO mapping
public class WaiterResource {

  private final WaiterService waiterService;

  @Autowired
  public WaiterResource(WaiterService waiterService) {
    this.waiterService = waiterService;
  }

  @GetMapping("/waiters")
  public List<Waiter> getWaiters() {
    log.info("Getting all getWaiters");
    return waiterService.findAll();
  }
}
