package alex;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * <p>Created by Damon.Q on 2017/2/15.
 */
@Service
public class ComputeService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "addServiceFallback")
    public String addService() {
        return restTemplate.getForEntity("http://A-BOOTIFUL-CLIENT/add?a=11&b=22", String.class)
            .getBody();
    }

    public String addServiceFallback() {
        return "error...fallback";
    }
}
