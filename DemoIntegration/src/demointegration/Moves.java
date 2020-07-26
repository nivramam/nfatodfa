package demointegration;

public class Moves {
    int Starting_state;
    int Ending_state;
    char value;

    public Moves(int start, char val,int end) {
        this.Starting_state = start;
        this.value = val;
        this.Ending_state = end;
    }
    
    public Moves(int start, int end) {
        this.Starting_state = start;
        this.value = 'e';
        this.Ending_state = end;
    }

    public int Startstate() {
        return Starting_state;
    }
      
    public int Endstate() {
        return Ending_state;
    }

    public char Value() {
        return value;
    }
    
    public void Display(){
        System.out.println("---------------------");
        System.out.println("|" + Starting_state + "|" + "\t" + value + "\t" + "|" +  Ending_state + "|");
    }
    
}
