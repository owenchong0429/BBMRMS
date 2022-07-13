
package JavaChainCode;

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

public final class RecordTransferTest {

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

        private final List<KeyValue> RecordList;

        MockRecordResultsIterator() {
            super();

            RecordList = new ArrayList<KeyValue>();

            RecordList.add(new MockKeyValue("Record1",
                    "{ \"RecordID\": \"MockRecord1\", "
                            + "\"Title\": \"MockTitle1\", "
                            + "\"PatientID\": \"MockPatientID1\", "
                            + "\"PatientName\": \"MockPatientName1\", "
                            + "\"Diagnosis\": \"MockDiagnosis1\", "
                            + "\"Prescriptions\": \"MockPrescriptions1\", "
                            + "\"DoctorName\": \"MockDoctorName1\", "
                            + "\"DateTime\": MockDateTime1, "
                            + "\"FacilityName\": \"MockFacilityName1\",}"));
            
            RecordList.add(new MockKeyValue("Record2",
                    "{ \"RecordID\": \"MockRecord2\", "
                            + "\"Title\": \"MockTitle2\", "
                            + "\"PatientID\": \"MockPatientID2\", "
                            + "\"PatientName\": \"MockPatientName2\", "
                            + "\"Diagnosis\": \"MockDiagnosis2\", "
                            + "\"Prescriptions\": \"MockPrescriptions2\", "
                            + "\"DoctorName\": \"MockDoctorName2\", "
                            + "\"DateTime\": MockDateTime2, "
                            + "\"FacilityName\": \"MockFacilityName2\",}"));
            
            RecordList.add(new MockKeyValue("Record3",
                    "{ \"RecordID\": \"MockRecord3\", "
                            + "\"Title\": \"MockTitle3\", "
                            + "\"PatientID\": \"MockPatientID3\", "
                            + "\"PatientName\": \"MockPatientName3\", "
                            + "\"Diagnosis\": \"MockDiagnosis3\", "
                            + "\"Prescriptions\": \"MockPrescriptions3\", "
                            + "\"DoctorName\": \"MockDoctorName3\", "
                            + "\"DateTime\": MockDateTime3, "
                            + "\"FacilityName\": \"MockFacilityName3\",}"));
            
        }

        @Override
        public Iterator<KeyValue> iterator() {
            return RecordList.iterator();
        }

