import workshop.estimator.factory.*;
import workshop.estimator.factory.*;
import workshop.estimator.model.*;
import workshop.estimator.model.*;
import workshop.estimator.model.*;
import workshop.estimator.service.*;
import workshop.estimator.strategy.*;
import java.util.*;


void main() {


    Vehicle Car = VehicleFactory.createVehicle("CAR", "Rav4", "Toyota",
            "SUV", "Black", 4, 1.5, "Gasoline").orElseThrow();

    Vehicle myTruck = VehicleFactory.createDefaultVehicle("Truck").orElseThrow();

    ServiceJob oilChangeJob = ServiceJobFactory.createServiceJob("oil change job").orElseThrow();
    ServiceJob transmissionJob = ServiceJobFactory.createServiceJob("transmission job").orElseThrow();
    ServiceJob brakeJob = ServiceJobFactory.createServiceJob("brake job").orElseThrow();

    EstimatorContext carContext = new EstimatorContext(
            new StandardLaborStrategy(),
            new StandardMarkupStrategy(),
            new NoDiscountStrategy()
    );
    EstimatorContext myTruckContext = new EstimatorContext(
            new CustomLaborStrategy(75.00),
            new WholesaleMarkupStrategy(),
            new PercentageDiscountStrategy(0.1)
    );

    ServiceOrder order1 = new ServiceOrder.Builder()
            .setCustomerName("ULPGL car")
            .setVehicle(Car)
            .addJob(oilChangeJob)
            .setPartsCost(80.00)
            .setPricingContext(carContext)
            .build();

    ServiceOrder order2 = new ServiceOrder.Builder()
            .setCustomerName("Virunga Energies")
            .setVehicle(myTruck)
            .addJob(brakeJob)
            .addJob(transmissionJob)
            .setPartsCost(1500.00)
            .setPricingContext(myTruckContext)
            .build();

    System.out.println("\nCustomer Name -> " + order1.getCustomerName());
    System.out.println("Car info ->  " + order1.getVehicle());
    for (ServiceJob service: order1.getServiceJobs()){
        System.out.println("\n\tService: "+service.getDescription() +" : "+ service.getLabourHours()+" hrs of works" );
    }
    System.out.println("Total Quote: $" + order1.calculateTotalBill());

    System.out.println("\nCustomer Name -> " + order2.getCustomerName());
    System.out.println("Car info -> " + order2.getVehicle());
    for (ServiceJob service: order2.getServiceJobs()){
        System.out.println("\n\tService: "+service.getDescription() +" : "+ service.getLabourHours()+" hrs of works" );
    }
    System.out.println("Total Quote: $" + order2.calculateTotalBill());
}
