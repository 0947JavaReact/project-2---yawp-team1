import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.revature.models.User;
import com.revature.models.Yawp;
import com.revature.repositories.UserRepository;
import com.revature.repositories.YawpRepository;
import com.revature.services.UserService;
import com.revature.services.YawpService;

public class MainDriver {
	public static ApplicationContext applicationContext =  new ClassPathXmlApplicationContext("applicationContext.xml");
	public static UserRepository urepo = applicationContext.getBean("userDao",UserRepository.class);
	public static YawpRepository yrepo =  applicationContext.getBean("yawpDao", YawpRepository.class);
	public static UserService userv = applicationContext.getBean("userServ",UserService.class); 
	public static YawpService yserv = applicationContext.getBean("yawpServ", YawpService.class);
	
	public static void main(String[] args) {
		insertInitialValues();
		System.out.println(userv.getAllUsers());
		System.out.println(yserv.getAllYawps());
	}
	
	public static void insertInitialValues() {
		
		/*
		 * 
		 * this.username = username;
		this.password = password;
		this.email = email;
		this.bio = bio;
		this.picUrl = picUrl;
		this.followersIds = followersIds;
		this.followingIds = followingIds;
		 */
		
		List<User>userfollowers = new ArrayList<>();
		List<User>userfollowing = new ArrayList<>();

		
		User u1 = new User("robuser","password","robert@email.com","bio","pic", userfollowers ,userfollowing);
		
		
		
		
		User u2 = new User("thomasuser","password","tom@email.com","bio","pic", userfollowers ,userfollowing);
		User u3 = new User("jruser","password","jr@email.com","bio","pic", userfollowers ,userfollowing);
		User u4 = new User("ethanuser","password","ethan@email.com","bio","pic", userfollowers ,userfollowing);
		User u5 = new User("mikeuser","password","mike@email.com","bio","pic", userfollowers ,userfollowing);
		
		
		
		urepo.insert(u1);
		urepo.insert(u2);
		urepo.insert(u3);
		urepo.insert(u4);
		urepo.insert(u5);
		
//		userfollowers.add(u1.getUserId());
//		userfollowers.add(u2.getUserId());
//		userfollowers.add(u3.getUserId());
//		
//		urepo.update(u1);
		/*
		 * this.message = message;
		this.authorId = authorId;
		this.yawpTime = yawpTime;
		this.likesCount = likesCount; 
		 */
		Yawp y1 = new Yawp("message1",u1.getUserId(),LocalDateTime.now(), 5000);
		Yawp y2 = new Yawp("message2",u2.getUserId(),LocalDateTime.now(), 5000);
		Yawp y3 = new Yawp("message3",u3.getUserId(),LocalDateTime.now(), 5000);
		Yawp y4 = new Yawp("message4",u4.getUserId(),LocalDateTime.now(), 5000);
		Yawp y5 = new Yawp("message5",u4.getUserId(),LocalDateTime.now(), 5000);
		
		yrepo.insert(y1);
		yrepo.insert(y2);
		yrepo.insert(y3);
		yrepo.insert(y4);
		yrepo.insert(y5);

	}
}
