package products;

import java.math.BigDecimal;

public class Product {
    private Long id;
    private String name;
    private String sku;
    private BigDecimal price;
    private Boolean active;

    public Product() {
    }

    public Product(Long id, String name, String sku, BigDecimal price, Boolean active) {
        this.id = id;
        this.name = name;
        this.sku = sku;
        this.price = price;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSku() {
        return sku;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Boolean getActive() {
        return active;
    }
}