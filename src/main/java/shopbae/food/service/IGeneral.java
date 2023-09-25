package shopbae.food.service;

import java.util.List;

/**
 * 
 * @author Nguyen_Huu_Quyet Class nhằm tạo ra các CRUD
 * @param <T> Thực thể
 */
public interface IGeneral<T> {
	/**
	 * Phương thức tìm thực thể
	 * 
	 * @param id : chỉ số thực thể cần tìm
	 * @return trả về 1 đối tượng
	 */
	T findById(Long id);

	/**
	 * Phương thức lưu 1 thực thể
	 * 
	 * @param t: thực thể cần lưu
	 * 
	 */
	void save(T t);

	/**
	 * Cập nhật 1 thực thể
	 * 
	 * @param t: thực thể cần lưu
	 */
	void update(T t);

	/**
	 * Xóa 1 thực thể
	 * 
	 * @param t: thực thể cần xóa
	 */
	void delete(T t);

	/**
	 * Lấy ra tất cả thực thể có
	 * 
	 * @return Trả về tất cả thực thể
	 */
	List<T> findAll();
}