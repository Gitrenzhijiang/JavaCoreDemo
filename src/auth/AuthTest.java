package auth;

import java.security.PrivilegedAction;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;

/**
 * This program authenticates a user via a custom login and then executes the SysPropAction with
 * the user's privileges
 * @author REN
 */
public class AuthTest {
    public static void main(final String[] args) {
        //System.setProperty("java.security.policy", "auth/AuthTest.policy");
        System.setSecurityManager(new SecurityManager());
        try {
            LoginContext context = new LoginContext("Login1");
            context.login();
            System.out.println("Authentication successful.");
            Subject subject = context.getSubject(); //拿到经过认证的主题
            System.out.println("subject=" + subject);
            PrivilegedAction<String> action = new SysPropAction("user.home");
            String result = Subject.doAsPrivileged(subject, action, null);
            System.out.println(result);
            context.logout();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
