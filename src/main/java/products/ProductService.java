package products;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConfig;

public class ProductService {

	public static List<Product> getActiveProducts(DatabaseConfig dbConfig) throws SQLException {
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

		try (Connection connection = getConnection(dbConfig);
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				Product product = new Product(resultSet.getLong("Id"), resultSet.getString("Name"),
						resultSet.getString("Sku"), resultSet.getBigDecimal("Price"), resultSet.getBoolean("Active"));

				products.add(product);
			}
		}

		return products;
	}

	public static Product createProduct(DatabaseConfig dbConfig, ProductCreateRequest request) throws SQLException {

		if (request.getName() == null || request.getName().trim().isEmpty()) {
			throw new IllegalArgumentException("Product name is required.");
		}

		if (request.getSku() == null || request.getSku().trim().isEmpty()) {
			throw new IllegalArgumentException("Product SKU is required.");
		}

		if (request.getPrice() == null) {
			throw new IllegalArgumentException("Product price is required.");
		}

		if (request.getPrice().compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("Product price cannot be negative.");
		}

		String insertSql = """
				INSERT INTO Products (
				    Name,
				    Sku,
				    Price,
				    Active
				)
				VALUES (?, ?, ?, ?)
				""";

		String selectSql = """
				SELECT
				    Id,
				    Name,
				    Sku,
				    Price,
				    Active
				FROM Products
				WHERE Id = ?
				""";

		try (Connection connection = getConnection(dbConfig);
				PreparedStatement insertStatement = connection.prepareStatement(insertSql,
						Statement.RETURN_GENERATED_KEYS)) {
			insertStatement.setString(1, request.getName());
			insertStatement.setString(2, request.getSku());
			insertStatement.setBigDecimal(3, request.getPrice());
			insertStatement.setBoolean(4, request.getActive() != null ? request.getActive() : true);

			insertStatement.executeUpdate();

			try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
				if (!generatedKeys.next()) {
					throw new SQLException("Creating product failed. No generated ID returned.");
				}

				long newId = generatedKeys.getLong(1);

				try (PreparedStatement selectStatement = connection.prepareStatement(selectSql)) {
					selectStatement.setLong(1, newId);

					try (ResultSet resultSet = selectStatement.executeQuery()) {
						if (!resultSet.next()) {
							throw new SQLException("Creating product failed. Inserted product could not be found.");
						}

						return new Product(resultSet.getLong("Id"), resultSet.getString("Name"),
								resultSet.getString("Sku"), resultSet.getBigDecimal("Price"),
								resultSet.getBoolean("Active"));
					}
				}
			}
		}
	}

	private static Connection getConnection(DatabaseConfig dbConfig) throws SQLException {
		return DriverManager.getConnection(dbConfig.getJdbcUrl(), dbConfig.getUser(), dbConfig.getPassword());
	}
}