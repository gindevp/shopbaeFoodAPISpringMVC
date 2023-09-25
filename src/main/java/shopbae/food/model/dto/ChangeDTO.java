package shopbae.food.model.dto;

import org.springframework.web.multipart.MultipartFile;

public class ChangeDTO {
	private Long id;
	private MultipartFile avatar;
	private String name;
	private String phone;
	private String email;
	private String openTime;
	private String closeTime;
	
	public ChangeDTO() {

	}

	public ChangeDTO(Long id, MultipartFile avatar, String name, String phone, String email, String openTime,
			String closeTime) {
		this.id = id;
		this.avatar = avatar;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.openTime = openTime;
		this.closeTime = closeTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MultipartFile getAvatar() {
		return avatar;
	}

	public void setAvatar(MultipartFile avatar) {
		this.avatar = avatar;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}

	
	
}
