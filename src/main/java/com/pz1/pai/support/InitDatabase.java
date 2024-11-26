package com.pz1.pai.support;

import com.pz1.pai.client.domain.Client;
import com.pz1.pai.client.service.ClientService;
import com.pz1.pai.order.domain.Order;
import com.pz1.pai.order.service.OrderService;
import com.pz1.pai.vehicle.dto.VehicleRequestDTO;
import com.pz1.pai.vehicle.service.VehicleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Service
//@Profile({"local"})
public class InitDatabase {

    private final VehicleService vehicleService;
    private final ClientService clientService;
    private final OrderService orderService;


//    @EventListener
//    @Order(Ordered.LOWEST_PRECEDENCE)
    public void onStartup(ApplicationReadyEvent event) {
        initVehicles();
        initClients();
        initOrders();

        BatchFactory batchFactory = new BatchFactory();
        orderService.saveOrUpdateBatches(1L, batchFactory.getBatches());

        log.info("INITIALIZED DATABASE WITH DUMMY ENTITIES");
    }

    private void initVehicles() {
        VehicleFactory vehicleFactory = new VehicleFactory();
        List<VehicleRequestDTO> vehicles = vehicleFactory.getVehicles();
        vehicles.forEach(vehicle -> vehicleService.saveVehicle(vehicle));
    }

    private void initClients(){
        ClientFactory clientFactory = new ClientFactory();
        List<Client> clients = clientFactory.getClients();
        clients.forEach(client -> clientService.saveClient(client));
    }
    private void initOrders(){
        OrderFactory orderFactory = new OrderFactory();
        List<Order> orders =  orderFactory.getOrders();
        Random random = new Random();
        for (Order order : orders) {
            order.setClient(clientService.getClient(random.nextLong(1, 6)));
        }
        orders.forEach(order -> orderService.saveOrder(order));
    }
}
