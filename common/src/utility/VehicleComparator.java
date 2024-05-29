package utility;

import models.Vehicle;

import java.util.Comparator;

public class VehicleComparator implements Comparator<Vehicle> {
    public int compare(Vehicle a, Vehicle b){
        return a.getName().compareTo(b.getName());
    }
}