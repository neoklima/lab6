package managers;

import models.Coordinates;
import models.FuelType;
import models.Vehicle;
import models.VehicleType;
import utility.Console;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Set;

public class DumpManager {
    private final String fileName;
    private final Console console;

    public DumpManager(Console console) {
        this.fileName = System.getenv("FILENAME");
        this.console = console;
    }

    /**
     * Записывает коллекцию в XML файл.
     *
     * @param collection коллекция
     */
    public void writeCollection(Collection<Vehicle> collection) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("vehicles");
            doc.appendChild(root);

            for (Vehicle vehicle : collection) {
                Element vehicleElement = doc.createElement("vehicle");
                root.appendChild(vehicleElement);

                Element idElement = doc.createElement("id");
                idElement.appendChild(doc.createTextNode(String.valueOf(vehicle.getId())));
                vehicleElement.appendChild(idElement);

                Element nameElement = doc.createElement("name");
                nameElement.appendChild(doc.createTextNode(vehicle.getName()));
                vehicleElement.appendChild(nameElement);

                Element coordinatesElement = doc.createElement("coordinates");
                vehicleElement.appendChild(coordinatesElement);

                Element xElement = doc.createElement("x");
                xElement.appendChild(doc.createTextNode(String.valueOf(vehicle.getCoordinates().getX())));
                coordinatesElement.appendChild(xElement);

                Element yElement = doc.createElement("y");
                yElement.appendChild(doc.createTextNode(String.valueOf(vehicle.getCoordinates().getY())));
                coordinatesElement.appendChild(yElement);

                Element creationDateElement = doc.createElement("creationDate");
                creationDateElement.appendChild(doc.createTextNode(vehicle.getCreationDate().toString()));
                vehicleElement.appendChild(creationDateElement);

                Element enginePowerElement = doc.createElement("enginePower");
                enginePowerElement.appendChild(doc.createTextNode(String.valueOf(vehicle.getEnginePower())));
                vehicleElement.appendChild(enginePowerElement);

                Element numberOfWheelsElement = doc.createElement("numberOfWheels");
                numberOfWheelsElement.appendChild(doc.createTextNode(String.valueOf(vehicle.getNumberOfWheels())));
                vehicleElement.appendChild(numberOfWheelsElement);

                Element typeElement = doc.createElement("type");
                typeElement.appendChild(doc.createTextNode(vehicle.getType().toString()));
                vehicleElement.appendChild(typeElement);

                Element fuelTypeElement = doc.createElement("fuelType");
                fuelTypeElement.appendChild(doc.createTextNode(vehicle.getFuelType().toString()));
                vehicleElement.appendChild(fuelTypeElement);

                // Трансформация документа в XML и запись в файл
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                DOMSource source = new DOMSource(doc);
                try (PrintWriter printWriter = new PrintWriter(fileName)) {
                    StreamResult result = new StreamResult(printWriter);
                    transformer.transform(source, result);
                    console.println("Коллекция успешно сохранена в файл!");
                } catch (FileNotFoundException e) {
                    console.printError("Не удалось найти файл для записи!");
                }
            }
        } catch (ParserConfigurationException | TransformerException e) {
            console.printError("Ошибка при создании или записи XML файла!");
        }
    }

    /**
     * Считывает коллекцию из XML файла.
     */
    public Collection<Vehicle> readCollection(Set<Vehicle> collection) {
        if (fileName != null && !fileName.isEmpty()) {
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(new File(fileName));

                NodeList vehicleNodes = doc.getElementsByTagName("vehicle");
                for (int i = 0; i < vehicleNodes.getLength(); i++) {
                    Element vehicleElement = (Element) vehicleNodes.item(i);
                    long id = Long.parseLong(vehicleElement.getElementsByTagName("id").item(0).getTextContent());
                    String name = vehicleElement.getElementsByTagName("name").item(0).getTextContent();
                    long x = Long.parseLong(vehicleElement.getElementsByTagName("x").item(0).getTextContent());
                    double y = Double.parseDouble(vehicleElement.getElementsByTagName("y").item(0).getTextContent());
                    java.time.LocalDateTime creationDate = java.time.LocalDateTime.parse(vehicleElement.getElementsByTagName("creationDate").item(0).getTextContent());
                    double enginePower = Double.parseDouble(vehicleElement.getElementsByTagName("enginePower").item(0).getTextContent());
                    int numberOfWheels = Integer.parseInt(vehicleElement.getElementsByTagName("numberOfWheels").item(0).getTextContent());
                    VehicleType type = VehicleType.valueOf(vehicleElement.getElementsByTagName("type").item(0).getTextContent());
                    FuelType fuelType = FuelType.valueOf(vehicleElement.getElementsByTagName("fuelType").item(0).getTextContent());

                    // Создание объекта Vehicle и добавление его в коллекцию
                    Vehicle vehicle = new Vehicle(id, name, new Coordinates(x, y), creationDate, enginePower, numberOfWheels, type, fuelType);
                    collection.add(vehicle);
                }

                console.println("Коллекция успешно загружена!");
            } catch (ParserConfigurationException | SAXException | IOException e) {
                console.printError("Ошибка при чтении XML файла!");
            }
        } else {
            console.printError("Переменная окружения с именем файла не найдена!");
        }
        return collection;
    }
}
