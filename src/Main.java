
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Main {
    
    public static void main(String[] args){
        
        ArrayList<Integer> referencias = new ArrayList<Integer>();
        int qtdeQuadros = 0;
        Algoritmos executaAlgoritmos;
        
        /**
         * Trecho de código onde será lido o arquivo contendo o tempo de entrada e tempo de cpu dos processos
         * Os tempos serão colocados em uma lista
         */
        String linha;
        File arquivo = new File("referencias.txt");
        
        try{
            
            FileReader lerArquivo = new FileReader(arquivo);
            BufferedReader bufferArquivo = new BufferedReader(lerArquivo);
            boolean lerPrimeiraLinha = true;
            
            while(bufferArquivo.ready()){
                
                linha = bufferArquivo.readLine();
                if(lerPrimeiraLinha){
                    
                    qtdeQuadros = Integer.parseInt(linha);
                    lerPrimeiraLinha = false;
                    
                }else{
                    
                    referencias.add(Integer.parseInt(linha));
                    
                }
                
            }
            
            bufferArquivo.close();
            lerArquivo.close();
        }catch(IOException e){
            e.printStackTrace();
        }
                
        executaAlgoritmos = new Algoritmos(qtdeQuadros, referencias);
        
        executaAlgoritmos.FIFO();
        executaAlgoritmos.OTM();
        executaAlgoritmos.LRU();
        
    }
    
}
