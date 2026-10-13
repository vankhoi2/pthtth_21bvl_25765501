
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChatRoom {
    private final String name;
    private final Set<ClientHandler> members =
            ConcurrentHashMap.newKeySet();

    private static final Map<Integer, ChatRoom> rooms =
            new LinkedHashMap<>();

    public ChatRoom(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addMember(ClientHandler client) {
        members.add(client);
        broadcast("*** " + client.getClientName()
                + " đã tham gia phòng " + name + " ***", client);
    }

    public void removeMember(ClientHandler client) {
        if (members.remove(client)) {
            broadcast("*** " + client.getClientName()
                    + " đã rời phòng " + name + " ***", null);
        }
    }

    public void broadcast(String message, ClientHandler sender) {
        for (ClientHandler member : members) {
            if (member != sender) {
                member.sendMessage(message);
            }
        }
    }

    public synchronized static void initializeRooms() {
        if (!rooms.isEmpty()) {
            return;
        }

        rooms.put(1, new ChatRoom("Phòng Học Nhóm"));
        rooms.put(2, new ChatRoom("Phòng Game"));
        rooms.put(3, new ChatRoom("Phòng Làm Việc Nhóm"));
        rooms.put(4, new ChatRoom("Phòng Dân Cư"));
    }

    public static synchronized List<String> getRoomList() {
        List<String> result = new ArrayList<>();

        for (Map.Entry<Integer, ChatRoom> entry : rooms.entrySet()) {
            result.add(entry.getKey() + ". "
                    + entry.getValue().getName());
        }

        return result;
    }

    public static synchronized ChatRoom getRoom(int number) {
        return rooms.get(number);
    }
}
