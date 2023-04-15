package julianf.dev.anamar.waiter.dto;

import julianf.dev.anamar.waiter.Waiter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public record WaiterDTO(Long id, String name, String lastName, String email) {

    public WaiterDTO(Waiter waiter) {
        this(waiter.getId(), waiter.getName(), waiter.getLastName(), waiter.getEmail());
    }
    public WaiterDTO {
        name = name.toUpperCase();
        log.info("WaiterDTO: " + name);
    }
}
