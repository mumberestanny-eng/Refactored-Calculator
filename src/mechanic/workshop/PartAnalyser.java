package mechanic.workshop;

import mechanic.Part;
import mechanic.workshop.partanalyser.WorkShopPartAnalyser;
import mechanic.workshop.partanalyser.csvhandler.CsvFileParser;

import java.util.*;

public class PartAnalyser {

    public static void main(String[] args){

        CsvFileParser fileParser = new CsvFileParser();
        WorkShopPartAnalyser partAnalyser = new WorkShopPartAnalyser();

        String path = "garage_parts_100.csv";
        List<String> partsAsList = fileParser.csvParser(path);
        List<Part> partsAsPart = fileParser.partTransformer(partsAsList);

        System.out.println("\n____List of all the WorkShop Part____");
        List<String> listOfParts = partAnalyser.getListOfParts(partsAsPart);
        listOfParts.forEach(System.out::println);

        System.out.println("\n____Target specific parts for which price is greater than $400.00 ____\n\t");
        partAnalyser.getPartByPrice(partsAsPart, 400.00).forEach(System.out::println);

        System.out.println("\n____Target specific part for which stock is less than 5 pieces____\n\t");
        partAnalyser.getPartByStock(partsAsPart, 5).forEach(System.out::println);

        System.out.println("\n____Calculating the price of all the parts according to their stock and the total quantity  of our parts____\n\t");
        System.out.printf("The sum of all the parts of the workshop is: $%.3f", partAnalyser.summingAllPartsPrice(partsAsPart));
        System.out.println("\nThe total number of parts in our workshop is: "+ partAnalyser.partsQuantity(partsAsPart)); //Stock of each parts
        System.out.println("The most expensive part is: "+partAnalyser.mostExpensivePart(partsAsPart));

        System.out.println("\n____Group all the parts supplier____\n");
        partAnalyser.groupBySupplier(partsAsPart).forEach(System.out::println);

        System.out.println("\n____Group all the parts by their category____\n");
        partAnalyser.getPartsByCategory(partsAsPart);

        System.out.println("\n____Group average price by supplier____\n");
        partAnalyser.getAveragePriceBySupplier(partsAsPart);

        System.out.println("\n____Get parts with low stock capacity____\n");
        System.out.println(partAnalyser.lowStockPart(partsAsPart));

        System.out.println("\n____Parts we would need to reorder____\n");
        partAnalyser.criticalPartsStock(partsAsPart);

        System.out.println("\nGet the highest price parts in each category____");
        partAnalyser.highPriceByCategory(partsAsPart);

        System.out.println("\n____List of all part sorted by name, price, category____\n");
        partAnalyser.sortPartByNameCatPrice(partsAsPart).forEach(System.out::println);

        System.out.println("\n____The statistics of our workshop____\n");
        partAnalyser.priceStatistics(partsAsPart);


    }
}
