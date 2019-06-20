import java.io.BufferedReader;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.text.*;
import java.io.*;
public class Knn {


    public static final String PATH_NAME = "D:\\iris.txt";

    public static double errorCount = 0;

    public static void main(String[] args) {
       
        for (int i = 0; i < 30; i++) {
            Iris test = new Iris();
            List<Iris> list = new ArrayList<Iris>();
            //创建测试对象test与训练样本list
            test = readFile(test, list);
            //对计算出的距离排序，从小到大
            Collections.sort(list);
            //从排序数组取出前k个
            HashMap<String, Integer> map = getK(list, 5);
            //分类
            classify(map, test);
        }
        double rightPer = (1.0 - errorCount / 30.0) * 100;
        DecimalFormat df = new DecimalFormat("0.00");
        System.out.println("the right percent is " + df.format(rightPer) + "%");
        
    }

    public static Iris readFile(Iris test, List<Iris> list) {
        //读取数据集，赋值到对象
        try{
            FileReader reader = new FileReader(PATH_NAME);
            BufferedReader br = new BufferedReader(reader);
            //随机读取一行测试数据
            Random random = new Random();
            int row = random.nextInt(150) + 1;
            //System.out.println("test data row is :" + row);
            String target = "";
            while (row != 0) {
                target = br.readLine();
                row--;
            }
            String[] t = target.split(",");
            //先随机产生一个测试对象
            test = createIris(t);
            test.setDistance(0);
            System.out.println("the right name is " + test.getName());
            
    
            FileReader read = new FileReader(PATH_NAME);
            BufferedReader b = new BufferedReader(read);
            String other = "";
            //大量训练对象
            while ((other = b.readLine())!= null) {
                String[] ot = other.split(",");
                Iris practice = createIris(ot);
                //计算出测试对象与其它每个训练对象距离
                double distance = getDistance(test, practice);
                if (distance != 0) {
                    practice.setDistance(distance);
                    list.add(practice);
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return test;

    }

    public static Iris createIris(String[] t) {
        //萼片长度
        double sepalLen = Double.valueOf(t[0]);

        //萼片宽度
        double sepalWid = Double.valueOf(t[1]);
        
        //花瓣长度
        double petalLen = Double.valueOf(t[2]);

        //花瓣宽度
        double petalWid = Double.valueOf(t[3]);

        //类名
        String name = t[4];
        Iris iris = new Iris(sepalLen, sepalWid, petalLen, petalWid, name);
        return iris;
    }

    //计算出测试对象与其它每个训练对象距离
    public static double getDistance(Iris test, Iris practice) {
        double sepalLenDiff = test.getSepalLen() - practice.getSepalLen();
        double sepalWidDiff = test.getSepalWid() - practice.getSepalWid();
        double petalLenDiff = test.getPetalLen() - practice.getPetalLen();
        double petalWidDiff = test.getPetalWid() - practice.getPetalWid();

        double sum = Math.pow(sepalLenDiff, 2) + Math.pow(sepalWidDiff, 2) + Math.pow(petalLenDiff, 2) + Math.pow(petalWidDiff, 2);
       
        return Math.sqrt(sum);
    }

    //从排序数组取出前k个,k=5
    public static HashMap<String, Integer> getK(List<Iris> list, int k) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("Iris-setosa", 0);
        map.put("Iris-versicolor", 0);
        map.put("Iris-virginica", 0);
        for (int i = 0; i < k; i++) {
            Iris iris = list.get(i);
            map.put(iris.getName(), map.get(iris.getName()) + 1);
        }
        return map;

    }

    //判断分类,与正确的test.getName()比较
    public static void classify(HashMap<String, Integer> map, Iris test) {
        //从多数类判定分类
        int max = map.get("Iris-setosa");
        String name = "Iris-setosa";
        if (max < map.get("Iris-versicolor")) {
            max = map.get("Iris-versicolor");
            name = "Iris-versicolor";
        }
        if (max < map.get("Iris-virginica")) {
            max = map.get("Iris-virginica");
            name = "Iris-virginica";
        }
        if (name.equals(test.getName())) {
            System.out.println("The class name is " + name);
        } else {
            ++errorCount;
            System.out.println("Error");
        }

    }


}

class Iris implements Comparable<Iris>{

    //萼片长度
    private double sepalLen;

    //萼片宽度
    private double sepalWid;

    //花瓣长度
    private double petalLen;

    //花瓣宽度
    private double petalWid;

    //类名
    private String name;

    //距离
    private double distance;

    public Iris() {}

    public Iris(double sepalLen, double sepalWid, double petalLen, double petalWid, String name) {
        this.sepalLen = sepalLen;
        this.sepalWid = sepalWid;
        this.petalLen = petalLen;
        this.petalWid = petalWid;
        this.name = name;
       
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getSepalLen() {
        return sepalLen;
    }

    public void setSepalLen(double sepalLen) {
        this.sepalLen = sepalLen;
    }

    public double getSepalWid() {
        return sepalWid;
    }

    public void setSepalWid(double sepalWid) {
        this.sepalWid = sepalWid;
    }

    public double getPetalLen() {
        return petalLen;
    }

    public void setPetalLen(double petalLen) {
        this.petalLen = petalLen;
    }

    public double getPetalWid() {
        return petalWid;
    }

    public void setPetalWid(double petalWid) {
        this.petalWid = petalWid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
	public String toString() {
		return "Iris [sepalLen=" + sepalLen + ", sepalWid=" + sepalWid + ", petalLen=" + petalLen + ", petalWid="
				+ petalWid + ", name=" + name + ", distance=" + distance + "]";
	}

    @Override
    public int compareTo(Iris iris) {
        if (this.distance > iris.getDistance()) {
            return 1;
        } else {
            return -1;
        }
    }

}

