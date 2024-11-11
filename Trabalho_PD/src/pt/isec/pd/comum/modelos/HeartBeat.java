package pt.isec.pd.comum.modelos;

import java.io.Serializable;

public class HeartBeat  implements Serializable {

    private int bdVersao;
    private int porto;

    public HeartBeat(int bdVersao, int porto) {
        this.bdVersao = bdVersao;
        this.porto = porto;
    }

    public int getBdVersao() {
        return bdVersao;
    }

    public int getPorto() {
        return porto;
    }
}
