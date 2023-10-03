//package shopbae.food.service.favorite;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import shopbae.food.model.AppUser;
//import shopbae.food.model.Favorite;
//import shopbae.food.model.Product;
//import shopbae.food.repository.favorite.IFavoriteRepository;
//
//@Service
//public class FavoriteService implements IFavoriteService {
//	@Autowired
//	private IFavoriteRepository favoriteRepository;
//
//	@Override
//	public Favorite findById(Long id) {
//		return favoriteRepository.findById(id);
//	}
//
//	@Override
//	public void save(Favorite t) {
//		favoriteRepository.save(t);
//	}
//
//	@Override
//	public void update(Favorite t) {
//		favoriteRepository.update(t);
//	}
//
//	@Override
//	public void delete(Favorite t) {
//		favoriteRepository.delete(t);
//	}
//
//	@Override
//	public List<Favorite> findAll() {
//		return favoriteRepository.findAll();
//	}
//
//	@Override
//	public Favorite findByUserAndPro(AppUser appUser, Product product) {
//		try {
//			List<Favorite> favorites = findAll();
//			Favorite favorite = null;
//			for (Favorite favorite1 : favorites) {
//				if (appUser.getId().equals(favorite1.getAppUser().getId())
//						&& product.getId().equals(favorite1.getProduct().getId())) {
//					favorite = favorite1;
//				}
//			}
//			return favorite;
//		} catch (Exception e) {
//			
//			return null;
//		}
//
//	}
//
//}
