public class Book {
    private int id;
    private String name;
    private int quantity;


    // สร้าง Object หนังสือจากรหัส ชื่อวิชา และจำนวน //
    public Book(int id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }


    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public int getQuantity() {
        return quantity;
    }


    // กำหนดจำนวนหนังสือคงเหลือ ใช้เมื่อรับเข้าหรือเบิกออก //
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
