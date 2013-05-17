package notifiers;

import play.mvc.*;
import models.*;

public class Mails extends Mailer {
	
	public static void verifyAccount(Customer customer){
		setSubject("Drive Cleaners account verification");
		addRecipient( customer.email );
		setFrom("drive-cleaners <support@drive-cleaners.com>");
		send( customer );

	}

	public static void forgotPassword(User user){
      setSubject("Your Password");
      addRecipient( user.email );
      setFrom("drive-cleaners <support@drive-cleaners.com>");
      //EmailAttachment attachment = new EmailAttachment();
      //attachment.setDescription("A pdf document");
      //attachment.setPath(Play.getFile("rules.pdf").getPath());
      //addAttachment(attachment);
      send(user);
	}
}