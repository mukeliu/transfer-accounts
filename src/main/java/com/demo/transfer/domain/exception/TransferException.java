package com.demo.transfer.domain.exception;

import com.demo.transfer.TransferApplication;
import com.demo.transfer.common.GlobalException;

public class TransferException extends GlobalException {

    protected Object attachment;

    public TransferException(String message) {
        super(message);
    }

    public TransferException(Object attachment, String message) {
        super(message);
        this.attachment = attachment;
    }

    public Object getAttachment() {
        return attachment;
    }

    public void setAttachment(Object attachment) {
        this.attachment = attachment;
    }
}
