package stmtnode.watch;

import java.util.logging.Logger;

public class Log {

	static Logger logger = Logger.getGlobal();

	public void severe(String format, Object... objects) {
		logger.severe(format(format, objects));
	}

	public void info(String format, Object... objects) {
		logger.info(format(format, objects));
	}

	protected String format(String format, Object... objects) {
		if (objects.length > 0) {
			format = String.format(format, objects);
		}
		return format;
	}

	public void infoTime(String format, Runnable runnable) {
		long time = System.currentTimeMillis();
		runnable.run();
		time = System.currentTimeMillis() - time;
		logger.info(format(format, time));
	}

}
