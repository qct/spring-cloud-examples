package alex.declarative.client.circuit.breaker;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>Created by qct on 2017/2/16.
 */
@Component
public class ComputeClientHystrix implements ComputeClient {

    @Override
    public Integer add(@RequestParam("a") Integer a, @RequestParam("b") Integer b) {
        return -9999;
    }
}
