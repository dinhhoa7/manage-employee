package hoavd.test.manage.employee.entity;

import javax.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long id;

  @Column(name = "user_name")
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "role")
  private String role;

  @Column(name = "create_at")
  private long createAt;

  @Column(name = "valid_until")
  private long validUntil;

  @Column(name = "is_valid")
  private boolean isValid;

  public Employee() {
  }

  public Employee(Long id, String username, String password, String role, long createAt, long validUntil,
    boolean isValid) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.role = role;
    this.createAt = createAt;
    this.validUntil = validUntil;
    this.isValid = isValid;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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
