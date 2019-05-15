package producer;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
@Dependent
public class MyLogger {
    @Produces
    public Logger produceLogger(InjectionPoint injectionPoint) {
        Logger logger = Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
        try {
            PatternLayout layout = new PatternLayout("%d{HH:mm:ss} %-5p %-25c{1} :: %m%n");
            FileAppender appender = new FileAppender(layout, "PrechtlBank.log", true);
            logger.addAppender(appender);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }

        logger.setLevel(Level.DEBUG);

        return logger;
    }
}
