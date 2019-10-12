package tron.deployment.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import tron.deployment.pojo.ConfigGenerator;
import tron.deployment.pojo.Configuration;
import tron.deployment.shellExecutor.BashExecutor;

@Controller
@RequestMapping(value = "/")
public class deployController {

	@RequestMapping(value = "/index")
	String index() {

		return "index.html";
	}

	@RequestMapping(value = "/deploy")
	public String config() {
		// return "configSelect.html";
		return "wizard-find-desk.html";
	}

	@RequestMapping(value = "/result")
	public String deploy(@ModelAttribute("form") Configuration configuration, Model model) {
		model.addAttribute("configuration", configuration);
		ConfigGenerator configGenerator = new ConfigGenerator();
		BashExecutor bashExecutor = new BashExecutor();
		bashExecutor.callBuildScript2();
		configGenerator.generateConfig(configuration);
		bashExecutor.callDeployScript();
		return "result";
	}
	
	
	
}


