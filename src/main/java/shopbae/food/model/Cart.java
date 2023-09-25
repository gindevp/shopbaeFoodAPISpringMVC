package shopbae.food.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int quantity;
    private  Double price;
    private Double totalPrice;
    @Column(name = "isFavorite")
    private boolean isFavorite=true;
    @Column(name = "deleteFlag")
    private boolean deleteFlag=true;

    @ManyToOne
    @JoinColumn(name = "user_id")
//    @JsonBackReference
    private AppUser user;

    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;




	@Override
	public String toString() {
		return "Cart [id=" + id + ", quantity=" + quantity + ", price=" + price + ", totalPrice=" + totalPrice
				+ ", isFavorite=" + isFavorite + ", deleteFlag=" + deleteFlag + ", user=" + user + ", product="
				+ product + "]";
	}

	public Cart() {

	}

	public Cart(Long id, int quantity, Double price, Double totalPrice, boolean isFavorite, boolean deleteFlag,
			AppUser user, Product product) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.price = price;
		this.totalPrice = totalPrice;
		this.isFavorite = isFavorite;
		this.deleteFlag = deleteFlag;
		this.user = user;
		this.product = product;
	}

	public Cart(Long id, int quantity, Double price, Double totalPrice, boolean deleteFlag, AppUser user,
			Product product) {
		this.id = id;
		this.quantity = quantity;
		this.price = price;
		this.totalPrice = totalPrice;
		this.deleteFlag = deleteFlag;
		this.user = user;
		this.product = product;
	}

	public Cart(int quantity, Double price, Double totalPrice, boolean deleteFlag, AppUser user, Product product) {
	
		this.quantity = quantity;
		this.price = price;
		this.totalPrice = totalPrice;
		this.deleteFlag = deleteFlag;
		this.user = user;
		this.product = product;
	}

	public boolean isDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getTotalPrice() {
		return price*quantity;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public boolean isFavorite() {
		return isFavorite;
	}

	public void setFavorite(boolean isFavorite) {
		this.isFavorite = isFavorite;
	}

 

}
