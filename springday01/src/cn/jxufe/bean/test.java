package cn.jxufe.bean;

public class test {

    public static void main(String arg[]){

        int i ;
        int j ;
        int k ;
        int a[][] = new int[6][6];
        for(i = 0;i<6;i++){
            k = 1;
            for(j=0;j<6;j++){
                if(i>j){
                    a[i][j] = 0;
                    System.out.print(a[i][j]);
                }
                else {
                    a[i][j] = k ;
                    System.out.print(a[i][j]);
                    k++;
                }

            }
            System.out.println();
        }


    }
}
