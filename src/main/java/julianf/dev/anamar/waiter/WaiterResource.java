package julianf.dev.anamar.waiter;


import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1")
@Hidden
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
