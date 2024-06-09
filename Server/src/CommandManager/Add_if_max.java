package CommandManager;
import CollectionManager.CollectionManager;
import Model.Vehicle;
import Response.*;

import java.util.logging.Logger;

import static CollectionManager.IDManager.GetNewId;

/**
 * Add_if_max - добавляет элемент в коллекцию, если он больше чем наибольший элемент коллекции
 */
public class Add_if_max extends Command{
    private final CollectionManager collectionManager;
    private final Logger logger;
    public Add_if_max(CollectionManager collectionManager, CommandManager commandManager, Logger logger){
        super("add_if_max", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
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
        if ((args == null || args.isEmpty())) {
            if (object.equals("")){
                logger.info(super.getName());
                return new Response(STATUS.NEED_OBJECT, "* Создание нового Vehicle:", GetNewId());
            } else {
                Vehicle a = (Vehicle) object;
                if (a.validate()) {
                    Vehicle maxVehicle = collectionManager.getCollection().stream().max(Vehicle::compareTo).orElse(null);
                    if (maxVehicle == null || a.compareTo(maxVehicle) > 0) {
                        collectionManager.add(a);
                        return new Response(STATUS.OK,"Элемент успешно добавлен в коллекцию");
                    } else {
                        return new Response(STATUS.OK, "Элемент не подошёл под условия");
                    }
                }
                else {
                    logger.info(super.getName());
                    return new Response(STATUS.ERROR, "Поля movie не валидны! Vehicle не создан!");
                }
            }
        }
        else{
            logger.warning("Неправильное количество аргументов! ");
            return new Response(STATUS.ERROR,
                    "Неправильное количество аргументов!)");
        }
    }
}