        @Override
        public void close() throws Exception {
            // do nothing
        }

    }

    @Test
    public void invokeUnknownTransaction() {
        RecordTransfer contract = new RecordTransfer();
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
            RecordTransfer contract = new RecordTransfer();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("Record1"))
                    .thenReturn("Record1",
                    "{ \"RecordID\": \"MockRecord1\", "
                            + "\"Title\": \"MockTitle1\", "
                            + "\"PatientID\": \"MockPatientID1\", "
                            + "\"PatientName\": \"MockPatientName1\", "
                            + "\"Diagnosis\": \"MockDiagnosis1\", "
                            + "\"Prescriptions\": \"MockPrescriptions1\", "
                            + "\"DoctorName\": \"MockDoctorName1\", "
                            + "\"DateTime\": MockDateTime1, "
                            + "\"FacilityName\": \"MockFacilityName1\",}");

            Record Record = contract.ReadRecord(ctx, "Record1");

            assertThat(Record).isEqualTo(new Record("MockRecord1", "MockTitle1", "MockPatientID1", "MockPatientName1", "MockDiagnosis1", 
                    "MockPrescriptions1", "MockDoctorName1", "MockDateTime1", "MockFacilityName1"));
        }

        @Test
        public void whenRecordDoesNotExist() {
            RecordTransfer contract = new RecordTransfer();
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
        RecordTransfer contract = new RecordTransfer();
        Context ctx = mock(Context.class);
        ChaincodeStub stub = mock(ChaincodeStub.class);
        when(ctx.getStub()).thenReturn(stub);

        contract.InitLedger(ctx);

        InOrder inOrder = inOrder(stub);
        inOrder.verify(stub).putStringState("Record1",
                    "{ \"RecordID\": \"MockRecord1\", "
                            + "\"Title\": \"MockTitle1\", "
                            + "\"PatientID\": \"MockPatientID1\", "
                            + "\"PatientName\": \"MockPatientName1\", "
                            + "\"Diagnosis\": \"MockDiagnosis1\", "
                            + "\"Prescriptions\": \"MockPrescriptions1\", "
                            + "\"DoctorName\": \"MockDoctorName1\", "
                            + "\"DateTime\": MockDateTime1, "
                            + "\"FacilityName\": \"MockFacilityName1\",}");
        
        inOrder.verify(stub).putStringState("Record1",
                    "{ \"RecordID\": \"MockRecord1\", "
                            + "\"Title\": \"MockTitle1\", "
                            + "\"PatientID\": \"MockPatientID1\", "
                            + "\"PatientName\": \"MockPatientName1\", "
                            + "\"Diagnosis\": \"MockDiagnosis1\", "
                            + "\"Prescriptions\": \"MockPrescriptions1\", "
                            + "\"DoctorName\": \"MockDoctorName1\", "
                            + "\"DateTime\": MockDateTime1, "
                            + "\"FacilityName\": \"MockFacilityName1\",}");
        
        inOrder.verify(stub).putStringState("Record3",
                    "{ \"RecordID\": \"MockRecord3\", "
                            + "\"Title\": \"MockTitle3\", "
                            + "\"PatientID\": \"MockPatientID3\", "
                            + "\"PatientName\": \"MockPatientName3\", "
                            + "\"Diagnosis\": \"MockDiagnosis3\", "
                            + "\"Prescriptions\": \"MockPrescriptions3\", "
                            + "\"DoctorName\": \"MockDoctorName3\", "
                            + "\"DateTime\": MockDateTime3, "
                            + "\"FacilityName\": \"MockFacilityName3\",}");

    }

    @Nested
    class InvokeCreateRecordTransaction {

        @Test
        public void whenRecordExists() {
            RecordTransfer contract = new RecordTransfer();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("Record1"))
                    .thenReturn("Record1",
                    "{ \"RecordID\": \"MockRecord1\", "
                            + "\"Title\": \"MockTitle1\", "
                            + "\"PatientID\": \"MockPatientID1\", "
                            + "\"PatientName\": \"MockPatientName1\", "
                            + "\"Diagnosis\": \"MockDiagnosis1\", "
                            + "\"Prescriptions\": \"MockPrescriptions1\", "
                            + "\"DoctorName\": \"MockDoctorName1\", "
                            + "\"DateTime\": MockDateTime1, "
                            + "\"FacilityName\": \"MockFacilityName1\",}");

            Throwable thrown = catchThrowable(() -> {
                contract.CreateRecord(ctx, "MockRecord1", "DemoTitle1", "DemoPatientID1", "DemoPatientName1", "DemoDiagnosis1", 
                    "DemoPrescriptions1", "DemoDoctorName1", "DemoMockDateTime1", "DemoFacilityName1");
            });

            assertThat(thrown).isInstanceOf(ChaincodeException.class).hasNoCause()
                    .hasMessage("Record Record1 already exists");
            assertThat(((ChaincodeException) thrown).getPayload()).isEqualTo("RECORD_ALREADY_EXISTS".getBytes());
        }

        @Test
        public void whenRecordDoesNotExist() {
            RecordTransfer contract = new RecordTransfer();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("Record1")).thenReturn("");

            Record Record = contract.CreateRecord(ctx, "MockRecord1", "DemoTitle1", "DemoPatientID1", "DemoPatientName1", "DemoDiagnosis1", 
                    "DemoPrescriptions1", "DemoDoctorName1", "DemoDateTime1", "DemoFacilityName1");

            assertThat(Record).isEqualTo(new Record("MockRecord1", "MockTitle1", "MockPatientID1", "MockPatientName1", "MockDiagnosis1", 
                    "MockPrescriptions1", "MockDoctorName1", "MockDateTime1", "MockFacilityName1"));
        }
    }

    @Test
    void invokeGetAllRecordsTransaction() {
        RecordTransfer contract = new RecordTransfer();
        Context ctx = mock(Context.class);
        ChaincodeStub stub = mock(ChaincodeStub.class);
        when(ctx.getStub()).thenReturn(stub);
        when(stub.getStateByRange("", "")).thenReturn(new MockRecordResultsIterator());

        String Records = contract.GetAllRecords(ctx);

        assertThat(Records).isEqualTo("{\"RecordID\": \"MockRecord1\", "
                            + "\"Title\": \"MockTitle1\", "
                            + "\"PatientID\": \"MockPatientID1\", "
                            + "\"PatientName\": \"MockPatientName1\", "
                            + "\"Diagnosis\": \"MockDiagnosis1\", "
                            + "\"Prescriptions\": \"MockPrescriptions1\", "
                            + "\"DoctorName\": \"MockDoctorName1\", "
                            + "\"DateTime\": MockDateTime1, "
                            + "\"FacilityName\": \"MockFacilityName1\"},"
                + "{\"RecordID\": \"MockRecord2\", "
                            + "\"Title\": \"MockTitle2\", "
                            + "\"PatientID\": \"MockPatientID2\", "
                            + "\"PatientName\": \"MockPatientName2\", "
                            + "\"Diagnosis\": \"MockDiagnosis2\", "
                            + "\"Prescriptions\": \"MockPrescriptions2\", "
                            + "\"DoctorName\": \"MockDoctorName2\", "
                            + "\"DateTime\": MockDateTime2, "
                            + "\"FacilityName\": \"MockFacilityName2\"},"
                + "{\"RecordID\": \"MockRecord3\", "
                            + "\"Title\": \"MockTitle3\", "
                            + "\"PatientID\": \"MockPatientID3\", "
                            + "\"PatientName\": \"MockPatientName3\", "
                            + "\"Diagnosis\": \"MockDiagnosis3\", "
                            + "\"Prescriptions\": \"MockPrescriptions3\", "
                            + "\"DoctorName\": \"MockDoctorName3\", "
                            + "\"DateTime\": MockDateTime3, "
                            + "\"FacilityName\": \"MockFacilityName3\"},");

    }

    //@Nested
