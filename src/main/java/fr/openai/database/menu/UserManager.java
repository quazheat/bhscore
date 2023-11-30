package fr.openai.database.menu;

import com.mongodb.client.MongoCollection;
import fr.openai.database.UsernameProvider;
import fr.openai.starter.uuid.UuidProvider;
import org.bson.Document;
import fr.openai.database.b;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserManager extends UsernameProvider {

    private final MongoCollection<Document> userCollection;
    private final UuidProvider uuidProvider;

    public UserManager() {
        this.userCollection = b.Zxc("users");
        this.uuidProvider = new UuidProvider();
    }

    public void addUser(String username, String uuid, boolean isAdmin, boolean isMod) {
        if (userCollection.find(new Document("uuid", uuid)).limit(1).first() == null) {
            Document newUser = new Document("username", username)
                    .append("uuid", uuid)
                    .append("admin", isAdmin)
                    .append("mod", isMod);

            userCollection.insertOne(newUser);
            System.out.println("Пользователь " + username + " успешно добавлен.");
        } else {
            System.out.println("Пользователь с UUID " + uuid + " уже существует.");
        }
    }

    public boolean removeUserByUsername(String username) {
        Document userToRemove = userCollection.find(new Document("username", username)).limit(1).first();
        if (userToRemove != null) {
            userCollection.deleteOne(userToRemove);
            System.out.println("Пользователь " + username + " успешно удален.");
            return true;
        } else {
            System.out.println("Пользователь " + username + " не найден.");
            return false;
        }
    }

    public boolean removeUserByUUID(String uuid) {
        Document userToRemove = userCollection.find(new Document("uuid", uuid)).limit(1).first();
        if (userToRemove != null) {
            userCollection.deleteOne(userToRemove);
            System.out.println("Пользователь с UUID " + uuid + " успешно удален.");
            return true;
        } else {
            System.out.println("Пользователь с UUID " + uuid + " не найден.");
            return false;
        }
    }


    public void showUserList() {
        List<Document> users = getUsers();
        UUID systemUUID = uuidProvider.getUUID();

        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            System.out.println("User List:");
            for (Document user : users) {
                String username = user.getString("username");
                String uuid = user.getString("uuid");
                boolean isAdmin = user.getBoolean("admin", false);
                boolean isMod = user.getBoolean("mod", false);

                System.out.print("Username: " + username + ", UUID: " + uuid + ", Admin: " + isAdmin + ", Mod: " + isMod + "\n");

                if (isAdmin && uuid.equals(systemUUID.toString())) {
                    System.out.println(" true SUPER_USER");
                    adminStatus = true;
                    modStatus = true;

                }

                if (isMod && uuid.equals(systemUUID.toString())) {
                    System.out.println(" true MODS_ALLOWED");
                    modStatus = true;
                }

            }
        }

    }

    public static boolean adminStatus = false;
    public static boolean modStatus = false;

    public List<Document> getUsers() {
        return userCollection.find().into(new ArrayList<>());
    }

    public List<String> getUsersUuids() {
        List<String> uuids = new ArrayList<>();
        List<Document> users = getUsers();

        for (Document user : users) {
            String uuid = user.getString("uuid");
            uuids.add(uuid);
        }

        return uuids;
    }
}
