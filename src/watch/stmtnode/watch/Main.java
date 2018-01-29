package stmtnode.watch;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

	static Log logger = new Log();

	public static void main(String[] args) {
		TaskQueue queue = new TaskQueue();
		Path inDir = Paths.get("mod");
		Path outDir = Paths.get("out");
		new ModuleWatch(inDir, paths -> {
			System.out.println(paths);
		}).start();
	}

}
