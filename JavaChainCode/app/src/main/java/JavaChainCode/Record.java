

package JavaChainCode;

import java.util.Objects;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import com.owlike.genson.annotation.JsonProperty;

@DataType()
public final class Record {

    @Property()
    private final String RecordID;

    @Property()
    private final String Title;
    
    @Property()
    private final String PatientID;
    
    @Property()
    private final String PatientName;
    
    @Property()
    private final String Diagnosis;
    
    @Property()
    private final String Prescriptions;
    
    @Property()
    private final String DoctorName;
    
    @Property()
    private final String DateTime;
    
    @Property()
    private final String FacilityName;

    public String getRecordID() {
        return RecordID;
    }

    public String getTitle() {
        return Title;
    }

    public String getDiagnosis() {
        return Diagnosis;
    }

    public String getPrescriptions() {
        return Prescriptions;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public String getDateTime() {
        return DateTime;
    }

    public String getPatientID() {
        return PatientID;
    }

    public String getPatientName() {
        return PatientName;
    }
    
    

    public Record(@JsonProperty("RecordID") final String RecordID, 
            @JsonProperty("Title") final String Title,
            @JsonProperty("PatientID") final String PatientID, 
            @JsonProperty("PatientName") final String PatientName, 
            @JsonProperty("Diagnosis") final String Diagnosis,
            @JsonProperty("Prescriptions") final String Prescriptions,
            @JsonProperty("DoctorName") final String DoctorName,
            @JsonProperty("DateTime") final String DateTime,) {
        this.RecordID = RecordID;
        this.Title = Title;
        this.PatientID = PatientID;
        this.PatientName = PatientName;
        this.Diagnosis = Diagnosis;
        this.Prescriptions = Prescriptions;
        this.DoctorName = DoctorName;
        this.DateTime = DateTime ;
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
                new String[] {getRecordID(), getTitle(), getPatientID(), getPatientName(), getDiagnosis(), getPrescriptions(),getDoctorName(),getDateTime(), getFacilityName()},
                new String[] {other.getRecordID(), other.getTitle(), other.getPatientID(), other.getPatientName(), other.getDiagnosis(), 
                    other.getPrescriptions(),other.getDoctorName(),other.getDateTime()});
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRecordID(), getTitle(), getPatientID(), getPatientName(), getDiagnosis(), getPrescriptions(),getDoctorName(),getDateTime());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + 
                " [RecordID=" + RecordID 
                + ", Title=" + Title 
                + ", PatientID=" + PatientID 
                + ", PatientName=" + PatientName
                + ", Diagnosis=" + Diagnosis
                + ", Prescriptions=" + Prescriptions
                + ", Doctorname=" + DoctorName
                + ", DateTime=" + DateTime
                + "]";
    }
}
