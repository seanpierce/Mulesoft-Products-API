package products;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService {

	public static List<Product> getActiveProducts(
		    String jdbcUrl,
		    String user,
		    String password
		) throws SQLException {
		    List<Product> products = new ArrayList<Product>();

        String sql = """
            SELECT
                Id,
                Name,
                Sku,
                Price,
                Active
            FROM Products
            WHERE Active = TRUE
            ORDER BY Id
            """;

        try (
            Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                Product product = new Product(
                    resultSet.getLong("Id"),
                    resultSet.getString("Name"),
                    resultSet.getString("Sku"),
                    resultSet.getBigDecimal("Price"),
                    resultSet.getBoolean("Active")
                );

                products.add(product);
            }
        }

        return products;
    }
}