package products;

import java.math.BigDecimal;

public class ProductCreateRequest {
    private String name;
    private String sku;
    private BigDecimal price;
    private Boolean active;

    public ProductCreateRequest() {
    }

    public ProductCreateRequest(String name, String sku, BigDecimal price, Boolean active) {
        this.name = name;
        this.sku = sku;
        this.price = price;
        this.active = active;
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