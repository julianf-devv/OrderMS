package julianf.dev.anamar.order;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
  String ORDER_FINISHED = "orderFinished";

  List<Orders> findAllByOrderFinishedFalse();
}
