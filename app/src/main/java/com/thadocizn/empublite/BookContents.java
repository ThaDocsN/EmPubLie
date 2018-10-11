package com.thadocizn.empublite;

import java.util.List;

public class BookContents {

    List<BookContents.Chapter> chapters;
    int getChapterCount(){
        return (chapters.size());
    }

    String getChapterFile(int pos){
        return (chapters.get(pos).file);
    }

    String getChapterTitle(int pos){
        return (chapters.get(pos).title);
    }

    static class Chapter{
        String file;
        String title;
    }
}
