package config;

import java.io.Serializable;
import java.util.List;

public class ActuatorConfig implements Serializable {
  private static final long serialVersionUID = 1L;

  public List<String> actuator_list;

  public ActuatorConfig(List<String> actuator_list) {
    this.actuator_list = actuator_list;
  }
}
