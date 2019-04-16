package uz.codic.ahmadtea.data.network.model;

import java.util.List;

public class EmployeeLocations {

    List<EmployeeLocation> employeeLocations;

    public EmployeeLocations(){

    }

    public void setEmployeeLocations(List<EmployeeLocation> employeeLocations) {
        this.employeeLocations = employeeLocations;
    }

    public List<EmployeeLocation> getEmployeeLocations() {
        return employeeLocations;
    }
}
