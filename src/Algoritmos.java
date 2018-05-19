
import java.util.ArrayList;


public class Algoritmos {
    
    private int qtde;
    private ArrayList<Integer> ref;
    
    public Algoritmos(int qtde, ArrayList<Integer> ref){
        
        this.qtde = qtde;
        this.ref = ref;
        
    }
    
    private void resetaQuadros(int[] quadros){
        
        for(int i = 0; i < qtde; i++)
            quadros[i] = -1;
        
    }
    
    private boolean checaReferencia(int referencia, int[] quadros){
        for(int i =0; i < qtde; i++)
            if(referencia == quadros[i])
                return true;
        
        return false;
    }
    
    private void ordenaQuadros(int[] quadros, int pos){
        
        for(int i=pos; i < qtde-1; i++){
            
            quadros[i] = quadros[i+1];
            
        }
        
    }
    
    private int[] ordenaPilhaQuadros(int[] pilhaQuadros, int ref){
        int pos;
        
        if((pos = posicaoVazia(pilhaQuadros)) != -1){
            
            pilhaQuadros[pos] = ref;
            return pilhaQuadros;
            
        }
        
        pos = 0;
        
        for(int i=0; i < qtde-1; i++){
            
            if(ref == pilhaQuadros[i])
                pos = i;
                                   
        }
        
        ordenaQuadros(pilhaQuadros, pos);
        pilhaQuadros[qtde-1] = ref;
        
        return pilhaQuadros;
    }
    
    /**
     * Retorna a posição que ainda está vazia, caso não esteja vazia
     * será retornado -1.
     */
    private int posicaoVazia(int[] quadros){
        int pos = -1;
        
        for(int i = 0; i < qtde; i++)
            if(quadros[i] == -1)
                return i;
        
        return pos;
    }
    
    private int posicaoMaisLonga(int[] quadros, int posFinal){
        int pos = 0, maisLongo = 0;
        boolean naoEncontrou = true;
                
        for(int i = 0; i < qtde; i++){
            
            for(int j = posFinal; j < ref.size() ; j++){
                
                naoEncontrou = true;
                
                if(quadros[i] == ref.get(j)){
                    
                    naoEncontrou = false;
                    
                    if(maisLongo < j){
                        
                        maisLongo = j;
                        pos = i;
                        
                    }
                    
                    break;
                    
                }
                
            }
            
            if(naoEncontrou)
                return i;
            
        }
        
        return pos;
        
    }
    
    private int posicaoBasePilha(int basePilha, int quadros[]){
        int pos = 0;
        
        for(int i = 0; i < qtde; i++){
            
            if(quadros[i] == basePilha)
                pos = i;
            
        }
        
        return pos;
    }
    
    public void FIFO(){
        
        int quadros[] = new int[qtde];
        int primeiro = 0, faltaPag = 0;
        ArrayList<Integer> referencias = (ArrayList<Integer>) ref.clone();
        
        resetaQuadros(quadros);
        
        for(int i = 0; i < ref.size(); i++){
            
            if(!checaReferencia(referencias.get(0), quadros)){
                
                quadros[primeiro] = referencias.get(0);
                faltaPag++;
                primeiro = (primeiro + 1) % qtde;
                
            }
            
            referencias.remove(0);
            
            /*for(int j = 0; j < qtde; j++)
                System.out.println((j+1) + " = " + quadros[j]);
            System.out.println("Primeiro atual "+primeiro);
            26
            18
            25
            System.out.println("########");*/
            
        }
        
        System.out.println("FIFO " + faltaPag);
        
    }
    
    public void OTM(){
        
        int quadros[] = new int[qtde];
        int pos = 0, faltaPag = 0;
        ArrayList<Integer> referencias = (ArrayList<Integer>) ref.clone();
        
        resetaQuadros(quadros);
        
        
        for(int i = 0; i < ref.size(); i++){
            
            if(!checaReferencia(referencias.get(0), quadros)){
                
                if((pos = posicaoVazia(quadros)) == -1)
                    pos = posicaoMaisLonga(quadros, i);
                
                quadros[pos] = referencias.get(0);
                
                faltaPag++;
                
            }
            
            referencias.remove(0);
            
            
            /*System.out.println("#"+(i+1));
            for(int j = 0; j < qtde; j++){
                
                System.out.println((j+1) + " = " + quadros[j]);
            
            }
            //System.out.println("Primeiro atual "+primeiro);
            System.out.println("########");*/
            
        }
        
        
        
        System.out.println("OTM " + faltaPag);
        
    }
    
    public void LRU(){
        
        int quadros[] = new int[qtde];
        int faltaPag = 0, pos = 0;
        int pilhaQuadros[] = new int[qtde];
        ArrayList<Integer> referencias = (ArrayList<Integer>) ref.clone();
        
        resetaQuadros(quadros);
        resetaQuadros(pilhaQuadros);
        
        for(int i = 0; i < ref.size(); i++){
            
            if(!checaReferencia(referencias.get(0), quadros)){
                
                if((pos = posicaoVazia(quadros)) == -1)
                    pos = posicaoBasePilha(pilhaQuadros[0], quadros);
                    
                quadros[pos] = referencias.get(0);
                faltaPag++;    
                
            }
            
            pilhaQuadros = ordenaPilhaQuadros(pilhaQuadros, referencias.get(0));
            referencias.remove(0);
            
        }
        
        System.out.println("LRU " + faltaPag);
        
    }
    
}
