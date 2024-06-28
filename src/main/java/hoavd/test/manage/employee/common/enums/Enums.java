package hoavd.test.manage.employee.common.enums;

public class Enums {
  public enum ResponseStatus {
    SUCCESS("Success"), ERROR("Error");

    ResponseStatus(String status) {
      this.status = status;
    }

    public String getStatus() {
      return status;
    }

    private String status;

  }

  public enum Scopes {
    ACCESS_TOKEN, REFRESH_TOKEN;

    public String authority() {
      return "ROLE_" + this.name();
    }
  }

  public enum Role {
    ROLE_MANAGER, ROLE_DEVELOPER
  }
}
