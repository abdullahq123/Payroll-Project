import java.util.HashMap;
import java.util.Map;

public class UserDatabase {
    private static final Map<String, User> users = new HashMap<>();

    static {
        users.put("admin", new User("admin", "adminpass", Role.ADMIN));
        users.put("manager", new User("manager", "managerpass", Role.MANAGER));
        users.put("employee", new User("employee", "employeepass", Role.EMPLOYEE));
    }

    public static User authenticate(String username, String password) {
        User user = users.get(username);
        if (user != null && user.validatePassword(password)) {
            return user;
        }
        return null;
    }

    public static User getUser(String username) {
        return users.get(username);
    }

    public static void loadUsers() {
        // Load users from a database or file in a real application
    }
}
