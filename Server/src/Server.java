import CommandManager.CommandManager;
import Launcher.LaunchCommand;
import Response.Response;
import Response.STATUS;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

public class Server {
    private static final Logger logger = Logger.getLogger(Server.class.getName());
    private final int port;
    private final LaunchCommand launchCommand;
    private final Map<SocketChannel, ObjectOutputStream> clients;
    private final Scanner scanner;
    public Server(int port, CommandManager commandManager, String propPath) {
        this.port = port;
        launchCommand = new LaunchCommand(commandManager);
        clients = new HashMap<>();
        System.setProperty("java.util.logging.config.file", propPath);
        scanner = new Scanner(System.in);
    }

    public void start() throws IOException, ClassNotFoundException {
        logger.info("Старт сервера");
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost", port));
        while (true) {
            logger.info("Подключение к клиенту");
            System.out.println("Введите exitAll, чтобы остановить работу сервера");
            System.out.println("Введите любой другой символ, чтобы продолжить работу");
            String s = scanner.nextLine();
            if (s.equals("exitAll")){
                logger.info("Прекращение работы");
                System.exit(0);
            }
            SocketChannel clientChannel = serverSocketChannel.accept();
            ObjectOutputStream oos = new ObjectOutputStream(clientChannel.socket().getOutputStream());
            clients.put(clientChannel, oos);
            handleClient(clientChannel);
        }
    }
    private void handleClient(SocketChannel clientChannel) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(clientChannel.socket().getInputStream());
        while (true) {
            try {
                Response message = (Response) ois.readObject();
                logger.info("Получение запроса");
                if (message.getMessage().equals("save")){
                    sendResponse(clientChannel, new Response(STATUS.ERROR, "Такой команды не существует \nЧтобы сохраниться напишите save на сервере!"));
                } else {
                    Response command_result = launchCommand.commandParser(message.getMessage(), message.getObject());
                    sendResponse(clientChannel, command_result);
                }
            } catch (IOException e) {
                logger.info("Клиент отключился");
                clients.remove(clientChannel);
                break;
            }
        }
        System.out.println("Введите save, чтобы сохранить всё, что есть на сервере");
        String s = scanner.nextLine();
        if (s.equals("save")){
            Response save = launchCommand.commandParser("save", "");
            System.out.println("Saved");
        }
    }
    private void sendResponse(SocketChannel clientChannel, Response response) throws IOException {
        ObjectOutputStream oos = clients.get(clientChannel);
        logger.info("Отправка результата на клиент");
        oos.writeObject(response);
        oos.flush();
    }
}
