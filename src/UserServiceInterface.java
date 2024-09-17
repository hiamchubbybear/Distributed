
import java.util.List;

public interface UserServiceInterface {
    public User findById(Long id);

    public Boolean deleteUserBydId(Long id);

    public List<User> findAll();

    public User updateById(Long id);
}
