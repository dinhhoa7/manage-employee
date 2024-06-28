package hoavd.test.manage.employee.model;

public class EmployeeRequest {
  private String username;

  private String password;

  private String role;

  private long createAt;

  private long validUntil;

  private boolean isValid;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public long getCreateAt() {
    return createAt;
  }

  public void setCreateAt(long createAt) {
    this.createAt = createAt;
  }

  public long getValidUntil() {
    return validUntil;
  }

  public void setValidUntil(long validUntil) {
    this.validUntil = validUntil;
  }

  public boolean isValid() {
    return isValid;
  }

  public void setValid(boolean valid) {
    isValid = valid;
  }
}
