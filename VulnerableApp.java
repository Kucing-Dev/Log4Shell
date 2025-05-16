import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VulnerableApp {
    private static final Logger logger = LogManager.getLogger(VulnerableApp.class);

    public static void main(String[] args) {
        if (args.length > 0) {
            logger.info("User input: " + args[0]);
        } else {
            logger.info("No input provided.");
        }
    }
}
