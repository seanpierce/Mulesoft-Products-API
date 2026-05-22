package config;

public class DatabaseConfig {

    private final String jdbcUrl;
    private final String user;
    private final String password;

    private DatabaseConfig(
        String jdbcUrl,
        String user,
        String password
    ) {
        this.jdbcUrl = jdbcUrl;
        this.user = user;
        this.password = password;
    }

    public static DatabaseConfig of(
        String jdbcUrl,
        String user,
        String password
    ) {
        return new DatabaseConfig(
            jdbcUrl,
            user,
            password
        );
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}