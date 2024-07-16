package learn.farm;

import learn.farm.data.PanelFileRepository;
import learn.farm.domain.PanelService;
import learn.farm.ui.Controller;
import learn.farm.ui.View;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@ComponentScan
@PropertySource("classpath:data.properties")
public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("dependency-configuration.xml");

        Controller controller = context.getBean(Controller.class);
        // Run the app!
        controller.run();
    }
}
