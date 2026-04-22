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
            if (other == null) {
                throw new IllegalArgumentException("Second operand cannot be null");
            }

            double base1 = this.unit.toBase(this.value);
            double base2 = other.unit.toBase(other.value);

            double sumBase = base1 + base2;

            double resultValue = this.unit.fromBase(sumBase);

            return new Quantity(resultValue, this.unit);
        }

        public double convertTo(LengthUnit targetUnit) {
            double base = unit.toBase(value);
            return targetUnit.fromBase(base);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;

            double base1 = this.unit.toBase(this.value);
            double base2 = other.unit.toBase(other.value);

            return Double.compare(base1, base2) == 0;
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    public static void main(String[] args) {

        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCH);

        Quantity result1 = q1.add(q2);
        System.out.println("1 ft + 12 inch = " + result1);

        Quantity q3 = new Quantity(12.0, LengthUnit.INCH);
        Quantity q4 = new Quantity(1.0, LengthUnit.FEET);

        Quantity result2 = q3.add(q4);
        System.out.println("12 inch + 1 ft = " + result2);

        Quantity q5 = new Quantity(1.0, LengthUnit.YARD);
        Quantity q6 = new Quantity(3.0, LengthUnit.FEET);

        System.out.println("1 yard + 3 ft = " + q5.add(q6));

        Quantity q7 = new Quantity(2.54, LengthUnit.CENTIMETER);
        Quantity q8 = new Quantity(1.0, LengthUnit.INCH);

        System.out.println("2.54 cm + 1 inch = " + q7.add(q8));
    }
}