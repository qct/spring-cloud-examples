package alex;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Created by qct on 2017/2/13.
 */
@EnableDiscoveryClient
@SpringBootApplication
public class EurekaClientAnotherApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaClientAnotherApplication.class, args);
    }
}

@RestController
class ServiceInstanceRestController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceInstanceRestController.class);

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping(value = "/service-instances/{applicationName}", method = RequestMethod.GET)
    public List<ServiceInstance> serviceInstancesByApplicationName(
        @PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public Integer add(@RequestParam Integer a, @RequestParam Integer b) {
        List<String> services = discoveryClient.getServices();
        logger.info("/add, services: {}, result: {}", services, a + b);
        return a + b;
    }
}
