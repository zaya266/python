// Data Layer
class UserRepository {
    public String getUserData() {
        return "User: Bat-Erdene, Role: Admin";
    }
}

class UserService {
    private final UserRepository repo = new UserRepository();

    public String processUser() {
        return "Processed -> " + repo.getUserData();
    }
}

public class LayeredApp {
    public static void main(String[] args) {
        UserService service = new UserService();
        System.out.println(service.processUser());
    }
}
+