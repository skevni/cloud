package ru.gb.sklyarov.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Command implements Serializable {
    private CommandType commandName;
    private Object[] args;

    public boolean checkArgs(int requiredNumberArguments) {
        return args.length == requiredNumberArguments;
    }
}
