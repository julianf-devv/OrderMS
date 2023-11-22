package julianf.dev.anamar.table;

import io.swagger.v3.oas.annotations.Hidden;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1")
@Hidden
public class TableResource {

  @Autowired private final TableService tableService;

  public TableResource(TableService tableService) {
    this.tableService = tableService;
  }

  @GetMapping("/tables")
  public List<RestaurantTable> getTables() {
    return tableService.findAll();
  }

  @GetMapping("/tables/{id}")
  public RestaurantTable getSingleTable(@PathVariable Short id) {
    return tableService.getTableById(id);
  }
}
