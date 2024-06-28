package hoavd.test.manage.employee.common.exception;

public class BusinessException extends Exception {
  private String error;

  public BusinessException(String message) {
    super(message);
  }

  public BusinessException(String message, String error) {
    super(message);
    setError(error);
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }
}
