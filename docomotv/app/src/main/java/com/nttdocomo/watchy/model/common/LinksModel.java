package com.nttdocomo.watchy.model.common;

/**
 * Created by anweiyang on 18/1/9.
 */

public class LinksModel {


    /**
     * previous : /items?name=%E8%8B%B1%E8%AF%AD&maxDistance=25000&page[no]=1&page[size]=10
     * self : /items?name=%E8%8B%B1%E8%AF%AD&maxDistance=25000&page[no]=2&page[size]=10
     * next : /items?name=%E8%8B%B1%E8%AF%AD&maxDistance=25000&page[no]=3&page[size]=10
     */

    private String previous;
    private String self;
    private String next;

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
