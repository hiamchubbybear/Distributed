import java.io.IOException;

public class UserController {
    private UserService service = new UserService();

    public User getById(Long id) throws IOException {
        User data = service.findById(id);
        return data;
    }

    public UserController() {
    }
}
