package com.portfolio.banktransactions.entity.constants;


public enum TypeTransactionEnum {

     TRANSFERENCIA(-1), PAGAMENTO(-1), DEPOSITO(1), SAQUE(-1);

    public int type;

    TypeTransactionEnum(int value) {
        type = value;
    }

    public int getType(){
        return this.type;
    }

}
