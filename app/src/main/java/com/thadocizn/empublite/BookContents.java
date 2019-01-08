package com.thadocizn.empublite;

import android.net.Uri;

import java.io.File;
import java.util.List;

public class BookContents {

    List<BookContents.Chapter> chapters;
    File baseDir = null;

    void setBaseDir(File baseDir) {
        this.baseDir = baseDir;
    }

    int getChapterCount(){
        return (chapters.size());
    }

    String getChapterPath(int position){
        String file = getChapterFile(position);
        if (baseDir == null){
            return ("file:///android_asset/book/" + file);
        }

        return (Uri.fromFile(new File(baseDir, file)).toString());
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
