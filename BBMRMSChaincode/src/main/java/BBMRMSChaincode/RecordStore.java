
package BBMRMSChaincode;

import java.util.ArrayList;
import java.util.List;


import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import com.owlike.genson.Genson;
import org.hyperledger.fabric.contract.annotation.License;

@Contract(
        name = "basic",
        info = @Info(
                title = "BBMRMS",
                license = @License(
                        name = "Apache 2.0 License",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"),
                description = "Chaincode to Store Medical Record",
                version = "0.0.1-SNAPSHOT"))
@Default
public final class RecordStore implements ContractInterface {

    private final Genson genson = new Genson();

    private enum RecordStoreErrors {
        RECORD_NOT_FOUND,
        RECORD_ALREADY_EXISTS
    }

    //Create Initial Record on the ledger
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public void InitLedger(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();

        CreateRecord(ctx, "Record1", "PatientID1", "MedicalInfo1", "DoctorName1", "DateTime1");
        CreateRecord(ctx, "Record2", "PatientID2", "MedicalInfo2", "DoctorName2", "DateTime2");
        CreateRecord(ctx, "Record3", "PatientID3", "MedicalInfo3", "DoctorName3", "DateTime3");
        CreateRecord(ctx, "Record4", "PatientID4", "MedicalInfo4", "DoctorName4", "DateTime4");
    }

    //Create New Record
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public Record CreateRecord(final Context ctx, final String RecordID, final String PatientID, final String MedicalInfo, final String DoctorName, final String DateTime) {
        ChaincodeStub stub = ctx.getStub();

        if (RecordExists(ctx, RecordID)) {
            String errorMessage = String.format("Record %s already exists", RecordID);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, RecordStoreErrors.RECORD_ALREADY_EXISTS.toString());
        }

        Record record = new Record(RecordID, PatientID, MedicalInfo, DoctorName, DateTime);
        //Use Genson to convert the Record into string, sort it alphabetically and serialize it into a json string
        String sortedJson = genson.serialize(record);
        stub.putStringState(RecordID, sortedJson);

        return record;
    }

    //Retrieve and read Record
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public Record ReadRecord(final Context ctx, final String RecordID) {
        ChaincodeStub stub = ctx.getStub();
        String RecordJSON = stub.getStringState(RecordID);

        if (RecordJSON == null || RecordJSON.isEmpty()) {
            String errorMessage = String.format("Record %s does not exist", RecordID);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, RecordStoreErrors.RECORD_NOT_FOUND.toString());
        }

        Record record = genson.deserialize(RecordJSON, Record.class);
        return record;
    }

    //Update Record
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public Record UpdateRecord(final Context ctx, final String RecordID, final String PatientID, final String MedicalInfo, final String DoctorName, final String DateTime) {
        ChaincodeStub stub = ctx.getStub();

        if (!RecordExists(ctx, RecordID)) {
            String errorMessage = String.format("Record %s does not exist", RecordID);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, RecordStoreErrors.RECORD_NOT_FOUND.toString());
        }

        Record newRecord = new Record(RecordID, PatientID, MedicalInfo, DoctorName, DateTime);
        //Use Genson to convert the Record into string, sort it alphabetically and serialize it into a json string
        String sortedJson = genson.serialize(newRecord);
        stub.putStringState(RecordID, sortedJson);
        return newRecord;
    }

    //Delete Record
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public void DeleteRecord(final Context ctx, final String RecordID) {
        ChaincodeStub stub = ctx.getStub();

        if (!RecordExists(ctx, RecordID)) {
            String errorMessage = String.format("Record %s does not exist", RecordID);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, RecordStoreErrors.RECORD_NOT_FOUND.toString());
        }

        stub.delState(RecordID);
    }

    //Check whether record exists or not
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public boolean RecordExists(final Context ctx, final String RecordID) {
        ChaincodeStub stub = ctx.getStub();
        String RecordJSON = stub.getStringState(RecordID);

        return (RecordJSON != null && !RecordJSON.isEmpty());
    }

    //Get all record
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public String GetAllRecords(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();

        List<Record> queryResults = new ArrayList<Record>();

        QueryResultsIterator<KeyValue> results = stub.getStateByRange("", "");

        for (KeyValue result: results) {
            Record Record = genson.deserialize(result.getStringValue(), Record.class);
            System.out.println(Record);
            queryResults.add(Record);
        }

        final String response = genson.serialize(queryResults);

        return response;
    }
}
