package app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cli.Runner;
import network.UDPClient;
import utility.StandartConsole;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Главный класс клиентского приложения.
 */
public class App {
    private static final int PORT = 23586;
    public static final Logger logger = LogManager.getLogger("ClientLogger");

    public static void main(String[] args) {
        var console = new StandartConsole();
        try {
            var client = new UDPClient(InetAddress.getLocalHost(), PORT);
            var cli = new Runner(client, console);

            cli.interactiveMode();
        } catch (IOException e) {
            logger.info("Невозможно подключиться к серверу.", e);
            System.out.println("Невозможно подключиться к серверу!");
        }
    }
}