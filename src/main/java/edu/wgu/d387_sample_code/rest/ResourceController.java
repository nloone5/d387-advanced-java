package edu.wgu.d387_sample_code.rest;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/resources")
@CrossOrigin
public class ResourceController {

    //Create executors
    private Executor e = Executors.newFixedThreadPool(2);

    @GetMapping("welcome")
    public ResponseEntity<List<String>> getWelcomeMessage() {
        List<String> l = new ArrayList<String>();
        Properties properties = new Properties();

        //Read en_US
        e.execute(() -> {
            try {
                InputStream stream = new ClassPathResource("translation_en_US.properties").getInputStream();
                properties.load(stream);
                l.add(properties.getProperty("welcome"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //Read fr_CA
        e.execute(() -> {
            try {
                InputStream stream = new ClassPathResource("translation_fr_CA.properties").getInputStream();
                properties.load(stream);
                l.add(properties.getProperty("welcome"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return ResponseEntity.ok(l);
    }
}
