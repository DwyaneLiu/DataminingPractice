package cn.jxufe;

public class KettleProblem {

    //为栈设置一个最大值
    public static int max = 100;
    //用一个栈储存状态
    public static Kettle[] stacks = new Kettle[max];
    //栈的顶点指针
    public static int i = 1;
    //a代表4加仑的水壶，b代表3加仑的水壶
    public static class  Kettle{
        private  int a;
        private  int b;
        Kettle(){
            a = 0;
            b = 0;
        }
        Kettle(Kettle kettle){
            this.a = kettle.a;
            this.b = kettle.b;
        }
    }
    //new一个水壶对象
    public static Kettle kettle = new Kettle();
    //判断是否存在环
    public static boolean isNotInStack(Kettle k ,int i) {
        int p = 0;
        while (p < i) {
            if (stacks[p].a == k.a && stacks[p].b == k.b  ) {
                return false;
            }
            p++;
        }
        return true;
    }
    //入栈或出栈操作
    public static void addStacks(){
        if (isNotInStack(kettle,i)) {
            stacks[i] = new Kettle(kettle);
            i++;
        }else {
            kettle = new Kettle(stacks[i-1]);
        }
    }
    //主函数
    public static void main(String agrs[]){

        stacks[0] = new Kettle(kettle);
        while (kettle.a != 2 ) {
            //1判断加水条件
            if (kettle.a < 4 && kettle.a != 2){
                r1(kettle);addStacks();
            }
            //2同上
            if (kettle.b < 3 && kettle.a != 2){
                r2(kettle);addStacks();
            }
            //3
            if (kettle.a > 0 && kettle.a != 2){
                r3(kettle);addStacks();
            }
            //4
            if (kettle.b>0 && kettle.a != 2){
                r4(kettle);addStacks();
            }
            //5
            if ((kettle.a+kettle.b) >= 4 && kettle.b > 0 && kettle.a != 2){
                r5(kettle);addStacks();
            }
            //6
            if ((kettle.a+kettle.b) >= 3 && kettle.a > 0 && kettle.a != 2){
                r6(kettle);addStacks();
            }
            //7
            if ((kettle.a+kettle.b) <= 4 && kettle.b > 0 && kettle.a != 2){
                r7(kettle);addStacks();
            }
            //8
            if ((kettle.a+kettle.b) <= 3 && kettle.a > 0 && kettle.a != 2){
                r8(kettle);addStacks();
            }
        }
        System.out.println("-------倒水顺序---------");
        int q = 0;
        while (q<i) {
            System.out.println("("+stacks[q].a+","+stacks[q].b+")");
            q++;
        }
        System.out.println("-------完成---------");
    }
    //所有操作函数
    public static void r1(Kettle kettle){
        kettle.a = 4;
    }
    public static void r2(Kettle kettle){
        kettle.b = 3;
    }
    public static void r3(Kettle kettle){
        kettle.a = 0;
    }
    public static void r4(Kettle kettle){
        kettle.b = 0;
    }
    public static void r5(Kettle kettle){
        kettle.b = kettle.b - (4 - kettle.a);
        kettle.a = 4;
    }
    public static void r6(Kettle kettle){
        kettle.a = kettle.a - (3 - kettle.b);
        kettle.b = 3;
    }
    public static void r7(Kettle kettle){
        kettle.a = kettle.a + kettle.b;
        kettle.b = 0;
    }
    public static void r8(Kettle kettle){
        kettle.b = kettle.a + kettle.b;
        kettle.a = 0;
    }
}
