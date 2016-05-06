package hesselintensifies;

import java.util.HashMap;
import java.util.Map;
import Instructions.*;
import Enumerations.enumRegistradores;
import Enumerations.enumOpcodes;
import Enumerations.enumInstrucao;
import Enumerations.enumFuncts;
import java.util.Scanner;

public class HesselIntensifies {

    private static Map<String, Integer> distanceLabels = new HashMap<>();
    private static Map<String, Integer> distanceInstructions = new HashMap<>();
    private static int iteratorDistance = 0;
    
     public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        //Perguntar se quer passar de codigo para hexa ou de hexa para codigo
        System.out.println("1. Código para Hexa");
        System.out.println("2. Hexa para código");
        // Salva todos os comandos em uma string grandona legal

        String line = "bne $t0,$t1,teste";
        
        // Chamar método apropriado
        instructionsToHexa(line);
    }

    public static void instructionsToHexa(String line) {

        // Separa as informações da linha por espaço
        String[] parts = line.split(" ");

        // Pega o opcode e tipo da função
        String operacao = parts[0];
        if (enumInstrucao.valueOf(operacao).getValue().equals("R")) {
            opTipoR(line);
        } else if (enumInstrucao.valueOf(operacao).getValue().equals("I")) {
            //opTipoI(line);
        } else {
            //opTipoJ(line);
        }

    }

    public static void opTipoR(String line) {
        // Separa as informações da linha por espaço
        String[] parts = line.split(" ");

        String operacao = parts[0];

        String opcode = enumOpcodes.valueOf(operacao).getValue();

        String funct = enumFuncts.valueOf(operacao).getValue();

        tipoR objInstrucao = new tipoR(operacao, opcode, funct);

        //Separa os registradores/valores
        String[] regs = parts[1].split(",");

        objInstrucao.setRs((enumRegistradores.valueOf(regs[0]).ordinal())+"");
        objInstrucao.setRt((enumRegistradores.valueOf(regs[1]).ordinal())+"");

        if (regs[2].contains("$")) {
            objInstrucao.setRd(enumRegistradores.valueOf(regs[2]).ordinal()+"");
        } else {
            objInstrucao.setRd("0");
            objInstrucao.setShamt(regs[2]);
        }

        String resultado = objInstrucao.toHexa();
        System.out.println(resultado);

    }

    public static void opTipoI(String line, String stringao){
         // Separa as informações da linha por espaço
        String [] parts = line.split(" ");
        
        String operacao = parts[0];
        
        String opcode = enumOpcodes.valueOf(operacao).getValue();
        
        tipoI objInstrucao = new tipoI(operacao, opcode);
        
        //Separa os registradores/valores
        String [] regs = parts[1].split(",");
        
        objInstrucao.setRs(enumRegistradores.valueOf(regs[0]).ordinal()+"");
        objInstrucao.setRt(enumRegistradores.valueOf(regs[1]).ordinal()+"");
        
        if(regs[2].matches("[0-9]+")){
            objInstrucao.setImediate(regs[2]);
        }
        else{
            String distancia = ""+(distanceLabels.get(regs[2]) - distanceInstructions.get(operacao));
            objInstrucao.setImediate(distancia);
        }
        String resultado = objInstrucao.toHexa();
        System.out.println(resultado);
        
    }

    public static void saveDistance(String line){
        switch(line){
            case "beq":
                distanceInstructions.put("beq",iteratorDistance);
                iteratorDistance++;
            case "bne":
                distanceInstructions.put("bne",iteratorDistance);
                iteratorDistance++;
            case "j":
                distanceInstructions.put("j",iteratorDistance);
                iteratorDistance++;
            case "jal":
                distanceInstructions.put("jal",iteratorDistance);
                iteratorDistance++;
            case ":":
                distanceLabels.put("jal",iteratorDistance);
                iteratorDistance++;
            default:
                iteratorDistance++;
        }
    }
}