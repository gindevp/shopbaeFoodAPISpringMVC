package shopbae.food.model.dto;

import org.springframework.web.multipart.MultipartFile;

public class ProductForm {
	private Long id;
    private String name;
    private String shortDescription;
    private Double oldPrice;
    private Double newPrice;
    private MultipartFile image;
    private int quantity;
	public ProductForm() {
	
	}
	public ProductForm(Long id, String name, String shortDescription, Double oldPrice, Double newPrice,
			MultipartFile image) {

		this.id = id;
		this.name = name;
		this.shortDescription = shortDescription;
		this.oldPrice = oldPrice;
		this.newPrice = newPrice;
		this.image = image;
	}
	
	public ProductForm(Long id, String name, String shortDescription, Double oldPrice, Double newPrice,
			MultipartFile image, int quantity) {
		super();
		this.id = id;
		this.name = name;
		this.shortDescription = shortDescription;
		this.oldPrice = oldPrice;
		this.newPrice = newPrice;
		this.image = image;
		this.quantity = quantity;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public Double getOldPrice() {
		return oldPrice;
	}
	public void setOldPrice(Double oldPrice) {
		this.oldPrice = oldPrice;
	}
	public Double getNewPrice() {
		return newPrice;
	}
	public void setNewPrice(Double newPrice) {
		this.newPrice = newPrice;
	}
	public MultipartFile getImage() {
		return image;
	}
	public void setImage(MultipartFile image) {
		this.image = image;
	}
	@Override
	public String toString() {
		return "ProductForm [id=" + id + ", name=" + name + ", shortDescription=" + shortDescription + ", oldPrice="
				+ oldPrice + ", newPrice=" + newPrice + ", image=" + image + "]";
	}
    
}
