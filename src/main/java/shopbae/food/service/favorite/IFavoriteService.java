package shopbae.food.service.favorite;


import shopbae.food.model.AppUser;
import shopbae.food.model.Favorite;
import shopbae.food.model.Product;
import shopbae.food.service.IGeneral;

public interface IFavoriteService extends IGeneral<Favorite>{
	Favorite findByUserAndPro(AppUser appUser, Product product);  
}
