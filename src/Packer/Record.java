package Packer;
import Exception.APIException;

public class Record {
    // we use integer for the item no and double for both weight and cost to capture floating point values.
    private int no;
    private double weight;
    private double cost;

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Record(String description) throws APIException
    {
        try {
            CreateRecord(description);
        } catch (APIException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public String toString()
    {
        return "No: " + this.no + " Weight: " + this.weight + " Cost: " + this.cost;
    }

    private void CreateRecord(String packageDetail) throws APIException
    {
        // Split each item description on comma to get its no, weight and costs. Store the values in a java Item object in property types for manipulation.
        String[] record = packageDetail.split(",");
        int no = Integer.parseInt(record[0].trim());
        double weight = Double.parseDouble(record[1].trim());
        // Remove euro symbol from cost
        record[2] = record[2].trim().substring(1, record[2].length());
        double cost = Double.parseDouble(record[2]);
        // Constraint: Perform constraint checks on the number of items per package, item weight and item cost
        if (no > 15)
        {
            throw new APIException("Too many items to choose from in package.Items may not exceed 15.");
        }
        if (weight > 100)
        {
            throw new APIException("Item weights above 100 for item.Maximum weight of any item should be below 100.");
        }
        if (cost > 100)
        {
            throw new APIException("Item costs more than €100 for item.Maximum cost of any item should be below €100.");
        }
        this.setNo(no);
        this.setWeight(weight);
        this.setCost(cost);

    }
}
