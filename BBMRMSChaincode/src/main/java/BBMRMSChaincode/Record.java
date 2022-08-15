

package BBMRMSChaincode;

import java.util.Objects;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import com.owlike.genson.annotation.JsonProperty;

@DataType()
public final class Record {

    @Property()
    private final String recordID;

    @Property()
    private final String patientID;
    
    @Property()
    private final String medicalInfo;
  
    @Property()
    private final String doctorName;
    
    @Property()
    private final String dateTime;

    public String getRecordID() {
        return recordID;
    }

    public String getPatientID() {
        return patientID;
    }

    public String getMedicalInfo() {
        return medicalInfo;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDateTime() {
        return dateTime;
    }
    
    public Record(
            @JsonProperty("RecordID") final String recordID, 
            @JsonProperty("PatientID") final String patientID,
            @JsonProperty("MedicalInfo") final String medicalInfo, 
            @JsonProperty("DoctorName") final String doctorName,
            @JsonProperty("DateTime") final String dateTime) {
        this.recordID = recordID;
        this.patientID = patientID;
        this.medicalInfo = medicalInfo;
        this.doctorName = doctorName;
        this.dateTime = dateTime;
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
                new String[] {getRecordID(), getPatientID(), getMedicalInfo(), getDoctorName(), getDateTime()},
                new String[] {other.getRecordID(), other.getPatientID(), other.getMedicalInfo(), other.getDoctorName(), other.getDateTime()});
    }
    

    @Override
    public int hashCode() {
        return Objects.hash(getRecordID(), getPatientID(), getMedicalInfo(), getDoctorName(), getDateTime());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + " [RecordID=" + recordID 
                + ", PatientID=" + patientID
                + ", MedicalInfo=" + medicalInfo
                + ", Doctorname=" + doctorName
                + ", DateTime=" + dateTime
                + "]";
    }
}
