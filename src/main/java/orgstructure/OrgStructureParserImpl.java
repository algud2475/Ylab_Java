package orgstructure;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class OrgStructureParserImpl implements OrgStructureParser {
    private List<String[]> employeesDataList = new ArrayList<>();
    private HashMap<Long, Employee> employeesIdMap = new HashMap<>();
    private Employee mainBoss;

    private Employee createEmployee(Long id, Long bossId, String name, String position) {
        Employee employee = new Employee();
        employee.setId(id);
        employee.setBossId(bossId);
        employee.setName(name);
        employee.setPosition(position);
        return employee;
    }

    private void parseEmployeesIdMap() {
        for (String[] employeeDataLine : employeesDataList) {
            Long id = Long.parseLong(employeeDataLine[0]);
            Long bossId;
            if (employeeDataLine[1] == "") {
                bossId = null;
            } else {
                bossId = Long.parseLong(employeeDataLine[1]);
            }
            String name = employeeDataLine[2];
            String position = employeeDataLine[3];
            employeesIdMap.put(id, createEmployee(id, bossId, name, position));
        }
    }

    private void setUpStructureEmployees() {
        for (Employee employee : employeesIdMap.values()) {
            if (employee.getBossId() != null) {
                employee.setBoss(employeesIdMap.get(employee.getBossId()));
                employee.getBoss().getSubordinate().add(employee);
            } else {
                mainBoss = employee;
            }
        }

    }

    public Employee parseStructure(File csvFile) throws IOException {
        Scanner scan = new Scanner(csvFile);
        scan.nextLine();
        while (scan.hasNextLine()) {
            employeesDataList.add(scan.nextLine().split(";"));
        }
        parseEmployeesIdMap();
        setUpStructureEmployees();
        return mainBoss;
    }
}
