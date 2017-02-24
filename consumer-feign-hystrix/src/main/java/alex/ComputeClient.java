package alex;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>Created by Damon.Q on 2017/2/15.
 */
@FeignClient(value = "A-BOOTIFUL-CLIENT", fallback = ComputeClientHystrix.class)
public interface ComputeClient {

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    Integer add(@RequestParam("a") Integer a, @RequestParam("b") Integer b);
}
