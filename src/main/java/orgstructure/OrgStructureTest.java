package orgstructure;

import java.io.File;
import java.io.IOException;

public class OrgStructureTest {
    public static void main(String[] args) {
        Employee employee;
        File file = new File("src/main/resources/employees.csv");
        try {
            OrgStructureParserImpl orgStructureParser = new OrgStructureParserImpl();
            employee = orgStructureParser.parseStructure(file);
            System.out.println("Main Boss id = " + employee.getId());
            System.out.println("Main Boss name = " + employee.getName());
            System.out.println("His Boss id = " + employee.getBossId());
            System.out.println("His subordinate are: " + employee.getSubordinate());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
