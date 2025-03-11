package util;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.*;

/**
 * @author confff
 */
public class SystemLogger {
    public static final Logger LOGGER = Logger.getLogger("SystemLogger");

    static {
        try{
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new ErrorFormatter());

            FileHandler fileHandler = new FileHandler("SystemLogger.log",true);
            fileHandler.setFormatter(new ErrorFormatter());

            for(Handler handler : LOGGER.getHandlers()){
                LOGGER.removeHandler(handler);
            }

            LOGGER.addHandler(consoleHandler);
            LOGGER.addHandler(fileHandler);

            LOGGER.setLevel(Level.ALL);

        } catch (IOException e) {
            System.out.println("无法初始化日志："+e.getMessage());
        }
    }

    public static class ErrorFormatter extends Formatter {
        @Override
        public String format(LogRecord record) {
            StringBuffer sb = new StringBuffer();
            String timestamp = LocalDateTime.now().toString();

            sb.append("\n=== 错误日志 ===\n");
            sb.append("时间: ").append(timestamp).append("\n");
            sb.append("级别: ").append(record.getLevel()).append("\n");
            sb.append("位置: ").append(record.getSourceClassName())
                    .append(".").append(record.getSourceMethodName()).append("\n");
            sb.append("消息: ").append(record.getMessage()).append("\n");

            if(record.getThrown() != null) {
                Exception e = (Exception) record.getThrown();
                sb.append("异常类型: ").append(e.getClass().getName()).append("\n");
                sb.append("异常信息: ").append(e.getMessage()).append("\n");
                sb.append("堆栈跟踪:\n");
                for (StackTraceElement element : e.getStackTrace()) {
                    sb.append("\t").append(element).append("\n");
                }
            }

            sb.append("================\n");
            return sb.toString();
        }

    }

    public static void logError(String message,Exception e) {
        LOGGER.log(Level.SEVERE, message,e);
    }
}
