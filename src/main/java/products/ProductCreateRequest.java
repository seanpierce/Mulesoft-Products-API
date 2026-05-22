package products;

import java.math.BigDecimal;

public class ProductCreateRequest {

    private String name;
    private String sku;
    private BigDecimal price;
    private Boolean active;

    public ProductCreateRequest() {
    }

    public ProductCreateRequest(
        String name,
        String sku,
        BigDecimal price,
        Boolean active
    ) {
        this.name = name;
        this.sku = sku;
        this.price = price;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}