import java.util.Scanner;

public class LibraryDelete {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj ISBN");
        String isbn = scanner.nextLine();

        BookDao bookDao = new BookDao();
        bookDao.deleteByIsbn(isbn);
        System.out.println("Usunięto");
    }
}
