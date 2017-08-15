package com.example.liujs.testannotation;


import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
public class MainActivity extends AppCompatActivity {

   //1、定义成员
    private static final String SON = "son";
    private static final String  MOUTHER = "mouther";
    private static final String FATHER = "father";
    //2、定义注解范围
    @Retention(RetentionPolicy.SOURCE)
    //3、定义可以接受的常量列表
    @StringDef({SON,MOUTHER,FATHER})
    @Target({ElementType.PARAMETER,ElementType.METHOD})
    //4、定义PEOPLE注解
    public @interface PEOPLE{};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView =  (TextView) findViewById(R.id.textview);
        setPeople(SON);
    }

    //自定义注解的使用
   public void setPeople(@PEOPLE String peopleMode){

   }

//初始化注解的值
   @TestAnnotation.testMethod(userName = "lixiaolong",age= 32)
 public static void test(String userName, int age){
     System.out.println("userName= "+userName +"\nage=" +age);
 }

    /**
     * 获取自定义注解的值并对注解对象({@link #test})进行初始化
     * @param ags
     */
 public static void main(String... ags){
     try {
      TestAnnotation.testMethod testMethod =   MainActivity.class.getDeclaredMethod("test",String.class,int.class)
                     .getAnnotation(TestAnnotation.testMethod.class);
         test(testMethod.userName(),testMethod.age());
     } catch (NoSuchMethodException e) {
         e.printStackTrace();
     }
 }

}
