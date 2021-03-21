import java.util.Scanner;

public class LibraryRead {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj ISBN");
        String isbn = scanner.nextLine();

        BookDao bookDao = new BookDao();
        Book book = bookDao.findByIsbn(isbn);
        System.out.println(book);
    }
}
