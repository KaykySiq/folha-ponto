package com.ibametro.folha_ponto_api.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Mes {
    JANEIRO("Janeiro"),
    FEVEREIRO("Fevereiro"),
    MARCO("Março"),
    ABRIL("Abril"),
    MAIO("Maio"),
    JUNHO("Junho"),
    JULHO("Julho"),
    AGOSTO("Agosto"),
    SETEMBRO("Setembro"),
    OUTUBRO("Outubro"),
    NOVEMBRO("Novembro"),
    DEZEMBRO("Dezembro");

    private final String mesRef;

    public static int getMesIndex(String mesRef) {
        for (Mes mes : values()) {
            if (mes.getMesRef().equalsIgnoreCase(mesRef)) {
                return mes.ordinal() + 1;
            }
        }
        throw new IllegalArgumentException("Mês inválido: " + mesRef);
    }
}
