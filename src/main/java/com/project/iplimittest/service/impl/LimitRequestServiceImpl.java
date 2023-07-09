package com.project.iplimittest.service.impl;

import com.project.iplimittest.configuration.LimitConfiguration;
import com.project.iplimittest.exception.LimitExceededException;
import com.project.iplimittest.service.LimitRequestService;
import com.project.iplimittest.utils.IpServiceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class LimitRequestServiceImpl implements LimitRequestService {

    private Map<String, Queue<Instant>> requestsMap = new ConcurrentHashMap<>();

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LimitConfiguration limitConfiguration;

    @Override
    public void checkLimitForRequests() throws LimitExceededException {
        Instant currentDate = Instant.now();
        String ip = IpServiceUtils.getCurrentClientIp();

        if (requestsMap.containsKey(ip)) {
            refreshRequestsInfoForIp(ip, currentDate);
            if (requestsMap.get(ip).size() >= limitConfiguration.getNumberOfRequests()) {
                throw new LimitExceededException("The number of requests has exceeded the limit for IP: " + ip);
            }
            logger.info("Added request for IP: {}, current size: {}", ip, requestsMap.get(ip).size());
            requestsMap.get(ip).add(currentDate);
        } else {
            logger.info("First request for IP: {}", ip);
            requestsMap.put(ip, new ConcurrentLinkedQueue<>(List.of(currentDate)));
        }
    }

    private void refreshRequestsInfoForIp(String ip, Instant currentDate) {
        if (requestsMap != null && requestsMap.containsKey(ip)) {
            Duration duration = Duration.ofMinutes(limitConfiguration.getExecutingTime());
            Queue<Instant> requests = requestsMap.get(ip);
            while (requests.peek() != null && requests.peek().isBefore(currentDate.minus(duration))) {
                logger.info("Removed request for IP: {}", ip);
                requests.remove();
            }
        }
    }
}
