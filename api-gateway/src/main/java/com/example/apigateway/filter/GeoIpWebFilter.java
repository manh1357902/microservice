package com.example.apigateway.filter;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;

@Component
public class GeoIpWebFilter implements WebFilter {

    private final DatabaseReader dbReader;
    private static final String VN = "VN"; // Mã ISO cho Việt Nam
    private static final String FILE_NAME = "GeoLite2-Country.mmdb";
    private static final List<String> RESTRICTED_PATHS = List.of("/table-type/");

    public GeoIpWebFilter() throws IOException {
        File database = new ClassPathResource(FILE_NAME).getFile();
        dbReader = new DatabaseReader.Builder(database).build();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // Kiểm tra nếu đường dẫn thuộc các service cần lọc
        String path = exchange.getRequest().getPath().toString();
        if (!RESTRICTED_PATHS.stream().anyMatch(path::startsWith)) {
            return chain.filter(exchange); // Bỏ qua nếu không phải route cần lọc
        }

        // Lấy địa chỉ từ xa từ ServerHttpRequest
        InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();
        if (remoteAddress != null) {
            // Sử dụng IP thử nghiệm cho phát triển; trong sản xuất, dùng remoteAddress.getAddress().getHostAddress()
            String ipAddress = "27.68.251.243"; // IP Việt Nam để thử nghiệm
            // String ipAddress = "43.231.112.45"; // IP Nhật Bản để thử nghiệm
            try {
                String country = dbReader.country(InetAddress.getByName(ipAddress))
                        .getCountry()
                        .getIsoCode();
                if (!VN.equals(country)) {
                    exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                    return exchange.getResponse().setComplete();
                }
            } catch (IOException | GeoIp2Exception e) {
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            }
        } else {
            // Xử lý trường hợp địa chỉ từ xa là null
            exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
            return exchange.getResponse().setComplete();
        }
        // Tiếp tục với chuỗi filter
        return chain.filter(exchange);
    }
}