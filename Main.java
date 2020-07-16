//package src;

import java.util.Scanner;

public class Main {

    static class Book {
        private static final int  ALPHABET_SIZE = 26;

        public Book[] children = new Book[ALPHABET_SIZE];
        public Trie users = new Trie();


        public boolean isEndOfWorld;
        public boolean isReturned;

        public int count;

        public Book() {
            isEndOfWorld = false;
            isReturned = false;

            for (int i = 0; i < ALPHABET_SIZE; i++) {
                children[i] = null;
            }
        }
    }

    static class TrieNode {

        private static final int  ALPHABET_SIZE = 26;

        public TrieNode[] children = new TrieNode[ALPHABET_SIZE];

        public boolean isEndOfWord;

        public int count;

        public TrieNode(){
            isEndOfWord = false;
            for (int i = 0; i < ALPHABET_SIZE; i++)
                children[i] = null;
        }
    }

    static class User {

        private static final int  ALPHABET_SIZE = 26;

        public User[] children = new User[ALPHABET_SIZE];
        public Trie books = new Trie();


        public boolean isEndOfWorld;
        public boolean isOnline;

        public long timeIn;
        public long timeOut;

        public User() {
            isEndOfWorld = false;
            isOnline = true;

            for (int i = 0; i < ALPHABET_SIZE; i++) {
                children[i] = null;
            }
        }
    }

    static class Trie {
        public TrieNode root = new TrieNode();

        int c = 0;

        public  void insert(String key){
            int level;
            int length = key.length();
            int index;

            TrieNode pCrawl = root;

            for (level = 0; level < length; level++){
                index = key.charAt(level) - 'a';
                if (pCrawl.children[index] == null)
                    pCrawl.children[index] = new TrieNode();
                    c++;

                pCrawl = pCrawl.children[index];
            }

            pCrawl.isEndOfWord = true;
        }

        public  boolean search(String key){
            int level;
            int length = key.length();
            int index;
            TrieNode pCrawl = root;

            for (level = 0; level < length; level++)
            {
                index = key.charAt(level) - 'a';

                if (pCrawl.children[index] == null)
                    return false;

                pCrawl = pCrawl.children[index];
            }

            return (pCrawl != null && pCrawl.isEndOfWord);
        }

        public void print(TrieNode root, char[] array, int level){
            if (root.isEndOfWord){
                array[level] = '\0';
                System.out.print(array);
                c--;

                if (c != 0){
                    System.out.print(" ");
                }
            }

            for (int i = 0; i < 26; i++) {
                if (root.children[i] != null){
                    array[level] =  (char) (i + 'a');
                    print(root.children[i], array, level + 1);
                }

            }
        }

        private boolean isEmpty(TrieNode root){

            for (int i = 0; i < 26; i++) {
                if (root.children[i] == null){
                    return false;
                }
            }

            return true;
        }

        public TrieNode delete(TrieNode root, String key, int depth) {

            if (root == null)
                return null;

            if (depth == key.length()) {

                if (root.isEndOfWord)
                    root.isEndOfWord = false;

                if (isEmpty(root)) {
                    root = null;
                }

                return root;
            }

            int index = key.charAt(depth) - 'a';
            root.children[index] = delete(root.children[index], key, depth + 1);

            if (isEmpty(root) && root.isEndOfWord == false) {
                root = null;
            }

            return root;
        }
    }

    static class TrieBook {
        Book root = new Book();

        public void insert(String key, int count){
            int level;
            int length = key.length();
            int index;

            Book pCrawl = root;

            for (level = 0; level < length; level++){
                index = key.charAt(level) - 'a';

                if (pCrawl.children[index] == null){
                    pCrawl.children[index] = new Book();
                    pCrawl.children[index].count = count;
                }

                pCrawl = pCrawl.children[index];
            }

            pCrawl.isEndOfWorld = true;
        }

        public Book search(String key){
            int level;
            int length = key.length();
            int index;
            Book pCrawl = root;

            for (level = 0; level < length; level++){
                index = key.charAt(level) - 'a';

                if (pCrawl.children[index] == null){
                    return null;
                }

                pCrawl = pCrawl.children[index];
            }

            return pCrawl;
        }
    }

    static class TrieUser {
        User root = new User();

        public void insert(String key, int  time){
            int level;
            int length = key.length();
            int index;

            User pCrawl = root;

            for (level = 0; level < length; level++){
                index = key.charAt(level) - 'a';

                if (pCrawl.children[index] == null){
                    pCrawl.children[index] = new User();
                    pCrawl.children[index].timeIn = time;
                }

                pCrawl = pCrawl.children[index];
            }

            pCrawl.isEndOfWorld = true;
        }

