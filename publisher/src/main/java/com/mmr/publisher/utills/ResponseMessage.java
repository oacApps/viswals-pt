package com.mmr.publisher.utills;

public enum ResponseMessage {

    INVALID_FILE ("Invalid file. Please provide a valid file. "),
    ERROR_READING_FILE("Unable to read the file. "),
    RECORD_PARSE_ERROR("Unable to convert Record. "),
    CSV_PUBLISH_SUCCESS("CSV published to the queue successfully. "),
    CSV_PUBLISH_FAILED("Error publishing CSV to the queue: ");

    private String message;
    ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
