package com.numberManagement.Controller;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@RestController
public class NumberController {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public NumberController(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/numbers")
    public ResponseEntity<?> getMergedNumbers(@RequestParam("url") List<String> urls) {
        List<Integer> mergedNumbers = new ArrayList<>();

        for (String url : urls) {
            List<Integer> numbers = fetchNumbersFromUrl(url);
            mergedNumbers.addAll(numbers);
        }

        Set<Integer> uniqueNumbers = new HashSet<>(mergedNumbers);
        List<Integer> sortedUniqueNumbers = new ArrayList<>(uniqueNumbers);
        Collections.sort(sortedUniqueNumbers);
        Map<String, List<Integer>> response = new HashMap<>();
        response.put("numbers", sortedUniqueNumbers);
        return ResponseEntity.ok(response);
    }

    private List<Integer> fetchNumbersFromUrl(String url) {
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                JsonNode jsonNode = objectMapper.readTree(response.getBody());
                return Arrays.asList(objectMapper.convertValue(jsonNode.get("numbers"), Integer[].class));
            } catch (Exception e) {
            }
        }
        return Collections.emptyList();
    }
}



