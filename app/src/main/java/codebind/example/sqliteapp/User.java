package codebind.example.sqliteapp;

public class User {
    private static String name;
    private static String email="sdfd";

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        User.name = name;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        User.email = email;
    }
}
