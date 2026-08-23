package mechanic.workshop.partanalyser;

import mechanic.Part;
import java.util.*;
import java.util.stream.Collectors;

public class WorkShopPartAnalyser {

    public List<String> getListOfParts(List<Part> parts){
        if(parts.isEmpty()){
            System.out.println("Empty list");
            return List.of();
        }
        return parts.stream()
                .map(Part::name)
                .sorted()
                .toList();
    }

    public List<Part> getPartByPrice(List<Part> parts, double threshold){
        if(parts.isEmpty()){
            System.out.println("Empty list");
            return List.of();
        }
        return parts
                .stream()
                .filter(pr -> pr.price() >= threshold)
                .toList();
    }

    public List<Part> getPartByStock(List<Part> parts, int threshold){
        if(parts.isEmpty()){
            System.out.println("Empty list");
            return List.of();
        }
        return parts
                .stream()
                .filter(pr -> pr.stock() < threshold)
                .sorted(Comparator.comparingInt(Part::stock))
                .toList();
    }

    public double summingAllPartsPrice(List<Part> parts){
        if(parts.isEmpty()){
            System.out.println("Empty list");
            return 0.0;
        }
        return parts
                .stream()
                .mapToDouble(pr -> pr.price() * pr.stock())
                .sum();
    }

    public int partsQuantity(List<Part> parts){
        if(parts.isEmpty()){
            System.out.println("Empty list");
            return 0;
        }

        return parts
                .stream()
                .mapToInt(Part::stock)
                .sum();
    }

    public Part mostExpensivePart(List<Part> parts){
        return parts
                .stream()
                .max(Comparator.comparingDouble(Part::price))
                .orElseThrow(() -> new IllegalArgumentException("Not parts found."));
    }

    public List<String> getDistinctSuppliers(List<Part> parts){
        if(parts.isEmpty()){
            System.out.println("Empty list");
            return List.of();
        }
        return parts
                .stream()
                .map(Part::supplier)
                .distinct()
                .sorted()
                .toList();
    }

    public Map<String, Long> getPartsByCategory(List<Part> parts){
        if(parts.isEmpty()){
            System.out.println("Empty list");
            return Map.of();
        }
         return parts
                .stream()
                .collect(Collectors.groupingBy(Part::category, Collectors.counting()));
    }

    public Map<String, Double> getAveragePriceBySupplier(List<Part> parts){
        if(parts.isEmpty()){
            System.out.println("Empty list");
            return Map.of();
        }

        return parts
                .stream()
                .collect(Collectors.groupingBy(Part::supplier, Collectors.averagingDouble(Part::price)));
    }

    public String lowStockPart(List<Part> parts){
        if(parts.isEmpty()){
            System.out.println("Empty list");
            return "";
        }
        return parts
                .stream()
                .filter(pr -> pr.stock() < 3)
                .map(Part::name)
                .sorted()
                .collect(Collectors.joining(","));
    }

    public List<Part> criticalPartsStock (List<Part> parts){
        if(parts.isEmpty()){
            System.out.println("Empty list");
            return  List.of();
        }
        return parts
                .stream()
                .filter(p -> p.stock() <= 1)
                .toList();
    }

    public Map<String, Optional<Part>> highPriceByCategory(List<Part> parts){
        if(parts.isEmpty()){
            System.out.println("Empty list");
            return Map.of();
        }
        return parts
                .stream()
                .collect(Collectors.groupingBy(Part::category, Collectors.maxBy(Comparator.comparingDouble(Part::price))));
    }

    public List<Part> sortPartByCatAndPrice(List<Part> parts){
        if(parts.isEmpty()){
            System.out.println("Empty list");
            return List.of();
        }
        return parts
                .stream()
                .sorted(Comparator.comparing(Part::category)
                        .thenComparing(Comparator.comparingDouble(Part::price).reversed())).toList();

    }

    public DoubleSummaryStatistics priceStatistics(List<Part> parts){
        if(parts.isEmpty()){
            System.out.println("Empty list");
        }

         return parts
                .stream()
                .collect(Collectors.summarizingDouble(Part::price));
    }

}

/*


avgSup

System.out.println("___Critical Stock Parts__");
        criticalStock.forEach((isLow, part) -> System.out.println("Is stock low?: "+isLow+" Part: "+part));

System.out.println("___Highest Price By Category__");
        highestPriceByCat


 */
