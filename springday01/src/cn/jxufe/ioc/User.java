package cn.jxufe.ioc;

public class User {

     public void add(){

         System.out.print("add...");

     }

     //原始方式
     public static void main(String args[]){

        User user = new User();

        user.add();

     }

}
