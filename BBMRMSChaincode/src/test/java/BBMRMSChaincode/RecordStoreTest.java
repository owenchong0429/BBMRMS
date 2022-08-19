
package BBMRMSChaincode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

public final class RecordStoreTest {

    private final class MockKeyValue implements KeyValue {

        private final String key;
        private final String value;

        MockKeyValue(final String key, final String value) {
            super();
            this.key = key;
            this.value = value;
        }

        @Override
        public String getKey() {
            return this.key;
        }

        @Override
        public String getStringValue() {
            return this.value;
        }

        @Override
        public byte[] getValue() {
            return this.value.getBytes();
        }

    }

    private final class MockRecordResultsIterator implements QueryResultsIterator<KeyValue> {

        private final List<KeyValue> recordList;

        MockRecordResultsIterator() {
            super();

            recordList = new ArrayList<KeyValue>();

            recordList.add(new MockKeyValue("Record1",
                    "{ \"recordID\": \"Record1\", \"patientID\": \"PatientID1\", \"medicalInfo\": \"MedicalInfo1\", \"doctorName\": \"DoctorName1\", \"dateTime\": \"DateTime1\" }"));
            
            recordList.add(new MockKeyValue("Record2",
                    "{ \"recordID\": \"Record2\", \"patientID\": \"PatientID2\", \"medicalInfo\": \"MedicalInfo2\", \"doctorName\": \"DoctorName2\", \"dateTime\": \"DateTime2\" }"));
            
            recordList.add(new MockKeyValue("Record3",
                    "{ \"recordID\": \"Record3\", \"patientID\": \"PatientID3\", \"medicalInfo\": \"MedicalInfo3\", \"doctorName\": \"DoctorName3\", \"dateTime\": \"DateTime3\" }"));
            
        }

        @Override
        public Iterator<KeyValue> iterator() {
            return recordList.iterator();
        }

        @Override
        public void close() throws Exception {
            // do nothing
        }

    }

    @Test
    public void invokeUnknownTransaction() {
        RecordStore contract = new RecordStore();
        Context ctx = mock(Context.class);

        Throwable thrown = catchThrowable(() -> {
            contract.unknownTransaction(ctx);
        });

        assertThat(thrown).isInstanceOf(ChaincodeException.class).hasNoCause()
                .hasMessage("Undefined contract method called");
        assertThat(((ChaincodeException) thrown).getPayload()).isEqualTo(null);

        verifyZeroInteractions(ctx);
    }

    @Nested
    class InvokeReadRecordTransaction {

        @Test
        public void whenRecordExists() {
            RecordStore contract = new RecordStore();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("Record1"))
                    .thenReturn("{ \"recordID\": \"Record1\", \"patientID\": \"PatientID1\", \"medicalInfo\": \"MedicalInfo1\", \"doctorName\": \"DoctorName1\", \"dateTime\": \"DateTime1\"}");

            Record Record = contract.ReadRecord(ctx, "Record1");

            assertThat(Record).isEqualTo(new Record("Record1", "PatientID1", "MedicalInfo1", "DoctorName1", "DateTime1"));
        }

        @Test
        public void whenRecordDoesNotExist() {
            RecordStore contract = new RecordStore();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("Record1")).thenReturn("");

            Throwable thrown = catchThrowable(() -> {
                contract.ReadRecord(ctx, "Record1");
            });

            assertThat(thrown).isInstanceOf(ChaincodeException.class).hasNoCause()
                    .hasMessage("Record Record1 does not exist");
            assertThat(((ChaincodeException) thrown).getPayload()).isEqualTo("RECORD_NOT_FOUND".getBytes());
        }
    }

    @Test
    void invokeInitLedgerTransaction() {
        RecordStore contract = new RecordStore();
        Context ctx = mock(Context.class);
        ChaincodeStub stub = mock(ChaincodeStub.class);
        when(ctx.getStub()).thenReturn(stub);

        contract.InitLedger(ctx);

        InOrder inOrder = inOrder(stub);
        inOrder.verify(stub).putStringState("Record1", "{\"dateTime\":\"DateTime1\",\"recordID\":\"Record1\",\"patientID\":\"PatientID1\",\"doctorName\":\"DoctorName1\",\"medicalInfo\":\"MedicalInfo1\"}");
        inOrder.verify(stub).putStringState("Record2", "{\"dateTime\":\"DateTime2\",\"recordID\":\"Record2\",\"patientID\":\"PatientID2\",\"doctorName\":\"DoctorName2\",\"medicalInfo\":\"MedicalInfo2\"}");
        inOrder.verify(stub).putStringState("Record3", "{\"dateTime\":\"DateTime3\",\"recordID\":\"Record3\",\"patientID\":\"PatientID3\",\"doctorName\":\"DoctorName3\",\"medicalInfo\":\"MedicalInfo3\"}");
        
    }

    @Nested
    class InvokeCreateRecordTransaction {

        @Test
        public void whenRecordExists() {
            RecordStore contract = new RecordStore();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("Record1"))
                    .thenReturn("{ \"recordID\": \"Record1\", \"patientID\": \"PatientID1\", \"medicalInfo\": \"MedicalInfo1\", \"doctorName\": \"DoctorName1\", \"dateTime\": \"DateTime1\"}");

            Throwable thrown = catchThrowable(() -> {
                contract.CreateRecord(ctx, "Record1", "PatientID1", "MedicalInfo1", "DoctorName1", "DateTime1");
            });

            assertThat(thrown).isInstanceOf(ChaincodeException.class).hasNoCause()
                    .hasMessage("Record Record1 already exists");
            assertThat(((ChaincodeException) thrown).getPayload()).isEqualTo("RECORD_ALREADY_EXISTS".getBytes());
        }

        @Test
        public void whenRecordDoesNotExist() {
            RecordStore contract = new RecordStore();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("Record1")).thenReturn("");

            Record Record = contract.CreateRecord(ctx, "Record1", "PatientID1", "MedicalInfo1", "DoctorName1", "DateTime1");

            assertThat(Record).isEqualTo(new Record("Record1", "PatientID1", "MedicalInfo1", "DoctorName1", "DateTime1"));
        }
    }

    @Test
    void invokeGetAllRecordsTransaction() {
        RecordStore contract = new RecordStore();
        Context ctx = mock(Context.class);
        ChaincodeStub stub = mock(ChaincodeStub.class);
        when(ctx.getStub()).thenReturn(stub);
        when(stub.getStateByRange("", "")).thenReturn(new MockRecordResultsIterator());

        String Records = contract.GetAllRecords(ctx);

        assertThat(Records).isEqualTo("[{\"dateTime\":\"DateTime1\",\"recordID\":\"Record1\",\"patientID\":\"PatientID1\",\"doctorName\":\"DoctorName1\",\"medicalInfo\":\"MedicalInfo1\"},"
                + "{ \"dateTime\":\"DateTime2\",\"recordID\":\"Record2\",\"patientID\":\"PatientID2\",\"doctorName\":\"DoctorName2\",\"medicalInfo\":\"MedicalInfo2\"},"
                + "{ \"dateTime\":\"DateTime3\",\"recordID\":\"Record3\",\"patientID\":\"PatientID3\",\"doctorName\":\"DoctorName3\",\"medicalInfo\":\"MedicalInfo3\"}]");

    }

    //@Nested
