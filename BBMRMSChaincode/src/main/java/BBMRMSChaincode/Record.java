

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
    private final String PatientID;
    
    @Property()
    private final String MedicalInfo;
    
    @Property()
    private final String DoctorName;
    
    @Property()
    private final String DateTime;

    public String getRecordID() {
        return RecordID;
    }

    public String getPatientID() {
        return PatientID;
    }

    public String getMedicalInfo() {
        return MedicalInfo;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public String getDateTime() {
        return DateTime;
    }

    public Record(
            @JsonProperty("RecordID") final String RecordID, 
            @JsonProperty("PatientID") final String PatientID,
            @JsonProperty("MedicalInfo") final String MedicalInfo, 
            @JsonProperty("DoctorName") final String DoctorName,
            @JsonProperty("DateTime") final String DateTime) {
        this.RecordID = RecordID;
        this.PatientID = PatientID;
        this.MedicalInfo = MedicalInfo;
        this.DoctorName = DoctorName;
        this.DateTime = DateTime;
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
        return this.getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + " [RecordID=" + RecordID 
                + ", PatientID=" + PatientID
                + ", MedicalInfo=" + MedicalInfo
                + ", Doctorname=" + DoctorName
                + ", DateTime=" + DateTime
                + "]";
    }
}
