public class Quantity_Measurement_App {

    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CENTIMETER(0.393701 / 12.0);

        private final double toFeet;

        LengthUnit(double toFeet) {
            this.toFeet = toFeet;
        }

        public double toBase(double value) {
            return value * toFeet;
        }

        public double fromBase(double feetValue) {
            return feetValue / toFeet;
        }
    }

    static class Quantity {
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

        public Quantity add(Quantity other) {
            return add(other, this.unit);
        }

        public Quantity add(Quantity other, LengthUnit targetUnit) {

            if (other == null) {
                throw new IllegalArgumentException("Second operand cannot be null");
            }
            if (targetUnit == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }

            double base1 = this.unit.toBase(this.value);
            double base2 = other.unit.toBase(other.value);

            double sumBase = base1 + base2;

            double resultValue = targetUnit.fromBase(sumBase);

            return new Quantity(resultValue, targetUnit);
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    public static void main(String[] args) {

        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCH);

        System.out.println("Feet: " + q1.add(q2, LengthUnit.FEET));
        System.out.println("Inches: " + q1.add(q2, LengthUnit.INCH));
        System.out.println("Yards: " + q1.add(q2, LengthUnit.YARD));

        Quantity q3 = new Quantity(1.0, LengthUnit.YARD);
        Quantity q4 = new Quantity(3.0, LengthUnit.FEET);

        System.out.println("Yard + Feet → " + q3.add(q4, LengthUnit.YARD));

        Quantity q5 = new Quantity(2.54, LengthUnit.CENTIMETER);
        Quantity q6 = new Quantity(1.0, LengthUnit.INCH);

        System.out.println("CM result → " + q5.add(q6, LengthUnit.CENTIMETER));
    }
}