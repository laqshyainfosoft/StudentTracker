package com.app.laqshya.studenttracker.activity.model;

import java.util.List;

public class PDFDoc {
    private int notesid;
    private String bookname;

    public int getNotesid() {
        return notesid;
    }

    public void setNotesid(int notesid) {
        this.notesid = notesid;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getBookpath() {
        return bookpath;
    }

    public void setBookpath(String bookpath) {
        this.bookpath = bookpath;
    }

    private String bookpath;


public static class PDFList{
    private List<PDFDoc> pdfDocList;
    private Throwable throwable;

    public Throwable getThrowable() {
        return throwable;
    }

    public List<PDFDoc> getPdfDocList() {

        return pdfDocList;
    }

    public void setPdfDocList(List<PDFDoc> pdfDocList) {
        this.pdfDocList = pdfDocList;
    }

    public PDFList(Throwable throwable) {
        this.throwable = throwable;
        pdfDocList=null;
    }

    public PDFList(List<PDFDoc> pdfDocList) {

        this.pdfDocList = pdfDocList;
        throwable=null;
    }
}

}

