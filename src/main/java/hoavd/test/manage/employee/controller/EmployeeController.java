package hoavd.test.manage.employee.controller;

import hoavd.test.manage.employee.common.constants.PagingConstants;
import hoavd.test.manage.employee.common.constants.ResponseMessageConstants;
import hoavd.test.manage.employee.common.enums.Enums;
import hoavd.test.manage.employee.common.enums.ResponseData;
import hoavd.test.manage.employee.common.exception.BusinessException;
import hoavd.test.manage.employee.common.utils.LogUtils;
import hoavd.test.manage.employee.config.security.JwtTokenProvider;
import hoavd.test.manage.employee.entity.Employee;
import hoavd.test.manage.employee.model.AuthRequest;
import hoavd.test.manage.employee.model.EmployeeRequest;
import hoavd.test.manage.employee.model.EmployeeUpdateRequest;
import hoavd.test.manage.employee.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
  private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

  @Autowired
  private EmployeeService employeeService;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @PostMapping("/login")
  public ResponseData login(@RequestBody AuthRequest request){
    ResponseData responseData = new ResponseData();
    try {
      Employee employee = employeeService.login(request);
      String jwt = jwtTokenProvider.generateToken(employee);
      Map<String, Object> authResponse = new HashMap<>();
      authResponse.put("access_token", jwt);
      responseData.setData(authResponse);
      responseData.setStatus(Enums.ResponseStatus.SUCCESS);
      responseData.setMessage(ResponseMessageConstants.LOGIN_SUCCESS);
    } catch (BusinessException be) {
      logger.error(be.getMessage());
      responseData.setMessage(be.getMessage());
      responseData.setStatus(Enums.ResponseStatus.ERROR);
    } catch (Exception ex) {
      logger.error(LogUtils.printLogStackTrace(ex));
      responseData.setMessage(ResponseMessageConstants.ERR_SYSTEM);
      responseData.setStatus(Enums.ResponseStatus.ERROR);
    }
    return responseData;
  }

  @GetMapping("/get-list")
  public ResponseData getList(@RequestParam(value = "page", required = false, defaultValue = PagingConstants.PAGING_STRING_PAGE_DEFAULT) int page,
    @RequestParam(value = "size", required = false, defaultValue = PagingConstants.PAGING_STRING_SIZE_DEFAULT) int size) {
    ResponseData responseData = new ResponseData();
    try {
      return employeeService.getPageList(page, size);
    } catch (BusinessException be) {
      logger.error(LogUtils.printLogStackTrace(be));
      responseData.setStatus(Enums.ResponseStatus.ERROR);
      responseData.setMessage(be.getMessage());
    } catch (Exception ex) {
      logger.error(LogUtils.printLogStackTrace(ex));
      responseData.setMessage(ResponseMessageConstants.ERR_SYSTEM);
      responseData.setStatus(Enums.ResponseStatus.ERROR);
    }
    return responseData;
  }

  @PostMapping("/create")
  public ResponseData create(@RequestBody EmployeeRequest request) {
    ResponseData responseData = new ResponseData();
    try {
      Employee employee = employeeService.create(request);
      responseData.setData(employee);
      responseData.setStatus(Enums.ResponseStatus.SUCCESS);
      responseData.setMessage(ResponseMessageConstants.CREATE_EMPLOYEE_SUCCESS);
    } catch (BusinessException be) {
      logger.error(be.getMessage());
      responseData.setMessage(be.getMessage());
      responseData.setStatus(Enums.ResponseStatus.ERROR);
    } catch (Exception ex) {
      logger.error(LogUtils.printLogStackTrace(ex));
      responseData.setMessage(ResponseMessageConstants.ERR_SYSTEM);
      responseData.setStatus(Enums.ResponseStatus.ERROR);
    }
    return responseData;
  }

  @PutMapping("/update")
  public ResponseData update(@RequestBody EmployeeUpdateRequest request){
    ResponseData responseData = new ResponseData();
    try {
      Employee employee = employeeService.update(request);
      responseData.setData(employee);
      responseData.setStatus(Enums.ResponseStatus.SUCCESS);
      responseData.setMessage(ResponseMessageConstants.UPDATE_EMPLOYEE_SUCCESS);
    } catch (BusinessException be) {
      logger.error(be.getMessage());
      responseData.setMessage(be.getMessage());
      responseData.setStatus(Enums.ResponseStatus.ERROR);
    } catch (Exception ex) {
      logger.error(LogUtils.printLogStackTrace(ex));
      responseData.setMessage(ResponseMessageConstants.ERR_SYSTEM);
      responseData.setStatus(Enums.ResponseStatus.ERROR);
    }
    return responseData;
  }

  @DeleteMapping("/delete/{id}")
  public ResponseData delete(@PathVariable long id){
    ResponseData responseData = new ResponseData();
    try {
      employeeService.delete(id);
      responseData.setStatus(Enums.ResponseStatus.SUCCESS);
      responseData.setMessage(ResponseMessageConstants.DELETE_EMPLOYEE_SUCCESS);
    } catch (BusinessException be) {
      logger.error(be.getMessage());
      responseData.setMessage(be.getMessage());
      responseData.setStatus(Enums.ResponseStatus.ERROR);
    } catch (Exception ex) {
      logger.error(LogUtils.printLogStackTrace(ex));
      responseData.setMessage(ResponseMessageConstants.ERR_SYSTEM);
      responseData.setStatus(Enums.ResponseStatus.ERROR);
    }
    return responseData;
  }

  @PostMapping("/import")
  public ResponseData importData(@RequestParam("file")MultipartFile file) {
    ResponseData responseData = new ResponseData();
    try {
      InputStream inputStream = file.getInputStream();
      List<EmployeeRequest> employees = employeeService.readFromExcel(inputStream);
      employeeService.saveEmployees(employees);
      responseData.setData(employees);
      responseData.setStatus(Enums.ResponseStatus.SUCCESS);
      responseData.setMessage(ResponseMessageConstants.DELETE_EMPLOYEE_SUCCESS);
    } catch (BusinessException be) {
      logger.error(be.getMessage());
      responseData.setMessage(be.getMessage());
      responseData.setStatus(Enums.ResponseStatus.ERROR);
    } catch (Exception ex) {
      logger.error(LogUtils.printLogStackTrace(ex));
      responseData.setMessage(ResponseMessageConstants.ERR_SYSTEM);
      responseData.setStatus(Enums.ResponseStatus.ERROR);
    }
    return responseData;
  }
}
