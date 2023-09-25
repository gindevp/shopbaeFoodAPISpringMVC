package shopbae.food.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shopbae.food.model.Product;
import shopbae.food.repository.product.IProductRepository;

@Service
public class ProductService implements IProductService {
    @Autowired
    private IProductRepository productRepository;

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public void save(Product t) {
        productRepository.save(t);
    }

    @Override
    public void update(Product t) {
        productRepository.update(t);
    }

    @Override
    public void delete(Product t) {
        productRepository.delete(t);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getAllByDeleteFlagTrueAndMerchant(Long id) {
        return productRepository.getAllByDeleteFlagTrueAndMerchant(id);
    }

    @Override
    public List<Product> fAllByDeleFlagTAndMerAndNameContai(Long id, String name) {
        return productRepository.fAllByDeleFlagTAndMerAndNameContai(id, name);
    }

}
