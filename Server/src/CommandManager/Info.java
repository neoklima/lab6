package CommandManager;
import Response.*;
import CollectionManager.CollectionManager;
import java.util.logging.Logger;

/**
 * Info - выводит информацию о коллекции
 */
public class Info extends Command{
    private final CollectionManager collectionManager;
    private final Logger logger;
    public Info(CollectionManager collectionManager, CommandManager commandManager, Logger logger){
        super("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        commandManager.addCommandList(getName(), getDescription());
        this.collectionManager = collectionManager;
        this.logger = logger;
    }
    /**
     * Выполнение команды
     * @return Успешность выполнения команды и сообщение об успешности.
     */
    @Override
    public Response execution(String args, Object object){
        if ((args == null || args.isEmpty())){
            String stringBuilder = "Данные о коллекции" + "\n" +
                    "Тип - TreeSet<Vehicle>" + "\n" +
                    "Дата инициализации - " + collectionManager.getLastInitTime() + "\n" +
                    "Количество элементов - " + collectionManager.getCollection().size() + "\n" +
                    "Дата сохранения - " + collectionManager.getLastSaveTime() + "\n";
            logger.info(super.getName());
            return new Response(STATUS.OK, stringBuilder);
        } else {
            logger.warning("Неправильное количество аргументов!)");
            return new Response(STATUS.ERROR,
                    "Неправильное количество аргументов!)");
        }
    }
}
