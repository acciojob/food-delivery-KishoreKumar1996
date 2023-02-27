package com.driver.service.impl;

import com.driver.io.entity.OrderEntity;
import com.driver.io.repository.OrderRepository;
import com.driver.model.request.OrderDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.OrderDetailsResponse;
import com.driver.model.response.RequestOperationName;
import com.driver.model.response.RequestOperationStatus;
import com.driver.service.OrderService;
import com.driver.shared.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderRepository orderRepository;
    @Override
    public OrderDto createOrder(OrderDto order) {

        //set the attributes to order entity
        OrderEntity orderEntity =new OrderEntity();
        orderEntity.setOrderId(order.getOrderId());
        orderEntity.setCost(order.getCost());
        orderEntity.setId(order.getId());
        orderEntity.setItems(order.getItems());
        orderEntity.setStatus(order.isStatus());
        orderEntity.setUserId(order.getUserId());
        //save to orderDTo

        order.setId(orderRepository.findByOrderId(order.getOrderId()).getId());
        return order;
    }

    @Override
    public OrderDto getOrderById(String orderId) throws Exception {
        OrderEntity orderEntity=orderRepository.findByOrderId(orderId);
        OrderDto orderDto=new OrderDto();
        orderDto.setUserId(orderEntity.getUserId());
        orderDto.setCost(orderEntity.getCost());
        orderDto.setOrderId(orderEntity.getOrderId());
        orderDto.setItems(orderEntity.getItems());
        orderDto.setStatus(orderEntity.isStatus());
        orderDto.setId(orderEntity.getId());

        return orderDto;
    }

    @Override
    public OrderDto updateOrderDetails(String orderId, OrderDto order) throws Exception {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        orderEntity.setUserId(order.getUserId());
        orderEntity.setItems(order.getItems());
        orderEntity.setCost(order.getCost());


        orderRepository.save(orderEntity);
        order.setId(orderEntity.getId());
        orderEntity.setStatus(orderEntity.isStatus());
        orderEntity.setOrderId(orderEntity.getOrderId());
        return order;
    }

    @Override
    public void deleteOrder(String orderId) throws Exception {
     long Id=orderRepository.findByOrderId(orderId).getId();
     orderRepository.deleteById(Id);
    }

    @Override
    public List<OrderDto> getOrders() {
        List<OrderEntity> orderEntityList=(List<OrderEntity>) orderRepository.findAll();
        List<OrderDto> orderDtoList=new ArrayList<>();
        for(OrderEntity order: orderEntityList){
            OrderDto orderDto = new OrderDto();
            orderDto.setOrderId(order.getOrderId());
            orderDto.setId(order.getId());
            orderDto.setUserId(order.getUserId());
            orderDto.setStatus(order.isStatus());
            orderDto.setItems(order.getItems());
            orderDto.setCost(order.getCost());
            orderDtoList.add(orderDto);
        }
        return orderDtoList;
    }
    public OrderDetailsResponse createOrder(OrderDetailsRequestModel order){

        OrderDto orderDto = new OrderDto();
        orderDto.setItems(order.getItems());
        orderDto.setCost(order.getCost());
        orderDto.setUserId(order.getUserId());

        OrderDto finalOrderDto = createOrder(orderDto);

        //Converting finalOrderDto into OrderDetailsResponse

        OrderDetailsResponse orderDetailsResponse = new OrderDetailsResponse();
        orderDetailsResponse.setOrderId(finalOrderDto.getOrderId());
        orderDetailsResponse.setCost(finalOrderDto.getCost());
        orderDetailsResponse.setItems(finalOrderDto.getItems());
        orderDetailsResponse.setUserId(finalOrderDto.getUserId());
        orderDetailsResponse.setStatus(finalOrderDto.isStatus());

        return orderDetailsResponse;

    }

    public OrderDetailsResponse getOrder(String id) throws Exception {
        OrderDto orderDto = getOrderById(id);

        OrderDetailsResponse orderDetailsResponse = new OrderDetailsResponse();
        orderDetailsResponse.setOrderId(orderDto.getOrderId());
        orderDetailsResponse.setCost(orderDto.getCost());
        orderDetailsResponse.setItems(orderDto.getItems());
        orderDetailsResponse.setUserId(orderDto.getUserId());
        orderDetailsResponse.setStatus(orderDto.isStatus());

        return orderDetailsResponse;
    }

    public OrderDetailsResponse updateOrder(String id, OrderDetailsRequestModel order) throws Exception {

        //We will convert 'order' (OrderDetailsRequestModel) into orderDto here

        OrderDto orderDto = new OrderDto();
        orderDto.setUserId(order.getUserId());
        orderDto.setCost(order.getCost());
        orderDto.setItems(order.getItems());

        OrderDto finalOrderDto = updateOrderDetails(id,orderDto);
        //We will convert this finalOrderDto into OrderDetailsResponse
        OrderDetailsResponse orderDetailsResponse = new OrderDetailsResponse();
        orderDetailsResponse.setOrderId(finalOrderDto.getOrderId());
        orderDetailsResponse.setUserId(finalOrderDto.getUserId());
        orderDetailsResponse.setStatus(finalOrderDto.isStatus());
        orderDetailsResponse.setItems(finalOrderDto.getItems());
        orderDetailsResponse.setCost(finalOrderDto.getCost());

        return orderDetailsResponse;


    }

    public OperationStatusModel delete_Order(String id){
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(RequestOperationName.DELETE.toString());
        try{
            deleteOrder(id);
        } catch (Exception e){
            operationStatusModel.setOperationResult(RequestOperationStatus.ERROR.toString());
            return operationStatusModel;
        }
        operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.toString());
        return operationStatusModel;
    }

    public List<OrderDetailsResponse> get_Orders(){
        List<OrderDto> orderDtoList = getOrders();
        List<OrderDetailsResponse> orderDetailsResponseList = new ArrayList<>();
        for(OrderDto o : orderDtoList){
            orderDetailsResponseList.add(new OrderDetailsResponse(o.getOrderId(),o.getCost(),
                    o.getItems(),o.getUserId(),o.isStatus()));
        }
        return orderDetailsResponseList;
    }
}