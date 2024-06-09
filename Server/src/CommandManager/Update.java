package CommandManager;
import Model.Vehicle;
import Response.*;
import CollectionManager.CollectionManager;
import Response.Response;

import java.util.logging.Logger;

/**
 * Команда Update - обновляет значение элемента коллекции, id которого равен заданному.
 */
public class Update extends Command{
    private final CollectionManager collectionManager;
    private final Logger logger;
    public Update(CollectionManager collectionManager, CommandManager commandManager, Logger logger) {
        super("update", "обновить значение элемента коллекции, id которого равен заданному");
        commandManager.addCommandList(getName(), getDescription());
        this.collectionManager = collectionManager;
        this.logger = logger;
    }
    /**
     * Выполнение команды
     * @return Успешность выполнения команды и сообщение об успешности.
     */
    @Override
    public Response execution(String args, Object object) {
        if (args == null || args.isEmpty()) {
            return new Response(STATUS.ERROR,
                    "Неправильное количество аргументов!)");
        } else {
            int id = Integer.parseInt(args.split(" ")[0]);
            if (object.equals("")){
                return new Response(STATUS.NEED_OBJECT, "* Создание нового Vehicle:", id);
            } else {
                if (collectionManager.getCollection().isEmpty()) {
                    System.out.println(super.getName());
                    logger.info(super.getName());
                    return new Response(STATUS.OK, "Коллекция пустая");
                }
                boolean exist = false;
                for (Vehicle vehicle : collectionManager.getCollection()) {
                    if (vehicle.getId() == id) {
                        exist = true;
                        break;
                    }
                }
                if (exist){
                    Vehicle a = (Vehicle) object;
                    if (a.validate()) {
                        collectionManager.remove(id);
                        collectionManager.add(a);
                        System.out.println(super.getName());
                        logger.info(super.getName());
                        return new Response(STATUS.OK, "Vehicle успешно обновлен!");
                    } else {
                        logger.info("Поля vehicle не валидны! Vehicle не создан!");
                        return new Response(STATUS.ERROR, "Поля vehicle не валидны! Vehicle не создан!");
                    }
                } else {
                    logger.info("Такого ID не существует");
                    return new Response(STATUS.ERROR, "Такого ID не существует");
                }
            }
        }
    }
}
