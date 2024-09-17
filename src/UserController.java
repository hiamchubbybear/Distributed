import java.io.IOException;

public class UserController {
    private UserService service;

    public User getById(Long id) throws IOException {
        User data = service.findById(id);
        return data;
    }
}