        public User search(String key){
            int level;
            int length = key.length();
            int index;
            User pCrawl = root;

            for (level = 0; level < length; level++){
                index = key.charAt(level) - 'a';

                if (pCrawl.children[index] == null){
                    return null;
                }

                pCrawl = pCrawl.children[index];
            }

            return pCrawl;
        }
    }

    static class Controllers {
        TrieUser trieUser = new TrieUser();
        TrieBook trieBook = new TrieBook();

        User user = new User();
        Book book = new Book();

        char[] wordsUser = new char[26];
        char[] wordsbook = new char[26];

        public void arrive(String personName, int time){
            user = trieUser.search(personName);

            if (user == null){
                trieUser.insert(personName, time);
            }else{
                user.isOnline = true;
            }
        }

        public void exit(String personName, int time){
            user = trieUser.search(personName);

            if (user != null){
                user.isOnline = false;
                user.timeOut = time;
            }
        }

        public void isInLib(String personName){
            user = trieUser.search(personName);
            if (user != null){
                System.out.println(user.isOnline ? "YES" : "NO");
            }else{
                System.out.println("NO");
            }
        }

        public void addNewBook(String bookName, int count){
            book = trieBook.search(bookName);

            if (book == null){
                trieBook.insert(bookName, count);
            }else {
                book.count += count;
            }
        }

        public void shouldBring(String bookName, String personName){
            user = trieUser.search(personName);
            book = trieBook.search(bookName);

            if (user != null && book != null){
                if (user.isOnline && book.count > 0){
                    user.books.insert(bookName);
                    book.users.insert(personName);
                    book.count--;
                }
            }
        }

        public void totalTimeInLab(String personName, long startTime, long timeOut){
            user = trieUser.search(personName);
            long max, min;

            if (user != null){
                min = Math.max(startTime, user.timeIn);
                max = Math.min(timeOut, user.timeOut);

                System.out.println(max - min);
            }

        }

        public void allPersonCurrentBooks(String personName){
            user = trieUser.search(personName);
            if (user == null || user.books == null){
                System.out.println("empty");
            }else {
                user.books.print(user.books.root, wordsUser, 0);
            }
        }

        public void allPersonHaveThisBook(String bookName){
            book = trieBook.search(bookName);
            if (book == null || book.users == null){
                System.out.println("empty");
            }else {
                book.users.print(book.users.root, wordsbook, 0);
                //System.out.print("\n");
                //System.out.println();
            }
        }

        public void returnBook(String bookName, String personName){
            user = trieUser.search(personName);
            book = trieBook.search(bookName);

            if (user != null && book != null){
                user.books.delete(user.books.root, bookName, 0);
                book.users.delete(book.users.root, personName, 0);
                book.count++;
            }
        }


    }

    public static void main(String[] args) {

        Controllers controllers = new Controllers();
        Scanner input = new Scanner(System.in);
        String[] command;

        while (input.hasNext()){

            command = input.nextLine().split(" ");

            switch (command[0]){
                case "arrive":
                    controllers.arrive(command[1],  Integer.parseInt(command[2]));
                    break;

                case "exit":
                    controllers.exit(command[1], Integer.parseInt(command[2]));
                    break;

                case "isInLib":
                    controllers.isInLib(command[1]);
                    break;

                case "returnBook":
                    controllers.returnBook(command[2], command[1]);
                    break;

                case "totalTimeInLib":
                    controllers.totalTimeInLab(command[1], Integer.parseInt(command[2]), Integer.parseInt(command[3]));
                    break;

                case "addNewBook":
                    controllers.addNewBook(command[1], Integer.parseInt(command[2]));
                    break;

                case "shouldBring":
                    controllers.shouldBring(command[1], command[2]);
                    break;

                case "allPersonCurrentBook":
                    controllers.allPersonCurrentBooks(command[1]);
                    break;

                case "allPersonHave":
                    controllers.allPersonHaveThisBook(command[1]);
                    break;

                default:
                    break;
            }

        }






       /*controllers.arrive("modos", 3);
       controllers.arrive("reza", 3);
       controllers.addNewBook("bookone", 10);
       controllers.addNewBook("booktwo", 20);
       controllers.shouldBring("bookone", "modos");
       controllers.shouldBring("bookone", "reza");
       controllers.shouldBring("booktwo", "modos");
       controllers.exit("modos", 6);
       controllers.totalTimeInLab("modos", 0, 8);
       controllers.returnBook("bookone", "modos");
       controllers.allPersonCurrentBooks("modos");
       controllers.allPersonHaveThisBook("bookone");*/

    }
}
