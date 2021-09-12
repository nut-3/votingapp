package com.github.nut3.votingapp.to;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.nut3.votingapp.HasId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public abstract class BaseTo implements HasId {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected Integer id;

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + id;
    }
}
