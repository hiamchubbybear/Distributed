
import java.beans.JavaBean;
import java.util.List;
@JavaBean
public interface UserServiceInterface {
    public User findById(Long id);

    public void deleteUserBydId(Long id);

    public List<User> findAll();

    public User updateById(Long id);

    public User addUser(User user);
}
