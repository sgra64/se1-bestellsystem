package application;

import java.util.Arrays;
import java.util.Optional;


/**
 * Application class with a {@code main()} - function that parses command line
 * arguments.
 *
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */
public class Application implements Runner {

    /*
     * Singleton instance (strict).
     */
    private final static Application application = new Application();

    /**
     * Private default constructor to prevent instance creation outside
     * this class (part of the Singleton pattern).
     */
    private Application() { }


    /**
     * {@code main()} - function is the entry point for the Java VM.
     * @param args arguments passed from the command line
     */
    public static void main(String[] args) {
        // 
        // execute this application
        Runner application_c12 = new Application_C12();
        application.execute(application_c12, args);
    }

    /**
     * Run application code on an instance rather than in {@code static main();}
     */
    @Override
    public void run(String[] args) {
        Arrays.stream(args)
            .map(arg -> String.format(" - arg: %s", arg))
            .forEach(System.out::println);
    }

    /**
     * Execute a {@link Runner} instance.
     * @param runner {@link Runner} instance to execute
     */
    private void execute(Runner runner, String[] args) {
        var className = runner.getClass().getSimpleName();
        var moduleName = Application.class.getModule().getName();
        System.out.println(Optional.ofNullable(moduleName)
            .map(m -> String.format("Hello, %s (%s, modular)", moduleName, className))
            .orElse(String.format("Hello, se1-bestellsystem (%s)", className)));
        // 
        runner.run(args);
    }
}