package hoavd.test.manage.employee.service.impl;

import hoavd.test.manage.employee.common.constants.ResponseMessageConstants;
import hoavd.test.manage.employee.common.enums.Enums;
import hoavd.test.manage.employee.common.enums.Pagination;
import hoavd.test.manage.employee.common.enums.ResponseDataPagination;
import hoavd.test.manage.employee.common.exception.BusinessException;
import hoavd.test.manage.employee.entity.Employee;
import hoavd.test.manage.employee.model.AuthRequest;
import hoavd.test.manage.employee.model.EmployeeRequest;
import hoavd.test.manage.employee.model.EmployeeUpdateRequest;
import hoavd.test.manage.employee.repository.EmployeeRepository;
import hoavd.test.manage.employee.service.EmployeeService;
import org.apache.logging.log4j.util.Strings;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
  private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

  @Autowired
  private EmployeeRepository employeeRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public Employee login(AuthRequest request) throws Exception {
    Employee employee = employeeRepository.findByUsername(request.getUsername().toLowerCase());
    if (employee == null)
      throw new BusinessException(ResponseMessageConstants.EMPLOYEE_DOES_NOT_EXIST);
    if (passwordEncoder.matches(request.getPassword(), employee.getPassword()))
      return employee;
    throw new BusinessException(ResponseMessageConstants.ERR_SYSTEM);
  }

  @Override
  public Employee create(EmployeeRequest request) throws Exception {
    Employee employeeUsername = employeeRepository.findByUsername(request.getUsername().toLowerCase());
    if (employeeUsername != null)
      throw new BusinessException(ResponseMessageConstants.USERNAME_ALREADY_EXIST);
    Employee employee = new Employee();
    employee.setUsername(request.getUsername().toLowerCase());
    employee.setPassword(passwordEncoder.encode(request.getPassword()));
    employee.setRole(request.getRole());
    employee.setCreateAt(System.currentTimeMillis());
    employee.setValidUntil(System.currentTimeMillis());
    employee.setValid(request.isValid());
    return employeeRepository.save(employee);
  }

  @Override
  public Employee update(EmployeeUpdateRequest request) throws Exception {
    logger.info(request.toString());
    Employee employee = employeeRepository.findById(request.getId());
    if (employee == null)
      throw new BusinessException(ResponseMessageConstants.EMPLOYEE_DOES_NOT_EXIST);
    if (Strings.isBlank(request.getUsername()) || Strings.isBlank(request.getPassword())){
      throw new BusinessException(ResponseMessageConstants.INFORMATION_INVALID);
    }
    employee.setUsername(request.getUsername().toLowerCase());
    employee.setPassword(passwordEncoder.encode(request.getPassword()));
    employee.setRole(request.getRole());
    employee.setCreateAt(System.currentTimeMillis());
    employee.setValidUntil(System.currentTimeMillis());
    employee.setValid(request.isValid());
    return employeeRepository.save(employee);
  }

  @Override
  public void delete(long id) throws Exception {
    Employee employee = employeeRepository.findById(id);
    if (employee == null)
      throw new BusinessException(ResponseMessageConstants.EMPLOYEE_DOES_NOT_EXIST);
    employeeRepository.deleteById(id);
  }

  @Override
  public ResponseDataPagination getPageList(int page, int size) throws Exception {
    ResponseDataPagination responseDataPagination = new ResponseDataPagination();
    int pageReq = page >= 1 ? page - 1 : page;
    Pageable pageable = PageRequest.of(pageReq, size);
    Page<Employee> employeePage = employeeRepository.getPageList(pageable);
    List<Employee> employeeList = employeePage.getContent();
    responseDataPagination.setData(employeeList);
    Pagination pagination = new Pagination();
    pagination.setCurrentPage(page);
    pagination.setPageSize(size);
    pagination.setTotalPage(employeePage.getTotalPages());
    pagination.setTotalRecords(employeePage.getTotalElements());
    responseDataPagination.setStatus(Enums.ResponseStatus.SUCCESS.getStatus());
    responseDataPagination.setPagination(pagination);
    return responseDataPagination;
  }

  @Override
  public Employee getByIdAndUsername(long id, String username) {
    return employeeRepository.findByIdAndUsername(id, username);
  }

  @Override
  public List<EmployeeRequest> readFromExcel(InputStream inputStream) throws Exception {
    Workbook workbook = WorkbookFactory.create(inputStream);
    Sheet sheet = workbook.getSheetAt(0);
    Iterator<Row> rowIterator = sheet.iterator();
    List<EmployeeRequest> employees = new ArrayList<>();
    if (rowIterator.hasNext()) {
      rowIterator.next();
    }
    while (rowIterator.hasNext()) {
      Row row = rowIterator.next();
      EmployeeRequest request = new EmployeeRequest();
      request.setUsername(row.getCell(0).getStringCellValue());
      request.setPassword(row.getCell(1).getStringCellValue());
      request.setRole(row.getCell(2).getStringCellValue());
      request.setCreateAt((long) row.getCell(3).getNumericCellValue());
      request.setValidUntil((long) row.getCell(4).getNumericCellValue());
      request.setValid(row.getCell(5).getBooleanCellValue());
      employees.add(request);
    }
    workbook.close();
    return employees;
  }

  @Transactional
  public void saveEmployees(List<EmployeeRequest> listEmployee) {
    for (EmployeeRequest emp : listEmployee) {
      Employee employee = new Employee();
      employee.setUsername(emp.getUsername());
      employee.setPassword(emp.getPassword());
      employee.setRole(emp.getRole());
      employee.setCreateAt(emp.getCreateAt());
      employee.setValidUntil(emp.getValidUntil());
      employee.setValid(emp.isValid());
      employeeRepository.save(employee);
    }
  }
}
