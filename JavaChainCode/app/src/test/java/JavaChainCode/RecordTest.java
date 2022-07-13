
package JavaChainCode;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public final class RecordTest {

    @Nested
    class Equality {

        @Test
        public void isReflexive() {
            Record record = new Record("Record1", "Title1", "PatientID1", "PatientName1", "Diagnosis1", "Prescriptions1", "DoctorName1", "DateTime1", "FacilityName1");

            assertThat(record).isEqualTo(record);
        }

        @Test
        public void isSymmetric() {
            Record RecordA = new Record("Record1", "Title1", "PatientID1", "PatientName1", "Diagnosis1", "Prescriptions1", "DoctorName1", "DateTime1", "FacilityName1");
            Record RecordB = new Record("Record1", "Title1", "PatientID1", "PatientName1", "Diagnosis1", "Prescriptions1", "DoctorName1", "DateTime1", "FacilityName1");

            assertThat(RecordA).isEqualTo(RecordB);
            assertThat(RecordB).isEqualTo(RecordA);
        }

        @Test
        public void isTransitive() {
            Record RecordA = new Record("Record1", "Title1", "PatientID1", "PatientName1", "Diagnosis1", "Prescriptions1", "DoctorName1", "DateTime1", "FacilityName1");
            Record RecordB = new Record("Record1", "Title1", "PatientID1", "PatientName1", "Diagnosis1", "Prescriptions1", "DoctorName1", "DateTime1", "FacilityName1");
            Record RecordC = new Record("Record1", "Title1", "PatientID1", "PatientName1", "Diagnosis1", "Prescriptions1", "DoctorName1", "DateTime1", "FacilityName1");

            assertThat(RecordA).isEqualTo(RecordB);
            assertThat(RecordB).isEqualTo(RecordC);
            assertThat(RecordA).isEqualTo(RecordC);
        }

        @Test
        public void handlesInequality() {
            Record RecordA = new Record("Record1", "Title1", "PatientID1", "PatientName1", "Diagnosis1", "Prescriptions1", "DoctorName1", "DateTime1", "FacilityName1");
            Record RecordB = new Record("Record2", "Title2", "PatientID2", "PatientName2", "Diagnosis2", "Prescriptions2", "DoctorName2", "DateTime2", "FacilityName2");

            assertThat(RecordA).isNotEqualTo(RecordB);
        }

        @Test
        public void handlesOtherObjects() {
            Record RecordA = new Record("Record1", "Title1", "PatientID1", "PatientName1", "Diagnosis1", "Prescriptions1", "DoctorName1", "DateTime1", "FacilityName1");
            String RecordB = "not a Record";

            assertThat(RecordA).isNotEqualTo(RecordB);
        }

        @Test
        public void handlesNull() {
            Record Record = new Record("Record1", "Title1", "PatientID1", "PatientName1", "Diagnosis1", "Prescriptions1", "DoctorName1", "DateTime1", "FacilityName1");

            assertThat(Record).isNotEqualTo(null);
        }
    }

    @Test
    public void toStringIdentifiesRecord() {
        Record Record = new Record("Record1", "Title1", "PatientID1", "PatientName1", "Diagnosis1", "Prescriptions1", "DoctorName1", "DateTime1", "FacilityName1");

        assertThat(Record.toString()).isEqualTo("Record@e04f6c53 [RecordID=Record1, color=Blue, size=20, owner=Guy, appraisedValue=100]");
    }
}
