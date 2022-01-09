package model;

import exception.ModelValidationException;

import java.io.Serializable;

public class Place implements Serializable {
    private LibraryRoom room;
    private String bookCase;
    private int shelfNumber;


    public Place(LibraryRoom room, String bookCase, int shelfNumber){
        setRoom(room);
        setBookCase(bookCase);
        setShelfNumber(shelfNumber);
    }

    public LibraryRoom getRoom() {
        return room;
    }

    public void setRoom(LibraryRoom room) {
        this.room = room;
    }

    public String getBookCase() {
        return bookCase;
    }

    public void setBookCase(String bookCase) {
        if((bookCase == null)||"".equals(bookCase.trim())){
            throw new ModelValidationException("bookcase can't be null!");
        }
        this.bookCase = bookCase;
    }

    public int getShelfNumber() {
        return shelfNumber;
    }

    public void setShelfNumber(int shelfNumber) {
        if(shelfNumber < 1){
            throw new ModelValidationException("shelf number can't be 0 or less");
        }
        this.shelfNumber = shelfNumber;
    }

    public String toString(){
        return getRoom() +" "+ getBookCase() + " shelf no.: " + getShelfNumber();
    }
}
