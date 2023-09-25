package shopbae.food.model.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import shopbae.food.model.Account;
import shopbae.food.service.account.IAccountService;



public class UserNameValidator implements ConstraintValidator<UserNameUnique, String> {

	@Autowired
	private IAccountService accountService;

	@Override
	public void initialize(UserNameUnique constraintAnnotation) {

	}

	@Override
	public boolean isValid(String userName, ConstraintValidatorContext context) {
		Account account = accountService.findByName(userName);
		if (account == null) {
			return true;
		}
		return false;
	}

}