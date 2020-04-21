package com.xiaoge.org.graph;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author luozw
 * @date 2019/6/26 14:02
 * @company www.ecitychina.com
 * @description:
 */
public class Vertex<T> implements VertexInterface<T>, java.io.Serializable {
    /**
     * 标识标点,可以用不同类型来标识顶点如String,Integer....
     */
    private T label;
    /**
     * 到该顶点邻接点的边,实际以java.util.LinkedList存储
     */
    private List<Edge> edgeList;
    /**
     * 标识顶点是否已访问
     */
    private boolean visited;
    /**
     * 该顶点的前驱顶点
     */
    private VertexInterface<T> previousVertex;
    /**
     * 顶点的权值,与边的权值要区别开来
     */
    private double cost;

    public Vertex(T vertexLabel){
        label = vertexLabel;
        //是Vertex的属性,说明每个顶点都有一个edgeList用来存储所有与该顶点关系的边
        edgeList = new LinkedList<Edge>();
        visited = false;
        previousVertex = null;
        cost = 0;
    }

    /**
     *Task: 这里用了一个单独的类来表示边,主要是考虑到带权值的边
     *可以看出,Edge类封装了一个顶点和一个double类型变量
     *若不需要考虑权值,可以不需要单独创建一个Edge类来表示边,只需要一个保存顶点的列表即可
     * @author hapjin
     */
    protected class Edge implements java.io.Serializable {
        /**
         * 终点
         */
        private VertexInterface<T> vertex;
        /**
         * 权值
         */
        private double weight;

        /**
         * Vertex 类本身就代表顶点对象,因此在这里只需提供 endVertex，就可以表示一条边了
         * @param endVertex
         * @param edgeWeight
         */
        protected Edge(VertexInterface<T> endVertex, double edgeWeight){
            vertex = endVertex;
            weight = edgeWeight;
        }

        protected VertexInterface<T> getEndVertex(){
            return vertex;
        }
        protected double getWeight(){
            return weight;
        }
    }

    /**Task: 遍历该顶点邻接点的迭代器--为 getNeighborInterator()方法 提供迭代器
     * 由于顶点的邻接点以边的形式存储在java.util.List中,因此借助List的迭代器来实现
     * 由于顶点的邻接点由Edge类封装起来了--见Edge.java的定义的第一个属性
     * 因此，首先获得遍历Edge对象的迭代器,再根据获得的Edge对象解析出邻接点对象
     */
    private class NeighborIterator implements Iterator<VertexInterface<T>>{

        Iterator<Edge> edgesIterator;
        private NeighborIterator() {
            //获得遍历edgesList 的迭代器
            edgesIterator = edgeList.iterator();
        }
        @Override
        public boolean hasNext() {
            return edgesIterator.hasNext();
        }

        @Override
        public VertexInterface<T> next() {
            VertexInterface<T> nextNeighbor = null;
            if(edgesIterator.hasNext()){
                //LinkedList中存储的是Edge
                Edge edgeToNextNeighbor = edgesIterator.next();
                //从Edge对象中取出顶点
                nextNeighbor = edgeToNextNeighbor.getEndVertex();
            }else{
                throw new NoSuchElementException();
            }
            return nextNeighbor;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**Task: 生成一个遍历该顶点所有邻接边的权值的迭代器
     * 权值是Edge类的属性,因此先获得一个遍历Edge对象的迭代器,取得Edge对象,再获得权值
     * @author hapjin
     *
     */
    private class WeightIterator implements Iterator{//这里不知道为什么,用泛型报编译错误???

        private Iterator<Edge> edgesIterator;
        private WeightIterator(){
            edgesIterator = edgeList.iterator();
        }
        @Override
        public boolean hasNext() {
            return edgesIterator.hasNext();
        }
        @Override
        public Object next() {
            Double result;
            if(edgesIterator.hasNext()){
                Edge edge = edgesIterator.next();
                result = edge.getWeight();
            }else {
                throw new NoSuchElementException();
            }
            //从迭代器中取得结果时,需要强制转换成Double
            return (Object)result;
        }
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    @Override
    public T getLabel() {
        return label;
    }

    @Override
    public void visit() {
        this.visited = true;
    }

    @Override
    public void unVisit() {
        this.visited = false;
    }

    @Override
    public boolean isVisited() {
        return visited;
    }

    @Override
    public boolean connect(VertexInterface<T> endVertex, double edgeWeight) {
        // 将"边"(边的实质是顶点)插入顶点的邻接表
        boolean result = false;
        //顶点互不相同
        if(!this.equals(endVertex)){
            Iterator<VertexInterface<T>> neighbors = this.getNeighborInterator();
            boolean duplicateEdge = false;
            //保证不添加重复的边
            while(!duplicateEdge && neighbors.hasNext()){
                VertexInterface<T> nextNeighbor = neighbors.next();
                if(endVertex.equals(nextNeighbor)){
                    duplicateEdge = true;
                    break;
                }
            }//end while
            if(!duplicateEdge){
                //添加一条新边
                edgeList.add(new Edge(endVertex, edgeWeight));
                result = true;
            }//end if
        }//end if
        return result;
    }

    @Override
    public boolean connect(VertexInterface<T> endVertex) {
        return connect(endVertex, 0);
    }

    @Override
    public Iterator<VertexInterface<T>> getNeighborInterator() {
        return new NeighborIterator();
    }

    @Override
    public Iterator getWeightIterator() {
        return new WeightIterator();
    }

    @Override
    public boolean hasNeighbor() {
        //邻接点实质是存储是List中
        return !(edgeList.isEmpty());
    }

    @Override
    public VertexInterface<T> getUnvisitedNeighbor() {
        VertexInterface<T> result = null;
        Iterator<VertexInterface<T>> neighbors = getNeighborInterator();
        //获得该顶点的第一个未被访问的邻接点
        while(neighbors.hasNext() && result == null){
            VertexInterface<T> nextNeighbor = neighbors.next();
            if(!nextNeighbor.isVisited()){
                result = nextNeighbor;
            }
        }
        return result;
    }

    @Override
    public void setPredecessor(VertexInterface<T> predecessor) {
        this.previousVertex = predecessor;
    }

    @Override
    public VertexInterface<T> getPredecessor() {
        return this.previousVertex;
    }

    @Override
    public boolean hasPredecessor() {
        return this.previousVertex != null;
    }

    @Override
    public void setCost(double newCost) {
        cost = newCost;
    }

    @Override
    public double getCost() {
        return cost;
    }

    /**
     * 判断两个顶点是否相同
     * @param other
     * @return
     */
    @Override
    public boolean equals(Object other){
        boolean result;
        if((other == null) || (getClass() != other.getClass())){
            result = false;
        }else {
            Vertex<T> otherVertex = (Vertex<T>)other;
            //节点是否相同最终还是由标识 节点类型的类的equals() 决定
            result = label.equals(otherVertex.label);
        }
        return result;
    }
}
