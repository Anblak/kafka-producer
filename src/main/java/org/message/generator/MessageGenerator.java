package org.message.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.message.generator.models.ClientLocation;
import org.message.generator.models.JSON.CoordinatesFromJSON;
import org.message.generator.models.Message;

import java.io.*;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.*;

public class MessageGenerator {

    private final String browserNames[] = {"Google Chrome 48–55", "Mozilla Firefox 44–50", "Microsoft Edge 14", "Opera 35–42",
            "Apple Safari 10", "SeaMonkey 2.24–2.30", "Pale Moon 27.0.0[18]"};
    private ObjectMapper objectMapper;
    private Random rand;
    private List<CoordinatesFromJSON> coordinates;
    public List<String> domains = new ArrayList<>();

    public MessageGenerator() throws IOException {
        objectMapper = new ObjectMapper();
        rand = new Random();
        coordinates = objectMapper.readValue(new File("locations.json"),
                objectMapper.getTypeFactory().constructCollectionType(List.class, CoordinatesFromJSON.class));

        BufferedReader bufferedReader = new BufferedReader(new FileReader("DOMAINS.txt"));
            while (bufferedReader.readLine() != null) {
                domains.add(bufferedReader.readLine());
            }
    }

    public String createJSONMessage(int index, int companyID) throws IOException {
        CoordinatesFromJSON tempCoordinatesFromJSON = coordinates.get(rand.nextInt(coordinates.size()));
        double lat = tempCoordinatesFromJSON.getLatitude();
        double lon = tempCoordinatesFromJSON.getLongitude();
        double[] location = new double[2];

        if (index % 2 == 0) {
            location[0] = lon + ((double) (int) (rand.nextDouble() * 1000000.0)) / 1000000.0;
            location[1] = lat + ((double) (int) (rand.nextDouble() * 1000000.0)) / 1000000.0;
        } else {
            location[0] = lon - ((double) (int) (rand.nextDouble() * 1000000.0)) / 1000000.0;
            location[1] = lat - ((double) (int) (rand.nextDouble() * 1000000.0)) / 1000000.0;
        }
        ClientLocation clientLocation = new ClientLocation(location, tempCoordinatesFromJSON.getCity(),
                tempCoordinatesFromJSON.getState(),tempCoordinatesFromJSON.getPopulation());
        return objectMapper.writeValueAsString(new Message(
                index,
         rand.nextInt(256) + "." + rand.nextInt(256) + "." + rand.nextInt(256) + "." + rand.nextInt(256),
                companyID,
                LocalDateTime.now().toString(),
                UUID.randomUUID().toString(),
                domains.get(rand.nextInt(domains.size())),
                rand.nextInt(600) + "",
                rand.nextInt(999),
                clientLocation,
                browserNames[rand.nextInt(browserNames.length)]));
    }


}
