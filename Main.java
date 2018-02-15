import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        Ejemplo test neurona simple:
//        double[][] proves = {{6,4},{1},{7,9},{-1},
//                {5,3},{1},{2,4},{-1},{6,8},{-1},{9,5},{1}};
//        testNeurona1(proves,50);

//        Ejemplo test red neuronal simple:
        RedNeuronal prova = new RedNeuronal(1, 6, 1);
        double[][] test = {{6},{2.23},{2},{1.41},{4},{2},{9},{3}};

        //Falta desarrollar entrenamiento.
        prova.entrenarRed(test,100);

        prova.setEntradasX(new double[]{3});
        prova.operarNeuronas(true,"i");
        prova.getSalidasY();
    }

//    Testeo de una red neuronal, aunque en este apartado no es funcional aun.
    public static void testRedNeuronal1(){

        RedNeuronal prova = new RedNeuronal(3, 2, 1);

        //Falta desarrollar entrenamiento.
        prova.entrenarRed(new double[][]{{4,5},{-1},{6,4},{1},{3,7},{-1},{9,8},{-1}},20);

        prova.setEntradasX(new double[]{3,5,1});
        prova.operarNeuronas(true,"i");
        prova.getSalidasY();
        prova.getN_en();
        prova.getN_oc();
        prova.getN_sa();

    }

    //Testeo de una neurona simple, que identifique entre dos valores si es mayor o menor.
    public static void testNeurona1(double[][] proves,int ciclos){

        //Fase de creación de la neurona
        Neurona n1 = new Neurona();

        //Fase de entrenamiento de la neurona
        int con = 1;
        for (int i = 0; i < ciclos; i++) {//Numero de ciclos
            for (int j = 0; j < proves.length; j+=2) {
                n1.setEntradas(proves[j]);
                if (j == 0 && i == 0) {
                    n1.setPesosW();
                }
                n1.sumatoriZ();
                if (n1.getSalidaY("s") != proves[con][0]){
                    n1.aprendizaje(proves[con][0],n1.getSalidaY("s"));
                }
                con+=2;
            }
            con = 1;
        }

        //Fase de testeo de la neurona
        while (true){
            Scanner jo = new Scanner(System.in);
            System.out.println("Pon entrada 1: ");
            double num1 = jo.nextDouble();
            Scanner jo1 = new Scanner(System.in);
            System.out.println("Pon entrada 2: ");
            double num2 = jo.nextDouble();
            n1.setEntradas(new double[]{num1,num2});
            n1.sumatoriZ();
            System.out.println("Resultado: "+n1.getSalidaY("s"));
            System.out.println(n1.toString());
        }
    }
}

class RedNeuronal{
    private Neurona[] n_en;
    private Neurona[] n_oc;
    private Neurona[] n_sa;
    private double[] salidaResultado;

    RedNeuronal(int n_entrada, int n_oculta, int n_salida){
        this.n_en = new Neurona[n_entrada];
        this.n_oc = new Neurona[n_oculta];
        this.n_sa = new Neurona[n_salida];
        createNeuronas();
    }

    private void createNeuronas(){
        if (n_en != null) {
            for (int i = 0; i < n_en.length; i++) {
                n_en[i] = new Neurona();
            }
        }
        if (n_oc != null){
            for (int i = 0; i < n_oc.length; i++) {
                n_oc[i] = new Neurona();
            }
        }
        if (n_sa != null){
            for (int i = 0; i < n_sa.length; i++) {
                n_sa[i] = new Neurona();
            }
        }
    }

    public void setEntradasX(double[] entradas){
        if (entradas.length != n_en.length){
            System.out.println("Error: La longitud de entradas debe ser igual al numero de neuronas de entrada!");
        } else {
            for (int i = 0; i < entradas.length; i++) {
                this.n_en[i].setEntradas(new double[]{entradas[i]});
            }
        }
    }

