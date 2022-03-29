package com.lucas.deliveryapi.api.controller;

public class Test {
    public static void main(String[] args) {


        int [] arrayTest = {10, -20, 61, -15 };
        int [] zeroArrayTest = {0, 0, 0, 0};
        int [] negativeArrayTest = {-100, -20, -60, -45};
        int [] loadedArrayTest = {20, 30, 15, 70};
        int [] emptyArrayTest = {};
        int [] nullArrayTest = null;

//
//        System.out.println("Bateria final array padrão: " + getBattery(arrayTest));
//        System.out.println("Bateria final array zerado: " + getBattery(zeroArrayTest));
//        System.out.println("Bateria final array negativo: " + getBattery(negativeArrayTest));
//        System.out.println("Bateria final array carregado: " + getBattery(loadedArrayTest));
//        System.out.println("Bateria final array vazio: " + getBattery(emptyArrayTest));
//        System.out.println("Bateria final array null: " + getBattery(nullArrayTest));


//        for (int i = 0; i < 3; i++) {
//            for (int j = 1; j < 3; j++) {
//                if( i%j==0){
//                    continue;
//                }else{
//                    System.out.println("i = "+i+", j = "+j);
//                }
//            }
//        }

        int number =2;
        System.out.println( number%2==0);
    }

    public static int getBattery(int eventos[]){
        int initialBattery = 50;
        if(eventos == null){
            return initialBattery;
        }

        for (int i = 0; i < eventos.length; i++) {
            if(initialBattery + eventos[i] > 100){
                initialBattery = 100;
            }
            else if(initialBattery + eventos[i] < 0){
                initialBattery = 0;
            }
            else{
                initialBattery+=eventos[i];
            }
        }

        return initialBattery;
//
//        a=true b=true c=false;
//        (true) | (false) = true;
//        (true) & (false) = false;
//        (true) & (false) & (false) -> (false) & (false) = false;
    }
}
