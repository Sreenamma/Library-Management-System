import java.io.*;
import java.util.*;

public class Main1 {

    private static final Scanner scanner = new Scanner(System.in);

    private static final List<Book> books = new ArrayList<>();
    private static final List<Member> members = new ArrayList<>();
    private static final List<IssueRecord> issueRecords = new ArrayList<>();

    private static int bookIdCounter = 1001;
    private static int memberIdCounter = 2001;
    private static int issueIdCounter = 1;

    private static final String DATA_FILE = "library_data.dat";

    // ============================================================
    // MAIN
    // ============================================================

    public static void main(String[] args) {

        loadData();

        System.out.println("==============================================");
        System.out.println("          LIBRARY MANAGEMENT SYSTEM");
        System.out.println("==============================================");

        while (true) {

            showMainMenu();

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    bookManagement();
                    break;

                case 2:
                    memberManagement();
                    break;

                case 3:
                    issueReturnManagement();
                    break;

                case 4:
                    searchAndFilter();
                    break;

                case 5:
                    showLibraryStatistics();
                    break;

                case 6:
                    saveData();
                    System.out.println("Data saved successfully.");
                    break;

                case 0:
                    saveData();
                    System.out.println("\nData saved successfully.");
                    System.out.println(
                            "Thank you for using Library Management System."
                    );
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println(
                            "Invalid choice. Please try again."
                    );
            }
        }
    }

    // ============================================================
    // MAIN MENU
    // ============================================================

    private static void showMainMenu() {

        System.out.println("\n==============================================");
        System.out.println("                 MAIN MENU");
        System.out.println("==============================================");
        System.out.println("1. Book Management");
        System.out.println("2. Member Management");
        System.out.println("3. Issue & Return Books");
        System.out.println("4. Search & Filter");
        System.out.println("5. Library Statistics");
        System.out.println("6. Save Data");
        System.out.println("0. Exit");
        System.out.println("==============================================");
    }

    // ============================================================
    // BOOK MANAGEMENT
    // ============================================================

    private static void bookManagement() {

        while (true) {

            System.out.println("\n==============================================");
            System.out.println("             BOOK MANAGEMENT");
            System.out.println("==============================================");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. View Book by ID");
            System.out.println("4. Update Book");
            System.out.println("5. Delete Book");
            System.out.println("0. Back");
            System.out.println("==============================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    addBook();
                    break;

                case 2:
                    viewAllBooks();
                    break;

                case 3:
                    viewBookById();
                    break;

                case 4:
                    updateBook();
                    break;

                case 5:
                    deleteBook();
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // ============================================================
    // ADD BOOK
    // ============================================================

    private static void addBook() {

        System.out.println("\n---------- ADD BOOK ----------");

        String title = readString("Book Title: ");
        String author = readString("Author: ");
        String category = readString("Category: ");
        String isbn = readString("ISBN: ");
        int quantity = readInt("Quantity: ");

        if (quantity < 1) {
            System.out.println(
                    "Quantity must be at least 1."
            );
            return;
        }

        Book book = new Book(
                bookIdCounter++,
                title,
                author,
                category,
                isbn,
                quantity,
                quantity
        );

        books.add(book);

        saveData();

        System.out.println("\nBook added successfully!");
        System.out.println(
                "Book ID: " + book.getId()
        );
    }

    // ============================================================
    // VIEW ALL BOOKS
    // ============================================================

    private static void viewAllBooks() {

        System.out.println(
                "\n---------- ALL BOOKS ----------"
        );

        if (books.isEmpty()) {

            System.out.println(
                    "No books found."
            );

            return;
        }

        System.out.printf(
                "%-7s %-25s %-20s %-15s %-18s %-10s %-10s%n",
                "ID",
                "Title",
                "Author",
                "Category",
                "ISBN",
                "Quantity",
                "Available"
        );

        System.out.println(
                "------------------------------------------------------------------------------------------------"
        );

        for (Book book : books) {

            System.out.printf(
                    "%-7d %-25s %-20s %-15s %-18s %-10d %-10d%n",
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getCategory(),
                    book.getIsbn(),
                    book.getQuantity(),
                    book.getAvailableQuantity()
            );
        }
    }

    // ============================================================
    // VIEW BOOK BY ID
    // ============================================================

    private static void viewBookById() {

        int id = readInt(
                "Enter Book ID: "
        );

        Book book = findBookById(id);

        if (book == null) {

            System.out.println(
                    "Book not found."
            );

            return;
        }

        displayBookDetails(book);
    }

    // ============================================================
    // DISPLAY BOOK DETAILS
    // ============================================================

    private static void displayBookDetails(
            Book book
    ) {

        System.out.println(
                "\n=============================================="
        );

        System.out.println(
                "               BOOK DETAILS"
        );

        System.out.println(
                "=============================================="
        );

        System.out.println(
                "Book ID          : "
                        + book.getId()
        );

        System.out.println(
                "Title            : "
                        + book.getTitle()
        );

        System.out.println(
                "Author           : "
                        + book.getAuthor()
        );

        System.out.println(
                "Category         : "
                        + book.getCategory()
        );

        System.out.println(
                "ISBN             : "
                        + book.getIsbn()
        );

        System.out.println(
                "Total Quantity   : "
                        + book.getQuantity()
        );

        System.out.println(
                "Available Books  : "
                        + book.getAvailableQuantity()
        );

        System.out.println(
                "=============================================="
        );
    }

    // ============================================================
    // UPDATE BOOK
    // ============================================================

    private static void updateBook() {

        int id = readInt(
                "Enter Book ID to update: "
        );

        Book book = findBookById(id);

        if (book == null) {

            System.out.println(
                    "Book not found."
            );

            return;
        }

        String title = readString(
                "New Title: "
        );

        String author = readString(
                "New Author: "
        );

        String category = readString(
                "New Category: "
        );

        String isbn = readString(
                "New ISBN: "
        );

        int quantity = readInt(
                "New Total Quantity: "
        );

        if (quantity < 1) {

            System.out.println(
                    "Quantity must be at least 1."
            );

            return;
        }

        int issuedBooks =
                book.getQuantity()
                        - book.getAvailableQuantity();

        if (quantity < issuedBooks) {

            System.out.println(
                    "Cannot reduce quantity below issued copies."
            );

            return;
        }

        book.setTitle(title);
        book.setAuthor(author);
        book.setCategory(category);
        book.setIsbn(isbn);
        book.setQuantity(quantity);
        book.setAvailableQuantity(
                quantity - issuedBooks
        );

        saveData();

        System.out.println(
                "Book updated successfully."
        );
    }

    // ============================================================
    // DELETE BOOK
    // ============================================================

    private static void deleteBook() {

        int id = readInt(
                "Enter Book ID to delete: "
        );

        Book book = findBookById(id);

        if (book == null) {

            System.out.println(
                    "Book not found."
            );

            return;
        }

        if (book.getAvailableQuantity()
                != book.getQuantity()) {

            System.out.println(
                    "Cannot delete this book because copies are issued."
            );

            return;
        }

        String confirmation =
                readString(
                        "Are you sure? (yes/no): "
                );

        if (confirmation.equalsIgnoreCase("yes")) {

            books.remove(book);

            saveData();

            System.out.println(
                    "Book deleted successfully."
            );

        } else {

            System.out.println(
                    "Delete operation cancelled."
            );
        }
    }

    // ============================================================
    // MEMBER MANAGEMENT
    // ============================================================

    private static void memberManagement() {

        while (true) {

            System.out.println(
                    "\n=============================================="
            );

            System.out.println(
                    "            MEMBER MANAGEMENT"
            );

            System.out.println(
                    "=============================================="
            );

            System.out.println(
                    "1. Add Member"
            );

            System.out.println(
                    "2. View All Members"
            );

            System.out.println(
                    "3. View Member by ID"
            );

            System.out.println(
                    "4. Update Member"
            );

            System.out.println(
                    "5. Delete Member"
            );

            System.out.println(
                    "0. Back"
            );

            System.out.println(
                    "=============================================="
            );

            int choice =
                    readInt(
                            "Enter your choice: "
                    );

            switch (choice) {

                case 1:
                    addMember();
                    break;

                case 2:
                    viewAllMembers();
                    break;

                case 3:
                    viewMemberById();
                    break;

                case 4:
                    updateMember();
                    break;

                case 5:
                    deleteMember();
                    break;

                case 0:
                    return;

                default:
                    System.out.println(
                            "Invalid choice."
                    );
            }
        }
    }

    // ============================================================
    // ADD MEMBER
    // ============================================================

    private static void addMember() {

        System.out.println(
                "\n---------- ADD MEMBER ----------"
        );

        String name =
                readString(
                        "Member Name: "
                );

        String email =
                readString(
                        "Email: "
                );

        String phone =
                readString(
                        "Phone: "
                );

        String address =
                readString(
                        "Address: "
                );

        Member member =
                new Member(
                        memberIdCounter++,
                        name,
                        email,
                        phone,
                        address
                );

        members.add(member);

        saveData();

        System.out.println(
                "\nMember added successfully!"
        );

        System.out.println(
                "Member ID: "
                        + member.getId()
        );
    }

    // ============================================================
    // VIEW ALL MEMBERS
    // ============================================================

    private static void viewAllMembers() {

        System.out.println(
                "\n---------- ALL MEMBERS ----------"
        );

        if (members.isEmpty()) {

            System.out.println(
                    "No members found."
            );

            return;
        }

        System.out.printf(
                "%-8s %-25s %-28s %-15s %-30s%n",
                "ID",
                "Name",
                "Email",
                "Phone",
                "Address"
        );

        System.out.println(
                "------------------------------------------------------------------------------------------------"
        );

        for (Member member : members) {

            System.out.printf(
                    "%-8d %-25s %-28s %-15s %-30s%n",
                    member.getId(),
                    member.getName(),
                    member.getEmail(),
                    member.getPhone(),
                    member.getAddress()
            );
        }
    }

    // ============================================================
    // VIEW MEMBER BY ID
    // ============================================================

    private static void viewMemberById() {

        int id =
                readInt(
                        "Enter Member ID: "
                );

        Member member =
                findMemberById(id);

        if (member == null) {

            System.out.println(
                    "Member not found."
            );

            return;
        }

        displayMemberDetails(member);
    }

    // ============================================================
    // MEMBER DETAILS
    // ============================================================

    private static void displayMemberDetails(
            Member member
    ) {

        System.out.println(
                "\n=============================================="
        );

        System.out.println(
                "              MEMBER DETAILS"
        );

        System.out.println(
                "=============================================="
        );

        System.out.println(
                "Member ID : "
                        + member.getId()
        );

        System.out.println(
                "Name      : "
                        + member.getName()
        );

        System.out.println(
                "Email     : "
                        + member.getEmail()
        );

        System.out.println(
                "Phone     : "
                        + member.getPhone()
        );

        System.out.println(
                "Address   : "
                        + member.getAddress()
        );

        System.out.println(
                "=============================================="
        );
    }

    // ============================================================
    // UPDATE MEMBER
    // ============================================================

    private static void updateMember() {

        int id =
                readInt(
                        "Enter Member ID to update: "
                );

        Member member =
                findMemberById(id);

        if (member == null) {

            System.out.println(
                    "Member not found."
            );

            return;
        }

        String name =
                readString(
                        "New Name: "
                );

        String email =
                readString(
                        "New Email: "
                );

        String phone =
                readString(
                        "New Phone: "
                );

        String address =
                readString(
                        "New Address: "
                );

        member.setName(name);
        member.setEmail(email);
        member.setPhone(phone);
        member.setAddress(address);

        saveData();

        System.out.println(
                "Member updated successfully."
        );
    }

    // ============================================================
    // DELETE MEMBER
    // ============================================================

    private static void deleteMember() {

        int id =
                readInt(
                        "Enter Member ID to delete: "
                );

        Member member =
                findMemberById(id);

        if (member == null) {

            System.out.println(
                    "Member not found."
            );

            return;
        }

        for (IssueRecord record :
                issueRecords) {

            if (record.getMemberId() == id
                    && record.getStatus()
                    .equals("ISSUED")) {

                System.out.println(
                        "Cannot delete member because a book is issued."
                );

                return;
            }
        }

        String confirmation =
                readString(
                        "Are you sure? (yes/no): "
                );

        if (confirmation.equalsIgnoreCase("yes")) {

            members.remove(member);

            saveData();

            System.out.println(
                    "Member deleted successfully."
            );

        } else {

            System.out.println(
                    "Delete operation cancelled."
            );
        }
    }

    // ============================================================
    // ISSUE AND RETURN MENU
    // ============================================================

    private static void issueReturnManagement() {

        while (true) {

            System.out.println(
                    "\n=============================================="
            );

            System.out.println(
                    "          ISSUE & RETURN MANAGEMENT"
            );

            System.out.println(
                    "=============================================="
            );

            System.out.println(
                    "1. Issue Book"
            );

            System.out.println(
                    "2. Return Book"
            );

            System.out.println(
                    "3. View All Issue Records"
            );

            System.out.println(
                    "4. View Currently Issued Books"
            );

            System.out.println(
                    "0. Back"
            );

            System.out.println(
                    "=============================================="
            );

            int choice =
                    readInt(
                            "Enter your choice: "
                    );

            switch (choice) {

                case 1:
                    issueBook();
                    break;

                case 2:
                    returnBook();
                    break;

                case 3:
                    viewIssueRecords();
                    break;

                case 4:
                    viewIssuedBooks();
                    break;

                case 0:
                    return;

                default:
                    System.out.println(
                            "Invalid choice."
                    );
            }
        }
    }

    // ============================================================
    // ISSUE BOOK
    // ============================================================

    private static void issueBook() {

        System.out.println(
                "\n---------- ISSUE BOOK ----------"
        );

        int bookId =
                readInt(
                        "Book ID: "
                );

        Book book =
                findBookById(bookId);

        if (book == null) {

            System.out.println(
                    "Book not found."
            );

            return;
        }

        if (book.getAvailableQuantity() <= 0) {

            System.out.println(
                    "No copies available."
            );

            return;
        }

        int memberId =
                readInt(
                        "Member ID: "
                );

        Member member =
                findMemberById(memberId);

        if (member == null) {

            System.out.println(
                    "Member not found."
            );

            return;
        }

        if (hasActiveIssue(
                memberId,
                bookId
        )) {

            System.out.println(
                    "This member already has this book."
            );

            return;
        }

        String issueDate =
                readString(
                        "Issue Date (YYYY-MM-DD): "
                );

        String dueDate =
                readString(
                        "Due Date (YYYY-MM-DD): "
                );

        IssueRecord record =
                new IssueRecord(
                        issueIdCounter++,
                        bookId,
                        memberId,
                        issueDate,
                        dueDate,
                        "",
                        "ISSUED"
                );

        issueRecords.add(record);

        book.setAvailableQuantity(
                book.getAvailableQuantity() - 1
        );

        saveData();

        System.out.println(
                "\nBook issued successfully!"
        );

        System.out.println(
                "Issue ID: "
                        + record.getId()
        );
    }

    // ============================================================
    // RETURN BOOK
    // ============================================================

    private static void returnBook() {

        System.out.println(
                "\n---------- RETURN BOOK ----------"
        );

        int issueId =
                readInt(
                        "Issue ID: "
                );

        IssueRecord record =
                findIssueRecordById(issueId);

        if (record == null) {

            System.out.println(
                    "Issue record not found."
            );

            return;
        }

        if (!record.getStatus()
                .equals("ISSUED")) {

            System.out.println(
                    "This book has already been returned."
            );

            return;
        }

        String returnDate =
                readString(
                        "Return Date (YYYY-MM-DD): "
                );

        record.setReturnDate(
                returnDate
        );

        record.setStatus(
                "RETURNED"
        );

        Book book =
                findBookById(
                        record.getBookId()
                );

        if (book != null) {

            book.setAvailableQuantity(
                    book.getAvailableQuantity() + 1
            );
        }

        saveData();

        System.out.println(
                "Book returned successfully."
        );
    }

    // ============================================================
    // VIEW ISSUE RECORDS
    // ============================================================

    private static void viewIssueRecords() {

        System.out.println(
                "\n---------- ISSUE RECORDS ----------"
        );

        if (issueRecords.isEmpty()) {

            System.out.println(
                    "No issue records found."
            );

            return;
        }

        System.out.printf(
                "%-9s %-10s %-25s %-10s %-20s %-15s %-12s%n",
                "Issue ID",
                "Book ID",
                "Book",
                "Member ID",
                "Member",
                "Issue Date",
                "Status"
        );

        System.out.println(
                "------------------------------------------------------------------------------------------------"
        );

        for (IssueRecord record :
                issueRecords) {

            Book book =
                    findBookById(
                            record.getBookId()
                    );

            Member member =
                    findMemberById(
                            record.getMemberId()
                    );

            String bookTitle =
                    book != null
                            ? book.getTitle()
                            : "Unknown";

            String memberName =
                    member != null
                            ? member.getName()
                            : "Unknown";

            System.out.printf(
                    "%-9d %-10d %-25s %-10d %-20s %-15s %-12s%n",
                    record.getId(),
                    record.getBookId(),
                    bookTitle,
                    record.getMemberId(),
                    memberName,
                    record.getIssueDate(),
                    record.getStatus()
            );
        }
    }

    // ============================================================
    // VIEW ISSUED BOOKS
    // ============================================================

    private static void viewIssuedBooks() {

        System.out.println(
                "\n---------- CURRENTLY ISSUED BOOKS ----------"
        );

        boolean found = false;

        for (IssueRecord record :
                issueRecords) {

            if (record.getStatus()
                    .equals("ISSUED")) {

                Book book =
                        findBookById(
                                record.getBookId()
                        );

                Member member =
                        findMemberById(
                                record.getMemberId()
                        );

                if (book != null
                        && member != null) {

                    System.out.println(
                            "\nIssue ID   : "
                                    + record.getId()
                    );

                    System.out.println(
                            "Book       : "
                                    + book.getTitle()
                    );

                    System.out.println(
                            "Member     : "
                                    + member.getName()
                    );

                    System.out.println(
                            "Issue Date : "
                                    + record.getIssueDate()
                    );

                    System.out.println(
                            "Due Date   : "
                                    + record.getDueDate()
                    );

                    found = true;
                }
            }
        }

        if (!found) {

            System.out.println(
                    "No books are currently issued."
            );
        }
    }

    // ============================================================
    // SEARCH AND FILTER
    // ============================================================

    private static void searchAndFilter() {

        while (true) {

            System.out.println(
                    "\n=============================================="
            );

            System.out.println(
                    "              SEARCH & FILTER"
            );

            System.out.println(
                    "=============================================="
            );

            System.out.println(
                    "1. Search Book by Title"
            );

            System.out.println(
                    "2. Search Book by Author"
            );

            System.out.println(
                    "3. Search Book by Category"
            );

            System.out.println(
                    "4. Search Member by Name"
            );

            System.out.println(
                    "5. Search Member by Email"
            );

            System.out.println(
                    "6. Show Available Books"
            );

            System.out.println(
                    "7. Show Issued Books"
            );

            System.out.println(
                    "0. Back"
            );

            System.out.println(
                    "=============================================="
            );

            int choice =
                    readInt(
                            "Enter your choice: "
                    );

            switch (choice) {

                case 1:
                    searchBookByTitle();
                    break;

                case 2:
                    searchBookByAuthor();
                    break;

                case 3:
                    searchBookByCategory();
                    break;

                case 4:
                    searchMemberByName();
                    break;

                case 5:
                    searchMemberByEmail();
                    break;

                case 6:
                    showAvailableBooks();
                    break;

                case 7:
                    viewIssuedBooks();
                    break;

                case 0:
                    return;

                default:
                    System.out.println(
                            "Invalid choice."
                    );
            }
        }
    }

    // ============================================================
    // SEARCH BOOK TITLE
    // ============================================================

    private static void searchBookByTitle() {

        String keyword =
                readString(
                        "Enter title: "
                );

        boolean found = false;

        for (Book book : books) {

            if (book.getTitle()
                    .toLowerCase()
                    .contains(
                            keyword.toLowerCase()
                    )) {

                displayBookDetails(book);

                found = true;
            }
        }

        if (!found) {

            System.out.println(
                    "No matching books found."
            );
        }
    }

    // ============================================================
    // SEARCH BOOK AUTHOR
    // ============================================================

    private static void searchBookByAuthor() {

        String keyword =
                readString(
                        "Enter author: "
                );

        boolean found = false;

        for (Book book : books) {

            if (book.getAuthor()
                    .toLowerCase()
                    .contains(
                            keyword.toLowerCase()
                    )) {

                displayBookDetails(book);

                found = true;
            }
        }

        if (!found) {

            System.out.println(
                    "No matching books found."
            );
        }
    }

    // ============================================================
    // SEARCH BOOK CATEGORY
    // ============================================================

    private static void searchBookByCategory() {

        String keyword =
                readString(
                        "Enter category: "
                );

        boolean found = false;

        for (Book book : books) {

            if (book.getCategory()
                    .toLowerCase()
                    .contains(
                            keyword.toLowerCase()
                    )) {

                displayBookDetails(book);

                found = true;
            }
        }

        if (!found) {

            System.out.println(
                    "No matching books found."
            );
        }
    }

    // ============================================================
    // SEARCH MEMBER NAME
    // ============================================================

    private static void searchMemberByName() {

        String keyword =
                readString(
                        "Enter member name: "
                );

        boolean found = false;

        for (Member member : members) {

            if (member.getName()
                    .toLowerCase()
                    .contains(
                            keyword.toLowerCase()
                    )) {

                displayMemberDetails(member);

                found = true;
            }
        }

        if (!found) {

            System.out.println(
                    "No matching members found."
            );
        }
    }

    // ============================================================
    // SEARCH MEMBER EMAIL
    // ============================================================

    private static void searchMemberByEmail() {

        String keyword =
                readString(
                        "Enter email: "
                );

        boolean found = false;

        for (Member member : members) {

            if (member.getEmail()
                    .toLowerCase()
                    .contains(
                            keyword.toLowerCase()
                    )) {

                displayMemberDetails(member);

                found = true;
            }
        }

        if (!found) {

            System.out.println(
                    "No matching members found."
            );
        }
    }

    // ============================================================
    // AVAILABLE BOOKS
    // ============================================================

    private static void showAvailableBooks() {

        System.out.println(
                "\n---------- AVAILABLE BOOKS ----------"
        );

        boolean found = false;

        for (Book book : books) {

            if (book.getAvailableQuantity() > 0) {

                displayBookDetails(book);

                found = true;
            }
        }

        if (!found) {

            System.out.println(
                    "No books are currently available."
            );
        }
    }

    // ============================================================
    // STATISTICS
    // ============================================================

    private static void showLibraryStatistics() {

        int totalCopies = 0;
        int availableCopies = 0;

        for (Book book : books) {

            totalCopies += book.getQuantity();

            availableCopies +=
                    book.getAvailableQuantity();
        }

        int issuedCopies =
                totalCopies - availableCopies;

        int activeIssues = 0;

        for (IssueRecord record :
                issueRecords) {

            if (record.getStatus()
                    .equals("ISSUED")) {

                activeIssues++;
            }
        }

        System.out.println(
                "\n=============================================="
        );

        System.out.println(
                "            LIBRARY STATISTICS"
        );

        System.out.println(
                "=============================================="
        );

        System.out.println(
                "Book Titles       : "
                        + books.size()
        );

        System.out.println(
                "Total Book Copies : "
                        + totalCopies
        );

        System.out.println(
                "Available Copies  : "
                        + availableCopies
        );

        System.out.println(
                "Issued Copies     : "
                        + issuedCopies
        );

        System.out.println(
                "Members           : "
                        + members.size()
        );

        System.out.println(
                "Issue Records     : "
                        + issueRecords.size()
        );

        System.out.println(
                "Active Issues     : "
                        + activeIssues
        );

        System.out.println(
                "=============================================="
        );
    }

    // ============================================================
    // FIND BOOK
    // ============================================================

    private static Book findBookById(int id) {

        for (Book book : books) {

            if (book.getId() == id) {

                return book;
            }
        }

        return null;
    }

    // ============================================================
    // FIND MEMBER
    // ============================================================

    private static Member findMemberById(int id) {

        for (Member member : members) {

            if (member.getId() == id) {

                return member;
            }
        }

        return null;
    }

    // ============================================================
    // FIND ISSUE
    // ============================================================

    private static IssueRecord findIssueRecordById(
            int id
    ) {

        for (IssueRecord record :
                issueRecords) {

            if (record.getId() == id) {

                return record;
            }
        }

        return null;
    }

    // ============================================================
    // CHECK ACTIVE ISSUE
    // ============================================================

    private static boolean hasActiveIssue(
            int memberId,
            int bookId
    ) {

        for (IssueRecord record :
                issueRecords) {

            if (record.getMemberId() == memberId
                    && record.getBookId() == bookId
                    && record.getStatus()
                    .equals("ISSUED")) {

                return true;
            }
        }

        return false;
    }

    // ============================================================
    // INPUT METHODS
    // ============================================================

    private static String readString(
            String message
    ) {

        System.out.print(message);

        return scanner.nextLine().trim();
    }

    private static int readInt(
            String message
    ) {

        while (true) {

            try {

                System.out.print(message);

                return Integer.parseInt(
                        scanner.nextLine().trim()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Exception: Please enter a valid number."
                );
            }
        }
    }

    // ============================================================
    // SAVE DATA
    // ============================================================

    private static void saveData() {

        try (
                ObjectOutputStream output =
                        new ObjectOutputStream(
                                new FileOutputStream(
                                        DATA_FILE
                                )
                        )
        ) {

            output.writeObject(
                    new ArrayList<>(books)
            );

            output.writeObject(
                    new ArrayList<>(members)
            );

            output.writeObject(
                    new ArrayList<>(issueRecords)
            );

            output.writeInt(
                    bookIdCounter
            );

            output.writeInt(
                    memberIdCounter
            );

            output.writeInt(
                    issueIdCounter
            );

        } catch (IOException e) {

            System.out.println(
                    "Error saving data: "
                            + e.getMessage()
            );
        }
    }

    // ============================================================
    // LOAD DATA
    // ============================================================

    @SuppressWarnings("unchecked")
    private static void loadData() {

        File file =
                new File(DATA_FILE);

        if (!file.exists()) {

            return;
        }

        try (
                ObjectInputStream input =
                        new ObjectInputStream(
                                new FileInputStream(
                                        file
                                )
                        )
        ) {

            books.addAll(
                    (ArrayList<Book>)
                            input.readObject()
            );

            members.addAll(
                    (ArrayList<Member>)
                            input.readObject()
            );

            issueRecords.addAll(
                    (ArrayList<IssueRecord>)
                            input.readObject()
            );

            bookIdCounter =
                    input.readInt();

            memberIdCounter =
                    input.readInt();

            issueIdCounter =
                    input.readInt();

        } catch (Exception e) {

            System.out.println(
                    "Exception while loading data."
            );

            System.out.println(
                    "Starting with fresh data."
            );
        }
    }
}


// ============================================================
// BOOK CLASS
// ============================================================

class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String title;
    private String author;
    private String category;
    private String isbn;
    private int quantity;
    private int availableQuantity;

    public Book(
            int id,
            String title,
            String author,
            String category,
            String isbn,
            int quantity,
            int availableQuantity
    ) {

        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isbn = isbn;
        this.quantity = quantity;
        this.availableQuantity =
                availableQuantity;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setAvailableQuantity(
            int availableQuantity
    ) {

        this.availableQuantity =
                availableQuantity;
    }
}


// ============================================================
// MEMBER CLASS
// ============================================================

class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String email;
    private String phone;
    private String address;

    public Member(
            int id,
            String name,
            String email,
            String phone,
            String address
    ) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}


// ============================================================
// ISSUE RECORD CLASS
// ============================================================

class IssueRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private int bookId;
    private int memberId;
    private String issueDate;
    private String dueDate;
    private String returnDate;
    private String status;

    public IssueRecord(
            int id,
            int bookId,
            int memberId,
            String issueDate,
            String dueDate,
            String returnDate,
            String status
    ) {

        this.id = id;
        this.bookId = bookId;
        this.memberId = memberId;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getBookId() {
        return bookId;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setReturnDate(
            String returnDate
    ) {

        this.returnDate = returnDate;
    }

    public void setStatus(
            String status
    ) {

        this.status = status;
    }
}