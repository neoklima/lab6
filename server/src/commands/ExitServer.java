package commands;

import requests.Request;
import responses.Response;

/**
 * Команда для завершения работы сервера.
 */
public class ExitServer extends CommandServer {

    public ExitServer() {
        super("exit", "Завершить работу сервера");
    }

    @Override
    public Response apply(Request request) {
        System.exit(0); // Завершаем работу сервера с кодом успешного завершения
        return null;
    }
}
