package uz.pdp;

import java.io.*;
import java.util.*;
import java.time.*;

class User implements Serializable {
    private String email;
    private String password;
    private LocalDateTime registrationTime;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.registrationTime = LocalDateTime.now();
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getRegistrationTime() {
        return registrationTime;
    }
}

class Chat implements Serializable {
    private String sender;
    private String message;
    private LocalDateTime timestamp;

    public Chat(String sender, String message) {
        this.sender = sender;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}

public class ChatApp {
    private static final String USER_FILE = "users.dat";
    private static final String CHAT_FILE = "chats.dat";
    private static List<User> users = new ArrayList<>();
    private static List<Chat> chats = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadUsers();
        loadChats();

        boolean running = true;
        while (running) {
            System.out.println("1. Register\n2. Login\n3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }

        saveUsers();
        saveChats();
        scanner.close();
    }

    private static void register() {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        users.add(new User(email, password));
        System.out.println("Registration successful!");
    }

    private static void login() {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                System.out.println("Login successful!");
                chat(user.getEmail());
                return;
            }
        }
        System.out.println("Invalid email or password");
    }

    private static void chat(String sender) {
        boolean chatting = true;
        while (chatting) {
            System.out.print("Enter your message (type 'exit' to end chat): ");
            String message = scanner.nextLine();
            if (message.equalsIgnoreCase("exit")) {
                chatting = false;
            } else {
                chats.add(new Chat(sender, message));
            }
        }
    }

    private static void loadUsers() {
        try {
            FileInputStream fis = new FileInputStream(USER_FILE);
            ObjectInputStream ois = new ObjectInputStream(fis);
            users = (List<User>) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            //e.printStackTrace();
        }
    }

    private static void saveUsers() {
        try {
            FileOutputStream fos = new FileOutputStream(USER_FILE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(users);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadChats() {
        try {
            FileInputStream fis = new FileInputStream(CHAT_FILE);
            ObjectInputStream ois = new ObjectInputStream(fis);
            chats = (List<Chat>) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {

        }
    }

    private static void saveChats() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(CHAT_FILE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(chats);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
