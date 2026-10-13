package lab3;

public class InventoryProduct {
    private String code;
    private String name;
    private double unitPrice;
    private int quantity;

    public InventoryProduct(String code, String name, double unitPrice, int quantity) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Mã sản phẩm không được rỗng");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Tên sản phẩm không được rỗng");
        }
        if (unitPrice <= 0) {
            throw new IllegalArgumentException("Đơn giá phải lớn hơn 0");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Số lượng không được âm");
        }
        this.code = code.trim();
        this.name = name.trim();
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public double getUnitPrice() { return unitPrice; }
    public int getQuantity() { return quantity; }

    public double getInventoryValue() {
        return unitPrice * quantity;
    }

    public String toCsvLine() {
        return "%s,%s,%.0f,%d".formatted(code, name, unitPrice, quantity);
    }

    @Override
    public String toString() {
        return "Mã: %s | Tên: %s | Đơn giá: %,.0f VND | Số lượng: %d | Giá trị tồn: %,.0f VND"
                .formatted(code, name, unitPrice, quantity, getInventoryValue());
    }
}