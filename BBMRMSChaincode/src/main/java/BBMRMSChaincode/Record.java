

package BBMRMSChaincode;

import java.util.Objects;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import com.owlike.genson.annotation.JsonProperty;

@DataType()
public final class Record {

    @Property()
    private final String RecordID;

    @Property()
    private final String PatientName;
    
    @Property()
    private final String IC_Passport;
    
    @Property()
    private final String Title;
    
    @Property()
    private final String Diagnosis;
    
    @Property()
    private final String Treatment;
    
    @Property()
    private final String Prescriptions;
    
    @Property()
    private final String DoctorName;
    
    @Property()
    private final String Date_Time;
    
    @Property()
    private final String Test_Lab_Result;

    public String getRecordID() {
        return RecordID;
    }

    public String getPatientName() {
        return PatientName;
    }

    public String getIC_Passport() {
        return IC_Passport;
    }

    public String getTitle() {
        return Title;
    }

    public String getDiagnosis() {
        return Diagnosis;
    }

    public String getTreatment() {
        return Treatment;
    }

    public String getPrescriptions() {
        return Prescriptions;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public String getDate_Time() {
        return Date_Time;
    }

    public String getTest_Lab_Result() {
        return Test_Lab_Result;
    }
    
    

    public Record(
            @JsonProperty("RecordID") final String RecordID, 
            @JsonProperty("PatientName") final String PatientName,
            @JsonProperty("IC_Passport") final String IC_Passport, 
            @JsonProperty("Title") final String Title,
            @JsonProperty("Diagnosis") final String Diagnosis,
            @JsonProperty("Treatment") final String Treatment,
            @JsonProperty("Prescriptions") final String Prescriptions,
            @JsonProperty("DoctorName") final String DoctorName,
            @JsonProperty("Date_Time") final String Date_Time,
            @JsonProperty("Test_Lab_Result") final String Test_Lab_Result) {
        this.RecordID = RecordID;
        this.PatientName = PatientName;
        this.IC_Passport = IC_Passport;
        this.Title = Title;
        this.Diagnosis = Diagnosis;
        this.Treatment = Treatment;
        this.Prescriptions = Prescriptions;
        this.DoctorName = DoctorName;
        this.Date_Time = Date_Time;
        this.Test_Lab_Result = Test_Lab_Result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        Record other = (Record) obj;

        return Objects.deepEquals(
                new String[] {getRecordID(), getPatientName(), getIC_Passport(), getTitle(), getDiagnosis(), getTreatment(), getPrescriptions(), getDoctorName(), getDate_Time(), getTest_Lab_Result()},
                new String[] {other.getRecordID(), other.getPatientName(),other.getIC_Passport(), other.getTitle(), other.getDiagnosis(), other.getTreatment(), other.getPrescriptions(), other.getDoctorName(), other.getDate_Time(), other.getTest_Lab_Result()});
    }
    

    @Override
    public int hashCode() {
        return Objects.hash(getRecordID(), getPatientName(), getIC_Passport(), getTitle(), getDiagnosis(), getTreatment(), getPrescriptions(), getDoctorName(), getDate_Time(), getTest_Lab_Result());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + 
                " [RecordID=" + RecordID 
                + ", PatientName=" + PatientName
                + ", IC_Passport=" + IC_Passport
                + ", Title=" + Title 
                + ", Diagnosis=" + Diagnosis
                + ", Treatment=" + Treatment
                + ", Prescriptions=" + Prescriptions
                + ", Doctorname=" + DoctorName
                + ", Date_Time=" + Date_Time
                + ", Test_Lab_Result=" + Test_Lab_Result
                + "]";
    }
}
