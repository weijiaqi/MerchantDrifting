package com.merchant.drifting.data.event;

public class UpdateProgressEvent {
    long bytesRead;
    long contentLength;
    boolean done;

    public long getBytesRead() {
        return bytesRead;
    }

    public void setBytesRead(long bytesRead) {
        this.bytesRead = bytesRead;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public UpdateProgressEvent(long bytesRead, long contentLength, boolean done) {
        this.bytesRead = bytesRead;
        this.contentLength = contentLength;
        this.done = done;
    }
}
