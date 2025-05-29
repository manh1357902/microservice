package com.example.apigateway.filter;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

@Component
public class GeoIpWebFilter extends OncePerRequestFilter {

    private final DatabaseReader dbReader;
    private static final String VN = "VN";
    private static final String FILE_NAME =  "GeoLite2-Country.mmdb";
    private static final List<String> RESTRICTED_PATHS = List.of("/table-type/");

    public GeoIpWebFilter() throws IOException {
        File database = new ClassPathResource(FILE_NAME).getFile();
        dbReader = new DatabaseReader.Builder(database).build();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        if (RESTRICTED_PATHS.stream().noneMatch(path::startsWith)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Tạm thời dùng IP hardcoded để test
        String ipAddress = "27.68.251.243"; // IP Việt Nam
//        String ipAddress = "43.231.112.45"; // IP Nhật Bản

        try {
            String country = dbReader.country(InetAddress.getByName(ipAddress))
                    .getCountry()
                    .getIsoCode();
            if (!VN.equals(country)) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                return;
            }
        } catch (IOException | GeoIp2Exception e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return;
        }

        filterChain.doFilter(request, response);
    }
}
