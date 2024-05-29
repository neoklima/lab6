
package serverapp;

import network.UDPDatagramServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import handlers.CommandHandler;
import managers.DumpManager;
import managers.CommandManager;
import commands.*;

import repositories.VehicleRepository;
import utility.Commands;
import utility.Console;
import utility.StandartConsole;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Серверная часть приложения.
 */
public class App {
    public static final int PORT = 23586;
    public static Logger logger = LogManager.getLogger("ServerLogger");

    public static void main(String[] args) {
        Console console = new StandartConsole();
        var dumpManager = new DumpManager(console);
        var repository = new VehicleRepository(dumpManager);

        Runtime.getRuntime().addShutdownHook(new Thread(repository::save));

        var commandManager = new CommandManager() {{
            register(Commands.ADD, new AddServer(repository));
            register(Commands.HELP, new HelpServer(this));
            register(Commands.INFO, new InfoServer(repository));
            register(Commands.SHOW, new ShowServer(repository));
            register(Commands.UPDATE, new UpdateServer(repository));
            register(Commands.REMOVE_BY_ID, new RemoveByIdServer(repository));
            register(Commands.CLEAR, new ClearServer(repository));
            register(Commands.REMOVE_LOWER, new RemoveLowerServer(repository));
            register(Commands.ADD_IF_MAX, new AddIfMaxServer(repository));
            register(Commands.FILTER_BY_NUMBER_OF_WHEELS, new FilterByNumberOfWheelsServer(repository));
            register(Commands.REMOVE_GREATER, new RemoveGreaterServer(repository));
            register(Commands.PRINT_FIELD_ASCENDING_FUEL_TYPE, new PrintFieldAscendingFuelTypeServer(repository));
            register(Commands.SUM_OF_NUMBER_OF_WHEELS, new SumOfNumberOfWheelsServer(repository));
            register(Commands.EXIT, new ExitServer());
        }};

        try {
            var server = new UDPDatagramServer(InetAddress.getLocalHost(), PORT, new CommandHandler(commandManager));
            server.run();
        } catch (IOException e) {
            logger.fatal("Случилась ошибка ввода-вывода", e);
        }

        System.exit(0);
    }
}
