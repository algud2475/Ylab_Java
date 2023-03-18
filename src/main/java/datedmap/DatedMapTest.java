package datedmap;

public class DatedMapTest {
    public static void main(String[] args) {
        DatedMapImpl datedMap = new DatedMapImpl();
        datedMap.put("HomeworkOne", "Done");
        datedMap.put("HomeworkTwo", "Done");
        datedMap.put("HomeworkThree", "Processing");
        datedMap.put("HomeworkFour", "Unidentified");
        System.out.println("Key set is: " + datedMap.keySet());
        System.out.println("HomeworkFour is presented: " + datedMap.containsKey("HomeworkFour"));
        System.out.println("HomeworkFour status is: " + datedMap.get("HomeworkFour"));
        datedMap.remove("HomeworkFour");
        System.out.println("'After remove method' HomeworkFour status is: " + datedMap.get("HomeworkFour"));
        System.out.println("HomeworkFour is presented: " + datedMap.containsKey("HomeworkFour"));
        System.out.println("HomeworkOne was done at " + datedMap.getKeyLastInsertionDate("HomeworkOne").toString());
    }
}
