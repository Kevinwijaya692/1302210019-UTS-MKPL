package lib;

public class TaxFunction {

    private static final double TAX_RATE = 0.05;
    private static final int MAX_CHILDREN_FOR_TAX_RELIEF = 3;
    private static final int TAX_RELIEF_FOR_SINGLE = 54000000;
    private static final int TAX_RELIEF_FOR_MARRIED = 58500000; // (54000000 + 4500000)
    private static final int TAX_RELIEF_PER_CHILD = 1500000;

    /**
     * Fungsi untuk menghitung jumlah pajak penghasilan pegawai yang harus dibayarkan setahun.
     * 
     * Pajak dihitung sebagai 5% dari penghasilan bersih tahunan (gaji dan pemasukan bulanan lainnya dikalikan jumlah bulan bekerja dikurangi pemotongan) dikurangi penghasilan tidak kena pajak.
     * 
     * Jika pegawai belum menikah dan belum punya anak maka penghasilan tidak kena pajaknya adalah Rp 54.000.000.
     * Jika pegawai sudah menikah maka penghasilan tidak kena pajaknya ditambah sebesar Rp 4.500.000.
     * Jika pegawai sudah memiliki anak maka penghasilan tidak kena pajaknya ditambah sebesar Rp 4.500.000 per anak sampai anak ketiga.
     * 
     */
    public static int calculateTax(int monthlySalary, int otherMonthlyIncome, int numberOfMonthWorking, int deductible, boolean isMarried, int numberOfChildren) {

        if (numberOfMonthWorking > 12) {
            System.err.println("More than 12 month working per year");
            return 0; // Jika lebih dari 12 bulan bekerja, pajak dianggap nol
        }

        int taxRelief = calculateTaxRelief(isMarried, numberOfChildren);
        double taxableIncome = calculateTaxableIncome(monthlySalary, otherMonthlyIncome, numberOfMonthWorking, deductible, taxRelief);
        int tax = calculateTaxAmount(taxableIncome);

        // Pajak tidak boleh negatif, jika negatif maka dianggap nol
        return Math.max(0, tax);
    }

    private static int calculateTaxRelief(boolean isMarried, int numberOfChildren) {
        int taxRelief = isMarried ? TAX_RELIEF_FOR_MARRIED : TAX_RELIEF_FOR_SINGLE;
        return taxRelief + Math.min(numberOfChildren, MAX_CHILDREN_FOR_TAX_RELIEF) * TAX_RELIEF_PER_CHILD;
    }

    private static double calculateTaxableIncome(int monthlySalary, int otherMonthlyIncome, int numberOfMonthWorking, int deductible, int taxRelief) {
        return ((monthlySalary + otherMonthlyIncome) * numberOfMonthWorking) - deductible - taxRelief;
    }

    private static int calculateTaxAmount(double taxableIncome) {
        return (int) Math.round(TAX_RATE * taxableIncome);
    }
}
