package org.message.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.message.generator.models.Coordinates;
import org.message.generator.models.Message;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class MessageGenerator {

    private final String browserNames[] = {"Google Chrome 48–55", "Mozilla Firefox 44–50", "Microsoft Edge 14", "Opera 35–42",
            "Apple Safari 10", "SeaMonkey 2.24–2.30", "Pale Moon 27.0.0[18]"};
    private ObjectMapper objectMapper;
    private Random rand;
    private List<Coordinates> coordinates;

    public MessageGenerator() throws IOException {
        objectMapper = new ObjectMapper();
        rand = new Random();
        coordinates = objectMapper.readValue(new File("locations.json"),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Coordinates.class));
    }

    public String createJSONMessage(int index) throws IOException {

        Coordinates location = coordinates.get(rand.nextInt(coordinates.size()));
        if (index % 2 == 0) {
            location.setLongitude(location.getLongitude() + ((double) (int) (rand.nextDouble() * 1000000.0)) / 1000000.0);
            location.setLatitude(location.getLatitude() + ((double) (int) (rand.nextDouble() * 1000000.0)) / 1000000.0);
        } else {
            location.setLongitude(location.getLongitude() - ((double) (int) (rand.nextDouble() * 1000000.0)) / 1000000.0);
            location.setLatitude(location.getLatitude() - ((double) (int) (rand.nextDouble() * 1000000.0)) / 1000000.0);
        }

        return objectMapper.writeValueAsString(new Message(
                index,
                InetAddress.getLocalHost().toString(),
                LocalDateTime.now(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                rand.nextInt(600) + "",
                rand.nextInt(999),
                location,
                browserNames[rand.nextInt(browserNames.length)]));
    }
}
