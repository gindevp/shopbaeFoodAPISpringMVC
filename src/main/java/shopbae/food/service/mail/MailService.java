package shopbae.food.service.mail;

import shopbae.food.model.Mail;

public interface MailService {
	/**
	 * Send mail
	 * 
	 * @param mail
	 */
	public void sendEmail(Mail mail);
}
