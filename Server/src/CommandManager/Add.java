package CommandManager;
import Model.Vehicle;
import Response.*;
import CollectionManager.CollectionManager;
import Response.Response;
import java.util.logging.Logger;
import static CollectionManager.IDManager.GetNewId;

/**
 * Add - добавляет элемент в коллекцию
 */
public class Add extends Command {
    private final CollectionManager collectionManager;
    private final Logger logger;
    public Add(CollectionManager collectionManager, CommandManager commandManager, Logger logger) {
        super("add", " добавить новый элемент в коллекцию");
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
        if ((args == null || args.isEmpty())) {
            if (object.equals("")){
                logger.info(super.getName());
                return new Response(STATUS.NEED_OBJECT, "* Создание нового Vehicle:", GetNewId());
            } else {
                Vehicle a = (Vehicle) object;
                System.out.println(a);
                if (a.validate()) {
                    collectionManager.add(a);
                    System.out.println(collectionManager.getCollection());
                    logger.info(super.getName());
                    return new Response(STATUS.OK, "Vehicle успешно добавлен!");
                } else
                    return new Response(STATUS.ERROR, "Поля vehicle не валидны! Vehicle не создан!");
            }
        }
        else{
            logger.warning("Неправильное количество аргументов!)");
            return new Response(STATUS.ERROR,
                    "Неправильное количество аргументов!)");
        }
    }
}
