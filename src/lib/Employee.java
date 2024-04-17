package lib;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Employee {

    private String employeeId;
    private String firstName;
    private String lastName;
    private String idNumber;
    private String address;

    private LocalDate joinDate;

    private boolean isForeigner;
    private boolean isMale; // Mengganti nama variabel gender menjadi isMale untuk lebih jelas

    private int monthlySalary;
    private int otherMonthlyIncome;
    private int annualDeductible;

    private Spouse spouse;
    private List<Child> children;

    public Employee(String employeeId, String firstName, String lastName, String idNumber, String address, LocalDate joinDate, boolean isForeigner, boolean isMale) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.address = address;
        this.joinDate = joinDate;
        this.isForeigner = isForeigner;
        this.isMale = isMale; // Mengubah nama variabel gender menjadi isMale

        children = new LinkedList<>();
        spouse = new Spouse();
    }

    public void setMonthlySalary(int grade) {
        int baseSalary = 0;

        if (grade == 1) {
            baseSalary = 3000000;
        } else if (grade == 2) {
            baseSalary = 5000000;
        } else if (grade == 3) {
            baseSalary = 7000000;
        }

        monthlySalary = isForeigner ? (int) (baseSalary * 1.5) : baseSalary;
    }

    public void setAnnualDeductible(int deductible) {
        this.annualDeductible = deductible;
    }

    public void setAdditionalIncome(int income) {
        this.otherMonthlyIncome = income;
    }

    public void setSpouse(String spouseName, String spouseIdNumber) {
        this.spouse.setName(spouseName);
        this.spouse.setIdNumber(spouseIdNumber);
    }

    public void addChild(String childName, String childIdNumber) {
        children.add(new Child(childName, childIdNumber));
    }

    public int getAnnualIncomeTax() {
        int monthsWorked = calculateMonthsWorked();
        return TaxFunction.calculateTax(monthlySalary, otherMonthlyIncome, monthsWorked, annualDeductible, spouse.isEmpty(), children.size());
    }

    private int calculateMonthsWorked() {
        LocalDate currentDate = LocalDate.now();

        if (currentDate.getYear() == joinDate.getYear()) {
            return currentDate.getMonthValue() - joinDate.getMonthValue();
        } else {
            return 12;
        }
    }
}

class Spouse {
    private String name;
    private String idNumber;

    public void setName(String name) {
        this.name = name;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public boolean isEmpty() {
        return name == null || name.isEmpty() || idNumber == null || idNumber.isEmpty();
    }
}

class Child {
    private String name;
    private String idNumber;

    public Child(String name, String idNumber) {
        this.name = name;
        this.idNumber = idNumber;
    }

}
