
import java.beans.JavaBean;
import java.util.List;
@JavaBean
public interface StudentServiceInterface {
    public Student findById(Long id);
    public List<Student> findByName(String firstname , String lastname);

    public void deleteUserBydId(Long id);

    public List<Student> findAll();

    public Student updateById(Long id , Student student);

    public Student addUser(Student student);
}
