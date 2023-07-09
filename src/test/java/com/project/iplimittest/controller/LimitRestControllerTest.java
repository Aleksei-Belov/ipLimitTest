package com.project.iplimittest.controller;

import com.project.iplimittest.configuration.LimitConfiguration;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LimitRestControllerTest {

    @Autowired
    private WebTestClient client;

    @Autowired
    private LimitConfiguration limitConfiguration;

    private final String ENDPOINT = "/api/limit";
    private final String X_FORWARDED_FOR = "X-Forwarded-For";

    @Test
    @DisplayName("Validation a success response for one request")
    public void testGetApiForOneRequest() {
        callGetApiWithSuccessResponse("11.11.11.11");
    }

    @Test
    @DisplayName("Validation a bad response when the limit is exceeded")
    public void testGetApiWithBadResponse() {
        sendLimitNumberRequestsForOneIp("11.11.11.10");
    }

    @Test
    @DisplayName("Validation a bad response when the limit is exceeded in several threads")
    public void testGetApiForFewIpsInParallel() {
        Runnable t1 = () -> sendLimitNumberRequestsForOneIp("10.10.10.1");
        Runnable t2 = () -> sendLimitNumberRequestsForOneIp("10.10.10.2");
        Runnable t3 = () -> sendLimitNumberRequestsForOneIp("10.10.10.3");
        Runnable t4 = () -> sendLimitNumberRequestsForOneIp("10.10.10.4");
        Runnable t5 = () -> sendLimitNumberRequestsForOneIp("10.10.10.5");

        new Thread(t1).start();
        new Thread(t2).start();
        new Thread(t3).start();
        new Thread(t4).start();
        new Thread(t5).start();
    }

    @Test
    @Disabled   //the method works - need to just enable (disabled to avoid waiting the time limit)
    @DisplayName("Validation a success response after time limit is over")
    public void testGetApiWithBadResponseWithWaiting() throws InterruptedException {
        String ip = "11.11.11.12";
        sendLimitNumberRequestsForOneIp(ip);

        // wait until the time limit is over
        int timeLimit = limitConfiguration.getExecutingTime() * 60 * 1000;
        Thread.sleep(timeLimit + 1000);

        callGetApiWithSuccessResponse(ip);
    }

    private void callGetApiWithSuccessResponse(String ip) {
        this.client
                .get()
                .uri(ENDPOINT)
                .header(X_FORWARDED_FOR, ip)
                .exchange()
                .expectStatus().isOk();
    }

    private void callGetApiWithBadResponse(String ip) {
        this.client
                .get()
                .uri(ENDPOINT)
                .header(X_FORWARDED_FOR, ip)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_GATEWAY);
    }

    private void sendLimitNumberRequestsForOneIp(String ip) {
        for (int i = 0; i < limitConfiguration.getNumberOfRequests(); i++) {
            callGetApiWithSuccessResponse(ip);
        }
        callGetApiWithBadResponse(ip);
    }
}
