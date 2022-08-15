package org.example.PEKS;

import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class PEKE_test {
    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        //生成双线性映射
        Pairing pairing = PairingFactory.getPairing("/home/unibeam/program/java/PEKS/PEKS/a.properties");

        PEKSmain PEKSmain = new PEKSmain();
        //PEKS 初始化
        PEKSmain.Setup(pairing);

        String w = "abc";
        String w1 = "abcd";

        //加密
        PEKSmain.C c = null;
        c = PEKSmain.Enc(PEKSmain.pk, w1);

        //生成陷门
        PEKSmain.TD td = null;
        PEKSmain.TD td1 = null;
        td = PEKSmain.TdGen(PEKSmain.pk, PEKSmain.sk, w);
        td1 = PEKSmain.TdGen(PEKSmain.pk, PEKSmain.sk, w1);

        //搜索
        boolean res = PEKSmain.Test(PEKSmain.pk, td, c);
        boolean res1 = PEKSmain.Test(PEKSmain.pk, td1, c);

        //搜索结果测试
        System.out.println(res);
        System.out.println(res1);

    }
}