//    class StoreRecordTransaction {
//
//        @Test
//        public void whenRecordExists() {
//            RecordStore contract = new RecordStore();
//            Context ctx = mock(Context.class);
//            ChaincodeStub stub = mock(ChaincodeStub.class);
//            when(ctx.getStub()).thenReturn(stub);
//            when(stub.getStringState("Record1"))
//                    .thenReturn("Record1",
//                    "{ \"RecordID\": \"MockRecord1\", "
//                            + "\"Title\": \"MockTitle1\", "
//                            + "\"PatientID\": \"MockPatientID1\", "
//                            + "\"PatientName\": \"MockPatientName1\", "
//                            + "\"MedicalInfo\": \"MockMedicalInfo1\", "
//                            + "\"Prescriptions\": \"MockPrescriptions1\", "
//                            + "\"DoctorName\": \"MockDoctorName1\", "
//                            + "\"DateTime\": MockDateTime1, "
//                            + "\"Test_Lab_Result\": \"MockTest_Lab_Result1\",}");
//
//            String oldOwner = contract.StoreRecord(ctx, "Record1", "Dr Evil");
//
//            assertThat(oldOwner).isEqualTo("Tomoko");
//        }
//
//        @Test
//        public void whenRecordDoesNotExist() {
//            RecordStore contract = new RecordStore();
//            Context ctx = mock(Context.class);
//            ChaincodeStub stub = mock(ChaincodeStub.class);
//            when(ctx.getStub()).thenReturn(stub);
//            when(stub.getStringState("Record1")).thenReturn("");
//
//            Throwable thrown = catchThrowable(() -> {
//                contract.StoreRecord(ctx, "Record1", "Dr Evil");
//            });
//
//            assertThat(thrown).isInstanceOf(ChaincodeException.class).hasNoCause()
//                    .hasMessage("Record Record1 does not exist");
//            assertThat(((ChaincodeException) thrown).getPayload()).isEqualTo("Record_NOT_FOUND".getBytes());
//        }
//    }

    @Nested
    class UpdateRecordTransaction {

        @Test
        public void whenRecordExists() {
            RecordStore contract = new RecordStore();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("Record1"))
                    .thenReturn("{ \"recordID\": \"Record1\", \"patientID\": \"PatientID1\", \"medicalInfo\": \"MedicalInfo1\", \"doctorName\": \"DoctorName1\", \"dateTime\": \"DateTime1\"}");

            Record Record = contract.UpdateRecord(ctx, "Record1", "PatientID1", "MedicalInfo1", "DoctorName1", "DateTime1");

            assertThat(Record).isEqualTo(new Record("Record1", "PatientID1", "MedicalInfo1", "DoctorName1", "DateTime1"));
        }

//        @Test
//        public void whenRecordDoesNotExist() {
//            RecordStore contract = new RecordStore();
//            Context ctx = mock(Context.class);
//            ChaincodeStub stub = mock(ChaincodeStub.class);
//            when(ctx.getStub()).thenReturn(stub);
//            when(stub.getStringState("Record1")).thenReturn("");
//
//            Throwable thrown = catchThrowable(() -> {
//                contract.StoreRecord(ctx, "Record1", "Alex");
//            });
//
//            assertThat(thrown).isInstanceOf(ChaincodeException.class).hasNoCause()
//                    .hasMessage("Record Record1 does not exist");
//            assertThat(((ChaincodeException) thrown).getPayload()).isEqualTo("Record_NOT_FOUND".getBytes());
//        }
    }

    @Nested
    class DeleteRecordTransaction {

        @Test
        public void whenRecordDoesNotExist() {
            RecordStore contract = new RecordStore();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("Record1")).thenReturn("");

            Throwable thrown = catchThrowable(() -> {
                contract.DeleteRecord(ctx, "Record1");
            });

            assertThat(thrown).isInstanceOf(ChaincodeException.class).hasNoCause()
                    .hasMessage("Record Record1 does not exist");
            assertThat(((ChaincodeException) thrown).getPayload()).isEqualTo("RECORD_NOT_FOUND".getBytes());
        }
    }
}
