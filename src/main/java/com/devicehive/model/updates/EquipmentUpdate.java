package com.devicehive.model.updates;

import com.devicehive.json.strategies.JsonPolicyDef;
import com.devicehive.model.Equipment;
import com.devicehive.model.HiveEntity;
import com.devicehive.model.JsonStringWrapper;
import com.devicehive.model.NullableWrapper;
import com.google.gson.annotations.SerializedName;

import static com.devicehive.json.strategies.JsonPolicyDef.Policy.EQUIPMENTCLASS_PUBLISHED;

public class EquipmentUpdate implements HiveEntity {
    @JsonPolicyDef(EQUIPMENTCLASS_PUBLISHED)
    Long id;

    @SerializedName("name")
    @JsonPolicyDef(EQUIPMENTCLASS_PUBLISHED)
    private NullableWrapper<String> name;

    @SerializedName("code")
    @JsonPolicyDef(EQUIPMENTCLASS_PUBLISHED)
    private NullableWrapper<String> code;

    @SerializedName("type")
    @JsonPolicyDef(EQUIPMENTCLASS_PUBLISHED)
    private NullableWrapper<String> type;

    @SerializedName("data")
    @JsonPolicyDef(EQUIPMENTCLASS_PUBLISHED)
    private NullableWrapper<JsonStringWrapper> data;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NullableWrapper<String> getName() {
        return name;
    }

    public void setName(NullableWrapper<String> name) {
        this.name = name;
    }

    public NullableWrapper<String> getCode() {
        return code;
    }

    public void setCode(NullableWrapper<String> code) {
        this.code = code;
    }

    public NullableWrapper<String> getType() {
        return type;
    }

    public void setType(NullableWrapper<String> type) {
        this.type = type;
    }

    public NullableWrapper<JsonStringWrapper> getData() {
        return data;
    }

    public void setData(NullableWrapper<JsonStringWrapper> data) {
        this.data = data;
    }

    public Equipment convertTo() {
        Equipment equipment = new Equipment();
        equipment.setId(id);
        if (data != null) {
            equipment.setData(data.getValue());
        }
        if (code != null) {
            equipment.setCode(code.getValue());
        }
        if (type != null) {
            equipment.setType(type.getValue());
        }
        if (name != null) {
            equipment.setName(name.getValue());
        }
        return equipment;
    }
}
