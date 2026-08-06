package com.epam.jsdmx.infomodel.sdmx30;

import org.apache.commons.lang3.StringUtils;

/**
 * Utility singleton class to hold the default Sender id for the library to be consumed
 * by message header writers. The id is read from the environment variable
 * <b>SDMX_SENDER_ID</b> and if not present, defaults to {@code "unknown"}.
 */
public class DefaultSenderIdHolder {

    public static final DefaultSenderIdHolder INSTANCE = new DefaultSenderIdHolder();

    public static final String SENDER_ID_VARIABLE_NAME = "SDMX_SENDER_ID";

    private static final String DEFAULT_SENDER_ID = "unknown";

    private final String senderId;

    private DefaultSenderIdHolder() {
        final String envSenderId = System.getenv(SENDER_ID_VARIABLE_NAME);
        senderId = StringUtils.isEmpty(envSenderId) ? DEFAULT_SENDER_ID : envSenderId;
    }

    /**
     * Returns current value of the default Sender id.
     */
    public String get() {
        return senderId;
    }
}
