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
                .map(Part::getName)
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
                .filter(pr -> pr.getPrice() >= threshold)
                .toList();
    }

    public List<Part> getPartByStock(List<Part> parts, int threshold){
        if(parts.isEmpty()){
            System.out.println("Empty list");
            return List.of();
        }
        return parts
                .stream()
                .filter(pr -> pr.getStock() < threshold)
                .sorted(Comparator.comparingInt(Part::getStock))
                .toList();
    }

    public double summingAllPartsPrice(List<Part> parts){
        if(parts.isEmpty()){
            System.out.println("Empty list");
            return 0.0;
        }
        return parts
                .stream()
                .mapToDouble(pr -> pr.getPrice() * pr.getStock())
                .sum();
    }

    public int partsQuantity(List<Part> parts){
        if(parts.isEmpty()){
            System.out.println("Empty list");
            return 0;
        }

        return parts
                .stream()
                .mapToInt(Part::getStock)
                .sum();
    }

    public Part mostExpensivePart(List<Part> parts){
        return parts
                .stream()
                .max(Comparator.comparingDouble(Part::getPrice))
                .orElseThrow(() -> new IllegalArgumentException("Not parts found."));
    }

    public List<String> groupBySupplier(List<Part> parts){
        if(parts.isEmpty()){
            System.out.println("Empty list");
            return List.of();
        }
        return parts
                .stream()
                .map(Part::getSupplier)
                .distinct()
                .sorted()
                .toList();
    }

    public void getPartsByCategory(List<Part> parts){
        if(parts.isEmpty()){
            System.out.println("Empty list");
        }
        Map<String, Long> groupByCat = parts
                .stream()
                .collect(Collectors.groupingBy(Part::getCategory, Collectors.counting()));

        groupByCat.forEach((category, count) -> System.out.println(category + " : " + count));
    }

    public void getAveragePriceBySupplier(List<Part> parts){
        if(parts.isEmpty()){
            System.out.println("Empty list");
        }

        Map<String, Double> avgSup = parts
                .stream()
                .collect(Collectors.groupingBy(Part::getSupplier, Collectors.averagingDouble(Part::getPrice)));

        avgSup.forEach((key, value) -> System.out.println(key + " : " + value));
    }

    public String lowStockPart(List<Part> parts){
        if(parts.isEmpty()){
            System.out.println("Empty list");
            return "";
        }
        return parts
                .stream()
                .filter(pr -> pr.getStock() < 3)
                .map(Part::getName)
                .sorted()
                .collect(Collectors.joining(","));
    }

    public void criticalPartsStock (List<Part> parts){
        if(parts.isEmpty()){
            System.out.println("Empty list");
            return;
        }
        Map<Boolean, List<Part>> criticalStock = parts
                .stream()
                .collect(Collectors.partitioningBy(part -> part.getStock() <= 1));

        System.out.println("___Critical Stock Parts__");
        criticalStock.forEach((isLow, part) -> System.out.println("Is stock low?: "+isLow+" Part: "+part));
    }

    public void highPriceByCategory(List<Part> parts){
        if(parts.isEmpty()){
            System.out.println("Empty list");
            return;
        }
        Map<String, Optional<Part>> highestPriceByCat = parts
                .stream()
                .collect(Collectors.groupingBy(Part::getCategory, Collectors.maxBy(Comparator.comparingDouble(Part::getPrice))));

        System.out.println("___Highest Price By Category__");
        highestPriceByCat.forEach((key, optionalPart) ->
                optionalPart.ifPresent( op -> System.out.printf("Category: %s, Highest Price: %s%n",key, op.getPrice())));
    }

    public List<Part> sortPartByCatAndPrice(List<Part> parts){
        if(parts.isEmpty()){
            System.out.println("Empty list");
            return List.of();
        }
        return parts
                .stream()
                .sorted(Comparator.comparing(Part::getCategory)
                        .thenComparing(Comparator.comparingDouble(Part::getPrice).reversed())).toList();

    }

    public void priceStatistics(List<Part> parts){
        if(parts.isEmpty()){
            System.out.println("Empty list");
            return;
        }

        DoubleSummaryStatistics priceStats = parts
                .stream()
                .collect(Collectors.summarizingDouble(Part::getPrice));

        System.out.printf("Total inventory tracked: %d parts\n", priceStats.getCount());
        System.out.printf("Highest Part's Price: $%.2f\n", priceStats.getMax());
        System.out.printf("Lowest Part's Price: $%.2f\n", priceStats.getMin());
        System.out.printf("Average WorkShop Price: $%.2f\n", priceStats.getAverage());
        System.out.printf("Total Inventory Value: $%.2f\n", priceStats.getSum());

    }

}
