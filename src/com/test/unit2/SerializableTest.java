package com.test.unit2;

import java.io.Externalizable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class SerializableTest {

    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        Manage hiker = new Manage("hiker", 233.3);
        Manage joe = new Manage("joe", 233.3);
        Employee roy = new Employee("roy", 23.3); // roy是员工
        hiker.setAide(roy);
        joe.setAide(roy);
        
        Employee []ees = {hiker, joe, roy};
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ZipTest.WORD_DIR + File.separator +"nums.txt"))){
            oos.writeObject(ees);
            User user = new User(null);user.setA(33);
            oos.writeObject(user);
        }
        
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ZipTest.WORD_DIR + File.separator + "nums.txt"))){
            Employee[] os = (Employee[]) ois.readObject();
            
//            for (Employee e:os) {
//                e.setSalary(e.getSalary()+1000);
//                if (e.getName().equals("roy")){
//                    e.salary-=200;
//                }
//            }
            for (Employee e:os) {
                System.out.println(e);
            }
            User user = (User) ois.readObject();
            System.out.println(user.getA());
        }
        
    }
    
public static class Employee implements Serializable{
        
        private static final long serialVersionUID = 123L;
        
        public Employee() {
            super();
        }
        public Employee(String name, Double salary) {
            super();
            this.name = name;
            this.salary = salary;
        }
        private String name;
        private Double salary;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public Double getSalary() {
            return salary;
        }
        public void setSalary(Double salary) {
            this.salary = salary;
        }
        @Override
        public String toString() {
            return "Employee [name=" + name + ", salary=" + salary + "]";
        }
        
        private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
            System.out.println("what happen ??? read");
            in.defaultReadObject();
            
        }
        private void writeObject(ObjectOutputStream oos) throws IOException {  
            oos.defaultWriteObject();  
            System.out.println("what happen ?? write");
        }  
    }
    public static class Manage extends Employee implements Externalizable{

        private static final long serialVersionUID = 1234L;
        
        private Employee aide;

        public Manage() {
            super();
        }


        public Manage(String name, Double salary) {
            super(name, salary);
        }


        public Employee getAide() {
            return aide;
        }

        public void setAide(Employee aide) {
            this.aide = aide;
        }

        


        @Override
        public String toString() {
            return "Manage [aide=" + aide + ", getName()=" + getName() + ", getSalary()=" + getSalary() + "]";
        }

        /**
         * 作为一种自定义的方式 父类及自身属性的序列化由这两个方法负全责
         */
        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            // TODO Auto-generated method stub
            System.out.println("method in writeExternal...");
            out.writeUTF(getName());
            out.writeDouble(getSalary());
            out.writeObject(getAide());
        }


        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            // TODO Auto-generated method stub
            System.out.println("method in readExternal...");
            setName(in.readUTF());
            setSalary(in.readDouble());
            setAide((Employee)in.readObject());
        }
        
    }
    
    private static class User implements Serializable{

        private static final long serialVersionUID = 112323L;
        // 虽然 a 被transient修饰， 不会被默认的序列化机制处理， 但是在额外的readObject和writeObject处理
        private transient Integer a = null;
        
        
        public User(Integer a) {
            super();
            this.a = a;
        }

        public Integer getA() {
            return a;
        }

        public void setA(Integer a) {
            this.a = a;
        }

        private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
            System.out.println("what happen ??? read in user");
            in.defaultReadObject();
            this.a = in.readInt();
        }
        
        private void writeObject(ObjectOutputStream out) throws IOException {
            System.out.println("what happen ??? write in user");
            out.defaultWriteObject();
            out.writeInt(this.a);
        }
        
    }
}
