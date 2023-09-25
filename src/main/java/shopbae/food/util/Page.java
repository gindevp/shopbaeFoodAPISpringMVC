package shopbae.food.util;

import java.util.List;

public class Page {
	public List<?> paging(int page, int pageSize, List<?> list) {
		// lấy dữ liệu cho trang hiện tại
				int fromIndex = page * pageSize;
				int toIndex = Math.min(fromIndex + pageSize, list.size());
				List<?> currentPageProduct = list.subList(fromIndex, toIndex);
				return currentPageProduct;
	}
}
