/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JavaChainCode;
import java.util.Objects;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import com.owlike.genson.annotation.JsonProperty;
/**
 *
 * @author owenc
 */
public final class RecordQueryResult {
    @Property()
    private final String key;

    @Property()
    private final Record record;

    public RecordQueryResult(@JsonProperty("Key") final String key, @JsonProperty("Record") final Record record) {
        this.key = key;
        this.record = record;
    }

    public String getKey() {
        return key;
    }

    public Record getRecord() {
        return record;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        RecordQueryResult other = (RecordQueryResult) obj;

        Boolean recordsAreEquals = this.getRecord().equals(other.getRecord());
        Boolean keysAreEquals = this.getKey().equals(other.getKey());

        return recordsAreEquals && keysAreEquals;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getKey(), this.getRecord());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + " [key=" + key + ", record="
                + record + "]";
    }
}
