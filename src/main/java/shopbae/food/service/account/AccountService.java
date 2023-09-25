package shopbae.food.service.account;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import shopbae.food.model.Account;
import shopbae.food.model.AccountDetails;
import shopbae.food.repository.account.IAccountRepository;

@Service
public class AccountService implements IAccountService, UserDetailsService {

	private static final long LOCK_TIME_DURATION = 10 * 1000; // 10'
	@Autowired
	private IAccountRepository accountRepository;

	@Override
	public Account findById(Long id) {
		return accountRepository.findById(id);
	}

	@Override
	public void save(Account t) {
		accountRepository.save(t);
	}

	@Override
	public void update(Account t) {
		accountRepository.update(t);
	}

	@Override
	public void delete(Account t) {
		accountRepository.delete(t);
	}

	@Override
	public List<Account> findAll() {
		return accountRepository.findAll();
	}

	@Override
	public Account findByName(String name) {
		return accountRepository.findByName(name);
	}

	@Override
	public Long findIdUserByUserName(String userName) {
		return accountRepository.findIdUserByUserName(userName);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = this.accountRepository.findByName(username);
		return AccountDetails.build(account);
	}

	@Override
	public boolean existsAccountByUserName(String username) {
		return accountRepository.existsAccountByUserName(username);
	}

	@Override
	public void increaseFailedAttempts(Account account) {
		int newFailAttempts = account.getFailedAttempt() + 1;
		account.setFailedAttempt(newFailAttempts);
		accountRepository.update(account);
	}

	@Override
	public void resetFailedAttempts(Account account) {
		account.setFailedAttempt(0);
		 accountRepository.update(account);
	}

	@Override
	public void lock(Account account) {
		account.setAccountNonLocked(true);
		account.setLockTime(new Date());

		accountRepository.update(account);
	}

	@Override
	public boolean unlockWhenTimeExpired(Account account) {
		long lockTimeInMillis = account.getLockTime().getTime();
		long currentTimeInMillis = System.currentTimeMillis();

		if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
			account.setAccountNonLocked(false);
			account.setLockTime(null);
			account.setFailedAttempt(0);

			accountRepository.update(account);

			return true;
		}

		return false;
	}

}
