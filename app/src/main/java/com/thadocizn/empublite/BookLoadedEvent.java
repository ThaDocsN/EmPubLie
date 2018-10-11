package com.thadocizn.empublite;

public class BookLoadedEvent {

    public BookContents getBook() {
        return (contents);
    }

    private BookContents contents = null;

    public BookLoadedEvent(BookContents contents) {
        this.contents = contents;
    }

}
