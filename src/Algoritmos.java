
import java.util.ArrayList;


public class Algoritmos {
    
    private int qtde;
    private ArrayList<Integer> ref;
    
    public Algoritmos(int qtde, ArrayList<Integer> ref){
        
        this.qtde = qtde;
        this.ref = ref;
        
    }
    
    
    /**
     * Seta todos as posições do array de quadros para -1.
     * Usado toda vez que inicia um algoritmo e para saber se a posição está vazia.
     */
    private void resetaQuadros(int[] quadros){
        
        for(int i = 0; i < qtde; i++)
            quadros[i] = -1;
        
    }
    
    /**
     * Checa se a referência já está no quadro de páginas. 
     */
    private boolean checaReferencia(int referencia, int[] quadros){
        for(int i =0; i < qtde; i++)
            if(referencia == quadros[i])
                return true;
        
        return false;
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
    
    private void ordenaQuadros(int[] quadros, int pos){
        
        for(int i=pos; i < qtde-1; i++){
            
            quadros[i] = quadros[i+1];
            
        }
        
    }
    
    /**
     * Ordena a pilha de quadros usada no algoritmo LRU.
     * Esse método verifica se tem posição vazia na pilha
     * Caso haja, insere na posição vazia, se não irá verificar
     * se a referência já está na pilha, se estiver, ela será movida
     * para o topo da pilha(última posição do array), se não a base
     * da pilha(primeira posição do array) será removida e a referência
     * inserida no topo da pilha.
     */
    private int[] ordenaPilhaQuadros(int[] pilhaQuadros, int ref){
        int pos;
        
        //Verifica se há posição vazia para inserir a referência na pilha.
        if((pos = posicaoVazia(pilhaQuadros)) != -1){
            
            pilhaQuadros[pos] = ref;
            return pilhaQuadros;
            
        }
        
        pos = 0;
        
        //Verifica se a referência já está na pilha.
        //Se estiver, a posição dela na pilha será reordenada.
        for(int i=0; i < qtde-1; i++){
            
            if(ref == pilhaQuadros[i])
                pos = i;
                                   
        }
        
        ordenaQuadros(pilhaQuadros, pos);
        pilhaQuadros[qtde-1] = ref;
        
        return pilhaQuadros;
    }

    /**
     * Vê qual das referências que estão no quadro de páginas
     * é a que demorará mais tempo para executar e retorna a posição dela.
     */
    private int posicaoMaisLonga(int[] quadros, int posFinal){
        int pos = 0, maisLongo = 0;
        boolean naoEncontrou = true;
        
        //Percorre todo quadro de páginas para procurar
        //o que irá demorar mais a executar novamente.
        for(int i = 0; i < qtde; i++){
            
            //Percorre toda a lista de referências a serem executadas ainda.
            for(int j = posFinal; j < ref.size() ; j++){
                
                naoEncontrou = true;
                
                //Se encontrar a referência na lista a serem executados,
                //verifica se ele é o que irá demorar mais entre as referências
                //que estão no quadro de referências.
                if(quadros[i] == ref.get(j)){
                    
                    naoEncontrou = false;
                    
                    if(maisLongo < j){
                        
                        maisLongo = j;
                        pos = i;
                        
                    }
                    
                    break;
                    
                }
                
            }
            
            //Se essa referência não vai ser executadas mais,
            //sua posição será retornada.
            if(naoEncontrou)
                return i;
            
        }
        
        return pos;
        
    }
    
    /**
     * Pegará a posição no quadro de páginas da referência que está na base da pilha. 
     */
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
            
            //Checa se a referência já está no quadro de páginas.
            //Caso não esteja, pegará o primeiro que chegou
            //e a posição do que chegou primeiro muda de maneira circular.
            if(!checaReferencia(referencias.get(0), quadros)){
                
                quadros[primeiro] = referencias.get(0);
                faltaPag++;
                primeiro = (primeiro + 1) % qtde;
                
            }
            
            referencias.remove(0);
            
        }
        
        System.out.println("FIFO " + faltaPag);
        
    }
    
    public void OTM(){
        
        int quadros[] = new int[qtde];
        int pos = 0, faltaPag = 0;
        ArrayList<Integer> referencias = (ArrayList<Integer>) ref.clone();
        
        resetaQuadros(quadros);
        
        
        for(int i = 0; i < ref.size(); i++){
            
            //Checa se a referência já está no quadro de páginas.
            //Caso não esteja, verificará se há posição vazia,
            //se não tiver posição vazia, irá procurar a referência
            //que irá demorar mais a ser executada novamente.
            if(!checaReferencia(referencias.get(0), quadros)){
                
                if((pos = posicaoVazia(quadros)) == -1)
                    pos = posicaoMaisLonga(quadros, i);
                
                quadros[pos] = referencias.get(0);
                
                faltaPag++;
                
            }
            
            referencias.remove(0);
            
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
            
            //Checa se a referência já está no quadro de páginas.
            //Caso não esteja, verificará se há posição vazia,
            //se não tiver posição vazia, irá procurar pela posição
            //da referência que é base da pilha e adicionará a nova referência
            //no quadro de páginas na posição onde estava a referência
            //da base da pilha.
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
