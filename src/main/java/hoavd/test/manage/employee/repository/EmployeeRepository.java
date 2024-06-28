package hoavd.test.manage.employee.repository;

import hoavd.test.manage.employee.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
  @Query(value = "select * from employees e order by e.create_at desc", nativeQuery = true)
  Page<Employee> getPageList(Pageable pageable);

  Employee findById(long id);

  Employee findByUsername(String username);

  Employee findByIdAndUsername(long id, String username);
}
