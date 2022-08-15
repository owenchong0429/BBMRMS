
package BBMRMSChaincode;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public final class RecordTest {

    @Nested
    class Equality {

        @Test
        public void isReflexive() {
            Record record = new Record("Record1", "PatientID1", "MedicalInfo1", "DoctorName1", "DateTime1");

            assertThat(record).isEqualTo(record);
        }

        @Test
        public void isSymmetric() {
            Record RecordA = new Record("Record1", "PatientID1", "MedicalInfo1", "DoctorName1", "DateTime1");
            Record RecordB = new Record("Record1", "PatientID1", "MedicalInfo1", "DoctorName1", "DateTime1");

            assertThat(RecordA).isEqualTo(RecordB);
            assertThat(RecordB).isEqualTo(RecordA);
        }

        @Test
        public void isTransitive() {
            Record RecordA = new Record("Record1", "PatientID1", "MedicalInfo1", "DoctorName1", "DateTime1");
            Record RecordB = new Record("Record1", "PatientID1", "MedicalInfo1", "DoctorName1", "DateTime1");
            Record RecordC = new Record("Record1", "PatientID1", "MedicalInfo1", "DoctorName1", "DateTime1");

            assertThat(RecordA).isEqualTo(RecordB);
            assertThat(RecordB).isEqualTo(RecordC);
            assertThat(RecordA).isEqualTo(RecordC);
        }

        @Test
        public void handlesInequality() {
            Record RecordA = new Record("Record1", "PatientID1", "MedicalInfo1", "DoctorName1", "DateTime1");
            Record RecordB = new Record("Record1", "PatientID2", "MedicalInfo2", "DoctorName2", "DateTime2");

            assertThat(RecordA).isNotEqualTo(RecordB);
        }

        @Test
        public void handlesOtherObjects() {
            Record RecordA = new Record("Record1", "PatientID1", "MedicalInfo1", "DoctorName1", "DateTime1");
            String RecordB = "not a Record";

            assertThat(RecordA).isNotEqualTo(RecordB);
        }

        @Test
        public void handlesNull() {
            Record Record = new Record("Record1", "PatientID1", "MedicalInfo1", "DoctorName1", "DateTime1");

            assertThat(Record).isNotEqualTo(null);
        }
    }

    @Test
    public void toStringIdentifiesRecord() {
        Record record = new Record("Record1", "PatientID1", "MedicalInfo1", "DoctorName1", "DateTime1");

        assertThat(record.toString()).isEqualTo("Record@e04f6c53 [RecordID=Record1, PatientID=PatientID1, MedicalInfo=MedicalInfo1, DoctorName=DoctorName1, DateTime=DateTime1]");
    }
}
