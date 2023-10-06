package shopbae.food.service.orderDetail;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shopbae.food.model.OrderDetail;
import shopbae.food.repository.orderDetail.IOrderDetailRepository;

@Service
public class OrderDetailService implements IOrderDetailService {
	@Autowired
	IOrderDetailRepository orderDetailRepository;

	@Override
	public OrderDetail findById(Long id) {
		return orderDetailRepository.findById(id);
	}

	@Override
	public void save(OrderDetail t) {
		orderDetailRepository.save(t);
	}

	@Override
	public void update(OrderDetail t) {
		orderDetailRepository.update(t);
	}

	@Override
	public void delete(OrderDetail t) {
		orderDetailRepository.delete(t);
	}

	@Override
	public List<OrderDetail> findAll() {
		return orderDetailRepository.findAll();
	}

	@Override
	public List<OrderDetail> findByOrder(Long id) {
		return orderDetailRepository.findByOrder(id);
	}

}