    void operarNeuronas(boolean pesos,String funcionAct){
        double[] salidaEntrada = new double[this.n_en.length];
        double[] salidaOculta = new double[this.n_oc.length];
        salidaResultado = new double[this.n_sa.length];

        for (int j = 0; j < this.n_en.length; j++) {
            if (pesos) {
                this.n_en[j].setPesosW();
            }
            this.n_en[j].sumatoriZ();
            salidaEntrada[j] = this.n_en[j].getSalidaY(funcionAct);
        }
        for (int i = 0; i < this.n_oc.length; i++) {
            this.n_oc[i].setEntradas(salidaEntrada);
            if (pesos) {
                this.n_oc[i].setPesosW();
            }
            this.n_oc[i].sumatoriZ();
            salidaOculta[i] = this.n_oc[i].getSalidaY(funcionAct);
        }
        for (int h = 0; h < this.n_sa.length; h++) {
            this.n_sa[h].setEntradas(salidaOculta);
            if (pesos) {
                this.n_sa[h].setPesosW();
            }
            this.n_sa[h].sumatoriZ();
            salidaResultado[h] = this.n_sa[h].getSalidaY(funcionAct);
        }
    }

    void entrenarRed(double[][] ar, int ciclos){
//        for (int i = 0; i < 100; i++) {
//            for (int j = 0; j < this.n_en.length; j++) {
//                this.n_en[j].setEntradas(new double[]{ar[j]});
//            }
//            operarNeuronas(true,"s");
//            for (int k = 0; k < this.n_sa.length; k++) {
//
//            }
//        }
    }

    void getSalidasY(){
        System.out.println("Resultados de cada neurona de salida: \n");
        for (int p = 0; p < this.salidaResultado.length; p++) {
            System.out.println("Neurona nº"+(p+1)+" --> y = "+salidaResultado[p]);
        }
    }

    void getN_en() {
        for (int i = 0; i < this.n_en.length; i++) {
            System.out.println("\nNEURONA DE ENTRADA nº"+(i+1)+":"+n_en[i].toString());
        }
    }

    void getN_oc() {
        for (int i = 0; i < this.n_oc.length; i++) {
            System.out.println("\nNEURONA OCULTA nº"+(i+1)+":"+n_oc[i].toString());
        }
    }

    void getN_sa() {
        for (int i = 0; i < this.n_sa.length;i++) {
            System.out.println("\nNEURONA DE SALIDA nº"+(i+1)+":"+n_sa[i].toString());
        }
    }

    public String toString(){
        return "Informació de capa \nNeuronas de entrada: "+n_en.length
                +"\nNeuronas ocultas: "+n_oc.length
                +"\nNeuronas de salida: "+n_sa.length;
    }
}

class Neurona{
    private double[] entradas;
    private double[] pesos;
    private double umbral;
    private double sumatoriZ;

    Neurona(){

    }

    Neurona(double[] entradas){
        this.entradas = entradas;
        this.pesos = new double[entradas.length];
    }

    void setEntradas(double[] entradas){
        this.entradas = entradas;
    }
    
    void setPesosW(){
        this.pesos = new double[entradas.length];
        for(int i = 0;i<entradas.length;i++){
            this.pesos[i] = Math.random();
        }
        this.umbral = Math.random();
    }

    void sumatoriZ(){
        if (this.pesos == null) {
            System.out.println("Error: No hay pesos con los que operar");
        } else {
            for (int i = 0; i < this.entradas.length; i++) {
                this.sumatoriZ += this.pesos[i] * this.entradas[i];
            }
            this.sumatoriZ += this.umbral;
        }
    }

    double getSalidaY(String n){
        switch (n){
            case "identidad":
            case "i":
                return this.sumatoriZ;
            case "sigmoidea":
            case "s":
                double y = Math.tanh(this.sumatoriZ);
                if (y >= 0){
                    return 1;
                } else {
                    return -1;
                }
            default:
                System.out.println("Error, escoge funcion sigmoidea(s) o indentidad(i)");
        }
        return 0;
    }

    public void aprendizaje(double t, double y){
        double e = t - y;
        for (int i = 0; i < this.pesos.length; i++) {
            this.pesos[i] = this.pesos[i] + e * this.entradas[i];
        }
        this.umbral = this.umbral + e;
    }

    public String toString(){
        if (this.entradas == null || this.pesos == null){
            return "\nError: La neurona no tiene entradas o pesos";
        } else {
            String info = "";
            int con = 1;
            for (int i = 0; i < this.entradas.length; i++) {
                info += "\nEntrada x" + con + " = " + this.entradas[i] +
                        " | Peso y" + con + " = " + this.pesos[i];
                con++;
            }
            return "\n||Información de la neurona||" + info + "\nUmbral u = " + this.umbral + "\nSumatorio Z = " + this.sumatoriZ + "\nSalida y = " + getSalidaY("s")+"\n";
        }
    }
}
