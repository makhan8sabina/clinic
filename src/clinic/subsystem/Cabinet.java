package clinic.subsystem;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cabinet {

    private final List<String> rooms = List.of("101", "102", "103");
    private final Map<LocalDateTime, Map<String, String>> doctorByRoomAtTime = new HashMap<>();

    public String assignRoom(String doctorName, LocalDateTime time) {
        Map<String, String> occupiedRooms = doctorByRoomAtTime.computeIfAbsent(time, t -> new HashMap<>());
        for (String room : rooms) {
            if (!occupiedRooms.containsKey(room)) {
                occupiedRooms.put(room, doctorName);
                return room;
            }
        }
        throw new SubsystemException("No free consulting room at " + time.toLocalTime());
    }
}
