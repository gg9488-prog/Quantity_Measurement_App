public class Quantity_Measurement_App {

    public static void main(String[] args) {

        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCH);

        System.out.println("Equality (1 ft == 12 inch): " + q1.equals(q2));

        System.out.println("Convert 1 ft → inch: " + q1.convertTo(LengthUnit.INCH));

        System.out.println("Add (Feet): " + q1.add(q2, LengthUnit.FEET));

        System.out.println("Add (Inch): " + q1.add(q2, LengthUnit.INCH));

        System.out.println("Add (Yard): " + q1.add(q2, LengthUnit.YARD));

        Quantity q3 = new Quantity(1.0, LengthUnit.YARD);
        Quantity q4 = new Quantity(3.0, LengthUnit.FEET);

        System.out.println("1 yard + 3 ft → " + q3.add(q4, LengthUnit.YARD));

        Quantity q5 = new Quantity(2.54, LengthUnit.CENTIMETER);
        System.out.println("2.54 cm → inch: " + q5.convertTo(LengthUnit.INCH));
    }
}

/* ===================== LENGTH UNIT ===================== */

enum LengthUnit {

    FEET(1.0),
    INCH(1.0 / 12.0),
    YARD(3.0),
    CENTIMETER(0.393701 / 12.0);

    private final double toFeet;

    LengthUnit(double toFeet) {
        this.toFeet = toFeet;
    }

    public double convertToBaseUnit(double value) {
        return value * toFeet;
    }

    public double convertFromBaseUnit(double baseValue) {
        return baseValue / toFeet;
    }
}

/* ===================== QUANTITY ===================== */

class Quantity {

    private final double value;
    private final LengthUnit unit;

    public Quantity(double value, LengthUnit unit) {

        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }

        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }

        this.value = value;
        this.unit = unit;
    }

    public Quantity convertTo(LengthUnit targetUnit) {

        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double base = unit.convertToBaseUnit(value);
        double result = targetUnit.convertFromBaseUnit(base);

        return new Quantity(result, targetUnit);
    }

    public Quantity add(Quantity other, LengthUnit targetUnit) {

        if (other == null || targetUnit == null) {
            throw new IllegalArgumentException("Invalid input");
        }

        double base1 = unit.convertToBaseUnit(value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        double sum = base1 + base2;

        double result = targetUnit.convertFromBaseUnit(sum);

        return new Quantity(result, targetUnit);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Quantity other = (Quantity) obj;

        double base1 = unit.convertToBaseUnit(value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        return Double.compare(base1, base2) == 0;
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }
}