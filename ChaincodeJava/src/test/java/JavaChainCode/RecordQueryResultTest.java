/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JavaChainCode;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
/**
 *
 * @author owenc
 */
public final class RecordQueryResultTest {
    
    @Nested
    class Equality {

        @Test
        public void isReflexive() {
            RecordQueryResult record = new RecordQueryResult("Record1", new Record("Record1", 
                "PatientName1",
                "IC_Passport1",
                "Title1",
                "Diagnosis1",
                "Treatment1",
                "Prescriptions1",
                "DoctorName1",
                "Date_Time1",
                "Test_Lab_Result1"));

            assertThat(record).isEqualTo(record);
        }

        @Test
        public void isSymmetric() {
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
            RecordQueryResult recordA = new RecordQueryResult("Record1", Record);
            RecordQueryResult recordB = new RecordQueryResult("Record1", Record);

            assertThat(recordA).isEqualTo(recordB);
            assertThat(recordB).isEqualTo(recordA);
        }

        @Test
        public void isTransitive() {
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
            RecordQueryResult recordA = new RecordQueryResult("Record1", Record);
            RecordQueryResult recordB = new RecordQueryResult("Record1", Record);
            RecordQueryResult recordC = new RecordQueryResult("Record1", Record);

            assertThat(recordA).isEqualTo(recordB);
            assertThat(recordB).isEqualTo(recordC);
            assertThat(recordA).isEqualTo(recordC);
        }

        @Test
        public void handlesKeyInequality() {
            RecordQueryResult recordA = new RecordQueryResult("Record1", new Record("Record1", 
                "PatientName1",
                "IC_Passport1",
                "Title1",
                "Diagnosis1",
                "Treatment1",
                "Prescriptions1",
                "DoctorName1",
                "Date_Time1",
                "Test_Lab_Result1"));
            RecordQueryResult recordB = new RecordQueryResult("Record2", new Record("Record1", 
                "PatientName1",
                "IC_Passport1",
                "Title1",
                "Diagnosis1",
                "Treatment1",
                "Prescriptions1",
                "DoctorName1",
                "Date_Time1",
                "Test_Lab_Result1"));

            assertThat(recordA).isNotEqualTo(recordB);
        }

        @Test
        public void handlesRecordInequality() {
            RecordQueryResult recordA = new RecordQueryResult("Record1", new Record("Record1", 
                "PatientName1",
                "IC_Passport1",
                "Title1",
                "Diagnosis1",
                "Treatment1",
                "Prescriptions1",
                "DoctorName1",
                "Date_Time1",
                "Test_Lab_Result1"));
            RecordQueryResult recordB = new RecordQueryResult("Record1", new Record("Record2", 
                "PatientName2",
                "IC_Passport2",
                "Title2",
                "Diagnosis2",
                "Treatment2",
                "Prescriptions2",
                "DoctorName2",
                "Date_Time2",
                "Test_Lab_Result2"));

            assertThat(recordA).isNotEqualTo(recordB);
        }

        @Test
        public void handlesKeyRecordInequality() {
            RecordQueryResult recordA = new RecordQueryResult("Record1", new Record("Record1", 
                "PatientName1",
                "IC_Passport1",
                "Title1",
                "Diagnosis1",
                "Treatment1",
                "Prescriptions1",
                "DoctorName1",
                "Date_Time1",
                "Test_Lab_Result1"));
            RecordQueryResult recordB = new RecordQueryResult("Record2", new Record("Record2", 
                "PatientName2",
                "IC_Passport2",
                "Title2",
                "Diagnosis2",
                "Treatment2",
                "Prescriptions2",
                "DoctorName2",
                "Date_Time2",
                "Test_Lab_Result2"));

            assertThat(recordA).isNotEqualTo(recordB);
        }

        @Test
        public void handlesOtherObjects() {
            RecordQueryResult recordA = new RecordQueryResult("Record1", new Record("Record1", 
                "PatientName1",
                "IC_Passport1",
                "Title1",
                "Diagnosis1",
                "Treatment1",
                "Prescriptions1",
                "DoctorName1",
                "Date_Time1",
                "Test_Lab_Result1"));
            String recordB = "not a Record";

            assertThat(recordA).isNotEqualTo(recordB);
        }

        @Test
        public void handlesNull() {
            RecordQueryResult record = new RecordQueryResult("Record1", new Record("Record1", 
                "PatientName1",
                "IC_Passport1",
                "Title1",
                "Diagnosis1",
                "Treatment1",
                "Prescriptions1",
                "DoctorName1",
                "Date_Time1",
                "Test_Lab_Result1"));

            assertThat(record).isNotEqualTo(null);
        }
    }

    @Test
    public void toStringIdentifiesRecordQueryResult() {
        RecordQueryResult record = new RecordQueryResult("Record1", new Record("Record1", 
                "PatientName1",
                "IC_Passport1",
                "Title1",
                "Diagnosis1",
                "Treatment1",
                "Prescriptions1",
                "DoctorName1",
                "Date_Time1",
                "Test_Lab_Result1"));

        assertThat(record.toString()).isEqualTo("RecordQueryResult@65766eb3 [key=Record1, "
                + "record=Record@61a77e4f "
                + "[RecordID = Record1, "
                + "PatientName = PatientName1, "
                + "IC_Passport = IC_Passport1, "
                + "Diagnosis = Diagnosis1"
                + "Treatment = Treatment1"
                + "Prescriptions = Prescriptions1"
                + "DoctorName = DoctorName1"
                + "Date_Time = Date_Time1"
                + "Test_Lab_Result = Test_Lab_Result1"
                + "]]");
    }
}
