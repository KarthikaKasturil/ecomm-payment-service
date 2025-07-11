package com.secor.ecommpaymentservice;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class PaymentService
{
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);


    @Autowired
    @Qualifier("productWebClient")
    WebClient productWebClient;

    @Autowired
    @Qualifier("inventoryWebClient")
    WebClient inventoryWebClient;

    @Autowired
    @Qualifier("orderWebClient")
    WebClient orderWebClient;

    @Autowired
    @Qualifier("customerWebClient")
    WebClient customerWebClient;

    public Order getOrderDetails(String orderId) throws WebClientResponseException
    {
        logger.info("Call order service for details");

        return orderWebClient.get()
                .uri("/" + orderId)
                .retrieve()
                .bodyToMono(Order.class)
                .block(); // Assuming the product details are valid for demonstration purposes
    }

    public InventoryItem checkInventory(String productId) throws WebClientResponseException
    {
        logger.info("Call check inventory service");

        return inventoryWebClient.get()
                .uri("/product/" + productId)
                .retrieve()
                .bodyToMono(InventoryItem.class)
                .block(); // Assuming the inventory check is valid for demonstration purposes
    }

    public InventoryItem updateInventory(InventoryItem itemDetails) throws WebClientResponseException
    {
        logger.info("Call update inventory service");

        return inventoryWebClient.put()
                .uri("/update/product")
                .bodyValue(itemDetails)
                .retrieve()
                .bodyToMono(InventoryItem.class)
                .block();// Assuming the inventory check is valid for demonstration purposes
    }

    public ResponseEntity<String> updateOrderStatus(OrderStatus status) throws WebClientResponseException
    {
        logger.info("Call update status of order");

        return inventoryWebClient.put()
                .uri("/update/status")
                .bodyValue(status)
                .retrieve()
                .toEntity(String.class)
                .block();// Assuming the inventory check is valid for demonstration purposes
    }

    public ResponseEntity<String> updateCustomerOrder(OrderInfo info) throws WebClientResponseException
    {
        logger.info("Call update order for customer");

        return customerWebClient.put()
                .uri("/update/order")
                .bodyValue(info)
                .retrieve()
                .toEntity(String.class)
                .block();// Assuming the inventory check is valid for demonstration purposes
    }

}
