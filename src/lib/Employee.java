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

    private int yearJoined;
    private int monthJoined;
    private int dayJoined;
    private int monthWorkingInYear;

    private boolean isForeigner;
    private boolean gender;

    private int monthlySalary;
    private int otherMonthlyIncome;
    private int annualDeductible;

    private String spouseName;
    private String spouseIdNumber;

    private List<String> childNames;
    private List<String> childIdNumbers;

    public Employee(String employeeId, String firstName, String lastName, String idNumber, String address, int yearJoined, int monthJoined, int dayJoined, boolean isForeigner, boolean gender) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.address = address;
        this.yearJoined = yearJoined;
        this.monthJoined = monthJoined;
        this.dayJoined = dayJoined;
        this.isForeigner = isForeigner;
        this.gender = gender;

        childNames = new LinkedList<String>();
        childIdNumbers = new LinkedList<String>();
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
        this.spouseName = spouseName;
        this.spouseIdNumber = spouseIdNumber;
    }

    public void addChild(String childName, String childIdNumber) {
        childNames.add(childName);
        childIdNumbers.add(childIdNumber);
    }

    public int getAnnualIncomeTax() {
        int monthsWorked = calculateMonthsWorked();
        return TaxFunction.calculateTax(monthlySalary, otherMonthlyIncome, monthsWorked, annualDeductible, isSpouseIdEmpty(), getChildCount());
    }

    private int calculateMonthsWorked() {
        LocalDate currentDate = LocalDate.now();

        if (currentDate.getYear() == yearJoined) {
            return currentDate.getMonthValue() - monthJoined;
        } else {
            return 12;
        }
    }

    private boolean isSpouseIdEmpty() {
        return spouseIdNumber.isEmpty();
    }

    private int getChildCount() {
        return childIdNumbers.size();
    }
}