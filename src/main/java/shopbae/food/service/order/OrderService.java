package shopbae.food.service.order;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shopbae.food.model.Order;
import shopbae.food.repository.order.IOrderRepository;
import shopbae.food.service.merchant.IMerchantService;

@Service
public class OrderService implements IOrderService {
    @Autowired
    IOrderRepository orderRepository;
//    @Autowired
//    private MessageSource messageSource;
//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private IMerchantService merchantService;

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public void save(Order t) {
        orderRepository.save(t);
    }

    @Override
    public void update(Order t) {
        orderRepository.update(t);
    }

    @Override
    public void delete(Order t) {
        orderRepository.delete(t);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> findByAppUserAndMer(Long userId) {
        return orderRepository.findByAppUserAndMer(userId);
    }

    @Override
    public List<Order> findByFlagAndStatus(String status) {
        return orderRepository.findByFlagAndStatus(status);
    }

    @Override
    public Serializable savee(Order t) {
        return orderRepository.savee(t);
    }

    @Override
    public void send(Order t, Long userId) {
//        OrderStatusDTO dto = new OrderStatusDTO();
//        dto.setId(t.getId());
//        String status = messageSource.getMessage(t.getStatus(), null, LocaleContextHolder.getLocale());
//        dto.setStatus(status);
//        Merchant merchant = merchantService.findById(t.getMerchant_id());
//        dto.setMerchant(merchant.getName());
//        messagingTemplate.convertAndSend("/topic/order/" + userId, dto);
    }

}
