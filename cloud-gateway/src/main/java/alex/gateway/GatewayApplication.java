package alex.gateway;

import java.util.function.Function;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.headers.HttpHeadersFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.builder.UriSpec;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
            .route("path_route", predicateSpec -> predicateSpec.path("/get")
                .filters(new Function<GatewayFilterSpec, UriSpec>() {
                    @Override
                    public UriSpec apply(GatewayFilterSpec gatewayFilterSpec) {
                        return gatewayFilterSpec.filter(new GatewayFilter() {
                            @Override
                            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                                ServerHttpRequest request = exchange.getRequest();
                                ServerHttpResponse response = exchange.getResponse();
                                System.out.println("request: " + request);
                                System.out.println("response: " + response);

                                return chain.filter(exchange);
                            }
                        });
                    }
                })
                .uri("http://google.com"))
            .route("websocket_route", predicateSpec -> predicateSpec.path("/echo").uri("ws://localhost:9000"))
            .build();
    }

    @Bean
    public HttpHeadersFilter addHeader() {
        return new HttpHeadersFilter() {
            @Override
            public boolean supports(Type type) {
                return Type.RESPONSE.equals(type);
            }

            @Override
            public HttpHeaders filter(HttpHeaders input, ServerWebExchange exchange) {
                HttpHeaders updated = new HttpHeaders();
                // copy all headers except Forwarded
                input.forEach(updated::addAll);
                updated.add("name", "test");
                return updated;
            }
        };
    }

}

@RestController
class GatewaySample {

    @GetMapping("/hystrixfallback")
    public String hystrixFallback() {
        return "this is a fallback";
    }

}