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
    }

    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            this.value = value;
            this.unit = unit;
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj) return true;

            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;

            double thisInFeet = this.unit.toBase(this.value);
            double otherInFeet = other.unit.toBase(other.value);

            return Double.compare(thisInFeet, otherInFeet) == 0;
        }
    }

    public static void main(String[] args) {

        Quantity q1 = new Quantity(1.0, LengthUnit.YARD);
        Quantity q2 = new Quantity(3.0, LengthUnit.FEET);

        Quantity q3 = new Quantity(1.0, LengthUnit.YARD);
        Quantity q4 = new Quantity(36.0, LengthUnit.INCH);

        Quantity q5 = new Quantity(1.0, LengthUnit.CENTIMETER);
        Quantity q6 = new Quantity(0.393701, LengthUnit.INCH);

        System.out.println("1 yard == 3 feet → " + q1.equals(q2));
        System.out.println("1 yard == 36 inches → " + q3.equals(q4));
        System.out.println("1 cm == 0.393701 inch → " + q5.equals(q6));
    }
}