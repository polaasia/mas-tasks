package model;

import exception.ModelValidationException;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Book implements Serializable {

    private static List<Book> extent = new ArrayList<>(); //class extent with extent container

    private long id;
    private String title; //simple attribute
    private int numberOfPages;
    private String language; //optional attribute
    private Set<String> genre = new HashSet<>(); //multivalued attribute
    private Place placeOfBook; //complex attribute, place of book consists of room in the house, book case and shelf number
    private LocalDate dateRead; //optional
    //isRead - derived attribute in a form of a method, can be found below
    private static Set<String> allGenres = new HashSet<>(); //as genres will be repeated they will be stored in the genres extent
    private static String belongsTo = "Joanna"; //class attribute - owner of the library




    //class constructors
    //constructor with only mandatory attributes
    public Book(long id, String title, int pages, String genre, Place place) {
        this.id = id;
        setTitle(title);
        setPages(pages);
        addGenre(genre);
        setPlaceOfBook(place);
        addBook(this);
    }

    //constructor with mandatory and optional attributes
    public Book(long id, String title, int pages, String language, String genre, LocalDate dateRead, Place place) {
        this.id = id;
        setTitle(title);
        setPages(pages);
        setLanguage(language);
        addGenre(genre);
        this.dateRead = dateRead;
        setPlaceOfBook(place);
        addBook(this);
    }

    //constructor with mandatory attributes and date read
    public Book(long id, String title, int pages, String genre, LocalDate dateRead, Place place) {
        this.id = id;
        setTitle(title);
        setPages(pages);
        addGenre(genre);
        this.dateRead = dateRead;
        setPlaceOfBook(place);
        addBook(this);
    }

    //getters and setters for the attributes

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // simple attribute - title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if((title == null)||"".equals(title.trim())){
            throw new ModelValidationException("book must have a title!"); //checking if the title is not null as it is mandatory attribute
        }
        this.title = title;
    }

    //simple attribute - number of pages
    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setPages(int pages) {
        this.numberOfPages = pages;
    }


    // multivalued attribute - genre
    public Set<String> getGenre() {
        return Collections.unmodifiableSet(genre);
    }

    public void addGenre(String gen){
        if((gen == null)||"".equals(gen.trim())){
            throw new ModelValidationException("genre can't be null!"); //checking if null - genre is mandatory attribute
        }
        if(!allGenres.contains(gen)){ // if provided genre was not created before it will be added to extent of all genres
            allGenres.add(gen);
        }
        this.genre.add(gen); //then it will be added to list of genres of given book
    }

    public void removeGenre(String gen){
        //prevents removing last genre of the book as it is mandatory attribute
        if(this.genre.size() < 2){
            throw new ModelValidationException("can't remove last genre!");
        }
        this.genre.remove(gen);
    }

    //derived attribute - isRead
    public LocalDate getDateRead() {
        return dateRead;
    }

    public void setDateRead(LocalDate dateRead) {
        LocalDate now = LocalDate.now();
        if (dateRead.isAfter(now)) {
            throw new ModelValidationException("date read can't be in the future!");
        }
        this.dateRead = dateRead;
    }

    public boolean isRead(){ //if book is read it has dateRead thus this method will return true
      return this.dateRead != null;
    }

    //optional attribute - language of the book
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String lang) {
        this.language = lang;
    }

    //methods to modify list of all possible genres
    public static Set<String> getAllGenres() {
        return  Collections.unmodifiableSet(allGenres);
    }

    public static void addToAllGenres(String gen){
        if((gen == null)||"".equals(gen.trim())){
            throw new ModelValidationException("genre can't be null!");
        }
        allGenres.add(gen);
    }

    public static void removeFromAllGenres(String gen){
        //always allow to change of attribute
        //prevent removing last genre
        if(allGenres.size() < 2){
            throw new ModelValidationException("can't remove last genre!");
        }
        allGenres.remove(gen);
    }

    //complex attribute - place of book
    public Place getPlaceOfBook() {
        return placeOfBook;
    }

    public void setPlaceOfBook(Place placeOfBook) {
        if(placeOfBook == null){
            throw new ModelValidationException("place of book can't be null");
        }
        this.placeOfBook = placeOfBook;
    }

    //class attribute - owner
    public static String getBelongsTo() {
        return belongsTo;
    }

    public static void setBelongsTo(String belongsTo) {
        if(belongsTo == null){
            throw new ModelValidationException("books have to have an owner");
        }
        Book.belongsTo = belongsTo;
    }


    //method overloading
    public String timeToRead(){ //how long it will take an average reader to read specific book
        //on average 300 words per minute
        //on average 250 words per page
        double timeMins = (250*getNumberOfPages())/300;
        double time = timeMins/60;
        return "This reader will read " + getTitle() + " in: " + time + " hours.";
    }

    public String timeToRead(double readingSpeed){ //how long it will take a reader to read this book based on how fast/slow they read
        //on average 250 words per page
        double timeMins = (250*getNumberOfPages())/readingSpeed;
        double time = timeMins/60;
        return "This reader will read " + getTitle() + " in: " + time + " hours.";
    }

    //class method - filtering all the books for specified genre
    public static List<Book> showByGenre(String genre){
        if((genre == null)||"".equals(genre.trim())){
            throw new ModelValidationException("genre can't be null!");
        }
        return extent.stream().filter(b -> b.genre.contains(genre)).collect(Collectors.toList());
    }

    //method overriding
    @Override
    public String toString() {
        return "id: " + getId() + " title: " + getTitle() + " of genre: " + getGenre() + " in language: " + getLanguage() + " can be found in: " + getPlaceOfBook() + " belongs to: " +getBelongsTo() + "\n";
    }


    //class extent - methods
    private static void addBook(Book book) {
        extent.add(book);
    }

    private static void removeBook(Book book) {
        extent.remove(book);
    }

    public static List<Book> getExtent() {
        return Collections.unmodifiableList(extent);
    }

    //saving and reading extent - persistence of the extent
    public static void saveExtent() throws IOException {
        try (ObjectOutput oos = new ObjectOutputStream(new FileOutputStream(("../data/books.ser")))) {
            oos.writeObject(extent);
        }
    }

    public static void loadExtent() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("../data.books.ser"))) {
            extent = (List<Book>)ois.readObject();
        }
    }
}
