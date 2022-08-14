
package JavaChainCode;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public final class RecordTest {

    @Nested
    class Equality {

        @Test
        public void isReflexive() {
            Record record = new Record("Record1", 
                "PatientName1",
                "IC_Passport1",
                "Title1",
                "Diagnosis1",
                "Treatment1",
                "Prescriptions1",
                "DoctorName1",
                "Date_Time1",
                "Test_Lab_Result1");

            assertThat(record).isEqualTo(record);
        }

        @Test
        public void isSymmetric() {
            Record RecordA = new Record("Record1", 
                "PatientName1",
                "IC_Passport1",
                "Title1",
                "Diagnosis1",
                "Treatment1",
                "Prescriptions1",
                "DoctorName1",
                "Date_Time1",
                "Test_Lab_Result1");
            Record RecordB = new Record("Record1", 
                "PatientName1",
                "IC_Passport1",
                "Title1",
                "Diagnosis1",
                "Treatment1",
                "Prescriptions1",
                "DoctorName1",
                "Date_Time1",
                "Test_Lab_Result1");

            assertThat(RecordA).isEqualTo(RecordB);
            assertThat(RecordB).isEqualTo(RecordA);
        }

        @Test
        public void isTransitive() {
            Record RecordA = new Record("Record1", 
                "PatientName1",
                "IC_Passport1",
                "Title1",
                "Diagnosis1",
                "Treatment1",
                "Prescriptions1",
                "DoctorName1",
                "Date_Time1",
                "Test_Lab_Result1");
            Record RecordB = new Record("Record1", 
                "PatientName1",
                "IC_Passport1",
                "Title1",
                "Diagnosis1",
                "Treatment1",
                "Prescriptions1",
                "DoctorName1",
                "Date_Time1",
                "Test_Lab_Result1");
            Record RecordC = new Record("Record1", 
                "PatientName1",
                "IC_Passport1",
                "Title1",
                "Diagnosis1",
                "Treatment1",
                "Prescriptions1",
                "DoctorName1",
                "Date_Time1",
                "Test_Lab_Result1");

            assertThat(RecordA).isEqualTo(RecordB);
            assertThat(RecordB).isEqualTo(RecordC);
            assertThat(RecordA).isEqualTo(RecordC);
        }

        @Test
        public void handlesInequality() {
            Record RecordA = new Record("Record1", 
                "PatientName1",
                "IC_Passport1",
                "Title1",
                "Diagnosis1",
                "Treatment1",
                "Prescriptions1",
                "DoctorName1",
                "Date_Time1",
                "Test_Lab_Result1");
            Record RecordB = new Record("Record2", 
                "PatientName2",
                "IC_Passport2",
                "Title2",
                "Diagnosis2",
                "Treatment2",
                "Prescriptions2",
                "DoctorName2",
                "Date_Time2",
                "Test_Lab_Result2");

            assertThat(RecordA).isNotEqualTo(RecordB);
        }

        @Test
        public void handlesOtherObjects() {
            Record RecordA = new Record("Record1", 
                "PatientName1",
                "IC_Passport1",
                "Title1",
                "Diagnosis1",
                "Treatment1",
                "Prescriptions1",
                "DoctorName1",
                "Date_Time1",
                "Test_Lab_Result1");
            String RecordB = "not a Record";

            assertThat(RecordA).isNotEqualTo(RecordB);
        }

        @Test
        public void handlesNull() {
            Record Record = new Record("Record1", 
                "PatientName1",
                "IC_Passport1",
                "Title1",
                "Diagnosis1",
                "Treatment1",
                "Prescriptions1",
                "DoctorName1",
                "Date_Time1",
                "Test_Lab_Result1");

            assertThat(Record).isNotEqualTo(null);
        }
    }

    @Test
    public void toStringIdentifiesRecord() {
        Record Record = new Record("Record1", 
                "PatientName1",
                "IC_Passport1",
                "Title1",
                "Diagnosis1",
                "Treatment1",
                "Prescriptions1",
                "DoctorName1",
                "Date_Time1",
                "Test_Lab_Result1");

        assertThat(Record.toString()).isEqualTo("Record@e04f6c53 [RecordID=Record1, color=Blue, size=20, owner=Guy, appraisedValue=100]");
    }
}
