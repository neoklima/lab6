package DumpManager;

import CollectionManager.CollectionManager;
import Model.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.time.LocalDateTime;

import static CollectionManager.IDManager.AddId;
import static CollectionManager.IDManager.ListID;

/**
 * Чтение и запись XML-файла
 */
public class DumpManager {
    /**
     * Записывает XML-файл
     */
    private final CollectionManager collectionManager;
    public DumpManager(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }
    public void Writer() throws FileNotFoundException{
        PrintWriter writer;
        try {
            writer = new PrintWriter(new FileWriter(System.getenv("FILENAME")));
            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.println("<vehicles>");
            for (Vehicle vehicle : collectionManager.getCollection()) {
                writer.println("\t<vehicle>");
                writer.println("\t\t<id>" + vehicle.getId() + "</id>");
                writer.println("\t\t<name>" + vehicle.getName() + "</name>");
                writer.println("\t\t<coordinates>");
                writer.println("\t\t\t<x>" + vehicle.getCoordinates().getX() + "</x>");
                writer.println("\t\t\t<y>" + vehicle.getCoordinates().getY() + "</y>");
                writer.println("\t\t</coordinates>");
                writer.println("\t\t<creationDate>" + vehicle.getCreationDate() + "</creationDate>");
                writer.println("\t\t<enginePower>" + vehicle.getEnginePower() + "</enginePower>");
                writer.println("\t\t<numberOfWheels>" + vehicle.getNumberOfWheels() + "</numberOfWheels>");
                writer.println("\t\t<type>" + vehicle.getType() + "</type>");
                writer.println("\t\t<fuelType>" + vehicle.getFuelType() + "</fuelType>");
                writer.println("\t</vehicle>");
            }
            writer.println("</vehicles>");
            collectionManager.setLastSaveTime(LocalDateTime.now());
            writer.close();
            System.out.println("XML файл успешно создан.");
        }
        catch (FileNotFoundException e){
            System.out.println("Такого пути не существует");
        }
        catch (IOException e) {
            System.out.println("Ошибка записи файла");
            throw new RuntimeException(e);
        }
    }
    /**
     * Читает XML-файл
     *
     * @param filePath - путь к XML-файлу
     */
    public void readMoviesFromXmlFile(String filePath) {
        try {
            File xmlFile = new File(filePath);
            xmlFile.compareTo(xmlFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            Element rootElement = doc.getDocumentElement();
            for (Node node = rootElement.getFirstChild(); node != null; node = node.getNextSibling()) {
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element movieElement = (Element) node;
                    Vehicle vehicle = createMovieFromElement(movieElement);
                    if (vehicle.validate()){
                        if (!(ListID.contains(vehicle.getId()))){
                            collectionManager.getCollection().add(vehicle);
                            AddId((int) vehicle.getId());
                        }
                    }
                }
            }
            collectionManager.setLastInitTime(LocalDateTime.now());
            collectionManager.setLastSaveTime(LocalDateTime.now());
        } catch (FileNotFoundException e){
            System.out.println("Такого пути не существует");
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("Проблемы со считыванием файла");
        }
    }
    /**
     * Создаёт Vehicle с помощью сторонних библиотек
     * @param element - узел, являющийся элементом коллекции
     * @return Vehicle - готовый элемент
     */
    private static Vehicle createMovieFromElement(Element element) {
        Element coordinatesElement = (Element) element.getElementsByTagName("coordinates").item(0);
        Coordinates coordinates = new Coordinates();
        coordinates.setX(Long.parseLong(coordinatesElement.getElementsByTagName("x").item(0).getTextContent()));
        coordinates.setY(Double.parseDouble(coordinatesElement.getElementsByTagName("y").item(0).getTextContent()));
        CollectionManager collectionManager = new CollectionManager();
        return collectionManager.VehicleCreator(Long.parseLong(element.getElementsByTagName("id").item(0).getTextContent()),
                element.getElementsByTagName("name").item(0).getTextContent(), coordinates, LocalDateTime.now(),
                Double.parseDouble(element.getElementsByTagName("enginePower").item(0).getTextContent()),
                Integer.parseInt(element.getElementsByTagName("numberOfWheels").item(0).getTextContent()),
                VehicleType.valueOf(element.getElementsByTagName("type").item(0).getTextContent()),
                        FuelType.valueOf(element.getElementsByTagName("fuelType").item(0).getTextContent()));
    }
}
