
package JavaChainCode;

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

@Contract(
        name = "basic",
        info = @Info(
                title = "Record Transfer",
                description = "Chaincode to Transfer Medical Record",
                version = "0.0.1-SNAPSHOT"))
@Default
public final class RecordTransfer implements ContractInterface {

    private final Genson genson = new Genson();

    private enum RecordTransferErrors {
        RECORD_NOT_FOUND,
        RECORD_ALREADY_EXISTS
    }

    //Create Initial Record on the ledger
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public void InitLedger(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();

        CreateRecord(ctx, "Record1", "Title1", "PatientID1", "PatientName1", "Diagnosis1", "Prescriptions1", "DoctorName1", "DateTime1", "FacilityName1");
    }

    //Create New Record
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public Record CreateRecord(final Context ctx, final String RecordID, final String Title, final String PatientID, final String PatientName, 
            final String Diagnosis, final String Prescriptions, final String DoctorName, final String DateTime, final String FacilityName ) {
        ChaincodeStub stub = ctx.getStub();

        if (RecordExists(ctx, RecordID)) {
            String errorMessage = String.format("Record %s already exists", RecordID);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, RecordTransferErrors.RECORD_ALREADY_EXISTS.toString());
        }

        Record record = new Record(RecordID, Title, PatientID, PatientName, Diagnosis, Prescriptions, DoctorName, DateTime, FacilityName);
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
            throw new ChaincodeException(errorMessage, RecordTransferErrors.RECORD_NOT_FOUND.toString());
        }

        Record Record = genson.deserialize(RecordJSON, Record.class);
        return Record;
    }

    //Update Record
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public Record UpdateRecord(final Context ctx, final String RecordID, final String Title, final String PatientID, final String PatientName, 
            final String Diagnosis, final String Prescriptions, final String DoctorName, final String DateTime, final String FacilityName) {
        ChaincodeStub stub = ctx.getStub();

        if (!RecordExists(ctx, RecordID)) {
            String errorMessage = String.format("Record %s does not exist", RecordID);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, RecordTransferErrors.RECORD_NOT_FOUND.toString());
        }

        Record newRecord = new Record(RecordID, Title, PatientID, PatientName, Diagnosis, Prescriptions, DoctorName, DateTime, FacilityName);
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
            throw new ChaincodeException(errorMessage, RecordTransferErrors.RECORD_NOT_FOUND.toString());
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

    //Change the owner
//    @Transaction(intent = Transaction.TYPE.SUBMIT)
//    public String TransferRecord(final Context ctx, final String RecordID, final String newOwner) {
//        ChaincodeStub stub = ctx.getStub();
//        String RecordJSON = stub.getStringState(RecordID);
//
//        if (RecordJSON == null || RecordJSON.isEmpty()) {
//            String errorMessage = String.format("Record %s does not exist", RecordID);
//            System.out.println(errorMessage);
//            throw new ChaincodeException(errorMessage, RecordTransferErrors.RECORD_NOT_FOUND.toString());
//        }
//
//        Record Record = genson.deserialize(RecordJSON, Record.class);
//
//        Record newRecord = new Record(Record.getRecordID(), Record.getColor(), Record.getSize(), newOwner, Record.getAppraisedValue());
//        //Use a Genson to conver the Record into string, sort it alphabetically and serialize it into a json string
//        String sortedJson = genson.serialize(newRecord);
//        stub.putStringState(RecordID, sortedJson);
//
//        return Record.getOwner();
    //}

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
