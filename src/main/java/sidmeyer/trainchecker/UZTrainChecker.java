package sidmeyer.trainchecker;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import sidmeyer.trainchecker.model.uz.UZRoute;
import sidmeyer.trainchecker.model.uz.UZTrainSearchResponse;
import sidmeyer.trainchecker.model.uz.UZTrainSearchResult;

import javax.swing.*;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class UZTrainChecker {
    private static final Logger LOG = LoggerFactory.getLogger(UZTrainChecker.class);
    private final List<UZRoute> checkedRoutes = parseTrains();
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String UZ_SEARCH_URL = "https://booking.uz.gov.ua/train_search/";
    private static final String ROUTES_FILE = "checkedRoutesUZ.json";
    private static final long CHECK_TIMEOUT = 4 * 60 * 1000;

    @Scheduled(fixedRate = CHECK_TIMEOUT)
    public void checkTrains() {
        LOG.info("Date: " + LocalDateTime.now());
        checkedRoutes.forEach(this::checkTrain);
    }

    private static List<UZRoute> parseTrains() {
        List<UZRoute> routes = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            routes = mapper.readValue(Paths.get(ClassLoader.getSystemResource(ROUTES_FILE).toURI()).toFile(), new TypeReference<List<UZRoute>>() {});
        } catch (Exception e) {
            LOG.error("Cannot read input file", e);
        }
        return routes;
    }

    private void checkTrain(final UZRoute route) {
        LOG.info("Checking availability for {}", route);
        ResponseEntity<UZTrainSearchResponse> response = restTemplate.postForEntity(UZ_SEARCH_URL, createRequest(route), UZTrainSearchResponse.class);
        LOG.info("Response body: {}", response.getBody());
        List<UZTrainSearchResult> results = response.getBody().getData().getList();
        results.forEach(this::checkResult);
    }

    private HttpEntity<MultiValueMap<String, String>> createRequest(final UZRoute route) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("from", route.getFrom());
        params.add("to", route.getTo());
        params.add("date", route.getDate().toString());
        params.add("time", route.getTime().toString());

        return new HttpEntity<>(params, headers);
    }

    private void checkResult(UZTrainSearchResult result) {
        result.getTypes().stream()
                .filter(t -> t.getPlaces() > 0)
                .forEach(t -> {
                    String message = "Found " + t.getPlaces() + " " + t.getTitle() + " places in train " + result.getNum();
                    LOG.warn("***** {} *****", message);
                    showMessage(message);
                });
    }

    private void showMessage(final String message) {
        new Thread(() -> JOptionPane.showMessageDialog(null, message, "Free places found!", JOptionPane.WARNING_MESSAGE)).start();
    }
}
