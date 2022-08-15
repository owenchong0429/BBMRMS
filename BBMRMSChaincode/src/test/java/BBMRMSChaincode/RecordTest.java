
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
            Record recordA = new Record("Record1", "PatientID1", "MedicalInfo1", "DoctorName1", "DateTime1");
            Record recordB = new Record("Record1", "PatientID1", "MedicalInfo1", "DoctorName1", "DateTime1");

            assertThat(recordA).isEqualTo(recordB);
            assertThat(recordB).isEqualTo(recordA);
        }

        @Test
        public void isTransitive() {
            Record recordA = new Record("Record1", "PatientID1", "MedicalInfo1", "DoctorName1", "DateTime1");
            Record recordB = new Record("Record1", "PatientID1", "MedicalInfo1", "DoctorName1", "DateTime1");
            Record recordC = new Record("Record1", "PatientID1", "MedicalInfo1", "DoctorName1", "DateTime1");

            assertThat(recordA).isEqualTo(recordB);
            assertThat(recordB).isEqualTo(recordC);
            assertThat(recordA).isEqualTo(recordC);
        }

        @Test
        public void handlesInequality() {
            Record recordA = new Record("Record1", "PatientID1", "MedicalInfo1", "DoctorName1", "DateTime1");
            Record recordB = new Record("Record1", "PatientID2", "MedicalInfo2", "DoctorName2", "DateTime2");

            assertThat(recordA).isNotEqualTo(recordB);
        }

        @Test
        public void handlesOtherObjects() {
            Record recordA = new Record("Record1", "PatientID1", "MedicalInfo1", "DoctorName1", "DateTime1");
            String recordB = "not a record";

            assertThat(recordA).isNotEqualTo(recordB);
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

        assertThat(record.toString()).isEqualTo("Record@e04f6c53 [recordID=Record1, patientID=PatientID1, medicalInfo=MedicalInfo1, doctorName=DoctorName1, dateTime=DateTime1]");
    }
}
