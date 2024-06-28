package hoavd.test.manage.employee.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class Config {
  @Value("${admin.name}")
  private String client;

  @Value("${admin.vn.time-zone}")
  private String adminVNTimezone;

  @Value("${uri.unauthorized}")
  private String[] uriUnauthorized;

  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder(13);
  }

  public String getClient() {
    return client;
  }

  public void setClient(String client) {
    this.client = client;
  }

  public String getAdminVNTimezone() {
    return adminVNTimezone;
  }

  public void setAdminVNTimezone(String adminVNTimezone) {
    this.adminVNTimezone = adminVNTimezone;
  }

  public String[] getUriUnauthorized() {
    return uriUnauthorized;
  }

  public void setUriUnauthorized(String[] uriUnauthorized) {
    this.uriUnauthorized = uriUnauthorized;
  }

  public List<String> getLstUriUnauthorized() {
    return new ArrayList<>(Arrays.asList(uriUnauthorized));
  }
}