//    class TransferRecordTransaction {
//
//        @Test
//        public void whenRecordExists() {
//            RecordTransfer contract = new RecordTransfer();
//            Context ctx = mock(Context.class);
//            ChaincodeStub stub = mock(ChaincodeStub.class);
//            when(ctx.getStub()).thenReturn(stub);
//            when(stub.getStringState("Record1"))
//                    .thenReturn("Record1",
//                    "{ \"RecordID\": \"MockRecord1\", "
//                            + "\"Title\": \"MockTitle1\", "
//                            + "\"PatientID\": \"MockPatientID1\", "
//                            + "\"PatientName\": \"MockPatientName1\", "
//                            + "\"Diagnosis\": \"MockDiagnosis1\", "
//                            + "\"Prescriptions\": \"MockPrescriptions1\", "
//                            + "\"DoctorName\": \"MockDoctorName1\", "
//                            + "\"DateTime\": MockDateTime1, "
//                            + "\"FacilityName\": \"MockFacilityName1\",}");
//
//            String oldOwner = contract.TransferRecord(ctx, "Record1", "Dr Evil");
//
//            assertThat(oldOwner).isEqualTo("Tomoko");
//        }
//
//        @Test
//        public void whenRecordDoesNotExist() {
//            RecordTransfer contract = new RecordTransfer();
//            Context ctx = mock(Context.class);
//            ChaincodeStub stub = mock(ChaincodeStub.class);
//            when(ctx.getStub()).thenReturn(stub);
//            when(stub.getStringState("Record1")).thenReturn("");
//
//            Throwable thrown = catchThrowable(() -> {
//                contract.TransferRecord(ctx, "Record1", "Dr Evil");
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
            RecordTransfer contract = new RecordTransfer();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("Record1"))
                    .thenReturn("Record1",
                    "{ \"RecordID\": \"MockRecord1\", "
                            + "\"Title\": \"MockTitle1\", "
                            + "\"PatientID\": \"MockPatientID1\", "
                            + "\"PatientName\": \"MockPatientName1\", "
                            + "\"Diagnosis\": \"MockDiagnosis1\", "
                            + "\"Prescriptions\": \"MockPrescriptions1\", "
                            + "\"DoctorName\": \"MockDoctorName1\", "
                            + "\"DateTime\": MockDateTime1, "
                            + "\"FacilityName\": \"MockFacilityName1\",}");

            Record Record = contract.UpdateRecord(ctx, "MockRecord1", "UpdatedTitle1", "UpdatedPatientID1", "UpdatedPatientName1", "UpdatedDiagnosis1", 
                    "UpdatedPrescriptions1", "UpdatedDoctorName1", "UpdatedDateTime1", "UpdatedFacilityName1");

            assertThat(Record).isEqualTo(new Record("MockRecord1", "UpdatedTitle1", "UpdatedPatientID1", "UpdatedPatientName1", "UpdatedDiagnosis1", 
                    "UpdatedPrescriptions1", "UpdatedDoctorName1", "UpdatedDateTime1", "UpdatedFacilityName1"));
        }

       // @Test
//        public void whenRecordDoesNotExist() {
//            RecordTransfer contract = new RecordTransfer();
//            Context ctx = mock(Context.class);
//            ChaincodeStub stub = mock(ChaincodeStub.class);
//            when(ctx.getStub()).thenReturn(stub);
//            when(stub.getStringState("Record1")).thenReturn("");
//
//            Throwable thrown = catchThrowable(() -> {
//                contract.TransferRecord(ctx, "Record1", "Alex");
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
            RecordTransfer contract = new RecordTransfer();
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
