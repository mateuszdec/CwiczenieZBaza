import java.util.Scanner;

public class LibraryUpdate {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj id książki którą chcesz zaktualizować ");
        long id = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Podaj tytuł ");
        String title = scanner.nextLine();
        System.out.println("Podaj autora ");
        String author = scanner.nextLine();
        System.out.println("Podaj rok wydania ");
        int year = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Podaj numer ISBN ");
        String isbn = scanner.nextLine();

        Book book = new Book(id, title, author, year, isbn);

        BookDao bookDao = new BookDao();
        bookDao.update(book);
    }
}
