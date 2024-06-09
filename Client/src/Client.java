import InputManager.Ask;
import InputManager.StandartConsole;
import Model.Vehicle;
import Response.Response;
import Response.STATUS;
import Utility.PortAsker;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static InputManager.Ask.askVehicle;

public class Client {
    ObjectOutputStream oos;
    ObjectInputStream ois;
    ArrayList<String> scriptHistory = new ArrayList<>();
    private final String host;
    private int port;
    public Client(String host, int port) {
        this.port = port;
        this.host = host;
    }
    public void run(){
        while (true){
            try {
                Socket socket = new Socket(host, port);
                oos = new ObjectOutputStream(socket.getOutputStream());
                ois = new ObjectInputStream(socket.getInputStream());
                start();
                if (!socket.isClosed()){
                    break;
                }
            } catch (IOException | ClassNotFoundException e ) {
                System.err.println("Не удалось подключиться к серверу. Пожалуйста, попробуйте позже.");
                System.out.println();
                System.out.print("Введите exit, чтобы завершить работу или любой другой символ, чтобы продолжить: ");
                Scanner scanner = new Scanner(System.in);
                if (scanner.hasNextLine()){
                    if (scanner.nextLine().equals("exit")){
                        System.out.println("Завершение работы");
                        System.exit(0);
                    }
                }
                port = PortAsker.getPort();
                try {
                    System.out.println("Ждём...");
                    Thread.sleep(3333);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
    public void launch(Scanner scanner, StandartConsole standartConsole) throws IOException, ClassNotFoundException {
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            if (s.trim().split(" ")[0].equals("execute_script")) {
                scriptMode((s.trim() + " ").split(" ", 2)[1].trim());
            }
            Response response1 = new Response(STATUS.COMMAND, s);
            try {
                oos.writeObject(response1);
                oos.flush();
                Response response2 = (Response) ois.readObject();
                if (response2.getStatus().equals(STATUS.NEED_OBJECT)) {
                    try {
                        Vehicle vehicle = askVehicle(standartConsole, 999999999);
                        if (!response2.getObject().equals("")) {
                            vehicle.setId((Integer) response2.getObject());
                        }
                        Response response3 = new Response(STATUS.COMMAND, s, vehicle);
                        oos.writeObject(response3);
                        oos.flush();
                        System.out.println(ois.readObject());
                    } catch (Ask.AskBreak e) {
                        System.out.println("Завершение работы");
                        System.exit(1);
                    }
                } else {
                    if (response2.getMessage().equals("STOP!")){
                        System.out.println("STOP");
                        System.exit(0);
                    } else {
                        System.out.println(response2);
                    }
                }
            } catch (IOException e) {
                run();
            }
        }
    }

    public void start() throws IOException, ClassNotFoundException {
        StandartConsole standartConsole = new StandartConsole();
        Scanner scanner = new Scanner(System.in);
        launch(scanner, standartConsole);
    }


    public void scriptMode(String args) throws IOException, ClassNotFoundException {
        if (!new File(args).exists()) {
            System.out.println(new Response(STATUS.ERROR, "Файл не существует!"));
            start();
        }
        if (!Files.isReadable(Paths.get(args))) {
            System.out.println(new Response(STATUS.ERROR, "Прав для чтения нет!"));
            start();
        }
        try (Scanner scriptScanner = new Scanner(new File(args))) {
            System.out.println(args);
            StandartConsole defaultInput = new StandartConsole();
            defaultInput.selectFileScanner(scriptScanner);
            if (scriptHistory.contains(args)){
                System.out.println("Рекурсия!!!");
                start();
            } else {
                scriptHistory.add(args);
                launch(scriptScanner, defaultInput);
                scriptHistory.clear();
                start();
            }
        }
    }
}
