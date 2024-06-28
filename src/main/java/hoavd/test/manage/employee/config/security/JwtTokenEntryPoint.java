package hoavd.test.manage.employee.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenEntryPoint implements AuthenticationEntryPoint {
  private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenEntryPoint.class);

  @Override
  public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
    AuthenticationException e) throws IOException {
    LOGGER.error("Responding with unauthorized error. Message - {}", e.getMessage());
    httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
      "Sorry, You're not authorized to access this resource.");
  }
}
