package hoavd.test.manage.employee.service;

import hoavd.test.manage.employee.common.enums.ResponseDataPagination;
import hoavd.test.manage.employee.entity.Employee;
import hoavd.test.manage.employee.model.AuthRequest;
import hoavd.test.manage.employee.model.EmployeeRequest;
import hoavd.test.manage.employee.model.EmployeeUpdateRequest;

import java.io.InputStream;
import java.util.List;

public interface EmployeeService {
  Employee login(AuthRequest request) throws Exception;

  Employee create(EmployeeRequest request) throws Exception;

  Employee update(EmployeeUpdateRequest request) throws Exception;

  void delete(long id) throws Exception;

  ResponseDataPagination getPageList(int page, int size) throws Exception;

  Employee getByIdAndUsername(long id, String username);

  List<EmployeeRequest> readFromExcel(InputStream inputStream) throws Exception;

  void saveEmployees(List<EmployeeRequest> listEmployee) throws Exception;
}
