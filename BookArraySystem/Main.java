import java.util.Scanner;


//ใช้ Array of Objects เพื่อเก็บข้อมูลหนังสือ


public class Main {


    // กำหนดให้คลังเก็บหนังสือได้สูงสุด 10 รายการ
    private static final int MAX_BOOKS = 10;


    // Array สำหรับเก็บ Object หนังสือได้สูงสุด 10 รายการ
    private static final Book[] books = new Book[MAX_BOOKS];


    // จำนวนรายการหนังสือที่ถูกเพิ่มลงใน Array แล้ว
    private static int count = 0;
    private static final Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        int choice;


        // แสดงเมนูซ้ำจนกว่าผู้ใช้จะเลือก 0
        do {
            showMenu();
            choice = readInteger("เลือกเมนู: ");


            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    showAllBooks();
                    break;
                case 3:
                    searchBook();
                    break;
                case 4:
                    updateStock();
                    break;
                case 5:
                    deleteBook();
                    break;
                case 6:
                    showSummary();
                    break;
                case 0:
                    System.out.println("ออกจากโปรแกรมเรียบร้อย");
                    break;
                default:
                    System.out.println("กรุณาเลือกเมนู 0-6");
            }
        } while (choice != 0);


        scanner.close();
    }


    // แสดงรายการเมนูหลัก //
    private static void showMenu() {
        System.out.println("\n===== ระบบจัดการคลังหนังสือเรียน =====");
        System.out.println("1. เพิ่มหนังสือ");
        System.out.println("2. แสดงหนังสือทั้งหมด");
        System.out.println("3. ค้นหาหนังสือด้วยรหัส");
        System.out.println("4. ปรับจำนวนหนังสือคงเหลือ");
        System.out.println("5. ลบหนังสือด้วยรหัส");
        System.out.println("6. สรุปข้อมูลคลังหนังสือ");
        System.out.println("0. ออกจากโปรแกรม");
    }


    // รับข้อมูลหนังสือและเก็บลงใน Array ตำแหน่ง count //
    private static void addBook() {
        if (count == MAX_BOOKS) {
            System.out.println("คลังหนังสือเต็ม ไม่สามารถเพิ่มข้อมูลได้");
            return;
        }


        int id = readInteger("กรอกรหัสหนังสือ: ");


        // ไม่อนุญาตให้ใช้รหัสหนังสือซ้ำกัน
        if (findBookIndex(id) != -1) {
            System.out.println("มีรหัสหนังสือนี้อยู่ในระบบแล้ว");
            return;
        }


        System.out.print("กรอกชื่อวิชา: ");
        String name = scanner.nextLine().trim();
        int quantity = readNonNegativeInteger("กรอกจำนวนหนังสือ: ");


        // สร้าง Object Book แล้วเก็บไว้ใน Array ตำแหน่ง count
        books[count] = new Book(id, name, quantity);
        count++;


        System.out.println("เพิ่มหนังสือเรียบร้อย");
    }


    // ใช้ for Loop วนแสดงข้อมูลที่เก็บอยู่ใน Array //
    private static void showAllBooks() {
        if (count == 0) {
            System.out.println("ยังไม่มีหนังสือในระบบ");
            return;
        }


        System.out.println("\n----- รายการหนังสือทั้งหมด -----");
        System.out.printf("%-10s %-25s %s%n", "รหัส", "ชื่อวิชา", "จำนวน");


        for (int i = 0; i < count; i++) {
            System.out.printf("%-10d %-25s %d เล่ม%n",
                    books[i].getId(), books[i].getName(), books[i].getQuantity());
        }
    }


    // รับรหัส แล้วค้นหาข้อมูลใน Array //
    private static void searchBook() {
        int id = readInteger("กรอกรหัสหนังสือที่ต้องการค้นหา: ");
        int index = findBookIndex(id);


        if (index == -1) {
            System.out.println("ไม่พบหนังสือรหัส " + id);
        } else {
            System.out.println("พบหนังสือ");
            System.out.println("รหัส: " + books[index].getId());
            System.out.println("ชื่อวิชา: " + books[index].getName());
            System.out.println("จำนวน: " + books[index].getQuantity() + " เล่ม");
        }
    }


    // เพิ่มหรือลดจำนวนคงเหลือของหนังสือที่ค้นพบ //
    private static void updateStock() {
        int id = readInteger("กรอกรหัสหนังสือที่ต้องการปรับจำนวน: ");
        int index = findBookIndex(id);


        if (index == -1) {
            System.out.println("ไม่พบหนังสือรหัส " + id);
            return;
        }


        Book book = books[index];
        System.out.println("ชื่อวิชา: " + book.getName());
        System.out.println("จำนวนปัจจุบัน: " + book.getQuantity() + " เล่ม");
        System.out.println("1. รับหนังสือเข้า");
        System.out.println("2. เบิกหนังสือออก");
        int choice = readInteger("เลือกการทำงาน: ");


        if (choice == 1) {
            int amount = readPositiveInteger("กรอกจำนวนที่รับเข้า: ");
            book.setQuantity(book.getQuantity() + amount);
            System.out.println("รับหนังสือเข้าเรียบร้อย");
        } else if (choice == 2) {
            int amount = readPositiveInteger("กรอกจำนวนที่เบิกออก: ");
            if (amount > book.getQuantity()) {
                System.out.println("จำนวนหนังสือคงเหลือไม่เพียงพอ");
                return;
            }
            book.setQuantity(book.getQuantity() - amount);
            System.out.println("เบิกหนังสือออกเรียบร้อย");
        } else {
            System.out.println("กรุณาเลือก 1 หรือ 2");
            return;
        }


        System.out.println("จำนวนคงเหลือใหม่: " + book.getQuantity() + " เล่ม");
    }


    // ลบหนังสือโดยเลื่อนสมาชิกด้านหลังมาทับ แล้วล้างช่องสุดท้ายเป็น null //
    private static void deleteBook() {
        int id = readInteger("กรอกรหัสหนังสือที่ต้องการลบ: ");
        int deleteIndex = findBookIndex(id);


        if (deleteIndex == -1) {
            System.out.println("ไม่พบหนังสือรหัส " + id);
            return;
        }


        String deletedName = books[deleteIndex].getName();
        for (int i = deleteIndex; i < count - 1; i++) {
            books[i] = books[i + 1];
        }


        books[count - 1] = null;
        count--;
        System.out.println("ลบหนังสือวิชา " + deletedName + " เรียบร้อย");
    }


    // ใช้ Loop รวมจำนวนหนังสือทุกวิชาและนับรายการที่ใกล้หมด //
    private static void showSummary() {
        int totalQuantity = 0;
        int lowStockCount = 0;


        for (int i = 0; i < count; i++) {
            totalQuantity += books[i].getQuantity();
            if (books[i].getQuantity() <= 5) {
                lowStockCount++;
            }
        }


        System.out.println("\n----- สรุปข้อมูลคลังหนังสือ -----");
        System.out.println("จำนวนวิชาในระบบ: " + count + " รายการ");
        System.out.println("จำนวนหนังสือรวม: " + totalQuantity + " เล่ม");
        System.out.println("รายการที่เหลือไม่เกิน 5 เล่ม: " + lowStockCount + " รายการ");
    }


    /**
     * ค้นหา Index ของหนังสือจากรหัส
     * คืนค่า -1 เมื่อค้นหาไม่พบ
     */
    private static int findBookIndex(int id) {
        for (int i = 0; i < count; i++) {
            if (books[i].getId() == id) {
                return i;
            }
        }
        return -1;
    }


    // รับจำนวนเต็ม และป้องกันโปรแกรมหยุดเมื่อผู้ใช้กรอกข้อความ //
    private static int readInteger(String message) {
        while (true) {
            System.out.print(message);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("กรุณากรอกเป็นจำนวนเต็ม");
            }
        }
    }


    // รับจำนวนเต็มที่มีค่าตั้งแต่ 0 ขึ้นไป //
    private static int readNonNegativeInteger(String message) {
        while (true) {
            int value = readInteger(message);
            if (value >= 0) {
                return value;
            }
            System.out.println("จำนวนหนังสือต้องไม่ติดลบ");
        }
    }


    // รับจำนวนเต็มที่มีค่ามากกว่า 0
    private static int readPositiveInteger(String message) {
        while (true) {
            int value = readInteger(message);
            if (value > 0) {
                return value;
            }
            System.out.println("จำนวนต้องมากกว่า 0");
        }
    }
}

