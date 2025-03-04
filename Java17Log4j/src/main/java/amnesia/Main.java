package amnesia;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        LOGGER.fatal("ログメッセージ `{}`", "fatal");
        LOGGER.error("ログメッセージ `{}`", "error");
        LOGGER.warn("ログメッセージ `{}`", "warn");
        LOGGER.info("ログメッセージ `{}`", "info");
        LOGGER.debug("ログメッセージ `{}`", "debug");
        LOGGER.trace("ログメッセージ `{}`", "trace");
    }
}
