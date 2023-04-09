package Packer;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import Exception.APIException;

public class Package
{
    private Set<Record> records;
    private double maxWeight;

    public Package(Set<Record> records, double maxWeight)
    {
        this.records = records;
        this.maxWeight = maxWeight;
    }

    public Set<Record> getRecords() {
        return records;
    }


    public double getMaxWeight() {
        return maxWeight;
    }

    public String getNumberOfRecords(){
        if(this.getRecords().size()==0){
            return "-";
        }else{
            return this.getRecords().stream().map(Record::getNo).sorted().map(String::valueOf).collect(Collectors.joining(",","",""));
        }
    }
    public Package(String inputLine) throws APIException
    {
        try {
            checkPackage(inputLine);
        } catch (APIException e) {
            throw new RuntimeException(e);
        }
    }

    public void checkPackage(String inputLine) throws APIException
    {
        this.records = new HashSet<>();
        // Split the packages and weight
        String[] PackageData = inputLine.split(":");
        // First array index is the maximum weight limit.
        this.maxWeight = Double.parseDouble(PackageData[0].trim());
        // Package Constraint: Package weight should not be greater than 100. Throw a constraint exception in case of a violation
        if (this.maxWeight > 100)
        {
            throw new APIException("Package must not weight more than 100");
        }
        PackageData[1] = PackageData[1].trim();
        // Split items on space into item string comprising no, weight and cost present
        String[] packageDetails = PackageData[1].split(" ");
        // Iterate through the items and remove parenthesis enclosing each item
        for(String packageDetail:packageDetails)
        {
            //String packageDetail = packageDetails[i];
            packageDetail = packageDetail.trim().substring(1, packageDetail.length() - 1);
            // create item objects from descriptions
            Record record = new Record(packageDetail);
            // Filter to remove items that are singularly heavier than the package weight limit
            if (record.getWeight() <= this.maxWeight)
            {
                records.add(record);
            }
        }

    }
   public Package findPackage()
    {
        /**In order to store the records which are unique in style,so I used Set and in Set we can get the max and min on the basis
         * of cost and to sort in descending order LinkedHashset is good.
         *
         */
        LinkedHashSet<Record> sortedRecords = this.getRecords().stream().sorted(Comparator.comparing(Record::getCost, Comparator.reverseOrder())).collect(Collectors.toCollection(LinkedHashSet::new));
        double packageWeightLimit = this.getMaxWeight();
        double sumOfWeightOfRecords = 0;
        Set<Record> valuedPackage = new HashSet<>();
        Record previousRecord = null;
        for (Record record : sortedRecords)
        {
            if (valuedPackage.size() == 0)
            {
                sumOfWeightOfRecords += record.getWeight();
                valuedPackage.add(record);
                previousRecord = record;
            }

            // If package weight will exceed with new item, compare it against previous item to check if they are same and add item with lower weight to package
            else if ((sumOfWeightOfRecords + record.getWeight() > packageWeightLimit) && (record.getCost() == previousRecord.getCost()))
            {
                if (record.getWeight() < previousRecord.getWeight())
                {
                    // Items have same cost. Remove item with larger weight before adding item with lesser weight to package
                    valuedPackage.remove(previousRecord);
                    sumOfWeightOfRecords -= previousRecord.getWeight();
                    sumOfWeightOfRecords += record.getWeight();
                    valuedPackage.add(record);
                }
            }
            // Check if package weight can accommodate item based on weight limit and add if it is.
            else if (sumOfWeightOfRecords + record.getWeight() <= packageWeightLimit)
            {
                sumOfWeightOfRecords += record.getWeight();
                valuedPackage.add(record);
                previousRecord = record;
            }
        }

        return new Package(valuedPackage, packageWeightLimit);

    }



}

