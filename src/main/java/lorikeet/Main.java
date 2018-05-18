package lorikeet;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

import lorikeet.compile.SourceCompiler;

public class Main {

    public static void run(CommandLine cl) {
        new SourceCompiler(toSettings(cl)).compile();
    }

    public static void main(String[] args) {
        Options options = getOptions();

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);
            System.exit(1);
            return;
        }
        run(cmd);
    }

    private static Settings toSettings(CommandLine cl) {
        return new Settings(
            cl.getOptionValue("source"),
            cl.getOptionValue("build")
        );
    }

    private static Options getOptions() {
        Options options = new Options();

        Option source = new Option("s", "source", true, "specify root source folder");
        source.setRequired(true);
        options.addOption(source);

        Option output = new Option("b", "build", true, "specify root build output directory");
        output.setRequired(true);
        options.addOption(output);

        Option launcher = new Option("l", "launcher", true, "create a launch script with the given name");
        options.addOption(launcher);

        return options;
    }
}
